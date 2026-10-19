package fuzs.tinyskeletons.data;

import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTagProvider extends AbstractTagProvider<EntityType<?>> {
   public ModEntityTypeTagProvider(DataProviderContext context) {
      super(Registries.ENTITY_TYPE, context);
   }

   public void addTags(Provider provider) {
      this.add(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add((EntityType)ModRegistry.BABY_STRAY_ENTITY_TYPE.value());
      this.add(EntityTypeTags.SKELETONS)
         .add(
            new EntityType[]{
               (EntityType)ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(),
               (EntityType)ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
               (EntityType)ModRegistry.BABY_STRAY_ENTITY_TYPE.value(),
               (EntityType)ModRegistry.BABY_BOGGED_ENTITY_TYPE.value()
            }
         );
      this.add(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE).add((EntityType)ModRegistry.BABY_BOGGED_ENTITY_TYPE.value());
   }
}
