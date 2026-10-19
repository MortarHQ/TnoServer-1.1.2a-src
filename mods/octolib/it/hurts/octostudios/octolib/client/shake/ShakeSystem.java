package it.hurts.octostudios.octolib.client.shake;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.joml.Vector2f;

public class ShakeSystem {
   private static final Map<Shakeable, ShakeData> ACTIVE_SHAKES = new ConcurrentHashMap<>();

   public static void startShake(Shakeable target, ShakeData data) {
      ACTIVE_SHAKES.put(target, data);
   }

   public static void stopShake(Shakeable target) {
      ACTIVE_SHAKES.remove(target);
      target.setShakeOffset(new Vector2f(0.0F, 0.0F));
   }

   public static void updateAll() {
      for (Entry<Shakeable, ShakeData> entry : ACTIVE_SHAKES.entrySet()) {
         Shakeable target = entry.getKey();
         ShakeData data = entry.getValue();
         Vector2f offset = data.update();
         target.setShakeOffset(offset);
      }

      ACTIVE_SHAKES.values().removeIf(ShakeData::isFinished);
   }

   public static void clearAll() {
      for (Shakeable target : ACTIVE_SHAKES.keySet()) {
         stopShake(target);
      }
   }
}
