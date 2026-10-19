package com.blamejared.crafttweaker.impl.registry.natives;

import com.blamejared.crafttweaker.api.natives.IExecutableReferenceInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

final class ExecutableReferenceGroupInfo {
   private final List<ExecutableReferenceInfo> signatures = new ArrayList<>();

   IExecutableReferenceInfo getOrCreateFor(Class<?>[] parameterTypes, Consumer<ExecutableReferenceInfo.AnnotationCreator> consumer) {
      ExecutableReferenceInfo info = this.findSignature(parameterTypes)
         .orElseGet(() -> new ExecutableReferenceInfo(Arrays.asList(parameterTypes), new HashMap<>()));
      ExecutableReferenceInfo.AnnotationCreator creator = new ExecutableReferenceInfo.AnnotationCreator();
      consumer.accept(creator);
      ExecutableReferenceInfo newInfo = new ExecutableReferenceInfo(info.arguments(), creator.toMap(info.presentAnnotationTypes()));
      if (!this.signatures.contains(info)) {
         this.signatures.add(newInfo);
      } else {
         this.signatures.set(this.signatures.indexOf(info), newInfo);
      }

      return newInfo;
   }

   Optional<ExecutableReferenceInfo> findSignature(Class<?>... parameterTypes) {
      return this.signatures.stream().filter(it -> it.matchesParameters(parameterTypes)).findAny();
   }
}
