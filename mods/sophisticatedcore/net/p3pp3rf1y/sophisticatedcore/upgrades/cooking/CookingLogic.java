package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RecipeHelper;

public class CookingLogic<T extends AbstractCookingRecipe> {
   private final ItemStack upgrade;
   private final Consumer<ItemStack> saveHandler;
   private CookingLogic<T>.CookingComponentItemHandler cookingInventory = null;
   public static final int COOK_INPUT_SLOT = 0;
   public static final int COOK_OUTPUT_SLOT = 2;
   public static final int FUEL_SLOT = 1;
   @Nullable
   private RecipeHolder<T> cookingRecipe = null;
   private boolean cookingRecipeInitialized = false;
   private final float burnTimeModifier;
   private final Predicate<ItemStack> isFuel;
   private final Predicate<ItemStack> isInput;
   private final double cookingSpeedMultiplier;
   private final double fuelEfficiencyMultiplier;
   private final RecipeType<T> recipeType;
   private boolean paused = false;
   private long remainingCookTime = 0L;
   private long remainingBurnTime = 0L;
   public static final Codec<Map<ResourceLocation, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(ResourceLocation.CODEC, ExtraCodecs.NON_NEGATIVE_INT);
   public static final StreamCodec<FriendlyByteBuf, Map<ResourceLocation, Integer>> RECIPES_USED_STREAM_CODEC = StreamCodec.of(
      (buf, map) -> buf.writeMap(map, ResourceLocation.STREAM_CODEC, ByteBufCodecs.INT), buf -> buf.readMap(ResourceLocation.STREAM_CODEC, ByteBufCodecs.INT)
   );

   public CookingLogic(
      ItemStack upgrade, Consumer<ItemStack> saveHandler, CookingUpgradeConfig cookingUpgradeConfig, RecipeType<T> recipeType, float burnTimeModifier
   ) {
      this(
         upgrade,
         saveHandler,
         s -> getBurnTime(s, recipeType, burnTimeModifier) > 0,
         s -> RecipeHelper.getCookingRecipe(s, recipeType).isPresent(),
         cookingUpgradeConfig,
         recipeType,
         burnTimeModifier
      );
   }

   public CookingLogic(
      ItemStack upgrade,
      Consumer<ItemStack> saveHandler,
      Predicate<ItemStack> isFuel,
      Predicate<ItemStack> isInput,
      CookingUpgradeConfig cookingUpgradeConfig,
      RecipeType<T> recipeType,
      float burnTimeModifier
   ) {
      this.upgrade = upgrade;
      this.saveHandler = saveHandler;
      this.isFuel = isFuel;
      this.isInput = isInput;
      this.cookingSpeedMultiplier = (Double)cookingUpgradeConfig.cookingSpeedMultiplier.get();
      this.fuelEfficiencyMultiplier = (Double)cookingUpgradeConfig.fuelEfficiencyMultiplier.get();
      this.recipeType = recipeType;
      this.burnTimeModifier = burnTimeModifier;
   }

   private void save() {
      this.saveHandler.accept(this.upgrade);
   }

   public boolean tick(Level level) {
      this.updateTimes(level);
      AtomicBoolean didSomething = new AtomicBoolean(true);
      if (this.isBurning(level) || this.readyToStartCooking()) {
         Optional<T> fr = this.getCookingRecipe();
         if (fr.isEmpty() && this.isCooking()) {
            this.setIsCooking(false);
         }

         fr.ifPresent(recipe -> {
            this.updateFuel(level, (T)recipe);
            if (this.isBurning(level) && this.canSmelt(recipe, level)) {
               this.updateCookingProgress(level, (T)recipe);
            } else if (!this.isBurning(level)) {
               didSomething.set(false);
            }
         });
      }

      if (!this.isBurning(level) && this.isCooking()) {
         this.updateCookingCooldown(level);
      } else {
         didSomething.set(false);
      }

      return didSomething.get();
   }

   private void updateTimes(Level level) {
      if (this.paused) {
         this.unpause(level);
      } else {
         if (this.isBurning(level)) {
            this.remainingBurnTime = this.getBurnTimeFinish() - level.getGameTime();
         } else {
            this.remainingBurnTime = 0L;
         }

         if (this.isCooking()) {
            this.remainingCookTime = this.getCookTimeFinish() - level.getGameTime();
         } else {
            this.remainingCookTime = 0L;
         }
      }
   }

