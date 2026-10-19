package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class ESPoiTypes {
   public static final RegistrationProvider<PoiType> POI_TYPES = RegistrationProvider.get(Registries.POINT_OF_INTEREST_TYPE, "eternal_starlight");
   public static final RegistryObject<PoiType, PoiType> STARFIRE_BIRD_NEST = POI_TYPES.register(
      "starfire_bird_nest",
      () -> new PoiType(
         ImmutableList.of(
               (StarfireBirdNestBlock)ESBlocks.STARFIRE_BIRD_NEST.get(),
               (StarfireBirdNestBlock)ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(),
               (StarfireBirdNestBlock)ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(),
               new StarfireBirdNestBlock[]{
                  (StarfireBirdNestBlock)ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(),
                  (StarfireBirdNestBlock)ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(),
                  (StarfireBirdNestBlock)ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(),
                  (StarfireBirdNestBlock)ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(),
                  (StarfireBirdNestBlock)ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(),
                  (StarfireBirdNestBlock)ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(),
                  (StarfireBirdNestBlock)ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get()
               }
            )
            .stream()
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .collect(ImmutableSet.toImmutableSet()),
         1,
         1
      )
   );
   public static final RegistryObject<PoiType, PoiType> ENERGY_BLOCK = POI_TYPES.register(
      "energy_block",
      () -> new PoiType(
         ImmutableList.of(ESBlocks.ENERGY_BLOCK.get())
            .stream()
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .collect(ImmutableSet.toImmutableSet()),
         1,
         1
      )
   );

   public static void loadClass() {
   }
}
