package com.bobmowzie.mowziesmobs.client.render.item;

import com.bobmowzie.mowziesmobs.client.model.item.ModelSculptorStaff;
import com.bobmowzie.mowziesmobs.server.item.ItemSculptorStaff;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.Color;

public class RenderSculptorStaff extends GeoItemRenderer<ItemSculptorStaff> {
   public float disappearController = 0.0F;

   public RenderSculptorStaff() {
      super(new ModelSculptorStaff());
   }

   public Color getRenderColor(ItemSculptorStaff animatable, float partialTick, int packedLight) {
      return Color.ofRGBA(1.0F, 1.0F, 1.0F, 1.0F - this.disappearController);
   }

   public RenderType getRenderType(ItemSculptorStaff animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return this.disappearController > 0.0F ? RenderType.entityTranslucent(texture) : super.getRenderType(animatable, texture, bufferSource, partialTick);
   }

   public void renderByItem(
      ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
      Optional<GeoBone> disappearControllerBone = this.model.getBone("disappearController");
      disappearControllerBone.ifPresent(geoBone -> this.disappearController = geoBone.getPosX());
   }
}
