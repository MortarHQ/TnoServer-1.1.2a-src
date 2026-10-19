package com.b1n_ry.yigd.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class ExpConfig {
   @EnumHandler(option = EnumDisplayOption.BUTTON)
   public ExpDropBehaviour dropBehaviour = ExpDropBehaviour.BEST_OF_BOTH;
   @Comment("Ignored if dropBehaviour is set to VANILLA")
   @BoundedDiscrete(max = 100L)
   public int dropPercentage = 0;
   @BoundedDiscrete(max = 100L)
   public int keepPercentage = 0;
}
