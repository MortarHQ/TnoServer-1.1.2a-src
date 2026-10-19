package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareBeGrumpyAtDarkSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareLocateDarkSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareLocateGlowBerriesTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareShakeGlowBerriesTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareStrollTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareTeleportToOwnerTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareTravelToDarkSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareTravelToGlowBerriesTask;
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
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;

public final class GlareBrain {
   public static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(
      MemoryModuleType.IS_PANICKING,
      MemoryModuleType.TEMPTING_PLAYER,
      MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
      MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
      MemoryModuleType.IS_PANICKING,
      MemoryModuleType.IS_TEMPTED,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.PATH,
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.ATE_RECENTLY,
      MemoryModuleType.BREED_TARGET,
      MemoryModuleType.AVOID_TARGET,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get()
   );
   public static final List<SensorType<? extends Sensor<? super GlareEntity>>> SENSORS = List.of(
      SensorType.NEAREST_LIVING_ENTITIES,
      SensorType.NEAREST_PLAYERS,
      SensorType.NEAREST_ITEMS,
      SensorType.NEAREST_ADULT,
      (SensorType<? extends Sensor<? super GlareEntity>>)FriendsAndFoesSensorTypes.GLARE_TEMPTATIONS.get(),
      (SensorType<? extends Sensor<? super GlareEntity>>)FriendsAndFoesSensorTypes.GLARE_SPECIFIC_SENSOR.get()
   );
   private static final UniformInt DARK_SPOT_LOCATING_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(20, 40);
   private static final UniformInt EAT_GLOW_BERRIES_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(30, 60);

   public static Brain<?> create(Dynamic<?> dynamic) {
      Provider<GlareEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
      Brain<GlareEntity> brain = profile.makeBrain(dynamic);
      addCoreActivities(brain);
      addAvoidActivities(brain);
      addIdleActivities(brain);
      addDarkSpotActivities(brain);
      addGlowBerriesActivities(brain);
      brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
      brain.setDefaultActivity(Activity.IDLE);
      brain.useDefaultActivity();
      return brain;
   }

   private static void addCoreActivities(Brain<GlareEntity> brain) {
      brain.addActivity(
         Activity.CORE,
         0,
         ImmutableList.of(
            new Swim(0.8F),
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink(),
            new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
            new CountDownCooldownTicks(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS),
            new CountDownCooldownTicks((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get()),
            new CountDownCooldownTicks((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get())
         )
      );
   }

   private static void addDarkSpotActivities(Brain<GlareEntity> brain) {
      brain.addActivityWithConditions(
         (Activity)FriendsAndFoesActivities.GLARE_SHOW_DARK_SPOT.get(),
         ImmutableList.of(Pair.of(0, new GlareLocateDarkSpotTask()), Pair.of(1, new GlareTravelToDarkSpotTask()), Pair.of(2, new GlareBeGrumpyAtDarkSpotTask())),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT),
            new Pair[0]
         )
      );
   }

   private static void addGlowBerriesActivities(Brain<GlareEntity> brain) {
      brain.addActivityWithConditions(
         (Activity)FriendsAndFoesActivities.GLARE_EAT_GLOW_BERRIES.get(),
         ImmutableList.of(
            Pair.of(0, GoToWantedItem.create(glare -> true, 1.25F, true, 32)),
            Pair.of(1, new GlareLocateGlowBerriesTask()),
            Pair.of(2, new GlareTravelToGlowBerriesTask()),
            Pair.of(3, new GlareShakeGlowBerriesTask())
         ),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
         )
      );
   }

   private static void addAvoidActivities(Brain<GlareEntity> brain) {
      brain.addActivityWithConditions(
         Activity.AVOID,
         ImmutableList.of(Pair.of(0, SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.25F, 16, false))),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_PRESENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT)
         )
      );
   }

   private static void addIdleActivities(Brain<GlareEntity> brain) {
      brain.addActivityWithConditions(
         Activity.IDLE,
         ImmutableList.of(
            Pair.of(0, new FollowTemptation(glare -> 1.25F)),
            Pair.of(1, new AnimalMakeLove((EntityType)FriendsAndFoesEntityTypes.GLARE.get())),
            Pair.of(2, BabyFollowAdult.create(UniformInt.of(5, 16), 1.25F)),
            Pair.of(3, new GlareTeleportToOwnerTask()),
            Pair.of(4, StayCloseToTarget.create(glare -> getOwner((GlareEntity)glare), glare -> true, 3, 8, 2.0F)),
            Pair.of(5, SetEntityLookTargetSometimes.create(3.0F, UniformInt.of(30, 60))),
            Pair.of(
               6,
               new RunOne(
                  ImmutableList.of(
                     Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 3), Pair.of(new GlareStrollTask(), 2), Pair.of(new DoNothing(30, 60), 1)
                  )
               )
            )
         ),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT)
         )
      );
   }

   private static Optional<PositionTracker> getOwner(GlareEntity glare) {
      return glare.isTame() && glare.getOwner() != null ? Optional.of(new EntityTracker(glare.getOwner(), true)) : Optional.empty();
   }

   public static void updateActivities(GlareEntity glare) {
      glare.getBrain()
         .setActiveActivityToFirstValid(
            ImmutableList.of(
               (Activity)FriendsAndFoesActivities.GLARE_SHOW_DARK_SPOT.get(),
               (Activity)FriendsAndFoesActivities.GLARE_EAT_GLOW_BERRIES.get(),
               Activity.AVOID,
               Activity.IDLE
            )
         );
   }

   public static void updateMemories(GlareEntity glare) {
      if ((!glare.isBaby() && !glare.isTame() || !GlareLocateDarkSpotTask.canLocateDarkSpot(glare))
         && glare.getBrain().checkMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)) {
         setDarkSpotLocatingCooldown(glare);
      }

      if (!glare.isOrderedToSit() && !glare.isLeashed() && !glare.isPassenger()) {
         glare.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get());
      } else {
         glare.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), true);
      }

      if (glare.isTame()) {
         glare.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), true);
      } else {
         glare.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get());
      }
   }

   public static void setDarkSpotLocatingCooldown(GlareEntity glare) {
      glare.getBrain()
         .setMemory(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(),
            DARK_SPOT_LOCATING_COOLDOWN_PROVIDER.sample(glare.getRandom())
         );
   }

   public static void setDarkSpotLocatingCooldown(GlareEntity glare, UniformInt cooldown) {
      glare.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), cooldown.sample(glare.getRandom()));
   }

   public static void setLocatingGlowBerriesCooldown(GlareEntity glare) {
      glare.getBrain()
         .setMemory(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(),
            EAT_GLOW_BERRIES_COOLDOWN_PROVIDER.sample(glare.getRandom())
         );
   }

   public static void setLocatingGlowBerriesCooldown(GlareEntity glare, UniformInt cooldown) {
      glare.getBrain()
         .setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), cooldown.sample(glare.getRandom()));
   }

   public static void setItemPickupCooldown(GlareEntity glare) {
      glare.getBrain().setMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, TimeUtil.rangeOfSeconds(1, 10).sample(glare.getRandom()));
   }

   public static Predicate<ItemStack> getTemptations() {
      return itemStack -> itemStack.is(FriendsAndFoesTags.GLARE_TEMPT_ITEMS);
   }
}
