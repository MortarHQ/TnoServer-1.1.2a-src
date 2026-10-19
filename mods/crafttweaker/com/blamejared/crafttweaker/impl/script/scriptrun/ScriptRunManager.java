package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptDiscoveryConfiguration;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.helper.FileGathererHelper;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

public final class ScriptRunManager implements IScriptRunManager {
   private static final Supplier<ScriptRunManager> INSTANCE = Suppliers.memoize(ScriptRunManager::new);
   private static final Supplier<Comparator<IScriptFile>> FILE_COMPARATOR = Suppliers.memoize(
      () -> {
         Comparator<IScriptFile> noop = (a, b) -> 0;
         return CraftTweakerAPI.getRegistry()
            .getPreprocessors()
            .stream()
            .sorted(Comparator.comparingInt(IPreprocessor::priority).reversed())
            .reduce(noop, Comparator::thenComparing, Comparator::thenComparing)
            .thenComparing(IScriptFile::name);
      }
   );
   private static final boolean DEVELOPMENT = Services.PLATFORM.isDevelopmentEnvironment();
   private final Logger logger = CommonLoggers.zenCode();
   private final Map<IScriptLoader, RunInfoQueue> previousRunQueues = new HashMap<>();
   private final ThreadLocal<Integer> nestingLevel = ThreadLocal.withInitial(() -> 0);
   private RunInfo currentRunInfo = null;

   private ScriptRunManager() {
   }

   public static ScriptRunManager get() {
      return INSTANCE.get();
   }

   @Override
   public IScriptRun createScriptRun(ScriptRunConfiguration configuration) {
      return this.createScriptRun(CraftTweakerAPI.getScriptsDirectory(), configuration);
   }

   @Override
   public IScriptRun createScriptRun(Path root, ScriptRunConfiguration configuration) {
      return this.createScriptRun(root, new ScriptDiscoveryConfiguration(ScriptDiscoveryConfiguration.SuspiciousNamesBehavior.WARN), configuration);
   }

   @Override
   public IScriptRun createScriptRun(Path root, ScriptDiscoveryConfiguration discoveryConfiguration, ScriptRunConfiguration runConfiguration) {
      return this.createScriptRun(root, this.lookupScriptFiles(root, discoveryConfiguration), runConfiguration);
   }

   @Override
   public IScriptRun createScriptRun(Path root, List<Path> files, ScriptRunConfiguration configuration) {
      List<IPreprocessor> preprocessors = CraftTweakerAPI.getRegistry().getPreprocessors();
      RunInfo info = RunInfo.create(configuration);
      List<SourceFile> sources = files.stream()
         .map(it -> ScriptFile.of(this.logger, root, it, info, preprocessors))
         .sorted(FILE_COMPARATOR.get())
         .map(ScriptFile::toSourceFile)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .toList();
      return this.createScriptRun(sources, info);
   }

   @Override
   public IScriptRun createScriptRun(List<SourceFile> sources, ScriptRunConfiguration configuration) {
      return this.createScriptRun(sources, RunInfo.create(configuration));
   }

   @Override
   public IScriptRunInfo currentRunInfo() {
      return Objects.requireNonNull(this.currentRunInfo, "Unable to get current run info outside a script run");
   }

   @Override
   public void applyAction(IAction action) {
      if (this.currentRunInfo == null) {
         this.applyActionOutsideRun(action);
      } else if (action instanceof IRuntimeAction || this.currentRunInfo.isFirstRun()) {
         this.applyActionInRun(action);
      }
   }

   private IScriptRun createScriptRun(List<SourceFile> sources, RunInfo info) {
      this.previousRunQueues.computeIfAbsent(info.loader(), it -> this.makeRunInfoQueue());
      return new ScriptRun(
         sources,
         info,
         this.logger,
         this::updateCurrentRunInfo,
         loader -> this.previousRunQueues.get(loader).isFirstRun(),
         loader -> this.previousRunQueues.get(loader).undoActions()
      );
   }

