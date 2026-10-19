package team.creative.creativecore.mixin;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VanillaPackResources.class)
public interface VanillaPackResourcesAccessor {
   @Accessor
   Map<PackType, List<Path>> getPathsForType();
}
