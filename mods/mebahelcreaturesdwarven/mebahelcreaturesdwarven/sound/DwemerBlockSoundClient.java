package mebahelcreaturesdwarven.sound;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1102;
import net.minecraft.class_1113;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_3419;
import net.minecraft.class_5321;
import net.minecraft.class_7923;
import net.minecraft.class_1113.class_1114;

@Environment(EnvType.CLIENT)
public final class DwemerBlockSoundClient {
   private static final Map<DwemerBlockSoundClient.Key, DwemerBlockSoundClient.LoopingBlockSoundInstance> ACTIVE = new HashMap<>();

   private DwemerBlockSoundClient() {
   }

   public static void startLoopingBlockSound(class_5321<class_1937> worldKey, class_2338 pos, class_2960 soundId, float volume, float pitch) {
      class_310 client = class_310.method_1551();
      if (client != null && client.field_1687 != null && client.method_1483() != null) {
         if (Objects.equals(client.field_1687.method_27983(), worldKey)) {
            DwemerBlockSoundClient.Key key = new DwemerBlockSoundClient.Key(worldKey, pos);
            DwemerBlockSoundClient.LoopingBlockSoundInstance existing = ACTIVE.get(key);
            if (existing != null) {
               if (!existing.isStopped()) {
                  return;
               }

               ACTIVE.remove(key);
            }

            class_3414 soundEvent = (class_3414)class_7923.field_41172.method_10223(soundId);
            if (soundEvent != null) {
               DwemerBlockSoundClient.LoopingBlockSoundInstance sound = new DwemerBlockSoundClient.LoopingBlockSoundInstance(soundEvent, pos, volume, pitch);
               ACTIVE.put(key, sound);
               client.method_1483().method_4873(sound);
            }
         }
      }
   }

   public static void stopLoopingBlockSound(class_5321<class_1937> worldKey, class_2338 pos) {
      class_310 client = class_310.method_1551();
      if (client != null && client.method_1483() != null) {
         DwemerBlockSoundClient.Key key = new DwemerBlockSoundClient.Key(worldKey, pos);
         DwemerBlockSoundClient.LoopingBlockSoundInstance sound = ACTIVE.remove(key);
         if (sound != null) {
            sound.stopLoop();
            client.method_1483().method_4870(sound);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   private record Key(class_5321<class_1937> worldKey, class_2338 pos) {
      private Key {
         pos = pos.method_10062();
      }
   }

   @Environment(EnvType.CLIENT)
   private static class LoopingBlockSoundInstance extends class_1102 {
      private boolean stopped = false;

      protected LoopingBlockSoundInstance(class_3414 soundEvent, class_2338 pos, float volume, float pitch) {
         super(soundEvent, class_3419.field_15245, class_1113.method_43221());
         this.field_5446 = true;
         this.field_5451 = 0;
         this.field_5442 = volume;
         this.field_5441 = pitch;
         this.field_5439 = pos.method_10263() + 0.5;
         this.field_5450 = pos.method_10264() + 0.5;
         this.field_5449 = pos.method_10260() + 0.5;
         this.field_5440 = class_1114.field_5476;
         this.field_18936 = false;
      }

      public void stopLoop() {
         this.stopped = true;
      }

      public boolean isStopped() {
         return this.stopped;
      }
   }
}
