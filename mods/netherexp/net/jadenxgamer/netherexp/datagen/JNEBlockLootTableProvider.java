package net.jadenxgamer.netherexp.datagen;

import java.util.Set;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class JNEBlockLootTableProvider extends BlockLootSubProvider {
   protected JNEBlockLootTableProvider(Provider registries) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
   }

   protected void generate() {
      this.add(JNEBlocks.DAMP_SILTMARRAM.get(), createShearsOnlyDrop((ItemLike)JNEBlocks.DAMP_SILTMARRAM.get()));
      this.add(JNEBlocks.MOIST_SILTMARRAM.get(), createShearsOnlyDrop((ItemLike)JNEBlocks.MOIST_SILTMARRAM.get()));
      this.add(JNEBlocks.DRY_SILTMARRAM.get(), createShearsOnlyDrop((ItemLike)JNEBlocks.DRY_SILTMARRAM.get()));
   }

   protected Iterable<Block> getKnownBlocks() {
      return Set.of(JNEBlocks.DAMP_SILTMARRAM.get(), JNEBlocks.MOIST_SILTMARRAM.get(), JNEBlocks.DRY_SILTMARRAM.get());
   }
}
