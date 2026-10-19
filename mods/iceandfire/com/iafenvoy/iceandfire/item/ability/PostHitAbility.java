package com.iafenvoy.iceandfire.item.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface PostHitAbility extends Ability {
   void active(ItemStack var1, LivingEntity var2, LivingEntity var3);
}
