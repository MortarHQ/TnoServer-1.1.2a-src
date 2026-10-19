package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFParticleType;

public class FireflySpawnerBlock extends AbstractParticleSpawnerBlock implements SimpleWaterloggedBlock {
   private static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public FireflySpawnerBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(WATERLOGGED, false));
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
      return SHAPE;
   }

   @Override
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
      super.animateTick(state, level, pos, rand);
      if (rand.nextInt(5) == 0) {
         double dx = pos.getX() + ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);
         double dy = pos.getY() + 0.4F + (rand.nextFloat() - rand.nextFloat()) * 0.3F;
         double dz = pos.getZ() + ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);
         level.addParticle((ParticleOptions)TFParticleType.FIREFLY.get(), dx, dy, dz, 0.0, 0.0, 0.0);
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (stack.getItem() == ((Block)TFBlocks.FIREFLY.get()).asItem() && !player.isShiftKeyDown() && (Integer)state.getValue(RADIUS) < 10) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(RADIUS, (Integer)state.getValue(RADIUS) + 1));
         stack.consume(1, player);
         player.displayClientMessage(
            Component.translatable("misc.twilightforest.firefly_spawner_radius", new Object[]{(Integer)state.getValue(RADIUS) + 1}), true
         );
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else if (player.isShiftKeyDown() && (Integer)state.getValue(RADIUS) > 1) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(RADIUS, (Integer)state.getValue(RADIUS) - 1));
         ItemEntity bug = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack((ItemLike)TFBlocks.FIREFLY.get()));
         level.addFreshEntity(bug);
         player.displayClientMessage(
            Component.translatable("misc.twilightforest.firefly_spawner_radius", new Object[]{(Integer)state.getValue(RADIUS) - 1}), true
         );
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   @Override
   public ParticleOptions getParticlesToSpawn() {
      return (ParticleOptions)TFParticleType.PARTICLE_SPAWNER_FIREFLY.get();
   }

   @Override
   public int getParticleCountPerSpawn(BlockState state) {
      return (int)Math.ceil(((Integer)state.getValue(RADIUS)).intValue() / 2.0);
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      boolean flag = fluidstate.is(Fluids.WATER);
      return (BlockState)super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{WATERLOGGED}));
   }
}
