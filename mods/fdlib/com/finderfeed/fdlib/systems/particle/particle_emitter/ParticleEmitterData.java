package com.finderfeed.fdlib.systems.particle.particle_emitter;

import com.finderfeed.fdlib.util.FDByteBufCodecs;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ParticleEmitterData {
   public static final StreamCodec<FriendlyByteBuf, List<ParticleOptions>> OPTIONS_CODEC = new StreamCodec<FriendlyByteBuf, List<ParticleOptions>>() {
      public List<ParticleOptions> decode(FriendlyByteBuf buf) {
         List<ParticleOptions> list = new ArrayList<>();
         int size = buf.readInt();

         for (int i = 0; i < size; i++) {
            ResourceLocation location = buf.readResourceLocation();
            ParticleType<?> type = (ParticleType<?>)BuiltInRegistries.PARTICLE_TYPE.get(location);
            if (type == null) {
               throw new RuntimeException("Unknown particle type: " + location);
            }

            ParticleOptions options = (ParticleOptions)type.streamCodec().decode(buf);
            list.add(options);
         }

         return list;
      }

      public void encode(FriendlyByteBuf buf, List<ParticleOptions> list) {
         buf.writeInt(list.size());

         for (ParticleOptions options : list) {
            ParticleType<?> type = options.getType();
            ResourceLocation key = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
            buf.writeResourceLocation(key);
            type.streamCodec().encode(buf, options);
         }
      }
   };
   public static final StreamCodec<FriendlyByteBuf, ParticleEmitterData> STREAM_CODEC = StreamCodec.composite(
      FDByteBufCodecs.VEC3,
      v -> v.position,
      ByteBufCodecs.INT,
      v -> v.lifetime,
      ByteBufCodecs.INT,
      v -> v.particlesPerTick,
      EmitterProcessor.STREAM_CODEC,
      v -> v.processor,
      OPTIONS_CODEC,
      v -> v.particleTypes,
      (position, lifetime, particlesPerTick, processor, types) -> {
         ParticleEmitterData data = new ParticleEmitterData();
         data.position = position;
         data.lifetime = lifetime;
         data.processor = processor;
         data.particleTypes = types;
         data.particlesPerTick = particlesPerTick;
         return data;
      }
   );
   public Vec3 position = Vec3.ZERO;
   public int particlesPerTick = 1;
   public int lifetime = 20;
   public EmitterProcessor<?> processor = new EmptyEmitterProcessor();
   public List<ParticleOptions> particleTypes = new ArrayList<>();

   private ParticleEmitterData() {
   }

   public static ParticleEmitterData.Builder builder(ParticleOptions options) {
      return new ParticleEmitterData.Builder(options);
   }

   public static class Builder {
      private ParticleEmitterData data = new ParticleEmitterData();

      public Builder(ParticleOptions options) {
         this.data.particleTypes.add(options);
      }

      public ParticleEmitterData.Builder position(Vec3 v) {
         this.data.position = v;
         return this;
      }

      public ParticleEmitterData.Builder lifetime(int lifetime) {
         this.data.lifetime = lifetime;
         return this;
      }

      public ParticleEmitterData.Builder processor(EmitterProcessor<?> processor) {
         this.data.processor = processor;
         return this;
      }

      public ParticleEmitterData.Builder particlesPerTick(int amount) {
         this.data.particlesPerTick = amount;
         return this;
      }

      public ParticleEmitterData.Builder addParticle(ParticleOptions options) {
         this.data.particleTypes.add(options);
         return this;
      }

      public ParticleEmitterData build() {
         return this.data;
      }
   }
}
