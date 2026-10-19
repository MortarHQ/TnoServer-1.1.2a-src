package net.tslat.smartbrainlib.api.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.object.BrainBehaviourConsumer;
import net.tslat.smartbrainlib.object.BrainBehaviourPredicate;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

public class SmartBrain<E extends LivingEntity & SmartBrainOwner<E>> extends Brain<E> {
   protected final List<MemoryModuleType<?>> expirableMemories = new ObjectArrayList();
   protected final List<SmartBrain.ActivityBehaviours<E>> behaviours = new ObjectArrayList();
   protected final List<Pair<SensorType<ExtendedSensor<? super E>>, ExtendedSensor<? super E>>> sensors = new ObjectArrayList();
   protected SmartBrainSchedule schedule = null;
   protected boolean sortBehaviours = false;

   public SmartBrain(List<MemoryModuleType<?>> memories, List<? extends ExtendedSensor<E>> sensors, @Nullable List<BrainActivityGroup<E>> taskList) {
      super(memories, ImmutableList.of(), ImmutableList.of(), SmartBrain::emptyBrainCodec);

      for (ExtendedSensor<E> sensor : sensors) {
         this.sensors.add(Pair.of(sensor.type(), sensor));
      }

      if (taskList != null) {
         for (BrainActivityGroup<E> group : taskList) {
            this.addActivity(group);
         }
      }
   }

   public void tick(ServerLevel level, E entity) {
      entity.level().getProfiler().push("SmartBrain");
      if (this.sortBehaviours) {
         this.behaviours.sort(Comparator.comparingInt(SmartBrain.ActivityBehaviours::priority));
      }

      this.forgetOutdatedMemories();
      this.tickSensors(level, entity);
      this.checkForNewBehaviours(level, entity);
      this.tickRunningBehaviours(level, entity);
      this.findAndSetActiveActivity(entity);
      entity.level().getProfiler().pop();
      if (entity instanceof Mob mob) {
         mob.setAggressive(BrainUtils.hasMemory(mob, MemoryModuleType.ATTACK_TARGET));
      }
   }

   protected void findAndSetActiveActivity(E entity) {
      Activity nextActivity = this.getFirstValidActivity(entity.getActivityPriorities());
      if (nextActivity != null && entity.getScheduleIgnoringActivities().contains(nextActivity)) {
         this.setActiveActivity(nextActivity);
      } else {
         if (this.schedule != null) {
            Activity scheduledActivity = this.schedule.tick(entity);
            if (scheduledActivity != null && this.activityRequirementsAreMet(scheduledActivity)) {
               if (!this.isActive(scheduledActivity)) {
                  this.setActiveActivity(scheduledActivity);
               }

               return;
            }
         }

         if (nextActivity != null) {
            this.setActiveActivity(nextActivity);
         }
      }
   }

   @Nullable
   protected Activity getFirstValidActivity(List<Activity> activities) {
      for (Activity activity : activities) {
         if (this.activityRequirementsAreMet(activity)) {
            return activity;
         }
      }

      return null;
   }

   protected void tickSensors(ServerLevel level, E entity) {
      for (Pair<SensorType<ExtendedSensor<? super E>>, ExtendedSensor<? super E>> sensor : this.sensors) {
         ((ExtendedSensor)sensor.getSecond()).tick(level, entity);
      }
   }

   protected void checkForNewBehaviours(ServerLevel level, E entity) {
      long gameTime = level.getGameTime();

      for (SmartBrain.ActivityBehaviours<E> behaviourGroup : this.behaviours) {
         for (Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours) {
            if (this.getActiveActivities().contains(pair.getFirst())) {
               for (BehaviorControl<? super E> behaviour : (List)pair.getSecond()) {
                  if (behaviour.getStatus() == Status.STOPPED) {
                     behaviour.tryStart(level, entity, gameTime);
                  }
               }
            }
         }
      }
   }

   protected void tickRunningBehaviours(ServerLevel level, E entity) {
      long gameTime = level.getGameTime();

      for (SmartBrain.ActivityBehaviours<E> behaviourGroup : this.behaviours) {
         for (Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours) {
            for (BehaviorControl<? super E> behaviour : (List)pair.getSecond()) {
               if (behaviour.getStatus() == Status.RUNNING) {
                  behaviour.tickOrStop(level, entity, gameTime);
               }
            }
         }
      }
   }

