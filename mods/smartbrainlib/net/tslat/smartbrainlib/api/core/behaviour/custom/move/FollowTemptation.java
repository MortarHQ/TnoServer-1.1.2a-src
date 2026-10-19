package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class FollowTemptation<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(7)
      .hasMemory(MemoryModuleType.TEMPTING_PLAYER)
      .noMemory(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
      .usesMemories(
         MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.IS_TEMPTED, MemoryModuleType.IS_PANICKING, MemoryModuleType.BREED_TARGET
      );
   protected BiFunction<E, Player, Float> speedMod = (entity, temptingPlayer) -> 1.0F;
   protected BiPredicate<E, Player> shouldFollow = (entity, temptingPlayer) -> !(entity instanceof Animal animal && animal.getAge() != 0)
      && !BrainUtils.memoryOrDefault(entity, MemoryModuleType.IS_PANICKING, () -> false);
   protected BiFunction<E, Player, Float> closeEnoughWhen = (owner, temptingPlayer) -> 2.5F;
   protected Object2IntFunction<E> temptationCooldown = entity -> 100;

   public FollowTemptation() {
      this.noTimeout();
   }

   public FollowTemptation<E> speedMod(BiFunction<E, Player, Float> speedModifier) {
      this.speedMod = speedModifier;
      return this;
   }

   public FollowTemptation<E> followIf(BiPredicate<E, Player> predicate) {
      this.shouldFollow = predicate;
      return this;
   }

   public FollowTemptation<E> closeEnoughDist(BiFunction<E, Player, Float> closeEnoughMod) {
      this.closeEnoughWhen = closeEnoughMod;
      return this;
   }

   public FollowTemptation<E> temptationCooldown(Object2IntFunction<E> cooldownFunction) {
      this.temptationCooldown = cooldownFunction;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return this.shouldFollow.test(entity, BrainUtils.getMemory(entity, MemoryModuleType.TEMPTING_PLAYER));
   }

   protected boolean shouldKeepRunning(E entity) {
      return BrainUtils.hasMemory(entity, MemoryModuleType.TEMPTING_PLAYER)
         && !BrainUtils.hasMemory(entity, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
         && !BrainUtils.hasMemory(entity, MemoryModuleType.BREED_TARGET)
         && this.shouldFollow.test(entity, BrainUtils.getMemory(entity, MemoryModuleType.TEMPTING_PLAYER));
   }

   protected void start(E entity) {
      BrainUtils.setMemory(entity, MemoryModuleType.IS_TEMPTED, true);
   }

   protected void tick(E entity) {
      Player temptingPlayer = BrainUtils.getMemory(entity, MemoryModuleType.TEMPTING_PLAYER);
      float closeEnough = this.closeEnoughWhen.apply(entity, temptingPlayer);
      BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(temptingPlayer, true));
      if (entity.distanceToSqr(temptingPlayer) < closeEnough * closeEnough) {
         BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
      } else {
         BrainUtils.setMemory(
            entity,
            MemoryModuleType.WALK_TARGET,
            new WalkTarget(new EntityTracker(temptingPlayer, false), this.speedMod.apply(entity, temptingPlayer), (int)closeEnough)
         );
      }
   }

   protected void stop(E entity) {
      int cooldownTicks = (Integer)this.temptationCooldown.apply(entity);
      BrainUtils.setForgettableMemory(entity, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, cooldownTicks, cooldownTicks);
      BrainUtils.setMemory(entity, MemoryModuleType.IS_TEMPTED, false);
      BrainUtils.clearMemories(entity, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET);
   }
}
