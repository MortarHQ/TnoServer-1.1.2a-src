package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.AfterImageParticleOptions;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.config.CMClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.function.Function;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class AfterImageParticle extends Particle {
   private final int EntityId;
   private final boolean ghost;

   public AfterImageParticle(ClientLevel world, double x, double y, double z, int r, int g, int b, int entityId, boolean ghost, int lifetimes) {
      super(world, x, y, z);
      this.setSize(6.0F, 6.0F);
      this.x = x;
      this.y = y;
      this.z = z;
      this.rCol = r;
      this.gCol = g;
      this.bCol = b;
      this.EntityId = entityId;
      this.lifetime = lifetimes;
      this.ghost = ghost;
   }

   public AABB getRenderBoundingBox(float partialTicks) {
      return this.getBoundingBox().inflate(0.0);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      }

      Entity from = this.getFromEntity();
      if (from == null) {
         this.remove();
      }
   }

   public Entity getFromEntity() {
      return this.EntityId == -1 ? null : this.level.getEntity(this.EntityId);
   }

   public void render(@NotNull VertexConsumer vertex, Camera camera, float tick) {
      BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
      Vec3 vec3 = camera.getPosition();
      EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
      double lerpX = Mth.lerp(tick, this.xo, this.x);
      double lerpY = Mth.lerp(tick, this.yo, this.y);
      double lerpZ = Mth.lerp(tick, this.zo, this.z);
      float colorR = this.rCol / 255.0F;
      float colorG = this.gCol / 255.0F;
      float colorB = this.bCol / 255.0F;
      float colorA = 0.5F / Math.abs(this.age + 1.0F);
      PoseStack stack = new PoseStack();
      if (this.getFromEntity() instanceof LivingEntity living
         && entityRenderDispatcher.getRenderer(living) instanceof LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer) {
         Function<VertexConsumer, VertexConsumer> createTintedBuffer = originalBuffer -> new VertexConsumer() {
            public VertexConsumer addVertex(float x, float y, float z) {
               originalBuffer.addVertex(x, y, z);
               return this;
            }

            public VertexConsumer setColor(int r, int g, int b, int a) {
               originalBuffer.setColor((int)(r * colorR), (int)(g * colorG), (int)(b * colorB), (int)(a * colorA));
               return this;
            }

            public VertexConsumer setUv(float u, float v) {
               originalBuffer.setUv(u, v);
               return this;
            }

            public VertexConsumer setUv1(int u, int v) {
               originalBuffer.setUv1(u, v);
               return this;
            }

            public VertexConsumer setUv2(int u, int v) {
               originalBuffer.setUv2(u, v);
               return this;
            }

            public VertexConsumer setNormal(float x, float y, float z) {
               originalBuffer.setNormal(x, y, z);
               return this;
            }
         };
         Minecraft minecraft = Minecraft.getInstance();
         boolean flag = !living.isInvisible();
         boolean flag1 = !flag && !living.isInvisibleTo(minecraft.player);
         boolean flag2 = minecraft.shouldEntityAppearGlowing(living);
         MultiBufferSource tintedSource = requestedType -> {
            boolean hasTexture = requestedType.format().getElements().contains(VertexFormatElement.UV0);
            boolean hasNormal = requestedType.format().getElements().contains(VertexFormatElement.NORMAL);
            if (hasTexture && hasNormal) {
               RenderType ghostType = this.getRenderType(living, renderer, flag, flag1, flag2);
               VertexConsumer originalBuffer = multibuffersource$buffersource.getBuffer(ghostType);
               return createTintedBuffer.apply(originalBuffer);
            } else {
               VertexConsumer originalBuffer = multibuffersource$buffersource.getBuffer(requestedType);
               return createTintedBuffer.apply(originalBuffer);
            }
         };
         entityRenderDispatcher.render(
            living,
            lerpX - vec3.x(),
            lerpY - vec3.y(),
            lerpZ - vec3.z(),
            living.getYRot(),
            tick,
            stack,
            tintedSource,
            entityRenderDispatcher.getPackedLightCoords(living, tick)
         );
      }
   }

   public RenderType getRenderType(
      LivingEntity p_230496_1_, LivingEntityRenderer<LivingEntity, ?> renderer, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_
   ) {
      ResourceLocation resourcelocation = renderer.getTextureLocation(p_230496_1_);
      if (p_230496_3_) {
         return RenderType.itemEntityTranslucentCull(resourcelocation);
      } else if (!p_230496_2_) {
         return p_230496_4_ ? RenderType.outline(resourcelocation) : null;
      } else {
         return this.ghost && !CMClientConfig.shadersCompat ? CMRenderTypes.getGhost(resourcelocation) : RenderType.entityTranslucent(resourcelocation);
      }
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<AfterImageParticleOptions> {
      public Particle createParticle(
         AfterImageParticleOptions data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new AfterImageParticle(level, x, y, z, data.r(), data.g(), data.b(), data.entityid(), data.ghost(), data.lifeticks());
      }
   }
}
