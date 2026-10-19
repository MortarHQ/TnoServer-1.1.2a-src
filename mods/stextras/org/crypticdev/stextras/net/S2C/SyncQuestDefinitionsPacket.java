package org.crypticdev.stextras.net.S2C;

import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.net.STNetworkCompat;
import org.crypticdev.stextras.net.STNetworkGuards;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public record SyncQuestDefinitionsPacket(List<QuestDefinition> quests, boolean replaceExisting) implements CustomPacketPayload {
   public static final Type<SyncQuestDefinitionsPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "sync_quest_defs"));
   public static final StreamCodec<FriendlyByteBuf, SyncQuestDefinitionsPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> {
      ListTag list = new ListTag();

      for (QuestDefinition q : packet.quests) {
         list.add(q.toNbt());
      }

      CompoundTag wrapper = new CompoundTag();
      wrapper.put("list", list);
      wrapper.putBoolean("replace", packet.replaceExisting());
      buf.writeNbt(wrapper);
   }, buf -> {
      CompoundTag wrapper = buf.readNbt();
      ListTag list = wrapper.getList("list", 10);
      List<QuestDefinition> quests = new ArrayList<>();

      for (int i = 0; i < list.size(); i++) {
         quests.add(QuestDefinition.fromNbt(list.getCompound(i)));
      }

      return new SyncQuestDefinitionsPacket(quests, wrapper.getBoolean("replace"));
   });

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
      STNetworkCompat.registerS2CType(TYPE, STREAM_CODEC);
   }

   public static void registerClient() {
      NetworkManager.registerReceiver(NetworkManager.s2c(), TYPE, STREAM_CODEC, (payload, context) -> context.queue(() -> {
         if (payload.replaceExisting()) {
            QuestRegistry.INSTANCE.setQuestsOnClient(payload.quests());
         } else {
            QuestRegistry.INSTANCE.mergeQuestsOnClient(payload.quests());
         }
      }));
   }

   public static void sendToPlayer(ServerPlayer player) {
      List<QuestDefinition> allQuests = new ArrayList<>(QuestRegistry.INSTANCE.all());
      NetworkManager.sendToPlayer(player, new SyncQuestDefinitionsPacket(allQuests, true));
   }

   public static void sendReferencedToPlayer(ServerPlayer player, boolean includeAllRaceQuests) {
      sendReferencedToPlayer(player, includeAllRaceQuests, true);
   }

   public static void sendReferencedToPlayer(ServerPlayer player, boolean includeAllRaceQuests, boolean replaceExisting) {
      if (!STNetworkGuards.canSendTo(player)) {
         Stextras.LOGGER
            .warn(
               "[NETWORK_GUARD] Skipped SyncQuestDefinitionsPacket payload because player cannot receive packets | player={}",
               STNetworkGuards.playerName(player)
            );
      } else {
         List<QuestDefinition> quests = collectReferencedQuests(player, includeAllRaceQuests);
         NetworkManager.sendToPlayer(player, new SyncQuestDefinitionsPacket(quests, replaceExisting));
      }
   }

   private static List<QuestDefinition> collectReferencedQuests(ServerPlayer player, boolean includeAllRaceQuests) {
      Set<ResourceLocation> ids = new LinkedHashSet<>();
      if (player instanceof ISTExtrasQuests questsAccess) {
         STExtrasQuestStorage store = questsAccess.stextras$getQuests();

         for (QuestCategory category : QuestCategory.values()) {
            ids.addAll(store.active(category));
            ids.addAll(store.completed(category));
            ids.addAll(store.progressMap(category).keySet());
         }
      }

      if (includeAllRaceQuests) {
         for (QuestDefinition quest : QuestRegistry.INSTANCE.byCategory(QuestCategory.RACE_PRESTIGE)) {
            if (quest != null && quest.id != null) {
               ids.add(quest.id);
            }
         }
      }

      List<QuestDefinition> quests = new ArrayList<>(ids.size());
      ids.stream().sorted(Comparator.comparing(ResourceLocation::toString)).map(QuestRegistry.INSTANCE::byId).filter(Objects::nonNull).forEach(quests::add);
      return quests;
   }
}
