package net.unusual.block_factorys_bosses.client.particle.bedrock.motion;

import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import org.joml.Vector3f;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleMotionCollision(MathValue enabled, float coefficientOfRestitution, float collisionDrag, float collisionRadius, boolean expireOnContact) {
   public void initialize(AbstractBedrockParticle particle) {
      particle.setSize(this.collisionRadius(), this.collisionRadius());
   }

   public Vec3 move(AbstractBedrockParticle particle, Vec3 motion) {
      if (this.enabled().get() == 0.0) {
         return motion;
      }

      Vec3 restrictedMotion = Entity.collideBoundingBox(null, motion, particle.getBoundingBox(), particle.getLevel(), List.of());
      boolean collided = !restrictedMotion.equals(motion);
      if (!collided) {
         return motion;
      }

      if (this.expireOnContact()) {
         particle.remove();
         return motion;
      }

      if (this.coefficientOfRestitution() > 0.0F) {
         Vec3 collisionNormal = restrictedMotion.subtract(motion).normalize();
         Vector3f velocity = particle.getVelocity();
         Vec3 impulse = collisionNormal.scale(velocity.length() * (1.0F + this.coefficientOfRestitution()));
         velocity.add(impulse.toVector3f());
         particle.setVelocity(velocity);
      }

      if (this.collisionDrag() > 0.0F) {
         if (restrictedMotion.length() > this.collisionDrag()) {
            restrictedMotion = restrictedMotion.subtract(restrictedMotion.normalize().scale(this.collisionDrag()));
         } else {
            restrictedMotion = Vec3.ZERO;
         }
      }

      return restrictedMotion;
   }

   @Nullable
   public static ParticleMotionCollision deserializeFromComponents(JsonObject allComponents) {
      JsonObject componentJson = allComponents.getAsJsonObject("minecraft:particle_motion_collision");
      return componentJson == null ? null : deserialize(componentJson);
   }

   private static float getFloatOrDefault(JsonObject json, String key, float defaultValue) {
      return !json.has(key) ? defaultValue : json.get(key).getAsFloat();
   }

   private static boolean getBooleanOrDefault(JsonObject json, String key, boolean defaultValue) {
      return !json.has(key) ? defaultValue : json.get(key).getAsBoolean();
   }

   public static ParticleMotionCollision deserialize(JsonObject component) {
      return new ParticleMotionCollision(
         BedrockParticleEffect.parseMolangJson(component.get("enabled"), 1.0),
         getFloatOrDefault(component, "coefficient_of_restitution", 0.0F),
         getFloatOrDefault(component, "collision_drag", 0.0F),
         getFloatOrDefault(component, "collision_radius", 0.1F),
         getBooleanOrDefault(component, "expire_on_contact", false)
      );
   }
}
