package com.chronodawn.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ReversingTimeSandstone extends Block {
   public ReversingTimeSandstone(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.SAND).strength(0.8F, 0.8F).sound(SoundType.STONE).pushReaction(PushReaction.BLOCK);
   }
}
