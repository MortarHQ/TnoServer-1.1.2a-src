package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class AsmodayPrisonRealm implements AsmodayAttack {
   private static final String TAG_TARGET = "AsmodayPrisonTarget";
   private static final String TAG_HELD_SECONDS = "AsmodayPrisonHeldSeconds";

   @Override
   public String modeId() {
      return "asmoday.prison_realm";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return instance.isMastered(entity) ? (Double)cfg().prisonRealmCostMastered.get() : (Double)cfg().prisonRealmCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         entity.sendSystemMessage(
            Component.literal("Hold this mode while staring at a player for " + cfg().prisonRealmHoldSeconds.get() + " seconds.")
               .withStyle(ChatFormatting.DARK_RED)
         );
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      if (entity instanceof ServerPlayer owner) {
         if (AsmodayPrisonRealmManager.hasActivePrison(owner)) {
            owner.displayClientMessage(Component.literal("You are already holding a closed space.").withStyle(ChatFormatting.RED), true);
            return true;
         }

         double range = instance.isMastered(owner) ? (Double)cfg().prisonRealmRangeMastered.get() : (Double)cfg().prisonRealmRange.get();
         Entity selected = ObjectSelectionHelper.getTargetingEntity(owner, range, false);
         if (selected instanceof ServerPlayer target) {
            if (target != owner && !target.getAbilities().invulnerable && !target.isSpectator()) {
               CompoundTag tag = instance.getOrCreateTag();
               if (tag.contains("AsmodayPrisonTarget") && !Objects.equals(tag.getUUID("AsmodayPrisonTarget"), target.getUUID())) {
                  tag.putInt("AsmodayPrisonHeldSeconds", Math.max(0, tag.getInt("AsmodayPrisonHeldSeconds") - 1));
                  owner.displayClientMessage(Component.literal("Target changed. Spatial sentence destabilizing.").withStyle(ChatFormatting.RED), true);
                  return true;
               }

               tag.putUUID("AsmodayPrisonTarget", target.getUUID());
               int seconds = tag.getInt("AsmodayPrisonHeldSeconds");
               int required = (Integer)cfg().prisonRealmHoldSeconds.get();
               if (heldTicks % 20 == 0 && heldTicks > 0) {
                  tag.putInt("AsmodayPrisonHeldSeconds", ++seconds);
                  int remaining = Math.max(0, required - seconds);
                  owner.displayClientMessage(
                     Component.literal("Five-Hundred-Year Closed Space: " + seconds + "/" + required + " | " + remaining + "s")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED)),
                     true
                  );
                  target.displayClientMessage(
                     Component.literal("Space is closing around you. " + remaining + "s").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true
                  );
                  AsmodayPrisonRealmManager.sendChargeFx(owner, target, seconds, required, instance.isMastered(owner));
                  owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 0.45F, 0.26F);
               }

               target.setDeltaMovement(target.getDeltaMovement().scale((Double)cfg().prisonRealmHoldSlowMultiplier.get()));
               target.hurtMarked = true;
               if (seconds < required) {
                  return true;
               }

               if (EnergyHelper.isOutOfEnergy(owner, instance, mode)) {
                  owner.sendSystemMessage(Component.literal("You lack the energy to close a five-hundred-year space.").withStyle(ChatFormatting.RED));
                  clear(instance);
                  return false;
               }

               boolean sealed = AsmodayPrisonRealmManager.seal(owner, target, instance.isMastered(owner));
               if (sealed) {
                  instance.addMasteryPoint(owner);
                  instance.setCoolDown(
                     instance.isMastered(owner) ? (Integer)cfg().prisonRealmCooldownTicksMastered.get() : (Integer)cfg().prisonRealmCooldownTicks.get(), mode
                  );
               }

               clear(instance);
               return false;
            } else {
               decay(owner, instance, heldTicks);
               return true;
            }
         } else {
            decay(owner, instance, heldTicks);
            return true;
         }
      } else {
         entity.sendSystemMessage(Component.literal("Only a player can become the prison's lock.").withStyle(ChatFormatting.RED));
         clear(instance);
         return false;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      clear(instance);
   }

   private static void decay(ServerPlayer owner, ManasSkillInstance instance, int heldTicks) {
      CompoundTag tag = instance.getOrCreateTag();
      if (heldTicks % 20 == 0) {
         int seconds = Math.max(0, tag.getInt("AsmodayPrisonHeldSeconds") - 1);
         tag.putInt("AsmodayPrisonHeldSeconds", seconds);
         if (seconds <= 0) {
            tag.remove("AsmodayPrisonTarget");
         }

         owner.displayClientMessage(
            Component.literal("Keep your sight locked on a player. " + seconds + "/" + cfg().prisonRealmHoldSeconds.get()).withStyle(ChatFormatting.DARK_GRAY),
            true
         );
      }
   }

   private static void clear(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("AsmodayPrisonTarget");
      tag.putInt("AsmodayPrisonHeldSeconds", 0);
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }
}
