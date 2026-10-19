package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TeacupRegistry {
   public static final Map<ResourceLocation, TeacupRegistry.TeacupData> TEACUP_DATA_MAP = Maps.newLinkedHashMap();
   public static ResourceLocation BARLEY_TEA;
   public static ResourceLocation TIEGUANYIN;
   public static ResourceLocation BILUOCHUN;
   public static ResourceLocation OOLONG;
   public static ResourceLocation SAKURA_FUBUKI;
   public static ResourceLocation FLOWER_TEA;

   public static void init() {
      TeacupRegistry registry = new TeacupRegistry();
      BARLEY_TEA = registry.registerTeacupData(
         "barley_tea", TeacupRegistry.TeacupData.create(4).addEffect(() -> new MobEffectInstance(ModEffects.VITALITY, 9600))
      );
      TIEGUANYIN = registry.registerTeacupData(
         "tieguanyin", TeacupRegistry.TeacupData.create(4).addEffect(() -> new MobEffectInstance(ModEffects.INSTANT_SMELTING, 2400))
      );
      BILUOCHUN = registry.registerTeacupData(
         "biluochun", TeacupRegistry.TeacupData.create(4).addEffect(() -> new MobEffectInstance(ModEffects.PROJECTILE_DODGE, 2400))
      );
      OOLONG = registry.registerTeacupData(
         "oolong",
         TeacupRegistry.TeacupData.create(4)
            .addEffect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 7200))
            .addEffect(() -> new MobEffectInstance(MobEffects.JUMP, 7200))
      );
      SAKURA_FUBUKI = registry.registerTeacupData(
         "sakura_fubuki", TeacupRegistry.TeacupData.create(4).addEffect(() -> new MobEffectInstance(ModEffects.HINDER, 7200))
      );
      FLOWER_TEA = registry.registerTeacupData(
         "flower_tea", TeacupRegistry.TeacupData.create(4).addEffect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400))
      );
   }

   public ResourceLocation registerTeacupData(ResourceLocation id, TeacupRegistry.TeacupData data) {
      TEACUP_DATA_MAP.put(id, data);
      return id;
   }

   public ResourceLocation registerTeacupData(String name, TeacupRegistry.TeacupData data) {
      return this.registerTeacupData(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", name), data);
   }

   public static Item getItem(ResourceLocation name) {
      return (Item)BuiltInRegistries.ITEM.get(name);
   }

   public static Block getBlock(ResourceLocation name) {
      return (Block)BuiltInRegistries.BLOCK.get(name);
   }

   public static final class TeacupData {
      private final int maxCount;
      private final List<Pair<Supplier<MobEffectInstance>, Float>> effects = Lists.newArrayList();
      @Nullable
      private VoxelShape aabb = null;

      private TeacupData(int maxCount) {
         this.maxCount = maxCount;
      }

      public static TeacupRegistry.TeacupData create(int maxCount) {
         return new TeacupRegistry.TeacupData(maxCount);
      }

      public TeacupRegistry.TeacupData addEffect(Supplier<MobEffectInstance> effect, float probability) {
         this.effects.add(Pair.of(effect, probability));
         return this;
      }

      public TeacupRegistry.TeacupData addEffect(Supplier<MobEffectInstance> effect) {
         return this.addEffect(effect, 1.0F);
      }

      public TeacupRegistry.TeacupData setAABB(VoxelShape aabb) {
         this.aabb = aabb;
         return this;
      }

      public int getMaxCount() {
         return this.maxCount;
      }

      @Nullable
      public VoxelShape getAABB() {
         return this.aabb;
      }

      public List<Pair<Supplier<MobEffectInstance>, Float>> getEffects() {
         return this.effects;
      }
   }
}
