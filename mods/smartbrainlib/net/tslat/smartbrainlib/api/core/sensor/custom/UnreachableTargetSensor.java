package net.tslat.smartbrainlib.api.core.sensor.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;

public class UnreachableTargetSensor<E extends LivingEntity> extends ExtendedSensor<E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
      new MemoryModuleType[]{MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, SBLMemoryTypes.TARGET_UNREACHABLE.get()}
   );
   protected long lastUnpathableTime = 0L;

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.UNREACHABLE_TARGET.get();
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      Brain<?> brain = entity.getBrain();
      LivingEntity target = BrainUtils.getTargetOfEntity(entity);
      if (target == null) {
         this.resetState(brain);
      } else {
         Long unpathableTime = BrainUtils.getMemory(brain, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         if (unpathableTime == null) {
            this.resetState(brain);
         } else {
            if (this.lastUnpathableTime == 0L) {
               this.lastUnpathableTime = unpathableTime;
            } else if (this.lastUnpathableTime == unpathableTime) {
               BrainUtils.clearMemory(brain, SBLMemoryTypes.TARGET_UNREACHABLE.get());
            } else if (this.lastUnpathableTime < unpathableTime) {
               this.lastUnpathableTime = unpathableTime;
               BrainUtils.setMemory(brain, SBLMemoryTypes.TARGET_UNREACHABLE.get(), target.getY() > entity.getEyeY());
            }
         }
      }
   }

   private void resetState(Brain<?> brain) {
      if (this.lastUnpathableTime > 0L) {
         BrainUtils.clearMemory(brain, SBLMemoryTypes.TARGET_UNREACHABLE.get());
      }

      this.lastUnpathableTime = 0L;
   }
}
