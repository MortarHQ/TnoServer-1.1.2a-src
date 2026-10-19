package net.marmar.enhanced_ore_variety.data.tag;

import java.util.concurrent.CompletableFuture;
import net.marmar.enhanced_ore_variety.block.EOVBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class EOVBlockTagGenerator extends BlockTagsProvider {
   public EOVBlockTagGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "enhanced_ore_variety", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BlockTags.COAL_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_COAL_ORE.get(),
               (Block)EOVBlocks.ANDESITE_COAL_ORE.get(),
               (Block)EOVBlocks.GRANITE_COAL_ORE.get(),
               (Block)EOVBlocks.TUFF_COAL_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_COAL_ORE.get()
            }
         );
      this.tag(BlockTags.COPPER_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_COPPER_ORE.get(),
               (Block)EOVBlocks.ANDESITE_COPPER_ORE.get(),
               (Block)EOVBlocks.GRANITE_COPPER_ORE.get(),
               (Block)EOVBlocks.TUFF_COPPER_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_COPPER_ORE.get()
            }
         );
      this.tag(BlockTags.IRON_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_IRON_ORE.get(),
               (Block)EOVBlocks.ANDESITE_IRON_ORE.get(),
               (Block)EOVBlocks.GRANITE_IRON_ORE.get(),
               (Block)EOVBlocks.TUFF_IRON_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_IRON_ORE.get()
            }
         );
      this.tag(BlockTags.GOLD_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_GOLD_ORE.get(),
               (Block)EOVBlocks.ANDESITE_GOLD_ORE.get(),
               (Block)EOVBlocks.GRANITE_GOLD_ORE.get(),
               (Block)EOVBlocks.TUFF_GOLD_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_GOLD_ORE.get()
            }
         );
      this.tag(BlockTags.LAPIS_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.ANDESITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.GRANITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.TUFF_LAPIS_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_LAPIS_ORE.get()
            }
         );
      this.tag(BlockTags.REDSTONE_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.ANDESITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.GRANITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.TUFF_REDSTONE_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_REDSTONE_ORE.get()
            }
         );
      this.tag(BlockTags.EMERALD_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.ANDESITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.GRANITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.TUFF_EMERALD_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_EMERALD_ORE.get()
            }
         );
      this.tag(BlockTags.DIAMOND_ORES)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.ANDESITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.GRANITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.TUFF_DIAMOND_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_DIAMOND_ORE.get()
            }
         );
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_COAL_ORE.get(),
               (Block)EOVBlocks.DIORITE_COPPER_ORE.get(),
               (Block)EOVBlocks.DIORITE_IRON_ORE.get(),
               (Block)EOVBlocks.DIORITE_GOLD_ORE.get(),
               (Block)EOVBlocks.DIORITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.DIORITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.DIORITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.DIORITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.ANDESITE_COAL_ORE.get(),
               (Block)EOVBlocks.ANDESITE_COPPER_ORE.get(),
               (Block)EOVBlocks.ANDESITE_IRON_ORE.get(),
               (Block)EOVBlocks.ANDESITE_GOLD_ORE.get(),
               (Block)EOVBlocks.ANDESITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.ANDESITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.ANDESITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.ANDESITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.GRANITE_COAL_ORE.get(),
               (Block)EOVBlocks.GRANITE_COPPER_ORE.get(),
               (Block)EOVBlocks.GRANITE_IRON_ORE.get(),
               (Block)EOVBlocks.GRANITE_GOLD_ORE.get(),
               (Block)EOVBlocks.GRANITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.GRANITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.GRANITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.GRANITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.TUFF_COAL_ORE.get(),
               (Block)EOVBlocks.TUFF_COPPER_ORE.get(),
               (Block)EOVBlocks.TUFF_IRON_ORE.get(),
               (Block)EOVBlocks.TUFF_GOLD_ORE.get(),
               (Block)EOVBlocks.TUFF_LAPIS_ORE.get(),
               (Block)EOVBlocks.TUFF_REDSTONE_ORE.get(),
               (Block)EOVBlocks.TUFF_EMERALD_ORE.get(),
               (Block)EOVBlocks.TUFF_DIAMOND_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_COAL_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_COPPER_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_IRON_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_GOLD_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_LAPIS_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_EMERALD_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_QUARTZ_ORE.get()
            }
         );
      this.tag(Blocks.NEEDS_WOOD_TOOL)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_COAL_ORE.get(),
               (Block)EOVBlocks.ANDESITE_COAL_ORE.get(),
               (Block)EOVBlocks.GRANITE_COAL_ORE.get(),
               (Block)EOVBlocks.TUFF_COAL_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_COAL_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_QUARTZ_ORE.get()
            }
         );
      this.tag(BlockTags.NEEDS_STONE_TOOL)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_COPPER_ORE.get(),
               (Block)EOVBlocks.ANDESITE_COPPER_ORE.get(),
               (Block)EOVBlocks.GRANITE_COPPER_ORE.get(),
               (Block)EOVBlocks.TUFF_COPPER_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_COPPER_ORE.get(),
               (Block)EOVBlocks.DIORITE_IRON_ORE.get(),
               (Block)EOVBlocks.ANDESITE_IRON_ORE.get(),
               (Block)EOVBlocks.GRANITE_IRON_ORE.get(),
               (Block)EOVBlocks.TUFF_IRON_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_IRON_ORE.get(),
               (Block)EOVBlocks.DIORITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.ANDESITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.GRANITE_LAPIS_ORE.get(),
               (Block)EOVBlocks.TUFF_LAPIS_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_LAPIS_ORE.get()
            }
         );
      this.tag(BlockTags.NEEDS_IRON_TOOL)
         .add(
            new Block[]{
               (Block)EOVBlocks.DIORITE_GOLD_ORE.get(),
               (Block)EOVBlocks.ANDESITE_GOLD_ORE.get(),
               (Block)EOVBlocks.GRANITE_GOLD_ORE.get(),
               (Block)EOVBlocks.TUFF_GOLD_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_GOLD_ORE.get(),
               (Block)EOVBlocks.DIORITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.ANDESITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.GRANITE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.TUFF_REDSTONE_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_REDSTONE_ORE.get(),
               (Block)EOVBlocks.DIORITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.ANDESITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.GRANITE_EMERALD_ORE.get(),
               (Block)EOVBlocks.TUFF_EMERALD_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_EMERALD_ORE.get(),
               (Block)EOVBlocks.DIORITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.ANDESITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.GRANITE_DIAMOND_ORE.get(),
               (Block)EOVBlocks.TUFF_DIAMOND_ORE.get(),
               (Block)EOVBlocks.BLACKSTONE_DIAMOND_ORE.get()
            }
         );
   }
}
