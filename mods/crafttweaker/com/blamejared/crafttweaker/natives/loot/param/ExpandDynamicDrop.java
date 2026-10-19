package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams.DynamicDrop;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/DynamicDrop")
@NativeTypeRegistration(value = DynamicDrop.class, zenCodeName = "crafttweaker.api.loot.DynamicDrop")
public final class ExpandDynamicDrop {
   @ZenCodeType.Method
   public static void add(DynamicDrop internal, Consumer<ItemStack> drop) {
      internal.add(drop);
   }
}
