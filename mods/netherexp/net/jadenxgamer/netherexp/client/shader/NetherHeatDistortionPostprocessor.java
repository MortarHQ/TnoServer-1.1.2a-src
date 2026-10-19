package net.jadenxgamer.netherexp.client.shader;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class NetherHeatDistortionPostprocessor extends PostProcessor {
   public static final NetherHeatDistortionPostprocessor INSTANCE = new NetherHeatDistortionPostprocessor();
   private static float currentIntensity = 0.0F;
   private static float currentSpeed = 0.0F;
   private static float currentMinDistance = 0.0F;
   private static float currentMaxDistance = 0.0F;
   private static long lastLavaCheckGameTime = -1L;
   private static boolean cachedNearLava = false;

   public ResourceLocation getPostChainLocation() {
      return NetherExp.netherexpPath("nether_heat_distortion");
   }

   public void beforeProcess(Matrix4f viewModelMatrix) {
      if (this.postChain != null) {
         Minecraft client = Minecraft.getInstance();
         if (client.level != null) {
            float gameTimeTicks = (float)client.level.getGameTime() + client.getTimer().getGameTimeDeltaPartialTick(true);
            float timeInSeconds = gameTimeTicks / 20.0F;
            this.postChain.setUniform("GameTime", timeInSeconds);
            this.postChain.setUniform("Intensity", currentIntensity);
            this.postChain.setUniform("Speed", currentSpeed);
            this.postChain.setUniform("MinDistance", currentMinDistance);
            this.postChain.setUniform("MaxDistance", currentMaxDistance);
         }
      }
   }

   public void afterProcess() {
   }

   public static boolean shouldEnable() {
      return (Boolean)JNEConfigs.ENABLE_HEAT_DISTORTION.get() && currentIntensity > 0.0;
   }

   public static void tick(Minecraft client, Level level, Entity player, Holder<Biome> biome) {
      float targetIntensity = 0.0F;
      float targetSpeed = ((Double)JNEConfigs.HEAT_DISTORTION_SPEED.get()).floatValue();
      float targetMinDistance = ((Double)JNEConfigs.HEAT_DISTORTION_MIN_DISTANCE.get()).floatValue();
      float targetMaxDistance = ((Double)JNEConfigs.HEAT_DISTORTION_MAX_DISTANCE.get()).floatValue();
      boolean nearLava = cachedNearLava;
      long gameTime = level.getGameTime();
      if ((Boolean)JNEConfigs.LAVA_HEAT_DISTORTION.get()
         && (lastLavaCheckGameTime == -1L || gameTime - lastLavaCheckGameTime >= ((Integer)JNEConfigs.HEAT_DISTORTION_LAVA_CHECK_PERIOD.get()).intValue())) {
         nearLava = isPlayerNearLava(level, player);
         cachedNearLava = nearLava;
         lastLavaCheckGameTime = gameTime;
      }

      if ((Boolean)JNEConfigs.BIOME_HEAT_DISTORTION.get() && biome.is(JNETags.Biomes.HAS_HEAT_DISTORTION)) {
         targetIntensity = ((Double)JNEConfigs.HEAT_DISTORTION_INTENSITY.get()).floatValue();
      }

      if (nearLava) {
         targetIntensity = ((Double)JNEConfigs.HEAT_DISTORTION_LAVA_INTENSITY.get()).floatValue();
         targetSpeed = ((Double)JNEConfigs.HEAT_DISTORTION_LAVA_SPEED.get()).floatValue();
         targetMinDistance = ((Double)JNEConfigs.HEAT_DISTORTION_LAVA_MIN_DISTANCE.get()).floatValue();
         targetMaxDistance = ((Double)JNEConfigs.HEAT_DISTORTION_LAVA_MAX_DISTANCE.get()).floatValue();
      }

      currentIntensity = Mth.lerp(0.05F, currentIntensity, targetIntensity);
      currentSpeed = Mth.lerp(0.05F, currentSpeed, targetSpeed);
      currentMinDistance = Mth.lerp(0.05F, currentMinDistance, targetMinDistance);
      currentMaxDistance = Mth.lerp(0.05F, currentMaxDistance, targetMaxDistance);
      float epsilon = 1.0E-4F;
      if (targetIntensity == 0.0F && Math.abs(currentIntensity) < 1.0E-4F) {
         currentIntensity = 0.0F;
      }

      if (targetSpeed == ((Double)JNEConfigs.HEAT_DISTORTION_SPEED.get()).floatValue() && Math.abs(currentSpeed - targetSpeed) < 1.0E-4F) {
         currentSpeed = targetSpeed;
      }

      if (targetMinDistance == ((Double)JNEConfigs.HEAT_DISTORTION_MIN_DISTANCE.get()).floatValue()
         && Math.abs(currentMinDistance - targetMinDistance) < 1.0E-4F) {
         currentMinDistance = targetMinDistance;
      }

      if (targetMaxDistance == ((Double)JNEConfigs.HEAT_DISTORTION_MAX_DISTANCE.get()).floatValue()
         && Math.abs(currentMaxDistance - targetMaxDistance) < 1.0E-4F) {
         currentMaxDistance = targetMaxDistance;
      }
   }

   private static boolean isPlayerNearLava(Level level, Entity player) {
      if (level != null && player != null) {
         float lavaProximityRadius = ((Double)JNEConfigs.HEAT_DISTORTION_LAVA_PROXIMITY.get()).floatValue();
         int requiredLavaBlocks = (Integer)JNEConfigs.REQUIRED_SOURCE_BLOCKS_FOR_LAVA_HEAT_DISTORTION.get();
         int foundLavaBlocks = 0;
         AABB searchBox = player.getBoundingBox().inflate(lavaProximityRadius);
         int minX = Mth.floor(searchBox.minX);
         int minY = Mth.floor(searchBox.minY);
         int minZ = Mth.floor(searchBox.minZ);
         int maxX = Mth.floor(searchBox.maxX);
         int maxY = Mth.floor(searchBox.maxY);
         int maxZ = Mth.floor(searchBox.maxZ);

         for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
               for (int z = minZ; z <= maxZ; z++) {
                  BlockPos pos = new BlockPos(x, y, z);
                  if (level.getBlockState(pos).getFluidState().is(FluidTags.LAVA) && level.getFluidState(pos).isSource()) {
                     foundLavaBlocks++;
                  }

                  if (foundLavaBlocks >= requiredLavaBlocks) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
