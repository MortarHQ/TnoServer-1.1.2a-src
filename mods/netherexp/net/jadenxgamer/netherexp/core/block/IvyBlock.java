package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class IvyBlock extends GenericMultiFaceBlock {
   public static final BooleanProperty HELIX = BooleanProperty.create("helix");
   private final Supplier<Item> helix;

   public IvyBlock(Supplier<Item> helix, Properties properties) {
      super(properties);
      this.helix = helix;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(HELIX, false));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if ((Boolean)state.getValue(HELIX)) {
         if (stack.is(Items.SHEARS)) {
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }

            level.setBlock(pos, (BlockState)state.cycle(HELIX), 3);
            Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack((ItemLike)this.helix.get()));
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (stack.is(this.helix.get())) {
         level.playSound(player, pos, this.soundType.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }

         level.setBlock(pos, (BlockState)state.cycle(HELIX), 3);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      }

      return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   @Override
   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return state.getValue(HELIX) ? true : super.isValidBonemealTarget(level, pos, state);
   }

   @Override
   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      if ((Boolean)state.getValue(HELIX)) {
         Direction direction = Direction.getRandom(random);
         BlockState adjacentState = level.getBlockState(pos.relative(direction));
         if (adjacentState.is(this) && !(Boolean)adjacentState.getValue(HELIX)) {
            level.setBlock(pos.relative(direction), (BlockState)adjacentState.setValue(HELIX, true), 3);
         }
      } else {
         super.performBonemeal(level, random, pos, state);
      }
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{HELIX});
   }
}
