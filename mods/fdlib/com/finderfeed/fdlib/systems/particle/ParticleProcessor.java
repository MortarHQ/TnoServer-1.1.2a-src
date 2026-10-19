package com.finderfeed.fdlib.systems.particle;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public interface ParticleProcessor<T extends ParticleProcessor<T>> {
   Codec<ResourceLocation> RL_CODEC = ResourceLocation.CODEC.fieldOf("type").codec();
   Codec<ParticleProcessor<?>> CODEC = new Codec<ParticleProcessor<?>>() {
      public <D> DataResult<Pair<ParticleProcessor<?>, D>> decode(DynamicOps<D> ops, D input) {
         DataResult<Pair<ResourceLocation, D>> result = ParticleProcessor.RL_CODEC.decode(ops, input);
         ResourceLocation location = (ResourceLocation)((Pair)result.getOrThrow()).getFirst();
         ParticleProcessorType<? extends ParticleProcessor<?>> type = (ParticleProcessorType<? extends ParticleProcessor<?>>)FDParticleProcessors.getType(
            location
         );
         Codec<ParticleProcessor<?>> particleProcessorCodec = (Codec<ParticleProcessor<?>>)type.codec();
         return particleProcessorCodec.decode(ops, input);
      }

      public <D> DataResult<D> encode(ParticleProcessor<?> value, DynamicOps<D> ops, D prefix) {
         ParticleProcessorType<?> type = value.type();
         Codec<ParticleProcessor<?>> codec = (Codec<ParticleProcessor<?>>)type.codec();
         return ParticleProcessor.RL_CODEC.encode(type.id(), ops, prefix).flatMap(f -> codec.encode(value, ops, f));
      }
   };
   StreamCodec<FriendlyByteBuf, ParticleProcessor<?>> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ParticleProcessor<?>>() {
      public ParticleProcessor<?> decode(FriendlyByteBuf buf) {
         ResourceLocation location = buf.readResourceLocation();
         ParticleProcessorType<? extends ParticleProcessor<?>> type = (ParticleProcessorType<? extends ParticleProcessor<?>>)FDParticleProcessors.getType(
            location
         );
         StreamCodec<FriendlyByteBuf, ? extends ParticleProcessor<?>> codec = (StreamCodec<FriendlyByteBuf, ? extends ParticleProcessor<?>>)type.streamCodec();
         return (ParticleProcessor<?>)codec.decode(buf);
      }

      public void encode(FriendlyByteBuf buf, ParticleProcessor<?> processor) {
         buf.writeResourceLocation(processor.type().id());
         ParticleProcessor.hackyEncode(buf, processor, (StreamCodec<FriendlyByteBuf, T>)processor.type().streamCodec());
      }
   };

   ParticleProcessorType<T> type();

   void processParticle(Particle var1);

   void init(Particle var1);

   private static <T extends ParticleProcessor<T>> void hackyEncode(
      FriendlyByteBuf buf, ParticleProcessor<?> processor, StreamCodec<FriendlyByteBuf, T> encoder
   ) {
      encoder.encode(buf, processor);
   }
}
