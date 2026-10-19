package com.mongoose.royalvariations.common.items.armor;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.utils.ItemHelper;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class RoyalKnightArmor extends ArmorItem {
   public RoyalKnightArmor(Type type) {
      super(RVArmorMaterials.ROYAL_KNIGHT, type, new Properties().durability(type.getDurability(21)));
   }

   public ResourceLocation getArmorTexture(
      @Nonnull ItemStack stack, @Nonnull Entity entity, @Nonnull EquipmentSlot slot, @Nonnull Layer layer, boolean isInnerModel
   ) {
      if (slot == EquipmentSlot.HEAD) {
         return RoyalVariations.location("textures/models/armor/royal_knight_helmet.png");
      } else if (slot == EquipmentSlot.CHEST) {
         return RoyalVariations.location("textures/models/armor/royal_knight_cuirass.png");
      } else if (slot == EquipmentSlot.LEGS) {
         return RoyalVariations.location("textures/models/armor/royal_knight_leggings.png");
      } else {
         return slot == EquipmentSlot.FEET
            ? RoyalVariations.location("textures/models/armor/royal_knight_boots.png")
            : RoyalVariations.location("textures/models/armor/royal_knight_armor.png");
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      ItemHelper.addOnShift(tooltipComponents, () -> this.addInformationAfterShift(tooltipComponents));
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.royal_knight_armor.0"));
      tooltip.add(Component.translatable("info.royalvariations.royal_knight_armor.1"));
   }
}
