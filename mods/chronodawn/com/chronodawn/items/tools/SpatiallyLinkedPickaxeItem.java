package com.chronodawn.items.tools;

import com.chronodawn.registry.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class SpatiallyLinkedPickaxeItem extends PickaxeItem {
   private static final Tier SPATIALLY_LINKED_TIER = new Tier() {
      public int getUses() {
         return 1561;
      }

      public float getSpeed() {
         return 8.0F;
      }

      public float getAttackDamageBonus() {
         return 3.0F;
      }

      public TagKey<Block> getIncorrectBlocksForDrops() {
         return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
      }

      public int getEnchantmentValue() {
         return 10;
      }

      public Ingredient getRepairIngredient() {
         return Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TIME_CRYSTAL.get()});
      }
   };

   public SpatiallyLinkedPickaxeItem(Properties properties) {
      super(SPATIALLY_LINKED_TIER, properties.attributes(PickaxeItem.createAttributes(SPATIALLY_LINKED_TIER, 1.0F, -2.8F)));
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1561);
   }

   public static boolean isSpatiallyLinkedPickaxe(Item item) {
      return item instanceof SpatiallyLinkedPickaxeItem;
   }

   public static double getDropDoublingChance() {
      return 0.33;
   }
}
