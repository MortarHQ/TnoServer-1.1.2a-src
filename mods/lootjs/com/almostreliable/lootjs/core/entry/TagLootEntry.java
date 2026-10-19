package com.almostreliable.lootjs.core.entry;

import com.almostreliable.lootjs.util.DebugInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TagLootEntry extends AbstractSimpleLootEntry<TagEntry> implements SingleLootEntry {
   public TagLootEntry(TagEntry vanillaEntry) {
      super(vanillaEntry);
   }

   public TagLootEntry(TagKey<Item> tag, boolean expand) {
      super(new TagEntry(tag, expand, 1, 0, EMPTY_CONDITIONS, EMPTY_FUNCTIONS));
   }

   public boolean getExpand() {
      return this.vanillaEntry.expand;
   }

   public void setExpand(boolean expand) {
      this.vanillaEntry.expand = expand;
   }

   public String getTag() {
      return this.vanillaEntry.tag.location().toString();
   }

   public void setTag(String tag) {
      if (tag.startsWith("#")) {
         tag = tag.substring(1);
      }

      this.vanillaEntry.tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(tag));
   }

   public boolean isTag(String tag) {
      if (tag.startsWith("#")) {
         tag = tag.substring(1);
      }

      return tag.equals(this.getTag());
   }

   public TagLootEntry addCondition(LootItemCondition condition) {
      this.getConditions().add(condition);
      return this;
   }

   @Override
   public void collectDebugInfo(DebugInfo info) {
      info.add("% Tag: " + this.getTag());
      super.collectDebugInfo(info);
   }
}
