package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LesionBlock extends Block {
   public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 4);
   protected static final VoxelShape SHAPE_4 = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SHAPE_3 = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected static final VoxelShape SHAPE_2 = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape SHAPE_1 = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   private final Supplier<Item> lesionOf;

   public LesionBlock(Supplier<Item> lesionOf, Properties properties) {
      super(properties);
      this.lesionOf = lesionOf;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SLICES, 4));
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(SLICES)) {
         case 1 -> SHAPE_1;
         case 2 -> SHAPE_2;
         case 3 -> SHAPE_3;
         default -> SHAPE_4;
      };
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (stack.isEmpty()) {
         int slices = (Integer)state.getValue(SLICES);
         if (slices == 1) {
            level.removeBlock(pos, false);
         } else {
            level.setBlock(pos, (BlockState)state.setValue(SLICES, slices - 1), 3);
         }

         popResourceFromFace(
            level, pos, hitResult.getDirection(), new ItemStack((ItemLike)this.lesionOf.get(), (Integer)JNEConfigs.LESION_DROPS_PER_HARVEST.get())
         );
         level.playSound(null, pos, JNESoundEvents.LESION_BLOCK_HARVEST.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(SLICES) < 4;
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!(random.nextDouble() > (Double)JNEConfigs.LESION_GROWTH_CHANCE.get())) {
         int slices = (Integer)state.getValue(SLICES);
         level.setBlock(pos, (BlockState)state.setValue(SLICES, slices + 1), 3);
         level.playSound(null, pos, JNESoundEvents.LESION_BLOCK_GROWS.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         level.levelEvent(2005, pos, 8);
      }
   }

   public boolean hasAnalogOutputSignal(BlockState blockState) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return (Integer)state.getValue(SLICES) * 3;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SLICES});
   }
}
