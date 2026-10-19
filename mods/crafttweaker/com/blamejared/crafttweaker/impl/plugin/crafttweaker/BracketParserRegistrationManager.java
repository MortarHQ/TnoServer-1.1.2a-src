package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.google.common.base.CaseFormat;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.openzen.zencode.java.ZenCodeType;

final class BracketParserRegistrationManager {
   private final Map<String, BracketParserRegistrationManager.BracketData> data = new HashMap<>();

   void addRegistrationCandidate(Class<?> clazz, String loader) {
      Arrays.stream(clazz.getMethods()).forEach(it -> this.tryAddMethod(this.data, loader, it));
   }

   void attemptRegistration(IBracketParserRegistrationHandler handler) {
      this.validateBrackets(this.data);
      this.register(this.data, handler);
      this.data.clear();
   }

   private void tryAddMethod(Map<String, BracketParserRegistrationManager.BracketData> data, String loader, Method method) {
      BracketParserRegistrationManager.BracketData bracketData = data.computeIfAbsent(loader, it -> new BracketParserRegistrationManager.BracketData());
      if (method.isAnnotationPresent(BracketResolver.class)) {
         this.addBracketResolver(bracketData, method);
      }

      if (method.isAnnotationPresent(BracketDumper.class)) {
         this.addBracketDumper(bracketData.brackets(), method);
      }

      if (method.isAnnotationPresent(BracketValidator.class)) {
         this.addBracketValidator(bracketData.brackets(), method);
      }
   }

   private void addBracketResolver(BracketParserRegistrationManager.BracketData data, Method method) {
      if (!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
         CraftTweakerCommon.logger().warn("Method '{}' is marked as a bracket resolver, but it is not public and static.", method.toString());
      } else {
         Class<?>[] parameters = method.getParameterTypes();
         String name = method.getAnnotation(BracketResolver.class).value();
         if (parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerCommon.logger()
               .error("Method '{}' is marked as a bracket resolver, but it does not have a String as it's only parameter.", method.toString());
         } else if (data.brackets().getOrDefault(name, BracketParserRegistrationManager.BracketHandle.EMPTY).resolver() != null) {
            Method current = data.brackets().get(name).resolver();
            CraftTweakerCommon.logger().error("Bracket resolver '{}' was already registered: current {}, attempt {}", name, current, method);
         } else {
            this.updateBracketHandle(data.brackets(), name, it -> it.resolver(method));
            Class<?> cls = method.getDeclaringClass();
            String clsName = cls.isAnnotationPresent(ZenCodeType.Name.class) ? cls.getAnnotation(ZenCodeType.Name.class).value() : cls.getCanonicalName();
            data.packageLookup().put(clsName.split("\\.", 2)[0], name);
         }
      }
   }

