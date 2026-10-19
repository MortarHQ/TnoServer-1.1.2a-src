package mebahelcreaturesdwarven.sound;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.class_243;
import net.minecraft.class_310;

@Environment(EnvType.CLIENT)
public final class DwemerSphereWalkSoundHandler {
   private static final Int2ObjectOpenHashMap<DwemerSphereWalkLoopSoundInstance> ACTIVE = new Int2ObjectOpenHashMap();

   private DwemerSphereWalkSoundHandler() {
   }

   public static void init() {
      ClientTickEvents.END_CLIENT_TICK.register(DwemerSphereWalkSoundHandler::onClientTick);
   }

   private static void onClientTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         ACTIVE.int2ObjectEntrySet().removeIf(e -> e.getValue() == null || ((DwemerSphereWalkLoopSoundInstance)e.getValue()).method_4793());

         for (DwemerSphereEntity entity : client.field_1687
            .method_8390(DwemerSphereEntity.class, client.field_1724.method_5829().method_1014(128.0), e -> true)) {
            int id = entity.method_5628();
            boolean moving = isMovingHorizontally(entity) && !entity.isTransforming();
            if (moving) {
               if (!ACTIVE.containsKey(id)) {
                  DwemerSphereWalkLoopSoundInstance sound = new DwemerSphereWalkLoopSoundInstance(entity, ModSounds.DWARVEN_SPHERE_WALK);
                  ACTIVE.put(id, sound);
                  client.method_1483().method_4873(sound);
               }
            } else {
               DwemerSphereWalkLoopSoundInstance sound = (DwemerSphereWalkLoopSoundInstance)ACTIVE.remove(id);
               if (sound != null) {
                  sound.requestStop();
               }
            }
         }

         ACTIVE.int2ObjectEntrySet().removeIf(entry -> {
            DwemerSphereWalkLoopSoundInstance snd = (DwemerSphereWalkLoopSoundInstance)entry.getValue();
            return snd == null || snd.method_4793();
         });
      } else {
         ACTIVE.clear();
      }
   }

   private static boolean isMovingHorizontally(DwemerSphereEntity e) {
      class_243 v = e.method_18798();
      return v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350 > 4.0E-4;
   }
}
