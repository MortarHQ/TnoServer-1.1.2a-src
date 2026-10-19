package com.finderfeed.fdlib.systems.screen.screen_particles;

import com.finderfeed.fdlib.util.math.ComplexEasingFunction;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class FDTexturedSParticle extends FDScreenParticle<FDTexturedSParticle> {
   private float u0 = 0.0F;
   private float v0 = 0.0F;
   private float u1 = 1.0F;
   private float v1 = 1.0F;
   private float maxQuadSize = 10.0F;
   private float currentQuadSize;
   private float quadSizeO;
   private ComplexEasingFunction scalingFunction = ComplexEasingFunction.builder().build();
   private ParticleRenderType renderType;

   public FDTexturedSParticle(Function<ResourceLocation, ? extends ParticleRenderType> factory, ResourceLocation location) {
      ParticleEngine engine = Minecraft.getInstance().particleEngine;
      TextureAtlas atlas = engine.textureAtlas;
      TextureAtlasSprite atlasSprite = (TextureAtlasSprite)atlas.getTextures().get(location);
      if (atlasSprite != null) {
         float width = engine.textureAtlas.width;
         float height = engine.textureAtlas.height;
         this.u0 = atlasSprite.getU0() + 1.0F / width;
         this.v0 = atlasSprite.getV0() + 1.0F / height;
         this.u1 = atlasSprite.getU1() - 1.0F / width;
         this.v1 = atlasSprite.getV1() - 1.0F / height;
         this.renderType = factory.apply(atlas.location());
      } else {
         this.renderType = factory.apply(location);
      }
   }

   public static FDTexturedSParticle create(Function<ResourceLocation, ? extends ParticleRenderType> factory, ResourceLocation location) {
      return new FDTexturedSParticle(factory, location);
   }

   @Override
   public void render(GuiGraphics graphics, BufferBuilder vertex, float partialTicks) {
      PoseStack matrices = graphics.pose();
      float x = (float)this.getX(partialTicks);
      float y = (float)this.getY(partialTicks);
      float roll = this.getRoll(partialTicks);
      float halfQuadSize = this.getCurrentQuadSize(partialTicks) / 2.0F;
      matrices.pushPose();
      matrices.translate(x, y, 0.0F);
      matrices.mulPose(Axis.ZN.rotationDegrees(roll));
      Matrix4f m = matrices.last().pose();
      vertex.addVertex(m, -halfQuadSize, -halfQuadSize, 0.0F).setUv(this.getU0(), this.getV0()).setColor(this.getR(), this.getG(), this.getB(), this.getA());
      vertex.addVertex(m, -halfQuadSize, halfQuadSize, 0.0F).setUv(this.getU0(), this.getV1()).setColor(this.getR(), this.getG(), this.getB(), this.getA());
      vertex.addVertex(m, halfQuadSize, halfQuadSize, 0.0F).setUv(this.getU1(), this.getV1()).setColor(this.getR(), this.getG(), this.getB(), this.getA());
      vertex.addVertex(m, halfQuadSize, -halfQuadSize, 0.0F).setUv(this.getU1(), this.getV0()).setColor(this.getR(), this.getG(), this.getB(), this.getA());
      matrices.popPose();
   }

   @Override
   public void tick() {
      super.tick();
      this.quadSizeO = this.currentQuadSize;
      this.currentQuadSize = this.scalingFunction.apply((float)this.getAge() / this.getLifetime()) * this.maxQuadSize;
   }

   @Override
   public ParticleRenderType getParticleRenderType() {
      return this.renderType;
   }

   public FDTexturedSParticle setQuadScaleOptions(ComplexEasingFunction function) {
      if (function.getLength() > 1.01) {
         throw new RuntimeException("Area of easing function in textured particle should not be bigger than 1. ");
      }

      this.scalingFunction = function;
      return this;
   }

   public FDTexturedSParticle setDefaultScaleOut() {
      return this.setQuadScaleOptions(ComplexEasingFunction.builder().addArea(1.0F, FDEasings::reversedLinear).build());
   }

   public FDTexturedSParticle setDefaultScaleInOut() {
      return this.setQuadScaleOptions(ComplexEasingFunction.builder().addArea(0.5F, FDEasings::easeOut).addArea(0.5F, FDEasings::reversedEaseOut).build());
   }

   public FDTexturedSParticle setDefaultScaleIn() {
      return this.setQuadScaleOptions(ComplexEasingFunction.builder().addArea(1.0F, FDEasings::linear).build());
   }

   public FDTexturedSParticle setMaxQuadSize(float size) {
      this.maxQuadSize = size;
      return this;
   }

   public float getCurrentQuadSize(float partialTicks) {
      return Mth.lerp(partialTicks, this.quadSizeO, this.currentQuadSize);
   }

   @Override
   public void onAddedToEngine() {
      this.currentQuadSize = this.scalingFunction.apply(0.0F) * this.maxQuadSize;
      this.quadSizeO = this.currentQuadSize;
   }

   public float getCurrentQuadSize() {
      return this.currentQuadSize;
   }

   public float getQuadSizeO() {
      return this.quadSizeO;
   }

   public float getU0() {
      return this.u0;
   }

   public float getV0() {
      return this.v0;
   }

   public float getU1() {
      return this.u1;
   }

   public float getV1() {
      return this.v1;
   }
}
