package io.github.Memoires.mysticism.data.chunk;

import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.manasmods.tensura.data.chunk.DataPackMagiculeModifier;
import io.github.manasmods.tensura.data.chunk.LevelMagiculeModifier;
import io.github.manasmods.tensura.data.chunk.DataPackMagiculeModifier.Mode;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import java.util.List;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class MysticismLevelMagiculeModifiers {
   public static void bootstrap(BootstrapContext<LevelMagiculeModifier> context) {
      register(
         context,
         new LevelMagiculeModifier(
            MysticismDimensions.ELEMENTAL_REALM.location(),
            0,
            List.of(new DataPackMagiculeModifier(Mode.ADD, 100000.0)),
            List.of(new DataPackMagiculeModifier(Mode.MULTIPLY, 2.0))
         )
      );
   }

   public static void register(BootstrapContext<LevelMagiculeModifier> context, LevelMagiculeModifier data) {
      ResourceKey<LevelMagiculeModifier> key = ResourceKey.create(TensuraCustomData.LEVEL_MAGICULE, data.worldId());
      context.register(key, data);
   }
}
