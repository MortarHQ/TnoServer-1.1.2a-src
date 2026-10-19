package com.mongoose.royalvariations.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DetonationTimerParticle extends TextureSheetParticle {
   public final int ownerId;
   public final boolean isEntity;
   public final Vec3 origin;
   private final SpriteSet spriteSet;

   protected DetonationTimerParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, int ownerId, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.ownerId = ownerId;
      this.isEntity = ownerId > 0;
      this.origin = new Vec3(x, y, z);
      this.spriteSet = spriteSet;
      this.setSize(0.2F, 0.2F);
      this.quadSize = 0.4F;
      this.lifetime = 84;
      this.gravity = 0.0F;
      this.hasPhysics = true;
      this.xd = vx * 0.0;
      this.yd = vy * 0.0;
      this.zd = vz * 0.0;
      this.setSpriteFromAge(spriteSet);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public Vec3 getPosition() {
      Entity owner = this.getEntity();
      return owner != null ? owner.position().add(0.0, owner.getBbHeight() + 1.0, 0.0) : this.origin;
   }

   public Entity getEntity() {
      return this.ownerId == -1 ? null : this.level.getEntity(this.ownerId);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         this.setSprite(this.spriteSet.get(this.age / 2 % 43 + 1, 43));
         Vec3 vec3 = this.getPosition();
         this.x = vec3.x;
         this.y = vec3.y;
         this.z = vec3.z;
         if (this.getEntity() == null && this.isEntity) {
            this.remove();
         }
      }
   }

   public static class Provider implements ParticleProvider<DTParticleOption> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(DTParticleOption typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new DetonationTimerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.getOwnerId(), this.spriteSet);
      }
   }
}
