package com.tensura_tno.client;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "tensura_tno", value = Dist.CLIENT)
public final class BossesRiseCinematicWatchdog {
   private static final String MOD_ID = "block_factorys_bosses";
   private static final String CAMERA_CLASS = "net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera";
   private static final int STUCK_TICKS_THRESHOLD = 700;
   private static final double MAX_TRACKED_DISTANCE = 80.0;
   private static volatile Class<?> cameraClass;
   private static volatile Method isCameraActiveMethod;
   private static volatile Method getTrackedObjectMethod;
   private static volatile Method stopCinematicCameraMethod;
   private static volatile boolean disabled = false;
   private static int cinematicTicks = 0;

   private BossesRiseCinematicWatchdog() {
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      if (!disabled) {
         if (!ModList.get().isLoaded("block_factorys_bosses")) {
            disabled = true;
         } else {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (mc.level != null && player != null) {
               String playerName = player.getGameProfile().getName();
               Vec3 playerPos = player.position();

               try {
                  ensureReflectionInitialized();
                  boolean active = (Boolean)isCameraActiveMethod.invoke(null);
                  if (!active) {
                     cinematicTicks = 0;
                     return;
                  }

                  cinematicTicks++;
                  if (cinematicTicks >= 700) {
                     forceStop(playerName, "cinematic camera stuck for " + cinematicTicks + " ticks (>= 700), forcing release");
                     return;
                  }

                  if (getTrackedObjectMethod.invoke(null) instanceof Entity entity) {
                     Vec3 entityPos = entity.position();
                     double dx = playerPos.x - entityPos.x;
                     double dz = playerPos.z - entityPos.z;
                     double horizontalSq = dx * dx + dz * dz;
                     if (horizontalSq > 6400.0) {
                        forceStop(
                           playerName, "player (" + String.format("%.1f", Math.sqrt(horizontalSq)) + " blocks from tracked target) > 80.0, forcing release"
                        );
                     }
                  }
               } catch (ReflectiveOperationException e) {
                  disabled = true;
                  cinematicTicks = 0;
                  TensuraTNOMod.LOGGER.error("[TensuraTNO] BossesRiseCinematicWatchdog disabled: reflection failed", e);
               }
            } else {
               cinematicTicks = 0;
            }
         }
      }
   }

   private static void ensureReflectionInitialized() throws ReflectiveOperationException {
      if (cameraClass == null) {
         Class<?> clazz = Class.forName("net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera");
         cameraClass = clazz;
         isCameraActiveMethod = clazz.getMethod("isCameraActive");
         getTrackedObjectMethod = clazz.getMethod("getTrackedObject");
         stopCinematicCameraMethod = clazz.getMethod("stopCinematicCamera");
      }
   }

   private static void forceStop(String playerName, String reason) {
      try {
         stopCinematicCameraMethod.invoke(null);
         cinematicTicks = 0;
         TensuraTNOMod.LOGGER.info("[TensuraTNO][BossesRiseWatchdog] {} (player={})", reason, playerName);
      } catch (ReflectiveOperationException e) {
         disabled = true;
         TensuraTNOMod.LOGGER.error("[TensuraTNO] BossesRiseCinematicWatchdog forceStop failed", e);
      }
   }
}
