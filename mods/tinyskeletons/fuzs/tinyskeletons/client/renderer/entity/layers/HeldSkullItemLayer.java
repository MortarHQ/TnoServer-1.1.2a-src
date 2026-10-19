package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.tinyskeletons.client.core.ClientAbstractions;
import fuzs.tinyskeletons.world.entity.monster.SkullCarryingMob;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HeldSkullItemLayer<T extends LivingEntity & SkullCarryingMob, M extends EntityModel<T> & ArmedModel> extends ItemInHandLayer<T, M> {
   public HeldSkullItemLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
      super(renderLayerParent, itemInHandRenderer);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int packedLight,
      T livingEntity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemstack = livingEntity.getSkullItem();
      if (!itemstack.isEmpty()) {
         poseStack.pushPose();
         this.renderHandSkullItem(itemstack, poseStack, multiBufferSource, packedLight);
         poseStack.popPose();
      } else {
         super.render(poseStack, multiBufferSource, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
      }
   }

   private void renderHandSkullItem(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
      poseStack.pushPose();
      poseStack.translate(0.0, 0.075, 0.375);
      poseStack.translate(0.0, 0.6875, -0.75);
      poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
      poseStack.translate(0.25, 0.1875, 0.25);
      poseStack.scale(-0.5F, -0.5F, 0.5F);
      poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
      ClientAbstractions.INSTANCE.renderByItem(itemStack, ItemDisplayContext.NONE, poseStack, multiBufferSource, packedLight, packedLight);
      poseStack.popPose();
   }
}