   private void unpause(Level level) {
      this.paused = false;
      if (this.remainingBurnTime > 0L) {
         this.setBurnTimeFinish(level.getGameTime() + this.remainingBurnTime);
      }

      if (this.remainingCookTime > 0L) {
         this.setCookTimeFinish(level.getGameTime() + this.remainingCookTime);
         this.setIsCooking(true);
      }
   }

   public boolean isBurning(Level level) {
      return this.getBurnTimeFinish() >= level.getGameTime();
   }

   private Optional<T> getCookingRecipe() {
      if (!this.cookingRecipeInitialized) {
         this.cookingRecipe = RecipeHelper.<T>getCookingRecipe(this.getCookInput(), this.recipeType).orElse(null);
         this.cookingRecipeInitialized = true;
      }

      return this.cookingRecipe != null ? Optional.of((T)this.cookingRecipe.value()) : Optional.empty();
   }

   private void updateCookingCooldown(Level level) {
      if (this.getRemainingCookTime(level) + 2 > this.getCookTimeTotal()) {
         this.setIsCooking(false);
      } else {
         this.setCookTimeFinish(level.getGameTime() + Math.min(this.getRemainingCookTime(level) + 2, this.getCookTimeTotal()));
      }
   }

   private void updateCookingProgress(Level level, T cookingRecipe) {
      if (this.isCooking() && this.finishedCooking(level)) {
         this.smelt(cookingRecipe, level);
         if (this.canSmelt(cookingRecipe, level)) {
            this.setCookTime(level, (int)(cookingRecipe.getCookingTime() * (1.0 / this.cookingSpeedMultiplier)));
         } else {
            this.setIsCooking(false);
         }
      } else if (!this.isCooking()) {
         this.setIsCooking(true);
         this.setCookTime(level, (int)(cookingRecipe.getCookingTime() * (1.0 / this.cookingSpeedMultiplier)));
      }
   }

   private boolean finishedCooking(Level level) {
      return this.getCookTimeFinish() <= level.getGameTime();
   }

   private boolean readyToStartCooking() {
      return !this.getFuel().isEmpty() && !this.getCookInput().isEmpty();
   }

   private void smelt(Recipe<?> recipe, Level level) {
      if (this.canSmelt(recipe, level)) {
         ItemStack input = this.getCookInput();
         ItemStack recipeOutput = recipe.getResultItem(level.registryAccess());
         ItemStack output = this.getCookOutput();
         if (output.isEmpty()) {
            this.setCookOutput(recipeOutput.copy());
         } else if (output.getItem() == recipeOutput.getItem()) {
            output.grow(recipeOutput.getCount());
            this.setCookOutput(output);
         }

         if (input.getItem() == Blocks.WET_SPONGE.asItem() && !this.getFuel().isEmpty() && this.getFuel().getItem() == Items.BUCKET) {
            this.setFuel(new ItemStack(Items.WATER_BUCKET));
         }

         if (this.cookingRecipe != null) {
            this.incrementRecipeUsed(this.cookingRecipe.id());
            this.addStoredExperience(((AbstractCookingRecipe)this.cookingRecipe.value()).getExperience());
         }

         input.shrink(1);
         this.setCookInput(input);
      }
   }

   private void addStoredExperience(float experience) {
      this.upgrade.set(ModCoreDataComponents.STORED_XP, this.getStoredExperience() + experience);
      this.save();
   }

   public float getStoredExperience() {
      return (Float)this.upgrade.getOrDefault(ModCoreDataComponents.STORED_XP, 0.0F);
   }

   public void drainStoredExperience(float xp) {
      float storedXp = this.getStoredExperience();
      storedXp = Math.max(0.0F, storedXp - xp);
      this.upgrade.set(ModCoreDataComponents.STORED_XP, storedXp);
      this.save();
   }

