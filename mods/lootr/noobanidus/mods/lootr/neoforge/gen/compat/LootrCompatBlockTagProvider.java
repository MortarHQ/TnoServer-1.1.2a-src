package noobanidus.mods.lootr.neoforge.gen.compat;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import org.jetbrains.annotations.Nullable;

public class LootrCompatBlockTagProvider extends BlockTagsProvider {
   private final List<String> BARREL_BLOCK_IDS;
   private final List<String> CHEST_BLOCK_IDS;
   private final List<String> TRAPPED_CHEST_BLOCK_IDS;
   private final List<String> SHULKER_BOX_BLOCK_IDS;
   private final String COMPAT_MODID;

   public LootrCompatBlockTagProvider(
      String compatModid,
      @Nullable List<String> barrelBlockIds,
      @Nullable List<String> chestBlockIds,
      @Nullable List<String> trappedBlockIds,
      @Nullable List<String> shulkerBoxIds,
      DataGenerator generator,
      CompletableFuture<Provider> lookupProvider,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(generator.getPackOutput(), lookupProvider, "lootr", existingFileHelper);
      this.COMPAT_MODID = compatModid;
      this.BARREL_BLOCK_IDS = barrelBlockIds == null ? List.of() : barrelBlockIds;
      this.CHEST_BLOCK_IDS = chestBlockIds == null ? List.of() : chestBlockIds;
      this.SHULKER_BOX_BLOCK_IDS = shulkerBoxIds == null ? List.of() : shulkerBoxIds;
      this.TRAPPED_CHEST_BLOCK_IDS = trappedBlockIds == null ? List.of() : trappedBlockIds;
   }

   protected void addTags(Provider provider) {
      if (!this.CHEST_BLOCK_IDS.isEmpty()) {
         IntrinsicTagAppender<Block> tag = this.tag(LootrTags.Blocks.CONVERT_CHESTS);

         for (String block : this.CHEST_BLOCK_IDS) {
            tag.add(TagEntry.optionalElement(ResourceLocation.fromNamespaceAndPath(this.COMPAT_MODID, block)));
         }
      }

      if (!this.BARREL_BLOCK_IDS.isEmpty()) {
         IntrinsicTagAppender<Block> tag = this.tag(LootrTags.Blocks.CONVERT_BARRELS);

         for (String block : this.BARREL_BLOCK_IDS) {
            tag.add(TagEntry.optionalElement(ResourceLocation.fromNamespaceAndPath(this.COMPAT_MODID, block)));
         }
      }

      if (!this.TRAPPED_CHEST_BLOCK_IDS.isEmpty()) {
         IntrinsicTagAppender<Block> tag = this.tag(LootrTags.Blocks.CONVERT_TRAPPED_CHESTS);

         for (String block : this.TRAPPED_CHEST_BLOCK_IDS) {
            tag.add(TagEntry.optionalElement(ResourceLocation.fromNamespaceAndPath(this.COMPAT_MODID, block)));
         }
      }

      if (!this.SHULKER_BOX_BLOCK_IDS.isEmpty()) {
         IntrinsicTagAppender<Block> tag = this.tag(LootrTags.Blocks.CONVERT_SHULKERS);

         for (String block : this.SHULKER_BOX_BLOCK_IDS) {
            tag.add(TagEntry.optionalElement(ResourceLocation.fromNamespaceAndPath(this.COMPAT_MODID, block)));
         }
      }
   }

   public String getName() {
      return this.COMPAT_MODID + " Compatibility Block Tags";
   }
}
