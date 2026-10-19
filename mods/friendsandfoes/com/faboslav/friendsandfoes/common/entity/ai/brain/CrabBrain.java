package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabBreedTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabDanceTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabGoToHomePositionTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabLayEggTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabLocateBurrowSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabTravelToBurrowSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab.CrabWaveTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;

public final class CrabBrain {
   public static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(
      MemoryModuleType.TEMPTING_PLAYER,
      MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
      MemoryModuleType.IS_TEMPTED,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.PATH,
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.BREED_TARGET,
      MemoryModuleType.IS_PANICKING,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      MemoryModuleType.NEAREST_VISIBLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get()
   );
   public static final List<SensorType<? extends Sensor<? super CrabEntity>>> SENSORS = List.of(
      SensorType.NEAREST_LIVING_ENTITIES,
      SensorType.NEAREST_PLAYERS,
      SensorType.NEAREST_ADULT,
      (SensorType<? extends Sensor<? super CrabEntity>>)FriendsAndFoesSensorTypes.CRAB_TEMPTATIONS.get()
   );
   private static final UniformInt WAVE_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(20, 40);

   public static Brain<?> create(Dynamic<?> dynamic) {
      Provider<CrabEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
      Brain<CrabEntity> brain = profile.makeBrain(dynamic);
      addCoreActivities(brain);
      addIdleActivities(brain);
      addLayEggActivities(brain);
      addDanceActivities(brain);
      addWaveActivities(brain);
      brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
      brain.setDefaultActivity(Activity.IDLE);
      brain.useDefaultActivity();
      return brain;
   }

   private static void addCoreActivities(Brain<CrabEntity> brain) {
      brain.addActivity(
         Activity.CORE,
         0,
         ImmutableList.of(
            new AnimalPanic(2.0F),
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink(),
            new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
            new CountDownCooldownTicks((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get())
         )
      );
   }

   private static void addLayEggActivities(Brain<CrabEntity> brain) {
      brain.addActivityWithConditions(
         (Activity)FriendsAndFoesActivities.CRAB_LAY_EGG.get(),
         ImmutableList.of(
            Pair.of(0, new CrabGoToHomePositionTask()),
            Pair.of(1, new CrabLocateBurrowSpotTask()),
            Pair.of(2, new CrabTravelToBurrowSpotTask()),
            Pair.of(3, new CrabLayEggTask())
         ),
         ImmutableSet.of(Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT))
      );
   }

   private static void addDanceActivities(Brain<CrabEntity> brain) {
      brain.addActivityWithConditions(
         (Activity)FriendsAndFoesActivities.CRAB_DANCE.get(),
         ImmutableList.of(Pair.of(0, new CrabDanceTask())),
         ImmutableSet.of(
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_ABSENT)
         )
      );
   }

   private static void addWaveActivities(Brain<CrabEntity> brain) {
      brain.addActivityWithConditions(
         (Activity)FriendsAndFoesActivities.CRAB_WAVE.get(),
         ImmutableList.of(Pair.of(0, new CrabWaveTask())),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT),
            Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryStatus.VALUE_ABSENT),
            new Pair[]{Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_ABSENT)}
         )
      );
   }

   private static void addIdleActivities(Brain<CrabEntity> brain) {
      brain.addActivityWithConditions(
         Activity.IDLE,
         ImmutableList.of(
            Pair.of(0, new FollowTemptation(crab -> 1.25F)),
            Pair.of(1, new CrabBreedTask((EntityType<? extends Animal>)FriendsAndFoesEntityTypes.CRAB.get())),
            Pair.of(2, BabyFollowAdult.create(UniformInt.of(5, 16), 1.25F)),
            Pair.of(
               3,
               new RunOne(
                  ImmutableList.of(
                     Pair.of(RandomStroll.stroll(1.0F), 2), Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2), Pair.of(new DoNothing(30, 60), 1)
                  )
               )
            )
         ),
         ImmutableSet.of(
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_ABSENT)
         )
      );
   }

   public static void updateActivities(CrabEntity crab) {
      crab.getBrain()
         .setActiveActivityToFirstValid(
            ImmutableList.of(
               (Activity)FriendsAndFoesActivities.CRAB_LAY_EGG.get(),
               (Activity)FriendsAndFoesActivities.CRAB_DANCE.get(),
               (Activity)FriendsAndFoesActivities.CRAB_WAVE.get(),
               Activity.IDLE
            )
         );
   }

   public static void updateMemories(CrabEntity crab) {
      if (crab.hasEgg()) {
         crab.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), true);
      } else {
         crab.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get());
      }

      if (crab.isDancing() && !crab.onClimbable()) {
         crab.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(), true);
      } else {
         crab.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get());
      }
   }

   public static void setWaveCooldown(CrabEntity crab) {
      crab.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), WAVE_COOLDOWN_PROVIDER.sample(crab.getRandom()));
   }

   public static Predicate<ItemStack> getTemptations() {
      return itemStack -> itemStack.is(FriendsAndFoesTags.CRAB_TEMPT_ITEMS);
   }
}
