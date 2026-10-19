package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;

public class BackpackShapes {
   private static volatile BackpackShapes.IShapeProvider defaultShapeProvider = new BackpackShapes.JsonModelShapeProvider(
      new BackpackShapes.CompositeModelJsonSource(BackpackShapes.ClasspathModelJsonSource.INSTANCE)
   );
   private static volatile BackpackShapes.IShapeProvider shapeProvider = defaultShapeProvider;

   private BackpackShapes() {
   }

   public static void setShapeProvider(BackpackShapes.IShapeProvider provider) {
      if (provider == null) {
         throw new IllegalArgumentException("Shape provider cannot be null");
      }

      shapeProvider = provider;
   }

   public static BackpackShapes.IShapeProvider getDefaultShapeProvider() {
      return defaultShapeProvider;
   }

   public static VoxelShape getShape(BlockState state) {
      return shapeProvider.getShape(state);
   }

   public static synchronized void reloadDefaultShapeProvider(ResourceManager resourceManager) {
      applyDefaultShapeProvider(createDefaultShapeProvider(resourceManager));
   }

   static BackpackShapes.IShapeProvider createDefaultShapeProvider(ResourceManager resourceManager) {
      return new BackpackShapes.JsonModelShapeProvider(
         new BackpackShapes.CompositeModelJsonSource(
            new BackpackShapes.ResourceManagerModelJsonSource(resourceManager), BackpackShapes.ClasspathModelJsonSource.INSTANCE
         )
      );
   }

   static synchronized void applyDefaultShapeProvider(BackpackShapes.IShapeProvider provider) {
      if (provider == null) {
         throw new IllegalArgumentException("Default shape provider cannot be null");
      }

      BackpackShapes.IShapeProvider oldDefault = defaultShapeProvider;
      defaultShapeProvider = provider;
      if (shapeProvider == oldDefault) {
         shapeProvider = provider;
      }
   }

