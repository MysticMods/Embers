package mysticmods.embers.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Objects;
import java.util.function.Consumer;

public class MalleableMetalDataComponent implements TooltipProvider {

    public static final Codec<MalleableMetalDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("stackHeat").forGetter(MalleableMetalDataComponent::getStackHeat),
            Codec.INT.fieldOf("maximumStackHeat").forGetter(MalleableMetalDataComponent::getMaximumStackHeat),
            Codec.INT.fieldOf("ingots").forGetter(MalleableMetalDataComponent::getIngots),
            Codec.INT.fieldOf("nuggets").forGetter(MalleableMetalDataComponent::getNuggets),
            MalleableMetal.CODEC.fieldOf("malleableMetal").forGetter(MalleableMetalDataComponent::getMalleableMetal)
    ).apply(instance, MalleableMetalDataComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MalleableMetalDataComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MalleableMetalDataComponent::getStackHeat,
            ByteBufCodecs.INT, MalleableMetalDataComponent::getMaximumStackHeat,
            ByteBufCodecs.INT, MalleableMetalDataComponent::getIngots,
            ByteBufCodecs.INT, MalleableMetalDataComponent::getNuggets,
            MalleableMetal.STREAM_CODEC, MalleableMetalDataComponent::getMalleableMetal,
            MalleableMetalDataComponent::new
    );

    private final int stackHeat;
    private final int maximumStackHeat;
    private final int ingots;
    private final int nuggets;
    private final MalleableMetal malleableMetal;

    public MalleableMetalDataComponent(int maximumStackHeat, MalleableMetal malleableMetal) {
        this(0, maximumStackHeat, 0, 0, malleableMetal);
    }

    public MalleableMetalDataComponent(int stackHeat, int maximumStackHeat, int ingots, int nuggets, MalleableMetal malleableMetal) {
        this.stackHeat = stackHeat;
        this.maximumStackHeat = maximumStackHeat;
        this.ingots = ingots;
        this.nuggets = nuggets;
        this.malleableMetal = malleableMetal;
    }

    public int getStackHeat() {
        return stackHeat;
    }

    public int getMaximumStackHeat() {
        return maximumStackHeat;
    }

    public int getIngots() {
        return ingots;
    }

    public int getNuggets() {
        return nuggets;
    }

    public MalleableMetal getMalleableMetal() {
        return malleableMetal;
    }

    public MalleableMetalDataComponent addIngots(int ingotAmount) {
        return new MalleableMetalDataComponent(this.stackHeat, this.maximumStackHeat, this.ingots + ingotAmount, this.nuggets, this.malleableMetal);
    }

    public MalleableMetalDataComponent removeIngots(int ingotAmount) {
        int newIngots = this.ingots - ingotAmount;
        if (newIngots < 0) {
            newIngots = 0;
        }
        return new MalleableMetalDataComponent(this.stackHeat, this.maximumStackHeat, newIngots, this.nuggets, this.malleableMetal);
    }

    public MalleableMetalDataComponent addNuggets(int nuggetAmount) {
        int newNuggets = this.nuggets + nuggetAmount;
        int extraIngots = 0;
        if (newNuggets >= 9) {
            newNuggets -= 9;
            extraIngots += 1;
        }

        return new MalleableMetalDataComponent(this.stackHeat, this.maximumStackHeat, this.ingots + extraIngots, newNuggets, this.malleableMetal);
    }

    public MalleableMetalDataComponent removeNuggets(int nuggetAmount) {
        int newNuggets = this.nuggets - nuggetAmount;
        if (newNuggets < 0) {
            newNuggets = 0;
        }
        return new MalleableMetalDataComponent(this.stackHeat, this.maximumStackHeat, this.ingots, newNuggets, this.malleableMetal);
    }

    public MalleableMetalDataComponent setMalleableMetal(MalleableMetal metal) {
        return new MalleableMetalDataComponent(this.stackHeat, this.maximumStackHeat, this.ingots, this.nuggets, metal);
    }

    public MalleableMetalDataComponent setMaxHeat() {
        return new MalleableMetalDataComponent(this.maximumStackHeat, this.maximumStackHeat, this.ingots, this.nuggets, this.malleableMetal);
    }

    //Remove heat
    public MalleableMetalDataComponent removeHeat(int amount) {
        int newStackHeat = this.stackHeat - amount;
        if (newStackHeat < 0) {
            newStackHeat = 0;
        }
        return new MalleableMetalDataComponent(newStackHeat, this.maximumStackHeat, this.ingots, this.nuggets, this.malleableMetal);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        tooltipAdder.accept(Component.translatable("tooltip.embers.heat", stackHeat, maximumStackHeat));
        tooltipAdder.accept(Component.translatable("tooltip.embers.ingots", ingots));
        tooltipAdder.accept(Component.translatable("tooltip.embers.nuggets", nuggets));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MalleableMetalDataComponent that = (MalleableMetalDataComponent) obj;
        return stackHeat == that.stackHeat &&
                maximumStackHeat == that.maximumStackHeat &&
                ingots == that.ingots &&
                nuggets == that.nuggets &&
                malleableMetal.equals(that.malleableMetal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stackHeat, maximumStackHeat, ingots, nuggets, malleableMetal);
    }
}
