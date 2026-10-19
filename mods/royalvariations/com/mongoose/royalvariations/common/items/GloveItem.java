package com.mongoose.royalvariations.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.utils.ItemHelper;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GloveItem extends Item implements ICurioItem {
   public GloveItem() {
      super(new Properties().stacksTo(1));
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return true;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      if (this == RVItems.SPECTRAL_GAUNTLET.get()) {
         ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(tooltip));
      }
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.worn"));
      tooltip.add(Component.translatable("info.royalvariations.spectral_gauntlet.0"));
      tooltip.add(Component.translatable("info.royalvariations.spectral_gauntlet.1"));
   }

   public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
      Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
      if (this == RVItems.SPECTRAL_GAUNTLET.get()) {
         map.put(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(RoyalVariations.location("glove_reach_extension"), 2.0, Operation.ADD_VALUE));
      }

      return map;
   }
}
