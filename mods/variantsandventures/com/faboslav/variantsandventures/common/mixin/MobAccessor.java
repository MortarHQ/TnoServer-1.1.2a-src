package com.faboslav.variantsandventures.common.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mob.class)
public interface MobAccessor {
   @Invoker("getEquipmentDropChance")
   float variantsandventures$getEquipmentDropChance(EquipmentSlot var1);
}
