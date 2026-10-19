package net.p3pp3rf1y.sophisticatedcore.data;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;

public class SCFluidTagsProvider extends FluidTagsProvider {
   public SCFluidTagsProvider(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "sophisticatedcore", existingFileHelper);
   }

   protected void addTags(Provider registries) {
      this.tag(ModFluids.EXPERIENCE_TAG).add((Fluid)ModFluids.XP_STILL.get());
   }
}
