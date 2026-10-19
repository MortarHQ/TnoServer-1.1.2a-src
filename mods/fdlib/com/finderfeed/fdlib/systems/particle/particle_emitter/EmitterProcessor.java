package com.finderfeed.fdlib.systems.particle.particle_emitter;

import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public interface EmitterProcessor<T extends EmitterProcessor<T>> {
   StreamCodec<FriendlyByteBuf, EmitterProcessor<?>> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, EmitterProcessor<?>>() {
      public void encode(FriendlyByteBuf buf, EmitterProcessor<?> emitter) {
         buf.writeResourceLocation(emitter.type().id());
         EmitterProcessor.hackyEncode(buf, emitter);
      }

      public EmitterProcessor<?> decode(FriendlyByteBuf buf) {
         ResourceLocation location = buf.readResourceLocation();
         EmitterProcessorType<?> type = FDEmitterProcessorTypes.get(location);
         if (type == null) {
            throw new RuntimeException("Emitter processor type unknown: " + location);
         } else {
            return (EmitterProcessor<?>)type.codec().decode(buf);
         }
      }
   };

   void initEmitter(ParticleEmitter var1);

   void tickEmitter(ParticleEmitter var1);

   void tickParticle(Particle var1);

   void initParticle(Particle var1);

   EmitterProcessorType<T> type();

   static <T extends EmitterProcessor<T>> void hackyEncode(FriendlyByteBuf buf, EmitterProcessor<T> emitterProcessor) {
      emitterProcessor.type().codec().encode(buf, emitterProcessor);
   }
}
