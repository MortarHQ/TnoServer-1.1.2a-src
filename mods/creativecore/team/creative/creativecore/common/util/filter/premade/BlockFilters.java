package team.creative.creativecore.common.util.filter.premade;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.ArrayUtils;
import team.creative.creativecore.common.util.CompoundSerializer;
import team.creative.creativecore.common.util.filter.Filter;

public class BlockFilters {
   public static Filter<Block> block(Block block) {
      return new BlockFilters.BlockFilter(block);
   }

   public static Filter<Block> blocks(Block... blocks) {
      return new BlockFilters.BlocksFilter(blocks);
   }

   public static Filter<Block> instance(Class<? extends Block> clazz) {
      return new BlockFilters.BlockClassFilter(clazz);
   }

   public static Filter<Block> and(Filter<Block>... filters) {
      return Filter.and(filters);
   }

   public static Filter<Block> or(Filter<Block>... filters) {
      return Filter.or(filters);
   }

   public static Filter<Block> not(Filter<Block> filter) {
      return Filter.not(filter);
   }

   public static Filter<Block> property(Property<?> property) {
      return new BlockFilters.BlockPropertyFilter(property);
   }

   public static Filter<Block> tag(TagKey<Block> tag) {
      return new BlockFilters.BlockTagFilter(tag);
   }

   static {
      Filter.SERIALIZER.register("block", BlockFilters.BlockFilter.class);
      Filter.SERIALIZER.register("blocks", BlockFilters.BlocksFilter.class);
      Filter.SERIALIZER.register("bclass", BlockFilters.BlockClassFilter.class);
   }

   private static class BlockClassFilter implements Filter<Block>, CompoundSerializer {
      public final Class<? extends Block> clazz;

      public BlockClassFilter(Class<? extends Block> clazz) {
         this.clazz = clazz;
      }

      public BlockClassFilter(CompoundTag nbt) {
         Class temp = null;

         try {
            temp = Class.forName(nbt.getString("c"));
         } catch (Exception var4) {
         }

         this.clazz = temp;
      }

      public boolean is(Block t) {
         return this.clazz != null && this.clazz.isInstance(t);
      }

      @Override
      public CompoundTag write() {
         CompoundTag nbt = new CompoundTag();
         nbt.putString("c", this.clazz.getName());
         return nbt;
      }
   }

   private static class BlockFilter implements Filter<Block>, CompoundSerializer {
      public final Block block;

      public BlockFilter(Block block) {
         this.block = block;
      }

      public BlockFilter(CompoundTag nbt) {
         this.block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(nbt.getString("b")));
      }

      public boolean is(Block t) {
         return t == this.block;
      }

      @Override
      public CompoundTag write() {
         CompoundTag nbt = new CompoundTag();
         nbt.putString("b", BuiltInRegistries.BLOCK.getKey(this.block).toString());
         return nbt;
      }
   }

   private record BlockPropertyFilter(Property<?> property) implements Filter<Block> {
      public boolean is(Block t) {
         return t.defaultBlockState().hasProperty(this.property);
      }
   }

   private record BlockTagFilter(TagKey<Block> tag) implements Filter<Block> {
      public boolean is(Block t) {
         return t.builtInRegistryHolder().is(this.tag);
      }
   }

   private static class BlocksFilter implements Filter<Block>, CompoundSerializer {
      public final Block[] blocks;

      public BlocksFilter(Block... blocks) {
         this.blocks = blocks;
      }

      public BlocksFilter(CompoundTag nbt) {
         ListTag list = nbt.getList("b", 8);
         this.blocks = new Block[list.size()];

         for (int i = 0; i < this.blocks.length; i++) {
            this.blocks[i] = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(list.getString(i)));
         }
      }

      public boolean is(Block t) {
         return ArrayUtils.contains(this.blocks, t);
      }

      @Override
      public CompoundTag write() {
         CompoundTag nbt = new CompoundTag();
         ListTag list = new ListTag();

         for (int i = 0; i < this.blocks.length; i++) {
            list.add(StringTag.valueOf(BuiltInRegistries.BLOCK.getKey(this.blocks[i]).toString()));
         }

         nbt.put("b", list);
         return nbt;
      }
   }
}
