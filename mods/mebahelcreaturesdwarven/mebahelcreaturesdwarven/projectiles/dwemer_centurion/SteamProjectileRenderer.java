package mebahelcreaturesdwarven.projectiles.dwemer_centurion;

import mebahelcreaturesdwarven.entity.projectile.SteamProjectileEntity;
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
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.RenderUtil;

@Environment(EnvType.CLIENT)
public class SteamProjectileRenderer extends GeoEntityRenderer<SteamProjectileEntity> {
   public SteamProjectileRenderer(class_5618 renderManager) {
      super(renderManager, new SteamProjectileModel());
      this.addRenderLayer(new AutoGlowingGeoLayer(this));
   }

   public class_1921 getRenderType(SteamProjectileEntity animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
      return super.getRenderType(animatable, texture, bufferSource, partialTick);
   }

   public void preRender(
      class_4587 poseStack,
      SteamProjectileEntity animatable,
      BakedGeoModel model,
      class_4597 bufferSource,
      class_4588 buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      poseStack.method_22905(0.7F, 0.7F, 0.7F);
      RenderUtil.faceRotation(poseStack, animatable, partialTick);
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
   }
}
