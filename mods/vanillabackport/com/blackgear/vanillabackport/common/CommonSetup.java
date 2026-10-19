package com.blackgear.vanillabackport.common;

import com.blackgear.platform.common.data.LootModifier;
import com.blackgear.platform.common.integration.BlockIntegration;
import com.blackgear.platform.common.integration.MobIntegration;
import com.blackgear.platform.common.integration.TradeIntegration;
import com.blackgear.platform.common.integration.BlockIntegration.Event;
import com.blackgear.platform.common.worldgen.modifier.BiomeManager;
import com.blackgear.platform.core.ParallelDispatch;
import com.blackgear.platform.core.events.ResourceReloadManager;
import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.common.api.interactions.GhastHarnessInteraction;
import com.blackgear.vanillabackport.common.api.leash.LeashIntegration;
import com.blackgear.vanillabackport.common.level.dispenser.PaleOakBoatDispenseBehavior;
import com.blackgear.vanillabackport.common.level.entities.creaking.Creaking;
import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import com.blackgear.vanillabackport.common.level.entities.sulfurcube.SulfurCube;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModEntities;
import com.blackgear.vanillabackport.common.registries.ModEntityTypes;
import com.blackgear.vanillabackport.common.registries.ModItems;
import com.blackgear.vanillabackport.common.resource.SulfurCubeArchetypeReloadListener;
import com.blackgear.vanillabackport.common.resource.sound.WolfSoundVariantReloadListener;
import com.blackgear.vanillabackport.common.resource.variant.CatVariantReloadListener;
import com.blackgear.vanillabackport.common.resource.variant.ChickenVariantReloadListener;
import com.blackgear.vanillabackport.common.resource.variant.CowVariantReloadListener;
import com.blackgear.vanillabackport.common.resource.variant.FrogVariantReloadListener;
import com.blackgear.vanillabackport.common.resource.variant.PigVariantReloadListener;
import com.blackgear.vanillabackport.common.resource.variant.WolfVariantReloadListener;
import com.blackgear.vanillabackport.common.worldgen.WorldGeneration;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class CommonSetup {
   public static void setup() {
      ResourceReloadManager.registerServer(event -> {
         event.register(VanillaBackport.resource("wolf_sound_variants"), WolfSoundVariantReloadListener.INSTANCE);
         event.register(VanillaBackport.resource("cow_variants"), new CowVariantReloadListener());
         event.register(VanillaBackport.resource("chicken_variants"), new ChickenVariantReloadListener());
         event.register(VanillaBackport.resource("pig_variants"), new PigVariantReloadListener());
         event.register(VanillaBackport.resource("wolf_variants"), new WolfVariantReloadListener());
         event.register(VanillaBackport.resource("frog_variants"), new FrogVariantReloadListener());
         event.register(VanillaBackport.resource("cat_variants"), new CatVariantReloadListener());
         event.register(VanillaBackport.resource("sulfur_cube_archetypes"), new SulfurCubeArchetypeReloadListener());
      });
      MobIntegration.registerIntegrations(CommonSetup::mobIntegrations);
   }

   public static void asyncSetup(ParallelDispatch dispatch) {
      dispatch.enqueueWork(() -> {
         BiomeManager.add(WorldGeneration::bootstrap);
         BlockIntegration.registerIntegrations(CommonSetup::blockIntegrations);
         TradeIntegration.registerVillagerTrades(CommonSetup::tradeIntegrations);
         Parrot.MOB_SOUND_MAP.put(ModEntities.CREAKING.get(), ModSoundEvents.PARROT_IMITATE_CREAKING.get());
      });
      LootModifier.modify(new LootIntegrations());
   }

   public static void blockIntegrations(Event event) {
      event.registerFuelItem((ItemLike)ModBlocks.SHORT_DRY_GRASS.get(), 100);
      event.registerFuelItem((ItemLike)ModBlocks.TALL_DRY_GRASS.get(), 100);
      event.registerFuelItem((ItemLike)ModBlocks.LEAF_LITTER.get(), 100);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_PLANKS.get(), 5, 20);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_SLAB.get(), 5, 20);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_FENCE_GATE.get(), 5, 20);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_FENCE.get(), 5, 20);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_STAIRS.get(), 5, 20);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_LOG.get(), 5, 5);
      event.registerFlammableBlock(ModBlocks.STRIPPED_PALE_OAK_LOG.get(), 5, 5);
      event.registerFlammableBlock(ModBlocks.STRIPPED_PALE_OAK_WOOD.get(), 5, 5);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_WOOD.get(), 5, 5);
      event.registerFlammableBlock(ModBlocks.PALE_OAK_LEAVES.get(), 30, 60);
      event.registerFlammableBlock(ModBlocks.PALE_MOSS_BLOCK.get(), 5, 100);
      event.registerFlammableBlock(ModBlocks.PALE_MOSS_CARPET.get(), 5, 100);
      event.registerFlammableBlock(ModBlocks.PALE_HANGING_MOSS.get(), 5, 100);
      event.registerFlammableBlock(ModBlocks.OPEN_EYEBLOSSOM.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.CLOSED_EYEBLOSSOM.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.BUSH.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.FIREFLY_BUSH.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.WILDFLOWERS.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.CACTUS_FLOWER.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.SHORT_DRY_GRASS.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.TALL_DRY_GRASS.get(), 60, 100);
      event.registerFlammableBlock(ModBlocks.LEAF_LITTER.get(), 60, 100);
      event.registerCompostableItem((ItemLike)ModBlocks.PALE_OAK_LEAVES.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.PALE_OAK_SAPLING.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.PALE_MOSS_CARPET.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.PALE_HANGING_MOSS.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.PALE_MOSS_BLOCK.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.BUSH.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.LEAF_LITTER.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.FIREFLY_BUSH.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.WILDFLOWERS.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.CACTUS_FLOWER.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.SHORT_DRY_GRASS.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.TALL_DRY_GRASS.get(), 0.3F);
      event.registerCompostableItem((ItemLike)ModBlocks.OPEN_EYEBLOSSOM.get(), 0.65F);
      event.registerCompostableItem((ItemLike)ModBlocks.CLOSED_EYEBLOSSOM.get(), 0.65F);
      event.registerStrippableBlock(ModBlocks.PALE_OAK_LOG.get(), ModBlocks.STRIPPED_PALE_OAK_LOG.get());
      event.registerStrippableBlock(ModBlocks.PALE_OAK_WOOD.get(), ModBlocks.STRIPPED_PALE_OAK_WOOD.get());
      event.registerDispenserBehavior((ItemLike)ModItems.PALE_OAK_BOAT.get(), new PaleOakBoatDispenseBehavior());
      event.registerDispenserBehavior((ItemLike)ModItems.PALE_OAK_CHEST_BOAT.get(), new PaleOakBoatDispenseBehavior(true));
      event.registerDispenserBehavior((ItemLike)ModItems.BLUE_EGG.get(), new ProjectileDispenseBehavior(ModItems.BLUE_EGG.get()));
      event.registerDispenserBehavior((ItemLike)ModItems.BROWN_EGG.get(), new ProjectileDispenseBehavior(ModItems.BROWN_EGG.get()));
   }

   public static void tradeIntegrations(com.blackgear.platform.common.integration.TradeIntegration.Event event) {
      if ((Boolean)VanillaBackport.COMMON_CONFIG.hasPaleTrades.get()) {
         event.registerWandererTrade(true, new ItemListing[]{new ItemsForEmeralds(ModBlocks.PALE_OAK_LOG.get(), 1, 8, 4, 1)});
         event.registerWandererTrade(
            false,
            new ItemListing[]{
               new ItemsForEmeralds(ModBlocks.OPEN_EYEBLOSSOM.get(), 1, 1, 7, 1),
               new ItemsForEmeralds(ModBlocks.PALE_OAK_SAPLING.get(), 5, 1, 8, 1),
               new ItemsForEmeralds(ModBlocks.PALE_HANGING_MOSS.get(), 1, 3, 4, 1),
               new ItemsForEmeralds(ModBlocks.PALE_MOSS_BLOCK.get(), 1, 2, 5, 1)
            }
         );
      }

      if ((Boolean)VanillaBackport.COMMON_CONFIG.hasSpringTrades.get()) {
         event.registerWandererTrade(
            false,
            new ItemListing[]{
               new ItemsForEmeralds(ModBlocks.WILDFLOWERS.get(), 1, 1, 12, 1),
               new ItemsForEmeralds(ModBlocks.TALL_DRY_GRASS.get(), 1, 1, 12, 1),
               new ItemsForEmeralds(ModBlocks.FIREFLY_BUSH.get(), 3, 1, 12, 1)
            }
         );
      }

      if ((Boolean)VanillaBackport.COMMON_CONFIG.doMerchantTradeChaosCubedContents.get()) {
         event.registerWandererTrade(false, new ItemListing[]{new ItemsForEmeralds(ModBlocks.SULFUR_SPIKE.get(), 1, 2, 5, 1)});
      }
   }

   public static void mobIntegrations(com.blackgear.platform.common.integration.MobIntegration.Event event) {
      event.registerMobInteraction(new GhastHarnessInteraction());
      event.registerMobInteraction(new LeashIntegration());
      event.registerPlacement(
         () -> EntityType.CAMEL,
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (type, level, reason, pos, random) -> level.getBlockState(pos.below()).is(ModBlockTags.CAMELS_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8
      );
      event.registerPlacement(
         () -> ModEntityTypes.SULFUR_CUBE, SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SulfurCube::checkSulfurCubeSpawnRules
      );
      event.registerAttributes(ModEntities.CREAKING, Creaking::createAttributes);
      event.registerAttributes(ModEntities.HAPPY_GHAST, HappyGhast::createAttributes);
      event.registerAttributes(() -> ModEntityTypes.SULFUR_CUBE, SulfurCube::createSulfurCubeAttributes);
      event.registerGoal(EntityType.VINDICATOR, 1, mob -> new AvoidEntityGoal((PathfinderMob)mob, Creaking.class, 8.0F, 0.6, 1.2));
      event.registerGoal(EntityType.PILLAGER, 1, mob -> new AvoidEntityGoal((PathfinderMob)mob, Creaking.class, 8.0F, 0.6, 1.2));
      event.registerGoal(EntityType.ILLUSIONER, 3, mob -> new AvoidEntityGoal((PathfinderMob)mob, Creaking.class, 8.0F, 0.6, 1.2));
      event.registerGoal(EntityType.EVOKER, 3, mob -> new AvoidEntityGoal((PathfinderMob)mob, Creaking.class, 8.0F, 0.6, 1.2));
   }
}
