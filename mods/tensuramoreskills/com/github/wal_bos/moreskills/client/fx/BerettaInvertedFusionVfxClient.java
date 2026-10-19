package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaInvertedFusionVfxClient {
   private static final List<BerettaInvertedFusionVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaInvertedFusionVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaInvertedFusionVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaInvertedFusionVfxClient.Cast(bossId, targetId, new Vec3(x, y, z), duration, seed, minecraft.level.getGameTime()));
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
            Iterator<BerettaInvertedFusionVfxClient.Cast> iterator = CASTS.iterator();
            Minecraft minecraft = Minecraft.getInstance();

            while (iterator.hasNext()) {
               BerettaInvertedFusionVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 16.0F) {
                  iterator.remove();
               } else {
                  Entity bossEntity = minecraft.level == null ? null : minecraft.level.getEntity(cast.bossId);
                  Entity targetEntity = minecraft.level == null ? null : minecraft.level.getEntity(cast.targetId);
                  Vec3 bossPosition = bossEntity == null ? cast.anchor : bossEntity.position();
                  Vec3 core = bossPosition.add(0.0, bossEntity == null ? 2.0 : bossEntity.getBbHeight() * 0.56, 0.0);
                  float shield = BerettaVfxRenderHelper.envelope(age, 4.0F, 124.0F, 7.0F);
                  if (shield > 0.001F) {
                     wrote |= BerettaVfxRenderHelper.crossGlow(frame, core, 6.8, 0.22F, 0.0F, 0.68F, shield * 0.14F);
                     wrote |= BerettaVfxRenderHelper.orb(frame, core, 4.7, 0.14F, 0.0F, 0.38F, shield * 0.3F);
                     wrote |= BerettaVfxRenderHelper.orb(frame, core, 4.0, 0.86F, 0.08F, 1.0F, shield * 0.82F);
                     wrote |= BerettaVfxRenderHelper.bloomOrb(frame, core, 1.08, age, cast.seed, shield);
                     wrote |= BerettaVfxRenderHelper.runeRing(frame, bossPosition.add(0.0, 0.08, 0.0), 6.3, age * 0.076, 28, 0.94F, 0.22F, 1.0F, shield * 0.78F);
                     wrote |= BerettaVfxRenderHelper.sparkField(frame, core, 5.6, age, cast.seed + 30, 30, shield * 0.82F);
                  }

                  for (int dash = 0; dash < 3; dash++) {
                     int dashStart = 32 + dash * 34;
                     int reveal = dashStart - 18;
                     int dashEnd = dashStart + 11 - 1;
                     if (age >= reveal && cast.dashStarts[dash] == null) {
                        cast.dashStarts[dash] = bossPosition;
                     }

                     if (age >= reveal && cast.dashEnds[dash] == null) {
                        cast.dashEnds[dash] = cast.predictedEndpoint(targetEntity, bossPosition, dash);
                     }

                     Vec3 dashOrigin = cast.dashStarts[dash] == null ? bossPosition : cast.dashStarts[dash];
                     Vec3 dashEndPoint = cast.dashEnds[dash] == null ? cast.predictedEndpoint(targetEntity, bossPosition, dash) : cast.dashEnds[dash];
                     if (age >= reveal && age < dashStart) {
                        float charge = BerettaVfxRenderHelper.smooth((age - reveal) / Math.max(1.0F, dashStart - reveal));
                        Vec3 start = dashOrigin.add(0.0, 1.0, 0.0);
                        Vec3 end = dashEndPoint.add(0.0, 1.0, 0.0);
                        wrote |= BerettaVfxRenderHelper.warningLane(frame, start, end, 1.72, age, charge * 0.94F);
                        wrote |= BerettaVfxRenderHelper.targetSeal(frame, dashEndPoint.add(0.0, 0.055, 0.0), dash == 2 ? 4.2 : 3.5, age, charge * 0.9F);
                        wrote |= BerettaVfxRenderHelper.verticalRay(
                           frame, dashEndPoint.add(0.0, 0.06, 0.0), 9.5, 0.2 + charge * 0.15, age, cast.seed + dash * 101, charge * 0.64F
                        );

                        for (int marker = 1; marker <= 5; marker++) {
                           double progress = marker / 6.0;
                           Vec3 point = start.add(end.subtract(start).scale(progress));
                           wrote |= BerettaVfxRenderHelper.bloomOrb(
                              frame, point, 0.2 + charge * 0.08, age + marker, cast.seed + dash * 131 + marker, charge * 0.46F
                           );
                        }
                     }

                     if (age >= dashStart && age <= dashEnd + 1.0F) {
                        cast.sampleTrail(dash, bossPosition);
                        float active = BerettaVfxRenderHelper.envelope(age, dashStart, dashEnd + 1.0F, 1.6F);
                        wrote |= cast.renderTrail(frame, dash, age, active);
                        wrote |= BerettaVfxRenderHelper.bloomOrb(frame, core, 1.42, age, cast.seed + dash * 173, active);
                        wrote |= BerettaVfxRenderHelper.crossGlow(frame, core, 5.2, 1.0F, 0.3F, 1.0F, active * 0.72F);
                        wrote |= BerettaVfxRenderHelper.sparkField(frame, core, 3.8, age, cast.seed + dash * 181, 18, active * 0.8F);
                     }

                     for (int orb = 0; orb < 4; orb++) {
                        int spawn = dashStart + 1 + orb * 2;
                        int explode = spawn + 15;
                        if (age >= spawn && cast.orbCenters[dash][orb] == null) {
                           cast.orbCenters[dash][orb] = bossPosition.add(0.0, 0.82 + orb % 2 * 0.16, 0.0);
                        }

                        Vec3 orbCenter = cast.orbCenters[dash][orb];
                        if (orbCenter != null) {
                           if (age < explode) {
                              float charge = BerettaVfxRenderHelper.smooth((age - spawn) / Math.max(1.0F, explode - spawn));
                              float pulse = 0.72F + charge * 0.42F;
                              wrote |= BerettaVfxRenderHelper.bloomOrb(
                                 frame, orbCenter, 0.58 * pulse, age, cast.seed + dash * 211 + orb * 19, 0.76F + charge * 0.24F
                              );
                              wrote |= BerettaVfxRenderHelper.horizontalRing(frame, orbCenter, 0.82 + charge * 0.58, 0.06, 32, 1.0F, 0.34F, 1.0F, 0.72F);
                              wrote |= BerettaVfxRenderHelper.sparkField(frame, orbCenter, 1.6 + charge * 0.8, age, cast.seed + dash * 223 + orb * 23, 8, 0.72F);
                           } else {
                              float impactAge = age - explode;
                              float burst = BerettaVfxRenderHelper.envelope(impactAge, 0.0F, 10.0F, 1.5F);
                              if (!(burst <= 0.001F)) {
                                 boolean finisher = dash == 2 && orb == 3;
                                 double radius = finisher ? 2.8 : 2.0;
                                 wrote |= BerettaVfxRenderHelper.impactBloom(frame, orbCenter, radius, impactAge, cast.seed + dash * 241 + orb * 29, burst);
                                 wrote |= BerettaVfxRenderHelper.shockwaveBloom(
                                    frame,
                                    orbCenter.add(0.0, 0.04, 0.0),
                                    (finisher ? 3.2 : 2.4) + impactAge * 0.26,
                                    impactAge,
                                    cast.seed + dash * 251 + orb * 31,
                                    burst * 0.9F
                                 );
                                 wrote |= BerettaVfxRenderHelper.verticalRay(
                                    frame,
                                    orbCenter.add(0.0, 0.04, 0.0),
                                    finisher ? 12.0 : 8.0,
                                    finisher ? 0.52 : 0.34,
                                    age,
                                    cast.seed + dash * 263 + orb * 37,
                                    burst * 0.84F
                                 );
                                 wrote |= BerettaVfxRenderHelper.sparkField(
                                    frame, orbCenter, finisher ? 5.2 : 3.8, age, cast.seed + dash * 271 + orb * 41, finisher ? 26 : 18, burst
                                 );
                              }
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

   private static final class Cast {
      private final int bossId;
      private final int targetId;
      private final Vec3 anchor;
      private final int duration;
      private final int seed;
      private final long startTick;
      private final Vec3[] dashStarts = new Vec3[3];
      private final Vec3[] dashEnds = new Vec3[3];
      private final Vec3[][] orbCenters = new Vec3[3][4];
      private final List<List<Vec3>> dashTrails = new ArrayList<>();

      private Cast(int bossId, int targetId, Vec3 anchor, int duration, int seed, long startTick) {
         this.bossId = bossId;
         this.targetId = targetId;
         this.anchor = anchor;
         this.duration = duration;
         this.seed = seed;
         this.startTick = startTick;

         for (int i = 0; i < 3; i++) {
            this.dashTrails.add(new ArrayList<>());
         }
      }

      private void sampleTrail(int dash, Vec3 position) {
         List<Vec3> trail = this.dashTrails.get(dash);
         if (trail.isEmpty() || trail.get(trail.size() - 1).distanceToSqr(position) > 0.018) {
            trail.add(position);
         }

         while (trail.size() > 18) {
            trail.remove(0);
         }
      }

      private boolean renderTrail(BerettaVfxRenderHelper.Frame frame, int dash, float age, float alpha) {
         List<Vec3> trail = this.dashTrails.get(dash);
         if (trail.size() >= 2 && !(alpha <= 0.001F)) {
            boolean wrote = false;

            for (int i = 1; i < trail.size(); i++) {
               float progress = (float)i / (trail.size() - 1);
               Vec3 start = trail.get(i - 1).add(0.0, 1.55, 0.0);
               Vec3 end = trail.get(i).add(0.0, 1.55, 0.0);
               wrote |= BerettaVfxRenderHelper.bloomBeam(
                  frame, start, end, 0.16 + progress * 0.34, age, this.seed + dash * 307 + i * 17, alpha * progress * 0.82F
               );
               if ((i & 2) == 0) {
                  wrote |= BerettaVfxRenderHelper.bloomOrb(frame, end, 0.2 + progress * 0.18, age, this.seed + dash * 311 + i * 23, alpha * progress * 0.62F);
               }
            }

            return wrote;
         } else {
            return false;
         }
      }

      private Vec3 predictedEndpoint(Entity targetEntity, Vec3 bossPosition, int dash) {
         if (targetEntity instanceof LivingEntity living) {
            return BerettaCombat.guardDashEndpoint(bossPosition, living, dash, this.seed);
         } else {
            double angle = this.seed * 1.7E-4 + dash * Math.PI * 2.0 / Math.max(1, 3);
            return bossPosition.add(Math.cos(angle) * 14.0, 0.0, Math.sin(angle) * 14.0);
         }
      }
   }
}
