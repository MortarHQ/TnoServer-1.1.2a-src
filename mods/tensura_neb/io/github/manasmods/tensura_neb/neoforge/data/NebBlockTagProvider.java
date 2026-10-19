package io.github.manasmods.tensura_neb.neoforge.data;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NebBlockTagProvider extends BlockTagsProvider {
   public NebBlockTagProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, completableFuture, "tensura_neb", existingFileHelper);
   }

   protected void addTags(Provider arg) {
   }
}
