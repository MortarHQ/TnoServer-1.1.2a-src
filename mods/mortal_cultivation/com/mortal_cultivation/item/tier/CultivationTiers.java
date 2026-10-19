package com.mortal_cultivation.item.tier;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum CultivationTiers implements Tier {
   RUYI_JINGU_BANG(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 8888, 10.0F, 84.0F, 30, () -> Ingredient.EMPTY);

   private final TagKey<Block> incorrectBlocksForDrops;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final Supplier<Ingredient> repairIngredient;

   CultivationTiers(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
      this.incorrectBlocksForDrops = incorrectBlocksForDrops;
      this.uses = uses;
      this.speed = speed;
      this.damage = damage;
      this.enchantmentValue = enchantmentValue;
      this.repairIngredient = Suppliers.memoize(repairIngredient::get);
   }

   public int getUses() {
      return this.uses;
   }

   public float getSpeed() {
      return this.speed;
   }

   public float getAttackDamageBonus() {
      return this.damage;
   }

   public TagKey<Block> getIncorrectBlocksForDrops() {
      return this.incorrectBlocksForDrops;
   }

   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   @Nonnull
   public Ingredient getRepairIngredient() {
      return this.repairIngredient.get();
   }
}
