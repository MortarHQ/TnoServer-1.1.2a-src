package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESStructureTagsProvider extends StructureTagsProvider {
   public ESStructureTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider arg) {
      this.tag(ESTags.Structures.BOSS_LANDMARKS).add(new ResourceKey[]{ESStructures.GOLEM_FORGE, ESStructures.CURSED_GARDEN});
      this.tag(ESTags.Structures.GOLEM_FORGE).add(ESStructures.GOLEM_FORGE);
      this.tag(ESTags.Structures.CURSED_GARDEN).add(ESStructures.CURSED_GARDEN);
      this.tag(ESTags.Structures.PORTAL_RUINS)
         .add(
            new ResourceKey[]{
               ESStructures.PORTAL_RUINS_COMMON,
               ESStructures.PORTAL_RUINS_FOREST,
               ESStructures.PORTAL_RUINS_DESERT,
               ESStructures.PORTAL_RUINS_JUNGLE,
               ESStructures.PORTAL_RUINS_COLD
            }
         );
   }
}
