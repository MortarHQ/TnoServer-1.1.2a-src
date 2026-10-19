package com.chronodawn.blocks;

import com.chronodawn.worldgen.features.FruitOfTimeTreeFeature;
import java.util.Optional;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TimeWoodSapling extends SaplingBlock {
   private static final TreeGrower TIME_WOOD_TREE_GROWER = new TreeGrower(
      "time_wood", Optional.empty(), Optional.of(FruitOfTimeTreeFeature.FRUIT_OF_TIME_TREE), Optional.empty()
   );

   public TimeWoodSapling(Properties properties) {
      super(TIME_WOOD_TREE_GROWER, properties);
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
