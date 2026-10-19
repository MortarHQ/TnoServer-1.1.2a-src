package com.chronodawn.neoforge.client;

import com.chronodawn.ChronoDawn;
import com.chronodawn.client.CobwebColorProvider;
import com.chronodawn.client.LeafColorProvider;
import com.chronodawn.client.TemporalGrassEdgeTint;
import com.chronodawn.client.TemporalPlantColorProvider;
import com.chronodawn.client.model.ChronalLeechModel;
import com.chronodawn.client.model.ChronoBovineModel;
import com.chronodawn.client.model.ChronoTurtleModel;
import com.chronodawn.client.model.ChronoUrsidModel;
import com.chronodawn.client.model.ChronosWardenModel;
import com.chronodawn.client.model.ClockworkColossusModel;
import com.chronodawn.client.model.ClockworkSentinelModel;
import com.chronodawn.client.model.EntropyKeeperModel;
import com.chronodawn.client.model.EpochHuskModel;
import com.chronodawn.client.model.FloqModel;
import com.chronodawn.client.model.ForgottenMinuteModel;
import com.chronodawn.client.model.GlideFishModel;
import com.chronodawn.client.model.HourglassGolemModel;
import com.chronodawn.client.model.MomentCreeperModel;
import com.chronodawn.client.model.ParadoxCrawlerModel;
import com.chronodawn.client.model.PulseHogModel;
import com.chronodawn.client.model.SecondhandArcherModel;
import com.chronodawn.client.model.SecondwingFowlModel;
import com.chronodawn.client.model.TemporalCapridModel;
import com.chronodawn.client.model.TemporalPhantomModel;
import com.chronodawn.client.model.TemporalWraithModel;
import com.chronodawn.client.model.TickingSheepBodyModel;
import com.chronodawn.client.model.TickingSheepWoolModel;
import com.chronodawn.client.model.TimeGuardianModel;
import com.chronodawn.client.model.TimeKeeperModel;
import com.chronodawn.client.model.TimeTyrantModel;
import com.chronodawn.client.model.TimeboundRabbitModel;
import com.chronodawn.client.model.TimelineStriderModel;
import com.chronodawn.client.particle.ChronoDawnPortalParticle;
import com.chronodawn.client.particle.ChronoShieldEchoParticle;
import com.chronodawn.client.renderer.ChronoDawnBoatRenderer;
import com.chronodawn.client.renderer.ChronoDawnChestBoatRenderer;
import com.chronodawn.client.renderer.ChronosWardenRenderer;
import com.chronodawn.client.renderer.ClockworkColossusRenderer;
import com.chronodawn.client.renderer.EntropyKeeperRenderer;
import com.chronodawn.client.renderer.GearProjectileRenderer;
import com.chronodawn.client.renderer.TemporalPhantomRenderer;
import com.chronodawn.client.renderer.TimeArrowRenderer;
import com.chronodawn.client.renderer.TimeBlastRenderer;
import com.chronodawn.client.renderer.TimeGuardianRenderer;
import com.chronodawn.client.renderer.TimeTyrantRenderer;
import com.chronodawn.client.renderer.mobs.ChronalLeechRenderer;
import com.chronodawn.client.renderer.mobs.ChronoBovineRenderer;
import com.chronodawn.client.renderer.mobs.ChronoTurtleRenderer;
import com.chronodawn.client.renderer.mobs.ChronoUrsidRenderer;
import com.chronodawn.client.renderer.mobs.ClockworkSentinelRenderer;
import com.chronodawn.client.renderer.mobs.EpochHuskRenderer;
import com.chronodawn.client.renderer.mobs.FloqRenderer;
import com.chronodawn.client.renderer.mobs.ForgottenMinuteRenderer;
import com.chronodawn.client.renderer.mobs.GlideFishRenderer;
import com.chronodawn.client.renderer.mobs.HourglassGolemRenderer;
import com.chronodawn.client.renderer.mobs.MomentCreeperRenderer;
import com.chronodawn.client.renderer.mobs.ParadoxCrawlerRenderer;
import com.chronodawn.client.renderer.mobs.PulseHogRenderer;
import com.chronodawn.client.renderer.mobs.SecondhandArcherRenderer;
import com.chronodawn.client.renderer.mobs.SecondwingFowlRenderer;
import com.chronodawn.client.renderer.mobs.TemporalCapridRenderer;
import com.chronodawn.client.renderer.mobs.TemporalWraithRenderer;
import com.chronodawn.client.renderer.mobs.TickingSheepRenderer;
import com.chronodawn.client.renderer.mobs.TimeKeeperRenderer;
import com.chronodawn.client.renderer.mobs.TimeboundRabbitRenderer;
import com.chronodawn.client.renderer.mobs.TimelineStriderRenderer;
import com.chronodawn.gui.ChronicleScreen;
import com.chronodawn.gui.data.ChronicleData;
import com.chronodawn.items.ChronicleBookItem;
import com.chronodawn.items.DeferredSpawnEggItem;
import com.chronodawn.items.TimeCompassItem;
import com.chronodawn.neoforge.registry.ModFluidTypes;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModItems;
import com.chronodawn.registry.ModParticles;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.fluids.FluidType;

