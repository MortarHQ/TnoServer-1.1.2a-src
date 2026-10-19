package com.tensuraskilldisabled.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.tensuraskilldisabled.TensuraSkillDisabled;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

public final class SkillBlacklistConfig {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final String CONFIG_FILE_NAME = "skill_blacklist.json";
   private static SkillBlacklistConfig instance;
   private final Path configPath;
   private final Set<String> disabledSkills = new LinkedHashSet<>();
   private SkillBlacklistConfig.ConfigData data = new SkillBlacklistConfig.ConfigData();

   private SkillBlacklistConfig() {
      this.configPath = FMLPaths.CONFIGDIR.get().resolve("skill_blacklist.json");
      this.load();
   }

   public static SkillBlacklistConfig getInstance() {
      if (instance == null) {
         instance = new SkillBlacklistConfig();
      }

      return instance;
   }

   public synchronized void load() {
      if (!Files.exists(this.configPath)) {
         this.save();
      } else {
         try (Reader reader = Files.newBufferedReader(this.configPath)) {
            SkillBlacklistConfig.ConfigData loaded = (SkillBlacklistConfig.ConfigData)GSON.fromJson(reader, SkillBlacklistConfig.ConfigData.class);
            this.data = loaded == null ? new SkillBlacklistConfig.ConfigData() : loaded;
            this.disabledSkills.clear();
            this.disabledSkills.addAll(this.data.disabledSkills);
            TensuraSkillDisabled.LOGGER.info("Loaded {} disabled Tensura skills", this.disabledSkills.size());
         } catch (IOException | JsonSyntaxException e) {
            TensuraSkillDisabled.LOGGER.error("Failed to load {}", this.configPath, e);
            this.data = new SkillBlacklistConfig.ConfigData();
            this.disabledSkills.clear();
            this.save();
         }
      }
   }

   public synchronized void save() {
      try {
         Files.createDirectories(this.configPath.getParent());
         this.data.disabledSkills.clear();
         this.data.disabledSkills.addAll(this.disabledSkills);

         try (Writer writer = Files.newBufferedWriter(this.configPath)) {
            GSON.toJson(this.data, writer);
         }
      } catch (IOException e) {
         TensuraSkillDisabled.LOGGER.error("Failed to save {}", this.configPath, e);
      }
   }

   public synchronized boolean isDisabled(ResourceLocation skillId) {
      return skillId != null && this.disabledSkills.contains(skillId.toString());
   }

   public synchronized boolean isDisabled(String skillId) {
      return this.disabledSkills.contains(skillId);
   }

   public synchronized boolean add(String skillId) {
      boolean changed = this.disabledSkills.add(skillId);
      if (changed) {
         this.save();
      }

      return changed;
   }

   public synchronized boolean remove(String skillId) {
      boolean changed = this.disabledSkills.remove(skillId);
      if (changed) {
         this.save();
      }

      return changed;
   }

   public synchronized Set<String> getDisabledSkills() {
      return Collections.unmodifiableSet(new LinkedHashSet<>(this.disabledSkills));
   }

   public static final class ConfigData {
      public String version = "1.0";
      public String description = "Global Tensura skill blacklist. Use namespace:path ids, for example tensura:gluttony.";
      public List<String> disabledSkills = new ArrayList<>();
   }
}
