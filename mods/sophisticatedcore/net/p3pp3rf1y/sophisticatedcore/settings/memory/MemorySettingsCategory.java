package net.p3pp3rf1y.sophisticatedcore.settings.memory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public class MemorySettingsCategory implements ISettingsCategory<MemorySettingsCategory> {
   public static final String NAME = "memory";
   private static final String SLOT_FILTER_ITEMS_TAG = "slotFilterItems";
   private static final String SLOT_FILTER_STACKS_TAG = "slotFilterStacks";
   private static final String IGNORE_NBT_TAG = "ignoreNbt";
   private final Supplier<InventoryHandler> inventoryHandlerSupplier;
   private CompoundTag categoryNbt;
   private final Consumer<CompoundTag> saveNbt;
   private final Map<Integer, Item> slotFilterItems = new LinkedHashMap<>();
   private final Map<Integer, ItemStackKey> slotFilterStacks = new LinkedHashMap<>();
   private final Map<Item, Set<Integer>> filterItemSlots = new HashMap<>();
   private final Map<Integer, Set<Integer>> filterStackSlots = new HashMap<>();
   private boolean ignoreNbt = true;
   private Consumer<Item> onItemAdded = i -> {};
   private Consumer<Integer> onStackAdded = i -> {};
   private Consumer<Item> onItemRemoved = i -> {};
   private Consumer<Integer> onStackRemoved = i -> {};

   public MemorySettingsCategory(Supplier<InventoryHandler> inventoryHandlerSupplier, CompoundTag categoryNbt, Consumer<CompoundTag> saveNbt) {
      this.inventoryHandlerSupplier = inventoryHandlerSupplier;
      this.categoryNbt = categoryNbt;
      this.saveNbt = saveNbt;
      this.deserialize();
   }

   private void deserialize() {
      NBTHelper.getMap(
            this.categoryNbt, "slotFilterItems", Integer::valueOf, (k, v) -> BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(v.getAsString()))
         )
         .ifPresent(map -> map.forEach(this::addSlotItem));
      NBTHelper.getMap(
            this.categoryNbt,
            "slotFilterStacks",
            Integer::valueOf,
            (k, v) -> v instanceof CompoundTag tag
               ? RegistryHelper.getRegistryAccess().flatMap(registryAccess -> ItemStack.parse(registryAccess, tag))
               : Optional.empty()
         )
         .ifPresent(map -> map.forEach(this::addSlotStack));
      this.ignoreNbt = NBTHelper.getBoolean(this.categoryNbt, "ignoreNbt").orElse(true);
   }

   public boolean matchesFilter(int slotNumber, ItemStack stack) {
      if (this.slotFilterItems.containsKey(slotNumber)) {
         return !stack.isEmpty() && stack.getItem() == this.slotFilterItems.get(slotNumber);
      } else {
         return !this.slotFilterStacks.containsKey(slotNumber) ? true : !stack.isEmpty() && this.slotFilterStacks.get(slotNumber).matches(stack);
      }
   }

   public Optional<ItemStack> getSlotFilterStack(int slotNumber, boolean copy) {
      if (this.slotFilterItems.containsKey(slotNumber)) {
         return Optional.of(new ItemStack((ItemLike)this.slotFilterItems.get(slotNumber)));
      } else if (this.slotFilterStacks.containsKey(slotNumber)) {
         ItemStack filterStack = this.slotFilterStacks.get(slotNumber).getStack();
         return Optional.of(copy ? filterStack.copy() : filterStack);
      } else {
         return Optional.empty();
      }
   }

   public boolean isSlotSelected(int slotNumber) {
      return this.slotFilterItems.containsKey(slotNumber) || this.slotFilterStacks.containsKey(slotNumber);
   }

   public void unselectAllSlots() {
      this.unselectAllFilterItemSlots();
      this.unselectAllFilteStackSlots();
      this.serializeFilterItems();
   }

   private void unselectAllFilteStackSlots() {
      this.filterStackSlots.keySet().forEach(i -> this.onStackRemoved.accept(i));
      this.slotFilterStacks.clear();
      this.filterStackSlots.clear();
   }

   private void unselectAllFilterItemSlots() {
      this.filterItemSlots.keySet().forEach(i -> this.onItemRemoved.accept(i));
      this.slotFilterItems.clear();
      this.filterItemSlots.clear();
   }

   public void selectSlots(int minSlot, int maxSlot) {
      for (int slot = minSlot; slot < maxSlot; slot++) {
         InventoryHandler inventoryHandler = this.getInventoryHandler();
         if (slot < inventoryHandler.getSlots()) {
            ItemStack stackInSlot = inventoryHandler.getStackInSlot(slot);
            if (!stackInSlot.isEmpty()) {
               if (this.ignoreNbt) {
                  Item item = stackInSlot.getItem();
                  this.addSlotItem(slot, item);
               } else {
                  this.addSlotStack(slot, stackInSlot);
               }
            } else {
               Item filterItem = inventoryHandler.getFilterItem(slot);
               if (filterItem != Items.AIR) {
                  if (this.ignoreNbt) {
                     this.addSlotItem(slot, filterItem);
                  } else {
                     this.addSlotStack(slot, new ItemStack(filterItem));
                  }
               }
            }
         }
      }

      this.serializeFilterItems();
   }

   private InventoryHandler getInventoryHandler() {
      return this.inventoryHandlerSupplier.get();
   }

   private void addSlotItem(int slot, Item item) {
      this.slotFilterItems.put(slot, item);
      this.filterItemSlots.computeIfAbsent(item, k -> {
         this.onItemAdded.accept(k);
         return new TreeSet<>();
      }).add(slot);
   }

   private void addSlotStack(int slot, ItemStack stack) {
      ItemStackKey isk = ItemStackKey.of(stack);
      this.slotFilterStacks.put(slot, isk);
      int stackHash = isk.hashCode();
      this.filterStackSlots.computeIfAbsent(stackHash, k -> {
         this.onStackAdded.accept(stackHash);
         return new TreeSet<>();
      }).add(slot);
   }

   public void selectSlot(int slotNumber) {
      this.selectSlots(slotNumber, slotNumber + 1);
   }

   public void unselectSlot(int slotNumber) {
      this.unselectFilterItemSlot(slotNumber);
      this.unselectFilterStackSlot(slotNumber);
      this.serializeFilterItems();
   }

   private void unselectFilterItemSlot(int slotNumber) {
      if (this.slotFilterItems.containsKey(slotNumber)) {
         Item item = this.slotFilterItems.remove(slotNumber);
         Set<Integer> itemSlots = this.filterItemSlots.get(item);
         itemSlots.remove(slotNumber);
         if (itemSlots.isEmpty()) {
            this.filterItemSlots.remove(item);
            this.onItemRemoved.accept(item);
         }
      }
   }

   private void unselectFilterStackSlot(int slotNumber) {
      if (this.slotFilterStacks.containsKey(slotNumber)) {
         ItemStackKey isk = this.slotFilterStacks.remove(slotNumber);
         int stackHash = isk.hashCode();
         Set<Integer> stackSlots = this.filterStackSlots.get(stackHash);
         stackSlots.remove(slotNumber);
         if (stackSlots.isEmpty()) {
            this.filterStackSlots.remove(stackHash);
            this.onStackRemoved.accept(stackHash);
         }
      }
   }

   public boolean ignoresNbt() {
      return this.ignoreNbt;
   }

   public void setIgnoreNbt(boolean ignoreNbt) {
      if (this.ignoreNbt != ignoreNbt) {
         Set<Integer> slotIndexes = this.getSlotIndexes();
         if (this.ignoreNbt && !ignoreNbt) {
            this.slotFilterItems.forEach((slot, item) -> {
               ItemStack stack = this.inventoryHandlerSupplier.get().getStackInSlot(slot);
               if (stack.isEmpty()) {
                  stack = new ItemStack(item);
               }

               this.addSlotStack(slot, stack);
            });
            this.unselectAllFilterItemSlots();
         } else {
            this.slotFilterStacks.forEach((slot, isk) -> this.addSlotItem(slot, isk.getStack().getItem()));
            this.unselectAllFilteStackSlots();
         }

         this.serializeFilterItems();
         this.ignoreNbt = ignoreNbt;
         this.serializeIgnoreNbt();
         slotIndexes.forEach(this::selectSlot);
      }
   }

   private void serializeIgnoreNbt() {
      this.categoryNbt.putBoolean("ignoreNbt", this.ignoreNbt);
      this.saveNbt.accept(this.categoryNbt);
   }

   private void serializeFilterItems() {
      NBTHelper.putMap(
         this.categoryNbt, "slotFilterItems", this.slotFilterItems, String::valueOf, i -> StringTag.valueOf(BuiltInRegistries.ITEM.getKey(i).toString())
      );
      NBTHelper.putMap(
         this.categoryNbt,
         "slotFilterStacks",
         this.slotFilterStacks,
         String::valueOf,
         isk -> RegistryHelper.getRegistryAccess().map(registryAccess -> isk.stack().saveOptional(registryAccess)).orElse(new CompoundTag())
      );
      this.saveNbt.accept(this.categoryNbt);
   }

   @Override
   public void reloadFrom(CompoundTag categoryNbt) {
      this.categoryNbt = categoryNbt;
      this.slotFilterItems.clear();
      this.filterItemSlots.clear();
      this.slotFilterStacks.clear();
      this.filterStackSlots.clear();
      this.deserialize();
   }

   public void overwriteWith(MemorySettingsCategory otherCategory) {
      this.unselectAllSlots();
      this.ignoreNbt = otherCategory.ignoreNbt;
      if (this.ignoreNbt) {
         this.overwriteFilterItems(otherCategory);
      } else {
         this.overwriteFilterStacks(otherCategory);
      }

      this.serializeIgnoreNbt();
      this.serializeFilterItems();
   }

   private void overwriteFilterStacks(MemorySettingsCategory otherCategory) {
      InventoryHandler inventoryHandler = this.getInventoryHandler();
      otherCategory.slotFilterStacks.forEach((slot, isk) -> {
         if (slot < inventoryHandler.getSlots()) {
            ItemStack stackInSlot = inventoryHandler.getStackInSlot(slot);
            if (stackInSlot.isEmpty() || otherCategory.matchesFilter(slot, stackInSlot)) {
               this.addSlotStack(slot, isk.getStack());
            }
         }
      });
   }

   private void overwriteFilterItems(MemorySettingsCategory otherCategory) {
      InventoryHandler inventoryHandler = this.getInventoryHandler();
      otherCategory.slotFilterItems.forEach((slot, item) -> {
         if (slot < inventoryHandler.getSlots()) {
            ItemStack stackInSlot = inventoryHandler.getStackInSlot(slot);
            if (stackInSlot.isEmpty() || otherCategory.matchesFilter(slot, stackInSlot)) {
               this.addSlotItem(slot, item);
            }
         }
      });
   }

   public Set<Integer> getSlotIndexes() {
      HashSet<Integer> slots = new HashSet<>(this.slotFilterItems.keySet());
      slots.addAll(this.slotFilterStacks.keySet());
      return slots;
   }

   public Map<Item, Set<Integer>> getFilterItemSlots() {
      return this.filterItemSlots;
   }

   public Map<Integer, Set<Integer>> getFilterStackSlots() {
      return this.filterStackSlots;
   }

   public boolean matchesFilter(ItemStack stack) {
      return this.filterItemSlots.containsKey(stack.getItem())
         || !this.filterStackSlots.isEmpty() && this.filterStackSlots.containsKey(ItemStack.hashItemAndComponents(stack));
   }

   public boolean matchesStackKey(ItemStackKey stackKey) {
      return this.filterStackSlots.containsKey(stackKey.hashCode());
   }

   public boolean matchesItem(Item item) {
      return this.filterItemSlots.containsKey(item);
   }

   public void registerListeners(Consumer<Item> onItemAdded, Consumer<Item> onItemRemoved, Consumer<Integer> onStackAdded, Consumer<Integer> onStackRemoved) {
      this.onItemAdded = onItemAdded;
      this.onItemRemoved = onItemRemoved;
      this.onStackAdded = onStackAdded;
      this.onStackRemoved = onStackRemoved;
   }

   public void unregisterListeners() {
      this.onItemAdded = i -> {};
      this.onItemRemoved = i -> {};
      this.onStackAdded = i -> {};
      this.onStackRemoved = i -> {};
   }

   public void setFilter(int slot, ItemStack filter) {
      InventoryHandler inventoryHandler = this.getInventoryHandler();
      if (slot < inventoryHandler.getSlots()) {
         ItemStack stackInSlot = inventoryHandler.getStackInSlot(slot);
         if (stackInSlot.isEmpty()) {
            if (this.ignoreNbt) {
               Item item = filter.getItem();
               this.addSlotItem(slot, item);
            } else {
               this.addSlotStack(slot, filter);
            }
         }
      }

      this.serializeFilterItems();
   }

   @Override
   public boolean isLargerThanNumberOfSlots(int slots) {
      return this.slotFilterItems.keySet().stream().anyMatch(slotIndex -> slotIndex >= slots)
         || this.slotFilterStacks.keySet().stream().anyMatch(slotIndex -> slotIndex >= slots);
   }

   public void copyTo(MemorySettingsCategory otherCategory, int startFromSlot, int slotOffset) {
      this.slotFilterItems.forEach((slotIndex, item) -> {
         if (slotIndex >= startFromSlot) {
            otherCategory.slotFilterItems.put(slotIndex + slotOffset, item);
         }
      });
      this.slotFilterStacks.forEach((slotIndex, isk) -> {
         if (slotIndex >= startFromSlot) {
            otherCategory.slotFilterStacks.put(slotIndex + slotOffset, isk);
         }
      });
      otherCategory.serializeFilterItems();
   }

   @Override
   public void deleteSlotSettingsFrom(int slotIndex) {
      this.slotFilterItems.entrySet().removeIf(e -> e.getKey() >= slotIndex);
      this.slotFilterStacks.entrySet().removeIf(e -> e.getKey() >= slotIndex);
      this.serializeFilterItems();
   }
}
