package com.iafenvoy.iceandfire.item.tool;

import com.iafenvoy.iceandfire.item.ability.PostHitAbility;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class ActivePostHitShovelItem extends ShovelItem {
   private final PostHitAbility ability;

   public ActivePostHitShovelItem(Tier toolMaterial, Properties settings, PostHitAbility ability) {
      super(toolMaterial, settings);
      this.ability = ability;
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (this.ability.isEnable()) {
         this.ability.active(stack, target, attacker);
      }

      return super.hurtEnemy(stack, target, attacker);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      if (this.ability.isEnable()) {
         this.ability.addDescription(tooltip);
      }
   }
}
