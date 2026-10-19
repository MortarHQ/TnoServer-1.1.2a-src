package com.finderfeed.fdlib.systems.particle;

import com.finderfeed.fdlib.util.FDByteBufCodecs;
import com.finderfeed.fdlib.util.FDCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class CircleParticleProcessor implements ParticleProcessor<CircleParticleProcessor> {
   private Vec3 point = null;
   private boolean forward = true;
   private boolean in = true;
   private float circleCount = 1.0F;
   private Vec3 previousPoint = null;
   private Vec3 initialPoint = null;
   private Vector3f axis = null;
   private Vec3 oldSpeed = Vec3.ZERO;

   public CircleParticleProcessor(Vec3 point, boolean forward, boolean in, float circleCount) {
      this.point = point;
      this.forward = forward;
      this.in = in;
      this.circleCount = circleCount;
   }

   @Override
   public ParticleProcessorType<CircleParticleProcessor> type() {
      return FDParticleProcessors.CIRCLE_PARTICLE_PROCESSOR;
   }

   @Override
   public void init(Particle particle) {
      this.initialPoint = new Vec3(particle.x, particle.y, particle.z);
      Vec3 b = this.initialPoint.subtract(this.point);
      Vec3 left = b.cross(new Vec3(0.0, 1.0, 0.0));
      Vec3 axis = left.cross(b);
      this.axis = new Vector3f((float)axis.x, (float)axis.y, (float)axis.z).normalize();
      if (!this.in) {
         particle.setPos(this.point.x, this.point.y, this.point.z);
         particle.xo = this.point.x;
         particle.yo = this.point.y;
         particle.zo = this.point.z;
         this.previousPoint = this.point;
      } else {
         this.previousPoint = this.initialPoint;
      }
   }

   @Override
   public void processParticle(Particle particle) {
      int particleLifetime = particle.lifetime - 2;
      if (particle.age <= particleLifetime) {
         float p2 = (float)particle.age / particleLifetime;
         double len = this.initialPoint.subtract(this.point).length();
         double targetRad;
         if (this.in) {
            targetRad = len * (1.0F - p2);
         } else {
            targetRad = len * p2;
         }

         float fullAngle = (float) (Math.PI * 2) * this.circleCount;
         float targetAngle = fullAngle * p2;
         if (!this.forward) {
            targetAngle = -targetAngle;
         }

         Quaternionf q = new Quaternionf(new AxisAngle4f(targetAngle, this.axis.x, this.axis.y, this.axis.z));
         Vector3d p = q.transform(
            (this.initialPoint.x - this.point.x) / len, (this.initialPoint.y - this.point.y) / len, (this.initialPoint.z - this.point.z) / len, new Vector3d()
         );
         Vec3 targetPoint = this.point.add(p.x * targetRad, p.y * targetRad, p.z * targetRad);
         Vec3 speed = targetPoint.subtract(this.previousPoint);
         this.previousPoint = targetPoint;
         particle.xd = particle.xd - this.oldSpeed.x;
         particle.yd = particle.yd - this.oldSpeed.y;
         particle.zd = particle.zd - this.oldSpeed.z;
         particle.xd = particle.xd + speed.x;
         particle.yd = particle.yd + speed.y;
         particle.zd = particle.zd + speed.z;
         this.oldSpeed = speed;
      }
   }

   public static class Type implements ParticleProcessorType<CircleParticleProcessor> {
      public static final StreamCodec<FriendlyByteBuf, CircleParticleProcessor> STREAM_CODEC = StreamCodec.composite(
         FDByteBufCodecs.VEC3,
         v -> v.point,
         ByteBufCodecs.BOOL,
         v -> v.forward,
         ByteBufCodecs.BOOL,
         v -> v.in,
         ByteBufCodecs.FLOAT,
         v -> v.circleCount,
         CircleParticleProcessor::new
      );
      public static final Codec<CircleParticleProcessor> CODEC = RecordCodecBuilder.create(
         p -> p.group(
               FDCodecs.VEC3.fieldOf("point").forGetter(v -> v.point),
               Codec.BOOL.fieldOf("forward").forGetter(v -> v.forward),
               Codec.BOOL.fieldOf("in").forGetter(v -> v.in),
               Codec.FLOAT.fieldOf("circleCount").forGetter(v -> v.circleCount)
            )
            .apply(p, CircleParticleProcessor::new)
      );

      @Override
      public StreamCodec<FriendlyByteBuf, CircleParticleProcessor> streamCodec() {
         return STREAM_CODEC;
      }

      @Override
      public Codec<CircleParticleProcessor> codec() {
         return CODEC;
      }

      @Override
      public ResourceLocation id() {
         return ResourceLocation.tryBuild("fdlib", "circle_particle_processor");
      }
   }
}
