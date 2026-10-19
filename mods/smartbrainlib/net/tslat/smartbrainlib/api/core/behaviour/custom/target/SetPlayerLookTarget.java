package net.tslat.smartbrainlib.api.core.behaviour.custom.target;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SetPlayerLookTarget<E extends LivingEntity> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2)
      .hasMemory(MemoryModuleType.NEAREST_PLAYERS)
      .noMemory(MemoryModuleType.LOOK_TARGET);
   protected BiPredicate<E, Player> lookPredicate = this::defaultPredicate;
   protected Predicate<Player> predicate = pl -> true;
   protected Player target = null;

   @Deprecated(forRemoval = true)
   public SetPlayerLookTarget<E> predicate(Predicate<Player> predicate) {
      return this.lookPredicate((entity, player) -> predicate.test(player));
   }

   public SetPlayerLookTarget<E> lookPredicate(BiPredicate<E, Player> predicate) {
      this.lookPredicate = predicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      for (Player player : (List)BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_PLAYERS)) {
         if (this.predicate.test(player) && this.lookPredicate.test(entity, player)) {
            this.target = player;
            break;
         }
      }

      return this.target != null;
   }

   protected boolean defaultPredicate(E entity, Player player) {
      if (entity.hasPassenger(player)) {
         return false;
      }

      if (entity instanceof Mob mob) {
         if (!mob.getSensing().hasLineOfSight(player)) {
            return false;
         }
      } else if (!entity.hasLineOfSight(player)) {
         return false;
      }

      double visibleDistance = Math.max(player.getVisibilityPercent(entity) * 16.0, 2.0);
      return entity.distanceToSqr(player) <= visibleDistance * visibleDistance;
   }

   @Override
   protected void start(E entity) {
      BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(this.target, true));
   }

   @Override
   protected void stop(E entity) {
      this.target = null;
   }
}
