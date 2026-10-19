package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class IceShatterParticle extends TextureSheetParticle {
   private final float uo;
   private final float vo;

   public IceShatterParticle(ClientLevel clientLevel, double xo, double yo, double zo) {
      super(clientLevel, xo, yo, zo, 0.0, 0.0, 0.0);
      this.setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(Blocks.ICE.defaultBlockState()));
      this.gravity = 1.0F;
      this.friction = 0.9F;
      this.xd = this.xd * (this.random.nextFloat() * 8.0F);
      this.zd = this.zd * (this.random.nextFloat() * 8.0F);
      this.yd = this.yd * (this.random.nextFloat() * 8.0F);
      this.quadSize /= 5.0F;
      this.lifetime = Mth.randomBetweenInclusive(this.random, 20, 40);
      this.uo = this.random.nextFloat();
      this.vo = this.random.nextFloat();
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.TERRAIN_SHEET;
   }

   protected float getU0() {
      return this.sprite.getU((this.uo + 1.0F) / 4.0F * 16.0F);
   }

   protected float getU1() {
      return this.sprite.getU(this.uo / 4.0F * 16.0F);
   }

   protected float getV0() {
      return this.sprite.getV(this.vo / 4.0F * 16.0F);
   }

   protected float getV1() {
      return this.sprite.getV((this.vo + 1.0F) / 4.0F * 16.0F);
   }

   public int getLightColor(float f) {
      int lightAtPos = super.getLightColor(f);
      int k = lightAtPos >> 16 & 0xFF;
      return 240 | k << 16;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet spriteSet) {
         this.sprite = spriteSet;
      }

      @ParametersAreNonnullByDefault
      public Particle createParticle(
         SimpleParticleType simpleParticleType, ClientLevel clientLevel, double xo, double yo, double zo, double dx, double dy, double dz
      ) {
         return new IceShatterParticle(clientLevel, xo, yo, zo);
      }
   }
}
