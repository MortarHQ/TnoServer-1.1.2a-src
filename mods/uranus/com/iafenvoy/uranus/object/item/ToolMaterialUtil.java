package com.iafenvoy.uranus.object.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ToolMaterialUtil {
   public static Tier of(int uses, float speed, float attackDamageBonus, int enchantmentLevel, ItemLike... repairIngredients) {
      return of(TagKey.create(Registries.BLOCK, ResourceLocation.parse("")), uses, speed, attackDamageBonus, enchantmentLevel, repairIngredients);
   }

   public static Tier of(TagKey<Block> inverseTag, int uses, float speed, float attackDamageBonus, int enchantmentLevel, ItemLike... repairIngredients) {
      return new Tier() {
         public int getUses() {
            return uses;
         }

         public float getSpeed() {
            return speed;
         }

         public float getAttackDamageBonus() {
            return attackDamageBonus;
         }

         public TagKey<Block> getIncorrectBlocksForDrops() {
            return inverseTag;
         }

         public int getEnchantmentValue() {
            return enchantmentLevel;
         }

         public Ingredient getRepairIngredient() {
            return Ingredient.of(repairIngredients);
         }
      };
   }
}
