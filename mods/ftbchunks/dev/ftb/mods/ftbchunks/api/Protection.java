package dev.ftb.mods.ftbchunks.api;

import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Protection {
   Protection EDIT_BLOCK = (player, pos, hand, chunk, entity) -> {
      BlockState blockState = player.level().getBlockState(pos);
      if (blockState.is(FTBChunksTags.Blocks.EDIT_WHITELIST_TAG)) {
         return ProtectionPolicy.ALLOW;
      } else {
         return chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.BLOCK_EDIT_MODE)
            ? ProtectionPolicy.ALLOW
            : ProtectionPolicy.CHECK;
      }
   };
   Protection INTERACT_BLOCK = (player, pos, hand, chunk, entity) -> {
      BlockState blockState = player.level().getBlockState(pos);
      if (blockState.is(FTBChunksTags.Blocks.INTERACT_WHITELIST_TAG)) {
         return ProtectionPolicy.ALLOW;
      } else {
         return chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.BLOCK_INTERACT_MODE)
            ? ProtectionPolicy.ALLOW
            : ProtectionPolicy.CHECK;
      }
   };
   Protection RIGHT_CLICK_ITEM = (player, pos, hand, chunk, entity) -> {
      ItemStack stack = player.getItemInHand(hand);
      if (!isFood(stack) && !isBeneficialPotion(stack) && !stack.is(FTBChunksTags.Items.RIGHT_CLICK_WHITELIST_TAG)) {
         if (chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.BLOCK_INTERACT_MODE)) {
            return ProtectionPolicy.ALLOW;
         } else {
            return chunk != null && stack.is(FTBChunksTags.Items.RIGHT_CLICK_BLACKLIST_TAG) ? ProtectionPolicy.DENY : ProtectionPolicy.ALLOW;
         }
      } else {
         return ProtectionPolicy.ALLOW;
      }
   };
   Protection EDIT_FLUID = (player, pos, hand, chunk, entity) -> chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.BLOCK_EDIT_MODE)
      ? ProtectionPolicy.ALLOW
      : ProtectionPolicy.CHECK;
   Protection INTERACT_ENTITY = (player, pos, hand, chunk, entity) -> {
      if (entity != null && entity.getType().is(FTBChunksTags.Entities.ENTITY_INTERACT_WHITELIST_TAG)) {
         return ProtectionPolicy.ALLOW;
      } else {
         return chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.ENTITY_INTERACT_MODE)
            ? ProtectionPolicy.ALLOW
            : ProtectionPolicy.CHECK;
      }
   };
   Protection ATTACK_NONLIVING_ENTITY = (player, pos, hand, chunk, entity) -> {
      if (entity != null && entity.getType().is(FTBChunksTags.Entities.NONLIVING_ENTITY_ATTACK_WHITELIST_TAG)) {
         return ProtectionPolicy.ALLOW;
      } else {
         return chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.NONLIVING_ENTITY_ATTACK_MODE)
            ? ProtectionPolicy.ALLOW
            : ProtectionPolicy.CHECK;
      }
   };
   Protection EDIT_AND_INTERACT_BLOCK = (player, pos, hand, chunk, entity) -> {
      BlockState blockState = player.level().getBlockState(pos);
      if (blockState.is(FTBChunksTags.Blocks.INTERACT_WHITELIST_TAG)) {
         return ProtectionPolicy.ALLOW;
      } else {
         return chunk != null && chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.BLOCK_EDIT_AND_INTERACT_MODE)
            ? ProtectionPolicy.ALLOW
            : ProtectionPolicy.CHECK;
      }
   };

   ProtectionPolicy getProtectionPolicy(ServerPlayer var1, BlockPos var2, InteractionHand var3, @Nullable ClaimedChunk var4, @Nullable Entity var5);

   static boolean isFood(ItemStack stack) {
      return stack.has(DataComponents.FOOD) && ((FoodProperties)stack.get(DataComponents.FOOD)).nutrition() > 0;
   }

   static boolean isBeneficialPotion(ItemStack stack) {
      return stack.has(DataComponents.POTION_CONTENTS)
         ? StreamSupport.<MobEffectInstance>stream(((PotionContents)stack.get(DataComponents.POTION_CONTENTS)).getAllEffects().spliterator(), false)
            .noneMatch(effect -> ((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL)
         : false;
   }
}
