package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

public final class BlackBloodDomainFogHandler {
   private static final int LOCAL_RADIUS = 10;
   private static final int LOCAL_VERTICAL_RADIUS = 6;
   private static final int DOMAIN_COLUMN_RADIUS = 18;
   private static final int DOMAIN_COLUMN_DEPTH = 96;
   private static final int DOMAIN_COLUMN_STEP = 3;
   private static final int SCAN_INTERVAL_TICKS = 5;
   private static final ResourceLocation BLOOD_ARTS_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "blood_arts");
   private static float smoothedStrength;
   private static float smoothedBloodSight;
   private static float cachedTargetStrength;
   private static long nextScanTick = Long.MIN_VALUE;
   private static ClientLevel cachedLevel;
   private static ManasSkill cachedBloodArtsSkill;

   private BlackBloodDomainFogHandler() {
   }

   public static void fogColor(ComputeFogColor event) {
      if (!isEnabled()) {
         reset();
      } else {
         Minecraft mc = Minecraft.getInstance();
         ClientLevel level = mc.level;
         if (level != null && mc.player != null) {
            float sight = getSmoothedBloodArtsSight();
            float strength = getSmoothedBloodFogStrength(level);
            strength *= getFogVisionMultiplier(sight);
            if (!(strength <= 0.001F)) {
               float heavy = strength * strength;
               float red = Mth.lerp(heavy, event.getRed(), 0.42F);
               float green = Mth.lerp(heavy, event.getGreen(), 0.01F);
               float blue = Mth.lerp(heavy, event.getBlue(), 0.018F);
               if (sight > 0.001F) {
                  red = Mth.lerp(sight, red, event.getRed());
                  green = Mth.lerp(sight, green, event.getGreen());
                  blue = Mth.lerp(sight, blue, event.getBlue());
               }

               event.setRed(red);
               event.setGreen(green);
               event.setBlue(blue);
            }
         } else {
            reset();
         }
      }
   }

   public static void fogDensity(RenderFog event) {
      if (!isEnabled()) {
         reset();
      } else {
         Minecraft mc = Minecraft.getInstance();
         ClientLevel level = mc.level;
         if (level != null && mc.player != null) {
            if (event.getMode() == FogMode.FOG_TERRAIN) {
               float sight = getSmoothedBloodArtsSight();
               float strength = getSmoothedBloodFogStrength(level);
               strength *= getFogVisionMultiplier(sight);
               if (!(strength <= 0.001F) || !(sight <= 0.001F)) {
                  float heavy = strength * strength;
                  float near = Mth.lerp(heavy, event.getNearPlaneDistance(), 0.1F);
                  float far = Mth.lerp(heavy, event.getFarPlaneDistance(), 18.0F);
                  if (strength > 0.35F) {
                     near = Mth.lerp(strength, near, 0.0F);
                     far = Mth.lerp(strength, far, 11.0F);
                  }

                  if (sight > 0.001F) {
                     float clearFar = Math.max(event.getFarPlaneDistance(), 96.0F);
                     near = Mth.lerp(sight, near, event.getNearPlaneDistance());
                     far = Mth.lerp(sight, far, clearFar);
                  }

                  event.setNearPlaneDistance(Math.max(0.0F, near));
                  event.setFarPlaneDistance(Math.max(4.0F, far));
                  event.setCanceled(true);
               }
            }
         } else {
            reset();
         }
      }
   }

   private static boolean isEnabled() {
      return (Boolean)MoreskillsRaceConfig.get().BlackCommunion.blockInfectionEnabled.get();
   }

   private static float getSmoothedBloodFogStrength(ClientLevel level) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.gameRenderer != null && mc.gameRenderer.getMainCamera() != null) {
         if (cachedLevel != level) {
            cachedLevel = level;
            cachedTargetStrength = 0.0F;
            smoothedStrength = 0.0F;
            nextScanTick = Long.MIN_VALUE;
         }

         long gameTime = level.getGameTime();
         if (gameTime >= nextScanTick) {
            Vec3 camera = mc.gameRenderer.getMainCamera().getPosition();
            BlockPos center = BlockPos.containing(camera);
            cachedTargetStrength = getLocalBloodFogStrength(level, center);
            nextScanTick = gameTime + 5L;
         }

         if (cachedTargetStrength > smoothedStrength) {
            smoothedStrength = Mth.lerp(0.22F, smoothedStrength, cachedTargetStrength);
         } else {
            smoothedStrength = Mth.lerp(0.025F, smoothedStrength, cachedTargetStrength);
         }

         if (smoothedStrength < 0.001F) {
            smoothedStrength = 0.0F;
         }

         return Mth.clamp(smoothedStrength, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   private static float getSmoothedBloodArtsSight() {
      float target = isBloodArtsToggled() ? 1.0F : 0.0F;
      if (target > smoothedBloodSight) {
         smoothedBloodSight = Mth.lerp(0.35F, smoothedBloodSight, target);
      } else {
         smoothedBloodSight = Mth.lerp(0.18F, smoothedBloodSight, target);
      }

      if (smoothedBloodSight < 0.001F) {
         smoothedBloodSight = 0.0F;
      }

      return Mth.clamp(smoothedBloodSight, 0.0F, 1.0F);
   }

   private static float getFogVisionMultiplier(float sight) {
      return Mth.lerp(Mth.clamp(sight, 0.0F, 1.0F), 1.0F, 0.06F);
   }

   private static boolean isBloodArtsToggled() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null) {
         return false;
      }

      if (cachedBloodArtsSkill == null) {
         cachedBloodArtsSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(BLOOD_ARTS_ID);
      }

      return cachedBloodArtsSkill != null && SkillUtils.isSkillToggled(mc.player, cachedBloodArtsSkill);
   }

   private static float getLocalBloodFogStrength(ClientLevel level, BlockPos center) {
      float localStrength = getNearbyBlockFogStrength(level, center);
      if (localStrength >= 1.0F) {
         return 1.0F;
      }

      float columnStrength = getHiddenBiomeColumnStrength(level, center);
      return Mth.clamp(Math.max(localStrength, columnStrength), 0.0F, 1.0F);
   }

   private static float getNearbyBlockFogStrength(ClientLevel level, BlockPos center) {
      int weighted = 0;
      MutableBlockPos mutable = new MutableBlockPos();
      int centerX = center.getX();
      int centerY = center.getY();
      int centerZ = center.getZ();

      for (int x = -10; x <= 10; x += 2) {
         for (int z = -10; z <= 10; z += 2) {
            for (int y = -6; y <= 6; y += 2) {
               int worldX = centerX + x;
               int worldY = centerY + y;
               int worldZ = centerZ + z;
               int weight = getLocalWeight(x, y, z);
               if (isInfected(level, mutable, worldX, worldY, worldZ)) {
                  weighted += weight;
               } else if (isInfected(level, mutable, worldX, worldY - 1, worldZ) || isInfected(level, mutable, worldX, worldY + 1, worldZ)) {
                  weighted += Math.max(1, weight - 1);
               }

               if (weighted >= 165) {
                  return 1.0F;
               }
            }
         }
      }

      return weighted <= 0 ? 0.0F : Mth.clamp(weighted / 165.0F, 0.0F, 1.0F);
   }

   private static float getHiddenBiomeColumnStrength(ClientLevel level, BlockPos center) {
      int weighted = 0;
      MutableBlockPos mutable = new MutableBlockPos();
      int centerX = center.getX();
      int centerY = center.getY();
      int centerZ = center.getZ();

      for (int x = -18; x <= 18; x += 3) {
         for (int z = -18; z <= 18; z += 3) {
            int columnWeight = getColumnHorizontalWeight(x, z);
            if (hasInfectedColumnBelow(level, mutable, centerX + x, centerY, centerZ + z)) {
               weighted += columnWeight;
               if (weighted >= 180) {
                  return 1.0F;
               }
            }
         }
      }

      return weighted <= 0 ? 0.0F : Mth.clamp(weighted / 180.0F, 0.0F, 1.0F);
   }

   private static boolean hasInfectedColumnBelow(ClientLevel level, MutableBlockPos mutable, int x, int startY, int z) {
      for (int down = 0; down <= 96; down += 3) {
         int y = startY - down;
         if (isInfected(level, mutable, x, y, z) || isInfected(level, mutable, x, y - 1, z)) {
            return true;
         }
      }

      return false;
   }

   private static int getColumnHorizontalWeight(int x, int z) {
      int distance = Math.abs(x) + Math.abs(z);
      if (distance <= 6) {
         return 8;
      } else if (distance <= 14) {
         return 5;
      } else {
         return distance <= 24 ? 3 : 1;
      }
   }

   private static int getLocalWeight(int x, int y, int z) {
      int distance = Math.abs(x) + Math.abs(y) + Math.abs(z);
      if (distance <= 4) {
         return 6;
      } else {
         return distance <= 10 ? 4 : 1;
      }
   }

   private static boolean isInfected(ClientLevel level, MutableBlockPos mutable, int x, int y, int z) {
      mutable.set(x, y, z);
      if (!level.isLoaded(mutable)) {
         return false;
      }

      BlockState state = level.getBlockState(mutable);
      return state.is((Block)ModBlocks.BLACK_BLOOD_MIRE.get())
         || state.is((Block)ModBlocks.INFECTED_GRASS.get())
         || state.is((Block)ModBlocks.INFECTED_DIRT.get())
         || state.is((Block)ModBlocks.INFECTED_WOOD.get())
         || state.is((Block)ModBlocks.INFECTED_LEAVES.get())
         || state.is((Block)ModBlocks.BLOOD_ALTAR.get());
   }

   private static void reset() {
      smoothedStrength = 0.0F;
      smoothedBloodSight = 0.0F;
      cachedTargetStrength = 0.0F;
      cachedLevel = null;
      nextScanTick = Long.MIN_VALUE;
   }
}
