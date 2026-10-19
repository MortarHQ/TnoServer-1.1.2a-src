package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.message.NetworkHandler;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ParticleRing extends TextureSheetParticle {
   public float r;
   public float g;
   public float b;
   public float opacity;
   public boolean facesCamera;
   public float yaw;
   public float pitch;
   public float size;
   private final ParticleRing.EnumRingBehavior behavior;

   public ParticleRing(
      ClientLevel world,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      float yaw,
      float pitch,
      int duration,
      float r,
      float g,
      float b,
      float opacity,
      float size,
      boolean facesCamera,
      ParticleRing.EnumRingBehavior behavior
   ) {
      super(world, x, y, z);
      this.setSize(1.0F, 1.0F);
      this.size = size * 0.1F;
      this.lifetime = duration;
      this.alpha = 1.0F;
      this.r = r;
      this.g = g;
      this.b = b;
      this.opacity = opacity;
      this.yaw = yaw;
      this.pitch = pitch;
      this.facesCamera = facesCamera;
      this.xd = motionX;
      this.yd = motionY;
      this.zd = motionZ;
      this.behavior = behavior;
   }

   public int getLightColor(float delta) {
      return 240 | super.getLightColor(delta) & 0xFF0000;
   }

   public void tick() {
      super.tick();
      if (this.age >= this.lifetime) {
         this.remove();
      }

      this.age++;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = (this.age + partialTicks) / this.lifetime;
      if (this.behavior == ParticleRing.EnumRingBehavior.GROW) {
         this.quadSize = this.size * var;
      } else if (this.behavior == ParticleRing.EnumRingBehavior.SHRINK) {
         this.quadSize = this.size * (1.0F - var);
      } else if (this.behavior == ParticleRing.EnumRingBehavior.GROW_THEN_SHRINK) {
         this.quadSize = (float)(this.size * (1.0F - var - Math.pow(2000.0, -var)));
      } else {
         this.quadSize = this.size;
      }

      this.alpha = this.opacity * 0.95F * (1.0F - (this.age + partialTicks) / this.lifetime) + 0.05F;
      this.rCol = this.r;
      this.gCol = this.g;
      this.bCol = this.b;
      Vec3 Vector3d = renderInfo.getPosition();
      float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - Vector3d.x());
      float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - Vector3d.y());
      float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - Vector3d.z());
      Quaternionf quaternionf = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
      if (this.facesCamera) {
         if (this.roll == 0.0F) {
            quaternionf = renderInfo.rotation();
         } else {
            quaternionf = new Quaternionf(renderInfo.rotation());
            float f3 = Mth.lerp(partialTicks, this.oRoll, this.roll);
            quaternionf.mul(Axis.ZP.rotation(f3));
         }
      } else {
         Quaternionf quatX = MathUtils.quatFromRotationXYZ(this.pitch, 0.0F, 0.0F, false);
         Quaternionf quatY = MathUtils.quatFromRotationXYZ(0.0F, this.yaw, 0.0F, false);
         quaternionf.mul(quatY);
         quaternionf.mul(quatX);
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      quaternionf.transform(vector3f1);
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f4 = this.getQuadSize(partialTicks);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         quaternionf.transform(vector3f);
         vector3f.mul(f4);
         vector3f.add(f, f1, f2);
      }

      float f7 = this.getU0();
      float f8 = this.getU1();
      float f5 = this.getV0();
      float f6 = this.getV1();
      int j = this.getLightColor(partialTicks);
      buffer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).setUv(f8, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).setUv(f8, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).setUv(f7, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
      buffer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).setUv(f7, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
   }

   public ParticleRenderType getRenderType() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public record Data(
      float red,
      float green,
      float blue,
      float alpha,
      float yaw,
      float pitch,
      float scale,
      int duration,
      boolean facesCamera,
      ParticleRing.EnumRingBehavior behavior
   ) implements ParticleOptions {
      public static final Codec<ParticleRing.EnumRingBehavior> BEHAVIOUR_CODEC = StringRepresentable.fromEnum(ParticleRing.EnumRingBehavior::values);
      public static final MapCodec<ParticleRing.Data> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("red").forGetter(ParticleRing.Data::red),
               Codec.FLOAT.fieldOf("green").forGetter(ParticleRing.Data::green),
               Codec.FLOAT.fieldOf("blue").forGetter(ParticleRing.Data::blue),
               Codec.FLOAT.fieldOf("alpha").forGetter(ParticleRing.Data::alpha),
               Codec.FLOAT.fieldOf("yaw").forGetter(ParticleRing.Data::yaw),
               Codec.FLOAT.fieldOf("pitch").forGetter(ParticleRing.Data::pitch),
               Codec.FLOAT.fieldOf("scale").forGetter(ParticleRing.Data::scale),
               Codec.INT.fieldOf("duration").forGetter(ParticleRing.Data::duration),
               Codec.BOOL.fieldOf("facesCamera").forGetter(ParticleRing.Data::facesCamera),
               BEHAVIOUR_CODEC.fieldOf("behaviour").forGetter(ParticleRing.Data::behavior)
            )
            .apply(instance, ParticleRing.Data::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ParticleRing.Data> STREAM_CODEC = NetworkHandler.composite(
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::red,
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::green,
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::blue,
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::alpha,
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::yaw,
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::pitch,
         ByteBufCodecs.FLOAT,
         ParticleRing.Data::scale,
         ByteBufCodecs.INT,
         ParticleRing.Data::duration,
         ByteBufCodecs.BOOL,
         ParticleRing.Data::facesCamera,
         NeoForgeStreamCodecs.enumCodec(ParticleRing.EnumRingBehavior.class),
         ParticleRing.Data::behavior,
         ParticleRing.Data::new
      );

      @NotNull
      public ParticleType<ParticleRing.Data> getType() {
         return (ParticleType<ParticleRing.Data>)ParticleHandler.RING.get();
      }
   }

   public enum EnumRingBehavior implements StringRepresentable {
      SHRINK("shrink"),
      GROW("grow"),
      CONSTANT("constant"),
      GROW_THEN_SHRINK("grow_then_shrink");

      private final String key;

      EnumRingBehavior(final String key) {
         this.key = key;
      }

      public String getSerializedName() {
         return this.key;
      }
   }

   public static final class Provider implements ParticleProvider<ParticleRing.Data> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(ParticleRing.Data typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ParticleRing particle = new ParticleRing(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.yaw(),
            typeIn.pitch(),
            typeIn.duration(),
            typeIn.red(),
            typeIn.green(),
            typeIn.blue(),
            typeIn.alpha(),
            typeIn.scale(),
            typeIn.facesCamera(),
            typeIn.behavior()
         );
         particle.setSpriteFromAge(this.spriteSet);
         return particle;
      }
   }
}
