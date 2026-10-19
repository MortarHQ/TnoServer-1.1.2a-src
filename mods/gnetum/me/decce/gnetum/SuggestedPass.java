package me.decce.gnetum;

import java.util.List;
import me.decce.gnetum.mixins.GuiAccessor;
import me.decce.gnetum.mixins.GuiLayerManagerAccessor;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.gui.GuiLayerManager.NamedLayer;

public class SuggestedPass {
   public static int get(String name) {
      int pass = switch (name) {
         case "minecraft:camera_overlays", "minecraft:crosshair", "minecraft:hotbar" -> 1;
         case "minecraft:jump_meter", "minecraft:experience_bar", "gnetum:status_bar", "minecraft:selected_item_name", "minecraft:spectator_tooltip", "minecraft:experience_level" -> 2;
         case "minecraft:effects", "minecraft:boss_overlay", "minecraft:sleep_overlay", "minecraft:demo_overlay", "minecraft:debug_overlay", "minecraft:scoreboard_sidebar", "minecraft:overlay_message", "minecraft:title", "minecraft:chat", "minecraft:tab_list", "minecraft:subtitle_overlay", "minecraft:saving_indicator" -> 3;
         default -> {
            if (name.startsWith("minecraft:")) {
               yield 3;
            } else {
               List<NamedLayer> layers = ((GuiLayerManagerAccessor)((GuiAccessor)Minecraft.getInstance().gui).getLayerManager()).getLayers();

               for (int i = 0; i < layers.size(); i++) {
                  if (layers.get(i).name().toString().equals(name)) {
                     yield i == 0 ? 1 : get(layers.get(i - 1).name().toString());
                  }
               }

               yield 3;
            }
         }
      };
      if (pass > Gnetum.config.numberOfPasses) {
         pass = Gnetum.config.numberOfPasses;
      }

      return pass;
   }
}
