package com.finderfeed.fdlib.systems.particle.particle_emitter.processors;

import com.finderfeed.fdlib.systems.particle.particle_emitter.EmitterProcessor;
import com.finderfeed.fdlib.systems.particle.particle_emitter.EmitterProcessorType;
import com.finderfeed.fdlib.systems.particle.particle_emitter.FDEmitterProcessorTypes;
import com.finderfeed.fdlib.systems.particle.particle_emitter.ParticleEmitter;
import com.finderfeed.fdlib.util.FDByteBufCodecs;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Random;
import org.joml.Vector4f;

public class CircleSpawnProcessor implements EmitterProcessor<CircleSpawnProcessor> {
   private static final Random r = new Random();
   private Vec3 direction = new Vec3(0.0, -1.0, 0.0);
   private float minSpeed = 0.0F;
   private float maxSpeed = 0.1F;
   private float radius = 1.0F;
   private Matrix4f mt;

   public CircleSpawnProcessor(Vec3 direction, float minSpeed, float maxSpeed, float radius) {
      this.direction = direction.normalize();
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
      this.radius = radius;
   }

   @Override
   public void initEmitter(ParticleEmitter emitter) {
      Matrix4f mt = new Matrix4f();
      FDRenderUtil.applyMovementMatrixRotations(mt, this.direction);
      this.mt = mt;
   }

   @Override
   public void tickEmitter(ParticleEmitter emitter) {
   }

   @Override
   public void tickParticle(Particle particle) {
   }

   @Override
   public void initParticle(Particle particle) {
      float x = (r.nextFloat() * 2.0F - 1.0F) * this.radius;

      float z;
      for (z = (r.nextFloat() * 2.0F - 1.0F) * this.radius; x * x + z * z > this.radius * this.radius; z = (r.nextFloat() * 2.0F - 1.0F) * this.radius) {
         x = (r.nextFloat() * 2.0F - 1.0F) * this.radius;
      }

      Vector4f pos = this.mt.transform(x, 0.0F, z, 1.0F, new Vector4f());
      float sp = FDMathUtil.lerp(this.minSpeed, this.maxSpeed, r.nextFloat());
      particle.xo = pos.x + particle.x;
      particle.yo = pos.y + particle.y;
      particle.zo = pos.z + particle.z;
      particle.setPos(pos.x + particle.x, pos.y + particle.y, pos.z + particle.z);
      particle.xd = this.direction.x * sp;
      particle.yd = this.direction.y * sp;
      particle.zd = this.direction.z * sp;
   }

   @Override
   public EmitterProcessorType<CircleSpawnProcessor> type() {
      return FDEmitterProcessorTypes.CIRCLE_SPAWN_PROCESSOR;
   }

   public static class Type implements EmitterProcessorType<CircleSpawnProcessor> {
      public static final StreamCodec<FriendlyByteBuf, CircleSpawnProcessor> STREAM_CODEC = StreamCodec.composite(
         FDByteBufCodecs.VEC3,
         v -> v.direction,
         ByteBufCodecs.FLOAT,
         v -> v.minSpeed,
         ByteBufCodecs.FLOAT,
         v -> v.maxSpeed,
         ByteBufCodecs.FLOAT,
         v -> v.radius,
         (direction, minSpeed, maxSpeed, radius) -> new CircleSpawnProcessor(direction, minSpeed, maxSpeed, radius)
      );

      @Override
      public StreamCodec<FriendlyByteBuf, CircleSpawnProcessor> codec() {
         return STREAM_CODEC;
      }

      @Override
      public ResourceLocation id() {
         return ResourceLocation.tryBuild("fdlib", "circle_spawn_processor");
      }
   }
}
