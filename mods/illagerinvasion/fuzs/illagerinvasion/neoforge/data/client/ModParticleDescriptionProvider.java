package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractParticleDescriptionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import net.minecraft.core.particles.ParticleType;

public class ModParticleDescriptionProvider extends AbstractParticleDescriptionProvider {
   public ModParticleDescriptionProvider(NeoForgeDataProviderContext context) {
      super(context);
   }

   public void addParticleDescriptions() {
      this.add((ParticleType)ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value());
      this.add((ParticleType)ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value());
   }
}
