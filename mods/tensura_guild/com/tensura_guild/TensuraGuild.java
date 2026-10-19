package com.tensura_guild;

import com.tensura_guild.client.GuildClientEvents;
import com.tensura_guild.command.GuildCommands;
import com.tensura_guild.compat.GuildCompatHooks;
import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.data.DwarfTaskDefinitions;
import com.tensura_guild.data.DwarfTaskService;
import com.tensura_guild.data.GuildPartyService;
import com.tensura_guild.data.GuildReputationShopDefinitions;
import com.tensura_guild.data.GuildTaskDefinitions;
import com.tensura_guild.dialogue.GuildDialogueManager;
import com.tensura_guild.entity.LaplaceEntity;
import com.tensura_guild.entity.YuukiKagurazakaEntity;
import com.tensura_guild.init.GuildStructures;
import com.tensura_guild.network.GuildNetwork;
import com.tensura_guild.registry.GuildAttachments;
import com.tensura_guild.registry.GuildBlockEntities;
import com.tensura_guild.registry.GuildBlocks;
import com.tensura_guild.registry.GuildEntityTypes;
import com.tensura_guild.registry.GuildItems;
import com.tensura_guild.registry.GuildProcessors;
import com.tensura_guild.registry.GuildStructureTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("tensura_guild")
public class TensuraGuild {
   public static final String MOD_ID = "tensura_guild";
   public static final Logger LOGGER = LoggerFactory.getLogger("tensura_guild");
   public static final String YUUKI_BOSS_BAR_KEY = "bossbar.tensura_guild.yuuki_kagurazaka";
   public static final String LAPLACE_BOSS_BAR_KEY = "bossbar.tensura_guild.laplace";

   public TensuraGuild(IEventBus modBus, ModContainer modContainer) {
      GuildGameRules.register();
      modContainer.registerConfig(Type.COMMON, GuildConfig.SPEC);
      GuildEntityTypes.ENTITY_TYPES.register(modBus);
      GuildBlocks.BLOCKS.register(modBus);
      GuildItems.ITEMS.register(modBus);
      GuildBlockEntities.BLOCK_ENTITIES.register(modBus);
      GuildAttachments.ATTACHMENTS.register(modBus);
      GuildStructureTypes.init(modBus);
      GuildProcessors.init(modBus);
      GuildStructures.register();
      GuildCompatHooks.init();
      modBus.addListener(this::registerAttributes);
      modBus.addListener(this::addToCreativeTabs);
      modBus.addListener(GuildNetwork::onRegister);
      NeoForge.EVENT_BUS.addListener(this::onAddReloadListeners);
      NeoForge.EVENT_BUS.addListener(GuildCommands::onRegister);
      NeoForge.EVENT_BUS.addListener(GuildPlayerEvents::onPlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(GuildPartyService::onPlayerLoggedOut);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, GuildPartyFriendlyFireEvents::onIncomingDamage);
      NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, GuildPlayerEvents::onPlayerNameFormat);
      NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, GuildPlayerEvents::onPlayerTabListNameFormat);
      NeoForge.EVENT_BUS.addListener(GuildTaskEvents::onLivingDeath);
      NeoForge.EVENT_BUS.addListener(GuildTaskEvents::onPlayerTick);
      NeoForge.EVENT_BUS.addListener(GuildTaskEvents::onServerTick);
      NeoForge.EVENT_BUS.addListener(GuildCharacterResetEvents::onUseItemStop);
      NeoForge.EVENT_BUS.addListener(DwarfTaskService::onEntityInteract);
      NeoForge.EVENT_BUS.addListener(DwarfTaskService::onEntityJoinLevel);
      NeoForge.EVENT_BUS.addListener(GuildYuukiAggroEvents::onEntityTick);
      if (FMLEnvironment.dist == Dist.CLIENT) {
         modBus.addListener(GuildClientEvents::onRegisterRenderers);
         NeoForge.EVENT_BUS.addListener(GuildClientEvents::onScreenRenderPost);
         NeoForge.EVENT_BUS.addListener(GuildClientEvents::onMouseButtonPressedPre);
         NeoForge.EVENT_BUS.addListener(GuildClientEvents::onRenderGuiPre);
         NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, GuildClientEvents::onRenderNameTag);
      }
   }

   private void onAddReloadListeners(AddReloadListenerEvent event) {
      event.addListener(new GuildDialogueManager());
      event.addListener(new GuildTaskDefinitions());
      event.addListener(new DwarfTaskDefinitions());
      event.addListener(new GuildReputationShopDefinitions());
   }

   private void registerAttributes(EntityAttributeCreationEvent event) {
      event.put(GuildEntityTypes.YUUKI_KAGURAZAKA.get(), YuukiKagurazakaEntity.setAttributes().build());
      event.put(GuildEntityTypes.LAPLACE.get(), LaplaceEntity.setAttributes().build());
   }

   private void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
         event.accept((ItemLike)GuildItems.YUUKI_KAGURAZAKA_SPAWN_EGG.get());
         event.accept((ItemLike)GuildItems.LAPLACE_SPAWN_EGG.get());
         event.accept((ItemLike)GuildItems.YUUKI_KAGURAZAKA_SPAWNER.get());
      }

      if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
         event.accept((ItemLike)GuildItems.ADVENTURER_GUILD_MAP.get());
      }
   }
}
