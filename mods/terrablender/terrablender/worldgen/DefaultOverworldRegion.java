package terrablender.worldgen;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class DefaultOverworldRegion extends Region {
   public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "overworld");

   public DefaultOverworldRegion(int weight) {
      super(LOCATION, RegionType.OVERWORLD, weight);
   }

   @Override
   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      new OverworldBiomeBuilder().addBiomes(mapper);
   }
}
