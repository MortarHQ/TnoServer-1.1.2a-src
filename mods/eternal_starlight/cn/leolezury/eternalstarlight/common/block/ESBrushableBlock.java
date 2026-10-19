package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.ESBrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ESBrushableBlock extends BrushableBlock {
   public ESBrushableBlock(Block turnsInto, SoundEvent brushSound, SoundEvent brushCompletedSound, Properties properties) {
      super(turnsInto, brushSound, brushCompletedSound, properties);
   }

   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new ESBrushableBlockEntity(blockPos, blockState);
   }
}
