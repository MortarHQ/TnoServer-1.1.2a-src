package com.mortal_cultivation.item;

import com.mortal_cultivation.client.renderer.RuneTableItemRenderer;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class RuneTableItem extends BlockItem implements GeoItem {
   private static final String STORED_RUNES_TAG = "StoredRunes";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public RuneTableItem(Block block, Properties properties) {
      super(block, properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      int storedRunes = getStoredRuneCount(stack);
      if (storedRunes > 0) {
         tooltip.add(Component.translatable("item.mortal_cultivation.rune_table.stored_runes", new Object[]{storedRunes, 500}).withStyle(ChatFormatting.GOLD));
      }

      super.appendHoverText(stack, context, tooltip, flag);
   }

   public static int getStoredRuneCount(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      return Mth.clamp(tag.getInt("StoredRunes"), 0, 500);
   }

   public static void setStoredRuneCount(ItemStack stack, int storedRunes) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      int clampedRunes = Mth.clamp(storedRunes, 0, 500);
      if (clampedRunes > 0) {
         tag.putInt("StoredRunes", clampedRunes);
      } else {
         tag.remove("StoredRunes");
      }

      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         private RuneTableItemRenderer renderer;

         @NotNull
         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            if (this.renderer == null) {
               this.renderer = new RuneTableItemRenderer();
            }

            return this.renderer;
         }
      });
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
