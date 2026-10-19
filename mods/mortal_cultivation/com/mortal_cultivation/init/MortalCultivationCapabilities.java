package com.mortal_cultivation.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;

public final class MortalCultivationCapabilities {
   private MortalCultivationCapabilities() {
   }

   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(
         ItemHandler.BLOCK, (BlockEntityType)BlockEntityRegistry.ALCHEMY_FURNACE.get(), (blockEntity, side) -> blockEntity.getItemHandler()
      );
   }
}
