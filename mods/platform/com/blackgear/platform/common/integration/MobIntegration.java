package com.blackgear.platform.common.integration;

import com.blackgear.platform.common.events.EntityEvents;
import com.blackgear.platform.common.integration.neoforge.MobIntegrationImpl;
import com.blackgear.platform.core.util.event.CancellableResult;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class MobIntegration {
   @ExpectPlatform
   @Transformed
   public static void registerIntegrations(Consumer<MobIntegration.Event> listener) {
      MobIntegrationImpl.registerIntegrations(listener);
   }

   public interface Event {
      void registerMobInteraction(MobInteraction var1);

      void registerAttributes(Supplier<? extends EntityType<? extends LivingEntity>> var1, Supplier<Builder> var2);

      <T extends Mob> void registerPlacement(Supplier<EntityType<T>> var1, SpawnPlacementType var2, Types var3, SpawnPredicate<T> var4);

      default void registerGoal(Predicate<Mob> predicate, int priority, Function<Mob, Goal> factory) {
         EntityEvents.ON_SPAWN.register((entity, level) -> {
            if (entity instanceof Mob mob && predicate.test(mob)) {
               mob.goalSelector.addGoal(priority, factory.apply(mob));
            }

            return CancellableResult.PASS;
         });
      }

      default void registerGoal(EntityType<?> entity, int priority, Function<Mob, Goal> factory) {
         this.registerGoal(mob -> mob.getType() == entity, priority, factory);
      }

      default void registerTarget(Predicate<Mob> predicate, int priority, Function<Mob, Goal> factory) {
         EntityEvents.ON_SPAWN.register((entity, level) -> {
            if (entity instanceof Mob mob && predicate.test(mob)) {
               mob.targetSelector.addGoal(priority, factory.apply(mob));
            }

            return CancellableResult.PASS;
         });
      }

      default void registerTarget(EntityType<? extends Entity> entity, int priority, Function<Mob, Goal> factory) {
         this.registerTarget(mob -> mob.getType() == entity, priority, factory);
      }
   }
}
