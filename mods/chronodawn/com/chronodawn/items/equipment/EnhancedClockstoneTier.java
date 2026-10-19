package com.chronodawn.items.equipment;

import com.chronodawn.registry.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class EnhancedClockstoneTier implements Tier {
   public static final EnhancedClockstoneTier INSTANCE = new EnhancedClockstoneTier();

   private EnhancedClockstoneTier() {
   }

   public int getUses() {
      return 1200;
   }

   public float getSpeed() {
      return 7.5F;
   }

   public float getAttackDamageBonus() {
      return 3.0F;
   }

   public TagKey<Block> getIncorrectBlocksForDrops() {
      return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
   }

   public int getEnchantmentValue() {
      return 16;
   }

   public Ingredient getRepairIngredient() {
      return Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TIME_CRYSTAL.get()});
   }
}
