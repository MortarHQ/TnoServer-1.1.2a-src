package com.illusivesoulworks.polymorph.api.common.capability;

import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public interface IRecipeData<E> {
   <I extends RecipeInput, T extends Recipe<I>> RecipeHolder<T> getRecipe(RecipeType<T> var1, I var2, Level var3, List<RecipeHolder<T>> var4);

   void selectRecipe(@Nonnull RecipeHolder<?> var1);

   RecipeHolder<?> getSelectedRecipe();

   void setSelectedRecipe(RecipeHolder<?> var1);

   @Nonnull
   SortedSet<IRecipePair> getRecipesList();

   void setRecipesList(@Nonnull SortedSet<IRecipePair> var1);

   Collection<ServerPlayer> getListeners();

   void addListener(@Nonnull ServerPlayer var1);

   void removeListener(@Nonnull ServerPlayer var1);

   void clearListeners();

   void sendRecipesListToListeners();

   E getOwner();

   @Nonnull
   CompoundTag writeNBT(Provider var1);

   void readNBT(Provider var1, CompoundTag var2);
}
