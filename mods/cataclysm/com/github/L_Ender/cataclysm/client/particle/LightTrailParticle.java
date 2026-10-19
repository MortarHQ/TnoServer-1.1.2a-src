package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.LightTrailParticleOptions;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LightTrailParticle extends AbstractTrailParticle {
   private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/particle/storm.png");
   private final int EntityId;
   private final float width;
   private final float height;
   private final float initialYRot;
   private final float rotateByAge;

   public LightTrailParticle(ClientLevel world, double x, double y, double z, float r, float g, float b, float width, float height, int EntityId) {
      super(world, x, y, z, 0.0, 0.0, 0.0, r, g, b);
      this.EntityId = EntityId;
      this.gravity = 0.0F;
      this.lifetime = 20 + this.random.nextInt(20);
      this.initialYRot = this.random.nextFloat() * 360.0F;
      this.rotateByAge = (10.0F + this.random.nextFloat() * 10.0F) * (this.random.nextBoolean() ? -1.0F : 1.0F);
      this.width = width;
      this.height = height;
      Vec3 vec3 = this.getOrbitPosition();
      this.x = this.xo = vec3.x;
      this.y = this.yo = vec3.y;
      this.z = this.zo = vec3.z;
      this.xd = 0.0;
      this.yd = 0.0;
      this.zd = 0.0;
   }

   public Vec3 getEntityPosition() {
      Entity from = this.getFromEntity();
      return from != null ? from.position() : new Vec3(this.x, this.y, this.z);
   }

   public Entity getFromEntity() {
      return this.EntityId == -1 ? null : this.level.getEntity(this.EntityId);
   }

   public Vec3 getOrbitPosition() {
      Vec3 dinoPos = this.getEntityPosition();
      Vec3 vec3 = new Vec3(0.0, this.height, this.width).yRot((float)Math.toRadians(this.initialYRot + this.rotateByAge * this.age));
      return dinoPos.add(vec3);
   }

   @Override
   public void tick() {
      super.tick();
      float fade = 1.0F - (float)this.age / this.lifetime;
      this.trailA = 1.0F * fade;
      Vec3 vec3 = this.getOrbitPosition();
      this.x = vec3.x;
      this.y = vec3.y;
      this.z = vec3.z;
      Entity from = this.getFromEntity();
      if (from == null) {
         this.remove();
      }
   }

   @Override
   protected VertexConsumer getVetrexConsumer(BufferSource multibuffersource$buffersource) {
      return multibuffersource$buffersource.getBuffer(CMRenderTypes.getLightTrailEffect(this.getTrailTexture()));
   }

   @Override
   public int sampleCount() {
      return 4;
   }

   @Override
   public int sampleStep() {
      return 1;
   }

   @Override
   public float getTrailHeight() {
      return 0.5F;
   }

   public int getLightColor(float f) {
      return 240;
   }

   @Override
   public ResourceLocation getTrailTexture() {
      return TRAIL_TEXTURE;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<LightTrailParticleOptions> {
      public Particle createParticle(
         LightTrailParticleOptions data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new LightTrailParticle(level, x, y, z, data.r(), data.g(), data.b(), data.width(), data.height(), data.entityid());
      }
   }
}
