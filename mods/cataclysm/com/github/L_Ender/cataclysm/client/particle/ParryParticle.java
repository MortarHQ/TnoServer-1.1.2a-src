package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.ParryParticleOptions;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

public class ParryParticle extends TextureSheetParticle {
   private Vec3[] trailPositions = new Vec3[64];
   private int trailPointer = -1;
   private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/particle/gathering_lightning.png");
   protected float trailA = 1.0F;

   protected ParryParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float r, float g, float b) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      this.gravity = 0.95F;
      this.friction = 0.999F;
      this.rCol = r;
      this.gCol = g;
      this.bCol = b;
      this.alpha = 0.6F;
      this.xd += xSpeed;
      this.yd += ySpeed;
      this.zd += zSpeed;
      this.yd = this.random.nextFloat() * 0.4F + 0.05F;
      this.quadSize *= 0.5F;
      this.lifetime = 9 + level.random.nextInt(3);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public int getLightColor(float p_107086_) {
      int i = super.getLightColor(p_107086_);
      int j = 240;
      int k = i >> 16 & 0xFF;
      return 240 | k << 16;
   }

   public float getQuadSize(float p_107089_) {
      float f = (this.age + p_107089_) / this.lifetime;
      return this.quadSize * (1.0F - f * f);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         float f = (float)this.age / this.lifetime;
         this.xd *= 1.0F - f * f;
         this.yd *= 1.0F - f * f;
         this.zd *= 1.0F - f * f;
         this.tickTrail();
      }

      if (this.onGround) {
         this.remove();
      }
   }

   public void tickTrail() {
      Vec3 currentPosition = new Vec3(this.x, this.y, this.z);
      if (this.trailPointer == -1) {
         for (int i = 0; i < this.trailPositions.length; i++) {
            this.trailPositions[i] = currentPosition;
         }
      }

      if (++this.trailPointer == this.trailPositions.length) {
         this.trailPointer = 0;
      }

      this.trailPositions[this.trailPointer] = currentPosition;
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTick) {
      super.render(consumer, camera, partialTick);
      if (this.trailPointer > -1) {
         BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
         VertexConsumer vertexConsumer = this.getVetrexConsumer(bufferSource);
         Vec3 cameraPos = camera.getPosition();
         float renderX = (float)Mth.lerp(partialTick, this.xo, this.x);
         float renderY = (float)Mth.lerp(partialTick, this.yo, this.y);
         float renderZ = (float)Mth.lerp(partialTick, this.zo, this.z);
         PoseStack poseStack = new PoseStack();
         poseStack.pushPose();
         poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
         int light = this.getLightColor(partialTick);
         float trailWidth = 0.08F;
         Pose lastPose = poseStack.last();
         Matrix4f matrix = lastPose.pose();
         Vec3 drawFrom = new Vec3(renderX, renderY, renderZ);
         int subSteps = 5;
         int totalSamples = this.sampleCount();
         float totalLength = totalSamples * subSteps;

         for (int i = 0; i < totalSamples - 1; i++) {
            Vec3 p0 = this.getTrailPosition(Math.max(0, i - 1) * this.sampleStep(), partialTick);
            Vec3 p1 = this.getTrailPosition(i * this.sampleStep(), partialTick);
            Vec3 p2 = this.getTrailPosition((i + 1) * this.sampleStep(), partialTick);
            Vec3 p3 = this.getTrailPosition(Math.min(totalSamples - 1, i + 2) * this.sampleStep(), partialTick);

            for (int step = 1; step <= subSteps; step++) {
               float t = (float)step / subSteps;
               Vec3 sample = this.catmullRom(t, p0, p1, p2, p3);
               float currentStepIndex = i * subSteps + step;
               float u1 = (currentStepIndex - 1.0F) / totalLength;
               float u2 = currentStepIndex / totalLength;
               Vec3 forward = sample.subtract(drawFrom);
               if (forward.lengthSqr() != 0.0) {
                  Vec3 toCamera = cameraPos.subtract(drawFrom);
                  Vec3 side = forward.cross(toCamera).normalize();
                  Vec3 offset = side.scale(trailWidth / 2.0F);
                  this.addVertex(vertexConsumer, matrix, drawFrom.add(offset), this.rCol, this.gCol, this.bCol, u1, 0.0F, light);
                  this.addVertex(vertexConsumer, matrix, drawFrom.add(offset.scale(-1.0)), this.rCol, this.gCol, this.bCol, u1, 1.0F, light);
                  this.addVertex(vertexConsumer, matrix, sample.add(offset.scale(-1.0)), this.rCol, this.gCol, this.bCol, u2, 1.0F, light);
                  this.addVertex(vertexConsumer, matrix, sample.add(offset), this.rCol, this.gCol, this.bCol, u2, 0.0F, light);
                  drawFrom = sample;
               }
            }
         }

         poseStack.popPose();
      }
   }

   private Vec3 catmullRom(float t, Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3) {
      double t2 = t * t;
      double t3 = t2 * t;
      double x = 0.5 * (2.0 * p1.x + (-p0.x + p2.x) * t + (2.0 * p0.x - 5.0 * p1.x + 4.0 * p2.x - p2.x) * t2 + (-p0.x + 3.0 * p1.x - 3.0 * p2.x + p3.x) * t3);
      double y = 0.5 * (2.0 * p1.y + (-p0.y + p2.y) * t + (2.0 * p0.y - 5.0 * p1.y + 4.0 * p2.y - p2.y) * t2 + (-p0.y + 3.0 * p1.y - 3.0 * p2.y + p3.y) * t3);
      double z = 0.5 * (2.0 * p1.z + (-p0.z + p2.z) * t + (2.0 * p0.z - 5.0 * p1.z + 4.0 * p2.z - p2.z) * t2 + (-p0.z + 3.0 * p1.z - 3.0 * p2.z + p3.z) * t3);
      return new Vec3(x, y, z);
   }

   private void addVertex(VertexConsumer consumer, Matrix4f matrix, Vec3 pos, float r, float g, float b, float u, float v, int light) {
      consumer.addVertex(matrix, (float)pos.x, (float)pos.y, (float)pos.z)
         .setColor(r, g, b, 1.0F)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(0.0F, 1.0F, 0.0F);
   }

   protected VertexConsumer getVetrexConsumer(BufferSource multibuffersource$buffersource) {
      return multibuffersource$buffersource.getBuffer(CMRenderTypes.getLightTrailEffect(this.getTrailTexture()));
   }

   public float getTrailRot(Camera camera) {
      return (float) (-Math.PI / 180.0) * camera.getXRot();
   }

   public float getTrailHeight() {
      return 0.4F;
   }

   public ResourceLocation getTrailTexture() {
      return TRAIL_TEXTURE;
   }

   public int sampleCount() {
      return Math.min(6, (this.lifetime - this.age) / 2);
   }

   public int sampleStep() {
      return 1;
   }

   public Vec3 getTrailPosition(int pointer, float partialTick) {
      if (this.removed) {
         partialTick = 1.0F;
      }

      int i = this.trailPointer - pointer & 63;
      int j = this.trailPointer - pointer - 1 & 63;
      Vec3 d0 = this.trailPositions[j];
      Vec3 d1 = this.trailPositions[i].subtract(d0);
      return d0.add(d1.scale(partialTick));
   }

   @OnlyIn(Dist.CLIENT)
   public static class ParryFactory implements ParticleProvider<ParryParticleOptions> {
      private final SpriteSet spriteSet;

      public ParryFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParryParticleOptions typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParryParticle particle = new ParryParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.r(), typeIn.g(), typeIn.b());
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
