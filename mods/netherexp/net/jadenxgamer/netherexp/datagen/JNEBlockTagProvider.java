package net.jadenxgamer.netherexp.datagen;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockTagsProvider;

public class JNEBlockTagProvider extends LodestoneBlockTagsProvider {
   public JNEBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "netherexp", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      new HashSet(JNEBlocks.BLOCKS.getEntries());
   }
}
