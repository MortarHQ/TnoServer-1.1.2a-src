package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.RainFogParticleOptions;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.function.Consumer;
import net.minecraft.Util;
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

public class Rain_Fog_Particle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private static final Vector3f ROTATION_VECTOR = (Vector3f)Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
   private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
   private static final float DEGREES_90 = (float) (Math.PI / 2);

   protected Rain_Fog_Particle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xd, double yd, double zd, float size, SpriteSet spriteSet) {
      super(level, xCoord, yCoord, zCoord, xd, yd, zd);
      this.sprites = spriteSet;
      this.setSpriteFromAge(this.sprites);
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.alpha = 0.6F;
      this.quadSize = size;
      this.gravity = 0.0F;
      this.lifetime = (int)(Math.random() * 2.0) + 60;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public float getQuadSize(float p_107608_) {
      float f = 1.0F - (this.age + p_107608_) / (this.lifetime * 1.5F);
      return this.quadSize * f;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
         this.move(this.xd, this.yd, this.zd);
         this.xd = this.xd * this.friction;
         this.yd = this.yd * this.friction;
         this.zd = this.zd * this.friction;
      }
   }

   private float noise(float offset) {
      return 10.0F * Mth.sin(offset * 0.01F);
   }

   public void render(VertexConsumer buffer, Camera camera, float partialticks) {
      this.alpha = 1.0F - Mth.clamp((this.age + partialticks - 20.0F) / this.lifetime, 0.2F, 0.7F);
      this.renderRotatedParticle(buffer, camera, partialticks, p_234005_ -> {
         p_234005_.mul(Axis.YP.rotation(0.0F));
         p_234005_.mul(Axis.XP.rotation((float) (-Math.PI / 2)));
      });
      this.renderRotatedParticle(buffer, camera, partialticks, p_234000_ -> {
         p_234000_.mul(Axis.YP.rotation((float) -Math.PI));
         p_234000_.mul(Axis.XP.rotation((float) (Math.PI / 2)));
      });
   }

   private void renderRotatedParticle(VertexConsumer pConsumer, Camera camera, float partialTick, Consumer<Quaternionf> pQuaternion) {
      Vec3 vec3 = camera.getPosition();
      float f = (float)(Mth.lerp(partialTick, this.xo, this.x) - vec3.x());
      float f1 = (float)(Mth.lerp(partialTick, this.yo, this.y) - vec3.y());
      float f2 = (float)(Mth.lerp(partialTick, this.zo, this.z) - vec3.z());
      Quaternionf quaternion = new Quaternionf().setAngleAxis(0.0F, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
      pQuaternion.accept(quaternion);
      quaternion.transform(TRANSFORM_VECTOR);
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f3 = this.getQuadSize(partialTick);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.rotate(quaternion);
         vector3f.mul(f3);
         vector3f.add(f, f1, f2);
      }

      int j = this.getLightColor(partialTick);
      this.makeCornerVertex(pConsumer, avector3f[0], this.getU1(), this.getV1(), j);
      this.makeCornerVertex(pConsumer, avector3f[1], this.getU1(), this.getV0(), j);
      this.makeCornerVertex(pConsumer, avector3f[2], this.getU0(), this.getV0(), j);
      this.makeCornerVertex(pConsumer, avector3f[3], this.getU0(), this.getV1(), j);
   }

   private void makeCornerVertex(VertexConsumer pConsumer, Vector3f pVec3f, float p_233996_, float p_233997_, int p_233998_) {
      Vec3 wiggle = new Vec3(this.noise((float)(this.age + this.x)), this.noise((float)(this.age - this.x)), this.noise((float)(this.age + this.z)))
         .scale(0.02F);
      pConsumer.addVertex(pVec3f.x() + (float)wiggle.x, pVec3f.y(), pVec3f.z() + (float)wiggle.z)
         .setUv(p_233996_, p_233997_)
         .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
         .setLight(p_233998_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<RainFogParticleOptions> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(
         RainFogParticleOptions typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new Rain_Fog_Particle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.size(), this.spriteSet);
      }
   }
}
