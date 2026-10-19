package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;

public record RegisterEntityAttributesEvent(BiConsumer<EntityType<? extends LivingEntity>, Builder> attributes) {
   public static final EventHandler<RegisterEntityAttributesEvent> EVENT = new EventHandler<>();

   public void register(EntityType<? extends LivingEntity> entityType, Builder builder) {
      this.attributes.accept(entityType, builder);
   }
}
