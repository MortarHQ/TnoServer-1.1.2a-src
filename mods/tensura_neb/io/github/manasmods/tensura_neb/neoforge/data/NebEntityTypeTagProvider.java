package io.github.manasmods.tensura_neb.neoforge.data;

import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NebEntityTypeTagProvider extends EntityTypeTagsProvider {
   public NebEntityTypeTagProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, completableFuture, "tensura_neb", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.CLONES).add((EntityType)NebEntityTypes.RIMURU_CLONE.get());
      this.tag(TensuraEntityTags.SLIME_WALKABLE_MOBS)
         .add(new EntityType[]{(EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(), (EntityType)NebEntityTypes.RIMURU_CLONE.get()});
      this.tag(TensuraEntityTags.MONSTER)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.SLIMES)
         .add(new EntityType[]{(EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(), (EntityType)NebEntityTypes.RIMURU_CLONE.get()});
      this.tag(TensuraEntityTags.HINATA_NEUTRAL).add((EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get());
      this.tag(TensuraEntityTags.SPIRITUAL).add((EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get());
      this.tag(TensuraEntityTags.HERO_BOSS)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_FEAR)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.FULL_GRAVITY_CONTROL)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_MIND_CONTROL)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.NON_LIVING).add((EntityType)NebEntityTypes.VELDORA.get());
      this.tag(TensuraEntityTags.NO_POSSESSION).add((EntityType)NebEntityTypes.RIMURU_CLONE.get());
      this.tag(TensuraEntityTags.NO_SYNTHESISE)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_ENERGY_DRAIN).add((EntityType)NebEntityTypes.RIMURU_CLONE.get());
      this.tag(TensuraEntityTags.NO_SPIRITUAL_DAMAGE).add((EntityType)NebEntityTypes.RIMURU_CLONE.get());
      this.tag(TensuraEntityTags.NO_BLOOD)
         .add(new EntityType[]{(EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(), (EntityType)NebEntityTypes.RIMURU_CLONE.get()});
      this.tag(EntityTypes.BOSSES)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
      this.tag(EntityTypes.CAPTURING_NOT_SUPPORTED)
         .add(
            new EntityType[]{
               (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
               (EntityType)NebEntityTypes.RIMURU_CLONE.get(),
               (EntityType)NebEntityTypes.CARRION.get(),
               (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get()
            }
         );
   }
}
