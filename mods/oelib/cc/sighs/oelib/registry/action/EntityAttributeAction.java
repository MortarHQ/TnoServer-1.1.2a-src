package cc.sighs.oelib.registry.action;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;

public record EntityAttributeAction(Supplier<? extends EntityType<? extends LivingEntity>> type, Supplier<Builder> attributes) implements RegistrationAction {
   public EntityAttributeAction {
      Objects.requireNonNull(type, "type");
      Objects.requireNonNull(attributes, "attributes");
   }
}
