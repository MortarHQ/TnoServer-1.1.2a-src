package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaReversedCrossVfxClient {
   private static final List<BerettaReversedCrossVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaReversedCrossVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaReversedCrossVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaReversedCrossVfxClient.Cast(new Vec3(x, y, z), flat(dirX, dirZ), duration, seed, minecraft.level.getGameTime()));
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
            Iterator<BerettaReversedCrossVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaReversedCrossVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 12.0F) {
                  iterator.remove();
               } else {
                  float domain = BerettaVfxRenderHelper.envelope(age, 0.0F, cast.duration, 10.0F);
                  Vec3 floor = cast.anchor.add(0.0, 0.055, 0.0);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor, 10.8, age * 0.025, 32, 0.92F, 0.16F, 1.0F, domain * 0.7F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor.add(0.0, 0.025, 0.0), 5.6, -age * 0.068, 24, 1.0F, 0.54F, 1.0F, domain * 0.82F);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, cast.anchor.add(0.0, 1.3, 0.0), 0.62, age, cast.seed, domain * 0.88F);

                  for (int i = 0; i < 8; i++) {
                     int fire = 34 + i * 9;
                     int reveal = fire - 20;
                     float echoStart = fire + 5.0F;
                     float end = fire + 9;
                     if (!(age < reveal) && !(age > Math.max(end, echoStart + 9.0F) + 2.0F)) {
                        Vec3 direction = BerettaCombat.riftDirection(cast.direction, cast.seed, i);
                        Vec3 center = BerettaCombat.riftCenter(cast.anchor, cast.direction, cast.seed, i);
                        Vec3 start = center.subtract(direction.scale(42.0)).add(0.0, 0.055, 0.0);
                        Vec3 finish = center.add(direction.scale(42.0)).add(0.0, 0.055, 0.0);
                        Vec3 echoCenter = BerettaCombat.riftEchoCenter(center, direction, i);
                        Vec3 echoStartPos = echoCenter.subtract(direction.scale(39.0)).add(0.0, 0.06, 0.0);
                        Vec3 echoEndPos = echoCenter.add(direction.scale(39.0)).add(0.0, 0.06, 0.0);
                        boolean finisher = i >= 6;
                        double width = finisher ? 2.05 : 1.36;
                        if (age < fire) {
                           float charge = BerettaVfxRenderHelper.smooth((age - reveal) / Math.max(1.0F, fire - reveal));
                           wrote |= BerettaVfxRenderHelper.warningLane(frame, start, finish, width, age + i, charge);
                           if (!finisher) {
                              wrote |= BerettaVfxRenderHelper.warningLane(frame, echoStartPos, echoEndPos, 0.92, age + 5.0F, charge * 0.66F);
                           }

                           wrote |= BerettaVfxRenderHelper.targetSeal(frame, center.add(0.0, 0.02, 0.0), 1.15 + i * 0.06, age, charge * 0.52F);
                        } else if (age <= end) {
                           float burst = BerettaVfxRenderHelper.envelope(age, fire, end, 1.6F);
                           wrote |= BerettaVfxRenderHelper.bloomBeam(
                              frame, start.add(0.0, 1.05, 0.0), finish.add(0.0, 1.05, 0.0), width, age, cast.seed + i * 53, burst
                           );
                           wrote |= BerettaVfxRenderHelper.impactBloom(
                              frame, center.add(0.0, 0.08, 0.0), finisher ? 1.65 : 1.0, age - fire, cast.seed + i * 61, burst * 0.7F
                           );
                        }

                        float echoEnd = echoStart + 9.0F;
                        if (!finisher && age >= echoStart && age <= echoEnd) {
                           float echo = BerettaVfxRenderHelper.envelope(age, echoStart, echoEnd, 1.5F);
                           wrote |= BerettaVfxRenderHelper.bloomBeam(
                              frame, echoStartPos.add(0.0, 1.05, 0.0), echoEndPos.add(0.0, 1.05, 0.0), 0.92, age, cast.seed + i * 71, echo * 0.88F
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
