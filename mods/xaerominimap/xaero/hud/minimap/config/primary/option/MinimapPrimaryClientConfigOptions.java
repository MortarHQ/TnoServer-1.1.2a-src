package xaero.hud.minimap.config.primary.option;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import xaero.lib.common.config.option.BooleanConfigOption;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.option.ConfigOptionManager;
import xaero.lib.common.config.option.BooleanConfigOption.Builder;
import xaero.lib.common.config.option.ConfigOption.FinalBuilder;
import xaero.lib.common.config.option.value.type.BuiltInConfigValueTypes;
import xaero.lib.common.config.util.ConfigUtils;

public class MinimapPrimaryClientConfigOptions {
   private static final List<ConfigOption<?>> ALL = new ArrayList<>();
   public static final BooleanConfigOption UPDATE_NOTIFICATIONS = ((Builder)((Builder)((Builder)Builder.begin().setId("update_notifications"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_update_notification")))
      .build(ALL);
   public static final ConfigOption<Integer> IGNORED_UPDATE = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                  .setId("ignored_update"))
               .setDefaultValue(0))
            .setValueType(BuiltInConfigValueTypes.INTEGER))
         .setDisplayGetter(ConfigUtils::getDisplayForSimpleNumber))
      .build(ALL);
   public static final BooleanConfigOption WRONG_WORLD_TELEPORT = ((Builder)((Builder)Builder.begin().setId("allow_wrong_world_teleport"))
         .setDefaultValue(false))
      .build(ALL);
   public static final BooleanConfigOption DIFFERENTIATE_BY_SERVER_ADDRESS = ((Builder)((Builder)Builder.begin().setId("differentiate_by_server_address"))
         .setDefaultValue(true))
      .build(ALL);
   public static final BooleanConfigOption DEBUG_ENTITY_ICONS = ((Builder)((Builder)Builder.begin().setId("debug_entity_icons")).setDefaultValue(false))
      .build(ALL);
   public static final BooleanConfigOption DEBUG_ENTITY_VARIANT_IDS = ((Builder)((Builder)Builder.begin().setId("debug_entity_variant_ids"))
         .setDefaultValue(false))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_MUTUAL_EDIT = ((Builder)((Builder)Builder.begin().setId("waypoint_mutual_edit")).setDefaultValue(true))
      .build(ALL);

   public static void registerAll(ConfigOptionManager manager) {
      for (ConfigOption<?> option : ALL) {
         manager.register(option);
      }
   }
}
