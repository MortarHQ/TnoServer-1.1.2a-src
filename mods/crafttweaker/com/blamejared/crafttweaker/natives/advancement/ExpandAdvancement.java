package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Map;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/Advancement")
@NativeTypeRegistration(value = Advancement.class, zenCodeName = "crafttweaker.api.advancement.Advancement")
public class ExpandAdvancement {
   @ZenCodeType.Getter("isRoot")
   public static boolean isRoot(Advancement internal) {
      return internal.isRoot();
   }

   @ZenCodeType.Getter("parent")
   public static @ZenCodeType.Nullable ResourceLocation parent(Advancement internal) {
      return (ResourceLocation)internal.parent().orElse(null);
   }

   @ZenCodeType.Getter("display")
   public static @ZenCodeType.Nullable DisplayInfo display(Advancement internal) {
      return (DisplayInfo)internal.display().orElse(null);
   }

   @ZenCodeType.Getter("rewards")
   public static AdvancementRewards rewards(Advancement internal) {
      return internal.rewards();
   }

   @ZenCodeType.Getter("criteria")
   public static Map<String, Criterion> criteria(Advancement internal) {
      return GenericUtil.uncheck(internal.criteria());
   }

   @ZenCodeType.Getter("requirements")
   public static AdvancementRequirements requirements(Advancement internal) {
      return internal.requirements();
   }

   @ZenCodeType.Getter("sendsTelemetryEvent")
   public static boolean sendsTelemetryEvent(Advancement internal) {
      return internal.sendsTelemetryEvent();
   }

   @ZenCodeType.Getter("name")
   public static @ZenCodeType.Nullable Component name(Advancement internal) {
      return (Component)internal.name().orElse(null);
   }
}
