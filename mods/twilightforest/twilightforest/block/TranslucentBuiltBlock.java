package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import twilightforest.init.TFSounds;

public class TranslucentBuiltBlock extends Block {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

   public TranslucentBuiltBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE});
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(ACTIVE)) {
         level.removeBlock(pos, false);
         level.playSound(null, pos, (SoundEvent)TFSounds.BUILDER_REPLACE.get(), SoundSource.BLOCKS, 0.3F, 0.5F);

         for (Direction e : Direction.values()) {
            BuilderBlock.activateBuiltBlocks(level, pos.relative(e));
         }
      }
   }
}
