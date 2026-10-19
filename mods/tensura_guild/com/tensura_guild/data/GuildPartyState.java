package com.tensura_guild.data;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;

public record GuildPartyState(boolean inParty, UUID leaderId, String partyName, List<GuildPartyState.Member> members, List<GuildPartyState.Message> messages) {
   public static final int MAX_MEMBERS = 4;
   public static final int MAX_PARTY_NAME_LENGTH = 64;
   public static final int MAX_MEMBER_NAME_LENGTH = 32;
   public static final int MAX_MESSAGE_SENDER_LENGTH = 32;
   public static final int MAX_MESSAGE_CONTENT_LENGTH = 160;
   public static final int MAX_MESSAGES = 50;
   public static final GuildPartyState EMPTY = new GuildPartyState(false, new UUID(0L, 0L), "", List.of(), List.of());

   public GuildPartyState {
      leaderId = leaderId == null ? new UUID(0L, 0L) : leaderId;
      partyName = limitUtf(partyName, 64);
      members = List.copyOf(members == null ? List.of() : members);
      messages = List.copyOf(messages == null ? List.of() : messages);
   }

   public static GuildPartyState decode(ByteBuf buffer) {
      FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
      boolean inParty = buf.readBoolean();
      UUID leaderId = buf.readUUID();
      String partyName = buf.readUtf(64);
      int memberCount = Math.min(4, buf.readVarInt());
      List<GuildPartyState.Member> members = new ArrayList<>(memberCount);

      for (int i = 0; i < memberCount; i++) {
         members.add(GuildPartyState.Member.decode(buf));
      }

      int messageCount = Math.min(50, buf.readVarInt());
      List<GuildPartyState.Message> messages = new ArrayList<>(messageCount);

      for (int i = 0; i < messageCount; i++) {
         messages.add(GuildPartyState.Message.decode(buf));
      }

      return new GuildPartyState(inParty, leaderId, partyName, members, messages);
   }

   public void encode(ByteBuf buffer) {
      FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
      buf.writeBoolean(this.inParty);
      buf.writeUUID(this.leaderId);
      buf.writeUtf(limitUtf(this.partyName, 64), 64);
      buf.writeVarInt(Math.min(4, this.members.size()));

      for (int i = 0; i < Math.min(4, this.members.size()); i++) {
         this.members.get(i).encode(buf);
      }

      buf.writeVarInt(Math.min(50, this.messages.size()));

      for (int i = 0; i < Math.min(50, this.messages.size()); i++) {
         this.messages.get(i).encode(buf);
      }
   }

   private static String limitUtf(String value, int maxLength) {
      String safeValue = value == null ? "" : value;
      return safeValue.length() <= maxLength ? safeValue : safeValue.substring(0, maxLength);
   }

   public record Member(UUID id, String name, boolean leader) {
      public Member {
         id = id == null ? new UUID(0L, 0L) : id;
         name = GuildPartyState.limitUtf(name, 32);
      }

      private static GuildPartyState.Member decode(FriendlyByteBuf buf) {
         return new GuildPartyState.Member(buf.readUUID(), buf.readUtf(32), buf.readBoolean());
      }

      private void encode(FriendlyByteBuf buf) {
         buf.writeUUID(this.id);
         buf.writeUtf(GuildPartyState.limitUtf(this.name, 32), 32);
         buf.writeBoolean(this.leader);
      }
   }

   public record Message(UUID senderId, String senderName, String content, long sentAtMillis) {
      public Message {
         senderId = senderId == null ? new UUID(0L, 0L) : senderId;
         senderName = GuildPartyState.limitUtf(senderName, 32);
         content = GuildPartyState.limitUtf(content, 160);
         sentAtMillis = Math.max(0L, sentAtMillis);
      }

      private static GuildPartyState.Message decode(FriendlyByteBuf buf) {
         return new GuildPartyState.Message(buf.readUUID(), buf.readUtf(32), buf.readUtf(160), buf.readLong());
      }

      private void encode(FriendlyByteBuf buf) {
         buf.writeUUID(this.senderId);
         buf.writeUtf(GuildPartyState.limitUtf(this.senderName, 32), 32);
         buf.writeUtf(GuildPartyState.limitUtf(this.content, 160), 160);
         buf.writeLong(this.sentAtMillis);
      }
   }
}
