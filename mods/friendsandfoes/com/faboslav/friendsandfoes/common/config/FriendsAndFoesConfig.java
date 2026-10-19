package com.faboslav.friendsandfoes.common.config;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.IntField;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import java.nio.file.Path;

public final class FriendsAndFoesConfig {
   public static ConfigClassHandler<FriendsAndFoesConfig> HANDLER = ConfigClassHandler.createBuilder(FriendsAndFoesConfig.class)
      .id(FriendsAndFoes.makeID("friendsandfoes"))
      .serializer(config -> GsonConfigSerializerBuilder.create(config).setPath(Path.of("config", "friendsandfoes.json")).build())
      .build();
   private static final String MOBS_CATEGORY = "mobs";
   private static final String BEEKEEPER_GROUP = "beekeeper";
   private static final String COPPER_GOLEM_GROUP = "copper_golem";
   private static final String CRAB_GROUP = "crab";
   private static final String GLARE_GROUP = "glare";
   private static final String ICEOLOGER_GROUP = "iceologer";
   private static final String ILLUSIONER_GROUP = "illusioner";
   private static final String MAULER_GROUP = "mauler";
   private static final String MOOBLOOM_GROUP = "moobloom";
   private static final String RASCAL_GROUP = "rascal";
   private static final String TUFF_GOLEM_GROUP = "tuff_golem";
   private static final String WILDFIRE_GROUP = "wildfire";
   private static final String ZOMBIE_HORSE_GROUP = "zombie_horse";
   @SerialEntry
   @AutoGen(category = "mobs", group = "beekeeper")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableBeekeeperVillagerProfession = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "beekeeper")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateBeekeeperAreaStructureInVillages = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "beekeeper")
   @IntSlider(min = 0, max = 100, step = 1)
   public int beekeeperAreaStructureWeight = 2;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableCopperGolem = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateCopperGolemWorkstationStructureInVillages = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @IntSlider(min = 0, max = 100, step = 1)
   public int copperGolemWorkstationStructureWeight = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateCopperGolemInAncientCity = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @IntSlider(min = 0, max = 100, step = 1)
   public int copperGolemAncientCityCenterWeight = 10;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableCopperGolemsInTrialChambers = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @IntSlider(min = 0, max = 100, step = 1)
   public int copperGolemInTrialChambersWeight = 4;
   @SerialEntry
   @AutoGen(category = "mobs", group = "copper_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableLightningRodOxidation = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableCrab = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableCrabSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @IntSlider(min = 0, max = 100, step = 1)
   public int crabSpawnWeight = 14;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @IntSlider(min = 0, max = 100, step = 1)
   public int crabSpawnMinGroupSize = 2;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @IntSlider(min = 0, max = 100, step = 1)
   public int crabSpawnMaxGroupSize = 4;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @IntField(min = 1, max = 100000)
   public int crabPotionOfReachingDuration = 3600;
   @SerialEntry
   @AutoGen(category = "mobs", group = "crab")
   @IntSlider(min = 0, max = 100, step = 1)
   public int reachingStatusEffectModifier = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "glare")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGlare = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "glare")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGlareSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "glare")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableGlareGriefing = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "glare")
   @IntSlider(min = 0, max = 100, step = 1)
   public int glareSpawnWeight = 4;
   @SerialEntry
   @AutoGen(category = "mobs", group = "glare")
   @IntSlider(min = 0, max = 100, step = 1)
   public int glareSpawnMinGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "glare")
   @IntSlider(min = 0, max = 100, step = 1)
   public int glareSpawnMaxGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "iceologer")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableIceologer = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "iceologer")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableIceologerSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "iceologer")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableIceologerInRaids = true;
   @SerialEntry
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   @AutoGen(category = "mobs", group = "iceologer")
   public boolean fleeAwayFromIronGolems = true;
   @SerialEntry
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   @AutoGen(category = "mobs", group = "iceologer")
   public boolean generateIceologerCabinStructure = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableIllusioner = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableIllusionerSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableIllusionerInRaids = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean replaceVanillaIllusioner = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateIllusionerShackStructure = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateIllusionerTrainingGroundsStructure = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @IntSlider(min = 0, max = 100, step = 1)
   public int illusionerMaxIllusionsCount = 9;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @IntSlider(min = 0, max = 1800, step = 1)
   public int illusionerIllusionLifetimeTicks = 600;
   @SerialEntry
   @AutoGen(category = "mobs", group = "illusioner")
   @IntSlider(min = 0, max = 180, step = 1)
   public int illusionerInvisibilityTicks = 60;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMauler = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMaulerSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMaulerSpawnInDesert = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerDesertSpawnWeight = 8;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerDesertSpawnMinGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerDesertSpawnMaxGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMaulerSpawnInBadlands = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerBadlandsSpawnWeight = 16;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerBadlandsSpawnMinGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerBadlandsSpawnMaxGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMaulerSpawnInSavanna = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerSavannaSpawnWeight = 32;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerSavannaSpawnMinGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "mauler")
   @IntSlider(min = 0, max = 100, step = 1)
   public int maulerSavannaSpawnMaxGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "moobloom")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMoobloom = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "moobloom")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableMoobloomSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "moobloom")
   @IntSlider(min = 0, max = 100, step = 1)
   public int moobloomSpawnWeight = 4;
   @SerialEntry
   @AutoGen(category = "mobs", group = "moobloom")
   @IntSlider(min = 0, max = 100, step = 1)
   public int moobloomSpawnMinGroupSize = 2;
   @SerialEntry
   @AutoGen(category = "mobs", group = "moobloom")
   @IntSlider(min = 0, max = 100, step = 1)
   public int moobloomSpawnMaxGroupSize = 4;
   @SerialEntry
   @AutoGen(category = "mobs", group = "rascal")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableRascal = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "rascal")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableRascalSpawn = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "rascal")
   @IntSlider(min = 0, max = 100, step = 1)
   public int rascalSpawnWeight = 4;
   @SerialEntry
   @AutoGen(category = "mobs", group = "rascal")
   @IntSlider(min = 0, max = 100, step = 1)
   public int rascalSpawnMinGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "rascal")
   @IntSlider(min = 0, max = 100, step = 1)
   public int rascalSpawnMaxGroupSize = 1;
   @SerialEntry
   @AutoGen(category = "mobs", group = "rascal")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean rascalGiveRewardInBundle = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "tuff_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableTuffGolem = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "tuff_golem")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateTuffGolemInStronghold = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "wildfire")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableWildfire = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "wildfire")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean generateCitadelStructure = true;
   @SerialEntry
   @AutoGen(category = "mobs", group = "zombie_horse")
   @Boolean(formatter = Formatter.YES_NO, colored = true)
   public boolean enableZombieHorseTrap = true;

   public void load() {
      HANDLER.load();
   }
}
