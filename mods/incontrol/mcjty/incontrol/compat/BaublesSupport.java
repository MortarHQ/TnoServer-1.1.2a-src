package mcjty.incontrol.compat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BaublesSupport {
   private static final int[] EMPTY = new int[0];

   public static int[] getAmuletSlots() {
      return EMPTY;
   }

   public static int[] getBeltSlots() {
      return EMPTY;
   }

   public static int[] getBodySlots() {
      return EMPTY;
   }

   public static int[] getCharmSlots() {
      return EMPTY;
   }

   public static int[] getHeadSlots() {
      return EMPTY;
   }

   public static int[] getRingSlots() {
      return EMPTY;
   }

   public static int[] getTrinketSlots() {
      return EMPTY;
   }

   public static ItemStack getStack(Player player, int slot) {
      return ItemStack.EMPTY;
   }
}