   public void forgetOutdatedMemories() {
      Iterator<MemoryModuleType<?>> expirable = this.expirableMemories.iterator();

      while (expirable.hasNext()) {
         MemoryModuleType<?> memoryType = expirable.next();
         Optional<? extends ExpirableValue<?>> memory = (Optional<? extends ExpirableValue<?>>)this.memories.get(memoryType);
         if (memory.isEmpty()) {
            expirable.remove();
         } else {
            ExpirableValue<?> value = (ExpirableValue<?>)memory.get();
            if (!value.canExpire()) {
               expirable.remove();
            } else if (value.hasExpired()) {
               expirable.remove();
               this.eraseMemory(memoryType);
            } else {
               value.tick();
            }
         }
      }
   }

   public void stopAll(ServerLevel level, E entity) {
      long gameTime = level.getGameTime();

      for (SmartBrain.ActivityBehaviours<E> behaviourGroup : this.behaviours) {
         for (Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours) {
            for (BehaviorControl<? super E> behaviour : (List)pair.getSecond()) {
               if (behaviour.getStatus() == Status.RUNNING) {
                  behaviour.doStop(level, entity, gameTime);
               }
            }
         }
      }
   }

   public <U> Optional<U> getMemory(MemoryModuleType<U> type) {
      return this.memories.computeIfAbsent(type, key -> Optional.empty()).map(ExpirableValue::getValue);
   }

   public <U> void setMemoryInternal(MemoryModuleType<U> memoryType, Optional<? extends ExpirableValue<?>> memory) {
      if (memory.isPresent() && memory.get().getValue() instanceof Collection<?> collection && collection.isEmpty()) {
         memory = Optional.empty();
      }

      this.memories.put(memoryType, memory);
      if (memory.isPresent() && memory.get().canExpire() && !this.expirableMemories.contains(memoryType)) {
         this.expirableMemories.add(memoryType);
      }
   }

   public <U> boolean isMemoryValue(MemoryModuleType<U> memoryType, U memory) {
      Optional<U> value = this.getMemory(memoryType);
      return value.isPresent() && value.get().equals(memory);
   }

