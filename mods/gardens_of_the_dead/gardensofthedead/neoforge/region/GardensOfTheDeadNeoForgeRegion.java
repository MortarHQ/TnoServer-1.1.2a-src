package gardensofthedead.neoforge.region;

import com.mojang.datafixers.util.Pair;
import gardensofthedead.region.GardensOfTheDeadRegion;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class GardensOfTheDeadNeoForgeRegion extends Region implements GardensOfTheDeadRegion {
   public GardensOfTheDeadNeoForgeRegion() {
      super(ID, RegionType.NETHER, 4);
   }

   @Override
   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      GardensOfTheDeadRegion.super.addBiomes(registry, mapper);
   }

   @Override
   public void add(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ParameterPoint parameters, ResourceKey<Biome> biome) {
      this.addBiome(mapper, parameters, biome);
   }
}
