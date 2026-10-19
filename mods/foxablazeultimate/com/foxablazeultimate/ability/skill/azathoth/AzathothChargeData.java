package com.foxablazeultimate.ability.skill.azathoth;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record AzathothChargeData(UUID owner, UUID token, int ticks) {
   public static final Codec<AzathothChargeData> CODEC = RecordCodecBuilder.create(
      i -> i.group(
            UUIDUtil.CODEC.fieldOf("owner").forGetter(AzathothChargeData::owner),
            UUIDUtil.CODEC.fieldOf("token").forGetter(AzathothChargeData::token),
            Codec.intRange(1, 72000).fieldOf("ticks").forGetter(AzathothChargeData::ticks)
         )
         .apply(i, AzathothChargeData::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, AzathothChargeData> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, AzathothChargeData>() {
      public AzathothChargeData decode(RegistryFriendlyByteBuf buf) {
         return new AzathothChargeData(buf.readUUID(), buf.readUUID(), buf.readVarInt());
      }

      public void encode(RegistryFriendlyByteBuf buf, AzathothChargeData value) {
         buf.writeUUID(value.owner());
         buf.writeUUID(value.token());
         buf.writeVarInt(value.ticks());
      }
   };
}
