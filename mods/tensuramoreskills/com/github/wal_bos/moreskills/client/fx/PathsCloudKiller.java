package com.github.wal_bos.moreskills.client.fx;

import java.lang.reflect.Method;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

public class PathsCloudKiller {
   private static boolean forcedOff = false;
   private static CloudStatus previous = null;

   public static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      ClientLevel level = mc.level;
      if (level != null) {
         boolean inPaths = level.dimension().equals(PathsClientState.PATHS);
         Options options = mc.options;
         if (inPaths) {
            if (!forcedOff) {
               previous = getCloudStatus(options);
               setCloudStatus(options, CloudStatus.OFF);
               forcedOff = true;
            } else if (getCloudStatus(options) != CloudStatus.OFF) {
               setCloudStatus(options, CloudStatus.OFF);
            }
         } else if (forcedOff) {
            setCloudStatus(options, previous == null ? CloudStatus.FANCY : previous);
            forcedOff = false;
            previous = null;
         }
      }
   }

   private static CloudStatus getCloudStatus(Options options) {
      try {
         Method method = Options.class.getMethod("getCloudsType");
         if (method.invoke(options) instanceof CloudStatus status) {
            return status;
         }
      } catch (Throwable var7) {
      }

      try {
         Method cloudStatus = Options.class.getMethod("cloudStatus");
         Object optionInstance = cloudStatus.invoke(options);
         Method get = optionInstance.getClass().getMethod("get");
         if (get.invoke(optionInstance) instanceof CloudStatus status) {
            return status;
         }
      } catch (Throwable var6) {
      }

      return CloudStatus.FANCY;
   }

   private static void setCloudStatus(Options options, CloudStatus status) {
      try {
         Method method = Options.class.getMethod("setCloudsType", CloudStatus.class);
         method.invoke(options, status);
      } catch (Throwable var6) {
         try {
            Method cloudStatus = Options.class.getMethod("cloudStatus");
            Object optionInstance = cloudStatus.invoke(options);
            Method set = optionInstance.getClass().getMethod("set", Object.class);
            set.invoke(optionInstance, status);
         } catch (Throwable var5) {
         }
      }
   }
}
