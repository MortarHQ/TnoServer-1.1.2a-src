package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import twilightforest.enums.HollowLogVariants;
import twilightforest.init.TFBlocks;
import twilightforest.util.AxisUtil;

public class HorizontalHollowLogBlock extends Block implements WaterloggedBlock {
   public static final EnumProperty<Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
   public static final EnumProperty<HollowLogVariants.Horizontal> VARIANT = EnumProperty.create("variant", HollowLogVariants.Horizontal.class);
   private static final VoxelShape HOLLOW_SHAPE_X = Shapes.join(Shapes.block(), Block.box(0.0, 2.0, 2.0, 16.0, 14.0, 14.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape HOLLOW_SHAPE_Z = Shapes.join(Shapes.block(), Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 16.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape CARPET_SHAPE_X = Shapes.join(Shapes.block(), Block.box(0.0, 3.0, 2.0, 16.0, 14.0, 14.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape CARPET_SHAPE_Z = Shapes.join(Shapes.block(), Block.box(2.0, 3.0, 0.0, 14.0, 14.0, 16.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape COLLISION_SHAPE_X = Shapes.join(Shapes.block(), Block.box(0.0, 1.0, 1.0, 16.0, 15.0, 15.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape COLLISION_SHAPE_Z = Shapes.join(Shapes.block(), Block.box(1.0, 1.0, 0.0, 15.0, 15.0, 16.0), BooleanOp.ONLY_FIRST);

   public HorizontalHollowLogBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(HORIZONTAL_AXIS, Axis.X))
            .setValue(VARIANT, HollowLogVariants.Horizontal.EMPTY)
      );
   }

   private static boolean canChangeVariant(HollowLogVariants.Horizontal variant, Level level, BlockPos pos, Axis axis) {
      return variant == HollowLogVariants.Horizontal.EMPTY
         || variant == HollowLogVariants.Horizontal.WATERLOGGED
            && level.getFluidState(pos.relative(AxisUtil.getAxisDirectionNegative(axis))).getType() != Fluids.WATER
            && level.getFluidState(pos.relative(AxisUtil.getAxisDirectionPositive(axis))).getType() != Fluids.WATER;
   }

   private static boolean isInside(HitResult result, Axis axis, BlockPos pos) {
      Vec3 vec = result.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
      if (!(0.124 > vec.y) && !(vec.y > 0.876)) {
         return switch (axis) {
            case X -> 0.124 <= vec.z && vec.z <= 0.876;
            case Z -> 0.124 <= vec.x && vec.x <= 0.876;
            default -> false;
         };
      } else {
         return false;
      }
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch ((HollowLogVariants.Horizontal)state.getValue(VARIANT)) {
         case EMPTY, WATERLOGGED -> {
            switch ((Axis)state.getValue(HORIZONTAL_AXIS)) {
               case X:
                  yield HOLLOW_SHAPE_X;
               case Z:
                  yield HOLLOW_SHAPE_Z;
               default:
                  yield Shapes.block();
            }
         }
         case MOSS, MOSS_AND_GRASS, SNOW -> {
            switch ((Axis)state.getValue(HORIZONTAL_AXIS)) {
               case X:
                  yield CARPET_SHAPE_X;
               case Z:
                  yield CARPET_SHAPE_Z;
               default:
                  yield Shapes.block();
            }
         }
      };
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch ((Axis)state.getValue(HORIZONTAL_AXIS)) {
         case X -> COLLISION_SHAPE_X;
         case Z -> COLLISION_SHAPE_Z;
         default -> Shapes.empty();
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{HORIZONTAL_AXIS, VARIANT});
   }

   @Override
   public boolean isStateWaterlogged(BlockState state) {
      return state.getValue(VARIANT) == HollowLogVariants.Horizontal.WATERLOGGED;
   }

   @Override
   public BlockState setWaterlog(BlockState prior, boolean doWater) {
      return (BlockState)prior.setValue(VARIANT, doWater ? HollowLogVariants.Horizontal.WATERLOGGED : HollowLogVariants.Horizontal.EMPTY);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)((BlockState)super.getStateForPlacement(context).setValue(HORIZONTAL_AXIS, context.getClickedFace().getAxis()))
         .setValue(
            VARIANT,
            context.getLevel().getBlockState(context.getClickedPos()).getFluidState().getType() == Fluids.WATER
               ? HollowLogVariants.Horizontal.WATERLOGGED
               : HollowLogVariants.Horizontal.EMPTY
         );
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(VARIANT) == HollowLogVariants.Horizontal.WATERLOGGED ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos) {
      if (this.isStateWaterlogged(state)) {
         accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, neighborState, accessor, pos, neighborPos);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      Axis stateAxis = (Axis)state.getValue(HORIZONTAL_AXIS);
      if (!isInside(hit, stateAxis, pos)) {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      }

      HollowLogVariants.Horizontal variant = (HollowLogVariants.Horizontal)state.getValue(VARIANT);
      if (stack.is(TFBlocks.MOSS_PATCH.asItem())) {
         if (canChangeVariant(variant, level, pos, stateAxis)) {
            level.setBlock(pos, (BlockState)state.setValue(VARIANT, HollowLogVariants.Horizontal.MOSS), 3);
            playPlaceSound(((Block)TFBlocks.MOSS_PATCH.get()).defaultBlockState(), level, pos, player);
            stack.consume(1, player);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (stack.is(Blocks.SHORT_GRASS.asItem())) {
         if (variant == HollowLogVariants.Horizontal.MOSS) {
            level.setBlock(pos, (BlockState)state.setValue(VARIANT, HollowLogVariants.Horizontal.MOSS_AND_GRASS), 3);
            playPlaceSound(Blocks.SHORT_GRASS.defaultBlockState(), level, pos, player);
            stack.consume(1, player);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (stack.is(Items.SNOWBALL)) {
         if (canChangeVariant(variant, level, pos, stateAxis)) {
            level.setBlock(pos, (BlockState)state.setValue(VARIANT, HollowLogVariants.Horizontal.SNOW), 3);
            playPlaceSound(Blocks.SNOW.defaultBlockState(), level, pos, player);
            stack.consume(1, player);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (stack.canPerformAction(ItemAbilities.SHOVEL_DIG)) {
         if (variant == HollowLogVariants.Horizontal.SNOW) {
            level.setBlock(pos, (BlockState)state.setValue(VARIANT, HollowLogVariants.Horizontal.EMPTY), 3);
            playBreakSound(Blocks.SNOW.defaultBlockState(), level, pos, player);
            if (!player.isCreative()) {
               stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
               level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Items.SNOWBALL)));
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (stack.canPerformAction(ItemAbilities.SHEARS_HARVEST)
         && (variant == HollowLogVariants.Horizontal.MOSS || variant == HollowLogVariants.Horizontal.MOSS_AND_GRASS)) {
         level.setBlock(pos, (BlockState)state.setValue(VARIANT, HollowLogVariants.Horizontal.EMPTY), 3);
         level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.isCreative()) {
            stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            level.addFreshEntity(
               new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack((ItemLike)TFBlocks.MOSS_PATCH.get()))
            );
            if (variant == HollowLogVariants.Horizontal.MOSS_AND_GRASS) {
               level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Blocks.SHORT_GRASS)));
            }
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      }

      return super.useItemOn(stack, state, level, pos, player, hand, hit);
   }

   public static void playPlaceSound(BlockState state, Level level, BlockPos pos, Player player) {
      SoundType soundtype = state.getSoundType(level, pos, player);
      level.playSound(null, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
   }

   public static void playBreakSound(BlockState state, Level level, BlockPos pos, Player player) {
      SoundType soundtype = state.getSoundType(level, pos, player);
      level.playSound(null, pos, soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      Axis stateAxis = (Axis)state.getValue(HORIZONTAL_AXIS);
      if (!isInside(hit, stateAxis, pos)) {
         return super.useWithoutItem(state, level, pos, player, hit);
      }

      if (player.isSecondaryUseActive()
         && player.getDirection().getAxis().equals(stateAxis)
         && player.blockPosition().getY() == pos.getY()
         && !player.isFallFlying()
         && player.position().distanceTo(Vec3.atBottomCenterOf(pos)) < 0.81) {
         player.setPose(Pose.SWIMMING);
      }

      return super.useWithoutItem(state, level, pos, player, hit);
   }
}
