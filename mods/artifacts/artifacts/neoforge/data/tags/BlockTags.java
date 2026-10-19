package artifacts.neoforge.data.tags;

import artifacts.client.mimic.MimicChestLayer;
import artifacts.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockTags extends BlockTagsProvider {
   public BlockTags(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(packOutput, lookupProvider, "artifacts", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE);
      this.tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL);
      this.tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE);
      this.tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE);
      this.tag(ModTags.CAMPSITE_CHESTS).add(Blocks.CHEST);

      for (String chestType : MimicChestLayer.QUARK_CHEST_MATERIALS) {
         this.tag(ModTags.CAMPSITE_CHESTS).addOptional(ResourceLocation.fromNamespaceAndPath("quark", "%s_chest".formatted(chestType)));
      }

      this.tag(ModTags.ROOTED_BOOTS_GRASS).add(new Block[]{Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK, Blocks.MOSS_CARPET, Blocks.DIRT_PATH, Blocks.PODZOL});
      this.tag(ModTags.SNOW_LAYERS).add(Blocks.SNOW);
   }
}
