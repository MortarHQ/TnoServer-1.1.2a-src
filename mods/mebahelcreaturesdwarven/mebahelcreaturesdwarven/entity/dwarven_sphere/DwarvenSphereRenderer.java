package mebahelcreaturesdwarven.entity.dwarven_sphere;

import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4597;
import net.minecraft.class_5617.class_5618;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class DwarvenSphereRenderer extends GeoEntityRenderer<DwemerSphereEntity> {
   public DwarvenSphereRenderer(class_5618 renderManager) {
      super(renderManager, new DwarvenSphereModel());
      this.field_4673 = 0.32F;
   }

   public class_1921 getRenderType(DwemerSphereEntity animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
      return super.getRenderType(animatable, texture, bufferSource, partialTick);
   }

   public float getMotionAnimThreshold(DwemerSphereEntity animatable) {
      return 0.008F;
   }
}
