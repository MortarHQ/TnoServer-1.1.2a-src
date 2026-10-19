package com.chaosthedude.explorerscompass.config;

import com.chaosthedude.explorerscompass.client.OverlaySide;
import java.util.ArrayList;
import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ConfigHandler {
   private static final Builder GENERAL_BUILDER = new Builder();
   private static final Builder CLIENT_BUILDER = new Builder();
   public static final ConfigHandler.General GENERAL = new ConfigHandler.General(GENERAL_BUILDER);
   public static final ConfigHandler.Client CLIENT = new ConfigHandler.Client(CLIENT_BUILDER);
   public static final ModConfigSpec GENERAL_SPEC = GENERAL_BUILDER.build();
   public static final ModConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

   public static class Client {
      public final BooleanValue displayWithChatOpen;
      public final BooleanValue translateStructureNames;
      public final EnumValue<OverlaySide> overlaySide;
      public final IntValue overlayLineOffset;

      Client(Builder builder) {
         builder.push("Client");
         String desc = "Displays Explorer's Compass information on the HUD even while chat is open.";
         this.displayWithChatOpen = builder.comment(desc).define("displayWithChatOpen", true);
         desc = "Attempts to translate structure names before fixing the unlocalized names. Translations may not be available for all structures.";
         this.translateStructureNames = builder.comment(desc).define("translateStructureNames", true);
         desc = "The line offset for information rendered on the HUD.";
         this.overlayLineOffset = builder.comment(desc).defineInRange("overlayLineOffset", 1, 0, 50);
         desc = "The side for information rendered on the HUD. Ex: LEFT, RIGHT";
         this.overlaySide = builder.comment(desc).defineEnum("overlaySide", OverlaySide.LEFT);
         builder.pop();
      }
   }

   public static class General {
      public final BooleanValue allowTeleport;
      public final IntValue maxNextSearches;
      public final BooleanValue displayCoordinates;
      public final IntValue maxRadius;
      public final ConfigValue<List<String>> structureBlacklist;
      public final IntValue defaultXpLevels;
      public final ConfigValue<List<String>> xpLevelOverrides;
      public final IntValue maxSamples;
      public final IntValue compassDurability;

      General(Builder builder) {
         builder.push("General");
         String desc = "Allows a player to teleport to a located structure when in creative mode, opped, or in cheat mode.";
         this.allowTeleport = builder.comment(desc).define("allowTeleport", true);
         desc = "The maximum number of times a player can search for the next instance of a located structure, excluding already-found locations. Set to 0 to disable searching for additional structure instances and make the compass always locate the nearest instance.";
         this.maxNextSearches = builder.comment(desc).defineInRange("maxNextSearches", 100, 0, 10000);
         desc = "Allows players to view the precise coordinates and distance of a located structure on the HUD, rather than relying on the direction the compass is pointing.";
         this.displayCoordinates = builder.comment(desc).define("displayCoordinates", true);
         desc = "The maximum radius that will be searched for a structure. Raising this value will increase search accuracy but will potentially make the process more resource intensive.";
         this.maxRadius = builder.comment(desc).defineInRange("maxRadius", 10000, 0, 1000000);
         desc = "A list of structures that the compass will not display in the GUI and will not be able to search for. Wildcard character * can be used to match any number of characters, and ? can be used to match one character. Ex: [\"minecraft:buried_treasure\", \"minecraft:end*\", \"minecraft:*village*\"]";
         this.structureBlacklist = builder.comment(desc).define("structureBlacklist", new ArrayList());
         desc = "The default number of XP levels consumed when searching for a structure. Individual structures can be configured via xpLevelOverrides.";
         this.defaultXpLevels = builder.comment(desc).defineInRange("defaultXpLevels", 0, 0, 3);
         desc = "A list of structure-specific XP level costs that override defaultXpLevels, specified as comma-separated \"structure_id,num_levels\" pairs. Structures not listed here use defaultXpLevels. Max of 3 levels. The wildcard character * can be used to match any number of characters, and ? can be used to match one character. Ex: [\"minecraft:buried_treasure,3\", \"minecraft:end*,2\", \"minecraft:*village*,1\"]";
         this.xpLevelOverrides = builder.comment(desc).define("xpLevelOverrides", new ArrayList());
         desc = "The maximum number of samples to be taken when searching for a structure.";
         this.maxSamples = builder.comment(desc).defineInRange("maxSamples", 100000, 0, 100000000);
         desc = "The number of successful structure searches before the compass breaks and must be repaired. Set to 0 to disable durability.";
         this.compassDurability = builder.comment(desc).defineInRange("compassDurability", 0, 0, 10000);
         builder.pop();
      }
   }
}
