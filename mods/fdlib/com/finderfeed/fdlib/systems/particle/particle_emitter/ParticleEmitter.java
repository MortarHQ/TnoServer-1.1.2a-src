package com.finderfeed.fdlib.systems.particle.particle_emitter;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;

public class ParticleEmitter {
   private static final Random random = new Random();
   public ParticleEmitterData data;
   public boolean removed;
   public int age;
   private List<Particle> activeParticles = new LinkedList<>();

   public ParticleEmitter(ParticleEmitterData data) {
      this.data = data;
      this.age = 0;
      this.removed = false;
   }

   public void tick() {
      ParticleEngine engine = Minecraft.getInstance().particleEngine;
      this.age++;
      this.data.processor.tickEmitter(this);
      List<ParticleOptions> types = this.data.particleTypes;

      for (int i = 0; i < this.data.particlesPerTick; i++) {
         Particle newParticle = engine.createParticle(
            types.get(random.nextInt(types.size())), this.data.position.x, this.data.position.y, this.data.position.z, 0.0, 0.0, 0.0
         );
         if (newParticle != null) {
            this.activeParticles.add(newParticle);
            this.data.processor.initParticle(newParticle);
         }
      }

      ListIterator<Particle> iterator = this.activeParticles.listIterator();

      while (iterator.hasNext()) {
         Particle particle = iterator.next();
         if (!particle.isAlive()) {
            iterator.remove();
         } else {
            this.data.processor.tickParticle(particle);
         }
      }

      if (this.age > this.data.lifetime) {
         this.removed = true;
      }
   }

   public void init() {
      this.data.processor.initEmitter(this);
   }
}
