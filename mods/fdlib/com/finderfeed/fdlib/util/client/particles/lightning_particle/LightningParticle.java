package com.finderfeed.fdlib.util.client.particles.lightning_particle;

import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Random;
import org.joml.Vector3f;

public class LightningParticle extends Particle {
   private LightningParticleOptions options;
   private float particleRoll;
   private int seed;
   public static final ParticleRenderType RENDER_TYPE = new ParticleRenderType() {
      @Nullable
      public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
         RenderSystem.depthMask(true);
         RenderSystem.enableBlend();
         RenderSystem.disableCull();
         RenderSystem.setShader(GameRenderer::getRendertypeLightningShader);
         RenderSystem.blendFunc(770, 771);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      }

      public boolean isTranslucent() {
         return true;
      }

      @Override
      public String toString() {
         return "LIGHTNING_RENDER_TYPE";
      }
   };

   public LightningParticle(LightningParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
      super(level, x, y, z, xd, yd, zd);
      this.x = x;
      this.y = y;
      this.z = z;
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.options = options;
      this.lifetime = options.getLifetime();
      this.setSize(options.getQuadSize(), options.getQuadSize());
      if (level != null) {
         this.particleRoll = level.random.nextFloat() * 360.0F;
         if (options.getSeed() == -1) {
            this.seed = level.random.nextInt();
         }
      } else {
         this.particleRoll = 0.0F;
      }

      this.hasPhysics = options.hasPhysics();
      options.getProcessor().init(this);
   }

   public void tick() {
      this.options.getProcessor().processParticle(this);
      super.tick();
   }

   public void render(VertexConsumer vertex, Camera camera, float pticks) {
      Vec3 lpos = new Vec3(FDMathUtil.lerp(this.xo, this.x, pticks), FDMathUtil.lerp(this.yo, this.y, pticks), FDMathUtil.lerp(this.zo, this.z, pticks));
      Matrix4fStack mat = new Matrix4fStack();
      long time = this.level.getGameTime();
      Random r = new Random(time + this.seed);
      Matrix4f rotMat = new Matrix4f();
      Quaternionf q = camera.rotation();
      q.get(rotMat);
      Vec3 pos = lpos.subtract(camera.getPosition());
      mat.translate((float)pos.x, (float)pos.y, (float)pos.z);
      mat.mul(rotMat);
      mat.rotateZ((float)Math.toRadians(this.particleRoll + (this.options.isRandomRoll() ? r.nextInt(360) : 0)));
      float size = this.options.getQuadSize();
      Vector3f startPos = new Vector3f(-size / 2.0F, 0.0F, 0.0F);
      int remainingParts = this.options.getMaxLightningSegments();
      float length = size / remainingParts;
      float width = size / 8.0F;
      LightningParticle.LightningNode main = this.constructLightningNodes(null, r, startPos, new Vector3f(length, 0.0F, 0.0F), remainingParts, r.nextInt(3) + 2);
      if (main != null) {
         this.renderLightningNode(
            mat, main, vertex, width, this.options.getR() / 255.0F, this.options.getG() / 255.0F, this.options.getB() / 255.0F, 1.0F, 0.0F
         );
         this.renderLightningNode(mat, main, vertex, width / 4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0E-4F);
      }
   }

   private void renderLightningNode(
      Matrix4f transform, LightningParticle.LightningNode node, VertexConsumer vertex, float lightningWidth, float r, float g, float b, float a, float zOffset
   ) {
      Vector3f direction = node.end.sub(node.start, new Vector3f()).normalize();
      Vector3f prev = this.vectorBetweenCurrentAndPrevious(node);
      if (prev.dot(new Vector3f(0.0F, 1.0F, 0.0F)) < 0.0F) {
         prev = prev.mul(-1.0F);
      }

      Vector3f cr = direction.cross(prev, new Vector3f());
      float sin1 = cr.length();
      Vector3f currentBetween = this.vectorBetweenCurrentAndNext(node, 0);
      if (currentBetween.dot(new Vector3f(0.0F, 1.0F, 0.0F)) < 0.0F) {
         currentBetween = currentBetween.mul(-1.0F);
      }

      Vector3f cr2 = direction.cross(currentBetween, new Vector3f());
      float sin2 = cr2.length();
      float whiteWidthMult = 0.25F;
      float widthStart = lightningWidth / sin1;
      float widthEnd = lightningWidth / sin2;
      vertex.addVertex(transform, node.start.x - prev.x * widthStart * whiteWidthMult, node.start.y - prev.y * widthStart * whiteWidthMult, zOffset)
         .setColor(r, g, b, a);
      vertex.addVertex(transform, node.end.x - currentBetween.x * widthEnd * whiteWidthMult, node.end.y - currentBetween.y * widthEnd * whiteWidthMult, zOffset)
         .setColor(r, g, b, a);
      vertex.addVertex(transform, node.end.x + currentBetween.x * widthEnd * whiteWidthMult, node.end.y + currentBetween.y * widthEnd * whiteWidthMult, zOffset)
         .setColor(r, g, b, a);
      vertex.addVertex(transform, node.start.x + prev.x * widthStart * whiteWidthMult, node.start.y + prev.y * widthStart * whiteWidthMult, zOffset)
         .setColor(r, g, b, a);

      for (int i = 0; i < node.branches.size(); i++) {
         this.renderLightningNode(transform, node.branches.get(i), vertex, lightningWidth, r, g, b, a, zOffset);
      }
   }

   private Vector3f vectorBetweenCurrentAndPrevious(LightningParticle.LightningNode node) {
      Vector3f currentDir = node.end.sub(node.start, new Vector3f());
      if (node.previous == null) {
         return new Vector3f(0.0F, 1.0F, 0.0F);
      }

      Vector3f lastDir = node.previous.start.sub(node.previous.end, new Vector3f());
      return FDMathUtil.vectorBetweenVectors(currentDir, lastDir);
   }

   private Vector3f vectorBetweenCurrentAndNext(LightningParticle.LightningNode node, int id) {
      Vector3f currentDir = node.start.sub(node.end, new Vector3f());
      Vector3f lastDir;
      if (node.branches.isEmpty()) {
         lastDir = currentDir.normalize(new Vector3f()).rotateZ((float) (Math.PI / 2));
      } else {
         LightningParticle.LightningNode nd = node.branches.get(id);
         lastDir = nd.end.sub(nd.start, new Vector3f());
      }

      return FDMathUtil.vectorBetweenVectors(currentDir, lastDir);
   }

   private LightningParticle.LightningNode constructLightningNodes(
      LightningParticle.LightningNode previous, Random random, Vector3f startPos, Vector3f direction, int remainingParts, int branches
   ) {
      Vector3f endPos = startPos.add(direction, new Vector3f());
      List<LightningParticle.LightningNode> b = new ArrayList<>();
      LightningParticle.LightningNode node = new LightningParticle.LightningNode(previous, startPos, endPos, b);
      if (remainingParts <= 0) {
         return null;
      }

      for (int i = 0; i < branches; i++) {
         Vector3f newDir = direction.rotateZ(random.nextFloat() * (float) Math.PI / 1.5F - (float) (Math.PI / 3), new Vector3f());
         LightningParticle.LightningNode node1 = this.constructLightningNodes(
            node, random, endPos, newDir, remainingParts - random.nextInt(1) - 1, random.nextInt(2) + 1
         );
         if (node1 != null) {
            b.add(node1);
         }
      }

      return node;
   }

   public ParticleRenderType getRenderType() {
      return RENDER_TYPE;
   }

   public static class Factory implements ParticleProvider<LightningParticleOptions> {
      @Nullable
      public Particle createParticle(LightningParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
         return new LightningParticle(options, level, x, y, z, xd, yd, zd);
      }
   }

   public record LightningNode(LightningParticle.LightningNode previous, Vector3f start, Vector3f end, List<LightningParticle.LightningNode> branches) {
   }
}
