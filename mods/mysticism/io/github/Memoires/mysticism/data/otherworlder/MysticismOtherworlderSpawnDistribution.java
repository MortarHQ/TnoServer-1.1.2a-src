package io.github.Memoires.mysticism.data.otherworlder;

import io.github.manasmods.tensura.data.otherworlder.OtherworlderSpawnDistribution;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class MysticismOtherworlderSpawnDistribution {
   public static void bootstrap(BootstrapContext<OtherworlderSpawnDistribution> context) {
      register(context, OtherworlderSpawnDistribution.getDefault(HumanEntityTypes.FOLGEN.getId(), 0.125));
   }

   public static void register(BootstrapContext<OtherworlderSpawnDistribution> context, OtherworlderSpawnDistribution data) {
      ResourceKey<OtherworlderSpawnDistribution> key = ResourceKey.create(TensuraCustomData.OTHERWORLDER_SPAWN_DISTRIBUTION, data.entity());
      context.register(key, data);
   }
}
