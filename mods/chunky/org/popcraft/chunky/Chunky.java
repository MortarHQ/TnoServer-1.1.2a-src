package org.popcraft.chunky;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.popcraft.chunky.api.ChunkyAPI;
import org.popcraft.chunky.api.ChunkyAPIImpl;
import org.popcraft.chunky.command.CancelCommand;
import org.popcraft.chunky.command.CenterCommand;
import org.popcraft.chunky.command.ChunkyCommand;
import org.popcraft.chunky.command.ConfirmCommand;
import org.popcraft.chunky.command.ContinueCommand;
import org.popcraft.chunky.command.CornersCommand;
import org.popcraft.chunky.command.HelpCommand;
import org.popcraft.chunky.command.PatternCommand;
import org.popcraft.chunky.command.PauseCommand;
import org.popcraft.chunky.command.ProgressCommand;
import org.popcraft.chunky.command.QuietCommand;
import org.popcraft.chunky.command.RadiusCommand;
import org.popcraft.chunky.command.ReloadCommand;
import org.popcraft.chunky.command.SelectionCommand;
import org.popcraft.chunky.command.ShapeCommand;
import org.popcraft.chunky.command.SilentCommand;
import org.popcraft.chunky.command.SpawnCommand;
import org.popcraft.chunky.command.StartCommand;
import org.popcraft.chunky.command.TrimCommand;
import org.popcraft.chunky.command.WorldBorderCommand;
import org.popcraft.chunky.command.WorldCommand;
import org.popcraft.chunky.event.EventBus;
import org.popcraft.chunky.platform.Config;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.Server;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.PendingAction;
import org.popcraft.chunky.util.RegionCache;
import org.popcraft.chunky.util.TaskLoader;
import org.popcraft.chunky.util.TaskScheduler;
import org.popcraft.chunky.util.Translator;
import org.popcraft.chunky.util.Version;

public class Chunky {
   private final Server server;
   private final Config config;
   private final TaskLoader taskLoader;
   private final EventBus eventBus;
   private final Selection.Builder selection;
   private final TaskScheduler scheduler = new TaskScheduler();
   private final Map<String, GenerationTask> generationTasks = new ConcurrentHashMap<>();
   private final Map<String, TrimCommand.Task> trimTasks = new ConcurrentHashMap<>();
   private final Map<String, PendingAction> pendingActions = new HashMap<>();
   private final RegionCache regionCache = new RegionCache();
   private final double limit;
   private final Version version;
   private final Map<String, ChunkyCommand> commands;
   private final ChunkyAPI api;

   public Chunky(Server server, Config config) {
      this.server = server;
      this.config = config;
      this.taskLoader = new TaskLoader(this);
      this.eventBus = new EventBus();
      this.selection = Selection.builder(this, server.getWorlds().get(0));
      this.limit = this.loadLimit().orElse(Double.MAX_VALUE);
      this.version = this.loadVersion();
      this.commands = this.loadCommands();
      this.api = new ChunkyAPIImpl(this);
      ChunkyProvider.register(this);
   }

   public void disable() {
      this.taskLoader.saveTasks();
      this.getGenerationTasks().values().forEach(generationTask -> generationTask.stop(false));
      this.getScheduler().cancelTasks();
      ChunkyProvider.unregister();
   }

   private Optional<Double> loadLimit() {
      Path limitFile = this.config.getDirectory().resolve(".chunky.properties");

      try (InputStream input = Files.newInputStream(limitFile)) {
         Properties properties = new Properties();
         properties.load(input);
         return Input.tryDouble(properties.getProperty("radius-limit"));
      } catch (IOException e) {
         return Optional.empty();
      }
   }

   private Version loadVersion() {
      try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("version.properties")) {
         Properties properties = new Properties();
         properties.load(input);
         return new Version(properties.getProperty("version"));
      } catch (IOException e) {
         return Version.INVALID;
      }
   }

   private Map<String, ChunkyCommand> loadCommands() {
      Map<String, ChunkyCommand> commandMap = new HashMap<>();
      commandMap.put("cancel", new CancelCommand(this));
      commandMap.put("center", new CenterCommand(this));
      commandMap.put("confirm", new ConfirmCommand(this));
      commandMap.put("continue", new ContinueCommand(this));
      commandMap.put("corners", new CornersCommand(this));
      commandMap.put("help", new HelpCommand(this));
      commandMap.put("pattern", new PatternCommand(this));
      commandMap.put("pause", new PauseCommand(this));
      commandMap.put("progress", new ProgressCommand(this));
      commandMap.put("quiet", new QuietCommand(this));
      commandMap.put("radius", new RadiusCommand(this));
      commandMap.put("reload", new ReloadCommand(this));
      commandMap.put("selection", new SelectionCommand(this));
      commandMap.put("shape", new ShapeCommand(this));
      commandMap.put("silent", new SilentCommand(this));
      commandMap.put("spawn", new SpawnCommand(this));
      commandMap.put("start", new StartCommand(this));
      commandMap.put("trim", new TrimCommand(this));
      commandMap.put("worldborder", new WorldBorderCommand(this));
      commandMap.put("world", new WorldCommand(this));
      return commandMap;
   }

   public TaskScheduler getScheduler() {
      return this.scheduler;
   }

   public Server getServer() {
      return this.server;
   }

   public Config getConfig() {
      return this.config;
   }

   public TaskLoader getTaskLoader() {
      return this.taskLoader;
   }

   public EventBus getEventBus() {
      return this.eventBus;
   }

   public Map<String, GenerationTask> getGenerationTasks() {
      return this.generationTasks;
   }

   public Map<String, TrimCommand.Task> getTrimTasks() {
      return this.trimTasks;
   }

   public Map<String, ChunkyCommand> getCommands() {
      return this.commands;
   }

   public Selection.Builder getSelection() {
      return this.selection;
   }

   public Optional<Runnable> getPendingAction(Sender sender) {
      this.pendingActions.values().removeIf(PendingAction::hasExpired);
      PendingAction pendingAction = this.pendingActions.remove(sender.getName());
      return Optional.ofNullable(pendingAction).map(PendingAction::getAction);
   }

   public void setPendingAction(Sender sender, Runnable action) {
      this.pendingActions.put(sender.getName(), new PendingAction(action));
   }

   public void setLanguage(String language) {
      Translator.setLanguage(language);
   }

   public RegionCache getRegionCache() {
      return this.regionCache;
   }

   public double getLimit() {
      return this.limit;
   }

   public Version getVersion() {
      return this.version;
   }

   public ChunkyAPI getApi() {
      return this.api;
   }
}
