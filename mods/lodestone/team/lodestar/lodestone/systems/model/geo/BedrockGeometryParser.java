package team.lodestar.lodestone.systems.model.geo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import org.joml.Vector2f;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.JsonHelper;
import team.lodestar.lodestone.systems.model.LodestoneParser;
import team.lodestar.lodestone.systems.model.geo.data.GeoBone;
import team.lodestar.lodestone.systems.model.geo.data.GeoCube;
import team.lodestar.lodestone.systems.model.geo.data.GeoDescription;
import team.lodestar.lodestone.systems.model.geo.data.GeoQuad;
import team.lodestar.lodestone.systems.model.geo.data.GeoVertex;

public class BedrockGeometryParser extends LodestoneParser<BedrockGeometryModel> {
   private final Map<String, GeoBone> bones = new HashMap<>();
   private String version;
   private GeoDescription geoDescription;

   public void parse(Resource resource, BedrockGeometryModel model) throws IOException {
      JsonObject json = GsonHelper.parse(resource.openAsReader());
      this.version = GsonHelper.getAsString(json, "format_version");
      JsonArray geometries = json.getAsJsonArray("minecraft:geometry");

      for (int i = 0; i < geometries.size(); i++) {
         this.parseGeometry(geometries.get(i).getAsJsonObject());
      }

      for (String boneName : this.bones.keySet()) {
         GeoBone bone = this.bones.get(boneName);
         if (bone.getParent() != null) {
            GeoBone parentBone = this.bones.get(bone.getParent());
            if (parentBone == null) {
               throw new IOException("Parent bone '" + bone.getParent() + "' not found for bone '" + boneName + "'");
            }

            parentBone.addChild(boneName, bone);
         } else {
            model.root = bone;
         }
      }
   }

   private void parseGeometry(JsonObject geometry) {
      this.geoDescription = this.parseDescription(geometry.getAsJsonObject("description"));
      JsonArray bones = geometry.getAsJsonArray("bones");

      for (int i = 0; i < bones.size(); i++) {
         this.parseBone(bones.get(i).getAsJsonObject());
      }
   }

   private GeoDescription parseDescription(JsonObject description) {
      String identifier = GsonHelper.getAsString(description, "identifier");
      int textureWidth = GsonHelper.getAsInt(description, "texture_width");
      int textureHeight = GsonHelper.getAsInt(description, "texture_height");
      float visibleBoundsWidth = GsonHelper.getAsFloat(description, "visible_bounds_width");
      float visibleBoundsHeight = GsonHelper.getAsFloat(description, "visible_bounds_height");
      Vector3f visibleBoundsOffset = JsonHelper.getAsVec3f(description, "visible_bounds_offset");
      return new GeoDescription(identifier, textureWidth, textureHeight, visibleBoundsWidth, visibleBoundsHeight, visibleBoundsOffset);
   }

   private GeoBone parseBone(JsonObject bone) {
      String name = GsonHelper.getAsString(bone, "name");
      String parent = GsonHelper.getAsString(bone, "parent", null);
      Vector3f pivot = JsonHelper.getAsVec3f(bone, "pivot");
      Vector3f rotation = JsonHelper.getAsVec3f(bone, "rotation");
      JsonArray cubes = bone.getAsJsonArray("cubes");
      List<GeoCube> geoCubes = new ArrayList<>();
      if (cubes != null) {
         for (int i = 0; i < cubes.size(); i++) {
            geoCubes.add(this.parseCube(cubes.get(i).getAsJsonObject()));
         }
      }

      GeoBone geoBone = new GeoBone(geoCubes, new HashMap<>(), parent);
      geoBone.setPosition(pivot);
      if (rotation != null) {
         geoBone.setRotation(rotation.x, rotation.y, rotation.z);
      }

      this.bones.put(name, geoBone);
      return geoBone;
   }

