package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades.TreasureMapForEmeralds;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/trade/type/TreasureMapForEmeralds")
@NativeTypeRegistration(value = TreasureMapForEmeralds.class, zenCodeName = "crafttweaker.api.villager.trade.type.TreasureMapForEmeralds")
public class ExpandTreasureMapForEmeralds {
   @ZenCodeType.StaticExpansionMethod
   public static TreasureMapForEmeralds create(
      int emeraldCost, ResourceLocation destination, String displayName, MapDecorationType destinationType, int maxUses, int villagerXp
   ) {
      return new TreasureMapForEmeralds(
         emeraldCost,
         TagKey.create(Registries.STRUCTURE, destination),
         displayName,
         Services.REGISTRY.holderOrThrow(Registries.MAP_DECORATION_TYPE, destinationType),
         maxUses,
         villagerXp
      );
   }
}
