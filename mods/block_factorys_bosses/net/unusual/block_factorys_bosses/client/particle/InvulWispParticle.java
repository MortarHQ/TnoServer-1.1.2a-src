package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.GeoBone;

@OnlyIn(Dist.CLIENT)
public class InvulWispParticle extends FlameParticle {
   private final SpriteSet spriteSet;
   public GeoBone bone;

   public static InvulWispParticle.InvulWispParticleProvider provider(SpriteSet spriteSet) {
      return new InvulWispParticle.InvulWispParticleProvider(spriteSet);
   }

   protected InvulWispParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.spriteSet = spriteSet;
      this.quadSize = 0.33F;
      this.lifetime = 0;
      this.pickSprite(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float scale) {
      return this.quadSize;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      if (this.bone != null) {
         Vector3d pos = this.bone.getWorldPosition();
         this.xo = pos.x;
         this.yo = pos.y;
         this.zo = pos.z;
         this.x = pos.x;
         this.y = pos.y;
         this.z = pos.z;
      }

      super.render(buffer, renderInfo, partialTicks);
   }

   public static class InvulWispParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public InvulWispParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new InvulWispParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
