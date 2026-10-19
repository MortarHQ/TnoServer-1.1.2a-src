package net.tslat.smartbrainlib.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.Timeline;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.object.BrainBehaviourConsumer;
import net.tslat.smartbrainlib.object.BrainBehaviourPredicate;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import org.jetbrains.annotations.Nullable;

public final class BrainUtils {
   public static <T> T memoryOrDefault(LivingEntity entity, MemoryModuleType<T> memory, Supplier<T> fallback) {
      return memoryOrDefault(entity.getBrain(), memory, fallback);
   }

   public static <T> T memoryOrDefault(Brain<?> brain, MemoryModuleType<T> memory, Supplier<T> fallback) {
      return brain.getMemory(memory).orElseGet(fallback);
   }

   public static <T> T computeMemoryIfAbsent(LivingEntity entity, MemoryModuleType<T> memory, Supplier<T> fallback) {
      return computeMemoryIfAbsent(entity.getBrain(), memory, fallback);
   }

   public static <T> T computeMemoryIfAbsent(Brain<?> brain, MemoryModuleType<T> memory, Supplier<T> fallback) {
      return brain.getMemory(memory).orElseGet(() -> {
         T newMemory = fallback.get();
         brain.setMemory(memory, newMemory);
         return newMemory;
      });
   }

   @Nullable
   public static <T> T getMemory(LivingEntity entity, MemoryModuleType<T> memory) {
      return getMemory(entity.getBrain(), memory);
   }

   @Nullable
   public static <T> T getMemory(Brain<?> brain, MemoryModuleType<T> memory) {
      return memoryOrDefault(brain, memory, () -> null);
   }

   public static <T> void withMemory(LivingEntity entity, MemoryModuleType<T> memory, Consumer<T> consumer) {
      withMemory(entity.getBrain(), memory, consumer);
   }

   public static <T> void withMemory(Brain<?> brain, MemoryModuleType<T> memory, Consumer<T> consumer) {
      brain.getMemory(memory).ifPresent(consumer);
   }

   public static boolean hasMemory(LivingEntity entity, MemoryModuleType<?> memory) {
      return hasMemory(entity.getBrain(), memory);
   }

   public static boolean hasMemory(Brain<?> brain, MemoryModuleType<?> memory) {
      return brain.hasMemoryValue(memory);
   }

   public static long getTimeUntilMemoryExpires(LivingEntity entity, MemoryModuleType<?> memory) {
      return getTimeUntilMemoryExpires(entity.getBrain(), memory);
   }

   public static long getTimeUntilMemoryExpires(Brain<?> brain, MemoryModuleType<?> memory) {
      return brain.getTimeUntilExpiry(memory);
   }

   public static <T> void setMemory(LivingEntity entity, MemoryModuleType<T> memoryType, T memory) {
      setMemory(entity.getBrain(), memoryType, memory);
   }

   public static <T> void setMemory(Brain<?> brain, MemoryModuleType<T> memoryType, T memory) {
      brain.setMemory(memoryType, memory);
   }

   public static <T> void setForgettableMemory(LivingEntity entity, MemoryModuleType<T> memoryType, T memory, int expirationTicks) {
      setForgettableMemory(entity.getBrain(), memoryType, memory, expirationTicks);
   }

   public static <T> void setForgettableMemory(Brain<?> brain, MemoryModuleType<T> memoryType, T memory, int expirationTicks) {
      brain.setMemoryWithExpiry(memoryType, memory, expirationTicks);
   }

   public static void clearMemory(LivingEntity entity, MemoryModuleType<?> memory) {
      clearMemory(entity.getBrain(), memory);
   }

   public static void clearMemory(Brain<?> brain, MemoryModuleType<?> memory) {
      brain.eraseMemory(memory);
   }

   public static void clearMemories(LivingEntity entity, MemoryModuleType<?>... memories) {
      clearMemories(entity.getBrain(), memories);
   }

   public static void clearMemories(Brain<?> brain, MemoryModuleType<?>... memories) {
      for (MemoryModuleType<?> memory : memories) {
         brain.eraseMemory(memory);
      }
   }

   @Nullable
   public static LivingEntity getTargetOfEntity(LivingEntity entity) {
      return getTargetOfEntity(entity, null);
   }

   @Nullable
   public static LivingEntity getTargetOfEntity(LivingEntity entity, @Nullable LivingEntity fallback) {
      return memoryOrDefault(entity.getBrain(), MemoryModuleType.ATTACK_TARGET, () -> fallback);
   }

   @Nullable
   public static LivingEntity getLastAttacker(LivingEntity entity) {
      return memoryOrDefault(entity, MemoryModuleType.HURT_BY_ENTITY, () -> null);
   }

