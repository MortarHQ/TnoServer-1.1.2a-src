package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IronChestsBlockTags extends BlockTagsProvider {
   public IronChestsBlockTags(PackOutput output, CompletableFuture<Provider> lookup, ExistingFileHelper existingFileHelper) {
      super(output, lookup, "ironchest", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(new Block[]{(Block)IronChestsBlocks.DIRT_CHEST.get(), (Block)IronChestsBlocks.TRAPPED_DIRT_CHEST.get()});
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
         .add(
            new Block[]{
               (Block)IronChestsBlocks.IRON_CHEST.get(),
               (Block)IronChestsBlocks.GOLD_CHEST.get(),
               (Block)IronChestsBlocks.DIAMOND_CHEST.get(),
               (Block)IronChestsBlocks.COPPER_CHEST.get(),
               (Block)IronChestsBlocks.CRYSTAL_CHEST.get(),
               (Block)IronChestsBlocks.OBSIDIAN_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_IRON_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_GOLD_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_COPPER_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get()
            }
         );
      this.tag(BlockTags.FEATURES_CANNOT_REPLACE)
         .add(
            new Block[]{
               (Block)IronChestsBlocks.IRON_CHEST.get(),
               (Block)IronChestsBlocks.GOLD_CHEST.get(),
               (Block)IronChestsBlocks.DIAMOND_CHEST.get(),
               (Block)IronChestsBlocks.COPPER_CHEST.get(),
               (Block)IronChestsBlocks.CRYSTAL_CHEST.get(),
               (Block)IronChestsBlocks.OBSIDIAN_CHEST.get()
            }
         );
      this.tag(BlockTags.GUARDED_BY_PIGLINS)
         .add(
            new Block[]{
               (Block)IronChestsBlocks.IRON_CHEST.get(),
               (Block)IronChestsBlocks.GOLD_CHEST.get(),
               (Block)IronChestsBlocks.DIAMOND_CHEST.get(),
               (Block)IronChestsBlocks.COPPER_CHEST.get(),
               (Block)IronChestsBlocks.CRYSTAL_CHEST.get(),
               (Block)IronChestsBlocks.OBSIDIAN_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_IRON_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_GOLD_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_COPPER_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(),
               (Block)IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get()
            }
         );
   }
}
