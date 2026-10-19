package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MichaelLataibBarrageVfxPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class MichaelLataibUnleashing implements MichaelAttack {
   private static final Map<UUID, MichaelLataibUnleashing.State> STATES = new ConcurrentHashMap<>();

   @Override
   public String modeId() {
      return "michael_angel_of_locks.lataib_unleashing";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibBarrageCost.get()).intValue();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            if (entity.isShiftKeyDown()) {
               openGate(entity);
               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibGateCooldownTicks.get(), mode);
            } else {
               Entity selected = ObjectSelectionHelper.getTargetingEntity(
                  entity, (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibTargetRange.get(), false, true
               );
               if (selected instanceof LivingEntity target && MichaelActions.validEnemy(entity, target)) {
                  start(entity, target, instance.isMastered(entity));
                  entity.swing(InteractionHand.MAIN_HAND, true);
                  entity.level()
                     .playSound(
                        null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.PLAYERS, 1.4F, 0.55F
                     );
                  instance.addMasteryPoint(entity);
                  instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibBarrageCooldownTicks.get(), mode);
               } else {
                  MichaelActions.fail(entity, "No target for spatial holes.");
                  instance.setCoolDown(20, mode);
               }
            }
         }
      }
   }

   public static boolean hasActive(LivingEntity owner) {
      return STATES.containsKey(owner.getUUID());
   }

   private static void start(LivingEntity owner, LivingEntity target, boolean mastered) {
      long now = owner.level().getGameTime();
      int duration = (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibBarrageDurationTicks.get();
      STATES.put(
         owner.getUUID(), new MichaelLataibUnleashing.State(owner.getUUID(), target.getUUID(), owner.level().dimension(), now + duration, now - 20L, mastered)
      );
      MichaelLataibBarrageVfxPayload.send(owner, target, duration + 80);
   }

   public static void tick(ServerLevel level) {
      Iterator<MichaelLataibUnleashing.State> iterator = STATES.values().iterator();

      while (iterator.hasNext()) {
         MichaelLataibUnleashing.State state = iterator.next();
         if (state.dimension.equals(level.dimension())) {
            long now = level.getGameTime();
            if (now > state.endTick) {
               iterator.remove();
            } else {
               LivingEntity owner = entity(level, state.owner);
               LivingEntity target = entity(level, state.target);
               if (owner != null && target != null && MichaelActions.validEnemy(owner, target)) {
                  if (now - state.lastHit >= 20L) {
                     state.lastHit = now;
                     Vec3 source = target.position()
                        .add(
                           (level.random.nextDouble() - 0.5) * 7.0,
                           target.getBbHeight() * 0.55 + level.random.nextDouble() * 3.0,
                           (level.random.nextDouble() - 0.5) * 7.0
                        );
                     target.hurt(
                        MichaelActions.damage(owner, TensuraDamageTypes.GRAVITY_PRESS),
                        ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.lataibBarrageDamage.get()).floatValue()
                     );
                     Vec3 push = target.position().subtract(source);
                     if (push.lengthSqr() > 0.001) {
                        MichaelActions.forceMove(target, owner, null, push.normalize().scale(state.mastered ? 1.15 : 0.85));
                     }

                     if (now % 40L == 0L) {
                        MichaelLataibBarrageVfxPayload.send(owner, target, 80);
                     }

                     if (now % 20L == 0L) {
                        level.playSound(
                           null, target.getX(), target.getY(), target.getZ(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.75F, 0.58F
                        );
                     }
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void openGate(LivingEntity entity) {
      Vec3 look = entity.getLookAngle().normalize();
      Vec3 target = entity.position().add(look.scale(8.0));
      entity.teleportTo(target.x, target.y, target.z);
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.55F);
   }

   private static LivingEntity entity(ServerLevel level, UUID uuid) {
      return level.getEntity(uuid) instanceof LivingEntity living ? living : null;
   }

   private static final class State {
      private final UUID owner;
      private final UUID target;
      private final ResourceKey<Level> dimension;
      private final long endTick;
      private long lastHit;
      private final boolean mastered;

      private State(UUID owner, UUID target, ResourceKey<Level> dimension, long endTick, long lastHit, boolean mastered) {
         this.owner = owner;
         this.target = target;
         this.dimension = dimension;
         this.endTick = endTick;
         this.lastHit = lastHit;
         this.mastered = mastered;
      }
   }
}
