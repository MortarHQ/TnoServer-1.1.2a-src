package me.decce.gnetum;

import java.util.HashSet;
import java.util.List;
import me.decce.gnetum.mixins.GuiAccessor;
import me.decce.gnetum.mixins.GuiLayerManagerAccessor;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.gui.GuiLayerManager.NamedLayer;

public class PackedVanillaElements {
   public static final String PACKED_STATUS_BAR = "gnetum:status_bar";
   public static HashSet<String> set;

   public static void init() {
      set = new HashSet<>();
      GuiLayerManager layerManager = ((GuiAccessor)Minecraft.getInstance().gui).getLayerManager();
      List<NamedLayer> layers = ((GuiLayerManagerAccessor)layerManager).getLayers();
      boolean pack = false;

      for (NamedLayer layer : layers) {
         if (VanillaGuiLayers.EXPERIENCE_BAR.equals(layer.name())) {
            pack = true;
         } else {
            if (VanillaGuiLayers.SELECTED_ITEM_NAME.equals(layer.name())) {
               break;
            }

            if (pack) {
               set.add(layer.name().toString());
            }
         }
      }
   }

   public static String consider(String element) {
      if (set == null) {
         init();
      }

      return set.contains(element) ? "gnetum:status_bar" : element;
   }

   public static void reset() {
      set = null;
   }
}
