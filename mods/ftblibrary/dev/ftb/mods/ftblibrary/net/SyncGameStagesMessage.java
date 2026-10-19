package dev.ftb.mods.ftblibrary.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.player.Player;

public record SyncGameStagesMessage(Collection<String> stages, SyncGameStagesMessage.Operation op) implements CustomPacketPayload {
   public static final Type<SyncGameStagesMessage> TYPE = new Type(FTBLibrary.rl("sync_game_stage"));
   public static final StreamCodec<FriendlyByteBuf, SyncGameStagesMessage> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.collection(ArrayList::new)),
      SyncGameStagesMessage::stages,
      NetworkHelper.enumStreamCodec(SyncGameStagesMessage.Operation.class),
      SyncGameStagesMessage::op,
      SyncGameStagesMessage::new
   );

   public static SyncGameStagesMessage add(String tag) {
      return new SyncGameStagesMessage(List.of(tag), SyncGameStagesMessage.Operation.ADD);
   }

   public static SyncGameStagesMessage add(Collection<String> tags) {
      return new SyncGameStagesMessage(tags, SyncGameStagesMessage.Operation.ADD);
   }

   public static SyncGameStagesMessage remove(String tag) {
      return new SyncGameStagesMessage(List.of(tag), SyncGameStagesMessage.Operation.REMOVE);
   }

   public static SyncGameStagesMessage fullSync(Player player) {
      return new SyncGameStagesMessage(player.getTags(), SyncGameStagesMessage.Operation.REPLACE);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(SyncGameStagesMessage message, PacketContext context) {
      context.queue(() -> {
         switch (message.op) {
            case ADD:
               context.getPlayer().getTags().addAll(message.stages);
               break;
            case REMOVE:
               context.getPlayer().getTags().removeAll(message.stages);
               break;
            case REPLACE:
               context.getPlayer().getTags().clear();
               context.getPlayer().getTags().addAll(message.stages);
         }
      });
   }

   public enum Operation {
      ADD,
      REMOVE,
      REPLACE;
   }
}
