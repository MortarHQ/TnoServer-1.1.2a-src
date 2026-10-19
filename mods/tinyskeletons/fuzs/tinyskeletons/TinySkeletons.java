package fuzs.tinyskeletons;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import fuzs.tinyskeletons.init.ModRegistry;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TinySkeletons implements ModConstructor {
   public static final String MOD_ID = "tinyskeletons";
   public static final String MOD_NAME = "Tiny Skeletons";
   public static final Logger LOGGER = LoggerFactory.getLogger("Tiny Skeletons");

   public void onConstructMod() {
      ModRegistry.touch();
      registerEventHandlers();
   }

   private static void registerEventHandlers() {
      PlayerInteractEvents.USE_ENTITY_AT.register(BabyConversionHandler::onEntityInteract);
      ServerEntityLevelEvents.SPAWN.register(BabyConversionHandler::onEntitySpawn);
   }

   public void onCommonSetup() {
      BabyConversionHandler.registerConversion(EntityType.SKELETON, (EntityType<? extends Mob>)ModRegistry.BABY_SKELETON_ENTITY_TYPE.value());
      BabyConversionHandler.registerConversion(EntityType.WITHER_SKELETON, (EntityType<? extends Mob>)ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value());
      BabyConversionHandler.registerConversion(EntityType.STRAY, (EntityType<? extends Mob>)ModRegistry.BABY_STRAY_ENTITY_TYPE.value());
      BabyConversionHandler.registerConversion(EntityType.BOGGED, (EntityType<? extends Mob>)ModRegistry.BABY_BOGGED_ENTITY_TYPE.value());
   }

   public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
      context.registerSpawnPlacement(
         (EntityType)ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules
      );
      context.registerSpawnPlacement(
         (EntityType)ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules
      );
      context.registerSpawnPlacement(
         (EntityType)ModRegistry.BABY_STRAY_ENTITY_TYPE.value(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         BabyStray::checkBabyStraySpawnRules
      );
      context.registerSpawnPlacement(
         (EntityType)ModRegistry.BABY_BOGGED_ENTITY_TYPE.value(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules
      );
   }

   public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
      context.registerEntityAttributes(
         (EntityType)ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(),
         Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3)
      );
      context.registerEntityAttributes(
         (EntityType)ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
         Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3)
      );
      context.registerEntityAttributes(
         (EntityType)ModRegistry.BABY_STRAY_ENTITY_TYPE.value(),
         Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3)
      );
      context.registerEntityAttributes(
         (EntityType)ModRegistry.BABY_BOGGED_ENTITY_TYPE.value(),
         Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3)
      );
   }

   public static ResourceLocation id(String path) {
      return ResourceLocationHelper.fromNamespaceAndPath("tinyskeletons", path);
   }
}
