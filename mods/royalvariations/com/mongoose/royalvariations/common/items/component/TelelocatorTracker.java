package com.mongoose.royalvariations.common.items.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;

public record TelelocatorTracker(Optional<GlobalPos> target) {
   public static final Codec<TelelocatorTracker> CODEC = RecordCodecBuilder.create(
      p_337949_ -> p_337949_.group(GlobalPos.CODEC.optionalFieldOf("pos").forGetter(TelelocatorTracker::getTarget)).apply(p_337949_, TelelocatorTracker::new)
   );
   public static final StreamCodec<ByteBuf, TelelocatorTracker> STREAM_CODEC = StreamCodec.composite(
      GlobalPos.STREAM_CODEC.apply(ByteBufCodecs::optional), TelelocatorTracker::getTarget, TelelocatorTracker::new
   );

   public Optional<GlobalPos> getTarget() {
      return this.target;
   }

   public TelelocatorTracker tick(ServerLevel level) {
      if (this.target.isPresent()) {
         if (this.target.get().dimension() != level.dimension()) {
            return this;
         }

         BlockPos blockpos = this.target.get().pos();
         return level.isInWorldBounds(blockpos) ? this : new TelelocatorTracker(Optional.empty());
      } else {
         return this;
      }
   }
}
