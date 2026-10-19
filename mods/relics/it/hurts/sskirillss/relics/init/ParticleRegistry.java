package it.hurts.sskirillss.relics.init;

import it.hurts.sskirillss.relics.client.particles.BasicColoredParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = "relics", bus = Bus.MOD, value = Dist.CLIENT)
public class ParticleRegistry {
   public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, "relics");
   public static final DeferredHolder<ParticleType<?>, BasicColoredParticle.Type> BASIC_COLORED = PARTICLES.register(
      "basic_colored", BasicColoredParticle.Type::new
   );

   public static void register(IEventBus bus) {
      PARTICLES.register(bus);
   }

   @SubscribeEvent
   public static void onParticleRegistry(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)BASIC_COLORED.get(), BasicColoredParticle.Factory::new);
   }
}
