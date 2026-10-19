package com.finderfeed.fdlib.systems.config.test;

import com.finderfeed.fdlib.systems.config.ConfigValue;
import com.finderfeed.fdlib.systems.config.ManualSerializeable;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class TestConfignEntry implements ManualSerializeable {
   @ConfigValue
   public double testEntry3 = 23.54;
   public Item sausage = Items.ACACIA_BOAT;

   @Override
   public boolean process(JsonObject object) {
      if (object.has("sausage")) {
         try {
            String s = object.get("sausage").getAsString();
            this.sausage = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(s));
            return false;
         } catch (Exception e) {
            object.addProperty("sausage", BuiltInRegistries.ITEM.getKey(Items.ACACIA_BOAT).toString());
            this.sausage = Items.ACACIA_BOAT;
            return true;
         }
      } else {
         object.addProperty("sausage", BuiltInRegistries.ITEM.getKey(Items.ACACIA_BOAT).toString());
         this.sausage = Items.ACACIA_BOAT;
         return true;
      }
   }
}
