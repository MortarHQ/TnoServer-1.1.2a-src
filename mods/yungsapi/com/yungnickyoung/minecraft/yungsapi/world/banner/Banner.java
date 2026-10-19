package com.yungnickyoung.minecraft.yungsapi.world.banner;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;

public class Banner {
   private List<ColoredBannerPattern> patterns;
   private BlockState state;
   private CompoundTag nbt;
   private boolean isWallBanner;

   public Banner(List<ColoredBannerPattern> patterns, BlockState state, CompoundTag nbt) {
      this.patterns = patterns;
      this.state = state;
      this.nbt = nbt;
      this.isWallBanner = this.state.getBlock() instanceof WallBannerBlock;
   }

   public Banner(List<ColoredBannerPattern> patterns, BlockState state, CompoundTag nbt, boolean isWallBanner) {
      this.patterns = patterns;
      this.state = state;
      this.nbt = nbt;
      this.isWallBanner = isWallBanner;
   }

   public List<ColoredBannerPattern> getPatterns() {
      return this.patterns;
   }

   public void setPatterns(List<ColoredBannerPattern> patterns) {
      this.patterns = patterns;
   }

   public BlockState getState() {
      return this.state;
   }

   public void setState(BlockState state) {
      this.state = state;
   }

   public CompoundTag getNbt() {
      return this.nbt;
   }

   public void setNbt(CompoundTag nbt) {
      this.nbt = nbt;
   }

   public boolean isWallBanner() {
      return this.isWallBanner;
   }

   public void setWallBanner(boolean wallBanner) {
      this.isWallBanner = wallBanner;
   }

   public static class Builder {
      private final List<ColoredBannerPattern> patterns = new ArrayList<>();
      private String customNameTranslate;
      private String customColor;
      private BlockState state = Blocks.BLACK_WALL_BANNER.defaultBlockState();

      public Banner.Builder blockState(BlockState state) {
         this.state = state;
         return this;
      }

      public Banner.Builder pattern(ColoredBannerPattern pattern) {
         this.patterns.add(pattern);
         return this;
      }

      public Banner.Builder pattern(ResourceKey<BannerPattern> pattern, DyeColor color) {
         this.patterns.add(new ColoredBannerPattern(pattern, color));
         return this;
      }

      public Banner.Builder customName(String translatableNamePath) {
         this.customNameTranslate = translatableNamePath;
         return this;
      }

      public Banner.Builder customColor(String colorString) {
         this.customColor = colorString;
         return this;
      }

      public Banner build() {
         CompoundTag nbt = this.createBannerNBT();
         return new Banner(this.patterns, this.state, nbt);
      }

      private CompoundTag createBannerNBT() {
         CompoundTag nbt = new CompoundTag();
         ListTag patternList = new ListTag();
         this.patterns.forEach(pattern -> {
            CompoundTag patternNBT = new CompoundTag();
            patternNBT.putString("pattern", pattern.getPattern().location().toString());
            patternNBT.putString("color", pattern.getColor().getName());
            patternList.add(patternNBT);
         });
         if (this.customColor != null || this.customNameTranslate != null) {
            String color = this.customColor == null ? "" : String.format("\"color\":\"%s\"", this.customColor);
            String name = this.customNameTranslate == null ? "" : String.format("\"translate\":\"%s\"", this.customNameTranslate);
            if (this.customColor != null && this.customNameTranslate != null) {
               name = "," + name;
            }

            String customNameString = "{" + color + name + "}";
            nbt.putString("CustomName", customNameString);
         }

         nbt.put("patterns", patternList);
         nbt.putString("id", "minecraft:banner");
         return nbt;
      }
   }
}
