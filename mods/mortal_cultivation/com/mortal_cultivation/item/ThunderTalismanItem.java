package com.mortal_cultivation.item;

import com.mortal_cultivation.entity.ThunderTalismanEntity;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class ThunderTalismanItem extends Item {
   public static final int MAX_CHARGE = 20;
   public static final double MAGICULE_COST = 150.0;

   public ThunderTalismanItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      level.playSound(
         null,
         player.getX(),
         player.getY(),
         player.getZ(),
         SoundEvents.ENCHANTMENT_TABLE_USE,
         SoundSource.PLAYERS,
         0.5F,
         0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      player.startUsingItem(hand);
      return InteractionResultHolder.consume(itemstack);
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 72000;
   }

   public Component getName(ItemStack stack) {
      return Component.translatable(this.getDescriptionId(stack)).withStyle(ChatFormatting.LIGHT_PURPLE);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("item.mortal_cultivation.thunder_talisman.desc").withStyle(ChatFormatting.GRAY));
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
      if (livingEntity instanceof Player player) {
         int chargeTime = this.getUseDuration(stack, livingEntity) - timeLeft;
         float powerScale = this.getPowerForTime(chargeTime);
         if (!(powerScale < 0.95F)) {
            if (this.hasEnoughMagicule(player)) {
               level.playSound(
                  null,
                  player.getX(),
                  player.getY(),
                  player.getZ(),
                  SoundEvents.LIGHTNING_BOLT_THUNDER,
                  SoundSource.PLAYERS,
                  0.5F,
                  0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
               );
               if (!level.isClientSide) {
                  this.consumeMagicule(player);
                  ThunderTalismanEntity talismanEntity = new ThunderTalismanEntity(level, player);
                  talismanEntity.setItem(stack);
                  float velocity = 1.5F;
                  talismanEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 1.0F);
                  talismanEntity.setCritArrow(true);
                  level.addFreshEntity(talismanEntity);
                  player.awardStat(Stats.ITEM_USED.get(this));
                  if (!player.getAbilities().instabuild) {
                     stack.shrink(1);
                  }
               }
            }
         }
      }
   }

   private boolean hasEnoughMagicule(Player player) {
      return player.getAbilities().instabuild ? true : EnergyHelper.isOutOfMagiculeConsuming(player, 0.0, 150.0) <= 0.0;
   }

   private void consumeMagicule(Player player) {
      if (!player.getAbilities().instabuild) {
         EnergyHelper.isOutOfMagiculeConsuming(player, 150.0);
      }
   }

   private float getPowerForTime(int chargeTime) {
      float power = chargeTime / 20.0F;
      power = (power * power + power * 2.0F) / 3.0F;
      if (power > 1.0F) {
         power = 1.0F;
      }

      return power;
   }
}
