package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import twilightforest.init.TFSounds;

public class EncasedSmokerBlock extends TFSmokerBlock {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

   public EncasedSmokerBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE});
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide()) {
         boolean powered = level.hasNeighborSignal(pos);
         if (!(Boolean)state.getValue(ACTIVE) && powered) {
            level.setBlock(pos, (BlockState)state.setValue(ACTIVE, true), 3);
            level.playSound(null, pos, (SoundEvent)TFSounds.SMOKER_START.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
         }

         if ((Boolean)state.getValue(ACTIVE) && !powered) {
            level.setBlock(pos, (BlockState)state.setValue(ACTIVE, false), 3);
            level.playSound(null, pos, (SoundEvent)TFSounds.SMOKER_START.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
         }
      }
   }
}
