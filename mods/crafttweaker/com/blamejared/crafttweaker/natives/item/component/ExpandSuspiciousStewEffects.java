package com.blamejared.crafttweaker.natives.item.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.component.SuspiciousStewEffects.Entry;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/component/SuspiciousStewEffects")
@NativeTypeRegistration(value = SuspiciousStewEffects.class, zenCodeName = "crafttweaker.api.item.component.SuspiciousStewEffects")
public class ExpandSuspiciousStewEffects {
   @ZenCodeType.StaticExpansionMethod
   public static SuspiciousStewEffects of(List<Entry> entries) {
      return new SuspiciousStewEffects(entries);
   }

   @ZenCodeType.Method
   public static SuspiciousStewEffects withEffectAdded(SuspiciousStewEffects internal, Entry entry) {
      return internal.withEffectAdded(entry);
   }

   @ZenCodeType.Getter("effects")
   public static List<Entry> effects(SuspiciousStewEffects internal) {
      return internal.effects();
   }

   @ZenRegister
   @Document("vanilla/api/item/component/SuspiciousStewEffectsEntry")
   @NativeTypeRegistration(value = Entry.class, zenCodeName = "crafttweaker.api.item.component.SuspiciousStewEffectsEntry")
   public static class ExpandSuspiciousStewEffectsEntry {
      @ZenCodeType.StaticExpansionMethod
      public static Entry of(MobEffect effect, int duration) {
         return new Entry(Services.REGISTRY.holderOrThrow(Registries.MOB_EFFECT, effect), duration);
      }

      @ZenCodeType.Method
      public static MobEffectInstance createEffectInstance(Entry internal) {
         return internal.createEffectInstance();
      }

      @ZenCodeType.Getter("duration")
      public static int duration(Entry internal) {
         return internal.duration();
      }

      @ZenCodeType.Getter("effect")
      public static MobEffect effect(Entry internal) {
         return (MobEffect)internal.effect().value();
      }
   }
}
