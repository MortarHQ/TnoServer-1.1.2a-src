package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.GatheringWaterParticleOptions;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Gathering_Water_Particle extends SpinTrailParticle {
   private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/particle/gathering_lightning.png");

   protected Gathering_Water_Particle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float r, float g, float b) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed, r, g, b);
      this.orbitAxis = this.random.nextInt(3);
      this.xd = 0.0;
      this.yd = 0.0;
      this.zd = 0.0;
      this.orbitOffset = new Vec3(0.0, 0.0, 0.0);
      this.orbitDistance = 3.0;
      this.orbitSpeed = 12.0F;
      this.alpha = 1.0F;
      this.hasPhysics = false;
      this.lifetime = 20 + this.random.nextInt(10);
   }

   @Override
   public int getLightColor(float partialTicks) {
      return 240;
   }

   @Override
   public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
      super.render(vertexConsumer, camera, partialTick);
   }

   public float getAlpha() {
      return Mth.clamp(1.0F - (float)this.age / this.lifetime, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      this.xd *= 0.9;
      this.yd *= 0.9;
      this.zd *= 0.9;
      super.tick();
      float fadeIn = 0.8F * Mth.clamp((float)this.age / this.lifetime * 32.0F, 0.0F, 1.0F);
      float fadeOut = Mth.clamp(1.0F - (float)this.age / this.lifetime, 0.0F, 1.0F);
      this.trailA = fadeIn * fadeOut;
   }

   @Override
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
   public float getTrailHeight() {
      return 0.4F;
   }

   @Override
   public ResourceLocation getTrailTexture() {
      return TRAIL_TEXTURE;
   }

   @Override
   public int sampleCount() {
      return Math.min(10, this.lifetime - this.age);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<GatheringWaterParticleOptions> {
      public Particle createParticle(
         GatheringWaterParticleOptions data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new Gathering_Water_Particle(level, x, y, z, xSpeed, ySpeed, zSpeed, data.r(), data.g(), data.b());
      }
   }
}
