package net.jadenxgamer.elysium_api.impl.config;

import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ElysiumConfig {
   public static BooleanValue BLOCK_SWAP;
   public static BooleanValue QUICK_BREAK;
   public static EnumValue<QuickPlaceConfig> QUICK_PLACE;
   public static BooleanValue REVEAL_INVISIBLE_BLOCKS;
   public static BooleanValue CONTROLLED_FLIGHT;
   public static BooleanValue NO_CLIP;
   public static BooleanValue AIR_PLACE;
   public static BooleanValue REACHAROUND;
   public static IntValue EXTENDED_BLOCK_REACH;
   public static DoubleValue FLIGHT_SPEED;
   public static BooleanValue SUPPRESS_INTERACTION;
   public static BooleanValue SUPPRESS_UPDATES;
}
