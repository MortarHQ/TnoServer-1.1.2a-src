package net.jadenxgamer.netherexp;

import net.jadenxgamer.netherexp.client.particle.BlackFlakeParticle;
import net.jadenxgamer.netherexp.client.particle.BurstParticle;
import net.jadenxgamer.netherexp.client.particle.CoilParticle;
import net.jadenxgamer.netherexp.client.particle.FallingParticle;
import net.jadenxgamer.netherexp.client.particle.GlacierEffectParticle;
import net.jadenxgamer.netherexp.client.particle.JNEDripHangParticle;
import net.jadenxgamer.netherexp.client.particle.JNEFallAndLandParticle;
import net.jadenxgamer.netherexp.client.particle.JNEPoofParticle;
import net.jadenxgamer.netherexp.client.particle.JNEPortalParticle;
import net.jadenxgamer.netherexp.client.particle.LodestoneDrippingParticle;
import net.jadenxgamer.netherexp.client.particle.ProximityFadeParticle;
import net.jadenxgamer.netherexp.client.particle.RedExplosionEmitterParticle;
import net.jadenxgamer.netherexp.client.particle.RedHazeParticle;
import net.jadenxgamer.netherexp.client.particle.SmogParticle;
import net.jadenxgamer.netherexp.client.particle.WindBlownParticle;
import net.jadenxgamer.netherexp.client.rendering.block_entity.DiscernmentGlassBlockRenderer;
import net.jadenxgamer.netherexp.client.rendering.block_entity.JNECampfireRenderer;
import net.jadenxgamer.netherexp.client.rendering.block_entity.SuspiciousSoulSandBlockRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.ApparitionRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.BansheeRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.BlackIcicleRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.CarcassRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.EctoSlabRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.JNEBlazeRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.PelletRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.PhasmoArrowRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.PhasmoPelletRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.PortalGlowRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.ShotgunPelletRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.SlugPelletRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.StampedeRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.VesselRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.WillOWispRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.WispRenderer;
import net.jadenxgamer.netherexp.client.rendering.item.PumpChargeShotgunModel;
import net.jadenxgamer.netherexp.client.rendering.item.ShotgunFistModel;
import net.jadenxgamer.netherexp.client.shader.NetherHeatDistortionPostprocessor;
import net.jadenxgamer.netherexp.client.shader.SoulGlassPostProcessor;
import net.jadenxgamer.netherexp.client.sound.InsideFluidAmbientSoundInstance;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.item.SanctumCompassItem;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle.SmallFlameProvider;
import net.minecraft.client.particle.SpellParticle.MobEffectProvider;
import net.minecraft.client.particle.SplashParticle.Provider;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType.Factory;

@Mod(value = "netherexp", dist = Dist.CLIENT)
public final class NetherExpClient {
   public static boolean shouldShowBetaPopup = false;

   public NetherExpClient(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
   }

