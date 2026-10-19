package team.creative.creativecore.common.util.type.list;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.util.ingredient.CreativeIngredient;
import team.creative.creativecore.common.util.ingredient.CreativeIngredientBlock;
import team.creative.creativecore.common.util.ingredient.CreativeIngredientBlockTag;
import team.creative.creativecore.common.util.ingredient.CreativeIngredientItem;
import team.creative.creativecore.common.util.ingredient.CreativeIngredientItemStack;
import team.creative.creativecore.common.util.ingredient.CreativeIngredientItemTag;

public class SortingList {
   @CreativeConfig
   public List<CreativeIngredient> entries = new ArrayList<>();
   @CreativeConfig
   public boolean isWhitelist;

   public SortingList(SortingList list) {
      this.isWhitelist = list.isWhitelist;
      this.entries = new ArrayList<>(list.entries);
   }

   public SortingList() {
      this(true);
   }

   public SortingList(boolean isWhitelist) {
      this.isWhitelist = isWhitelist;
   }

   public boolean isBlacklist() {
      return !this.isWhitelist();
   }

   public boolean isWhitelist() {
      return this.isWhitelist;
   }

   public void setListType(boolean isWhitelist) {
      this.isWhitelist = isWhitelist;
   }

   public void setWhitelist() {
      this.isWhitelist = true;
   }

   public void setBlacklist() {
      this.isWhitelist = false;
   }

   public void addSortingObjects(Object... objects) {
      for (int i = 0; i < objects.length; i++) {
         this.addSortingObject(objects[i]);
      }
   }

   public void addSortingObject(Object object) {
      if (object instanceof CreativeIngredient ingredient) {
         this.entries.add(ingredient);
      } else {
         CreativeIngredient info = CreativeIngredient.parse(object);
         if (info != null) {
            this.entries.add(info);
         }
      }
   }

   public void addSortingByBlock(Block block) {
      this.entries.add(new CreativeIngredientBlock(block));
   }

   public void addSortingByItem(Item item) {
      this.entries.add(new CreativeIngredientItem(item));
   }

   public void addSortingByItemStack(ItemStack stack) {
      this.entries.add(new CreativeIngredientItemStack(stack));
   }

   public void addSortingByBlockTag(TagKey<Block> tag) {
      this.entries.add(new CreativeIngredientBlockTag(tag));
   }

   public void addSortingByItemTag(TagKey<Item> tag) {
      this.entries.add(new CreativeIngredientItemTag(tag));
   }

   protected boolean canBeFoundInList(Object object) {
      CreativeIngredient info = CreativeIngredient.parse(object);
      if (info == null) {
         return false;
      }

      for (CreativeIngredient ingredient : this.entries) {
         if (ingredient.is(info)) {
            return true;
         }
      }

      return false;
   }

   protected boolean canBeFoundInList(ItemStack stack) {
      for (CreativeIngredient ingredient : this.entries) {
         if (ingredient.is(stack)) {
            return true;
         }
      }

      return false;
   }

   public boolean canPass(Object object) {
      return this.canBeFoundInList(object) == this.isWhitelist;
   }

   public boolean canPass(ItemStack stack) {
      return this.canBeFoundInList(stack) == this.isWhitelist;
   }
}
