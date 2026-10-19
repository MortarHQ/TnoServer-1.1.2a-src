package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.ingredient.vanilla.CraftTweakerIngredients;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.mod.PlatformMod;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.villager.trade.type.IBasicItemListing;
import com.blamejared.crafttweaker.impl.loot.CraftTweakerPrivilegedLootModifierMap;
import com.blamejared.crafttweaker.impl.loot.NeoForgeLootModifierMapAdapter;
import com.blamejared.crafttweaker.impl.mod.NeoForgeMod;
import com.blamejared.crafttweaker.mixin.common.access.entity.AccessFakePlayerFactory;
import com.blamejared.crafttweaker.mixin.common.access.loot.AccessLootModifierManager;
import com.blamejared.crafttweaker.mixin.common.access.neoforge.AccessDataMapLoader;
import com.blamejared.crafttweaker.mixin.common.access.neoforge.AccessNeoForgeInternalHandler;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessBasicTrade;
import com.blamejared.crafttweaker.platform.helper.inventory.IItemHandlerWrapper;
import com.blamejared.crafttweaker.platform.services.IPlatformHelper;
import com.google.common.base.Suppliers;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.common.conditions.WithConditions;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifierManager;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.datamaps.DataMapEntry;
import net.neoforged.neoforge.registries.datamaps.DataMapFile;
import net.neoforged.neoforge.registries.datamaps.DataMapEntry.Removal;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import net.neoforged.neoforgespi.locating.IModFile;
import org.objectweb.asm.Type;

public class NeoForgePlatformHelper implements IPlatformHelper {
   public final Supplier<List<Mod>> modList = Suppliers.memoize(
      () -> ModList.get()
         .getMods()
         .stream()
         .map(iModInfo -> new Mod(iModInfo.getModId(), iModInfo.getDisplayName(), iModInfo.getVersion().toString()))
         .toList()
   );
   public final Function<String, Optional<Mod>> modFinder = Util.memoize(
      modid -> this.modList.get().stream().filter(modObject -> modObject.id().equals(modid)).findFirst()
   );

   @Override
   public String getLogFormat() {
      return "[%d{HH:mm:ss.SSS}][%level][%markerSimpleName]: %minecraftFormatting{%msg}{strip}%n%throwable";
   }

   @Override
   public String getPlatformName() {
      return "NeoForge";
   }

   @Override
   public boolean isModLoaded(String modId) {
      return ModList.get().isLoaded(modId);
   }

   @Override
   public boolean isDevelopmentEnvironment() {
      return !FMLLoader.isProduction();
   }

   @Override
   public boolean isDataGen() {
      return DatagenModLoader.isRunningDataGen();
   }

   @Override
   public List<Mod> getMods() {
      return this.modList.get();
   }

   @Override
   public Optional<Mod> getMod(String modid) {
      return this.modFinder.apply(modid);
   }

   @Override
   public IItemStack createItemStack(ItemStack stack, IngredientConditions conditions, IngredientTransformers transformers) {
      return new MCItemStack(stack, conditions, transformers);
   }

   @Override
   public IItemStack createItemStackMutable(ItemStack stack, IngredientConditions conditions, IngredientTransformers transformers) {
      return new MCItemStackMutable(stack, conditions, transformers);
   }

   @Override
   public IFluidStack createFluidStack(Fluid fluid, long amount, DataComponentPatch patch) {
      return new MCFluidStack(new FluidStack(fluid.builtInRegistryHolder(), Math.toIntExact(amount), patch));
   }

   @Override
   public IFluidStack createFluidStackMutable(Fluid fluid, long amount, DataComponentPatch patch) {
      return new MCFluidStackMutable(new FluidStack(fluid.builtInRegistryHolder(), Math.toIntExact(amount), patch));
   }

   @Override
   public <T> IFluidStack createFluidStack(T stack) {
      if (stack instanceof FluidStack fluidStack) {
         return new MCFluidStack(fluidStack);
      } else {
         throw new IllegalArgumentException("Unable to create IFluidStack from '" + stack + "'!");
      }
   }

   @Override
   public <T> IFluidStack createFluidStackMutable(T stack) {
      if (stack instanceof FluidStack fluidStack) {
         return new MCFluidStackMutable(fluidStack);
      } else {
         throw new IllegalArgumentException("Unable to create IFluidStack from '" + stack + "'!");
      }
   }

