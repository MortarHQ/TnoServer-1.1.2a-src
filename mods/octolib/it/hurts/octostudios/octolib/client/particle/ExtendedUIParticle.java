package it.hurts.octostudios.octolib.client.particle;

import it.hurts.octostudios.octolib.util.VectorUtils;
import net.minecraft.util.Mth;
import org.joml.Vector2f;

public class ExtendedUIParticle extends UIParticle {
   private Vector2f gravityDirection = new Vector2f(0.0F, 1.0F);
   private float gravity = 0.0F;
   private float friction = 0.0F;
   private float angularVelocity = 0.0F;
   private float gravityAccel = 0.0F;

   public ExtendedUIParticle(UIParticle.Texture2D texture, float maxSpeed, int lifetime, float xStart, float yStart, UIParticle.Layer layer, float zOffset) {
      super(texture, maxSpeed, lifetime, xStart, yStart, layer, zOffset);
   }

   public void setGravityDirection(float x, float y) {
      this.gravityDirection = new Vector2f(x, y).normalize();
   }

   public void setGravityDirection(Vector2f gravityDirection) {
      this.gravityDirection = new Vector2f(gravityDirection).normalize();
   }

   @Override
   public void tick() {
      if (!this.isExpired()) {
         super.tick();
         if (this.angularVelocity != 0.0F) {
            this.setDirection(VectorUtils.rotate(this.getDirection().normalize(), this.angularVelocity));
         } else {
            this.getDirection().normalize();
         }

         this.setSpeed(Mth.clamp(this.getSpeed() * (1.0F - this.friction), 0.0F, this.getMaxSpeed()));
         this.gravityAccel = this.gravityAccel + this.gravity;
         if (this.gravityAccel != 0.0F) {
            this.getTransform().getPosition().add(new Vector2f(this.gravityDirection).mul(this.gravityAccel));
         }
      }
   }

   public Vector2f getGravityDirection() {
      return this.gravityDirection;
   }

   public float getGravity() {
      return this.gravity;
   }

   public void setGravity(float gravity) {
      this.gravity = gravity;
   }

   public float getFriction() {
      return this.friction;
   }

   public void setFriction(float friction) {
      this.friction = friction;
   }

   public float getAngularVelocity() {
      return this.angularVelocity;
   }

   public void setAngularVelocity(float angularVelocity) {
      this.angularVelocity = angularVelocity;
   }
}
