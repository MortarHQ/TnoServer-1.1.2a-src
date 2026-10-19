package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonSovereignSanctuaryVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

final class AvalonSovereignSanctuaryHandler implements AvalonSovereignModeHandler {
   private static final String ACTIVE = "AvalonSovereignSanctuaryActive";
   private static final String UNTIL = "AvalonSovereignSanctuaryUntil";
   private static final String X = "AvalonSovereignSanctuaryX";
   private static final String Y = "AvalonSovereignSanctuaryY";
   private static final String Z = "AvalonSovereignSanctuaryZ";

   @Override
   public String name() {
      return "Sanctuary";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.sanctuaryCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int topMode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, topMode)) {
            Vec3 center = entity.position();
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("AvalonSovereignSanctuaryActive", true);
            tag.putLong(
               "AvalonSovereignSanctuaryUntil", level.getGameTime() + ((Integer)MoreSkillsConfigs.GRAND.avalon.sanctuaryDurationTicks.get()).intValue()
            );
            tag.putDouble("AvalonSovereignSanctuaryX", center.x);
            tag.putDouble("AvalonSovereignSanctuaryY", center.y);
            tag.putDouble("AvalonSovereignSanctuaryZ", center.z);
            instance.markDirty();
            AvalonSovereignSanctuaryVfxPayload.send(
               level,
               entity,
               null,
               center,
               center,
               ((Double)MoreSkillsConfigs.GRAND.avalon.sanctuaryRadius.get()).floatValue(),
               (Integer)MoreSkillsConfigs.GRAND.avalon.sanctuaryDurationTicks.get(),
               0
            );
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.0F, 1.55F);
            instance.setCoolDown(500, topMode);
            instance.addMasteryPoint(entity);
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("AvalonSovereignSanctuaryActive")) {
            if (level.getGameTime() > tag.getLong("AvalonSovereignSanctuaryUntil")) {
               tag.remove("AvalonSovereignSanctuaryActive");
               instance.markDirty();
            } else if (entity.tickCount % 10 == 0) {
               Vec3 center = new Vec3(
                  tag.getDouble("AvalonSovereignSanctuaryX"), tag.getDouble("AvalonSovereignSanctuaryY"), tag.getDouble("AvalonSovereignSanctuaryZ")
               );
               double radius = (Double)MoreSkillsConfigs.GRAND.avalon.sanctuaryRadius.get();

               for (LivingEntity ally : level.getEntitiesOfClass(
                  LivingEntity.class, new AABB(center, center).inflate(radius), living -> RoyalSkillSupport.isAlly(entity, living)
               )) {
                  if (!(ally.position().distanceToSqr(center) > radius * radius)) {
                     ally.heal(ally.getMaxHealth() * 0.05F);
                     RoyalSkillSupport.restoreEnergyPercent(ally, 0.025, 0.025);
                     RoyalSkillSupport.removeHarmfulEffects(ally);
                  }
               }

               float damage = ((Double)MoreSkillsConfigs.GRAND.avalon.sanctuaryDamage.get()).floatValue();
               RoyalSkillSupport.damageArea(entity, instance, 3, center, radius, damage, TensuraDamageTypes.HOLY_DAMAGE);
               AvalonSovereignSanctuaryVfxPayload.send(level, entity, null, center, center, (float)radius, 24, 1);
            }
         }
      }
   }
}
