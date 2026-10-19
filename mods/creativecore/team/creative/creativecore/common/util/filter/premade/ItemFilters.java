package team.creative.creativecore.common.util.filter.premade;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.ArrayUtils;
import team.creative.creativecore.common.util.CompoundSerializer;
import team.creative.creativecore.common.util.filter.Filter;

public class ItemFilters {
   public static Filter<Item> item(Item item) {
      return new ItemFilters.ItemFilter(item);
   }

   public static Filter<Item> items(Item... items) {
      return new ItemFilters.ItemsFilter(items);
   }

   public static Filter<Item> instance(Class<? extends Item> clazz) {
      return new ItemFilters.ItemClassFilter(clazz);
   }

   public static Filter<Item> and(Filter<Item>... filters) {
      return Filter.and(filters);
   }

   public static Filter<Item> or(Filter<Item>... filters) {
      return Filter.or(filters);
   }

   public static Filter<Item> not(Filter<Item> filter) {
      return Filter.not(filter);
   }

   static {
      Filter.SERIALIZER.register("item", ItemFilters.ItemFilter.class);
      Filter.SERIALIZER.register("items", ItemFilters.ItemsFilter.class);
      Filter.SERIALIZER.register("iclass", ItemFilters.ItemClassFilter.class);
   }

   private static class ItemClassFilter implements Filter<Item>, CompoundSerializer {
      public final Class<? extends Item> clazz;

      public ItemClassFilter(Class<? extends Item> clazz) {
         this.clazz = clazz;
      }

      public ItemClassFilter(CompoundTag nbt) {
         Class temp = null;

         try {
            temp = Class.forName(nbt.getString("c"));
         } catch (Exception var4) {
         }

         this.clazz = temp;
      }

      public boolean is(Item t) {
         return this.clazz != null && this.clazz.isInstance(t);
      }

      @Override
      public CompoundTag write() {
         CompoundTag nbt = new CompoundTag();
         nbt.putString("c", this.clazz.getName());
         return nbt;
      }
   }

   private static class ItemFilter implements Filter<Item>, CompoundSerializer {
      public final Item item;

      public ItemFilter(Item item) {
         this.item = item;
      }

      public ItemFilter(CompoundTag nbt) {
         this.item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(nbt.getString("i")));
      }

      public boolean is(Item t) {
         return t == this.item;
      }

      @Override
      public CompoundTag write() {
         CompoundTag nbt = new CompoundTag();
         nbt.putString("i", BuiltInRegistries.ITEM.getKey(this.item).toString());
         return nbt;
      }
   }

   private static class ItemsFilter implements Filter<Item>, CompoundSerializer {
      public final Item[] items;

      public ItemsFilter(Item... items) {
         this.items = items;
      }

      public ItemsFilter(CompoundTag nbt) {
         ListTag list = nbt.getList("i", 8);
         this.items = new Item[list.size()];

         for (int i = 0; i < this.items.length; i++) {
            this.items[i] = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(list.getString(i)));
         }
      }

      public boolean is(Item t) {
         return ArrayUtils.contains(this.items, t);
      }

      @Override
      public CompoundTag write() {
         CompoundTag nbt = new CompoundTag();
         ListTag list = new ListTag();

         for (int i = 0; i < this.items.length; i++) {
            list.add(StringTag.valueOf(BuiltInRegistries.ITEM.getKey(this.items[i]).toString()));
         }

         nbt.put("i", list);
         return nbt;
      }
   }
}
