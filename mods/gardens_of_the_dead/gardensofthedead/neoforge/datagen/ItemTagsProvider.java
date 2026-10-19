package gardensofthedead.neoforge.datagen;

import gardensofthedead.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {
   public ItemTagsProvider(
      PackOutput packOutput,
      CompletableFuture<Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> blockTags,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(packOutput, lookupProvider, blockTags, "gardens_of_the_dead", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.copy(ModTags.Blocks.SOULBLIGHT_STEMS, ModTags.Items.SOULBLIGHT_STEMS);
      this.copy(ModTags.Blocks.WHISTLECANE_BLOCKS, ModTags.Items.WHISTLECANE_BLOCKS);
      this.copy(BlockTags.LOGS, ItemTags.LOGS);
      this.copy(BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);
      this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
      this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
      this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
      this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
      this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
      this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
      this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
      this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
      this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
      this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
      this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
      this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(BlockTagsProvider.NON_FLAMMABLE_WOOD_ITEMS.stream().map(Block::asItem).toArray(Item[]::new));
   }
}
