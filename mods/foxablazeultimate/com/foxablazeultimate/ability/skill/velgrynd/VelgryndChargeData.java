package com.foxablazeultimate.ability.skill.velgrynd;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;

public record VelgryndChargeData(UUID owner) {
   public static final Codec<VelgryndChargeData> CODEC = UUIDUtil.CODEC.xmap(VelgryndChargeData::new, VelgryndChargeData::owner);
   public static final StreamCodec<ByteBuf, VelgryndChargeData> STREAM_CODEC = UUIDUtil.STREAM_CODEC.map(VelgryndChargeData::new, VelgryndChargeData::owner);
}
