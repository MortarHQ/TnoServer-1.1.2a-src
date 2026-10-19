package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public class ModMatcher<V, R extends Registry<V>, C> implements Predicate<C> {
   private final R registry;
   private final String modId;
   private final Function<C, V> getObjectFromContext;

   public ModMatcher(R registry, String modId, Function<C, V> getObjectFromContext) {
      this.registry = registry;
      this.modId = modId;
      this.getObjectFromContext = getObjectFromContext;
      ToolRegistry.addModWithMapping(modId);
   }

   @Override
   public boolean test(C context) {
      return RegistryHelper.getRegistryName(this.registry, this.getObjectFromContext.apply(context))
         .map(rn -> rn.getNamespace().equals(this.modId))
         .orElse(false);
   }
}
