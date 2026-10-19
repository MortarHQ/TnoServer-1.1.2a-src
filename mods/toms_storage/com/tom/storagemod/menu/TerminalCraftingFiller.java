package com.tom.storagemod.menu;

import com.tom.storagemod.block.entity.CraftingTerminalBlockEntity;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.util.TerminalSyncManager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class TerminalCraftingFiller {
   private CraftingTerminalBlockEntity te;
   private Player player;
   private Int2ObjectMap<List<ItemStack>> allItems = new Int2ObjectOpenHashMap();
   private TerminalSyncManager sync;

   public TerminalCraftingFiller(CraftingTerminalBlockEntity te, Player player, TerminalSyncManager sync) {
      this.te = te;
      this.player = player;
      this.sync = sync;
   }

   public void placeRecipe(Recipe<?> recipe) {
      this.te.clear(this.player);
      this.sync.fillCraftingFiller(this);

      for (ItemStack i : this.player.getInventory().items) {
         this.accountStack(i);
      }

      int rw;
      if (recipe instanceof ShapedRecipe sr) {
         rw = sr.getWidth();
      } else {
         int cnt = recipe.getIngredients().size();
         if (cnt == 1) {
            rw = 1;
         } else if (cnt <= 4) {
            rw = 2;
         } else {
            rw = 3;
         }
      }

      NonNullList<Ingredient> ings = recipe.getIngredients();

      for (int i = 0; i < ings.size(); i++) {
         Ingredient ingr = (Ingredient)ings.get(i);
         if (!ingr.isEmpty()) {
            int x = i % rw;
            int y = i / rw;
            boolean filled = false;
            IntListIterator var9 = ingr.getStackingIds().iterator();

            while (var9.hasNext()) {
               int v = (Integer)var9.next();
               List<ItemStack> lst = (List<ItemStack>)this.allItems.get(v);
               if (lst != null) {
                  for (ItemStack item : lst) {
                     if (ingr.test(item)) {
                        StoredItemStack pull = this.te.pullStack(new StoredItemStack(item), 1L);
                        if (pull != null) {
                           filled = true;
                           this.te.setCraftSlot(x, y, pull.getActualStack());
                           break;
                        }

                        int id = this.player.getInventory().findSlotMatchingItem(item);
                        if (id != -1) {
                           this.te.setCraftSlot(x, y, this.player.getInventory().removeItem(id, 1));
                           filled = true;
                           break;
                        }
                     }
                  }

                  if (filled) {
                     break;
                  }
               }
            }
         }
      }
   }

   public void accountStack(ItemStack st) {
      if (!st.has(DataComponents.CUSTOM_NAME)) {
         int index = StackedContents.getStackingIndex(st);
         ((List)this.allItems.computeIfAbsent(index, __ -> new ArrayList())).add(st);
      }
   }
}
