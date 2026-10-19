package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import com.yungnickyoung.minecraft.yungscavebiomes.block.BrittleSandstoneBlock;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FallingAncientDustParticle extends TextureSheetParticle {
   private final float rotSpeed;
   private final SpriteSet sprites;

   FallingAncientDustParticle(ClientLevel clientLevel, double xo, double yo, double zo, float r, float g, float b, SpriteSet spriteSet) {
      super(clientLevel, xo, yo, zo);
      this.sprites = spriteSet;
      this.rCol = r;
      this.gCol = g;
      this.bCol = b;
      int k = Mth.randomBetweenInclusive(clientLevel.getRandom(), 256, 512);
      this.lifetime = (int)Math.max(k, 1.0F);
      this.age = Mth.randomBetweenInclusive(clientLevel.getRandom(), 0, 96);
      this.setSpriteFromAge(spriteSet);
      this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public float getQuadSize(float f) {
      return this.quadSize * Mth.clamp((this.age + f) / this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
         this.oRoll = this.roll;
         this.roll = this.roll + (float) Math.PI * this.rotSpeed * 2.0F;
         if (this.onGround) {
            this.roll = 0.0F;
            this.oRoll = 0.0F;
         }

         this.move(this.xd, this.yd, this.zd);
         this.yd -= 0.003F;
         this.yd = Math.max(this.yd, -0.14F);
      }
   }

   public static class Provider implements ParticleProvider<BlockParticleOption> {
      private final SpriteSet sprite;

      public Provider(SpriteSet spriteSet) {
         this.sprite = spriteSet;
      }

      @Nullable
      public Particle createParticle(
         BlockParticleOption blockParticleOption, @NotNull ClientLevel clientLevel, double xo, double yo, double zo, double dx, double dy, double dz
      ) {
         BlockState blockState = blockParticleOption.getState();
         if (!blockState.isAir() && blockState.getRenderShape() == RenderShape.INVISIBLE) {
            return null;
         }

         BlockPos blockPos = BlockPos.containing(xo, yo, zo);
         int color = Minecraft.getInstance().getBlockColors().getColor(blockState, clientLevel, blockPos);
         if (blockState.getBlock() instanceof BrittleSandstoneBlock) {
            color = ((BrittleSandstoneBlock)blockState.getBlock()).getDustColor();
         }

         float r = (color >> 16 & 0xFF) / 255.0F;
         float g = (color >> 8 & 0xFF) / 255.0F;
         float b = (color & 0xFF) / 255.0F;
         return new FallingAncientDustParticle(clientLevel, xo, yo, zo, r, g, b, this.sprite);
      }
   }
}
