package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;

public class KrakenTentacleModel extends CustomEntityGeoModel<KrakenTentacleEntity> {
   private static final ResourceLocation KRAKEN_TENTACLE_BASE_LOCATION = BossesRise.prefix("kraken_tentacle");
   private static final ResourceLocation KRAKEN_TENTACLE_SLIM_BASE_LOCATION = BossesRise.prefix("kraken_tentacle_slim");
   public static final Vec3 ROOT_OFFSET = new Vec3(0.0, -11.0, -8.5);

   public KrakenTentacleModel(boolean isSlim) {
      super(isSlim ? KRAKEN_TENTACLE_SLIM_BASE_LOCATION : KRAKEN_TENTACLE_BASE_LOCATION);
      this.withAltTexture(KrakenModel.KRAKEN_BASE_LOCATION);
   }
}
