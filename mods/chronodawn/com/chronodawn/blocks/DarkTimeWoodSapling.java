package com.chronodawn.blocks;

import com.chronodawn.worldgen.features.DarkTimeWoodTreeFeature;
import java.util.Optional;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class DarkTimeWoodSapling extends SaplingBlock {
   private static final TreeGrower DARK_TIME_WOOD_TREE_GROWER = new TreeGrower(
      "dark_time_wood", Optional.empty(), Optional.of(DarkTimeWoodTreeFeature.DARK_TIME_WOOD_TREE), Optional.empty()
   );

   public DarkTimeWoodSapling(Properties properties) {
      super(DARK_TIME_WOOD_TREE_GROWER, properties);
   }

   public static Properties createProperties() {
      return Properties.of()
         .mapColor(MapColor.PLANT)
         .noCollission()
         .noOcclusion()
         .randomTicks()
         .instabreak()
         .sound(SoundType.GRASS)
         .pushReaction(PushReaction.DESTROY);
   }
}
