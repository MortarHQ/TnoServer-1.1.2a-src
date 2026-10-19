package noobanidus.mods.lootr.common.api.data;

import com.google.common.base.Suppliers;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SimpleLootrEntityInstance extends SimpleLootrInstance {
   private final Supplier<UUID> uuidSupplier;

   public SimpleLootrEntityInstance(Entity entityInstance, Supplier<Set<UUID>> visualOpenersSupplier, int size) {
      super(visualOpenersSupplier, size);
      this.providesOwnUuid = true;
      this.uuidSupplier = Suppliers.memoize(entityInstance::getUUID);
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.uuidSupplier.get();
   }
}
