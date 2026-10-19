package com.blamejared.crafttweaker.mixin.common.access.registry;

import java.util.stream.Stream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.RegistryAccess.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RegistrySynchronization.class)
public interface AccessRegistrySynchronization {
   @Invoker("ownedNetworkableRegistries")
   static Stream<RegistryEntry<?>> crafttweaker$callOwnedNetworkableRegistries(RegistryAccess $$0) {
      throw new UnsupportedOperationException();
   }
}
