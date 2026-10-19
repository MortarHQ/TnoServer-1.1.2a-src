package net.unusual.block_factorys_bosses.geckolib.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.geckolib.util.CustomItemGeoModel;
import net.unusual.block_factorys_bosses.item.UnderworldArenaKeyItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class UnderworldArenaKeyRenderer extends GeoItemRenderer<UnderworldArenaKeyItem> {
   public UnderworldArenaKeyRenderer() {
      super(new CustomItemGeoModel(BossesRise.prefix("underworld_arena_key")).withAltTexture(BossesRise.prefix("underworld_arena_key_model")));
   }

   public RenderType getRenderType(UnderworldArenaKeyItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityCutout(texture);
   }

   public void renderByItem(
      ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      if (transformType != ItemDisplayContext.FIRST_PERSON_LEFT_HAND && transformType != ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
         super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
      } else {
         poseStack.pushPose();
         poseStack.translate(0.25, -0.05, -0.25);
         poseStack.mulPose(Axis.XP.rotationDegrees(15.0F));
         poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
         poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
         poseStack.scale(1.0F, 1.0F, 1.0F);
         super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
         poseStack.popPose();
      }
   }
}