   private GeoCube parseCube(JsonObject cube) {
      Vector3f origin = JsonHelper.getAsVec3f(cube, "origin");
      Vector3f size = JsonHelper.getAsVec3f(cube, "size");
      BedrockGeometryParser.VertexSet vertexSet = BedrockGeometryParser.VertexSet.fromCubeData(origin, size);
      JsonObject uv = cube.getAsJsonObject("uv");
      BedrockGeometryParser.UVSet uvSet = new BedrockGeometryParser.UVSet(
         this.parseUV(uv, "north"),
         this.parseUV(uv, "south"),
         this.parseUV(uv, "east"),
         this.parseUV(uv, "west"),
         this.parseUV(uv, "up"),
         this.parseUV(uv, "down")
      );
      List<GeoQuad> quads = new ArrayList<>();

      for (Direction direction : Direction.values()) {
         BedrockGeometryParser.UVData uvData = uvSet.getFace(direction);
         if (uvData != null) {
            GeoQuad quad = GeoQuad.build(
               vertexSet.verticesForQuad(direction),
               uvData.uv,
               uvData.uvSize,
               uvData.uvRotation,
               new Vector2f(this.geoDescription.textureWidth(), this.geoDescription.textureHeight())
            );
            quads.add(quad);
         }
      }

      return new GeoCube(quads.toArray(new GeoQuad[0]), origin, size, new Vector3f());
   }

   @Nullable
   private BedrockGeometryParser.UVData parseUV(JsonObject uvSet, String face) {
      if (!uvSet.has(face)) {
         return null;
      }

      JsonObject uv = uvSet.getAsJsonObject(face);
      Vector2f uvVec = JsonHelper.getAsVec2f(uv, "uv");
      Vector2f uvSize = JsonHelper.getAsVec2f(uv, "uv_size");
      int rot = GsonHelper.getAsInt(uv, "uv_rotation", 0);
      return new BedrockGeometryParser.UVData(uvVec, uvSize, rot);
   }

   private record UVData(Vector2f uv, Vector2f uvSize, int uvRotation) {
   }

   private record UVSet(
      @Nullable BedrockGeometryParser.UVData north,
      @Nullable BedrockGeometryParser.UVData south,
      @Nullable BedrockGeometryParser.UVData east,
      @Nullable BedrockGeometryParser.UVData west,
      @Nullable BedrockGeometryParser.UVData up,
      @Nullable BedrockGeometryParser.UVData down
   ) {
      @Nullable
      public BedrockGeometryParser.UVData getFace(Direction direction) {
         return switch (direction) {
            case WEST -> this.west;
            case EAST -> this.east;
            case NORTH -> this.north;
            case SOUTH -> this.south;
            case UP -> this.up;
            case DOWN -> this.down;
            default -> throw new MatchException(null, null);
         };
      }
   }

   private record VertexSet(
      GeoVertex bottomLeftBack,
      GeoVertex bottomRightBack,
      GeoVertex topLeftBack,
      GeoVertex topRightBack,
      GeoVertex topLeftFront,
      GeoVertex topRightFront,
      GeoVertex bottomLeftFront,
      GeoVertex bottomRightFront
   ) {
      public static BedrockGeometryParser.VertexSet fromCubeData(Vector3f origin, Vector3f size) {
         GeoVertex blb = new GeoVertex(origin.x, origin.y, origin.z);
         GeoVertex brb = new GeoVertex(origin.x, origin.y, origin.z + size.z);
         GeoVertex tlb = new GeoVertex(origin.x, origin.y + size.y, origin.z);
         GeoVertex trb = new GeoVertex(origin.x, origin.y + size.y, origin.z + size.z);
         GeoVertex tlf = new GeoVertex(origin.x + size.x, origin.y + size.y, origin.z);
         GeoVertex trf = new GeoVertex(origin.x + size.x, origin.y + size.y, origin.z + size.z);
         GeoVertex blf = new GeoVertex(origin.x + size.x, origin.y, origin.z);
         GeoVertex brf = new GeoVertex(origin.x + size.x, origin.y, origin.z + size.z);
         return new BedrockGeometryParser.VertexSet(blb, brb, tlb, trb, tlf, trf, blf, brf);
      }

      public GeoVertex[] verticesForQuad(Direction direction) {
         return switch (direction) {
            case WEST -> new GeoVertex[]{this.topLeftFront, this.topRightFront, this.bottomRightFront, this.bottomLeftFront};
            case EAST -> new GeoVertex[]{this.topRightBack, this.topLeftBack, this.bottomLeftBack, this.bottomRightBack};
            case NORTH -> new GeoVertex[]{this.topLeftBack, this.topLeftFront, this.bottomLeftFront, this.bottomLeftBack};
            case SOUTH -> new GeoVertex[]{this.topRightFront, this.topRightBack, this.bottomRightBack, this.bottomRightFront};
            case UP -> new GeoVertex[]{this.topRightBack, this.topRightFront, this.topLeftFront, this.topLeftBack};
            case DOWN -> new GeoVertex[]{this.bottomLeftBack, this.bottomLeftFront, this.bottomRightFront, this.bottomRightBack};
            default -> throw new MatchException(null, null);
         };
      }
   }
}
