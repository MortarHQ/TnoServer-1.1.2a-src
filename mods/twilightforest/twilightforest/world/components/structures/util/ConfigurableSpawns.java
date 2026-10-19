package twilightforest.world.components.structures.util;

import java.util.List;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

public interface ConfigurableSpawns extends ControlledSpawns {
   ControlledSpawns.ControlledSpawningConfig getConfig();

   @Override
   default List<SpawnerData> getCombinedMonsterSpawnableList() {
      return this.getConfig().combinedMonsterSpawnableCache();
   }

   @Override
   default List<SpawnerData> getCombinedCreatureSpawnableList() {
      return this.getConfig().combinedCreatureSpawnableCache();
   }

   @Override
   default List<SpawnerData> getSpawnableList(MobCategory creatureType) {
      return switch (creatureType) {
         case MONSTER -> this.getSpawnableMonsterList(0);
         case AMBIENT -> this.getConfig().ambientCreatureList();
         case WATER_CREATURE -> this.getConfig().waterCreatureList();
         default -> List.of();
      };
   }

   @Override
   default List<SpawnerData> getSpawnableMonsterList(int index) {
      return this.getConfig().getForLabel(index + "");
   }
}
