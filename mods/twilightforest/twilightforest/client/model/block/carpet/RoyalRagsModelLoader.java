package twilightforest.client.model.block.carpet;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;

public class RoyalRagsModelLoader implements IGeometryLoader<UnbakedRoyalRagsModel> {
   @Deprecated
   public static final RoyalRagsModelLoader INSTANCE = new RoyalRagsModelLoader();

   public UnbakedRoyalRagsModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
      return new UnbakedRoyalRagsModel();
   }
}
