package com.chronodawn.items;

import com.chronodawn.registry.ModEffects;
import com.chronodawn.registry.ModSounds;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class ChronoAegisItem extends Item {
   public ChronoAegisItem(Properties properties) {
      super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide) {
         MobEffect effect = (MobEffect)ModEffects.CHRONO_AEGIS_BUFF.get();
         Holder<MobEffect> effectHolder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
         if (player.hasEffect(effectHolder)) {
            player.displayClientMessage(Component.translatable("item.chronodawn.chrono_aegis.already_active"), true);
            return InteractionResultHolder.fail(stack);
         }

         double BUFF_RANGE = 32.0;
         int playersAffected = 0;

         for (Player nearbyPlayer : level.players()) {
            if (nearbyPlayer.level().dimension() == player.level().dimension() && nearbyPlayer.distanceTo(player) <= 32.0) {
               nearbyPlayer.addEffect(new MobEffectInstance(effectHolder, 12000, 0, false, true, true));
               if (nearbyPlayer == player) {
                  nearbyPlayer.displayClientMessage(Component.translatable("item.chronodawn.chrono_aegis.activated"), true);
               } else {
                  nearbyPlayer.displayClientMessage(
                     Component.translatable("item.chronodawn.chrono_aegis.received_from_ally", new Object[]{player.getName()}), true
                  );
               }

               playersAffected++;
            }
         }

         level.playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)ModSounds.CHRONO_AEGIS_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }

         return InteractionResultHolder.success(stack);
      } else {
         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      tooltipComponents.add(Component.translatable("item.chronodawn.chrono_aegis.tooltip"));
      tooltipComponents.add(Component.translatable("item.chronodawn.chrono_aegis.tooltip.duration"));
   }

   public boolean isFoil(ItemStack stack) {
      return true;
   }
}
