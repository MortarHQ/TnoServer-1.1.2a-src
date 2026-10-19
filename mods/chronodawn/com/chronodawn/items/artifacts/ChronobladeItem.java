package com.chronodawn.items.artifacts;

import com.chronodawn.registry.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ChronobladeItem extends SwordItem {
   public static final float AI_SKIP_CHANCE = 0.25F;
   public static final ChronobladeItem.ChronobladeTier TIER = new ChronobladeItem.ChronobladeTier();

   public ChronobladeItem(Properties properties) {
      super(TIER, properties.attributes(SwordItem.createAttributes(TIER, 4, -2.4F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(2000).rarity(Rarity.EPIC);
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (attacker.level().getRandom().nextFloat() < 0.25F) {
         ChronobladeAISkipHandler.applyAISkip(target);
      }

      return super.hurtEnemy(stack, target, attacker);
   }

   private static class ChronobladeTier implements Tier {
      public int getUses() {
         return 2000;
      }

      public float getSpeed() {
         return 9.0F;
      }

      public float getAttackDamageBonus() {
         return 3.5F;
      }

      public TagKey<Block> getIncorrectBlocksForDrops() {
         return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
      }

      public int getEnchantmentValue() {
         return 18;
      }

      public Ingredient getRepairIngredient() {
         return Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TIME_CRYSTAL.get()});
      }
   }
}
