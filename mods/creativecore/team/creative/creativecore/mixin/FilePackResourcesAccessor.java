package team.creative.creativecore.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.FilePackResources.SharedZipFileAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FilePackResources.class)
public interface FilePackResourcesAccessor {
   @Invoker
   static String callGetPathFromLocation(PackType type, ResourceLocation location) {
      throw new UnsupportedOperationException();
   }

   @Accessor
   SharedZipFileAccess getZipFileAccess();
}
