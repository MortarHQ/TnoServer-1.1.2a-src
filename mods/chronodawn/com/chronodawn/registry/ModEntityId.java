package com.chronodawn.registry;

import com.chronodawn.version.CurrentVersion;
import com.chronodawn.version.MinecraftVersion;
import java.util.Arrays;
import java.util.List;

public enum ModEntityId {
   TIME_GUARDIAN("time_guardian"),
   CHRONOS_WARDEN("chronos_warden"),
   CLOCKWORK_COLOSSUS("clockwork_colossus"),
   TIME_TYRANT("time_tyrant"),
   TEMPORAL_PHANTOM("temporal_phantom"),
   ENTROPY_KEEPER("entropy_keeper"),
   TEMPORAL_WRAITH("temporal_wraith"),
   CLOCKWORK_SENTINEL("clockwork_sentinel"),
   EPOCH_HUSK("epoch_husk"),
   FORGOTTEN_MINUTE("forgotten_minute"),
   CHRONAL_LEECH("chronal_leech"),
   MOMENT_CREEPER("moment_creeper"),
   PARADOX_CRAWLER("paradox_crawler"),
   SECONDHAND_ARCHER("secondhand_archer"),
   TIMELINE_STRIDER("timeline_strider"),
   HOURGLASS_GOLEM("hourglass_golem"),
   TIME_KEEPER("time_keeper"),
   FLOQ("floq"),
   GLIDE_FISH("glide_fish"),
   CHRONO_TURTLE("chrono_turtle"),
   TIMEBOUND_RABBIT("timebound_rabbit"),
   PULSE_HOG("pulse_hog"),
   SECONDWING_FOWL("secondwing_fowl"),
   TICKING_SHEEP("ticking_sheep"),
   CHRONO_BOVINE("chrono_bovine"),
   TEMPORAL_CAPRID("temporal_caprid"),
   CHRONO_URSID("chrono_ursid"),
   GEAR_PROJECTILE("gear_projectile"),
   TIME_ARROW("time_arrow"),
   TIME_BLAST("time_blast"),
   CHRONO_DAWN_BOAT("chronodawn_boat"),
   CHRONO_DAWN_CHEST_BOAT("chronodawn_chest_boat");

   private final String id;
   private final MinecraftVersion minVersion;

   ModEntityId(String id) {
      this(id, MinecraftVersion.MC_1_20_1);
   }

   ModEntityId(String id, MinecraftVersion minVersion) {
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

   public static List<ModEntityId> availableFor(MinecraftVersion version) {
      return Arrays.stream(values()).filter(e -> e.isAvailableFor(version)).toList();
   }

   public static List<ModEntityId> availableForCurrent() {
      return availableFor(CurrentVersion.VERSION);
   }
}
