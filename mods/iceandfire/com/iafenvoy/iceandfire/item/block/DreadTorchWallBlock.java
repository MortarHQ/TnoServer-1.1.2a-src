package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.item.block.util.DreadBlock;
import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class DreadTorchWallBlock extends WallTorchBlock implements DreadBlock {
   public DreadTorchWallBlock() {
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
      Direction direction = (Direction)stateIn.getValue(FACING);
      double d0 = pos.getX() + 0.5;
      double d1 = pos.getY() + 0.7;
      double d2 = pos.getZ() + 0.5;
      Direction direction1 = direction.getOpposite();
      worldIn.addParticle(
         (ParticleOptions)IafParticles.DREAD_TORCH.get(), d0 + 0.27 * direction1.getStepX(), d1 + 0.22, d2 + 0.27 * direction1.getStepZ(), 0.0, 0.0, 0.0
      );
   }
}
