package twilightforest.client;

import java.util.Optional;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import twilightforest.client.renderer.TFSkyRenderer;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.init.TFBiomes;

public class TwilightForestRenderInfo extends DimensionSpecialEffects {
   public TwilightForestRenderInfo(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
      super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
   }

   @Nullable
   public float[] getSunriseColor(float daycycle, float partialTicks) {
      return null;
   }

   public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
      return biomeFogColor.multiply(daylight * 0.94F + 0.06F, daylight * 0.94F + 0.06F, daylight * 0.91F + 0.09F);
   }

   public boolean isFoggyAt(int x, int y) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         Optional<ResourceKey<Biome>> biome = player.level().getBiome(player.blockPosition()).unwrapKey();
         if (biome.isPresent()) {
            boolean spooky = biome.get() == TFBiomes.SPOOKY_FOREST;
            if (player.position().y > 20.0 && !spooky) {
               return false;
            }

            return spooky || biome.get() == TFBiomes.DARK_FOREST || biome.get() == TFBiomes.DARK_FOREST_CENTER;
         }
      }

      return false;
   }

   public boolean renderSky(
      ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog
   ) {
      return TFSkyRenderer.renderSky(level, partialTick, modelViewMatrix, camera, projectionMatrix, setupFog);
   }

   public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
      return TFWeatherRenderer.renderSnowAndRain(level, ticks, partialTick, lightTexture, new Vec3(camX, camY, camZ));
   }

   public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
      return TFWeatherRenderer.tickRain(level, ticks, camera.getBlockPosition());
   }
}
