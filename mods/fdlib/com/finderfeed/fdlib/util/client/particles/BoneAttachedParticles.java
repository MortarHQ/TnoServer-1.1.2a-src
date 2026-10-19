package com.finderfeed.fdlib.util.client.particles;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class BoneAttachedParticles {
   private AnimatedObject animatedObject;
   private List<Particle> particles;
   private FDModel fdModel;
   private String bone;
   private Vec3 offset;

   public BoneAttachedParticles(FDModel model, AnimatedObject animatedObject, String bone, Vec3 offset) {
      this.animatedObject = animatedObject;
      this.particles = new ArrayList<>();
      this.fdModel = model;
      this.bone = bone;
      this.offset = offset;
   }

   public void clientTick(Vec3 worldPos) {
      if (!this.particles.isEmpty()) {
         Vec3 particlePos = this.getWorldPos(worldPos);
         Iterator<Particle> iterator = this.particles.iterator();

         while (iterator.hasNext()) {
            Particle particle = iterator.next();
            if (!particle.isAlive()) {
               iterator.remove();
            } else {
               particle.setPos(particlePos.x, particlePos.y, particlePos.z);
            }
         }
      }
   }

   public void addParticle(ParticleOptions particleOptions, Vec3 worldPos) {
      Vec3 particlePos = this.getWorldPos(worldPos);
      ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
      Particle particle = particleEngine.createParticle(particleOptions, particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0);
      if (particle != null) {
         this.particles.add(particle);
      }
   }

   private Vec3 getWorldPos(Vec3 worldPos) {
      Matrix4f transformation = this.getTransformation();
      return new Vec3(transformation.transformPosition(this.offset.toVector3f())).add(worldPos);
   }

   private Matrix4f getTransformation() {
      return this.animatedObject instanceof Entity entity
         ? this.animatedObject.getModelPartTransformation(entity, this.bone, this.fdModel)
         : this.animatedObject.getModelPartTransformation(this.bone, this.fdModel);
   }

   public AnimatedObject getAnimatedObject() {
      return this.animatedObject;
   }

   public FDModel getFdModel() {
      return this.fdModel;
   }
}
