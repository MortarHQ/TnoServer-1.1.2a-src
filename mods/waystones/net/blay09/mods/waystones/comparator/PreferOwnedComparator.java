package net.blay09.mods.waystones.comparator;

import java.util.Comparator;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.world.entity.player.Player;

public class PreferOwnedComparator implements Comparator<Waystone> {
   private final Player owner;

   public PreferOwnedComparator(Player owner) {
      this.owner = owner;
   }

   public int compare(Waystone o1, Waystone o2) {
      if (o1.isOwner(this.owner) && !o2.isOwner(this.owner)) {
         return -1;
      } else {
         return !o1.isOwner(this.owner) && o2.isOwner(this.owner) ? 1 : 0;
      }
   }
}
