package com.benji.netherman.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class AzazelTier implements Tier {
   public static final AzazelTier INSTANCE = new AzazelTier();

   public int getUses() {
      return 4200;
   }

   public float getSpeed() {
      return 9.0F;
   }

   public float getAttackDamageBonus() {
      return 0.0F;
   }

   public TagKey<Block> getIncorrectBlocksForDrops() {
      return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
   }

   public int getEnchantmentValue() {
      return 22;
   }

   public Ingredient getRepairIngredient() {
      return Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT});
   }
}
