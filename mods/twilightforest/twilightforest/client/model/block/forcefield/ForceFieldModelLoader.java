package twilightforest.client.model.block.forcefield;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import org.jetbrains.annotations.Nullable;

public class ForceFieldModelLoader implements IGeometryLoader<UnbakedForceFieldModel> {
   public static final ForceFieldModelLoader INSTANCE = new ForceFieldModelLoader();

   public UnbakedForceFieldModel read(JsonObject json, JsonDeserializationContext context) throws JsonParseException {
      Map<BlockElement, ForceFieldModelLoader.Condition> elementsAndConditions = new HashMap<>();
      if (json.has("elements")) {
         for (JsonElement jsonElement : GsonHelper.getAsJsonArray(json, "elements")) {
            ForceFieldModel.ExtraDirection direction = null;
            boolean b = false;
            List<ForceFieldModel.ExtraDirection> parents = new ArrayList<>();
            if (jsonElement instanceof JsonObject element && element.get("condition") instanceof JsonObject condition) {
               direction = ForceFieldModel.ExtraDirection.byName(GsonHelper.getAsString(condition, "direction", "up"));
               b = GsonHelper.getAsBoolean(condition, "if", true);

               for (JsonElement parentElement : GsonHelper.getAsJsonArray(condition, "parents")) {
                  parents.add(ForceFieldModel.ExtraDirection.byName(parentElement.getAsString()));
               }
            }

            elementsAndConditions.put(
               (BlockElement)context.deserialize(jsonElement, BlockElement.class), new ForceFieldModelLoader.Condition(direction, b, parents)
            );
         }
      }

      return new UnbakedForceFieldModel(elementsAndConditions);
   }

   public record Condition(@Nullable ForceFieldModel.ExtraDirection direction, boolean b, List<ForceFieldModel.ExtraDirection> parents) {
   }
}
