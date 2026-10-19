package com.mongoose.royalvariations.client.render.item;

import com.mongoose.royalvariations.client.render.RVModelLayer;
import com.mongoose.royalvariations.client.render.model.RoyalKnightBootsModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightCuirassModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightHelmetModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightLeggingsModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ArmorClientExtension implements IClientItemExtensions {
   public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
      EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
      HumanoidModel<?> model;
      if (equipmentSlot == EquipmentSlot.HEAD) {
         model = new RoyalKnightHelmetModel(modelSet.bakeLayer(RVModelLayer.ROYAL_KNIGHT_HELMET));
      } else if (equipmentSlot == EquipmentSlot.CHEST) {
         model = new RoyalKnightCuirassModel(modelSet.bakeLayer(RVModelLayer.ROYAL_KNIGHT_CUIRASS));
      } else if (equipmentSlot == EquipmentSlot.LEGS) {
         model = new RoyalKnightLeggingsModel(modelSet.bakeLayer(RVModelLayer.ROYAL_KNIGHT_LEGGINGS));
      } else {
         model = new RoyalKnightBootsModel(modelSet.bakeLayer(RVModelLayer.ROYAL_KNIGHT_BOOTS));
      }

      model.hat.visible = equipmentSlot == EquipmentSlot.HEAD;
      model.body.visible = equipmentSlot == EquipmentSlot.CHEST;
      model.rightArm.visible = equipmentSlot == EquipmentSlot.CHEST;
      model.leftArm.visible = equipmentSlot == EquipmentSlot.CHEST;
      model.rightLeg.visible = equipmentSlot == EquipmentSlot.FEET;
      model.leftLeg.visible = equipmentSlot == EquipmentSlot.FEET;
      model.young = original.young;
      model.crouching = original.crouching;
      model.riding = original.riding;
      model.rightArmPose = original.rightArmPose;
      model.leftArmPose = original.leftArmPose;
      return model;
   }
}
