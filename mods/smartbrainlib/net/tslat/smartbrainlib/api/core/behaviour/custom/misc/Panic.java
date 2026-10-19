package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class Panic<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(3)
      .hasMemory(MemoryModuleType.HURT_BY)
      .usesMemories(MemoryModuleType.WALK_TARGET, MemoryModuleType.IS_PANICKING);
   protected BiPredicate<E, DamageSource> shouldPanicPredicate = (entity, damageSource) -> entity.isFreezing()
      || entity.isOnFire()
      || damageSource.getEntity() instanceof LivingEntity;
   protected Object2FloatFunction<E> speedMod = entity -> 1.25F;
   protected SquareRadius radius = new SquareRadius(5.0, 4.0);
   protected BiFunction<E, DamageSource, Integer> panicFor = (entity, damageSource) -> entity.getRandom().nextInt(100, 120);
   protected Vec3 targetPos = null;
   protected int panicEndTime = 0;

   public Panic() {
      this.noTimeout();
   }

   public Panic<E> panicIf(BiPredicate<E, DamageSource> predicate) {
      this.shouldPanicPredicate = predicate;
      return this;
   }

   public Panic<E> panicFor(BiFunction<E, DamageSource, Integer> function) {
      this.panicFor = function;
      return this;
   }

   public Panic<E> speedMod(Object2FloatFunction<E> speedModifier) {
      this.speedMod = speedModifier;
      return this;
   }

   public Panic<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public Panic<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      if (!this.shouldPanicPredicate.test(entity, BrainUtils.getMemory(entity, MemoryModuleType.HURT_BY))) {
         return false;
      }

      this.setPanicTarget(entity);
      return this.targetPos != null;
   }

   protected boolean shouldKeepRunning(E entity) {
      return entity.tickCount < this.panicEndTime;
   }

   protected void start(E entity) {
      BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(this.targetPos, (Float)this.speedMod.apply(entity), 0));
      BrainUtils.setMemory(entity, MemoryModuleType.IS_PANICKING, true);
      this.panicEndTime = entity.tickCount + this.panicFor.apply(entity, BrainUtils.getMemory(entity, MemoryModuleType.HURT_BY));
   }

   protected void tick(E entity) {
      if (entity.getNavigation().isDone()) {
         this.targetPos = null;
         this.setPanicTarget(entity);
         if (this.targetPos != null) {
            BrainUtils.clearMemory(entity, MemoryModuleType.PATH);
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(this.targetPos, (Float)this.speedMod.apply(entity), 1));
         }
      }
   }

   protected void stop(E entity) {
      this.targetPos = null;
      this.panicEndTime = 0;
      BrainUtils.setMemory(entity, MemoryModuleType.IS_PANICKING, false);
   }

   @Nullable
   protected Vec3 findNearbyWater(E entity) {
      BlockPos pos = entity.blockPosition();
      Level level = entity.level();
      return !level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()
         ? null
         : BlockPos.findClosestMatch(
               entity.blockPosition(), (int)this.radius.xzRadius(), (int)this.radius.yRadius(), checkPos -> level.getFluidState(checkPos).is(FluidTags.WATER)
            )
            .<Vec3>map(Vec3::atBottomCenterOf)
            .orElse(null);
   }

   protected void setPanicTarget(E entity) {
      if (entity.isOnFire()) {
         this.targetPos = this.findNearbyWater(entity);
      }

      if (this.targetPos == null) {
         DamageSource lastDamage = BrainUtils.getMemory(entity, MemoryModuleType.HURT_BY);
         if (lastDamage != null && lastDamage.getEntity() instanceof LivingEntity attacker) {
            this.targetPos = DefaultRandomPos.getPosAway(entity, (int)this.radius.xzRadius(), (int)this.radius.yRadius(), attacker.position());
         }

         if (this.targetPos == null) {
            this.targetPos = DefaultRandomPos.getPos(entity, (int)this.radius.xzRadius(), (int)this.radius.yRadius());
         }
      }
   }
}
