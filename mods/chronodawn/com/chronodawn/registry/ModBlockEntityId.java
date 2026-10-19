package com.chronodawn.registry;

import com.chronodawn.version.CurrentVersion;
import com.chronodawn.version.MinecraftVersion;
import java.util.Arrays;
import java.util.List;

public enum ModBlockEntityId {
   CLOCK_TOWER_TELEPORTER("clock_tower_teleporter"),
   BOSS_ROOM_DOOR("boss_room_door"),
   BOSS_ROOM_BOUNDARY_MARKER("boss_room_boundary_marker");

   private final String id;
   private final MinecraftVersion minVersion;

   ModBlockEntityId(String id) {
      this(id, MinecraftVersion.MC_1_20_1);
   }

   ModBlockEntityId(String id, MinecraftVersion minVersion) {
      this.id = id;
      this.minVersion = minVersion;
   }

   public String id() {
      return this.id;
   }

   public MinecraftVersion minVersion() {
      return this.minVersion;
   }

   public boolean isAvailableFor(MinecraftVersion version) {
      return version.isAtLeast(this.minVersion);
   }

   public static List<ModBlockEntityId> availableFor(MinecraftVersion version) {
      return Arrays.stream(values()).filter(e -> e.isAvailableFor(version)).toList();
   }

   public static List<ModBlockEntityId> availableForCurrent() {
      return availableFor(CurrentVersion.VERSION);
   }
}
