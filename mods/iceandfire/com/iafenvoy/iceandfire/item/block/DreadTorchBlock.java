package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.item.block.util.DreadBlock;
import com.iafenvoy.iceandfire.item.block.util.WallBlock;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class DreadTorchBlock extends TorchBlock implements DreadBlock, WallBlock {
   public DreadTorchBlock() {
      super(
         ParticleTypes.DUST_PLUME,
         Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .ignitedByLava()
            .lightLevel(state -> 5)
            .sound(SoundType.STONE)
            .noOcclusion()
            .dynamicShape()
            .noCollission()
      );
   }

   public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
      super.animateTick(stateIn, worldIn, pos, rand);
      double d0 = pos.getX() + 0.5;
      double d1 = pos.getY() + 0.6;
      double d2 = pos.getZ() + 0.5;
      worldIn.addParticle((ParticleOptions)IafParticles.DREAD_TORCH.get(), d0, d1, d2, 0.0, 0.0, 0.0);
   }

   @Override
   public Block wallBlock() {
      return (Block)IafBlocks.DREAD_TORCH_WALL.get();
   }
}
