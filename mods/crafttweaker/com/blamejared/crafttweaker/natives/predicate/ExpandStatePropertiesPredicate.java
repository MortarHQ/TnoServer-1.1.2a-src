package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/StatePropertiesPredicate")
@NativeTypeRegistration(value = StatePropertiesPredicate.class, zenCodeName = "crafttweaker.api.predicate.StatePropertiesPredicate")
public final class ExpandStatePropertiesPredicate {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create() {
      return Builder.properties();
   }

   @ZenCodeType.Method
   public static boolean matches(StatePropertiesPredicate internal, BlockState state) {
      return internal.matches(state);
   }
}
