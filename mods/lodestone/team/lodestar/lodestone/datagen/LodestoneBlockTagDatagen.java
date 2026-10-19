package team.lodestar.lodestone.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class LodestoneBlockTagDatagen extends BlockTagsProvider {
   public LodestoneBlockTagDatagen(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "lodestone", existingFileHelper);
   }

   public String getName() {
      return "Lodestone Block Tags";
   }

   protected void addTags(Provider pProvider) {
   }
}
