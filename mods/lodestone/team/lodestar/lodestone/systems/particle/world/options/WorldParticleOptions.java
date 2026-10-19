package team.lodestar.lodestone.systems.particle.world.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import team.lodestar.lodestone.handlers.LodestoneRenderHandler;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.BillboardParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;
import team.lodestar.lodestone.systems.rendering.buffer.LodestoneRenderLayer;

public class WorldParticleOptions extends SimpleParticleOptions implements ParticleOptions {
   public final ParticleType<?> type;
   public LodestoneParticleBehavior behavior = BillboardParticleBehavior.INSTANCE;
   public ParticleRenderType renderType = LodestoneWorldParticleRenderType.ADDITIVE;
   public LodestoneRenderLayer renderLayer = LodestoneRenderHandler.DEFERRED_RENDER;
   public final Collection<Consumer<LodestoneWorldParticle>> tickActors = new ArrayList<>();
   public final Collection<Consumer<LodestoneWorldParticle>> spawnActors = new ArrayList<>();
   public final Collection<Consumer<LodestoneWorldParticle>> renderActors = new ArrayList<>();
   public int particleLight = 15728880;
   public boolean noClip = false;

   public WorldParticleOptions(ParticleType<?> type) {
      this.type = type;
   }

   public WorldParticleOptions(Supplier<? extends LodestoneWorldParticleType> type) {
      this((ParticleType<?>)type.get());
   }

   public WorldParticleOptions setBehavior(LodestoneParticleBehavior behavior) {
      this.behavior = behavior;
      return this;
   }

   public WorldParticleOptions setBehaviorIfDefault(LodestoneParticleBehavior newBehavior) {
      return this.behavior.equals(BillboardParticleBehavior.INSTANCE) ? this.setBehavior(newBehavior) : this;
   }

   public ParticleType<?> getType() {
      return this.type;
   }
}
