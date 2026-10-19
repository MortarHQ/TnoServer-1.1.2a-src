package org.openzen.zencode.java.module;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import org.openzen.zencode.java.module.converters.JavaNativeConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.ModuleSpace;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.ISymbol;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.parser.BracketExpressionParser;

public class JavaNativeModule {
   private final IZSLogger logger;
   private final JavaNativePackageInfo packageInfo;
   private final JavaNativeTypeConversionContext typeConversionContext;
   private final JavaNativeConverter nativeConverter;

   public JavaNativeModule(IZSLogger logger, ZSPackage pkg, String name, String basePackage, GlobalTypeRegistry registry, JavaNativeModule[] dependencies) {
      this(logger, pkg, name, basePackage, registry, dependencies, new JavaNativeConverterBuilder());
   }

   public JavaNativeModule(
      IZSLogger logger,
      ZSPackage pkg,
      String name,
      String basePackage,
      GlobalTypeRegistry registry,
      JavaNativeModule[] dependencies,
      JavaNativeConverterBuilder nativeConverterBuilder
   ) {
      this.packageInfo = new JavaNativePackageInfo(pkg, basePackage, new Module(name));
      this.logger = logger;
      this.typeConversionContext = new JavaNativeTypeConversionContext(this.packageInfo, dependencies, registry);
      this.nativeConverter = nativeConverterBuilder.build(this.packageInfo, logger, this.typeConversionContext, this);
   }

   public SemanticModule toSemantic(ModuleSpace space) {
      return new SemanticModule(
         this.packageInfo.getModule(),
         SemanticModule.NONE,
         FunctionParameter.NONE,
         SemanticModule.State.NORMALIZED,
         space.rootPackage,
         this.packageInfo.getPkg(),
         this.typeConversionContext.packageDefinitions,
         Collections.emptyList(),
         space.registry,
         space.collectExpansions(),
         space.getAnnotations(),
         this.logger
      );
   }

   public HighLevelDefinition addClass(Class<?> cls) {
      return this.nativeConverter.addClass(cls);
   }

   public void addGlobals(Class<?> cls) {
      HighLevelDefinition definition = this.addClass(cls);
      this.nativeConverter.globalConverter.addGlobal(cls, definition);
   }

   public FunctionalMemberRef loadStaticMethod(Method method) {
      HighLevelDefinition definition = this.addClass(method.getDeclaringClass());
      return this.nativeConverter.memberConverter.loadStaticMethod(method, definition);
   }

   public void registerBEP(BracketExpressionParser bep) {
      this.nativeConverter.registerBEP(bep);
   }

   public JavaCompiledModule getCompiled() {
      return this.typeConversionContext.compiled;
   }

   public Map<String, ISymbol> getGlobals() {
      return this.typeConversionContext.globals;
   }

   public Module getModule() {
      return this.packageInfo.getModule();
   }

   JavaNativeTypeConversionContext getTypeConversionContext() {
      return this.typeConversionContext;
   }
}
