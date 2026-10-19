package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEConfiguredFeatures;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.AdvancementGranter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class CerebrageSkullBlock extends AbstractHeadBlock implements BonemealableBlock {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_4;

   public CerebrageSkullBlock(Properties properties) {
      super(properties);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (stack.is(Items.BONE_MEAL)) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      int age = (Integer)state.getValue(AGE);
      if (age > 2) {
         if ((Integer)JNEConfigs.MAX_CEREBRAGE_DROPPED.get() > 0 && age == 3) {
            popResourceFromFace(
               level,
               pos,
               hitResult.getDirection(),
               new ItemStack(
                  (ItemLike)JNEItems.CEREBRAGE.get(),
                  level.random.nextInt((Integer)JNEConfigs.MIN_CEREBRAGE_DROPPED.get(), (Integer)JNEConfigs.MAX_CEREBRAGE_DROPPED.get())
               )
            );
         }

         if (age == 4 && !level.getBlockState(pos.above()).isAir()) {
            return ItemInteractionResult.FAIL;
         }

         if (level.random.nextDouble() < (Double)JNEConfigs.CEREBRAGE_SEEDS_DROP_CHANCE.get()) {
            popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack((ItemLike)JNEItems.CEREBRAGE_SEEDS.get(), 1));
         }

         level.setBlock(pos, (BlockState)state.setValue(AGE, 1), 2);
         level.playSound(null, pos, JNESoundEvents.CEREBRAGE_PLANT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (random.nextDouble() < (Double)JNEConfigs.CEREBRAGE_GROWTH_CHANCE.get()) {
         int age = (Integer)state.getValue(AGE);
         level.setBlock(pos, (BlockState)state.setValue(AGE, age + 1), 2);
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(AGE) < 3;
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{AGE});
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return state.getValue(AGE) == 4 ? level.getBlockState(pos.above()).isAir() : true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return random.nextInt(state.getValue(AGE) < 3 ? 8 : 2) == 0;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      int age = (Integer)state.getValue(AGE);
      if (age < 3) {
         level.setBlock(pos, (BlockState)state.setValue(AGE, age + 1), 2);
      } else if ((Boolean)JNEConfigs.CEREBRAGE_GROWS_BRAIN_TREES.get() && level.getBlockState(pos.above()).isAir()) {
         level.setBlock(pos, (BlockState)state.setValue(AGE, 4), 2);
         level.registryAccess()
            .registry(Registries.CONFIGURED_FEATURE)
            .flatMap(registry -> registry.getHolder(JNEConfiguredFeatures.CEREBRAGE_TREE))
            .ifPresent(reference -> ((ConfiguredFeature)reference.value()).place(level, level.getChunkSource().getGenerator(), random, pos));
         AdvancementGranter.grantPlayersInRadius(level, pos, JNECriteriaTriggers.GROW_CEREBRAGE_CLARET);
      }
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return new ItemStack((ItemLike)JNEItems.CEREBRAGE_SEEDS.get());
   }
}
