package it.hurts.sskirillss.relics.entities.misc;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public interface ITargetableEntity {
   @Nullable
   LivingEntity getTarget();

   void setTarget(LivingEntity var1);
}
