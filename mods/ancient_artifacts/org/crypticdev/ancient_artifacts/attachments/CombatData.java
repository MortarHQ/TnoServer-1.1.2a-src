package org.crypticdev.ancient_artifacts.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CombatData {
   public static final Codec<CombatData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.LONG.fieldOf("last_combat_time").forGetter(CombatData::getLastCombatTime),
            Codec.LONG.fieldOf("first_combat_time").forGetter(CombatData::getFirstCombatTime)
         )
         .apply(instance, CombatData::new)
   );
   private long lastCombatTime;
   private long firstCombatTime;

   public CombatData(long lastCombatTime, long firstCombatTime) {
      this.lastCombatTime = lastCombatTime;
      this.firstCombatTime = firstCombatTime;
   }

   public long getLastCombatTime() {
      return this.lastCombatTime;
   }

   public long getFirstCombatTime() {
      return this.firstCombatTime;
   }

   public void setLastCombatTime(long time) {
      this.lastCombatTime = time;
   }

   public void setFirstCombatTime(long time) {
      this.firstCombatTime = time;
   }
}
