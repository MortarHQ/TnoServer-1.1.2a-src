package net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.renderdata.DisplaySide;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.ISlotColorCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.MathHelper;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class ItemDisplaySettingsCategory implements ISettingsCategory<ItemDisplaySettingsCategory>, ISlotColorCategory {
   public static final String NAME = "item_display";
   private static final String SLOT_TAG = "slot";
   private static final String ROTATION_TAG = "rotation";
   private static final String SLOTS_TAG = "slots";
   private static final String ROTATIONS_TAG = "rotations";
   private static final String COLOR_TAG = "color";
   private static final String DISPLAY_SIDE_TAG = "displaySide";
   private final Supplier<InventoryHandler> inventoryHandlerSupplier;
   private final Supplier<RenderInfo> renderInfoSupplier;
   private CompoundTag categoryNbt;
   private final Consumer<CompoundTag> saveNbt;
   private final int itemNumberLimit;
   private final Supplier<MemorySettingsCategory> getMemorySettings;
   private DyeColor color = DyeColor.RED;
   private final List<Integer> slotIndexes = new LinkedList<>();
   private Map<Integer, Integer> slotRotations = new HashMap<>();
   private DisplaySide displaySide = DisplaySide.FRONT;

   public ItemDisplaySettingsCategory(
      Supplier<InventoryHandler> inventoryHandlerSupplier,
      Supplier<RenderInfo> renderInfoSupplier,
      CompoundTag categoryNbt,
      Consumer<CompoundTag> saveNbt,
      int itemNumberLimit,
      Supplier<MemorySettingsCategory> getMemorySettings
   ) {
      this.inventoryHandlerSupplier = inventoryHandlerSupplier;
      this.renderInfoSupplier = renderInfoSupplier;
      this.categoryNbt = categoryNbt;
      this.saveNbt = saveNbt;
      this.itemNumberLimit = itemNumberLimit;
      this.getMemorySettings = getMemorySettings;
      this.deserialize();
   }

   public int getItemNumberLimit() {
      return this.itemNumberLimit;
   }

   public void unselectSlot(int slotIndex) {
      int orderIndex = this.slotIndexes.indexOf(slotIndex);
      this.slotIndexes.remove(orderIndex);
      this.slotRotations.remove(slotIndex);
      if (this.slotIndexes.isEmpty()) {
         this.categoryNbt.remove("slots");
         this.categoryNbt.remove("rotations");
      }

      this.serializeSlotIndexes();
      this.updateFullRenderInfo();
   }

   private boolean haveRenderedItemsChanged() {
      List<RenderInfo.DisplayItem> previousDisplayItems = this.renderInfoSupplier.get().getItemDisplayRenderInfo().getDisplayItems();
      List<Integer> inaccessibleSlots = this.renderInfoSupplier.get().getItemDisplayRenderInfo().getInaccessibleSlots();
      if (previousDisplayItems.size() != this.slotIndexes.size()) {
         return true;
      }

      int i = 0;
      InventoryHandler inventoryHandler = this.inventoryHandlerSupplier.get();

      for (int slotIndex : this.slotIndexes) {
         ItemStack newItem = this.getSlotItemCopy(slotIndex).orElse(ItemStack.EMPTY);
         ItemStack stack = previousDisplayItems.get(i).getItem();
         if (ItemStack.hashItemAndComponents(newItem) != ItemStack.hashItemAndComponents(stack)
            || inaccessibleSlots.contains(slotIndex) == inventoryHandler.isSlotAccessible(slotIndex)) {
            return true;
         }

         i++;
      }

      return i != previousDisplayItems.size();
   }

   private boolean haveCountsOrFillRatiosChanged(InventoryHandler inventoryHandler) {
      if (this.renderInfoSupplier.get().showsCountsAndFillRatios()) {
         List<Integer> previousSlotCounts = this.renderInfoSupplier.get().getItemDisplayRenderInfo().getSlotCounts();
         List<Float> previousSlotFillRatios = this.renderInfoSupplier.get().getItemDisplayRenderInfo().getSlotFillRatios();
         List<Integer> previousInfiniteSlots = this.renderInfoSupplier.get().getItemDisplayRenderInfo().getInfiniteSlots();
         if (previousSlotCounts.size() != inventoryHandler.getSlots() || previousSlotFillRatios.size() != inventoryHandler.getSlots()) {
            return true;
         }

         for (int slotIndex = 0; slotIndex < inventoryHandler.getSlots(); slotIndex++) {
            int previousSlotCount = previousSlotCounts.get(slotIndex);
            float previousSlotFillRatio = previousSlotFillRatios.get(slotIndex);
            ItemStack stack = inventoryHandler.getStackInSlot(slotIndex);
            float currentSlotFillRatio = calculateSlotFillRatio(stack, inventoryHandler, slotIndex);
            if (previousSlotCount != stack.getCount()
               || previousInfiniteSlots.contains(slotIndex) != inventoryHandler.isInfinite(slotIndex)
               || !MathHelper.epsilonEquals(previousSlotFillRatio, currentSlotFillRatio)) {
               return true;
            }
         }
      }

      return false;
   }

   private void updateFullRenderInfo() {
      List<RenderInfo.DisplayItem> displayItems = new ArrayList<>();
      List<Integer> inaccessibleSlots = new ArrayList<>();
      InventoryHandler inventoryHandler = this.inventoryHandlerSupplier.get();
      this.collectDisplayItemsAndInaccessibleSlots(displayItems, inventoryHandler, inaccessibleSlots);
      List<Integer> slotCounts = new ArrayList<>();
      List<Float> slotFillRatios = new ArrayList<>();
      List<Integer> infiniteSlots = new ArrayList<>();
      RenderInfo renderInfo = this.renderInfoSupplier.get();
      this.collectSlotCountsSlotFillRatiosAndInfiniteSlots(renderInfo, inventoryHandler, slotCounts, slotFillRatios, infiniteSlots);
      renderInfo.refreshItemDisplayRenderInfo(displayItems, inaccessibleSlots, infiniteSlots, slotCounts, slotFillRatios);
   }

   private void collectSlotCountsSlotFillRatiosAndInfiniteSlots(
      RenderInfo renderInfo, InventoryHandler inventoryHandler, List<Integer> slotCounts, List<Float> slotFillRatios, List<Integer> infiniteSlots
   ) {
      if (renderInfo.showsCountsAndFillRatios()) {
         for (int slotIndex = 0; slotIndex < inventoryHandler.getSlots(); slotIndex++) {
            ItemStack stack = inventoryHandler.getStackInSlot(slotIndex);
            slotCounts.add(stack.getCount());
            slotFillRatios.add(calculateSlotFillRatio(stack, inventoryHandler, slotIndex));
            if (inventoryHandler.isInfinite(slotIndex)) {
               infiniteSlots.add(slotIndex);
            }
         }
      }
   }

   private void collectDisplayItemsAndInaccessibleSlots(
      List<RenderInfo.DisplayItem> displayItems, InventoryHandler inventoryHandler, List<Integer> inaccessibleSlots
   ) {
      for (int slotIndex : this.slotIndexes) {
         displayItems.add(
            new RenderInfo.DisplayItem(
               this.getSlotItemCopy(slotIndex).orElse(ItemStack.EMPTY), this.slotRotations.getOrDefault(slotIndex, 0), slotIndex, this.displaySide
            )
         );
         if (!inventoryHandler.isSlotAccessible(slotIndex)) {
            inaccessibleSlots.add(slotIndex);
         }
      }
   }

   private void updateDisplayItemsAndInaccessibleSlots() {
      List<RenderInfo.DisplayItem> displayItems = new ArrayList<>();
      List<Integer> inaccessibleSlots = new ArrayList<>();
      InventoryHandler inventoryHandler = this.inventoryHandlerSupplier.get();
      this.collectDisplayItemsAndInaccessibleSlots(displayItems, inventoryHandler, inaccessibleSlots);
      this.renderInfoSupplier.get().refreshDisplayItemsAndInaccessibleSlots(displayItems, inaccessibleSlots);
   }

   private void updateCountsFillRatiosAndInfiniteSlots() {
      List<Integer> slotCounts = new ArrayList<>();
      List<Float> slotFillRatios = new ArrayList<>();
      List<Integer> infiniteSlots = new ArrayList<>();
      RenderInfo renderInfo = this.renderInfoSupplier.get();
      this.collectSlotCountsSlotFillRatiosAndInfiniteSlots(renderInfo, this.inventoryHandlerSupplier.get(), slotCounts, slotFillRatios, infiniteSlots);
      renderInfo.refreshSlotCountsFillRatiosAndInfiniteSlots(slotCounts, slotFillRatios, infiniteSlots);
   }

   private static float calculateSlotFillRatio(ItemStack stack, InventoryHandler inventoryHandler, int slotIndex) {
      return stack.isEmpty() ? 0.0F : (float)stack.getCount() / inventoryHandler.getStackLimit(slotIndex, stack);
   }

   private Optional<ItemStack> getSlotItemCopy(int slotIndex) {
      ItemStack slotStack = this.inventoryHandlerSupplier.get().getStackInSlot(slotIndex);
      if (slotStack.isEmpty()) {
         Item filterItem = this.inventoryHandlerSupplier.get().getFilterItem(slotIndex);
         return filterItem != Items.AIR ? Optional.of(new ItemStack(filterItem)) : this.getMemorySettings.get().getSlotFilterStack(slotIndex, true);
      } else {
         ItemStack stackCopy = slotStack.copy();
         stackCopy.setCount(1);
         return Optional.of(stackCopy);
      }
   }

   public void selectSlot(int slotIndex) {
      if (this.slotIndexes.size() + 1 <= this.itemNumberLimit) {
         this.slotIndexes.add(slotIndex);
         this.serializeSlotIndexes();
         this.updateFullRenderInfo();
      }
   }

   private void serializeSlotIndexes() {
      this.categoryNbt.putIntArray("slots", this.slotIndexes);
      this.saveNbt.accept(this.categoryNbt);
   }

   public List<Integer> getSlots() {
      return this.slotIndexes;
   }

   public int getRotation(int slotIndex) {
      return this.slotRotations.getOrDefault(slotIndex, 0);
   }

   public void rotate(int slotIndex, boolean clockwise) {
      if (this.slotIndexes.contains(slotIndex)) {
         int rotation = this.getRotation(slotIndex);
         rotation = (rotation + (clockwise ? 1 : -1) * 45 + 360) % 360;
         this.slotRotations.put(slotIndex, rotation);
         this.serializeRotations();
         this.updateFullRenderInfo();
      }
   }

   private void serializeRotations() {
      NBTHelper.putMap(this.categoryNbt, "rotations", this.slotRotations, String::valueOf, IntTag::valueOf);
      this.saveNbt.accept(this.categoryNbt);
   }

   public void setColor(DyeColor color) {
      this.color = color;
      this.categoryNbt.putInt("color", color.getId());
      this.saveNbt.accept(this.categoryNbt);
   }

   public DyeColor getColor() {
      return this.color;
   }

   public DisplaySide getDisplaySide() {
      return this.displaySide;
   }

   public void setDisplaySide(DisplaySide displaySide) {
      this.displaySide = displaySide;
      this.categoryNbt.putString("displaySide", displaySide.getSerializedName());
      this.saveNbt.accept(this.categoryNbt);
      this.updateFullRenderInfo();
   }

   @Override
   public void reloadFrom(CompoundTag categoryNbt) {
      this.categoryNbt = categoryNbt;
      this.deserialize();
   }

   public void overwriteWith(ItemDisplaySettingsCategory otherCategory) {
      this.slotIndexes.clear();
      this.slotIndexes.addAll(otherCategory.getSlots());
      this.serializeSlotIndexes();
      this.slotRotations.clear();
      this.slotRotations.putAll(otherCategory.slotRotations);
      this.serializeRotations();
      this.setColor(otherCategory.getColor());
      this.itemsChanged();
   }

   private void deserialize() {
      this.slotIndexes.clear();
      NBTHelper.getIntArray(this.categoryNbt, "slots").ifPresent(slots -> {
         for (int slot : slots) {
            this.slotIndexes.add(slot);
         }
      });
      this.slotRotations = NBTHelper.<Integer, Integer>getMap(this.categoryNbt, "rotations", Integer::valueOf, (k, v) -> Optional.of(((IntTag)v).getAsInt()))
         .orElseGet(HashMap::new);
      this.color = NBTHelper.getInt(this.categoryNbt, "color").<DyeColor>map(DyeColor::byId).orElse(DyeColor.RED);
      NBTHelper.getInt(this.categoryNbt, "slot").ifPresent(e -> {
         this.slotIndexes.add(e);
         this.categoryNbt.remove("slot");
         this.serializeSlotIndexes();
      });
      NBTHelper.getInt(this.categoryNbt, "rotation").ifPresent(r -> {
         if (!this.slotIndexes.isEmpty()) {
            this.slotRotations.put(this.slotIndexes.iterator().next(), r);
         }

         this.categoryNbt.remove("rotation");
         this.serializeRotations();
      });
      NBTHelper.getEnumConstant(this.categoryNbt, "displaySide", DisplaySide::fromName).ifPresent(ds -> this.displaySide = ds);
   }

   public void itemChanged(int changedSlotIndex) {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && this.slotIndexes.contains(changedSlotIndex)) {
         if (this.haveRenderedItemsChanged()) {
            this.updateDisplayItemsAndInaccessibleSlots();
         }

         if (this.haveCountsOrFillRatiosChanged(this.inventoryHandlerSupplier.get())) {
            this.updateCountsFillRatiosAndInfiniteSlots();
         }
      }
   }

   public void itemsChanged() {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         if (this.haveRenderedItemsChanged()) {
            this.updateDisplayItemsAndInaccessibleSlots();
         }

         if (this.haveCountsOrFillRatiosChanged(this.inventoryHandlerSupplier.get())) {
            this.updateCountsFillRatiosAndInfiniteSlots();
         }
      }
   }

   @Override
   public Optional<Integer> getSlotColor(int slotNumber) {
      return this.slotIndexes.contains(slotNumber) ? Optional.of(this.color.getTextureDiffuseColor()) : Optional.empty();
   }

   public void selectSlots(int minSlot, int maxSlot) {
      for (int slotIndex = minSlot; slotIndex < maxSlot; slotIndex++) {
         if (this.slotIndexes.size() + 1 > this.itemNumberLimit) {
            return;
         }

         this.slotIndexes.add(slotIndex);
      }

      this.serializeSlotIndexes();
      this.updateFullRenderInfo();
   }

   @Override
   public boolean isLargerThanNumberOfSlots(int slots) {
      return this.slotIndexes.stream().anyMatch(slotIndex -> slotIndex >= slots);
   }

   public void copyTo(ItemDisplaySettingsCategory otherCategory, int startFromSlot, int slotOffset) {
   }

   @Override
   public void deleteSlotSettingsFrom(int slotIndex) {
      this.slotIndexes.removeIf(slot -> slot >= slotIndex);
      this.serializeSlotIndexes();
   }
}
