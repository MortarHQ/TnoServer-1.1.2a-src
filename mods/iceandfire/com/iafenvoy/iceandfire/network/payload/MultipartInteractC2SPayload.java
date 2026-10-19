package com.iafenvoy.iceandfire.network.payload;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record MultipartInteractC2SPayload(UUID creatureID, float dmg) implements CustomPacketPayload {
   private static final ResourceLocation IDENTIFIER = ResourceLocation.fromNamespaceAndPath("iceandfire", "multipart_interact");
   public static final Type<MultipartInteractC2SPayload> ID = new Type(IDENTIFIER);
   public static final StreamCodec<ByteBuf, MultipartInteractC2SPayload> CODEC = ByteBufCodecs.fromCodec(
      RecordCodecBuilder.create(
         i -> i.group(
               UUIDUtil.AUTHLIB_CODEC.fieldOf("blockPos").forGetter(MultipartInteractC2SPayload::creatureID),
               Codec.FLOAT.fieldOf("isProducing").forGetter(MultipartInteractC2SPayload::dmg)
            )
            .apply(i, MultipartInteractC2SPayload::new)
      )
   );

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }
}