   public static void setTargetOfEntity(LivingEntity entity, @Nullable LivingEntity target) {
      if (entity instanceof Mob mob) {
         mob.setTarget(target);
      }

      if (target == null) {
         clearMemory(entity, MemoryModuleType.ATTACK_TARGET);
      } else {
         setMemory(entity, MemoryModuleType.ATTACK_TARGET, target);
      }
   }

   public static boolean canSee(LivingEntity entity, LivingEntity target) {
      Brain<?> brain = entity.getBrain();
      if (BehaviorUtils.entityIsVisible(brain, target)) {
         return true;
      } else {
         return entity instanceof Mob mob ? mob.getSensing().hasLineOfSight(target) : entity.hasLineOfSight(target);
      }
   }

   public static void setSpecialCooldown(LivingEntity entity, int ticks) {
      setForgettableMemory(entity, SBLMemoryTypes.SPECIAL_ATTACK_COOLDOWN.get(), true, ticks);
   }

   public static boolean isOnSpecialCooldown(LivingEntity entity) {
      return hasMemory(entity, SBLMemoryTypes.SPECIAL_ATTACK_COOLDOWN.get());
   }

   public static Stream<BehaviorControl<?>> getAllBehaviours(Brain<?> brain) {
      return brain instanceof SmartBrain smartBrain
         ? smartBrain.getBehaviours()
         : brain.availableBehaviorsByPriority
            .values()
            .stream()
            .map(Map::values)
            .flatMap(set -> set.stream().map(value -> value.stream().toList()).flatMap(Collection::stream));
   }

   public static void forEachBehaviour(Brain<?> brain, BrainBehaviourConsumer consumer) {
      if (brain instanceof SmartBrain smartBrain) {
         smartBrain.forEachBehaviour(consumer);
      } else {
         for (Entry<Integer, Map<Activity, Set<BehaviorControl<?>>>> priorityEntry : brain.availableBehaviorsByPriority.entrySet()) {
            Integer priority = priorityEntry.getKey();

            for (Entry<Activity, Set<BehaviorControl<?>>> activityEntry : priorityEntry.getValue().entrySet()) {
               Activity activity = activityEntry.getKey();

               for (BehaviorControl<?> behaviour : activityEntry.getValue()) {
                  consumeBehaviour(priority, activity, behaviour, null, consumer);
               }
            }
         }
      }
   }

   private static <E extends LivingEntity> void consumeBehaviour(
      int priority, Activity activity, BehaviorControl<E> behaviour, @Nullable BehaviorControl<E> parentBehaviour, BrainBehaviourConsumer consumer
   ) {
      consumer.consume(priority, activity, behaviour, parentBehaviour);
      if (behaviour instanceof GateBehavior<E> groupBehaviour) {
         groupBehaviour.behaviors
            .stream()
            .forEach(childBehaviour -> consumeBehaviour(priority, activity, (BehaviorControl<E>)childBehaviour, groupBehaviour, consumer));
      } else if (behaviour instanceof GroupBehaviour<E> groupBehaviour) {
         groupBehaviour.getBehaviours()
            .forEachRemaining(childBehaviour -> consumeBehaviour(priority, activity, (BehaviorControl<E>)childBehaviour, groupBehaviour, consumer));
      }
   }

   public static <E extends LivingEntity> void removeBehaviour(E entity, BrainBehaviourPredicate predicate) {
      if (entity.getBrain() instanceof SmartBrain smartBrain) {
         smartBrain.removeBehaviour(entity, predicate);
      } else {
         for (Entry<Integer, Map<Activity, Set<BehaviorControl<E>>>> priorityEntry : entity.getBrain().availableBehaviorsByPriority.entrySet()) {
            Integer priority = priorityEntry.getKey();

            for (Entry<Activity, Set<BehaviorControl<E>>> activityEntry : priorityEntry.getValue().entrySet()) {
               Activity activity = activityEntry.getKey();
               Iterator<BehaviorControl<E>> iterator = activityEntry.getValue().iterator();

               while (iterator.hasNext()) {
                  BehaviorControl<E> behaviour = iterator.next();
                  checkBehaviour(priority, activity, behaviour, null, predicate, () -> {
                     if (behaviour.getStatus() == Status.RUNNING) {
                        behaviour.doStop((ServerLevel)entity.level(), entity, entity.level().getGameTime());
                     }

                     iterator.remove();
                  });
               }
            }
         }
      }
   }

