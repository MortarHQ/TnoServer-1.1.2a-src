package net.pixeldreamstudios.mythika.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.entity.SatyrEntity;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SatyrRenderer extends GeoEntityRenderer<SatyrEntity> {
   private static final String AULOS_PIPE = "bone9";
   private static final float AULOS_REED = 0.04375F;
   private static final float AULOS_BELL = -0.609375F;
   private static final float NOTE_ALONG_PIPE = 0.6F;
   private static final Vector4f NOTE_ORIGIN = new Vector4f(0.0F, 0.0F, Mth.lerp(0.6F, 0.04375F, -0.609375F), 1.0F);

   public SatyrRenderer(Context context) {
      super(context, new SatyrModel());
      this.shadowRadius = 0.5F;
   }

   public void renderRecursively(
      PoseStack poseStack,
      SatyrEntity animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      if ("bone9".equals(bone.getName())) {
         if (bone.isTrackingMatrices()) {
            Vector4f origin = bone.getWorldSpaceMatrix().transform(new Vector4f(NOTE_ORIGIN));
            animatable.setAulosMouth(new Vec3(origin.x(), origin.y(), origin.z()));
         } else {
            bone.setTrackingMatrices(true);
         }
      }
   }
}
