package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;

public final class DefaultScriptRunModuleConfigurator implements IScriptRunModuleConfigurator {
   private final String basePackage;

   private DefaultScriptRunModuleConfigurator(String basePackage) {
      this.basePackage = basePackage;
   }

   public static IScriptRunModuleConfigurator of(String basePackage) {
      return new DefaultScriptRunModuleConfigurator(basePackage);
   }

   @Override
   public Collection<JavaNativeModule> populateModules(
      ICraftTweakerRegistry registry, ScriptRunConfiguration configuration, IScriptRunModuleConfigurator.ModuleCreator creator
   ) throws CompileException {
      IScriptLoader loader = configuration.loader();
      IZenClassRegistry zenClassRegistry = registry.getZenClassRegistry();
      JavaNativeModule baseModule = this.createModule(creator, zenClassRegistry, loader, this.basePackage, this.basePackage);
      List<JavaNativeModule> otherModules = this.createOtherModules(creator, zenClassRegistry, loader, baseModule);
      JavaNativeModule expansions = this.createExpansionModule(creator, zenClassRegistry, loader, baseModule, otherModules);
      return Stream.concat(otherModules.stream(), Stream.of(baseModule, expansions)).toList();
   }

   private List<JavaNativeModule> createOtherModules(
      IScriptRunModuleConfigurator.ModuleCreator creator, IZenClassRegistry registry, IScriptLoader loader, JavaNativeModule baseModule
   ) throws CompileException {
      AtomicReference<CompileException> exception = new AtomicReference<>(null);
      List<JavaNativeModule> otherModules = registry.getRootPackages(loader).stream().filter(it -> !it.equals(this.basePackage)).map(it -> {
         try {
            return this.createModule(creator, registry, loader, it, it, baseModule);
         } catch (CompileException e) {
            if (!exception.compareAndSet(null, e)) {
               exception.get().addSuppressed(e);
            }

            return null;
         }
      }).filter(Objects::nonNull).toList();
      if (exception.get() != null) {
         throw (CompileException)exception.get();
      } else {
         return otherModules;
      }
   }

   private JavaNativeModule createExpansionModule(
      IScriptRunModuleConfigurator.ModuleCreator creator,
      IZenClassRegistry registry,
      IScriptLoader loader,
      JavaNativeModule baseModule,
      List<JavaNativeModule> otherDependencies
   ) throws CompileException {
      return this.createModule(
         creator,
         "expansions",
         "",
         Stream.concat(Stream.of(baseModule), otherDependencies.stream()).toArray(JavaNativeModule[]::new),
         List.of(),
         registry.getClassData(loader).expansions().values()
      );
   }

   private JavaNativeModule createModule(
      IScriptRunModuleConfigurator.ModuleCreator creator,
      IZenClassRegistry registry,
      IScriptLoader loader,
      String name,
      String rootPackage,
      JavaNativeModule... dependencies
   ) throws CompileException {
      return this.createModule(
         creator, name, rootPackage, dependencies, registry.getGlobalsInPackage(loader, rootPackage), registry.getClassesInPackage(loader, rootPackage)
      );
   }

   private JavaNativeModule createModule(
      IScriptRunModuleConfigurator.ModuleCreator creator,
      String name,
      String rootPackage,
      JavaNativeModule[] dependencies,
      Collection<Class<?>> globals,
      Collection<Class<?>> classes
   ) throws CompileException {
      return creator.createNativeModule(name, rootPackage, Arrays.asList(dependencies), it -> {
         globals.forEach(it::addGlobals);
         classes.forEach(it::addClass);
      });
   }
}
