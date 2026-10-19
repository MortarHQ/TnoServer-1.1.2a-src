package net.jadenxgamer.netherexp.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

public class LodestoneDrippingParticle extends LodestoneWorldParticle {
   private static final int GROW_DURATION_TICKS = 30;
   private static final int SHRINK_DURATION_TICKS = 30;
   private static final float MAX_SIZE = 0.22F;
   private LodestoneDrippingParticle.State currentState = LodestoneDrippingParticle.State.GROWING;
   private int shrinkStartAge = -1;
   private final float defaultGravity;

   public LodestoneDrippingParticle(
      ClientLevel world, WorldParticleOptions options, MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd
   ) {
      super(world, options, spriteSet, x, y, z, xd, yd, zd);
      this.defaultGravity = options.getGravity();
      this.gravity = 0.0F;
      this.quadSize = 0.0F;
      this.setLifetime(200);
      this.setSize(0.0F, 0.0F);
   }

   public void tick() {
      super.tick();
      float size = this.quadSize;
      switch (this.currentState) {
         case GROWING:
            float progressx = Math.min(1.0F, this.age / 30.0F);
            size = 0.22F * progressx;
            if (this.age >= 30) {
               this.currentState = LodestoneDrippingParticle.State.FALLING;
               this.gravity = this.defaultGravity;
               size = 0.22F;
            }
            break;
         case FALLING:
            size = 0.22F;
            if (this.onGround) {
               this.xd = this.yd = this.zd = 0.0;
               this.gravity = 0.0F;
               this.currentState = LodestoneDrippingParticle.State.SHRINKING;
               this.shrinkStartAge = this.age;
            }
            break;
         case SHRINKING:
            int shrinkAge = this.age - this.shrinkStartAge;
            float progress = Math.min(1.0F, shrinkAge / 30.0F);
            size = 0.22F * (1.0F - progress);
            if (progress >= 1.0F) {
               this.remove();
            }
      }

      this.quadSize = size;
      this.setSize(size, size);
   }

   public float getQuadLength(float partialTicks) {
      return this.quadSize;
   }

   public static class Factory implements ParticleProvider<WorldParticleOptions> {
      private final SpriteSet sprite;

      public Factory(SpriteSet sprite) {
         this.sprite = sprite;
      }

      @Nullable
      public Particle createParticle(WorldParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
         return new LodestoneDrippingParticle(level, options, (MutableSpriteSet)this.sprite, x, y, z, mx, my, mz);
      }
   }

   private enum State {
      GROWING,
      FALLING,
      SHRINKING;
   }
}
