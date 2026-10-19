package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface Curve {
   double interpolate(float var1);

   static List<MathValue> parseNodes(JsonObject curveObject) {
      JsonArray nodesInput = curveObject.getAsJsonArray("nodes");
      ArrayList<MathValue> points = new ArrayList<>(nodesInput.size());

      for (JsonElement nodeInput : nodesInput) {
         MathValue point = MathParser.parseJson(nodeInput);
         points.add(point);
      }

      return points;
   }
}
