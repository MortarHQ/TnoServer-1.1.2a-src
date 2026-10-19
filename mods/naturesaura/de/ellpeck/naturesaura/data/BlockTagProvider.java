package de.ellpeck.naturesaura.data;

import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.reg.IAxeBreakable;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BlockTagProvider extends BlockTagsProvider {
   public static final TagKey<Block> ALTAR_WOOD = BlockTags.create(ResourceLocation.fromNamespaceAndPath("naturesaura", "altar_wood"));
   public static final TagKey<Block> ALTAR_STONE = BlockTags.create(ResourceLocation.fromNamespaceAndPath("naturesaura", "altar_stone"));
   public static final TagKey<Block> ALTAR_GOLD_BRICK = BlockTags.create(ResourceLocation.fromNamespaceAndPath("naturesaura", "altar_gold_brick"));
   public static final TagKey<Block> ALTAR_FANCY_BRICK = BlockTags.create(ResourceLocation.fromNamespaceAndPath("naturesaura", "altar_fancy_brick"));

   public BlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "naturesaura", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BlockTags.LOGS).add(new Block[]{ModBlocks.ANCIENT_LOG, ModBlocks.ANCIENT_BARK});
      this.tag(BlockTags.PLANKS).add(ModBlocks.ANCIENT_PLANKS);
      this.tag(BlockTags.STAIRS).add(new Block[]{ModBlocks.ANCIENT_STAIRS, ModBlocks.INFUSED_BRICK_STAIRS, ModBlocks.INFUSED_STAIRS});
      this.tag(BlockTags.LEAVES).add(new Block[]{ModBlocks.GOLDEN_LEAVES, ModBlocks.ANCIENT_LEAVES, ModBlocks.DECAYED_LEAVES});
      this.tag(BlockTags.RAILS).add(new Block[]{ModBlocks.DIMENSION_RAIL_END, ModBlocks.DIMENSION_RAIL_NETHER, ModBlocks.DIMENSION_RAIL_OVERWORLD});
      this.tag(BlockTags.SLABS).add(new Block[]{ModBlocks.ANCIENT_SLAB, ModBlocks.INFUSED_SLAB, ModBlocks.INFUSED_BRICK_SLAB});
      this.tag(BlockTags.DIRT).add(ModBlocks.NETHER_GRASS);
      this.tag(BlockTags.SMALL_FLOWERS).add(new Block[]{ModBlocks.END_FLOWER, ModBlocks.AURA_BLOOM});
      this.tag(ALTAR_WOOD).addTag(BlockTags.PLANKS).add(new Block[]{Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS});
      this.tag(ALTAR_STONE).addTag(BlockTags.STONE_BRICKS).add(Blocks.NETHER_BRICKS);
      this.tag(ALTAR_GOLD_BRICK).add(new Block[]{ModBlocks.GOLD_BRICK, ModBlocks.GOLD_NETHER_BRICK});
      this.tag(ALTAR_FANCY_BRICK).add(new Block[]{Blocks.RED_NETHER_BRICKS, Blocks.CHISELED_STONE_BRICKS});
      this.tag(net.neoforged.neoforge.common.Tags.Blocks.STORAGE_BLOCKS)
         .add(new Block[]{ModBlocks.DEPTH_INGOT_BLOCK, ModBlocks.SKY_INGOT_BLOCK, ModBlocks.INFUSED_IRON_BLOCK});
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
         .add(ModData.getAllModItems().filter(i -> i instanceof IPickaxeBreakable).map(i -> (Block)i).toArray(Block[]::new));
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
         .add(
            new Block[]{
               ModBlocks.CONVERSION_CATALYST,
               ModBlocks.CRUSHING_CATALYST,
               ModBlocks.DEPTH_INGOT_BLOCK,
               ModBlocks.GOLD_BRICK,
               ModBlocks.GOLD_NETHER_BRICK,
               ModBlocks.INFUSED_STONE,
               ModBlocks.INFUSED_BRICK,
               ModBlocks.INFUSED_BRICK_SLAB,
               ModBlocks.INFUSED_BRICK_STAIRS,
               ModBlocks.INFUSED_IRON_BLOCK,
               ModBlocks.INFUSED_SLAB,
               ModBlocks.INFUSED_STAIRS,
               ModBlocks.SKY_INGOT_BLOCK,
               ModBlocks.TAINTED_GOLD_BLOCK
            }
         );
      this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModData.getAllModItems().filter(i -> i instanceof IAxeBreakable).map(i -> (Block)i).toArray(Block[]::new));
      this.tag(BlockTags.MINEABLE_WITH_AXE)
         .add(new Block[]{ModBlocks.ANCIENT_PLANKS, ModBlocks.ANCIENT_SLAB, ModBlocks.ANCIENT_STAIRS, ModBlocks.NETHER_WART_MUSHROOM});
   }
}
