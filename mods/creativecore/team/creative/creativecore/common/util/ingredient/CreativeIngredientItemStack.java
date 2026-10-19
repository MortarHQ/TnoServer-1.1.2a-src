package team.creative.creativecore.common.util.ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.common.util.type.itr.FunctionIterator;

public class CreativeIngredientItemStack extends CreativeIngredient {
   public ItemStack stack;
   private List<ResourceLocation> included = Collections.EMPTY_LIST;

   public CreativeIngredientItemStack(ItemStack stack) {
      this.stack = stack;
   }

   public CreativeIngredientItemStack(ItemStack stack, List<ResourceLocation> included) {
      this.stack = stack;
      this.included = included;
   }

   public CreativeIngredientItemStack() {
   }

   public List<ResourceLocation> getIncluded() {
      return new ArrayList<>(this.included);
   }

   @Override
   protected void saveExtra(Provider provider, CompoundTag nbt) {
      nbt.put("stack", this.stack.saveOptional(provider));
      if (!this.included.isEmpty()) {
         nbt.putString("included", String.join(";", new FunctionIterator<>(this.included, x -> x.toString())));
      }
   }

   @Override
   protected void loadExtra(Provider provider, CompoundTag nbt) {
      this.stack = ItemStack.parseOptional(provider, nbt.getCompound("stack"));
      if (nbt.contains("included")) {
         this.included = new ArrayList<>();
         String[] array = nbt.getString("included").split(";");

         for (int i = 0; i < array.length; i++) {
            this.included.add(ResourceLocation.parse(array[i]));
         }
      }
   }

   @Override
   public boolean is(CreativeIngredient info) {
      return info instanceof CreativeIngredientItemStack s ? this.is(s.stack) : false;
   }

   @Override
   public boolean is(ItemStack stack) {
      if (stack.getItem() != this.stack.getItem()) {
         return false;
      }

      for (TypedDataComponent<?> comp : this.stack.getComponents()) {
         if (this.included.contains(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(comp.type())) && !Objects.equals(comp.value(), stack.get(comp.type()))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getExample() {
      return this.stack.copy();
   }

   @Override
   public boolean equals(CreativeIngredient object) {
      return object instanceof CreativeIngredientItemStack && object.is(this.stack);
   }

   @Override
   public CreativeIngredient copy() {
      return new CreativeIngredientItemStack(this.stack.copy());
   }

   @Override
   public Component description() {
      return this.stack.getDisplayName();
   }

   @Override
   public Component descriptionDetail() {
      return Component.translatable("minecraft.stack").append(": ").append(((MutableComponent)this.stack.getDisplayName()).withStyle(ChatFormatting.YELLOW));
   }
}
