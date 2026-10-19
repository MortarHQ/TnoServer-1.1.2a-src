package twilightforest.client.event;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.FlameParticle.SmallFlameProvider;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.ModelEvent.BakingCompleted;
import net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.map.RegisterMapDecorationRenderersEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.client.BakedMultiPartRenderers;
import twilightforest.client.ISTER;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.client.MagicPaintingTextureManager;
import twilightforest.client.TFShaders;
import twilightforest.client.TextureGeneratorReloadListener;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.UncraftingScreen;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.armor.ArcticArmorModel;
import twilightforest.client.model.armor.FieryArmorModel;
import twilightforest.client.model.armor.KnightmetalArmorModel;
import twilightforest.client.model.armor.PhantomArmorModel;
import twilightforest.client.model.armor.TFArmorModel;
import twilightforest.client.model.armor.TravellersGearModels;
import twilightforest.client.model.armor.TravellersWingsModel;
import twilightforest.client.model.armor.YetiArmorModel;
import twilightforest.client.model.block.BrazierModel;
import twilightforest.client.model.block.ConditionalMippedModel;
import twilightforest.client.model.block.ReactorDebrisModel;
import twilightforest.client.model.block.aurorablock.NoiseVaryingModelLoader;
import twilightforest.client.model.block.carpet.RoyalRagsModelLoader;
import twilightforest.client.model.block.connected.ConnectedTextureModelLoader;
import twilightforest.client.model.block.forcefield.ForceFieldModelLoader;
import twilightforest.client.model.block.giantblock.GiantBlockModelLoader;
import twilightforest.client.model.block.patch.PatchModelLoader;
import twilightforest.client.model.entity.AdherentModel;
import twilightforest.client.model.entity.AlphaYetiModel;
import twilightforest.client.model.entity.BighornModel;
import twilightforest.client.model.entity.BlockChainGoblinModel;
import twilightforest.client.model.entity.BoarModel;
import twilightforest.client.model.entity.BunnyModel;
import twilightforest.client.model.entity.CarminiteGolemModel;
import twilightforest.client.model.entity.ChainModel;
import twilightforest.client.model.entity.CicadaModel;
import twilightforest.client.model.entity.CubeOfAnnihilationModel;
import twilightforest.client.model.entity.DeathTomeModel;
import twilightforest.client.model.entity.DeerModel;
import twilightforest.client.model.entity.FireBeetleModel;
import twilightforest.client.model.entity.FireflyModel;
import twilightforest.client.model.entity.HarbingerCubeModel;
import twilightforest.client.model.entity.HelmetCrabModel;
import twilightforest.client.model.entity.HydraHeadModel;
import twilightforest.client.model.entity.HydraModel;
import twilightforest.client.model.entity.HydraMortarModel;
import twilightforest.client.model.entity.HydraNeckModel;
import twilightforest.client.model.entity.IceCrystalModel;
import twilightforest.client.model.entity.KnightPhantomModel;
import twilightforest.client.model.entity.KnightmetalShieldModel;
import twilightforest.client.model.entity.KoboldModel;
import twilightforest.client.model.entity.LichMinionModel;
import twilightforest.client.model.entity.LichModel;
import twilightforest.client.model.entity.LowerGoblinKnightModel;
import twilightforest.client.model.entity.LoyalZombieModel;
import twilightforest.client.model.entity.MinoshroomModel;
import twilightforest.client.model.entity.MinotaurModel;
import twilightforest.client.model.entity.MoonwormModel;
import twilightforest.client.model.entity.MosquitoSwarmModel;
import twilightforest.client.model.entity.NagaModel;
import twilightforest.client.model.entity.NoopModel;
import twilightforest.client.model.entity.PenguinModel;
import twilightforest.client.model.entity.PinchBeetleModel;
import twilightforest.client.model.entity.ProtectionBoxModel;
import twilightforest.client.model.entity.QuestRamModel;
import twilightforest.client.model.entity.RavenModel;
import twilightforest.client.model.entity.RedThreadModel;
import twilightforest.client.model.entity.RedcapModel;
import twilightforest.client.model.entity.SkeletonDruidModel;
import twilightforest.client.model.entity.SlimeBeetleModel;
import twilightforest.client.model.entity.SnowQueenModel;
import twilightforest.client.model.entity.SpikeBlockModel;
import twilightforest.client.model.entity.SquirrelModel;
import twilightforest.client.model.entity.StableIceCoreModel;
import twilightforest.client.model.entity.TFGhastModel;
import twilightforest.client.model.entity.TinyBirdModel;
import twilightforest.client.model.entity.TrollModel;
import twilightforest.client.model.entity.UnstableIceCoreModel;
import twilightforest.client.model.entity.UpperGoblinKnightModel;
import twilightforest.client.model.entity.UrGhastModel;
import twilightforest.client.model.entity.WraithModel;
import twilightforest.client.model.entity.YetiModel;
import twilightforest.client.model.item.TravellersGearItemModel;
import twilightforest.client.model.item.TrollsteinnModel;
import twilightforest.client.particle.AngryLichParticle;
import twilightforest.client.particle.AnnihilateParticle;
import twilightforest.client.particle.CloudPuffParticle;
import twilightforest.client.particle.CustomTextureParticle;
import twilightforest.client.particle.DoubleJumpParticle;
import twilightforest.client.particle.DryingRackParticle;
import twilightforest.client.particle.FireflyParticle;
import twilightforest.client.particle.GhastTearParticle;
import twilightforest.client.particle.GhastTrapParticle;
import twilightforest.client.particle.IceBeamParticle;
import twilightforest.client.particle.LargeFlameParticle;
import twilightforest.client.particle.LeafParticle;
import twilightforest.client.particle.LeafRuneParticle;
import twilightforest.client.particle.LogCoreParticle;
import twilightforest.client.particle.MagicEffectParticle;
import twilightforest.client.particle.PerfectDodgeParticle;
import twilightforest.client.particle.ProtectionParticle;
import twilightforest.client.particle.SmokeScaleParticle;
import twilightforest.client.particle.SnowGuardianParticle;
import twilightforest.client.particle.SnowParticle;
import twilightforest.client.particle.SnowWarningParticle;
import twilightforest.client.particle.SortingParticle;
import twilightforest.client.particle.TransformationParticle;
import twilightforest.client.renderer.TFSkyRenderer;
import twilightforest.client.renderer.armor.TFArmorRenderer;
import twilightforest.client.renderer.armor.TFSimpleArmorRenderer;
import twilightforest.client.renderer.block.BrazierRenderer;
import twilightforest.client.renderer.block.CandelabraRenderer;
import twilightforest.client.renderer.block.CicadaRenderer;
import twilightforest.client.renderer.block.DryingRackRenderer;
import twilightforest.client.renderer.block.FireflyRenderer;
import twilightforest.client.renderer.block.JarRenderer;
import twilightforest.client.renderer.block.KeepsakeCasketRenderer;
import twilightforest.client.renderer.block.MoonwormRenderer;
import twilightforest.client.renderer.block.OminousCandleRenderer;
import twilightforest.client.renderer.block.ReactorDebrisRenderer;
import twilightforest.client.renderer.block.RedThreadRenderer;
import twilightforest.client.renderer.block.SinisterSpawnerRenderer;
import twilightforest.client.renderer.block.SkullCandleRenderer;
import twilightforest.client.renderer.block.SkullChestRenderer;
import twilightforest.client.renderer.block.TFChestRenderer;
import twilightforest.client.renderer.block.TrophyRenderer;
import twilightforest.client.renderer.entity.AdherentRenderer;
import twilightforest.client.renderer.entity.BighornRenderer;
import twilightforest.client.renderer.entity.BirdRenderer;
import twilightforest.client.renderer.entity.BlockChainGoblinRenderer;
import twilightforest.client.renderer.entity.BlockChainRenderer;
import twilightforest.client.renderer.entity.BoarRenderer;
import twilightforest.client.renderer.entity.BunnyRenderer;
import twilightforest.client.renderer.entity.CarminiteGhastRenderer;
import twilightforest.client.renderer.entity.CarminiteGolemRenderer;
import twilightforest.client.renderer.entity.CubeOfAnnihilationRenderer;
import twilightforest.client.renderer.entity.CustomProjectileTextureRenderer;
import twilightforest.client.renderer.entity.DefaultArrowRenderer;
import twilightforest.client.renderer.entity.FallingIceRenderer;
import twilightforest.client.renderer.entity.HelmetCrabRenderer;
import twilightforest.client.renderer.entity.HostileWolfRenderer;
import twilightforest.client.renderer.entity.HydraMortarRenderer;
import twilightforest.client.renderer.entity.HydraRenderer;
import twilightforest.client.renderer.entity.IceCrystalRenderer;
import twilightforest.client.renderer.entity.KnightPhantomRenderer;
import twilightforest.client.renderer.entity.LichRenderer;
import twilightforest.client.renderer.entity.MagicPaintingRenderer;
import twilightforest.client.renderer.entity.MazeSlimeRenderer;
import twilightforest.client.renderer.entity.MinoshroomRenderer;
import twilightforest.client.renderer.entity.MistWolfRenderer;
import twilightforest.client.renderer.entity.MoonwormShotRenderer;
import twilightforest.client.renderer.entity.MosquitoSwarmRenderer;
import twilightforest.client.renderer.entity.NagaRenderer;
import twilightforest.client.renderer.entity.ProtectionBoxRenderer;
import twilightforest.client.renderer.entity.QuestRamRenderer;
import twilightforest.client.renderer.entity.RisingZombieRenderer;
import twilightforest.client.renderer.entity.RovingCubeRenderer;
import twilightforest.client.renderer.entity.SlideBlockRenderer;
import twilightforest.client.renderer.entity.SlimeBeetleRenderer;
import twilightforest.client.renderer.entity.SnowGuardianRenderer;
import twilightforest.client.renderer.entity.SnowQueenRenderer;
import twilightforest.client.renderer.entity.StableIceCoreRenderer;
import twilightforest.client.renderer.entity.TFBipedRenderer;
import twilightforest.client.renderer.entity.TFGenericMobRenderer;
import twilightforest.client.renderer.entity.TFGhastRenderer;
import twilightforest.client.renderer.entity.TFGiantRenderer;
import twilightforest.client.renderer.entity.TFSpiderRenderer;
import twilightforest.client.renderer.entity.ThrownBlockRenderer;
import twilightforest.client.renderer.entity.ThrownIceRenderer;
import twilightforest.client.renderer.entity.ThrownWepRenderer;
import twilightforest.client.renderer.entity.TinyBirdRenderer;
import twilightforest.client.renderer.entity.UnstableIceCoreRenderer;
import twilightforest.client.renderer.entity.UpperGoblinKnightRenderer;
import twilightforest.client.renderer.entity.UrGhastRenderer;
import twilightforest.client.renderer.entity.WinterWolfRenderer;
import twilightforest.client.renderer.entity.WraithRenderer;
import twilightforest.client.renderer.entity.layers.IceLayer;
import twilightforest.client.renderer.entity.layers.ShieldLayer;
import twilightforest.client.renderer.map.ConqueredMapIconRenderer;
import twilightforest.client.renderer.map.MagicMapPlayerIconRenderer;
import twilightforest.client.renderer.tooltip.ItemDisplayTooltipComponent;
import twilightforest.client.renderer.tooltip.PotionFlaskTooltipComponent;
import twilightforest.client.renderer.tooltip.TravellersBeltTooltipComponent;
import twilightforest.components.item.PotionFlaskComponent;
import twilightforest.entity.boss.UrGhast;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFDimension;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFKeyBinds;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFMenuTypes;
import twilightforest.init.TFParticleType;
import twilightforest.item.ArcticArmorItem;
import twilightforest.item.BrittleFlaskItem;
import twilightforest.item.Experiment115Item;
import twilightforest.item.OreMeterItem;
import twilightforest.item.travellers_gear.TravellersArmorBeltItem;
import twilightforest.item.travellers_gear.TravellersArmorItem;
import twilightforest.item.travellers_gear.TravellersGogglesItem;
import twilightforest.util.woods.TFWoodTypes;

