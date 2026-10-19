package com.github.ysbbbbbb.kaleidoscopecookery.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import org.jetbrains.annotations.Nullable;

public class StrawHatItem extends ArmorItem {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "textures/models/armor/straw_hat.png");
   private static final ResourceLocation TEXTURE_FLOWER = ResourceLocation.fromNamespaceAndPath(
      "kaleidoscope_cookery", "textures/models/armor/straw_hat_flower.png"
   );
   private final boolean hasFlower;

   public StrawHatItem(boolean hasFlower) {
      super(ArmorMaterials.LEATHER, Type.HELMET, new Properties().stacksTo(1));
      this.hasFlower = hasFlower;
   }

   @Nullable
   public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
      return this.hasFlower ? TEXTURE_FLOWER : TEXTURE;
   }

   public boolean hasFlower() {
      return this.hasFlower;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("tooltip.kaleidoscope_cookery.straw_hat").withStyle(ChatFormatting.GRAY));
   }
}
