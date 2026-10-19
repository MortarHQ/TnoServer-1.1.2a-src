package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.SpinTrailParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SpinTrailParticle extends AbstractTrailParticle {
   private static final ResourceLocation PROTON_TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/particle/gathering_lightning.png");
   protected double orbitX;
   protected double orbitY;
   protected double orbitZ;
   protected double orbitDistance;
   protected Vec3 orbitOffset;
   protected boolean reverseOrbit;
   protected int orbitAxis;
   protected float orbitSpeed = 1.0F;

   public SpinTrailParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd, float r, float g, float b) {
      super(world, x, y, z, 0.0, 0.0, 0.0, r, g, b);
      this.trailA = 0.8F;
      this.lifetime = 50 + this.random.nextInt(30);
      this.gravity = 0.0F;
      this.orbitX = xd;
      this.orbitY = yd;
      this.orbitZ = zd;
      this.orbitDistance = 4.0;
      this.orbitOffset = new Vec3(0.0, 0.0, 0.0);
      this.reverseOrbit = this.random.nextBoolean();
      this.orbitAxis = this.random.nextInt(2);
      this.orbitSpeed = 10.0F;
   }

   public Vec3 getOrbitPosition(float angle) {
      Vec3 center = new Vec3(this.orbitX, this.orbitY, this.orbitZ);
      float f = this.reverseOrbit ? -1.0F : 1.0F;
      Vec3 add = Vec3.ZERO;
      float rot = angle * 3.0F * this.orbitSpeed * (float) (Math.PI / 180.0);
      switch (this.orbitAxis) {
         case 0:
            add = new Vec3(0.0, this.orbitDistance * 0.5, this.orbitDistance * 0.5).xRot(rot * f);
            break;
         case 1:
            add = new Vec3(this.orbitDistance * 0.5, 0.0, this.orbitDistance * 0.5).yRot(rot * f);
            break;
         case 2:
            add = new Vec3(this.orbitDistance * 0.5, this.orbitDistance * 0.5, 0.0).zRot(rot * f);
      }

      return center.add(add);
   }

   @Override
   public void tick() {
      Vec3 vec3 = this.getOrbitPosition(this.age);
      Vec3 movement = vec3.subtract(this.x, this.y, this.z).normalize().scale(this.orbitSpeed * 0.01F);
      this.xd = this.xd + movement.x;
      this.yd = this.yd + movement.y;
      this.zd = this.zd + movement.z;
      float fade = 1.0F - (float)this.age / this.lifetime;
      this.trailA = 0.8F * fade;
      super.tick();
   }

   public int getLightColor(float f) {
      return 240;
   }

   @Override
   public float getTrailHeight() {
      return 0.2F;
   }

   @Override
   public ResourceLocation getTrailTexture() {
      return PROTON_TRAIL_TEXTURE;
   }

   @Override
   public int sampleCount() {
      return Math.min(10, this.lifetime - this.age);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SpinTrailParticleOptions> {
      public Particle createParticle(
         SpinTrailParticleOptions data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new SpinTrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, data.r(), data.g(), data.b());
      }
   }
}
