package net.jadenxgamer.netherexp.client.particle;

import javax.annotation.Nullable;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

public class ProximityFadeParticle extends LodestoneWorldParticle {
   public ProximityFadeParticle(
      ClientLevel world, WorldParticleOptions options, MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd
   ) {
      super(world, options, spriteSet, x, y, z, xd, yd, zd);
   }

   public void tick() {
      super.tick();
      double config = (Double)JNEConfigs.NETHER_MIST_DISSIPATE_DISTANCE.get();
      if (!(config <= 0.0) && this.age < this.lifetime) {
         double proximityThreshold = config * config;
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null) {
            Vec3 particlePos = this.getParticlePosition();
            Vec3 playerPos = player.position();
            double dx = particlePos.x - playerPos.x;
            double dz = particlePos.z - playerPos.z;
            double horizontalDistSq = dx * dx + dz * dz;
            if (horizontalDistSq < proximityThreshold) {
               double dist = Math.sqrt(horizontalDistSq);
               double maxDist = Math.sqrt(proximityThreshold);
               double factor = 1.0 - dist / maxDist;
               int extra = (int)(factor * 10.0);
               if (extra > 0) {
                  this.age = Math.min(this.lifetime, this.age + extra);
               }
            }
         }
      }
   }

   public static class Factory implements ParticleProvider<WorldParticleOptions> {
      private final SpriteSet sprite;

      public Factory(SpriteSet sprite) {
         this.sprite = sprite;
      }

      @Nullable
      public Particle createParticle(WorldParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
         return new ProximityFadeParticle(level, options, (MutableSpriteSet)this.sprite, x, y, z, mx, my, mz);
      }
   }
}
