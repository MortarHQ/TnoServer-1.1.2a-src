package com.tom.storagemod.inventory.sorting;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.util.Comparator;

public class ComparatorName implements Comparator<TerminalItemStack> {
   public int compare(TerminalItemStack in1, TerminalItemStack in2) {
      return in1.getDisplayName().compareTo(in2.getDisplayName());
   }
}
