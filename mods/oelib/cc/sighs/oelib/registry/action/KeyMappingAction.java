package cc.sighs.oelib.registry.action;

import java.util.Objects;
import net.minecraft.client.KeyMapping;

public record KeyMappingAction(KeyMapping mapping) implements RegistrationAction {
   public KeyMappingAction(KeyMapping mapping) {
      this.mapping = Objects.requireNonNull(mapping, "mapping");
   }
}
