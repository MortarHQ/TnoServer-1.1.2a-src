package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTagProvider extends AbstractTagProvider<EntityType<?>> {
   public ModEntityTypeTagProvider(DataProviderContext context) {
      super(Registries.ENTITY_TYPE, context);
   }

   public void addTags(Provider provider) {
      this.add(EntityTypeTags.RAIDERS)
         .add(
            new EntityType[]{
               (EntityType)ModEntityTypes.BASHER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.SORCERER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.INVOKER_ENTITY_TYPE.value()
            }
         );
      this.add(EntityTypeTags.ILLAGER)
         .add(
            new EntityType[]{
               (EntityType)ModEntityTypes.BASHER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.SORCERER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.INVOKER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.FIRECALLER_ENTITY_TYPE.value()
            }
         );
      this.add(EntityTypeTags.FALL_DAMAGE_IMMUNE).add((EntityType)ModEntityTypes.INVOKER_ENTITY_TYPE.value());
      this.add("numismatic-overhaul:the_bourgeoisie")
         .add(
            new EntityType[]{
               (EntityType)ModEntityTypes.BASHER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.SORCERER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
               (EntityType)ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value()
            }
         );
   }
}
