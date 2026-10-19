package team.lodestar.lodestone.systems.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import team.lodestar.lodestone.systems.datagen.LodestoneDatagenBlockData;

public abstract class LodestoneItemTagsProvider extends ItemTagsProvider {
   public LodestoneItemTagsProvider(
      PackOutput pOutput,
      CompletableFuture<Provider> pLookupProvider,
      CompletableFuture<TagLookup<Block>> pBlockTags,
      String modId,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
   }

   public IntrinsicTagAppender<Item> tag(TagKey<Item> pTag) {
      return super.tag(pTag);
   }

   public void safeCopy(DeferredRegister<Block> blocks, TagKey<Item> itemTag) {
      this.safeCopy(blocks, TagKey.create(Registries.BLOCK, itemTag.location()), itemTag);
   }

   public void safeCopy(DeferredRegister<Block> blocks, TagKey<Block> blockTag, TagKey<Item> itemTag) {
      for (DeferredHolder<Block, ? extends Block> holder : blocks.getEntries()) {
         Block block = (Block)holder.get();
         if (block.properties instanceof LodestoneBlockProperties lodestoneBlockProperties) {
            LodestoneDatagenBlockData datagenData = lodestoneBlockProperties.getDatagenData();
            if (datagenData.getTags().contains(blockTag)) {
               Item item = block.asItem();
               if (!item.equals(Items.AIR)) {
                  this.tag(itemTag).add(item);
               }
            }
         }
      }
   }
}
