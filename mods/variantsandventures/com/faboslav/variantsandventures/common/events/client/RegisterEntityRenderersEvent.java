package com.faboslav.variantsandventures.common.events.client;

import com.faboslav.variantsandventures.common.events.base.EventHandler;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public record RegisterEntityRenderersEvent(RegisterEntityRenderersEvent.Registrar registrar) {
   public static final EventHandler<RegisterEntityRenderersEvent> EVENT = new EventHandler<>();

   public <T extends Entity> void register(EntityType<T> type, EntityRendererProvider<T> factory) {
      this.registrar.register(type, factory);
   }

   @FunctionalInterface
   public interface Registrar {
      <T extends Entity> void register(EntityType<? extends T> var1, EntityRendererProvider<T> var2);
   }
}
