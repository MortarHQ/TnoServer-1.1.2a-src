package com.github.wal_bos.moreskills.client.fx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaChaosCoreExposureVfxClient {
   private static final List<BerettaChaosCoreExposureVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaChaosCoreExposureVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaChaosCoreExposureVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaChaosCoreExposureVfxClient.Cast(bossId, new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
            if (CASTS.size() > 4) {
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
            Iterator<BerettaChaosCoreExposureVfxClient.Cast> iterator = CASTS.iterator();
            Minecraft minecraft = Minecraft.getInstance();

            while (iterator.hasNext()) {
               BerettaChaosCoreExposureVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 10.0F) {
                  iterator.remove();
               } else {
                  Vec3 center = cast.anchor;
                  Entity boss = minecraft.level == null ? null : minecraft.level.getEntity(cast.bossId);
                  if (boss != null) {
                     center = boss.position();
                  }

                  Vec3 core = center.add(0.0, 2.15, 0.0);
                  float life = BerettaVfxRenderHelper.envelope(age, 0.0F, cast.duration, 10.0F);
                  float warning = BerettaVfxRenderHelper.pulse(age, cast.duration - 18.0F, 18.0F);
                  wrote |= BerettaVfxRenderHelper.crossGlow(frame, core, 8.5, 0.36F, 0.0F, 0.88F, life * 0.12F);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, core, 2.2, age, cast.seed, life);
                  wrote |= BerettaVfxRenderHelper.sparkField(frame, core, 8.0, age, cast.seed + 400, 44, life * 0.92F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, center.add(0.0, 0.08, 0.0), 5.4, age * 0.062, 24, 0.94F, 0.22F, 1.0F, life * 0.92F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, center.add(0.0, 0.11, 0.0), 7.2, -age * 0.038, 30, 0.66F, 0.04F, 1.0F, life * 0.62F);
                  wrote |= BerettaVfxRenderHelper.horizontalRing(
                     frame, center.add(0.0, 0.16, 0.0), 4.4 + Math.sin(age * 0.16) * 0.46, 0.16, 72, 1.0F, 0.62F, 1.0F, life
                  );
                  wrote |= BerettaVfxRenderHelper.horizontalRing(
                     frame, center.add(0.0, 0.2, 0.0), 6.2 + Math.cos(age * 0.11) * 0.52, 0.12, 72, 0.74F, 0.06F, 1.0F, life * 0.74F
                  );

                  for (int i = 0; i < 16; i++) {
                     double angle = age * (0.042 + i % 4 * 0.004) + (Math.PI * 2) * i / 16.0;
                     double radius = 3.2 + i % 4 * 0.72;
                     Vec3 point = core.add(Math.cos(angle) * radius, Math.sin(age * 0.09 + i) * 1.55, Math.sin(angle) * radius);
                     wrote |= BerettaVfxRenderHelper.shard(frame, point, point.subtract(core), 0.9 + i % 3 * 0.28, 0.06, 0.94F, 0.26F, 1.0F, life * 0.92F);
                     if ((i & 3) == 0) {
                        wrote |= BerettaVfxRenderHelper.bloomBeam(frame, point, core, 0.055, age, cast.seed + i * 37, life * 0.3F);
                     }
                  }

                  if (warning > 0.001F) {
                     wrote |= BerettaVfxRenderHelper.warningCircle(frame, center.add(0.0, 0.055, 0.0), 8.0, age, warning);
                     wrote |= BerettaVfxRenderHelper.crossGlow(frame, core, 4.5 + warning * 3.0, 1.0F, 0.58F, 1.0F, warning * 0.32F);
                  }
               }
            }

            BerettaVfxRenderHelper.end(frame, wrote);
         }
      }
   }

   private record Cast(int bossId, Vec3 anchor, int duration, int seed, long startTick) {
   }
}
