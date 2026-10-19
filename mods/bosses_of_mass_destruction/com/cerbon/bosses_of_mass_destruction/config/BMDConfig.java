package com.cerbon.bosses_of_mass_destruction.config;

import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.config.mob.ObsidilithConfig;
import com.cerbon.bosses_of_mass_destruction.config.mob.VoidBlossomConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.TransitiveObject;

@Config(name = "bosses_of_mass_destruction")
public class BMDConfig implements ConfigData {
   @Category("Lich")
   @TransitiveObject
   public LichConfig lichConfig = new LichConfig();
   @Category("Obsidilith")
   @TransitiveObject
   public ObsidilithConfig obsidilithConfig = new ObsidilithConfig();
   @Category("Gauntlet")
   @TransitiveObject
   public GauntletConfig gauntletConfig = new GauntletConfig();
   @Category("VoidBlossom")
   @TransitiveObject
   public VoidBlossomConfig voidBlossomConfig = new VoidBlossomConfig();
   @Category("General")
   @TransitiveObject
   public GeneralConfig generalConfig = new GeneralConfig();

   public void postInit() {
      List<String> entitiesThatCountToSummonCounter = this.lichConfig.summonMechanic.entitiesThatCountToSummonCounter;
      if (entitiesThatCountToSummonCounter == null) {
         List<String> defaultEntities = Arrays.asList(
            "minecraft:zombie",
            "minecraft:skeleton",
            "minecraft:drowned",
            "minecraft:giant",
            "minecraft:husk",
            "minecraft:phantom",
            "minecraft:skeleton_horse",
            "minecraft:stray",
            "minecraft:wither",
            "minecraft:wither_skeleton",
            "minecraft:zoglin",
            "minecraft:zombie_horse",
            "minecraft:zombie_villager",
            "minecraft:zombified_piglin"
         );
         this.lichConfig.summonMechanic.entitiesThatCountToSummonCounter = new ArrayList<>(defaultEntities);
      } else {
         this.lichConfig.summonMechanic.entitiesThatCountToSummonCounter = new ArrayList<>(new HashSet<>(entitiesThatCountToSummonCounter));
      }
   }
}
