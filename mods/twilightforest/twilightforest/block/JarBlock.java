package twilightforest.block;

import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity.WobbleStyle;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.block.entity.JarBlockEntity;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

public class JarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final MapCodec<JarBlock> CODEC = simpleCodec(JarBlock::new);
   private static final VoxelShape JAR = Block.box(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);
   private static final VoxelShape LID = Block.box(4.0, 14.0, 4.0, 12.0, 16.0, 12.0);
   private static final VoxelShape AABB = Shapes.or(JAR, LID);
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public JarBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(WATERLOGGED, false));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return AABB;
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState superState = super.getStateForPlacement(context);
      return superState == null
         ? null
         : (BlockState)superState.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{WATERLOGGED});
   }

   public Item getDefaultLid() {
      return TFBlocks.TWILIGHT_OAK_LOG.asItem();
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return level.getBlockEntity(pos) instanceof JarBlockEntity jarBlockEntity
         ? jarBlockEntity.getJarAsItem()
         : super.getCloneItemStack(state, target, level, pos, player);
   }

   protected List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder params) {
      if (params.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof JarBlockEntity jarBlockEntity) {
         params = params.withDynamicDrop(JarBlockEntity.JAR_LID, stackConsumer -> stackConsumer.accept(jarBlockEntity.lid.getDefaultInstance()));
      }

      return super.getDrops(state, params);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new JarBlockEntity(pos, state);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (level.getBlockEntity(pos) instanceof JarBlockEntity jarBlockEntity && hitResult.getLocation().y >= pos.getY() + 0.875) {
         Item lid = stack.getItem();
         if (lid != jarBlockEntity.lid && JarBlockEntity.REGISTERED_LOG_LIDS.get(lid) instanceof BooleanSupplier check && check.getAsBoolean()) {
            jarBlockEntity.lid = lid;
            if (level instanceof ServerLevel serverLevel) {
               serverLevel.playSound(null, pos, (SoundEvent)TFSounds.JAR_LID_SWAP.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
               jarBlockEntity.wobble(WobbleStyle.POSITIVE);
               jarBlockEntity.setChanged();
            }

            return ItemInteractionResult.SUCCESS;
         }
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.getBlockEntity(pos) instanceof JarBlockEntity blockEntity
         && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()
         && player.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
         if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, (SoundEvent)TFSounds.JAR_WIGGLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            blockEntity.wobble(WobbleStyle.NEGATIVE);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
