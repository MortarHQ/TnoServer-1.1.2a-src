package com.blamejared.crafttweaker.mixin.common.transform.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ingredient.IngredientCacheBuster;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager.LoadResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagValue.class)
public class MixinIngredientTagValue {
   @Shadow
   @Final
   private TagKey<Item> tag;

   @Inject(method = "getItems", at = @At("HEAD"), cancellable = true)
   public void crafttweaker$injectTags(CallbackInfoReturnable<Collection<ItemStack>> cir) {
      if (IngredientCacheBuster.claimed()) {
         cir.setReturnValue(
            CraftTweakerAPI.getAccessibleElementsProvider()
               .server()
               .accessibleResources()
               .crafttweaker$getTagManager()
               .getResult()
               .stream()
               .filter(loadResult -> loadResult.key().equals(Registries.ITEM))
               .map(GenericUtil::uncheck)
               .<Map>map(LoadResult::tags)
               .map(map -> (Collection)map.get(this.tag.location()))
               .filter(Objects::nonNull)
               .flatMap(Collection::stream)
               .map(Holder::value)
               .map(ItemStack::new)
               .toList()
         );
      }
   }
}
