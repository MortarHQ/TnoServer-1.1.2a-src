package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAuraDetector;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockAuraDetector extends BlockContainerImpl implements IPickaxeBreakable {
   public BlockAuraDetector() {
      super("aura_detector", BlockEntityAuraDetector.class, Properties.of().strength(2.0F).sound(SoundType.STONE));
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState blockState, Level levelIn, BlockPos pos) {
      return levelIn.getBlockEntity(pos) instanceof BlockEntityAuraDetector detector ? detector.redstonePower : 0;
   }
}
