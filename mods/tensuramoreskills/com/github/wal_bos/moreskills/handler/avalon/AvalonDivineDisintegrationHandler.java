package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonDivineDisintegrationVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Comparator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AvalonDivineDisintegrationHandler implements AvalonModeHandler {
   private static final String ACTIVE = "AvalonDivineDisintegrationActive";
   private static final String TICKS = "AvalonDivineDisintegrationTicks";
   private static final String X = "AvalonDivineDisintegrationX";
   private static final String Y = "AvalonDivineDisintegrationY";
   private static final String Z = "AvalonDivineDisintegrationZ";
   private static final String MODE = "AvalonDivineDisintegrationMode";
   private static final String TARGET = "AvalonDivineDisintegrationTarget";

   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.divine_disintegration";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.getBoolean("AvalonDivineDisintegrationActive")) {
            LivingEntity target = nearestTarget(level, entity, 160.0);
            if (target != null) {
               if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                  Vec3 position = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
                  int chargeTicks = (Integer)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationChargeTicks.get();
                  tag.putBoolean("AvalonDivineDisintegrationActive", true);
                  tag.putInt("AvalonDivineDisintegrationTicks", chargeTicks);
                  tag.putDouble("AvalonDivineDisintegrationX", position.x);
                  tag.putDouble("AvalonDivineDisintegrationY", position.y);
                  tag.putDouble("AvalonDivineDisintegrationZ", position.z);
                  tag.putInt("AvalonDivineDisintegrationMode", mode);
                  tag.putInt("AvalonDivineDisintegrationTarget", target.getId());
                  instance.markDirty();
                  lockTarget(target, position);
                  AvalonDivineDisintegrationVfxPayload.send(level, entity, target, position.add(0.0, 220.0, 0.0), position, visualRadius(), chargeTicks, 0);
                  level.playSound(null, target.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 5.0F, 0.22F);
                  level.playSound(null, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.0F, 0.72F);
                  entity.swing(InteractionHand.MAIN_HAND, true);
               }
            }
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("AvalonDivineDisintegrationActive")) {
            int ticks = tag.getInt("AvalonDivineDisintegrationTicks") - 1;
            tag.putInt("AvalonDivineDisintegrationTicks", ticks);
            Vec3 position = new Vec3(
               tag.getDouble("AvalonDivineDisintegrationX"), tag.getDouble("AvalonDivineDisintegrationY"), tag.getDouble("AvalonDivineDisintegrationZ")
            );
            LivingEntity target = resolveTarget(level, tag.getInt("AvalonDivineDisintegrationTarget"));
            if (target != null && target.isAlive()) {
               lockTarget(target, position);
            }

            if (ticks > 0) {
               if (ticks % 20 == 0) {
                  float progress = 1.0F - ticks / ((Integer)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationChargeTicks.get()).floatValue();
                  level.playSound(null, position.x, position.y, position.z, SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 4.8F, 0.32F + progress * 1.55F);
                  level.playSound(
                     null, position.x, position.y, position.z, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.PLAYERS, 2.8F, 0.45F + progress * 0.55F
                  );
                  AvalonDivineDisintegrationVfxPayload.send(level, entity, target, position.add(0.0, 220.0, 0.0), position, visualRadius(), 24, 2);
               }

               if (ticks % 20 == 0) {
                  instance.markDirty();
               }
            } else {
               int mode = tag.getInt("AvalonDivineDisintegrationMode");
               float damage = ((Double)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationDamage.get()).floatValue();
               double radius = (Double)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationRadius.get();
               RoyalSkillSupport.damageArea(entity, instance, mode, position, radius, damage * 0.6F, TensuraDamageTypes.HOLY_DAMAGE);
               RoyalSkillSupport.damageArea(entity, instance, mode, position, radius, damage * 0.4F, TensuraDamageTypes.DISINTEGRATION);
               AvalonDivineDisintegrationVfxPayload.send(level, entity, target, position.add(0.0, 220.0, 0.0), position, visualRadius(), 110, 1);
               level.playSound(null, position.x, position.y, position.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 12.0F, 0.3F);
               level.playSound(null, position.x, position.y, position.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 11.0F, 0.5F);
               level.playSound(null, position.x, position.y, position.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 9.0F, 0.42F);
               clear(tag);
               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationCooldown.get(), mode);
               instance.addMasteryPoint(entity);
               instance.markDirty();
            }
         }
      }
   }

   private static LivingEntity nearestTarget(ServerLevel level, LivingEntity caster, double radius) {
      AABB area = caster.getBoundingBox().inflate(radius);
      return level.getEntitiesOfClass(
            LivingEntity.class,
            area,
            target -> target != caster
               && (target instanceof Mob || target instanceof Player)
               && target.isAlive()
               && !target.isSpectator()
               && !RoyalSkillSupport.isAlly(caster, target)
               && !(target instanceof Player player && player.getAbilities().invulnerable)
         )
         .stream()
         .min(Comparator.comparingDouble(target -> caster.distanceToSqr(target)))
         .orElse(null);
   }

   private static LivingEntity resolveTarget(ServerLevel level, int id) {
      return level.getEntity(id) instanceof LivingEntity living ? living : null;
   }

   private static void lockTarget(LivingEntity target, Vec3 center) {
      double y = center.y - target.getBbHeight() * 0.5;
      target.setDeltaMovement(Vec3.ZERO);
      target.fallDistance = 0.0F;
      if (target instanceof Mob mob) {
         mob.getNavigation().stop();
      }

      target.teleportTo(center.x, y, center.z);
      target.setDeltaMovement(Vec3.ZERO);
   }

   private static void clear(CompoundTag tag) {
      tag.remove("AvalonDivineDisintegrationActive");
      tag.remove("AvalonDivineDisintegrationTicks");
      tag.remove("AvalonDivineDisintegrationX");
      tag.remove("AvalonDivineDisintegrationY");
      tag.remove("AvalonDivineDisintegrationZ");
      tag.remove("AvalonDivineDisintegrationMode");
      tag.remove("AvalonDivineDisintegrationTarget");
   }

   private static float visualRadius() {
      return ((Double)MoreSkillsConfigs.GRAND.avalon.divineDisintegrationRadius.get()).floatValue() * 2.0F;
   }
}
