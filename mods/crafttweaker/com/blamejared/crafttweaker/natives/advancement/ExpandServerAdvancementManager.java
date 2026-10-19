package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Collection;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/ServerAdvancementManager")
@NativeTypeRegistration(value = ServerAdvancementManager.class, zenCodeName = "crafttweaker.api.advancement.ServerAdvancementManager")
public class ExpandServerAdvancementManager {
   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
   public static @ZenCodeType.Nullable AdvancementHolder getAdvancement(ServerAdvancementManager internal, ResourceLocation id) {
      return internal.get(id);
   }

   @ZenCodeType.Getter("allAdvancements")
   public static Collection<AdvancementHolder> getAllAdvancements(ServerAdvancementManager internal) {
      return internal.getAllAdvancements();
   }
}
