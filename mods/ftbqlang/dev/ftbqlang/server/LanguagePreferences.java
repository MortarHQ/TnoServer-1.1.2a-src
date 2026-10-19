package dev.ftbqlang.server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class LanguagePreferences extends SavedData {
   private static final String FILE_ID = "ftbqlang_preferences";
   private static final Factory<LanguagePreferences> FACTORY = new Factory(LanguagePreferences::new, LanguagePreferences::load, null);
   private final Map<UUID, LanguagePolicy.Preference> players = new HashMap<>();

   public static LanguagePreferences get(MinecraftServer server) {
      return (LanguagePreferences)server.overworld().getDataStorage().computeIfAbsent(FACTORY, "ftbqlang_preferences");
   }

   private static LanguagePreferences load(CompoundTag tag, Provider registries) {
      LanguagePreferences data = new LanguagePreferences();
      CompoundTag players = tag.getCompound("players");

      for (String key : players.getAllKeys()) {
         if (players.contains(key, 10)) {
            try {
               UUID playerId = UUID.fromString(key);
               CompoundTag entry = players.getCompound(key);
               String selected = entry.getString("selected");
               data.players
                  .put(
                     playerId,
                     new LanguagePolicy.Preference(
                        LanguagePolicy.isValidLocale(selected) ? LanguagePolicy.normalize(selected) : "", entry.getBoolean("prompted")
                     )
                  );
            } catch (IllegalArgumentException var9) {
            }
         }
      }

      return data;
   }

   public LanguagePolicy.Preference get(UUID playerId) {
      return this.players.getOrDefault(playerId, LanguagePolicy.Preference.UNSET);
   }

   public void put(UUID playerId, LanguagePolicy.Preference preference) {
      if (!this.get(playerId).equals(preference)) {
         this.players.put(playerId, preference);
         this.setDirty();
      }
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      CompoundTag entries = new CompoundTag();
      this.players.forEach((playerId, preference) -> {
         CompoundTag entry = new CompoundTag();
         entry.putString("selected", preference.selected());
         entry.putBoolean("prompted", preference.prompted());
         entries.put(playerId.toString(), entry);
      });
      tag.put("players", entries);
      return tag;
   }
}
