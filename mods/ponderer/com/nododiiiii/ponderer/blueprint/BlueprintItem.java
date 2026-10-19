package com.nododiiiii.ponderer.blueprint;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Blocks;

public class BlueprintItem extends Item {
   public BlueprintItem(Properties properties) {
      super(properties);
   }

   public static void replaceStructureVoidWithAir(CompoundTag nbt) {
      String air = Blocks.AIR.builtInRegistryHolder().key().location().toString();
      String structureVoid = Blocks.STRUCTURE_VOID.builtInRegistryHolder().key().location().toString();
      ListTag palette = nbt.getList("palette", 10);

      for (int i = 0; i < palette.size(); i++) {
         CompoundTag entry = palette.getCompound(i);
         if (entry.contains("Name") && entry.getString("Name").equals(structureVoid)) {
            entry.putString("Name", air);
         }
      }
   }

   private static ListTag newIntegerList(int... values) {
      ListTag list = new ListTag();

      for (int v : values) {
         list.add(IntTag.valueOf(v));
      }

      return list;
   }

   private static ListTag newDoubleList(double... values) {
      ListTag list = new ListTag();

      for (double v : values) {
         list.add(DoubleTag.valueOf(v));
      }

      return list;
   }
}
