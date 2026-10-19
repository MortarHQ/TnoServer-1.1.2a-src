package net.tslat.smartbrainlib.api.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

public class SmartBrainProvider<E extends LivingEntity & SmartBrainOwner<E>> extends Provider<E> {
   private static final Map<EntityType<? extends LivingEntity>, ImmutableList<MemoryModuleType<?>>> BRAIN_MEMORY_CACHE = new Object2ObjectOpenHashMap();
   private final E owner;
   private final boolean nonStaticMemories;

   public SmartBrainProvider(E owner) {
      this(owner, false);
   }

   public SmartBrainProvider(E owner, boolean nonStaticMemories) {
      super(List.of(), List.of());
      this.owner = owner;
      this.nonStaticMemories = nonStaticMemories;
   }

   public final SmartBrain<E> makeBrain(Dynamic<?> codecLoader) {
      List<? extends ExtendedSensor<? extends E>> sensors = this.owner.getSensors();
      List<BrainActivityGroup<? extends E>> taskList = this.compileTasks();
      ImmutableList<MemoryModuleType<?>> memories;
      if (!this.nonStaticMemories && BRAIN_MEMORY_CACHE.containsKey(this.owner.getType())) {
         memories = BRAIN_MEMORY_CACHE.get(this.owner.getType());
      } else {
         memories = this.createMemoryList(taskList, sensors);
         if (!this.nonStaticMemories) {
            BRAIN_MEMORY_CACHE.put(this.owner.getType(), memories);
         }
      }

      SmartBrain<E> brain = new SmartBrain<>(memories, (List<? extends ExtendedSensor<E>>)sensors, (List<BrainActivityGroup<E>>)taskList);
      this.finaliseBrain(brain);
      return brain;
   }

   private ImmutableList<MemoryModuleType<?>> createMemoryList(List<BrainActivityGroup<? extends E>> taskList, List<? extends ExtendedSensor<?>> sensors) {
      Set<MemoryModuleType<?>> memoryTypes = new ObjectOpenHashSet();
      taskList.forEach(activityGroup -> activityGroup.getBehaviours().forEach(behavior -> this.collectMemoriesFromTask(memoryTypes, behavior)));
      sensors.forEach(sensor -> memoryTypes.addAll(sensor.memoriesUsed()));
      return ImmutableList.copyOf(memoryTypes);
   }

   private void collectMemoriesFromTask(Set<MemoryModuleType<?>> memories, BehaviorControl<?> behaviour) {
      if (behaviour instanceof GateBehavior<?> gateBehaviour) {
         gateBehaviour.behaviors.stream().forEach(subBehaviour -> this.collectMemoriesFromTask(memories, (BehaviorControl<?>)subBehaviour));
      } else if (behaviour instanceof GroupBehaviour<?> groupBehaviour) {
         groupBehaviour.getBehaviours().forEachRemaining(subBehaviour -> this.collectMemoriesFromTask(memories, subBehaviour));
      } else if (behaviour instanceof Behavior<?> behaviour2) {
         memories.addAll(behaviour2.entryCondition.keySet());
      }
   }

   private List<BrainActivityGroup<? extends E>> compileTasks() {
      List<BrainActivityGroup<? extends E>> tasks = new ObjectArrayList();
      BrainActivityGroup<? extends E> activityGroup;
      if (!(activityGroup = this.owner.getCoreTasks()).getBehaviours().isEmpty()) {
         tasks.add(activityGroup);
      }

      if (!(activityGroup = this.owner.getIdleTasks()).getBehaviours().isEmpty()) {
         tasks.add(activityGroup);
      }

      if (!(activityGroup = this.owner.getFightTasks()).getBehaviours().isEmpty()) {
         tasks.add(activityGroup);
      }

      tasks.addAll(this.owner.getAdditionalTasks().values());
      return tasks;
   }

   private void finaliseBrain(SmartBrain<E> brain) {
      brain.setCoreActivities(this.owner.getAlwaysRunningActivities());
      brain.setDefaultActivity(this.owner.getDefaultActivity());
      brain.useDefaultActivity();
      brain.setSchedule(this.owner.getSchedule());
      this.owner.handleAdditionalBrainSetup(brain);
   }

   @Deprecated(forRemoval = true)
   protected void addActivity(SmartBrain<E> brain, Activity activity, BrainActivityGroup<E> activityGroup) {
      brain.activityRequirements.put(activity, activityGroup.getActivityStartMemoryConditions());
      if (!activityGroup.getWipedMemoriesOnFinish().isEmpty()) {
         brain.activityMemoriesToEraseWhenStopped.put(activity, activityGroup.getWipedMemoriesOnFinish());
      }

      UnmodifiableIterator var4 = activityGroup.pairBehaviourPriorities().iterator();

      while (var4.hasNext()) {
         Pair<Integer, ? extends Behavior<? super E>> pair = (Pair<Integer, ? extends Behavior<? super E>>)var4.next();
         brain.addBehaviour((Integer)pair.getFirst(), activity, (BehaviorControl<? super E>)pair.getSecond());
      }
   }
}
