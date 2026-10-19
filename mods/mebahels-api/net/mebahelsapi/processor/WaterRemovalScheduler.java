package net.mebahelsapi.processor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2746;
import net.minecraft.class_3218;

public class WaterRemovalScheduler {
   private static final int MAX_TASKS = 120;
   private static final int MAX_TASKS_PER_TICK = 10;
   private final Queue<WaterRemovalScheduler.ScheduledTask> tasks = new ConcurrentLinkedQueue<>();
   private final Set<Integer> processedStructures = new HashSet<>();
   public final List<class_3218> worlds = new CopyOnWriteArrayList<>();
   private static final class_2248 VINE = class_2246.field_10597;
   private static final Set<class_2248> HANGING_VINES = Set.of(class_2246.field_22123, class_2246.field_22124, class_2246.field_28675, class_2246.field_28676);
   private static final Set<class_2248> UPWARD_VINES = Set.of(class_2246.field_23078, class_2246.field_23079);

   public void addWorld(class_3218 world) {
      if (world.method_27983().equals(class_1937.field_25179)) {
         this.worlds.clear();
         this.worlds.add(world);
      }
   }

   public void removeWorld(class_3218 world) {
      this.worlds.remove(world);
      this.tasks.clear();
      this.processedStructures.clear();
   }

   public void tick() {
      int processedCount = 0;
      Iterator<WaterRemovalScheduler.ScheduledTask> iterator = this.tasks.iterator();

      while (iterator.hasNext() && processedCount < 10) {
         WaterRemovalScheduler.ScheduledTask task = iterator.next();
         task.tick();
         if (task.isExpired()) {
            iterator.remove();
         } else if (task.isDue() && !this.processedStructures.contains(task.getHash())) {
            task.run();
            this.processedStructures.add(task.getHash());
            iterator.remove();
            processedCount++;
         }
      }

      if (this.processedStructures.size() > 120) {
         this.processedStructures.clear();
      }

      if (this.tasks.size() > 1000) {
         this.tasks.clear();
         this.processedStructures.clear();
      }
   }

   public void schedule(class_2338 startPos, class_2338 endPos, int delayTicks) {
      int hash = Objects.hash(startPos, endPos);
      if (this.tasks.size() < 120 && !this.processedStructures.contains(hash)) {
         class_243 startVec = new class_243(startPos.method_10263(), startPos.method_10264(), startPos.method_10260());
         class_243 endVecVec = new class_243(startPos.method_10263(), startPos.method_10264(), startPos.method_10260());
         this.tasks.add(new WaterRemovalScheduler.ScheduledTask(this.worlds, new class_238(startVec, endVecVec), delayTicks, hash));
      }
   }

   private static class ScheduledTask {
      private static final int MAX_LIFESPAN_TICKS = 200;
      private final List<class_3218> worlds;
      private final class_238 structureBox;
      private final int delayTicks;
      private final int hash;
      private int currentTick;

      public ScheduledTask(List<class_3218> worlds, class_238 structureBox, int delayTicks, int hash) {
         this.worlds = worlds;
         this.structureBox = structureBox;
         this.delayTicks = delayTicks;
         this.hash = hash;
      }

      public int getHash() {
         return this.hash;
      }

      public boolean isDue() {
         return this.currentTick >= this.delayTicks;
      }

      public boolean isExpired() {
         return this.currentTick >= 200;
      }

      public void tick() {
         this.currentTick++;
      }

      public void run() {
         for (class_3218 world : this.worlds) {
            class_2338.method_29715(this.structureBox).forEach(pos -> {
               if (world.method_22340(pos)) {
                  class_2680 state = world.method_8320(pos);
                  if (state.method_28498(class_2741.field_12508) && (Boolean)state.method_11654(class_2741.field_12508)) {
                     world.method_8652(pos, (class_2680)state.method_11657(class_2741.field_12508, false), 3);

                     for (class_2350 dir : class_2350.values()) {
                        class_2338 nPos = pos.method_10093(dir);
                        if (world.method_22340(nPos)) {
                           class_2680 nState = world.method_8320(nPos);
                           if (nState.method_27852(class_2246.field_10382)) {
                              world.method_8652(nPos, class_2246.field_10124.method_9564(), 3);
                           } else if (nState.method_28498(class_2741.field_12508) && (Boolean)nState.method_11654(class_2741.field_12508)) {
                              world.method_8652(nPos, (class_2680)nState.method_11657(class_2741.field_12508, false), 3);
                           }
                        }
                     }
                  } else {
                     class_2248 block = state.method_26204();
                     if (block == WaterRemovalScheduler.VINE) {
                        if (isFloatingVine(world, pos, state)) {
                           world.method_8652(pos, class_2246.field_10124.method_9564(), 3);
                        }
                     } else if (WaterRemovalScheduler.HANGING_VINES.contains(block)) {
                        if (isBrokenHangingChain(world, pos)) {
                           world.method_8652(pos, class_2246.field_10124.method_9564(), 3);
                        }
                     } else {
                        if (WaterRemovalScheduler.UPWARD_VINES.contains(block) && isBrokenUpwardChain(world, pos)) {
                           world.method_8652(pos, class_2246.field_10124.method_9564(), 3);
                        }
                     }
                  }
               }
            });
         }
      }

      private static boolean isFloatingVine(class_3218 world, class_2338 pos, class_2680 vineState) {
         class_2338 up = pos.method_10084();
         if (world.method_22340(up)) {
            class_2680 upState = world.method_8320(up);
            if (upState.method_26206(world, up, class_2350.field_11033)) {
               return false;
            }
         }

         if (hasValidSideSupport(world, pos, vineState, class_2350.field_11043, class_2741.field_12489)) {
            return false;
         } else if (hasValidSideSupport(world, pos, vineState, class_2350.field_11035, class_2741.field_12540)) {
            return false;
         } else {
            return hasValidSideSupport(world, pos, vineState, class_2350.field_11034, class_2741.field_12487)
               ? false
               : !hasValidSideSupport(world, pos, vineState, class_2350.field_11039, class_2741.field_12527);
         }
      }

      private static boolean hasValidSideSupport(class_3218 world, class_2338 pos, class_2680 vineState, class_2350 dir, class_2746 property) {
         if (vineState.method_28498(property) && (Boolean)vineState.method_11654(property)) {
            class_2338 supportPos = pos.method_10093(dir);
            if (!world.method_22340(supportPos)) {
               return false;
            }

            class_2680 supportState = world.method_8320(supportPos);
            return supportState.method_26206(world, supportPos, dir.method_10153());
         } else {
            return false;
         }
      }

      private static boolean isBrokenHangingChain(class_3218 world, class_2338 pos) {
         class_2338 up = pos.method_10084();
         if (!world.method_22340(up)) {
            return false;
         }

         class_2680 upState = world.method_8320(up);
         return WaterRemovalScheduler.HANGING_VINES.contains(upState.method_26204()) ? false : !upState.method_26212(world, up);
      }

      private static boolean isBrokenUpwardChain(class_3218 world, class_2338 pos) {
         class_2338 down = pos.method_10074();
         if (!world.method_22340(down)) {
            return false;
         }

         class_2680 downState = world.method_8320(down);
         return WaterRemovalScheduler.UPWARD_VINES.contains(downState.method_26204()) ? false : !downState.method_26212(world, down);
      }
   }
}
