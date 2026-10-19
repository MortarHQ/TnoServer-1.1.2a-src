package net.unusual.block_factorys_bosses.entity.control;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class NoLookControl extends LookControl {
   public NoLookControl(Mob mob) {
      super(mob);
   }

   public void tick() {
   }
}
