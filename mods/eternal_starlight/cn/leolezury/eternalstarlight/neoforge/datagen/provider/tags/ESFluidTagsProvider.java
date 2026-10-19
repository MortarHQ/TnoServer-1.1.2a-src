package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESFluidTagsProvider extends FluidTagsProvider {
   public ESFluidTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ESTags.Fluids.ETHER).add(new Fluid[]{(Fluid)ESFluids.ETHER_STILL.get(), (Fluid)ESFluids.ETHER_FLOWING.get()});
   }
}
