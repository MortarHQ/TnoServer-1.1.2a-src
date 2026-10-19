package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ParticleSnowFlake extends TextureSheetParticle {
   private int swirlTick;
   private final float spread;
   boolean swirls;

   public ParticleSnowFlake(ClientLevel world, double x, double y, double z, double vX, double vY, double vZ, double duration, boolean swirls) {
      super(world, x, y, z);
      this.setSize(1.0F, 1.0F);
      this.xd = vX;
      this.yd = vY;
      this.zd = vZ;
      this.lifetime = (int)duration;
      this.swirlTick = this.random.nextInt(120);
      this.spread = this.random.nextFloat();
      this.swirls = swirls;
   }

   protected float getU1() {
      return super.getU1() - (super.getU1() - super.getU0()) / 8.0F;
   }

   protected float getV1() {
      return super.getV1() - (super.getV1() - super.getV0()) / 8.0F;
   }

   public ParticleRenderType getRenderType() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public void tick() {
      super.tick();
      if (this.swirls) {
         Vector3f motionVec = new Vector3f((float)this.xd, (float)this.yd, (float)this.zd);
         motionVec.normalize();
         float yaw = (float)Math.atan2(motionVec.x(), motionVec.z());
         float pitch = (float)Math.atan2(motionVec.y(), 1.0);
         float swirlRadius = 4.0F * ((float)this.age / this.lifetime) * this.spread;
         Quaternionf quatSpin = new Quaternionf(new AxisAngle4f(this.swirlTick * 0.2F, motionVec));
         Quaternionf quatOrient = MathUtils.quatFromRotationXYZ(pitch, yaw, 0.0F, false);
         Vector3f vec = new Vector3f(swirlRadius, 0.0F, 0.0F);
         quatOrient.transform(vec);
         quatSpin.transform(vec);
         this.x = this.x + vec.x();
         this.y = this.y + vec.y();
         this.z = this.z + vec.z();
      }

      if (this.age >= this.lifetime) {
         this.remove();
      }

      this.age++;
      this.swirlTick++;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = (this.age + partialTicks) / this.lifetime;
      this.alpha = (float)(1.0 - Math.exp(10.0F * (var - 1.0F)) - Math.pow(2000.0, -var));
      if (this.alpha < 0.01) {
         this.alpha = 0.01F;
      }

      super.render(buffer, renderInfo, partialTicks);
   }

   public record Data(float duration, boolean swirls) implements ParticleOptions {
      public static final MapCodec<ParticleSnowFlake.Data> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("duration").forGetter(ParticleSnowFlake.Data::duration),
               Codec.BOOL.fieldOf("swirls").forGetter(ParticleSnowFlake.Data::swirls)
            )
            .apply(instance, ParticleSnowFlake.Data::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ParticleSnowFlake.Data> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.FLOAT, ParticleSnowFlake.Data::duration, ByteBufCodecs.BOOL, ParticleSnowFlake.Data::swirls, ParticleSnowFlake.Data::new
      );

      @NotNull
      public ParticleType<ParticleSnowFlake.Data> getType() {
         return (ParticleType<ParticleSnowFlake.Data>)ParticleHandler.SNOWFLAKE.get();
      }
   }

   public static final class Provider implements ParticleProvider<ParticleSnowFlake.Data> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParticleSnowFlake.Data typeIn, @NotNull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleSnowFlake particle = new ParticleSnowFlake(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.duration(), typeIn.swirls());
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
