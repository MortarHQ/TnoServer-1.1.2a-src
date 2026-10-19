package com.sjyueluo.tensura_skill_books.integration.jei;

import com.sjyueluo.tensura_skill_books.registry.ModItems;
import com.sjyueluo.tensura_skill_books.util.SkillItemNbt;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class TensuraSkillBooksJeiPlugin implements IModPlugin {
   private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "jei_plugin");

   public ResourceLocation getPluginUid() {
      return UID;
   }

   public void registerItemSubtypes(ISubtypeRegistration registration) {
      Item book = (Item)ModItems.SKILL_STUDY_BOOK.get();
      registration.registerSubtypeInterpreter(book, new IIngredientSubtypeInterpreter<ItemStack>() {
         public String apply(ItemStack itemStack, UidContext context) {
            ResourceLocation skillId = SkillItemNbt.getSkillId(itemStack);
            Object category = SkillItemNbt.getRandomCategory(itemStack);
            return skillId != null ? skillId.toString() : (category != null ? category.toString() : "");
         }
      });
   }
}
