package com.b1n_ry.yigd.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class GraveConfig {
   public boolean enabled = true;
   public boolean storeItems = true;
   public boolean storeXp = true;
   @Comment("Inform player where the grave generated when respawning")
   public boolean informGraveLocation = true;
   @Comment("If true, you HAVE to have `requiredItemCount` number of `requiredItem` for a grave to generate. That many of that item will then be consumed")
   public boolean requireItem = false;
   public String requiredItem = "yigd:grave";
   public int requiredItemCount = 1;
   public boolean requireShovelToLoot = false;
   @CollapsibleObject
   public GraveConfig.RetrieveMethods retrieveMethods = new GraveConfig.RetrieveMethods();
   public boolean mergeStacksOnRetrieve = true;
   @EnumHandler(option = EnumDisplayOption.BUTTON)
   public DropType dropOnRetrieve = DropType.IN_INVENTORY;
   public boolean dropGraveBlock = false;
   public boolean generateEmptyGraves = false;
   @Comment("Allows everyone to bypass spawn protection for grave blocks")
   public boolean overrideSpawnProtection = true;
   @Comment("Which of the layout in the grave or in your inventory should be prioritized")
   @EnumHandler(option = EnumDisplayOption.BUTTON)
   public ClaimPriority claimPriority = ClaimPriority.GRAVE;
   @CollapsibleObject
   public GraveConfig.GraveRobbing graveRobbing = new GraveConfig.GraveRobbing();
   @CollapsibleObject
   public GraveConfig.GraveTimeout graveTimeout = new GraveConfig.GraveTimeout();
   @Comment("If false, layout prioritizing doesn't care if armor is cursed with binding")
   public boolean treatBindingCurse = true;
   public boolean generateGraveInVoid = true;
   @Comment("Minimum y-level a grave can spawn in a dimension")
   public List<MapEntryConfig.IntType> minimumGraveYLevel = new ArrayList<MapEntryConfig.IntType>() {
      {
         this.add(new MapEntryConfig.IntType("minecraft:overworld", -60));
         this.add(new MapEntryConfig.IntType("minecraft:the_nether", 3));
         this.add(new MapEntryConfig.IntType("minecraft:the_end", 3));
         this.add(new MapEntryConfig.IntType("misc", 3));
      }
   };
   public boolean generateOnlyWithinBorder = true;
   public List<String> ignoredDeathTypes = new ArrayList<>();
   @Comment("Allow players to unlock their graves through GUI")
   public boolean unlockable = true;
   @CollapsibleObject
   public GraveConfig.RandomSpawn randomSpawn = new GraveConfig.RandomSpawn();
   public boolean generateOnLastGroundPos = false;
   public boolean tryGenerateOnGround = false;
   @CollapsibleObject
   public GraveConfig.Range generationMaxDistance = new GraveConfig.Range();
   public boolean useSoftBlockWhitelist = false;
   public boolean useStrictBlockBlacklist = true;
   public boolean replaceOldWhenClaimed = true;
   public boolean dropItemsIfDestroyed = false;
   public boolean notifyOwnerIfDestroyed = true;
   @Comment(
      "If true, graves will persist when claiming them, and right clicking on them after that will let you know when and how they died. Can also then be mined"
   )
   @CollapsibleObject
   public GraveConfig.PersistentGraves persistentGraves = new GraveConfig.PersistentGraves();
   public List<String> dimensionBlacklist = new ArrayList<>();
   @CollapsibleObject
   public GraveConfig.BlockUnderGrave blockUnderGrave = new GraveConfig.BlockUnderGrave();
   @Comment("When people leave, should the game let everyone know where they have a grave?")
   public boolean sellOutOfflinePeople = false;
   @Comment("Max amount of backed up graves")
   public int maxBackupsPerPerson = 100;
   public boolean dropFromOldestWhenDeleted = true;

   public static class BlockUnderGrave {
      public boolean enabled = true;
      public List<MapEntryConfig.StringType> blockInDimensions = new ArrayList<MapEntryConfig.StringType>() {
         {
            this.add(new MapEntryConfig.StringType("minecraft:overworld", "minecraft:cobblestone"));
            this.add(new MapEntryConfig.StringType("minecraft:the_nether", "minecraft:soul_soil"));
            this.add(new MapEntryConfig.StringType("minecraft:the_end", "minecraft:end_stone"));
            this.add(new MapEntryConfig.StringType("misc", "minecraft:dirt"));
         }
      };
   }

   public static class GraveRobbing {
      public boolean enabled = true;
      public boolean killerSkipWaitTime = false;
      public int afterTime = 1;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public TimeUnit timeUnit = TimeUnit.HOURS;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public ClaimPriority robPriority = ClaimPriority.INVENTORY;
      public boolean notifyWhenRobbed = true;
      public boolean tellWhoRobbed = true;
   }

   public static class GraveTimeout {
      public boolean enabled = false;
      public int afterTime = 5;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public TimeUnit timeUnit = TimeUnit.HOURS;
      public boolean dropContentsOnTimeout = true;
   }

   public static class PersistentGraves {
      public boolean enabled = false;
      public boolean showDeathDay = true;
      public boolean showDeathIrlTime = true;
      public boolean useAmPm = true;
   }

   public static class RandomSpawn {
      public int percentSpawnChance = 0;
      public String spawnEntity = "minecraft:zombie";
      public String spawnNbt = "{ArmorItems:[{},{},{},{id:\"minecraft:player_head\",components:{\"minecraft:profile\":{name:\"${owner.name}\",id:${owner.uuid}}}}]}";
   }

   public static class Range {
      public int x = 5;
      public int y = 5;
      public int z = 5;
   }

   public static class RetrieveMethods {
      public boolean onClick = true;
      public boolean onBreak = false;
      public boolean onSneak = false;
      public boolean onStand = false;
   }
}
