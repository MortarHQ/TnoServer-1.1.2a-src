package terrablender.worldgen;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.RTree;
import net.minecraft.world.level.biome.Climate.TargetPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public interface IExtendedParameterList<T> extends Cloneable {
   void initializeForTerraBlender(RegistryAccess var1, RegionType var2, long var3);

   T findValuePositional(TargetPoint var1, int var2, int var3, int var4);

   int getUniqueness(int var1, int var2, int var3);

   RTree getTree(int var1);

   Region getRegion(int var1);

   int getTreeCount();

   boolean isInitialized();

   ParameterList<T> clone();

   void recreateUniqueness();
}