   @Override
   public Fluid getBucketContent(BucketItem item) {
      return item.content;
   }

   @Override
   public Path getGameDirectory() {
      return FMLPaths.GAMEDIR.get();
   }

   @Override
   public <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
      Class<T> annotationClass, Consumer<PlatformMod> classProviderConsumer, Predicate<Either<T, Map<String, Object>>> annotationFilter
   ) {
      Type annotationType = Type.getType(annotationClass);
      return ModList.get()
         .getAllScanData()
         .stream()
         .flatMap(it -> this.fromScanData(annotationType, classProviderConsumer, annotationFilter, it))
         .map(NeoForgePlatformHelper::getClassFromType)
         .filter(Objects::nonNull);
   }

   @Override
   public Map<ResourceLocation, ILootModifier> getLootModifiersMap() {
      try {
         LootModifierManager rawManager = AccessNeoForgeInternalHandler.crafttweaker$getLootModifierManager();
         AccessLootModifierManager manager = GenericUtil.uncheck(rawManager);
         Map<ResourceLocation, IGlobalLootModifier> map = manager.crafttweaker$getModifiers();
         if (!(map instanceof CraftTweakerPrivilegedLootModifierMap)) {
            Map<ResourceLocation, IGlobalLootModifier> newMap = CraftTweakerPrivilegedLootModifierMap.of(map);
            manager.crafttweaker$setModifiers(newMap);
            map = newMap;
         }

         return NeoForgeLootModifierMapAdapter.adapt(map);
      } catch (IllegalStateException e) {
         return Collections.emptyMap();
      }
   }

   public IItemHandlerWrapper getPlayerInventory(Player player) {
      return new IItemHandlerWrapper((IItemHandler)player.getCapability(ItemHandler.ENTITY));
   }

   @Override
   public Set<MutableComponent> getFluidsForDump(ItemStack stack, Player player, InteractionHand hand) {
      IFluidHandlerItem cap = (IFluidHandlerItem)stack.getCapability(FluidHandler.ITEM);
      if (cap == null) {
         return Set.of();
      }

      Set<MutableComponent> components = new HashSet<>();
      int tanks = cap.getTanks();

      for (int i = 0; i < tanks; i++) {
         components.add(Component.literal(IFluidStack.of(cap.getFluidInTank(i)).getCommandString()));
      }

      return components;
   }

   private <T extends Annotation> Stream<Type> fromScanData(
      Type annotationType, Consumer<PlatformMod> classProviderConsumer, Predicate<Either<T, Map<String, Object>>> annotationFilter, ModFileScanData data
   ) {
      return data.getAnnotations()
         .stream()
         .filter(it -> annotationType.equals(it.annotationType()))
         .filter(it -> annotationFilter.test(Either.right(it.annotationData())))
         .peek(ignored -> data.getIModInfoData().stream().flatMap(it -> it.getMods().stream()).map(it -> {
            Mod mod = new Mod(it.getModId(), it.getDisplayName(), it.getVersion().toString());
            IModFile file = it.getOwningFile().getFile();
            return new NeoForgeMod(mod, file.getFilePath(), file.findResource(new String[]{""}).resolve("/"));
         }).forEach(classProviderConsumer))
         .map(AnnotationData::clazz);
   }

   private static Class<?> getClassFromType(Type type) {
      try {
         return Class.forName(type.getClassName(), false, CraftTweakerCommon.class.getClassLoader());
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   public CompoundTag getCustomData(Entity entity) {
      return entity.getPersistentData();
   }

   @Override
   public CompoundTag getPersistentData(ServerPlayer player) {
      return player.getPersistentData().getCompound("PlayerPersisted");
   }

   @Override
   public boolean doesIngredientRequireTesting(Ingredient ingredient) {
      return !ingredient.isSimple();
   }

   @Override
   public IItemStack getRemainingItem(ItemStack stack) {
      return IItemStack.of(stack.getCraftingRemainingItem());
   }

   @Override
   public Ingredient getIngredientAny() {
      return CraftTweakerIngredients.Ingredients.any().toVanilla();
   }

   @Override
   public Ingredient getIngredientList(List<Ingredient> children) {
      return CraftTweakerIngredients.Ingredients.list(children).toVanilla();
   }

   @Override
   public Ingredient getCraftTweakerIngredient(IIngredient internal) {
      return CraftTweakerIngredients.Ingredients.crafttweaker(internal).toVanilla();
   }

   @Override
   public Ingredient getIItemStackIngredient(IItemStack internal) {
      return CraftTweakerIngredients.Ingredients.iitemstack(internal).toVanilla();
   }

   @Override
   public boolean isCustomIngredient(Ingredient ingredient) {
      return ingredient.getCustomIngredient() != null;
   }

   @Override
   public Stream<GameProfile> fakePlayers() {
      return Stream.concat(
         AccessFakePlayerFactory.crafttweaker$getFakePlayers().keySet().stream(), Stream.of(AccessFakePlayerFactory.crafttweaker$getMINECRAFT())
      );
   }

   @Override
   public boolean isFakePlayer(Player player) {
      return player instanceof FakePlayer;
   }

   @Override
   public ItemStack getBasicTradePrice(IBasicItemListing internal) {
      return internal instanceof AccessBasicTrade abt ? abt.crafttweaker$getPrice() : IPlatformHelper.super.getBasicTradePrice(internal);
   }

   @Override
   public ItemStack getBasicTradePrice2(IBasicItemListing internal) {
      return internal instanceof AccessBasicTrade abt ? abt.crafttweaker$getPrice2() : IPlatformHelper.super.getBasicTradePrice2(internal);
   }

   @Override
   public ItemStack getBasicTradeForSale(IBasicItemListing internal) {
      return internal instanceof AccessBasicTrade abt ? abt.crafttweaker$getForSale() : IPlatformHelper.super.getBasicTradeForSale(internal);
   }

   @Override
   public int getBasicTradeMaxTrades(IBasicItemListing internal) {
      return internal instanceof AccessBasicTrade abt ? abt.crafttweaker$getMaxTrades() : IPlatformHelper.super.getBasicTradeMaxTrades(internal);
   }

   @Override
   public int getBasicTradeXp(IBasicItemListing internal) {
      return internal instanceof AccessBasicTrade abt ? abt.crafttweaker$getXp() : IPlatformHelper.super.getBasicTradeXp(internal);
   }

   @Override
   public float getBasicTradePriceMult(IBasicItemListing internal) {
      return internal instanceof AccessBasicTrade abt ? abt.crafttweaker$getPriceMult() : IPlatformHelper.super.getBasicTradePriceMult(internal);
   }

   @Override
   public PossibleEffect createPossibleEffect(MobEffectInstance effect, float probability) {
      return new PossibleEffect(() -> effect, probability);
   }

   @Override
   public void setCompostable(IItemStack stack, Optional<Float> value, boolean undoing) {
      IPlatformHelper.super.setCompostable(stack, value, undoing);
      if (!undoing) {
         Item item = stack.getInternal().getItem();
         ResourceKey<Item> itemResourceKey = Services.REGISTRY.resourceKeyOrThrow(Registries.ITEM, item);
         AccessDataMapLoader mapLoader = (AccessDataMapLoader)AccessNeoForgeInternalHandler.crafttweaker$getDataMap();
         List<DataMapFile<?, ?>> dataMapFiles = mapLoader.results().get(Registries.ITEM).results().get(NeoForgeDataMaps.COMPOSTABLES);
         Map<Either<TagKey<Item>, ResourceKey<Item>>, Optional<WithConditions<DataMapEntry<Compostable>>>> values = new HashMap<>();
         List<Removal<Compostable, Item>> removals = new ArrayList<>();
         Either<TagKey<Item>, ResourceKey<Item>> dataRight = Either.right(itemResourceKey);
         Optional<WithConditions<DataMapEntry<Compostable>>> entry = value.<Compostable>map(Compostable::new)
            .map(compostable -> new DataMapEntry(compostable, true))
            .map(WithConditions::new);
         if (entry.isPresent()) {
            values.put(dataRight, entry);
         } else {
            removals.add(new Removal(dataRight, Optional.empty()));
         }

         dataMapFiles.add(new DataMapFile(false, values, removals));
      }
   }
}
