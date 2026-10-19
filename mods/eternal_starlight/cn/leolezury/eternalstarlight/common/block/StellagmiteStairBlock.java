package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class StellagmiteStairBlock extends StairBlock implements Stellagmite {
   public static final MapCodec<StellagmiteStairBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(BlockState.CODEC.fieldOf("base_state").forGetter(stairBlock -> stairBlock.baseState), propertiesCodec())
         .apply(instance, StellagmiteStairBlock::new)
   );

   public StellagmiteStairBlock(BlockState blockState, Properties properties) {
      super(blockState, properties);
   }

   public MapCodec<StellagmiteStairBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.getPlacementState(context, super.getStateForPlacement(context));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult
   ) {
      return this.use(itemStack, blockState, level, blockPos, player, interactionHand);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      BlockState newState = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
      return this.updateShape(newState, level, pos);
   }

   public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
      this.step(entity);
   }
}
