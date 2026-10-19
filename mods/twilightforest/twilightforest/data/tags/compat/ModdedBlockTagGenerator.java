package twilightforest.data.tags.compat;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlocks;

public class ModdedBlockTagGenerator extends IntrinsicHolderTagsProvider<Block> {
   public static final TagKey<Block> AC_FERROMAGNETIC_BLOCKS = createTagFor("alexscaves", "ferromagnetic_blocks");
   public static final TagKey<Block> AC_GLOOMOTH_LIGHT_SOURCES = createTagFor("alexscaves", "gloomoth_light_sources");
   public static final TagKey<Block> AC_UNDERZEALOT_LIGHT_SOURCES = createTagFor("alexscaves", "underzealot_light_sources");
   public static final TagKey<Block> ARTIFACTS_CAMPSITE_CHESTS = createTagFor("artifacts", "campsite_chests");
   public static final TagKey<Block> CHEST_MOUNTED_STORAGE = createTagFor("create", "chest_mounted_storage");
   public static final TagKey<Block> PASSIVE_BOILER_HEATERS = createTagFor("create", "passive_boiler_heaters");
   public static final TagKey<Block> TREE_ATTACHMENTS = createTagFor("create", "tree_attachments");
   public static final TagKey<Block> FD_COMPOST_ACTIVATORS = createTagFor("farmersdelight", "compost_activators");
   public static final TagKey<Block> FD_HEAT_SOURCES = createTagFor("farmersdelight", "heat_sources");

   public ModdedBlockTagGenerator(PackOutput output, CompletableFuture<Provider> future, ExistingFileHelper helper) {
      super(output, Registries.BLOCK, future, block -> block.builtInRegistryHolder().key(), "twilightforest", helper);
   }

   protected void addTags(Provider provider) {
      this.tag(AC_FERROMAGNETIC_BLOCKS)
         .addTag(BlockTagGenerator.STORAGE_BLOCKS_IRONWOOD)
         .addTag(BlockTagGenerator.STORAGE_BLOCKS_STEELEAF)
         .addTag(BlockTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL)
         .add((Block)TFBlocks.CANDELABRA.get())
         .add((Block)TFBlocks.WROUGHT_IRON_FENCE.get());
      this.tag(AC_GLOOMOTH_LIGHT_SOURCES).add(new Block[]{(Block)TFBlocks.FIREFLY_SPAWNER.get(), (Block)TFBlocks.FIREFLY_JAR.get()});
      this.tag(AC_UNDERZEALOT_LIGHT_SOURCES).add(new Block[]{(Block)TFBlocks.FIREFLY.get(), (Block)TFBlocks.MOONWORM.get()});
      this.tag(ARTIFACTS_CAMPSITE_CHESTS).addTag(BlockTagGenerator.TF_CHESTS);
      this.tag(CHEST_MOUNTED_STORAGE)
         .addTag(BlockTagGenerator.TF_CHESTS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
               (Block)TFBlocks.DARK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TIME_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MINING_TRAPPED_CHEST.get(),
               (Block)TFBlocks.SORTING_TRAPPED_CHEST.get()
            }
         );
      this.tag(PASSIVE_BOILER_HEATERS).addTag(BlockTagGenerator.STORAGE_BLOCKS_FIERY);
      this.tag(TREE_ATTACHMENTS)
         .add(
            new Block[]{
               (Block)TFBlocks.TIME_LOG_CORE.get(),
               (Block)TFBlocks.TRANSFORMATION_LOG_CORE.get(),
               (Block)TFBlocks.MINING_LOG_CORE.get(),
               (Block)TFBlocks.SORTING_LOG_CORE.get(),
               (Block)TFBlocks.ROOT_BLOCK.get(),
               (Block)TFBlocks.LIVEROOT_BLOCK.get(),
               (Block)TFBlocks.MANGROVE_ROOT.get(),
               (Block)TFBlocks.FIREFLY.get(),
               (Block)TFBlocks.CICADA.get()
            }
         );
      this.tag(FD_COMPOST_ACTIVATORS).add(new Block[]{(Block)TFBlocks.UBEROUS_SOIL.get(), (Block)TFBlocks.MUSHGLOOM.get()});
      this.tag(FD_HEAT_SOURCES).addTag(BlockTagGenerator.STORAGE_BLOCKS_FIERY);
   }

   private static TagKey<Block> createTagFor(String modid, String tagName) {
      return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modid, tagName));
   }
}
