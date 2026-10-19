package net.unusual.block_factorys_bosses.entity.control;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class NoMoveControl extends MoveControl {
   public NoMoveControl(Mob mob) {
      super(mob);
   }

   public void tick() {
      this.operation = Operation.WAIT;
   }
}
