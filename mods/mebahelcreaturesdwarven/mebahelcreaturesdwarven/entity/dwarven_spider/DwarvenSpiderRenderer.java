package mebahelcreaturesdwarven.entity.dwarven_spider;

import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4597;
import net.minecraft.class_5617.class_5618;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class DwarvenSpiderRenderer extends GeoEntityRenderer<DwemerSpiderEntity> {
   public DwarvenSpiderRenderer(class_5618 renderManager) {
      super(renderManager, new DwarvenSpidersModel());
      this.field_4673 = 0.32F;
   }

   public class_1921 getRenderType(DwemerSpiderEntity animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
      return super.getRenderType(animatable, texture, bufferSource, partialTick);
   }

   public float getMotionAnimThreshold(DwemerSpiderEntity animatable) {
      return 0.008F;
   }
}
