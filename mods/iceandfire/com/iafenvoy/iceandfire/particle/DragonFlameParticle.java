package com.iafenvoy.iceandfire.particle;

import com.iafenvoy.uranus.object.VecUtil;
import com.iafenvoy.uranus.util.RandomHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DragonFlameParticle extends TextureSheetParticle {
   protected DragonFlameParticle(
      DragonFlameParticleType parameters,
      ClientLevel world,
      double x,
      double y,
      double z,
      double velocityX,
      double velocityY,
      double velocityZ,
      SpriteSet spriteProvider
   ) {
      super(world, x, y, z);
      float size = parameters.getScale();
      this.quadSize = this.quadSize * (float)RandomHelper.nextDouble(size, size * 2.0F);
      this.lifetime = 30;
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.setParticleSpeed(RandomHelper.randomize(velocityX, 0.5), RandomHelper.randomize(velocityY, 0.5), RandomHelper.randomize(velocityZ, 0.5));
      this.pickSprite(spriteProvider);
   }

   public static ParticleProvider<DragonFlameParticleType> factory(SpriteSet spriteProvider) {
      return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new DragonFlameParticle(
         parameters, world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider
      );
   }

   public int getLightColor(float partialTick) {
      return 240;
   }

   public void tick() {
      super.tick();
      BlockState state = this.level.getBlockState(VecUtil.createBlockPos(this.x, this.y, this.z));
      if (state != null && state.isSolid()) {
         this.remove();
      }
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }
}
