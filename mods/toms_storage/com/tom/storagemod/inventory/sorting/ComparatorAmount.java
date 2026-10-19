package com.tom.storagemod.inventory.sorting;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.util.Comparator;

public class ComparatorAmount implements Comparator<TerminalItemStack> {
   public int compare(TerminalItemStack in1, TerminalItemStack in2) {
      int c = Long.compare(in1.getQuantity(), in2.getQuantity());
      return c != 0 ? -c : in1.getDisplayName().compareTo(in2.getDisplayName());
   }
}
