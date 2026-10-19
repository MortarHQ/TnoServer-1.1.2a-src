package com.bobmowzie.mowziesmobs.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
      .add(Registries.STRUCTURE, StructureHandler::bootstrap)
      .add(Registries.STRUCTURE_SET, StructureSetHandler::bootstrap);

   public RegistryDataGenerator(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider, BUILDER, Set.of("minecraft", "mowziesmobs"));
   }
}
