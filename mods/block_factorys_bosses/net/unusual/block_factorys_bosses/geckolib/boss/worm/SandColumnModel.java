package net.unusual.block_factorys_bosses.geckolib.boss.worm;

import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;

public class SandColumnModel extends CustomEntityGeoModel<SandColumnEntity> {
   public SandColumnModel() {
      super(ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "sand_column"));
   }
}
