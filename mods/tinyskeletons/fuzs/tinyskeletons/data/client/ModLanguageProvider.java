package fuzs.tinyskeletons.data.client;

import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider.TranslationBuilder;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.world.entity.EntityType;

public class ModLanguageProvider extends AbstractLanguageProvider {
   public ModLanguageProvider(DataProviderContext context) {
      super(context);
   }

   public void addTranslations(TranslationBuilder builder) {
      builder.add((EntityType)ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(), "Baby Skeleton");
      builder.add((EntityType)ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(), "Baby Wither Skeleton");
      builder.add((EntityType)ModRegistry.BABY_STRAY_ENTITY_TYPE.value(), "Baby Stray");
      builder.add((EntityType)ModRegistry.BABY_BOGGED_ENTITY_TYPE.value(), "Baby Bogged");
   }
}
