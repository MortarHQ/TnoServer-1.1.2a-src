package com.chronodawn.entities.boats;

import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModItems;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public enum ChronoDawnBoatType {
   TIME_WOOD(
      "time_wood", () -> (Block)ModBlocks.TIME_WOOD_PLANKS.get(), () -> (Item)ModItems.TIME_WOOD_BOAT.get(), () -> (Item)ModItems.TIME_WOOD_CHEST_BOAT.get()
   ),
   DARK_TIME_WOOD(
      "dark_time_wood",
      () -> (Block)ModBlocks.DARK_TIME_WOOD_PLANKS.get(),
      () -> (Item)ModItems.DARK_TIME_WOOD_BOAT.get(),
      () -> (Item)ModItems.DARK_TIME_WOOD_CHEST_BOAT.get()
   ),
   ANCIENT_TIME_WOOD(
      "ancient_time_wood",
      () -> (Block)ModBlocks.ANCIENT_TIME_WOOD_PLANKS.get(),
      () -> (Item)ModItems.ANCIENT_TIME_WOOD_BOAT.get(),
      () -> (Item)ModItems.ANCIENT_TIME_WOOD_CHEST_BOAT.get()
   );

   private final String name;
   private final Supplier<Block> planks;
   private final Supplier<Item> boatItem;
   private final Supplier<Item> chestBoatItem;

   ChronoDawnBoatType(String name, Supplier<Block> planks, Supplier<Item> boatItem, Supplier<Item> chestBoatItem) {
      this.name = name;
      this.planks = planks;
      this.boatItem = boatItem;
      this.chestBoatItem = chestBoatItem;
   }

   public String getName() {
      return this.name;
   }

   public Block getPlanks() {
      return this.planks.get();
   }

   public Item getBoatItem() {
      return this.boatItem.get();
   }

   public Item getChestBoatItem() {
      return this.chestBoatItem.get();
   }

   public static ChronoDawnBoatType byName(String name) {
      for (ChronoDawnBoatType type : values()) {
         if (type.name.equals(name)) {
            return type;
         }
      }

      return TIME_WOOD;
   }

   public static ChronoDawnBoatType byId(int id) {
      return id >= 0 && id < values().length ? values()[id] : TIME_WOOD;
   }
}
