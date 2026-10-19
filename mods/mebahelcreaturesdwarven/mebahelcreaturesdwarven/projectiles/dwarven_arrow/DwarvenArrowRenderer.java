package mebahelcreaturesdwarven.projectiles.dwarven_arrow;

import mebahelcreaturesdwarven.entity.projectile.DwarvenArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_5617.class_5618;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtil;

@Environment(EnvType.CLIENT)
public class DwarvenArrowRenderer extends GeoEntityRenderer<DwarvenArrowEntity> {
   public DwarvenArrowRenderer(class_5618 renderManager) {
      super(renderManager, new DwarvenArrowModel());
   }

   public class_1921 getRenderType(DwarvenArrowEntity animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
      return super.getRenderType(animatable, texture, bufferSource, partialTick);
   }

   public void preRender(
      class_4587 poseStack,
      DwarvenArrowEntity animatable,
      BakedGeoModel model,
      class_4597 bufferSource,
      class_4588 buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      poseStack.method_22905(1.25F, 1.25F, 1.25F);
      RenderUtil.faceRotation(poseStack, animatable, partialTick);
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
   }
}
