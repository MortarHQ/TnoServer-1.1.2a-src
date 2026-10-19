package com.cerbon.bosses_of_mass_destruction.entity.util;

import net.minecraft.nbt.CompoundTag;

public interface INbtHandler {
   CompoundTag toTag(CompoundTag var1);

   void fromTag(CompoundTag var1);
}
