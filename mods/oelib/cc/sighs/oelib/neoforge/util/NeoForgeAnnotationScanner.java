package cc.sighs.oelib.neoforge.util;

import cc.sighs.oelib.util.spi.IAnnotationScanner;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import org.objectweb.asm.Type;

public class NeoForgeAnnotationScanner implements IAnnotationScanner {
   @Override
   public Set<Class<?>> findAnnotatedClasses(Class<? extends Annotation> annotationType, Set<String> basePackages, Predicate<Class<?>> classFilter) {
      Set<Class<?>> result = new LinkedHashSet<>();
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      Type asmType = Type.getType(annotationType);

      for (ModFileScanData scanData : ModList.get().getAllScanData()) {
         for (AnnotationData annotation : scanData.getAnnotations()) {
            ElementType target = annotation.targetType();
            if ((target == ElementType.TYPE || target == ElementType.METHOD) && annotation.annotationType().equals(asmType)) {
               String className = annotation.clazz().getClassName();
               if (this.matchesBasePackages(className, basePackages)) {
                  try {
                     Class<?> clazz = Class.forName(className, false, loader);
                     if (classFilter.test(clazz)) {
                        result.add(clazz);
                     }
                  } catch (ClassNotFoundException | NoClassDefFoundError var14) {
                  }
               }
            }
         }
      }

      return result;
   }

   private boolean matchesBasePackages(String className, Set<String> basePackages) {
      if (basePackages.isEmpty()) {
         return true;
      }

      for (String base : basePackages) {
         if (className.startsWith(base + ".") || className.equals(base)) {
            return true;
         }
      }

      return false;
   }
}
