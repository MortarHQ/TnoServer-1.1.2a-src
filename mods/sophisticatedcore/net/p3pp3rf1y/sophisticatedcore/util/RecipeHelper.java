package net.p3pp3rf1y.sophisticatedcore.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;

public class RecipeHelper {
   private static final int MAX_FOLLOW_UP_COMPACTING_RECIPES = 30;
   @Nullable
   private static RecipeHelper.RecipeCache clientCache = null;
   @Nullable
   private static RecipeHelper.RecipeCache serverCache = null;

   private RecipeHelper() {
   }

   public static void setLevel(Level l) {
      getCache().setLevel(l);
   }

   public static void clearListeners() {
      runOnCache(cache -> cache.recipeChangeListeners.list.clear());
   }

   private static void runOnCache(Consumer<RecipeHelper.RecipeCache> consumer) {
      consumer.accept(getCache());
   }

   private static RecipeHelper.RecipeCache getCache() {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         if (serverCache == null) {
            serverCache = new RecipeHelper.RecipeCache();
         }

         return serverCache;
      } else {
         if (clientCache == null) {
            clientCache = new RecipeHelper.RecipeCache();
         }

         return clientCache;
      }
   }

   private static <T> T getFromCache(Function<RecipeHelper.RecipeCache, T> getter, T defaultValue) {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         return serverCache == null ? defaultValue : getter.apply(serverCache);
      } else {
         return clientCache == null ? defaultValue : getter.apply(clientCache);
      }
   }

   public static void addRecipeChangeListener(Runnable runnable) {
      runOnCache(cache -> cache.addRecipeChangeListener(runnable));
   }

   public static void onRecipesUpdated(RecipesUpdatedEvent event) {
      runOnCache(cache -> {
         cache.clearCache();
         cache.recipeChangeListeners.notifyAllListeners();
      });
   }

   public static void onDataPackSync(OnDatapackSyncEvent event) {
      runOnCache(cache -> {
         cache.clearCache();
         cache.recipeChangeListeners.notifyAllListeners();
      });
   }

   private static Optional<Level> getLevel() {
      return getFromCache(cache -> Optional.ofNullable(cache.level == null ? null : cache.level.get()), Optional.empty());
   }

   private static Set<RecipeHelper.CompactingShape> getCompactingShapes(ItemStack stack) {
      return getLevel()
         .map(
            w -> {
               Set<RecipeHelper.CompactingShape> compactingShapes = new HashSet<>();
               getCompactingShape(stack, w, 2, 2, RecipeHelper.CompactingShape.TWO_BY_TWO_UNCRAFTABLE, RecipeHelper.CompactingShape.TWO_BY_TWO)
                  .ifPresent(compactingShapes::add);
               getCompactingShape(stack, w, 3, 3, RecipeHelper.CompactingShape.THREE_BY_THREE_UNCRAFTABLE, RecipeHelper.CompactingShape.THREE_BY_THREE)
                  .ifPresent(compactingShapes::add);
               if (compactingShapes.isEmpty()) {
                  compactingShapes.add(RecipeHelper.CompactingShape.NONE);
               }

               return compactingShapes;
            }
         )
         .orElse(Collections.emptySet());
   }

   private static Optional<RecipeHelper.CompactingShape> getCompactingShape(
      ItemStack stack, Level w, int width, int height, RecipeHelper.CompactingShape uncraftableShape, RecipeHelper.CompactingShape shape
   ) {
      RecipeHelper.CompactingResult compactingResult = getCompactingResult(stack, w, width, height);
      if (!compactingResult.getResult().isEmpty()) {
         if (ItemStack.isSameItemSameComponents(stack, compactingResult.getResult())) {
            return Optional.empty();
         } else if (isPartOfCompactingLoop(stack, compactingResult.getResult(), w)) {
            return Optional.empty();
         } else {
            return uncompactMatchesItem(compactingResult.getResult(), w, stack, width * height) ? Optional.of(uncraftableShape) : Optional.of(shape);
         }
      } else {
         return Optional.empty();
      }
   }

   private static boolean isPartOfCompactingLoop(ItemStack firstCompacted, ItemStack firstCompactResult, Level w) {
      int iterations = 0;
      Set<Integer> compactedItemHashes = new HashSet<>();
      Queue<ItemStack> itemsToCompact = new LinkedList<>();
      itemsToCompact.add(firstCompactResult);

      while (!itemsToCompact.isEmpty()) {
         ItemStack itemToCompact = itemsToCompact.poll();
         ItemStack compactingResultStack = getCompactingResult(itemToCompact, w, 2, 2).getResult();
         if (!compactingResultStack.isEmpty()) {
            if (ItemStack.isSameItemSameComponents(compactingResultStack, firstCompacted)) {
               return true;
            }

            if (compactedItemHashes.contains(ItemStack.hashItemAndComponents(compactingResultStack))) {
               return false;
            }

            itemsToCompact.add(compactingResultStack);
         }

         compactingResultStack = getCompactingResult(itemToCompact, w, 3, 3).getResult();
         if (!compactingResultStack.isEmpty()) {
            if (ItemStack.isSameItemSameComponents(compactingResultStack, firstCompacted)) {
               return true;
            }

            if (compactedItemHashes.contains(ItemStack.hashItemAndComponents(compactingResultStack))) {
               return false;
            }

            itemsToCompact.add(compactingResultStack);
         }

         compactedItemHashes.add(ItemStack.hashItemAndComponents(itemToCompact));
         if (++iterations > 30) {
            return true;
         }
      }

      return false;
   }

   private static boolean uncompactMatchesItem(ItemStack itemToUncompact, Level w, ItemStack itemToMatch, int count) {
      for (ItemStack uncompactResult : getUncompactResultItems(w, itemToUncompact)) {
         if (ItemStack.isSameItemSameComponents(uncompactResult, itemToMatch) && uncompactResult.getCount() == count) {
            return true;
         }
      }

      return false;
   }

   public static RecipeHelper.UncompactingResult getUncompactingResult(ItemStack uncompactedItem) {
      return getFromCache(
         cache -> cache.getUncompactingResults()
            .computeIfAbsent(
               ItemStack.hashItemAndComponents(uncompactedItem),
               k -> getLevel()
                  .map(
                     w -> {
                        for (ItemStack uncompactResultItem : getUncompactResultItems(w, uncompactedItem)) {
                           if (uncompactResultItem.getCount() == 9) {
                              if (ItemStack.isSameItemSameComponents(getCompactingResult(uncompactResultItem, 3, 3).getResult(), uncompactedItem)) {
                                 return new RecipeHelper.UncompactingResult(uncompactResultItem, RecipeHelper.CompactingShape.THREE_BY_THREE_UNCRAFTABLE);
                              }
                           } else if (uncompactResultItem.getCount() == 4
                              && ItemStack.isSameItemSameComponents(getCompactingResult(uncompactResultItem, 2, 2).getResult(), uncompactedItem)) {
                              return new RecipeHelper.UncompactingResult(uncompactResultItem, RecipeHelper.CompactingShape.TWO_BY_TWO_UNCRAFTABLE);
                           }
                        }

                        return RecipeHelper.UncompactingResult.EMPTY;
                     }
                  )
                  .orElse(RecipeHelper.UncompactingResult.EMPTY)
            ),
         RecipeHelper.UncompactingResult.EMPTY
      );
   }

   private static List<ItemStack> getUncompactResultItems(Level w, ItemStack itemToUncompact) {
      CraftingContainer craftingInventory = getFilledCraftingInventory(itemToUncompact, 1, 1);
      return safeGetRecipesFor(RecipeType.CRAFTING, craftingInventory.asCraftInput(), w)
         .stream()
         .map(r -> ((CraftingRecipe)r.value()).assemble(craftingInventory.asCraftInput(), w.registryAccess()))
         .toList();
   }

   public static RecipeHelper.CompactingResult getCompactingResult(ItemStack stack, RecipeHelper.CompactingShape shape) {
      if (shape == RecipeHelper.CompactingShape.TWO_BY_TWO_UNCRAFTABLE || shape == RecipeHelper.CompactingShape.TWO_BY_TWO) {
         return getCompactingResult(stack, 2, 2);
      } else {
         return shape != RecipeHelper.CompactingShape.THREE_BY_THREE_UNCRAFTABLE && shape != RecipeHelper.CompactingShape.THREE_BY_THREE
            ? RecipeHelper.CompactingResult.EMPTY
            : getCompactingResult(stack, 3, 3);
      }
   }

   public static RecipeHelper.CompactingResult getCompactingResult(ItemStack stack, int width, int height) {
      return getLevel().map(w -> getCompactingResult(stack, w, width, height)).orElse(RecipeHelper.CompactingResult.EMPTY);
   }

   private static RecipeHelper.CompactingResult getCompactingResult(ItemStack stack, Level level, int width, int height) {
      return getFromCache(cache -> getCompactingResult(stack, level, width, height, cache.getCompactingResults()), RecipeHelper.CompactingResult.EMPTY);
   }

   private static RecipeHelper.CompactingResult getCompactingResult(
      ItemStack stack, Level level, int width, int height, Map<RecipeHelper.CompactedItem, RecipeHelper.CompactingResult> cachedCompactingResults
   ) {
      RecipeHelper.CompactedItem compactedItem = new RecipeHelper.CompactedItem(stack, width, height);
      if (cachedCompactingResults.containsKey(compactedItem)) {
         return cachedCompactingResults.get(compactedItem);
      }

      CraftingContainer craftingInventory = getFilledCraftingInventory(stack, width, height);
      List<RecipeHolder<CraftingRecipe>> compactingRecipes = safeGetRecipesFor(RecipeType.CRAFTING, craftingInventory.asCraftInput(), level);
      if (compactingRecipes.isEmpty()) {
         cachedCompactingResults.put(compactedItem, RecipeHelper.CompactingResult.EMPTY);
         return RecipeHelper.CompactingResult.EMPTY;
      }

      if (compactingRecipes.size() == 1) {
         return cacheAndGetCompactingResult(compactedItem, (CraftingRecipe)compactingRecipes.getFirst().value(), craftingInventory);
      }

      for (RecipeHolder<CraftingRecipe> recipeHolder : compactingRecipes) {
         CraftingRecipe recipe = (CraftingRecipe)recipeHolder.value();
         ItemStack result = recipe.assemble(craftingInventory.asCraftInput(), level.registryAccess());
         if (uncompactMatchesItem(result, level, stack, width * height)) {
            return cacheAndGetCompactingResult(compactedItem, recipe, craftingInventory, result);
         }
      }

      return cacheAndGetCompactingResult(compactedItem, (CraftingRecipe)compactingRecipes.getFirst().value(), craftingInventory);
   }

   private static RecipeHelper.CompactingResult cacheAndGetCompactingResult(
      RecipeHelper.CompactedItem compactedItem, CraftingRecipe recipe, CraftingContainer craftingInventory
   ) {
      return getLevel()
         .map(
            level -> cacheAndGetCompactingResult(
               compactedItem, recipe, craftingInventory, recipe.assemble(craftingInventory.asCraftInput(), level.registryAccess())
            )
         )
         .orElse(RecipeHelper.CompactingResult.EMPTY);
   }

   private static RecipeHelper.CompactingResult cacheAndGetCompactingResult(
      RecipeHelper.CompactedItem compactedItem, CraftingRecipe recipe, CraftingContainer craftingInventory, ItemStack result
   ) {
      List<ItemStack> remainingItems = new ArrayList<>();
      recipe.getRemainingItems(craftingInventory.asCraftInput()).forEach(stack -> {
         if (!stack.isEmpty()) {
            remainingItems.add(stack);
         }
      });
      RecipeHelper.CompactingResult compactingResult = new RecipeHelper.CompactingResult(result, remainingItems);
      return getFromCache(cache -> {
         if (!result.isEmpty()) {
            cache.getCompactingResults().put(compactedItem, compactingResult);
         }

         return compactingResult;
      }, compactingResult);
   }

   private static CraftingContainer getFilledCraftingInventory(ItemStack stack, int width, int height) {
      CraftingContainer craftinginventory = new TransientCraftingContainer(new AbstractContainerMenu(null, -1) {
         public ItemStack quickMoveStack(Player player, int index) {
            return ItemStack.EMPTY;
         }

         public boolean stillValid(Player playerIn) {
            return false;
         }
      }, width, height);

      for (int i = 0; i < craftinginventory.getContainerSize(); i++) {
         craftinginventory.setItem(i, stack.copyWithCount(1));
      }

      return craftinginventory;
   }

   public static <T extends AbstractCookingRecipe> Optional<RecipeHolder<T>> getCookingRecipe(ItemStack stack, RecipeType<T> recipeType) {
      return getLevel().flatMap(w -> safeGetRecipeFor(recipeType, new SingleRecipeInput(stack), w, null));
   }

   public static Set<RecipeHelper.CompactingShape> getItemCompactingShapes(ItemStack stack) {
      return getFromCache(cache -> cache.getItemCompactingShapes(stack), Collections.emptySet());
   }

   public static <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipesOfType(RecipeType<T> recipeType, I inventory) {
      return getLevel().map(w -> w.getRecipeManager().getRecipesFor(recipeType, inventory, w)).orElse(Collections.emptyList());
   }

   public static <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> safeGetRecipeFor(
      RecipeType<T> recipeType, I inventory, @Nullable ResourceLocation recipeId
   ) {
      return getLevel().flatMap(w -> safeGetRecipeFor(recipeType, inventory, w, recipeId));
   }

   public static <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> safeGetRecipeFor(
      RecipeType<T> recipeType, I inventory, Level level, @Nullable ResourceLocation recipeId
   ) {
      try {
         return level.getRecipeManager().getRecipeFor(recipeType, inventory, level, recipeId);
      } catch (Exception e) {
         SophisticatedCore.LOGGER.error("Error while getting recipe ", e);
         return Optional.empty();
      }
   }

   public static <I extends CraftingInput, T extends Recipe<I>> List<RecipeHolder<T>> safeGetRecipesFor(RecipeType<T> recipeType, I inventory, Level level) {
      try {
         return level.getRecipeManager().getRecipesFor(recipeType, inventory, level);
      } catch (Exception e) {
         SophisticatedCore.LOGGER.error("Error while getting recipe ", e);
         return Collections.emptyList();
      }
   }

   private static class CompactedItem {
      private final ItemStack item;
      private final int itemHash;
      private final int width;
      private final int height;

      private CompactedItem(ItemStack item, int width, int height) {
         this.item = item.copyWithCount(1);
         this.width = width;
         this.height = height;
         this.itemHash = ItemStack.hashItemAndComponents(item);
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            RecipeHelper.CompactedItem that = (RecipeHelper.CompactedItem)o;
            return this.width == that.width && this.height == that.height && ItemStack.isSameItemSameComponents(this.item, that.item);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.itemHash, this.width, this.height);
      }
   }

   public static class CompactingResult {
      public static final RecipeHelper.CompactingResult EMPTY = new RecipeHelper.CompactingResult(ItemStack.EMPTY, Collections.emptyList());
      private final ItemStack result;
      private final List<ItemStack> remainingItems;

      public CompactingResult(ItemStack result, List<ItemStack> remainingItems) {
         this.result = result;
         this.remainingItems = remainingItems;
      }

      public ItemStack getResult() {
         return this.result;
      }

      public List<ItemStack> getRemainingItems() {
         return this.remainingItems;
      }
   }

   public enum CompactingShape {
      NONE(false, 0),
      THREE_BY_THREE(false, 9),
      TWO_BY_TWO(false, 4),
      THREE_BY_THREE_UNCRAFTABLE(true, 9),
      TWO_BY_TWO_UNCRAFTABLE(true, 4);

      private final int numberOfIngredients;
      private final boolean uncraftable;

      CompactingShape(boolean uncraftable, int numberOfIngredients) {
         this.uncraftable = uncraftable;
         this.numberOfIngredients = numberOfIngredients;
      }

      public boolean isUncraftable() {
         return this.uncraftable;
      }

      public int getNumberOfIngredients() {
         return this.numberOfIngredients;
      }
   }

   private static class RecipeCache {
      private final Cache<Integer, Set<RecipeHelper.CompactingShape>> itemCompactingShapes = CacheBuilder.newBuilder()
         .expireAfterAccess(10L, TimeUnit.MINUTES)
         .build();
      private final Map<RecipeHelper.CompactedItem, RecipeHelper.CompactingResult> compactingResults = new HashMap<>();
      private final Map<Integer, RecipeHelper.UncompactingResult> uncompactingResults = new HashMap<>();
      private final RecipeHelper.RecipeChangeListenerList recipeChangeListeners = new RecipeHelper.RecipeChangeListenerList();
      private WeakReference<Level> level;

      public void addRecipeChangeListener(Runnable runnable) {
         this.recipeChangeListeners.add(runnable);
      }

      public Map<Integer, RecipeHelper.UncompactingResult> getUncompactingResults() {
         return this.uncompactingResults;
      }

      public Map<RecipeHelper.CompactedItem, RecipeHelper.CompactingResult> getCompactingResults() {
         return this.compactingResults;
      }

      public Set<RecipeHelper.CompactingShape> getItemCompactingShapes(ItemStack stack) {
         int hash = ItemStack.hashItemAndComponents(stack);
         Set<RecipeHelper.CompactingShape> compactingShapes = (Set<RecipeHelper.CompactingShape>)this.itemCompactingShapes.getIfPresent(hash);
         if (compactingShapes == null) {
            SophisticatedCore.LOGGER
               .debug("Compacting shapes not found in cache for \"{}\" - querying recipes to get these", BuiltInRegistries.ITEM.getKey(stack.getItem()));
            compactingShapes = RecipeHelper.getCompactingShapes(stack);
            this.itemCompactingShapes.put(hash, compactingShapes);
         }

         return compactingShapes;
      }

      private void clearCache() {
         this.compactingResults.clear();
         this.uncompactingResults.clear();
         this.itemCompactingShapes.invalidateAll();
      }

      public void setLevel(Level l) {
         this.level = new WeakReference<>(l);
      }
   }

   private static class RecipeChangeListenerList {
      private final List<WeakReference<Runnable>> list = new CopyOnWriteArrayList<>();

      public void add(Runnable runnable) {
         this.list.add(new WeakReference<>(runnable));
      }

      public void notifyAllListeners() {
         this.list.removeIf(ref -> {
            Runnable runnable = ref.get();
            if (runnable != null) {
               runnable.run();
               return false;
            } else {
               return true;
            }
         });
      }
   }

   public static class UncompactingResult {
      public static final RecipeHelper.UncompactingResult EMPTY = new RecipeHelper.UncompactingResult(ItemStack.EMPTY, RecipeHelper.CompactingShape.NONE);
      private final ItemStack result;
      private final RecipeHelper.CompactingShape compactUsingShape;

      public UncompactingResult(ItemStack result, RecipeHelper.CompactingShape compactUsingShape) {
         this.result = result.copyWithCount(1);
         this.compactUsingShape = compactUsingShape;
      }

      public ItemStack getResult() {
         return this.result;
      }

      public RecipeHelper.CompactingShape getCompactUsingShape() {
         return this.compactUsingShape;
      }
   }
}
