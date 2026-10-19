package de.ellpeck.naturesaura.particles;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import de.ellpeck.naturesaura.ModConfig;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public final class ParticleHandler {
   public static final ParticleRenderType MAGIC = new ParticleRenderType() {
      @Nullable
      public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
         ParticleHandler.setupRendering();
         RenderSystem.enableDepthTest();
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public String toString() {
         return "naturesaura".toUpperCase(Locale.ROOT) + "_MAGIC";
      }
   };
   public static final ParticleRenderType MAGIC_NO_DEPTH = new ParticleRenderType() {
      @Nullable
      public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
         ParticleHandler.setupRendering();
         RenderSystem.disableDepthTest();
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public String toString() {
         return "naturesaura".toUpperCase(Locale.ROOT) + "_MAGIC_NO_DEPTH";
      }
   };
   public static boolean depthEnabled = true;
   public static int range = 32;
   public static boolean culling = true;

   public static void spawnParticle(Supplier<Particle> particle, double x, double y, double z) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player.distanceToSqr(x, y, z) <= range * range) {
         if (culling) {
            if ((Boolean)ModConfig.instance.respectVanillaParticleSettings.get()) {
               ParticleStatus setting = (ParticleStatus)mc.options.particles().get();
               if (setting != ParticleStatus.ALL
                  && (setting != ParticleStatus.DECREASED || mc.level.random.nextInt(3) != 0)
                  && (setting != ParticleStatus.MINIMAL || mc.level.random.nextInt(10) != 0)) {
                  return;
               }
            }

            double setting = (Double)ModConfig.instance.particleAmount.get();
            if (setting < 1.0 && mc.level.random.nextDouble() > setting) {
               return;
            }
         }

         mc.particleEngine.add(particle.get());
      }
   }

   private static void setupRendering() {
      RenderSystem.disableCull();
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      RenderSystem.depthMask(false);
      RenderSystem.setShader(GameRenderer::getParticleShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, ParticleMagic.TEXTURE);
   }
}