   private RunInfoQueue makeRunInfoQueue() {
      return new RunInfoQueue(() -> this.logger.info("Undoing previous actions"), this::determineLoggerForAction);
   }

   private List<Path> lookupScriptFiles(Path root, ScriptDiscoveryConfiguration discoveryConfiguration) {
      try {
         FileSystem fs = root.getFileSystem();
         PathMatcher correctMatcher = fs.getPathMatcher("glob:**.zs");
         PathMatcher suspiciousMatcher = fs.getPathMatcher("glob:**.zs.txt");
         PathMatcher combinedMatcher = it -> correctMatcher.matches(it) || suspiciousMatcher.matches(it);
         List<Path> files = SuspiciousAwarePathList.of(suspiciousMatcher, this.makeSuspiciousConsumer(root, discoveryConfiguration));
         Files.walkFileTree(root, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, FileGathererHelper.of(combinedMatcher, files::add));
         discoveryConfiguration.retainer().retain(root, files);
         return files;
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private Consumer<Path> makeSuspiciousConsumer(Path root, ScriptDiscoveryConfiguration configuration) {
      return switch (configuration.suspiciousNamesBehavior()) {
         case IGNORE -> it -> {};
         case WARN -> it -> this.logger
            .warn(
               "Identified file with suspicious name '{}': ignoring; if this is supposed to be a script, please correct the name",
               root.toAbsolutePath().relativize(it.toAbsolutePath())
            );
      };
   }

   private void updateCurrentRunInfo(RunInfo current) {
      if (current != null) {
         this.attemptRunStart(current);
      } else {
         this.attemptRunStop();
      }
   }

   private void attemptRunStart(RunInfo current) {
      if (this.currentRunInfo != null) {
         throw new IllegalStateException("Unable to run a script run while another is in progress");
      }

      this.currentRunInfo = current;
   }

   private void attemptRunStop() {
      if (this.currentRunInfo == null) {
         throw new IllegalStateException("Unable to terminate a script run that never started");
      }

      this.previousRunQueues.get(this.currentRunInfo.loader()).offer(this.currentRunInfo);
      this.currentRunInfo = null;
   }

   private void applyActionOutsideRun(IAction action) {
      throw new UnsupportedOperationException("Unable to apply an action outside of a script run");
   }

   private void applyActionInRun(IAction action) {
      RunInfo info = Objects.requireNonNull(this.currentRunInfo);
      Logger logger = this.determineLoggerForAction(action);

      try {
         if (!action.shouldApplyOn(info.loadSource(), logger)) {
            return;
         }

         if (!action.validate(logger)) {
            info.enqueueAction(action, false);
            return;
         }

         int nestLevel = this.nestingLevel.get();

         try {
            this.nestingLevel.set(nestLevel + 1);
            logger.info(this.makeNestedDescription(action, nestLevel));
            action.apply();
            info.enqueueAction(action, true);
         } finally {
            this.nestingLevel.set(nestLevel);
         }
      } catch (Exception e) {
         logger.error("Unable to run action due to an error", e);
      }
   }

   private Logger determineLoggerForAction(IAction action) {
      Logger logger = CraftTweakerAPI.getLogger(this.checkSystemName(action));
      if (DEVELOPMENT && logger != action.logger()) {
         String message = "Action %s attempted to hijack the logger instance: expected %s, got %s".formatted(action, logger, action.logger());
         throw new IllegalStateException(message);
      } else {
         return logger;
      }
   }

   private String checkSystemName(IAction action) {
      String systemName = action.systemName();
      if (systemName != null && !systemName.isEmpty()) {
         return systemName;
      } else {
         throw new IllegalStateException("Action " + action + " does not specify a valid system name");
      }
   }

   private String makeNestedDescription(IAction action, int nestLevel) {
      return "-".repeat(nestLevel) + (nestLevel > 0 ? " " : "") + this.makeDescription(action);
   }

   private String makeDescription(IAction action) {
      String description = action.describe();
      return description != null && !description.isEmpty()
         ? description
         : "Applying unknown action '" + action + "': tell the mod author to properly implement describe";
   }
}
