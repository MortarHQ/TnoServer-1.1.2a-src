package com.mortal_cultivation;

import com.mojang.logging.LogUtils;
import com.mortal_cultivation.client.model.MonsterGatheringTalismanModel;
import com.mortal_cultivation.client.model.ThunderTalismanModel;
import com.mortal_cultivation.client.renderer.FlyingSwordRenderer;
import com.mortal_cultivation.client.renderer.MonsterGatheringTalismanRenderer;
import com.mortal_cultivation.client.renderer.RuneBurstRenderer;
import com.mortal_cultivation.client.renderer.TaoistArrayRenderer;
import com.mortal_cultivation.client.renderer.TenThousandSoulBannerRenderer;
import com.mortal_cultivation.client.renderer.ThunderTalismanRenderer;
import com.mortal_cultivation.client.renderer.ZhaoYingZhuRenderer;
import com.mortal_cultivation.compat.curios.client.SpiritGlassesCuriosClientCompat;
import com.mortal_cultivation.config.AlchemyFurnaceConfig;
import com.mortal_cultivation.config.MortalCultivationGeneralConfig;
import com.mortal_cultivation.event.MindscapeSkillRestrictionHandler;
import com.mortal_cultivation.event.RaceFlightAbilityHandler;
import com.mortal_cultivation.event.WastelanderBookHandler;
import com.mortal_cultivation.flight.FlightAnimationNetwork;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.init.MortalCultivationCapabilities;
import com.mortal_cultivation.init.MortalCultivationModMenus;
import com.mortal_cultivation.loot.CultivationLootModifiers;
import com.mortal_cultivation.myriadswords.MyriadSwordsNetwork;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.registry.EffectRegistry;
import com.mortal_cultivation.registry.EntityRegistry;
import com.mortal_cultivation.registry.ParticleRegistry;
import com.mortal_cultivation.registry.RecipeRegistry;
import com.mortal_cultivation.registry.SoundRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import com.mortal_cultivation.registry.item.CultivationCreativeTabs;
import com.mortal_cultivation.registry.item.CultivationItems;
import com.mortal_cultivation.registry.race.CultivationRaces;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import com.mortal_cultivation.spiritroot.SpiritRootNetwork;
import com.mortal_cultivation.spiritroot.SpiritRootRaceHandler;
import com.mortal_cultivation.util.FuelSpeedManager;
import com.mortal_cultivation.worldgen.SpiritForestRegion;
import io.github.manasmods.manascore.config.ConfigRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import terrablender.api.Regions;

@Mod("mortal_cultivation")
public class MortalCultivation {
   public static final String MODID = "mortal_cultivation";
   public static final Logger LOGGER = LogUtils.getLogger();

   public MortalCultivation(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(this::commonSetup);
      AlchemyFurnaceConfig.register(modContainer);
      ConfigRegistry.registerConfig(new MortalCultivationGeneralConfig());
      BlockRegistry.register(modEventBus);
      WorldgenRegistry.register(modEventBus);
      ParticleRegistry.register(modEventBus);
      SoundRegistry.register(modEventBus);
      CultivationCreativeTabs.register(modEventBus);
      CultivationItems.register(modEventBus);
      CultivationSkills.register(modEventBus);
      CultivationRaces.register(modEventBus);
      EntityRegistry.register(modEventBus);
      CultivationEntities.register(modEventBus);
      EffectRegistry.register(modEventBus);
      MortalCultivationModMenus.register(modEventBus);
      RecipeRegistry.register(modEventBus);
      BlockEntityRegistry.register(modEventBus);
      CultivationLootModifiers.register(modEventBus);
      modEventBus.addListener(MortalCultivationCapabilities::registerCapabilities);
      modEventBus.addListener(SpiritRootNetwork::registerPayloadHandlers);
      modEventBus.addListener(FlightAnimationNetwork::registerPayloadHandlers);
      modEventBus.addListener(MyriadSwordsNetwork::registerPayloadHandlers);
      NeoForge.EVENT_BUS.register(this);
      SpiritRootRaceHandler.init();
      RaceFlightAbilityHandler.init();
      MindscapeSkillRestrictionHandler.init();
      WastelanderBookHandler.init();
      MortalCultivationAddon.addToConfig();
      LOGGER.info("Mortal Cultivation mod has been loaded!");
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      LOGGER.info("Initializing Mortal Cultivation races...");
      event.enqueueWork(() -> {
         Regions.register(new SpiritForestRegion());
         FuelSpeedManager.initialize();
         LOGGER.info("Fuel speed manager initialized!");
      });
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
   }

   @EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
   public static class ClientModEvents {
      private static final ResourceLocation WATER_STILL = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still");
      private static final ResourceLocation WATER_FLOW = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow");
      private static final ResourceLocation WATER_OVERLAY = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_overlay");
      private static final ResourceLocation UNDERWATER_OVERLAY = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/misc/underwater.png");

      @SubscribeEvent
      public static void onClientSetup(FMLClientSetupEvent event) {
         MortalCultivation.LOGGER.info("HELLO FROM CLIENT SETUP");
         MortalCultivation.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
         event.enqueueWork(() -> {
            EntityRenderers.register((EntityType)EntityRegistry.THUNDER_TALISMAN_ENTITY.get(), ThunderTalismanRenderer::new);
            EntityRenderers.register((EntityType)EntityRegistry.MONSTER_GATHERING_TALISMAN_ENTITY.get(), MonsterGatheringTalismanRenderer::new);
            EntityRenderers.register((EntityType)CultivationEntities.TAOIST_ARRAY.get(), TaoistArrayRenderer::new);
            EntityRenderers.register((EntityType)CultivationEntities.TEN_THOUSAND_SOUL_BANNER.get(), TenThousandSoulBannerRenderer::new);
            EntityRenderers.register((EntityType)CultivationEntities.ALCHEMIST_FIRE_BREATH.get(), NoopRenderer::new);
            EntityRenderers.register((EntityType)CultivationEntities.FLYING_SWORD.get(), FlyingSwordRenderer::new);
            EntityRenderers.register((EntityType)CultivationEntities.ZHAO_YING_ZHU.get(), ZhaoYingZhuRenderer::new);
            EntityRenderers.register((EntityType)CultivationEntities.RUNE_BURST.get(), RuneBurstRenderer::new);
            if (ModList.get().isLoaded("curios")) {
               SpiritGlassesCuriosClientCompat.registerRenderer();
            }
         });
      }

      @SubscribeEvent
      public static void registerLayerDefinition(RegisterLayerDefinitions event) {
         event.registerLayerDefinition(ThunderTalismanModel.LAYER_LOCATION, ThunderTalismanModel::createBodyLayer);
         event.registerLayerDefinition(MonsterGatheringTalismanModel.LAYER_LOCATION, MonsterGatheringTalismanModel::createBodyLayer);
      }
   }
}
