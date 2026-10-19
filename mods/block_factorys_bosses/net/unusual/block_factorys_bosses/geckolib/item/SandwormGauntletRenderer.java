package net.unusual.block_factorys_bosses.geckolib.item;

import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.geckolib.util.CustomItemGeoModel;
import net.unusual.block_factorys_bosses.item.SandwormGauntletItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SandwormGauntletRenderer extends GeoItemRenderer<SandwormGauntletItem> {
   public SandwormGauntletRenderer() {
      super(new CustomItemGeoModel(BossesRise.prefix("sandworm_gauntlet")).withAltTexture(BossesRise.prefix("sandworm_gauntlet_model")));
   }
}
