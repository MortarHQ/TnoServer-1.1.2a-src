package artifacts.neoforge.data.tags;

import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModTags;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EntityTypeTags extends EntityTypeTagsProvider {
   public EntityTypeTags(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(packOutput, lookupProvider, "artifacts", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(EntityTypes.CAPTURING_NOT_SUPPORTED).add(ModEntityTypes.MIMIC.value());
      this.tag(ModTags.CREEPERS).add(EntityType.CREEPER);

      for (String creeper : Arrays.asList(
         "jungle_creeper",
         "bamboo_creeper",
         "desert_creeper",
         "badlands_creeper",
         "hills_creeper",
         "savannah_creeper",
         "mushroom_creeper",
         "swamp_creeper",
         "dripstone_creeper",
         "cave_creeper",
         "dark_oak_creeper",
         "spruce_creeper",
         "beach_creeper",
         "snowy_creeper"
      )) {
         this.tag(ModTags.CREEPERS).addOptional(ResourceLocation.fromNamespaceAndPath("creeperoverhaul", creeper));
      }
   }
}
