package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.registry.TagAddingRegistryLookup;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessReloadableServerResources;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;

public final class AccessibleServerElementsProvider implements IAccessibleServerElementsProvider {
   private static final Supplier<AccessibleServerElementsProvider> INSTANCE = Suppliers.memoize(AccessibleServerElementsProvider::new);
   private ReloadableServerResources resources = null;
   private TagAddingRegistryLookup tagAddingRegistryLookup;
   private RegistryAccess registryAccess = null;
   private final List<Consumer<RegistryAccess>> waitingForRegistryAccess;

   private AccessibleServerElementsProvider() {
      this.tagAddingRegistryLookup = null;
      this.waitingForRegistryAccess = new ArrayList<>();
   }

   static IAccessibleServerElementsProvider get() {
      return INSTANCE.get();
   }

   @Override
   public ReloadableServerResources resources() {
      return Objects.requireNonNull(this.resources, "Resources is unavailable");
   }

   @Override
   public AccessReloadableServerResources accessibleResources() {
      return (AccessReloadableServerResources)this.resources();
   }

   @Override
   public boolean hasResources() {
      return this.resources != null;
   }

   @Override
   public void resources(ReloadableServerResources resources) {
      this.resources = resources;
   }

   @Override
   public RegistryAccess registryAccess() {
      return this.registryAccess;
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
