package nadiendev.constructionwand.wand.undo;

import javax.annotation.Nullable;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.wand.WandItemUseContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;

public class PlaceSnapshot implements ISnapshot {
   private BlockState block;
   private final BlockPos pos;
   private final BlockItem item;
   private final BlockState supportingBlock;
   private final boolean targetMode;

   public PlaceSnapshot(BlockState block, BlockPos pos, BlockItem item, BlockState supportingBlock, boolean targetMode) {
      this.block = block;
      this.pos = pos;
      this.item = item;
      this.supportingBlock = supportingBlock;
      this.targetMode = targetMode;
   }

   public static PlaceSnapshot get(
      Level world,
      Player player,
      BlockHitResult rayTraceResult,
      BlockPos pos,
      BlockItem item,
      @Nullable BlockState supportingBlock,
      @Nullable WandOptions options
   ) {
      boolean targetMode = options != null && supportingBlock != null && options.direction.get() == WandOptions.DIRECTION.TARGET;
      BlockState blockState = getPlaceBlockstate(world, player, rayTraceResult, pos, item, supportingBlock, targetMode);
      return blockState == null ? null : new PlaceSnapshot(blockState, pos, item, supportingBlock, targetMode);
   }

   @Override
   public BlockPos getPos() {
      return this.pos;
   }

   @Override
   public BlockState getBlockState() {
      return this.block;
   }

   @Override
   public ItemStack getRequiredItems() {
      return new ItemStack(this.item);
   }

   @Override
   public boolean execute(Level world, Player player, BlockHitResult rayTraceResult) {
      this.block = getPlaceBlockstate(world, player, rayTraceResult, this.pos, this.item, this.supportingBlock, this.targetMode);
      return this.block == null ? false : WandUtil.placeBlock(world, player, this.block, this.pos, this.item);
   }

   @Override
   public boolean canRestore(Level world, Player player) {
      return true;
   }

   @Override
   public boolean restore(Level world, Player player) {
      return WandUtil.removeBlock(world, player, this.block, this.pos);
   }

   @Override
   public void forceRestore(Level world) {
      world.removeBlock(this.pos, false);
   }

   @Nullable
   private static BlockState getPlaceBlockstate(
      Level world, Player player, BlockHitResult rayTraceResult, BlockPos pos, BlockItem item, @Nullable BlockState supportingBlock, boolean targetMode
   ) {
      BlockPlaceContext ctx = new WandItemUseContext(world, player, rayTraceResult, pos, item);
      if (!ctx.canPlace()) {
         return null;
      }

      BlockState blockState = item.getBlock().getStateForPlacement(ctx);
      if (blockState == null || !blockState.canSurvive(world, pos)) {
         return null;
      }

      if (!WandUtil.isTEAllowed(blockState)) {
         return null;
      }

      if (WandUtil.entitiesCollidingWithBlock(world, blockState, pos)) {
         return null;
      }

      if (targetMode && supportingBlock != null) {
         for (Property property : new Property[]{
            BlockStateProperties.HORIZONTAL_FACING,
            BlockStateProperties.FACING,
            BlockStateProperties.FACING_HOPPER,
            BlockStateProperties.ROTATION_16,
            BlockStateProperties.AXIS,
            BlockStateProperties.HALF,
            BlockStateProperties.STAIRS_SHAPE
         }) {
            if (supportingBlock.hasProperty(property) && blockState.hasProperty(property)) {
               blockState = (BlockState)blockState.setValue(property, supportingBlock.getValue(property));
            }
         }

         if (supportingBlock.hasProperty(BlockStateProperties.SLAB_TYPE) && blockState.hasProperty(BlockStateProperties.SLAB_TYPE)) {
            SlabType slabType = (SlabType)supportingBlock.getValue(BlockStateProperties.SLAB_TYPE);
            if (slabType != SlabType.DOUBLE) {
               blockState = (BlockState)blockState.setValue(BlockStateProperties.SLAB_TYPE, slabType);
            }
         }
      }

      return blockState;
   }
}
