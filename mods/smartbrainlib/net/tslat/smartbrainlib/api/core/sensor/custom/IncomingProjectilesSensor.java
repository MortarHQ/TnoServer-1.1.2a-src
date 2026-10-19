package net.tslat.smartbrainlib.api.core.sensor.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.projectile.Projectile;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

public class IncomingProjectilesSensor<E extends LivingEntity> extends PredicateSensor<Projectile, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{SBLMemoryTypes.INCOMING_PROJECTILES.get()});

   public IncomingProjectilesSensor() {
      this.setScanRate(entity -> 3);
      this.setPredicate(
         (projectile, entity) -> !projectile.onGround() && !projectile.horizontalCollision && !projectile.verticalCollision
            ? entity.getBoundingBox().clip(projectile.position(), projectile.position().add(projectile.getDeltaMovement().multiply(3.0, 3.0, 3.0))).isPresent()
            : false
      );
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.INCOMING_PROJECTILES.get();
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      List<Projectile> projectiles = EntityRetrievalUtil.getEntities(entity, 7.0, Projectile.class, projectile -> this.predicate().test(projectile, entity));
      if (!projectiles.isEmpty()) {
         projectiles.sort(Comparator.comparingDouble(entity::distanceToSqr));
         BrainUtils.setMemory(entity, SBLMemoryTypes.INCOMING_PROJECTILES.get(), projectiles);
      } else {
         BrainUtils.clearMemory(entity, SBLMemoryTypes.INCOMING_PROJECTILES.get());
      }
   }
}
