package cc.sighs.oelib.registry.action;

import java.util.Objects;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public record ParticleProviderAction<T extends ParticleOptions>(ParticleType<T> type, ParticleProvider<T> provider) implements RegistrationAction {
   public ParticleProviderAction {
      Objects.requireNonNull(type, "type");
      Objects.requireNonNull(provider, "provider");
   }
}
