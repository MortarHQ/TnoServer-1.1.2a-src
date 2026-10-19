package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IFilterSlot;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IServerUpdater;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class FilterLogicContainerBase<T extends FilterLogic, S extends Slot> {
   private static final String DATA_IS_ALLOW_LIST = "isAllowList";
   private static final String DATA_MATCH_DURABILITY = "matchDurability";
   private static final String DATA_MATCH_NBT = "matchNbt";
   private static final String DATA_PRIMARY_MATCH = "primaryMatch";
   private static final String DATA_ADD_TAG_NAME = "addTagName";
   private static final String DATA_REMOVE_TAG_NAME = "removeTagName";
   private static final String DATA_MATCH_ANY_TAG = "matchAnyTag";
   private static final String DATA_COMPONENT_KEY = "parentTagKey";
   protected final List<S> filterSlots = new ArrayList<>();
   protected final IServerUpdater serverUpdater;
   protected final Supplier<T> filterLogic;
   private final FilterLogicContainerBase<T, S>.TagSelectionSlot tagSelectionSlot;
   private int selectedTagToAdd = 0;
   private int selectedTagToRemove = 0;
   private final Set<TagKey<Item>> tagsToAdd = new TreeSet<>(Comparator.comparing(TagKey::location));

   public FilterLogicContainerBase(IServerUpdater serverUpdater, Supplier<T> filterLogic, Consumer<Slot> addSlot) {
      this.serverUpdater = serverUpdater;
      this.filterLogic = filterLogic;
      this.tagSelectionSlot = new FilterLogicContainerBase.TagSelectionSlot();
      addSlot.accept(this.tagSelectionSlot);
   }

   public int getSelectedTagToAdd() {
      return this.selectedTagToAdd;
   }

   public int getSelectedTagToRemove() {
      return this.selectedTagToRemove;
   }

   public FilterLogicContainerBase<T, S>.TagSelectionSlot getTagSelectionSlot() {
      return this.tagSelectionSlot;
   }

   public List<S> getFilterSlots() {
      return this.filterSlots;
   }

   public Set<TagKey<Item>> getTagNames() {
      return this.filterLogic.get().getTagKeys();
   }

   public Set<TagKey<Item>> getTagsToAdd() {
      return this.tagsToAdd;
   }

   public void addSelectedTag() {
      this.getTagAtIndex(this.tagsToAdd, this.selectedTagToAdd).ifPresent(tagName -> {
         this.addTagName((TagKey<Item>)tagName);
         this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "addTagName", tagName.location().toString()));
         this.selectedTagToRemove = 0;
         this.tagsToAdd.remove(tagName);
         this.selectedTagToAdd = Math.max(0, this.selectedTagToAdd - 1);
      });
   }

   private void addTagName(TagKey<Item> tagName) {
      this.filterLogic.get().addTag(tagName);
   }

   public void removeSelectedTag() {
      this.getTagAtIndex(this.getTagNames(), this.selectedTagToRemove).ifPresent(tagName -> {
         this.removeSelectedTag((TagKey<Item>)tagName);
         this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "removeTagName", tagName.location().toString()));
         if (this.tagSelectionSlot.getItem().is(tagName)) {
            this.tagsToAdd.add((TagKey<Item>)tagName);
         }

         this.selectedTagToRemove = Math.max(0, this.selectedTagToRemove - 1);
      });
   }

   private void removeSelectedTag(TagKey<Item> tagName) {
      this.filterLogic.get().removeTagName(tagName);
   }

   public void selectNextTagToRemove() {
      this.selectedTagToRemove = this.getNextIndex(this.getTagNames().size(), this.selectedTagToRemove);
   }

   private int getNextIndex(int colSize, int selectedIndex) {
      return selectedIndex + 1 >= colSize ? 0 : selectedIndex + 1;
   }

   private int getPreviousIndex(int colSize, int selectedIndex) {
      return selectedIndex == 0 ? colSize - 1 : selectedIndex - 1;
   }

   public void selectPreviousTagToRemove() {
      this.selectedTagToRemove = this.getPreviousIndex(this.getTagNames().size(), this.selectedTagToRemove);
   }

   public void selectNextTagToAdd() {
      this.selectedTagToAdd = this.getNextIndex(this.tagsToAdd.size(), this.selectedTagToAdd);
   }

   public void selectPreviousTagToAdd() {
      this.selectedTagToAdd = this.getPreviousIndex(this.tagsToAdd.size(), this.selectedTagToAdd);
   }

   private Optional<TagKey<Item>> getTagAtIndex(Set<TagKey<Item>> col, int index) {
      int curIndex = 0;

      for (TagKey<Item> tagName : col) {
         if (curIndex == index) {
            return Optional.of(tagName);
         }

         curIndex++;
      }

      return Optional.empty();
   }

   public boolean isAllowList() {
      return this.filterLogic.get().isAllowList();
   }

   public boolean shouldMatchDurability() {
      return this.filterLogic.get().shouldMatchDurability();
   }

   public boolean shouldMatchNbt() {
      return this.filterLogic.get().shouldMatchComponents();
   }

   public PrimaryMatch getPrimaryMatch() {
      return this.filterLogic.get().getPrimaryMatch();
   }

   public boolean shouldMatchAnyTag() {
      return this.filterLogic.get().shouldMatchAnyTag();
   }

   public void setAllowList(boolean isAllowList) {
      this.filterLogic.get().setAllowList(isAllowList);
      this.sendBooleanToServer("isAllowList", isAllowList);
   }

   private void sendBooleanToServer(String dataId, boolean value) {
      this.serverUpdater.sendDataToServer(() -> {
         CompoundTag tag = new CompoundTag();
         tag.putBoolean(dataId, value);
         tag.putString("parentTagKey", this.filterLogic.get().getAttributesComponent().getKey().location().toString());
         return tag;
      });
   }

   protected void sendDataToServer(Supplier<CompoundTag> dataSupplier) {
      this.serverUpdater.sendDataToServer(() -> {
         CompoundTag tag = dataSupplier.get();
         tag.putString("parentTagKey", this.filterLogic.get().getAttributesComponent().getKey().location().toString());
         return tag;
      });
   }

   public void setMatchDurability(boolean matchDurability) {
      this.filterLogic.get().setMatchDurability(matchDurability);
      this.sendBooleanToServer("matchDurability", matchDurability);
   }

   public void setMatchNbt(boolean matchNbt) {
      this.filterLogic.get().setMatchComponents(matchNbt);
      this.sendBooleanToServer("matchNbt", matchNbt);
   }

   public void setPrimaryMatch(PrimaryMatch primaryMatch) {
      this.filterLogic.get().setPrimaryMatch(primaryMatch);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "primaryMatch", primaryMatch));
   }

   public void setMatchAnyTag(boolean matchAnyTag) {
      this.filterLogic.get().setMatchAnyTag(matchAnyTag);
      this.sendBooleanToServer("matchAnyTag", matchAnyTag);
   }

   public boolean handlePacket(CompoundTag data) {
      if (this.isDifferentFilterLogicsData(data)) {
         return false;
      }

      for (String key : data.getAllKeys()) {
         switch (key) {
            case "isAllowList":
               this.setAllowList(data.getBoolean("isAllowList"));
               return true;
            case "matchDurability":
               this.setMatchDurability(data.getBoolean("matchDurability"));
               return true;
            case "matchNbt":
               this.setMatchNbt(data.getBoolean("matchNbt"));
               return true;
            case "primaryMatch":
               this.setPrimaryMatch(PrimaryMatch.fromName(data.getString("primaryMatch")));
               return true;
            case "addTagName":
               this.addTagName(TagKey.create(Registries.ITEM, ResourceLocation.parse(data.getString("addTagName"))));
               return true;
            case "removeTagName":
               this.removeSelectedTag(TagKey.create(Registries.ITEM, ResourceLocation.parse(data.getString("removeTagName"))));
               return true;
            case "matchAnyTag":
               this.setMatchAnyTag(data.getBoolean("matchAnyTag"));
               return true;
         }
      }

      return false;
   }

   protected boolean isDifferentFilterLogicsData(CompoundTag data) {
      return data.contains("parentTagKey")
         && !this.filterLogic.get().getAttributesComponent().getKey().location().toString().equals(data.getString("parentTagKey"));
   }

   public class TagSelectionSlot extends Slot implements IFilterSlot {
      private ItemStack stack = ItemStack.EMPTY;
      private Runnable onUpdate = () -> {};

      public TagSelectionSlot() {
         super(new SimpleContainer(0), 0, -1, -1);
      }

      public void setOnUpdate(Runnable onUpdate) {
         this.onUpdate = onUpdate;
      }

      public boolean mayPlace(ItemStack stack) {
         return stack.isEmpty() || stack.getTags().findAny().isPresent();
      }

      public boolean mayPickup(Player player) {
         return false;
      }

      public ItemStack getItem() {
         return this.stack;
      }

      public int getMaxStackSize() {
         return 1;
      }

      public ItemStack remove(int amount) {
         this.stack = ItemStack.EMPTY;
         return this.stack;
      }

      public boolean isSameInventory(Slot other) {
         return false;
      }

      public void set(ItemStack stack) {
         this.stack = stack;
         FilterLogicContainerBase.this.tagsToAdd.clear();
         FilterLogicContainerBase.this.tagsToAdd.addAll(stack.getTags().toList());
         FilterLogicContainerBase.this.getTagNames().forEach(FilterLogicContainerBase.this.tagsToAdd::remove);
         FilterLogicContainerBase.this.selectedTagToAdd = 0;
         this.onUpdate.run();
      }

      public void setChanged() {
      }
   }
}
