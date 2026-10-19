package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire.WildfireBarrageAttackTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire.WildfireShockwaveAttackTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire.WildfireSummonBlazeTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;

public final class WildfireBrain {
   public static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(
      MemoryModuleType.PATH,
      MemoryModuleType.NEAREST_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
      MemoryModuleType.ATTACK_TARGET,
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.AVOID_TARGET,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(),
      (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()
   );
   public static final List<SensorType<? extends Sensor<? super WildfireEntity>>> SENSORS = List.of(SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
   private static final UniformInt BARRAGE_ATTACK_COOLDOWN_PROVIDER = UniformInt.of(150, 300);
   private static final UniformInt SHOCKWAVE_ATTACK_COOLDOWN_PROVIDER = UniformInt.of(150, 300);
   private static final UniformInt SUMMON_BLAZE_COOLDOWN_PROVIDER = UniformInt.of(600, 1200);
   private static final UniformInt AVOID_MEMORY_DURATION = TimeUtil.rangeOfSeconds(5, 20);

   public static Brain<?> create(Dynamic<?> dynamic) {
      Provider<WildfireEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
      Brain<WildfireEntity> brain = profile.makeBrain(dynamic);
      addCoreActivities(brain);
      addIdleActivities(brain);
      addFightActivities(brain);
      addAvoidActivities(brain);
      brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
      brain.setDefaultActivity(Activity.IDLE);
      brain.useDefaultActivity();
      return brain;
   }

   private static void addCoreActivities(Brain<WildfireEntity> brain) {
      brain.addActivity(
         Activity.CORE,
         0,
         ImmutableList.of(
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink(),
            new CountDownCooldownTicks((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get()),
            new CountDownCooldownTicks((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()),
            new CountDownCooldownTicks((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get())
         )
      );
   }

   private static void addIdleActivities(Brain<WildfireEntity> brain) {
      brain.addActivity(
         Activity.IDLE, ImmutableList.of(Pair.of(0, StartAttacking.create(wildfire -> true, WildfireBrain::getTarget)), Pair.of(1, makeRandomWanderTask()))
      );
   }

   private static void addFightActivities(Brain<WildfireEntity> brain) {
      brain.addActivityAndRemoveMemoryWhenStopped(
         Activity.FIGHT,
         10,
         ImmutableList.of(new WildfireSummonBlazeTask(), new WildfireBarrageAttackTask(), new WildfireShockwaveAttackTask()),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static void addAvoidActivities(Brain<WildfireEntity> brain) {
      brain.addActivityAndRemoveMemoryWhenStopped(
         Activity.AVOID,
         10,
         ImmutableList.of(SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.4F, 16, true), makeRandomWanderTask()),
         MemoryModuleType.AVOID_TARGET
      );
   }

   private static RunOne<WildfireEntity> makeRandomWanderTask() {
      return new RunOne(
         ImmutableList.of(Pair.of(RandomStroll.stroll(0.6F), 2), Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2), Pair.of(new DoNothing(30, 60), 1))
      );
   }

   public static void updateActivities(WildfireEntity wildfire) {
      wildfire.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
   }

   public static void setBarrageAttackCooldown(WildfireEntity wildfire) {
      wildfire.getBrain()
         .setMemory(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(),
            BARRAGE_ATTACK_COOLDOWN_PROVIDER.sample(wildfire.getRandom())
         );
      onCooldown(wildfire);
   }

   public static void setShockwaveAttackCooldown(WildfireEntity wildfire) {
      wildfire.getBrain()
         .setMemory(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(),
            SHOCKWAVE_ATTACK_COOLDOWN_PROVIDER.sample(wildfire.getRandom())
         );
      onCooldown(wildfire);
   }

   public static void setSummonBlazeCooldown(WildfireEntity wildfire) {
      wildfire.getBrain()
         .setMemory(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(), SUMMON_BLAZE_COOLDOWN_PROVIDER.sample(wildfire.getRandom())
         );
      onCooldown(wildfire);
   }

   public static boolean shouldRunAway(WildfireEntity wildfire) {
      return wildfire.getBrain().getMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get()).isPresent()
         && wildfire.getBrain().getMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()).isPresent()
         && (
            wildfire.getBrain().getMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get()).isPresent()
               || wildfire.getSummonedBlazesCount() == 2
         );
   }

   public static void onCooldown(WildfireEntity wildfire) {
      if (shouldRunAway(wildfire)) {
         LivingEntity attackTarget = (LivingEntity)wildfire.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
         if (attackTarget != null) {
            runAwayFrom(wildfire, attackTarget);
         }
      }
   }

   public static void onAttacked(WildfireEntity wildfire, LivingEntity attacker) {
      setAttackTarget(wildfire, attacker);
      if (shouldRunAway(wildfire)) {
         runAwayFrom(wildfire, attacker);
      }
   }

   private static Optional<? extends LivingEntity> getTarget(WildfireEntity wildfire) {
      Player nearestVisibleTargetablePlayer = wildfire.getBrain()
         .getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
         .orElse(wildfire.level().getNearestPlayer(wildfire, 32.0));
      return nearestVisibleTargetablePlayer == null ? Optional.empty() : Optional.of(nearestVisibleTargetablePlayer);
   }

   public static void setAttackTarget(WildfireEntity wildfire, LivingEntity target) {
      wildfire.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      wildfire.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, target, 200L);
   }

   private static void runAwayFrom(WildfireEntity wildfire, LivingEntity target) {
      wildfire.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
      wildfire.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      wildfire.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, target, AVOID_MEMORY_DURATION.sample(wildfire.level().getRandom()));
   }
}
