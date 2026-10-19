package noobanidus.mods.lootr.neoforge.gen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import org.jetbrains.annotations.Nullable;

public class LootrStructureTagsProvider extends IntrinsicHolderTagsProvider<Structure> {
   public LootrStructureTagsProvider(PackOutput arg, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(arg, Registries.STRUCTURE, completableFuture, arg2 -> null, "lootr", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(LootrTags.Structure.STRUCTURE_BLACKLIST);
      this.tag(LootrTags.Structure.STRUCTURE_WHITELIST);
      this.tag(LootrTags.Structure.DECAY_STRUCTURES);
      this.tag(LootrTags.Structure.REFRESH_STRUCTURES);
   }

   public String getName() {
      return "Lootr Structure Tags";
   }
}
