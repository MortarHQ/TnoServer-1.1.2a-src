package com.chronodawn.neoforge.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber(modid = "chronodawn")
public class FuelEventHandler {
   @SubscribeEvent
   public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
      ItemStack itemStack = event.getItemStack();
      if (isInTag(itemStack, "minecraft:logs")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:planks")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:wooden_stairs")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:wooden_slabs")) {
         event.setBurnTime(150);
      } else if (isInTag(itemStack, "minecraft:wooden_fences")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:fence_gates")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:wooden_buttons")) {
         event.setBurnTime(100);
      } else if (isInTag(itemStack, "minecraft:wooden_pressure_plates")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:wooden_doors")) {
         event.setBurnTime(200);
      } else if (isInTag(itemStack, "minecraft:wooden_trapdoors")) {
         event.setBurnTime(300);
      } else if (isInTag(itemStack, "minecraft:boats")) {
         event.setBurnTime(1200);
      } else if (isInTag(itemStack, "minecraft:chest_boats")) {
         event.setBurnTime(1200);
      } else {
         if (isInTag(itemStack, "minecraft:saplings")) {
            event.setBurnTime(100);
         }
      }
   }

   private static boolean isInTag(ItemStack itemStack, String tagId) {
      ResourceLocation tagLocation = ResourceLocation.parse(tagId);
      TagKey<Item> tag = TagKey.create(Registries.ITEM, tagLocation);
      return itemStack.is(tag);
   }
}
