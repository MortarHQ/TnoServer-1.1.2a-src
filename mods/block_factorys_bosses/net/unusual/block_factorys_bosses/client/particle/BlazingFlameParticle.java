package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.procedures.SnowCloudParticleVisualScaleProcedure;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class BlazingFlameParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final float random_roll = 1.2F * (float)Math.random();

   public static BlazingFlameParticle.BlazingFlameParticleProvider provider(SpriteSet spriteSet) {
      return new BlazingFlameParticle.BlazingFlameParticleProvider(spriteSet);
   }

   protected BlazingFlameParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.setSize(0.9F, 0.9F);
      this.lifetime = 15;
      this.gravity = 0.0F;
      this.hasPhysics = true;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
      this.roll = this.random_roll;
      this.pickSprite(spriteSet);
      this.setAlpha(0.8F);
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float scale) {
      Level world = this.level;
      return super.getQuadSize(scale) * 4.0F * (float)SnowCloudParticleVisualScaleProcedure.execute(this.age);
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      this.roll = this.random_roll;
      this.rCol = 1.0F - this.age / 16.0F + this.age / 16.0F;
      this.gCol = 1.0F - this.age / 16.0F + 0.0F;
      this.bCol = 1.0F - this.age / 16.0F + 0.0F;
      if (!this.removed) {
         this.setSprite(this.spriteSet.get(this.age % 16 + 1, 16));
      }
   }

   protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
      quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
      quaternion = new Quaternionf(quaternion).rotateZ(this.roll);
      float f = this.getQuadSize(partialTicks);
      float u0 = this.getU0();
      float u1 = this.getU1();
      float v0 = this.getV0();
      float v1 = this.getV1();
      int light = this.getLightColor(partialTicks);
      this.renderVertex(buffer, quaternion, x, y, z, 0.5F, -0.5F, f, u1, v1, light);
      this.renderVertex(buffer, quaternion, x, y, z, 0.5F, 0.5F, f, u1, v0, light);
      this.renderVertex(buffer, quaternion, x, y, z, -0.5F, 0.5F, f, u0, v0, light);
      this.renderVertex(buffer, quaternion, x, y, z, -0.5F, -0.5F, f, u0, v1, light);
   }

   private void renderVertex(
      VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float xOffset, float yOffset, float quadSize, float u, float v, int packedLight
   ) {
      Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0F).rotate(quaternion).mul(quadSize).add(x, y, z);
      buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight);
   }

   public static class BlazingFlameParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public BlazingFlameParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new BlazingFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
