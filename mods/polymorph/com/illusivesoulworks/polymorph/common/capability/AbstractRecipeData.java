package com.illusivesoulworks.polymorph.common.capability;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import com.illusivesoulworks.polymorph.api.common.capability.IRecipeData;
import com.illusivesoulworks.polymorph.common.util.RecipePair;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRecipeData<E> implements IRecipeData<E> {
   private final SortedSet<IRecipePair> recipesList = new TreeSet<>();
   private final E owner;
   private final RecipeCache recipeCache;
   private final Map<UUID, ServerPlayer> listeners;
   private RecipeHolder<?> selectedRecipe;
   private ResourceLocation loadedRecipe;

   public AbstractRecipeData(E owner) {
      this.owner = owner;
      this.recipeCache = new RecipeCache(10);
      this.listeners = new HashMap<>();
   }

   @Override
   public <I extends RecipeInput, T extends Recipe<I>> RecipeHolder<T> getRecipe(
      RecipeType<T> type, I recipeInput, Level level, List<RecipeHolder<T>> recipesListIn
   ) {
      List<RecipeHolder<T>> recipes = recipesListIn.isEmpty() ? this.recipeCache.get(level, type, recipeInput) : recipesListIn;
      if (recipes.isEmpty()) {
         this.updateRecipesList(new TreeSet<>());
         return null;
      }

      RegistryAccess registryAccess = level.registryAccess();
      if (this.loadedRecipe != null && this.getSelectedRecipe() == null) {
         level.getRecipeManager().byKey(this.loadedRecipe).ifPresent(this::setSelectedRecipe);
      }

      this.loadedRecipe = null;
      RecipeHolder<T> firstResult = null;
      RecipeHolder<T> selected = null;
      SortedSet<IRecipePair> recipesList = new TreeSet<>();

      for (RecipeHolder<T> entry : recipes) {
         T recipe = (T)entry.value();
         ResourceLocation id = entry.id();
         ItemStack output = recipe.getResultItem(registryAccess);
         if (output == null || output.isEmpty() || entry.value() instanceof CustomRecipe) {
            output = recipe.assemble(recipeInput, registryAccess);
         }

         if (!output.isEmpty()) {
            if (firstResult == null) {
               firstResult = entry;
            }

            boolean flag = false;
            if (selected == null && this.getSelectedRecipe() != null && this.getSelectedRecipe().id().equals(id)) {
               selected = entry;
               flag = true;
            }

            if (recipesList.size() < 15 || flag) {
               recipesList.add(new RecipePair(id, output));
            }
         }
      }

      if (selected == null) {
         selected = firstResult;
         this.setSelectedRecipe(selected);
      }

      this.updateRecipesList(recipesList);
      return selected;
   }

   protected void updateRecipesList(SortedSet<IRecipePair> recipesList) {
      this.setRecipesList(recipesList);
      this.sendRecipesListToListeners();
   }

   @Override
   public RecipeHolder<?> getSelectedRecipe() {
      return this.selectedRecipe;
   }

   @Override
   public void setSelectedRecipe(RecipeHolder<?> recipe) {
      this.selectedRecipe = recipe;
   }

   @Nonnull
   @Override
   public SortedSet<IRecipePair> getRecipesList() {
      return this.recipesList;
   }

   @Override
   public void setRecipesList(@Nonnull SortedSet<IRecipePair> recipesList) {
      this.recipesList.clear();
      this.recipesList.addAll(recipesList);
   }

   @Override
   public E getOwner() {
      return this.owner;
   }

   @Override
   public void selectRecipe(@Nonnull RecipeHolder<?> recipe) {
      this.setSelectedRecipe(recipe);
   }

   @Override
   public Collection<ServerPlayer> getListeners() {
      return Collections.unmodifiableCollection(this.listeners.values());
   }

   @Override
   public void addListener(@Nonnull ServerPlayer serverPlayer) {
      this.listeners.put(serverPlayer.getUUID(), serverPlayer);
   }

   @Override
   public void removeListener(@NotNull ServerPlayer serverPlayer) {
      this.listeners.remove(serverPlayer.getUUID());
   }

   @Override
   public void clearListeners() {
      this.listeners.clear();
   }

   @Override
   public void sendRecipesListToListeners() {
      ResourceLocation resourceLocation = this.getSelectedRecipe() != null ? this.getSelectedRecipe().id() : null;
      Pair<SortedSet<IRecipePair>, ResourceLocation> packetData = new Pair(this.getRecipesList(), resourceLocation);

      for (ServerPlayer listener : this.getListeners()) {
         PolymorphApi.getInstance()
            .getNetwork()
            .sendRecipesListS2C(listener, (SortedSet<IRecipePair>)packetData.getFirst(), (ResourceLocation)packetData.getSecond());
      }
   }

   @Override
   public void readNBT(Provider provider, CompoundTag compoundTag) {
      if (compoundTag.contains("SelectedRecipe")) {
         this.loadedRecipe = ResourceLocation.tryParse(compoundTag.getString("SelectedRecipe"));
      }
   }

   @Nonnull
   @Override
   public CompoundTag writeNBT(Provider provider) {
      CompoundTag nbt = new CompoundTag();
      if (this.selectedRecipe != null) {
         nbt.putString("SelectedRecipe", this.selectedRecipe.id().toString());
      }

      return nbt;
   }
}
