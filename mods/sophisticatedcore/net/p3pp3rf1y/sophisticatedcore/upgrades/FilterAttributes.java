package net.p3pp3rf1y.sophisticatedcore.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.concurrent.Immutable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.p3pp3rf1y.sophisticatedcore.util.CodecHelper;
import net.p3pp3rf1y.sophisticatedcore.util.StreamCodecHelper;

@Immutable
public record FilterAttributes(
   Set<TagKey<Item>> tagKeys,
   boolean isAllowList,
   boolean matchDurability,
   boolean matchComponents,
   PrimaryMatch primaryMatch,
   boolean matchAnyTag,
   ItemContainerContents filterItems,
   boolean filterByStorage,
   boolean filterByInventory
) {
   private static final Codec<List<ItemStack>> LEGACY_ITEMS_CODEC = Codec.list(ItemStack.OPTIONAL_CODEC);
   private static final Codec<ItemContainerContents> COMPATIBLE_FILTER_ITEMS_CODEC = Codec.withAlternative(
         ItemContainerContents.CODEC, LEGACY_ITEMS_CODEC.xmap(legacyList -> ItemContainerContents.fromItems(legacyList), container -> NonNullList.create())
      )
      .orElseGet(() -> ItemContainerContents.EMPTY);
   public static final Codec<FilterAttributes> CODEC = RecordCodecBuilder.create(
      builder -> builder.group(
            CodecHelper.setOf(TagKey.codec(Registries.ITEM)).optionalFieldOf("tag_keys", Collections.emptySet()).forGetter(FilterAttributes::tagKeys),
            Codec.BOOL.optionalFieldOf("is_allow_list", false).forGetter(FilterAttributes::isAllowList),
            Codec.BOOL.optionalFieldOf("match_durability", false).forGetter(FilterAttributes::matchDurability),
            Codec.BOOL.optionalFieldOf("match_components", false).forGetter(FilterAttributes::matchComponents),
            PrimaryMatch.CODEC.optionalFieldOf("primary_match", PrimaryMatch.ITEM).forGetter(FilterAttributes::primaryMatch),
            Codec.BOOL.optionalFieldOf("match_any_tag", false).forGetter(FilterAttributes::matchAnyTag),
            CodecHelper.LENIENT_ITEM_CONTAINER_CONTENTS_CODEC
               .optionalFieldOf("filter_items", ItemContainerContents.EMPTY)
               .forGetter(FilterAttributes::filterItems),
            Codec.BOOL.optionalFieldOf("filter_by_storage", false).forGetter(FilterAttributes::filterByStorage),
            Codec.BOOL.optionalFieldOf("filter_by_inventory", false).forGetter(FilterAttributes::filterByInventory)
         )
         .apply(builder, FilterAttributes::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, FilterAttributes> STREAM_CODEC = StreamCodecHelper.composite(
      StreamCodecHelper.ofCollection(StreamCodecHelper.ofTagkey(Registries.ITEM), HashSet::new),
      FilterAttributes::tagKeys,
      ByteBufCodecs.BOOL,
      FilterAttributes::isAllowList,
      ByteBufCodecs.BOOL,
      FilterAttributes::matchDurability,
      ByteBufCodecs.BOOL,
      FilterAttributes::matchComponents,
      PrimaryMatch.STREAM_CODEC,
      FilterAttributes::primaryMatch,
      ByteBufCodecs.BOOL,
      FilterAttributes::matchAnyTag,
      ItemContainerContents.STREAM_CODEC,
      FilterAttributes::filterItems,
      ByteBufCodecs.BOOL,
      FilterAttributes::filterByStorage,
      ByteBufCodecs.BOOL,
      FilterAttributes::filterByInventory,
      FilterAttributes::new
   );

   public FilterAttributes setTagKeys(Set<TagKey<Item>> tagKeys) {
      return new FilterAttributes.CopyBuilder(this).setTagKeys(tagKeys).build();
   }

   public FilterAttributes setAllowList(boolean isAllowList) {
      return new FilterAttributes.CopyBuilder(this).setAllowList(isAllowList).build();
   }

   public FilterAttributes setMatchDurability(boolean matchDurability) {
      return new FilterAttributes.CopyBuilder(this).setMatchDurability(matchDurability).build();
   }

   public FilterAttributes setMatchComponents(boolean matchComponents) {
      return new FilterAttributes.CopyBuilder(this).setMatchComponents(matchComponents).build();
   }

   public FilterAttributes setPrimaryMatch(PrimaryMatch primaryMatch) {
      return new FilterAttributes.CopyBuilder(this).setPrimaryMatch(primaryMatch).build();
   }

   public FilterAttributes setMatchAnyTag(boolean matchAnyTag) {
      return new FilterAttributes.CopyBuilder(this).setMatchAnyTag(matchAnyTag).build();
   }

   public FilterAttributes setFilterItem(int slot, ItemStack filterItem) {
      return new FilterAttributes.CopyBuilder(this).setFilterItem(slot, filterItem).build();
   }

   public FilterAttributes setFilterByStorage(boolean filterByStorage) {
      return new FilterAttributes.CopyBuilder(this).setFilterByStorage(filterByStorage).build();
   }

   public FilterAttributes setFilterByInventory(boolean filterByInventory) {
      return new FilterAttributes.CopyBuilder(this).setFilterByInventory(filterByInventory).build();
   }

   protected static class CopyBuilder {
      private Set<TagKey<Item>> tagKeys;
      private boolean isAllowList;
      private boolean matchDurability;
      private boolean matchComponents;
      private PrimaryMatch primaryMatch;
      private boolean matchAnyTag;
      private ItemContainerContents filterItems;
      private boolean filterByStorage;
      private boolean filterByInventory;

      public CopyBuilder(FilterAttributes original) {
         this.tagKeys = original.tagKeys();
         this.isAllowList = original.isAllowList();
         this.matchDurability = original.matchDurability();
         this.matchComponents = original.matchComponents();
         this.primaryMatch = original.primaryMatch();
         this.matchAnyTag = original.matchAnyTag();
         this.filterItems = original.filterItems();
         this.filterByStorage = original.filterByStorage();
         this.filterByInventory = original.filterByInventory();
      }

      public FilterAttributes.CopyBuilder setTagKeys(Set<TagKey<Item>> tagKeys) {
         this.tagKeys = tagKeys;
         return this;
      }

      public FilterAttributes build() {
         return new FilterAttributes(
            this.tagKeys,
            this.isAllowList,
            this.matchDurability,
            this.matchComponents,
            this.primaryMatch,
            this.matchAnyTag,
            this.filterItems,
            this.filterByStorage,
            this.filterByInventory
         );
      }

      public FilterAttributes.CopyBuilder setAllowList(boolean isAllowList) {
         this.isAllowList = isAllowList;
         return this;
      }

      public FilterAttributes.CopyBuilder setMatchDurability(boolean matchDurability) {
         this.matchDurability = matchDurability;
         return this;
      }

      public FilterAttributes.CopyBuilder setMatchComponents(boolean matchComponents) {
         this.matchComponents = matchComponents;
         return this;
      }

      public FilterAttributes.CopyBuilder setPrimaryMatch(PrimaryMatch primaryMatch) {
         this.primaryMatch = primaryMatch;
         return this;
      }

      public FilterAttributes.CopyBuilder setMatchAnyTag(boolean matchAnyTag) {
         this.matchAnyTag = matchAnyTag;
         return this;
      }

      public FilterAttributes.CopyBuilder setFilterItem(int slot, ItemStack filterItem) {
         NonNullList<ItemStack> items = NonNullList.withSize(Math.max(slot + 1, this.filterItems.getSlots()), ItemStack.EMPTY);
         this.filterItems.copyInto(items);
         items.set(slot, filterItem);
         this.filterItems = ItemContainerContents.fromItems(items);
         return this;
      }

      public FilterAttributes.CopyBuilder setFilterByStorage(boolean filterByStorage) {
         this.filterByStorage = filterByStorage;
         return this;
      }

      public FilterAttributes.CopyBuilder setFilterByInventory(boolean filterByInventory) {
         this.filterByInventory = filterByInventory;
         return this;
      }
   }
}
