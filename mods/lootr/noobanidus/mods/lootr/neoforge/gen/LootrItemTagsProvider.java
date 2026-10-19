package noobanidus.mods.lootr.neoforge.gen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.Nullable;

public class LootrItemTagsProvider extends ItemTagsProvider {
   public LootrItemTagsProvider(
      PackOutput output, CompletableFuture<Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(output, lookup, blockTags, "lootr", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(LootrTags.Items.BARRELS).add(LootrRegistry.getBarrelItem());
      this.tag(LootrTags.Items.CHESTS).add(new Item[]{LootrRegistry.getChestItem(), LootrRegistry.getTrappedChestItem(), LootrRegistry.getInventoryItem()});
      this.tag(LootrTags.Items.TRAPPED_CHESTS).add(LootrRegistry.getTrappedChestItem());
      this.tag(LootrTags.Items.SHULKERS).add(LootrRegistry.getShulkerItem());
      this.tag(LootrTags.Items.SANDS).add(LootrRegistry.getSuspiciousSandItem());
      this.tag(LootrTags.Items.GRAVELS).add(LootrRegistry.getSuspiciousGravelItem());
      this.tag(LootrTags.Items.POTS).add(LootrRegistry.getDecoratedPotItem());
      this.tag(LootrTags.Items.CONTAINERS)
         .addTags(
            new TagKey[]{
               LootrTags.Items.BARRELS,
               LootrTags.Items.CHESTS,
               LootrTags.Items.TRAPPED_CHESTS,
               LootrTags.Items.SHULKERS,
               LootrTags.Items.SANDS,
               LootrTags.Items.GRAVELS,
               LootrTags.Items.POTS
            }
         );
      this.tag(LootrTags.Items.ITEM_FRAME_CONVERT_BLACKLIST).add(new Item[]{Items.FILLED_MAP, Items.MAP});
   }

   public String getName() {
      return "Lootr Item Tags";
   }
}
