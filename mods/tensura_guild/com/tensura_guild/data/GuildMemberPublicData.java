package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record GuildMemberPublicData(boolean joinedGuild, Optional<String> title, boolean guildPrefixVisible) {
   public static final GuildMemberPublicData EMPTY = new GuildMemberPublicData(false, Optional.empty(), false);
   public static final Codec<GuildMemberPublicData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.BOOL.optionalFieldOf("joined_guild", false).forGetter(GuildMemberPublicData::joinedGuild),
            Codec.STRING.optionalFieldOf("title").forGetter(GuildMemberPublicData::title),
            Codec.BOOL.optionalFieldOf("guild_prefix_visible", false).forGetter(GuildMemberPublicData::guildPrefixVisible)
         )
         .apply(instance, GuildMemberPublicData::new)
   );
   public static final StreamCodec<ByteBuf, GuildMemberPublicData> STREAM_CODEC = new StreamCodec<ByteBuf, GuildMemberPublicData>() {
      public GuildMemberPublicData decode(ByteBuf buffer) {
         CompoundTag tag = new FriendlyByteBuf(buffer).readNbt();
         return tag == null
            ? GuildMemberPublicData.EMPTY
            : GuildMemberPublicData.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(GuildMemberPublicData.EMPTY);
      }

      public void encode(ByteBuf buffer, GuildMemberPublicData value) {
         CompoundTag tag = (CompoundTag)GuildMemberPublicData.CODEC.encodeStart(NbtOps.INSTANCE, value).result().orElseGet(CompoundTag::new);
         FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(buffer);
         friendlyByteBuf.writeNbt(tag);
      }
   };

   public GuildMemberPublicData {
      title = title == null ? Optional.empty() : title.filter(value -> !value.isBlank()).map(String::trim);
      guildPrefixVisible = joinedGuild && guildPrefixVisible;
   }

   public static GuildMemberPublicData from(GuildMemberData memberData) {
      return from(memberData, true);
   }

   public static GuildMemberPublicData from(GuildMemberData memberData, boolean globalGuildPrefixVisible) {
      return new GuildMemberPublicData(memberData.hasJoinedGuild(), memberData.title(), globalGuildPrefixVisible && memberData.guildPrefixVisible());
   }
}
