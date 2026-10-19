package com.faboslav.variantsandventures.common.events.entity;

import com.faboslav.variantsandventures.common.events.base.EventHandler;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;

public record ProjectileHitEvent(Projectile projectile, EntityHitResult hitResult) {
   public static final EventHandler<ProjectileHitEvent> EVENT = new EventHandler<>();
}
