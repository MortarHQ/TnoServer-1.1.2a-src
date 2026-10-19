package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.p3pp3rf1y.sophisticatedcore.util.FilterItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.ItemStackHelper;

public class FilterLogic {
   protected final ItemStack upgrade;
   protected final Consumer<ItemStack> saveHandler;
   protected final DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> filterAttributesComponent;
   private final int defaultFilterSlotCount;
   private final Predicate<ItemStack> isItemValid;
   @Nullable
   protected Set<TagKey<Item>> tagKeys = null;
   private FilterLogic.ObservableFilterItemStackHandler filterHandler = null;
   private boolean emptyAllowListMatchesEverything = false;
   private boolean allowListDefault = false;
   @Nullable
   private FilterAttributes emptyAttributes = null;

   public FilterLogic(
      ItemStack upgrade,
      Consumer<ItemStack> saveHandler,
      int defaultFilterSlotCount,
      DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> filterAttributesComponent
   ) {
      this(upgrade, saveHandler, defaultFilterSlotCount, s -> true, filterAttributesComponent);
   }

   public FilterLogic(
      ItemStack upgrade,
      Consumer<ItemStack> saveHandler,
      int defaultFilterSlotCount,
      Predicate<ItemStack> isItemValid,
      DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> filterAttributesComponent
   ) {
      this.upgrade = upgrade;
      this.saveHandler = saveHandler;
      this.filterAttributesComponent = filterAttributesComponent;
      this.defaultFilterSlotCount = defaultFilterSlotCount;
      this.isItemValid = isItemValid;
   }

   public void setEmptyAllowListMatchesEverything() {
      this.emptyAllowListMatchesEverything = true;
   }

   public FilterLogic.ObservableFilterItemStackHandler getFilterHandler() {
      if (this.filterHandler == null) {
         ItemContainerContents filterItemContents = this.getAttributes().filterItems();
         NonNullList<ItemStack> filterItems = NonNullList.withSize(filterItemContents.getSlots(), ItemStack.EMPTY);
         filterItemContents.copyInto(filterItems);
         int filterSlotCount = Math.max(filterItems.size(), this.defaultFilterSlotCount);
         this.filterHandler = new FilterLogic.ObservableFilterItemStackHandler(filterSlotCount);
         this.filterHandler.initFilters(filterItems);
      }

      return this.filterHandler;
   }

   public boolean matchesFilter(ItemStack stack) {
      if (this.isAllowList()) {
         return this.getPrimaryMatch() == PrimaryMatch.TAGS
            ? this.isTagMatch(stack)
            : this.getFilterHandler().hasOnlyEmptyFilters() && this.emptyAllowListMatchesEverything
               || InventoryHelper.iterate(
                  this.getFilterHandler(), (slot, filter) -> this.stackMatchesFilter(stack, filter), () -> false, returnValue -> returnValue
               );
      } else {
         return this.getPrimaryMatch() == PrimaryMatch.TAGS
            ? !this.isTagMatch(stack)
            : this.getFilterHandler().hasOnlyEmptyFilters()
               || InventoryHelper.iterate(
                  this.getFilterHandler(), (slot, filter) -> !this.stackMatchesFilter(stack, filter), () -> true, returnValue -> !returnValue
               );
      }
   }

   private boolean isTagMatch(ItemStack stack) {
      return this.shouldMatchAnyTag() ? this.anyTagMatches(stack.getTags()) : this.allTagsMatch(stack.getTags());
   }

   private boolean allTagsMatch(Stream<TagKey<Item>> tagsStream) {
      if (this.tagKeys == null) {
         this.initTags();
      }

      Set<TagKey<Item>> tags = tagsStream.collect(Collectors.toSet());

      for (TagKey<Item> tagName : this.tagKeys) {
         if (!tags.contains(tagName)) {
            return false;
         }
      }

      return true;
   }

   private boolean anyTagMatches(Stream<TagKey<Item>> tags) {
      if (this.tagKeys == null) {
         this.initTags();
      }

      return tags.anyMatch(t -> this.tagKeys.contains(t));
   }

   protected FilterAttributes getAttributes() {
      return (FilterAttributes)this.upgrade.getOrDefault(this.filterAttributesComponent, this.getEmptyAttributes());
   }

   private FilterAttributes getEmptyAttributes() {
      if (this.emptyAttributes == null) {
         this.emptyAttributes = new FilterAttributes(
            Collections.emptySet(), this.allowListDefault, false, false, PrimaryMatch.ITEM, true, ItemContainerContents.EMPTY, false, false
         );
      }

      return this.emptyAttributes;
   }

