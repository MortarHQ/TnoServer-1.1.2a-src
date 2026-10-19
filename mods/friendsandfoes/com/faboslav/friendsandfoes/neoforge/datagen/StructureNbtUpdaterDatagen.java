package com.faboslav.friendsandfoes.neoforge.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class StructureNbtUpdaterDatagen {
   public static void gatherData(GatherDataEvent event) {
      ExistingFileHelper exHelper = event.getExistingFileHelper();
      DataGenerator gen = event.getGenerator();
      PackOutput output = gen.getPackOutput();
      if (event.includeServer()) {
         gen.addProvider(true, new StructureNbtUpdater("structures", "friendsandfoes", exHelper, output));
      }
   }
}
