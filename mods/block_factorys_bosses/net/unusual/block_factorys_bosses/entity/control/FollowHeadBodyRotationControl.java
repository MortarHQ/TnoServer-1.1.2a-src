package net.unusual.block_factorys_bosses.entity.control;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FollowHeadBodyRotationControl extends BodyRotationControl {
   protected final Mob mob;

   public FollowHeadBodyRotationControl(Mob mob) {
      super(mob);
      this.mob = mob;
   }

   public void clientTick() {
      this.mob.yBodyRot = this.mob.getYRot();
   }
}
