package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaAbyssalGearSweepVfxClient {
   private static final List<BerettaAbyssalGearSweepVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaAbyssalGearSweepVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaAbyssalGearSweepVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaAbyssalGearSweepVfxClient.Cast(new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
            if (CASTS.size() > 5) {
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
            Iterator<BerettaAbyssalGearSweepVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaAbyssalGearSweepVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 10.0F) {
                  iterator.remove();
               } else {
                  Vec3 floor = cast.anchor.add(0.0, 0.055, 0.0);
                  Vec3 beamCore = cast.anchor.add(0.0, 0.6, 0.0);
                  Vec3 upperCore = cast.anchor.add(0.0, 1.45, 0.0);
                  float domain = BerettaVfxRenderHelper.envelope(age, 0.0F, cast.duration, 8.0F);
                  wrote |= BerettaVfxRenderHelper.disk(frame, floor, 0.8, 7.6, 72, 0.42F, 0.0F, 1.0F, domain * 0.09F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor.add(0.0, 0.02, 0.0), 4.8, age * 0.048, 20, 0.9F, 0.18F, 1.0F, domain * 0.8F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor.add(0.0, 0.035, 0.0), 6.8, -age * 0.03, 28, 0.66F, 0.04F, 1.0F, domain * 0.52F);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, upperCore, 1.02, age, cast.seed, domain * 0.92F);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, beamCore, 0.82, age, cast.seed + 71, domain * 0.84F);
                  wrote |= BerettaVfxRenderHelper.sparkField(frame, upperCore, 5.8, age, cast.seed + 130, 28, domain * 0.76F);
                  if (age >= 16.0F && age <= 146.0F) {
                     double warningRotation = BerettaCombat.gearRotation(cast.seed, age + 11.52);
                     float warning = age < 32.0F ? BerettaVfxRenderHelper.smooth((age - 16.0F) / 16.0F) : 0.4F;

                     for (int spoke = 0; spoke < 4; spoke++) {
                        double angle = warningRotation + (Math.PI * 2) * spoke / 4.0;
                        Vec3 direction = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
                        Vec3 start = beamCore.add(direction.scale(2.8));
                        Vec3 end = beamCore.add(direction.scale(25.5));
                        wrote |= BerettaVfxRenderHelper.warningLane(frame, start, end, 0.78, age, warning * 0.84F);
                     }
                  }

                  if (age >= 32.0F && age <= 146.0F) {
                     float active = BerettaVfxRenderHelper.envelope(age, 32.0F, 146.0F, 4.0F);
                     double rotation = BerettaCombat.gearRotation(cast.seed, age);

                     for (int spoke = 0; spoke < 4; spoke++) {
                        double angle = rotation + (Math.PI * 2) * spoke / 4.0;
                        Vec3 direction = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
                        Vec3 start = beamCore.add(direction.scale(2.8));
                        Vec3 end = beamCore.add(direction.scale(25.5));
                        wrote |= BerettaVfxRenderHelper.bloomBeam(frame, start, end, 0.74, age, cast.seed + spoke * 43, active * 0.94F);
                        wrote |= BerettaVfxRenderHelper.bloomBeam(
                           frame, start.add(0.0, 0.05, 0.0), end.add(0.0, 0.05, 0.0), 0.24, age, cast.seed + spoke * 47, active
                        );

                        for (int i = 2; i < 10; i += 2) {
                           Vec3 point = beamCore.add(direction.scale(i * 25.5 / 10.0));
                           wrote |= BerettaVfxRenderHelper.bloomOrb(frame, point, 0.22 + i * 0.014, age, cast.seed + spoke * 71 + i, active * 0.64F);
                        }
                     }

                     wrote |= BerettaVfxRenderHelper.horizontalRing(
                        frame, beamCore, 3.2 + Math.sin(age * 0.11) * 0.22, 0.12, 64, 1.0F, 0.7F, 1.0F, active * 0.84F
                     );
                     wrote |= BerettaVfxRenderHelper.horizontalRing(
                        frame, beamCore, 4.8 + Math.cos(age * 0.075) * 0.28, 0.09, 64, 0.72F, 0.06F, 1.0F, active * 0.58F
                     );
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
