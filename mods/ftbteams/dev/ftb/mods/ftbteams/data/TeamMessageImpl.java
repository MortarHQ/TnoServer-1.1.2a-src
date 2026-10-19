package dev.ftb.mods.ftbteams.data;

import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftbteams.api.TeamMessage;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TeamMessageImpl(UUID sender, long date, Component text) implements TeamMessage {
   public static final StreamCodec<RegistryFriendlyByteBuf, TeamMessage> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      TeamMessage::sender,
      ByteBufCodecs.VAR_LONG,
      TeamMessage::date,
      ComponentSerialization.STREAM_CODEC,
      TeamMessage::text,
      TeamMessageImpl::new
   );

   public static TeamMessage fromNBT(CompoundTag tag, Provider provider) {
      return new TeamMessageImpl(UUID.fromString(tag.getString("from")), tag.getLong("date"), Serializer.fromJson(tag.getString("text"), provider));
   }

   public static CompoundTag toNBT(TeamMessage msg, Provider provider) {
      SNBTCompoundTag tag = new SNBTCompoundTag();
      tag.singleLine();
      tag.putString("from", msg.sender().toString());
      tag.putLong("date", msg.date());
      tag.putString("text", Serializer.toJson(msg.text(), provider));
      return tag;
   }
}
