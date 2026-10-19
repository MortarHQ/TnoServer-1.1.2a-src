package com.chronodawn.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class AncientTimeWoodPressurePlate extends PressurePlateBlock {
   public AncientTimeWoodPressurePlate(Properties properties) {
      super(BlockSetType.OAK, properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY);
   }
}
