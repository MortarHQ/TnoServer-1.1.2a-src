package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class IceCubeRenderer extends MobRenderer<IceCubeEntity, IceCubeModel<IceCubeEntity>> {
   public static final ResourceLocation RESOURCE_LOCATION = YungsCaveBiomesCommon.id("textures/entity/ice_cube/ice_cube.png");
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(YungsCaveBiomesCommon.id("ice_cube"), "main");

   public IceCubeRenderer(Context context) {
      super(context, new IceCubeModel(context.bakeLayer(LAYER_LOCATION)), 0.7F);
   }

   protected void scale(IceCubeEntity iceCube, PoseStack poseStack, float f) {
      poseStack.scale(0.999F, 0.999F, 0.999F);
      poseStack.translate(0.0, 0.001F, 0.0);
      float iceCubeSize = 1.0F;
      float i = Mth.lerp(f, iceCube.oSquish, iceCube.squish) / (iceCubeSize * 0.5F + 1.0F);
      float j = 1.0F / (i + 1.0F);
      poseStack.scale(j * iceCubeSize, 1.0F / j * iceCubeSize, j * iceCubeSize);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull IceCubeEntity entity) {
      return RESOURCE_LOCATION;
   }
}
