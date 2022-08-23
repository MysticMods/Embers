package com.mystic.embers.core.capability.reciever;

import net.minecraft.nbt.CompoundTag;

public class EmberReceiverCapability implements IEmberReceiverCapability {
	//private final Object2FloatOpenHashMap<Herb> HERB_MAP = new Object2FloatOpenHashMap<>();

	public EmberReceiverCapability() {
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag result = new CompoundTag();
//        HERB_MAP.forEach((herb, value) -> {
//            CompoundTag tag = new CompoundTag();
//            tag.putString("herb", ModRegistries.HERB_REGISTRY.get().getKey(herb).toString());
//            tag.putFloat("value", value);
//            result.add(tag);
//        });
		return result;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		//        HERB_MAP.clear();
//        for (int i = 0; i < nbt.size(); i++) {
//            CompoundTag tag = nbt.getCompound(i);
//            HERB_MAP.put(ModRegistries.HERB_REGISTRY.get().getValue(new ResourceLocation(tag.getString("herb"))), tag.getFloat("value"));
//        }
	}


}