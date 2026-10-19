package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.script.scriptrun.natives.CtJavaNativeConverterBuilder;
import com.google.common.base.Suppliers;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.BracketExpressionParser;

abstract sealed class ScriptRunner implements IScriptRunner permits ExecutingScriptRunner, FormattingScriptRunner, SyntaxCheckScriptRunner, GameTestScriptRunner {
   private static final Supplier<Map<ScriptRunConfiguration.RunKind, ScriptRunner.ScriptRunCreator>> CREATORS = Suppliers.memoize(
      () -> Map.of(
         ScriptRunConfiguration.RunKind.SYNTAX_CHECK,
         SyntaxCheckScriptRunner::new,
         ScriptRunConfiguration.RunKind.FORMAT,
         FormattingScriptRunner::new,
         ScriptRunConfiguration.RunKind.EXECUTE,
         ExecutingScriptRunner::new,
         ScriptRunConfiguration.RunKind.GAME_TEST,
         GameTestScriptRunner::new
      )
   );
   private final IScriptRunInfo runInfo;
   private final List<SourceFile> sources;
   private final ScriptingEngine scriptingEngine;

   protected ScriptRunner(IScriptRunInfo runInfo, List<SourceFile> sources, ScriptingEngineLogger logger) {
      this.runInfo = runInfo;
      this.sources = List.copyOf(sources);
      this.scriptingEngine = new ScriptingEngine(logger, ScriptRunner.class::getResourceAsStream);
      this.scriptingEngine.debug = runInfo.dumpClasses();
   }

   public static ScriptRunner of(IScriptRunInfo info, List<SourceFile> sources, ScriptingEngineLogger logger) {
      ScriptRunConfiguration.RunKind kind = info.configuration().runKind();
      ScriptRunner.ScriptRunCreator creator = Objects.requireNonNull(CREATORS.get().get(kind), "Unknown kind specified");
      return creator.of(info, sources, logger);
   }

   @Override
   public final void run() throws Exception {
      BracketExpressionParser parser = this.initializeEngine();
      this.runScripts(parser);
   }

   private BracketExpressionParser initializeEngine() throws CompileException {
      ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
      CtJavaNativeConverterBuilder converterBuilder = new CtJavaNativeConverterBuilder(this.runInfo, registry.getZenClassRegistry());
      BracketExpressionParser parser = this.createParser(registry);
      Collection<DecoratedJavaNativeModule> modules = this.populateModules(converterBuilder, registry, parser);
      this.scriptingEngine.logger.info("Successfully initialized modules " + modules);
      return parser;
   }

   protected void runScripts(BracketExpressionParser parser) throws ParseException {
      SourceFile[] sources = this.sources.toArray(SourceFile[]::new);
      SemanticModule module = this.engine().createScriptedModule("scripts", sources, parser, FunctionParameter.NONE);
      if (!module.isValid()) {
         CraftTweakerCommon.logger().error("Scripts are invalid!");
      } else {
         this.executeRunAction(module);
      }
   }

   protected abstract void executeRunAction(SemanticModule var1);

   private Collection<DecoratedJavaNativeModule> populateModules(
      CtJavaNativeConverterBuilder builder, ICraftTweakerRegistry registry, BracketExpressionParser parser
   ) throws CompileException {
      return this.gatherModules(builder, registry, parser).stream().map(DecoratedJavaNativeModule::new).toList();
   }

   private Collection<JavaNativeModule> gatherModules(CtJavaNativeConverterBuilder builder, ICraftTweakerRegistry registry, BracketExpressionParser parser) throws CompileException {
      IScriptLoader loader = this.runInfo().loader();
      IScriptRunModuleConfigurator configurator = registry.getConfiguratorFor(loader);
      ScriptRunConfiguration configuration = this.runInfo().configuration();
      return configurator.populateModules(
         registry, configuration, (name, root, dependencies, config) -> this.createNativeModule(name, root, builder, parser, dependencies, config)
      );
   }

   private JavaNativeModule createNativeModule(
      String name,
      String rootPackage,
      CtJavaNativeConverterBuilder builder,
      BracketExpressionParser parser,
      List<JavaNativeModule> dependenciesList,
      Consumer<JavaNativeModule> configurator
   ) throws CompileException {
      JavaNativeModule[] dependencies = dependenciesList.toArray(JavaNativeModule[]::new);
      JavaNativeModule module = this.engine().createNativeModule(name, rootPackage, dependencies, builder);
      module.registerBEP(parser);
      configurator.accept(module);
      this.engine().registerNativeProvided(module);
      builder.reinitializeLazyHeaderValues();
      return module;
   }

   private BracketExpressionParser createParser(ICraftTweakerRegistry registry) {
      return new IgnorePrefixCasingBracketParser(this.getBracketsFor(registry));
   }

   private Map<String, BracketExpressionParser> getBracketsFor(ICraftTweakerRegistry registry) {
      return registry.getBracketHandlers(this.runInfo().loader(), null);
   }

   protected IScriptRunInfo runInfo() {
      return this.runInfo;
   }

   protected List<SourceFile> sources() {
      return this.sources;
   }

   protected ScriptingEngine engine() {
      return this.scriptingEngine;
   }

   @FunctionalInterface
   private interface ScriptRunCreator {
      ScriptRunner of(IScriptRunInfo var1, List<SourceFile> var2, ScriptingEngineLogger var3);
   }
}