   private static <E extends LivingEntity> void checkBehaviour(
      int priority,
      Activity activity,
      BehaviorControl<E> behaviour,
      @Nullable BehaviorControl<E> parentBehaviour,
      BrainBehaviourPredicate predicate,
      Runnable callback
   ) {
      if (predicate.isBehaviour(priority, activity, behaviour, parentBehaviour)) {
         callback.run();
      } else if (behaviour instanceof GateBehavior groupBehaviour) {
         Iterator<BehaviorControl<E>> childBehaviourIterator = groupBehaviour.behaviors.iterator();

         while (childBehaviourIterator.hasNext()) {
            checkBehaviour(priority, activity, childBehaviourIterator.next(), groupBehaviour, predicate, childBehaviourIterator::remove);
         }

         if (!groupBehaviour.behaviors.iterator().hasNext()) {
            callback.run();
         }
      } else if (behaviour instanceof GroupBehaviour groupBehaviour) {
         Iterator<BehaviorControl<E>> childBehaviourIterator = groupBehaviour.getBehaviours();

         while (childBehaviourIterator.hasNext()) {
            checkBehaviour(priority, activity, childBehaviourIterator.next(), groupBehaviour, predicate, childBehaviourIterator::remove);
         }

         if (!groupBehaviour.getBehaviours().hasNext()) {
            callback.run();
         }
      }
   }

   public static void addBehaviour(Brain<?> brain, int priority, Activity activity, BehaviorControl behaviourControl) {
      if (brain instanceof SmartBrain smartBrain) {
         smartBrain.addBehaviour(priority, activity, behaviourControl);
      } else {
         brain.availableBehaviorsByPriority
            .computeIfAbsent(priority, priority2 -> Maps.newHashMap())
            .computeIfAbsent(activity, activity2 -> Sets.newLinkedHashSet())
            .add(behaviourControl);
         if (behaviourControl instanceof Behavior<?> behavior) {
            for (MemoryModuleType<?> memoryType : behavior.entryCondition.keySet()) {
               brain.memories.putIfAbsent(memoryType, Optional.empty());
            }
         }
      }
   }

   public static void addActivity(Brain<?> brain, BrainActivityGroup<?> behaviourGroup) {
      if (brain instanceof SmartBrain smartBrain) {
         smartBrain.addActivity(behaviourGroup);
      } else {
         brain.addActivityAndRemoveMemoriesWhenStopped(
            behaviourGroup.getActivity(),
            behaviourGroup.pairBehaviourPriorities(),
            behaviourGroup.getActivityStartMemoryConditions(),
            behaviourGroup.getWipedMemoriesOnFinish()
         );
      }
   }

   public static <S extends Sensor<?>> void addSensor(Brain<?> brain, SensorType<S> sensorType, S sensor) {
      if (brain instanceof SmartBrain smartBrain) {
         if (sensor instanceof ExtendedSensor extendedSensor) {
            smartBrain.addSensor(extendedSensor);
         } else {
            throw new IllegalArgumentException(
               "Attempted to provide sensor to SmartBrain, only ExtendedSensor subclasses acceptable. Sensor: " + sensor.getClass()
            );
         }
      } else {
         brain.sensors.put(sensorType, sensor);
         addMemories(brain, sensor.requires().toArray(new MemoryModuleType[0]));
      }
   }

   public static void addMemories(Brain<?> brain, MemoryModuleType<?>... memories) {
      if (brain instanceof SmartBrain smartBrain) {
         for (MemoryModuleType<?> memoryType : memories) {
            smartBrain.getMemory(memoryType);
         }
      } else {
         for (MemoryModuleType<?> memoryType : memories) {
            brain.memories.computeIfAbsent(memoryType, key -> Optional.empty()).map(ExpirableValue::getValue);
         }
      }
   }

   public static void addScheduledActivityTransition(Brain<?> brain, Activity activity, int tickTime, SmartBrainSchedule.Type tickType) {
      if (brain instanceof SmartBrain smartBrain) {
         SmartBrainSchedule schedule;
         if ((schedule = smartBrain.getSchedule()) == null) {
            smartBrain.setSchedule(schedule = new SmartBrainSchedule(tickType));
         }

         schedule.activityAt(tickTime, activity);
      } else {
         Schedule schedule;
         if ((schedule = brain.getSchedule()) == Schedule.EMPTY) {
            brain.setSchedule(new Schedule());
         }

         Timeline timeline = schedule.timelines.computeIfAbsent(activity, key -> new Timeline());
         timeline.addKeyframe(tickTime, 1.0F);

         for (Entry<Activity, Timeline> entry : schedule.timelines.entrySet()) {
            if (entry.getKey() != activity) {
               entry.getValue().addKeyframe(tickTime, 0.0F);
            }
         }
      }
   }
}
