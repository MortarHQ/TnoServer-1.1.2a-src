package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tag/ExpandManyItemTag")
@ZenCodeType.Expansion("crafttweaker.api.util.Many<crafttweaker.api.tag.type.KnownTag<crafttweaker.api.item.ItemDefinition>>")
public class ExpandManyItemTag {
   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   public static IIngredientWithAmount asIngredient(Many<KnownTag<Item>> internal) {
      IIngredient iIngredient = ExpandItemTag.asIIngredient(internal.getData());
      return iIngredient.mul(internal.getAmount());
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   public static IData asIData(Many<KnownTag<Item>> internal) {
      return asIngredient(internal).asIData();
   }
}
