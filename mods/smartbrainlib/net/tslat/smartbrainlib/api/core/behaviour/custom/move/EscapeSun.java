package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class EscapeSun<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2)
      .noMemory(MemoryModuleType.ATTACK_TARGET)
      .usesMemory(MemoryModuleType.WALK_TARGET);
   protected float speedModifier = 1.0F;
   protected Vec3 hidePos = null;

   public EscapeSun() {
      this.noTimeout();
   }

   public EscapeSun<E> speedModifier(float speedMod) {
      this.speedModifier = speedMod;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      if (!level.isDay() || !entity.isOnFire() || !level.canSeeSky(entity.blockPosition())) {
         return false;
      }

      if (!entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
         return false;
      }

      this.hidePos = this.getHidePos(entity);
      return this.hidePos != null;
   }

   protected void start(E entity) {
      BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(this.hidePos, this.speedModifier, 0));
   }

   protected boolean shouldKeepRunning(E entity) {
      if (this.hidePos == null) {
         return false;
      }

      WalkTarget walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);
      return walkTarget == null
         ? false
         : walkTarget.getTarget().currentBlockPosition().equals(BlockPos.containing(this.hidePos)) && !entity.getNavigation().isDone();
   }

   protected void stop(E entity) {
      this.hidePos = null;
   }

   @Nullable
   protected Vec3 getHidePos(E entity) {
      RandomSource randomsource = entity.getRandom();
      BlockPos entityPos = entity.blockPosition();

      for (int i = 0; i < 10; i++) {
         BlockPos hidePos = entityPos.offset(randomsource.nextInt(20) - 10, randomsource.nextInt(6) - 3, randomsource.nextInt(20) - 10);
         if (!entity.level().canSeeSky(hidePos) && entity.getWalkTargetValue(hidePos) < 0.0F) {
            return Vec3.atBottomCenterOf(hidePos);
         }
      }

      return null;
   }
}
