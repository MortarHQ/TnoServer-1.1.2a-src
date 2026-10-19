package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class GlintParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;

   public static GlintParticle.GlintParticleProvider provider(SpriteSet spriteSet) {
      return new GlintParticle.GlintParticleProvider(spriteSet);
   }

   protected GlintParticle(ClientLevel world, double x, double y, double z, double inactive, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.quadSize = 1.0F;
      this.lifetime = 3;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
      this.oRoll = this.roll;
      this.pickSprite(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float scale) {
      return this.quadSize;
   }

   protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
      float f = this.getQuadSize(partialTicks);
      float f1 = this.getU0();
      float f2 = this.getU1();
      float f3 = this.getV0();
      float f4 = this.getV1();
      int i = this.getLightColor(partialTicks);
      float xOffset = 60.0F + (float)this.age / this.lifetime * 10.0F;
      float yOffset = 0.25F + 1.75F * (1.0F - this.age / 3.0F);
      this.renderVertex(buffer, quaternion, x, y, z, xOffset, -yOffset, f, f2, f4, i);
      this.renderVertex(buffer, quaternion, x, y, z, xOffset, yOffset, f, f2, f3, i);
      this.renderVertex(buffer, quaternion, x, y, z, -xOffset, yOffset, f, f1, f3, i);
      this.renderVertex(buffer, quaternion, x, y, z, -xOffset, -yOffset, f, f1, f4, i);
   }

   private void renderVertex(
      VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float xOffset, float yOffset, float quadSize, float u, float v, int packedLight
   ) {
      Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0F).rotate(quaternion).mul(quadSize).add(x, y, z);
      buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight);
   }

   public static class GlintParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public GlintParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new GlintParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
