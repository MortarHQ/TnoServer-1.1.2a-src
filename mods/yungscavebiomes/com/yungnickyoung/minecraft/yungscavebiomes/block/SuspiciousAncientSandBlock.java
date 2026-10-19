package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.SuspiciousAncientSandBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
public class SuspiciousAncientSandBlock extends BrushableBlock {
   public SuspiciousAncientSandBlock(Properties properties, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
      super((Block)BlockModule.ANCIENT_SAND.get(), brushingSound, brushingCompleteSound, properties);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new SuspiciousAncientSandBlockEntity(blockPos, blockState);
   }
}
