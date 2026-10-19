package team.creative.creativecore.common.util.ingredient;

import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CreativeIngredientItemTag extends CreativeIngredient {
   public TagKey<Item> tag;

   public CreativeIngredientItemTag(TagKey<Item> tag) {
      this.tag = tag;
   }

   public CreativeIngredientItemTag() {
   }

   @Override
   protected void saveExtra(Provider provider, CompoundTag nbt) {
      nbt.putString("tag", this.tag.location().toString());
   }

   @Override
   protected void loadExtra(Provider provider, CompoundTag nbt) {
      this.tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(nbt.getString("tag")));
   }

   @Override
   public boolean is(ItemStack stack) {
      return stack.getItem().builtInRegistryHolder().is(this.tag);
   }

   @Override
   public boolean is(CreativeIngredient info) {
      return info instanceof CreativeIngredientItemTag && ((CreativeIngredientItemTag)info).tag == this.tag;
   }

   @Override
   public ItemStack getExample() {
      Optional<Named<Item>> optional = BuiltInRegistries.ITEM.getTag(this.tag);
      return !optional.isEmpty() && optional.get().size() != 0 ? new ItemStack((ItemLike)optional.get().get(0).value()) : ItemStack.EMPTY;
   }

   @Override
   public boolean equals(CreativeIngredient object) {
      return object instanceof CreativeIngredientItemTag && ((CreativeIngredientItemTag)object).tag == this.tag;
   }

   @Override
   public CreativeIngredient copy() {
      return new CreativeIngredientItemTag(this.tag);
   }

   @Override
   public Component description() {
      return Component.literal(this.tag.location().toString());
   }

   @Override
   public Component descriptionDetail() {
      return Component.translatable("minecraft.item_tag").append(": " + ChatFormatting.YELLOW + this.tag.location());
   }
}
