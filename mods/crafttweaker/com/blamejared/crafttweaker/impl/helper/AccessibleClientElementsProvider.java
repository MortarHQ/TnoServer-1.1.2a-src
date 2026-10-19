package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.registry.TagAddingRegistryLookup;
import com.blamejared.crafttweaker.platform.helper.IAccessibleClientElementsProvider;
import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.RegistryAccess;

public final class AccessibleClientElementsProvider implements IAccessibleClientElementsProvider {
   private static final Supplier<AccessibleClientElementsProvider> INSTANCE = Suppliers.memoize(AccessibleClientElementsProvider::new);
   private RegistryAccess registryAccess = null;
   private final List<Consumer<RegistryAccess>> waitingForRegistryAccess = new ArrayList<>();
   private TagAddingRegistryLookup tagAddingRegistryLookup = null;

   private AccessibleClientElementsProvider() {
   }

   static IAccessibleClientElementsProvider get() {
      return INSTANCE.get();
   }

   @Override
   public RegistryAccess registryAccess() {
      return Objects.requireNonNull(this.registryAccess, "RegistryAccess is unavailable");
   }

   @Override
   public void registryAccess(RegistryAccess registryAccess) {
      this.registryAccess = registryAccess;
      this.tagAddingRegistryLookup = new TagAddingRegistryLookup(this.registryAccess);
      Iterator<Consumer<RegistryAccess>> iterator = this.waitingForRegistryAccess.iterator();

      while (iterator.hasNext()) {
         iterator.next().accept(this.registryAccess);
         iterator.remove();
      }
   }

   @Override
   public void runWithRegistryAccess(Consumer<RegistryAccess> consumer) {
      if (this.hasRegistryAccess()) {
         consumer.accept(this.registryAccess());
      } else {
         this.waitingForRegistryAccess.add(consumer);
      }
   }

   @Override
   public boolean hasRegistryAccess() {
      return this.registryAccess != null;
   }

   @Override
   public TagAddingRegistryLookup tagAddingRegistryLookup() {
      return this.tagAddingRegistryLookup;
   }
}
