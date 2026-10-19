package com.github.wal_bos.moreskills.client.fx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaMineralEruptionVfxClient {
   private static final List<BerettaMineralEruptionVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaMineralEruptionVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaMineralEruptionVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaMineralEruptionVfxClient.Cast(new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
            if (CASTS.size() > 18) {
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
            Iterator<BerettaMineralEruptionVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaMineralEruptionVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 6.0F) {
                  iterator.remove();
               } else {
                  Vec3 floor = cast.anchor.add(0.0, 0.05, 0.0);
                  float charge = BerettaVfxRenderHelper.smooth(age / 12.0F);
                  wrote |= BerettaVfxRenderHelper.warningCircle(frame, floor, 2.75, age, charge);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor.add(0.0, 0.02, 0.0), 1.75, -age * 0.08, 12, 0.9F, 0.2F, 1.0F, charge * 0.84F);
                  wrote |= BerettaVfxRenderHelper.sparkField(frame, floor.add(0.0, 1.2, 0.0), 2.8, age, cast.seed, 16, charge * 0.72F);
                  if (age >= 12.0F) {
                     float active = BerettaVfxRenderHelper.envelope(age, 12.0F, cast.duration, 2.0F);
                     float local = age - 12.0F;
                     wrote |= BerettaVfxRenderHelper.impactBloom(frame, floor, 2.35, local, cast.seed, active);

                     for (int i = 0; i < 9; i++) {
                        double angle = (Math.PI * 2) * i / 9.0 + cast.seed * 2.1E-4;
                        double radius = i == 0 ? 0.0 : 0.55 + i % 3 * 0.48;
                        Vec3 base = floor.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
                        double height = 3.4 + i % 4 * 1.0 + Math.sin(local * 0.22 + i) * 0.32;
                        wrote |= BerettaVfxRenderHelper.pillar(frame, base, height, 0.16 + i % 3 * 0.045, 0.72F, 0.06F, 1.0F, active * 0.82F);
                        wrote |= BerettaVfxRenderHelper.shard(
                           frame,
                           base.add(0.0, height * 0.48, 0.0),
                           new Vec3(Math.cos(angle), 1.45, Math.sin(angle)),
                           1.3 + i % 3 * 0.38,
                           0.08,
                           1.0F,
                           0.42F,
                           1.0F,
                           active
                        );
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
