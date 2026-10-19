package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.util.CMMathUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Abstract_Ignis_Swing_Particle extends TextureSheetParticle {
   private final SpriteSet sprites;
   public float yaw;
   public float pitch;
   public float Swingroll;
   private final Vector3f[] localVertices;

   public Abstract_Ignis_Swing_Particle(
      ClientLevel pLevel,
      double pX,
      double pY,
      double pZ,
      SpriteSet spriteSet,
      double xd,
      double yd,
      double zd,
      int lifetime,
      float scale,
      float yaw,
      float pitch,
      float roll
   ) {
      super(pLevel, pX, pY, pZ, 0.0, 0.0, 0.0);
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.lifetime = lifetime;
      this.gravity = 0.0F;
      this.sprites = spriteSet;
      this.quadSize = scale * 3.25F;
      this.yaw = yaw;
      this.pitch = pitch;
      this.Swingroll = roll;
      this.friction = 1.0F;
      this.localVertices = this.calculateVertices();
      this.setSpriteFromAge(this.sprites);
   }

   private Vec3 vec3Copy(Vector3f vector3f) {
      return new Vec3(vector3f.x, vector3f.y, vector3f.z);
   }

   public void tick() {
      if (this.age == 0) {
      }

      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
      }
   }

   private void createFlameTrail() {
      int particleCount = 12;

      for (int i = 1; i < particleCount - 1; i++) {
         float t = (float)i / particleCount;
         float u = 1.0F - t;
         Vec3 localPos = this.vec3Copy(this.localVertices[1])
            .scale(u * u * u)
            .add(
               this.vec3Copy(this.localVertices[0])
                  .scale(3.0F * u * u * t)
                  .add(this.vec3Copy(this.localVertices[3]).scale(3.0F * u * t * t).add(this.vec3Copy(this.localVertices[2]).scale(t * t * t)))
            )
            .scale(this.quadSize * 0.9F)
            .add((2.0 * Math.random() - 1.0) * 0.3, (2.0 * Math.random() - 1.0) * 0.3, (2.0 * Math.random() - 1.0) * 0.3);
         ParticleOptions particleoptions = this.TrailParticle();
         if (particleoptions != null) {
            this.level
               .addParticle(
                  particleoptions,
                  this.x + localPos.x,
                  this.y + localPos.y,
                  this.z + localPos.z,
                  this.random.nextGaussian() * 0.03,
                  this.random.nextGaussian() * 0.03,
                  this.random.nextGaussian() * 0.03
               );
         }
      }
   }

   @Nullable
   public ParticleOptions TrailParticle() {
      return ParticleTypes.FLAME;
   }

   private Vector3f[] calculateVertices() {
      Quaternionf quaternionf = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
      Quaternionf quatY = CMMathUtil.quatFromRotationXYZ(0.0F, this.yaw, 0.0F, false);
      Quaternionf quatX = CMMathUtil.quatFromRotationXYZ(this.pitch, 0.0F, 0.0F, false);
      Quaternionf quatZ = CMMathUtil.quatFromRotationXYZ(0.0F, 0.0F, this.Swingroll, false);
      quaternionf.mul(quatY);
      quaternionf.mul(quatX);
      quaternionf.mul(quatZ);
      Vector3f[] vertices = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = vertices[i];
         quaternionf.transform(vector3f);
      }

      return vertices;
   }

   public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
      Vec3 Vector3d = camera.getPosition();
      float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - Vector3d.x());
      float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - Vector3d.y());
      float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - Vector3d.z());
      Vector3f[] avector3f = new Vector3f[4];
      float f4 = this.getQuadSize(partialTicks);

      for (int i = 0; i < 4; i++) {
         Vector3f localVertex = this.localVertices[i];
         avector3f[i] = new Vector3f(localVertex.x(), localVertex.y(), localVertex.z());
         avector3f[i].mul(f4);
         avector3f[i].add(f, f1, f2);
      }

      float f7 = this.getU0();
      float f8 = this.getU1();
      float f5 = this.getV0();
      float f6 = this.getV1();
      int j = this.getLightColor(partialTicks);
      buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).setUv(f8, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).setUv(f8, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).setUv(f7, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).setUv(f7, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).setUv(f7, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).setUv(f7, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).setUv(f8, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).setUv(f8, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   protected int getLightColor(float pPartialTick) {
      return 15728880;
   }
}
