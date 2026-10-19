package com.blamejared.controlling.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;

public class CustomList extends KeyBindsList {
   public List<Entry> allEntries;

   public CustomList(KeyBindsScreen controls, Minecraft mcIn) {
      super(controls, mcIn);
   }

   public List<Entry> getAllEntries() {
      return this.allEntries;
   }

   protected int addEntry(Entry ent) {
      if (this.allEntries == null) {
         this.allEntries = new ArrayList<>();
      }

      this.allEntries.add(ent);
      this.children().add(ent);
      return this.children().size() - 1;
   }
}
