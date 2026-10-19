package twilightforest.data;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate.Type;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;
import net.neoforged.neoforge.common.util.Lazy;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.AddModifierTrigger;
import twilightforest.advancements.DrinkFromFlaskTrigger;
import twilightforest.advancements.HurtBossTrigger;
import twilightforest.advancements.KillBugTrigger;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.advancements.UncraftItemTrigger;
import twilightforest.advancements.predicate.ItemColorPredicate;
import twilightforest.beanification.Autowired;
import twilightforest.block.Experiment115Block;
import twilightforest.block.GhastTrapBlock;
import twilightforest.components.item.PotionFlaskComponent;
import twilightforest.data.helpers.AdvancementDataMultiRequirements;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFDimension;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItemSubPredicates;
import twilightforest.init.TFItems;
import twilightforest.init.TFStats;
import twilightforest.init.TFStructures;

public class TFAdvancementGenerator implements AdvancementGenerator {
   private static final Supplier<EntityType<?>[]> TF_KILLABLE = Lazy.of(
      () -> new EntityType[]{
         (EntityType)TFEntities.ADHERENT.get(),
         (EntityType)TFEntities.ARMORED_GIANT.get(),
         (EntityType)TFEntities.BIGHORN_SHEEP.get(),
         (EntityType)TFEntities.BLOCKCHAIN_GOBLIN.get(),
         (EntityType)TFEntities.DWARF_RABBIT.get(),
         (EntityType)TFEntities.DEATH_TOME.get(),
         (EntityType)TFEntities.DEER.get(),
         (EntityType)TFEntities.FIRE_BEETLE.get(),
         (EntityType)TFEntities.GIANT_MINER.get(),
         (EntityType)TFEntities.LOWER_GOBLIN_KNIGHT.get(),
         (EntityType)TFEntities.UPPER_GOBLIN_KNIGHT.get(),
         (EntityType)TFEntities.HARBINGER_CUBE.get(),
         (EntityType)TFEntities.HEDGE_SPIDER.get(),
         (EntityType)TFEntities.HELMET_CRAB.get(),
         (EntityType)TFEntities.HOSTILE_WOLF.get(),
         (EntityType)TFEntities.HYDRA.get(),
         (EntityType)TFEntities.KING_SPIDER.get(),
         (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
         (EntityType)TFEntities.KOBOLD.get(),
         (EntityType)TFEntities.LICH.get(),
         (EntityType)TFEntities.LICH_MINION.get(),
         (EntityType)TFEntities.MAZE_SLIME.get(),
         (EntityType)TFEntities.CARMINITE_GHASTLING.get(),
         (EntityType)TFEntities.MINOSHROOM.get(),
         (EntityType)TFEntities.MINOTAUR.get(),
         (EntityType)TFEntities.MIST_WOLF.get(),
         (EntityType)TFEntities.MOSQUITO_SWARM.get(),
         (EntityType)TFEntities.NAGA.get(),
         (EntityType)TFEntities.PENGUIN.get(),
         (EntityType)TFEntities.PINCH_BEETLE.get(),
         (EntityType)TFEntities.PLATEAU_BOSS.get(),
         (EntityType)TFEntities.QUEST_RAM.get(),
         (EntityType)TFEntities.RAVEN.get(),
         (EntityType)TFEntities.REDCAP.get(),
         (EntityType)TFEntities.REDCAP_SAPPER.get(),
         (EntityType)TFEntities.SKELETON_DRUID.get(),
         (EntityType)TFEntities.SLIME_BEETLE.get(),
         (EntityType)TFEntities.SNOW_GUARDIAN.get(),
         (EntityType)TFEntities.SNOW_QUEEN.get(),
         (EntityType)TFEntities.SQUIRREL.get(),
         (EntityType)TFEntities.STABLE_ICE_CORE.get(),
         (EntityType)TFEntities.SWARM_SPIDER.get(),
         (EntityType)TFEntities.TINY_BIRD.get(),
         (EntityType)TFEntities.CARMINITE_BROODLING.get(),
         (EntityType)TFEntities.CARMINITE_GHASTGUARD.get(),
         (EntityType)TFEntities.CARMINITE_GOLEM.get(),
         (EntityType)TFEntities.TOWERWOOD_BORER.get(),
         (EntityType)TFEntities.TROLL.get(),
         (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
         (EntityType)TFEntities.UR_GHAST.get(),
         (EntityType)TFEntities.BOAR.get(),
         (EntityType)TFEntities.WINTER_WOLF.get(),
         (EntityType)TFEntities.WRAITH.get(),
         (EntityType)TFEntities.YETI.get(),
         (EntityType)TFEntities.ALPHA_YETI.get()
      }
   );
   private static final Supplier<ItemLike[]> DENDROLOGIST_BLOCKS = Lazy.of(
      () -> new ItemLike[]{
         (ItemLike)TFBlocks.TWILIGHT_OAK_LOG.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_LEAVES.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_SAPLING.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_PLANKS.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_SLAB.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_STAIRS.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_BUTTON.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_FENCE.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_GATE.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_PLATE.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_DOOR.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_TRAPDOOR.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_SIGN.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_CHEST.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_BANISTER.get(),
         (ItemLike)TFBlocks.TWILIGHT_OAK_DRYING_RACK.get(),
         (ItemLike)TFItems.TWILIGHT_OAK_BOAT.get(),
         (ItemLike)TFItems.TWILIGHT_OAK_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.CANOPY_LOG.get(),
         (ItemLike)TFBlocks.CANOPY_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_CANOPY_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_CANOPY_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.CANOPY_LEAVES.get(),
         (ItemLike)TFBlocks.CANOPY_SAPLING.get(),
         (ItemLike)TFBlocks.CANOPY_PLANKS.get(),
         (ItemLike)TFBlocks.CANOPY_SLAB.get(),
         (ItemLike)TFBlocks.CANOPY_STAIRS.get(),
         (ItemLike)TFBlocks.CANOPY_BUTTON.get(),
         (ItemLike)TFBlocks.CANOPY_FENCE.get(),
         (ItemLike)TFBlocks.CANOPY_GATE.get(),
         (ItemLike)TFBlocks.CANOPY_PLATE.get(),
         (ItemLike)TFBlocks.CANOPY_DOOR.get(),
         (ItemLike)TFBlocks.CANOPY_TRAPDOOR.get(),
         (ItemLike)TFBlocks.CANOPY_SIGN.get(),
         (ItemLike)TFBlocks.CANOPY_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.CANOPY_CHEST.get(),
         (ItemLike)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.CANOPY_BANISTER.get(),
         (ItemLike)TFBlocks.CANOPY_DRYING_RACK.get(),
         (ItemLike)TFBlocks.CANOPY_BOOKSHELF.get(),
         (ItemLike)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get(),
         (ItemLike)TFItems.CANOPY_BOAT.get(),
         (ItemLike)TFItems.CANOPY_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.MANGROVE_LOG.get(),
         (ItemLike)TFBlocks.MANGROVE_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_MANGROVE_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_MANGROVE_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.MANGROVE_LEAVES.get(),
         (ItemLike)TFBlocks.MANGROVE_SAPLING.get(),
         (ItemLike)TFBlocks.MANGROVE_PLANKS.get(),
         (ItemLike)TFBlocks.MANGROVE_SLAB.get(),
         (ItemLike)TFBlocks.MANGROVE_STAIRS.get(),
         (ItemLike)TFBlocks.MANGROVE_BUTTON.get(),
         (ItemLike)TFBlocks.MANGROVE_FENCE.get(),
         (ItemLike)TFBlocks.MANGROVE_GATE.get(),
         (ItemLike)TFBlocks.MANGROVE_PLATE.get(),
         (ItemLike)TFBlocks.MANGROVE_DOOR.get(),
         (ItemLike)TFBlocks.MANGROVE_TRAPDOOR.get(),
         (ItemLike)TFBlocks.MANGROVE_SIGN.get(),
         (ItemLike)TFBlocks.MANGROVE_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.MANGROVE_CHEST.get(),
         (ItemLike)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.MANGROVE_BANISTER.get(),
         (ItemLike)TFBlocks.MANGROVE_DRYING_RACK.get(),
         (ItemLike)TFItems.MANGROVE_BOAT.get(),
         (ItemLike)TFItems.MANGROVE_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.DARK_LOG.get(),
         (ItemLike)TFBlocks.DARK_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_DARK_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_DARK_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.DARK_LEAVES.get(),
         (ItemLike)TFBlocks.DARKWOOD_SAPLING.get(),
         (ItemLike)TFBlocks.DARK_PLANKS.get(),
         (ItemLike)TFBlocks.DARK_SLAB.get(),
         (ItemLike)TFBlocks.DARK_STAIRS.get(),
         (ItemLike)TFBlocks.DARK_BUTTON.get(),
         (ItemLike)TFBlocks.DARK_FENCE.get(),
         (ItemLike)TFBlocks.DARK_GATE.get(),
         (ItemLike)TFBlocks.DARK_PLATE.get(),
         (ItemLike)TFBlocks.DARK_DOOR.get(),
         (ItemLike)TFBlocks.DARK_TRAPDOOR.get(),
         (ItemLike)TFBlocks.DARK_SIGN.get(),
         (ItemLike)TFBlocks.DARK_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.DARK_CHEST.get(),
         (ItemLike)TFBlocks.DARK_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.DARK_BANISTER.get(),
         (ItemLike)TFBlocks.DARK_DRYING_RACK.get(),
         (ItemLike)TFItems.DARK_BOAT.get(),
         (ItemLike)TFItems.DARK_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.TIME_LOG.get(),
         (ItemLike)TFBlocks.TIME_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_TIME_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_TIME_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.TIME_LEAVES.get(),
         (ItemLike)TFBlocks.TIME_SAPLING.get(),
         (ItemLike)TFBlocks.TIME_PLANKS.get(),
         (ItemLike)TFBlocks.TIME_SLAB.get(),
         (ItemLike)TFBlocks.TIME_STAIRS.get(),
         (ItemLike)TFBlocks.TIME_BUTTON.get(),
         (ItemLike)TFBlocks.TIME_FENCE.get(),
         (ItemLike)TFBlocks.TIME_GATE.get(),
         (ItemLike)TFBlocks.TIME_PLATE.get(),
         (ItemLike)TFBlocks.TIME_DOOR.get(),
         (ItemLike)TFBlocks.TIME_TRAPDOOR.get(),
         (ItemLike)TFBlocks.TIME_SIGN.get(),
         (ItemLike)TFBlocks.TIME_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.TIME_CHEST.get(),
         (ItemLike)TFBlocks.TIME_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.TIME_BANISTER.get(),
         (ItemLike)TFBlocks.TIME_DRYING_RACK.get(),
         (ItemLike)TFItems.TIME_BOAT.get(),
         (ItemLike)TFItems.TIME_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_LOG.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_LEAVES.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_SAPLING.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_PLANKS.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_SLAB.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_STAIRS.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_BUTTON.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_FENCE.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_GATE.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_PLATE.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_DOOR.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_TRAPDOOR.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_SIGN.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_CHEST.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_BANISTER.get(),
         (ItemLike)TFBlocks.TRANSFORMATION_DRYING_RACK.get(),
         (ItemLike)TFItems.TRANSFORMATION_BOAT.get(),
         (ItemLike)TFItems.TRANSFORMATION_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.MINING_LOG.get(),
         (ItemLike)TFBlocks.MINING_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_MINING_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_MINING_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.MINING_LEAVES.get(),
         (ItemLike)TFBlocks.MINING_SAPLING.get(),
         (ItemLike)TFBlocks.MINING_PLANKS.get(),
         (ItemLike)TFBlocks.MINING_SLAB.get(),
         (ItemLike)TFBlocks.MINING_STAIRS.get(),
         (ItemLike)TFBlocks.MINING_BUTTON.get(),
         (ItemLike)TFBlocks.MINING_FENCE.get(),
         (ItemLike)TFBlocks.MINING_GATE.get(),
         (ItemLike)TFBlocks.MINING_PLATE.get(),
         (ItemLike)TFBlocks.MINING_DOOR.get(),
         (ItemLike)TFBlocks.MINING_TRAPDOOR.get(),
         (ItemLike)TFBlocks.MINING_SIGN.get(),
         (ItemLike)TFBlocks.MINING_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.MINING_CHEST.get(),
         (ItemLike)TFBlocks.MINING_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.MINING_BANISTER.get(),
         (ItemLike)TFBlocks.MINING_DRYING_RACK.get(),
         (ItemLike)TFItems.MINING_BOAT.get(),
         (ItemLike)TFItems.MINING_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.SORTING_LOG.get(),
         (ItemLike)TFBlocks.SORTING_WOOD.get(),
         (ItemLike)TFBlocks.STRIPPED_SORTING_LOG.get(),
         (ItemLike)TFBlocks.STRIPPED_SORTING_WOOD.get(),
         (ItemLike)TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get(),
         (ItemLike)TFBlocks.SORTING_LEAVES.get(),
         (ItemLike)TFBlocks.SORTING_SAPLING.get(),
         (ItemLike)TFBlocks.SORTING_PLANKS.get(),
         (ItemLike)TFBlocks.SORTING_SLAB.get(),
         (ItemLike)TFBlocks.SORTING_STAIRS.get(),
         (ItemLike)TFBlocks.SORTING_BUTTON.get(),
         (ItemLike)TFBlocks.SORTING_FENCE.get(),
         (ItemLike)TFBlocks.SORTING_GATE.get(),
         (ItemLike)TFBlocks.SORTING_PLATE.get(),
         (ItemLike)TFBlocks.SORTING_DOOR.get(),
         (ItemLike)TFBlocks.SORTING_TRAPDOOR.get(),
         (ItemLike)TFBlocks.SORTING_SIGN.get(),
         (ItemLike)TFBlocks.SORTING_HANGING_SIGN.get(),
         (ItemLike)TFBlocks.SORTING_CHEST.get(),
         (ItemLike)TFBlocks.SORTING_TRAPPED_CHEST.get(),
         (ItemLike)TFBlocks.SORTING_BANISTER.get(),
         (ItemLike)TFBlocks.SORTING_DRYING_RACK.get(),
         (ItemLike)TFItems.SORTING_BOAT.get(),
         (ItemLike)TFItems.SORTING_CHEST_BOAT.get(),
         (ItemLike)TFBlocks.TOWERWOOD.get(),
         (ItemLike)TFBlocks.CRACKED_TOWERWOOD.get(),
         (ItemLike)TFBlocks.MOSSY_TOWERWOOD.get(),
         (ItemLike)TFBlocks.ENCASED_TOWERWOOD.get(),
         (ItemLike)TFBlocks.ROOT_BLOCK.get(),
         (ItemLike)TFBlocks.ROOT_STRAND.get(),
         (ItemLike)TFBlocks.LIVEROOT_BLOCK.get(),
         (ItemLike)TFItems.LIVEROOT.get(),
         (ItemLike)TFBlocks.HOLLOW_OAK_SAPLING.get(),
         (ItemLike)TFBlocks.RAINBOW_OAK_SAPLING.get(),
         (ItemLike)TFBlocks.RAINBOW_OAK_LEAVES.get(),
         (ItemLike)TFBlocks.GIANT_LOG.get(),
         (ItemLike)TFBlocks.GIANT_LEAVES.get(),
         (ItemLike)TFBlocks.HUGE_STALK.get(),
         (ItemLike)TFBlocks.BEANSTALK_LEAVES.get(),
         (ItemLike)TFBlocks.THORN_LEAVES.get(),
         (ItemLike)TFBlocks.THORN_ROSE.get(),
         (ItemLike)TFBlocks.HEDGE.get(),
         (ItemLike)TFBlocks.FALLEN_LEAVES.get(),
         (ItemLike)TFBlocks.MANGROVE_ROOT.get()
      }
   );
   @Autowired
   private static AdvancementDataMultiRequirements advancementDataMultiRequirements;
   @Autowired
   private static DrinkFromFlaskTrigger.TriggerInstance.DrinkFromFlaskTriggerInstanceFactory drinkFromFlaskTriggerInstanceFactory;

   public void generate(Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
      RegistryLookup<Biome> biomes = registries.lookupOrThrow(Registries.BIOME);
      RegistryLookup<Structure> structures = registries.lookupOrThrow(Registries.STRUCTURE);
      AdvancementHolder root = Builder.advancement()
         .display(
            (ItemLike)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(),
            Component.translatable("advancement.twilightforest.root"),
            Component.translatable("advancement.twilightforest.root.desc"),
            TwilightForestMod.prefix("textures/block/mazestone_large_brick.png"),
            AdvancementType.TASK,
            true,
            false,
            false
         )
         .requirements(Strategy.OR)
         .addCriterion("in_tf", TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inDimension(TFDimension.DIMENSION_KEY)))
         .addCriterion("make_portal", SimpleAdvancementTrigger.TriggerInstance.makeTFPortal())
         .save(consumer, "twilightforest:root");
      AdvancementHolder silence = this.addTFKillable(
            Builder.advancement()
               .parent(root)
               .display(
                  (ItemLike)TFItems.RAVEN_FEATHER.get(),
                  Component.translatable("advancement.twilightforest.twilight_hunter"),
                  Component.translatable("advancement.twilightforest.twilight_hunter.desc"),
                  null,
                  AdvancementType.TASK,
                  true,
                  true,
                  false
               )
               .requirements(Strategy.OR)
         )
         .save(consumer, "twilightforest:twilight_hunter");
      AdvancementHolder naga = Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get(),
            Component.translatable("advancement.twilightforest.kill_naga"),
            Component.translatable(
               "advancement.twilightforest.kill_naga.desc",
               new Object[]{
                  Component.translatable(((EntityType)TFEntities.NAGA.get()).getDescriptionId()),
                  Component.translatable(((Item)TFItems.NAGA_SCALE.get()).getDescriptionId())
               }
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion(
            "naga",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.NAGA.get())
            )
         )
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.NAGA_TROPHY.get()})
         )
         .addCriterion(
            "scale", net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.NAGA_SCALE.get()})
         )
         .addCriterion(
            "was_in_fight",
            HurtBossTrigger.TriggerInstance.hurtBoss(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.NAGA.get())
            )
         )
         .requirements(Strategy.OR)
         .save(consumer, "twilightforest:progress_naga");
      AdvancementHolder lich = advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(naga)
               .display(
                  (ItemLike)TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get(),
                  Component.translatable("advancement.twilightforest.kill_lich"),
                  Component.translatable(
                     "advancement.twilightforest.kill_lich.desc", new Object[]{Component.translatable(((EntityType)TFEntities.LICH.get()).getDescriptionId())}
                  ),
                  null,
                  AdvancementType.GOAL,
                  true,
                  true,
                  false
               )
         )
         .addCriterion(
            "kill_lich",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.LICH.get())
            )
         )
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.LICH_TROPHY.get()})
         )
         .addCriterion(
            "lifedrain_scepter",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.LIFEDRAIN_SCEPTER.get()})
         )
         .addCriterion(
            "twilight_scepter",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.TWILIGHT_SCEPTER.get()})
         )
         .addCriterion(
            "zombie_scepter",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.ZOMBIE_SCEPTER.get()})
         )
         .addCriterion(
            "shield_scepter",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FORTIFICATION_SCEPTER.get()})
         )
         .addCriterion(
            "was_in_fight",
            HurtBossTrigger.TriggerInstance.hurtBoss(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.LICH.get())
            )
         )
         .and()
         .addCriterion("kill_naga", this.advancementTrigger(naga))
         .requirements()
         .save(consumer, "twilightforest:progress_lich");
      AdvancementHolder minoshroom = Builder.advancement()
         .parent(lich)
         .display(
            (ItemLike)TFBlocks.MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE.get(),
            Component.translatable("advancement.twilightforest.progress_labyrinth"),
            Component.translatable("advancement.twilightforest.progress_labyrinth.desc"),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion("meef", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem((ItemLike)TFItems.MEEF_STROGANOFF.get()))
         .addCriterion("kill_lich", this.advancementTrigger(lich))
         .requirements(Strategy.AND)
         .save(consumer, "twilightforest:progress_labyrinth");
      AdvancementHolder hydra = advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(minoshroom)
               .display(
                  (ItemLike)TFBlocks.HYDRA_TROPHY.get(),
                  Component.translatable("advancement.twilightforest.kill_hydra"),
                  Component.translatable(
                     "advancement.twilightforest.kill_hydra.desc",
                     new Object[]{Component.translatable(((EntityType)TFEntities.HYDRA.get()).getDescriptionId())}
                  ),
                  null,
                  AdvancementType.GOAL,
                  true,
                  true,
                  false
               )
         )
         .addCriterion(
            "kill_hydra",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.HYDRA.get())
            )
         )
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.HYDRA_TROPHY.get()})
         )
         .addCriterion(
            "was_in_fight",
            HurtBossTrigger.TriggerInstance.hurtBoss(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.HYDRA.get())
            )
         )
         .and()
         .addCriterion("stroganoff", this.advancementTrigger(minoshroom))
         .requirements()
         .save(consumer, "twilightforest:progress_hydra");
      AdvancementHolder trophy_pedestal = Builder.advancement()
         .parent(lich)
         .display(
            (ItemLike)TFBlocks.TROPHY_PEDESTAL.get(),
            Component.translatable("advancement.twilightforest.progress_trophy_pedestal"),
            Component.translatable("advancement.twilightforest.progress_trophy_pedestal.desc"),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion("trophy_pedestal", SimpleAdvancementTrigger.TriggerInstance.activatedPedestal())
         .addCriterion("kill_lich", this.advancementTrigger(lich))
         .requirements(Strategy.AND)
         .save(consumer, "twilightforest:progress_trophy_pedestal");
      AdvancementHolder knights = advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(trophy_pedestal)
               .display(
                  (ItemLike)TFBlocks.KNIGHT_PHANTOM_TROPHY.get(),
                  Component.translatable("advancement.twilightforest.progress_knights"),
                  Component.translatable("advancement.twilightforest.progress_knights.desc"),
                  null,
                  AdvancementType.GOAL,
                  true,
                  true,
                  false
               )
         )
         .addCriterion("all_knights", SimpleAdvancementTrigger.TriggerInstance.killAllPhantoms())
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.KNIGHT_PHANTOM_TROPHY.get()})
         )
         .and()
         .addCriterion("previous_progression", this.advancementTrigger(trophy_pedestal))
         .requirements()
         .save(consumer, "twilightforest:progress_knights");
      AdvancementHolder trap = Builder.advancement()
         .parent(knights)
         .display(
            (ItemLike)TFBlocks.GHAST_TRAP.get(),
            Component.translatable("advancement.twilightforest.ghast_trap"),
            Component.translatable(
               "advancement.twilightforest.ghast_trap.desc",
               new Object[]{
                  Component.translatable(((EntityType)TFEntities.CARMINITE_GHASTLING.get()).getDescriptionId()),
                  Component.translatable(((GhastTrapBlock)TFBlocks.GHAST_TRAP.get()).getDescriptionId()),
                  Component.translatable(((EntityType)TFEntities.UR_GHAST.get()).getDescriptionId())
               }
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("activate_ghast_trap", SimpleAdvancementTrigger.TriggerInstance.activateGhastTrap())
         .save(consumer, "twilightforest:ghast_trap");
      AdvancementHolder ur_ghast = advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(trap)
               .display(
                  (ItemLike)TFBlocks.DARK_TOWER_MINIATURE_STRUCTURE.get(),
                  Component.translatable("advancement.twilightforest.progress_ur_ghast"),
                  Component.translatable(
                     "advancement.twilightforest.progress_ur_ghast.desc",
                     new Object[]{Component.translatable(((EntityType)TFEntities.UR_GHAST.get()).getDescriptionId())}
                  ),
                  null,
                  AdvancementType.GOAL,
                  true,
                  true,
                  false
               )
         )
         .addCriterion(
            "ghast",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.UR_GHAST.get())
            )
         )
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.UR_GHAST_TROPHY.get()})
         )
         .addCriterion(
            "was_in_fight",
            HurtBossTrigger.TriggerInstance.hurtBoss(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.UR_GHAST.get())
            )
         )
         .and()
         .addCriterion("previous_progression", this.advancementTrigger(knights))
         .requirements()
         .save(consumer, "twilightforest:progress_ur_ghast");
      AdvancementHolder yeti = advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(lich)
               .display(
                  (ItemLike)TFItems.ALPHA_YETI_FUR.get(),
                  Component.translatable("advancement.twilightforest.progress_yeti"),
                  Component.translatable(
                     "advancement.twilightforest.progress_yeti.desc",
                     new Object[]{Component.translatable(((EntityType)TFEntities.ALPHA_YETI.get()).getDescriptionId())}
                  ),
                  null,
                  AdvancementType.GOAL,
                  true,
                  true,
                  false
               )
         )
         .addCriterion(
            "yeti",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.ALPHA_YETI.get())
            )
         )
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.ALPHA_YETI_TROPHY.get()})
         )
         .addCriterion(
            "fur", net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.ALPHA_YETI_FUR.get()})
         )
         .addCriterion(
            "was_in_fight",
            HurtBossTrigger.TriggerInstance.hurtBoss(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.ALPHA_YETI.get())
            )
         )
         .and()
         .addCriterion("previous_progression", this.advancementTrigger(lich))
         .requirements()
         .save(consumer, "twilightforest:progress_yeti");
      AdvancementHolder snow_queen = advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(yeti)
               .display(
                  (ItemLike)TFBlocks.SNOW_QUEEN_TROPHY.get(),
                  Component.translatable("advancement.twilightforest.progress_glacier"),
                  Component.translatable(
                     "advancement.twilightforest.progress_glacier.desc",
                     new Object[]{Component.translatable(((EntityType)TFEntities.SNOW_QUEEN.get()).getDescriptionId())}
                  ),
                  null,
                  AdvancementType.GOAL,
                  true,
                  true,
                  false
               )
         )
         .addCriterion(
            "queen",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.SNOW_QUEEN.get())
            )
         )
         .addCriterion(
            "trophy",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFBlocks.SNOW_QUEEN_TROPHY.get()})
         )
         .addCriterion(
            "was_in_fight",
            HurtBossTrigger.TriggerInstance.hurtBoss(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.SNOW_QUEEN.get())
            )
         )
         .and()
         .addCriterion("previous_progression", this.advancementTrigger(yeti))
         .requirements()
         .save(consumer, "twilightforest:progress_glacier");
      AdvancementHolder merge = Builder.advancement()
         .parent(lich)
         .display(
            (ItemLike)TFBlocks.UBEROUS_SOIL.get(),
            Component.translatable("advancement.twilightforest.progress_merge"),
            Component.translatable(
               "advancement.twilightforest.progress_merge.desc",
               new Object[]{
                  Component.translatable(((EntityType)TFEntities.HYDRA.get()).getDescriptionId()),
                  Component.translatable(((EntityType)TFEntities.UR_GHAST.get()).getDescriptionId()),
                  Component.translatable(((EntityType)TFEntities.SNOW_QUEEN.get()).getDescriptionId())
               }
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion("hydra", this.advancementTrigger(hydra))
         .addCriterion("ur_ghast", this.advancementTrigger(ur_ghast))
         .addCriterion("snow_queen", this.advancementTrigger(snow_queen))
         .save(consumer, "twilightforest:progress_merge");
      Builder.advancement()
         .parent(merge)
         .display(
            (ItemLike)TFItems.MAGIC_BEANS.get(),
            Component.translatable("advancement.twilightforest.troll"),
            Component.translatable(
               "advancement.twilightforest.troll.desc", new Object[]{Component.translatable(((EntityType)TFEntities.TROLL.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "troll",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of((EntityType)TFEntities.TROLL.get())
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structures.getOrThrow(TFStructures.TROLL_CAVE)))
            )
         )
         .save(consumer, "twilightforest:troll");
      AdvancementHolder beanstalk = Builder.advancement()
         .parent(merge)
         .display(
            (ItemLike)TFBlocks.HUGE_STALK.get(),
            Component.translatable("advancement.twilightforest.beanstalk"),
            Component.translatable(
               "advancement.twilightforest.beanstalk.desc", new Object[]{Component.translatable(((Item)TFItems.MAGIC_BEANS.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion(
            "beans", net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.MAGIC_BEANS.get()})
         )
         .addCriterion(
            "use_beans",
            net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
               net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                  .setBlock(net.minecraft.advancements.critereon.BlockPredicate.Builder.block().of(new Block[]{(Block)TFBlocks.UBEROUS_SOIL.get()})),
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{(ItemLike)TFItems.MAGIC_BEANS.get()})
            )
         )
         .save(consumer, "twilightforest:beanstalk");
      AdvancementHolder giants = Builder.advancement()
         .parent(beanstalk)
         .display(
            (ItemLike)TFItems.GIANT_PICKAXE.get(),
            Component.translatable("advancement.twilightforest.giants"),
            Component.translatable(
               "advancement.twilightforest.giants.desc",
               new Object[]{
                  Component.translatable(((EntityType)TFEntities.GIANT_MINER.get()).getDescriptionId()),
                  Component.translatable(((Item)TFItems.GIANT_PICKAXE.get()).getDescriptionId())
               }
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion(
            "giant",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)TFEntities.GIANT_MINER.get())
            )
         )
         .addCriterion(
            "pickaxe",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.MAGIC_BEANS.get()})
         )
         .save(consumer, "twilightforest:giants");
      AdvancementHolder lamp = Builder.advancement()
         .parent(giants)
         .display(
            (ItemLike)TFItems.LAMP_OF_CINDERS.get(),
            Component.translatable("advancement.twilightforest.progress_troll"),
            Component.translatable(
               "advancement.twilightforest.progress_troll.desc", new Object[]{Component.translatable(((Item)TFItems.LAMP_OF_CINDERS.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion(
            "lamp",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.LAMP_OF_CINDERS.get()})
         )
         .addCriterion("previous_progression", this.advancementTrigger(merge))
         .save(consumer, "twilightforest:progress_troll");
      Builder.advancement()
         .parent(lamp)
         .display(
            Items.STRUCTURE_VOID,
            Component.translatable("advancement.twilightforest.progression_end"),
            Component.translatable("advancement.twilightforest.progression_end.desc"),
            null,
            AdvancementType.GOAL,
            true,
            false,
            false
         )
         .addCriterion("previous_progression", this.advancementTrigger(lamp))
         .addCriterion(
            "plateau",
            TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inBiome(biomes.getOrThrow(TFBiomes.FINAL_PLATEAU)))
         )
         .save(consumer, "twilightforest:progression_end");
      Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFBlocks.QUEST_RAM_TROPHY.get(),
            Component.translatable("advancement.twilightforest.quest_ram"),
            Component.translatable(
               "advancement.twilightforest.quest_ram.desc", new Object[]{Component.translatable(((EntityType)TFEntities.QUEST_RAM.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion("quest_ram_complete", SimpleAdvancementTrigger.TriggerInstance.completeQuestRam())
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
         .save(consumer, "twilightforest:quest_ram");
      Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFBlocks.CICADA.get(),
            Component.translatable("advancement.twilightforest.kill_cicada"),
            Component.translatable("advancement.twilightforest.kill_cicada.desc"),
            null,
            AdvancementType.TASK,
            true,
            false,
            true
         )
         .addCriterion("kill_cicada", KillBugTrigger.TriggerInstance.killBug((Block)TFBlocks.CICADA.get()))
         .save(consumer, "twilightforest:kill_cicada");
      Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFBlocks.UNCRAFTING_TABLE.get(),
            Component.translatable("advancement.twilightforest.uncraft_uncrafting_table"),
            Component.translatable("advancement.twilightforest.uncraft_uncrafting_table.desc"),
            null,
            AdvancementType.TASK,
            true,
            true,
            true
         )
         .addCriterion("uncraft_table", UncraftItemTrigger.TriggerInstance.uncraftedItem((ItemLike)TFBlocks.UNCRAFTING_TABLE.get()))
         .save(consumer, "twilightforest:uncraft_uncrafting_table");
      AdvancementHolder gear = Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFItems.TRAVELLERS_BOOTS.get(),
            Component.translatable("advancement.twilightforest.craft_travellers_gear"),
            Component.translatable("advancement.twilightforest.craft_travellers_gear.desc"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "craft_goggles",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/travellers_goggles"))
         )
         .addCriterion(
            "craft_vest",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/travellers_chest"))
         )
         .addCriterion(
            "craft_wings",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/travellers_wings"))
         )
         .addCriterion(
            "craft_boots",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/travellers_boots"))
         )
         .addCriterion(
            "craft_belt",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/travellers_belt"))
         )
         .addCriterion(
            "craft_gloves",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/travellers_gloves"))
         )
         .requirements(Strategy.OR)
         .save(consumer, "twilightforest:craft_travellers_gear");
      Builder.advancement()
         .parent(gear)
         .display(
            new ItemStack(
               TFItems.TRAVELLERS_BOOTS, 1, DataComponentPatch.builder().set((DataComponentType)TFDataComponents.WATER_WALK.get(), Unit.INSTANCE).build()
            ),
            Component.translatable("advancement.twilightforest.modify_travellers_gear"),
            Component.translatable("advancement.twilightforest.modify_travellers_gear.desc"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("add_any_modifier", AddModifierTrigger.TriggerInstance.addedAnyModifier())
         .save(consumer, "twilightforest:modify_travellers_gear");
      AdvancementHolder focus = Builder.advancement()
         .parent(silence)
         .display(
            (ItemLike)TFItems.MAGIC_MAP_FOCUS.get(),
            Component.translatable("advancement.twilightforest.magic_map_focus"),
            Component.translatable(
               "advancement.twilightforest.magic_map_focus.desc",
               new Object[]{
                  Component.translatable(((Item)TFItems.MAGIC_MAP_FOCUS.get()).getDescriptionId()),
                  Component.translatable(((Item)TFItems.RAVEN_FEATHER.get()).getDescriptionId()),
                  Component.translatable(Items.GLOWSTONE_DUST.getDescriptionId()),
                  Component.translatable(((Item)TFItems.TORCHBERRIES.get()).getDescriptionId())
               }
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "focus",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.MAGIC_MAP_FOCUS.get()})
         )
         .save(consumer, "twilightforest:magic_map_focus");
      AdvancementHolder magic_map = Builder.advancement()
         .parent(focus)
         .display(
            (ItemLike)TFItems.FILLED_MAGIC_MAP.get(),
            Component.translatable("advancement.twilightforest.magic_map"),
            Component.translatable(
               "advancement.twilightforest.magic_map.desc", new Object[]{Component.translatable(((Item)TFItems.FILLED_MAGIC_MAP.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "magic_map",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FILLED_MAGIC_MAP.get()})
         )
         .save(consumer, "twilightforest:magic_map");
      AdvancementHolder maze_map = Builder.advancement()
         .parent(magic_map)
         .display(
            (ItemLike)TFItems.FILLED_MAZE_MAP.get(),
            Component.translatable("advancement.twilightforest.maze_map"),
            Component.translatable(
               "advancement.twilightforest.maze_map.desc", new Object[]{Component.translatable(((Item)TFItems.FILLED_MAZE_MAP.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.GOAL,
            true,
            true,
            false
         )
         .addCriterion("maze_map", net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("maze_map")))
         .save(consumer, "twilightforest:maze_map");
      Builder.advancement()
         .parent(maze_map)
         .display(
            (ItemLike)TFItems.FILLED_ORE_MAP.get(),
            Component.translatable("advancement.twilightforest.ore_map"),
            Component.translatable(
               "advancement.twilightforest.ore_map.desc", new Object[]{Component.translatable(((Item)TFItems.FILLED_ORE_MAP.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion("ore_map", net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("ore_map")))
         .save(consumer, "twilightforest:ore_map");
      AdvancementHolder hill1 = Builder.advancement()
         .parent(root)
         .display(
            Items.IRON_BOOTS,
            Component.translatable("advancement.twilightforest.hill1"),
            Component.translatable(
               "advancement.twilightforest.hill1.desc", new Object[]{Component.translatable(((EntityType)TFEntities.REDCAP.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "redcap",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of((EntityType)TFEntities.REDCAP.get())
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structures.getOrThrow(TFStructures.HOLLOW_HILL_SMALL)))
            )
         )
         .save(consumer, "twilightforest:hill1");
      AdvancementHolder hill2 = Builder.advancement()
         .parent(hill1)
         .display(
            (ItemLike)TFItems.IRONWOOD_PICKAXE.get(),
            Component.translatable("advancement.twilightforest.hill2"),
            Component.translatable(
               "advancement.twilightforest.hill2.desc", new Object[]{Component.translatable(((EntityType)TFEntities.REDCAP_SAPPER.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "redcap",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of((EntityType)TFEntities.REDCAP_SAPPER.get())
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structures.getOrThrow(TFStructures.HOLLOW_HILL_MEDIUM)))
            )
         )
         .save(consumer, "twilightforest:hill2");
      Builder.advancement()
         .parent(hill2)
         .display(
            Items.GLOWSTONE_DUST,
            Component.translatable("advancement.twilightforest.hill3"),
            Component.translatable(
               "advancement.twilightforest.hill3.desc", new Object[]{Component.translatable(((EntityType)TFEntities.WRAITH.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "redcap",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of((EntityType)TFEntities.WRAITH.get())
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structures.getOrThrow(TFStructures.HOLLOW_HILL_LARGE)))
            )
         )
         .save(consumer, "twilightforest:hill3");
      Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFBlocks.HEDGE.get(),
            Component.translatable("advancement.twilightforest.hedge"),
            Component.translatable("advancement.twilightforest.hedge.desc"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "hedge_spider",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of((EntityType)TFEntities.HEDGE_SPIDER.get())
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structures.getOrThrow(TFStructures.HEDGE_MAZE)))
            )
         )
         .addCriterion(
            "swarm_spider",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of((EntityType)TFEntities.SWARM_SPIDER.get())
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inStructure(structures.getOrThrow(TFStructures.HEDGE_MAZE)))
            )
         )
         .requirements(Strategy.OR)
         .save(consumer, "twilightforest:hedge");
      Builder.advancement()
         .parent(root)
         .display(
            Items.BOWL,
            Component.translatable("advancement.twilightforest.twilight_dining"),
            Component.translatable("advancement.twilightforest.twilight_dining.desc"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            false
         )
         .addCriterion(
            "venison",
            net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TFItems.RAW_VENISON, TFItems.COOKED_VENISON})
            )
         )
         .addCriterion(
            "meef",
            net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TFItems.RAW_MEEF, TFItems.COOKED_MEEF})
            )
         )
         .addCriterion("meef_stroganoff", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.MEEF_STROGANOFF))
         .addCriterion("hydra_chop", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.HYDRA_CHOP))
         .addCriterion("maze_wafer", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.MAZE_WAFER))
         .addCriterion("experiment_115", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.EXPERIMENT_115))
         .addCriterion(
            "jerky",
            net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ItemTagGenerator.FOODS_JERKY)
            )
         )
         .addCriterion(
            "berry",
            net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item()
                  .of(new ItemLike[]{TFItems.RASPBERRY, TFItems.BLACKBERRY, TFItems.BLUEBERRY, TFItems.MALOBERRY})
            )
         )
         .addCriterion(
            "slime_drop",
            net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item()
                  .of(new ItemLike[]{TFItems.GELATINOUS_SLIME_DROP, TFItems.GELATINOUS_MAZE_SLIME_DROP})
            )
         )
         .addCriterion("berry_medley", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.BERRY_MEDLEY))
         .addCriterion("moss_soup", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.MOSS_SOUP))
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(75))
         .save(consumer, "twilightforest:twilight_dinner");
      Builder.advancement()
         .parent(root)
         .display(
            (ItemLike)TFItems.CHICKEN_JERKY.get(),
            Component.translatable("advancement.twilightforest.chicken_jerky"),
            Component.empty(),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion(
            "jerky",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of(EntityType.ZOMBIE)
                  .flags(net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags().setIsBaby(true))
                  .vehicle(net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(EntityType.CHICKEN)),
               net.minecraft.advancements.critereon.DamageSourcePredicate.Builder.damageType()
                  .direct(
                     net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                        .equipment(
                           net.minecraft.advancements.critereon.EntityEquipmentPredicate.Builder.equipment()
                              .mainhand(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TFItems.CHICKEN_JERKY}))
                        )
                  )
            )
         )
         .save(consumer, "twilightforest:chicken_jerky");
      Builder.advancement()
         .parent(naga)
         .display(
            (ItemLike)TFItems.NAGA_CHESTPLATE.get(),
            Component.translatable("advancement.twilightforest.naga_armors"),
            Component.translatable(
               "advancement.twilightforest.naga_armors.desc",
               new Object[]{
                  Component.translatable(((Item)TFItems.NAGA_CHESTPLATE.get()).getDescriptionId()),
                  Component.translatable(((Item)TFItems.NAGA_LEGGINGS.get()).getDescriptionId())
               }
            ),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            false
         )
         .addCriterion(
            "chestplate",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/naga_chestplate"))
         )
         .addCriterion(
            "leggings",
            net.minecraft.advancements.critereon.RecipeCraftedTrigger.TriggerInstance.craftedItem(TwilightForestMod.prefix("equipment/naga_leggings"))
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(25))
         .save(consumer, "twilightforest:naga_armors");
      Builder.advancement()
         .parent(lich)
         .display(
            (ItemLike)TFItems.ZOMBIE_SCEPTER.get(),
            Component.translatable("advancement.twilightforest.lich_scepters"),
            Component.translatable("advancement.twilightforest.lich_scepters.desc"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion(
            "scepters",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(
               new ItemLike[]{
                  (ItemLike)TFItems.LIFEDRAIN_SCEPTER.get(),
                  (ItemLike)TFItems.TWILIGHT_SCEPTER.get(),
                  (ItemLike)TFItems.ZOMBIE_SCEPTER.get(),
                  (ItemLike)TFItems.FORTIFICATION_SCEPTER.get()
               }
            )
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
         .save(consumer, "twilightforest:lich_scepters");
      Builder.advancement()
         .parent(lich)
         .display(
            this.flaskWithHarming(),
            Component.translatable("advancement.twilightforest.full_mettle_alchemist"),
            Component.translatable("advancement.twilightforest.full_mettle_alchemist.desc"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion("drink_4_harming", drinkFromFlaskTriggerInstanceFactory.drankPotion(3, Ints.atMost(6), Potions.STRONG_HARMING))
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
         .save(consumer, "twilightforest:full_mettle_alchemist");
      Builder.advancement()
         .parent(minoshroom)
         .display(
            (ItemLike)TFItems.MAZEBREAKER_PICKAXE.get(),
            Component.translatable("advancement.twilightforest.mazebreaker"),
            Component.translatable(
               "advancement.twilightforest.mazebreaker.desc",
               new Object[]{Component.translatable(((Item)TFItems.MAZEBREAKER_PICKAXE.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            false
         )
         .addCriterion(
            "pick",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.MAZEBREAKER_PICKAXE.get()})
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(50))
         .save(consumer, "twilightforest:mazebreaker");
      Builder.advancement()
         .parent(hydra)
         .display(
            (ItemLike)TFItems.HYDRA_CHOP.get(),
            Component.translatable("advancement.twilightforest.hydra_chop"),
            Component.translatable(
               "advancement.twilightforest.hydra_chop.desc", new Object[]{Component.translatable(((EntityType)TFEntities.HYDRA.get()).getDescriptionId())}
            ),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion("hydra_chop", SimpleAdvancementTrigger.TriggerInstance.eatHydraChop())
         .save(consumer, "twilightforest:hydra_chop");
      advancementDataMultiRequirements.wrap(
            Builder.advancement()
               .parent(hydra)
               .display(
                  (ItemLike)TFItems.FIERY_SWORD.get(),
                  Component.translatable("advancement.twilightforest.fiery_set"),
                  Component.translatable("advancement.twilightforest.fiery_set.desc"),
                  null,
                  AdvancementType.CHALLENGE,
                  true,
                  true,
                  false
               )
         )
         .addCriterion(
            "fiery_pick",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FIERY_PICKAXE.get()})
         )
         .addCriterion(
            "fiery_sword",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FIERY_SWORD.get()})
         )
         .and()
         .addCriterion(
            "fiery_helmet",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FIERY_HELMET.get()})
         )
         .addCriterion(
            "fiery_chestplate",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FIERY_CHESTPLATE.get()})
         )
         .addCriterion(
            "fiery_leggings",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FIERY_LEGGINGS.get()})
         )
         .addCriterion(
            "fiery_boots",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{(ItemLike)TFItems.FIERY_BOOTS.get()})
         )
         .requirements()
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(75))
         .save(consumer, "twilightforest:fiery_set");
      AdvancementHolder e115 = Builder.advancement()
         .parent(knights)
         .display(
            (ItemLike)TFItems.EXPERIMENT_115.get(),
            Component.translatable("advancement.twilightforest.experiment_115"),
            Component.translatable("advancement.twilightforest.experiment_115.desc"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "eat_experiment_115", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem((ItemLike)TFItems.EXPERIMENT_115.get())
         )
         .save(consumer, "twilightforest:experiment_115");
      Builder.advancement()
         .parent(e115)
         .display(
            this.e115Tag("think"),
            Component.translatable("advancement.twilightforest.experiment_115_3"),
            Component.translatable("advancement.twilightforest.experiment_115_3.desc"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion(
            "eat_115_e115",
            TriggerInstance.located(
               Optional.of(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                     .subPredicate(
                        net.minecraft.advancements.critereon.PlayerPredicate.Builder.player()
                           .addStat(
                              Stats.CUSTOM, registries.lookupOrThrow(Registries.CUSTOM_STAT).getOrThrow(TFStats.E115_SLICES_EATEN.getKey()), Ints.atLeast(115)
                           )
                           .build()
                     )
                     .build()
               )
            )
         )
         .save(consumer, "twilightforest:experiment_115_115");
      Builder.advancement()
         .parent(e115)
         .display(
            this.e115Tag("full"),
            Component.translatable("advancement.twilightforest.experiment_115_2"),
            Component.translatable("advancement.twilightforest.experiment_115_2.desc"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion(
            "place_complete_e115",
            net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
               net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                  .setBlock(
                     net.minecraft.advancements.critereon.BlockPredicate.Builder.block()
                        .of(new Block[]{(Block)TFBlocks.EXPERIMENT_115.get()})
                        .setProperties(
                           net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(Experiment115Block.REGENERATE, true)
                        )
                  ),
               net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.REDSTONE})
            )
         )
         .save(consumer, "twilightforest:experiment_115_self_replenishing");
      Builder.advancement()
         .parent(yeti)
         .display(
            (ItemLike)TFItems.ARCTIC_CHESTPLATE.get(),
            Component.translatable("advancement.twilightforest.arctic_dyed"),
            Component.translatable("advancement.twilightforest.arctic_dyed.desc"),
            null,
            AdvancementType.TASK,
            true,
            true,
            false
         )
         .addCriterion(
            "helmet",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(
               new ItemPredicate[]{
                  net.minecraft.advancements.critereon.ItemPredicate.Builder.item()
                     .of(new ItemLike[]{(ItemLike)TFItems.ARCTIC_HELMET.get()})
                     .withSubPredicate((Type)TFItemSubPredicates.COLOR.get(), ItemColorPredicate.anyColor())
                     .build()
               }
            )
         )
         .addCriterion(
            "chestplate",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(
               new ItemPredicate[]{
                  net.minecraft.advancements.critereon.ItemPredicate.Builder.item()
                     .of(new ItemLike[]{(ItemLike)TFItems.ARCTIC_CHESTPLATE.get()})
                     .withSubPredicate((Type)TFItemSubPredicates.COLOR.get(), ItemColorPredicate.anyColor())
                     .build()
               }
            )
         )
         .addCriterion(
            "leggings",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(
               new ItemPredicate[]{
                  net.minecraft.advancements.critereon.ItemPredicate.Builder.item()
                     .of(new ItemLike[]{(ItemLike)TFItems.ARCTIC_LEGGINGS.get()})
                     .withSubPredicate((Type)TFItemSubPredicates.COLOR.get(), ItemColorPredicate.anyColor())
                     .build()
               }
            )
         )
         .addCriterion(
            "boots",
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(
               new ItemPredicate[]{
                  net.minecraft.advancements.critereon.ItemPredicate.Builder.item()
                     .of(new ItemLike[]{(ItemLike)TFItems.ARCTIC_BOOTS.get()})
                     .withSubPredicate((Type)TFItemSubPredicates.COLOR.get(), ItemColorPredicate.anyColor())
                     .build()
               }
            )
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(25))
         .save(consumer, "twilightforest:arctic_armor_dyed");
      Builder.advancement()
         .parent(yeti)
         .display(
            (ItemLike)TFItems.GLASS_SWORD.get(),
            Component.translatable("advancement.twilightforest.glass_sword"),
            Component.translatable("advancement.twilightforest.glass_sword.desc"),
            null,
            AdvancementType.CHALLENGE,
            true,
            true,
            true
         )
         .addCriterion("broken_sword", SimpleAdvancementTrigger.TriggerInstance.brokenSword())
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(42))
         .save(consumer, "twilightforest:break_glass_sword");
      this.addDendrologistBlock(
            Builder.advancement()
               .parent(root)
               .display(
                  (ItemLike)TFBlocks.TWILIGHT_OAK_FENCE.get(),
                  Component.translatable("advancement.twilightforest.arborist"),
                  Component.translatable("advancement.twilightforest.arborist.desc"),
                  null,
                  AdvancementType.CHALLENGE,
                  true,
                  true,
                  false
               )
               .requirements(Strategy.AND)
         )
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(1000))
         .save(consumer, "twilightforest:arborist");
   }

   private ItemStack e115Tag(String key) {
      ItemStack itemstack = new ItemStack((ItemLike)TFItems.EXPERIMENT_115.get());
      itemstack.set(TFDataComponents.EXPERIMENT_115_VARIANTS, key);
      return itemstack;
   }

   private ItemStack flaskWithHarming() {
      ItemStack itemstack = new ItemStack((ItemLike)TFItems.BRITTLE_FLASK.get());
      itemstack.set(TFDataComponents.POTION_FLASK_CONTENTS, new PotionFlaskComponent(new PotionContents(Potions.STRONG_HARMING), 4, 0, false));
      return itemstack;
   }

   private Builder addTFKillable(Builder builder) {
      for (EntityType<?> entity : TF_KILLABLE.get()) {
         builder.addCriterion(
            EntityType.getKey(entity).getPath(),
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                  .of(entity)
                  .located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inDimension(TFDimension.DIMENSION_KEY))
            )
         );
      }

      return builder;
   }

   private Builder addDendrologistBlock(Builder builder) {
      for (ItemLike dendrologistBlock : DENDROLOGIST_BLOCKS.get()) {
         builder.addCriterion(
            BuiltInRegistries.ITEM.getKey(dendrologistBlock.asItem()).getPath(),
            net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{dendrologistBlock})
         );
      }

      return builder;
   }

   private Criterion<TriggerInstance> advancementTrigger(AdvancementHolder advancement) {
      return this.advancementTrigger(advancement.id().getPath());
   }

   private Criterion<TriggerInstance> advancementTrigger(String name) {
      return CriteriaTriggers.TICK
         .createCriterion(
            new TriggerInstance(
               Optional.of(
                  ContextAwarePredicate.create(
                     new LootItemCondition[]{
                        LootItemEntityPropertyCondition.hasProperties(
                              EntityTarget.THIS,
                              net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                                 .subPredicate(
                                    net.minecraft.advancements.critereon.PlayerPredicate.Builder.player()
                                       .checkAdvancementDone(TwilightForestMod.prefix(name), true)
                                       .build()
                                 )
                           )
                           .build()
                     }
                  )
               )
            )
         );
   }
}
