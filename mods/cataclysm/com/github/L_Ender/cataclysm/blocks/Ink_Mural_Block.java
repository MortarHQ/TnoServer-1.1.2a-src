package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;

public class Ink_Mural_Block extends Mural_Block {
   private static final List<DeferredBlock<Block>> RANDOM_INK = (List<DeferredBlock<Block>>)Util.make(Lists.newArrayList(), list -> {
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_URCHINKIN);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_CINDARIA);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_HIPPOCAMTUS);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_CLAWDIAN);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_THUNDER);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_SEA);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_UNDERWORLD);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_HARVEST);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_WISDOM);
      list.add(ModBlocks.AZURE_SEASTONE_MURAL_SMITHING);
   });

   public Ink_Mural_Block(Properties properties) {
      super(properties);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!stack.is(Items.INK_SAC) && !stack.is(Items.WET_SPONGE)) {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }

      if (level.isClientSide) {
         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      }

      Item item = stack.getItem();
      boolean water = (Boolean)state.getValue(WATERLOGGED);
      AttachFace face = (AttachFace)state.getValue(FACE);
      Direction facing = (Direction)state.getValue(FACING);
      if (stack.is(Items.INK_SAC)) {
         stack.consume(1, player);
         BlockState mural = (BlockState)((BlockState)((BlockState)((Block)((DeferredBlock)Util.getRandom(RANDOM_INK, level.getRandom())).get())
                  .defaultBlockState()
                  .setValue(WATERLOGGED, water))
               .setValue(FACE, face))
            .setValue(FACING, facing);
         level.setBlockAndUpdate(pos, mural);
      }

      if (level.getBlockState(pos).getBlock() != ModBlocks.AZURE_SEASTONE_MURAL_EMPTY.get() && stack.is(Items.WET_SPONGE)) {
         level.setBlockAndUpdate(
            pos,
            (BlockState)((BlockState)((BlockState)((Block)ModBlocks.AZURE_SEASTONE_MURAL_EMPTY.get()).defaultBlockState().setValue(WATERLOGGED, water))
                  .setValue(FACE, face))
               .setValue(FACING, facing)
         );
      }

      player.awardStat(Stats.ITEM_USED.get(item));
      return ItemInteractionResult.sidedSuccess(level.isClientSide);
   }
}
