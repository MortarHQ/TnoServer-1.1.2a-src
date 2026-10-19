package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESEntityTypeTagsProvider extends EntityTypeTagsProvider {
   public ESEntityTypeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider lookupProvider) {
      this.tag(EntityTypes.BOSSES)
         .add(
            new EntityType[]{
               ESEntities.THE_GATEKEEPER.get(), ESEntities.STARLIGHT_GOLEM.get(), ESEntities.LUNAR_MONSTROSITY.get(), ESEntities.SOLAR_CREEPER.get()
            }
         );
      this.tag(EntityTypes.BOATS).add(new EntityType[]{ESEntities.BOAT.get(), ESEntities.CHEST_BOAT.get()});
      this.tag(ESTags.EntityTypes.AFFECTS_PROGRESSION)
         .add(
            new EntityType[]{
               ESEntities.THIRST_WALKER.get(),
               ESEntities.STRANGHOUL.get(),
               ESEntities.SHIMMER_LACEWING.get(),
               ESEntities.TWILIGHT_GAZE.get(),
               ESEntities.FREEZE.get(),
               ESEntities.PERMAFROST.get(),
               ESEntities.TANGLED.get()
            }
         );
      this.tag(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLIES)
         .add(new EntityType[]{ESEntities.FREEZE.get(), ESEntities.PERMAFROST.get(), ESEntities.STARLIGHT_GOLEM.get()});
      this.tag(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES)
         .add(new EntityType[]{ESEntities.TANGLED.get(), ESEntities.TANGLED_SKULL.get(), ESEntities.LUNAR_MONSTROSITY.get()});
      this.tag(ESTags.EntityTypes.ABYSSAL_FIRE_IMMUNE)
         .add(new EntityType[]{ESEntities.LUMINOFISH.get(), ESEntities.LUMINARIS.get(), ESEntities.TWILIGHT_GAZE.get()});
      this.tag(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB).add(new EntityType[]{EntityType.BAT, EntityType.WARDEN});
      this.tag(ESTags.EntityTypes.GLEECH_IMMUNE)
         .add(new EntityType[]{ESEntities.GLEECH.get(), ESEntities.NIGHTFALL_SPIDER.get(), EntityType.SILVERFISH, EntityType.SPIDER, EntityType.CAVE_SPIDER});
      this.tag(ESTags.EntityTypes.CHAIN_OF_SOULS_CANNOT_PULL).addTag(EntityTypes.BOSSES);
      this.tag(ESTags.EntityTypes.TEARY_IMMUNE).addTag(EntityTypes.BOSSES);
      this.tag(ESTags.EntityTypes.STRANGHOUL_PREYS).addTag(EntityTypeTags.ZOMBIES);
      this.tag(ESTags.EntityTypes.STRANGHOUL_CANNOT_HUNT).addTag(EntityTypes.BOSSES);
      this.tag(ESTags.EntityTypes.STARFIRE_BIRD_AFRAID_OF).add(EntityType.PLAYER).addTag(EntityTypeTags.ARTHROPOD);
      this.tag(ESTags.EntityTypes.AETHERSENT_GOLEM_TARGETS).add(new EntityType[]{ESEntities.SEEKER.get(), ESEntities.TINY_CRETEOR.get(), EntityType.PHANTOM});
      this.tag(EntityTypeTags.SKELETONS).add(new EntityType[]{ESEntities.LONESTAR_SKELETON.get(), ESEntities.TANGLED.get()});
      this.tag(EntityTypeTags.ZOMBIES).add(ESEntities.ZOMBIFIED_RATLIN.get());
      this.tag(EntityTypeTags.ARROWS)
         .add(
            new EntityType[]{
               ESEntities.THIOQUARTZ_ARROW.get(),
               ESEntities.AETHERSENT_ARROW.get(),
               ESEntities.GLACITE_ARROW.get(),
               ESEntities.MALARITE_ARROW.get(),
               ESEntities.AMARAMBER_ARROW.get(),
               ESEntities.VORACIOUS_ARROW.get(),
               ESEntities.AIR_SAC_ARROW.get()
            }
         );
      this.tag(EntityTypeTags.IMPACT_PROJECTILES)
         .add(
            new EntityType[]{
               ESEntities.AETHERSENT_METEOR.get(),
               ESEntities.AETHERSTRIKE_ROCKET.get(),
               ESEntities.GLEECH_EGG.get(),
               ESEntities.FROZEN_TUBE.get(),
               ESEntities.LUNAR_SPORE.get(),
               ESEntities.SHATTERED_BLADE.get(),
               ESEntities.MALARITE_SPEAR.get(),
               ESEntities.PUNGENCY_FRUIT_SPEAR.get(),
               ESEntities.THIOQUARTZ_SHARD.get(),
               ESEntities.SONAR_BOMB.get(),
               ESEntities.ASHEN_SNOWBALL.get(),
               ESEntities.FROZEN_BOMB.get(),
               ESEntities.WILTED_PETAL.get(),
               ESEntities.SHOT_SEEDS.get()
            }
         );
      this.tag(EntityTypeTags.UNDEAD).add(new EntityType[]{ESEntities.LONESTAR_SKELETON.get(), ESEntities.TANGLED.get(), ESEntities.TANGLED_SKULL.get()});
      this.tag(EntityTypeTags.ARTHROPOD).add(new EntityType[]{ESEntities.NIGHTFALL_SPIDER.get(), ESEntities.GLEECH.get()});
      this.tag(EntityTypeTags.AQUATIC)
         .add(new EntityType[]{ESEntities.ROOKFISH.get(), ESEntities.LUMINOFISH.get(), ESEntities.LUMINARIS.get(), ESEntities.TWILIGHT_GAZE.get()});
      this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
         .add(new EntityType[]{ESEntities.ROOKFISH.get(), ESEntities.LUMINOFISH.get(), ESEntities.LUMINARIS.get(), ESEntities.TWILIGHT_GAZE.get()});
      this.tag(EntityTypeTags.IMMUNE_TO_INFESTED).add(ESEntities.GLEECH.get());
      this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(new EntityType[]{ESEntities.FREEZE.get(), ESEntities.PERMAFROST.get()});
      this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
         .add(
            new EntityType[]{
               ESEntities.ASTRAL_GOLEM.get(),
               ESEntities.SEEKER.get(),
               ESEntities.CRETEOR.get(),
               ESEntities.TINY_CRETEOR.get(),
               ESEntities.CRYSTALLIZED_MOTH.get(),
               ESEntities.SHIMMER_LACEWING.get(),
               ESEntities.STARFIRE_BIRD.get(),
               ESEntities.GRIMSTONE_GOLEM.get(),
               ESEntities.AETHERSENT_GOLEM.get(),
               ESEntities.THE_GATEKEEPER.get(),
               ESEntities.STARLIGHT_GOLEM.get(),
               ESEntities.FREEZE.get(),
               ESEntities.PERMAFROST.get(),
               ESEntities.LUNAR_MONSTROSITY.get(),
               ESEntities.TANGLED_SKULL.get(),
               ESEntities.SOLAR_CREEPER.get()
            }
         );
   }
}
