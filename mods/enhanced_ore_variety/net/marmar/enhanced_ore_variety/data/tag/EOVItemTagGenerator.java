package net.marmar.enhanced_ore_variety.data.tag;

import java.util.concurrent.CompletableFuture;
import net.marmar.enhanced_ore_variety.block.EOVBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class EOVItemTagGenerator extends ItemTagsProvider {
   public EOVItemTagGenerator(
      PackOutput packOutput,
      CompletableFuture<Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> lookupProviderBlocks,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(packOutput, lookupProvider, lookupProviderBlocks, "enhanced_ore_variety", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ItemTags.COAL_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_COAL_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_COAL_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_COAL_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_COAL_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_COAL_ORE.get()).asItem()
            }
         );
      this.tag(ItemTags.COPPER_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_COPPER_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_COPPER_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_COPPER_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_COPPER_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_COPPER_ORE.get()).asItem()
            }
         );
      this.tag(ItemTags.IRON_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_IRON_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_IRON_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_IRON_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_IRON_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_IRON_ORE.get()).asItem()
            }
         );
      this.tag(ItemTags.GOLD_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_GOLD_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_GOLD_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_GOLD_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_GOLD_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_GOLD_ORE.get()).asItem()
            }
         );
      this.tag(EOVTags.Items.QUARTZ_ORES).add(new Item[]{Items.NETHER_QUARTZ_ORE, ((Block)EOVBlocks.BLACKSTONE_QUARTZ_ORE.get()).asItem()});
      this.tag(ItemTags.LAPIS_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_LAPIS_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_LAPIS_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_LAPIS_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_LAPIS_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_LAPIS_ORE.get()).asItem()
            }
         );
      this.tag(ItemTags.REDSTONE_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_REDSTONE_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_REDSTONE_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_REDSTONE_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_REDSTONE_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_REDSTONE_ORE.get()).asItem()
            }
         );
      this.tag(ItemTags.EMERALD_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_EMERALD_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_EMERALD_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_EMERALD_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_EMERALD_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_EMERALD_ORE.get()).asItem()
            }
         );
      this.tag(ItemTags.DIAMOND_ORES)
         .add(
            new Item[]{
               ((Block)EOVBlocks.DIORITE_DIAMOND_ORE.get()).asItem(),
               ((Block)EOVBlocks.ANDESITE_DIAMOND_ORE.get()).asItem(),
               ((Block)EOVBlocks.GRANITE_DIAMOND_ORE.get()).asItem(),
               ((Block)EOVBlocks.TUFF_DIAMOND_ORE.get()).asItem(),
               ((Block)EOVBlocks.BLACKSTONE_DIAMOND_ORE.get()).asItem()
            }
         );
   }
}
