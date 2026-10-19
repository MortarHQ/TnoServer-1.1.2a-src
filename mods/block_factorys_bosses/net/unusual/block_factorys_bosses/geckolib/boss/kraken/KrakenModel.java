package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;

public class KrakenModel extends CustomEntityGeoModel<KrakenEntity> {
   public static final ResourceLocation KRAKEN_BASE_LOCATION = BossesRise.prefix("kraken");

   public KrakenModel() {
      super(KRAKEN_BASE_LOCATION);
   }
}
