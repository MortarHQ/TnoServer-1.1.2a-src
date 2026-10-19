package com.finderfeed.fdlib.systems.bedrock.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class FDModelInfo {
   private ResourceLocation modelName;
   private float cubeScale;
   public List<FDModelPartDefinition> partDefinitionList = new ArrayList<>();

   public FDModelInfo(ResourceLocation location, float cubeScale) {
      this.modelName = location;
      this.cubeScale = cubeScale;
   }

   public void load(JsonObject model) {
      this.parseModel(model, this.cubeScale);
   }

   public ResourceLocation getModelName() {
      return this.modelName;
   }

   public float getCubeScale() {
      return this.cubeScale;
   }

   private void parseModel(JsonObject bmodel, float cubeScale) {
      this.partDefinitionList.clear();
      JsonObject jmodel = bmodel.getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();
      JsonObject description = jmodel.getAsJsonObject("description");
      int texWidth = description.get("texture_width").getAsInt();
      int texHeight = description.get("texture_height").getAsInt();
      JsonArray parts = jmodel.getAsJsonArray("bones");
      Map<String, Vector3f> pivotOffsets = new HashMap<>();

      for (JsonElement epart : parts) {
         JsonObject part = epart.getAsJsonObject();
         String name = JsonHelper.getString(part, "name");
         String parent = JsonHelper.getString(part, "parent");
         Vector3f pivot = JsonHelper.parseVector3f(part, "pivot").mul(-1.0F, 1.0F, 1.0F, new Vector3f());
         Vector3f rotation = JsonHelper.parseVector3f(part, "rotation").mul(-1.0F, -1.0F, 1.0F, new Vector3f());
         List<FDCube> cubes;
         if (part.has("cubes")) {
            cubes = this.parseCubes(part.getAsJsonArray("cubes"), pivot, texWidth, texHeight, cubeScale);
         } else {
            cubes = new ArrayList<>();
         }

         Vector3f v = pivotOffsets.get(parent);
         if (v == null) {
            v = new Vector3f();
         }

         pivotOffsets.put(name, new Vector3f(pivot));
         pivot.sub(v);
         FDModelPartDefinition definition = new FDModelPartDefinition(cubes, name, parent, rotation, pivot);
         this.partDefinitionList.add(definition);
      }
   }

   private List<FDCube> parseCubes(JsonArray array, Vector3f bonePivot, int texWidth, int texHeight, float cubeScale) {
      List<FDCube> cubes = new ArrayList<>();

      for (JsonElement element : array) {
         cubes.add(FDCube.fromJson(element.getAsJsonObject(), bonePivot, texWidth, texHeight, cubeScale));
      }

      return cubes;
   }

   public record ModelSyncInstance(ResourceLocation location, List<FDModelPartDefinition> definitions) {
      public static final StreamCodec<FriendlyByteBuf, FDModelInfo.ModelSyncInstance> CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         inst -> inst.location.toString(),
         FDModelPartDefinition.CODEC.apply(ByteBufCodecs.list()),
         inst -> inst.definitions,
         (str, defs) -> new FDModelInfo.ModelSyncInstance(ResourceLocation.parse(str), defs)
      );
   }
}
