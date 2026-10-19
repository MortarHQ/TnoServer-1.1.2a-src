package com.mongoose.royalvariations.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mongoose.royalvariations.client.render.model.GloveModel;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public record WearRenderer(ResourceLocation texture, HumanoidModel<LivingEntity> model) implements ICurioRenderer {
   private ResourceLocation getTexture(LivingEntity livingEntity) {
      return this.texture;
   }

   private HumanoidModel<LivingEntity> getModel() {
      return this.model;
   }

   @Nullable
   public static WearRenderer getRenderer(ItemStack stack) {
      return !stack.isEmpty() ? (WearRenderer)CuriosRendererRegistry.getRenderer(stack.getItem()).orElse(null) : null;
   }

   public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack matrixStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource renderTypeBuffer,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      LivingEntity livingEntity = slotContext.entity();
      HumanoidModel<LivingEntity> model = this.getModel();
      model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
      ICurioRenderer.followBodyRotations(slotContext.entity(), new HumanoidModel[]{model});
      this.render(livingEntity, matrixStack, renderTypeBuffer, light);
   }

   private void render(LivingEntity livingEntity, PoseStack matrixStack, MultiBufferSource buffer, int light) {
      RenderType renderType = this.getModel().renderType(this.getTexture(livingEntity));
      VertexConsumer vertexBuilder = buffer.getBuffer(renderType);
      this.getModel().renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY);
   }

   public void renderFirstPersonArm(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm side, boolean hasFoil) {
      if (!player.isSpectator()) {
         GloveModel model = (GloveModel)this.getModel();
         ModelPart arm = side == HumanoidArm.LEFT ? model.leftArm : model.rightArm;
         model.setAllVisible(false);
         arm.visible = true;
         model.crouching = false;
         model.attackTime = 0.0F;
         model.swimAmount = 0.0F;
         model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         arm.xRot = 0.0F;
         this.renderFirstPersonArm(player, model, arm, matrixStack, buffer, light, hasFoil);
      }
   }

   private void renderFirstPersonArm(
      LivingEntity livingEntity, GloveModel model, ModelPart arm, PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasFoil
   ) {
      RenderType renderType = model.renderType(this.getTexture(livingEntity));
      VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
      arm.render(matrixStack, builder, light, OverlayTexture.NO_OVERLAY);
   }
}
