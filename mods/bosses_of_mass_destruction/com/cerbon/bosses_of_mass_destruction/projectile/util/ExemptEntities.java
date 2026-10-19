package com.cerbon.bosses_of_mass_destruction.projectile.util;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.EntityHitResult;

public class ExemptEntities implements Predicate<EntityHitResult> {
   final List<EntityType<?>> exemptEntities;

   public ExemptEntities(List<EntityType<?>> exemptEntities) {
      this.exemptEntities = exemptEntities;
   }

   public boolean test(EntityHitResult t) {
      return !this.exemptEntities.contains(t.getEntity().getType());
   }
}
