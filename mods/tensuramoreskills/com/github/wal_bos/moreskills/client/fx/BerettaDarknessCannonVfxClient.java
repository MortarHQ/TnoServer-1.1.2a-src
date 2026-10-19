package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaDarknessCannonVfxClient {
   private static final List<BerettaDarknessCannonVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaDarknessCannonVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaDarknessCannonVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaDarknessCannonVfxClient.Cast(new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
            if (CASTS.size() > 12) {
               CASTS.remove(0);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (!CASTS.isEmpty()) {
         BerettaVfxRenderHelper.Frame frame = BerettaVfxRenderHelper.begin(event);
         if (frame != null) {
            boolean wrote = false;
            Iterator<BerettaDarknessCannonVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaDarknessCannonVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 8.0F) {
                  iterator.remove();
               } else {
                  float charge = BerettaVfxRenderHelper.smooth(age / 14.0F);
                  float impact = BerettaVfxRenderHelper.envelope(age, 14.0F, cast.duration, 2.0F);
                  Vec3 skyCore = cast.anchor.add(0.0, 17.0, 0.0);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, skyCore, 1.55, age, cast.seed, charge * 0.92F);
                  wrote |= BerettaVfxRenderHelper.sparkField(frame, skyCore, 7.0, age, cast.seed + 90, 24, charge * 0.86F);

                  for (int i = 0; i < 8; i++) {
                     Vec3 point = BerettaCombat.orbFieldPoint(cast.anchor, cast.seed, i);
                     boolean core = i == 7;
                     double radius = core ? 3.5 : (i < 5 ? 3.0 : 2.55);
                     Vec3 floor = point.add(0.0, 0.055, 0.0);
                     if (age <= 14.0F) {
                        wrote |= BerettaVfxRenderHelper.warningCircle(frame, floor, radius, age + i * 0.65F, charge);
                        Vec3 top = point.add(0.0, 16.0 + i * 0.35, 0.0);
                        wrote |= BerettaVfxRenderHelper.bloomBeam(frame, top, point.add(0.0, 0.35, 0.0), 0.16, age, cast.seed + i * 29, charge * 0.28F);
                        wrote |= BerettaVfxRenderHelper.bloomOrb(frame, top, core ? 0.78 : 0.52, age, cast.seed + i * 31, charge * 0.74F);
                     } else {
                        float local = age - 14.0F;
                        wrote |= BerettaVfxRenderHelper.verticalRay(frame, point.add(0.0, 0.22, 0.0), 18.0, core ? 0.72 : 0.48, age, cast.seed + i * 43, impact);
                        wrote |= BerettaVfxRenderHelper.impactBloom(frame, floor, radius, local, cast.seed + i * 61, impact);
                     }
                  }
               }
            }

            BerettaVfxRenderHelper.end(frame, wrote);
         }
      }
   }

   private record Cast(Vec3 anchor, int duration, int seed, long startTick) {
   }
}
