package fuzs.illagerinvasion.data.loot;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider.Blocks;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.level.block.Block;

public class ModBlockLootProvider extends Blocks {
   public ModBlockLootProvider(DataProviderContext context) {
      super(context);
   }

   public void addLootTables() {
      this.dropSelf((Block)ModRegistry.IMBUING_TABLE_BLOCK.value());
      this.dropNothing((Block)ModRegistry.MAGIC_FIRE_BLOCK.value());
   }
}
