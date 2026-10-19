package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

public class FloatToSurfaceOfFluid<E extends Mob> extends ExtendedBehaviour<E> {
   protected float riseChance = 0.8F;
   protected boolean canFloatPrevious;

   public FloatToSurfaceOfFluid<E> riseChance(float chance) {
      this.riseChance = chance;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return entity.isInWater() && entity.getFluidHeight(FluidTags.WATER) > entity.getFluidJumpThreshold() || entity.isInLava();
   }

   protected boolean shouldKeepRunning(E entity) {
      return this.checkExtraStartConditions((ServerLevel)entity.level(), entity);
   }

   protected void start(E entity) {
      super.start(entity);
      this.canFloatPrevious = entity.getNavigation().canFloat();
      entity.getNavigation().setCanFloat(true);
   }

   protected void tick(E entity) {
      if (entity.getRandom().nextFloat() < this.riseChance) {
         entity.getJumpControl().jump();
      }
   }

   protected void stop(E entity) {
      super.stop(entity);
      entity.getNavigation().setCanFloat(this.canFloatPrevious);
   }
}
