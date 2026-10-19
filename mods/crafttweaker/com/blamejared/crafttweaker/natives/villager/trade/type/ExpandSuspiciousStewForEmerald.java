package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.VillagerTrades.SuspiciousStewForEmerald;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/trade/type/SuspiciousStewForEmerald")
@NativeTypeRegistration(value = SuspiciousStewForEmerald.class, zenCodeName = "crafttweaker.api.villager.trade.type.SuspiciousStewForEmerald")
public class ExpandSuspiciousStewForEmerald {
   @ZenCodeType.StaticExpansionMethod
   public static SuspiciousStewForEmerald of(MobEffect effect, int duration, int xp) {
      return new SuspiciousStewForEmerald(Services.REGISTRY.holderOrThrow(Registries.MOB_EFFECT, effect), duration, xp);
   }

   @ZenCodeType.StaticExpansionMethod
   public static SuspiciousStewForEmerald of(SuspiciousStewEffects effects, int xp, float priceMultiplier) {
      return new SuspiciousStewForEmerald(effects, xp, priceMultiplier);
   }
}
