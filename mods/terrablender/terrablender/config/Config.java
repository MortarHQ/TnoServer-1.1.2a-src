package terrablender.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.UnmodifiableCommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.CommentedConfig.Entry;
import com.electronwill.nightconfig.core.UnmodifiableCommentedConfig.CommentNode;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.google.common.base.Predicates;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import terrablender.core.TerraBlender;

public abstract class Config implements UnmodifiableConfig, CommentedConfig {
   private CommentedConfig config;
   private final Path path;

   protected Config(Path path) {
      this(path, readToml(path));
   }

   protected Config(Path path, String toml) {
      this.parse(toml);
      this.path = path;
      this.load();
      this.write();
   }

   public <T> T add(String key, T defaultValue, String comment) {
      return this.add(key, defaultValue, comment, Predicates.alwaysTrue());
   }

   public <T> T add(String key, T defaultValue, String comment, Predicate<T> validator) {
      T value = (T)this.config.getOrElse(key, defaultValue);
      if (!validator.test(value)) {
         TerraBlender.LOGGER.warn("Invalid value {} for key {}. Reverting to default", value, key);
         value = defaultValue;
      }

      this.config.set(key, value);
      this.config.setComment(key, comment);
      return value;
   }

   public <T extends Number & Comparable<T>> T addNumber(String key, T defaultValue, T min, T max, String comment) {
      return this.add(key, defaultValue, comment, v -> v.compareTo(max) <= 0 && v.compareTo(min) >= 0);
   }

   public abstract void load();

   public void parse(String toml) {
      this.config = (CommentedConfig)TomlFormat.instance().createParser().parse(toml);
   }

   public void read() {
      this.parse(readToml(this.path));
   }

   public void write() {
      TomlFormat.instance().createWriter().write(this.config, this.path, WritingMode.REPLACE);
   }

   public String encode() {
      return TomlFormat.instance().createWriter().writeToString(this.config);
   }

   public Path getPath() {
      return this.path;
   }

   private static String readToml(Path path) {
      path.getParent().toFile().mkdirs();

      try {
         return Files.readString(path);
      } catch (Exception var2) {
         return "";
      }
   }

   public <T> T getRaw(List<String> path) {
      return (T)this.config.getRaw(path);
   }

   public Map<String, Object> valueMap() {
      return this.config.valueMap();
   }

   public boolean contains(List<String> path) {
      return this.config.contains(path);
   }

   public int size() {
      return this.config.size();
   }

   public boolean isEmpty() {
      return this.config.isEmpty();
   }

   @Override
   public boolean equals(Object obj) {
      return this.config.equals(obj);
   }

   @Override
   public int hashCode() {
      return this.config.hashCode();
   }

   public ConfigFormat<?> configFormat() {
      return this.config.configFormat();
   }

   public <T> T set(List<String> path, Object value) {
      return (T)this.config.set(path, value);
   }

   public boolean add(List<String> path, Object value) {
      return this.config.add(path, value);
   }

   public <T> T remove(List<String> path) {
      return (T)this.config.remove(path);
   }

   public void clear() {
      this.config.clear();
   }

   public String getComment(List<String> path) {
      return this.config.getComment(path);
   }

   public boolean containsComment(List<String> path) {
      return this.config.containsComment(path);
   }

   public String setComment(List<String> path, String comment) {
      return this.config.setComment(path, comment);
   }

   public String removeComment(List<String> path) {
      return this.config.removeComment(path);
   }

   public Map<String, String> commentMap() {
      return this.config.commentMap();
   }

   public Set<? extends Entry> entrySet() {
      return this.config.entrySet();
   }

   public void clearComments() {
      this.config.clearComments();
   }

   public void putAllComments(Map<String, CommentNode> comments) {
      this.config.putAllComments(comments);
   }

   public void putAllComments(UnmodifiableCommentedConfig commentedConfig) {
      this.config.putAllComments(commentedConfig);
   }

   public Map<String, CommentNode> getComments() {
      return this.config.getComments();
   }

   public CommentedConfig createSubConfig() {
      return this.config.createSubConfig();
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + ":" + this.config;
   }
}
