package net.jadenxgamer.elysium_api.api.util;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LookupRegistryHelper {
   public static Block getBlock(ResourceLocation id) {
      return (Block)BuiltInRegistries.BLOCK.get(id);
   }

   public static Item getItem(ResourceLocation id) {
      return (Item)BuiltInRegistries.ITEM.get(id);
   }

   public static EntityType<?> getEntityType(ResourceLocation id) {
      return (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
   }

   public static BlockEntityType<?> getBlockEntityType(ResourceLocation id) {
      return (BlockEntityType<?>)BuiltInRegistries.BLOCK_ENTITY_TYPE.get(id);
   }

   public static ParticleType<?> getParticleType(ResourceLocation id) {
      return (ParticleType<?>)BuiltInRegistries.PARTICLE_TYPE.get(id);
   }

   public static SoundEvent getSoundEvent(ResourceLocation id) {
      return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(id);
   }

   public static MobEffect getMobEffect(ResourceLocation id) {
      return (MobEffect)BuiltInRegistries.MOB_EFFECT.get(id);
   }

   public static Potion getPotion(ResourceLocation id) {
      return (Potion)BuiltInRegistries.POTION.get(id);
   }

   public static Fluid getFluid(ResourceLocation id) {
      return (Fluid)BuiltInRegistries.FLUID.get(id);
   }

   public static FluidType getFluidType(ResourceLocation id) {
      return (FluidType)NeoForgeRegistries.FLUID_TYPES.get(id);
   }
}
