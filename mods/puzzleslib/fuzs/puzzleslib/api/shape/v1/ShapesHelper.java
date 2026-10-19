package fuzs.puzzleslib.api.shape.v1;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Quaternionf;
import org.joml.Vector3d;

public final class ShapesHelper {
   private ShapesHelper() {
   }

   public static Map<Direction, VoxelShape> rotate(VoxelShape voxelShape) {
      Map<Direction, VoxelShape> shapes = Maps.newEnumMap(Direction.class);

      for (Direction direction : Direction.values()) {
         shapes.put(direction, rotate(direction.getRotation(), voxelShape));
      }

      return Maps.immutableEnumMap(shapes);
   }

   public static Map<Direction, VoxelShape> rotateHorizontally(VoxelShape voxelShape) {
      Map<Direction, VoxelShape> shapes = Maps.newEnumMap(Direction.class);

      for (Direction direction : Plane.HORIZONTAL) {
         Quaternionf rotation = getHorizontalRotation(direction);
         shapes.put(direction, rotate(rotation, voxelShape));
      }

      return Maps.immutableEnumMap(shapes);
   }

   public static Quaternionf getHorizontalRotation(Direction direction) {
      return new Quaternionf().rotationY((float)Math.atan2(direction.getStepX(), direction.getStepZ()));
   }

   @Deprecated(forRemoval = true)
   public static VoxelShape rotate(Direction direction, VoxelShape voxelShape) {
      return rotate(direction.getRotation(), voxelShape);
   }

   @Deprecated(forRemoval = true)
   public static VoxelShape rotate(Direction direction, VoxelShape voxelShape, Vector3d originOffset) {
      return rotate(direction.getRotation(), voxelShape, originOffset);
   }

   public static VoxelShape rotate(Quaternionf rotation, VoxelShape voxelShape) {
      return rotate(rotation, voxelShape, new Vector3d(0.5, 0.5, 0.5));
   }

   public static VoxelShape rotate(Quaternionf rotation, VoxelShape voxelShape, Vector3d originOffset) {
      VoxelShape[] joinedVoxelShape = new VoxelShape[]{Shapes.empty()};
      voxelShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
         Vector3d start = rotation.transform(new Vector3d(minX, minY, minZ).sub(originOffset)).add(originOffset);
         Vector3d end = rotation.transform(new Vector3d(maxX, maxY, maxZ).sub(originOffset)).add(originOffset);
         joinedVoxelShape[0] = Shapes.or(joinedVoxelShape[0], box(start.x, start.y, start.z, end.x, end.y, end.z));
      });
      return joinedVoxelShape[0];
   }

   public static VoxelShape box(double startX, double startY, double startZ, double endX, double endY, double endZ) {
      return Shapes.box(
         Math.min(startX, endX), Math.min(startY, endY), Math.min(startZ, endZ), Math.max(startX, endX), Math.max(startY, endY), Math.max(startZ, endZ)
      );
   }
}
