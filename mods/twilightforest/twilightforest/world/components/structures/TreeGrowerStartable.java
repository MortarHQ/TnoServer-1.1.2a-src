package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public interface TreeGrowerStartable {
   boolean checkSaplingClearance(ServerLevel var1, BlockPos var2);

   StructureStart generateFromSapling(
      RegistryAccess var1,
      ChunkGenerator var2,
      BiomeSource var3,
      RandomState var4,
      StructureTemplateManager var5,
      long var6,
      BlockPos var8,
      LevelHeightAccessor var9
   );
}
