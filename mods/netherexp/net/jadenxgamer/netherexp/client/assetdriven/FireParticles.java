package net.jadenxgamer.netherexp.client.assetdriven;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public record FireParticles(Color[] smokeStartColors, Color[] smokeEndColors, Color[] emberColors) {
   public static final Map<ResourceLocation, FireParticles> FIRE_PARTICLES = new HashMap<>();
   public static final FireParticles DEFAULT = new FireParticles(
      new Color[]{new Color(5329233), new Color(5723991), new Color(6776679), new Color(7434609)},
      new Color[]{new Color(5329233), new Color(5723991), new Color(6776679), new Color(7434609)},
      new Color[0]
   );

   public static FireParticles parseSetting(JsonObject json) {
      Color[] smokeStartColors = parseColorArray(json.getAsJsonArray("smoke_start_colors"));
      Color[] smokeEndColors = parseColorArray(json.getAsJsonArray("smoke_end_colors"));
      Color[] emberColors = parseColorArray(json.getAsJsonArray("ember_colors"));
      return new FireParticles(smokeStartColors, smokeEndColors, emberColors);
   }

   private static Color[] parseColorArray(JsonArray jsonArray) {
      if (jsonArray != null && !jsonArray.isEmpty()) {
         Color[] colors = new Color[jsonArray.size()];

         for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            if (element.isJsonPrimitive()) {
               String hex = element.getAsString();
               colors[i] = hexToColor(hex);
            }
         }

         return colors;
      } else {
         return new Color[0];
      }
   }

   private static Color hexToColor(String hex) {
      if (hex.startsWith("#")) {
         hex = hex.substring(1);
      }

      if (hex.length() != 6) {
         throw new IllegalArgumentException("Hex color must be in #RRGGBB format");
      } else {
         return new Color(Integer.parseInt(hex, 16));
      }
   }
}
