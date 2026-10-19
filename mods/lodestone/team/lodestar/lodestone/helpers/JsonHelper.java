package team.lodestar.lodestone.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class JsonHelper {
   @Nullable
   public static Vector2f getAsVec2f(JsonObject object, String key) {
      JsonArray array = object.getAsJsonArray(key);
      return array != null && array.size() >= 2 ? new Vector2f(array.get(0).getAsFloat(), array.get(1).getAsFloat()) : null;
   }

   @Nullable
   public static Vector3f getAsVec3f(JsonObject object, String key) {
      JsonArray array = object.getAsJsonArray(key);
      return array != null && array.size() >= 3 ? new Vector3f(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat()) : null;
   }
}
