package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class ZenClassGatherer {
   private final Supplier<ZenClassGatherer.ZenCandidates> zenCandidates = Suppliers.memoize(
      () -> {
         CraftTweakerModList modList = new CraftTweakerModList();
         List<ZenClassGatherer.ZenClassData> classes = ClassUtil.findClassesWithAnnotation(ZenRegister.class, modList::add, this::checkModDependencies)
            .filter(Objects::nonNull)
            .flatMap(this::makeForClass)
            .toList();
         return new ZenClassGatherer.ZenCandidates(classes, modList::printToLog);
      }
   );

   void gatherCandidates() {
      this.zenCandidates.get();
   }

   void onCandidates(Consumer<ZenClassGatherer.ZenClassData> consumer) {
      this.zenCandidates.get().classCandidates().forEach(consumer);
   }

   void listProviders() {
      this.zenCandidates.get().modsProvidingLister().run();
   }

   private boolean checkModDependencies(Either<ZenRegister, Map<String, Object>> annotationData) {
      return ((List)annotationData.map(zenRegister -> List.of(zenRegister.modDeps()), map -> map.getOrDefault("modDeps", List.of())))
         .stream()
         .allMatch(Services.PLATFORM::isModLoaded);
   }

   private Stream<ZenClassGatherer.ZenClassData> makeForClass(Class<?> clazz) {
      return Arrays.stream(clazz.getDeclaredAnnotation(ZenRegister.class).loaders()).map(it -> new ZenClassGatherer.ZenClassData(clazz, it));
   }

   private record ZenCandidates(List<ZenClassGatherer.ZenClassData> classCandidates, Runnable modsProvidingLister) {
   }

   record ZenClassData(Class<?> clazz, String loader) {
   }
}
