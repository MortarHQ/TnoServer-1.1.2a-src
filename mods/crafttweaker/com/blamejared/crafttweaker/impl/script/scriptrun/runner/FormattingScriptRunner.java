package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.formatter.FileFormatter;
import org.openzen.zenscript.formatter.ScriptFormattingSettings;

final class FormattingScriptRunner extends ScriptRunner {
   FormattingScriptRunner(IScriptRunInfo runInfo, List<SourceFile> sources, ScriptingEngineLogger logger) {
      super(runInfo, sources, logger);
   }

   @Override
   protected void executeRunAction(SemanticModule module) {
      this.format(module);
      if (this.runInfo().dumpClasses()) {
         Path classes = PathUtil.findFromGameDirectory("classes");
         this.engine().createRunUnit().dump(classes.toFile());
      }
   }

   private void format(SemanticModule module) {
      List<HighLevelDefinition> definitions = module.definitions.getAll();
      ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
      FileFormatter formatter = new FileFormatter(builder.build());
      Path formattedScriptsDirectory = PathUtil.findFromGameDirectory("scriptsFormatted");
      module.scripts.forEach(block -> {
         String format = formatter.format(module.rootPackage, block, definitions);
         Path file = formattedScriptsDirectory.resolve(block.file.getFilename());
         Path parent = file.getParent();
         if (!Files.isDirectory(parent)) {
            try {
               Files.createDirectories(parent);
            } catch (IOException e) {
               this.engine().logger.error("Unable to create directory " + file.getParent() + ", aborting: " + e.getMessage());
               return;
            }
         }

         try {
            Files.write(file, Arrays.asList(format.split(System.lineSeparator())));
         } catch (IOException e) {
            this.engine().logger.error("Unable to write formatted files: " + e.getMessage());
         }
      });
   }
}
