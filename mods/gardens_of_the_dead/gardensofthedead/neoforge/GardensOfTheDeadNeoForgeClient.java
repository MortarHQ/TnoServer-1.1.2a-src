package gardensofthedead.neoforge;

import gardensofthedead.GardensOfTheDeadClient;
import gardensofthedead.client.particle.SoulblightSporeProvider;
import gardensofthedead.client.particle.WhistlecaneSmokeParticle;
import gardensofthedead.registry.ModBlockEntityTypes;
import gardensofthedead.registry.ModParticleTypes;
import gardensofthedead.registry.ModWoodTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

public class GardensOfTheDeadNeoForgeClient {
   public GardensOfTheDeadNeoForgeClient(IEventBus modBus) {
      GardensOfTheDeadClient.init();
      modBus.addListener(this::onClientSetup);
      modBus.addListener(this::onRegisterRenderers);
      modBus.addListener(this::onRegisterParticleProviders);
   }

   private void onClientSetup(FMLClientSetupEvent event) {
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntityTypes.SIGN.get(), SignRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntityTypes.HANGING_SIGN.get(), HangingSignRenderer::new);
   }

   private void onRegisterRenderers(RegisterRenderers event) {
      ModWoodTypes.register();
   }

   private void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)ModParticleTypes.SOULBLIGHT_SPORE.get(), SoulblightSporeProvider::new);
      event.registerSpriteSet((ParticleType)ModParticleTypes.WHISTLECANE_SMOKE.get(), WhistlecaneSmokeParticle.Provider::new);
   }
}
