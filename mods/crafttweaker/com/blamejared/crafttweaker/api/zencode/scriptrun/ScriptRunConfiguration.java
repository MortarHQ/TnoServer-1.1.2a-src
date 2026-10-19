package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import net.minecraft.resources.ResourceLocation;

public record ScriptRunConfiguration(IScriptLoader loader, IScriptLoadSource loadSource, ScriptRunConfiguration.RunKind runKind) {
   public ScriptRunConfiguration(String loader, ResourceLocation loadSource, ScriptRunConfiguration.RunKind runKind) {
      this(IScriptLoader.find(loader), IScriptLoadSource.find(loadSource), runKind);
   }

   public enum RunKind {
      SYNTAX_CHECK,
      FORMAT,
      EXECUTE,
      GAME_TEST;
   }
}
