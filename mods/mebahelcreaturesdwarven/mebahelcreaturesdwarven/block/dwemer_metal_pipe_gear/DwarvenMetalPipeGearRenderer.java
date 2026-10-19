package mebahelcreaturesdwarven.block.dwemer_metal_pipe_gear;

import mebahelcreaturesdwarven.block.entity.custom.DwemerMetalPipeGearBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4597;
import net.minecraft.class_5614.class_5615;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@Environment(EnvType.CLIENT)
public class DwarvenMetalPipeGearRenderer extends GeoBlockRenderer<DwemerMetalPipeGearBlockEntity> {
   public DwarvenMetalPipeGearRenderer(class_5615 context) {
      super(new DwarvenMetalPipeGearModel());
   }

   public class_1921 getRenderType(DwemerMetalPipeGearBlockEntity animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
      return super.getRenderType(animatable, texture, bufferSource, partialTick);
   }
}