   private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return Shapes.box(Math.min(minX, maxX), Math.min(minY, maxY), Math.min(minZ, maxZ), Math.max(minX, maxX), Math.max(minY, maxY), Math.max(minZ, maxZ));
   }

   private static final class ClasspathModelJsonSource implements BackpackShapes.ModelJsonSource {
      private static final BackpackShapes.ClasspathModelJsonSource INSTANCE = new BackpackShapes.ClasspathModelJsonSource();

      @Override
      public JsonObject loadModelJson(ResourceLocation modelLocation) {
         String path = "assets/" + modelLocation.getNamespace() + "/models/" + modelLocation.getPath() + ".json";
         ClassLoader classLoader = BackpackShapes.class.getClassLoader();

         try (InputStream stream = classLoader.getResourceAsStream(path)) {
            if (stream == null) {
               throw new IllegalStateException("Could not find model JSON resource for shape: " + path);
            }

            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
               return JsonParser.parseReader(reader).getAsJsonObject();
            }
         } catch (Exception e) {
            throw new IllegalStateException("Failed to load model JSON for backpack shape: " + modelLocation, e);
         }
      }
   }

   private static final class CompositeModelJsonSource implements BackpackShapes.ModelJsonSource {
      private final BackpackShapes.ModelJsonSource[] sources;

      private CompositeModelJsonSource(BackpackShapes.ModelJsonSource... sources) {
         this.sources = sources;
      }

      @Override
      public JsonObject loadModelJson(ResourceLocation modelLocation) {
         IllegalStateException lastError = null;

         for (BackpackShapes.ModelJsonSource source : this.sources) {
            try {
               JsonObject json = source.loadModelJson(modelLocation);
               if (json != null) {
                  return json;
               }
            } catch (IllegalStateException e) {
               lastError = e;
            }
         }

         if (lastError != null) {
            throw lastError;
         } else {
            throw new IllegalStateException("Failed to load model JSON for backpack shape: " + modelLocation);
         }
      }
   }

   public interface IShapeProvider {
      VoxelShape getShape(BlockState var1);
   }

   private static final class JsonModelShapeProvider implements BackpackShapes.IShapeProvider {
      private final BackpackShapes.ModelJsonSource modelJsonSource;
      private final Map<BackpackShapeHelper.ShapeKey, VoxelShape> shapes = new ConcurrentHashMap<>();
      private final EnumMap<BackpackShapeHelper.Part, VoxelShape> partShapes;

      private JsonModelShapeProvider(BackpackShapes.ModelJsonSource modelJsonSource) {
         this.modelJsonSource = modelJsonSource;
         this.partShapes = this.loadPartShapes();
      }

      private EnumMap<BackpackShapeHelper.Part, VoxelShape> loadPartShapes() {
         EnumMap<BackpackShapeHelper.Part, VoxelShape> loadedShapes = new EnumMap<>(BackpackShapeHelper.Part.class);
         Map<ResourceLocation, JsonObject> modelCache = new HashMap<>();

         for (BackpackShapes.PartModel part : BackpackShapes.PartModel.values()) {
            loadedShapes.put(part.part, this.loadShapeFromModel(part.modelLocation, modelCache));
         }

         return loadedShapes;
      }

      private VoxelShape loadShapeFromModel(ResourceLocation modelLocation, Map<ResourceLocation, JsonObject> modelCache) {
         JsonObject modelJson = this.resolveModelJson(modelLocation, modelCache);
         if (modelJson.has("elements") && modelJson.getAsJsonArray("elements").isEmpty()) {
            modelJson.remove("elements");
         }

         if (modelJson.has("elements")) {
            return parseElementsToShape(modelJson);
         }

         ResourceLocation parent = getParentLocation(modelJson, modelLocation);
         return parent == null ? Shapes.empty() : this.loadShapeFromModel(parent, modelCache);
      }

      private JsonObject resolveModelJson(ResourceLocation modelLocation, Map<ResourceLocation, JsonObject> modelCache) {
         return modelCache.computeIfAbsent(modelLocation, this.modelJsonSource::loadModelJson);
      }

      @Nullable
      private static ResourceLocation getParentLocation(JsonObject modelJson, ResourceLocation modelLocation) {
         if (!modelJson.has("parent")) {
            return null;
         }

         String parentName = GsonHelper.getAsString(modelJson, "parent");
         return parentName.contains(":") ? ResourceLocation.parse(parentName) : ResourceLocation.fromNamespaceAndPath(modelLocation.getNamespace(), parentName);
      }

      private static VoxelShape parseElementsToShape(JsonObject modelJson) {
         return BackpackShapeHelper.or(BackpackShapes.StreamSupportHelper.shapeStream(modelJson));
      }

      @Override
      public VoxelShape getShape(BlockState state) {
         BackpackShapeHelper.ShapeKey key = getKey(state);
         return this.shapes.computeIfAbsent(key, this::composeShape);
      }

      private VoxelShape composeShape(BackpackShapeHelper.ShapeKey key) {
         return BackpackShapeHelper.composeAndRotate(key, this.partShapes::get);
      }

      private static BackpackShapeHelper.ShapeKey getKey(BlockState state) {
         Direction dir = (Direction)state.getValue(BackpackBlock.FACING);
         boolean leftTank = (Boolean)state.getValue(BackpackBlock.LEFT_TANK);
         boolean rightTank = (Boolean)state.getValue(BackpackBlock.RIGHT_TANK);
         boolean battery = (Boolean)state.getValue(BackpackBlock.BATTERY);
         return BackpackShapeHelper.ShapeKey.of(dir, leftTank, rightTank, battery);
      }
   }

   private interface ModelJsonSource {
      JsonObject loadModelJson(ResourceLocation var1);
   }

   private enum PartModel {
      BASE(BackpackShapeHelper.Part.BASE, "block/backpack_base"),
      BATTERY(BackpackShapeHelper.Part.BATTERY, "block/backpack_battery"),
      FRONT_POUCH(BackpackShapeHelper.Part.FRONT_POUCH, "block/backpack_front_pouch"),
      LEFT_POUCH(BackpackShapeHelper.Part.LEFT_POUCH, "block/backpack_left_pouch"),
      LEFT_TANK(BackpackShapeHelper.Part.LEFT_TANK, "block/backpack_left_tank"),
      RIGHT_POUCH(BackpackShapeHelper.Part.RIGHT_POUCH, "block/backpack_right_pouch"),
      RIGHT_TANK(BackpackShapeHelper.Part.RIGHT_TANK, "block/backpack_right_tank");

      private final BackpackShapeHelper.Part part;
      private final ResourceLocation modelLocation;

      PartModel(BackpackShapeHelper.Part part, String modelPath) {
         this.part = part;
         this.modelLocation = SophisticatedBackpacks.getRL(modelPath);
      }
   }

   private static final class ResourceManagerModelJsonSource implements BackpackShapes.ModelJsonSource {
      private final ResourceManager resourceManager;

      private ResourceManagerModelJsonSource(ResourceManager resourceManager) {
         this.resourceManager = resourceManager;
      }

      @Override
      public JsonObject loadModelJson(ResourceLocation modelLocation) {
         ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(modelLocation.getNamespace(), "models/" + modelLocation.getPath() + ".json");
         Optional<Resource> resource = this.resourceManager.getResource(resourceLocation);
         if (resource.isEmpty()) {
            throw new IllegalStateException("Missing model resource " + resourceLocation);
         }

         try (
            InputStream stream = resource.get().open();
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
         ) {
            return JsonParser.parseReader(reader).getAsJsonObject();
         } catch (Exception e) {
            throw new IllegalStateException("Failed to read model resource " + resourceLocation, e);
         }
      }
   }

   private static final class StreamSupportHelper {
      private static Stream<VoxelShape> shapeStream(JsonObject modelJson) {
         return modelJson.getAsJsonArray("elements").asList().stream().map(element -> {
            JsonObject elementJson = element.getAsJsonObject();
            if (elementJson.has("rotation")) {
               JsonObject rotation = elementJson.getAsJsonObject("rotation");
               if (!rotation.has("angle") || Math.abs(GsonHelper.getAsFloat(rotation, "angle")) > 1.0E-6F) {
                  throw new IllegalStateException("Backpack shape model contains unsupported element rotation");
               }
            }

            double[] from = parseVec3(elementJson, "from");
            double[] to = parseVec3(elementJson, "to");
            return BackpackShapes.box(from[0] / 16.0, from[1] / 16.0, from[2] / 16.0, to[0] / 16.0, to[1] / 16.0, to[2] / 16.0);
         });
      }

      private static double[] parseVec3(JsonObject json, String key) {
         if (json.has(key) && json.get(key).isJsonArray() && json.getAsJsonArray(key).size() == 3) {
            return new double[]{
               json.getAsJsonArray(key).get(0).getAsDouble(), json.getAsJsonArray(key).get(1).getAsDouble(), json.getAsJsonArray(key).get(2).getAsDouble()
            };
         } else {
            throw new IllegalStateException("Backpack shape model element is missing vec3: " + key);
         }
      }
   }
}
