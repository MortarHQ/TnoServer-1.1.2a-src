package dev.ftb.mods.ftbchunks;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ProtectionPolicy;
import dev.ftb.mods.ftbchunks.data.AllyMode;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftbchunks.data.ForceLoadMode;
import dev.ftb.mods.ftbchunks.data.PartyLimitMode;
import dev.ftb.mods.ftbchunks.data.PvPMode;
import dev.ftb.mods.ftbchunks.integration.PermissionsHelper;
import dev.ftb.mods.ftbchunks.util.DimensionFilter;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.DoubleValue;
import dev.ftb.mods.ftblibrary.snbt.config.EnumValue;
import dev.ftb.mods.ftblibrary.snbt.config.IntValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringListValue;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.property.PrivacyMode;
import java.util.Collections;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface FTBChunksWorldConfig {
   String KEY = "ftbchunks-world";
   SNBTConfig CONFIG = (SNBTConfig)SNBTConfig.create("ftbchunks-world")
      .comment(
         new String[]{
            "Server-specific configuration for FTB Chunks",
            "Modpack defaults should be defined in <instance>/config/ftbchunks-world.snbt",
            "  (may be overwritten on modpack update)",
            "Server admins may locally override this by copying into <instance>/world/serverconfig/ftbchunks-world.snbt",
            "  (will NOT be overwritten on modpack update)"
         }
      );
   BooleanValue DISABLE_PROTECTION = (BooleanValue)CONFIG.addBoolean("disable_protection", false)
      .comment(new String[]{"Disables all land protection. Useful for private servers where everyone is trusted and claims are only used for force-loading"});
   EnumValue<AllyMode> ALLY_MODE = (EnumValue<AllyMode>)CONFIG.addEnum("ally_mode", AllyMode.NAME_MAP)
      .comment(new String[]{"Forced modes won't let players change their ally settings"});
   EnumValue<PvPMode> PVP_MODE = (EnumValue<PvPMode>)CONFIG.addEnum("pvp_mode", PvPMode.NAME_MAP)
      .comment(
         new String[]{
            "Should PvP combat be allowed in claimed chunks? Default is ALWAYS; NEVER prevents it in all claimed chunks; PER_TEAM allows teams to decide if PvP is allowed in their claims"
         }
      );
   BooleanValue NO_WILDERNESS = (BooleanValue)CONFIG.addBoolean("no_wilderness", false)
      .comment(new String[]{"Requires you to claim chunks in order to edit and interact with blocks"});
   StringListValue NO_WILDERNESS_DIMENSIONS = (StringListValue)CONFIG.addStringList("no_wilderness_dimensions", Collections.emptyList())
      .comment(
         new String[]{
            "Dimension ID's where the no_wilderness rule is enforced - building is only allowed in claimed chunks. If this is non-empty, it overrides the 'no_wilderness' setting."
         }
      );
   BooleanValue FORCE_DISABLE_MINIMAP = (BooleanValue)CONFIG.addBoolean("force_disable_minimap", false)
      .comment(new String[]{"Minimap for clients connecting to this server will be disabled"});
   IntValue LONG_RANGE_TRACKER_INTERVAL = (IntValue)CONFIG.addInt("long_range_tracker_interval", 20, 0, Integer.MAX_VALUE)
      .comment(
         new String[]{
            "Interval in ticks to send updates to clients with long-range player tracking data.",
            "Lower values mean more frequent updates but more server load and network traffic; be careful with this, especially on busy servers.",
            "Setting this to 0 disables long-range tracking."
         }
      );
   BooleanValue PROTECT_UNKNOWN_EXPLOSIONS = (BooleanValue)CONFIG.addBoolean("protect_unknown_explosions", true)
      .comment(
         new String[]{
            "When true, standard FTB Chunk explosion protection is applied in protected chunks when the source of the explosion cannot be determined",
            "(Ghast fireballs are a common case - vanilla supplies a null entity source)"
         }
      );
   BooleanValue REQUIRE_GAME_STAGE = (BooleanValue)CONFIG.addBoolean("require_game_stage", false)
      .comment(
         new String[]{
            "If true, the player must have the 'ftbchunks_mapping' Game stage to be able to use the map and minimap.\nRequires KubeJS and/or Gamestages to be installed."
         }
      );
   BooleanValue LOCATION_MODE_OVERRIDE = (BooleanValue)CONFIG.addBoolean("location_mode_override", false)
      .comment(new String[]{"If true, \"Location Visibility\" team settings are ignored, and all players can see each other anywhere on the map."});
   BooleanValue PISTON_PROTECTION = (BooleanValue)CONFIG.addBoolean("piston_protection", true)
      .comment(
         new String[]{
            "If true, pistons are prevented from pushing/pulling blocks across claims owned by different teams (unless the target claim has public 'edit block' permissions defined). If 'disable_protection' is set to true, this setting is ignored."
         }
      );
   BooleanValue FLOWING_FLUID_PROTECTION = (BooleanValue)CONFIG.addBoolean("flowing_fluid_protection", false)
      .comment(
         new String[]{
            "If true, fluids are prevented from flowing horizontally across claims owned by different teams (unless the target claim has public 'edit block' permissions defined).",
            "False by default; enabling this can potentially lead to server performance issues, since fluid flow ticks are checked very frequently, especially for large bodies of flowing fluids",
            "If 'disable_protection' is set to true, this setting is ignored."
         }
      );
   BooleanValue FIRE_SPREAD_PROTECTION = (BooleanValue)CONFIG.addBoolean("fire_spread_protection", false)
      .comment(
         new String[]{
            "If true, fire is prevented from spreading across claims owned by different teams",
            "(unless the target claim has public 'edit block' permissions defined).",
            "False by default; enabling this can potentially lead to server performance issues,",
            "especially when many blocks are on fire",
            "If 'disable_protection' is set to true, this setting is ignored."
         }
      );
   SNBTConfig FAKE_PLAYERS = CONFIG.addGroup("fake_players");
   EnumValue<ProtectionPolicy> ALLOW_FAKE_PLAYERS = (EnumValue<ProtectionPolicy>)FAKE_PLAYERS.addEnum(
         "fake_players", NameMap.of(ProtectionPolicy.CHECK, ProtectionPolicy.values()).create()
      )
      .comment(new String[]{"Override to disable/enable fake players like miners and auto-clickers globally.", "Default will check this setting for each team"});
   IntValue MAX_PREVENTED_LOG_AGE = (IntValue)FAKE_PLAYERS.addInt("max_prevented_log_age", 7, 1, Integer.MAX_VALUE)
      .comment(new String[]{"Maximum time in days to keep logs of prevented fakeplayer access to a team's claims."});
   SNBTConfig CLAIMING = CONFIG.addGroup("claiming");
   IntValue MAX_CLAIMED_CHUNKS = (IntValue)CLAIMING.addInt("max_claimed_chunks", 500)
      .comment(new String[]{"Max claimed chunks.", "You can override this with FTB Ranks 'ftbchunks.max_claimed' permission"});
   StringListValue CLAIM_DIMENSION_BLACKLIST = (StringListValue)CLAIMING.addStringList("claim_dimension_blacklist", Collections.emptyList())
      .comment(
         new String[]{
            "Dimension ID's where chunks may not be claimed. Add \"minecraft:the_end\" to this list if you want to disable chunk claiming in The End, or \"othermod:*\" to disable chunk claiming in *all* dimensions added by \"othermod\""
         }
      );
   StringListValue CLAIM_DIMENSION_WHITELIST = (StringListValue)CLAIMING.addStringList("claim_dimension_whitelist", Collections.emptyList())
      .comment(
         new String[]{
            "Dimension ID's where chunks may be claimed. If non-empty, chunks may be claimed *only* in these dimensions (and the dimension is not in \"claim_dimension_blacklist\"). Same syntax as for \"claim_dimension_blacklist\"."
         }
      );
   DoubleValue MAX_IDLE_DAYS_BEFORE_UNCLAIM = (DoubleValue)CLAIMING.addDouble("max_idle_days_before_unclaim", 0.0, 0.0, 3650.0)
      .comment(
         new String[]{
            "Maximum time (in real-world days) where if no player in a team logs in, the team automatically loses their claims.",
            "Prevents chunks being claimed indefinitely by teams who no longer play.",
            "Default of 0 means no automatic loss of claims."
         }
      );
   IntValue HARD_TEAM_CLAIM_LIMIT = (IntValue)CLAIMING.addInt("hard_team_claim_limit", 0, 0, Integer.MAX_VALUE)
      .comment(new String[]{"Hard limit for the number of chunks a team can claim, regardless of how many members. Default of 0 means no hard limit."});
   EnumValue<PartyLimitMode> PARTY_LIMIT_MODE = (EnumValue<PartyLimitMode>)CLAIMING.addEnum("party_limit_mode", PartyLimitMode.NAME_MAP)
      .comment(
         new String[]{
            "Method by which party claim & force-load limits are calculated.",
            "LARGEST: use the limits of the member with the largest limits",
            "SUM: add up all the members' limits",
            "OWNER: use the party owner's limits only",
            "AVERAGE: use the average of all members' limits."
         }
      );
   SNBTConfig FORCE_LOADING = CONFIG.addGroup("force_loading");
   EnumValue<ForceLoadMode> FORCE_LOAD_MODE = (EnumValue<ForceLoadMode>)FORCE_LOADING.addEnum("force_load_mode", ForceLoadMode.NAME_MAP)
      .comment(
         new String[]{
            "Control how force-loaded chunks work.",
            "NEVER: only allow chunk force-loading if the owning team has at least one online player.",
            "ALWAYS: always allow force-loading, even if no players are online.",
            "DEFAULT: allow force-loading IF the team has at least one player with the 'ftbchunks.chunk_load_offline' FTB Ranks permission."
         }
      );
   IntValue MAX_FORCE_LOADED_CHUNKS = (IntValue)FORCE_LOADING.addInt("max_force_loaded_chunks", 25)
      .comment(new String[]{"Max force loaded chunks.", "You can override this with FTB Ranks 'ftbchunks.max_force_loaded' permission"});
   DoubleValue MAX_IDLE_DAYS_BEFORE_UNFORCE = (DoubleValue)FORCE_LOADING.addDouble("max_idle_days_before_unforce", 0.0, 0.0, 3650.0)
      .comment(
         new String[]{
            "Maximum time (in real-world days) where if no player in a team logs in, any forceloaded chunks owned by the team are no longer forceloaded.",
            "Prevents chunks being forceloaded indefinitely by teams who no longer play.",
            "Default of 0 means no automatic loss of forceloading."
         }
      );
   IntValue HARD_TEAM_FORCE_LIMIT = (IntValue)FORCE_LOADING.addInt("hard_team_force_limit", 0, 0, Integer.MAX_VALUE)
      .comment(new String[]{"Hard limit for the number of chunks a team can force-load, regardless of how many members. Default of 0 means no hard limit."});
   SNBTConfig WAYPOINT_SHARING = CONFIG.addGroup("waypoint_sharing");
   BooleanValue WAYPOINT_SHARING_SERVER = (BooleanValue)WAYPOINT_SHARING.addBoolean("waypoint_sharing_server", true)
      .comment(new String[]{"Allow players to share waypoints with the entire server."});
   BooleanValue WAYPOINT_SHARING_PARTY = (BooleanValue)WAYPOINT_SHARING.addBoolean("waypoint_sharing_party", true)
      .comment(new String[]{"Allow players to share waypoints with their party."});
   BooleanValue WAYPOINT_SHARING_PLAYERS = (BooleanValue)WAYPOINT_SHARING.addBoolean("waypoint_sharing_players", true)
      .comment(new String[]{"Allow players to share waypoints with other players."});
   SNBTConfig TEAM_PROP_DEFAULTS = CONFIG.addGroup("team_prop_defaults");
   BooleanValue DEF_ALLOW_FAKE_PLAYERS = (BooleanValue)TEAM_PROP_DEFAULTS.addBoolean("def_fake_players", false)
      .comment(new String[]{"Default allow-fake-player setting for team properties"});
   BooleanValue DEF_ALLOW_FAKE_PLAYER_IDS = (BooleanValue)TEAM_PROP_DEFAULTS.addBoolean("def_fake_player_ids", true)
      .comment(new String[]{"Default allow fake player IDs which are the same as real permitted players"});
   StringListValue DEF_ALLOW_NAMED_FAKE_PLAYERS = (StringListValue)TEAM_PROP_DEFAULTS.addStringList("def_named_fake_players", Collections.emptyList())
      .comment(new String[]{"Default named fake players who should be allowed by default"});
   EnumValue<PrivacyMode> DEF_ENTITY_INTERACT = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum(
         "def_entity_interact", PrivacyMode.NAME_MAP, PrivacyMode.ALLIES
      )
      .comment(new String[]{"Default mode for entity interaction in claimed chunks"});
   EnumValue<PrivacyMode> DEF_BLOCK_INTERACT = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum(
         "def_block_interact", PrivacyMode.NAME_MAP, PrivacyMode.ALLIES
      )
      .comment(new String[]{"Default mode for block interaction (right-click) in claimed chunks (NeoForge only)"})
      .enabled(Platform::isForgeLike);
   EnumValue<PrivacyMode> DEF_BLOCK_EDIT = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum("def_block_edit", PrivacyMode.NAME_MAP, PrivacyMode.ALLIES)
      .comment(new String[]{"Default mode for block breaking and placing in claimed chunks (NeoForge only)"})
      .enabled(Platform::isForgeLike);
   EnumValue<PrivacyMode> DEF_BLOCK_EDIT_INTERACT = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum(
         "def_block_edit_interact", PrivacyMode.NAME_MAP, PrivacyMode.ALLIES
      )
      .comment(new String[]{"Default mode for block interaction, breaking and placing in claimed chunks (Fabric only)"})
      .enabled(Platform::isFabric);
   EnumValue<PrivacyMode> DEF_NONLIVING_ENTITY_ATTACK = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum(
         "def_entity_attack", PrivacyMode.NAME_MAP, PrivacyMode.ALLIES
      )
      .comment(new String[]{"Default mode for left-clicking non-living entities (armor stands, item frames...) in claimed chunks"});
   BooleanValue DEF_ALLOW_EXPLOSIONS = (BooleanValue)TEAM_PROP_DEFAULTS.addBoolean("def_allow_explosions", false)
      .comment(new String[]{"Default explosion protection for claimed chunks"});
   BooleanValue DEF_MOB_GRIEFING = (BooleanValue)TEAM_PROP_DEFAULTS.addBoolean("def_mob_griefing", false)
      .comment(new String[]{"Default mob griefing protection for claimed chunks"});
   EnumValue<PrivacyMode> DEF_CLAIM_VISIBILITY = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum(
         "def_claim_visibility", PrivacyMode.NAME_MAP, PrivacyMode.PUBLIC
      )
      .comment(new String[]{"Default claim visibility for claimed chunks"});
   EnumValue<PrivacyMode> DEF_PLAYER_VISIBILITY = (EnumValue<PrivacyMode>)TEAM_PROP_DEFAULTS.addEnum(
         "def_player_visibility", PrivacyMode.NAME_MAP, PrivacyMode.ALLIES
      )
      .comment(new String[]{"Default long-range player visibility on map"});
   BooleanValue DEF_PVP = (BooleanValue)TEAM_PROP_DEFAULTS.addBoolean("def_pvp", true).comment(new String[]{"Default PvP setting in claimed chunks"});
   SNBTConfig DEV = CONFIG.addGroup("dev");
   BooleanValue DEV_COMMANDS = (BooleanValue)DEV.addBoolean("commands", false).excluded().comment(new String[]{"Enable dev commands"});

   static int getMaxClaimedChunks(ChunkTeamData playerData, ServerPlayer player) {
      return player != null
         ? PermissionsHelper.getMaxClaimedChunks(player, (Integer)MAX_CLAIMED_CHUNKS.get()) + playerData.getExtraClaimChunks()
         : (Integer)MAX_CLAIMED_CHUNKS.get() + playerData.getExtraClaimChunks();
   }

   static int getMaxForceLoadedChunks(ChunkTeamData playerData, ServerPlayer player) {
      return player != null
         ? PermissionsHelper.getMaxForceLoadedChunks(player, (Integer)MAX_FORCE_LOADED_CHUNKS.get()) + playerData.getExtraForceLoadChunks()
         : (Integer)MAX_FORCE_LOADED_CHUNKS.get() + playerData.getExtraForceLoadChunks();
   }

   static boolean canPlayerOfflineForceload(ServerPlayer player) {
      return player != null && PermissionsHelper.getChunkLoadOffline(player, false);
   }

   static boolean noWilderness(ServerPlayer player) {
      return player == null
         ? (Boolean)NO_WILDERNESS.get()
         : DimensionFilter.isNoWildernessDimension(player.level().dimension()) || PermissionsHelper.getNoWilderness(player, (Boolean)NO_WILDERNESS.get());
   }

   static boolean playerHasMapStage(Player player) {
      return !(Boolean)REQUIRE_GAME_STAGE.get() || StageHelper.getInstance().getProvider().has(player, "ftbchunks_mapping");
   }

   static boolean shouldShowMinimap(Player player) {
      return !(Boolean)FORCE_DISABLE_MINIMAP.get() && playerHasMapStage(player);
   }

   static void onConfigChanged(boolean isServerConfig) {
      if (isServerConfig) {
         DimensionFilter.clearMatcherCaches();
         FTBTeamsAPI.api().getManager().getTeams().forEach(team -> ClaimedChunkManagerImpl.getInstance().getOrCreateData(team).updateLimits());
      }
   }
}
