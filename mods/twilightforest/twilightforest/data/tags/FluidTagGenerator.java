package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;

public class FluidTagGenerator extends FluidTagsProvider {
   public static final TagKey<Fluid> FIRE_JET_FUEL = FluidTags.create(TwilightForestMod.prefix("fire_jet_fuel"));

   public FluidTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "twilightforest", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(FIRE_JET_FUEL).addTag(FluidTags.LAVA);
   }

   public String getName() {
      return "Twilight Forest Fluid Tags";
   }
}
