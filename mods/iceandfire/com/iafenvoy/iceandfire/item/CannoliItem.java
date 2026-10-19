package com.iafenvoy.iceandfire.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class CannoliItem extends Item {
   public CannoliItem() {
      super(
         new Properties()
            .food(
               new Builder()
                  .nutrition(20)
                  .saturationModifier(2.0F)
                  .alwaysEdible()
                  .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 2), 1.0F)
                  .build()
            )
      );
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.cannoli.desc").withStyle(ChatFormatting.GRAY));
   }
}
