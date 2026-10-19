package com.b1n_ry.yigd.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigData.ValidationException;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "yigd")
public class YigdConfig implements ConfigData {
   @CollapsibleObject
   public InventoryConfig inventoryConfig = new InventoryConfig();
   @CollapsibleObject
   public ExpConfig expConfig = new ExpConfig();
   @CollapsibleObject
   public GraveConfig graveConfig = new GraveConfig();
   @CollapsibleObject
   public RespawnConfig respawnConfig = new RespawnConfig();
   @CollapsibleObject
   public CompatConfig compatConfig = new CompatConfig();
   @CollapsibleObject
   public CommandConfig commandConfig = new CommandConfig();
   @Comment("Client only config")
   @CollapsibleObject
   public GraveRenderingConfig graveRendering = new GraveRenderingConfig();
   @Comment("Toggleable custom features (registries)")
   @CollapsibleObject
   public ExtraFeaturesConfig extraFeatures = new ExtraFeaturesConfig();

   public static YigdConfig getConfig() {
      return (YigdConfig)AutoConfig.getConfigHolder(YigdConfig.class).getConfig();
   }

   public void validatePostLoad() throws ValidationException {
      super.validatePostLoad();
   }
}
