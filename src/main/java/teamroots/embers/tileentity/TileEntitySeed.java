package teamroots.embers.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.jcraft.jorbis.Block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import teamroots.embers.Embers;
import teamroots.embers.EventManager;
import teamroots.embers.RegistryManager;
import teamroots.embers.api.tile.IEmberInjectable;
import teamroots.embers.block.BlockBreaker;
import teamroots.embers.block.BlockItemTransfer;
import teamroots.embers.block.BlockSeed;
import teamroots.embers.block.BlockVacuum;
import teamroots.embers.network.PacketHandler;
import teamroots.embers.network.message.MessageTEUpdate;
import teamroots.embers.particle.ParticleUtil;
import teamroots.embers.util.Misc;

public class TileEntitySeed extends TileEntity implements ITileEntityBase, ITickable, IEmberInjectable {
	boolean[] willSpawn = new boolean[12];

	public static ResourceLocation TEXTURE_IRON = new ResourceLocation(Embers.MODID + ":textures/blocks/material_iron.png");
	public static ResourceLocation TEXTURE_GOLD = new ResourceLocation(Embers.MODID + ":textures/blocks/material_gold.png");
	public static ResourceLocation TEXTURE_COPPER = new ResourceLocation(Embers.MODID + ":textures/blocks/material_copper.png");
	public static ResourceLocation TEXTURE_LEAD = new ResourceLocation(Embers.MODID + ":textures/blocks/material_lead.png");
	public static ResourceLocation TEXTURE_SILVER = new ResourceLocation(Embers.MODID + ":textures/blocks/material_silver.png");

	protected int size = 0;
	protected int ticksExisted = 0;
	protected int material = -1;
	protected Random random = new Random();

	public TileEntitySeed() {
		resetSpawns();
	}

	public void resetSpawns(){
		for (int i = 0; i < 12; i ++){
			willSpawn[i] = random.nextInt(3) == 0;
		}
	}
	
	public TileEntitySeed setMaterial(int material){
		this.material = material;
		return this;
	}

	public String getSpawnString(){
		String result = "";
		for (int i = 0; i < 12; i ++){
			result += willSpawn[i] ? "1" : "0";
		}
		return result;
	}

	public void loadSpawnsFromString(String s){
		for (int i = 0; i < 12; i ++){
			willSpawn[i] = s.substring(i, i+1).compareTo("1") == 0;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setString("spawns", getSpawnString());
		tag.setInteger("size", size);
		tag.setInteger("material", material);
		return tag;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		loadSpawnsFromString(tag.getString("spawns"));
		size = tag.getInteger("size");
		material = tag.getInteger("material");
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		this.invalidate();
		world.setTileEntity(pos, null);
	}

	@Override
	public void update() {
		if (material == -1){
			material = world.getBlockState(getPos()).getValue(BlockSeed.type);
		}
		ticksExisted ++;
		if (size > 1000){
			size = 0;
			for (int i = 0; i < 12; i ++){
				if (willSpawn[i] && !getWorld().isRemote){
					ItemStack nuggetStack = getDrop();
					float offX = 0.4f*(float)Math.sin(Math.toRadians(i*30.0));
					float offZ = 0.4f*(float)Math.cos(Math.toRadians(i*30.0));
					world.spawnEntity(new EntityItem(world,getPos().getX()+0.5+offX,getPos().getY()+0.5f,getPos().getZ()+0.5+offZ,nuggetStack));
				}
			}
			markDirty();
			resetSpawns();
		}
	}

	protected ItemStack getDrop() {
		switch (material) {
			case 0: return new ItemStack(Items.IRON_NUGGET,1);
			case 1: return new ItemStack(Items.GOLD_NUGGET,1);
			case 2: return new ItemStack(RegistryManager.nugget_copper,1);
			case 3: return new ItemStack(RegistryManager.nugget_lead,1);
			case 4: return new ItemStack(RegistryManager.nugget_silver,1);
			default: return ItemStack.EMPTY;
		}
	}

	@Override
	public void inject(TileEntity injector, double ember) {
		size++;
		markDirty();
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean dirty = false;

	public ResourceLocation getTexture() {
		switch (material) {
			case 0: return TEXTURE_IRON;
			case 1: return TEXTURE_GOLD;
			case 2: return TEXTURE_COPPER;
			case 3: return TEXTURE_LEAD;
			case 4: return TEXTURE_SILVER;
			default: return TEXTURE_IRON;
		}
	}
	
	@Override
	public void markForUpdate(){
		EventManager.markTEForUpdate(getPos(), this);
	}
	
	@Override
	public void markDirty(){
		markForUpdate();
		super.markDirty();
	}
}
