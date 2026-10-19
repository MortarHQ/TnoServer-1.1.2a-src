package team.creative.creativecore.mixin;

import java.nio.file.Path;
import net.minecraft.server.packs.PathPackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PathPackResources.class)
public interface PathPackResourcesAccessor {
   @Accessor
   Path getRoot();
}
