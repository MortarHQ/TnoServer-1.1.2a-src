package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.mod.PlatformMod;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Either;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ClassUtil {
   public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(Class<T> annotationClass) {
      return findClassesWithAnnotation(annotationClass, it -> {});
   }

   public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
      Class<T> annotationClass, Consumer<PlatformMod> classProviderConsumer
   ) {
      return findClassesWithAnnotation(annotationClass, classProviderConsumer, annotationData -> true);
   }

   public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
      Class<T> annotationClass, Predicate<Either<T, Map<String, Object>>> annotationFilter
   ) {
      return findClassesWithAnnotation(annotationClass, it -> {}, annotationFilter);
   }

   public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
      Class<T> annotationClass, Consumer<PlatformMod> classProviderConsumer, Predicate<Either<T, Map<String, Object>>> annotationFilter
   ) {
      return Services.PLATFORM.findClassesWithAnnotation(annotationClass, classProviderConsumer, annotationFilter);
   }
}
