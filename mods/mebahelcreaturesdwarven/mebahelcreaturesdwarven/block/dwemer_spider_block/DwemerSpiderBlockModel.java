package mebahelcreaturesdwarven.block.dwemer_spider_block;

import mebahelcreaturesdwarven.block.block.custom.DwemerSpiderBlock;
import mebahelcreaturesdwarven.block.entity.custom.DwemerSpiderBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class DwemerSpiderBlockModel extends GeoModel<DwemerSpiderBlockEntity> {
   public class_2960 getModelResource(DwemerSpiderBlockEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwarven_spider_block.geo.json");
   }

   public class_2960 getTextureResource(DwemerSpiderBlockEntity animatable) {
      int state = (Integer)animatable.method_11010().method_11654(DwemerSpiderBlock.STATE);

      return switch (state) {
         case 1 -> class_2960.method_60655("mebahelcreaturesdwarven", "textures/block/dwarven_spider_block_damaged.png");
         case 2 -> class_2960.method_60655("mebahelcreaturesdwarven", "textures/block/dwarven_spider_block_broken.png");
         default -> class_2960.method_60655("mebahelcreaturesdwarven", "textures/block/dwarven_spider_block_intact.png");
      };
   }

   public class_2960 getAnimationResource(DwemerSpiderBlockEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwarven_spider.animation.json");
   }
}
