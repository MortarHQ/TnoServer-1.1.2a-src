package dev.ftb.mods.ftbquests.block.neoforge;

import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

public class LootCrateOpenerBlockImpl {
   public static BlockEntitySupplier<NeoForgeLootCrateOpenerBlockEntity> blockEntityProvider() {
      return NeoForgeLootCrateOpenerBlockEntity::new;
   }
}
