package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.message.NetworkHandler;
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

public class ParticleOrb extends TextureSheetParticle {
   private double targetX;
   private double targetY;
   private double targetZ;
   private double startX;
   private double startY;
   private double startZ;
   private double signX;
   private double signZ;
   private float red;
   private float green;
   private float blue;
   private int mode;
   private double duration;

   public ParticleOrb(ClientLevel world, double x, double y, double z, double targetX, double targetZ) {
      super(world, x, y, z);
      this.targetX = targetX;
      this.targetZ = targetZ;
      this.quadSize = (4.5F + this.random.nextFloat() * 1.5F) * 0.1F;
      this.lifetime = 120;
      this.signX = Math.signum(targetX - x);
      this.signZ = Math.signum(targetZ - z);
      this.mode = 0;
      this.alpha = 0.0F;
      this.red = this.green = this.blue = 1.0F;
   }

   public ParticleOrb(ClientLevel world, double x, double y, double z, double targetX, double targetY, double targetZ, double speed) {
      this(world, x, y, z, targetX, targetZ);
      this.targetY = targetY;
      this.startX = x;
      this.startY = y;
      this.startZ = z;
      this.duration = speed;
      this.mode = 1;
      this.alpha = 0.1F;
   }

   public ParticleOrb(
      ClientLevel world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, double scale, int duration
   ) {
      super(world, x, y, z);
      this.quadSize = (float)scale * 0.1F;
      this.lifetime = duration;
      this.duration = duration;
      this.xd = vx;
      this.yd = vy;
      this.zd = vz;
      this.setColor((float)r, (float)g, (float)b);
      this.mode = 2;
   }

   public ParticleRenderType getRenderType() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public int getLightColor(float delta) {
      return 240 | super.getLightColor(delta) & 0xFF0000;
   }

   public void tick() {
      this.alpha = 0.1F;
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.mode == 0) {
         double vecX = this.targetX - this.x;
         double vecZ = this.targetZ - this.z;
         double dist = Math.sqrt(vecX * vecX + vecZ * vecZ);
         if (dist > 2.0 || Math.signum(vecX) != this.signX || Math.signum(vecZ) != this.signZ || this.age > this.lifetime) {
            this.remove();
            return;
         }

         double peak = 0.5;
         this.alpha = (float)(dist > 0.5 ? MathUtils.linearTransformd(dist, 0.5, 2.0, 1.0, 0.0) : MathUtils.linearTransformd(dist, 0.1F, 0.5, 0.0, 1.0));
         double minVel = 0.05;
         double maxVel = 0.3;
         double progress = Math.sin((-Math.PI / 4) * dist) + 1.0;
         double magMultipler = (progress * 0.25 + 0.05) / dist;
         vecX *= magMultipler;
         vecZ *= magMultipler;
         this.xd = vecX;
         this.yd = progress;
         this.zd = vecZ;
         this.move(this.xd, this.yd, this.zd);
      } else if (this.mode == 1) {
         this.alpha = this.age / (float)this.duration;
         this.x = this.startX + (this.targetX - this.startX) / (1.0 + Math.exp(-(8.0 / this.duration) * (this.age - this.duration / 2.0)));
         this.y = this.startY + (this.targetY - this.startY) / (1.0 + Math.exp(-(8.0 / this.duration) * (this.age - this.duration / 2.0)));
         this.z = this.startZ + (this.targetZ - this.startZ) / (1.0 + Math.exp(-(8.0 / this.duration) * (this.age - this.duration / 2.0)));
         if (this.age == this.duration) {
            this.remove();
         }
      } else if (this.mode == 2) {
         super.tick();
         if (this.age >= this.lifetime) {
            this.remove();
         }
      }

      this.age++;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      if (this.mode == 2) {
         this.alpha = Math.max(1.0F - (this.age + partialTicks) / (float)this.duration, 0.001F);
      } else {
         this.alpha = (this.age + partialTicks) / (float)this.duration;
      }

      this.rCol = this.red;
      this.gCol = this.green;
      this.bCol = this.blue;
      super.render(buffer, renderInfo, partialTicks);
   }

   public record Data(float red, float green, float blue, float scale, int duration, float targetX, float targetY, float targetZ, float speed, int mode)
      implements ParticleOptions {
      public static final MapCodec<ParticleOrb.Data> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("red").forGetter(ParticleOrb.Data::red),
               Codec.FLOAT.fieldOf("green").forGetter(ParticleOrb.Data::green),
               Codec.FLOAT.fieldOf("blue").forGetter(ParticleOrb.Data::blue),
               Codec.FLOAT.fieldOf("scale").forGetter(ParticleOrb.Data::scale),
               Codec.INT.fieldOf("duration").forGetter(ParticleOrb.Data::duration),
               Codec.FLOAT.fieldOf("targetX").forGetter(ParticleOrb.Data::targetX),
               Codec.FLOAT.fieldOf("targetY").forGetter(ParticleOrb.Data::targetY),
               Codec.FLOAT.fieldOf("targetZ").forGetter(ParticleOrb.Data::targetZ),
               Codec.FLOAT.fieldOf("speed").forGetter(ParticleOrb.Data::speed),
               Codec.INT.fieldOf("mode").forGetter(ParticleOrb.Data::mode)
            )
            .apply(instance, ParticleOrb.Data::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ParticleOrb.Data> STREAM_CODEC = NetworkHandler.composite(
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::red,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::green,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::blue,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::scale,
         ByteBufCodecs.INT,
         ParticleOrb.Data::duration,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::targetX,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::targetY,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::targetZ,
         ByteBufCodecs.FLOAT,
         ParticleOrb.Data::speed,
         ByteBufCodecs.INT,
         ParticleOrb.Data::mode,
         ParticleOrb.Data::new
      );

      public static ParticleOrb.Data create(float targetX, float targetZ) {
         return new ParticleOrb.Data(1.0F, 1.0F, 1.0F, 0.0F, 0, targetX, 0.0F, targetZ, 0.0F, 0);
      }

      public static ParticleOrb.Data create(float targetX, float targetY, float targetZ, float speed) {
         return new ParticleOrb.Data(1.0F, 1.0F, 1.0F, 0.0F, 0, targetX, targetY, targetZ, speed, 1);
      }

      public static ParticleOrb.Data create(float red, float green, float blue, float scale, int duration) {
         return new ParticleOrb.Data(red, green, blue, scale, duration, 0.0F, 0.0F, 0.0F, 0.0F, 2);
      }

      @NotNull
      public ParticleType<ParticleOrb.Data> getType() {
         return (ParticleType<ParticleOrb.Data>)ParticleHandler.ORB.get();
      }
   }

   public static final class Provider implements ParticleProvider<ParticleOrb.Data> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParticleOrb.Data data, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleOrb particle = switch (data.mode()) {
            case 0 -> new ParticleOrb(level, x, y, z, data.targetX(), data.targetZ());
            case 1 -> new ParticleOrb(level, x, y, z, data.targetX(), data.targetY(), data.targetZ(), data.speed());
            default -> new ParticleOrb(level, x, y, z, xSpeed, ySpeed, zSpeed, data.red(), data.green(), data.blue(), data.scale(), data.duration());
         };
         particle.setSpriteFromAge(this.spriteSet);
         return particle;
      }
   }
}
