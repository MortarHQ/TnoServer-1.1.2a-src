package com.illusivesoulworks.polymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.client.PolymorphWidgetsImpl;
import com.illusivesoulworks.polymorph.common.capability.CrafterRecipeData;
import com.illusivesoulworks.polymorph.common.capability.FurnaceRecipeData;
import com.illusivesoulworks.polymorph.common.integration.PolymorphIntegrations;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CrafterBlockEntity;

public class PolymorphCommonMod {
   public static void init() {
      PolymorphIntegrations.init();
   }

   public static void setup() {
      PolymorphApi api = PolymorphApi.getInstance();
      api.registerBlockEntity(blockEntity -> {
         if (blockEntity instanceof AbstractFurnaceBlockEntity) {
            return new FurnaceRecipeData((AbstractFurnaceBlockEntity)blockEntity);
         } else {
            return blockEntity instanceof CrafterBlockEntity ? new CrafterRecipeData((CrafterBlockEntity)blockEntity) : null;
         }
      });
      api.registerMenu(menu -> {
         for (Slot inventorySlot : menu.slots) {
            Container inventory = inventorySlot.container;
            if (inventory instanceof BlockEntity) {
               return (BlockEntity)inventory;
            }
         }

         return null;
      });
      PolymorphIntegrations.setup();
   }

   public static void clientSetup() {
      PolymorphWidgetsImpl.setup();
      PolymorphIntegrations.clientSetup();
   }
}
