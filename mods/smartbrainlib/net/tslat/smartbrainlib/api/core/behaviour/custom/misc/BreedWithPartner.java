package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.animal.Animal;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class BreedWithPartner<E extends Animal> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(4)
      .hasMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
      .noMemory(MemoryModuleType.BREED_TARGET)
      .usesMemories(MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET);
   protected BiFunction<E, Animal, Float> speedMod = (entity, partner) -> 1.0F;
   protected ToIntBiFunction<E, Animal> closeEnoughDist = (entity, partner) -> 2;
   protected BiFunction<E, Animal, Integer> breedTime = (entity, partner) -> entity.getRandom().nextInt(60, 110);
   protected BiPredicate<E, Animal> partnerPredicate = (entity, partner) -> entity.getType() == partner.getType() && entity.canMate(partner);
   protected int childBreedTick = -1;
   protected Animal partner = null;

   public BreedWithPartner() {
      this.noTimeout();
   }

   public BreedWithPartner<E> speedMod(BiFunction<E, Animal, Float> speedModifier) {
      this.speedMod = speedModifier;
      return this;
   }

   public BreedWithPartner<E> closeEnoughDist(ToIntBiFunction<E, Animal> closeEnoughDist) {
      this.closeEnoughDist = closeEnoughDist;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      if (!entity.isInLove()) {
         return false;
      }

      this.partner = this.findPartner(entity);
      return this.partner != null;
   }

   protected boolean shouldKeepRunning(E entity) {
      return this.partner != null
         && this.partner.isAlive()
         && entity.tickCount <= this.childBreedTick
         && BehaviorUtils.entityIsVisible(entity.getBrain(), this.partner)
         && this.partnerPredicate.test(entity, this.partner);
   }

   protected void start(E entity) {
      this.childBreedTick = entity.tickCount + this.breedTime.apply(entity, this.partner);
      BrainUtils.setMemory(entity, MemoryModuleType.BREED_TARGET, this.partner);
      BrainUtils.setMemory(this.partner, MemoryModuleType.BREED_TARGET, entity);
      BehaviorUtils.lockGazeAndWalkToEachOther(
         entity, this.partner, this.speedMod.apply(entity, this.partner), this.closeEnoughDist.applyAsInt(entity, this.partner)
      );
   }

   protected void tick(E entity) {
      BehaviorUtils.lockGazeAndWalkToEachOther(
         entity, this.partner, this.speedMod.apply(entity, this.partner), this.closeEnoughDist.applyAsInt(entity, this.partner)
      );
      if (entity.closerThan(this.partner, 3.0) && entity.tickCount == this.childBreedTick) {
         entity.spawnChildFromBreeding((ServerLevel)entity.level(), this.partner);
         BrainUtils.clearMemory(entity, MemoryModuleType.BREED_TARGET);
         BrainUtils.clearMemory(this.partner, MemoryModuleType.BREED_TARGET);
      }
   }

   protected void stop(E entity) {
      BrainUtils.clearMemories(entity, MemoryModuleType.BREED_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET);
      if (this.partner != null) {
         BrainUtils.clearMemories(this.partner, MemoryModuleType.BREED_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET);
      }

      this.childBreedTick = -1;
      this.partner = null;
   }

   @Nullable
   protected Animal findPartner(E entity) {
      return BrainUtils.<NearestVisibleLivingEntities>getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .findClosest(entity2 -> entity2 instanceof Animal partner && this.partnerPredicate.test(entity, partner))
         .map(Animal.class::cast)
         .orElse(null);
   }
}
