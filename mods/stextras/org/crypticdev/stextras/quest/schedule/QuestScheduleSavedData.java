package org.crypticdev.stextras.quest.schedule;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class QuestScheduleSavedData extends SavedData {
   private static final String KEY = "stextras_global_quests";
   private final QuestSchedule schedule = new QuestSchedule();

   public QuestSchedule schedule() {
      return this.schedule;
   }

   public static QuestScheduleSavedData get(MinecraftServer server) {
      DimensionDataStorage storage = server.overworld().getDataStorage();
      return (QuestScheduleSavedData)storage.computeIfAbsent(
         new Factory(QuestScheduleSavedData::new, (nbt, provider) -> load(nbt), DataFixTypes.LEVEL), "stextras_global_quests"
      );
   }

   public static QuestScheduleSavedData load(CompoundTag tag) {
      QuestScheduleSavedData data = new QuestScheduleSavedData();
      if (tag.contains("schedule")) {
         QuestSchedule loaded = QuestSchedule.fromNbt(tag.getCompound("schedule"));
         data.schedule().dailyCode = loaded.dailyCode;
         data.schedule().dailyCount = loaded.dailyCount;
         data.schedule().dailyNonce = loaded.dailyNonce;
         data.schedule().weeklyCode = loaded.weeklyCode;
         data.schedule().weeklyCount = loaded.weeklyCount;
         data.schedule().weeklyNonce = loaded.weeklyNonce;
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      tag.put("schedule", this.schedule.toNbt());
      return tag;
   }
}
