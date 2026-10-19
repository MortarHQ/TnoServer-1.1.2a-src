package com.chronodawn.blocks;

import com.chronodawn.worldgen.features.AncientTimeWoodTreeFeature;
import java.util.Optional;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class AncientTimeWoodSapling extends SaplingBlock {
   private static final TreeGrower ANCIENT_TIME_WOOD_TREE_GROWER = new TreeGrower(
      "ancient_time_wood", Optional.empty(), Optional.of(AncientTimeWoodTreeFeature.ANCIENT_TIME_WOOD_TREE), Optional.empty()
   );

   public AncientTimeWoodSapling(Properties properties) {
      super(ANCIENT_TIME_WOOD_TREE_GROWER, properties);
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
