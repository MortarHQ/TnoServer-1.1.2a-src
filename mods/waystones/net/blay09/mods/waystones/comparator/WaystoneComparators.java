package net.blay09.mods.waystones.comparator;

import java.util.Comparator;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.world.entity.player.Player;

public class WaystoneComparators {
   public static Comparator<Waystone> forAdminInspection(Player caller, Player target) {
      return new PreferOwnedComparator(target)
         .thenComparing(new PreferSameDimensionComparator(caller.level().dimension()).thenComparing(new DistanceToPlayerComparator(caller)));
   }
}
