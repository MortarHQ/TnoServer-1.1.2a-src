package mebahelcreaturesdwarven.entity.dwarven_centurion;

import mebahelcreaturesdwarven.entity.custom.DwemerCenturionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4597;
import net.minecraft.class_5617.class_5618;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class DwarvenCenturionRenderer extends GeoEntityRenderer<DwemerCenturionEntity> {
   public DwarvenCenturionRenderer(class_5618 renderManager) {
      super(renderManager, new DwarvenCenturionModel());
      this.field_4673 = 0.4F;
   }

   public class_1921 getRenderType(DwemerCenturionEntity animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
      return super.getRenderType(animatable, texture, bufferSource, partialTick);
   }

   public float getMotionAnimThreshold(DwemerCenturionEntity animatable) {
      return 0.008F;
   }
}
