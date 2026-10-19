package com.tom.storagemod.block;

import com.tom.storagemod.platform.Platform;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IPaintable {
   boolean paint(Level var1, BlockPos var2, BlockState var3);

   static BlockState readBlockState(Level level, CompoundTag tag) {
      HolderGetter<Block> holdergetter = (HolderGetter<Block>)(level != null ? level.holderLookup(Registries.BLOCK) : BuiltInRegistries.BLOCK.asLookup());
      return NbtUtils.readBlockState(holdergetter, tag);
   }

   default boolean tryScrape(ItemStack item, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand) {
      if (playerHasShieldUseIntent(player, hand)) {
         return false;
      }

      if (Platform.canScrapeWithItem(item, player, hand) && this.paint(world, pos, null)) {
         if (player instanceof ServerPlayer sp) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, pos, item);
         }

         world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (player != null) {
            item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
         }

         return true;
      } else {
         return false;
      }
   }

   static boolean playerHasShieldUseIntent(Player player, InteractionHand hand) {
      return hand.equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
   }
}
