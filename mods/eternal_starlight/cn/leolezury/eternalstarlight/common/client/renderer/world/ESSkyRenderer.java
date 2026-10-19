package cn.leolezury.eternalstarlight.common.client.renderer.world;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.weather.ClientWeatherState;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexBuffer.Usage;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ESSkyRenderer {
   private static final ResourceLocation DEAD_STAR_LOCATION = EternalStarlight.id("textures/environment/dead_star.png");
   private static final float TIME = 12500.0F;
   private static final float FIXED_TIME = (float)(Mth.frac(0.27083333333333337) * 2.0 + 0.5 - Math.cos(Mth.frac(0.27083333333333337) * Math.PI) / 2.0) / 3.0F;
   private static VertexBuffer starBuffer;

   public static boolean renderSky(ClientLevel level, Matrix4f modelViewMatrix, Matrix4f matrix, float partialTicks, Camera camera, Runnable setupFog) {
      Minecraft minecraft = Minecraft.getInstance();
      LevelRenderer levelRenderer = minecraft.levelRenderer;
      setupFog.run();
      if (starBuffer == null) {
         createStars();
      }

      FogType fogType = camera.getFluidInCamera();
      if (fogType != FogType.POWDER_SNOW && fogType != FogType.LAVA && !levelRenderer.doesMobEffectBlockSky(camera)) {
         PoseStack poseStack = new PoseStack();
         poseStack.mulPose(modelViewMatrix);
         Vec3 vec3 = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), partialTicks);
         float g = (float)vec3.x;
         float h = (float)vec3.y;
         float i = (float)vec3.z;
         FogRenderer.levelFogColor();
         Tesselator tesselator = Tesselator.getInstance();
         RenderSystem.depthMask(false);
         RenderSystem.setShaderColor(g, h, i, 1.0F);
         ShaderInstance shaderInstance = RenderSystem.getShader();
         levelRenderer.skyBuffer.bind();
         levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), matrix, shaderInstance);
         VertexBuffer.unbind();
         RenderSystem.enableBlend();
         float[] fs = level.effects().getSunriseColor(FIXED_TIME, partialTicks);
         if (fs != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            float j = Mth.sin(level.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(j));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            float k = fs[0];
            float l = fs[1];
            float m = fs[2];
            Matrix4f matrix4f3 = poseStack.last().pose();
            BufferBuilder bufferBuilder = tesselator.begin(Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.addVertex(matrix4f3, 0.0F, 100.0F, 0.0F).setColor(k, l, m, fs[3]);

            for (int o = 0; o <= 16; o++) {
               float p = o * (float) (Math.PI * 2) / 16.0F;
               float q = Mth.sin(p);
               float r = Mth.cos(p);
               bufferBuilder.addVertex(matrix4f3, q * 120.0F, r * 120.0F, -r * 40.0F * fs[3]).setColor(fs[0], fs[1], fs[2], 0.0F);
            }

            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            poseStack.popPose();
         }

         RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
         poseStack.pushPose();
         float rainLevel = level.getRainLevel(partialTicks);
         if (ClientWeatherState.weather != null) {
            rainLevel = ClientWeatherState.weather.modifyRainLevel(rainLevel);
         } else {
            ClientWeatherState.levelTarget = rainLevel;
            rainLevel = ClientWeatherState.getRainLevel(partialTicks);
         }

         float j = 1.0F - rainLevel;
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, j);
         poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
         poseStack.mulPose(Axis.XP.rotationDegrees(FIXED_TIME * 360.0F));
         Matrix4f matrix4f4 = poseStack.last().pose();
         float l = 60.0F;
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, DEAD_STAR_LOCATION);
         BufferBuilder bufferBuilder2 = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
         bufferBuilder2.addVertex(matrix4f4, -l, 100.0F, -l).setUv(0.0F, 0.0F);
         bufferBuilder2.addVertex(matrix4f4, l, 100.0F, -l).setUv(1.0F, 0.0F);
         bufferBuilder2.addVertex(matrix4f4, l, 100.0F, l).setUv(1.0F, 1.0F);
         bufferBuilder2.addVertex(matrix4f4, -l, 100.0F, l).setUv(0.0F, 1.0F);
         BufferUploader.drawWithShader(bufferBuilder2.buildOrThrow());
         float v = 1.0F;
         RenderSystem.setShaderColor(v, v, v, v);
         FogRenderer.setupNoFog();
         starBuffer.bind();
         starBuffer.drawWithShader(poseStack.last().pose(), matrix, GameRenderer.getPositionShader());
         VertexBuffer.unbind();
         setupFog.run();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
         poseStack.popPose();
         RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.depthMask(true);
      }

      return true;
   }

   private static void createStars() {
      if (starBuffer != null) {
         starBuffer.close();
      }

      starBuffer = new VertexBuffer(Usage.STATIC);
      starBuffer.bind();
      starBuffer.upload(drawStars(Tesselator.getInstance()));
      VertexBuffer.unbind();
   }

   private static MeshData drawStars(Tesselator tesselator) {
      RandomSource randomSource = RandomSource.create(10842L);
      float f = 100.0F;
      BufferBuilder bufferBuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION);

      for (int j = 0; j < 3000; j++) {
         float g = randomSource.nextFloat() * 2.0F - 1.0F;
         float h = randomSource.nextFloat() * 2.0F - 1.0F;
         float k = randomSource.nextFloat() * 2.0F - 1.0F;
         float l = 0.15F + randomSource.nextFloat() * 0.1F;
         float m = Mth.lengthSquared(g, h, k);
         if (!(m <= 0.010000001F) && !(m >= 1.0F)) {
            Vector3f vector3f = new Vector3f(g, h, k).normalize(100.0F);
            float n = (float)(randomSource.nextDouble() * (float) Math.PI * 2.0);
            Quaternionf quaternionf = new Quaternionf().rotateTo(new Vector3f(0.0F, 0.0F, -1.0F), vector3f).rotateZ(n);
            bufferBuilder.addVertex(vector3f.add(new Vector3f(l, -l, 0.0F).rotate(quaternionf)));
            bufferBuilder.addVertex(vector3f.add(new Vector3f(l, l, 0.0F).rotate(quaternionf)));
            bufferBuilder.addVertex(vector3f.add(new Vector3f(-l, l, 0.0F).rotate(quaternionf)));
            bufferBuilder.addVertex(vector3f.add(new Vector3f(-l, -l, 0.0F).rotate(quaternionf)));
         }
      }

      return bufferBuilder.buildOrThrow();
   }
}