@Component(dist = Dist.CLIENT)
public class ClientRegistrationEvents {
   private static boolean optifinePresent = false;

   @PostConstruct
   private void setup(IEventBus bus) {
      bus.addListener(AddLayers.class, this::attachRenderLayers);
      bus.addListener(this::bakeCustomModels);
      bus.addListener(this::cacheJarLids);
      bus.addListener(this::clientSetup);
      bus.addListener(this::registerAdditionalModels);
      bus.addListener(this::registerClientReloadListeners);
      bus.addListener(this::registerDimEffects);
      bus.addListener(this::registerEntityRenderers);
      bus.addListener(this::registerLayerDefinitions);
      bus.addListener(this::registerModelLoaders);
      bus.addListener(this::registerScreens);
      bus.addListener(this::registerClientExtensions);
      bus.addListener(this::registerMapDecorators);
      bus.addListener(this::registerParticleFactories);
      bus.addListener(RegisterKeyMappingsEvent.class, event -> TFKeyBinds.KEY_MAPPINGS.forEach(event::register));
      bus.addListener(ColorHandler::registerBlockColors);
      bus.addListener(ColorHandler::registerItemColors);
      bus.addListener(TFShaders::registerShaders);
      bus.addListener(OverlayHandler::registerOverlays);
      bus.addListener(RegisterClientTooltipComponentFactoriesEvent.class, event -> {
         event.register(BrittleFlaskItem.Tooltip.class, PotionFlaskTooltipComponent::new);
         event.register(TravellersArmorBeltItem.Tooltip.class, TravellersBeltTooltipComponent::new);
         event.register(TravellersGogglesItem.Tooltip.class, ItemDisplayTooltipComponent::new);
      });
   }

   private void registerModelLoaders(RegisterGeometryLoaders event) {
      event.register(TwilightForestMod.prefix("patch"), PatchModelLoader.INSTANCE);
      event.register(TwilightForestMod.prefix("giant_block"), GiantBlockModelLoader.INSTANCE);
      event.register(TwilightForestMod.prefix("force_field"), ForceFieldModelLoader.INSTANCE);
      event.register(TwilightForestMod.prefix("connected_texture_block"), ConnectedTextureModelLoader.INSTANCE);
      event.register(TwilightForestMod.prefix("noise_varying"), NoiseVaryingModelLoader.INSTANCE);
      event.register(TwilightForestMod.prefix("royal_rags"), RoyalRagsModelLoader.INSTANCE);
      event.register(TwilightForestMod.prefix("travellers_gear"), TravellersGearItemModel.Loader.INSTANCE);
   }

