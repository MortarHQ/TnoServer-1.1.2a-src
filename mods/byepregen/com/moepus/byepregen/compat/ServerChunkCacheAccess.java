package com.moepus.byepregen.compat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.thread.BlockableEventLoop;

public final class ServerChunkCacheAccess {
   private static final VarHandle MAIN_THREAD_PROCESSOR = getVarHandle("mainThreadProcessor");

   private ServerChunkCacheAccess() {
   }

   public static BlockableEventLoop<Runnable> mainThreadProcessor(ServerChunkCache cache) {
      return (BlockableEventLoop)MAIN_THREAD_PROCESSOR.get((ServerChunkCache)cache);
   }

   private static VarHandle getVarHandle(String name) {
      try {
         Field field = ServerChunkCache.class.getDeclaredField(name);
         Lookup lookup = MethodHandles.privateLookupIn(ServerChunkCache.class, MethodHandles.lookup());
         return lookup.findVarHandle(ServerChunkCache.class, name, field.getType());
      } catch (ReflectiveOperationException e) {
         throw new IllegalStateException("Unable to find ServerChunkCache." + name, e);
      }
   }
}
