package com.tom.storagemod.inventory.sorting;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.util.Comparator;

public class ComparatorID implements Comparator<TerminalItemStack> {
   public int compare(TerminalItemStack in1, TerminalItemStack in2) {
      return in1.getDescriptionId().compareTo(in2.getDescriptionId());
   }
}
