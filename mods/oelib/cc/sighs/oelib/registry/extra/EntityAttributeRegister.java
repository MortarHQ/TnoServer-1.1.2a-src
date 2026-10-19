package cc.sighs.oelib.registry.extra;

import cc.sighs.oelib.registry.RegistrationDispatcher;
import cc.sighs.oelib.registry.action.EntityAttributeAction;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;

public final class EntityAttributeRegister {
   private EntityAttributeRegister() {
   }

   public static void register(Supplier<? extends EntityType<? extends LivingEntity>> type, Supplier<Builder> attributes) {
      RegistrationDispatcher.perform(new EntityAttributeAction(type, attributes));
   }
}
