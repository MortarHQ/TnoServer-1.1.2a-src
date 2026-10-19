package net.unusual.block_factorys_bosses.geckolib.boss.worm;

import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;

public class SandwormModel extends CustomEntityGeoModel<SandwormEntity> {
   public static final ResourceLocation SANDWORM_BASE_LOCATION = ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "sandworm");
   public static final ResourceLocation SANDWORM_DAMAGED_BASE_LOCATION = ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "sandworm_damaged");

   public SandwormModel() {
      super(SANDWORM_BASE_LOCATION);
   }
}
