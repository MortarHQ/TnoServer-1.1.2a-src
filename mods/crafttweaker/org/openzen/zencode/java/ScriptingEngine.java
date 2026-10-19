package org.openzen.zencode.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.java.logger.ScriptingEngineStreamLogger;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.ModuleSpace;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.context.CompilingPackage;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.ISymbol;
import org.openzen.zenscript.javabytecode.JavaBytecodeRunUnit;
import org.openzen.zenscript.javabytecode.JavaCompiler;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaEnumMapper;
import org.openzen.zenscript.javashared.SimpleJavaCompileSpace;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.ModuleLoader;
import org.openzen.zenscript.parser.ParsedFile;
import org.openzen.zenscript.parser.ZippedPackage;
import org.openzen.zenscript.validator.Validator;

public class ScriptingEngine {
   public final ScriptingEngineLogger logger;
   private final ZSPackage root = ZSPackage.createRoot();
   private final ZSPackage stdlib = this.root.getOrCreatePackage("stdlib");
   public final GlobalTypeRegistry registry = new GlobalTypeRegistry(this.stdlib);
   private final ModuleSpace space;
   private final List<JavaNativeModule> nativeModules = new ArrayList<>();
   private final List<SemanticModule> compiledModules = new ArrayList<>();
   public boolean debug = false;

   public ScriptingEngine() {
      this(new ScriptingEngineStreamLogger());
   }

   public ScriptingEngine(ScriptingEngineLogger logger) {
      this(logger, ScriptingEngine.class::getResourceAsStream);
   }

   public ScriptingEngine(ScriptingEngineLogger logger, Function<String, InputStream> resourceGetter) {
      this.space = new ModuleSpace(this.registry, new ArrayList<>());
      this.logger = logger;

      try {
         ZippedPackage stdlibs = new ZippedPackage(resourceGetter.apply("/StdLibs.jar"));
         this.registerModule("stdlib", this.stdlib, stdlibs);
         this.registerModule("math", this.root.getOrCreatePackage("math"), stdlibs);
         this.registerModule("collections", this.root.getOrCreatePackage("collections"), stdlibs);
         this.registerModule("uuid", this.root.getOrCreatePackage("uuid"), stdlibs);
      } catch (CompileException | ParseException | IOException ex) {
         throw new RuntimeException(ex);
      }
   }

   public void registerModule(String name, ZSPackage zsPackage, ModuleLoader loader) throws CompileException, ParseException {
      SemanticModule stdlibModule = loader.loadModule(this.space, name, null, SemanticModule.NONE, FunctionParameter.NONE, zsPackage, this.logger);
      stdlibModule = Validator.validate(stdlibModule, this.logger);
      this.space.addModule(name, stdlibModule);
      this.registerCompiled(stdlibModule);
   }

   public JavaNativeModule createNativeModule(String name, String basePackage, JavaNativeModule... dependencies) {
      ZSPackage testPackage = new ZSPackage(this.space.rootPackage, name);
      return new JavaNativeModule(this.logger, testPackage, name, basePackage, this.registry, dependencies);
   }

   public JavaNativeModule createNativeModule(
      String name, String basePackage, JavaNativeModule[] dependencies, JavaNativeConverterBuilder nativeConverterBuilder
   ) {
      ZSPackage testPackage = new ZSPackage(this.space.rootPackage, name);
      return new JavaNativeModule(this.logger, testPackage, name, basePackage, this.registry, dependencies, nativeConverterBuilder);
   }

   public void registerNativeProvided(JavaNativeModule module) throws CompileException {
      SemanticModule semantic = Validator.validate(module.toSemantic(this.space), this.logger);
      if (semantic.isValid()) {
         this.space.addModule(module.getModule().name, semantic);
         this.nativeModules.add(module);

         for (Entry<String, ISymbol> globalEntry : module.getGlobals().entrySet()) {
            this.space.addGlobal(globalEntry.getKey(), globalEntry.getValue());
         }
      }
   }

   public SemanticModule createScriptedModule(String name, SourceFile[] sources, String... dependencies) throws ParseException {
      return this.createScriptedModule(name, sources, null, FunctionParameter.NONE, dependencies);
   }

   public SemanticModule createScriptedModule(
      String name, SourceFile[] sources, BracketExpressionParser bracketParser, FunctionParameter[] scriptParameters, String... dependencies
   ) throws ParseException {
      Module scriptModule = new Module(name);
      CompilingPackage scriptPackage = new CompilingPackage(new ZSPackage(this.space.rootPackage, name), scriptModule);
      ParsedFile[] files = new ParsedFile[sources.length];

      for (int i = 0; i < sources.length; i++) {
         this.logger.logSourceFile(sources[i]);
         files[i] = ParsedFile.parse(scriptPackage, bracketParser, sources[i]);
      }

      SemanticModule[] dependencyModules = new SemanticModule[dependencies.length + 1];
      dependencyModules[0] = this.space.getModule("stdlib");

      for (int i = 0; i < dependencies.length; i++) {
         dependencyModules[i + 1] = this.space.getModule(dependencies[i]);
      }

      SemanticModule scripts = ParsedFile.compileSyntaxToSemantic(dependencyModules, scriptPackage, files, this.space, scriptParameters, this.logger);
      return !scripts.isValid() ? scripts : Validator.validate(scripts.normalize(), this.logger);
   }

   public void registerCompiled(SemanticModule module) {
      this.compiledModules.add(module);
   }

   public void run() {
      this.run(Collections.emptyMap());
   }

   public void run(Map<FunctionParameter, Object> arguments) {
      this.run(arguments, this.getClass().getClassLoader());
   }

   public JavaBytecodeRunUnit createRunUnit() {
      SimpleJavaCompileSpace javaSpace = new SimpleJavaCompileSpace(this.registry);
      JavaEnumMapper enumMapper = new JavaEnumMapper();

      for (JavaNativeModule nativeModule : this.nativeModules) {
         JavaCompiledModule compiled = nativeModule.getCompiled();
         javaSpace.register(compiled);
         enumMapper.merge(compiled.getEnumMapper());
      }

      JavaCompiler compiler = new JavaCompiler(this.logger);
      JavaBytecodeRunUnit runUnit = new JavaBytecodeRunUnit(this.logger);

      for (SemanticModule compiled : this.compiledModules) {
         runUnit.add(compiler.compile(compiled.name, compiled, javaSpace, enumMapper));
      }

      if (this.debug) {
         runUnit.dump(new File("classes"));
      }

      return runUnit;
   }

   public void run(Map<FunctionParameter, Object> arguments, ClassLoader parentClassLoader) {
      JavaBytecodeRunUnit runUnit = this.createRunUnit();

      try {
         runUnit.run(arguments, parentClassLoader);
      } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
         this.logger.throwingErr(e.getCause().getMessage(), e.getCause());
      }
   }

   public List<JavaNativeModule> getNativeModules() {
      return Collections.unmodifiableList(this.nativeModules);
   }

   public ZSPackage getRoot() {
      return this.root;
   }
}
