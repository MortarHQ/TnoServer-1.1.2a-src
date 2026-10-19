package twilightforest.entity.passive;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class Bird extends Animal {
   public float flapLength = 0.0F;
   public float flapIntensity = 0.0F;
   public float lastFlapIntensity;
   public float lastFlapLength;
   public float flapSpeed = 1.0F;

   public Bird(EntityType<? extends Bird> entity, Level world) {
      super(entity, world);
   }

   public void aiStep() {
      super.aiStep();
      this.lastFlapLength = this.flapLength;
      this.lastFlapIntensity = this.flapIntensity;
      this.flapIntensity = (float)(this.flapIntensity + (this.onGround() ? -1 : 4) * 0.3);
      if (this.flapIntensity < 0.0F) {
         this.flapIntensity = 0.0F;
      }

      if (this.flapIntensity > 1.0F) {
         this.flapIntensity = 1.0F;
      }

      if (!this.onGround() && this.flapSpeed < 1.0F) {
         this.flapSpeed = 1.0F;
      }

      this.flapSpeed = (float)(this.flapSpeed * 0.9);
      if (!this.onGround() && this.getDeltaMovement().y() < 0.0) {
         this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), this.getDeltaMovement().y() * 0.6, this.getDeltaMovement().z()));
      }

      this.flapLength = this.flapLength + this.flapSpeed * 2.0F;
   }

   public boolean isSteppingCarefully() {
      return false;
   }

   public boolean isFood(ItemStack stack) {
      return false;
   }

   public Animal getBreedOffspring(ServerLevel world, AgeableMob ageableMob) {
      return null;
   }

   public boolean isBirdLanded() {
      return true;
   }
}
