package mebahelcreaturesdwarven.util;

import java.lang.reflect.Method;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_5321;

public final class ClientSoundBridge {
   private static final String IMPL = "mebahelcreaturesdwarven.sound.DwemerBlockSoundClient";

   private ClientSoundBridge() {
   }

   public static void startLoopingBlockSound(class_5321<class_1937> worldKey, class_2338 pos, class_2960 soundId, float volume, float pitch) {
      if (FabricLoader.getInstance().getEnvironmentType().name().equals("CLIENT")) {
         try {
            Class<?> clazz = Class.forName("mebahelcreaturesdwarven.sound.DwemerBlockSoundClient");
            Method m = clazz.getMethod("startLoopingBlockSound", class_5321.class, class_2338.class, class_2960.class, float.class, float.class);
            m.invoke(null, worldKey, pos, soundId, volume, pitch);
         } catch (Throwable ignored) {
            System.out.println("startLoopingBlockSound failing");
         }
      }
   }

   public static void stopLoopingBlockSound(class_5321<class_1937> worldKey, class_2338 pos) {
      if (FabricLoader.getInstance().getEnvironmentType().name().equals("CLIENT")) {
         try {
            Class<?> clazz = Class.forName("mebahelcreaturesdwarven.sound.DwemerBlockSoundClient");
            Method m = clazz.getMethod("stopLoopingBlockSound", class_5321.class, class_2338.class);
            m.invoke(null, worldKey, pos);
         } catch (Throwable var4) {
         }
      }
   }
}
