package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ui.catnip.AbstractReadonlyDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.FullButtonListEntry;
import com.nododiiiii.ponderer.ui.catnip.SectionHeaderListEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class StructureListScreen extends AbstractReadonlyDeclarativeListScreen {
   private final Screen parent;
   private final Consumer<String> onSelect;
   private final List<String> allStructures = new ArrayList<>();

   public StructureListScreen(Screen parent, Consumer<String> onSelect) {
      super(parent, "ponderer.ui.scope.editor", "ponderer.ui.scene_desc.structure_list_title", 300);
      this.parent = parent;
      this.onSelect = onSelect;
   }

   @Override
   protected void init() {
      this.reloadStructures();
      super.init();
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
      if (this.allStructures.isEmpty()) {
         entries.add(new SectionHeaderListEntry(UIText.of("ponderer.ui.structure_list.empty")));
      } else {
         for (String structureId : this.allStructures) {
            entries.add(new FullButtonListEntry(structureId, null, () -> {
               this.onSelect.accept(structureId);
               Minecraft.getInstance().setScreen(this.parent);
            }));
         }
      }
   }

   @Override
   protected int getEntryHeight() {
      return 24;
   }

   private void reloadStructures() {
      this.allStructures.clear();

      try {
         Registry<?> registry = null;
         IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
         if (server != null) {
            registry = server.registryAccess().registryOrThrow(Registries.STRUCTURE);
         }

         if (registry == null || registry.keySet().isEmpty()) {
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection != null) {
               registry = connection.registryAccess().registryOrThrow(Registries.STRUCTURE);
            }
         }

         if (registry != null) {
            for (ResourceLocation key : registry.keySet()) {
               this.allStructures.add(key.toString());
            }
         }
      } catch (Exception var5) {
      }

      Collections.sort(this.allStructures);
   }
}
