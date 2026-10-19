package net.regions_unexplored.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.Nullable;

public class RULeavesParticle extends TextureSheetParticle {
   private static final float ACCELERATION_SCALE = 0.0025F;
   private static final int INITIAL_LIFETIME = 300;
   private static final int CURVE_ENDPOINT_TIME = 300;
   private float rotSpeed;
   private int ticksOnGround;
   private final float spinAcceleration;
   private final float windBig;
   private final boolean swirl;
   private final boolean flowAway;
   private final double xaFlowScale;
   private final double zaFlowScale;
   private final double swirlPeriod;

   protected RULeavesParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      SpriteSet sprites,
      float fallAcceleration,
      float sideAcceleration,
      boolean swirl,
      boolean flowAway,
      float scale,
      float startVelocity
   ) {
      super(level, x, y, z);
      this.pickSprite(sprites);
      this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
      this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
      this.windBig = sideAcceleration;
      this.swirl = swirl;
      this.flowAway = flowAway;
      this.lifetime = 300;
      this.gravity = fallAcceleration * 1.2F * 0.0025F;
      float size;
      this.quadSize = size = scale * (this.random.nextBoolean() ? 0.05F : 0.075F);
      this.setSize(size, size);
      this.friction = 1.0F;
      this.yd = -startVelocity;
      float particleRandom = this.random.nextFloat();
      this.xaFlowScale = Math.cos(Math.toRadians(particleRandom * 60.0F)) * this.windBig;
      this.zaFlowScale = Math.sin(Math.toRadians(particleRandom * 60.0F)) * this.windBig;
      this.swirlPeriod = Math.toRadians(1000.0F + particleRandom * 3000.0F);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.lifetime-- <= 0) {
         this.remove();
      }

      if (!this.removed) {
         float aliveTicks = 300 - this.lifetime;
         float relativeAge = Math.min(aliveTicks / 300.0F, 1.0F);
         double xa = 0.0;
         double za = 0.0;
         if (this.flowAway) {
            xa += this.xaFlowScale * Math.pow(relativeAge, 1.25);
            za += this.zaFlowScale * Math.pow(relativeAge, 1.25);
         }

         if (this.swirl) {
            xa += relativeAge * Math.cos(relativeAge * this.swirlPeriod) * this.windBig;
            za += relativeAge * Math.sin(relativeAge * this.swirlPeriod) * this.windBig;
         }

         this.xd += xa * 0.0025F;
         this.zd += za * 0.0025F;
         this.yd = this.yd - this.gravity;
         this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
         this.oRoll = this.roll;
         if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
            this.ticksOnGround++;
            if (this.ticksOnGround >= 10) {
               this.alpha = 1.0F - (this.ticksOnGround - 10.0F) / 10.0F;
               if (this.ticksOnGround >= 20) {
                  this.remove();
               }
            }
         } else {
            this.ticksOnGround = 0;
            this.roll = this.roll + this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
         }

         if (!this.removed) {
            this.xd = this.xd * this.friction;
            this.yd = this.yd * this.friction;
            this.zd = this.zd * this.friction;
         }
      }
   }

   public static class Provider<T extends ParticleOptions> implements ParticleProvider<T> {
      private final SpriteSet sprites;
      private final float fallAcceleration;
      private final float sideAcceleration;
      private final boolean swirl;
      private final boolean flowAway;
      private final float scale;
      private final float startVelocity;

      public Provider(SpriteSet sprites, float fallAcceleration, float sideAcceleration, boolean swirl, boolean flowAway, float scale, float startVelocity) {
         this.sprites = sprites;
         this.fallAcceleration = fallAcceleration;
         this.sideAcceleration = sideAcceleration;
         this.swirl = swirl;
         this.flowAway = flowAway;
         this.scale = scale;
         this.startVelocity = startVelocity;
      }

      public static <T extends ParticleOptions> RULeavesParticle.Provider<T> flowing(SpriteSet sprites) {
         return new RULeavesParticle.Provider<>(sprites, 0.25F, 2.0F, false, true, 1.0F, 0.0F);
      }

      public static <T extends ParticleOptions> RULeavesParticle.Provider<T> swirling(SpriteSet sprites) {
         return new RULeavesParticle.Provider<>(sprites, 0.07F, 10.0F, true, false, 2.0F, 0.021F);
      }

      public static <T extends ParticleOptions> RULeavesParticle.Provider<T> large(SpriteSet sprites) {
         return new RULeavesParticle.Provider<>(sprites, 0.12F, 6.0F, true, false, 2.5F, 0.04F);
      }

      public static <T extends ParticleOptions> RULeavesParticle.Provider<T> magnolia(SpriteSet sprites) {
         return new RULeavesParticle.Provider<>(sprites, 0.1F, 4.0F, false, true, 2.0F, 0.03F);
      }

      public static <T extends ParticleOptions> RULeavesParticle.Provider<T> pine(SpriteSet sprites) {
         return new RULeavesParticle.Provider<>(sprites, 0.3F, 3.0F, true, false, 2.0F, 0.0F);
      }

      @Nullable
      public Particle createParticle(T options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux) {
         Particle particle = new RULeavesParticle(
            level, x, y, z, this.sprites, this.fallAcceleration, this.sideAcceleration, this.swirl, this.flowAway, this.scale, this.startVelocity
         );
         if (options instanceof ColorParticleOption colored) {
            particle.setColor(colored.getRed(), colored.getGreen(), colored.getBlue());
         }

         return particle;
      }
   }
}
