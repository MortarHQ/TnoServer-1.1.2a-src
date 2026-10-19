package twilightforest.inventory;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFMenuTypes;
import twilightforest.init.TFRecipes;
import twilightforest.inventory.slot.AssemblySlot;
import twilightforest.inventory.slot.UncraftingResultSlot;
import twilightforest.inventory.slot.UncraftingSlot;
import twilightforest.item.recipe.UncraftingRecipe;
import twilightforest.util.TFItemStackUtils;

public class UncraftingMenu extends RecipeBookMenu<RecipeInput, Recipe<RecipeInput>> {
   private static final String TAG_MARKER = "TwilightForestMarker";
   private final UncraftingContainer uncraftingMatrix = new UncraftingContainer(this);
   public final CraftingContainer assemblyMatrix = new TransientCraftingContainer(this, 3, 3);
   private final CraftingContainer combineMatrix = new TransientCraftingContainer(this, 3, 3);
   public final Container tinkerInput = new UncraftingInputContainer(this);
   private final ResultContainer tinkerResult = new ResultContainer();
   private final ContainerLevelAccess positionData;
   private final Level level;
   private final Player player;
   public int unrecipeInCycle = 0;
   public int ingredientsInCycle = 0;
   public int recipeInCycle = 0;
   @Nullable
   public Recipe<?> storedGhostRecipe = null;

   public static UncraftingMenu fromNetwork(int id, Inventory inventory) {
      return new UncraftingMenu(id, inventory, inventory.player.level(), ContainerLevelAccess.NULL);
   }

   public UncraftingMenu(int id, Inventory inventory, Level level, ContainerLevelAccess positionData) {
      super((MenuType)TFMenuTypes.UNCRAFTING.get(), id);
      this.positionData = positionData;
      this.level = level;
      this.player = inventory.player;
      this.addSlot(new Slot(this.tinkerInput, 0, 13, 35));
      this.addSlot(new UncraftingResultSlot(inventory.player, this.tinkerInput, this.uncraftingMatrix, this.assemblyMatrix, this.tinkerResult, 0, 147, 35));

      for (int invX = 0; invX < 3; invX++) {
         for (int invY = 0; invY < 3; invY++) {
            this.addSlot(
               new UncraftingSlot(
                  inventory.player, this.tinkerInput, this.uncraftingMatrix, this.assemblyMatrix, invY + invX * 3, 300000 + invY * 18, 17 + invX * 18
               )
            );
         }
      }

      for (int var11 = 0; var11 < 3; var11++) {
         for (int invY = 0; invY < 3; invY++) {
            this.addSlot(new AssemblySlot(this.assemblyMatrix, invY + var11 * 3, 62 + invY * 18, 17 + var11 * 18));
         }
      }

      for (int var12 = 0; var12 < 3; var12++) {
         for (int invY = 0; invY < 9; invY++) {
            this.addSlot(new Slot(inventory, invY + var12 * 9 + 9, 8 + invY * 18, 84 + var12 * 18));
         }
      }

      for (int var13 = 0; var13 < 9; var13++) {
         this.addSlot(new Slot(inventory, var13, 8 + var13 * 18, 142));
      }

      this.slotsChanged(this.assemblyMatrix);
      if (!FMLLoader.isProduction()) {
         NonNullList<Slot> slots = this.slots;
         StringJoiner joiner = new StringJoiner(",\n", "Uncrafting Menu Slots:\n", "(" + slots.size() + " total slots)");

         for (Slot slot : this.slots) {
            joiner.add("[index " + slot.index + ": " + slot.getClass().getName() + " (container slot: " + slot.getContainerSlot() + ")]");
         }

         TwilightForestMod.LOGGER.info(joiner.toString());
      }
   }

