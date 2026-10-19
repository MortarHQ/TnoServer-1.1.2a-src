package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaLowVoidLaserVfxClient {
   private static final List<BerettaLowVoidLaserVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaLowVoidLaserVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaLowVoidLaserVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaLowVoidLaserVfxClient.Cast(new Vec3(x, y, z), flat(dirX, dirZ), duration, seed, minecraft.level.getGameTime()));
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
            Iterator<BerettaLowVoidLaserVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaLowVoidLaserVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 12.0F) {
                  iterator.remove();
               } else {
                  float domain = BerettaVfxRenderHelper.envelope(age, 0.0F, cast.duration, 10.0F);
                  Vec3 floor = cast.anchor.add(0.0, 0.055, 0.0);
                  Vec3 core = cast.anchor.add(0.0, 1.55, 0.0);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor, 8.4, age * 0.03, 28, 0.92F, 0.16F, 1.0F, domain * 0.72F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor.add(0.0, 0.025, 0.0), 4.4, -age * 0.055, 20, 1.0F, 0.54F, 1.0F, domain * 0.82F);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, core, 0.82, age, cast.seed, domain * 0.92F);
                  wrote |= BerettaVfxRenderHelper.sparkField(frame, core, 6.8, age, cast.seed + 80, 24, domain * 0.72F);

                  for (int i = 0; i < 7; i++) {
                     int fire = 32 + i * 10;
                     int reveal = fire - 22;
                     int end = fire + 10;
                     if (!(age < reveal) && !(age > end + 7.0F)) {
                        Vec3 direction = BerettaCombat.lowLaserDirection(cast.direction, cast.seed, i);
                        Vec3 center = BerettaCombat.lowLaserCenter(cast.anchor, cast.direction, cast.seed, i);
                        double halfLength = 42.0;
                        double width = i == 6 ? 2.05 : (i >= 4 ? 1.58 : 1.34);
                        Vec3 floorStart = center.subtract(direction.scale(halfLength)).add(0.0, 0.055, 0.0);
                        Vec3 floorEnd = center.add(direction.scale(halfLength)).add(0.0, 0.055, 0.0);
                        Vec3 echoCenter = BerettaCombat.lowLaserEchoCenter(center, direction, i);
                        Vec3 echoStart = echoCenter.subtract(direction.scale(40.0)).add(0.0, 0.06, 0.0);
                        Vec3 echoEnd = echoCenter.add(direction.scale(40.0)).add(0.0, 0.06, 0.0);
                        if (age < fire) {
                           float charge = BerettaVfxRenderHelper.smooth((age - reveal) / Math.max(1.0F, fire - reveal));
                           wrote |= BerettaVfxRenderHelper.warningLane(frame, floorStart, floorEnd, width, age + i * 0.8F, charge);
                           if (i < 6) {
                              wrote |= BerettaVfxRenderHelper.warningLane(frame, echoStart, echoEnd, 0.98, age + 6.0F, charge * 0.68F);
                           }

                           float prefire = BerettaVfxRenderHelper.pulse(age, fire - 2.0F, 4.5F);
                           wrote |= BerettaVfxRenderHelper.bloomOrb(
                              frame, center.add(0.0, 1.05, 0.0), 0.34 + charge * 0.28, age, cast.seed + i * 31, prefire * 0.82F
                           );
                        } else if (age <= end) {
                           float burst = BerettaVfxRenderHelper.envelope(age, fire, end, 1.7F);
                           Vec3 start = floorStart.add(0.0, 1.0, 0.0);
                           Vec3 finish = floorEnd.add(0.0, 1.0, 0.0);
                           wrote |= BerettaVfxRenderHelper.bloomBeam(frame, start, finish, width, age, cast.seed + i * 37, burst);
                           wrote |= BerettaVfxRenderHelper.shockwaveBloom(
                              frame, center.add(0.0, 0.08, 0.0), 1.4 + (age - fire) * 0.36, age - fire, cast.seed + i * 41, burst * 0.64F
                           );
                        }

                        float echoStartAge = fire + 5.0F;
                        float echoEndAge = echoStartAge + 10.0F;
                        if (i < 6 && age >= echoStartAge && age <= echoEndAge) {
                           float echo = BerettaVfxRenderHelper.envelope(age, echoStartAge, echoEndAge, 1.5F);
                           wrote |= BerettaVfxRenderHelper.bloomBeam(
                              frame, echoStart.add(0.0, 1.0, 0.0), echoEnd.add(0.0, 1.0, 0.0), 0.98, age, cast.seed + i * 59, echo * 0.9F
                           );
                        }
                     }
                  }
               }
            }

            BerettaVfxRenderHelper.end(frame, wrote);
         }
      }
   }

   private static Vec3 flat(double x, double z) {
      Vec3 value = new Vec3(x, 0.0, z);
      return value.lengthSqr() < 1.0E-4 ? new Vec3(0.0, 0.0, 1.0) : value.normalize();
   }

   private record Cast(Vec3 anchor, Vec3 direction, int duration, int seed, long startTick) {
   }
}
