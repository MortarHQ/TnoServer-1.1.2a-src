package dev.ftb.mods.ftbteams.client;

import com.mojang.authlib.GameProfile;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class KnownClientPlayerNet {
   public static final StreamCodec<FriendlyByteBuf, KnownClientPlayer> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      KnownClientPlayer::id,
      ByteBufCodecs.STRING_UTF8,
      KnownClientPlayer::name,
      ByteBufCodecs.BOOL,
      KnownClientPlayer::online,
      UUIDUtil.STREAM_CODEC,
      KnownClientPlayer::teamId,
      ByteBufCodecs.GAME_PROFILE,
      KnownClientPlayer::profile,
      ByteBufCodecs.COMPOUND_TAG,
      KnownClientPlayer::extraData,
      KnownClientPlayer::new
   );

   public static KnownClientPlayer fromNetwork(FriendlyByteBuf buf) {
      UUID id = buf.readUUID();
      String name = buf.readUtf(32767);
      boolean online = buf.readBoolean();
      UUID teamId = buf.readUUID();
      CompoundTag extraData = buf.readNbt();
      return new KnownClientPlayer(id, name, online, teamId, new GameProfile(id, name), extraData);
   }

   public static void write(KnownClientPlayer kcp, FriendlyByteBuf buf) {
      buf.writeUUID(kcp.id());
      buf.writeUtf(kcp.name(), 32767);
      buf.writeBoolean(kcp.online());
      buf.writeUUID(kcp.teamId());
      buf.writeNbt(kcp.extraData());
   }
}
