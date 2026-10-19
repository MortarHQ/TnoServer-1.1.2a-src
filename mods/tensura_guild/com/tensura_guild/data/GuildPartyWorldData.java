package com.tensura_guild.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class GuildPartyWorldData extends SavedData {
   private static final String DATA_NAME = "tensura_guild_parties";
   private final List<GuildPartyWorldData.SavedParty> parties = new ArrayList<>();

   public static GuildPartyWorldData get(MinecraftServer server) {
      ServerLevel level = server.overworld();
      return (GuildPartyWorldData)level.getDataStorage()
         .computeIfAbsent(new Factory(GuildPartyWorldData::new, GuildPartyWorldData::load, null), "tensura_guild_parties");
   }

   private static GuildPartyWorldData load(CompoundTag tag, Provider provider) {
      GuildPartyWorldData data = new GuildPartyWorldData();
      ListTag partyList = tag.getList("parties", 10);

      for (int i = 0; i < partyList.size(); i++) {
         CompoundTag partyTag = partyList.getCompound(i);
         if (partyTag.hasUUID("leader_id")) {
            UUID leaderId = partyTag.getUUID("leader_id");
            String name = partyTag.getString("name");
            List<UUID> members = new ArrayList<>();
            ListTag memberList = partyTag.getList("members", 10);

            for (int memberIndex = 0; memberIndex < memberList.size(); memberIndex++) {
               CompoundTag memberTag = memberList.getCompound(memberIndex);
               if (memberTag.hasUUID("id")) {
                  members.add(memberTag.getUUID("id"));
               }
            }

            if (!members.contains(leaderId)) {
               members.add(0, leaderId);
            }

            List<GuildPartyState.Message> messages = new ArrayList<>();
            ListTag messageList = partyTag.getList("messages", 10);

            for (int messageIndex = 0; messageIndex < messageList.size(); messageIndex++) {
               CompoundTag messageTag = messageList.getCompound(messageIndex);
               if (messageTag.hasUUID("sender_id")) {
                  messages.add(
                     new GuildPartyState.Message(
                        messageTag.getUUID("sender_id"),
                        messageTag.getString("sender_name"),
                        messageTag.getString("content"),
                        messageTag.getLong("sent_at_millis")
                     )
                  );
               }
            }

            if (!name.isBlank() && !members.isEmpty()) {
               data.parties.add(new GuildPartyWorldData.SavedParty(leaderId, name, members, messages));
            }
         }
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag partyList = new ListTag();

      for (GuildPartyWorldData.SavedParty party : this.parties) {
         CompoundTag partyTag = new CompoundTag();
         partyTag.putUUID("leader_id", party.leaderId());
         partyTag.putString("name", party.name());
         ListTag memberList = new ListTag();

         for (UUID memberId : party.members()) {
            CompoundTag memberTag = new CompoundTag();
            memberTag.putUUID("id", memberId);
            memberList.add(memberTag);
         }

         partyTag.put("members", memberList);
         ListTag messageList = new ListTag();

         for (GuildPartyState.Message message : party.messages()) {
            CompoundTag messageTag = new CompoundTag();
            messageTag.putUUID("sender_id", message.senderId());
            messageTag.putString("sender_name", message.senderName());
            messageTag.putString("content", message.content());
            messageTag.putLong("sent_at_millis", message.sentAtMillis());
            messageList.add(messageTag);
         }

         partyTag.put("messages", messageList);
         partyList.add(partyTag);
      }

      tag.put("parties", partyList);
      return tag;
   }

   public List<GuildPartyWorldData.SavedParty> parties() {
      return List.copyOf(this.parties);
   }

   public void replaceParties(Collection<GuildPartyWorldData.SavedParty> parties) {
      this.parties.clear();
      this.parties.addAll(parties);
      this.setDirty();
   }

   public record SavedParty(UUID leaderId, String name, List<UUID> members, List<GuildPartyState.Message> messages) {
      public SavedParty {
         name = name == null ? "" : name;
         members = List.copyOf(members == null ? List.of() : members);
         messages = List.copyOf(messages == null ? List.of() : messages);
      }
   }
}
