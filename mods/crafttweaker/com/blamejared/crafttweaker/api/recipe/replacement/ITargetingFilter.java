package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/ITargetingFilter")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.ITargetingFilter")
@ZenRegister
public interface ITargetingFilter {
   Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> var1);
}
