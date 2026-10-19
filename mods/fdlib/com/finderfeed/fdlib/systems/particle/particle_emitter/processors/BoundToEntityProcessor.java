package com.finderfeed.fdlib.systems.particle.particle_emitter.processors;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.systems.particle.particle_emitter.EmitterProcessor;
import com.finderfeed.fdlib.systems.particle.particle_emitter.EmitterProcessorType;
import com.finderfeed.fdlib.systems.particle.particle_emitter.FDEmitterProcessorTypes;
import com.finderfeed.fdlib.systems.particle.particle_emitter.ParticleEmitter;
import com.finderfeed.fdlib.util.FDByteBufCodecs;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BoundToEntityProcessor implements EmitterProcessor<BoundToEntityProcessor> {
   private int id;
   private Vec3 offset;
   private Entity entity;

   public BoundToEntityProcessor(int id, Vec3 offset) {
      this.id = id;
      this.offset = offset;
   }

   @Override
   public void initEmitter(ParticleEmitter emitter) {
      Level level = FDClientHelpers.getClientLevel();
      Entity entity = level.getEntity(this.id);
      if (entity == null) {
         emitter.removed = true;
      } else {
         this.entity = entity;
         emitter.data.position = entity.position().add(this.offset);
      }
   }

   @Override
   public void tickEmitter(ParticleEmitter emitter) {
      if (this.entity == null) {
         emitter.removed = true;
      } else {
         emitter.data.position = this.entity.position().add(this.offset);
         if (this.entity.isRemoved()) {
            emitter.removed = true;
         }
      }
   }

   @Override
   public void tickParticle(Particle particle) {
   }

   @Override
   public void initParticle(Particle particle) {
   }

   @Override
   public EmitterProcessorType<BoundToEntityProcessor> type() {
      return FDEmitterProcessorTypes.BOUND_TO_ENTITY;
   }

   public static class Type implements EmitterProcessorType<BoundToEntityProcessor> {
      public static final StreamCodec<FriendlyByteBuf, BoundToEntityProcessor> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.INT, v -> v.id, FDByteBufCodecs.VEC3, v -> v.offset, (id, offset) -> new BoundToEntityProcessor(id, offset)
      );

      @Override
      public StreamCodec<FriendlyByteBuf, BoundToEntityProcessor> codec() {
         return STREAM_CODEC;
      }

      @Override
      public ResourceLocation id() {
         return ResourceLocation.tryBuild("fdlib", "bound_to_entity");
      }
   }
}
