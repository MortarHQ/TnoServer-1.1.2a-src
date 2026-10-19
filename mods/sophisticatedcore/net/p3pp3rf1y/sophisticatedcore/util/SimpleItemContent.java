package net.p3pp3rf1y.sophisticatedcore.util;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SimpleItemContent implements DataComponentHolder {
   public static final SimpleItemContent EMPTY = new SimpleItemContent(ItemStack.EMPTY);
   public static final Codec<SimpleItemContent> CODEC = ItemStack.OPTIONAL_CODEC
      .orElse(ItemStack.EMPTY)
      .xmap(SimpleItemContent::new, content -> content.itemStack);
   public static final StreamCodec<RegistryFriendlyByteBuf, SimpleItemContent> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
      .map(SimpleItemContent::new, content -> content.itemStack);
   private final ItemStack itemStack;

   private SimpleItemContent(ItemStack itemStack) {
      this.itemStack = itemStack;
   }

   public static SimpleItemContent copyOf(ItemStack itemStack) {
      return itemStack.isEmpty() ? EMPTY : new SimpleItemContent(itemStack.copy());
   }

   public ItemStack copy() {
      return this.itemStack.copy();
   }

   public boolean isEmpty() {
      return this.itemStack.isEmpty();
   }

   public Item getItem() {
      return this.itemStack.getItem();
   }

   public boolean is(TagKey<Item> tag) {
      return this.itemStack.is(tag);
   }

   public boolean is(Item item) {
      return this.itemStack.is(item);
   }

   public boolean is(Predicate<Holder<Item>> predicate) {
      return this.itemStack.is(predicate);
   }

   public boolean is(Holder<Item> holder) {
      return this.itemStack.is(holder);
   }

   public boolean is(HolderSet<Item> holders) {
      return this.itemStack.is(holders);
   }

   public int getCount() {
      return this.itemStack.getCount();
   }

   public boolean matches(ItemStack other) {
      return ItemStack.isSameItemSameComponents(this.itemStack, other);
   }

   public boolean isSameItem(ItemStack other) {
      return ItemStack.isSameItem(this.itemStack, other);
   }

   public boolean isSameItemSameComponents(ItemStack other) {
      return ItemStack.isSameItemSameComponents(this.itemStack, other);
   }

   public boolean isSameItemSameComponents(SimpleItemContent content) {
      return this.isSameItemSameComponents(content.itemStack);
   }

   public DataComponentMap getComponents() {
      return this.itemStack.getComponents();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof SimpleItemContent o ? ItemStack.isSameItemSameComponents(this.itemStack, o.itemStack) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.itemStack.getCount() * 31 + ItemStack.hashItemAndComponents(this.itemStack);
   }
}
