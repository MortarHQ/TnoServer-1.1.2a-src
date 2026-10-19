package twilightforest.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
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

public class ClimbableHollowLogBlock extends HorizontalDirectionalBlock implements WaterloggedBlock {
   public static final EnumProperty<HollowLogVariants.Climbable> VARIANT = EnumProperty.create("variant", HollowLogVariants.Climbable.class);
   public static final MapCodec<ClimbableHollowLogBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("vertical_log").forGetter(o -> o.vertical), propertiesCodec())
         .apply(instance, ClimbableHollowLogBlock::new)
   );
   private static final VoxelShape LADDER_EAST = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   private static final VoxelShape LADDER_WEST = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape LADDER_SOUTH = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   private static final VoxelShape LADDER_NORTH = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   private static final VoxelShape HOLLOW_SHAPE = Shapes.join(Shapes.block(), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape HOLLOW_SHAPE_SOUTH = Shapes.or(HOLLOW_SHAPE, LADDER_SOUTH);
   private static final VoxelShape HOLLOW_SHAPE_NORTH = Shapes.or(HOLLOW_SHAPE, LADDER_NORTH);
   private static final VoxelShape HOLLOW_SHAPE_EAST = Shapes.or(HOLLOW_SHAPE, LADDER_EAST);
   private static final VoxelShape HOLLOW_SHAPE_WEST = Shapes.or(HOLLOW_SHAPE, LADDER_WEST);
   private static final VoxelShape COLLISION_SHAPE = Shapes.join(Shapes.block(), Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), BooleanOp.ONLY_FIRST);
   private static final VoxelShape COLLISION_SHAPE_SOUTH = Shapes.or(COLLISION_SHAPE, LADDER_SOUTH);
   private static final VoxelShape COLLISION_SHAPE_NORTH = Shapes.or(COLLISION_SHAPE, LADDER_NORTH);
   private static final VoxelShape COLLISION_SHAPE_EAST = Shapes.or(COLLISION_SHAPE, LADDER_EAST);
   private static final VoxelShape COLLISION_SHAPE_WEST = Shapes.or(COLLISION_SHAPE, LADDER_WEST);
   private final Holder<Block> vertical;

   public ClimbableHollowLogBlock(Holder<Block> vertical, Properties properties) {
      super(properties);
      this.vertical = vertical;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(VARIANT, HollowLogVariants.Climbable.VINE))
            .setValue(FACING, Direction.NORTH)
      );
   }

   private static boolean isInside(HitResult result, BlockPos pos) {
      Vec3 vec = result.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
      return 0.124 <= vec.x() && vec.x() <= 0.876 && 0.124 <= vec.z() && vec.z() <= 0.876;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape var10000;
      if (state.getValue(VARIANT) == HollowLogVariants.Climbable.VINE) {
         var10000 = HOLLOW_SHAPE;
      } else {
         switch ((Direction)state.getValue(FACING)) {
            case SOUTH:
               var10000 = HOLLOW_SHAPE_SOUTH;
               break;
            case WEST:
               var10000 = HOLLOW_SHAPE_WEST;
               break;
            case EAST:
               var10000 = HOLLOW_SHAPE_EAST;
               break;
            default:
               var10000 = HOLLOW_SHAPE_NORTH;
         }
      }

      return var10000;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      VoxelShape var10000;
      if (state.getValue(VARIANT) == HollowLogVariants.Climbable.VINE) {
         var10000 = COLLISION_SHAPE;
      } else {
         switch ((Direction)state.getValue(FACING)) {
            case SOUTH:
               var10000 = COLLISION_SHAPE_SOUTH;
               break;
            case WEST:
               var10000 = COLLISION_SHAPE_WEST;
               break;
            case EAST:
               var10000 = COLLISION_SHAPE_EAST;
               break;
            default:
               var10000 = COLLISION_SHAPE_NORTH;
         }
      }

      return var10000;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{VARIANT, FACING});
   }

   @Override
   public boolean isStateWaterlogged(BlockState state) {
      return state.getValue(VARIANT) == HollowLogVariants.Climbable.LADDER_WATERLOGGED;
   }

   @Override
   public BlockState setWaterlog(BlockState prior, boolean doWater) {
      return switch ((HollowLogVariants.Climbable)prior.getValue(VARIANT)) {
         case VINE -> doWater ? (BlockState)((Block)this.vertical.value()).defaultBlockState().setValue(VerticalHollowLogBlock.WATERLOGGED, true) : prior;
         case LADDER -> (BlockState)prior.setValue(VARIANT, HollowLogVariants.Climbable.LADDER_WATERLOGGED);
         case LADDER_WATERLOGGED -> (BlockState)prior.setValue(VARIANT, HollowLogVariants.Climbable.LADDER);
      };
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(VARIANT) == HollowLogVariants.Climbable.LADDER_WATERLOGGED ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
      if (!isInside(hit, pos)) {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      }

      if (stack.canPerformAction(ItemAbilities.SHEARS_HARVEST)) {
         HollowLogVariants.Climbable variant = (HollowLogVariants.Climbable)state.getValue(VARIANT);
         level.setBlock(
            pos,
            (BlockState)((Block)this.vertical.value())
               .defaultBlockState()
               .setValue(VerticalHollowLogBlock.WATERLOGGED, variant == HollowLogVariants.Climbable.LADDER_WATERLOGGED),
            3
         );
         level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.isCreative()) {
            stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            level.addFreshEntity(
               new ItemEntity(
                  level,
                  pos.getX() + 0.5,
                  pos.getY() + 0.5,
                  pos.getZ() + 0.5,
                  new ItemStack(variant == HollowLogVariants.Climbable.VINE ? Blocks.VINE : Blocks.LADDER)
               )
            );
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hit);
      }
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }
}
