package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public record RegisterEntityLayersEvent(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> registrar) {
   public static final EventHandler<RegisterEntityLayersEvent> EVENT = new EventHandler<>();

   public void register(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
      this.registrar.accept(location, definition);
   }
}
