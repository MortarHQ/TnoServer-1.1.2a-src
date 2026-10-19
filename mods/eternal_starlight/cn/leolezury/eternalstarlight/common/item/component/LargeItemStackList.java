package cn.leolezury.eternalstarlight.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.AbstractList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public class LargeItemStackList extends AbstractList<LargeItemStackList.LargeItemStack> {
   private final List<LargeItemStackList.LargeItemStack> list;

   public LargeItemStackList(List<LargeItemStackList.LargeItemStack> list) {
      this.list = list;
   }

   public LargeItemStackList.LargeItemStack get(int index) {
      return this.list.get(index);
   }

   public LargeItemStackList.LargeItemStack set(int index, LargeItemStackList.LargeItemStack value) {
      return this.list.set(index, value);
   }

   public void add(int index, LargeItemStackList.LargeItemStack value) {
      this.list.add(index, value);
   }

   public LargeItemStackList.LargeItemStack remove(int index) {
      return this.list.remove(index);
   }

   @Override
   public int size() {
      return this.list.size();
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof LargeItemStackList stacks)) {
         return false;
      } else {
         if (stacks.size() != this.size()) {
            return false;
         }

         for (int i = 0; i < stacks.size(); i++) {
            if (!ItemStack.matches(stacks.get(i).getItem(), this.get(i).getItem()) || stacks.get(i).getCount() != this.get(i).getCount()) {
               return false;
            }
         }

         return true;
      }
   }

   public static class LargeItemStack {
      private final ItemStack item;
      private int count;
      public static final Codec<LargeItemStackList.LargeItemStack> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ItemStack.SINGLE_ITEM_CODEC.fieldOf("item").forGetter(LargeItemStackList.LargeItemStack::getItem),
               Codec.INT.fieldOf("count").forGetter(LargeItemStackList.LargeItemStack::getCount)
            )
            .apply(instance, LargeItemStackList.LargeItemStack::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, LargeItemStackList.LargeItemStack> STREAM_CODEC = StreamCodec.composite(
         ItemStack.STREAM_CODEC,
         LargeItemStackList.LargeItemStack::getItem,
         ByteBufCodecs.INT,
         LargeItemStackList.LargeItemStack::getCount,
         LargeItemStackList.LargeItemStack::new
      );

      public ItemStack getItem() {
         return this.item;
      }

      public int getCount() {
         return this.count;
      }

      public void setCount(int count) {
         this.count = count;
      }

      public LargeItemStack(ItemStack stack) {
         this(stack.copyWithCount(1), stack.getCount());
      }

      public LargeItemStack(ItemStack item, int count) {
         this.item = item;
         this.count = count;
      }

      public boolean isEmpty() {
         return this.item.isEmpty() || this.count == 0;
      }

      public void grow(int amount) {
         this.setCount(this.getCount() + amount);
      }

      public void shrink(int amount) {
         this.grow(-amount);
      }

      public ItemStack split(int splitAmount) {
         int splitCount = Math.min(splitAmount, this.count);
         ItemStack stack = this.item.copyWithCount(splitCount);
         this.count -= splitCount;
         return stack;
      }

      public ItemStack splitMaxStack() {
         return this.split(this.item.getMaxStackSize());
      }

      public ItemStack asItemStack() {
         return this.item.copyWithCount(this.count);
      }
   }
}
