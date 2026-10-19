package com.chronodawn.items.equipment;

import com.chronodawn.registry.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ClockstoneTier implements Tier {
   public static final ClockstoneTier INSTANCE = new ClockstoneTier();

   private ClockstoneTier() {
   }

   public int getUses() {
      return 450;
   }

   public float getSpeed() {
      return 6.5F;
   }

   public float getAttackDamageBonus() {
      return 2.5F;
   }

   public TagKey<Block> getIncorrectBlocksForDrops() {
      return BlockTags.INCORRECT_FOR_IRON_TOOL;
   }

   public int getEnchantmentValue() {
      return 14;
   }

   public Ingredient getRepairIngredient() {
      return Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TIME_CRYSTAL.get()});
   }
}