   public void slotsChanged(Container inventory) {
      if (inventory == this.tinkerInput) {
         this.uncraftingMatrix.clearContent();
         ItemStack inputStack = this.tinkerInput.getItem(0);
         Recipe<?>[] recipes = getRecipesFor(inputStack, this.level);
         int size = recipes.length;
         if (size > 0 && !inputStack.is(ItemTagGenerator.BANNED_UNCRAFTABLES)) {
            Recipe<?> recipe = recipes[Math.floorMod(this.unrecipeInCycle, size)];
            this.storedGhostRecipe = recipe;
            ItemStack[] recipeItems = this.getIngredients(recipe);
            if (!(recipe instanceof ShapedRecipe rec)) {
               for (int i = 0; i < this.uncraftingMatrix.getContainerSize(); i++) {
                  if (i < recipeItems.length) {
                     ItemStack ingredient = normalizeIngredient(recipeItems[i].copy());
                     this.uncraftingMatrix.setItem(i, ingredient);
                  }
               }
            } else {
               int recipeWidth = rec.getWidth();
               int recipeHeight = rec.getHeight();

               for (int invY = 0; invY < recipeHeight; invY++) {
                  for (int invX = 0; invX < recipeWidth; invX++) {
                     int index = invX + invY * recipeWidth;
                     if (index < recipeItems.length) {
                        ItemStack ingredient = normalizeIngredient(recipeItems[index].copy());
                        this.uncraftingMatrix.setItem(invX + invY * 3, ingredient);
                     }
                  }
               }
            }

            if (inputStack.isDamaged()) {
               int damagedParts = this.countDamagedParts(inputStack);

               for (int i = 0; i < 9 && damagedParts > 0; i++) {
                  ItemStack stack = this.uncraftingMatrix.getItem(i);
                  if (isDamageableComponent(stack)) {
                     markStack(stack);
                     damagedParts--;
                  }
               }
            }

            for (int i = 0; i < 9; i++) {
               ItemStack ingredient = this.uncraftingMatrix.getItem(i);
               if (isIngredientProblematic(ingredient)) {
                  markStack(ingredient);
               }
            }

            this.uncraftingMatrix.numberOfInputItems = recipe instanceof UncraftingRecipe uncraftingRecipe
               ? uncraftingRecipe.getCount()
               : recipe.getResultItem(this.level.registryAccess()).getCount();
            this.uncraftingMatrix.uncraftingCost = this.calculateUncraftingCost();
            this.uncraftingMatrix.recraftingCost = 0;
         } else {
            this.storedGhostRecipe = null;
            this.uncraftingMatrix.numberOfInputItems = 0;
            this.uncraftingMatrix.uncraftingCost = 0;
         }
      }

      if (inventory == this.assemblyMatrix || inventory == this.tinkerInput) {
         if (this.tinkerInput.isEmpty()) {
            this.chooseRecipe(this.assemblyMatrix.asCraftInput());
         } else {
            this.tinkerResult.setItem(0, ItemStack.EMPTY);
            this.uncraftingMatrix.uncraftingCost = this.calculateUncraftingCost();
         }

         this.uncraftingMatrix.recraftingCost = 0;
      }

      if (inventory != this.combineMatrix && !this.uncraftingMatrix.isEmpty() && !this.assemblyMatrix.isEmpty()) {
         for (int i = 0; i < 9; i++) {
            ItemStack assembly = this.assemblyMatrix.getItem(i);
            ItemStack uncrafting = this.uncraftingMatrix.getItem(i);
            if (!assembly.isEmpty()) {
               this.combineMatrix.setItem(i, assembly);
            } else if (!uncrafting.isEmpty() && !isMarked(uncrafting)) {
               this.combineMatrix.setItem(i, uncrafting);
            } else {
               this.combineMatrix.setItem(i, ItemStack.EMPTY);
            }
         }

         this.chooseRecipe(this.combineMatrix.asCraftInput());
         ItemStack input = this.tinkerInput.getItem(0);
         ItemStack result = this.tinkerResult.getItem(0);
         if (!result.isEmpty() && isValidMatchForInput(input, result)) {
            if (result.getItem().isEnchantable(result)) {
               Mutable enchants = new Mutable((ItemEnchantments)input.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
               if (result.has(DataComponents.ENCHANTMENTS)) {
                  ((ItemEnchantments)Objects.requireNonNull((ItemEnchantments)result.get(DataComponents.ENCHANTMENTS)))
                     .entrySet()
                     .forEach(enchantment -> enchants.set((Holder)enchantment.getKey(), enchantment.getIntValue()));
               }

               enchants.removeIf(holder -> !result.supportsEnchantment(holder));
               result.remove(DataComponents.ENCHANTMENTS);
               EnchantmentHelper.setEnchantments(result, enchants.toImmutable());
            }

            this.tinkerResult.setItem(0, result);
            this.uncraftingMatrix.uncraftingCost = 0;
            this.uncraftingMatrix.recraftingCost = this.calculateRecraftingCost();
         }
      }
   }

   public static void markStack(ItemStack stack) {
      TFItemStackUtils.addInfoTag(stack, "TwilightForestMarker");
   }

   public static boolean isMarked(ItemStack stack) {
      return TFItemStackUtils.hasInfoTag(stack, "TwilightForestMarker");
   }

   public static void unmarkStack(ItemStack stack) {
      TFItemStackUtils.clearInfoTag(stack, "TwilightForestMarker");
   }

   public static boolean isIngredientProblematic(ItemStack ingredient) {
      return !ingredient.isEmpty() && ingredient.getItem().hasCraftingRemainingItem(ingredient) || ingredient.is(Items.BARRIER);
   }

   private static ItemStack normalizeIngredient(ItemStack ingredient) {
      if (ingredient.getCount() > 1) {
         ingredient.setCount(1);
      }

      return ingredient;
   }

   private static Recipe<?>[] getRecipesFor(ItemStack inputStack, Level world) {
      List<Recipe<?>> recipes = new ArrayList<>();
      if (!inputStack.isEmpty()) {
         for (RecipeHolder<?> recipe : world.getRecipeManager().getRecipes()) {
            if (isRecipeSupported(recipe.value())
               && !recipe.value().isIncomplete()
               && recipe.value().canCraftInDimensions(3, 3)
               && !recipe.value().getIngredients().isEmpty()
               && matches(inputStack, recipe.value().getResultItem(world.registryAccess()))
               && TFConfig.reverseRecipeBlacklist == TFConfig.disableUncraftingRecipes.contains(recipe.id().toString())
               && TFConfig.flipUncraftingModIdList == TFConfig.blacklistedUncraftingModIds.contains(recipe.id().getNamespace())) {
               recipes.add(recipe.value());
            }
         }

         for (RecipeHolder<UncraftingRecipe> uncraftingRecipe : world.getRecipeManager().getAllRecipesFor((RecipeType)TFRecipes.UNCRAFTING_RECIPE.get())) {
            if (((UncraftingRecipe)uncraftingRecipe.value()).isItemStackAnIngredient(inputStack)) {
               recipes.add(uncraftingRecipe.value());
            }
         }
      }

      return recipes.toArray(new Recipe[0]);
   }

   private static boolean isRecipeSupported(Recipe<?> recipe) {
      return TFConfig.allowShapelessUncrafting ? recipe instanceof CraftingRecipe : recipe instanceof ShapedRecipe;
   }

   private static boolean matches(ItemStack input, ItemStack output) {
      return input.is(output.getItem()) && input.getCount() >= output.getCount();
   }

   private static RecipeHolder<CraftingRecipe>[] getRecipesFor(CraftingInput input, Level level) {
      return level.getRecipeManager().getRecipesFor(RecipeType.CRAFTING, input, level).toArray(new RecipeHolder[0]);
   }

   private void chooseRecipe(CraftingInput input) {
      RecipeHolder<CraftingRecipe>[] recipes = getRecipesFor(input, this.level);
      if (recipes.length == 0) {
         this.tinkerResult.setItem(0, ItemStack.EMPTY);
      } else {
         RecipeHolder<CraftingRecipe> recipe = recipes[Math.floorMod(this.recipeInCycle, recipes.length)];
         if (recipe == null
            || this.level.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING) && !((ServerPlayer)this.player).getRecipeBook().contains(recipe.id())) {
            this.tinkerResult.setItem(0, ItemStack.EMPTY);
         } else {
            this.tinkerResult.setRecipeUsed(recipe);
            this.tinkerResult.setItem(0, ((CraftingRecipe)recipe.value()).assemble(input, this.level.registryAccess()));
         }
      }
   }

