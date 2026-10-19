package com.nododiiiii.ponderer.registry;

import com.nododiiiii.ponderer.blueprint.BlueprintItem;
import com.nododiiiii.ponderer.platform.PondererServices;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ModItems {
   public static final Supplier<Item> BLUEPRINT = PondererServices.REGISTRATION
      .registerItem("blueprint", () -> new BlueprintItem(new Properties().stacksTo(1)));

   public static void init() {
   }

   private ModItems() {
   }
}
