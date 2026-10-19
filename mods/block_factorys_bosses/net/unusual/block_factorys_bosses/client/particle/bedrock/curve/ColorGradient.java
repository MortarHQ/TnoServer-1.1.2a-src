package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ColorGradient(List<ColorGradient.ColorNode> nodes) {
   private static final Pattern COLOR_STRING_PATTERN = Pattern.compile("^#([0-9a-f]{2})?([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$", 2);

   public int getColor(float progress) {
      progress = Mth.clamp(progress, 0.0F, 1.0F);
      ColorGradient.ColorNode firstNode = this.nodes.getFirst();
      if (progress <= firstNode.progress()) {
         return firstNode.color();
      }

      for (int index = this.nodes.size() - 2; index >= 0; index--) {
         ColorGradient.ColorNode previousNode = this.nodes.get(index);
         if (progress >= previousNode.progress()) {
            ColorGradient.ColorNode nextNode = this.nodes.get(index + 1);
            float x = (progress - previousNode.progress()) / nextNode.progress();
            return ARGB32.lerp(x, previousNode.color(), nextNode.color());
         }
      }

      return this.nodes.getLast().color();
   }

   public static int parseColor(JsonElement jsonElement) {
      if (jsonElement == null) {
         throw new IllegalArgumentException("Received null color.");
      }

      if (jsonElement instanceof JsonPrimitive jsonPrimitive && jsonPrimitive.isString()) {
         String colorString = jsonPrimitive.getAsString();
         Matcher matcher = COLOR_STRING_PATTERN.matcher(colorString);
         if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid color string: " + colorString);
         }

         String alphaString = matcher.group(1);
         int alpha = alphaString != null && !alphaString.isEmpty() ? Integer.parseInt(alphaString, 16) : 255;
         int red = Integer.parseInt(matcher.group(2), 16);
         int green = Integer.parseInt(matcher.group(3), 16);
         int blue = Integer.parseInt(matcher.group(4), 16);
         return ARGB32.color(alpha, red, green, blue);
      } else if (jsonElement instanceof JsonArray jsonArray) {
         if (jsonArray.size() != 3 && jsonArray.size() != 4) {
            throw new IllegalArgumentException("Color array must have exactly 3 or 4 elements.");
         }

         int red = (int)(jsonArray.get(0).getAsFloat() * 255.0F);
         int green = (int)(jsonArray.get(1).getAsFloat() * 255.0F);
         int blue = (int)(jsonArray.get(2).getAsFloat() * 255.0F);
         int alpha = jsonArray.size() < 3 ? 255 : (int)(jsonArray.get(3).getAsFloat() * 255.0F);
         return ARGB32.color(alpha, red, green, blue);
      } else {
         throw new IllegalArgumentException("Color must be a string, or an array, but received " + jsonElement);
      }
   }

   public static ColorGradient deserialize(JsonElement jsonElement) {
      if (jsonElement instanceof JsonArray gradientArray) {
         int colorCount = gradientArray.size();
         ArrayList<ColorGradient.ColorNode> nodes = new ArrayList<>(colorCount);

         for (int index = 0; index < gradientArray.size(); index++) {
            JsonElement colorJson = gradientArray.get(index);
            nodes.add(new ColorGradient.ColorNode((float)index / colorCount, parseColor(colorJson)));
         }

         return new ColorGradient(nodes);
      } else if (!(jsonElement instanceof JsonObject jsonObject)) {
         throw new IllegalArgumentException("Color gradient must be an object or array of colors, but received " + jsonElement);
      } else {
         if (jsonObject.isEmpty()) {
            throw new IllegalArgumentException("Color gradient has no colors.");
         }

         ArrayList<ColorGradient.ColorNode> parsedNodes = new ArrayList<>(jsonObject.size());

         for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            ColorGradient.ColorNode parsedNode = ColorGradient.ColorNode.deserialize(entry.getKey(), entry.getValue());
            parsedNodes.add(parsedNode);
         }

         parsedNodes.sort((a, b) -> Float.compare(a.progress, b.progress));
         return new ColorGradient(parsedNodes);
      }
   }

   public record ColorNode(float progress, int color) {
      @Override
      public String toString() {
         return "ColorNode(progress = %.3f, color = #%08x)".formatted(this.progress, this.color);
      }

      public static ColorGradient.ColorNode deserialize(String progress, JsonElement color) {
         return new ColorGradient.ColorNode(Float.parseFloat(progress), ColorGradient.parseColor(color));
      }
   }
}
