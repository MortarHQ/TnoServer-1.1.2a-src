package com.finderfeed.fdlib.systems.particle.particle_emitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class CompositeEmitterProcessor implements EmitterProcessor<CompositeEmitterProcessor> {
   private List<EmitterProcessor<?>> processors = new ArrayList<>();

   public static CompositeEmitterProcessor create(EmitterProcessor<?>... processors) {
      CompositeEmitterProcessor processor = new CompositeEmitterProcessor();
      processor.processors = Arrays.stream(processors).toList();
      return processor;
   }

   @Override
   public void initEmitter(ParticleEmitter emitter) {
      for (EmitterProcessor<?> processor : this.processors) {
         processor.initEmitter(emitter);
      }
   }

   @Override
   public void tickEmitter(ParticleEmitter emitter) {
      for (EmitterProcessor<?> processor : this.processors) {
         processor.tickEmitter(emitter);
      }
   }

   @Override
   public void tickParticle(Particle particle) {
      for (EmitterProcessor<?> processor : this.processors) {
         processor.tickParticle(particle);
      }
   }

   @Override
   public void initParticle(Particle particle) {
      for (EmitterProcessor<?> processor : this.processors) {
         processor.initParticle(particle);
      }
   }

   @Override
   public EmitterProcessorType<CompositeEmitterProcessor> type() {
      return FDEmitterProcessorTypes.COMPOSITE;
   }

   public static class Type implements EmitterProcessorType<CompositeEmitterProcessor> {
      public static StreamCodec<FriendlyByteBuf, CompositeEmitterProcessor> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, CompositeEmitterProcessor>() {
         public CompositeEmitterProcessor decode(FriendlyByteBuf buf) {
            CompositeEmitterProcessor processor = new CompositeEmitterProcessor();
            int len = buf.readInt();

            for (int i = 0; i < len; i++) {
               processor.processors.add((EmitterProcessor<?>)EmitterProcessor.STREAM_CODEC.decode(buf));
            }

            return processor;
         }

         public void encode(FriendlyByteBuf buf, CompositeEmitterProcessor processor) {
            buf.writeInt(processor.processors.size());

            for (EmitterProcessor<?> p : processor.processors) {
               EmitterProcessor.STREAM_CODEC.encode(buf, p);
            }
         }
      };

      @Override
      public StreamCodec<FriendlyByteBuf, CompositeEmitterProcessor> codec() {
         return STREAM_CODEC;
      }

      @Override
      public ResourceLocation id() {
         return ResourceLocation.tryBuild("fdlib", "composite");
      }
   }
}
