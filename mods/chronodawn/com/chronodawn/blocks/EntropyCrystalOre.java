package com.chronodawn.blocks;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class EntropyCrystalOre extends DropExperienceBlock {
   public EntropyCrystalOre(Properties properties) {
      super(UniformInt.of(2, 5), properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.STONE);
   }
}
