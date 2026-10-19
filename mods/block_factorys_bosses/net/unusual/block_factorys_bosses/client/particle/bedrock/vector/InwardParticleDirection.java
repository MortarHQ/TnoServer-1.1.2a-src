package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import org.joml.Vector3f;

public class InwardParticleDirection implements ParticleDirection {
   public static InwardParticleDirection INSTANCE = new InwardParticleDirection();

   private InwardParticleDirection() {
   }

   @Override
   public void getDirection(Vector3f offsetInput, Vector3f direction) {
      direction.set(offsetInput);
      direction.normalize();
   }
}
