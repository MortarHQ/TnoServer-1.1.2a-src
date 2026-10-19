package com.finderfeed.raids_enhanced.content.items.handcannon;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class HandCannonClientItemExtensions implements IClientItemExtensions {
   @Nullable
   public ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
      return ArmPose.BOW_AND_ARROW;
   }
}
