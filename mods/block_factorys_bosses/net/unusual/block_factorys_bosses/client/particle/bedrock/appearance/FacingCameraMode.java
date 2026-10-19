package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Quaternionf;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface FacingCameraMode {
   Logger LOGGER = LogManager.getLogger();

   void getFacing(BedrockParticle var1, Quaternionf var2, Camera var3, float var4);

   static FacingCameraMode deserialize(JsonElement facingJson) {
      if (facingJson == null) {
         throw new IllegalArgumentException("Received a null facing_camera_vector.");
      }

      if (facingJson instanceof JsonPrimitive jsonPrimitive && jsonPrimitive.isString()) {
         String mode = jsonPrimitive.getAsString();

         return switch (mode) {
            case "direction_x", "direction_y", "direction_z", "emitter_transform_xy", "emitter_transform_xz", "emitter_transform_yz", "lookat_xyz", "lookat_y", "lookat_direction" -> {
               LOGGER.warn("Bedrock particle facing_camera_mode '{}' is not yet implemented, falling back to rotate_xyz", mode);
               yield FacingCameraMode.FacingCameraModeRotateXYZ.INSTANCE;
            }
            case "rotate_xyz" -> FacingCameraMode.FacingCameraModeRotateXYZ.INSTANCE;
            case "rotate_y" -> FacingCameraMode.FacingCameraModeRotateY.INSTANCE;
            default -> throw new IllegalArgumentException("Unknown facing_camera_vector: " + mode);
         };
      } else {
         throw new IllegalArgumentException("facing_camera_vector must be a string.");
      }
   }

   record FacingCameraModeRotateXYZ() implements FacingCameraMode {
      public static FacingCameraMode.FacingCameraModeRotateXYZ INSTANCE = new FacingCameraMode.FacingCameraModeRotateXYZ();

      @Override
      public void getFacing(BedrockParticle particle, Quaternionf quaternion, Camera renderInfo, float partialTicks) {
         net.minecraft.client.particle.SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ.setRotation(quaternion, renderInfo, partialTicks);
      }
   }

   record FacingCameraModeRotateY() implements FacingCameraMode {
      public static FacingCameraMode.FacingCameraModeRotateY INSTANCE = new FacingCameraMode.FacingCameraModeRotateY();

      @Override
      public void getFacing(BedrockParticle particle, Quaternionf quaternion, Camera renderInfo, float partialTicks) {
         net.minecraft.client.particle.SingleQuadParticle.FacingCameraMode.LOOKAT_Y.setRotation(quaternion, renderInfo, partialTicks);
      }
   }
}
