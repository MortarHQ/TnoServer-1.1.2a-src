package twilightforest.beanification.internal;

import cpw.mods.jarhandling.SecureJar;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.modscan.Scanner;
import net.neoforged.fml.loading.modscan.ModAnnotation.EnumHolder;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class DistAnnotationRetriever {
   @Nullable
   private Supplier<ModFileScanData> cachedBeanificationScan;

   public final Optional<ModFileScanData> getBeanificationScanData() {
      if (this.cachedBeanificationScan != null) {
         return Optional.ofNullable(this.cachedBeanificationScan.get());
      }

      FMLLoader.getGameLayer().configuration().modules().stream().filter(r -> r.name().equals("beanification")).findAny().ifPresentOrElse(module -> {
         SecureJar jar = SecureJar.from(new Path[]{Path.of(module.reference().location().orElseThrow())});
         ModFile modFile = new ModFile(jar, file -> null, new ModFileDiscoveryAttributes(null, null, null, null));
         ModFileScanData result = new Scanner(modFile).scan();
         this.cachedBeanificationScan = () -> result;
      }, () -> this.cachedBeanificationScan = () -> null);
      return Optional.ofNullable(this.cachedBeanificationScan.get());
   }

   @SafeVarargs
   public final Stream<AnnotationData> retrieve(ModFileScanData scanData, ElementType elementType, Class<? extends Annotation>... types) {
      List<Class<? extends Annotation>> t = new ArrayList<>();

      for (Class<? extends Annotation> type : types) {
         t.add(type);
      }

      return t.stream()
         .flatMap(
            typex -> {
               Stream<AnnotationData> modScan = scanData.getAnnotatedBy(typex, elementType);
               Stream<AnnotationData> combinedScan = this.getBeanificationScanData()
                  .map(s -> Stream.concat(s.getAnnotatedBy(typex, elementType), modScan))
                  .orElse(modScan);
               return combinedScan.filter(annotation -> {
                  if (annotation.annotationData().get("dist") instanceof ArrayList<?> list) {
                     if (list.isEmpty()) {
                        return true;
                     }

                     for (Object o : list) {
                        if (o instanceof EnumHolder e && Dist.valueOf(e.value()) == FMLEnvironment.dist) {
                           return true;
                        }
                     }

                     return false;
                  } else {
                     return true;
                  }
               });
            }
         );
   }
}
