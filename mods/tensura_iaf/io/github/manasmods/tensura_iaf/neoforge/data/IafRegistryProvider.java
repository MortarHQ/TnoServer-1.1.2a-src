package io.github.manasmods.tensura_iaf.neoforge.data;

import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura_iaf.data.IafEntityExistenceData;
import io.github.manasmods.tensura_iaf.data.IafGearExistenceData;
import io.github.manasmods.tensura_iaf.data.IafLevelMagiculeModifiers;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class IafRegistryProvider extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
      .add(TensuraCustomData.ENTITY_EXISTENCE, IafEntityExistenceData::bootstrap)
      .add(TensuraCustomData.GEAR_EXISTENCE, IafGearExistenceData::bootstrap)
      .add(TensuraCustomData.LEVEL_MAGICULE, IafLevelMagiculeModifiers::bootstrap);

   public IafRegistryProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries, BUILDER, Set.of("tensura_iaf", "iceandfire"));
   }
}
