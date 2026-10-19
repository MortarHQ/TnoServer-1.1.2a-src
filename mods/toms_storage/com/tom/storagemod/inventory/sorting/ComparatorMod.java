package com.tom.storagemod.inventory.sorting;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.util.Comparator;

public class ComparatorMod implements Comparator<TerminalItemStack> {
   public int compare(TerminalItemStack in1, TerminalItemStack in2) {
      int c = in1.getNamespace().compareTo(in2.getNamespace());
      return c != 0 ? c : in1.getDisplayName().compareTo(in2.getDisplayName());
   }
}
