package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.ArthurHolyDisintegrationVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class ArthurHolyDisintegrationHandler implements ArthurModeHandler {
   private static final String ACTIVE = "ArthurHolyDisintegrationActive";
   private static final String X = "ArthurHolyDisintegrationX";
   private static final String Y = "ArthurHolyDisintegrationY";
   private static final String Z = "ArthurHolyDisintegrationZ";
   private static final int CAST_TICKS = 100;

   @Override
   public String modeId() {
      return "arthur_once_and_future_king.holy_disintegration";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.arthur.holyDisintegrationCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (entity.level() instanceof ServerLevel level) {
            CompoundTag var9 = instance.getOrCreateTag();
            LivingEntity target = RoyalSkillSupport.lookTarget(entity, 96.0);
            Vec3 position = target == null ? RoyalSkillSupport.aimEnd(entity, 52.0) : target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            var9.putBoolean("ArthurHolyDisintegrationActive", true);
            var9.putDouble("ArthurHolyDisintegrationX", position.x);
            var9.putDouble("ArthurHolyDisintegrationY", position.y);
            var9.putDouble("ArthurHolyDisintegrationZ", position.z);
            instance.markDirty();
            ArthurHolyDisintegrationVfxPayload.send(
               level,
               entity,
               target,
               entity.getEyePosition().add(entity.getLookAngle().scale(0.55)),
               position,
               ((Double)MoreSkillsConfigs.GRAND.arthur.holyDisintegrationRadius.get()).floatValue(),
               100,
               0
            );
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 3.0F, 0.45F);
            entity.swing(InteractionHand.MAIN_HAND, true);
         }
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("ArthurHolyDisintegrationActive")) {
         return false;
      }

      int capped = Math.min(heldTicks, 100);
      if (entity instanceof Player player && heldTicks % 5 == 0) {
         player.displayClientMessage(
            Component.translatable("tensura.skill.time_held.max", new Object[]{capped / 20, 5}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), true
         );
      }

      if (heldTicks < 100) {
         return true;
      }

      if (entity.level() instanceof ServerLevel level) {
         if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            clear(instance);
            return false;
         } else {
            Vec3 position = new Vec3(
               tag.getDouble("ArthurHolyDisintegrationX"), tag.getDouble("ArthurHolyDisintegrationY"), tag.getDouble("ArthurHolyDisintegrationZ")
            );
            float damage = ((Double)MoreSkillsConfigs.GRAND.arthur.holyDisintegrationDamage.get()).floatValue();
            double radius = (Double)MoreSkillsConfigs.GRAND.arthur.holyDisintegrationRadius.get();
            RoyalSkillSupport.damageArea(entity, instance, mode, position, radius, damage * 0.65F, TensuraDamageTypes.HOLY_DAMAGE);
            RoyalSkillSupport.damageArea(entity, instance, mode, position, radius, damage * 0.35F, TensuraDamageTypes.LIGHT_ELEMENTAL);
            ArthurHolyDisintegrationVfxPayload.send(level, entity, null, position.add(0.0, 96.0, 0.0), position, (float)radius, 54, 1);
            level.playSound(null, position.x, position.y, position.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 8.0F, 0.55F);
            level.playSound(null, position.x, position.y, position.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 6.0F, 0.8F);
            level.playSound(null, position.x, position.y, position.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.5F, 1.7F);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.arthur.holyDisintegrationCooldown.get(), mode);
            instance.addMasteryPoint(entity);
            clear(instance);
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (heldTicks < 100) {
         clear(instance);
      }
   }

   @Override
   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return true;
   }

   private static void clear(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("ArthurHolyDisintegrationActive");
      tag.remove("ArthurHolyDisintegrationX");
      tag.remove("ArthurHolyDisintegrationY");
      tag.remove("ArthurHolyDisintegrationZ");
      instance.markDirty();
   }

   @Override
   public void cleanup(LivingEntity entity) {
   }
}
