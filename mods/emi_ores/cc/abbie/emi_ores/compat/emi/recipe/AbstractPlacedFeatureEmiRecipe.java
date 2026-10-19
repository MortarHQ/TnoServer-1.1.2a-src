package cc.abbie.emi_ores.compat.emi.recipe;

import cc.abbie.emi_ores.EmiOres;
import cc.abbie.emi_ores.client.FeaturesReciever;
import cc.abbie.emi_ores.mixin.accessor.TrapezoidHeightAccessor;
import cc.abbie.emi_ores.mixin.accessor.UniformHeightAccessor;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.VerticalAnchor.AboveBottom;
import net.minecraft.world.level.levelgen.VerticalAnchor.Absolute;
import net.minecraft.world.level.levelgen.VerticalAnchor.BelowTop;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class AbstractPlacedFeatureEmiRecipe implements EmiRecipe {
   private static final ResourceLocation DISTRIBUTION = EmiOres.id("textures/gui/distribution.png");

   protected static Component anchorText(VerticalAnchor anchor) {
      String s;
      if (anchor instanceof Absolute absolute) {
         s = String.valueOf(absolute.y());
      } else if (anchor instanceof AboveBottom aboveBottom) {
         int offset = aboveBottom.offset();
         if (offset == 0) {
            s = "bot";
         } else if (offset > 0) {
            s = "bot+" + offset;
         } else {
            s = "bot" + offset;
         }
      } else {
         if (!(anchor instanceof BelowTop belowTop)) {
            throw new RuntimeException();
         }

         int offset = -belowTop.offset();
         if (offset == 0) {
            s = "top";
         } else if (offset > 0) {
            s = "top+" + offset;
         } else {
            s = "top" + offset;
         }
      }

      return Component.literal(s);
   }

   protected static Component anchorTextLong(VerticalAnchor anchor) {
      return anchorTextLongInner(anchor).withStyle(ChatFormatting.WHITE);
   }

   private static MutableComponent anchorTextLongInner(VerticalAnchor anchor) {
      if (anchor instanceof Absolute absolute) {
         return Component.literal(String.valueOf(absolute.y()));
      } else if (anchor instanceof AboveBottom aboveBottom) {
         int offset = aboveBottom.offset();
         if (offset == 0) {
            return Component.translatable("emi_ores.distribution.anchor.bottom");
         } else {
            return offset > 0
               ? Component.translatable("emi_ores.distribution.anchor.above_bottom", new Object[]{offset})
               : Component.translatable("emi_ores.distribution.anchor.below_bottom", new Object[]{-offset});
         }
      } else if (anchor instanceof BelowTop belowTop) {
         int offset = -belowTop.offset();
         if (offset == 0) {
            return Component.translatable("emi_ores.distribution.anchor.top");
         } else {
            return offset > 0
               ? Component.translatable("emi_ores.distribution.anchor.above_top", new Object[]{offset})
               : Component.translatable("emi_ores.distribution.anchor.below_top", new Object[]{-offset});
         }
      } else {
         throw new RuntimeException();
      }
   }

   protected static List<Biome> getBiomes(ResourceLocation id, PlacedFeature feature) {
      Registry<Biome> biomeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME);
      return FeaturesReciever.getBiomes().get(ResourceKey.create(Registries.PLACED_FEATURE, id)).stream().<Biome>map(biomeRegistry::get).toList();
   }

   protected static void addDistributionGraph(WidgetHolder widgets, int x, int y, HeightProvider heightProvider) {
      if (heightProvider != null) {
         AbstractPlacedFeatureEmiRecipe.HeightProviderType type;
         VerticalAnchor min;
         VerticalAnchor max;
         VerticalAnchor midLow;
         VerticalAnchor midHigh;
         if (heightProvider instanceof UniformHeight uniform) {
            type = AbstractPlacedFeatureEmiRecipe.HeightProviderType.UNIFORM;
            UniformHeightAccessor accessor = (UniformHeightAccessor)uniform;
            min = accessor.getMinInclusive();
            max = accessor.getMaxInclusive();
            midHigh = null;
            midLow = null;
         } else if (heightProvider instanceof TrapezoidHeight trapezoid) {
            TrapezoidHeightAccessor accessor = (TrapezoidHeightAccessor)trapezoid;
            min = accessor.getMinInclusive();
            max = accessor.getMaxInclusive();
            int plateau = accessor.getPlateau();
            if (min instanceof Absolute minAbs && max instanceof Absolute maxAbs) {
               midLow = VerticalAnchor.absolute((minAbs.y() + maxAbs.y() - plateau) / 2);
               midHigh = VerticalAnchor.absolute((minAbs.y() + maxAbs.y() + plateau) / 2);
            } else if (min instanceof AboveBottom minBot && max instanceof AboveBottom maxBot) {
               midLow = VerticalAnchor.aboveBottom((minBot.offset() + maxBot.offset() - plateau) / 2);
               midHigh = VerticalAnchor.aboveBottom((minBot.offset() + maxBot.offset() + plateau) / 2);
            } else if (min instanceof BelowTop minTop && max instanceof BelowTop maxTop) {
               midLow = VerticalAnchor.belowTop((minTop.offset() + maxTop.offset() - plateau) / 2);
               midHigh = VerticalAnchor.belowTop((minTop.offset() + maxTop.offset() + plateau) / 2);
            } else {
               midHigh = null;
               midLow = null;
            }

            if (plateau == 0) {
               type = AbstractPlacedFeatureEmiRecipe.HeightProviderType.TRIANGULAR;
               if (midLow != null) {
                  widgets.addText(anchorText(midLow), 80, 8, 0, false).verticalAlign(Alignment.CENTER).horizontalAlign(Alignment.CENTER);
               }
            } else {
               type = AbstractPlacedFeatureEmiRecipe.HeightProviderType.TRAPEZOID;
            }
         } else {
            type = null;
            midHigh = null;
            midLow = null;
            max = null;
            min = null;
         }

         if (type != null && min != null && max != null) {
            widgets.addTexture(DISTRIBUTION, x, y, 32, 16, 0, type.v).tooltipText(getDistributionGraphTooltip(type, min, max, midLow, midHigh));
            widgets.addText(anchorText(min), x, y + 8, 0, false).verticalAlign(Alignment.CENTER).horizontalAlign(Alignment.END);
            widgets.addText(anchorText(max), x + 32, y + 8, 0, false).verticalAlign(Alignment.CENTER).horizontalAlign(Alignment.START);
         }
      }
   }

   protected static List<Component> getDistributionGraphTooltip(
      AbstractPlacedFeatureEmiRecipe.HeightProviderType type, VerticalAnchor min, VerticalAnchor max, VerticalAnchor midLow, VerticalAnchor midHigh
   ) {
      List<Component> tooltip = new ArrayList<>();
      tooltip.add(type.name);
      tooltip.add(Component.translatable("emi_ores.distribution.range", new Object[]{anchorTextLong(min), anchorTextLong(max)}).withStyle(ChatFormatting.GRAY));
      if (midLow != null && midHigh != null) {
         if (midLow.equals(midHigh)) {
            tooltip.add(Component.translatable("emi_ores.distribution.middle", new Object[]{anchorTextLong(midLow)}).withStyle(ChatFormatting.GRAY));
         } else {
            tooltip.add(
               Component.translatable("emi_ores.distribution.middle_range", new Object[]{anchorTextLong(midLow), anchorTextLong(midHigh)})
                  .withStyle(ChatFormatting.GRAY)
            );
         }
      }

      return tooltip;
   }

   protected static Component getVeinFreqComponent(int countMin, int countMax, int rarityChance) {
      Component veinFreq;
      if (countMin != -1 && countMax != -1) {
         if (countMin == countMax) {
            veinFreq = Component.translatable("emi_ores.veins_per_chunk", new Object[]{countMin});
         } else {
            veinFreq = Component.translatable("emi_ores.veins_per_chunk_range", new Object[]{countMin, countMax});
         }
      } else if (rarityChance != -1) {
         veinFreq = Component.translatable("emi_ores.rarity_chance", new Object[]{rarityChance});
      } else {
         veinFreq = null;
      }

      return veinFreq;
   }

   protected enum HeightProviderType {
      UNIFORM(0, Component.translatable("emi_ores.distribution.uniform").withStyle(ChatFormatting.BLUE)),
      TRIANGULAR(16, Component.translatable("emi_ores.distribution.triangle").withStyle(ChatFormatting.GREEN)),
      TRAPEZOID(32, Component.translatable("emi_ores.distribution.trapezoid").withStyle(ChatFormatting.RED));

      public final int v;
      public final Component name;

      HeightProviderType(int v, Component name) {
         this.v = v;
         this.name = name;
      }
   }
}
