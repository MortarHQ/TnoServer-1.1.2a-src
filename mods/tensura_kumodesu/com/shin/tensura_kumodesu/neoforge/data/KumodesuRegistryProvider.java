package com.shin.tensura_kumodesu.neoforge.data;

import com.shin.tensura_kumodesu.data.existence.KumodesuEntityExistenceData;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class KumodesuRegistryProvider extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(TensuraCustomData.ENTITY_EXISTENCE, KumodesuEntityExistenceData::bootstrap);

   public KumodesuRegistryProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries, BUILDER, Set.of("tensura_kumodesu", "minecraft"));
   }
}