   private void bakeCustomModels(ModifyBakingResult event) {
      ItemProperties.register(
         (Item)TFItems.CUBE_OF_ANNIHILATION.get(),
         TwilightForestMod.prefix("thrown"),
         (stack, level, entity, idk) -> stack.get(TFDataComponents.THROWN_PROJECTILE) != null ? 1.0F : 0.0F
      );
      ItemProperties.register(
         (Item)TFItems.KNIGHTMETAL_SHIELD.get(),
         ResourceLocation.parse("blocking"),
         (stack, level, entity, idk) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register((Item)TFItems.MOON_DIAL.get(), ResourceLocation.parse("phase"), new ClampedItemPropertyFunction() {
         double rotation;
         double rota;
         long lastUpdateTick;

         public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entityBase, int idk) {
            boolean flag = entityBase != null;
            Entity entity = (Entity)(flag ? entityBase : stack.getFrame());
            if (level == null && entity != null) {
               level = (ClientLevel)entity.level();
            }

            return level == null ? 0.0F : (float)(level.dimensionType().natural() ? Mth.frac(level.getMoonPhase() / 8.0F) : this.wobble(level, Math.random()));
         }

         private double wobble(Level level, double rotation) {
            if (level.getGameTime() != this.lastUpdateTick) {
               this.lastUpdateTick = level.getGameTime();
               double delta = rotation - this.rotation;
               delta = Mth.positiveModulo(delta + 0.5, 1.0) - 0.5;
               this.rota += delta * 0.1;
               this.rota *= 0.9;
               this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0);
            }

            return this.rotation;
         }
      });
      ItemProperties.register((Item)TFItems.ORE_METER.get(), TwilightForestMod.prefix("active"), (stack, level, entity, idk) -> {
         if (!OreMeterItem.isLoading(stack)) {
            return stack.has(TFDataComponents.ORE_DATA) ? 1.0F : 0.0F;
         }

         int totalLoadTime = 50 + OreMeterItem.getRange(stack) * 25;
         int progress = OreMeterItem.getLoadProgress(stack);
         return progress % 5 >= 2 + (int)(Math.random() * 2.0) && progress <= totalLoadTime - 15 ? 1.0F : 0.0F;
      });
      ItemProperties.register((Item)TFItems.MOONWORM_QUEEN.get(), TwilightForestMod.prefix("alt"), (stack, level, entity, idk) -> {
         if (entity != null && entity.getUseItem() == stack) {
            int useTime = stack.getUseDuration(entity) - entity.getUseItemRemainingTicks();
            if (useTime >= 12 && (useTime >>> 1) % 2 == 0) {
               return 1.0F;
            }
         }

         return 0.0F;
      });
      ItemProperties.register((Item)TFItems.ENDER_BOW.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         (Item)TFItems.ENDER_BOW.get(),
         ResourceLocation.parse("pulling"),
         (stack, level, entity, idk) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register((Item)TFItems.ICE_BOW.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         (Item)TFItems.ICE_BOW.get(),
         ResourceLocation.parse("pulling"),
         (stack, level, entity, idk) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register((Item)TFItems.SEEKER_BOW.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         (Item)TFItems.SEEKER_BOW.get(),
         ResourceLocation.parse("pulling"),
         (stack, level, entity, idk) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register((Item)TFItems.TRIPLE_BOW.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         (Item)TFItems.TRIPLE_BOW.get(),
         ResourceLocation.parse("pulling"),
         (stack, level, entity, idk) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register((Item)TFItems.ORE_MAGNET.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
         if (entity == null) {
            return 0.0F;
         }

         ItemStack itemstack = entity.getUseItem();
         return !itemstack.isEmpty() ? (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F : 0.0F;
      });
      ItemProperties.register(((Block)TFBlocks.RED_THREAD.get()).asItem(), TwilightForestMod.prefix("size"), (stack, level, entity, idk) -> {
         if (stack.getCount() >= 32) {
            return 1.0F;
         } else if (stack.getCount() >= 16) {
            return 0.5F;
         } else {
            return stack.getCount() >= 4 ? 0.25F : 0.0F;
         }
      });
      ItemProperties.register(
         (Item)TFItems.ORE_MAGNET.get(),
         ResourceLocation.parse("pulling"),
         (stack, level, entity, idk) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         (Item)TFItems.BLOCK_AND_CHAIN.get(),
         TwilightForestMod.prefix("thrown"),
         (stack, level, entity, idk) -> stack.get(TFDataComponents.THROWN_PROJECTILE) != null ? 1.0F : 0.0F
      );
      ItemProperties.register(
         (Item)TFItems.EXPERIMENT_115.get(),
         Experiment115Item.THINK,
         (stack, level, entity, idk) -> stack.get(TFDataComponents.EXPERIMENT_115_VARIANTS) != null
               && ((String)stack.get(TFDataComponents.EXPERIMENT_115_VARIANTS)).equals("think")
            ? 1.0F
            : 0.0F
      );
      ItemProperties.register(
         (Item)TFItems.EXPERIMENT_115.get(),
         Experiment115Item.FULL,
         (stack, level, entity, idk) -> stack.get(TFDataComponents.EXPERIMENT_115_VARIANTS) != null
               && ((String)stack.get(TFDataComponents.EXPERIMENT_115_VARIANTS)).equals("full")
            ? 1.0F
            : 0.0F
      );
      ItemProperties.register(
         (Item)TFItems.BRITTLE_FLASK.get(),
         TwilightForestMod.prefix("breakage"),
         (stack, level, entity, i) -> ((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).breakage()
      );
      ItemProperties.register(
         (Item)TFItems.BRITTLE_FLASK.get(),
         TwilightForestMod.prefix("potion_level"),
         (stack, level, entity, i) -> ((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).doses()
      );
      ItemProperties.register(
         (Item)TFItems.GREATER_FLASK.get(),
         TwilightForestMod.prefix("potion_level"),
         (stack, level, entity, i) -> ((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).doses()
      );
      ItemProperties.register(
         (Item)TFItems.CRUMBLE_HORN.get(),
         TwilightForestMod.prefix("tooting"),
         (stack, world, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      Predicate<ResourceLocation> mippedIDs = location -> location.getNamespace().equals("twilightforest")
         && (
            location.getPath().contains("leaves") && !location.getPath().contains("dark")
               || location.getPath().contains("_bush")
               || location.getPath().contains("_oreberry")
         );
      Map<ModelResourceLocation, BakedModel> models = event.getModels();
      List<Entry<ModelResourceLocation, BakedModel>> mippedModels = models.entrySet().stream().filter(entry -> mippedIDs.test(entry.getKey().id())).toList();
      mippedModels.forEach(entry -> models.put(entry.getKey(), new ConditionalMippedModel(entry.getValue())));
      BakedModel oldModel = (BakedModel)event.getModels().get(ModelResourceLocation.inventory(TwilightForestMod.prefix("trollsteinn")));
      models.put(ModelResourceLocation.inventory(TwilightForestMod.prefix("trollsteinn")), new TrollsteinnModel(oldModel));
      BakedModel defaultReactorDebrisModel = (BakedModel)event.getModels().get(ModelResourceLocation.vanilla("netherrack", ""));
      models.put(new ModelResourceLocation(TwilightForestMod.prefix("reactor_debris"), ""), new ReactorDebrisModel(defaultReactorDebrisModel));
   }

   private void registerAdditionalModels(RegisterAdditional event) {
      event.register(ShieldLayer.LOC);
      event.register(ModelResourceLocation.standalone(TwilightForestMod.prefix("item/trophy")));
      event.register(ModelResourceLocation.standalone(TwilightForestMod.prefix("item/trophy_minor")));
      event.register(ModelResourceLocation.standalone(TwilightForestMod.prefix("item/trophy_quest")));
      event.register(TrollsteinnModel.LIT_TROLLSTEINN);

      for (JarRenderer.LidResource lid : (List)JarRenderer.LID_LOCATION_LIST.get()) {
         ResourceLocation location = lid.resourceLocation();
         String name = location.getPath();
         if (lid.customPath() != null) {
            name = lid.customPath();
         }

         event.register(ModelResourceLocation.standalone(TwilightForestMod.prefix("block/lid/" + name)));
      }
   }

   private void cacheJarLids(BakingCompleted event) {
      ((List)JarRenderer.LID_LOCATION_LIST.get()).forEach(lid -> {
         String name = lid.resourceLocation().getPath();
         if (lid.customPath() != null) {
            name = lid.customPath();
         }

         JarRenderer.LIDS.put(lid.lid(), (BakedModel)event.getModels().get(ModelResourceLocation.standalone(TwilightForestMod.prefix("block/lid/" + name))));
      });
   }

   private void registerDimEffects(RegisterDimensionSpecialEffectsEvent event) {
      TFSkyRenderer.createStars();
      event.register(TFDimension.DIMENSION_RENDERER, new TwilightForestRenderInfo(128.0F, false, SkyType.NONE, false, false));
   }

   private void clientSetup(FMLClientSetupEvent evt) {
      try {
         Class.forName("net.optifine.Config");
         optifinePresent = true;
      } catch (ClassNotFoundException e) {
         optifinePresent = false;
      }

      evt.enqueueWork(() -> {
         Sheets.addWoodType(TFWoodTypes.TWILIGHT_OAK_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.CANOPY_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.MANGROVE_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.DARK_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.TIME_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.TRANSFORMATION_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.MINING_WOOD_TYPE);
         Sheets.addWoodType(TFWoodTypes.SORTING_WOOD_TYPE);
      });
   }

   private void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
      ((ReloadableResourceManager)Minecraft.getInstance().getResourceManager()).listeners.addFirst(JappaPackReloadListener.INSTANCE);
      MagicPaintingTextureManager.instance = new MagicPaintingTextureManager(Minecraft.getInstance().getTextureManager());
      event.registerReloadListener(MagicPaintingTextureManager.instance);
      event.registerReloadListener(TextureGeneratorReloadListener.INSTANCE);
      event.registerReloadListener(new TFArmorRenderer.ResourceReloadListener());
   }

   private void registerScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)TFMenuTypes.UNCRAFTING.get(), UncraftingScreen::new);
   }

   private void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)TFEntities.BOAR.get(), m -> new BoarRenderer(m, new BoarModel(m.bakeLayer(TFModelLayers.BOAR))));
      event.registerEntityRenderer(
         (EntityType)TFEntities.BIGHORN_SHEEP.get(), m -> new BighornRenderer(m, new BighornModel(m.bakeLayer(TFModelLayers.BIGHORN_SHEEP)), 0.7F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.DEER.get(), m -> new TFGenericMobRenderer(m, new DeerModel(m.bakeLayer(TFModelLayers.DEER)), 0.7F, "wilddeer.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.REDCAP.get(),
         m -> new TFBipedRenderer(
            m,
            new RedcapModel(m.bakeLayer(TFModelLayers.REDCAP)),
            new RedcapModel(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_INNER)),
            new RedcapModel(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_OUTER)),
            0.4F,
            "redcap.png"
         )
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.SKELETON_DRUID.get(),
         m -> new TFBipedRenderer(m, new SkeletonDruidModel(m.bakeLayer(TFModelLayers.SKELETON_DRUID)), 0.5F, "skeletondruid.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.HOSTILE_WOLF.get(), HostileWolfRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.WRAITH.get(), m -> new WraithRenderer(m, new WraithModel(m.bakeLayer(TFModelLayers.WRAITH)), 0.5F));
      event.registerEntityRenderer((EntityType)TFEntities.HYDRA.get(), m -> new HydraRenderer(m, new HydraModel(m.bakeLayer(TFModelLayers.HYDRA)), 4.0F));
      event.registerEntityRenderer((EntityType)TFEntities.LICH.get(), m -> new LichRenderer(m, new LichModel(m.bakeLayer(TFModelLayers.LICH)), 0.6F));
      event.registerEntityRenderer(
         (EntityType)TFEntities.PENGUIN.get(), m -> new BirdRenderer(m, new PenguinModel(m.bakeLayer(TFModelLayers.PENGUIN)), 0.375F, "penguin.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.LICH_MINION.get(),
         m -> new TFBipedRenderer(
            m,
            new LichMinionModel(m.bakeLayer(TFModelLayers.LICH_MINION)),
            new LichMinionModel(m.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
            new LichMinionModel(m.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
            0.5F,
            "textures/entity/zombie/zombie.png"
         )
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.LOYAL_ZOMBIE.get(),
         m -> new TFBipedRenderer(
            m,
            new LoyalZombieModel(m.bakeLayer(TFModelLayers.LOYAL_ZOMBIE)),
            new LoyalZombieModel(m.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
            new LoyalZombieModel(m.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
            0.5F,
            "textures/entity/zombie/zombie.png"
         )
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.TINY_BIRD.get(), m -> new TinyBirdRenderer(m, new TinyBirdModel(m.bakeLayer(TFModelLayers.TINY_BIRD)), 0.3F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.SQUIRREL.get(), m -> new TFGenericMobRenderer(m, new SquirrelModel(m.bakeLayer(TFModelLayers.SQUIRREL)), 0.3F, "squirrel2.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.DWARF_RABBIT.get(), m -> new BunnyRenderer(m, new BunnyModel(m.bakeLayer(TFModelLayers.BUNNY)), 0.3F));
      event.registerEntityRenderer(
         (EntityType)TFEntities.RAVEN.get(), m -> new BirdRenderer(m, new RavenModel(m.bakeLayer(TFModelLayers.RAVEN)), 0.3F, "raven.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.QUEST_RAM.get(), m -> new QuestRamRenderer(m, new QuestRamModel(m.bakeLayer(TFModelLayers.QUEST_RAM)))
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.KOBOLD.get(), m -> new TFBipedRenderer(m, new KoboldModel(m.bakeLayer(TFModelLayers.KOBOLD)), 0.4F, "kobold.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.MOSQUITO_SWARM.get(), MosquitoSwarmRenderer::new);
      event.registerEntityRenderer(
         (EntityType)TFEntities.DEATH_TOME.get(),
         m -> new TFGenericMobRenderer(m, new DeathTomeModel(m.bakeLayer(TFModelLayers.DEATH_TOME)), 0.3F, "textures/entity/enchanting_table_book.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.MINOTAUR.get(), m -> new TFBipedRenderer(m, new MinotaurModel(m.bakeLayer(TFModelLayers.MINOTAUR)), 0.625F, "minotaur.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.MINOSHROOM.get(), m -> new MinoshroomRenderer(m, new MinoshroomModel(m.bakeLayer(TFModelLayers.MINOSHROOM)), 0.625F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.FIRE_BEETLE.get(),
         m -> new TFGenericMobRenderer(m, new FireBeetleModel(m.bakeLayer(TFModelLayers.FIRE_BEETLE)), 0.8F, "firebeetle.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.SLIME_BEETLE.get(),
         m -> new SlimeBeetleRenderer(m, new SlimeBeetleModel(m.bakeLayer(TFModelLayers.SLIME_BEETLE)), m.bakeLayer(TFModelLayers.SLIME_BEETLE_TAIL), 0.6F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.PINCH_BEETLE.get(),
         m -> new TFGenericMobRenderer(m, new PinchBeetleModel(m.bakeLayer(TFModelLayers.PINCH_BEETLE)), 0.6F, "pinchbeetle.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.MIST_WOLF.get(), MistWolfRenderer::new);
      event.registerEntityRenderer(
         (EntityType)TFEntities.CARMINITE_GHASTLING.get(),
         m -> new TFGhastRenderer(m, new TFGhastModel(m.bakeLayer(TFModelLayers.CARMINITE_GHASTLING)), 0.625F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.CARMINITE_GOLEM.get(),
         m -> new CarminiteGolemRenderer(m, new CarminiteGolemModel(m.bakeLayer(TFModelLayers.CARMINITE_GOLEM)), 0.75F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.TOWERWOOD_BORER.get(),
         m -> new TFGenericMobRenderer(m, new SilverfishModel(m.bakeLayer(ModelLayers.SILVERFISH)), 0.3F, "towertermite.png")
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.CARMINITE_GHASTGUARD.get(),
         m -> new CarminiteGhastRenderer(m, new TFGhastModel(m.bakeLayer(TFModelLayers.CARMINITE_GHASTGUARD)), 3.0F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.UR_GHAST.get(),
         m -> new UrGhastRenderer<UrGhast, UrGhastModel>(m, new UrGhastModel(m.bakeLayer(TFModelLayers.UR_GHAST)), 8.0F, 24.0F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.BLOCKCHAIN_GOBLIN.get(),
         m -> new BlockChainGoblinRenderer(m, new BlockChainGoblinModel(m.bakeLayer(TFModelLayers.BLOCKCHAIN_GOBLIN)), 0.4F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.UPPER_GOBLIN_KNIGHT.get(),
         m -> new UpperGoblinKnightRenderer(m, new UpperGoblinKnightModel(m.bakeLayer(TFModelLayers.UPPER_GOBLIN_KNIGHT)), 0.625F)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.LOWER_GOBLIN_KNIGHT.get(),
         m -> new TFBipedRenderer(m, new LowerGoblinKnightModel(m.bakeLayer(TFModelLayers.LOWER_GOBLIN_KNIGHT)), 0.625F, "doublegoblin.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.HELMET_CRAB.get(), HelmetCrabRenderer::new);
      event.registerEntityRenderer(
         (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
         m -> new KnightPhantomRenderer(m, new KnightPhantomModel(m.bakeLayer(TFModelLayers.KNIGHT_PHANTOM)), 0.625F)
      );
      event.registerEntityRenderer((EntityType)TFEntities.NAGA.get(), m -> new NagaRenderer(m, new NagaModel(m.bakeLayer(TFModelLayers.NAGA)), 1.45F));
      event.registerEntityRenderer((EntityType)TFEntities.SWARM_SPIDER.get(), m -> new TFSpiderRenderer(m, 0.25F, "swarmspider.png", 0.5F));
      event.registerEntityRenderer((EntityType)TFEntities.KING_SPIDER.get(), m -> new TFSpiderRenderer(m, 1.25F, "kingspider.png", 1.9F));
      event.registerEntityRenderer((EntityType)TFEntities.CARMINITE_BROODLING.get(), m -> new TFSpiderRenderer(m, 0.6F, "towerbroodling.png", 0.7F));
      event.registerEntityRenderer((EntityType)TFEntities.HEDGE_SPIDER.get(), m -> new TFSpiderRenderer(m, 0.8F, "hedgespider.png", 1.0F));
      event.registerEntityRenderer(
         (EntityType)TFEntities.REDCAP_SAPPER.get(),
         m -> new TFBipedRenderer(
            m,
            new RedcapModel(m.bakeLayer(TFModelLayers.REDCAP)),
            new RedcapModel(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_INNER)),
            new RedcapModel(m.bakeLayer(TFModelLayers.REDCAP_ARMOR_OUTER)),
            0.4F,
            "redcapsapper.png"
         )
      );
      event.registerEntityRenderer((EntityType)TFEntities.MAZE_SLIME.get(), m -> new MazeSlimeRenderer(m, 0.625F));
      event.registerEntityRenderer(
         (EntityType)TFEntities.YETI.get(), m -> new TFBipedRenderer(m, new YetiModel(m.bakeLayer(TFModelLayers.YETI)), 0.625F, "yeti2.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.PROTECTION_BOX.get(), ProtectionBoxRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.MAGIC_PAINTING.get(), MagicPaintingRenderer::new);
      event.registerEntityRenderer(
         (EntityType)TFEntities.ALPHA_YETI.get(),
         m -> new TFBipedRenderer(m, new AlphaYetiModel(m.bakeLayer(TFModelLayers.ALPHA_YETI)), 1.75F, "yetialpha.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.WINTER_WOLF.get(), WinterWolfRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.SNOW_GUARDIAN.get(), m -> new SnowGuardianRenderer(m, new NoopModel(m.bakeLayer(TFModelLayers.NOOP))));
      event.registerEntityRenderer(
         (EntityType)TFEntities.STABLE_ICE_CORE.get(), m -> new StableIceCoreRenderer(m, new StableIceCoreModel(m.bakeLayer(TFModelLayers.STABLE_ICE_CORE)))
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
         m -> new UnstableIceCoreRenderer(m, new UnstableIceCoreModel(m.bakeLayer(TFModelLayers.UNSTABLE_ICE_CORE)))
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.SNOW_QUEEN.get(), m -> new SnowQueenRenderer(m, new SnowQueenModel(m.bakeLayer(TFModelLayers.SNOW_QUEEN)))
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.TROLL.get(), m -> new TFBipedRenderer(m, new TrollModel(m.bakeLayer(TFModelLayers.TROLL)), 0.625F, "troll.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.GIANT_MINER.get(), TFGiantRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.ARMORED_GIANT.get(), TFGiantRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.ICE_CRYSTAL.get(), IceCrystalRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.CHAIN_BLOCK.get(), BlockChainRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.CUBE_OF_ANNIHILATION.get(), CubeOfAnnihilationRenderer::new);
      event.registerEntityRenderer(
         (EntityType)TFEntities.HARBINGER_CUBE.get(),
         m -> new TFGenericMobRenderer(m, new HarbingerCubeModel(m.bakeLayer(TFModelLayers.HARBINGER_CUBE)), 1.0F, "apocalypse2.png")
      );
      event.registerEntityRenderer((EntityType)TFEntities.ADHERENT.get(), AdherentRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.ROVING_CUBE.get(), RovingCubeRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.RISING_ZOMBIE.get(), RisingZombieRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.PLATEAU_BOSS.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.NATURE_BOLT.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer(
         (EntityType)TFEntities.LICH_BOLT.get(),
         c -> new CustomProjectileTextureRenderer(c, TwilightForestMod.prefix("textures/particle/twilight_orb.png"), 1.0F, true, false)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.WAND_BOLT.get(),
         c -> new CustomProjectileTextureRenderer(c, TwilightForestMod.prefix("textures/particle/twilight_orb.png"), 1.0F, true, false)
      );
      event.registerEntityRenderer(
         (EntityType)TFEntities.LICH_BOMB.get(),
         c -> new CustomProjectileTextureRenderer(c, ResourceLocation.withDefaultNamespace("textures/item/magma_cream.png"), 1.0F, true, true)
      );
      event.registerEntityRenderer((EntityType)TFEntities.TOME_BOLT.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.HYDRA_MORTAR.get(), HydraMortarRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.SLIME_BLOB.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.MOONWORM_SHOT.get(), MoonwormShotRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.CHARM_EFFECT.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.THROWN_WEP.get(), ThrownWepRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.FALLING_ICE.get(), FallingIceRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.THROWN_ICE.get(), ThrownIceRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.THROWN_BLOCK.get(), ThrownBlockRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.ICE_SNOWBALL.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.SLIDER.get(), SlideBlockRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.SEEKER_ARROW.get(), DefaultArrowRenderer::new);
      event.registerEntityRenderer((EntityType)TFEntities.ICE_ARROW.get(), DefaultArrowRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.FIREFLY.get(), FireflyRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.CICADA.get(), CicadaRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.MOONWORM.get(), MoonwormRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.TROPHY.get(), TrophyRenderer::new);
      event.registerBlockEntityRenderer(BlockEntityType.CHEST, TFChestRenderer::new);
      event.registerBlockEntityRenderer(BlockEntityType.TRAPPED_CHEST, TFChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.SKULL_CHEST.get(), SkullChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.KEEPSAKE_CASKET.get(), KeepsakeCasketRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.SKULL_CANDLE.get(), SkullCandleRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.REACTOR_DEBRIS.get(), ReactorDebrisRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.RED_THREAD.get(), RedThreadRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.CANDELABRA.get(), CandelabraRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.JAR.get(), JarRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.MASON_JAR.get(), JarRenderer.MasonJarRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.OMINOUS_CANDLE.get(), OminousCandleRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.SINISTER_SPAWNER.get(), SinisterSpawnerRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.BRAZIER.get(), BrazierRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TFBlockEntities.DRYING_RACK.get(), DryingRackRenderer::new);
   }

   private void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(
         TFModelLayers.ARCTIC_ARMOR_INNER, () -> LayerDefinition.create(ArcticArmorModel.addPieces(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.ARCTIC_ARMOR_OUTER, () -> LayerDefinition.create(ArcticArmorModel.addPieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.FIERY_ARMOR_INNER, () -> LayerDefinition.create(FieryArmorModel.createMesh(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0.0F), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.FIERY_ARMOR_OUTER, () -> LayerDefinition.create(FieryArmorModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.TRAVELLERS_ARMOR_HELMET,
         () -> LayerDefinition.create(TravellersGearModels.addGogglePieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.TRAVELLERS_ARMOR_CHEST_GLOVES,
         () -> LayerDefinition.create(TravellersGearModels.addGlovePieces(new CubeDeformation(0.295F), false), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.TRAVELLERS_ARMOR_CHEST_GLOVES_SLIM,
         () -> LayerDefinition.create(TravellersGearModels.addGlovePieces(new CubeDeformation(0.295F), true), 64, 32)
      );
      event.registerLayerDefinition(TFModelLayers.TRAVELLERS_ARMOR_LEGGINGS, () -> TravellersWingsModel.createLayer(0.25F));
      event.registerLayerDefinition(
         TFModelLayers.TRAVELLERS_ARMOR_BOOTS, () -> LayerDefinition.create(TravellersGearModels.addBootPieces(new CubeDeformation(0.5F)), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.KNIGHTMETAL_ARMOR_INNER, () -> LayerDefinition.create(KnightmetalArmorModel.addPieces(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.KNIGHTMETAL_ARMOR_OUTER, () -> LayerDefinition.create(KnightmetalArmorModel.addPieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.PHANTOM_ARMOR_INNER, () -> LayerDefinition.create(PhantomArmorModel.addPieces(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.PHANTOM_ARMOR_OUTER, () -> LayerDefinition.create(PhantomArmorModel.addPieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.YETI_ARMOR_INNER, () -> LayerDefinition.create(YetiArmorModel.addPieces(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.YETI_ARMOR_OUTER, () -> LayerDefinition.create(YetiArmorModel.addPieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      event.registerLayerDefinition(TFModelLayers.ALPHA_YETI_TROPHY, AlphaYetiModel::createTrophy);
      event.registerLayerDefinition(TFModelLayers.HYDRA_TROPHY, HydraHeadModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.KNIGHT_PHANTOM_TROPHY, KnightPhantomModel::createTrophy);
      event.registerLayerDefinition(TFModelLayers.LICH_TROPHY, LichModel::create);
      event.registerLayerDefinition(TFModelLayers.MINOSHROOM_TROPHY, MinoshroomModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.NAGA_TROPHY, NagaModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.QUEST_RAM_TROPHY, QuestRamModel::checkForPackTrophyEdition);
      event.registerLayerDefinition(TFModelLayers.SNOW_QUEEN_TROPHY, SnowQueenModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.UR_GHAST_TROPHY, UrGhastModel::create);
      event.registerLayerDefinition(TFModelLayers.ADHERENT, AdherentModel::create);
      event.registerLayerDefinition(TFModelLayers.ALPHA_YETI, AlphaYetiModel::create);
      event.registerLayerDefinition(TFModelLayers.ARMORED_GIANT, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 32));
      event.registerLayerDefinition(TFModelLayers.BIGHORN_SHEEP, BighornModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.BLOCKCHAIN_GOBLIN, BlockChainGoblinModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.BOAR, BoarModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.BUNNY, BunnyModel::create);
      event.registerLayerDefinition(TFModelLayers.CARMINITE_BROODLING, SpiderModel::createSpiderBodyLayer);
      event.registerLayerDefinition(TFModelLayers.CARMINITE_GOLEM, CarminiteGolemModel::create);
      event.registerLayerDefinition(TFModelLayers.CARMINITE_GHASTGUARD, TFGhastModel::create);
      event.registerLayerDefinition(TFModelLayers.CARMINITE_GHASTLING, TFGhastModel::create);
      event.registerLayerDefinition(TFModelLayers.CHAIN, ChainModel::create);
      event.registerLayerDefinition(TFModelLayers.CUBE_OF_ANNIHILATION, CubeOfAnnihilationModel::create);
      event.registerLayerDefinition(TFModelLayers.DEATH_TOME, DeathTomeModel::create);
      event.registerLayerDefinition(TFModelLayers.DEER, DeerModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.FIRE_BEETLE, FireBeetleModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.GIANT_MINER, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 32));
      event.registerLayerDefinition(TFModelLayers.HARBINGER_CUBE, HarbingerCubeModel::create);
      event.registerLayerDefinition(TFModelLayers.HEDGE_SPIDER, SpiderModel::createSpiderBodyLayer);
      event.registerLayerDefinition(TFModelLayers.HELMET_CRAB, HelmetCrabModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.HOSTILE_WOLF, () -> LayerDefinition.create(WolfModel.createMeshDefinition(CubeDeformation.NONE), 64, 32));
      event.registerLayerDefinition(TFModelLayers.HYDRA_HEAD, HydraHeadModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.HYDRA, HydraModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.HYDRA_MORTAR, HydraMortarModel::create);
      event.registerLayerDefinition(TFModelLayers.HYDRA_NECK, HydraNeckModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.ICE_CRYSTAL, IceCrystalModel::create);
      event.registerLayerDefinition(TFModelLayers.KING_SPIDER, SpiderModel::createSpiderBodyLayer);
      event.registerLayerDefinition(TFModelLayers.KNIGHT_PHANTOM, KnightPhantomModel::create);
      event.registerLayerDefinition(TFModelLayers.KOBOLD, KoboldModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.LICH_MINION, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64));
      event.registerLayerDefinition(TFModelLayers.LICH, LichModel::create);
      event.registerLayerDefinition(TFModelLayers.LOWER_GOBLIN_KNIGHT, LowerGoblinKnightModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.LOYAL_ZOMBIE, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64));
      event.registerLayerDefinition(TFModelLayers.MAZE_SLIME, SlimeModel::createInnerBodyLayer);
      event.registerLayerDefinition(TFModelLayers.MAZE_SLIME_OUTER, SlimeModel::createOuterBodyLayer);
      event.registerLayerDefinition(TFModelLayers.MINOSHROOM, MinoshroomModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.MINOTAUR, MinotaurModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.MIST_WOLF, () -> LayerDefinition.create(WolfModel.createMeshDefinition(CubeDeformation.NONE), 64, 32));
      event.registerLayerDefinition(TFModelLayers.MOSQUITO_SWARM, MosquitoSwarmModel::create);
      event.registerLayerDefinition(TFModelLayers.NAGA, NagaModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.NAGA_BODY, NagaModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.NOOP, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 0, 0));
      event.registerLayerDefinition(TFModelLayers.PENGUIN, PenguinModel::create);
      event.registerLayerDefinition(TFModelLayers.PINCH_BEETLE, PinchBeetleModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.PROTECTION_BOX, () -> LayerDefinition.create(ProtectionBoxModel.createMesh(), 16, 16));
      event.registerLayerDefinition(TFModelLayers.QUEST_RAM, QuestRamModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.RAVEN, RavenModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.REDCAP, RedcapModel::checkForPack);
      event.registerLayerDefinition(
         TFModelLayers.REDCAP_ARMOR_INNER, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.7F), 64, 32)
      );
      event.registerLayerDefinition(
         TFModelLayers.REDCAP_ARMOR_OUTER, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.65F), 0.7F), 64, 32)
      );
      event.registerLayerDefinition(TFModelLayers.RISING_ZOMBIE, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64));
      event.registerLayerDefinition(TFModelLayers.ROVING_CUBE, CubeOfAnnihilationModel::create);
      event.registerLayerDefinition(TFModelLayers.SKELETON_DRUID, SkeletonDruidModel::create);
      event.registerLayerDefinition(TFModelLayers.SLIME_BEETLE, SlimeBeetleModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.SLIME_BEETLE_TAIL, SlimeBeetleModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.SNOW_QUEEN, SnowQueenModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.CHAIN_BLOCK, SpikeBlockModel::create);
      event.registerLayerDefinition(TFModelLayers.SQUIRREL, SquirrelModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.STABLE_ICE_CORE, UnstableIceCoreModel::create);
      event.registerLayerDefinition(TFModelLayers.SWARM_SPIDER, SpiderModel::createSpiderBodyLayer);
      event.registerLayerDefinition(TFModelLayers.TINY_BIRD, TinyBirdModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.TOWERWOOD_BORER, SilverfishModel::createBodyLayer);
      event.registerLayerDefinition(TFModelLayers.TROLL, TrollModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.UNSTABLE_ICE_CORE, UnstableIceCoreModel::create);
      event.registerLayerDefinition(TFModelLayers.UPPER_GOBLIN_KNIGHT, UpperGoblinKnightModel::checkForPack);
      event.registerLayerDefinition(TFModelLayers.UR_GHAST, UrGhastModel::create);
      event.registerLayerDefinition(TFModelLayers.WINTER_WOLF, () -> LayerDefinition.create(WolfModel.createMeshDefinition(CubeDeformation.NONE), 64, 32));
      event.registerLayerDefinition(TFModelLayers.WRAITH, WraithModel::create);
      event.registerLayerDefinition(TFModelLayers.YETI, YetiModel::create);
      event.registerLayerDefinition(TFModelLayers.CICADA, CicadaModel::create);
      event.registerLayerDefinition(TFModelLayers.FIREFLY, FireflyModel::create);
      event.registerLayerDefinition(TFModelLayers.KEEPSAKE_CASKET, () -> SkullChestRenderer.create(true));
      event.registerLayerDefinition(TFModelLayers.SKULL_CHEST, () -> SkullChestRenderer.create(false));
      event.registerLayerDefinition(TFModelLayers.MOONWORM, MoonwormModel::create);
      event.registerLayerDefinition(TFModelLayers.BRAZIER, BrazierModel::create);
      event.registerLayerDefinition(TFModelLayers.RED_THREAD, RedThreadModel::create);
      event.registerLayerDefinition(TFModelLayers.KNIGHTMETAL_SHIELD, KnightmetalShieldModel::create);
   }

   private void registerParticleFactories(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)TFParticleType.LARGE_FLAME.get(), LargeFlameParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.LEAF_RUNE.get(), LeafRuneParticle.Factory::new);
      event.registerSpecial((ParticleType)TFParticleType.BOSS_TEAR.get(), new GhastTearParticle.Factory());
      event.registerSpriteSet((ParticleType)TFParticleType.GHAST_TRAP.get(), GhastTrapParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.PROTECTION.get(), ProtectionParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.SNOW.get(), SnowParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.SNOW_GUARDIAN.get(), SnowGuardianParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.SNOW_WARNING.get(), SnowWarningParticle.SimpleFactory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.EXTENDED_SNOW_WARNING.get(), SnowWarningParticle.ExtendedFactory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.ICE_BEAM.get(), IceBeamParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.ANNIHILATE.get(), AnnihilateParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.PERFECT_DODGE.get(), PerfectDodgeParticle.Provider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.DOUBLE_JUMP.get(), DoubleJumpParticle.Provider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.HUGE_SMOKE.get(), SmokeScaleParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.FIREFLY.get(), FireflyParticle.StationaryProvider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.WANDERING_FIREFLY.get(), FireflyParticle.WanderingProvider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.PARTICLE_SPAWNER_FIREFLY.get(), FireflyParticle.ParticleSpawnerProvider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.FALLEN_LEAF.get(), LeafParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.DIM_FLAME.get(), SmallFlameProvider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.OMINOUS_FLAME.get(), SmallFlameProvider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.SORTING_PARTICLE.get(), SortingParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.TRANSFORMATION_PARTICLE.get(), TransformationParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.LOG_CORE_PARTICLE.get(), LogCoreParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.CLOUD_PUFF.get(), CloudPuffParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.DRYING_RACK.get(), DryingRackParticle.Provider::new);
      event.registerSpriteSet((ParticleType)TFParticleType.MAGIC_EFFECT.get(), MagicEffectParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.ANGRY_LICH.get(), AngryLichParticle.Factory::new);
      event.registerSpriteSet((ParticleType)TFParticleType.TWILIGHT_ORB.get(), sprite -> new CustomTextureParticle.Factory(sprite, true));
      event.registerSpriteSet((ParticleType)TFParticleType.SHIELD_BREAK.get(), CustomTextureParticle.ShieldBreak::new);
   }

   private void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerBlock(
         new IClientBlockExtensions() {
            public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
               if (level.random.nextBoolean() && target instanceof BlockHitResult hitResult) {
                  BlockPos pos = hitResult.getBlockPos();
                  BlockState blockstate = level.getBlockState(pos);
                  if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                     Direction side = hitResult.getDirection();
                     int posX = pos.getX();
                     int posY = pos.getY();
                     int posZ = pos.getZ();
                     AABB aabb = blockstate.getShape(level, pos).bounds();
                     double x = posX + level.random.nextDouble() * (aabb.maxX - aabb.minX - 0.2F) + 0.1F + aabb.minX;
                     double y = posY + level.random.nextDouble() * (aabb.maxY - aabb.minY - 0.2F) + 0.1F + aabb.minY;
                     double z = posZ + level.random.nextDouble() * (aabb.maxZ - aabb.minZ - 0.2F) + 0.1F + aabb.minZ;
                     if (side == Direction.DOWN) {
                        y = posY + aabb.minY - 0.1F;
                     }

                     if (side == Direction.UP) {
                        y = posY + aabb.maxY + 0.1F;
                     }

                     if (side == Direction.NORTH) {
                        z = posZ + aabb.minZ - 0.1F;
                     }

                     if (side == Direction.SOUTH) {
                        z = posZ + aabb.maxZ + 0.1F;
                     }

                     if (side == Direction.WEST) {
                        x = posX + aabb.minX - 0.1F;
                     }

                     if (side == Direction.EAST) {
                        x = posX + aabb.maxX + 0.1F;
                     }

                     Particle particle = Minecraft.getInstance()
                        .particleEngine
                        .createParticle(
                           (ParticleOptions)TFParticleType.CLOUD_PUFF.get(), x, y, z, side.getStepX() * 0.01, side.getStepY() * 0.01, side.getStepZ() * 0.01
                        );
                     if (particle == null) {
                        return true;
                     }

                     manager.add(particle);
                  }
               }

               return true;
            }

            public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
               state.getShape(level, pos)
                  .forAllBoxes(
                     (boxX, boxY, boxZ, boxX1, boxY1, boxZ1) -> {
                        double xSize = Math.min(1.0, boxX1 - boxX);
                        double ySize = Math.min(1.0, boxY1 - boxY);
                        double zSize = Math.min(1.0, boxZ1 - boxZ);
                        int xMax = Math.max(2, Mth.ceil(xSize / 0.25));
                        int yMax = Math.max(2, Mth.ceil(ySize / 0.25));
                        int zMax = Math.max(2, Mth.ceil(zSize / 0.25));

                        for (int xSlice = 0; xSlice < xMax; xSlice++) {
                           if (level.random.nextInt(3) != 1) {
                              for (int ySlice = 0; ySlice < yMax; ySlice++) {
                                 if (level.random.nextInt(3) != 1) {
                                    for (int zSlice = 0; zSlice < zMax; zSlice++) {
                                       if (level.random.nextInt(3) != 1) {
                                          double speedX = (xSlice + 0.5) / xMax;
                                          double speedY = (ySlice + 0.5) / yMax;
                                          double speedZ = (zSlice + 0.5) / zMax;
                                          double x = speedX * xSize + boxX;
                                          double y = speedY * ySize + boxY;
                                          double z = speedZ * zSize + boxZ;
                                          speedX = (speedX - 0.5) * 0.05;
                                          speedY = (speedY - 0.5) * 0.05;
                                          speedZ = (speedZ - 0.5) * 0.05;
                                          Particle particle = Minecraft.getInstance()
                                             .particleEngine
                                             .createParticle(
                                                (ParticleOptions)TFParticleType.CLOUD_PUFF.get(),
                                                pos.getX() + x,
                                                pos.getY() + y,
                                                pos.getZ() + z,
                                                speedX,
                                                speedY,
                                                speedZ
                                             );
                                          if (particle == null) {
                                             return;
                                          }

                                          manager.add(particle);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  );
               return true;
            }
         },
         new Block[]{
            (Block)TFBlocks.WISPY_CLOUD.get(), (Block)TFBlocks.RAINY_CLOUD.get(), (Block)TFBlocks.SNOWY_CLOUD.get(), (Block)TFBlocks.FLUFFY_CLOUD.get()
         }
      );
      event.registerItem(
         ISTER.CLIENT_ITEM_EXTENSION,
         new Item[]{
            TFBlocks.CICADA.asItem(),
            TFBlocks.FIREFLY.asItem(),
            TFBlocks.MOONWORM.asItem(),
            TFBlocks.SKULL_CHEST.asItem(),
            TFBlocks.KEEPSAKE_CASKET.asItem(),
            TFBlocks.CANDELABRA.asItem(),
            TFBlocks.BRAZIER.asItem(),
            (Item)TFItems.CICADA_JAR.get(),
            (Item)TFItems.FIREFLY_JAR.get(),
            (Item)TFItems.MASON_JAR.get(),
            (Item)TFItems.KNIGHTMETAL_SHIELD.get(),
            (Item)TFItems.MYSTIC_CROWN.value(),
            TFBlocks.TWILIGHT_OAK_CHEST.asItem(),
            TFBlocks.CANOPY_CHEST.asItem(),
            TFBlocks.MANGROVE_CHEST.asItem(),
            TFBlocks.DARK_CHEST.asItem(),
            TFBlocks.TIME_CHEST.asItem(),
            TFBlocks.TRANSFORMATION_CHEST.asItem(),
            TFBlocks.MINING_CHEST.asItem(),
            TFBlocks.SORTING_CHEST.asItem(),
            TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.asItem(),
            TFBlocks.CANOPY_TRAPPED_CHEST.asItem(),
            TFBlocks.MANGROVE_TRAPPED_CHEST.asItem(),
            TFBlocks.DARK_TRAPPED_CHEST.asItem(),
            TFBlocks.TIME_TRAPPED_CHEST.asItem(),
            TFBlocks.TRANSFORMATION_TRAPPED_CHEST.asItem(),
            TFBlocks.MINING_TRAPPED_CHEST.asItem(),
            TFBlocks.SORTING_TRAPPED_CHEST.asItem(),
            (Item)TFItems.NAGA_TROPHY.get(),
            (Item)TFItems.LICH_TROPHY.get(),
            (Item)TFItems.MINOSHROOM_TROPHY.get(),
            (Item)TFItems.HYDRA_TROPHY.get(),
            (Item)TFItems.KNIGHT_PHANTOM_TROPHY.get(),
            (Item)TFItems.UR_GHAST_TROPHY.get(),
            (Item)TFItems.ALPHA_YETI_TROPHY.get(),
            (Item)TFItems.SNOW_QUEEN_TROPHY.get(),
            (Item)TFItems.QUEST_RAM_TROPHY.get(),
            (Item)TFItems.CREEPER_SKULL_CANDLE.get(),
            (Item)TFItems.PIGLIN_SKULL_CANDLE.get(),
            (Item)TFItems.PLAYER_SKULL_CANDLE.get(),
            (Item)TFItems.SKELETON_SKULL_CANDLE.get(),
            (Item)TFItems.WITHER_SKELETON_SKULL_CANDLE.get(),
            (Item)TFItems.ZOMBIE_SKULL_CANDLE.get()
         }
      );
      event.registerItem(
         new ArcticArmorItem.ArmorRender(),
         new Item[]{
            (Item)TFItems.ARCTIC_HELMET.get(), (Item)TFItems.ARCTIC_CHESTPLATE.get(), (Item)TFItems.ARCTIC_LEGGINGS.get(), (Item)TFItems.ARCTIC_BOOTS.get()
         }
      );
      event.registerItem(
         new TFSimpleArmorRenderer(FieryArmorModel::new, TFModelLayers.FIERY_ARMOR_INNER, TFModelLayers.FIERY_ARMOR_OUTER),
         new Item[]{
            (Item)TFItems.FIERY_HELMET.get(), (Item)TFItems.FIERY_CHESTPLATE.get(), (Item)TFItems.FIERY_LEGGINGS.get(), (Item)TFItems.FIERY_BOOTS.get()
         }
      );
      event.registerItem(
         new TravellersArmorItem.ArmorRender(),
         new Item[]{
            (Item)TFItems.TRAVELLERS_GOGGLES.get(),
            (Item)TFItems.TRAVELLERS_VEST.get(),
            (Item)TFItems.TRAVELLERS_GLOVES.get(),
            (Item)TFItems.TRAVELLERS_WINGS.get(),
            (Item)TFItems.TRAVELLERS_BELT.get(),
            (Item)TFItems.TRAVELLERS_BOOTS.get()
         }
      );
      event.registerItem(
         new TFSimpleArmorRenderer(TFArmorModel::new, TFModelLayers.KNIGHTMETAL_ARMOR_INNER, TFModelLayers.KNIGHTMETAL_ARMOR_OUTER),
         new Item[]{
            (Item)TFItems.KNIGHTMETAL_HELMET.get(),
            (Item)TFItems.KNIGHTMETAL_CHESTPLATE.get(),
            (Item)TFItems.KNIGHTMETAL_LEGGINGS.get(),
            (Item)TFItems.KNIGHTMETAL_BOOTS.get()
         }
      );
      event.registerItem(
         new TFSimpleArmorRenderer(TFArmorModel::new, TFModelLayers.PHANTOM_ARMOR_INNER, TFModelLayers.PHANTOM_ARMOR_OUTER),
         new Item[]{(Item)TFItems.PHANTOM_HELMET.get(), (Item)TFItems.PHANTOM_CHESTPLATE.get()}
      );
      event.registerItem(
         new TFSimpleArmorRenderer(YetiArmorModel::new, TFModelLayers.YETI_ARMOR_INNER, TFModelLayers.YETI_ARMOR_OUTER),
         new Item[]{(Item)TFItems.YETI_HELMET.get(), (Item)TFItems.YETI_CHESTPLATE.get(), (Item)TFItems.YETI_LEGGINGS.get(), (Item)TFItems.YETI_BOOTS.get()}
      );
   }

   private void registerMapDecorators(RegisterMapDecorationRenderersEvent event) {
      event.register((MapDecorationType)MapDecorationTypes.PLAYER.value(), new MagicMapPlayerIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.QUEST_GROVE.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.NAGA_COURTYARD.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.LICH_TOWER.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.LABYRINTH.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.HYDRA_LAIR.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.KNIGHT_STRONGHOLD.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.DARK_TOWER.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.YETI_LAIR.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.AURORA_PALACE.get(), new ConqueredMapIconRenderer());
      event.register((MapDecorationType)TFMapDecorations.FINAL_CASTLE.get(), new ConqueredMapIconRenderer());
   }

   private void attachRenderLayers(AddLayers event) {
      BakedMultiPartRenderers.bakeMultiPartRenderers(event.getContext());

      for (EntityType<?> type : event.getEntityTypes()) {
         if (event.getRenderer(type) instanceof LivingEntityRenderer<?, ?> living) {
            this.attachRenderLayers(living);
         }
      }

      event.getSkins().forEach(renderer -> {
         LivingEntityRenderer<Player, EntityModel<Player>> skin = (LivingEntityRenderer<Player, EntityModel<Player>>)event.getSkin(renderer);
         this.attachRenderLayers(Objects.requireNonNull(skin));
      });
   }

   private <T extends LivingEntity, M extends EntityModel<T>> void attachRenderLayers(LivingEntityRenderer<T, M> renderer) {
      renderer.addLayer(new ShieldLayer(renderer));
      renderer.addLayer(new IceLayer(renderer));
   }

   public static boolean isOptifinePresent() {
      return optifinePresent;
   }

   private float isBroken(@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity livingEntity, int seed) {
      return stack.getDamageValue() >= stack.getMaxDamage() - 1 ? 1.0F : 0.0F;
   }
}
