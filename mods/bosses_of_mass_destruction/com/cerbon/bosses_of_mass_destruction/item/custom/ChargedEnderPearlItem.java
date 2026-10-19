package com.cerbon.bosses_of_mass_destruction.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ChargedEnderPearlItem extends Item {
   public ChargedEnderPearlItem(Properties properties) {
      super(properties);
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand usedHand) {
      ItemStack itemStack = player.getItemInHand(usedHand);
      level.playSound(
         null,
         player.getX(),
         player.getY(),
         player.getZ(),
         SoundEvents.ENDER_PEARL_THROW,
         SoundSource.NEUTRAL,
         0.5F,
         0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      int itemCooldown = 180;
      player.getCooldowns().addCooldown(this, itemCooldown);
      if (!level.isClientSide()) {
         ThrowableItemProjectile projectile = new ChargedEnderPearlEntity(level, player);
         projectile.setItem(itemStack);
         projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
         level.addFreshEntity(projectile);
      }

      player.awardStat(Stats.ITEM_USED.get(this));
      return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(Component.translatable("item.bosses_of_mass_destruction.charged_ender_pearl.tooltip").withStyle(ChatFormatting.DARK_GRAY));
      tooltipComponents.add(Component.translatable("item.bosses_of_mass_destruction.charged_ender_pearl.tooltip2").withStyle(ChatFormatting.DARK_GRAY));
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
   }
}
