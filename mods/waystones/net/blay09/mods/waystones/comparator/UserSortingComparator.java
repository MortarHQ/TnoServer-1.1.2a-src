package net.blay09.mods.waystones.comparator;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.blay09.mods.waystones.api.Waystone;

public class UserSortingComparator implements Comparator<Waystone> {
   private final List<UUID> sortingIndex;

   public UserSortingComparator(List<UUID> sortingIndex) {
      this.sortingIndex = sortingIndex;
   }

   public int compare(Waystone o1, Waystone o2) {
      int index1 = this.sortingIndex.indexOf(o1.getWaystoneUid());
      int index2 = this.sortingIndex.indexOf(o2.getWaystoneUid());
      if (index1 == -1 && index2 == -1) {
         return 0;
      } else if (index1 == -1) {
         return 1;
      } else {
         return index2 == -1 ? -1 : Integer.compare(index1, index2);
      }
   }
}
