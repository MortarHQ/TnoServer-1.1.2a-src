package com.blamejared.crafttweaker.api.mod;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.List;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mod.Mods")
@Document("vanilla/api/mod/Mods")
public class Mods {
   @ZenCodeGlobals.Global("loadedMods")
   public static final Mods INSTANCE = new Mods();

   private Mods() {
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("mods")
   public List<Mod> getMods() {
      return Services.PLATFORM.getMods();
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.MEMBERGETTER)
   public @ZenCodeType.Nullable Mod getMod(String modid) {
      return Services.PLATFORM.getMod(modid).orElse(null);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
   public @ZenCodeType.Nullable Mod getModByIndex(String modid) {
      return Services.PLATFORM.getMod(modid).orElse(null);
   }

   @ZenCodeType.Method
   public boolean isModLoaded(String modid) {
      return Services.PLATFORM.isModLoaded(modid);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("size")
   public int getSize() {
      return this.getMods().size();
   }
}
