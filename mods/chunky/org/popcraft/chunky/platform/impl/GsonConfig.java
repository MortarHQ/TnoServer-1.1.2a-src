package org.popcraft.chunky.platform.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import org.popcraft.chunky.platform.Config;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Translator;

public class GsonConfig implements Config {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Path savePath;
   private GsonConfig.ConfigModel configModel = new GsonConfig.ConfigModel();

   public GsonConfig(Path savePath) {
      this.savePath = savePath;
      if (Files.exists(this.savePath)) {
         this.reload();
      } else {
         this.saveConfig();
      }

      Translator.setLanguage(this.getLanguage());
   }

   @Override
   public Path getDirectory() {
      return this.savePath.getParent();
   }

   @Override
   public int getVersion() {
      return Optional.ofNullable(this.configModel.version).orElse(0);
   }

   @Override
   public String getLanguage() {
      return Optional.ofNullable(this.configModel.language).map(Input::checkLanguage).orElse("en");
   }

   @Override
   public boolean getContinueOnRestart() {
      return Optional.ofNullable(this.configModel.continueOnRestart).orElse(false);
   }

   @Override
   public boolean isForceLoadExistingChunks() {
      return Optional.ofNullable(this.configModel.forceLoadExistingChunks).orElse(false);
   }

   @Override
   public boolean isSilent() {
      return Optional.ofNullable(this.configModel.silent).orElse(false);
   }

   @Override
   public void setSilent(boolean silent) {
      this.configModel.silent = silent;
   }

   @Override
   public int getUpdateInterval() {
      return Optional.ofNullable(this.configModel.updateInterval).orElse(1);
   }

   @Override
   public void setUpdateInterval(int updateInterval) {
      this.configModel.updateInterval = updateInterval;
   }

   @Override
   public void reload() {
      try (Reader reader = Files.newBufferedReader(this.savePath)) {
         this.configModel = (GsonConfig.ConfigModel)GSON.fromJson(reader, GsonConfig.ConfigModel.class);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void saveConfig() {
      try {
         Files.createDirectories(this.savePath.getParent());
      } catch (IOException e) {
         e.printStackTrace();
      }

      try (Writer writer = Files.newBufferedWriter(this.savePath)) {
         GSON.toJson(this.configModel, writer);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private static class ConfigModel {
      private Integer version = 2;
      private String language = "en";
      private Boolean continueOnRestart = false;
      private Boolean forceLoadExistingChunks = false;
      private Boolean silent = false;
      private Integer updateInterval = 1;
      private Map<String, GsonConfig.TaskModel> tasks;

      public Integer getVersion() {
         return this.version;
      }

      public void setVersion(Integer version) {
         this.version = version;
      }

      public String getLanguage() {
         return this.language;
      }

      public void setLanguage(String language) {
         this.language = language;
      }

      public Boolean getContinueOnRestart() {
         return this.continueOnRestart;
      }

      public void setContinueOnRestart(Boolean continueOnRestart) {
         this.continueOnRestart = continueOnRestart;
      }

      public Boolean getForceLoadExistingChunks() {
         return this.forceLoadExistingChunks;
      }

      public void setForceLoadExistingChunks(Boolean forceLoadExistingChunks) {
         this.forceLoadExistingChunks = forceLoadExistingChunks;
      }

      public Map<String, GsonConfig.TaskModel> getTasks() {
         return this.tasks;
      }

      public void setTasks(Map<String, GsonConfig.TaskModel> tasks) {
         this.tasks = tasks;
      }

      public boolean isSilent() {
         return this.silent;
      }

      public void setSilent(boolean silent) {
         this.silent = silent;
      }

      public int getUpdateInterval() {
         return this.updateInterval;
      }

      public void setUpdateInterval(int updateInterval) {
         this.updateInterval = updateInterval;
      }
   }

   private static class TaskModel {
      private Boolean cancelled;
      private Double radius;
      private Double radiusZ;
      private Double centerX;
      private Double centerZ;
      private String iterator;
      private String shape;
      private Long count;
      private Long time;

      public Boolean getCancelled() {
         return this.cancelled;
      }

      public void setCancelled(Boolean cancelled) {
         this.cancelled = cancelled;
      }

      public Double getRadius() {
         return this.radius;
      }

      public void setRadius(Double radius) {
         this.radius = radius;
      }

      public Double getRadiusZ() {
         return this.radiusZ;
      }

      public void setRadiusZ(Double radiusZ) {
         this.radiusZ = radiusZ;
      }

      public Double getCenterX() {
         return this.centerX;
      }

      public void setCenterX(Double centerX) {
         this.centerX = centerX;
      }

      public Double getCenterZ() {
         return this.centerZ;
      }

      public void setCenterZ(Double centerZ) {
         this.centerZ = centerZ;
      }

      public String getIterator() {
         return this.iterator;
      }

      public void setIterator(String iterator) {
         this.iterator = iterator;
      }

      public String getShape() {
         return this.shape;
      }

      public void setShape(String shape) {
         this.shape = shape;
      }

      public Long getCount() {
         return this.count;
      }

      public void setCount(Long count) {
         this.count = count;
      }

      public Long getTime() {
         return this.time;
      }

      public void setTime(Long time) {
         this.time = time;
      }
   }
}
