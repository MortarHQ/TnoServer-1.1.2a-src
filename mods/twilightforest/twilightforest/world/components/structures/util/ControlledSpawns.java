package twilightforest.world.components.structures.util;

import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

public interface ControlledSpawns {
   String CODEC_NAME = "controlled_spawns";

   List<SpawnerData> getCombinedMonsterSpawnableList();

   List<SpawnerData> getCombinedCreatureSpawnableList();

   List<SpawnerData> getSpawnableList(MobCategory var1);

   List<SpawnerData> getSpawnableMonsterList(int var1);

   record ControlledSpawningConfig(
      Map<String, List<SpawnerData>> spawnableMonsterLists,
      List<SpawnerData> ambientCreatureList,
      List<SpawnerData> waterCreatureList,
      List<SpawnerData> combinedMonsterSpawnableCache,
      List<SpawnerData> combinedCreatureSpawnableCache
   ) {
      public static final Codec<ControlledSpawns.ControlledSpawningConfig> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.unboundedMap(Codec.STRING, SpawnerData.CODEC.listOf())
                  .fieldOf("labelled_monster_spawns")
                  .forGetter(ControlledSpawns.ControlledSpawningConfig::spawnableMonsterLists),
               SpawnerData.CODEC.listOf().fieldOf("ambient_spawns").forGetter(ControlledSpawns.ControlledSpawningConfig::ambientCreatureList),
               SpawnerData.CODEC.listOf().fieldOf("water_spawns").forGetter(ControlledSpawns.ControlledSpawningConfig::waterCreatureList)
            )
            .apply(instance, ControlledSpawns.ControlledSpawningConfig::create)
      );
      public static final ControlledSpawns.ControlledSpawningConfig EMPTY = create(Map.of(), List.of(), List.of());

      public static ControlledSpawns.ControlledSpawningConfig firstIndexMonsters(SpawnerData... spawnableMonsterList) {
         return justMonsters(Arrays.asList(spawnableMonsterList));
      }

      public static ControlledSpawns.ControlledSpawningConfig justMonsters(List<SpawnerData>... spawnableMonsterLists) {
         return create(convertMonsterList(Arrays.asList(spawnableMonsterLists)), List.of(), List.of());
      }

      public static ControlledSpawns.ControlledSpawningConfig create(
         List<List<SpawnerData>> spawnableMonsterLists, List<SpawnerData> ambientCreatureList, List<SpawnerData> waterCreatureList
      ) {
         return create(convertMonsterList(spawnableMonsterLists), ambientCreatureList, waterCreatureList);
      }

      public static ControlledSpawns.ControlledSpawningConfig create(
         Map<String, List<SpawnerData>> spawnableMonsterLists, List<SpawnerData> ambientCreatureList, List<SpawnerData> waterCreatureList
      ) {
         return new ControlledSpawns.ControlledSpawningConfig(
            spawnableMonsterLists,
            ambientCreatureList,
            waterCreatureList,
            spawnableMonsterLists.values().stream().flatMap(Collection::stream).toList(),
            Streams.concat(new Stream[]{ambientCreatureList.stream(), waterCreatureList.stream()}).toList()
         );
      }

      private static Map<String, List<SpawnerData>> convertMonsterList(List<List<SpawnerData>> lists) {
         int i = 0;
         Map<String, List<SpawnerData>> map = new HashMap<>();

         for (List<SpawnerData> list : lists) {
            map.put(i + "", list);
            i++;
         }

         return map;
      }

      public List<SpawnerData> getForLabel(String index) {
         return this.spawnableMonsterLists().getOrDefault(index, List.of());
      }
   }
}
