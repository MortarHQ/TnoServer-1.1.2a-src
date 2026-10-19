package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import java.util.HashSet;
import java.util.regex.Pattern;

public class TeamProperties {
   public static final StringProperty DISPLAY_NAME = (StringProperty)new StringProperty(FTBTeamsAPI.rl("display_name"), "", Pattern.compile(".{3,}"))
      .syncToAll();
   public static final StringProperty DESCRIPTION = new StringProperty(FTBTeamsAPI.rl("description"), "");
   public static final ColorProperty COLOR = (ColorProperty)new ColorProperty(FTBTeamsAPI.rl("color"), Color4I.WHITE).syncToAll();
   public static final BooleanProperty FREE_TO_JOIN = new BooleanProperty(FTBTeamsAPI.rl("free_to_join"), false);
   public static final IntProperty MAX_MSG_HISTORY_SIZE = new IntProperty(FTBTeamsAPI.rl("max_msg_history_size"), 1000);
   public static final StringSetProperty TEAM_STAGES = (StringSetProperty)new StringSetProperty(FTBTeamsAPI.rl("team_stages"), new HashSet<>())
      .hidden()
      .notPlayerEditable();
   public static final IntProperty LIVES_REMAINING = (IntProperty)new IntProperty(FTBTeamsAPI.rl("lives_remaining"), 0, 0, Integer.MAX_VALUE)
      .hidden()
      .notPlayerEditable();
}