   protected static <E extends LivingEntity & SmartBrainOwner<E>> Codec<Brain<E>> emptyBrainCodec() {
      MutableObject<Codec<Brain<E>>> brainCodec = new MutableObject();
      brainCodec.setValue(Codec.unit(() -> new Brain(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), brainCodec::getValue)));
      return (Codec<Brain<E>>)brainCodec.getValue();
   }

   protected static <E extends LivingEntity & SmartBrainOwner<E>> List<? extends SensorType<? extends Sensor<? super E>>> convertSensorsToTypes(
      List<? extends ExtendedSensor<E>> sensors
   ) {
      List<SensorType<? extends Sensor<? super E>>> types = new ObjectArrayList(sensors.size());

      for (ExtendedSensor<?> sensor : sensors) {
         types.add((SensorType<? extends Sensor<? super E>>)sensor.type());
      }

      return types;
   }

   public Brain<E> copyWithoutBehaviors() {
      SmartBrain<E> brain = new SmartBrain<>(
         this.memories.keySet().stream().toList(), this.sensors.stream().map(pair -> (ExtendedSensor<E>)pair.getSecond()).toList(), null
      );

      for (Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> entry : this.memories.entrySet()) {
         MemoryModuleType<?> memoryType = entry.getKey();
         if (entry.getValue().isPresent()) {
            brain.memories.put(memoryType, entry.getValue());
         }
      }

      return brain;
   }

   public List<BehaviorControl<? super E>> getRunningBehaviors() {
      List<BehaviorControl<? super E>> runningBehaviours = new ObjectArrayList();

      for (SmartBrain.ActivityBehaviours<E> behaviourGroup : this.behaviours) {
         for (Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours) {
            for (BehaviorControl<? super E> behaviour : (List)pair.getSecond()) {
               if (behaviour.getStatus() == Status.RUNNING) {
                  runningBehaviours.add(behaviour);
               }
            }
         }
      }

      return runningBehaviours;
   }

   public Stream<BehaviorControl<? super E>> getBehaviours() {
      return this.behaviours
         .stream()
         .map(SmartBrain.ActivityBehaviours::behaviours)
         .flatMap(list -> list.stream().map(Pair::getSecond).flatMap(Collection::stream));
   }

   public void removeAllBehaviors() {
      this.behaviours.clear();
   }

   public void addActivityAndRemoveMemoriesWhenStopped(
      Activity activity,
      ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> tasks,
      Set<Pair<MemoryModuleType<?>, MemoryStatus>> memorieStatuses,
      Set<MemoryModuleType<?>> memoryTypes
   ) {
      this.activityRequirements.put(activity, memorieStatuses);
      if (!memoryTypes.isEmpty()) {
         this.activityMemoriesToEraseWhenStopped.put(activity, memoryTypes);
      }

      UnmodifiableIterator var5 = tasks.iterator();

      while (var5.hasNext()) {
         Pair<Integer, ? extends BehaviorControl<? super E>> pair = (Pair<Integer, ? extends BehaviorControl<? super E>>)var5.next();
         this.addBehaviour((Integer)pair.getFirst(), activity, (BehaviorControl<? super E>)pair.getSecond());
      }
   }

   public void addActivity(BrainActivityGroup<E> activityGroup) {
      this.addActivityAndRemoveMemoriesWhenStopped(
         activityGroup.getActivity(),
         activityGroup.pairBehaviourPriorities(),
         activityGroup.getActivityStartMemoryConditions(),
         activityGroup.getWipedMemoriesOnFinish()
      );
   }

   public void addBehaviour(int priority, Activity activity, BehaviorControl<? super E> behaviour) {
      for (SmartBrain.ActivityBehaviours<E> behaviourGroup : this.behaviours) {
         if (behaviourGroup.priority == priority) {
            for (Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours) {
               if (pair.getFirst() == activity) {
                  ((List)pair.getSecond()).add(behaviour);
                  return;
               }
            }

            behaviourGroup.behaviours.add(Pair.of(activity, ObjectArrayList.of(new BehaviorControl[]{behaviour})));
            return;
         }
      }

      this.behaviours
         .add(
            new SmartBrain.ActivityBehaviours<>(
               priority, ObjectArrayList.of(new Pair[]{Pair.of(activity, ObjectArrayList.of(new BehaviorControl[]{behaviour}))})
            )
         );
      this.sortBehaviours = true;
   }

   public void removeBehaviour(E entity, BrainBehaviourPredicate predicate) {
      for (SmartBrain.ActivityBehaviours<E> behaviourGroup : this.behaviours) {
         int priority = behaviourGroup.priority;

         for (Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours) {
            Activity activity = (Activity)pair.getFirst();
            Iterator<BehaviorControl<? super E>> iterator = ((List)pair.getSecond()).iterator();

            while (iterator.hasNext()) {
               BehaviorControl<? super E> behaviour = iterator.next();
               checkBehaviour(priority, activity, (BehaviorControl<E>)behaviour, null, predicate, () -> {
                  if (behaviour.getStatus() == Status.RUNNING) {
                     behaviour.doStop((ServerLevel)entity.level(), entity, entity.level().getGameTime());
                  }

                  iterator.remove();
               });
            }
         }
      }
   }

   public SmartBrain<E> setSchedule(SmartBrainSchedule schedule) {
      this.schedule = schedule;
      return this;
   }

   public SmartBrainSchedule getSchedule() {
      return this.schedule;
   }

   public void scheduleTask(E brainOwner, int delay, Consumer<E> task) {
      if (this.schedule == null) {
         this.schedule = new SmartBrainSchedule();
      }

      this.schedule.scheduleTask(brainOwner, delay, task);
   }

   protected static <E extends LivingEntity> void checkBehaviour(
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

   public void forEachBehaviour(BrainBehaviourConsumer consumer) {
      for (SmartBrain.ActivityBehaviours<E> behavioursGroup : this.behaviours) {
         int priority = behavioursGroup.priority();

         for (Pair<Activity, List<BehaviorControl<? super E>>> behaviourList : behavioursGroup.behaviours()) {
            Activity activity = (Activity)behaviourList.getFirst();

            for (BehaviorControl<? super E> behaviour : (List)behaviourList.getSecond()) {
               consumeBehaviour(priority, activity, (BehaviorControl<E>)behaviour, null, consumer);
            }
         }
      }
   }

   protected static <E extends LivingEntity> void consumeBehaviour(
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

   public void addSensor(ExtendedSensor<E> sensor) {
      SensorType<ExtendedSensor<? super E>> sensorType = (SensorType<ExtendedSensor<? super E>>)sensor.type();
      this.sensors.add(Pair.of(sensorType, sensor));
   }

   @Deprecated(forRemoval = true)
   public final void setSchedule(Schedule schedule) {
   }

   protected record ActivityBehaviours<E extends LivingEntity & SmartBrainOwner<E>>(
      int priority, List<Pair<Activity, List<BehaviorControl<? super E>>>> behaviours
   ) {
   }
}
