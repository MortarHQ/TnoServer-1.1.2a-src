package fuzs.illagerinvasion.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.mixin.accessor.VillagerHostilesSensorAccessor;
import fuzs.illagerinvasion.world.entity.monster.Alchemist;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import fuzs.illagerinvasion.world.entity.monster.Basher;
import fuzs.illagerinvasion.world.entity.monster.Firecaller;
import fuzs.illagerinvasion.world.entity.monster.Inquisitor;
import fuzs.illagerinvasion.world.entity.monster.Invoker;
import fuzs.illagerinvasion.world.entity.monster.Marauder;
import fuzs.illagerinvasion.world.entity.monster.Necromancer;
import fuzs.illagerinvasion.world.entity.monster.Provoker;
import fuzs.illagerinvasion.world.entity.monster.Sorcerer;
import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.AbstractVillager;

public class VillagerGoalHandler {
   private static final List<VillagerGoalHandler.AvoidVillagerEnemy<?>> VILLAGER_ENEMIES = ImmutableList.builder()
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Alchemist.class, ModEntityTypes.ALCHEMIST_ENTITY_TYPE::value, 12.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Archivist.class, ModEntityTypes.ARCHIVIST_ENTITY_TYPE::value, 12.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Basher.class, ModEntityTypes.BASHER_ENTITY_TYPE::value, 8.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Firecaller.class, ModEntityTypes.FIRECALLER_ENTITY_TYPE::value, 12.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Inquisitor.class, ModEntityTypes.INQUISITOR_ENTITY_TYPE::value, 15.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Invoker.class, ModEntityTypes.INVOKER_ENTITY_TYPE::value, 15.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Marauder.class, ModEntityTypes.MARAUDER_ENTITY_TYPE::value, 12.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Necromancer.class, ModEntityTypes.NECROMANCER_ENTITY_TYPE::value, 15.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Provoker.class, ModEntityTypes.PROVOKER_ENTITY_TYPE::value, 12.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Sorcerer.class, ModEntityTypes.SORCERER_ENTITY_TYPE::value, 12.0F))
      .add(new VillagerGoalHandler.AvoidVillagerEnemy(Surrendered.class, ModEntityTypes.SURRENDERED_ENTITY_TYPE::value, 8.0F))
      .build();

   public static EventResult onEntityLoad(Entity entity, ServerLevel level) {
      if (entity.getType() == EntityType.WANDERING_TRADER) {
         for (VillagerGoalHandler.AvoidVillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
            villagerEnemy.addGoal((AbstractVillager)entity);
         }
      }

      return EventResult.PASS;
   }

   public static void init() {
      Map<EntityType<?>, Float> map = new IdentityHashMap<>(VillagerHostilesSensorAccessor.illagerinvasion$getAcceptableDistanceFromHostiles());

      for (VillagerGoalHandler.AvoidVillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
         villagerEnemy.addAcceptableDistance(map::put);
      }

      VillagerHostilesSensorAccessor.illagerinvasion$setAcceptableDistanceFromHostiles(ImmutableMap.copyOf(map));
   }

   private record AvoidVillagerEnemy<T extends LivingEntity>(Class<T> clazz, Supplier<EntityType<T>> type, float acceptableDistance) {
      public void addGoal(AbstractVillager abstractVillager) {
         abstractVillager.goalSelector.addGoal(1, this.makeGoal(abstractVillager));
      }

      private Goal makeGoal(AbstractVillager abstractVillager) {
         return new AvoidEntityGoal(abstractVillager, this.clazz, this.acceptableDistance, 0.5, 0.5);
      }

      public void addAcceptableDistance(BiConsumer<EntityType<?>, Float> consumer) {
         consumer.accept(this.type.get(), this.acceptableDistance);
      }
   }
}
