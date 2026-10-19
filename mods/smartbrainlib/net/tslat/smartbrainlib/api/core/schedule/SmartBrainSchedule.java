package net.tslat.smartbrainlib.api.core.schedule;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.jetbrains.annotations.Nullable;

public class SmartBrainSchedule extends Schedule {
   private final SmartBrainSchedule.Type type;
   private final Int2ObjectArrayMap<Activity> timeline = new Int2ObjectArrayMap(0);
   private final ListMultimap<Integer, Consumer<LivingEntity>> callbacks = MultimapBuilder.hashKeys(0).arrayListValues().build();
   private boolean sortedTimeline = true;

   public SmartBrainSchedule() {
      this(SmartBrainSchedule.Type.DAYTIME);
   }

   public SmartBrainSchedule(SmartBrainSchedule.Type type) {
      this.type = type;
   }

   public SmartBrainSchedule activityAt(int tick, Activity activity) {
      this.timeline.put(tick, activity);
      this.sortedTimeline = false;
      return this;
   }

   public SmartBrainSchedule doAt(int tick, Consumer<LivingEntity> callback) {
      this.callbacks.put(tick, callback);
      return this;
   }

   public void scheduleTask(LivingEntity brainOwner, int delay, Consumer<LivingEntity> task) {
      this.callbacks.put(this.type.resolveDelay(brainOwner, delay), (Consumer<LivingEntity>)entity -> task.accept(brainOwner));
   }

   public void clearSchedule() {
      this.callbacks.clear();
      this.timeline.clear();
   }

   @Nullable
   public Activity tick(LivingEntity brainOwner) {
      int tick = this.type.resolve(brainOwner);
      if (!this.callbacks.isEmpty()) {
         this.callbacks.get(tick).forEach(consumer -> consumer.accept(brainOwner));
         if (this.type == SmartBrainSchedule.Type.AGE) {
            this.callbacks.removeAll(tick);
         }
      }

      if (this.timeline.isEmpty()) {
         return null;
      }

      if (!this.sortedTimeline) {
         this.sortTimeline();
      }

      int index = -1;
      Activity activity = null;
      ObjectIterator var5 = this.timeline.int2ObjectEntrySet().iterator();

      while (var5.hasNext()) {
         Entry<Activity> entry = (Entry<Activity>)var5.next();
         index++;
         if (entry.getIntKey() >= tick) {
            if (entry.getIntKey() == tick) {
               activity = (Activity)entry.getValue();
            }
            break;
         }

         activity = (Activity)entry.getValue();
      }

      if (this.type == SmartBrainSchedule.Type.AGE && index + 1 >= this.timeline.size()) {
         this.timeline.clear();
      }

      return activity;
   }

   private void sortTimeline() {
      Int2ObjectArrayMap<Activity> copy = new Int2ObjectArrayMap(this.timeline);
      int[] keys = copy.keySet().toArray(new int[0]);
      Arrays.sort(keys);
      this.timeline.clear();

      for (int key : keys) {
         this.timeline.put(key, (Activity)copy.get(key));
      }

      this.sortedTimeline = true;
   }

   public final Activity getActivityAt(int tick) {
      if (this.type == SmartBrainSchedule.Type.AGE) {
         return Activity.IDLE;
      }

      Activity activity = Activity.IDLE;
      ObjectIterator var3 = this.timeline.int2ObjectEntrySet().iterator();

      while (var3.hasNext()) {
         Entry<Activity> entry = (Entry<Activity>)var3.next();
         if (entry.getIntKey() >= tick) {
            return activity;
         }

         activity = (Activity)entry.getValue();
      }

      return activity;
   }

   public enum Type {
      DAYTIME(e -> (int)(e.level().getDayTime() % 24000L), (e, t) -> (int)((e.level().getDayTime() + t.intValue()) % 24000L)),
      AGE(e -> e.tickCount, (e, t) -> e.tickCount + t);

      final ToIntFunction<LivingEntity> tickResolver;
      final ToIntBiFunction<LivingEntity, Integer> delayResolver;

      Type(ToIntFunction<LivingEntity> tickResolver, ToIntBiFunction<LivingEntity, Integer> delayResolver) {
         this.tickResolver = tickResolver;
         this.delayResolver = delayResolver;
      }

      public int resolve(LivingEntity entity) {
         return this.tickResolver.applyAsInt(entity);
      }

      public int resolveDelay(LivingEntity entity, int delay) {
         return this.delayResolver.applyAsInt(entity, delay);
      }
   }
}
