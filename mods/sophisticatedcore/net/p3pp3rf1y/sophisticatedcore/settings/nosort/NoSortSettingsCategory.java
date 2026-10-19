package net.p3pp3rf1y.sophisticatedcore.settings.nosort;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.ISlotColorCategory;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class NoSortSettingsCategory implements ISettingsCategory<NoSortSettingsCategory>, ISlotColorCategory {
   public static final String NAME = "no_sort";
   private static final String COLOR_TAG = "color";
   private static final String SELECTED_SLOTS_TAG = "selectedSlots";
   private CompoundTag categoryNbt;
   private final Consumer<CompoundTag> saveNbt;
   private final Set<Integer> selectedSlots = new HashSet<>();
   private DyeColor color = DyeColor.LIME;

   public NoSortSettingsCategory(CompoundTag categoryNbt, Consumer<CompoundTag> saveNbt) {
      this.categoryNbt = categoryNbt;
      this.saveNbt = saveNbt;
      this.deserialize();
   }

   private void deserialize() {
      for (int slotNumber : this.categoryNbt.getIntArray("selectedSlots")) {
         this.selectedSlots.add(slotNumber);
      }

      NBTHelper.getInt(this.categoryNbt, "color").ifPresent(c -> this.color = DyeColor.byId(c));
   }

   public boolean isSlotSelected(int slotNumber) {
      return this.selectedSlots.contains(slotNumber);
   }

   public void unselectAllSlots() {
      this.selectedSlots.clear();
      this.serializeSelectedSlots();
   }

   public void selectSlots(int minSlot, int maxSlot) {
      for (int slot = minSlot; slot < maxSlot; slot++) {
         this.selectedSlots.add(slot);
      }

      this.serializeSelectedSlots();
   }

   public void selectSlot(int slotNumber) {
      this.selectSlots(slotNumber, slotNumber + 1);
   }

   public void unselectSlot(int slotNumber) {
      this.selectedSlots.remove(slotNumber);
      this.serializeSelectedSlots();
   }

   private void serializeSelectedSlots() {
      int[] slots = new int[this.selectedSlots.size()];
      int i = 0;

      for (int slotNumber : this.selectedSlots) {
         slots[i++] = slotNumber;
      }

      this.categoryNbt.putIntArray("selectedSlots", slots);
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

   @Override
   public Optional<Integer> getSlotColor(int slotNumber) {
      return this.selectedSlots.contains(slotNumber) ? Optional.of(this.color.getTextureDiffuseColor()) : Optional.empty();
   }

   public Set<Integer> getNoSortSlots() {
      return this.selectedSlots;
   }

   @Override
   public void reloadFrom(CompoundTag categoryNbt) {
      this.categoryNbt = categoryNbt;
      this.selectedSlots.clear();
      this.color = DyeColor.LIME;
      this.deserialize();
   }

   public void overwriteWith(NoSortSettingsCategory otherCategory) {
      this.selectedSlots.clear();
      this.selectedSlots.addAll(otherCategory.getNoSortSlots());
      this.serializeSelectedSlots();
      this.setColor(otherCategory.getColor());
   }

   @Override
   public boolean isLargerThanNumberOfSlots(int slots) {
      return this.selectedSlots.stream().anyMatch(slotIndex -> slotIndex >= slots);
   }

   public void copyTo(NoSortSettingsCategory otherCategory, int startFromSlot, int slotOffset) {
      this.selectedSlots.forEach(slotIndex -> {
         if (slotIndex >= startFromSlot) {
            otherCategory.selectedSlots.add(slotIndex + slotOffset);
         }
      });
      otherCategory.serializeSelectedSlots();
   }

   @Override
   public void deleteSlotSettingsFrom(int slotIndex) {
      this.selectedSlots.removeIf(slot -> slot >= slotIndex);
      this.serializeSelectedSlots();
   }
}
