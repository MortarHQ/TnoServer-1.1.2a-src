package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFStructures;

public class StructureTagGenerator extends StructureTagsProvider {
   public static final TagKey<Structure> LANDMARK = TagKey.create(Registries.STRUCTURE, TwilightForestMod.prefix("landmark"));

   public StructureTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
      super(output, provider, "twilightforest", helper);
   }

   protected void addTags(Provider p_256087_) {
      this.tag(LANDMARK)
         .add(
            new ResourceKey[]{
               TFStructures.HEDGE_MAZE,
               TFStructures.QUEST_GROVE,
               TFStructures.MUSHROOM_TOWER,
               TFStructures.HOLLOW_HILL_SMALL,
               TFStructures.HOLLOW_HILL_MEDIUM,
               TFStructures.HOLLOW_HILL_LARGE,
               TFStructures.NAGA_COURTYARD,
               TFStructures.LICH_TOWER,
               TFStructures.LABYRINTH,
               TFStructures.HYDRA_LAIR,
               TFStructures.KNIGHT_STRONGHOLD,
               TFStructures.DARK_TOWER,
               TFStructures.YETI_CAVE,
               TFStructures.AURORA_PALACE,
               TFStructures.TROLL_CAVE,
               TFStructures.FINAL_CASTLE
            }
         );
   }
}
