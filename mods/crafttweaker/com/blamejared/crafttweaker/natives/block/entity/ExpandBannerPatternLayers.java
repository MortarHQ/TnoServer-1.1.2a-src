package com.blamejared.crafttweaker.natives.block.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatternLayers.Builder;
import net.minecraft.world.level.block.entity.BannerPatternLayers.Layer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/entity/BannerPatternLayers")
@NativeTypeRegistration(value = BannerPatternLayers.class, zenCodeName = "crafttweaker.api.block.entity.BannerPatternLayers")
public class ExpandBannerPatternLayers {
   @ZenCodeType.StaticExpansionMethod
   public static BannerPatternLayers of(List<Layer> layers) {
      return new BannerPatternLayers(layers);
   }

   @ZenCodeType.Method
   public static BannerPatternLayers removeLast(BannerPatternLayers internal) {
      return internal.removeLast();
   }

   @ZenCodeType.Getter("layers")
   public static List<Layer> layers(BannerPatternLayers internal) {
      return internal.layers();
   }

   @ZenRegister
   @Document("vanilla/api/block/entity/BannerPatternLayersBuilder")
   @NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.block.entity.BannerPatternLayersBuilder")
   public static class ExpandBannerPatternLayersBuilder {
      @ZenCodeType.StaticExpansionMethod
      public static Builder of() {
         return new Builder();
      }

      @ZenCodeType.Method
      public static Builder add(Builder internal, BannerPattern pattern, DyeColor color) {
         return internal.add(Services.REGISTRY.holderOrThrow(Registries.BANNER_PATTERN, pattern), color);
      }

      @ZenCodeType.Method
      public static Builder addAll(Builder internal, BannerPatternLayers layers) {
         return internal.addAll(layers);
      }

      @ZenCodeType.Method
      public static Builder add(Builder internal, Layer layer) {
         return internal.add(layer);
      }

      @ZenCodeType.Method
      public static BannerPatternLayers build(Builder internal) {
         return internal.build();
      }
   }

   @ZenRegister
   @Document("vanilla/api/block/entity/BannerPatternLayersLayer")
   @NativeTypeRegistration(value = Layer.class, zenCodeName = "crafttweaker.api.block.entity.BannerPatternLayersLayer")
   public static class ExpandBannerPatternLayersLayer {
      @ZenCodeType.StaticExpansionMethod
      public static Layer of(BannerPattern pattern, DyeColor color) {
         return new Layer(Services.REGISTRY.holderOrThrow(Registries.BANNER_PATTERN, pattern), color);
      }

      @ZenCodeType.Getter("description")
      public static MutableComponent description(Layer internal) {
         return internal.description();
      }

      @ZenCodeType.Getter("pattern")
      public static BannerPattern pattern(Layer internal) {
         return (BannerPattern)internal.pattern().value();
      }

      @ZenCodeType.Getter("color")
      public static DyeColor color(Layer internal) {
         return internal.color();
      }
   }
}
