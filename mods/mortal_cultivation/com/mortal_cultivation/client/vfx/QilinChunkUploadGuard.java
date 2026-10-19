package com.mortal_cultivation.client.vfx;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

public final class QilinChunkUploadGuard {
   private static final ThreadLocal<Deque<AtomicBoolean>> TASKS = new ThreadLocal<>();

   private QilinChunkUploadGuard() {
   }

   public static void enter(AtomicBoolean cancelled) {
      Deque<AtomicBoolean> tasks = TASKS.get();
      if (tasks == null) {
         tasks = new ArrayDeque<>();
         TASKS.set(tasks);
      }

      tasks.push(cancelled);
   }

   public static void leave() {
      Deque<AtomicBoolean> tasks = TASKS.get();
      if (tasks != null) {
         tasks.pop();
         if (tasks.isEmpty()) {
            TASKS.remove();
         }
      }
   }

   public static AtomicBoolean current() {
      Deque<AtomicBoolean> tasks = TASKS.get();
      return tasks == null ? null : tasks.peek();
   }
}
