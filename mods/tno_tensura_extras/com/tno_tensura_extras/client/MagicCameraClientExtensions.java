package com.tno_tensura_extras.client;

import com.tno_tensura_extras.registry.ExtrasItems;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public final class MagicCameraClientExtensions {
   private MagicCameraClientExtensions() {
   }

   public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(new IClientItemExtensions() {
         public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
            return MagicCameraArmPose.select(entity, hand);
         }
      }, new Item[]{ExtrasItems.MAGIC_CAMERA.get()});
   }
}
