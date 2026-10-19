package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

@ZenRegister
@Document("vanilla/api/loot/param/LootContextParamSet")
@NativeTypeRegistration(value = LootContextParamSet.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParamSet")
public class ExpandLootContextParamSet {
}
