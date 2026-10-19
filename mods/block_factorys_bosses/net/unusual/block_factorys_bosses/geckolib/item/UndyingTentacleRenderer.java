package net.unusual.block_factorys_bosses.geckolib.item;

import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.geckolib.util.CustomItemGeoModel;
import net.unusual.block_factorys_bosses.item.UndyingTentacleItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class UndyingTentacleRenderer extends GeoItemRenderer<UndyingTentacleItem> {
   public UndyingTentacleRenderer() {
      super(new CustomItemGeoModel(BossesRise.prefix("undying_tentacle")).withAltTexture(BossesRise.prefix("undying_tentacle_model")));
   }
}
