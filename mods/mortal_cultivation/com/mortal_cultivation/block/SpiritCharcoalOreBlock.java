package com.mortal_cultivation.block;

import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class SpiritCharcoalOreBlock extends Block {
   public SpiritCharcoalOreBlock(Properties properties) {
      super(properties);
   }

   public List<ItemStack> getDrops(BlockState state, Builder builder) {
      List<ItemStack> drops = new ArrayList<>();
      RandomSource random = builder.getLevel().getRandom();
      ItemStack tool = (ItemStack)builder.getParameter(LootContextParams.TOOL);
      int fortuneLevel = 0;
      if (!tool.isEmpty()) {
         fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(
            builder.getLevel().holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), tool
         );
      }

      int maxDrops = 1 + fortuneLevel;
      int dropCount = random.nextInt(maxDrops) + 1;

      for (int i = 0; i < dropCount; i++) {
         ItemStack drop = this.getRandomCharcoalDrop(random);
         if (!drop.isEmpty()) {
            drops.add(drop);
         }
      }

      return drops;
   }

   private ItemStack getRandomCharcoalDrop(RandomSource random) {
      int roll = random.nextInt(175);
      if (roll < 80) {
         return new ItemStack((ItemLike)CultivationItems.MUD_ESSENCE_CHARCOAL.get());
      } else if (roll < 130) {
         return new ItemStack((ItemLike)CultivationItems.YELLOW_ESSENCE_CHARCOAL.get());
      } else if (roll < 155) {
         return new ItemStack((ItemLike)CultivationItems.BLACK_ESSENCE_CHARCOAL.get());
      } else {
         return roll < 170
            ? new ItemStack((ItemLike)CultivationItems.VOID_ESSENCE_CHARCOAL.get())
            : new ItemStack((ItemLike)CultivationItems.PRIME_ESSENCE_CHARCOAL.get());
      }
   }

   public static ItemStack getRandomCharcoalDropStatic(RandomSource random) {
      int roll = random.nextInt(175);
      if (roll < 80) {
         return new ItemStack((ItemLike)CultivationItems.MUD_ESSENCE_CHARCOAL.get());
      } else if (roll < 130) {
         return new ItemStack((ItemLike)CultivationItems.YELLOW_ESSENCE_CHARCOAL.get());
      } else if (roll < 155) {
         return new ItemStack((ItemLike)CultivationItems.BLACK_ESSENCE_CHARCOAL.get());
      } else {
         return roll < 170
            ? new ItemStack((ItemLike)CultivationItems.VOID_ESSENCE_CHARCOAL.get())
            : new ItemStack((ItemLike)CultivationItems.PRIME_ESSENCE_CHARCOAL.get());
      }
   }
}
