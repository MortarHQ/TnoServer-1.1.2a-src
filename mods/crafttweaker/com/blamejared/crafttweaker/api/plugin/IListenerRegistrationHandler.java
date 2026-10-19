package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import java.util.function.Consumer;

public interface IListenerRegistrationHandler {
   void onZenDataRegistrationCompletion(Runnable var1);

   void onCraftTweakerLoadCompletion(Runnable var1);

   void onExecuteRun(Consumer<ScriptRunConfiguration> var1);
}
