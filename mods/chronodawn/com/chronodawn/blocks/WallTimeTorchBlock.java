package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WallTimeTorchBlock extends WallTorchBlock {
   public static final MapCodec<WallTimeTorchBlock> CODEC = simpleCodec(props -> new WallTimeTorchBlock(ParticleTypes.FLAME, props));

   public WallTimeTorchBlock(SimpleParticleType particle, Properties properties) {
      super(particle, properties);
   }

   public MapCodec<WallTorchBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties(String blockId) {
      return Properties.of().noCollission().noOcclusion().instabreak().lightLevel(state -> 12).sound(SoundType.WOOD);
   }
}
