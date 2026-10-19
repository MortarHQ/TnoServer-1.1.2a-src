package artifacts.mixin.debug;

import artifacts.Artifacts;
import com.google.gson.JsonElement;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagLoader.EntryWithSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TagLoader.class)
public class TagLoaderMixin {
   @Inject(
      method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;)Ljava/util/Map;",
      require = 1,
      locals = LocalCapture.CAPTURE_FAILHARD,
      at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V")
   )
   private void detectClear(
      ResourceManager resourceManager,
      CallbackInfoReturnable<Map<ResourceLocation, List<EntryWithSource>>> cir,
      Map<ResourceLocation, List<EntryWithSource>> map,
      FileToIdConverter fileToIdConverter,
      Iterator var4,
      Entry<ResourceLocation, List<Resource>> entry,
      ResourceLocation resourceLocation,
      ResourceLocation resourceLocation2,
      Iterator var8,
      Resource resource,
      Reader reader,
      JsonElement jsonElement,
      List<EntryWithSource> list,
      TagFile tagFile
   ) {
      String namespace = resourceLocation.getNamespace();
      if (namespace.equals("trinkets") || namespace.equals("curios") || namespace.equals("accessories") || namespace.equals("artifacts")) {
         Artifacts.LOGGER.warn("Tag entries for {} cleared by {}", resourceLocation, resource.sourcePackId());
      }
   }
}
