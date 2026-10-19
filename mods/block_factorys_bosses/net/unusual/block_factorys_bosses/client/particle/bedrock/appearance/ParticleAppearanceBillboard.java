package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector2;
import org.joml.Vector2f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleAppearanceBillboard(ParticleVector2 size, FacingCameraMode facingCameraMode, ParticleUV uv) {
   public AABB getRenderBoundingBox(BedrockParticle particle, float partialTicks) {
      Vector2f quadSize = this.size().getNewVector();
      Vec3 position = particle.getPos(partialTicks);
      float size = Math.max(quadSize.x(), quadSize.y()) + 0.5F;
      return new AABB(position.x() - size, position.y() - size, position.z() - size, position.x() + size, position.y() + size, position.z() + size);
   }

   public static ParticleAppearanceBillboard deserializeFromComponents(JsonObject allComponents) {
      return deserialize(allComponents.getAsJsonObject("minecraft:particle_appearance_billboard"));
   }

   public static ParticleAppearanceBillboard deserialize(JsonObject component) {
      return new ParticleAppearanceBillboard(
         ParticleVector2.deserialize(component.get("size")),
         FacingCameraMode.deserialize(component.get("facing_camera_mode")),
         ParticleUV.deserialize(component.getAsJsonObject("uv"))
      );
   }
}
