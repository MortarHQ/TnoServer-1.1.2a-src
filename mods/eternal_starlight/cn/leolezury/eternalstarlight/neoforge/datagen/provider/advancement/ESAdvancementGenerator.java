package cn.leolezury.eternalstarlight.neoforge.datagen.provider.advancement;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.critereon.WitnessWeatherTrigger;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;

public class ESAdvancementGenerator implements AdvancementGenerator {
   public void generate(Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
      RegistryLookup<Biome> biomes = registries.lookupOrThrow(Registries.BIOME);
      RegistryLookup<Structure> structures = registries.lookupOrThrow(Registries.STRUCTURE);
      RegistryLookup<Fluid> fluids = registries.lookupOrThrow(Registries.FLUID);
      AdvancementHolder root = Builder.advancement()
         .display(
            (ItemLike)ESBlocks.LUNAR_LOG.get(),
            Component.translatable("advancements.eternal_starlight.root.title"),
            Component.translatable("advancements.eternal_starlight.root.description"),
            EternalStarlight.id("textures/block/lunar_log.png"),
            AdvancementType.TASK,
            false,
            false,
            false
         )
         .addCriterion(
            "in_dim", TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY))
         )
         .save(consumer, "eternal_starlight:root");
      AdvancementHolder challengeGatekeeper = Builder.advancement()
         .parent(root)
         .display(
            Items.DIAMOND_SWORD,
            Component.translatable("advancements.eternal_starlight.challenge_gatekeeper.title"),
            Component.translatable("advancements.eternal_starlight.challenge_gatekeeper.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("challenged", ((PlayerTrigger)ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:challenge_gatekeeper");
      AdvancementHolder obtainOrbOfProphecy = addItemObtain(consumer, challengeGatekeeper, "obtain_orb_of_prophecy", ESItems.ORB_OF_PROPHECY.get());
      AdvancementHolder enterDim = Builder.advancement()
         .parent(obtainOrbOfProphecy)
         .display(
            (ItemLike)ESBlocks.LUNAR_LOG.get(),
            Component.translatable("advancements.eternal_starlight.enter_starlight.title"),
            Component.translatable("advancements.eternal_starlight.enter_starlight.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "in_dim", TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inDimension(ESDimensions.STARLIGHT_KEY))
         )
         .save(consumer, "eternal_starlight:enter_starlight");
      AdvancementHolder seekingEye = addItemObtain(consumer, enterDim, "obtain_seeking_eye", ESItems.SEEKING_EYE.get());
      AdvancementHolder scythe = addItemObtain(consumer, enterDim, "obtain_scythe", ESTags.Items.SCYTHES, ESItems.PETAL_SCYTHE.get());
      AdvancementHolder enterAbyss = addInBiome(consumer, enterDim, "enter_abyss", ESItems.ABYSSLATE.get(), biomes.getOrThrow(ESBiomes.THE_ABYSS));
      AdvancementHolder redVelvetumossFlower = addItemObtain(consumer, enterAbyss, "obtain_red_velvetumoss_flower", ESItems.RED_VELVETUMOSS_FLOWER.get());
      AdvancementHolder underPermafrostForest = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESBlocks.ICICLE.get(),
            Component.translatable("advancements.eternal_starlight.under_permafrost_forest.title"),
            Component.translatable("advancements.eternal_starlight.under_permafrost_forest.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "in_biome",
            TriggerInstance.located(
               net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                  .setBiomes(biomes.getOrThrow(ESTags.Biomes.PERMAFROST))
                  .setY(Doubles.atMost(-5.0))
            )
         )
         .save(consumer, "eternal_starlight:under_permafrost_forest");
      AdvancementHolder glaciteShard = addItemObtain(consumer, underPermafrostForest, "obtain_glacite_shard", ESItems.GLACITE_SHARD.get());
      AdvancementHolder glaciteArrow = addItemObtain(consumer, glaciteShard, "obtain_glacite_arrow", ESItems.GLACITE_ARROW.get());
      AdvancementHolder frozenBomb = addItemObtain(consumer, underPermafrostForest, "obtain_frozen_bomb", ESItems.FROZEN_BOMB.get());
      AdvancementHolder auroraDeerAntler = addItemObtain(consumer, underPermafrostForest, "obtain_aurora_deer_antler", ESItems.AURORA_DEER_ANTLER.get());
      AdvancementHolder enterCrystallizedDesert = addInBiome(
         consumer, enterDim, "enter_crystallized_desert", ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get(), biomes.getOrThrow(ESBiomes.CRYSTALLIZED_DESERT)
      );
      AdvancementHolder crinoaSeeds = addItemObtain(consumer, enterCrystallizedDesert, "obtain_crinoa_seeds", ESItems.CRINOA_SEEDS.get());
      AdvancementHolder toothOfHunger = addItemObtain(consumer, enterCrystallizedDesert, "obtain_tooth_of_hunger", ESItems.TOOTH_OF_HUNGER.get());
      AdvancementHolder daggerOfHunger = addItemObtain(consumer, toothOfHunger, "obtain_dagger_of_hunger", ESItems.DAGGER_OF_HUNGER.get());
      AdvancementHolder saturateDaggerOfHunger = Builder.advancement()
         .parent(daggerOfHunger)
         .display(
            (ItemLike)ESItems.DAGGER_OF_HUNGER.get(),
            Component.translatable("advancements.eternal_starlight.saturate_dagger_of_hunger.title"),
            Component.translatable("advancements.eternal_starlight.saturate_dagger_of_hunger.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("saturate", ((PlayerTrigger)ESCriteriaTriggers.SATURATE_DAGGER_OF_HUNGER.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:saturate_dagger_of_hunger");
      AdvancementHolder toothOfHungerBlocks = addItemObtain(
         consumer, toothOfHunger, "obtain_tooth_of_hunger_blocks", ESTags.Items.TOOTH_OF_HUNGER_BLOCKS, ESItems.CHISELED_TOOTH_OF_HUNGER_TILES.get()
      );
      AdvancementHolder crystalbornCatalyst = addItemObtain(consumer, toothOfHunger, "obtain_crystalborn_catalyst", ESItems.CRYSTALBORN_CATALYST.get());
      AdvancementHolder summonGrimstoneGolem = addEntitySummon(
         consumer, enterCrystallizedDesert, "summon_grimstone_golem", ESEntities.GRIMSTONE_GOLEM.get(), ESItems.GRIMSTONE_BRICKS.get()
      );
      AdvancementHolder throwGleechEgg = Builder.advancement()
         .parent(enterCrystallizedDesert)
         .display(
            (ItemLike)ESItems.GLEECH_EGG.get(),
            Component.translatable("advancements.eternal_starlight.throw_gleech_egg.title"),
            Component.translatable("advancements.eternal_starlight.throw_gleech_egg.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("thrown", ((PlayerTrigger)ESCriteriaTriggers.THROW_GLEECH_EGG.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:throw_gleech_egg");
      AdvancementHolder tameCrystallizedMoth = Builder.advancement()
         .parent(enterCrystallizedDesert)
         .display(
            (ItemLike)ESItems.SHIVERING_GEL.get(),
            Component.translatable("advancements.eternal_starlight.tame_crystallized_moth.title"),
            Component.translatable("advancements.eternal_starlight.tame_crystallized_moth.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "tame",
            net.minecraft.advancements.critereon.TameAnimalTrigger.TriggerInstance.tamedAnimal(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(ESEntities.CRYSTALLIZED_MOTH.get())
            )
         )
         .save(consumer, "eternal_starlight:tame_crystallized_moth");
      AdvancementHolder inEtherFluid = addInFluid(consumer, enterDim, "in_ether_fluid", ESItems.ETHER_BUCKET.get(), fluids.getOrThrow(ESTags.Fluids.ETHER));
      AdvancementHolder forgottenNocturnalMillet = addItemObtain(
         consumer, inEtherFluid, "obtain_forgotten_nocturnal_millet", ESItems.FORGOTTEN_NOCTURNAL_MILLET.get()
      );
      AdvancementHolder thioquartzShard = addItemObtain(consumer, inEtherFluid, "obtain_thioquartz_shard", ESItems.THIOQUARTZ_SHARD.get());
      AdvancementHolder alchemistMask = addItemObtain(consumer, thioquartzShard, "obtain_alchemist_mask", ESItems.ALCHEMIST_MASK.get());
      AdvancementHolder witnessMeteorShower = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESItems.RAW_AETHERSENT.get(),
            Component.translatable("advancements.eternal_starlight.witness_meteor_shower.title"),
            Component.translatable("advancements.eternal_starlight.witness_meteor_shower.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "witnessed",
            ((WitnessWeatherTrigger)ESCriteriaTriggers.WITNESS_WEATHER.get())
               .createCriterion(new WitnessWeatherTrigger.TriggerInstance(Optional.empty(), ESWeathers.METEOR_SHOWER.asHolder()))
         )
         .save(consumer, "eternal_starlight:witness_meteor_shower");
      AdvancementHolder aethersentIngot = addItemObtain(consumer, witnessMeteorShower, "obtain_aethersent_ingot", ESItems.AETHERSENT_INGOT.get());
      AdvancementHolder summonAethersentGolem = addEntitySummon(
         consumer, aethersentIngot, "summon_aethersent_golem", ESEntities.AETHERSENT_GOLEM.get(), ESItems.STARFALL_LONGBOW.get()
      );
      AdvancementHolder killCreteor = addEntityKill(consumer, witnessMeteorShower, "kill_creteor", ESEntities.CRETEOR.get(), ESItems.CRETEOR_HIDE.get());
      AdvancementHolder aetherstrikeRocket = addItemObtain(consumer, killCreteor, "obtain_aetherstrike_rocket", ESItems.AETHERSTRIKE_ROCKET.get());
      AdvancementHolder deepsilverIngot = addItemObtain(consumer, enterDim, "obtain_deepsilver_ingot", ESItems.DEEPSILVER_INGOT.get());
      AdvancementHolder numbnessEffect = Builder.advancement()
         .parent(deepsilverIngot)
         .display(
            (ItemLike)ESItems.SILVER_PUNGENCY_FRUIT.get(),
            Component.translatable("advancements.eternal_starlight.obtain_numbness_effect.title"),
            Component.translatable("advancements.eternal_starlight.obtain_numbness_effect.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "obtain",
            net.minecraft.advancements.critereon.EffectsChangedTrigger.TriggerInstance.hasEffects(
               net.minecraft.advancements.critereon.MobEffectsPredicate.Builder.effects().and(ESMobEffects.NUMBNESS.asHolder())
            )
         )
         .save(consumer, "eternal_starlight:obtain_numbness_effect");
      AdvancementHolder starlitDiamond = addItemObtain(consumer, enterDim, "obtain_starlit_diamond", ESItems.STARLIT_DIAMOND.get());
      AdvancementHolder igniteTearBomb = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESItems.TEAR_BOMB.get(),
            Component.translatable("advancements.eternal_starlight.ignite_tear_bomb.title"),
            Component.translatable("advancements.eternal_starlight.ignite_tear_bomb.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("ignite", ((PlayerTrigger)ESCriteriaTriggers.IGNITE_TEAR_BOMB.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:ignite_tear_bomb");
      AdvancementHolder witnessStranghoulHunt = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESItems.MALARITE_SWORD.get(),
            Component.translatable("advancements.eternal_starlight.witness_stranghoul_hunt.title"),
            Component.translatable("advancements.eternal_starlight.witness_stranghoul_hunt.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("witness", ((PlayerTrigger)ESCriteriaTriggers.WITNESS_STRANGHOUL_HUNT.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:witness_stranghoul_hunt");
      AdvancementHolder hireStranghoul = Builder.advancement()
         .parent(witnessStranghoulHunt)
         .display(
            (ItemLike)ESItems.PUNGENCY_STEW.get(),
            Component.translatable("advancements.eternal_starlight.hire_stranghoul.title"),
            Component.translatable("advancements.eternal_starlight.hire_stranghoul.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("hire", ((PlayerTrigger)ESCriteriaTriggers.HIRE_STRANGHOUL.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:hire_stranghoul");
      AdvancementHolder hammerCriticalHit = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESItems.STARFIRE_HAMMER.get(),
            Component.translatable("advancements.eternal_starlight.hammer_critical_hit.title"),
            Component.translatable("advancements.eternal_starlight.hammer_critical_hit.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("critical_hit", ((PlayerTrigger)ESCriteriaTriggers.HAMMER_CRITICAL_HIT.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:hammer_critical_hit");
      AdvancementHolder putSeedsIntoStarfireBirdNest = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESItems.STARFIRE_BIRD_NEST.get(),
            Component.translatable("advancements.eternal_starlight.put_seeds_into_starfire_bird_nest.title"),
            Component.translatable("advancements.eternal_starlight.put_seeds_into_starfire_bird_nest.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "put_seeds", ((PlayerTrigger)ESCriteriaTriggers.PUT_SEEDS_INTO_STARFIRE_BIRD_NEST.get()).createCriterion(new TriggerInstance(Optional.empty()))
         )
         .save(consumer, "eternal_starlight:put_seeds_into_starfire_bird_nest");
      AdvancementHolder starfire = addItemObtain(consumer, putSeedsIntoStarfireBirdNest, "obtain_starfire", ESItems.STARFIRE.get());
      AdvancementHolder flowglaze = addItemObtain(consumer, starfire, "obtain_flowglaze", ESItems.FLOWGLAZE.get());
      AdvancementHolder thermalSpringstone = addItemObtain(consumer, enterDim, "obtain_thermal_springstone", ESItems.THERMAL_SPRINGSTONE.get());
      AdvancementHolder rawAmaramber = addItemObtain(consumer, enterDim, "obtain_raw_amaramber", ESItems.RAW_AMARAMBER.get());
      AdvancementHolder stellagmite = addItemObtain(consumer, enterDim, "obtain_stellagmite", ESItems.STELLAGMITE.get());
      AdvancementHolder bouldershroomStew = addItemObtain(consumer, enterDim, "obtain_bouldershroom_stew", ESItems.BOULDERSHROOM_STEW.get());
      AdvancementHolder dustedShard = addItemObtain(consumer, enterDim, "obtain_dusted_shard", ESItems.DUSTED_SHARD.get());
      AdvancementHolder jinglestemSandwich = addItemObtain(consumer, enterDim, "obtain_jinglestem_sandwich", ESItems.JINGLESTEM_SANDWICH.get());
      Builder allStarlightBiomesBuilder = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESBlocks.NIGHTFALL_GRASS_BLOCK.get(),
            Component.translatable("advancements.eternal_starlight.all_starlight_biomes.title"),
            Component.translatable("advancements.eternal_starlight.all_starlight_biomes.description"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            false
         )
         .requirements(Strategy.AND)
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(500));

      for (ResourceKey<Biome> key : biomes.listElementIds().sorted(ResourceKey::compareTo).toList()) {
         if (key.location().getNamespace().equals("eternal_starlight")) {
            allStarlightBiomesBuilder.addCriterion(
               "in_" + key.location().getPath(),
               TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inBiome(biomes.getOrThrow(key)))
            );
         }
      }

      AdvancementHolder allStarlightBiomes = allStarlightBiomesBuilder.save(consumer, "eternal_starlight:all_starlight_biomes");
      AdvancementHolder enterGolemForge = addInStructure(
         consumer, seekingEye, "enter_golem_forge", ESItems.GOLEM_STEEL_PILLAR.get(), structures.getOrThrow(ESStructures.GOLEM_FORGE)
      );
      AdvancementHolder killPermafrost = addEntityKill(consumer, enterGolemForge, "kill_permafrost", ESEntities.PERMAFROST.get(), ESItems.COLDSNAP.get());
      AdvancementHolder freezeStarlightGolem = Builder.advancement()
         .parent(enterGolemForge)
         .display(
            (ItemLike)ESItems.FROZEN_TUBE.get(),
            Component.translatable("advancements.eternal_starlight.freeze_starlight_golem.title"),
            Component.translatable("advancements.eternal_starlight.freeze_starlight_golem.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("freeze", ((PlayerTrigger)ESCriteriaTriggers.FREEZE_STARLIGHT_GOLEM.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:freeze_starlight_golem");
      AdvancementHolder deactivateEnergyBlock = Builder.advancement()
         .parent(freezeStarlightGolem)
         .display(
            (ItemLike)ESItems.ENERGY_BLOCK.get(),
            Component.translatable("advancements.eternal_starlight.deactivate_energy_block.title"),
            Component.translatable("advancements.eternal_starlight.deactivate_energy_block.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("deactivate", ((PlayerTrigger)ESCriteriaTriggers.DEACTIVATE_ENERGY_BLOCK.get()).createCriterion(new TriggerInstance(Optional.empty())))
         .save(consumer, "eternal_starlight:deactivate_energy_block");
      AdvancementHolder killGolem = addEntityKill(
         consumer, deactivateEnergyBlock, "kill_golem", ESEntities.STARLIGHT_GOLEM.get(), ESItems.CHISELED_GOLEM_STEEL_BLOCK.get()
      );
      AdvancementHolder golemSteelIngot = addItemObtain(consumer, killGolem, "obtain_golem_steel_ingot", ESItems.GOLEM_STEEL_INGOT.get());
      AdvancementHolder igniteLunarMonstrosity = Builder.advancement()
         .parent(killGolem)
         .display(
            (ItemLike)ESItems.SALTPETER_MATCHBOX.get(),
            Component.translatable("advancements.eternal_starlight.ignite_lunar_monstrosity.title"),
            Component.translatable("advancements.eternal_starlight.ignite_lunar_monstrosity.description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "ignite",
            net.minecraft.advancements.critereon.PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ESTags.Items.LUNAR_MONSTROSITY_IGNITERS),
               Optional.of(
                  EntityPredicate.wrap(net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(ESEntities.LUNAR_MONSTROSITY.get()).build())
               )
            )
         )
         .save(consumer, "eternal_starlight:ignite_lunar_monstrosity");
      AdvancementHolder killLunarMonstrosity = addEntityKill(
         consumer, igniteLunarMonstrosity, "kill_lunar_monstrosity", ESEntities.LUNAR_MONSTROSITY.get(), ESItems.PARASOL_GRASS.get()
      );
      AdvancementHolder crescentSpear = addItemObtain(consumer, killLunarMonstrosity, "obtain_crescent_spear", ESItems.CRESCENT_SPEAR.get());
      AdvancementHolder chainTangledSkullExplosion = Builder.advancement()
         .parent(killLunarMonstrosity)
         .display(
            (ItemLike)ESItems.TANGLED_SKULL.get(),
            Component.translatable("advancements.eternal_starlight.chain_tangled_skull_explosion.title"),
            Component.translatable("advancements.eternal_starlight.chain_tangled_skull_explosion.description"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            false
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(60))
         .addCriterion(
            "explode", ((PlayerTrigger)ESCriteriaTriggers.CHAIN_TANGLED_SKULL_EXPLOSION.get()).createCriterion(new TriggerInstance(Optional.empty()))
         )
         .save(consumer, "eternal_starlight:chain_tangled_skull_explosion");
      Builder.advancement()
         .parent(killLunarMonstrosity)
         .display(
            (ItemLike)ESItems.UNREALIUM_HELMET.get(),
            Component.translatable("advancements.eternal_starlight.full_unrealium_armor.title"),
            Component.translatable("advancements.eternal_starlight.full_unrealium_armor.description"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            false
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
         .addCriterion(
            "has_helmet",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)ESItems.UNREALIUM_HELMET.get()})
         )
         .addCriterion(
            "has_chestplate",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)ESItems.UNREALIUM_CHESTPLATE.get()})
         )
         .addCriterion(
            "has_leggings",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)ESItems.UNREALIUM_LEGGINGS.get()})
         )
         .addCriterion(
            "has_boots",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)ESItems.UNREALIUM_BOOTS.get()})
         )
         .save(consumer, "eternal_starlight:full_unrealium_armor");
      AdvancementHolder useBlossomOfStars = Builder.advancement()
         .parent(enterDim)
         .display(
            (ItemLike)ESItems.BLOSSOM_OF_STARS.get(),
            Component.translatable("advancements.eternal_starlight.use_blossom_of_stars.title"),
            Component.translatable("advancements.eternal_starlight.use_blossom_of_stars.description"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion("use_item", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem((ItemLike)ESItems.BLOSSOM_OF_STARS.get()))
         .save(consumer, "eternal_starlight:use_blossom_of_stars");
   }

   private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item item) {
      return Builder.advancement()
         .parent(parent)
         .display(
            item,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("has_item", net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{item}))
         .save(consumer, "eternal_starlight:" + id);
   }

   private static AdvancementHolder addItemObtain(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, TagKey<Item> tag, Item item) {
      return Builder.advancement()
         .parent(parent)
         .display(
            item,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "has_item",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(
               new net.minecraft.advancements.critereon.ItemPredicate.Builder[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(tag)}
            )
         )
         .save(consumer, "eternal_starlight:" + id);
   }

   private static AdvancementHolder addEntityKill(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityType<?> entity, Item item) {
      return addEntityKill(consumer, parent, id, net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(entity).build(), item);
   }

   private static AdvancementHolder addEntityKill(
      Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityPredicate predicate, Item item
   ) {
      return Builder.advancement()
         .parent(parent)
         .display(
            item,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("kill", net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(Optional.ofNullable(predicate)))
         .save(consumer, "eternal_starlight:" + id);
   }

   private static AdvancementHolder addEntitySummon(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, EntityType<?> entity, Item item) {
      return addEntitySummon(consumer, parent, id, net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(entity), item);
   }

   private static AdvancementHolder addEntitySummon(
      Consumer<AdvancementHolder> consumer,
      AdvancementHolder parent,
      String id,
      net.minecraft.advancements.critereon.EntityPredicate.Builder predicate,
      Item item
   ) {
      return Builder.advancement()
         .parent(parent)
         .display(
            item,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("summon", net.minecraft.advancements.critereon.SummonedEntityTrigger.TriggerInstance.summonedEntity(predicate))
         .save(consumer, "eternal_starlight:" + id);
   }

   private static AdvancementHolder addInBiome(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, Holder<Biome> biome) {
      return Builder.advancement()
         .parent(parent)
         .display(
            display,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("in_biome", TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inBiome(biome)))
         .save(consumer, "eternal_starlight:" + id);
   }

   private static AdvancementHolder addInStructure(
      Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, Holder<Structure> structure
   ) {
      return Builder.advancement()
         .parent(parent)
         .display(
            display,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("in_structure", TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structure)))
         .save(consumer, "eternal_starlight:" + id);
   }

   private static AdvancementHolder addInFluid(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, String id, Item display, HolderSet<Fluid> fluids) {
      return Builder.advancement()
         .parent(parent)
         .display(
            display,
            Component.translatable("advancements.eternal_starlight." + id + ".title"),
            Component.translatable("advancements.eternal_starlight." + id + ".description"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "in_fluid",
            TriggerInstance.located(
               net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                  .setFluid(net.minecraft.advancements.critereon.FluidPredicate.Builder.fluid().of(fluids))
            )
         )
         .save(consumer, "eternal_starlight:" + id);
   }
}
