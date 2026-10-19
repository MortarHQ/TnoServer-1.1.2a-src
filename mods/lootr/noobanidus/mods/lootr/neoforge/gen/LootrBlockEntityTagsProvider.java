package noobanidus.mods.lootr.neoforge.gen;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.Nullable;

public class LootrBlockEntityTagsProvider extends IntrinsicHolderTagsProvider<BlockEntityType<?>> {
   public LootrBlockEntityTagsProvider(PackOutput arg, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(
         arg, Registries.BLOCK_ENTITY_TYPE, completableFuture, arg2 -> Objects.requireNonNull(arg2.builtInRegistryHolder()).key(), "lootr", existingFileHelper
      );
   }

   protected void addTags(Provider provider) {
      this.tag(LootrTags.BlockEntity.LOOTR_OBJECT);
      this.tag(LootrTags.BlockEntity.TRAPPED).add(LootrRegistry.getTrappedChestBlockEntity());
      this.tag(LootrTags.BlockEntity.CUSTOM_INELIGIBLE);
      this.tag(LootrTags.BlockEntity.CONVERT_BLACKLIST)
         .add(
            new BlockEntityType[]{
               BlockEntityType.BANNER,
               BlockEntityType.BEACON,
               BlockEntityType.BED,
               BlockEntityType.BEEHIVE,
               BlockEntityType.BELL,
               BlockEntityType.BLAST_FURNACE,
               BlockEntityType.BREWING_STAND,
               BlockEntityType.CALIBRATED_SCULK_SENSOR,
               BlockEntityType.CAMPFIRE,
               BlockEntityType.CHISELED_BOOKSHELF,
               BlockEntityType.COMMAND_BLOCK,
               BlockEntityType.COMPARATOR,
               BlockEntityType.CONDUIT,
               BlockEntityType.CRAFTER,
               BlockEntityType.DAYLIGHT_DETECTOR,
               BlockEntityType.DISPENSER,
               BlockEntityType.DROPPER,
               BlockEntityType.ENCHANTING_TABLE,
               BlockEntityType.ENDER_CHEST,
               BlockEntityType.FURNACE,
               BlockEntityType.HANGING_SIGN,
               BlockEntityType.HOPPER,
               BlockEntityType.JIGSAW,
               BlockEntityType.JUKEBOX,
               BlockEntityType.LECTERN,
               BlockEntityType.PISTON,
               BlockEntityType.SCULK_CATALYST,
               BlockEntityType.SCULK_SENSOR,
               BlockEntityType.SCULK_SHRIEKER,
               BlockEntityType.SIGN,
               BlockEntityType.SKULL,
               BlockEntityType.SMOKER,
               BlockEntityType.MOB_SPAWNER,
               BlockEntityType.STRUCTURE_BLOCK,
               BlockEntityType.END_GATEWAY,
               BlockEntityType.END_PORTAL,
               BlockEntityType.TRIAL_SPAWNER,
               BlockEntityType.VAULT
            }
         )
         .add(
            new BlockEntityType[]{
               LootrRegistry.getBrushableBlockEntity(),
               LootrRegistry.getBarrelBlockEntity(),
               LootrRegistry.getChestBlockEntity(),
               LootrRegistry.getTrappedChestBlockEntity(),
               LootrRegistry.getInventoryBlockEntity(),
               LootrRegistry.getShulkerBlockEntity(),
               LootrRegistry.getDecoratedPotBlockEntity()
            }
         );
   }

   public String getName() {
      return "Lootr Block Entity Type Tags";
   }
}
