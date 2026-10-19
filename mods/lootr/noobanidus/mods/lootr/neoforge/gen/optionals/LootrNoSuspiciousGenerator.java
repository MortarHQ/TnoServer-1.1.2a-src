package noobanidus.mods.lootr.neoforge.gen.optionals;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import org.jetbrains.annotations.Nullable;

public class LootrNoSuspiciousGenerator {
   public static class LootrBlockTagProvider extends BlockTagsProvider {
      public LootrBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
         super(output, lookupProvider, "lootr", existingFileHelper);
      }

      protected void addTags(Provider provider) {
         this.tag(LootrTags.Blocks.CONVERT_SANDS).replace();
         this.tag(LootrTags.Blocks.CONVERT_GRAVELS).replace();
      }

      public String getName() {
         return "(Optional) Remove Lootr Suspicious Block Tags";
      }
   }
}
