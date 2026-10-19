package mebahelcreaturesdwarven.block.dwemer_chest;

import mebahelcreaturesdwarven.block.entity.custom.DwemerChestBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class DwemerChestModel extends GeoModel<DwemerChestBlockEntity> {
   public class_2960 getModelResource(DwemerChestBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwemer_chest.geo.json");
   }

   public class_2960 getTextureResource(DwemerChestBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "textures/block/dwemer_chest.png");
   }

   public class_2960 getAnimationResource(DwemerChestBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwemer_chest.animation.json");
   }
}
