package com.faboslav.variantsandventures.common.init;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.config.VariantsAndVenturesConfig;
import com.faboslav.variantsandventures.common.mixin.JigsawStructureAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.SimpleWeightedRandomList.Builder;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.Random;
import net.minecraft.world.level.levelgen.structure.pools.alias.RandomGroup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public final class VariantsAndVenturesStructurePoolAliases {
   public static void init(MinecraftServer server) {
      updateTrialChamberSpawners(server);
   }

   private static void updateTrialChamberSpawners(MinecraftServer server) {
      VariantsAndVenturesConfig config = VariantsAndVentures.getConfig();
      Registry<Structure> structureRegistry = server.registryAccess().registryOrThrow(Registries.STRUCTURE);
      JigsawStructure structure = (JigsawStructure)structureRegistry.get(VariantsAndVentures.makeNamespacedId("minecraft:trial_chambers"));
      JigsawStructureAccessor structureAccessor = (JigsawStructureAccessor)structure;
      List<PoolAliasBinding> originalPoolAliasBindings = structureAccessor.getPoolAliasBindings();
      List<PoolAliasBinding> newPoolAliasBindings = new ArrayList<>();

      for (PoolAliasBinding originalPoolAliasBinding : originalPoolAliasBindings) {
         if (originalPoolAliasBinding instanceof RandomGroup randomGroupStructurePoolAliasBinding) {
            Builder<List<PoolAliasBinding>> dataPoolBuilder = SimpleWeightedRandomList.builder();

            for (Wrapper<List<PoolAliasBinding>> group : randomGroupStructurePoolAliasBinding.groups().unwrap()) {
               dataPoolBuilder.add((List)group.data());
            }

            if (config.enableMurkSpawnersInTrialChambers) {
               dataPoolBuilder.add(
                  List.of(
                     PoolAliasBinding.direct("trial_chambers/spawner/contents/ranged", "trial_chambers/spawner/ranged/murk"),
                     PoolAliasBinding.direct("trial_chambers/spawner/contents/slow_ranged", "trial_chambers/spawner/slow_ranged/murk")
                  )
               );
            }

            if (config.enableVerdantSpawnersInTrialChambers) {
               dataPoolBuilder.add(
                  List.of(
                     PoolAliasBinding.direct("trial_chambers/spawner/contents/ranged", "trial_chambers/spawner/ranged/verdant"),
                     PoolAliasBinding.direct("trial_chambers/spawner/contents/slow_ranged", "trial_chambers/spawner/slow_ranged/verdant")
                  )
               );
            }

            RandomGroup newRandomGroupStructurePoolAliasBinding = PoolAliasBinding.randomGroup(dataPoolBuilder.build());
            newPoolAliasBindings.add(newRandomGroupStructurePoolAliasBinding);
         } else if (originalPoolAliasBinding instanceof Random randomStructurePoolAliasBinding) {
            String alias = randomStructurePoolAliasBinding.alias().location().getPath();
            List<ResourceKey<StructureTemplatePool>> registryKeys = randomStructurePoolAliasBinding.allTargets().toList();
            Random newRandomStructurePoolAliasBinding;
            if (Objects.equals(alias, "trial_chambers/spawner/contents/melee")) {
               Builder<String> dataPoolBuilder = SimpleWeightedRandomList.builder();
               registryKeys.forEach(registryKey -> {
                  String value = registryKey.location().getPath();
                  dataPoolBuilder.add(value);
               });
               if (config.enableGelidSpawnersInTrialChambers) {
                  dataPoolBuilder.add("trial_chambers/spawner/melee/gelid");
               }

               if (config.enableThicketSpawnersInTrialChambers) {
                  dataPoolBuilder.add("trial_chambers/spawner/melee/thicket");
               }

               newRandomStructurePoolAliasBinding = PoolAliasBinding.random(alias, dataPoolBuilder.build());
            } else {
               newRandomStructurePoolAliasBinding = randomStructurePoolAliasBinding;
            }

            newPoolAliasBindings.add(newRandomStructurePoolAliasBinding);
         } else {
            newPoolAliasBindings.add(originalPoolAliasBinding);
         }
      }

      structureAccessor.setPoolAliasBindings(newPoolAliasBindings);
   }

   private VariantsAndVenturesStructurePoolAliases() {
   }
}
