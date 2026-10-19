package com.github.wal_bos.moreskills.client.fx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaCameraShakeClient {
   private static final List<BerettaCameraShakeClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaCameraShakeClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaCameraShakeClient::onCameraAngles);
      }
   }

   public static void add(int attackId, double x, double y, double z, int duration) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null) {
         CASTS.add(new BerettaCameraShakeClient.Cast(attackId, new Vec3(x, y, z), duration, minecraft.level.getGameTime()));
         if (CASTS.size() > 20) {
            CASTS.remove(0);
         }
      }
   }

   private static void onCameraAngles(ComputeCameraAngles event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         float best = 0.0F;
         float ageBest = 0.0F;
         Iterator<BerettaCameraShakeClient.Cast> iterator = CASTS.iterator();

         while (iterator.hasNext()) {
            BerettaCameraShakeClient.Cast cast = iterator.next();
            float age = (float)(minecraft.level.getGameTime() - cast.startTick) + (float)event.getPartialTick();
            if (age > cast.duration + 10.0F) {
               iterator.remove();
            } else {
               double distance = minecraft.player.position().distanceTo(cast.origin);
               float distanceFade = (float)Mth.clamp(1.0 - distance / 92.0, 0.0, 1.0);
               float intensity = intensity(cast, age) * distanceFade;
               if (intensity > best) {
                  best = intensity;
                  ageBest = age;
               }
            }
         }

         if (!(best <= 0.001F)) {
            event.setPitch(event.getPitch() + Mth.sin(ageBest * 2.7F) * best * 0.82F + Mth.sin(ageBest * 5.1F) * best * 0.34F);
            event.setYaw(event.getYaw() + Mth.cos(ageBest * 2.4F) * best * 0.72F + Mth.sin(ageBest * 4.7F) * best * 0.28F);
            event.setRoll(event.getRoll() + Mth.sin(ageBest * 3.1F) * best * 1.02F);
         }
      } else {
         CASTS.clear();
      }
   }

   private static float intensity(BerettaCameraShakeClient.Cast cast, float age) {
      int id = cast.attackId;
      if (id == 4 && cast.duration <= 32) {
         return pulse(age, 12.0F, 4.0F, 0.34F);
      }

      if (id == 3 && cast.duration <= 32) {
         return pulse(age, 14.0F, 5.0F, 0.42F);
      }

      if (id == 6 && cast.duration <= 64) {
         return pulse(age, 12.0F, 5.0F, 0.34F);
      }

      if (id == 1) {
         float value = 0.0F;

         for (int i = 0; i < 7; i++) {
            value = Math.max(value, pulse(age, 32 + i * 10, 4.0F, 0.42F));
         }

         return value;
      } else if (id == 2) {
         float value = 0.0F;

         for (int i = 0; i < 40; i += 3) {
            value = Math.max(value, pulse(age, 34 + i * 2, 3.0F, 0.18F));
         }

         return value;
      } else if (id == 5) {
         float value = 0.0F;

         for (int i = 0; i < 8; i++) {
            value = Math.max(value, pulse(age, 34 + i * 9, 4.0F, 0.48F));
         }

         return value;
      } else if (id == 7) {
         float value = 0.0F;

         for (int i = 0; i < 5; i++) {
            value = Math.max(value, pulse(age, 36 + i * 18, 4.0F, 0.46F));
         }

         return value;
      } else if (id != 8) {
         if (id == 9) {
            return window(age, 32.0F, 146.0F, 0.22F);
         } else {
            return id == 10 ? pulse(age, 1.0F, 16.0F, 0.28F) : 0.0F;
         }
      } else {
         float value = 0.0F;

         for (int i = 0; i < 3; i++) {
            value = Math.max(value, pulse(age, 32 + i * 34, 4.0F, 0.44F));
         }

         return value;
      }
   }

   private static float window(float age, float start, float end, float strength) {
      return BerettaVfxRenderHelper.envelope(age, start, end, 3.0F) * strength;
   }

   private static float pulse(float age, float center, float width, float strength) {
      return BerettaVfxRenderHelper.pulse(age, center, width) * strength;
   }

   private record Cast(int attackId, Vec3 origin, int duration, long startTick) {
   }
}
