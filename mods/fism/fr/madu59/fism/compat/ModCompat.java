package fr.madu59.fism.compat;

import fr.madu59.fism.compat.entityculling.EntityCullingCompat;
import fr.madu59.fism.platform.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3d;

public class ModCompat {
   private static boolean isIrisLoaded = PlatformHelper.isModLoaded("iris") || PlatformHelper.isModLoaded("occulus");
   private static boolean isEntityCullingLoaded = PlatformHelper.isModLoaded("entityculling");

   public static boolean isShadowPass() {
      return isIrisLoaded() ? IrisCompat.isShadowPass() : false;
   }

   public static Vector3d getCameraPosition() {
      return isIrisLoaded() ? IrisCompat.getCameraPosition() : new Vector3d();
   }

   public static boolean isIrisLoaded() {
      return isIrisLoaded;
   }

   public static boolean isOcclusionCulled(BlockPos blockPos, BlockEntityType<?> beType) {
      return isOcclusionCulled(setUpAABB(blockPos, beType));
   }

   public static boolean isOcclusionCulled(AABB aabb) {
      return isEntityCullingLoaded() ? EntityCullingCompat.isOcclusionCulled(aabb) : false;
   }

   public static void clearCache() {
      if (isEntityCullingLoaded()) {
         EntityCullingCompat.clearCache();
      }

      if (isIrisLoaded()) {
         IrisCompat.clearCache();
      }
   }

   public static boolean isEntityCullingLoaded() {
      return false;
   }

   private static AABB setUpAABB(BlockPos pos, BlockEntityType<?> beType) {
      return beType == BlockEntityType.BANNER ? new AABB(pos).inflate(0.0, 1.0, 0.0) : new AABB(pos);
   }
}
