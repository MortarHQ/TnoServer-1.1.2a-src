package com.blamejared.crafttweaker.natives.tooltip;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tooltip/TooltipContext")
@NativeTypeRegistration(value = TooltipContext.class, zenCodeName = "crafttweaker.api.tooltip.TooltipContext")
public class ExpandTooltipContext {
   @ZenCodeType.StaticExpansionMethod
   public static TooltipContext of(@ZenCodeType.Nullable Level level) {
      return TooltipContext.of(level);
   }

   @ZenCodeType.Getter("tickRate")
   public static float tickRate(TooltipContext internal) {
      return internal.tickRate();
   }

   @ZenCodeType.Method
   public static @ZenCodeType.Nullable MapItemSavedData mapData(TooltipContext internal, MapId var1) {
      return internal.mapData(var1);
   }
}
