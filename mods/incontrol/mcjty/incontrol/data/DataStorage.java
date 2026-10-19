package mcjty.incontrol.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import mcjty.incontrol.rules.PhaseRule;
import mcjty.incontrol.rules.RulesManager;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class DataStorage extends SavedData {
   private static final String NAME = "InControlData";
   private Boolean isDay = null;
   private int daycounter = 0;
   private final Set<String> phases = new HashSet<>();
   private final Map<String, Integer> numbers = new HashMap<>();
   private int checkCounter = 0;

   public DataStorage() {
   }

   public DataStorage(CompoundTag tag) {
      this.daycounter = tag.getInt("daycounter");
      if (tag.contains("isday")) {
         this.isDay = tag.getBoolean("isday");
      } else {
         this.isDay = null;
      }

      ListTag phasesTag = tag.getList("phases", 8);

      for (int i = 0; i < phasesTag.size(); i++) {
         this.phases.add(phasesTag.getString(i));
      }

      CompoundTag numbersTag = tag.getCompound("numbers");

      for (String key : numbersTag.getAllKeys()) {
         this.numbers.put(key, numbersTag.getInt(key));
      }
   }

   @Nonnull
   public static DataStorage getData(LevelAccessor world) {
      if (world.isClientSide()) {
         throw new RuntimeException("Don't access this client-side!");
      }

      MinecraftServer server = world.getServer();
      ServerLevel overworld = server.getLevel(Level.OVERWORLD);
      DimensionDataStorage storage = overworld.getDataStorage();
      return (DataStorage)storage.computeIfAbsent(new Factory(DataStorage::new, (compoundTag, provider) -> new DataStorage(compoundTag)), "InControlData");
   }

   public int getDaycounter() {
      return this.daycounter;
   }

   public void setDaycounter(int daycounter) {
      this.daycounter = daycounter;
      this.setDirty();
   }

   public void tick(Level world) {
      this.tickTime(world);
      this.checkCounter--;
      if (this.checkCounter <= 0) {
         this.checkCounter = 10;
         this.tickPhases(world);
      }
   }

   private void tickTime(Level world) {
      long time = world.getDayTime() % 24000L;
      boolean day = time >= 0L && time < 12000L;
      if (this.isDay == null) {
         this.isDay = day;
         this.setDirty();
      } else if (day != this.isDay) {
         if (day) {
            this.daycounter++;
         }

         this.isDay = day;
         this.setDirty();
      }
   }

   public Map<String, Integer> getNumbers() {
      return this.numbers;
   }

   public void setNumber(String name, int value) {
      this.numbers.put(name, value);
      this.setDirty();
   }

   public int getNumber(String name) {
      return this.numbers.getOrDefault(name, 0);
   }

   public Set<String> getPhases() {
      return this.phases;
   }

   public void setPhase(String phase, boolean value) {
      if (value) {
         if (this.phases.add(phase)) {
            RulesManager.onPhaseChange();
            this.setDirty();
         }
      } else if (this.phases.remove(phase)) {
         RulesManager.onPhaseChange();
         this.setDirty();
      }
   }

   public void togglePhase(String phase) {
      if (this.phases.contains(phase)) {
         this.phases.remove(phase);
      } else {
         this.phases.add(phase);
      }

      RulesManager.onPhaseChange();
      this.setDirty();
   }

   private void tickPhases(Level world) {
      boolean dirty = false;

      for (PhaseRule rule : RulesManager.phaseRules) {
         if (rule.match(world)) {
            if (this.phases.add(rule.getName())) {
               dirty = true;
            }
         } else if (this.phases.remove(rule.getName())) {
            dirty = true;
         }
      }

      if (dirty) {
         RulesManager.onPhaseChange();
         this.setDirty();
      }
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      tag.putInt("daycounter", this.daycounter);
      if (this.isDay != null) {
         tag.putBoolean("isday", this.isDay);
      }

      ListTag phasesTag = new ListTag();

      for (String phase : this.phases) {
         phasesTag.add(StringTag.valueOf(phase));
      }

      tag.put("phases", phasesTag);
      CompoundTag numbersTag = new CompoundTag();

      for (Entry<String, Integer> entry : this.numbers.entrySet()) {
         numbersTag.putInt(entry.getKey(), entry.getValue());
      }

      tag.put("numbers", numbersTag);
      return tag;
   }
}