   private static boolean isValidMatchForInput(ItemStack inputStack, ItemStack resultStack) {
      if (inputStack.is(ItemTags.PICKAXES) && resultStack.is(ItemTags.PICKAXES)) {
         return true;
      } else if (inputStack.is(ItemTags.AXES) && resultStack.is(ItemTags.AXES)) {
         return true;
      } else if (inputStack.is(ItemTags.SHOVELS) && resultStack.is(ItemTags.SHOVELS)) {
         return true;
      } else if (inputStack.is(ItemTags.HOES) && resultStack.is(ItemTags.HOES)) {
         return true;
      } else if (inputStack.is(ItemTags.SWORDS) && resultStack.is(ItemTags.SWORDS)) {
         return true;
      } else if (inputStack.is(net.neoforged.neoforge.common.Tags.Items.TOOLS_BOW) && resultStack.is(net.neoforged.neoforge.common.Tags.Items.TOOLS_BOW)) {
         return true;
      } else if (inputStack.is(net.neoforged.neoforge.common.Tags.Items.TOOLS_CROSSBOW)
         && resultStack.is(net.neoforged.neoforge.common.Tags.Items.TOOLS_CROSSBOW)) {
         return true;
      } else if (inputStack.is(net.neoforged.neoforge.common.Tags.Items.TOOLS_FISHING_ROD)
         && resultStack.is(net.neoforged.neoforge.common.Tags.Items.TOOLS_FISHING_ROD)) {
         return true;
      } else {
         return inputStack.getItem() instanceof ArmorItem input && resultStack.getItem() instanceof ArmorItem result
            ? input.getEquipmentSlot() == result.getEquipmentSlot()
            : false;
      }
   }