   private void incrementRecipeUsed(ResourceLocation recipeId) {
      Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap(this.getRecipesUsed());
      recipesUsed.addTo(recipeId, 1);
      this.upgrade.set(ModCoreDataComponents.RECIPES_USED, recipesUsed);
      this.save();
   }

   private void clearRecipesUsed() {
      this.upgrade.set(ModCoreDataComponents.RECIPES_USED, Map.of());
      this.save();
   }

   private Map<ResourceLocation, Integer> getRecipesUsed() {
      return (Map<ResourceLocation, Integer>)this.upgrade.getOrDefault(ModCoreDataComponents.RECIPES_USED, Map.of());
   }

   public void setCookInput(ItemStack input) {
      this.cookingInventory.setStackInSlot(0, input);
   }

   private void setCookOutput(ItemStack stack) {
      this.getCookingInventory().setStackInSlot(2, stack);
   }

   private int getRemainingCookTime(Level level) {
      return (int)(this.getCookTimeFinish() - level.getGameTime());
   }

   private void setCookTime(Level level, int cookTime) {
      this.setCookTimeFinish(level.getGameTime() + cookTime);
      this.setCookTimeTotal(cookTime);
   }

   public void pause() {
      this.paused = true;
      this.setCookTimeFinish(0L);
      this.setIsCooking(false);
      this.setBurnTimeFinish(0L);
   }

   private void updateFuel(Level level, T cookingRecipe) {
      ItemStack fuel = this.getFuel();
      if (!this.isBurning(level) && this.canSmelt(cookingRecipe, level)) {
         if (getBurnTime(fuel, this.recipeType, this.burnTimeModifier) <= 0) {
            return;
         }

         this.setBurnTime(level, (int)(getBurnTime(fuel, this.recipeType, this.burnTimeModifier) * this.fuelEfficiencyMultiplier / this.cookingSpeedMultiplier));
         if (this.isBurning(level)) {
            if (fuel.hasCraftingRemainingItem()) {
               this.setFuelWithoutValidation(fuel.getCraftingRemainingItem());
            } else if (!fuel.isEmpty()) {
               fuel.shrink(1);
               this.setFuel(fuel);
               if (fuel.isEmpty()) {
                  this.setFuel(fuel.getCraftingRemainingItem());
               }
            }
         }
      }
   }

   private void setBurnTime(Level level, int burnTime) {
      this.setBurnTimeFinish(level.getGameTime() + burnTime);
      this.setBurnTimeTotal(burnTime);
   }

   protected boolean canSmelt(Recipe<?> cookingRecipe, Level level) {
      if (this.getCookInput().isEmpty()) {
         return false;
      } else {
         ItemStack recipeOutput = cookingRecipe.getResultItem(level.registryAccess());
         if (recipeOutput.isEmpty()) {
            return false;
         } else {
            ItemStack output = this.getCookOutput();
            if (output.isEmpty()) {
               return true;
            } else if (output.getItem() != recipeOutput.getItem()) {
               return false;
            } else {
               return output.getCount() + recipeOutput.getCount() <= 99 && output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize()
                  ? true
                  : output.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxStackSize();
            }
         }
      }
   }

   private static <T extends AbstractCookingRecipe> int getBurnTime(ItemStack fuel, RecipeType<T> recipeType, float burnTimeModifier) {
      return (int)(fuel.getBurnTime(recipeType) * burnTimeModifier);
   }

   public ItemStack getCookOutput() {
      return this.getCookingInventory().getStackInSlot(2);
   }

   public ItemStack getCookInput() {
      return this.getCookingInventory().getStackInSlot(0);
   }

   public ItemStack getFuel() {
      return this.getCookingInventory().getStackInSlot(1);
   }

   public void setFuel(ItemStack fuel) {
      this.getCookingInventory().setStackInSlot(1, fuel);
   }

   private void setFuelWithoutValidation(ItemStack fuel) {
      this.getCookingInventory().setStackInSlotWithoutValidation(1, fuel);
   }

   public CookingLogic<T>.CookingComponentItemHandler getCookingInventory() {
      if (this.cookingInventory == null) {
         this.cookingInventory = new CookingLogic.CookingComponentItemHandler();
      }

      return this.cookingInventory;
   }

