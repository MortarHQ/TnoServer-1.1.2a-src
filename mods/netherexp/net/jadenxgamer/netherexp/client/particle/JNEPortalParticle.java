package net.jadenxgamer.netherexp.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

public class JNEPortalParticle extends LodestoneWorldParticle {
   private double targetX;
   private double targetY;
   private double targetZ;
   private final double startX = this.x;
   private final double startY = this.y;
   private final double startZ = this.z;

   public JNEPortalParticle(
      ClientLevel world, WorldParticleOptions options, MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd
   ) {
      super(world, options, spriteSet, x, y, z, xd, yd, zd);
   }

   public void setTargetPos(double x, double y, double z) {
      this.targetX = x;
      this.targetY = y;
      this.targetZ = z;
   }

   public void tick() {
      float progress = (float)this.age / (this.lifetime - 1);
      if (progress > 1.0F) {
         progress = 1.0F;
      }

      double newX = this.startX + (this.targetX - this.startX) * progress;
      double newY = this.startY + (this.targetY - this.startY) * progress;
      double newZ = this.startZ + (this.targetZ - this.startZ) * progress;
      this.setPos(newX, newY, newZ);
      super.tick();
   }

   public void move(double x, double y, double z) {
      this.setBoundingBox(this.getBoundingBox().move(x, y, z));
      this.setLocationFromBoundingbox();
   }

   public static class Factory implements ParticleProvider<WorldParticleOptions> {
      private final SpriteSet sprite;

      public Factory(SpriteSet sprite) {
         this.sprite = sprite;
      }

      @Nullable
      public Particle createParticle(WorldParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
         return new JNEPortalParticle(world, data, (MutableSpriteSet)this.sprite, x, y, z, mx, my, mz);
      }
   }
}
