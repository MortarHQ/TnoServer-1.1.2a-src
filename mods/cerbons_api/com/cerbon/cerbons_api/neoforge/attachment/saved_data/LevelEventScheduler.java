package com.cerbon.cerbons_api.neoforge.attachment.saved_data;

import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.NotNull;

public class LevelEventScheduler extends SavedData {
   private static final Factory<LevelEventScheduler> FACTORY = new Factory(LevelEventScheduler::new, LevelEventScheduler::new);
   public static LevelEventScheduler INSTANCE;
   private static final String DATA_KEY = "event_scheduler";
   private EventScheduler eventScheduler;

   public LevelEventScheduler() {
      this(new CompoundTag(), null);
   }

   @NotNull
   public CompoundTag save(@NotNull CompoundTag tag, @NotNull Provider registries) {
      return tag;
   }

   public LevelEventScheduler(CompoundTag tag, Provider provider) {
   }

   public static EventScheduler get(Level level) {
      LevelEventScheduler levelEventScheduler = getSavedData(level);
      if (levelEventScheduler.eventScheduler == null) {
         levelEventScheduler.eventScheduler = new EventScheduler();
      }

      return levelEventScheduler.eventScheduler;
   }

   private static LevelEventScheduler getSavedData(Level level) {
      return level.isClientSide ? INSTANCE : (LevelEventScheduler)((ServerLevel)level).getDataStorage().computeIfAbsent(FACTORY, "event_scheduler");
   }
}
