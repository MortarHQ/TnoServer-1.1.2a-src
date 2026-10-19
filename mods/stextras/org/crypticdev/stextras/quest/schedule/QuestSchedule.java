package org.crypticdev.stextras.quest.schedule;

import net.minecraft.nbt.CompoundTag;

public class QuestSchedule {
   public long dailyCode = -1L;
   public int dailyCount = 0;
   public int dailyNonce = 0;
   public long weeklyCode = -1L;
   public int weeklyCount = 0;
   public int weeklyNonce = 0;

   public CompoundTag toNbt() {
      CompoundTag tag = new CompoundTag();
      tag.putLong("daily_code", this.dailyCode);
      tag.putInt("daily_count", this.dailyCount);
      tag.putInt("daily_nonce", this.dailyNonce);
      tag.putLong("weekly_code", this.weeklyCode);
      tag.putInt("weekly_count", this.weeklyCount);
      tag.putInt("weekly_nonce", this.weeklyNonce);
      return tag;
   }

   public static QuestSchedule fromNbt(CompoundTag tag) {
      QuestSchedule schedule = new QuestSchedule();
      if (tag.contains("daily_code")) {
         schedule.dailyCode = tag.getLong("daily_code");
      }

      if (tag.contains("daily_count")) {
         schedule.dailyCount = tag.getInt("daily_count");
      }

      if (tag.contains("daily_nonce")) {
         schedule.dailyNonce = tag.getInt("daily_nonce");
      }

      if (tag.contains("weekly_code")) {
         schedule.weeklyCode = tag.getLong("weekly_code");
      }

      if (tag.contains("weekly_count")) {
         schedule.weeklyCount = tag.getInt("weekly_count");
      }

      if (tag.contains("weekly_nonce")) {
         schedule.weeklyNonce = tag.getInt("weekly_nonce");
      }

      return schedule;
   }
}
