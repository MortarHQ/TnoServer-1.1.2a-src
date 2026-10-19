package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.blocks.tiles.BlockEntityGratedChute;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockGratedChute extends BlockContainerImpl implements ICustomBlockState, ICustomItemModel, IPickaxeBreakable {
   public static final DirectionProperty FACING = HopperBlock.FACING;
   private static final VoxelShape INSIDE = Block.box(2.0, 11.0, 2.0, 14.0, 16.0, 14.0);
   private static final VoxelShape INPUT_SHAPE = Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape MIDDLE_SHAPE = Block.box(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
   private static final VoxelShape INPUT_MIDDLE_SHAPE = Shapes.or(MIDDLE_SHAPE, INPUT_SHAPE);
   private static final VoxelShape COMBINED_SHAPE = Shapes.join(INPUT_MIDDLE_SHAPE, INSIDE, BooleanOp.ONLY_FIRST);
   private static final VoxelShape DOWN_SHAPE = Shapes.or(COMBINED_SHAPE, Block.box(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
   private static final VoxelShape EAST_SHAPE = Shapes.or(COMBINED_SHAPE, Block.box(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
   private static final VoxelShape NORTH_SHAPE = Shapes.or(COMBINED_SHAPE, Block.box(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
   private static final VoxelShape SOUTH_SHAPE = Shapes.or(COMBINED_SHAPE, Block.box(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
   private static final VoxelShape WEST_SHAPE = Shapes.or(COMBINED_SHAPE, Block.box(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
   private static final VoxelShape DOWN_RAYTRACE_SHAPE = INSIDE;
   private static final VoxelShape EAST_RAYTRACE_SHAPE = Shapes.or(INSIDE, Block.box(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
   private static final VoxelShape NORTH_RAYTRACE_SHAPE = Shapes.or(INSIDE, Block.box(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
   private static final VoxelShape SOUTH_RAYTRACE_SHAPE = Shapes.or(INSIDE, Block.box(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
   private static final VoxelShape WEST_RAYTRACE_SHAPE = Shapes.or(INSIDE, Block.box(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

   public BlockGratedChute() {
      super("grated_chute", BlockEntityGratedChute.class, Properties.of().strength(3.0F, 8.0F).sound(SoundType.METAL));
   }

   @Override
   protected boolean hasWaterlogging() {
      return true;
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case DOWN -> DOWN_SHAPE;
         case NORTH -> NORTH_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         case EAST -> EAST_SHAPE;
         default -> COMBINED_SHAPE;
      };
   }

   public VoxelShape getInteractionShape(BlockState state, BlockGetter levelIn, BlockPos pos) {
      return switch ((Direction)state.getValue(FACING)) {
         case DOWN -> DOWN_RAYTRACE_SHAPE;
         case NORTH -> NORTH_RAYTRACE_SHAPE;
         case SOUTH -> SOUTH_RAYTRACE_SHAPE;
         case WEST -> WEST_RAYTRACE_SHAPE;
         case EAST -> EAST_RAYTRACE_SHAPE;
         default -> INSIDE;
      };
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.getBlockEntity(pos) instanceof BlockEntityGratedChute chute) {
         if (!level.isClientSide) {
            chute.isBlacklist = !chute.isBlacklist;
            chute.sendToClients();
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction newFacing = context.getClickedFace().getOpposite();
      if (newFacing == Direction.UP) {
         newFacing = Direction.DOWN;
      }

      return (BlockState)super.getStateForPlacement(context).setValue(FACING, newFacing);
   }

   @Override
   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState blockState, Level levelIn, BlockPos pos) {
      BlockEntity tile = levelIn.getBlockEntity(pos);
      if (tile instanceof BlockEntityGratedChute) {
         IItemHandler handler = (IItemHandler)levelIn.getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
         ItemStack stack = handler.getStackInSlot(0);
         return stack.isEmpty() ? 0 : Mth.ceil((float)stack.getCount() / stack.getMaxStackSize() * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING});
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      generator.withExistingParent(this.getBaseName(), generator.modLoc("block/" + this.getBaseName() + "_down"));
   }
}
