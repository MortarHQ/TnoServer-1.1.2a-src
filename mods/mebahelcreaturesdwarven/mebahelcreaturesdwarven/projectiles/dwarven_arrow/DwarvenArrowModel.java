package mebahelcreaturesdwarven.projectiles.dwarven_arrow;

import mebahelcreaturesdwarven.entity.projectile.DwarvenArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class DwarvenArrowModel extends GeoModel<DwarvenArrowEntity> {
   public class_2960 getModelResource(DwarvenArrowEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwarven_arrow.geo.json");
   }

   public class_2960 getTextureResource(DwarvenArrowEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "textures/entity/dwarven_arrow.png");
   }

   public class_2960 getAnimationResource(DwarvenArrowEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/venom_entity.animation.json");
   }
}
