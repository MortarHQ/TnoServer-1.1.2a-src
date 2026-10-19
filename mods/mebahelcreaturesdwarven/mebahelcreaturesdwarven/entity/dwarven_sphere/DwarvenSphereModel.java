package mebahelcreaturesdwarven.entity.dwarven_sphere;

import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class DwarvenSphereModel extends GeoModel<DwemerSphereEntity> {
   private static final class_2960 TEXTURE = class_2960.method_60655("mebahelcreaturesdwarven", "textures/entity/dwarven_sphere/dwarven_sphere_texture.png");

   public class_2960 getModelResource(DwemerSphereEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwarven_sphere.geo.json");
   }

   public class_2960 getTextureResource(DwemerSphereEntity animatable) {
      return TEXTURE;
   }

   public class_2960 getAnimationResource(DwemerSphereEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwarven_sphere.animation.json");
   }
}
