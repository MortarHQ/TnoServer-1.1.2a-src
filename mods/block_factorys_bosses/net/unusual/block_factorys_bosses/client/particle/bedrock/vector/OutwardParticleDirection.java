package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import org.joml.Vector3f;

public class OutwardParticleDirection implements ParticleDirection {
   public static OutwardParticleDirection INSTANCE = new OutwardParticleDirection();

   private OutwardParticleDirection() {
   }

   @Override
   public void getDirection(Vector3f offsetInput, Vector3f direction) {
      direction.set(offsetInput);
      direction.normalize(-1.0F);
   }
}
