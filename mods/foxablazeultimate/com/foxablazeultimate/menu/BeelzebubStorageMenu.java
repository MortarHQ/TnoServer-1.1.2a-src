package com.foxablazeultimate.menu;

import com.foxablazeultimate.item.beelzebub.CapturedEntityItem;
import com.foxablazeultimate.menu.slot.SharedBucketSlot;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.menu.container.TensuraCraftingContainer;
import io.github.manasmods.tensura.menu.slot.SpatialSlot;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BeelzebubStorageMenu extends RecipeBookMenu<CraftingInput, CraftingRecipe> {
   public static final int SPATIAL_SLOT_START = 50;
   public static final int BUCKET_INPUT_SLOT = 48;
   public static final int BUCKET_OUTPUT_SLOT = 49;
   private static final int BUCKET_INPUT_X = 207;
   private static final int BUCKET_INPUT_Y = 123;
   private static final int BUCKET_OUTPUT_X = 207;
   private static final int BUCKET_OUTPUT_Y = 174;
   private final ManasSkill skill;
   private final Player player;
   private final LivingEntity storageOwner;
   private final int page;
   private final SpatialStorageContainer container;
   private final TensuraCraftingContainer craftSlots;
   private final ResultContainer craftResultSlots;
   private boolean placingRecipe;
   private final SpatialStorageContainer furnaceInputSlots;
   private final Slot furnaceInputSlot;
   private final Slot furnaceResultSlot;
   private final ResultContainer furnaceResultSlots;
   private final SpatialStorageContainer bucketInputSlots;
   public final ResultContainer bucketOutput = new ResultContainer() {
      public int getMaxStackSize() {
         return 64;
      }
   };
   private static final BeelzebubStorageMenu.ExtractEntry[] EXTRACT_TABLE = new BeelzebubStorageMenu.ExtractEntry[]{
      new BeelzebubStorageMenu.ExtractEntry(Items.BUCKET, 3.0, Items.WATER_BUCKET, (byte)0),
      new BeelzebubStorageMenu.ExtractEntry(Items.SPONGE, 18.0, Items.WET_SPONGE, (byte)0),
      new BeelzebubStorageMenu.ExtractEntry(Items.GLASS_BOTTLE, 1.0, Items.POTION, (byte)0),
      new BeelzebubStorageMenu.ExtractEntry(Items.BUCKET, 3.0, Items.LAVA_BUCKET, (byte)1)
   };

   public BeelzebubStorageMenu(int id, Inventory inv, LivingEntity storageOwner, SpatialStorageContainer container, ManasSkill skill, int page) {
      super((MenuType)null, id);
      this.skill = skill;
      this.player = inv.player;
      this.storageOwner = storageOwner;
      this.page = page;
      this.container = container;
      container.startOpen(this.player);
      this.craftSlots = new TensuraCraftingContainer(this, 3, 3);
      this.craftResultSlots = new ResultContainer();
      this.furnaceInputSlots = new SpatialStorageContainer(1, 128);
      this.furnaceInputSlot = new Slot(this.furnaceInputSlots, 0, 183, 89) {
         public void set(ItemStack stack) {
            super.set(stack);
            BeelzebubStorageMenu.this.slotChangedFurnaceInput();
         }
      };
      this.furnaceResultSlots = new ResultContainer() {
         public int getMaxStackSize() {
            return BeelzebubStorageMenu.this.getMaxStack();
         }
      };
      this.furnaceResultSlot = new Slot(this.furnaceResultSlots, 0, 224, 89) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }

         public void onTake(Player p, ItemStack s) {
            super.onTake(p, s);
            if (!BeelzebubStorageMenu.this.furnaceInputSlots.isEmpty()) {
               BeelzebubStorageMenu.this.slotChangedFurnaceInput();
            }
         }
      };
      this.bucketInputSlots = new SpatialStorageContainer(1, 1);
      this.addPlayerInventory(inv);
      this.addCraftingSlots();
      this.addFurnaceSlots();
      this.addBucketSlots();
      this.addSpatialSlots();
   }

   private void addPlayerInventory(Inventory inv) {
      for (int row = 0; row < 3; row++) {
         for (int slot = 0; slot < 9; slot++) {
            this.addSlot(new Slot(inv, slot + row * 9 + 9, 8 + slot * 18, 119 + row * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(inv, i, 8 + i * 18, 177));
      }
   }

   private void addCraftingSlots() {
      for (int x = 0; x < 3; x++) {
         for (int y = 0; y < 3; y++) {
            this.addSlot(new Slot(this.craftSlots, y + x * 3, 175 + y * 18, 32 + x * 18));
         }
      }

      this.addSlot(new ResultSlot(this.player, this.craftSlots, this.craftResultSlots, 0, 232, 50));
   }

   private void addFurnaceSlots() {
      this.addSlot(this.furnaceInputSlot);
      this.addSlot(this.furnaceResultSlot);
   }

   private void addBucketSlots() {
      this.addSlot(new SharedBucketSlot(this, this.bucketInputSlots, 0, 207, 123));
      this.addSlot(new Slot(this.bucketOutput, 0, 207, 174) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }
      });
   }

   private void addSpatialSlots() {
      int slotIndex = 27 * this.page;
      int remaining = this.container.getContainerSize() - this.page * 27;

      for (int i = 0; i < 3 && remaining > 0; i++) {
         for (int j = 0; j < 9 && remaining > 0; j++) {
            this.addSlot(new SpatialSlot(this.container, slotIndex, 8 + j * 18, 44 + i * 18));
            slotIndex++;
            remaining--;
         }
      }
   }

   public int getSpatialSize() {
      return Math.min(this.container.getContainerSize() - this.page * 27, 27);
   }

   public int getMaxStack() {
      return this.container.getMaxStackSize();
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   public int getPage() {
      return this.page;
   }

   public ManasSkill getSkill() {
      return this.skill;
   }

   public Player getPlayer() {
      return this.player;
   }

   public LivingEntity getStorageOwner() {
      return this.storageOwner;
   }

   public SpatialStorageContainer getContainer() {
      return this.container;
   }

   protected static void slotChangedCraftingGrid(
      AbstractContainerMenu menu,
      Level level,
      Player player,
      CraftingContainer craftingContainer,
      ResultContainer resultContainer,
      @Nullable RecipeHolder<CraftingRecipe> recipeHolder
   ) {
      if (!level.isClientSide()) {
         CraftingInput craftingInput = craftingContainer.asCraftInput();
         ServerPlayer serverPlayer = (ServerPlayer)player;
         ItemStack result = ItemStack.EMPTY;
         Optional<RecipeHolder<CraftingRecipe>> optional = level.getServer()
            .getRecipeManager()
            .getRecipeFor(RecipeType.CRAFTING, craftingInput, level, recipeHolder);
         if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipe = optional.get();
            CraftingRecipe craftingRecipe = (CraftingRecipe)recipe.value();
            if (resultContainer.setRecipeUsed(level, serverPlayer, recipe)) {
               ItemStack assembled = craftingRecipe.assemble(craftingInput, level.registryAccess());
               if (assembled.isItemEnabled(level.enabledFeatures())) {
                  result = assembled;
               }
            }
         }

         resultContainer.setItem(0, result);
         menu.setRemoteSlot(0, result);
         serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, result));
      }
   }

   public void slotsChanged(Container container) {
      if (!this.placingRecipe) {
         slotChangedCraftingGrid(this, this.player.level(), this.player, this.craftSlots, this.craftResultSlots, null);
      }
   }

   private void slotChangedFurnaceInput() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         ServerLevel var15 = serverPlayer.serverLevel();
         MinecraftServer server = serverPlayer.getServer();
         if (server != null) {
            SingleRecipeInput input = new SingleRecipeInput(this.furnaceInputSlots.getItem(0));
            Optional<RecipeHolder<SmeltingRecipe>> opt = server.getRecipeManager().getRecipeFor(RecipeType.SMELTING, input, var15);
            if (!opt.isEmpty()) {
               RecipeHolder<SmeltingRecipe> recipe = opt.get();
               SmeltingRecipe smelting = (SmeltingRecipe)recipe.value();
               if (this.furnaceResultSlots.setRecipeUsed(var15, serverPlayer, recipe)) {
                  ItemStack result = smelting.assemble(input, var15.registryAccess());
                  if (result.isItemEnabled(var15.enabledFeatures())) {
                     ItemStack oldResult = this.furnaceResultSlots.getItem(0);
                     if (oldResult.isEmpty() || oldResult.is(result.getItem())) {
                        if (this.furnaceResultSlots.setRecipeUsed(var15, serverPlayer, opt.get())) {
                           int countToSet = 0;
                           int countToRemove = 0;
                           int recipeItemCount = result.getCount();
                           int resultContainerItemCount = oldResult.getCount();
                           int inputSize = this.furnaceInputSlots.getItem(0).getCount();
                           if (resultContainerItemCount != this.getMaxStack()) {
                              while (resultContainerItemCount + recipeItemCount + countToSet <= this.getMaxStack() && inputSize - countToRemove > 0) {
                                 countToSet += recipeItemCount;
                                 countToRemove++;
                              }

                              result.setCount(countToSet + resultContainerItemCount);
                              this.furnaceInputSlots.getItem(0).shrink(countToRemove);
                              this.furnaceResultSlots.setItem(0, result);
                              this.setRemoteSlot(0, result);
                              serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, result));
                              this.furnaceResultSlots.awardUsedRecipes(serverPlayer, List.of(result));
                              serverPlayer.playNotifySound(SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void beginPlacingRecipe() {
      this.placingRecipe = true;
   }

   public void finishPlacingRecipe(RecipeHolder<CraftingRecipe> recipeHolder) {
      this.placingRecipe = false;
      slotChangedCraftingGrid(this, this.player.level(), this.player, this.craftSlots, this.craftResultSlots, recipeHolder);
   }

   public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
      this.craftSlots.fillStackedContents(stackedContents);
   }

   public void clearCraftingContent() {
      this.craftSlots.clearContent();
      this.craftResultSlots.clearContent();
   }

   public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipeHolder) {
      return ((CraftingRecipe)recipeHolder.value()).matches(this.craftSlots.asCraftInput(), this.player.level());
   }

   public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
      return !isCapturedEntityStack(stack) && !isCapturedEntityStack(slot.getItem())
         ? slot.container != this.craftResultSlots && super.canTakeItemForPickAll(stack, slot)
         : false;
   }

   public int getResultSlotIndex() {
      return 0;
   }

   public int getGridWidth() {
      return this.craftSlots.getWidth();
   }

   public int getGridHeight() {
      return this.craftSlots.getHeight();
   }

   public int getSize() {
      return 10;
   }

   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   public boolean shouldMoveToInventory(int i) {
      return i != this.getResultSlotIndex();
   }

   public boolean clickMenuButton(Player player, int i) {
      if (i == 0 && this.page > 0) {
         return this.openPage(player, this.page - 1);
      } else {
         return i == 1 && this.page < (this.container.getContainerSize() - 1) / 27 ? this.openPage(player, this.page + 1) : false;
      }
   }

   private boolean openPage(Player player, int newPage) {
      ManasSkillInstance instance = this.getSkillInstance(this.storageOwner);
      if (instance != null && instance.getSkill() instanceof ISpatialStorage spatial) {
         this.removed(player);
         if (player instanceof ServerPlayer serverPlayer) {
            spatial.openSpatialStoragePage(serverPlayer, this.storageOwner, instance, newPage);
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   private ManasSkillInstance getSkillInstance(LivingEntity owner) {
      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(owner).getSkill(this.skill);
      return opt.orElse(null);
   }

   public void removed(Player p) {
      ItemStack carried = this.getCarried();
      if (isCapturedEntityStack(carried) && this.container.canAddItem(carried)) {
         ItemStack remainder = this.container.addItem(carried.copy());
         if (remainder.isEmpty()) {
            this.setCarried(ItemStack.EMPTY);
         }
      }

      ManasSkillInstance instance = this.getSkillInstance(this.storageOwner);
      if (instance != null && instance.getSkill() instanceof ISpatialStorage spatial) {
         spatial.saveContainer(instance, this.storageOwner, this.container);
      }

      this.container.stopOpen(p);
      this.clearContainer(p, this.craftSlots);
      this.clearContainer(p, this.furnaceInputSlots);
      this.clearContainer(p, this.furnaceResultSlots);
      this.clearContainer(p, this.bucketInputSlots);
      this.clearContainer(p, this.bucketOutput);
      super.removed(p);
   }

   public void extractFluid(ServerPlayer player, byte fluidType) {
      ItemStack carried = this.getCarried();
      if (!carried.isEmpty()) {
         BeelzebubStorageMenu.ExtractEntry entry = lookupExtract(carried.getItem(), fluidType);
         if (entry != null) {
            IAbility ability = TensuraStorages.getAbilityFrom(this.storageOwner);
            double currentPoint = fluidType == 0 ? ability.getWaterPoint() : ability.getLavaPoint();
            if (!(currentPoint < entry.cost())) {
               double newPoint = currentPoint - entry.cost();
               if (fluidType == 0) {
                  ability.setWaterPoint(newPoint);
               } else {
                  ability.setLavaPoint(newPoint);
               }

               ability.markDirty();
               ItemStack filled = entry.output().getDefaultInstance();
               if (carried.getCount() <= 1) {
                  this.setCarried(filled);
               } else {
                  carried.shrink(1);
                  this.setCarried(carried);
                  if (!player.getInventory().add(filled)) {
                     player.drop(filled, false);
                  }
               }

               SoundEvent sound = fluidType == 0 ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_FILL_LAVA;
               player.playSound(sound, 1.0F, 1.0F);
               this.broadcastChanges();
            }
         }
      }
   }

   private static BeelzebubStorageMenu.ExtractEntry lookupExtract(Item input, byte fluidType) {
      for (BeelzebubStorageMenu.ExtractEntry e : EXTRACT_TABLE) {
         if (e.input().equals(input) && e.kind() == fluidType) {
            return e;
         }
      }

      return null;
   }

   public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
      boolean spatialSlot = slotId >= 50 && slotId < 50 + this.getSpatialSize();
      if (isCapturedEntityStack(this.getCarried())) {
         if (spatialSlot && ((Slot)this.slots.get(slotId)).getItem().isEmpty()) {
            ((Slot)this.slots.get(slotId)).set(this.getCarried());
            this.setCarried(ItemStack.EMPTY);
            this.broadcastChanges();
         }
      } else {
         if (slotId >= 0 && slotId < this.slots.size()) {
            Slot slot = (Slot)this.slots.get(slotId);
            ItemStack stack = slot.getItem();
            if (isCapturedEntityStack(stack)) {
               if (!spatialSlot && slotId < 36 && clickType == ClickType.QUICK_MOVE) {
                  this.quickMoveStack(player, slotId);
                  return;
               }

               if (spatialSlot
                  && player instanceof ServerPlayer serverPlayer
                  && (clickType == ClickType.QUICK_MOVE || clickType == ClickType.PICKUP && dragType == 0)) {
                  this.releaseCapturedEntity(serverPlayer, slot, stack);
               }

               return;
            }
         }

         super.clicked(slotId, dragType, clickType, player);
      }
   }

   private void releaseCapturedEntity(ServerPlayer player, Slot slot, ItemStack stack) {
      CapturedEntityItem.ReleaseStatus status = CapturedEntityItem.releaseFromStack(player, stack);
      if (status.shouldConsume()) {
         slot.set(ItemStack.EMPTY);
         this.broadcastChanges();
      }
   }

   private static boolean isCapturedEntityStack(ItemStack stack) {
      return !stack.isEmpty() && stack.getItem() == FoxAblazeUltimateItems.CAPTURED_ENTITY.get();
   }

   public ItemStack quickMoveStack(Player p, int i) {
      ItemStack copy = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(i);
      if (!slot.hasItem()) {
         return copy;
      } else {
         ItemStack stack = slot.getItem();
         if (i >= 50 && i < 50 + this.getSpatialSize() && isCapturedEntityStack(stack) && p instanceof ServerPlayer serverPlayer) {
            this.releaseCapturedEntity(serverPlayer, slot, stack);
            return ItemStack.EMPTY;
         } else {
            copy = stack.copy();
            int spatialEnd = 50 + this.getSpatialSize();
            if (i == 45 || i == 47 || i == 49) {
               stack.getItem().onCraftedBy(stack, this.player.level(), this.player);
               if (!this.moveItemStackTo(stack, 0, 36, false) && !this.moveItemStackTo(stack, 50, spatialEnd, false)) {
                  return ItemStack.EMPTY;
               }

               slot.onQuickCraft(stack, copy);
            } else if (i >= 0 && i < 36) {
               if (i < 27) {
                  if (!this.moveItemStackTo(stack, 50, spatialEnd, false) && !this.moveItemStackTo(stack, 27, 36, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (!this.moveItemStackTo(stack, 50, spatialEnd, false) && !this.moveItemStackTo(stack, 0, 27, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (i >= 50 && i <= spatialEnd) {
               if (!this.moveItemStackTo(stack, 0, 36, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(stack, 0, 27, false) && !this.moveItemStackTo(stack, 50, spatialEnd, false)) {
               return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
               slot.setByPlayer(ItemStack.EMPTY);
            } else {
               slot.setChanged();
            }

            if (stack.getCount() == copy.getCount()) {
               return ItemStack.EMPTY;
            }

            slot.onTake(this.player, stack);
            return copy;
         }
      }
   }

   protected boolean moveItemStackTo(ItemStack pStack, int pStartIndex, int pEndIndex, boolean pReverseDirection) {
      boolean flag = false;
      int i = pReverseDirection ? pEndIndex - 1 : pStartIndex;
      if (pStack.isStackable()) {
         while (!pStack.isEmpty() && (pReverseDirection ? i >= pStartIndex : i < pEndIndex)) {
            Slot slot = (Slot)this.slots.get(i);
            ItemStack itemstack = slot.getItem();
            if (!itemstack.isEmpty() && slot.mayPlace(pStack) && ItemStack.isSameItemSameComponents(pStack, itemstack)) {
               int j = itemstack.getCount() + pStack.getCount();
               int maxSize = i >= 50 ? this.container.getMaxStackSize() : Math.min(itemstack.getMaxStackSize(), slot.getMaxStackSize());
               if (j <= maxSize) {
                  pStack.setCount(0);
                  itemstack.setCount(j);
                  slot.setChanged();
                  flag = true;
               } else if (itemstack.getCount() < maxSize) {
                  pStack.shrink(maxSize - itemstack.getCount());
                  itemstack.setCount(maxSize);
                  slot.setChanged();
                  flag = true;
               }
            }

            if (pReverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (!pStack.isEmpty()) {
         i = pReverseDirection ? pEndIndex - 1 : pStartIndex;

         while (pReverseDirection ? i >= pStartIndex : i < pEndIndex) {
            Slot pSlot = (Slot)this.slots.get(i);
            ItemStack stack = pSlot.getItem();
            if (stack.isEmpty() && pSlot.mayPlace(pStack)) {
               if (pStack.getCount() > pSlot.getMaxStackSize()) {
                  pSlot.set(pStack.split(pSlot.getMaxStackSize()));
               } else {
                  pSlot.set(pStack.split(pStack.getCount()));
               }

               pSlot.setChanged();
               flag = true;
               break;
            }

            if (pReverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   private record ExtractEntry(Item input, double cost, Item output, byte kind) {
   }
}
