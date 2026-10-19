package team.lodestar.lodestone.systems.particle.world.behaviors;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;

public interface LodestoneParticleBehavior {
   default void tick(LodestoneWorldParticle particle) {
   }

   void render(LodestoneWorldParticle var1, VertexConsumer var2, Camera var3, float var4);
}
