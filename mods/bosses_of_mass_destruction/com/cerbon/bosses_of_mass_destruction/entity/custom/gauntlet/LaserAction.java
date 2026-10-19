package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.general.data.HistoricalData;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.EventSeries;
import com.cerbon.cerbons_api.api.general.event.IEvent;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class LaserAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;
   public static final int laserLagTicks = 8;
   private static final ResourceLocation laserDamageModifier = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "laser");

   public LaserAction(GauntletEntity entity, EventScheduler eventScheduler, Supplier<Boolean> cancelAction, ServerLevel serverLevel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.cancelAction = cancelAction;
      this.serverLevel = serverLevel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      if (target == null) {
         return 40;
      }

      HistoricalData<Vec3> laserRenderPositions = new HistoricalData(Vec3.ZERO, 8);
      SoundUtils.playSound(
         this.serverLevel, this.entity.position(), (SoundEvent)BMDSounds.GAUNTLET_LASER_CHARGE.get(), SoundSource.HOSTILE, 3.0F, 1.0F, 64.0, null
      );
      TimedEvent sendStartToClient = new TimedEvent(() -> this.entity.getEntityData().set(GauntletEntity.laserTarget, target.getId()), 25, 1, this.cancelAction);
      TimedEvent applyLaser = new TimedEvent(() -> {
         laserRenderPositions.add(target.getBoundingBox().getCenter());
         if (laserRenderPositions.size() == 8) {
            this.applyLaser(laserRenderPositions);
         }
      }, 0, 60, this.cancelAction);
      TimedEvent stop = new TimedEvent(() -> {
         laserRenderPositions.clear();
         this.entity.getEntityData().set(GauntletEntity.laserTarget, 0);
         this.entity.level().broadcastEntityEvent(this.entity, (byte)9);
      }, 0);
      this.eventScheduler.addEvent(new EventSeries(new IEvent[]{sendStartToClient, applyLaser, stop}));
      return 120;
   }

   private void applyLaser(HistoricalData<Vec3> laserRenderPositions) {
      Vec3 targetLaserPos = (Vec3)laserRenderPositions.getAll().get(0);
      Vec3 extendedLaserPos = extendLaser(this.entity, targetLaserPos);
      BlockHitResult result = this.entity
         .level()
         .clip(new ClipContext(MobUtils.eyePos(this.entity), extendedLaserPos, Block.COLLIDER, Fluid.NONE, this.entity));
      if (result.getType() == Type.BLOCK) {
         if (this.entity.tickCount % 2 == 0) {
            VanillaCopiesServer.destroyBlocks(this.entity, new AABB(result.getLocation(), result.getLocation()).inflate(0.1));
         }

         this.applyLaserToEntities(result.getLocation());
      } else {
         this.applyLaserToEntities(extendedLaserPos);
      }
   }

   private void applyLaserToEntities(Vec3 laserTargetPos) {
      for (LivingEntity hitEntity : BMDUtils.findEntitiesInLine(this.entity.level(), MobUtils.eyePos(this.entity), laserTargetPos, this.entity)
         .stream()
         .filter(LivingEntity.class::isInstance)
         .map(entity1 -> (LivingEntity)entity1)
         .toList()) {
         this.entity
            .getAttribute(Attributes.ATTACK_DAMAGE)
            .addTransientModifier(new AttributeModifier(laserDamageModifier, -0.25, Operation.ADD_MULTIPLIED_BASE));
         this.entity.doHurtTarget(hitEntity);
         this.entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(laserDamageModifier);
      }
   }

   public static Vec3 extendLaser(Entity entity, Vec3 laserTargetPos) {
      return MathUtils.unNormedDirection(MobUtils.eyePos(entity), laserTargetPos).normalize().scale(30.0).add(MobUtils.eyePos(entity));
   }
}
