package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.renderer.FDBlockEntityRendererBuilder;
import com.finderfeed.fdlib.systems.post_shaders.FDPostShadersReloadableResourceListener;
import com.finderfeed.fdlib.systems.shapes.FD2DShape;
import com.finderfeed.fdlib.test.FDTestBlockEntity;
import com.finderfeed.fdlib.util.FDColor;
import com.finderfeed.fdlib.util.client.NullEntityRenderer;
import com.finderfeed.fdlib.util.client.particles.FDTerrainParticle;
import com.finderfeed.fdlib.util.client.particles.InvisibleParticle;
import com.finderfeed.fdlib.util.client.particles.ball_particle.BallParticle;
import com.finderfeed.fdlib.util.client.particles.lightning_particle.LightningParticle;
import com.finderfeed.fdlib.util.rendering.renderers.ShapeOnCurveRenderer;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "fdlib", value = Dist.CLIENT)
public class FDClientModEvents {
   public static final String FDLIB_KEY_CATEGORY = "fdlib.key_category";
   public static final KeyMapping END_CUTSCENE = new KeyMapping("fdlib.key.end_cutscene", Type.KEYSYM, 74, "fdlib.key_category");

   @SubscribeEvent
   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(END_CUTSCENE);
   }

   @SubscribeEvent
   public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new FDPostShadersReloadableResourceListener());
   }

   @SubscribeEvent
   public static void assignFactories(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)FDParticles.INVISIBLE.get(), InvisibleParticle.Factory::new);
      event.registerSpriteSet(FDParticles.BALL_PARTICLE.get(), BallParticle.Factory::new);
      event.registerSpecial(FDParticles.LIGHTNING_PARTICLE.get(), new LightningParticle.Factory());
      event.registerSpecial(FDParticles.TERRAIN_PARTICLE.get(), new FDTerrainParticle.Provider());
   }

   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(FDEntities.CLIENT_CAMERA.get(), NullEntityRenderer::new);
   }

   @SubscribeEvent
   public static void registerBERenderers(FMLClientSetupEvent event) {
      BlockEntityRenderers.register(
         FDBlockEntities.TEST.get(),
         FDBlockEntityRendererBuilder.<FDTestBlockEntity>builder()
            .freeRender(
               (blockEntity, pticks, matrices, src, light, overlay) -> {
                  VertexConsumer vertexConsumer = src.getBuffer(RenderType.lightning());
                  FD2DShape shape = FD2DShape.createSimpleCircleNVertexShape(1.0F, 4);
                  float time = (float)blockEntity.getLevel().getGameTime() + pticks;
                  float percent = (float)Math.sin(time / 50.0F) / 2.0F + 0.5F;
                  float percent2 = (float)Math.sin(time / 50.0F - (float) (Math.PI / 8)) / 2.0F + 0.5F;
                  ShapeOnCurveRenderer.start(vertexConsumer)
                     .uModifier(10.0F)
                     .vModifier(2.0F)
                     .shape(shape)
                     .lod(100)
                     .startColor(new FDColor(1.0F, 0.0F, 0.0F, 0.25F))
                     .endColor(new FDColor(0.0F, 1.0F, 0.0F, 0.25F))
                     .curvePositions(
                        new Vector3f(10.0F, 10.0F, 0.0F),
                        new Vector3f(0.0F, 3.0F, 3.0F),
                        new Vector3f(10.1F, -2.0F, 0.0F),
                        new Vector3f(10.0F, -1.0F, 5.0F),
                        new Vector3f(0.0F, 2.0F, 10.0F),
                        new Vector3f(-0.001F, 1.0E-4F, 1.0E-4F)
                     )
                     .pose(matrices)
                     .trailScalingFunction()
                     .render();
               }
            )
            .build()
      );
   }

   private static Vector3f rotatePoint(Quaternionf quaternionf, Vector3f point) {
      return quaternionf.transform(point, new Vector3f());
   }
}
