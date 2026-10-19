package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaOrbCascadeVfxClient {
   private static final List<BerettaOrbCascadeVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaOrbCascadeVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaOrbCascadeVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaOrbCascadeVfxClient.Cast(new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
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
            Iterator<BerettaOrbCascadeVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaOrbCascadeVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 14.0F) {
                  iterator.remove();
               } else {
                  Vec3 ground = cast.anchor;
                  Vec3 core = ground.add(0.0, 2.75, 0.0);
                  float domain = BerettaVfxRenderHelper.envelope(age, 0.0F, cast.duration, 10.0F);
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, core, 1.56, age, cast.seed, domain);
                  wrote |= BerettaVfxRenderHelper.crossGlow(frame, core, 5.6, 0.95F, 0.18F, 1.0F, domain * 0.68F);
                  wrote |= BerettaVfxRenderHelper.sparkField(frame, core, 8.6, age, cast.seed + 300, 46, domain * 0.92F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, ground.add(0.0, 0.07, 0.0), 7.8, -age * 0.062, 30, 0.96F, 0.16F, 1.0F, domain * 0.86F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, ground.add(0.0, 0.09, 0.0), 10.8, age * 0.041, 40, 0.58F, 0.02F, 1.0F, domain * 0.44F);

                  for (int arm = 0; arm < 5; arm++) {
                     double angle = cast.seed * 1.3E-4 + arm * Math.PI * 2.0 / 5.0 + age * (arm % 2 == 0 ? 0.052 : -0.052);
                     Vec3 node = core.add(Math.cos(angle) * 3.8, Math.sin(age * 0.12 + arm) * 0.72, Math.sin(angle) * 3.8);
                     wrote |= BerettaVfxRenderHelper.bloomOrb(frame, node, 0.54, age, cast.seed + arm * 71, domain * 0.84F);
                     wrote |= BerettaVfxRenderHelper.bloomBeam(frame, core, node, 0.09, age, cast.seed + arm * 83, domain * 0.38F);
                  }

                  for (int i = 0; i < 40; i++) {
                     int launch = 34 + i * 2;
                     int reveal = launch - 20;
                     int impact = launch + 34;
                     if (!(age < reveal) && !(age > impact + 12.0F)) {
                        boolean heavy = i % 5 == 4;
                        boolean finisher = i >= 35;
                        Vec3 endpoint = BerettaCombat.spiralOrbEndpoint(ground, cast.seed, i);
                        if (!(age < launch)) {
                           if (age <= impact) {
                              float orbAge = age - launch;
                              Vec3 current = BerettaCombat.spiralOrbPosition(ground, cast.seed, i, orbAge);
                              Vec3 previous = BerettaCombat.spiralOrbPosition(ground, cast.seed, i, Math.max(0.0F, orbAge - 2.8F));
                              Vec3 older = BerettaCombat.spiralOrbPosition(ground, cast.seed, i, Math.max(0.0F, orbAge - 6.8F));
                              Vec3 oldest = BerettaCombat.spiralOrbPosition(ground, cast.seed, i, Math.max(0.0F, orbAge - 11.2F));
                              float fade = BerettaVfxRenderHelper.envelope(orbAge, 0.0F, 34.0F, 3.2F);
                              double radius = finisher ? 1.08 : (heavy ? 0.94 : 0.64);
                              wrote |= BerettaVfxRenderHelper.bloomBeam(frame, oldest, older, radius * 0.32, age, cast.seed + i * 31, fade * 0.24F);
                              wrote |= BerettaVfxRenderHelper.bloomBeam(frame, older, previous, radius * 0.5, age, cast.seed + i * 37, fade * 0.5F);
                              wrote |= BerettaVfxRenderHelper.bloomBeam(frame, previous, current, radius * 0.78, age, cast.seed + i * 43, fade * 0.92F);
                              wrote |= BerettaVfxRenderHelper.bloomOrb(frame, current, radius, age, cast.seed + i * 53, fade);
                              wrote |= BerettaVfxRenderHelper.crossGlow(frame, current, radius * 2.8, 1.0F, 0.36F, 1.0F, fade * 0.64F);
                              if ((i + (int)orbAge) % 4 == 0) {
                                 wrote |= BerettaVfxRenderHelper.sparkField(frame, current, radius * 2.8, age, cast.seed + i * 67, 6, fade * 0.76F);
                              }
                           } else {
                              float impactAge = age - impact;
                              float burst = BerettaVfxRenderHelper.envelope(impactAge, 0.0F, 11.0F, 1.4F);
                              if (!(burst <= 0.001F)) {
                                 double radius = finisher ? 4.7 : (heavy ? 3.9 : 2.85);
                                 wrote |= BerettaVfxRenderHelper.impactBloom(
                                    frame, endpoint, finisher ? 2.9 : (heavy ? 2.35 : 1.65), impactAge, cast.seed + i * 79, burst
                                 );
                                 wrote |= BerettaVfxRenderHelper.shockwaveBloom(
                                    frame, endpoint.add(0.0, 0.05, 0.0), radius * (0.55 + impactAge * 0.08), impactAge, cast.seed + i * 83, burst * 0.92F
                                 );
                                 wrote |= BerettaVfxRenderHelper.verticalRay(
                                    frame,
                                    endpoint.add(0.0, 0.04, 0.0),
                                    finisher ? 14.0 : (heavy ? 10.0 : 7.0),
                                    finisher ? 0.58 : 0.38,
                                    age,
                                    cast.seed + i * 89,
                                    burst * 0.88F
                                 );
                                 wrote |= BerettaVfxRenderHelper.sparkField(
                                    frame, endpoint.add(0.0, 0.8, 0.0), radius * 1.25, age, cast.seed + i * 97, finisher ? 24 : (heavy ? 18 : 12), burst
                                 );
                              }
                           }
                        } else {
                           float charge = BerettaVfxRenderHelper.smooth((age - reveal) / Math.max(1.0F, launch - reveal));
                           Vec3 previous = BerettaCombat.spiralOrbPosition(ground, cast.seed, i, 0.0);
                           int segments = 9;

                           for (int segment = 1; segment <= segments; segment++) {
                              double sampleAge = (double)(34 * segment) / segments;
                              Vec3 next = BerettaCombat.spiralOrbPosition(ground, cast.seed, i, sampleAge);
                              float segmentAlpha = charge * (0.34F + segment * 0.065F);
                              wrote |= BerettaVfxRenderHelper.warningLane(
                                 frame, previous, next, finisher ? 0.28 : (heavy ? 0.22 : 0.15), age + segment * 0.8F, segmentAlpha
                              );
                              previous = next;
                           }

                           double warningRadius = finisher ? 4.7 : (heavy ? 3.9 : 2.85);
                           wrote |= BerettaVfxRenderHelper.warningCircle(frame, endpoint.add(0.0, 0.035, 0.0), warningRadius, age, charge * 0.92F);
                           wrote |= BerettaVfxRenderHelper.targetSeal(frame, endpoint.add(0.0, 0.05, 0.0), warningRadius * 0.72, age * 1.12F, charge * 0.72F);
                           wrote |= BerettaVfxRenderHelper.verticalRay(
                              frame,
                              endpoint.add(0.0, 0.04, 0.0),
                              finisher ? 11.0 : (heavy ? 8.0 : 5.6),
                              finisher ? 0.24 : 0.15,
                              age,
                              cast.seed + i * 17,
                              charge * 0.42F
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

   private record Cast(Vec3 anchor, int duration, int seed, long startTick) {
   }
}
