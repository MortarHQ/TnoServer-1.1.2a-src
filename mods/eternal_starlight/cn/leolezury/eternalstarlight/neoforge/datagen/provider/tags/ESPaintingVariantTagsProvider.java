package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESPaintingVariantTagsProvider extends PaintingVariantTagsProvider {
   public ESPaintingVariantTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ESTags.PaintingVariants.PLACEABLE)
         .add(
            new ResourceKey[]{
               ESPaintingVariants.THE_CATALYST,
               ESPaintingVariants.CRYSTALBORN,
               ESPaintingVariants.VOIDSTONE_APPLE,
               ESPaintingVariants.PUNGENCY_FRUIT_FEAST,
               ESPaintingVariants.STARFLOWERS,
               ESPaintingVariants.COLD_ANOMALY,
               ESPaintingVariants.NORTHLAND_HUT,
               ESPaintingVariants.RAT_HEAD,
               ESPaintingVariants.ORIGINAL_CRETEOR,
               ESPaintingVariants.TWILIGHT_SQUID,
               ESPaintingVariants.POWER,
               ESPaintingVariants.POT_OF_CRESTS,
               ESPaintingVariants.THIRSTY,
               ESPaintingVariants.STACK,
               ESPaintingVariants.CARVED_SLATE,
               ESPaintingVariants.INSECT_SPECIMEN,
               ESPaintingVariants.SWORD_OF_THE_LAKE,
               ESPaintingVariants.SKELETON_LONESTAR,
               ESPaintingVariants.HYMN_OF_THE_RATS,
               ESPaintingVariants.COOLER,
               ESPaintingVariants.RIVEN_WELKIN,
               ESPaintingVariants.UMBROUS_ALCHEMIST,
               ESPaintingVariants.EXTINGUISHED_SUN,
               ESPaintingVariants.NOVUS_SOL
            }
         );
   }
}
