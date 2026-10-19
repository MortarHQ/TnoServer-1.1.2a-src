package com.tensura_tno.client.compat;

import java.lang.reflect.Field;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;

public final class MoreSkillsAsmodayFrustumCulling {
   private static final double HORIZONTAL_EXTENT_SCALE = 11.0;
   private static final double VERTICAL_EXTENT_SCALE = 6.0;
   private static final double EXTENT_PADDING = 1.0;
   private static final ThreadLocal<Frustum> FRAME_FRUSTUM = new ThreadLocal<>();
   private static final ClassValue<MoreSkillsAsmodayFrustumCulling.HoleFields> HOLE_FIELDS = new ClassValue<MoreSkillsAsmodayFrustumCulling.HoleFields>() {
      protected MoreSkillsAsmodayFrustumCulling.HoleFields computeValue(Class<?> type) {
         return MoreSkillsAsmodayFrustumCulling.HoleFields.inspect(type);
      }
   };

   private MoreSkillsAsmodayFrustumCulling() {
   }

   public static void beginFrame(RenderLevelStageEvent event) {
      FRAME_FRUSTUM.remove();
      if (event.getStage() == Stage.AFTER_PARTICLES && event.getFrustum() != null) {
         FRAME_FRUSTUM.set(event.getFrustum());
      }
   }

   public static void endFrame() {
      FRAME_FRUSTUM.remove();
   }

   public static boolean shouldCull(Object hole) {
      Frustum frustum = FRAME_FRUSTUM.get();
      return frustum != null && hole != null ? HOLE_FIELDS.get(hole.getClass()).isFullyOutside(hole, frustum) : false;
   }

   private static boolean isFinite(Vec3 value) {
      return value != null && Double.isFinite(value.x) && Double.isFinite(value.y) && Double.isFinite(value.z);
   }

   private static final class HoleFields {
      private static final MoreSkillsAsmodayFrustumCulling.HoleFields INVALID = new MoreSkillsAsmodayFrustumCulling.HoleFields(null, null);
      private final Field center;
      private final Field radius;

      private HoleFields(Field center, Field radius) {
         this.center = center;
         this.radius = radius;
      }

      private static MoreSkillsAsmodayFrustumCulling.HoleFields inspect(Class<?> type) {
         try {
            Field center = type.getDeclaredField("center");
            Field radius = type.getDeclaredField("radius");
            return center.trySetAccessible() && radius.trySetAccessible() ? new MoreSkillsAsmodayFrustumCulling.HoleFields(center, radius) : INVALID;
         } catch (ReflectiveOperationException | RuntimeException ignored) {
            return INVALID;
         }
      }

      private boolean isFullyOutside(Object hole, Frustum frustum) {
         if (this == INVALID) {
            return false;
         }

         try {
            Object centerValue = this.center.get(hole);
            Object radiusValue = this.radius.get(hole);
            if (centerValue instanceof Vec3 center && radiusValue instanceof Number number) {
               float radiusValueFloat = number.floatValue();
               if (MoreSkillsAsmodayFrustumCulling.isFinite(center) && Float.isFinite(radiusValueFloat)) {
                  double radius = Math.abs((double)radiusValueFloat);
                  double horizontal = radius * 11.0 + 1.0;
                  double vertical = radius * 6.0 + 1.0;
                  if (Double.isFinite(horizontal) && Double.isFinite(vertical)) {
                     AABB bounds = new AABB(
                        center.x - horizontal, center.y - vertical, center.z - horizontal, center.x + horizontal, center.y + vertical, center.z + horizontal
                     );
                     return !frustum.isVisible(bounds);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } catch (ReflectiveOperationException | RuntimeException ignored) {
            return false;
         }
      }
   }
}
