package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;

public class TorchberryPlantBlock extends TFPlantBlock implements BonemealableBlock {
   public static final BooleanProperty HAS_BERRIES = BooleanProperty.create("has_torchberries");
   public static final MapCodec<TorchberryPlantBlock> CODEC = simpleCodec(TorchberryPlantBlock::new);
   private static final VoxelShape TORCHBERRY_SHAPE = Block.box(1.0, 2.0, 1.0, 15.0, 16.0, 15.0);

   public TorchberryPlantBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(HAS_BERRIES, false));
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
      return TFPlantBlock.canPlaceRootAt(reader, pos);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return TORCHBERRY_SHAPE;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
      if ((Boolean)state.getValue(HAS_BERRIES)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(HAS_BERRIES, false));
         level.playSound(null, pos, (SoundEvent)TFSounds.PICKED_TORCHBERRIES.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         ItemEntity torchberries = new ItemEntity(level, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, new ItemStack((ItemLike)TFItems.TORCHBERRIES.get()));
         level.addFreshEntity(torchberries);
         if (player instanceof ServerPlayer) {
            player.awardStat((ResourceLocation)TFStats.TORCHBERRIES_HARVESTED.get());
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useWithoutItem(state, level, pos, player, result);
      }
   }

   public boolean isValidBonemealTarget(LevelReader getter, BlockPos pos, BlockState state) {
      return !(Boolean)state.getValue(HAS_BERRIES);
   }

   public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      level.setBlock(pos, (BlockState)state.setValue(HAS_BERRIES, true), 2);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{HAS_BERRIES});
   }
}
