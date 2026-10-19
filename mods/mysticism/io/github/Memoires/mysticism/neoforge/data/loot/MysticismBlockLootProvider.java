package io.github.Memoires.mysticism.neoforge.data.loot;

import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MysticismBlockLootProvider extends BlockLootSubProvider {
   public MysticismBlockLootProvider(Provider lookup) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
   }

   protected void generate() {
      this.add((Block)MysticismBlocks.ICE_ORE.get(), block -> this.createOreDrop(block, (Item)MysticismMobDropItems.ICE_ESSENCE.get()));
   }

   @NotNull
   protected Iterable<Block> getKnownBlocks() {
      return BuiltInRegistries.BLOCK
         .stream()
         .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals("mysticism"))
         .collect(Collectors.toList());
   }
}
