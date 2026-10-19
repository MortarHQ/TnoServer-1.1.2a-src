package gardensofthedead.neoforge.datagen;

import gardensofthedead.block.StandingSignBlock;
import gardensofthedead.block.WallHangingSignBlock;
import gardensofthedead.block.WallSignBlock;
import gardensofthedead.registry.ModBlocks;
import gardensofthedead.registry.ModTags;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockTagsProvider extends net.neoforged.neoforge.common.data.BlockTagsProvider {
   protected static final List<Block> NON_FLAMMABLE_WOOD_ITEMS = List.of(
      (Block)ModBlocks.SOULBLIGHT_PLANKS.get(),
      (Block)ModBlocks.SOULBLIGHT_SLAB.get(),
      (Block)ModBlocks.SOULBLIGHT_STAIRS.get(),
      (Block)ModBlocks.SOULBLIGHT_FENCE.get(),
      (Block)ModBlocks.SOULBLIGHT_FENCE_GATE.get(),
      (Block)ModBlocks.SOULBLIGHT_BUTTON.get(),
      (Block)ModBlocks.SOULBLIGHT_PRESSURE_PLATE.get(),
      (Block)ModBlocks.SOULBLIGHT_DOOR.get(),
      (Block)ModBlocks.SOULBLIGHT_TRAPDOOR.get(),
      (Block)ModBlocks.SOULBLIGHT_SIGN.get(),
      (Block)ModBlocks.SOULBLIGHT_HANGING_SIGN.get(),
      (Block)ModBlocks.WHISTLECANE_PLANKS.get(),
      (Block)ModBlocks.WHISTLECANE_SLAB.get(),
      (Block)ModBlocks.WHISTLECANE_STAIRS.get(),
      (Block)ModBlocks.WHISTLECANE_FENCE.get(),
      (Block)ModBlocks.WHISTLECANE_FENCE_GATE.get(),
      (Block)ModBlocks.WHISTLECANE_BUTTON.get(),
      (Block)ModBlocks.WHISTLECANE_PRESSURE_PLATE.get(),
      (Block)ModBlocks.WHISTLECANE_DOOR.get(),
      (Block)ModBlocks.WHISTLECANE_TRAPDOOR.get(),
      (Block)ModBlocks.WHISTLECANE_SIGN.get(),
      (Block)ModBlocks.WHISTLECANE_HANGING_SIGN.get(),
      (Block)ModBlocks.WHISTLECANE_MOSAIC.get(),
      (Block)ModBlocks.WHISTLECANE_MOSAIC_SLAB.get(),
      (Block)ModBlocks.WHISTLECANE_MOSAIC_STAIRS.get()
   );

   public BlockTagsProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(packOutput, lookupProvider, "gardens_of_the_dead", existingFileHelper);
   }

   protected void addTags(Provider lookup) {
      BuiltInRegistries.BLOCK
         .stream()
         .filter(block -> Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getNamespace().equals("gardens_of_the_dead"))
         .forEach(block -> {
            switch (block) {
               case FlowerPotBlock flowerPotBlock:
                  this.tag(BlockTags.FLOWER_POTS).add(flowerPotBlock);
                  break;
               case StandingSignBlock standingSignBlock:
                  this.tag(BlockTags.STANDING_SIGNS).add(standingSignBlock);
                  break;
               case WallSignBlock wallSignBlock:
                  this.tag(BlockTags.WALL_SIGNS).add(wallSignBlock);
                  break;
               case CeilingHangingSignBlock ceilingHangingSignBlock:
                  this.tag(BlockTags.CEILING_HANGING_SIGNS).add(ceilingHangingSignBlock);
                  break;
               case WallHangingSignBlock wallHangingSignBlock:
                  this.tag(BlockTags.WALL_HANGING_SIGNS).add(wallHangingSignBlock);
                  break;
               case FenceGateBlock fenceGateBlock:
                  this.tag(BlockTags.FENCE_GATES).add(fenceGateBlock);
                  break;
               default:
            }
         });
      this.tag(BlockTags.MINEABLE_WITH_AXE)
         .addTag(ModTags.Blocks.SOULBLIGHT_STEMS)
         .addTag(ModTags.Blocks.WHISTLECANE_BLOCKS)
         .add((Block[])NON_FLAMMABLE_WOOD_ITEMS.toArray(new Block[0]));
      this.tag(BlockTags.MINEABLE_WITH_HOE).add((Block)ModBlocks.BLIGHTWART_BLOCK.get());
      this.tag(BlockTags.ENDERMAN_HOLDABLE)
         .add(new Block[]{(Block)ModBlocks.SOULBLIGHT_FUNGUS.get(), (Block)ModBlocks.SOULBLIGHT_SPROUTS.get(), (Block)ModBlocks.BLISTERCROWN.get()});
      this.tag(ModTags.Blocks.SOULBLIGHT_STEMS)
         .add(
            new Block[]{
               (Block)ModBlocks.SOULBLIGHT_STEM.get(),
               (Block)ModBlocks.STRIPPED_SOULBLIGHT_STEM.get(),
               (Block)ModBlocks.SOULBLIGHT_HYPHAE.get(),
               (Block)ModBlocks.STRIPPED_SOULBLIGHT_HYPHAE.get()
            }
         );
      this.tag(ModTags.Blocks.WHISTLECANE_BLOCKS).add((Block)ModBlocks.WHISTLECANE_BLOCK.get());
      this.tag(BlockTags.LOGS).addTag(ModTags.Blocks.SOULBLIGHT_STEMS);
      this.tag(BlockTags.WART_BLOCKS).add((Block)ModBlocks.BLIGHTWART_BLOCK.get());
      this.tag(BlockTags.WOODEN_FENCES).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_FENCE.get(), (Block)ModBlocks.WHISTLECANE_FENCE.get()});
      this.tag(BlockTags.WOODEN_BUTTONS).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_BUTTON.get(), (Block)ModBlocks.WHISTLECANE_BUTTON.get()});
      this.tag(BlockTags.PLANKS).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_PLANKS.get(), (Block)ModBlocks.WHISTLECANE_PLANKS.get()});
      this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
         .add(new Block[]{(Block)ModBlocks.SOULBLIGHT_PRESSURE_PLATE.get(), (Block)ModBlocks.WHISTLECANE_PRESSURE_PLATE.get()});
      this.tag(BlockTags.WOODEN_SLABS).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_SLAB.get(), (Block)ModBlocks.WHISTLECANE_SLAB.get()});
      this.tag(BlockTags.WOODEN_DOORS).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_DOOR.get(), (Block)ModBlocks.WHISTLECANE_DOOR.get()});
      this.tag(BlockTags.WOODEN_TRAPDOORS).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_TRAPDOOR.get(), (Block)ModBlocks.WHISTLECANE_TRAPDOOR.get()});
      this.tag(BlockTags.WOODEN_STAIRS).add(new Block[]{(Block)ModBlocks.SOULBLIGHT_STAIRS.get(), (Block)ModBlocks.WHISTLECANE_STAIRS.get()});
   }
}