   public long getBurnTimeFinish() {
      return (Long)this.upgrade.getOrDefault(ModCoreDataComponents.BURN_TIME_FINISH, 0L);
   }

   private void setBurnTimeFinish(long burnTimeFinish) {
      this.upgrade.set(ModCoreDataComponents.BURN_TIME_FINISH, burnTimeFinish);
      this.save();
   }

   public int getBurnTimeTotal() {
      return (Integer)this.upgrade.getOrDefault(ModCoreDataComponents.BURN_TIME_TOTAL, 0);
   }

   private void setBurnTimeTotal(int burnTimeTotal) {
      this.upgrade.set(ModCoreDataComponents.BURN_TIME_TOTAL, burnTimeTotal);
      this.save();
   }

   public long getCookTimeFinish() {
      return (Long)this.upgrade.getOrDefault(ModCoreDataComponents.COOK_TIME_FINISH, -1L);
   }

   private void setCookTimeFinish(long cookTimeFinish) {
      this.upgrade.set(ModCoreDataComponents.COOK_TIME_FINISH, cookTimeFinish);
      this.save();
   }

   public int getCookTimeTotal() {
      return (Integer)this.upgrade.getOrDefault(ModCoreDataComponents.COOK_TIME_TOTAL, 0);
   }

   private void setCookTimeTotal(int cookTimeTotal) {
      this.upgrade.set(ModCoreDataComponents.COOK_TIME_TOTAL, cookTimeTotal);
      this.save();
   }

   public boolean isCooking() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.IS_COOKING, false);
   }

   private void setIsCooking(boolean isCooking) {
      this.upgrade.set(ModCoreDataComponents.IS_COOKING, isCooking);
      this.save();
   }

   public void awardUsedRecipesAndPopExperience(ServerPlayer serverPlayer) {
      List<RecipeHolder<?>> recipes = this.getRecipesToAwardAndPopExperience(serverPlayer.serverLevel(), serverPlayer.position());
      serverPlayer.awardRecipes(recipes);
      List<ItemStack> items = InventoryHelper.getStacks(this.cookingInventory);

      for (RecipeHolder<?> recipeholder : recipes) {
         if (recipeholder != null) {
            serverPlayer.triggerRecipeCrafted(recipeholder, items);
         }
      }

      this.clearRecipesUsed();
   }

   public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel serverLevel, Vec3 position) {
      List<RecipeHolder<?>> recipes = Lists.newArrayList();

      for (Entry<ResourceLocation, Integer> entry : this.getRecipesUsed().entrySet()) {
         serverLevel.getRecipeManager().byKey(entry.getKey()).ifPresent(recipes::add);
      }

      this.createExperience(serverLevel, position);
      return recipes;
   }

   private void createExperience(ServerLevel serverLevel, Vec3 position) {
      float storedXp = this.getStoredExperience();
      int i = Mth.floor(storedXp);
      float f = Mth.frac(storedXp);
      if (f != 0.0F && Math.random() < f) {
         i++;
      }

      ExperienceOrb.award(serverLevel, position, i);
      this.drainStoredExperience(i);
   }

   public class CookingComponentItemHandler extends ComponentItemHandler {
      public CookingComponentItemHandler() {
         super(CookingLogic.this.upgrade, ModCoreDataComponents.COOKING_INVENTORY.get(), 3);
      }

      protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
         super.onContentsChanged(slot, oldStack, newStack);
         CookingLogic.this.save();
         if (slot == 0) {
            CookingLogic.this.cookingRecipeInitialized = false;
         }
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         if (!stack.isEmpty() && !ItemStack.isSameItemSameComponents(this.getStackInSlot(slot), stack)) {
            return switch (slot) {
               case 0 -> CookingLogic.this.isInput.test(stack);
               case 1 -> CookingLogic.this.isFuel.test(stack);
               default -> true;
            };
         } else {
            return true;
         }
      }

      public void setStackInSlotWithoutValidation(int slot, ItemStack stack) {
         ItemContainerContents contents = this.getContents();
         ItemStack existing = this.getStackFromContents(contents, slot);
         if (!ItemStack.matches(stack, existing)) {
            this.updateContents(contents, stack, slot);
         }
      }
   }
}
