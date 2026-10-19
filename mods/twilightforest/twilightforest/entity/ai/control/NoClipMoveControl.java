package twilightforest.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;

public class NoClipMoveControl extends MoveControl {
   private final LivingEntity parentEntity;
   private int courseChangeCooldown;

   public NoClipMoveControl(Mob entity) {
      super(entity);
      this.parentEntity = entity;
   }

   public void tick() {
      if (this.operation == Operation.MOVE_TO) {
         double dx = this.getWantedX() - this.parentEntity.getX();
         double dy = this.getWantedY() - this.parentEntity.getY();
         double dz = this.getWantedZ() - this.parentEntity.getZ();
         double dist = dx * dx + dy * dy + dz * dz;
         if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown = this.courseChangeCooldown + this.parentEntity.getRandom().nextInt(5) + 2;
            dist = Mth.sqrt((float)dist);
            this.parentEntity
               .setDeltaMovement(
                  this.parentEntity
                     .getDeltaMovement()
                     .add(dx / dist * 0.1 * this.speedModifier, dy / dist * 0.1 * this.speedModifier, dz / dist * 0.1 * this.speedModifier)
               );
         }
      }
   }
}
