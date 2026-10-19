package com.tom.storagemod.util;

import com.tom.storagemod.Content;
import com.tom.storagemod.components.TagFilterComponent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AbstractTagFilter {
   protected ItemStack stack;
   protected boolean allowList;
   protected List<TagKey<Item>> tags;

   public AbstractTagFilter(ItemStack stack) {
      this.stack = stack;
      TagFilterComponent f = (TagFilterComponent)stack.get(Content.tagFilterComponent.get());
      if (f != null) {
         this.tags = new ArrayList<>(f.tags());
         this.allowList = f.allowList();
      } else {
         this.tags = new ArrayList<>();
      }
   }

   public boolean isAllowList() {
      return this.allowList;
   }

   public void setAllowList(boolean allowList) {
      this.allowList = allowList;
   }

   public void flush() {
      TagFilterComponent c = new TagFilterComponent(new ArrayList<>(this.tags), this.allowList);
      this.stack.applyComponents(DataComponentPatch.builder().set(Content.tagFilterComponent.get(), c).build());
   }

   public List<TagKey<Item>> getTags() {
      return this.tags;
   }

   public void setTags(List<ResourceLocation> tags) {
      this.tags = tags.stream().map(t -> TagKey.create(Registries.ITEM, t)).toList();
   }
}
