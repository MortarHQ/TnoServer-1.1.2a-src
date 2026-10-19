package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ESWoodTypes {
   public static final BlockSetType LUNAR_SET = new BlockSetType("eternal_starlight:lunar");
   public static final BlockSetType NORTHLAND_SET = new BlockSetType("eternal_starlight:northland");
   public static final BlockSetType BANYIN_SET = new BlockSetType("eternal_starlight:banyin");
   public static final BlockSetType SCARLET_SET = new BlockSetType("eternal_starlight:scarlet");
   public static final BlockSetType TORREYA_SET = new BlockSetType("eternal_starlight:torreya");
   public static final BlockSetType JINGLESTEM_SET = new BlockSetType("eternal_starlight:jinglestem");
   public static final BlockSetType CRADLEWOOD_SET = new BlockSetType("eternal_starlight:cradlewood");
   public static final BlockSetType LUNAR_MOSAIC_SET = new BlockSetType("eternal_starlight:lunar_mosaic");
   public static WoodType LUNAR = WoodType.register(new WoodType("eternal_starlight:lunar", LUNAR_SET));
   public static WoodType NORTHLAND = WoodType.register(new WoodType("eternal_starlight:northland", NORTHLAND_SET));
   public static WoodType BANYIN = WoodType.register(new WoodType("eternal_starlight:banyin", BANYIN_SET));
   public static WoodType SCARLET = WoodType.register(new WoodType("eternal_starlight:scarlet", SCARLET_SET));
   public static WoodType TORREYA = WoodType.register(new WoodType("eternal_starlight:torreya", TORREYA_SET));
   public static WoodType JINGLESTEM = WoodType.register(new WoodType("eternal_starlight:jinglestem", JINGLESTEM_SET));
   public static WoodType CRADLEWOOD = WoodType.register(new WoodType("eternal_starlight:cradlewood", CRADLEWOOD_SET));
   public static WoodType LUNAR_MOSAIC = WoodType.register(new WoodType("eternal_starlight:lunar_mosaic", LUNAR_MOSAIC_SET));
}
