package net.tslat.smartbrainlib.api;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.Nullable;

public interface SmartBrainOwner<T extends LivingEntity & SmartBrainOwner<T>> {
   List<? extends ExtendedSensor<? extends T>> getSensors();

   default BrainActivityGroup<? extends T> getCoreTasks() {
      return BrainActivityGroup.empty();
   }

   default BrainActivityGroup<? extends T> getIdleTasks() {
      return BrainActivityGroup.empty();
   }

   default BrainActivityGroup<? extends T> getFightTasks() {
      return BrainActivityGroup.empty();
   }

   default Map<Activity, BrainActivityGroup<? extends T>> getAdditionalTasks() {
      return new Object2ObjectOpenHashMap(0);
   }

   default Set<Activity> getAlwaysRunningActivities() {
      return ImmutableSet.of(Activity.CORE);
   }

   default Activity getDefaultActivity() {
      return Activity.IDLE;
   }

   default List<Activity> getActivityPriorities() {
      return ObjectArrayList.of(new Activity[]{Activity.FIGHT, Activity.IDLE});
   }

   default Set<Activity> getScheduleIgnoringActivities() {
      return ObjectArraySet.of(Activity.FIGHT);
   }

   default void handleAdditionalBrainSetup(SmartBrain<? extends T> brain) {
   }

   @Nullable
   default SmartBrainSchedule getSchedule() {
      return null;
   }

   default void tickBrain(T entity) {
      entity.getBrain().tick((ServerLevel)entity.level(), entity);
   }
}
