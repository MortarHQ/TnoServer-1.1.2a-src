package com.tno_tensura_extras.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record BoundCargo(ResourceKey<Level> dimension, BlockPos pos, UUID binder) {
   public static final Codec<BoundCargo> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(BoundCargo::dimension),
            BlockPos.CODEC.fieldOf("pos").forGetter(BoundCargo::pos),
            UUIDUtil.CODEC.fieldOf("binder").forGetter(BoundCargo::binder)
         )
         .apply(instance, BoundCargo::new)
   );
   public static final StreamCodec<ByteBuf, BoundCargo> STREAM_CODEC = StreamCodec.composite(
      ResourceKey.streamCodec(Registries.DIMENSION),
      BoundCargo::dimension,
      BlockPos.STREAM_CODEC,
      BoundCargo::pos,
      UUIDUtil.STREAM_CODEC,
      BoundCargo::binder,
      BoundCargo::new
   );
}
