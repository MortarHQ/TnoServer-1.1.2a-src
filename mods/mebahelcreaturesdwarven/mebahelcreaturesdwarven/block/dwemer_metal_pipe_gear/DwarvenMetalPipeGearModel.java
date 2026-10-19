package mebahelcreaturesdwarven.block.dwemer_metal_pipe_gear;

import mebahelcreaturesdwarven.block.entity.custom.DwemerMetalPipeGearBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class DwarvenMetalPipeGearModel extends GeoModel<DwemerMetalPipeGearBlockEntity> {
   public class_2960 getModelResource(DwemerMetalPipeGearBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwemer_metal_pipe_gear.geo.json");
   }

   public class_2960 getTextureResource(DwemerMetalPipeGearBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "textures/block/dwemer_metal_pipe.png");
   }

   public class_2960 getAnimationResource(DwemerMetalPipeGearBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwemer_metal_pipe_gear.animation.json");
   }
}
