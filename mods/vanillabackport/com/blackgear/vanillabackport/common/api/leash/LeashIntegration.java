package com.blackgear.vanillabackport.common.api.leash;

import com.blackgear.platform.common.integration.MobInteraction;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

public class LeashIntegration implements MobInteraction {
   public InteractionResult onInteract(Player player, Entity entity, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!entity.level().isClientSide()
         && player.isSecondaryUseActive()
         && entity instanceof Leashable leashable
         && leashable.canBeLeashed()
         && entity.isAlive()
         && !(entity instanceof LivingEntity le && le.isBaby())) {
         List<Leashable> nearby = LeashPhysics.inArea(entity, l -> l.getLeashHolder() == player);
         if (!nearby.isEmpty()) {
            boolean attached = false;

            for (Leashable target : nearby) {
               if (LeashPhysics.canAttachLeash(target, entity)) {
                  target.setLeashedTo(entity, true);
                  attached = true;
               }
            }

            if (attached) {
               entity.level().gameEvent(GameEvent.ENTITY_INTERACT, entity.blockPosition(), Context.of(player));
               entity.playSound(SoundEvents.LEASH_KNOT_PLACE);
               return InteractionResult.SUCCESS;
            }
         }
      }

      if (stack.is(Items.SHEARS)) {
         boolean cut = shearAllConnections(entity, player);
         if (cut) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            return InteractionResult.SUCCESS;
         }
      }

      if (entity.isAlive() && entity instanceof Leashable leashable) {
         if (leashable.getLeashHolder() == player) {
            if (!entity.level().isClientSide()) {
               leashable.dropLeash(true, !player.isCreative());
               entity.gameEvent(GameEvent.ENTITY_INTERACT, player);
               entity.playSound(SoundEvents.LEASH_KNOT_BREAK);
            }

            return InteractionResult.SUCCESS;
         }

         if (stack.is(Items.LEAD) && !(leashable.getLeashHolder() instanceof Player)) {
            if (!entity.level().isClientSide() && LeashPhysics.canAttachLeash(leashable, player)) {
               if (leashable.isLeashed()) {
                  leashable.dropLeash(true, true);
               }

               leashable.setLeashedTo(player, true);
               entity.playSound(SoundEvents.LEASH_KNOT_PLACE);
               if (!player.isCreative()) {
                  stack.shrink(1);
               }
            }

            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   private static boolean shearAllConnections(Entity entity, Player player) {
      boolean cut = LeashPhysics.dropAllConnections(entity, player);
      if (cut && entity.level() instanceof ServerLevel server) {
         server.playSound(null, entity.blockPosition(), SoundEvents.SHEEP_SHEAR, player != null ? player.getSoundSource() : entity.getSoundSource(), 1.0F, 1.0F);
      }

      return cut;
   }
}
