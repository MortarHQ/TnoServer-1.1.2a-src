package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.elysium_api.api.event.BlockOnPlaceEvent;
import net.jadenxgamer.elysium_api.api.surface_rules.SurfaceRulesRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.assetdriven.managers.BurnPalettesManager;
import net.jadenxgamer.netherexp.client.assetdriven.managers.FireParticlesManager;
import net.jadenxgamer.netherexp.core.block.SorrowsquashBlock;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.core.effect.DeterrentEffect;
import net.jadenxgamer.netherexp.core.entity.PortalGlow;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.misc.JNEBuiltinPacks;
import net.jadenxgamer.netherexp.core.misc.JNECauldronInteractions;
import net.jadenxgamer.netherexp.core.worldgen.JNESurfaceRules;
import net.jadenxgamer.netherexp.registry.JNEAttachmentTypes;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.BlockCrackTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.CalculateDetachedCameraDistanceEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.PortalSpawnEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent.NewRegistry;

@EventBusSubscriber(modid = "netherexp")
public class JNEEvents {
   @SubscribeEvent
   public static void onServerAboutToStart(ServerAboutToStartEvent event) {
      NetherExp.registryAccess = event.getServer().registryAccess();
   }

   @SubscribeEvent
   public static void calculateCameraDistance(CalculateDetachedCameraDistanceEvent event) {
      if (event.getCamera().getEntity().getVehicle() instanceof Stampede) {
         event.setDistance(event.getDistance() * 1.7F);
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      BlockCrackTracker.tick();
   }

   @SubscribeEvent
   public static void onPortalSpawn(PortalSpawnEvent event) {
      if (event.getLevel() instanceof Level level) {
         BlockPos pos = event.getPos();
         if (level.getServer() == null) {
            return;
         }

         level.getServer().execute(() -> {
            if (level.getBlockState(pos).is(Blocks.NETHER_PORTAL)) {
               level.playSound(null, pos, JNESoundEvents.NETHER_PORTAL_ACTIVATE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
               PortalGlow.spawnForPortal(level, pos);
            }
         });
      }
   }

   @SubscribeEvent
   public static void onBlockBreak(BreakEvent event) {
      BlockState state = event.getState();
      if (state.is(JNEBlocks.FOSSIL_FUEL_ORE.get()) && event.getPlayer() instanceof ServerPlayer serverPlayer) {
         JNECriteriaTriggers.BROKEN_FOSSIL_FUEL_ORE.get().trigger(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onLevelUnload(Unload event) {
      if (event.getLevel() instanceof Level level) {
         BlockCrackTracker.onLevelUnload(level);
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      OnDeathGroundConversion.onLivingDeath(event);
   }

   @SubscribeEvent
   public static void commonSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         SurfaceRulesRegistry.registerNetherSurfaceRule(JNESurfaceRules.init(), "netherexp");
         JNECauldronInteractions.register();
         JNEItems.setup();
         JNEFluids.setup();
      });
   }

   @SubscribeEvent
   public static void onPlaceBlock(BlockOnPlaceEvent event) {
      Level level = event.getLevel();
      BlockState state = event.getState();
      BlockPos pos = event.getPos();
      SorrowsquashBlock.convertPumpkinStem(level, state, pos);
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
      JNECreativeModeTabs.addToExistingTabs(event);
   }

   @SubscribeEvent
   public static void registerRegistries(NewRegistryEvent event) {
      JNERegistries.registryInit(event);
   }

   @SubscribeEvent
   public static void datapackRegistry(NewRegistry event) {
      JNERegistries.datapackInit(event);
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      JNEEntityType.registerAttributes(event);
   }

   @SubscribeEvent
   public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
      JNEEntityType.registerSpawnPlacements(event);
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void registerEvent(RegisterEvent event) {
      JNEItems.backportRegistries(event);
   }

   @SubscribeEvent
   public static void addBuiltinPacks(AddPackFindersEvent event) {
      if (event.getPackType() == PackType.CLIENT_RESOURCES) {
         JNEBuiltinPacks.rpJNERetextures(event);
      }

      if (event.getPackType() == PackType.SERVER_DATA) {
         JNEBuiltinPacks.dpNetherMosaicBiomeSource(event);
         JNEBuiltinPacks.dpNetherWorldgenOverhaul(event);
      }
   }

   @SubscribeEvent
   public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new FireParticlesManager());
      event.registerReloadListener(new BurnPalettesManager());
   }

   @SubscribeEvent
   public static void onLivingTickPost(net.neoforged.neoforge.event.tick.EntityTickEvent.Post event) {
      if (event.getEntity() instanceof LivingEntity entity) {
         if (!entity.level().isClientSide()) {
            ResourceLocation current = (ResourceLocation)entity.getData(JNEAttachmentTypes.LAST_FIRE);
            ResourceLocation defaultLastFire = NetherExp.minecraftPath("fire");
            if (entity.displayFireAnimation()) {
               BlockState state = entity.getInBlockState();
               if (state.is(JNETags.Blocks.LAST_FIRE_SUPPORTED_BLOCKS)) {
                  ResourceLocation newLastFire = state.getBlock().builtInRegistryHolder().key().location();
                  if (!current.equals(newLastFire)) {
                     entity.setData(JNEAttachmentTypes.LAST_FIRE, newLastFire);
                  }
               }
            } else if (!current.equals(defaultLastFire)) {
               entity.setData(JNEAttachmentTypes.LAST_FIRE, defaultLastFire);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingTickPre(Pre event) {
      if (event.getEntity() instanceof LivingEntity entity) {
         DeterrentEffect.handlePulseEffect(entity);
      }
   }
}
