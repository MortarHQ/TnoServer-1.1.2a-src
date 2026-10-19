package io.github.manasmods.tensura_iaf.neoforge.data;

import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.tag.IafEntityTags;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura_iaf.data.TensuraIafEntityTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IafEntityTypeTagProvider extends EntityTypeTagsProvider {
   public IafEntityTypeTagProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, completableFuture, "tensura_iaf", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(TensuraIafEntityTags.CANNOT_BE_CHAINED)
         .addTag(TensuraEntityTags.HERO_BOSS)
         .add(
            new EntityType[]{
               (EntityType)HumanEntityTypes.HINATA_SAKAGUCHI.get(),
               (EntityType)HumanEntityTypes.GAZEL_DWARGO.get(),
               (EntityType)MonsterEntityTypes.IFRIT_CLONE.get()
            }
         )
         .addOptional(ResourceLocation.parse("tensura_neb:rimuru_ogre_fight"))
         .addOptional(ResourceLocation.parse("tensura_neb:rimuru_clone"))
         .addOptional(ResourceLocation.parse("tensura_neb:carrion"))
         .addOptional(ResourceLocation.parse("tensura_neb:luminous_valentine"));
      this.tag(TensuraEntityTags.NON_LIVING).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.EP_INITIATE_EXCLUDED).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.NO_ENERGY_DRAIN).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.NO_EP_PLUNDER).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.NO_SKILL_PLUNDER).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.NO_SPIRITUAL_DAMAGE).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.CAN_DIE_IN_LABYRINTH).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.HINATA_NEUTRAL).add((EntityType)IafEntities.STONE_STATUE.get());
      this.tag(TensuraEntityTags.DROP_CRYSTAL)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.AMPHITHERE.get(),
               (EntityType)IafEntities.COCKATRICE.get(),
               (EntityType)IafEntities.CYCLOPS.get(),
               (EntityType)IafEntities.DEATH_WORM.get(),
               (EntityType)IafEntities.DREAD_BEAST.get(),
               (EntityType)IafEntities.DREAD_GHOUL.get(),
               (EntityType)IafEntities.DREAD_KNIGHT.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.DREAD_SCUTTLER.get(),
               (EntityType)IafEntities.DREAD_THRALL.get(),
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.GHOST.get(),
               (EntityType)IafEntities.GORGON.get(),
               (EntityType)IafEntities.HIPPOCAMPUS.get(),
               (EntityType)IafEntities.HIPPOGRYPH.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get(),
               (EntityType)IafEntities.PIXIE.get(),
               (EntityType)IafEntities.SEA_SERPENT.get(),
               (EntityType)IafEntities.SIREN.get(),
               (EntityType)IafEntities.STYMPHALIAN_BIRD.get(),
               (EntityType)IafEntities.TROLL.get()
            }
         );
      this.tag(TensuraEntityTags.NAMEABLE)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.AMPHITHERE.get(),
               (EntityType)IafEntities.COCKATRICE.get(),
               (EntityType)IafEntities.DEATH_WORM.get(),
               (EntityType)IafEntities.HIPPOCAMPUS.get(),
               (EntityType)IafEntities.HIPPOGRYPH.get(),
               (EntityType)IafEntities.PIXIE.get(),
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get()
            }
         );
      this.tag(TensuraEntityTags.HERO_BOSS)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.GORGON.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get()
            }
         );
      this.tag(TensuraEntityTags.FULL_GRAVITY_CONTROL)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get()
            }
         );
      this.tag(TensuraEntityTags.NO_FEAR)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.GORGON.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get(),
               (EntityType)IafEntities.STONE_STATUE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_POSSESSION)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.GORGON.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get(),
               (EntityType)IafEntities.STONE_STATUE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_MIND_CONTROL)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.GORGON.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get(),
               (EntityType)IafEntities.STONE_STATUE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_CHARISMA)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.COCKATRICE.get(),
               (EntityType)IafEntities.CYCLOPS.get(),
               (EntityType)IafEntities.DEATH_WORM.get(),
               (EntityType)IafEntities.DREAD_BEAST.get(),
               (EntityType)IafEntities.DREAD_GHOUL.get(),
               (EntityType)IafEntities.DREAD_KNIGHT.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.DREAD_HORSE.get(),
               (EntityType)IafEntities.DREAD_SCUTTLER.get(),
               (EntityType)IafEntities.DREAD_THRALL.get(),
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.GHOST.get(),
               (EntityType)IafEntities.GORGON.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.ICE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get(),
               (EntityType)IafEntities.SEA_SERPENT.get(),
               (EntityType)IafEntities.SIREN.get(),
               (EntityType)IafEntities.STYMPHALIAN_BIRD.get(),
               (EntityType)IafEntities.TROLL.get()
            }
         );
      this.tag(TensuraEntityTags.SPIRITUAL).add(new EntityType[]{(EntityType)IafEntities.GHOST.get(), (EntityType)IafEntities.PIXIE.get()});
      this.tag(TensuraEntityTags.NO_SOUND)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.DRAGON_EGG.get(),
               (EntityType)IafEntities.DRAGON_SKULL.get(),
               (EntityType)IafEntities.HIPPOGRYPH_EGG.get(),
               (EntityType)IafEntities.STONE_STATUE.get(),
               (EntityType)IafEntities.DEATH_WORM_EGG.get(),
               (EntityType)IafEntities.COCKATRICE_EGG.get(),
               (EntityType)IafEntities.STYMPHALIAN_FEATHER.get(),
               (EntityType)IafEntities.SEA_SERPENT_BUBBLES.get(),
               (EntityType)IafEntities.CHAIN_TIE.get(),
               (EntityType)IafEntities.MOB_SKULL.get(),
               (EntityType)IafEntities.STONE_STATUE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_BLOOD).add(new EntityType[]{(EntityType)IafEntities.DEATH_WORM.get(), (EntityType)IafEntities.STONE_STATUE.get()});
      this.tag(TensuraEntityTags.COLD_BLOODED)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.HIPPOCAMPUS.get(),
               (EntityType)IafEntities.SEA_SERPENT.get(),
               (EntityType)IafEntities.SIREN.get(),
               (EntityType)IafEntities.HYDRA.get(),
               (EntityType)IafEntities.COCKATRICE.get(),
               (EntityType)IafEntities.FIRE_DRAGON.get(),
               (EntityType)IafEntities.LIGHTNING_DRAGON.get(),
               (EntityType)IafEntities.ICE_DRAGON.get()
            }
         );
      this.tag(TensuraEntityTags.COLD_SOURCE)
         .add(
            new EntityType[]{
               (EntityType)IafEntities.DREAD_BEAST.get(),
               (EntityType)IafEntities.DREAD_GHOUL.get(),
               (EntityType)IafEntities.DREAD_KNIGHT.get(),
               (EntityType)IafEntities.DREAD_HORSE.get(),
               (EntityType)IafEntities.DREAD_LICH.get(),
               (EntityType)IafEntities.DREAD_SCUTTLER.get(),
               (EntityType)IafEntities.DREAD_THRALL.get(),
               (EntityType)IafEntities.GHOST.get(),
               (EntityType)IafEntities.ICE_DRAGON.get()
            }
         );
      this.tag(TensuraEntityTags.HOT_SOURCE).add((EntityType)IafEntities.FIRE_DRAGON.get());
      this.tag(IafEntityTags.IMMUNE_TO_GORGON_STONE)
         .addTag(TensuraEntityTags.SPIRITUAL)
         .add(
            new EntityType[]{
               (EntityType)MonsterEntityTypes.BASILISK.get(),
               (EntityType)MonsterEntityTypes.ELEMENTAL_COLOSSUS.get(),
               (EntityType)HumanEntityTypes.HINATA_SAKAGUCHI.get(),
               (EntityType)MonsterEntityTypes.CHARYBDIS.get(),
               (EntityType)HumanEntityTypes.GAZEL_DWARGO.get(),
               (EntityType)MonsterEntityTypes.IFRIT_CLONE.get()
            }
         )
         .addOptional(ResourceLocation.parse("tensura_neb:rimuru_ogre_fight"))
         .addOptional(ResourceLocation.parse("tensura_neb:rimuru_clone"))
         .addOptional(ResourceLocation.parse("tensura_neb:carrion"))
         .addOptional(ResourceLocation.parse("tensura_neb:luminous_valentine"));
      this.tag(IafEntityTags.CYCLOPS_UNLIFTABLES)
         .addTag(TensuraEntityTags.HERO_BOSS)
         .add(
            new EntityType[]{
               (EntityType)HumanEntityTypes.HINATA_SAKAGUCHI.get(),
               (EntityType)HumanEntityTypes.GAZEL_DWARGO.get(),
               (EntityType)MonsterEntityTypes.IFRIT_CLONE.get()
            }
         )
         .addOptional(ResourceLocation.parse("tensura_neb:rimuru_ogre_fight"))
         .addOptional(ResourceLocation.parse("tensura_neb:rimuru_clone"))
         .addOptional(ResourceLocation.parse("tensura_neb:carrion"))
         .addOptional(ResourceLocation.parse("tensura_neb:luminous_valentine"));
   }
}
