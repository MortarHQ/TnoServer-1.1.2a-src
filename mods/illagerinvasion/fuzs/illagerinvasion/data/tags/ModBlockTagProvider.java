package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {
   public ModBlockTagProvider(DataProviderContext context) {
      super(Registries.BLOCK, context);
   }

   public void addTags(Provider provider) {
      this.add(BlockTags.MINEABLE_WITH_AXE).add((Block)ModRegistry.IMBUING_TABLE_BLOCK.value());
      this.add(BlockTags.FIRE).add((Block)ModRegistry.MAGIC_FIRE_BLOCK.value());
   }
}
