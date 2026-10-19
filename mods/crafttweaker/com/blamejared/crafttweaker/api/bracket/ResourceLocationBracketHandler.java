package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@ZenCodeType.Name("crafttweaker.api.bracket.ResourceLocationBracketHandler")
@Document("vanilla/api/ResourceLocationBracketHandler")
public class ResourceLocationBracketHandler {
   @ZenCodeType.Method
   @BracketResolver("resource")
   public static ResourceLocation getResourceLocation(String tokens) {
      return ResourceLocation.parse(tokens);
   }
}
