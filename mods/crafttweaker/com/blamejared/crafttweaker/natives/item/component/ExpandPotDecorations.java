package com.blamejared.crafttweaker.natives.item.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/component/PotDecorations")
@NativeTypeRegistration(value = PotDecorations.class, zenCodeName = "crafttweaker.api.item.component.PotDecorations")
public class ExpandPotDecorations {
   @ZenCodeType.StaticExpansionMethod
   public static PotDecorations of(
      @ZenCodeType.Nullable Item back, @ZenCodeType.Nullable Item left, @ZenCodeType.Nullable Item right, @ZenCodeType.Nullable Item front
   ) {
      return new PotDecorations(back, left, right, front);
   }

   @ZenCodeType.Getter("back")
   public static @ZenCodeType.Nullable Item back(PotDecorations internal) {
      return (Item)internal.back().orElse(null);
   }

   @ZenCodeType.Getter("front")
   public static @ZenCodeType.Nullable Item front(PotDecorations internal) {
      return (Item)internal.front().orElse(null);
   }

   @ZenCodeType.Getter("right")
   public static @ZenCodeType.Nullable Item right(PotDecorations internal) {
      return (Item)internal.right().orElse(null);
   }

   @ZenCodeType.Getter("left")
   public static @ZenCodeType.Nullable Item left(PotDecorations internal) {
      return (Item)internal.left().orElse(null);
   }

   @ZenCodeType.Getter("ordered")
   public static List<Item> ordered(PotDecorations internal) {
      return internal.ordered();
   }
}