   public int getUncraftingCost() {
      return this.uncraftingMatrix.uncraftingCost;
   }

   public int getRecraftingCost() {
      return this.uncraftingMatrix.recraftingCost;
   }

   private int calculateUncraftingCost() {
      if ((!TFConfig.disableUncraftingOnly || this.storedGhostRecipe instanceof UncraftingRecipe) && this.assemblyMatrix.isEmpty()) {
         return this.storedGhostRecipe instanceof UncraftingRecipe recipe
            ? recipe.getCost()
            : (int)Math.round(countDamageableParts(this.uncraftingMatrix) * TFConfig.uncraftingXpCostMultiplier);
      } else {
         return 0;
      }
   }

   private int calculateRecraftingCost() {
      ItemStack input = this.tinkerInput.getItem(0);
      ItemStack output = this.tinkerResult.getItem(0);
      if (!input.isEmpty() && !output.isEmpty()) {
         if (!output.getItem().isEnchantable(output)) {
            return 0;
         }

         int cost = 0;
         if (!ItemStack.isSameItem(input, output)) {
            cost += this.assemblyMatrix.getItems().stream().filter(stack -> !stack.isEmpty()).toList().size();
         }

         int enchantCost = countTotalEnchantmentCost(input);
         cost += enchantCost;
         int damagedCost = (1 + this.countDamagedParts(input)) * output.getEnchantments().size();
         cost += damagedCost;
         cost = Math.max(1, cost);
         return (int)Math.round(cost * TFConfig.repairingXpCostMultiplier);
      } else {
         return 0;
      }
   }

   private static int countTotalEnchantmentCost(ItemStack stack) {
      int count = 0;

      for (Entry<Holder<Enchantment>> entry : stack.getEnchantments().entrySet()) {
         Enchantment ench = (Enchantment)((Holder)entry.getKey()).value();
         int level = entry.getIntValue();
         if (level > 0) {
            count += getWeightModifier(ench) * level;
            count++;
         }
      }

      return count;
   }

   private static int getWeightModifier(Enchantment ench) {
      return switch (ench.getWeight()) {
         case 1 -> 8;
         case 2 -> 4;
         case 3, 4, 5 -> 2;
         default -> 1;
      };
   }

   public void clicked(int slotNum, int mouseButton, ClickType clickType, Player player) {
      if (slotNum > 0
         && this.getSlotContainer(slotNum) == this.assemblyMatrix
         && player.containerMenu.getCarried().isEmpty()
         && !((Slot)this.slots.get(slotNum)).hasItem()
         && this.assemblyMatrix.isEmpty()
         && (clickType != ClickType.SWAP || player.getInventory().getItem(mouseButton).isEmpty())) {
         slotNum -= 9;
      }

      if (slotNum <= 0
         || this.getSlotContainer(slotNum) != this.tinkerResult
         || this.calculateRecraftingCost() <= player.experienceLevel
         || player.getAbilities().instabuild) {
         if (slotNum > 0 && this.getSlotContainer(slotNum) == this.uncraftingMatrix) {
            if (TFConfig.disableUncraftingOnly && !(this.storedGhostRecipe instanceof UncraftingRecipe)) {
               return;
            }

            if (this.calculateUncraftingCost() > player.experienceLevel && !player.getAbilities().instabuild) {
               return;
            }

            ItemStack stackInSlot = ((Slot)this.slots.get(slotNum)).getItem();
            if (stackInSlot.isEmpty() || isMarked(stackInSlot)) {
               return;
            }
         }

         super.clicked(slotNum, mouseButton, clickType, player);
         if (slotNum > 0 && this.getSlotContainer(slotNum) == this.tinkerInput) {
            this.slotsChanged(this.tinkerInput);
         }
      }
   }

