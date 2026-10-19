package com.bobmowzie.mowziesmobs.client.particle;

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
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

public class ParticleCloud extends TextureSheetParticle {
   private final float red;
   private final float green;
   private final float blue;
   private final float scale;
   private final ParticleCloud.EnumCloudBehavior behavior;
   private final float airDrag;

   public ParticleCloud(
      ClientLevel world,
      double x,
      double y,
      double z,
      double vx,
      double vy,
      double vz,
      double r,
      double g,
      double b,
      double scale,
      int duration,
      ParticleCloud.EnumCloudBehavior behavior,
      double airDrag
   ) {
      super(world, x, y, z);
      this.scale = (float)scale * 0.5F * 0.1F;
      this.lifetime = duration;
      this.xd = vx * 0.5;
      this.yd = vy * 0.5;
      this.zd = vz * 0.5;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.behavior = behavior;
      this.roll = this.oRoll = (float)(this.random.nextInt(4) * Math.PI / 2.0);
      this.airDrag = (float)airDrag;
   }

   public ParticleRenderType getRenderType() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public void tick() {
      super.tick();
      this.xd = this.xd * this.airDrag;
      this.yd = this.yd * this.airDrag;
      this.zd = this.zd * this.airDrag;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = (this.age + partialTicks) / this.lifetime;
      this.alpha = 0.2F * (float)(1.0 - Math.exp(5.0F * (var - 1.0F)) - Math.pow(2000.0, -var));
      if (this.alpha < 0.01) {
         this.alpha = 0.01F;
      }

      if (this.behavior == ParticleCloud.EnumCloudBehavior.SHRINK) {
         this.quadSize = this.scale * (1.0F - 0.7F * var + 0.3F);
      } else if (this.behavior == ParticleCloud.EnumCloudBehavior.GROW) {
         this.quadSize = this.scale * (0.7F * var + 0.3F);
      } else {
         this.quadSize = this.scale;
      }

      super.render(buffer, renderInfo, partialTicks);
   }

   public record Data(float red, float green, float blue, float scale, int duration, ParticleCloud.EnumCloudBehavior behavior, float airDrag)
      implements ParticleOptions {
      public static final Codec<ParticleCloud.EnumCloudBehavior> BEHAVIOUR_CODEC = StringRepresentable.fromEnum(ParticleCloud.EnumCloudBehavior::values);
      public static final MapCodec<ParticleCloud.Data> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("red").forGetter(data -> data.red),
               Codec.FLOAT.fieldOf("green").forGetter(data -> data.green),
               Codec.FLOAT.fieldOf("blue").forGetter(data -> data.blue),
               Codec.FLOAT.fieldOf("scale").forGetter(data -> data.scale),
               Codec.INT.fieldOf("duration").forGetter(data -> data.duration),
               BEHAVIOUR_CODEC.fieldOf("behaviour").forGetter(data -> data.behavior),
               Codec.FLOAT.fieldOf("air_drag").forGetter(data -> data.airDrag)
            )
            .apply(instance, ParticleCloud.Data::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ParticleCloud.Data> STREAM_CODEC = NeoForgeStreamCodecs.composite(
         ByteBufCodecs.FLOAT,
         ParticleCloud.Data::red,
         ByteBufCodecs.FLOAT,
         ParticleCloud.Data::green,
         ByteBufCodecs.FLOAT,
         ParticleCloud.Data::blue,
         ByteBufCodecs.FLOAT,
         ParticleCloud.Data::scale,
         ByteBufCodecs.INT,
         ParticleCloud.Data::duration,
         NeoForgeStreamCodecs.enumCodec(ParticleCloud.EnumCloudBehavior.class),
         ParticleCloud.Data::behavior,
         ByteBufCodecs.FLOAT,
         ParticleCloud.Data::airDrag,
         ParticleCloud.Data::new
      );

      @NotNull
      public ParticleType<?> getType() {
         return (ParticleType<?>)ParticleHandler.CLOUD.value();
      }
   }

   public enum EnumCloudBehavior implements StringRepresentable {
      SHRINK("shrink"),
      GROW("grow"),
      CONSTANT("constant");

      private final String key;

      EnumCloudBehavior(final String key) {
         this.key = key;
      }

      @NotNull
      public String getSerializedName() {
         return this.key;
      }
   }

   public static final class Provider implements ParticleProvider<ParticleCloud.Data> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParticleCloud.Data typeIn, @NotNull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleCloud particleCloud = new ParticleCloud(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.red(),
            typeIn.green(),
            typeIn.blue(),
            typeIn.scale(),
            typeIn.duration(),
            typeIn.behavior(),
            typeIn.airDrag()
         );
         particleCloud.setSpriteFromAge(this.spriteSet);
         particleCloud.setColor(typeIn.red(), typeIn.green(), typeIn.blue());
         return particleCloud;
      }
   }
}