   private void addBracketDumper(Map<String, BracketParserRegistrationManager.BracketHandle> targetMap, Method method) {
      if (!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
         CraftTweakerCommon.logger().warn("Method '{}' is marked as a bracket dumper, but it is not public and static.", method.toString());
      } else if (method.getParameterCount() != 0) {
         CraftTweakerCommon.logger().warn("Method '{}' is marked as bracket dumper but does not have 0 parameters.", method.toString());
      } else if (Collection.class.isAssignableFrom(method.getReturnType())
         && method.getGenericReturnType() instanceof ParameterizedType param
         && param.getActualTypeArguments()[0] == String.class) {
         BracketDumper annotation = method.getAnnotation(BracketDumper.class);
         String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, annotation.value());
         String subCommand = annotation.subCommandName().isEmpty() ? null : annotation.subCommandName();
         String fileName = annotation.fileName().isEmpty() ? null : annotation.fileName();
         this.updateBracketHandle(targetMap, name, it -> it.dumper(subCommand, fileName, method));
      } else {
         CraftTweakerCommon.logger()
            .warn("Method '{}' is marked as bracket dumper but does not have 'Collection<String>' as return type.", method.toGenericString());
      }
   }

   private void addBracketValidator(Map<String, BracketParserRegistrationManager.BracketHandle> targetMap, Method method) {
      if (!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
         CraftTweakerCommon.logger().warn("Method '{}' is marked as a bracket validator, but it is not public and static.", method.toString());
      } else {
         BracketValidator validator = method.getAnnotation(BracketValidator.class);
         String name = validator.value();
         Class<?>[] parameters = method.getParameterTypes();
         if (parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerCommon.logger()
               .error("Method '{}' is marked as a bracket validator, but it does not have a String as it's only parameter.", method.toString());
         } else if (targetMap.getOrDefault(name, BracketParserRegistrationManager.BracketHandle.EMPTY).validator() != null) {
            Method current = targetMap.get(name).resolver();
            CraftTweakerCommon.logger().error("Bracket resolver '{}' was already registered: current {}, attempt {}", name, current, method);
         } else if (method.getReturnType() != boolean.class) {
            CraftTweakerCommon.logger().error("Method '{}' is marked as a bracket validator, so it must return a boolean", method);
         } else {
            this.updateBracketHandle(targetMap, name, it -> it.validator(method));
         }
      }
   }

   private void updateBracketHandle(
      Map<String, BracketParserRegistrationManager.BracketHandle> map, String target, UnaryOperator<BracketParserRegistrationManager.BracketHandle> operation
   ) {
      map.put(target, operation.apply(map.getOrDefault(target, BracketParserRegistrationManager.BracketHandle.EMPTY)));
   }

   private void validateBrackets(Map<String, BracketParserRegistrationManager.BracketData> data) {
      data.values()
         .stream()
         .map(BracketParserRegistrationManager.BracketData::brackets)
         .map(Map::entrySet)
         .flatMap(Collection::stream)
         .filter(it -> ((BracketParserRegistrationManager.BracketHandle)it.getValue()).validator() != null)
         .filter(it -> ((BracketParserRegistrationManager.BracketHandle)it.getValue()).resolver() == null)
         .map(Entry::getKey)
         .forEach(it -> CraftTweakerCommon.logger().info("BEP '{}' has a validator but no BEP method", it));
   }

   private void register(Map<String, BracketParserRegistrationManager.BracketData> data, IBracketParserRegistrationHandler handler) {
      data.entrySet()
         .stream()
         .flatMap(
            it -> it.getValue()
               .brackets()
               .entrySet()
               .stream()
               .map(x -> new BracketParserRegistrationManager.FlattenedData(it.getKey(), x.getKey(), x.getValue()))
         )
         .forEach(it -> this.register(it, handler));
   }

   private void register(BracketParserRegistrationManager.FlattenedData data, IBracketParserRegistrationHandler handler) {
      handler.registerParserFor(data.loader(), data.name(), data.handle().resolver(), data.handle().validator(), this.convert(data.handle().dumper()));
   }

   private IBracketParserRegistrationHandler.DumperData convert(BracketParserRegistrationManager.BracketDumpInfo info) {
      return info == null
         ? null
         : new IBracketParserRegistrationHandler.DumperData(
            info.subCommandName(), info.outputFileName(), () -> info.dumpMethods().stream().map(this::invoke).flatMap(Collection::stream)
         );
   }

   private Collection<String> invoke(MethodHandle handle) {
      try {
         return (Collection)handle.invokeExact();
      } catch (Throwable e) {
         throw new RuntimeException("Error executing bracket dumper", e);
      }
   }

   private record BracketData(Map<String, BracketParserRegistrationManager.BracketHandle> brackets, Multimap<String, String> packageLookup) {
      BracketData() {
         this(new HashMap<>(), HashMultimap.create());
      }
   }

   private record BracketDumpInfo(String subCommandName, String outputFileName, List<MethodHandle> dumpMethods) {
      BracketDumpInfo(String subCommandName, String outputFileName) {
         this(subCommandName, outputFileName, List.of());
      }

      BracketParserRegistrationManager.BracketDumpInfo method(Method dumper) {
         return new BracketParserRegistrationManager.BracketDumpInfo(
            this.subCommandName, this.outputFileName, Stream.concat(this.dumpMethods().stream(), Stream.of(this.lookup(dumper))).toList()
         );
      }

      private MethodHandle lookup(Method method) {
         try {
            return MethodHandles.publicLookup().unreflect(method);
         } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
         }
      }
   }

   private record BracketHandle(Method resolver, Method validator, BracketParserRegistrationManager.BracketDumpInfo dumper) {
      public static final BracketParserRegistrationManager.BracketHandle EMPTY = new BracketParserRegistrationManager.BracketHandle(null, null, null);

      BracketParserRegistrationManager.BracketHandle resolver(Method resolver) {
         return new BracketParserRegistrationManager.BracketHandle(resolver, this.validator, this.dumper);
      }

      BracketParserRegistrationManager.BracketHandle validator(Method validator) {
         return new BracketParserRegistrationManager.BracketHandle(this.resolver, validator, this.dumper);
      }

      BracketParserRegistrationManager.BracketHandle dumper(String subCommandName, String outputFileName, Method dumper) {
         return new BracketParserRegistrationManager.BracketHandle(
            this.resolver,
            this.validator,
            (this.dumper != null ? this.dumper : new BracketParserRegistrationManager.BracketDumpInfo(subCommandName, outputFileName)).method(dumper)
         );
      }
   }

   private record FlattenedData(String loader, String name, BracketParserRegistrationManager.BracketHandle handle) {
   }
}
