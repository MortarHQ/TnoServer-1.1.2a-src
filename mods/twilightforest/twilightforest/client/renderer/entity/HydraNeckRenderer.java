package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.HydraHeadContainer;
import twilightforest.entity.boss.HydraNeck;

public class HydraNeckRenderer<T extends HydraNeck, M extends ListModel<T>> extends TFPartRenderer<T, M> {
   private static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("hydra4.png");

   public HydraNeckRenderer(Context context, M model) {
      super(context, model);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      HydraHeadContainer headCon = HydraHeadRenderer.getHeadObject(entity.head);
      if (headCon != null && entity.isActive()) {
         float yawDiff = entity.getYRot() - entity.yRotO;
         if (yawDiff > 180.0F) {
            yawDiff -= 360.0F;
         } else if (yawDiff < -180.0F) {
            yawDiff += 360.0F;
         }

         float yaw2 = entity.yRotO + yawDiff * partialTicks;
         stack.mulPose(Axis.YN.rotationDegrees(yaw2 + 180.0F));
         super.render(entity, entityYaw, partialTicks, stack, buffer, light);
      }
   }

   protected float getFlipDegrees(T entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }
}
