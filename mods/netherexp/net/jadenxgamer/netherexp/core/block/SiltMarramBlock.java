package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;

public class SiltMarramBlock extends RootsBlock {
   public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");
   private final TagKey<Biome> homeBiomes;

   public SiltMarramBlock(TagKey<Biome> homeBiomes, Properties properties) {
      super(properties);
      this.homeBiomes = homeBiomes;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SHEARED, false));
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      Holder<Biome> biome = level.getBiome(pos);
      if (!(Boolean)state.getValue(SHEARED)) {
         if (!biome.is(this.homeBiomes)) {
            BlockState targetState = JNEBlocks.MOIST_SILTMARRAM.get().defaultBlockState();
            if (biome.is(JNETags.Biomes.SPAWNS_DAMP_VARIANT_STRIDERS_AND_SILTMARRAM)) {
               targetState = JNEBlocks.DAMP_SILTMARRAM.get().defaultBlockState();
            }

            if (biome.is(JNETags.Biomes.SPAWNS_DRY_VARIANT_STRIDERS_AND_SILTMARRAM)) {
               targetState = JNEBlocks.DRY_SILTMARRAM.get().defaultBlockState();
            }

            if (!state.equals(targetState)) {
               level.setBlock(pos, targetState, 3);
            }
         }
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (stack.canPerformAction(ItemAbilities.SHEARS_TRIM) && !(Boolean)state.getValue(SHEARED)) {
         level.setBlock(pos, (BlockState)state.setValue(SHEARED, true), 3);
         level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(JNETags.Blocks.SILTMARRAM_PLANTABLE_ON);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SHEARED});
   }
}
