package mebahelcreaturesdwarven.entity.dwarven_spider;

import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class DwarvenSpidersModel extends GeoModel<DwemerSpiderEntity> {
   private static final class_2960 TEXTURE = class_2960.method_60655("mebahelcreaturesdwarven", "textures/entity/dwarven_spider/dwarven_spider_texture.png");

   public class_2960 getModelResource(DwemerSpiderEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwarven_spider.geo.json");
   }

   public class_2960 getTextureResource(DwemerSpiderEntity animatable) {
      return TEXTURE;
   }

   public class_2960 getAnimationResource(DwemerSpiderEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwarven_spider.animation.json");
   }
}
