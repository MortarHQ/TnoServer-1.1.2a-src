package com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction;

import java.util.List;
import net.minecraft.world.phys.Vec3;

public class ValidDirectionAnd implements IValidDirection {
   private final List<IValidDirection> validators;

   public ValidDirectionAnd(List<IValidDirection> validators) {
      this.validators = validators;
   }

   @Override
   public boolean isValidDirection(Vec3 normedDirection) {
      return this.validators.stream().allMatch(validator -> validator.isValidDirection(normedDirection));
   }
}
