package xaero.hud.minimap.radar.icon.creator.render.form.model.part;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelPart.Cube;
import xaero.lib.common.reflection.util.ReflectionUtils;

public class ModelPartUtil {
   private static final Field CUBES_FIELD = ReflectionUtils.getFieldReflection(ModelPart.class, "cubes", "field_3663", "Ljava/util/List;", "f_104212_");
   private static final Field CHILDREN_FIELD = ReflectionUtils.getFieldReflection(ModelPart.class, "children", "field_3661", "Ljava/util/Map;", "f_104213_");

   public static List<Cube> getCubes(ModelPart modelRenderer) {
      return (List<Cube>)ReflectionUtils.getReflectFieldValue(modelRenderer, CUBES_FIELD);
   }

   public static Map<String, ModelPart> getChildren(ModelPart modelRenderer) {
      return (Map<String, ModelPart>)ReflectionUtils.getReflectFieldValue(modelRenderer, CHILDREN_FIELD);
   }

   public static boolean hasDirectCubes(ModelPart part) {
      List<Cube> cubes = getCubes(part);
      return cubes != null && !cubes.isEmpty();
   }

   public static boolean hasCubes(ModelPart part) {
      if (hasDirectCubes(part)) {
         return true;
      }

      Map<String, ModelPart> children = getChildren(part);

      for (ModelPart child : children.values()) {
         if (hasCubes(child)) {
            return true;
         }
      }

      return false;
   }

   public static Cube getBiggestCuboid(ModelPart part) {
      List<Cube> mainCubeList = getCubes(part);
      if (mainCubeList == null) {
         return null;
      }

      if (mainCubeList.isEmpty()) {
         return null;
      }

      float biggestSize = 0.0F;
      Cube biggestCuboid = null;

      for (Cube cuboid : mainCubeList) {
         float size = Math.abs((cuboid.maxX - cuboid.minX) * (cuboid.maxY - cuboid.minY) * (cuboid.maxZ - cuboid.minZ));
         if (size >= biggestSize) {
            biggestCuboid = cuboid;
            biggestSize = size;
         }
      }

      return biggestCuboid;
   }
}
