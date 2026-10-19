package org.crypticdev.stextras.net;

import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.net.S2C.SyncQuestDefinitionsPacket;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.registry.RacePrestigeMetadataRegistry;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.storage.quest.client.ClientSTExtrasQuests;

public record STExtrasQuestSync(CompoundTag data) implements CustomPacketPayload {
   public static final Type<STExtrasQuestSync> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "sync_quests"));
   private static final String KEY_RACE_IDS = "race_ids";
   private static final String KEY_RACE_QUESTS = "race_quests";
   private static final String KEY_SOUL_GRADE_LEVELS = "soul_grade_levels";
   public static final StreamCodec<FriendlyByteBuf, STExtrasQuestSync> STREAM_CODEC = StreamCodec.of(
      (buf, p) -> buf.writeNbt(p.data()), buf -> new STExtrasQuestSync(buf.readNbt())
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
      STNetworkCompat.registerS2CType(TYPE, STREAM_CODEC);
   }

   public static void registerClient() {
      NetworkManager.registerReceiver(NetworkManager.s2c(), TYPE, STREAM_CODEC, (payload, context) -> {
         CompoundTag nbt = payload.data();
         context.queue(() -> {
            if (Minecraft.getInstance().player instanceof ClientSTExtrasQuests c) {
               STExtrasQuestStorage s = c.stextras$clientQuests();
               s.fromNet(nbt);
            }

            Set<ResourceLocation> ids = readRaceIds(nbt);
            if (!ids.isEmpty()) {
               STExtarsStorage.Quests.acceptRaceIdsSync(ids);
            }

            Map<ResourceLocation, List<ResourceLocation>> rq = readRaceQuests(nbt);
            if (!rq.isEmpty()) {
               STExtarsStorage.Quests.acceptRaceQuestsSync(rq);
            }

            List<Integer> levels = readSoulGradeLevels(nbt);
            if (!levels.isEmpty()) {
               STExtarsStorage.Player.acceptSoulGradeLevelSync(levels);
            }
         });
      });
   }

   public static void sendTo(ServerPlayer player, CompoundTag nbt) {
      CompoundTag payload = toClientPayload(nbt);
      SyncQuestDefinitionsPacket.sendReferencedToPlayer(player, false, false);
      sendPayload(player, payload);
   }

   private static void sendPayload(ServerPlayer player, CompoundTag nbt) {
      if (!STNetworkGuards.canSendTo(player)) {
         Stextras.LOGGER
            .warn("[NETWORK_GUARD] Skipped STExtrasQuestSync payload because player cannot receive packets | player={}", STNetworkGuards.playerName(player));
      } else {
         NetworkManager.sendToPlayer(player, new STExtrasQuestSync(nbt));
      }
   }

   public static CompoundTag attachRaceData(CompoundTag base) {
      CompoundTag out = toClientPayload(base);
      ListTag idList = new ListTag();

      for (ResourceLocation id : RacePrestigeMetadataRegistry.INSTANCE.getAllRaceIds()) {
         idList.add(StringTag.valueOf(id.toString()));
      }

      out.put("race_ids", idList);
      CompoundTag raceQuests = new CompoundTag();
      List<QuestDefinition> allRaceQuests = QuestRegistry.INSTANCE.byCategory(QuestCategory.RACE_PRESTIGE);
      Map<ResourceLocation, List<ResourceLocation>> grouped = allRaceQuests.stream()
         .filter(q -> q.race != null)
         .collect(Collectors.groupingBy(q -> q.race, LinkedHashMap::new, Collectors.mapping(q -> q.id, Collectors.toList())));

      for (Entry<ResourceLocation, List<ResourceLocation>> e : grouped.entrySet()) {
         ListTag qList = new ListTag();

         for (ResourceLocation qid : e.getValue()) {
            qList.add(StringTag.valueOf(qid.toString()));
         }

         raceQuests.put(e.getKey().toString(), qList);
      }

      out.put("race_quests", raceQuests);
      ListTag soulGradeLevels = new ListTag();

      for (int threshold : STExtarsStorage.Player.getSoulGradeLevelThresholds()) {
         soulGradeLevels.add(IntTag.valueOf(threshold));
      }

      out.put("soul_grade_levels", soulGradeLevels);
      return out;
   }

   public static void sendWithRaceIds(ServerPlayer player, CompoundTag questNbt) {
      CompoundTag payload = attachRaceData(questNbt);
      SyncQuestDefinitionsPacket.sendReferencedToPlayer(player, true, true);
      sendPayload(player, payload);
   }

   public static void sendRaceDataOnly(ServerPlayer player) {
      CompoundTag payload = attachRaceData(new CompoundTag());
      SyncQuestDefinitionsPacket.sendReferencedToPlayer(player, true, true);
      sendPayload(player, payload);
   }

   private static CompoundTag toClientPayload(CompoundTag source) {
      CompoundTag out = new CompoundTag();
      if (source == null) {
         return out;
      }

      if (source.contains("cooldowns", 10)) {
         out.put("cooldowns", source.getCompound("cooldowns").copy());
      }

      if (source.contains("completed_races", 9)) {
         out.put("completed_races", source.getList("completed_races", 8).copy());
      }

      for (QuestCategory category : QuestCategory.values()) {
         String key = category.name().toLowerCase(Locale.ROOT);
         CompoundTag sourceCategory = source.getCompound(key);
         CompoundTag categoryTag = new CompoundTag();
         if (sourceCategory.contains("active", 9)) {
            categoryTag.put("active", sourceCategory.getList("active", 8).copy());
         }

         if (sourceCategory.contains("completed", 9)) {
            categoryTag.put("completed", sourceCategory.getList("completed", 8).copy());
         }

         if (sourceCategory.contains("progress", 10)) {
            categoryTag.put("progress", sourceCategory.getCompound("progress").copy());
         }

         if (!categoryTag.isEmpty()) {
            out.put(key, categoryTag);
         }
      }

      return out;
   }

   private static Set<ResourceLocation> readRaceIds(CompoundTag nbt) {
      Set<ResourceLocation> out = new LinkedHashSet<>();
      if (nbt != null && nbt.contains("race_ids", 9)) {
         ListTag list = nbt.getList("race_ids", 8);

         for (int i = 0; i < list.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(list.getString(i));
            if (id != null) {
               out.add(id);
            }
         }

         return out;
      } else {
         return out;
      }
   }

   private static Map<ResourceLocation, List<ResourceLocation>> readRaceQuests(CompoundTag nbt) {
      Map<ResourceLocation, List<ResourceLocation>> out = new LinkedHashMap<>();
      if (nbt != null && nbt.contains("race_quests", 10)) {
         CompoundTag byRace = nbt.getCompound("race_quests");

         for (String key : byRace.getAllKeys()) {
            ResourceLocation raceId = ResourceLocation.tryParse(key);
            if (raceId != null) {
               ListTag list = byRace.getList(key, 8);
               List<ResourceLocation> quests = new ArrayList<>(list.size());

               for (int i = 0; i < list.size(); i++) {
                  ResourceLocation qid = ResourceLocation.tryParse(list.getString(i));
                  if (qid != null) {
                     quests.add(qid);
                  }
               }

               out.put(raceId, quests);
            }
         }

         return out;
      } else {
         return out;
      }
   }

   private static List<Integer> readSoulGradeLevels(CompoundTag nbt) {
      List<Integer> out = new ArrayList<>();
      if (nbt != null && nbt.contains("soul_grade_levels", 9)) {
         ListTag list = nbt.getList("soul_grade_levels", 3);

         for (int i = 0; i < list.size(); i++) {
            out.add(list.getInt(i));
         }

         return out;
      } else {
         return out;
      }
   }
}
