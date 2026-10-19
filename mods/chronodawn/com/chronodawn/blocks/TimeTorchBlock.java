package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TimeTorchBlock extends TorchBlock {
   public static final MapCodec<TimeTorchBlock> CODEC = simpleCodec(props -> new TimeTorchBlock(ParticleTypes.FLAME, props));

   public TimeTorchBlock(SimpleParticleType particle, Properties properties) {
      super(particle, properties);
   }

   public MapCodec<? extends TorchBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties(String blockId) {
      return Properties.of().noCollission().noOcclusion().instabreak().lightLevel(state -> 12).sound(SoundType.WOOD);
   }
}
