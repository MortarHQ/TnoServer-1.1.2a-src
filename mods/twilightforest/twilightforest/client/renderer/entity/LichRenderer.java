package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.LichModel;
import twilightforest.client.renderer.TFRenderTypes;
import twilightforest.client.renderer.entity.layers.ShieldLayer;
import twilightforest.entity.boss.Lich;

public class LichRenderer<T extends Lich, M extends LichModel<T>> extends HumanoidMobRenderer<T, M> {
   public static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("twilightlich64.png");

   public LichRenderer(Context context, M model, float shadowSize) {
      super(context, model, shadowSize);
      this.addLayer(new ShieldLayer(this));
      this.addLayer(
         new EyesLayer<T, M>(this) {
            private static final RenderType EYES = RenderType.eyes(TwilightForestMod.getModelTexture("twilightlich64_eyes.png"));

            public void render(
               PoseStack poseStack,
               MultiBufferSource buffer,
               int packedLight,
               T t,
               float limbSwing,
               float limbSwingAmount,
               float partialTicks,
               float ageInTicks,
               float netHeadYaw,
               float headPitch
            ) {
               if (t.isShadowClone() && !t.isInvisible()) {
                  super.render(poseStack, buffer, packedLight, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
               }
            }

            public RenderType renderType() {
               return EYES;
            }
         }
      );
   }

   @Nullable
   protected RenderType getRenderType(T entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return entity.isShadowClone() && bodyVisible ? TFRenderTypes.SHADOW_CLONE : super.getRenderType(entity, bodyVisible, translucent, glowing);
   }

   protected boolean isShaking(T entity) {
      return super.isShaking(entity) || entity.isDeadOrDying() && entity.deathTime <= 115;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight) {
      stack.pushPose();
      stack.scale(1.125F, 1.125F, 1.125F);
      if (entity.deathTime > 0) {
         if (entity.deathTime > 115) {
            stack.translate(0.0, -1.8 * Math.pow(Math.min((entity.deathTime - 115 + partialTicks) / 16.0F, 1.0), 3.0), 0.0);
         } else {
            float time = entity.deathTime + partialTicks;
            stack.translate(Math.sin(time * time) * 0.01, 0.0, Math.cos(time * time) * 0.01);
         }

         super.render(entity, entityYaw, partialTicks, stack, buffer, packedLight);
      } else {
         super.render(entity, entityYaw, partialTicks, stack, buffer, packedLight);
      }

      stack.popPose();
   }

   protected float getFlipDegrees(T entity) {
      return entity.isDeadOrDying() ? 0.0F : super.getFlipDegrees(entity);
   }

   public ResourceLocation getTextureLocation(Lich entity) {
      return TEXTURE;
   }

   protected float getShadowRadius(T entity) {
      return !entity.isShadowClone() && entity.deathTime <= 115 ? super.getShadowRadius(entity) : 0.0F;
   }
}
