package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class ItemGrantSunsBlessing extends Item {
   public ItemGrantSunsBlessing(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
      playerIn.addEffect(new MobEffectInstance(EffectHandler.SUNS_BLESSING, -1, 0, false, false));
      return super.use(worldIn, playerIn, hand);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      int effectDuration = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get();
      int solarBeamCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get();
      int supernovaCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.supernovaCost.get();
      tooltip.add(
         Component.translatable(this.getDescriptionId() + ".text.0")
            .append(" " + effectDuration + " ")
            .append(Component.translatable(this.getDescriptionId() + ".text.1"))
            .setStyle(ItemHandler.TOOLTIP_STYLE)
      );
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.3").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.4").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(
         Component.translatable(this.getDescriptionId() + ".text.5")
            .append(" " + solarBeamCost + " ")
            .append(Component.translatable(this.getDescriptionId() + ".text.6"))
            .setStyle(ItemHandler.TOOLTIP_STYLE)
      );
      MutableComponent supernovaComponent = Component.translatable(this.getDescriptionId() + ".text.7");
      if (supernovaCost >= effectDuration) {
         supernovaComponent.append(Component.translatable(this.getDescriptionId() + ".text.8"));
      } else {
         supernovaComponent.append(" " + supernovaCost + " minutes");
      }

      supernovaComponent.append(Component.translatable(this.getDescriptionId() + ".text.9"));
      supernovaComponent.withStyle(ItemHandler.TOOLTIP_STYLE);
      tooltip.add(supernovaComponent);
   }
}
