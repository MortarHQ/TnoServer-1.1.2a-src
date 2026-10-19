package com.almostreliable.lootjs.mixin;

import dev.latvian.mods.rhino.util.RemapForJS;
import java.util.Optional;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPredicate.class)
public abstract class EntityPredicateMixin {
   @RemapForJS("distance")
   @Shadow
   @Final
   private Optional<DistancePredicate> distanceToPlayer;

   @RemapForJS("distance")
   @Shadow
   public abstract Optional<DistancePredicate> distanceToPlayer();
}
