package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownBoomerang;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ThrownBoomerangRenderer<T extends ThrownBoomerang> extends EntityRenderer<T> {
   private final ItemRenderer itemRenderer;

   public ThrownBoomerangRenderer(Context context) {
      super(context);
      this.itemRenderer = context.getItemRenderer();
   }

   public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
      poseStack.pushPose();
      ItemStack itemStack = entity.getPickupItemStackOrigin();
      BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.level(), null, entity.getId());
      poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
      poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
      poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
      poseStack.mulPose(Axis.ZP.rotationDegrees(entity.inGround ? 0.0F : (entity.tickCount + partialTicks) * 15.0F));
      this.itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, multiBufferSource, light, OverlayTexture.NO_OVERLAY, bakedModel);
      poseStack.popPose();
      super.render(entity, yaw, partialTicks, poseStack, multiBufferSource, light);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
