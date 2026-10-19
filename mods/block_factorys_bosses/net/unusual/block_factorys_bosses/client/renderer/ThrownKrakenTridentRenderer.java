package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.unusual.block_factorys_bosses.entity.projectile.ThrownKrakenTridentEntity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ThrownKrakenTridentRenderer extends EntityRenderer<ThrownKrakenTridentEntity> {
   private final ItemRenderer itemRenderer;

   public ThrownKrakenTridentRenderer(Context context) {
      super(context);
      this.itemRenderer = context.getItemRenderer();
   }

   public ResourceLocation getTextureLocation(ThrownKrakenTridentEntity entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   public void render(
      ThrownKrakenTridentEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
   ) {
      poseStack.pushPose();
      poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));
      ItemStack itemStack = entity.pickupItemStack;
      BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.level(), null, entity.getId());
      this.itemRenderer.render(itemStack, ItemDisplayContext.HEAD, false, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, bakedModel);
      poseStack.popPose();
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
