package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaChaosRingsVfxClient {
   private static final List<BerettaChaosRingsVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaChaosRingsVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaChaosRingsVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaChaosRingsVfxClient.Cast(new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
            if (CASTS.size() > 16) {
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
            Iterator<BerettaChaosRingsVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaChaosRingsVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 6.0F) {
                  iterator.remove();
               } else {
                  Vec3 center = cast.anchor.add(0.0, 0.36, 0.0);
                  if (age < 12.0F) {
                     float charge = BerettaVfxRenderHelper.smooth(age / 12.0F);
                     wrote |= BerettaVfxRenderHelper.bloomOrb(frame, cast.anchor.add(0.0, 1.25, 0.0), 1.12, age, cast.seed, charge);
                     wrote |= BerettaVfxRenderHelper.warningCircle(frame, cast.anchor.add(0.0, 0.055, 0.0), 2.3, age, charge);
                     wrote |= BerettaVfxRenderHelper.runeRing(frame, cast.anchor.add(0.0, 0.08, 0.0), 4.6, age * 0.09, 20, 0.92F, 0.22F, 1.0F, charge * 0.86F);
                  } else {
                     float activeAge = age - 12.0F;
                     if (activeAge <= 42.0F) {
                        double radius = BerettaCombat.expandingRingRadius(activeAge);
                        float life = BerettaVfxRenderHelper.envelope(activeAge, 0.0F, 42.0F, 3.0F);
                        wrote |= BerettaVfxRenderHelper.horizontalRing(frame, center, radius, 0.78, 96, 0.18F, 0.0F, 0.42F, life * 0.3F);
                        wrote |= BerettaVfxRenderHelper.horizontalRing(frame, center.add(0.0, 0.035, 0.0), radius, 0.34, 96, 0.76F, 0.06F, 1.0F, life * 0.88F);
                        wrote |= BerettaVfxRenderHelper.horizontalRing(frame, center.add(0.0, 0.07, 0.0), radius, 0.08, 96, 1.0F, 0.72F, 1.0F, life);
                        wrote |= BerettaVfxRenderHelper.disk(
                           frame, cast.anchor.add(0.0, 0.04, 0.0), Math.max(0.0, radius - 0.82), radius + 0.82, 96, 0.54F, 0.0F, 1.0F, life * 0.1F
                        );
                        wrote |= BerettaVfxRenderHelper.bloomOrb(frame, cast.anchor.add(0.0, 1.05, 0.0), 0.84, activeAge, cast.seed + 17, life * 0.84F);

                        for (int i = 0; i < 20; i++) {
                           double angle = (Math.PI * 2) * i / 20.0 + activeAge * 0.048;
                           Vec3 point = center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
                           Vec3 direction = new Vec3(Math.cos(angle), 0.45 + i % 4 * 0.12, Math.sin(angle));
                           wrote |= BerettaVfxRenderHelper.shard(frame, point, direction, 0.75 + i % 3 * 0.22, 0.055, 0.96F, 0.28F, 1.0F, life * 0.76F);
                           if ((i & 3) == 0) {
                              wrote |= BerettaVfxRenderHelper.crossGlow(frame, point, 0.42, 0.8F, 0.08F, 1.0F, life * 0.32F);
                           }
                        }
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
