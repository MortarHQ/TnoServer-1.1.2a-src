package com.corosus.coroutil.loader.neoforge;

import com.corosus.coroutil.util.CULog;
import com.corosus.modconfig.ConfigComment;
import com.corosus.modconfig.ConfigParams;
import com.corosus.modconfig.CoroConfigRegistry;
import com.corosus.modconfig.IConfigCategory;
import com.corosus.modconfig.ModConfigData;
import java.lang.reflect.Field;
import java.util.HashMap;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class ModConfigDataNeoForge extends ModConfigData {
   public HashMap<String, ConfigValue<String>> valsStringConfig = new HashMap<>();
   public HashMap<String, ConfigValue<Integer>> valsIntegerConfig = new HashMap<>();
   public HashMap<String, ConfigValue<Double>> valsDoubleConfig = new HashMap<>();
   public HashMap<String, ConfigValue<Boolean>> valsBooleanConfig = new HashMap<>();

   public ModConfigDataNeoForge(String savePath, String parStr, Class parClass, IConfigCategory parConfig) {
      super(savePath, parStr, parClass, parConfig);
   }

   @Override
   public String getConfigString(String fieldName) {
      return (String)this.valsStringConfig.get(fieldName).get();
   }

   @Override
   public Integer getConfigInteger(String fieldName) {
      return (Integer)this.valsIntegerConfig.get(fieldName).get();
   }

   @Override
   public Double getConfigDouble(String fieldName) {
      return (Double)this.valsDoubleConfig.get(fieldName).get();
   }

   @Override
   public Boolean getConfigBoolean(String fieldName) {
      return (Boolean)this.valsBooleanConfig.get(fieldName).get();
   }

   @Override
   public <T> void setConfig(String fieldName, T obj) {
      if (obj instanceof String) {
         this.valsStringConfig.get(fieldName).set((String)obj);
         this.valsStringConfig.get(fieldName).save();
      } else if (obj instanceof Integer) {
         this.valsIntegerConfig.get(fieldName).set((Integer)obj);
         this.valsIntegerConfig.get(fieldName).save();
      } else if (obj instanceof Double) {
         this.valsDoubleConfig.get(fieldName).set((Double)obj);
         this.valsDoubleConfig.get(fieldName).save();
      } else if (obj instanceof Boolean) {
         this.valsBooleanConfig.get(fieldName).set((Boolean)obj);
         this.valsBooleanConfig.get(fieldName).save();
      }
   }

   @Override
   public void writeConfigFile(boolean resetConfig) {
      Builder BUILDER = new Builder();
      BUILDER.comment("General mod settings").push("general");
      Field[] fields = this.configClass.getDeclaredFields();

      for (int i = 0; i < fields.length; i++) {
         Field field = fields[i];
         String name = field.getName();
         this.addToConfig(BUILDER, field, name);
      }

      CULog.dbg("writeConfigFile invoked for " + this.configID + ", resetConfig: " + resetConfig);
      BUILDER.pop();
      ModConfigSpec CONFIG = BUILDER.build();
      ConfigModNeoForge.container.registerConfig(Type.COMMON, CONFIG, this.saveFilePath + ".toml");
   }

   private void addToConfig(Builder builder, Field field, String name) {
      String comment = "-";
      double min = -Double.MAX_VALUE;
      double max = Double.MAX_VALUE;
      ConfigComment anno_comment = field.getAnnotation(ConfigComment.class);
      if (anno_comment != null) {
         comment = anno_comment.value()[0];
      }

      ConfigParams anno_params = field.getAnnotation(ConfigParams.class);
      if (anno_params != null) {
         comment = anno_params.comment();
         min = anno_params.min();
         max = anno_params.max();
      }

      Object obj = CoroConfigRegistry.instance().getField(this.configID, name);
      if (obj instanceof String) {
         this.valsStringConfig.put(name, builder.comment(comment).define(name, (String)obj));
      } else if (obj instanceof Integer) {
         this.valsIntegerConfig.put(name, builder.comment(comment).defineInRange(name, (Integer)obj, (int)min, (int)max));
      } else if (obj instanceof Double) {
         this.valsDoubleConfig.put(name, builder.comment(comment).defineInRange(name, (Double)obj, min, max));
      } else if (obj instanceof Boolean) {
         this.valsBooleanConfig.put(name, builder.comment(comment).define(name, (Boolean)obj));
      }

      this.setFieldBasedOnType(name, obj);
   }
}
