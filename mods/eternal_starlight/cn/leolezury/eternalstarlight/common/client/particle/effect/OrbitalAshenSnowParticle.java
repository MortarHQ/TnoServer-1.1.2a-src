package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class OrbitalAshenSnowParticle extends BaseAshSmokeParticle {
   private final double cx;
   private final double cz;
   private final float maxRadius;
   private final float rotSpeed;
   private float radius;
   private float oRadius;
   private float angle;
   private float oAngle;

   protected OrbitalAshenSnowParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(level, x, y, z, 1.0F, 1.0F, 1.0F, xSpeed, ySpeed, zSpeed, 1.25F, sprites, 1.0F, 20, 0.0125F, true);
      this.rCol = this.gCol = this.bCol = 1.0F;
      this.lifetime *= 4;
      this.cx = x;
      this.cz = z;
      this.oAngle = this.angle = (this.random.nextFloat() * 90.0F + 90.0F) * (this.random.nextBoolean() ? 1 : -1);
      this.maxRadius = this.random.nextFloat() * 3.0F + 2.0F;
      this.rotSpeed = (this.random.nextFloat() * 5.0F + 3.0F) * (this.random.nextBoolean() ? 1 : -1);
   }

   public void tick() {
      super.tick();
      this.oAngle = this.angle;
      this.angle = this.angle + this.rotSpeed;
      this.oRadius = this.radius;
      this.radius += 0.25F;
      if (this.radius > this.maxRadius) {
         this.radius = this.maxRadius;
      }

      if (!this.onGround) {
         Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(this.cx, 0.0, this.cz), this.radius, 0.0F, this.angle);
         this.setPos(pos.x, this.y, pos.z);
      }
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTick) {
      if (!this.onGround) {
         Vec3 pos = ESMathUtil.rotationToPosition(
            new Vec3(this.cx, 0.0, this.cz), Mth.lerp(partialTick, this.oRadius, this.radius), 0.0F, Mth.lerp(partialTick, this.oAngle, this.angle)
         );
         this.x = pos.x;
         this.z = pos.z;
      }

      super.render(consumer, camera, partialTick);
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xs, double ys, double zs) {
         return new OrbitalAshenSnowParticle(level, x, y, z, xs, ys, zs, this.sprites);
      }
   }
}
