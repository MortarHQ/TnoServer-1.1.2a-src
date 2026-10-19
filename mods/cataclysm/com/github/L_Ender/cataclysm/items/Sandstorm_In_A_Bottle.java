package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class Sandstorm_In_A_Bottle extends Item {
   public Sandstorm_In_A_Bottle(Properties properties) {
      super(properties);
   }

   @Nonnull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide()) {
         for (int i = 0; i < 2; i++) {
            float angle = i * (float) Math.PI;
            double sx = player.getX() + Mth.cos(angle) * 6.0F;
            double sy = player.getY();
            double sz = player.getZ() + Mth.sin(angle) * 6.0F;
            Sandstorm_Entity projectile = new Sandstorm_Entity(level, sx, sy, sz, 200, angle, player);
            boolean flag = level.addFreshEntity(projectile);
            if (flag) {
               player.getCooldowns().addCooldown(this, CMCommonConfig.SandstormInABottle.cooldown);
            }
         }
      }

      return InteractionResultHolder.success(stack);
   }

   public boolean isEnchantable(ItemStack stack) {
      return false;
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return false;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
   }
}
