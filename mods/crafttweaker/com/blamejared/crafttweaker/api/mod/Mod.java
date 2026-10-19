package com.blamejared.crafttweaker.api.mod;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Collection;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mod.Mod")
@Document("vanilla/api/mod/Mod")
public final class Mod {
   private final String id;
   private final String displayName;
   private final String version;

   public Mod(String id, String displayName, String version) {
      this.id = id;
      this.displayName = displayName;
      this.version = version;
   }

   private <T> Collection<T> getRegistryObjects(ResourceKey<Registry<T>> registry) {
      return Services.REGISTRY
         .registryOrThrow(registry)
         .entrySet()
         .stream()
         .filter(resourceKeyTEntry -> ((ResourceKey)resourceKeyTEntry.getKey()).location().getNamespace().equals(this.id()))
         .map(Entry::getValue)
         .toList();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("items")
   public Collection<Item> getItems() {
      return this.getRegistryObjects(Registries.ITEM);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("itemStacks")
   public Collection<IItemStack> getItemStacks() {
      return this.getItems().stream().<ItemStack>map(Item::getDefaultInstance).map(IItemStack::of).toList();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("potions")
   public Collection<Potion> getPotions() {
      return this.getRegistryObjects(Registries.POTION);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("attributes")
   public Collection<Attribute> getAttributes() {
      return this.getRegistryObjects(Registries.ATTRIBUTE);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("fluids")
   public Collection<Fluid> getFluids() {
      return this.getRegistryObjects(Registries.FLUID);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("enchantments")
   public Collection<Enchantment> getEnchantments() {
      return this.getRegistryObjects(Registries.ENCHANTMENT);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("blocks")
   public Collection<Block> getBlocks() {
      return this.getRegistryObjects(Registries.BLOCK);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("mobEffects")
   public Collection<MobEffect> getMobEffects() {
      return this.getRegistryObjects(Registries.MOB_EFFECT);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("villagerProfessions")
   public Collection<VillagerProfession> getVillagerProfessions() {
      return this.getRegistryObjects(Registries.VILLAGER_PROFESSION);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("soundEvents")
   public Collection<SoundEvent> getSoundEvents() {
      return this.getRegistryObjects(Registries.SOUND_EVENT);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("id")
   public String id() {
      return this.id;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("displayName")
   public String displayName() {
      return this.displayName;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("version")
   public String version() {
      return this.version;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         Mod that = (Mod)obj;
         return Objects.equals(this.id, that.id) && Objects.equals(this.displayName, that.displayName) && Objects.equals(this.version, that.version);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id, this.displayName, this.version);
   }

   @Override
   public String toString() {
      return "Mod[id=" + this.id + ", displayName=" + this.displayName + ", version=" + this.version + "]";
   }
}
