package com.tno_tensura_extras;

import com.tno_tensura_extras.client.ExtrasClientEvents;
import com.tno_tensura_extras.client.LooterClientEvents;
import com.tno_tensura_extras.client.MagicCameraClientExtensions;
import com.tno_tensura_extras.client.MagicCameraView;
import com.tno_tensura_extras.client.TimeTravelClient;
import com.tno_tensura_extras.config.ExtrasConfig;
import com.tno_tensura_extras.event.AlignmentFieldEvents;
import com.tno_tensura_extras.event.DayNightInversionEvents;
import com.tno_tensura_extras.event.DiabloCombatEvents;
import com.tno_tensura_extras.event.DiabloSpawnEvents;
import com.tno_tensura_extras.event.HighQualityHoneyEvents;
import com.tno_tensura_extras.network.LooterPackets;
import com.tno_tensura_extras.registry.ExtrasAttachments;
import com.tno_tensura_extras.registry.ExtrasBlocks;
import com.tno_tensura_extras.registry.ExtrasDataComponents;
import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import com.tno_tensura_extras.registry.ExtrasItems;
import com.tno_tensura_extras.registry.ExtrasMobEffects;
import com.tno_tensura_extras.registry.ExtrasSkills;
import com.tno_tensura_extras.registry.ExtrasSounds;
import com.tno_tensura_extras.skill.unique.SeducerSkill;
import com.tno_tensura_extras.util.LooterManager;
import com.tno_tensura_extras.util.SeducerManager;
import com.tno_tensura_extras.util.TimeTravelManager;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("tno_tensura_extras")
public class TensuraExtras {
   public static final String MOD_ID = "tno_tensura_extras";
   public static final Logger LOGGER = LoggerFactory.getLogger("tno_tensura_extras");
   private static final ResourceKey<CreativeModeTab> TENSURA_CONSUMABLES_TAB = ResourceKey.create(
      Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("tensura", "05-consumables")
   );
   private static final ResourceKey<CreativeModeTab> TENSURA_MISC_TAB = ResourceKey.create(
      Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("tensura", "08-miscellaneous")
   );
   private static final ResourceKey<CreativeModeTab> TENSURA_SPAWN_EGGS_TAB = ResourceKey.create(
      Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("tensura", "09-spawn_eggs")
   );

   public TensuraExtras(IEventBus modBus) {
      ExtrasAttachments.ATTACHMENT_TYPES.register(modBus);
      ExtrasDataComponents.DATA_COMPONENTS.register(modBus);
      ExtrasItems.ITEMS.register(modBus);
      ExtrasBlocks.BLOCKS.register(modBus);
      ExtrasBlocks.BLOCK_ENTITY_TYPES.register(modBus);
      ExtrasEntityTypes.ENTITY_TYPES.register(modBus);
      ExtrasMobEffects.MOB_EFFECTS.register(modBus);
      ExtrasSounds.SOUND_EVENTS.register(modBus);
      ExtrasSkills.init();
      ExtrasConfig.addUniqueSkillsToReincarnationPool();
      LooterPackets.registerC2S();
      LooterPackets.registerS2C();
      TensuraEntityEvents.SPIRITUAL_HURT_EVENT.register(SeducerManager::onSpiritualHurt);
      TensuraEntityEvents.FORCE_TAME_EVENT.register(SeducerManager::onForceTame);
      TensuraEntityEvents.LIVING_POST_DAMAGE.register(SeducerSkill::onPostDamage);
      modBus.addListener(this::addToCreativeTabs);
      modBus.addListener(DiabloSpawnEvents::onCreateAttributes);
      NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, DiabloSpawnEvents::onFinalizeSpawn);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, DiabloCombatEvents::onIncomingDamage);
      NeoForge.EVENT_BUS.addListener(HighQualityHoneyEvents::onEntityInteract);
      NeoForge.EVENT_BUS.addListener(DayNightInversionEvents::onLivingDeath);
      NeoForge.EVENT_BUS.addListener(AlignmentFieldEvents::onPlayerTick);
      NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, AlignmentFieldEvents::onEntityTick);
      NeoForge.EVENT_BUS.addListener(AlignmentFieldEvents::onServerStopped);
      NeoForge.EVENT_BUS.addListener(TimeTravelManager::onServerStopping);
      NeoForge.EVENT_BUS.addListener(TimeTravelManager::onServerStopped);
      NeoForge.EVENT_BUS.addListener(TimeTravelManager::onPlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, LooterManager::onLivingDeath);
      NeoForge.EVENT_BUS.addListener(LooterManager::onPlayerClone);
      NeoForge.EVENT_BUS.addListener(LooterManager::onPlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(LooterManager::onLivingDrops);
      NeoForge.EVENT_BUS.addListener(LooterManager::onLivingExperienceDrop);
      NeoForge.EVENT_BUS.addListener(LooterManager::onEntityJoinLevel);
      NeoForge.EVENT_BUS.addListener(LooterManager::onEntityLeaveLevel);
      NeoForge.EVENT_BUS.addListener(LooterManager::onLivingChangeTarget);
      NeoForge.EVENT_BUS.addListener(LooterManager::onServerTick);
      NeoForge.EVENT_BUS.addListener(LooterManager::onServerStopped);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onRegisterCommands);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, SeducerManager::onAttackEntity);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, SeducerManager::onIncomingDamage);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onLivingChangeTarget);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onEntityJoinLevel);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onEntityLeaveLevel);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onMobDespawn);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onEntityTick);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onPlayerLoggedOut);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onPlayerClone);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onServerTick);
      NeoForge.EVENT_BUS.addListener(SeducerManager::onServerStopped);
      if (FMLEnvironment.dist == Dist.CLIENT) {
         modBus.addListener(ExtrasClientEvents::onRegisterRenderers);
         modBus.addListener(MagicCameraClientExtensions::onRegisterClientExtensions);
         NeoForge.EVENT_BUS.addListener(MagicCameraView::onRenderFramePre);
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, MagicCameraView::onRenderHand);
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, MagicCameraView::onRenderGuiPre);
         NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, MagicCameraView::onRenderFramePost);
         NeoForge.EVENT_BUS.addListener(MagicCameraView::onRenderLevelStage);
         NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, TimeTravelClient::onClientTick);
         NeoForge.EVENT_BUS.addListener(LooterClientEvents::onClientTick);
         NeoForge.EVENT_BUS.addListener(TimeTravelClient::onClientPlayerLoggingIn);
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, TimeTravelClient::onRenderFramePre);
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, TimeTravelClient::onRenderGuiPre);
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, TimeTravelClient::onSelectMusic);
         NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, TimeTravelClient::onScreenRenderPost);
         NeoForge.EVENT_BUS.addListener(LooterClientEvents::onCompileGeoLayers);
         modBus.addListener(LooterClientEvents::onAddLayers);
      }
   }

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", path);
   }

   private void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
      if (TENSURA_SPAWN_EGGS_TAB.equals(event.getTabKey()) || CreativeModeTabs.SPAWN_EGGS.equals(event.getTabKey())) {
         event.accept((ItemLike)ExtrasItems.DIABLO_SPAWN_EGG.get());
      }

      if (TENSURA_MISC_TAB.equals(event.getTabKey())) {
         event.accept((ItemLike)ExtrasItems.TELEPORT_MAGIC_TUBE.get());
         event.accept((ItemLike)ExtrasItems.MAGIC_CAMERA.get());
      }

      if (TENSURA_CONSUMABLES_TAB.equals(event.getTabKey())) {
         event.accept((ItemLike)ExtrasItems.HIGH_QUALITY_HONEY.get());
      }
   }
}