   @NotNull
   private Container getSlotContainer(int slotNum) {
      return ((Slot)this.slots.get(slotNum)).container;
   }

   public static boolean isDamageableComponent(ItemStack stack) {
      return !stack.isEmpty() && !stack.is(ItemTagGenerator.UNCRAFTING_IGNORES_COST);
   }

   public static int countDamageableParts(Container matrix) {
      int count = matrix.getContainerSize();

      for (int i = 0; i < matrix.getContainerSize(); i++) {
         if (isIngredientProblematic(matrix.getItem(i)) || isMarked(matrix.getItem(i)) || !isDamageableComponent(matrix.getItem(i))) {
            count--;
         }
      }

      return count;
   }

   private int countDamagedParts(ItemStack input) {
      int totalMax4 = Math.max(4, countDamageableParts(this.uncraftingMatrix));
      float damage = (float)input.getDamageValue() / input.getMaxDamage();
      return (int)Math.ceil(totalMax4 * damage);
   }

   public ItemStack quickMoveStack(Player player, int slotNum) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(slotNum);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (slotNum == 0) {
            if (!this.moveItemStackTo(itemstack1, 20, 56, false)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(itemstack1, itemstack);
         } else if (slotNum == 1) {
            this.positionData.execute((p_39378_, p_39379_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, player));
            if (!this.moveItemStackTo(itemstack1, 20, 56, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(itemstack1, itemstack);
         } else if (slotNum >= 20 && slotNum < 56) {
            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (slot.container == this.assemblyMatrix) {
            if (!this.moveItemStackTo(itemstack1, 20, 56, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.moveItemStackTo(itemstack1, 20, 56, false)) {
            slot.onTake(player, itemstack1);
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, itemstack1);
         if (slotNum == 1) {
            player.drop(itemstack1, false);
         }
      }

      return itemstack;
   }

   public void removed(Player player) {
      super.removed(player);
      this.positionData.execute((world, pos) -> {
         this.clearContainer(player, this.assemblyMatrix);
         this.clearContainer(player, this.tinkerInput);
      });
   }

   private ItemStack[] getIngredients(Recipe<?> recipe) {
      ItemStack[] stacks = new ItemStack[recipe.getIngredients().size()];

      for (int i = 0; i < recipe.getIngredients().size(); i++) {
         ItemStack[] matchingStacks = Arrays.stream(((Ingredient)recipe.getIngredients().get(i)).getItems())
            .filter(s -> !s.is(ItemTagGenerator.BANNED_UNCRAFTING_INGREDIENTS))
            .toArray(ItemStack[]::new);
         stacks[i] = matchingStacks.length > 0 ? matchingStacks[Math.floorMod(this.ingredientsInCycle, matchingStacks.length)] : ItemStack.EMPTY;
      }

      return stacks;
   }

   public boolean stillValid(Player player) {
      return !TFConfig.disableEntireTable && stillValid(this.positionData, player, (Block)TFBlocks.UNCRAFTING_TABLE.get());
   }

   public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
      this.assemblyMatrix.fillStackedContents(stackedContents);
   }

   public void clearCraftingContent() {
      this.tinkerInput.clearContent();
      this.assemblyMatrix.clearContent();
      this.tinkerResult.clearContent();
   }

   public int getResultSlotIndex() {
      return 1;
   }

   public int getGridWidth() {
      return this.assemblyMatrix.getWidth();
   }

   public int getGridHeight() {
      return this.assemblyMatrix.getHeight();
   }

   public int getSize() {
      return 20;
   }

   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   public boolean shouldMoveToInventory(int slot) {
      return slot == 0 || 11 <= slot && slot <= 19;
   }

   public boolean recipeMatches(RecipeHolder<Recipe<RecipeInput>> recipeHolder) {
      return recipeHolder.value().matches(this.assemblyMatrix.asCraftInput(), this.player.level());
   }

   public void handlePlacement(boolean placeAll, RecipeHolder<?> recipe, ServerPlayer player) {
      new UncrafterPlaceRecipe(this).recipeClicked(player, recipe, placeAll);
   }
}
