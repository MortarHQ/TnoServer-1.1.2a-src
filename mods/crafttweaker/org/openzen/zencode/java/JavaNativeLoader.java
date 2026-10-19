package org.openzen.zencode.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.definition.ZSPackage;

public class JavaNativeLoader {
   private final Class<?>[] classes;
   private final Class<?>[] globals;
   private final Map<String, JavaNativeLoader.LoadingModule> modulesByName = new HashMap<>();
   private final IZSLogger logger;
   private boolean loaded = false;

   public JavaNativeLoader(Class<?>[] classes, Class<?>[] globals, IZSLogger logger) {
      this.classes = classes;
      this.globals = globals;
      this.logger = logger;
   }

   public JavaNativeLoader.LoadingModule addModule(ZSPackage pkg, String name, String basePackage, String... dependencies) {
      if (this.loaded) {
         throw new IllegalStateException("Already loaded modules!");
      }

      if (this.modulesByName.containsKey(name)) {
         throw new IllegalArgumentException("Module already exists: " + name);
      }

      JavaNativeLoader.LoadingModule module = new JavaNativeLoader.LoadingModule(pkg, name, basePackage, dependencies);
      this.modulesByName.put(name, module);
      return module;
   }

   public ScriptingEngine load() throws CompileException {
      this.loaded = true;
      this.sortClasses();
      ScriptingEngine engine = new ScriptingEngine();

      for (JavaNativeLoader.LoadingModule module : this.modulesByName.values()) {
         this.load(engine, module);
      }

      return engine;
   }

   private void sortClasses() throws CompileException {
      Map<String, JavaNativeLoader.LoadingModule> modulesByPackage = new HashMap<>();

      for (JavaNativeLoader.LoadingModule module : this.modulesByName.values()) {
         if (modulesByPackage.containsKey(module.basePackage)) {
            throw CompileException.internalError("Two modules have the same base package: " + module.basePackage);
         }

         modulesByPackage.put(module.basePackage, module);
      }

      for (Class<?> cls : this.classes) {
         JavaNativeLoader.LoadingModule module = this.findModuleByPackage(modulesByPackage, this.getPackageName(cls));
         if (module == null) {
            this.logger.warning("Module not found for class " + cls.getName());
         } else {
            module.classes.add(cls);
         }
      }

      for (Class<?> cls : this.globals) {
         JavaNativeLoader.LoadingModule module = this.findModuleByPackage(modulesByPackage, this.getPackageName(cls));
         if (module == null) {
            this.logger.warning("Module not found for class " + cls.getName());
         } else {
            module.globals.add(cls);
         }
      }
   }

   private String getPackageName(Class<?> cls) {
      String name = cls.getName();
      return name.substring(0, name.lastIndexOf(46));
   }

   private JavaNativeLoader.LoadingModule findModuleByPackage(Map<String, JavaNativeLoader.LoadingModule> modulesByPackage, String packageName) {
      if (modulesByPackage.containsKey(packageName)) {
         return modulesByPackage.get(packageName);
      }

      int index = packageName.lastIndexOf(46);
      return index < 0 ? null : this.findModuleByPackage(modulesByPackage, packageName.substring(0, index));
   }

   private JavaNativeModule load(ScriptingEngine engine, JavaNativeLoader.LoadingModule module) throws CompileException {
      if (module.resolved != null) {
         return module.resolved;
      }

      JavaNativeModule[] dependencies = new JavaNativeModule[module.dependencies.length];

      for (int i = 0; i < dependencies.length; i++) {
         JavaNativeLoader.LoadingModule dependency = this.modulesByName.get(module.dependencies[i]);
         if (dependency == null) {
            throw CompileException.internalError("Module dependency for " + module.name + " missing: " + module.dependencies[i]);
         }

         dependencies[i] = this.load(engine, dependency);
      }

      module.resolved = new JavaNativeModule(this.logger, module.pkg, module.name, module.basePackage, engine.registry, dependencies);

      for (Class<?> cls : module.classes) {
         module.resolved.addClass(cls);
      }

      for (Class<?> cls : module.globals) {
         module.resolved.addGlobals(cls);
      }

      for (Consumer<JavaNativeModule> resolved : module.whenResolved) {
         resolved.accept(module.resolved);
      }

      engine.registerNativeProvided(module.resolved);
      return module.resolved;
   }

   public static class LoadingModule {
      private final ZSPackage pkg;
      private final String name;
      private final String basePackage;
      private final String[] dependencies;
      private final List<Consumer<JavaNativeModule>> whenResolved = new ArrayList<>();
      private final List<Class<?>> classes = new ArrayList<>();
      private final List<Class<?>> globals = new ArrayList<>();
      private JavaNativeModule resolved;

      private LoadingModule(ZSPackage pkg, String name, String basePackage, String[] dependencies) {
         this.pkg = pkg;
         this.name = name;
         this.basePackage = basePackage;
         this.dependencies = dependencies;
      }

      public void whenLoaded(Consumer<JavaNativeModule> consumer) {
         this.whenResolved.add(consumer);
      }
   }
}
