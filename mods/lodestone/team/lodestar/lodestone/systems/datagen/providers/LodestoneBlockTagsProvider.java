package team.lodestar.lodestone.systems.datagen.providers;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import team.lodestar.lodestone.systems.datagen.LodestoneDatagenBlockData;

public abstract class LodestoneBlockTagsProvider extends BlockTagsProvider {
   public LodestoneBlockTagsProvider(
      PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(output, lookupProvider, modId, existingFileHelper);
   }

   public void addTagsFromBlockProperties(Set<DeferredHolder<Block, ? extends Block>> blocks) {
      for (Block block : blocks.stream().<Block>map(DeferredHolder::get).sorted(Comparator.comparingInt(b -> BuiltInRegistries.BLOCK.getId(b))).toList()) {
         LodestoneBlockProperties properties = (LodestoneBlockProperties)block.properties();
         LodestoneDatagenBlockData data = properties.getDatagenData();

         for (TagKey<Block> tag : data.getTags()) {
            this.tag(tag).add(block);
         }
      }
   }
}
