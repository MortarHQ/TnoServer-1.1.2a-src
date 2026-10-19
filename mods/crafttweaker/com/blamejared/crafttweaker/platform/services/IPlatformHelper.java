package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.mod.PlatformMod;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.villager.trade.type.IBasicItemListing;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.Optionull;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.material.Fluid;

public interface IPlatformHelper {
   default String getLogFormat() {
      return "[%d{HH:mm:ss.SSS}][%level][%markerSimpleName]: %msg%n%throwable";
   }

   String getPlatformName();

   boolean isModLoaded(String var1);

   boolean isDevelopmentEnvironment();

   boolean isDataGen();

   List<Mod> getMods();

   Optional<Mod> getMod(String var1);

   IItemStack createItemStack(ItemStack var1, IngredientConditions var2, IngredientTransformers var3);

   IItemStack createItemStackMutable(ItemStack var1, IngredientConditions var2, IngredientTransformers var3);

   IFluidStack createFluidStack(Fluid var1, long var2, DataComponentPatch var4);

   IFluidStack createFluidStackMutable(Fluid var1, long var2, DataComponentPatch var4);

   <T> IFluidStack createFluidStack(T var1);

   <T> IFluidStack createFluidStackMutable(T var1);

   Fluid getBucketContent(BucketItem var1);

   Path getGameDirectory();

   <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
      Class<T> var1, Consumer<PlatformMod> var2, Predicate<Either<T, Map<String, Object>>> var3
   );

   default String findMappedMethodName(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... parameterTypes) {
      return methodName;
   }

   default String findMappedFieldName(Class<?> clazz, String fieldName, Class<?> fieldType) {
      return fieldName;
   }

   Map<ResourceLocation, ILootModifier> getLootModifiersMap();

   IInventoryWrapper getPlayerInventory(Player var1);

   @Deprecated(forRemoval = true)
   default boolean doCraftingTableRecipesConflict(IRecipeManager<?> manager, Recipe<?> first, Recipe<?> second) {
      return CraftingTableRecipeConflictChecker.checkConflicts(manager, first, second);
   }

   Set<MutableComponent> getFluidsForDump(ItemStack var1, Player var2, InteractionHand var3);

   CompoundTag getCustomData(Entity var1);

   CompoundTag getPersistentData(ServerPlayer var1);

   boolean doesIngredientRequireTesting(Ingredient var1);

   default void invalidateIngredients(List<Ingredient> ingredients) {
      ingredients.forEach(ingredient -> ((AccessIngredient)ingredient).crafttweaker$setItemStacks(null));
      ingredients.clear();
   }

   default IItemStack getRemainingItem(ItemStack stack) {
      return (IItemStack)Optionull.mapOrDefault(stack.getItem().getCraftingRemainingItem(), IItemStack::of, IItemStack.empty());
   }

   Ingredient getIngredientAny();

   Ingredient getIngredientList(List<Ingredient> var1);

   Ingredient getCraftTweakerIngredient(IIngredient var1);

   Ingredient getIItemStackIngredient(IItemStack var1);

   boolean isCustomIngredient(Ingredient var1);

   default Stream<ItemStack> getCustomIngredientItems(Ingredient ingredient) {
      return Arrays.stream(ingredient.getItems());
   }

   Stream<GameProfile> fakePlayers();

   boolean isFakePlayer(Player var1);

   default ItemStack getBasicTradePrice(IBasicItemListing internal) {
      throw new UnsupportedOperationException("Unable to get price for '%s'".formatted(internal));
   }

   default ItemStack getBasicTradePrice2(IBasicItemListing internal) {
      throw new UnsupportedOperationException("Unable to get price2 for '%s'".formatted(internal));
   }

   default ItemStack getBasicTradeForSale(IBasicItemListing internal) {
      throw new UnsupportedOperationException("Unable to get forSale for '%s'".formatted(internal));
   }

   default int getBasicTradeMaxTrades(IBasicItemListing internal) {
      throw new UnsupportedOperationException("Unable to get maxTrades for '%s'".formatted(internal));
   }

   default int getBasicTradeXp(IBasicItemListing internal) {
      throw new UnsupportedOperationException("Unable to get xp for '%s'".formatted(internal));
   }

   default float getBasicTradePriceMult(IBasicItemListing internal) {
      throw new UnsupportedOperationException("Unable to get priceMult for '%s'".formatted(internal));
   }

   PossibleEffect createPossibleEffect(MobEffectInstance var1, float var2);

   default void setCompostable(IItemStack stack, Optional<Float> value, boolean undoing) {
      Item item = stack.getInternal().getItem();
      value.ifPresentOrElse(val -> ComposterBlock.COMPOSTABLES.put(item, val), () -> ComposterBlock.COMPOSTABLES.removeFloat(item));
   }
}
