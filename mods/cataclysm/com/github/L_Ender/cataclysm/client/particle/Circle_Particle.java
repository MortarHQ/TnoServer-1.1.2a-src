package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.Circle_ParticleOptions;
import com.github.L_Ender.cataclysm.util.CMMathUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Circle_Particle extends TextureSheetParticle {
   public int r;
   public int g;
   public int b;
   public float opacity;
   public float opacitystart;
   public boolean facesCamera;
   public float yaw;
   public float pitch;
   public float size;
   private final SpriteSet sprites;
   public int behavior;

   public Circle_Particle(
      ClientLevel world,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      float yaw,
      float pitch,
      int duration,
      int r,
      int g,
      int b,
      float opacity,
      float opacitystart,
      float size,
      boolean facesCamera,
      int behavior,
      SpriteSet sprites
   ) {
      super(world, x, y, z);
      this.sprites = sprites;
      this.setSize(1.0F, 1.0F);
      this.setSpriteFromAge(this.sprites);
      this.size = size * 0.1F;
      this.lifetime = duration;
      this.alpha = 1.0F;
      this.r = r;
      this.g = g;
      this.b = b;
      this.opacity = opacity;
      this.opacitystart = opacitystart;
      this.yaw = yaw;
      this.pitch = pitch;
      this.facesCamera = facesCamera;
      this.xd = motionX;
      this.yd = motionY;
      this.zd = motionZ;
      this.behavior = behavior;
   }

   public int getLightColor(float delta) {
      return 240 | super.getLightColor(delta) & 0xFF0000;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
      }
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = (this.age + partialTicks) / this.lifetime;
      if (this.behavior == 0) {
         this.quadSize = this.size * var;
      } else if (this.behavior == 1) {
         this.quadSize = this.size * (1.0F - var);
      } else if (this.behavior == 2) {
         this.quadSize = (float)(this.size * (1.0F - var - Math.pow(2000.0, -var)));
      } else {
         this.quadSize = this.size;
      }

      float smoothAge = this.age + partialTicks;
      float fadeProgress = (smoothAge - this.opacitystart) / (this.lifetime - this.opacitystart);
      float fadeMultiplier = 1.0F - fadeProgress;
      fadeMultiplier = Mth.clamp(fadeMultiplier, 0.0F, 1.0F);
      this.alpha = this.opacity * (0.95F * fadeMultiplier + 0.05F);
      this.rCol = this.r;
      this.gCol = this.g;
      this.bCol = this.b;
      Vec3 Vector3d = renderInfo.getPosition();
      float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - Vector3d.x());
      float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - Vector3d.y());
      float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - Vector3d.z());
      Quaternionf quaternionf = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
      if (this.facesCamera) {
         if (this.roll == 0.0F) {
            quaternionf = renderInfo.rotation();
         } else {
            quaternionf = new Quaternionf(renderInfo.rotation());
            float f3 = Mth.lerp(partialTicks, this.oRoll, this.roll);
            quaternionf.mul(Axis.ZP.rotation(f3));
         }
      } else {
         Quaternionf quatX = CMMathUtil.quatFromRotationXYZ(this.pitch, 0.0F, 0.0F, false);
         Quaternionf quatY = CMMathUtil.quatFromRotationXYZ(0.0F, this.yaw, 0.0F, false);
         quaternionf.mul(quatY);
         quaternionf.mul(quatX);
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      quaternionf.transform(vector3f1);
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f4 = this.getQuadSize(partialTicks);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         quaternionf.transform(vector3f);
         vector3f.mul(f4);
         vector3f.add(f, f1, f2);
      }

      float f7 = this.getU0();
      float f8 = this.getU1();
      float f5 = this.getV0();
      float f6 = this.getV1();
      int j = this.getLightColor(partialTicks);
      buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z())
         .setUv(f8, f6)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z())
         .setUv(f8, f5)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z())
         .setUv(f7, f5)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z())
         .setUv(f7, f6)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z())
         .setUv(f7, f6)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z())
         .setUv(f7, f5)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z())
         .setUv(f8, f5)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
      buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z())
         .setUv(f8, f6)
         .setColor(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, this.alpha)
         .setLight(j);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @OnlyIn(Dist.CLIENT)
   public static class CircleFactory implements ParticleProvider<Circle_ParticleOptions> {
      private final SpriteSet spriteSet;

      public CircleFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         Circle_ParticleOptions typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new Circle_Particle(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.yaw(),
            typeIn.pitch(),
            typeIn.duration(),
            typeIn.r(),
            typeIn.g(),
            typeIn.b(),
            typeIn.a(),
            typeIn.aStart(),
            typeIn.scale(),
            typeIn.facesCamera(),
            typeIn.behavior(),
            this.spriteSet
         );
      }
   }
}
