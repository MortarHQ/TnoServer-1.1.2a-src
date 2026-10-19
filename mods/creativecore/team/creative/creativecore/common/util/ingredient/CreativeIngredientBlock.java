package team.creative.creativecore.common.util.ingredient;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CreativeIngredientBlock extends CreativeIngredient {
   public Block block;

   public CreativeIngredientBlock(Block block) {
      this.block = block;
   }

   public CreativeIngredientBlock() {
   }

   @Override
   protected void loadExtra(Provider provider, CompoundTag nbt) {
      this.block = (Block)((RegistryLookup)provider.lookup(Registries.BLOCK).get())
         .get(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse(nbt.getString("block"))))
         .orElse(Blocks.AIR.builtInRegistryHolder())
         .value();
   }

   @Override
   protected void saveExtra(Provider provider, CompoundTag nbt) {
      nbt.putString("block", this.block.builtInRegistryHolder().getRegisteredName());
   }

   @Override
   public boolean is(CreativeIngredient info) {
      if (info instanceof CreativeIngredientBlock) {
         return ((CreativeIngredientBlock)info).block == this.block;
      } else {
         return info instanceof CreativeIngredientItemStack ? this.block == Block.byItem(((CreativeIngredientItemStack)info).stack.getItem()) : false;
      }
   }

   @Override
   public boolean is(ItemStack stack) {
      return Block.byItem(stack.getItem()) == this.block;
   }

   @Override
   public boolean equals(CreativeIngredient object) {
      return object instanceof CreativeIngredientBlock && ((CreativeIngredientBlock)object).block == this.block;
   }

   @Override
   public ItemStack getExample() {
      return new ItemStack(this.block);
   }

   @Override
   public CreativeIngredient copy() {
      return new CreativeIngredientBlock(this.block);
   }

   @Override
   public Component description() {
      return this.block.getName();
   }

   @Override
   public Component descriptionDetail() {
      return Component.translatable("minecraft.block").append(": " + ChatFormatting.YELLOW + this.block.builtInRegistryHolder().getRegisteredName());
   }
}
