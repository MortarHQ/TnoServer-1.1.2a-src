package artifacts.neoforge.data.tags;

import artifacts.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DamageTypeTags extends DamageTypeTagsProvider {
   public DamageTypeTags(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(packOutput, lookupProvider, "artifacts", existingFileHelper);
   }

   protected void addTags(Provider arg) {
      this.tag(ModTags.IS_HOT_FLOOR).add(DamageTypes.HOT_FLOOR);
   }
}
