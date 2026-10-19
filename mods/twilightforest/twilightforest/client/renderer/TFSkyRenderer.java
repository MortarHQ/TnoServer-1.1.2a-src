package twilightforest.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TFSkyRenderer {
   private static VertexBuffer starBuffer;

   public static boolean renderSky(ClientLevel level, float partialTicks, Matrix4f frustumMatrix, Camera camera, Matrix4f projectionMatrix, Runnable setupFog) {
      LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
      PoseStack stack = new PoseStack();
      stack.mulPose(frustumMatrix);
      setupFog.run();
      Vec3 vec3 = level.getSkyColor(camera.getPosition(), partialTicks);
      float f = (float)vec3.x();
      float f1 = (float)vec3.y();
      float f2 = (float)vec3.z();
      FogRenderer.levelFogColor();
      RenderSystem.depthMask(false);
      RenderSystem.setShaderColor(f, f1, f2, 1.0F);
      ShaderInstance shaderinstance = RenderSystem.getShader();
      levelRenderer.skyBuffer.bind();
      levelRenderer.skyBuffer.drawWithShader(stack.last().pose(), projectionMatrix, shaderinstance);
      VertexBuffer.unbind();
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      stack.pushPose();
      float f11 = 1.0F - level.getRainLevel(partialTicks);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
      stack.mulPose(Axis.YP.rotationDegrees(-90.0F));
      stack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTicks) * 360.0F));
      float f10 = 1.0F;
      RenderSystem.setShaderColor(f10, f10, f10, f10);
      FogRenderer.setupNoFog();
      starBuffer.bind();
      starBuffer.drawWithShader(stack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
      VertexBuffer.unbind();
      setupFog.run();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
      stack.popPose();
      RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
      double d0 = camera.getEntity().getEyePosition(partialTicks).y() - level.getMinBuildHeight();
      if (d0 < 0.0) {
         stack.pushPose();
         stack.translate(0.0F, 12.0F, 0.0F);
         levelRenderer.darkBuffer.bind();
         levelRenderer.darkBuffer.drawWithShader(stack.last().pose(), projectionMatrix, shaderinstance);
         VertexBuffer.unbind();
         stack.popPose();
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.depthMask(true);
      return true;
   }

   public static void createStars() {
      if (starBuffer != null) {
         starBuffer.close();
      }

      starBuffer = new VertexBuffer(Usage.STATIC);
      starBuffer.bind();
      starBuffer.upload(drawStars(Tesselator.getInstance()));
      VertexBuffer.unbind();
   }

   private static MeshData drawStars(Tesselator tesselator) {
      RandomSource random = RandomSource.create(10842L);
      BufferBuilder bufferbuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION);

      for (int i = 0; i < 3000; i++) {
         float f1 = random.nextFloat() * 2.0F - 1.0F;
         float f2 = random.nextFloat() * 2.0F - 1.0F;
         float f3 = random.nextFloat() * 2.0F - 1.0F;
         float f4 = 0.15F + random.nextFloat() * 0.1F;
         float f5 = Mth.lengthSquared(f1, f2, f3);
         if (!(f5 <= 0.010000001F) && !(f5 >= 1.0F)) {
            Vector3f vector3f = new Vector3f(f1, f2, f3).normalize(100.0F);
            float f6 = (float)(random.nextDouble() * (float) Math.PI * 2.0);
            Quaternionf quaternionf = new Quaternionf().rotateTo(new Vector3f(0.0F, 0.0F, -1.0F), vector3f).rotateZ(f6);
            bufferbuilder.addVertex(vector3f.add(new Vector3f(f4, -f4, 0.0F).rotate(quaternionf)));
            bufferbuilder.addVertex(vector3f.add(new Vector3f(f4, f4, 0.0F).rotate(quaternionf)));
            bufferbuilder.addVertex(vector3f.add(new Vector3f(-f4, f4, 0.0F).rotate(quaternionf)));
            bufferbuilder.addVertex(vector3f.add(new Vector3f(-f4, -f4, 0.0F).rotate(quaternionf)));
         }
      }

      return bufferbuilder.buildOrThrow();
   }
}
