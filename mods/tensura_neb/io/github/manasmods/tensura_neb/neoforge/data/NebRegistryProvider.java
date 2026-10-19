package io.github.manasmods.tensura_neb.neoforge.data;

import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura_neb.data.NebEntityExistenceData;
import io.github.manasmods.tensura_neb.data.NebGearExistenceData;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class NebRegistryProvider extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
      .add(TensuraCustomData.ENTITY_EXISTENCE, NebEntityExistenceData::bootstrap)
      .add(TensuraCustomData.GEAR_EXISTENCE, NebGearExistenceData::bootstrap);

   public NebRegistryProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries, BUILDER, Set.of("tensura_neb", "minecraft"));
   }
}
