package com.iafenvoy.iceandfire.network.payload;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.phys.Vec3;

public record LightningBoltS2CPayload(List<Tuple<Vec3, Vec3>> lightnings) implements CustomPacketPayload {
   private static final ResourceLocation IDENTIFIER = ResourceLocation.fromNamespaceAndPath("iceandfire", "lightning_bolt_s2c");
   public static final Type<LightningBoltS2CPayload> ID = new Type(IDENTIFIER);
   public static final StreamCodec<ByteBuf, LightningBoltS2CPayload> CODEC = ByteBufCodecs.fromCodec(
      RecordCodecBuilder.create(
         i -> i.group(
               RecordCodecBuilder.create(
                     i1 -> i1.group(Vec3.CODEC.fieldOf("left").forGetter(Tuple::getA), Vec3.CODEC.fieldOf("right").forGetter(Tuple::getB))
                        .apply(i1, Tuple::new)
                  )
                  .listOf()
                  .fieldOf("lightnings")
                  .forGetter(LightningBoltS2CPayload::lightnings)
            )
            .apply(i, LightningBoltS2CPayload::new)
      )
   );

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }
}
