package cn.star.structblend.mixin;

import java.util.Optional;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(JigsawStructure.class)
public interface JigsawStructureAccessor {
   @Accessor("projectStartToHeightmap")
   Optional<Types> structblend$getProjectStartToHeightmap();
}
