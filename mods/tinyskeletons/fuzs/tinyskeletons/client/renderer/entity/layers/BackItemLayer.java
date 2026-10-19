package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;

public class BackItemLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private final ItemInHandRenderer itemInHandRenderer;

   public BackItemLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
      super(renderLayerParent);
      this.itemInHandRenderer = itemInHandRenderer;
   }

   public void render(
      PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l
   ) {
      ItemStack itemstack = livingEntity.getOffhandItem();
      if (!itemstack.isEmpty()) {
         matrixStack.pushPose();
         ModelPart modelPart = ((HumanoidModel)this.getParentModel()).body;
         modelPart.translateAndRotate(matrixStack);
         float scale = 2.0F;
         float backOffset = livingEntity.hasItemInSlot(EquipmentSlot.CHEST) ? 0.24F : 0.18F;
         if (((HumanoidModel)this.getParentModel()).young) {
            matrixStack.translate(0.0, 0.75, 0.0);
            scale *= 0.5F;
            backOffset *= 0.5F;
         }

         if (itemstack.getItem() instanceof TridentItem) {
            scale *= 0.5F;
            matrixStack.mulPose(Axis.YP.rotationDegrees(52.0F));
            matrixStack.mulPose(Axis.XP.rotationDegrees(40.0F));
            matrixStack.mulPose(Axis.ZP.rotationDegrees(-25.0F));
            matrixStack.scale(scale, -scale, -scale);
            matrixStack.translate(-backOffset, 0.0, 0.0);
            this.itemInHandRenderer
               .renderItem(livingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
         } else {
            matrixStack.translate(0.0, 0.0, backOffset);
            matrixStack.scale(scale, scale, scale);
            if (itemstack.getItem() instanceof FishingRodItem || itemstack.getItem() instanceof FoodOnAStickItem) {
               matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
               matrixStack.translate(0.0, -0.3, 0.0);
            }

            this.itemInHandRenderer.renderItem(livingEntity, itemstack, ItemDisplayContext.GROUND, false, matrixStack, vertexConsumerProvider, i);
         }

         matrixStack.popPose();
      }
   }
}
