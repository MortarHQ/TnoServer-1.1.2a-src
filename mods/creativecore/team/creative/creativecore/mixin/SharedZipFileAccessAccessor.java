package team.creative.creativecore.mixin;

import java.util.zip.ZipFile;
import net.minecraft.server.packs.FilePackResources.SharedZipFileAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SharedZipFileAccess.class)
public interface SharedZipFileAccessAccessor {
   @Invoker
   ZipFile callGetOrCreateZipFile();
}