   protected void setAttributes(Function<FilterAttributes, FilterAttributes> setter) {
      this.upgrade.set(this.filterAttributesComponent, setter.apply(this.getAttributes()));
   }

   public void setAllowByDefault(boolean allowListDefault) {
      this.allowListDefault = allowListDefault;
   }

   protected void save() {
      this.saveHandler.accept(this.upgrade);
   }

   public boolean stackMatchesFilter(ItemStack stack, ItemStack filter) {
      if (filter.isEmpty()) {
         return false;
      }

      PrimaryMatch primaryMatch = this.getPrimaryMatch();
      if (primaryMatch == PrimaryMatch.MOD) {
         if (!BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals(BuiltInRegistries.ITEM.getKey(filter.getItem()).getNamespace())) {
            return false;
         }
      } else if (primaryMatch == PrimaryMatch.ITEM && stack.getItem() != filter.getItem()) {
         return false;
      }

      return this.shouldMatchDurability() && stack.getDamageValue() != filter.getDamageValue()
         ? false
         : !this.shouldMatchComponents() || ItemStackHelper.areItemStackComponentsEqualIgnoreDurability(stack, filter);
   }

   public Set<TagKey<Item>> getTagKeys() {
      if (this.tagKeys == null) {
         this.initTags();
      }

      return Collections.unmodifiableSet(this.tagKeys);
   }

   public void addTag(TagKey<Item> tagName) {
      if (this.tagKeys == null) {
         this.initTags();
      }

      this.tagKeys.add(tagName);
      this.serializeTags();
      this.save();
   }

   private void serializeTags() {
      if (this.tagKeys != null) {
         this.setAttributes(contents -> contents.setTagKeys(this.tagKeys));
      }
   }

   public void removeTagName(TagKey<Item> tagName) {
      if (this.tagKeys == null) {
         this.initTags();
      }

      this.tagKeys.remove(tagName);
      this.serializeTags();
      this.save();
   }

   protected void initTags() {
      this.tagKeys = new TreeSet<>(Comparator.comparing(TagKey::location));
      this.tagKeys.addAll(this.getAttributes().tagKeys());
   }

   public void setAllowList(boolean isAllowList) {
      this.setAttributes(contents -> contents.setAllowList(isAllowList));
      this.save();
   }

   public boolean isAllowList() {
      return this.getAttributes().isAllowList();
   }

   public boolean shouldMatchDurability() {
      return this.getAttributes().matchDurability();
   }

   public void setMatchDurability(boolean matchDurability) {
      this.setAttributes(contents -> contents.setMatchDurability(matchDurability));
      this.save();
   }

   public void setMatchComponents(boolean matchComponents) {
      this.setAttributes(contents -> contents.setMatchComponents(matchComponents));
      this.save();
   }

   public boolean shouldMatchComponents() {
      return this.getAttributes().matchComponents();
   }

   public void setPrimaryMatch(PrimaryMatch primaryMatch) {
      this.setAttributes(contents -> contents.setPrimaryMatch(primaryMatch));
      this.save();
   }

   public PrimaryMatch getPrimaryMatch() {
      return this.getAttributes().primaryMatch();
   }

   public boolean shouldMatchAnyTag() {
      return this.getAttributes().matchAnyTag();
   }

   public void setMatchAnyTag(boolean matchAnyTag) {
      this.setAttributes(contents -> contents.setMatchAnyTag(matchAnyTag));
      this.save();
   }

   public DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> getAttributesComponent() {
      return this.filterAttributesComponent;
   }

   public class ObservableFilterItemStackHandler extends FilterItemStackHandler {
      private IntConsumer onSlotChange = s -> {};

      public ObservableFilterItemStackHandler(int filterSlotCount) {
         super(filterSlotCount);
      }

      @Override
      protected void onContentsChanged(int slot) {
         super.onContentsChanged(slot);
         FilterLogic.this.setAttributes(contents -> contents.setFilterItem(slot, (ItemStack)this.stacks.get(slot)));
         FilterLogic.this.save();
         this.onSlotChange.accept(slot);
      }

      public void setOnSlotChange(IntConsumer onSlotChange) {
         this.onSlotChange = onSlotChange;
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return stack.isEmpty() || this.doesNotContain(stack) && FilterLogic.this.isItemValid.test(stack);
      }

      private boolean doesNotContain(ItemStack stack) {
         return !InventoryHelper.hasItem(this, s -> ItemStack.isSameItemSameComponents(s, stack));
      }

      public void initFilters(List<ItemStack> filterItems) {
         for (int slot = 0; slot < filterItems.size(); slot++) {
            this.setStackInSlot(slot, filterItems.get(slot).copy());
         }

         this.onLoad();
      }
   }
}
