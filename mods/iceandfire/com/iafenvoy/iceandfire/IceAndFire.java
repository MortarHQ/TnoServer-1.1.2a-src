package com.iafenvoy.iceandfire;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.data.DragonColor;
import com.iafenvoy.iceandfire.data.IafSkullType;
import com.iafenvoy.iceandfire.data.SeaSerpentType;
import com.iafenvoy.iceandfire.data.TrollType;
import com.iafenvoy.iceandfire.event.ServerEvents;
import com.iafenvoy.iceandfire.network.ServerNetworkHelper;
import com.iafenvoy.iceandfire.registry.IafArmorMaterials;
import com.iafenvoy.iceandfire.registry.IafBestiaryPages;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafDragonColors;
import com.iafenvoy.iceandfire.registry.IafDragonTypes;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafFeatures;
import com.iafenvoy.iceandfire.registry.IafHippogryphTypes;
import com.iafenvoy.iceandfire.registry.IafItemGroups;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafLoots;
import com.iafenvoy.iceandfire.registry.IafParticles;
import com.iafenvoy.iceandfire.registry.IafProcessors;
import com.iafenvoy.iceandfire.registry.IafRecipeSerializers;
import com.iafenvoy.iceandfire.registry.IafRecipes;
import com.iafenvoy.iceandfire.registry.IafScreenHandlers;
import com.iafenvoy.iceandfire.registry.IafSeaSerpentTypes;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.IafStatusEffects;
import com.iafenvoy.iceandfire.registry.IafStructurePieces;
import com.iafenvoy.iceandfire.registry.IafStructureTypes;
import com.iafenvoy.iceandfire.registry.IafToolMaterials;
import com.iafenvoy.iceandfire.registry.IafTrades;
import com.iafenvoy.iceandfire.registry.IafTrollTypes;
import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.ServerConfigManager.PermissionChecker;
import com.iafenvoy.uranus.event.EntityEvents;
import com.iafenvoy.uranus.event.LivingEntityEvents;
import com.iafenvoy.uranus.event.PlayerEvents;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IceAndFire {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final String MOD_ID = "iceandfire";
   public static final String MOD_NAME = "Ice And Fire";
   public static final String VERSION = Platform.getMod("iceandfire").getVersion();

   public static ResourceLocation id(String path) {
      return path.contains(":") ? ResourceLocation.tryParse(path) : ResourceLocation.fromNamespaceAndPath("iceandfire", path);
   }

   public static void init() {
      ConfigManager.getInstance().registerConfigHandler(IafCommonConfig.INSTANCE);
      ServerConfigManager.registerServerConfig(IafCommonConfig.INSTANCE, PermissionChecker.IS_OPERATOR);
      IafBestiaryPages.init();
      IafDragonColors.init();
      IafDragonTypes.init();
      IafHippogryphTypes.init();
      IafSeaSerpentTypes.init();
      IafTrollTypes.init();
      DragonColor.initArmors();
      SeaSerpentType.initArmors();
      IafSkullType.initItems();
      TrollType.initArmors();
      IafArmorMaterials.REGISTRY.register();
      IafSounds.REGISTRY.register();
      IafBlocks.REGISTRY.register();
      IafBlockEntities.REGISTRY.register();
      IafDataComponents.REGISTRY.register();
      IafEntities.REGISTRY.register();
      IafItemGroups.REGISTRY.register();
      IafItems.REGISTRY.register();
      IafLoots.REGISTRY.register();
      IafRecipes.REGISTRY.register();
      IafRecipeSerializers.REGISTRY.register();
      IafParticles.REGISTRY.register();
      IafProcessors.REGISTRY.register();
      IafFeatures.REGISTRY.register();
      IafScreenHandlers.REGISTRY.register();
      IafStatusEffects.REGISTRY.register();
      IafStructurePieces.REGISTRY.register();
      IafStructureTypes.REGISTRY.register();
      IafTrades.POI_REGISTRY.register();
      IafTrades.PROFESSION_REGISTRY.register();
      IafEntities.registerAttributes();
   }

   public static void process() {
      IafEntities.init();
      IafTrades.init();
      IafRecipes.init();
      IafFeatures.init();
      IafToolMaterials.init();
      BlockEvent.BREAK.register(ServerEvents::onBreakBlock);
      InteractionEvent.INTERACT_ENTITY.register(ServerEvents::onEntityInteract);
      InteractionEvent.RIGHT_CLICK_BLOCK.register(ServerEvents::onPlayerRightClick);
      EntityEvent.LIVING_DEATH.register(ServerEvents::onEntityDie);
      PlayerEvent.ATTACK_ENTITY.register(ServerEvents::onPlayerAttack);
      PlayerEvents.LOGGED_OUT.register(ServerEvents::onPlayerLeaveEvent);
      EntityEvents.ON_JOIN_WORLD.register(ServerEvents::onEntityJoinWorld);
      EntityEvents.START_TRACKING_TAIL.register(ServerEvents::onLivingSetTarget);
      LivingEntityEvents.DAMAGE.register(ServerEvents::onEntityDamage);
      EntityEvent.LIVING_HURT.register(ServerEvents::onLivingHurt);
      ServerNetworkHelper.registerReceivers();
   }
}
