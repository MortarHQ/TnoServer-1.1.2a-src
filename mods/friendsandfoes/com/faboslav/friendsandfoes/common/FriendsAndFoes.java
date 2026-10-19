package com.faboslav.friendsandfoes.common;

import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;
import com.faboslav.friendsandfoes.common.entity.event.IllusionerOnEntitySpawn;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntityAttributesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntitySpawnRestrictionsEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterFlammabilityEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterReloadListenerEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.SetupEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesArmorMaterials;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlockEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItemGroups;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesPointOfInterestTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesPotions;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesRecipes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureProcessorTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesVillagerProfessions;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.faboslav.friendsandfoes.common.network.packet.EntityAnimationsSyncPacket;
import com.faboslav.friendsandfoes.common.network.packet.MoobloomVariantsSyncPacket;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FriendsAndFoes {
   private static final Logger LOGGER = LoggerFactory.getLogger("friendsandfoes");
   private static final FriendsAndFoesConfig CONFIG = new FriendsAndFoesConfig();
   public static final String MOD_ID = "friendsandfoes";

   public static ResourceLocation makeID(String path) {
      return ResourceLocation.fromNamespaceAndPath("friendsandfoes", path);
   }

   public static String makeStringID(String name) {
      return "friendsandfoes:" + name;
   }

   public static ResourceLocation makeNamespacedId(String id) {
      return ResourceLocation.tryParse(id);
   }

   public static FriendsAndFoesConfig getConfig() {
      return (FriendsAndFoesConfig)FriendsAndFoesConfig.HANDLER.instance();
   }

   public static Logger getLogger() {
      return LOGGER;
   }

   public static void init() {
      FriendsAndFoesTags.init();
      getConfig().load();
      ModChecker.setupModCompat();
      RegisterReloadListenerEvent.EVENT.addListener(FriendsAndFoes::registerServerDataListeners);
      SetupEvent.EVENT.addListener(FriendsAndFoes::setup);
      DatapackSyncEvent.EVENT.addListener(MoobloomVariantsSyncPacket::sendToClient);
      DatapackSyncEvent.EVENT.addListener(EntityAnimationsSyncPacket::sendToClient);
      RegisterFlammabilityEvent.EVENT.addListener(FriendsAndFoesBlocks::registerFlammablity);
      RegisterEntityAttributesEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntityAttributes);
      RegisterEntitySpawnRestrictionsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntitySpawnRestrictions);
      AddSpawnBiomeModificationsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::addSpawnBiomeModifications);
      RegisterBrewingRecipesEvent.EVENT.addListener(FriendsAndFoesRecipes::registerBrewingRecipes);
      RegisterVillagerTradesEvent.EVENT.addListener(FriendsAndFoesVillagerProfessions::registerVillagerTrades);
      AddItemGroupEntriesEvent.EVENT.addListener(FriendsAndFoesItemGroups::addItemGroupEntries);
      EntitySpawnEvent.EVENT.addListener(IllusionerOnEntitySpawn::handleEntitySpawn);
      FriendsAndFoesActivities.ACTIVITIES.init();
      FriendsAndFoesArmorMaterials.ARMOR_MATERIALS.init();
      FriendsAndFoesBlocks.BLOCKS.init();
      FriendsAndFoesCriterias.CRITERIAS.init();
      FriendsAndFoesEntityDataSerializers.init();
      FriendsAndFoesEntityTypes.ENTITY_TYPES.init();
      FriendsAndFoesItemGroups.ITEM_GROUPS.init();
      FriendsAndFoesItems.ITEMS.init();
      FriendsAndFoesMemoryModuleTypes.MEMORY_MODULE_TYPES.init();
      FriendsAndFoesParticleTypes.PARTICLE_TYPES.init();
      FriendsAndFoesPointOfInterestTypes.POINT_OF_INTEREST_TYPES.init();
      FriendsAndFoesPotions.POTIONS.init();
      FriendsAndFoesSensorTypes.SENSOR_TYPES.init();
      FriendsAndFoesSoundEvents.SOUND_EVENTS.init();
      FriendsAndFoesStatusEffects.STATUS_EFFECTS.init();
      FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.init();
      FriendsAndFoesStructureProcessorTypes.init();
      FriendsAndFoesStructureTypes.STRUCTURE_TYPES.init();
      FriendsAndFoesVillagerProfessions.VILLAGER_PROFESSIONS.init();
   }

   public static void lateInit() {
      FriendsAndFoesBlockEntityTypes.lateInit();
      FriendsAndFoesItems.registerCompostableItems();
      PlatformHooks.BIOME_MODIFICATIONS.addButtercupFeature();
   }

   private static void registerServerDataListeners(RegisterReloadListenerEvent event) {
      event.register(makeID("moobloom_variants"), MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER);
      event.register(makeID("entity_animations"), AnimationLoader.INSTANCE);
   }

   private static void setup(SetupEvent event) {
      MessageHandler.init();
   }
}