@EventBusSubscriber(modid = "chronodawn", value = Dist.CLIENT, bus = Bus.MOD)
public class ChronoDawnClientNeoForge {
   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         registerBlockColors();
         registerItemProperties();
      });
   }

   @SubscribeEvent
   public static void onRegisterLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(TimeGuardianRenderer.LAYER_LOCATION, TimeGuardianModel::createBodyLayer);
      event.registerLayerDefinition(ChronosWardenModel.LAYER_LOCATION, ChronosWardenModel::createBodyLayer);
      event.registerLayerDefinition(TimeTyrantRenderer.LAYER_LOCATION, TimeTyrantModel::createBodyLayer);
      event.registerLayerDefinition(TemporalWraithModel.LAYER_LOCATION, TemporalWraithModel::createBodyLayer);
      event.registerLayerDefinition(ClockworkSentinelModel.LAYER_LOCATION, ClockworkSentinelModel::createBodyLayer);
      event.registerLayerDefinition(TimeKeeperModel.LAYER_LOCATION, TimeKeeperModel::createBodyLayer);
      event.registerLayerDefinition(FloqModel.LAYER_LOCATION, FloqModel::createBodyLayer);
      event.registerLayerDefinition(ClockworkColossusRenderer.LAYER_LOCATION, ClockworkColossusModel::createBodyLayer);
      event.registerLayerDefinition(TemporalPhantomRenderer.LAYER_LOCATION, TemporalPhantomModel::createBodyLayer);
      event.registerLayerDefinition(EntropyKeeperRenderer.LAYER_LOCATION, EntropyKeeperModel::createBodyLayer);
      event.registerLayerDefinition(EpochHuskModel.LAYER_LOCATION, EpochHuskModel::createBodyLayer);
      event.registerLayerDefinition(ForgottenMinuteModel.LAYER_LOCATION, ForgottenMinuteModel::createBodyLayer);
      event.registerLayerDefinition(ChronalLeechModel.LAYER_LOCATION, ChronalLeechModel::createBodyLayer);
      event.registerLayerDefinition(MomentCreeperModel.LAYER_LOCATION, MomentCreeperModel::createBodyLayer);
      event.registerLayerDefinition(GlideFishModel.LAYER_LOCATION, GlideFishModel::createBodyLayer);
      event.registerLayerDefinition(TimelineStriderModel.LAYER_LOCATION, TimelineStriderModel::createBodyLayer);
      event.registerLayerDefinition(HourglassGolemModel.LAYER_LOCATION, HourglassGolemModel::createBodyLayer);
      event.registerLayerDefinition(SecondhandArcherModel.LAYER_LOCATION, SecondhandArcherModel::createBodyLayer);
      event.registerLayerDefinition(ParadoxCrawlerModel.LAYER_LOCATION, ParadoxCrawlerModel::createBodyLayer);
      event.registerLayerDefinition(ChronoTurtleModel.LAYER_LOCATION, ChronoTurtleModel::createBodyLayer);
      event.registerLayerDefinition(TimeboundRabbitModel.LAYER_LOCATION, TimeboundRabbitModel::createBodyLayer);
      event.registerLayerDefinition(PulseHogModel.LAYER_LOCATION, PulseHogModel::createBodyLayer);
      event.registerLayerDefinition(SecondwingFowlModel.LAYER_LOCATION, SecondwingFowlModel::createBodyLayer);
      event.registerLayerDefinition(TickingSheepBodyModel.LAYER_LOCATION, TickingSheepBodyModel::createBodyLayer);
      event.registerLayerDefinition(TickingSheepWoolModel.LAYER_LOCATION, TickingSheepWoolModel::createBodyLayer);
      event.registerLayerDefinition(ChronoBovineModel.LAYER_LOCATION, ChronoBovineModel::createBodyLayer);
      event.registerLayerDefinition(TemporalCapridModel.LAYER_LOCATION, TemporalCapridModel::createBodyLayer);
      event.registerLayerDefinition(ChronoUrsidModel.LAYER_LOCATION, ChronoUrsidModel::createBodyLayer);
      VersionSpecificClientHelper.registerBoatModelLayers(event);
      ChronoDawn.LOGGER.debug("Registered entity model layers for NeoForge");
   }

   @SubscribeEvent
   public static void onRegisterEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)ModEntities.TIME_GUARDIAN.get(), TimeGuardianRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONOS_WARDEN.get(), ChronosWardenRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIME_TYRANT.get(), TimeTyrantRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIME_ARROW.get(), TimeArrowRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIME_BLAST.get(), TimeBlastRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.GEAR_PROJECTILE.get(), GearProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CLOCKWORK_COLOSSUS.get(), ClockworkColossusRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TEMPORAL_PHANTOM.get(), TemporalPhantomRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTROPY_KEEPER.get(), EntropyKeeperRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TEMPORAL_WRAITH.get(), TemporalWraithRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CLOCKWORK_SENTINEL.get(), ClockworkSentinelRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIME_KEEPER.get(), TimeKeeperRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.FLOQ.get(), FloqRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.EPOCH_HUSK.get(), EpochHuskRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.FORGOTTEN_MINUTE.get(), ForgottenMinuteRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONAL_LEECH.get(), ChronalLeechRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.MOMENT_CREEPER.get(), MomentCreeperRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.GLIDE_FISH.get(), GlideFishRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIMELINE_STRIDER.get(), TimelineStriderRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.HOURGLASS_GOLEM.get(), HourglassGolemRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SECONDHAND_ARCHER.get(), SecondhandArcherRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PARADOX_CRAWLER.get(), ParadoxCrawlerRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONO_TURTLE.get(), ChronoTurtleRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIMEBOUND_RABBIT.get(), TimeboundRabbitRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PULSE_HOG.get(), PulseHogRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SECONDWING_FOWL.get(), SecondwingFowlRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TICKING_SHEEP.get(), TickingSheepRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONO_BOVINE.get(), ChronoBovineRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TEMPORAL_CAPRID.get(), TemporalCapridRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONO_URSID.get(), ChronoUrsidRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONO_DAWN_BOAT.get(), ChronoDawnBoatRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CHRONO_DAWN_CHEST_BOAT.get(), ChronoDawnChestBoatRenderer::new);
      ChronoDawn.LOGGER.debug("Registered entity renderers for NeoForge");
   }

   @SubscribeEvent
   public static void onRegisterBlockColors(Block event) {
      event.register(
         (state, world, pos, tintIndex) -> LeafColorProvider.colorAt(LeafColorProvider.LeafKind.TIME_WOOD, pos),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.TIME_WOOD_LEAVES.get()}
      );
      event.register(
         (state, world, pos, tintIndex) -> LeafColorProvider.colorAt(LeafColorProvider.LeafKind.DARK_TIME_WOOD, pos),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.DARK_TIME_WOOD_LEAVES.get()}
      );
      event.register(
         (state, world, pos, tintIndex) -> LeafColorProvider.colorAt(LeafColorProvider.LeafKind.ANCIENT_TIME_WOOD, pos),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.ANCIENT_TIME_WOOD_LEAVES.get()}
      );
      event.register((state, world, pos, tintIndex) -> {
         int age = (Integer)state.getValue(BlockStateProperties.AGE_7);
         int r = (int)(93.0 + 119 * age / 7.0);
         int g = (int)(191.0 + -16 * age / 7.0);
         int b = (int)(65.0 + -10 * age / 7.0);
         return r << 16 | g << 8 | b;
      }, new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.CHRONO_MELON_STEM.get()});
      event.register(
         (state, world, pos, tintIndex) -> 13938487,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.ATTACHED_CHRONO_MELON_STEM.get()}
      );
      event.register(
         TemporalGrassEdgeTint::provide,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.TEMPORAL_GRASS_BLOCK.get()}
      );
      event.register(
         (state, world, pos, tintIndex) -> TemporalPlantColorProvider.blockTint(world, pos, tintIndex),
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)ModBlocks.TEMPORAL_TALL_GRASS.get(),
            (net.minecraft.world.level.block.Block)ModBlocks.TEMPORAL_FERN.get(),
            (net.minecraft.world.level.block.Block)ModBlocks.TEMPORAL_GRASS.get()
         }
      );
      event.register(
         (state, world, pos, tintIndex) -> CobwebColorProvider.blockTint(world, pos, tintIndex),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)ModBlocks.CHRONO_COBWEB.get()}
      );
      event.register(
         TemporalGrassEdgeTint::provideForSandGravel,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)ModBlocks.TEMPORAL_SAND.get(), (net.minecraft.world.level.block.Block)ModBlocks.TEMPORAL_GRAVEL.get()
         }
      );
      ChronoDawn.LOGGER.debug("Registered block color handlers for NeoForge");
   }

   @SubscribeEvent
   public static void onRegisterItemColors(Item event) {
      event.register((stack, tintIndex) -> LeafColorProvider.LeafKind.TIME_WOOD.iconColor(), new ItemLike[]{(ItemLike)ModBlocks.TIME_WOOD_LEAVES.get()});
      event.register(
         (stack, tintIndex) -> LeafColorProvider.LeafKind.DARK_TIME_WOOD.iconColor(), new ItemLike[]{(ItemLike)ModBlocks.DARK_TIME_WOOD_LEAVES.get()}
      );
      event.register(
         (stack, tintIndex) -> LeafColorProvider.LeafKind.ANCIENT_TIME_WOOD.iconColor(), new ItemLike[]{(ItemLike)ModBlocks.ANCIENT_TIME_WOOD_LEAVES.get()}
      );
      event.register((stack, tintIndex) -> 13938487, new ItemLike[]{(ItemLike)ModBlocks.CHRONO_MELON_STEM.get()});
      event.register((stack, tintIndex) -> 13938487, new ItemLike[]{(ItemLike)ModBlocks.ATTACHED_CHRONO_MELON_STEM.get()});
      event.register((stack, tintIndex) -> tintIndex == 0 ? 5999300 : -1, new ItemLike[]{(ItemLike)ModItems.TEMPORAL_GRASS_BLOCK.get()});
      event.register(
         (stack, tintIndex) -> TemporalPlantColorProvider.itemTint(tintIndex),
         new ItemLike[]{(ItemLike)ModItems.TEMPORAL_TALL_GRASS.get(), (ItemLike)ModItems.TEMPORAL_FERN.get(), (ItemLike)ModItems.TEMPORAL_GRASS.get()}
      );
      event.register(
         (stack, tintIndex) -> {
            if (stack.getItem() instanceof DeferredSpawnEggItem egg) {
               int color = egg.getColor(tintIndex);
               return 0xFF000000 | color;
            } else {
               return -1;
            }
         },
         new ItemLike[]{
            (ItemLike)ModItems.TEMPORAL_WRAITH_SPAWN_EGG.get(),
            (ItemLike)ModItems.CLOCKWORK_SENTINEL_SPAWN_EGG.get(),
            (ItemLike)ModItems.TIME_KEEPER_SPAWN_EGG.get(),
            (ItemLike)ModItems.FLOQ_SPAWN_EGG.get(),
            (ItemLike)ModItems.TIME_GUARDIAN_SPAWN_EGG.get(),
            (ItemLike)ModItems.TIME_TYRANT_SPAWN_EGG.get(),
            (ItemLike)ModItems.CHRONOS_WARDEN_SPAWN_EGG.get(),
            (ItemLike)ModItems.CLOCKWORK_COLOSSUS_SPAWN_EGG.get(),
            (ItemLike)ModItems.ENTROPY_KEEPER_SPAWN_EGG.get(),
            (ItemLike)ModItems.TEMPORAL_PHANTOM_SPAWN_EGG.get(),
            (ItemLike)ModItems.EPOCH_HUSK_SPAWN_EGG.get(),
            (ItemLike)ModItems.FORGOTTEN_MINUTE_SPAWN_EGG.get(),
            (ItemLike)ModItems.CHRONAL_LEECH_SPAWN_EGG.get(),
            (ItemLike)ModItems.MOMENT_CREEPER_SPAWN_EGG.get(),
            (ItemLike)ModItems.GLIDE_FISH_SPAWN_EGG.get(),
            (ItemLike)ModItems.TIMELINE_STRIDER_SPAWN_EGG.get(),
            (ItemLike)ModItems.HOURGLASS_GOLEM_SPAWN_EGG.get(),
            (ItemLike)ModItems.SECONDHAND_ARCHER_SPAWN_EGG.get(),
            (ItemLike)ModItems.PARADOX_CRAWLER_SPAWN_EGG.get(),
            (ItemLike)ModItems.CHRONO_TURTLE_SPAWN_EGG.get(),
            (ItemLike)ModItems.TIMEBOUND_RABBIT_SPAWN_EGG.get(),
            (ItemLike)ModItems.PULSE_HOG_SPAWN_EGG.get(),
            (ItemLike)ModItems.SECONDWING_FOWL_SPAWN_EGG.get(),
            (ItemLike)ModItems.TICKING_SHEEP_SPAWN_EGG.get(),
            (ItemLike)ModItems.CHRONO_BOVINE_SPAWN_EGG.get()
         }
      );
      ChronoDawn.LOGGER.debug("Registered item color handlers for NeoForge");
   }

   @SubscribeEvent
   public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)ModParticles.CHRONO_DAWN_PORTAL.get(), ChronoDawnPortalParticle.Provider::new);
      event.registerSpriteSet((ParticleType)ModParticles.CHRONO_SHIELD_ECHO.get(), ChronoShieldEchoParticle.Provider::new);
      ChronoDawn.LOGGER.debug("Registered particle providers for NeoForge");
   }

   @SubscribeEvent
   public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerFluidType(new IClientFluidTypeExtensions() {
         public ResourceLocation getStillTexture() {
            return ResourceLocation.withDefaultNamespace("block/water_still");
         }

         public ResourceLocation getFlowingTexture() {
            return ResourceLocation.withDefaultNamespace("block/water_flow");
         }

         public ResourceLocation getOverlayTexture() {
            return ResourceLocation.withDefaultNamespace("block/water_overlay");
         }

         public int getTintColor() {
            return -12618012;
         }
      }, new FluidType[]{ModFluidTypes.DECORATIVE_WATER_TYPE.get()});
      ChronoDawn.LOGGER.debug("Registered fluid type client extensions for NeoForge");
   }

   private static void registerBlockColors() {
   }

   private static void registerItemProperties() {
      registerBlockingProperty((net.minecraft.world.item.Item)ModItems.CLOCKSTONE_SHIELD.get());
      registerBlockingProperty((net.minecraft.world.item.Item)ModItems.ENHANCED_CLOCKSTONE_SHIELD.get());
      registerBlockingProperty((net.minecraft.world.item.Item)ModItems.ENTROPY_CRYSTAL_SHIELD.get());
      ItemProperties.register(
         (net.minecraft.world.item.Item)ModItems.TIME_COMPASS.get(),
         ResourceLocation.fromNamespaceAndPath("minecraft", "angle"),
         (stack, level, entity, seed) -> {
            Optional<GlobalPos> targetPos = TimeCompassItem.getTargetPosition(stack);
            if (!targetPos.isEmpty() && level != null) {
               GlobalPos target = targetPos.get();
               if (!level.dimension().equals(target.dimension())) {
                  return (float)Math.random();
               }

               Entity holder = entity != null ? entity : null;
               return holder == null ? 0.0F : calculateCompassAngle(holder, target.pos());
            } else {
               return (float)Math.random();
            }
         }
      );
   }

   private static void registerBlockingProperty(net.minecraft.world.item.Item item) {
      ItemProperties.register(
         item,
         ResourceLocation.parse("blocking"),
         (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
   }

   private static float calculateCompassAngle(Entity entity, BlockPos targetPos) {
      Vec3 entityPos = entity.position();
      double dx = targetPos.getX() + 0.5 - entityPos.x;
      double dz = targetPos.getZ() + 0.5 - entityPos.z;
      double angleRadians = Math.atan2(dz, dx);
      float yaw = entity.getYRot();
      double compassAngle = (angleRadians - Math.toRadians(yaw) + Math.PI) / (Math.PI * 2);
      return (float)Mth.positiveModulo(compassAngle, 1.0);
   }

   @SubscribeEvent
   public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
         protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
            return null;
         }

         protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
            ChronicleData.getInstance().load(resourceManager);
            ChronoDawn.LOGGER.debug("Chronicle data loaded/reloaded");
         }
      });
   }

   @EventBusSubscriber(modid = "chronodawn", value = Dist.CLIENT, bus = Bus.GAME)
   public static class ForgeEventHandlers {
      @SubscribeEvent
      public static void onClientTickEnd(Post event) {
         VersionSpecificClientHelper.onClientTick();
      }

      @SubscribeEvent
      public static void onRightClickItem(RightClickItem event) {
         ItemStack stack = event.getItemStack();
         if (stack.getItem() instanceof ChronicleBookItem) {
            if (event.getLevel().isClientSide()) {
               Minecraft.getInstance().setScreen(new ChronicleScreen());
            }

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
         }
      }
   }
}
