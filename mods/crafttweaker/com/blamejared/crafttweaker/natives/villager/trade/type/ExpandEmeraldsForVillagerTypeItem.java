package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldsForVillagerTypeItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/trade/type/EmeraldsForVillagerTypeItem")
@NativeTypeRegistration(value = EmeraldsForVillagerTypeItem.class, zenCodeName = "crafttweaker.api.villager.trade.type.EmeraldsForVillagerTypeItem")
public class ExpandEmeraldsForVillagerTypeItem {
   @ZenCodeType.StaticExpansionMethod
   public static EmeraldsForVillagerTypeItem create(int cost, int maxUses, int villagerXp, Map<VillagerType, IItemStack> trades) {
      return new EmeraldsForVillagerTypeItem(
         cost,
         maxUses,
         villagerXp,
         trades.entrySet().stream().collect(Collectors.toMap(Entry::getKey, o -> ((IItemStack)o.getValue()).getDefinition(), (o, o2) -> o.asItem()))
      );
   }
}
