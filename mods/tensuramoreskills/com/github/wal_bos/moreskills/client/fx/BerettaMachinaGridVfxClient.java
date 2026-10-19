package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class BerettaMachinaGridVfxClient {
   private static final List<BerettaMachinaGridVfxClient.Cast> CASTS = new ArrayList<>();
   private static boolean initialized;

   private BerettaMachinaGridVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(BerettaMachinaGridVfxClient::render);
      }
   }

   public static void add(int bossId, int targetId, double x, double y, double z, double dirX, double dirZ, int duration, int seed) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.player != null) {
         if (!(minecraft.player.distanceToSqr(x, y, z) > 57600.0)) {
            CASTS.add(new BerettaMachinaGridVfxClient.Cast(new Vec3(x, y, z), flat(dirX, dirZ), duration, seed, minecraft.level.getGameTime()));
            if (CASTS.size() > 6) {
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
            Iterator<BerettaMachinaGridVfxClient.Cast> iterator = CASTS.iterator();

            while (iterator.hasNext()) {
               BerettaMachinaGridVfxClient.Cast cast = iterator.next();
               float age = BerettaVfxRenderHelper.age(frame, cast.startTick);
               if (age > cast.duration + 12.0F) {
                  iterator.remove();
               } else {
                  float domain = BerettaVfxRenderHelper.envelope(age, 0.0F, cast.duration, 10.0F);
                  Vec3 floor = cast.anchor.add(0.0, 0.05, 0.0);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor, 16.8, -age * 0.02, 36, 0.76F, 0.08F, 1.0F, domain * 0.64F);
                  wrote |= BerettaVfxRenderHelper.runeRing(frame, floor.add(0.0, 0.025, 0.0), 12.2, age * 0.034, 28, 1.0F, 0.42F, 1.0F, domain * 0.48F);
                  wrote |= BerettaVfxRenderHelper.disk(frame, floor, 10.8, 17.6, 80, 0.26F, 0.0F, 0.62F, domain * 0.08F);

                  for (int wave = 0; wave < 5; wave++) {
                     int fire = 36 + wave * 18;
                     int reveal = fire - 22;
                     if (!(age < reveal) && !(age > fire + 9.0F)) {
                        Vec3 direction = BerettaCombat.gridDirection(cast.direction, wave, cast.seed);
                        if (age < fire) {
                           float charge = BerettaVfxRenderHelper.smooth((age - reveal) / Math.max(1.0F, fire - reveal));

                           for (int gx = 0; gx < 5; gx++) {
                              for (int gz = 0; gz < 5; gz++) {
                                 Vec3 center = BerettaCombat.gridCellCenter(cast.anchor, direction, gx, gz).add(0.0, 0.055, 0.0);
                                 if (BerettaCombat.isGridCellDangerous(cast.seed, wave, gx, gz)) {
                                    wrote |= BerettaVfxRenderHelper.warningSquare(
                                       frame, center, direction, 2.4939999999999998, age + gx * 0.42F + gz * 0.25F, charge
                                    );
                                 } else {
                                    wrote |= BerettaVfxRenderHelper.safeSquare(frame, center, direction, 2.4939999999999998, age + gx + gz, charge * 0.82F);
                                 }
                              }
                           }
                        } else {
                           float burst = BerettaVfxRenderHelper.envelope(age, fire, fire + 9.0F, 1.7F);

                           for (int gx = 0; gx < 5; gx++) {
                              for (int gz = 0; gz < 5; gz++) {
                                 if (BerettaCombat.isGridCellDangerous(cast.seed, wave, gx, gz)) {
                                    Vec3 center = BerettaCombat.gridCellCenter(cast.anchor, direction, gx, gz).add(0.0, 0.05, 0.0);
                                    double delay = (Math.abs(gx - 2) + Math.abs(gz - 2)) * 0.22;
                                    float local = BerettaVfxRenderHelper.envelope(age, (float)(fire + delay), (float)(fire + 9.0 + delay), 1.5F);
                                    double height = 6.4 + (gx * 3 + gz * 5 + wave) % 4 * 1.25;
                                    wrote |= BerettaVfxRenderHelper.pillar(frame, center, height, 2.552, 0.26F, 0.0F, 0.68F, local * 0.24F);
                                    wrote |= BerettaVfxRenderHelper.pillar(frame, center, height * 0.94, 0.42, 1.0F, 0.68F, 1.0F, local);
                                    wrote |= BerettaVfxRenderHelper.verticalRay(
                                       frame, center, height + 2.5, 0.22, age, cast.seed + wave * 311 + gx * 17 + gz, local * 0.72F
                                    );
                                    wrote |= BerettaVfxRenderHelper.impactBloom(
                                       frame, center, 1.74, age - fire, cast.seed + wave * 337 + gx * 19 + gz, local * 0.66F
                                    );
                                 }
                              }
                           }

                           wrote |= BerettaVfxRenderHelper.shockwaveBloom(
                              frame, cast.anchor.add(0.0, 0.06, 0.0), 5.0 + (age - fire) * 1.3, age - fire, cast.seed + wave * 401, burst * 0.3F
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