   public static void registerRenderers() {
      EntityRenderers.register(JNEEntityType.WISP.get(), WispRenderer::new);
      EntityRenderers.register(JNEEntityType.APPARITION.get(), ApparitionRenderer::new);
      EntityRenderers.register(JNEEntityType.VESSEL.get(), VesselRenderer::new);
      EntityRenderers.register(JNEEntityType.ECTO_SLAB.get(), EctoSlabRenderer::new);
      EntityRenderers.register(JNEEntityType.BANSHEE.get(), BansheeRenderer::new);
      EntityRenderers.register(JNEEntityType.STAMPEDE.get(), StampedeRenderer::new);
      EntityRenderers.register(JNEEntityType.CARCASS.get(), CarcassRenderer::new);
      EntityRenderers.register(JNEEntityType.PHASMO_ARROW.get(), PhasmoArrowRenderer::new);
      EntityRenderers.register(JNEEntityType.BLACK_ICICLE.get(), BlackIcicleRenderer::new);
      EntityRenderers.register(JNEEntityType.SHOTGUN_PELLET.get(), ShotgunPelletRenderer::new);
      EntityRenderers.register(JNEEntityType.SLUG_PELLET.get(), SlugPelletRenderer::new);
      EntityRenderers.register(JNEEntityType.PHASMO_PELLET.get(), PhasmoPelletRenderer::new);
      EntityRenderers.register(JNEEntityType.WILL_O_WISP.get(), WillOWispRenderer::new);
      EntityRenderers.register(JNEEntityType.PORTAL_GLOW.get(), PortalGlowRenderer::new);
      EntityRenderers.register(JNEEntityType.ANTIDOTE.get(), ThrownItemRenderer::new);
      EntityRenderers.register(JNEEntityType.ANTIDOTE_EFFECT_CLOUD.get(), NoopRenderer::new);
      EntityRenderers.register(EntityType.BLAZE, JNEBlazeRenderer::new);
      BlockEntityRenderers.register(JNEBlockEntityType.SUSPICIOUS_SOUL_SAND.get(), SuspiciousSoulSandBlockRenderer::new);
      BlockEntityRenderers.register(JNEBlockEntityType.DISCERNMENT_GLASS.get(), DiscernmentGlassBlockRenderer::new);
      BlockEntityRenderers.register(JNEBlockEntityType.JNE_CAMPFIRE.get(), JNECampfireRenderer::new);
      ItemBlockRenderTypes.setRenderLayer((Fluid)JNEFluids.ECTOPLASM_SOURCE.get(), RenderType.translucent());
      ItemBlockRenderTypes.setRenderLayer((Fluid)JNEFluids.ECTOPLASM_FLOWING.get(), RenderType.translucent());
   }

   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(WispRenderer.WispModel.LAYER, WispRenderer.WispModel::createBodyLayer);
      event.registerLayerDefinition(ApparitionRenderer.ApparitionModel.LAYER, ApparitionRenderer.ApparitionModel::createBodyLayer);
      event.registerLayerDefinition(VesselRenderer.VesselModel.LAYER, VesselRenderer.VesselModel::createBodyLayer);
      event.registerLayerDefinition(EctoSlabRenderer.EctoSlabModel.LAYER, EctoSlabRenderer.EctoSlabModel::createBodyLayer);
      event.registerLayerDefinition(BansheeRenderer.BansheeModel.LAYER, BansheeRenderer.BansheeModel::createBodyLayer);
      event.registerLayerDefinition(StampedeRenderer.StampedeModel.LAYER, StampedeRenderer.StampedeModel::createBodyLayer);
      event.registerLayerDefinition(StampedeRenderer.StampedeModel.SADDLE_LAYER, StampedeRenderer.StampedeModel::createBodyLayer);
      event.registerLayerDefinition(CarcassRenderer.CarcassModel.LAYER, CarcassRenderer.CarcassModel::createBodyLayer);
      event.registerLayerDefinition(WillOWispRenderer.WillOWispModel.LAYER, WillOWispRenderer.WillOWispModel::createBodyLayer);
      event.registerLayerDefinition(WillOWispRenderer.WillOWispItemModel.LAYER, WillOWispRenderer.WillOWispItemModel::createOrbLayer);
      event.registerLayerDefinition(WillOWispRenderer.WillOWispItemModel.LAYER_HAND, WillOWispRenderer.WillOWispItemModel::createHandLayer);
      event.registerLayerDefinition(ShotgunFistModel.LAYER, ShotgunFistModel::createBodyLayer);
      event.registerLayerDefinition(PumpChargeShotgunModel.LAYER, PumpChargeShotgunModel::createBodyLayer);
      event.registerLayerDefinition(PelletRenderer.ShotgunPelletModel.LAYER, PelletRenderer.ShotgunPelletModel::createBodyLayer);
      event.registerLayerDefinition(PortalGlowRenderer.PortalGlowModel.LAYER, PortalGlowRenderer.PortalGlowModel::createBodyLayer);
   }

   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SOUL_SWIRL_POP.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.TINT_SWIRL_POP.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.WISP.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), SmallFlameProvider::new);
      event.registerSpriteSet(JNEParticleTypes.IMMUNITY_EFFECT.get(), MobEffectProvider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SOUL_MAGMA.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.CRIMSON_SMOG.get(), SmogParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.WARPED_SMOG.get(), SmogParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BLACK_SMOKE.get(), SmogParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.WHITE_SMOKE.get(), SmogParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BLACK_FLAKE.get(), BlackFlakeParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.DRIPPING_ECTOPLASM.get(), JNEDripHangParticle.EctoplasmProvider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.FALLING_ECTOPLASM.get(), JNEFallAndLandParticle.EctoplasmProvider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.ECTOSPLASH.get(), Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.ECTOPLASM_RAYS.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.WIND_TRAIL.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.GLOWING_DOT.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.GLOWING_DOT_COIL.get(), CoilParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SOUL_CLOUD.get(), JNEPoofParticle.SoulProvider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SILVER_GLIMMER.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.TREACHEROUS_FLAME.get(), net.minecraft.client.particle.FlameParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.POSSESSION.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SHOTGUN_SPARK.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.NETHER_FOG.get(), ProximityFadeParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.LIGHTSPORE.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.NIGHTSPORE.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.WINDY_ASH.get(), WindBlownParticle.Provider::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.DRIFTING_SOUL.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.WILL_O_WISP_IMPACT.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.REDUX_DUST_BLOB.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.REDUX_DUST_STAR.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.REDUX_POOF.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.REDUX_POOF_BLOB.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.REDUX_POOF_STAR.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.GENERIC_GLOW.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SPARKLE.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.SHOTGUN_FLASH.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.PUMP_SHOTGUN_FLASH.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.PELLET_HIT.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.RED_EXPLOSION.get(), net.minecraft.client.particle.HugeExplosionParticle.Provider::new);
      event.registerSpecial((ParticleType)JNEParticleTypes.RED_EXPLOSION_EMITTER.get(), new RedExplosionEmitterParticle.Factory());
      event.registerSpriteSet((ParticleType)JNEParticleTypes.JNE_PORTAL.get(), JNEPortalParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.PORTAL_MIST.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.ECTO_SHARD.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.LARGE_BURST.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BURN_DROPLET.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BURN_SIDE.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BURN_DROPLET_TINT.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BURN_SIDE_TINT.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.RED_HAZE.get(), RedHazeParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.RED_SPARKLE.get(), FallingParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.CANDLE_BURST.get(), BurstParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BLOT_DROP.get(), LodestoneDrippingParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.GLACIER_EFFECT.get(), GlacierEffectParticle.Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.BLACK_FLAKE_WORLD.get(), Factory::new);
      event.registerSpriteSet((ParticleType)JNEParticleTypes.COLD_FOG.get(), Factory::new);
   }

   public static void registerProperties() {
      ItemProperties.register(
         JNEItems.SANCTUM_COMPASS.get(),
         ResourceLocation.withDefaultNamespace("angle"),
         new CompassItemPropertyFunction((level, stack, entity) -> SanctumCompassItem.getStructurePosition(stack))
      );
   }

   public static class HandlePostShaders {
      public static void tick(Minecraft client) {
         Entity player = client.getCameraEntity();
         if (player != null) {
            BlockState state = player.level().getBlockState(client.gameRenderer.getMainCamera().getBlockPosition());
            Holder<Biome> biome = player.level().getBiome(client.gameRenderer.getMainCamera().getBlockPosition());
            SoulGlassPostProcessor.INSTANCE.setActive((Boolean)JNEConfigs.ENABLE_SOUL_GLASS_SCREEN_FILTER.get() && state.is(JNETags.Blocks.SOUL_GLASSES));
            SoulGlassPostProcessor.tick(state);
            NetherHeatDistortionPostprocessor.INSTANCE.setActive(NetherHeatDistortionPostprocessor.shouldEnable());
            NetherHeatDistortionPostprocessor.tick(client, player.level(), player, biome);
         }
      }
   }

   public static class SubmergedStates {
      public static boolean isInsideSoulGlass;
      public static boolean wasInsideSoulGlass;

      public static void tick() {
         Minecraft client = Minecraft.getInstance();
         Player player = client.player;
         if (player instanceof LocalPlayer localPlayer) {
            BlockPos eyePos = BlockPos.containing(player.getEyePosition());
            BlockState state = player.level().getBlockState(eyePos);
            updateSoulGlass(client, player.level(), localPlayer, state);
         }
      }

      private static void updateSoulGlass(Minecraft client, Level level, LocalPlayer player, BlockState state) {
         isInsideSoulGlass = state.is(JNETags.Blocks.SOUL_GLASSES);
         if (isInsideSoulGlass && !wasInsideSoulGlass) {
            level.playLocalSound(player, JNESoundEvents.SOUL_GLASS_ENTER.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
            client.getSoundManager().play(new InsideFluidAmbientSoundInstance(player, JNESoundEvents.SOUL_GLASS_SUBMERGED.get(), 0.8F, p -> isInsideSoulGlass));
         } else if (!isInsideSoulGlass && wasInsideSoulGlass) {
            level.playLocalSound(player, JNESoundEvents.SOUL_GLASS_EXIT.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
         }

         wasInsideSoulGlass = isInsideSoulGlass;
      }
   }
}
