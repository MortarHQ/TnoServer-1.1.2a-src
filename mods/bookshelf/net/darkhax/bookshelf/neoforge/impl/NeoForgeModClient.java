package net.darkhax.bookshelf.neoforge.impl;

import java.util.function.BiConsumer;
import net.darkhax.bookshelf.common.api.registry.register.RegisterBlockEntityRenderer;
import net.darkhax.bookshelf.common.api.registry.register.RegisterParticleProviders;
import net.darkhax.bookshelf.common.api.service.Services;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

public class NeoForgeModClient {
   public NeoForgeModClient(IEventBus bus) {
      bus.addListener(this::registerParticles);
      bus.addListener(this::registerEntityRenderers);
   }

   private void registerParticles(RegisterParticleProvidersEvent event) {
      BiConsumer<SimpleParticleType, RegisterParticleProviders.SimpleParticleProviderBuilder> registerSimple = (type, builder) -> event.registerSpriteSet(
         type, builder::build
      );
      Services.CONTENT_PROVIDERS
         .get()
         .forEach(provider -> provider.registerParticleFactories(new RegisterParticleProviders(provider.contentNamespace(), registerSimple)));
   }

   private void registerEntityRenderers(RegisterRenderers event) {
      RegisterBlockEntityRenderer.bindBlockEntityRenderers();
   }
}
