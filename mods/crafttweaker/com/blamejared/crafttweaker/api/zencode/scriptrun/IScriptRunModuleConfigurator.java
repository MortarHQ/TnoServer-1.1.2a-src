package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;

@FunctionalInterface
public interface IScriptRunModuleConfigurator {
   static IScriptRunModuleConfigurator createDefault(String basePackage) {
      return Services.BRIDGE.defaultScriptRunModuleConfigurator(basePackage);
   }

   Collection<JavaNativeModule> populateModules(ICraftTweakerRegistry var1, ScriptRunConfiguration var2, IScriptRunModuleConfigurator.ModuleCreator var3) throws CompileException;

   @FunctionalInterface
   interface ModuleCreator {
      JavaNativeModule createNativeModule(String var1, String var2, List<JavaNativeModule> var3, Consumer<JavaNativeModule> var4) throws CompileException;
   }
}
