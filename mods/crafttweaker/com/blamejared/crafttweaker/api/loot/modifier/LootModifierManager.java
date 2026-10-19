package com.blamejared.crafttweaker.api.loot.modifier;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.loot.ActionRegisterLootModifier;
import com.blamejared.crafttweaker.api.action.loot.ActionRemoveLootModifier;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.LootModifierManager")
@Document("vanilla/api/loot/modifier/LootModifierManager")
public class LootModifierManager {
   public static final LootModifierManager INSTANCE = new LootModifierManager();
   private final Supplier<Map<ResourceLocation, ILootModifier>> lootMap = Services.PLATFORM::getLootModifiersMap;

   private LootModifierManager() {
   }

   @ZenCodeType.Method
   public void register(String name, LootConditions conditions, ILootModifier modifier) {
      CraftTweakerAPI.apply(new ActionRegisterLootModifier(this.fromName(name), this.buildModifierCreator(conditions, modifier), this.lootMap));
   }

   @ZenCodeType.Method
   public ILootModifier getByName(String name) {
      return this.lootMap.get().getOrDefault(ResourceLocation.parse(name), ILootModifier.DEFAULT);
   }

   @ZenCodeType.Method
   public List<ResourceLocation> getAllNames() {
      return List.copyOf(this.lootMap.get().keySet());
   }

   @ZenCodeType.Method
   public List<ILootModifier> getAll() {
      return List.copyOf(this.lootMap.get().values());
   }

   @ZenCodeType.Method
   public void removeByName(String name) {
      ResourceLocation id = name.contains(":") ? ResourceLocation.parse(name) : CraftTweakerConstants.rl(name);
      CraftTweakerAPI.apply(new ActionRemoveLootModifier("with name '" + id + "'", entry -> entry.getKey().equals(id), this.lootMap));
   }

   @ZenCodeType.Method
   public void removeByModId(String modId) {
      CraftTweakerAPI.apply(
         new ActionRemoveLootModifier("registered by the mod '" + modId + "'", entry -> modId.equals(entry.getKey().getNamespace()), this.lootMap)
      );
   }

   @ZenCodeType.Method
   public void removeByRegex(String regex) {
      Pattern pattern = Pattern.compile(regex);
      CraftTweakerAPI.apply(
         new ActionRemoveLootModifier(
            "matching the regular expression '" + regex + "'", entry -> pattern.matcher(entry.getKey().toString()).matches(), this.lootMap
         )
      );
   }

   @ZenCodeType.Method
   public void removeAll() {
      CraftTweakerAPI.apply(new ActionRemoveLootModifier(null, entry -> true, this.lootMap));
   }

   private Supplier<ILootModifier> buildModifierCreator(LootConditions conditions, ILootModifier modifier) {
      Predicate<LootContext> condition = conditions.gather();
      return () -> (loot, context) -> condition.test(context) ? modifier.modify(loot, context) : loot;
   }

   private ResourceLocation fromName(String name) {
      return NameUtil.fromFixedName(
         name,
         (fixed, mistakes) -> CommonLoggers.api()
            .warn("The given loot modifier name '{}' isn't valid due to:\n{}\nThe name was changed to '{}'", name, String.join("\n", mistakes), fixed)
      );
   }
}
