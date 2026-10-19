package dev.ftb.mods.ftbquests.block;

import com.mojang.serialization.MapCodec;
import dev.ftb.mods.ftbquests.block.entity.DetectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class DetectorBlock extends BaseEntityBlock {
   private static final MapCodec<DetectorBlock> CODEC = simpleCodec(DetectorBlock::new);
   public static final Properties PROPS = Properties.of().strength(0.3F);

   public DetectorBlock(Properties props) {
      super(props);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BlockStateProperties.POWERED, false));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new DetectorBlockEntity(blockPos, blockState);
   }

   protected MapCodec<DetectorBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState blockState) {
      return RenderShape.MODEL;
   }

   public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
      if (!level.isClientSide()) {
         boolean wasPowered = (Boolean)blockState.getValue(BlockStateProperties.POWERED);
         if (wasPowered != level.hasNeighborSignal(blockPos)) {
            level.setBlock(blockPos, (BlockState)blockState.setValue(BlockStateProperties.POWERED, !wasPowered), 2);
            if (!wasPowered && level.getBlockEntity(blockPos) instanceof DetectorBlockEntity dbe) {
               dbe.onPowered(level, blockPos);
            }
         }
      }
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
      super.setPlacedBy(level, pos, state, entity, stack);
      if (!level.isClientSide() && stack.has(DataComponents.CUSTOM_NAME) && level.getBlockEntity(pos) instanceof DetectorBlockEntity dbe) {
         dbe.update(stack.getHoverName().getString());
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BlockStateProperties.POWERED});
   }
}
