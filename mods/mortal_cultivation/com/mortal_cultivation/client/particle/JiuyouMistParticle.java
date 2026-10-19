package com.mortal_cultivation.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class JiuyouMistParticle extends TextureSheetParticle {
   private final float height;
   private final float opacity;

   private JiuyouMistParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, SpriteSet sprites) {
      super(level, x, y, z);
      boolean underwater = level.getFluidState(BlockPos.containing(x, y, z)).is(FluidTags.WATER);
      this.xd = dx;
      this.yd = dy;
      this.zd = dz;
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.quadSize = underwater ? 1.4F : 2.3F + this.random.nextFloat();
      this.height = underwater ? 0.45F : 0.14F;
      this.opacity = underwater ? 0.18F : 0.27F;
      this.lifetime = 110 + this.random.nextInt(50);
      this.rCol = 0.035F;
      this.gCol = 0.075F;
      this.bCol = 0.16F;
      this.alpha = 0.0F;
      this.pickSprite(sprites);
   }

   public void tick() {
      super.tick();
      this.alpha = (float)Math.sin(Math.PI * Math.min(this.age, this.lifetime) / this.lifetime) * this.opacity;
   }

   public void render(VertexConsumer buffer, Camera camera, float partialTick) {
      Vec3 view = camera.getPosition();
      float x = (float)(Mth.lerp(partialTick, this.xo, this.x) - view.x);
      float y = (float)(Mth.lerp(partialTick, this.yo, this.y) - view.y);
      float z = (float)(Mth.lerp(partialTick, this.zo, this.z) - view.z);
      this.vertex(buffer, camera, x, y, z, 1, -1, this.getU1(), this.getV1());
      this.vertex(buffer, camera, x, y, z, 1, 1, this.getU1(), this.getV0());
      this.vertex(buffer, camera, x, y, z, -1, 1, this.getU0(), this.getV0());
      this.vertex(buffer, camera, x, y, z, -1, -1, this.getU0(), this.getV1());
   }

   private void vertex(VertexConsumer buffer, Camera camera, float x, float y, float z, int sx, int sy, float u, float v) {
      Vector3f vertex = new Vector3f(sx * this.quadSize, sy * this.height, 0.0F).rotate(camera.rotation()).add(x, y, z);
      buffer.addVertex(vertex.x, vertex.y, vertex.z).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(15728880);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new JiuyouMistParticle(level, x, y, z, dx, dy, dz, this.sprites);
      }
   }
}
