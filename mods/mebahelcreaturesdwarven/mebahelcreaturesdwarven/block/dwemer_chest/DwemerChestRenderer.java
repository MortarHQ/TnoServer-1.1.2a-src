package mebahelcreaturesdwarven.block.dwemer_chest;

import mebahelcreaturesdwarven.block.entity.custom.DwemerChestBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5614.class_5615;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@Environment(EnvType.CLIENT)
public class DwemerChestRenderer extends GeoBlockRenderer<DwemerChestBlockEntity> {
   public DwemerChestRenderer(class_5615 context) {
      super(new DwemerChestModel());
   }
}
