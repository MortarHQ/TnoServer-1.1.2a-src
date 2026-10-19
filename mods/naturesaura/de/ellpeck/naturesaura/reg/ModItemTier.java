package de.ellpeck.naturesaura.reg;

import de.ellpeck.naturesaura.items.ModItems;
import java.util.function.Supplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.Lazy;

public enum ModItemTier implements Tier {
   INFUSED(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 16, () -> Ingredient.of(new ItemLike[]{ModItems.INFUSED_IRON})),
   SKY(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 8.0F, 3.0F, 12, () -> Ingredient.of(new ItemLike[]{ModItems.SKY_INGOT})),
   DEPTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2500, 10.0F, 5.0F, 18, () -> Ingredient.of(new ItemLike[]{ModItems.DEPTH_INGOT}));

   private final TagKey<Block> incorrectBlocksForDrops;
   private final int maxUses;
   private final float efficiency;
   private final float attackDamage;
   private final int enchantability;
   private final Lazy<Ingredient> repairMaterial;

   ModItemTier(
      TagKey<Block> incorrectBlocksForDrops,
      int maxUsesIn,
      float efficiencyIn,
      float attackDamageIn,
      int enchantabilityIn,
      Supplier<Ingredient> repairMaterialIn
   ) {
      this.incorrectBlocksForDrops = incorrectBlocksForDrops;
      this.maxUses = maxUsesIn;
      this.efficiency = efficiencyIn;
      this.attackDamage = attackDamageIn;
      this.enchantability = enchantabilityIn;
      this.repairMaterial = Lazy.of(repairMaterialIn);
   }

   public int getUses() {
      return this.maxUses;
   }

   public float getSpeed() {
      return this.efficiency;
   }

   public float getAttackDamageBonus() {
      return this.attackDamage;
   }

   public TagKey<Block> getIncorrectBlocksForDrops() {
      return this.incorrectBlocksForDrops;
   }

   public int getEnchantmentValue() {
      return this.enchantability;
   }

   public Ingredient getRepairIngredient() {
      return (Ingredient)this.repairMaterial.get();
   }
}
