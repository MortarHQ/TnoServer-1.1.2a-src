package com.github.wal_bos.moreskills.world.entity;

import io.github.manasmods.tensura.recipe.KilnMixingRecipe;
import io.github.manasmods.tensura.recipe.RefiningRecipe;
import io.github.manasmods.tensura.recipe.SmithingBenchRecipe;
import io.github.manasmods.tensura.registry.recipe.TensuraRecipes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public final class ArsWeissCloneTensuraRecipeSupport {
   private ArsWeissCloneTensuraRecipeSupport() {
   }

   public static List<ItemStack> expandTensuraRecipe(ServerPlayer owner, ItemStack target) {
      List<ItemStack> out = new ArrayList<>();
      if (owner == null || target.isEmpty()) {
         return out;
      }

      if (tryHardcodedResetScroll(target, out)) {
         return out;
      }

      if (tryHardcodedMagicStone(target, out)) {
         return out;
      }

      if (tryHardcodedMagisteelChain(target, out)) {
         return out;
      }

      RecipeManager manager = owner.getServer().getRecipeManager();
      trySmithing(owner, manager, target, out);
      if (!out.isEmpty()) {
         return out;
      }

      tryRefining(owner, manager, target, out);
      if (!out.isEmpty()) {
         return out;
      }

      tryKilnMixing(owner, manager, target, out);
      return out;
   }

   private static boolean tryHardcodedResetScroll(ItemStack target, List<ItemStack> out) {
      String text = itemText(target);
      if (text.contains("reset") && text.contains("scroll")) {
         int count = Math.max(1, target.getCount());
         String metal = "pure_magisteel_ingot";
         if (text.contains("orichalcum")) {
            metal = "orichalcum_ingot";
         }

         if (text.contains("mithril")) {
            metal = "mithril_ingot";
         }

         addResolved(out, "minecraft", "paper", 3 * count);
         addResolved(out, "tensura", metal, 3 * count);
         addResolved(out, "tensura", "magic_stone", 1 * count);
         addResolved(out, "tensura", "monster_leather_a", 2 * count);
         return !out.isEmpty();
      } else {
         return false;
      }
   }

   private static boolean tryHardcodedMagicStone(ItemStack target, List<ItemStack> out) {
      String text = itemText(target);
      if (!text.contains("magic stone")) {
         return false;
      }

      int count = Math.max(1, target.getCount());
      addResolved(out, "tensura", "low_magisteel_ingot", count);
      addResolved(out, "tensura", "low_quality_magic_crystal", 8 * count);
      return !out.isEmpty();
   }

   private static boolean tryHardcodedMagisteelChain(ItemStack target, List<ItemStack> out) {
      String text = itemText(target);
      int count = Math.max(1, target.getCount());
      if (text.contains("low magisteel ingot")) {
         addResolved(out, "tensura", "magic_ore", count);
         addResolved(out, "minecraft", "raw_iron", count);
         return !out.isEmpty();
      } else if (text.contains("high magisteel ingot")) {
         addResolved(out, "tensura", "magic_ore", Math.max(1, count * 4));
         addResolved(out, "minecraft", "raw_iron", Math.max(1, count));
         return !out.isEmpty();
      } else if (text.contains("pure magisteel ingot")) {
         addResolved(out, "tensura", "magic_ore", Math.max(1, count * 5));
         return !out.isEmpty();
      } else if (text.contains("mithril ingot")) {
         addResolved(out, "tensura", "magic_ore", Math.max(1, count * 3));
         addResolved(out, "tensura", "raw_silver", Math.max(1, count));
         return !out.isEmpty();
      } else if (text.contains("orichalcum ingot")) {
         addResolved(out, "tensura", "magic_ore", Math.max(1, count * 3));
         addResolved(out, "minecraft", "raw_gold", Math.max(1, count));
         return !out.isEmpty();
      } else if (text.contains("adamantite ingot")) {
         addResolved(out, "tensura", "magic_ore", Math.max(1, count * 8));
         addResolved(out, "minecraft", "diamond", Math.max(1, count));
         return !out.isEmpty();
      } else if (text.contains("hihiirokane ingot")) {
         addResolved(out, "tensura", "magic_ore", Math.max(1, count * 12));
         addResolved(out, "minecraft", "raw_gold", Math.max(1, count * 2));
         return !out.isEmpty();
      } else {
         return false;
      }
   }

   private static void addResolved(List<ItemStack> out, String namespace, String path, int count) {
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, path);
      if (BuiltInRegistries.ITEM.containsKey(id)) {
         Item item = (Item)BuiltInRegistries.ITEM.get(id);
         if (item != Items.AIR) {
            out.add(new ItemStack(item, Math.max(1, count)));
         }
      }
   }

   private static String itemText(ItemStack stack) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      String registry = id == null ? "" : id.toString();
      return (registry + " " + stack.getHoverName().getString()).toLowerCase().replace('_', ' ');
   }

   private static void trySmithing(ServerPlayer owner, RecipeManager manager, ItemStack target, List<ItemStack> out) {
      RecipeType type = (RecipeType)TensuraRecipes.SMITHING_BENCH_TYPE.get();

      for (Object raw : manager.getAllRecipesFor(type)) {
         RecipeHolder holder = (RecipeHolder)raw;
         if (holder.value() instanceof SmithingBenchRecipe recipe && ItemStack.isSameItem(recipe.getResultItem(owner.registryAccess()), target)) {
            List<Ingredient> ingredients = recipe.getIngredients();
            List<Integer> amounts = recipe.getIngredientAmount();

            for (int i = 0; i < ingredients.size() && i < amounts.size(); i++) {
               Ingredient ingredient = ingredients.get(i);
               int amount = Math.max(0, amounts.get(i));
               if (!ingredient.isEmpty() && amount > 0) {
                  ItemStack[] options = ingredient.getItems();
                  if (options.length > 0 && !options[0].isEmpty()) {
                     out.add(options[0].copyWithCount(amount));
                  }
               }
            }

            return;
         }
      }
   }

   private static void tryRefining(ServerPlayer owner, RecipeManager manager, ItemStack target, List<ItemStack> out) {
      RecipeType type = (RecipeType)TensuraRecipes.REFINING_TYPE.get();

      for (Object raw : manager.getAllRecipesFor(type)) {
         RecipeHolder holder = (RecipeHolder)raw;
         if (holder.value() instanceof RefiningRecipe recipe && ItemStack.isSameItem(recipe.getResultItem(owner.registryAccess()), target)) {
            try {
               ItemStack input = (ItemStack)recipe.getClass().getMethod("getInput").invoke(recipe);
               if (!input.isEmpty()) {
                  out.add(input.copy());
               }
            } catch (Throwable var13) {
            }

            for (Ingredient ingredient : recipe.getIngredients()) {
               if (!ingredient.isEmpty()) {
                  ItemStack[] options = ingredient.getItems();
                  if (options.length > 0 && !options[0].isEmpty()) {
                     out.add(options[0].copyWithCount(1));
                  }
               }
            }

            return;
         }
      }
   }

   private static void tryKilnMixing(ServerPlayer owner, RecipeManager manager, ItemStack target, List<ItemStack> out) {
      RecipeType type = (RecipeType)TensuraRecipes.KILN_MIXING_TYPE.get();

      for (Object raw : manager.getAllRecipesFor(type)) {
         RecipeHolder holder = (RecipeHolder)raw;
         if (holder.value() instanceof KilnMixingRecipe recipe && ItemStack.isSameItem(recipe.getResultItem(owner.registryAccess()), target)) {
            String left = recipe.getLeftInput().getPath();
            String right = recipe.getRightInput().getPath();
            addMoltenProxy(out, left, recipe.getLeftAmount());
            addMoltenProxy(out, right, recipe.getRightAmount());
            return;
         }
      }
   }

   private static void addMoltenProxy(List<ItemStack> out, String material, int amount) {
      if (material != null && !material.isBlank() && !material.equals("air") && amount > 0) {
         int count = Math.max(1, (int)Math.ceil(amount / 144.0));
         ItemStack stack = resolveMaterial(material, count);
         if (!stack.isEmpty()) {
            out.add(stack);
         }
      }
   }

   private static ItemStack resolveMaterial(String material, int count) {
      String clean = material.toLowerCase().replace("molten_", "").replace(" ", "_");
      String[] names = new String[]{clean + "_ingot", clean + "_ore", "raw_" + clean, clean + "_gem", clean + "_crystal", clean};

      for (String name : names) {
         ResourceLocation id = ResourceLocation.fromNamespaceAndPath("tensura", name);
         if (BuiltInRegistries.ITEM.containsKey(id)) {
            Item item = (Item)BuiltInRegistries.ITEM.get(id);
            if (item != Items.AIR) {
               return new ItemStack(item, count);
            }
         }
      }

      for (ResourceLocation id : BuiltInRegistries.ITEM.keySet()) {
         if (id.getNamespace().equals("tensura")) {
            String path = id.getPath();
            if (path.contains(clean) && (path.contains("ingot") || path.contains("ore") || path.contains("crystal") || path.contains("stone"))) {
               Item item = (Item)BuiltInRegistries.ITEM.get(id);
               if (item != Items.AIR) {
                  return new ItemStack(item, count);
               }
            }
         }
      }

      return ItemStack.EMPTY;
   }
}
