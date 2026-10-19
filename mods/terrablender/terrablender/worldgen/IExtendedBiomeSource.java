package terrablender.worldgen;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public interface IExtendedBiomeSource {
   void appendDeferredBiomesList(List<Holder<Biome>> var1);
}
