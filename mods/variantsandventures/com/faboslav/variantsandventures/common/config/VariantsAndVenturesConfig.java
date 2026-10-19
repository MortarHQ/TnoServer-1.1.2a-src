package com.faboslav.variantsandventures.common.config;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.DoubleSlider;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import java.nio.file.Path;

public final class VariantsAndVenturesConfig {
   public static ConfigClassHandler<VariantsAndVenturesConfig> HANDLER = ConfigClassHandler.createBuilder(VariantsAndVenturesConfig.class)
      .id(VariantsAndVentures.makeID("variantsandventures"))
      .serializer(config -> GsonConfigSerializerBuilder.create(config).setPath(Path.of("config", "variantsandventures.json")).build())
      .build();
   public static final int MIN_PERCENT_VALUE = 0;
   public static final int MAX_PERCENT_VALUE = 100;
   public static final int PERCENT_STEP = 1;
   public static final String PERCENT_FORMAT = "%.0f%%";
   private static final String MOD_MOBS_CATEGORY = "mod_mobs";
   private static final String GELID_GROUP = "gelid";
   private static final String MURK_GROUP = "murk";
   private static final String THICKET_GROUP = "thicket";
   private static final String VERDANT_GROUP = "verdant";
   private static final String VANILLA_MOBS_CATEGORY = "vanilla_mobs";
   private static final String STRAY_GROUP = "stray";
   private static final String HUSK_GROUP = "husk";
   private static final String BOGGED_GROUP = "bogged";
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGelid = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGelidSpawns = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double gelidSpawnChance = 80.0;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @IntSlider(min = -256, max = 256, step = 1)
   public int gelidMinimumYLevel = -64;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGelidSpawners = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double gelidSpawnerChance = 80.0;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "gelid")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGelidSpawnersInTrialChambers = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "murk")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMurk = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "murk")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMurkSpawns = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "murk")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMurkSpawnersInTrialChambers = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableThicket = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableThicketSpawns = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double thicketSpawnChance = 80.0;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @IntSlider(min = -256, max = 256, step = 1)
   public int thicketMinimumYLevel = -64;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableThicketSpawners = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double thicketSpawnerChance = 80.0;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "thicket")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableThicketSpawnersInTrialChambers = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableVerdant = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableVerdantSpawns = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double verdantSpawnChance = 80.0;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @IntSlider(min = -256, max = 256, step = 1)
   public int verdantMinimumYLevel = -64;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableVerdantSpawners = true;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double verdantSpawnerChance = 80.0;
   @SerialEntry
   @AutoGen(category = "mod_mobs", group = "verdant")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableVerdantSpawnersInTrialChambers = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "stray")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableBetterStraySpawns = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "stray")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double straySpawnChance = 80.0;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "stray")
   @IntSlider(min = -256, max = 256, step = 1)
   public int strayMinimumYLevel = -64;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "stray")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableStraySpawners = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "stray")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double straySpawnerChance = 80.0;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "husk")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableBetterHuskSpawns = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "husk")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double huskSpawnChance = 80.0;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "husk")
   @IntSlider(min = -256, max = 256, step = 1)
   public int huskMinimumYLevel = -64;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "husk")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableHuskSpawners = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "husk")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double huskSpawnerChance = 80.0;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "bogged")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableBetterBoggedSpawns = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "bogged")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double boggedSpawnChance = 80.0;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "bogged")
   @IntSlider(min = -256, max = 256, step = 1)
   public int boggedMinimumYLevel = -64;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "bogged")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableBoggedSpawners = true;
   @SerialEntry
   @AutoGen(category = "vanilla_mobs", group = "bogged")
   @DoubleSlider(min = 0.0, max = 100.0, step = 1.0, format = "%.0f%%")
   public double boggedSpawnerChance = 80.0;

   public void load() {
      HANDLER.load();
   }
}
