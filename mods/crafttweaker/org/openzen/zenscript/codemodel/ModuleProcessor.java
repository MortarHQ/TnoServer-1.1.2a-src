package org.openzen.zenscript.codemodel;

public interface ModuleProcessor {
   ScriptBlock process(ScriptBlock var1);

   void process(HighLevelDefinition var1);
}
