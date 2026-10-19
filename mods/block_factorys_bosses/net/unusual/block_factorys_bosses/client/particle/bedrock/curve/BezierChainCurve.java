package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record BezierChainCurve(List<BezierChainCurve.BezierChainSegment> segments) implements Curve {
   public BezierChainCurve {
      if (segments.isEmpty()) {
         throw new IllegalArgumentException("Curve with type bezier_chain must have at least 1 segment.");
      }
   }

   @Override
   public double interpolate(float progress) {
      progress = Mth.clamp(progress, 0.0F, 1.0F);
      BezierChainCurve.BezierChainSegment firstSegment = this.segments.getFirst();
      if (progress < firstSegment.start()) {
         return firstSegment.curve().interpolate(0.0F);
      }

      for (BezierChainCurve.BezierChainSegment segment : this.segments) {
         if (progress < segment.end()) {
            return segment.curve().interpolate(Mth.map(progress, segment.start(), segment.end(), 0.0F, 1.0F));
         }
      }

      return this.segments.getLast().curve().interpolate(1.0F);
   }

   public static BezierChainCurve deserialize(JsonObject curveObject) {
      List<BezierChainCurve.BezierChainSegment> segments = BezierChainCurve.BezierChainSegment.deserialize(curveObject);
      return new BezierChainCurve(segments);
   }

   public record BezierChainSegment(float start, float end, BezierCurve curve) {
      public static List<BezierChainCurve.BezierChainSegment> deserialize(JsonObject curveObject) {
         List<BezierChainCurve.ParsedBezierChainNode> nodes = BezierChainCurve.ParsedBezierChainNode.parseNodes(curveObject);
         ArrayList<BezierChainCurve.BezierChainSegment> segments = new ArrayList<>(nodes.size() - 1);

         for (int nodeIndex = 0; nodeIndex < nodes.size() - 1; nodeIndex++) {
            BezierChainCurve.ParsedBezierChainNode startNode = nodes.get(nodeIndex);
            BezierChainCurve.ParsedBezierChainNode endNode = nodes.get(nodeIndex + 1);
            segments.add(
               new BezierChainCurve.BezierChainSegment(
                  startNode.progress(),
                  endNode.progress(),
                  new BezierCurve(
                     List.of(
                        new Constant(startNode.rightValue()),
                        new Constant(startNode.rightSlope()),
                        new Constant(endNode.leftSlope()),
                        new Constant(endNode.leftValue())
                     )
                  )
               )
            );
         }

         return segments;
      }
   }

   private record ParsedBezierChainNode(float progress, float leftValue, float leftSlope, float rightSlope, float rightValue) {
      private static float getFloatOrFallback(JsonObject json, String primaryKey, String fallbackKey) {
         return json.has(primaryKey) ? json.getAsJsonPrimitive(primaryKey).getAsFloat() : json.getAsJsonPrimitive(fallbackKey).getAsFloat();
      }

      private static BezierChainCurve.ParsedBezierChainNode parseNode(String key, JsonObject nodeJson) {
         float progress = Float.parseFloat(key);
         float leftSlope = getFloatOrFallback(nodeJson, "left_slope", "slope");
         float rightSlope = getFloatOrFallback(nodeJson, "right_slope", "slope");
         float leftValue = getFloatOrFallback(nodeJson, "left_value", "value");
         float rightValue = getFloatOrFallback(nodeJson, "right_value", "value");
         return new BezierChainCurve.ParsedBezierChainNode(progress, leftValue, leftValue + leftSlope / 3.0F, rightValue - rightSlope / 3.0F * 2.0F, rightValue);
      }

      public static List<BezierChainCurve.ParsedBezierChainNode> parseNodes(JsonObject curveObject) {
         JsonObject nodesInput = curveObject.getAsJsonObject("nodes");
         if (nodesInput.size() < 2) {
            throw new IllegalArgumentException("Curve with type bezier_chain must have at least 2 nodes, but has " + nodesInput.size() + ".");
         }

         ArrayList<BezierChainCurve.ParsedBezierChainNode> parsedNodes = new ArrayList<>(nodesInput.size());

         for (Entry<String, JsonElement> entry : nodesInput.entrySet()) {
            BezierChainCurve.ParsedBezierChainNode parsedNode = parseNode(entry.getKey(), entry.getValue().getAsJsonObject());
            parsedNodes.add(parsedNode);
         }

         parsedNodes.sort((a, b) -> Float.compare(a.progress, b.progress));
         return parsedNodes;
      }
   }
}
