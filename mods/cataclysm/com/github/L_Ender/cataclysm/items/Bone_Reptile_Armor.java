package com.github.L_Ender.cataclysm.items;

import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;

public class Bone_Reptile_Armor extends Cataclysm_Armor {
   public Bone_Reptile_Armor(Holder<ArmorMaterial> material, Type slot, Properties properties) {
      super(material, slot, properties);
   }

   public ResourceLocation getArmorTexture(
      @Nonnull ItemStack stack, @Nonnull Entity entity, @Nonnull EquipmentSlot slot, @Nonnull Layer layer, boolean isInnerModel
   ) {
      return ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/armor/bone_reptile_armor.png");
   }
}
