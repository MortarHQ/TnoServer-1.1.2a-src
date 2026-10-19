package de.ellpeck.naturesaura.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.BreakSpeed;

public class ItemBreakPrevention extends ItemImpl {
   public ItemBreakPrevention() {
      super("break_prevention");
      NeoForge.EVENT_BUS.register(new ItemBreakPrevention.Events());
   }

   public record Data(boolean enabled) {
      public static final Codec<ItemBreakPrevention.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.BOOL.fieldOf("enabled").forGetter(d -> d.enabled)).apply(i, ItemBreakPrevention.Data::new)
      );
      public static final DataComponentType<ItemBreakPrevention.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }

   public static class Events {
      @SubscribeEvent
      public void onAnvilUpdate(AnvilUpdateEvent event) {
         ItemStack stack = event.getLeft();
         if (stack.isDamageableItem()) {
            ItemStack second = event.getRight();
            if (second.getItem() == ModItems.BREAK_PREVENTION) {
               ItemStack output = stack.copy();
               output.set(ItemBreakPrevention.Data.TYPE, new ItemBreakPrevention.Data(true));
               event.setOutput(output);
               event.setMaterialCost(1);
               event.setCost(1L);
            }
         }
      }

      @SubscribeEvent
      public void onBreakSpeed(BreakSpeed event) {
         Player player = event.getEntity();
         if (player != null) {
            ItemStack stack = player.getMainHandItem();
            if (stack.has(ItemBreakPrevention.Data.TYPE) && ((ItemBreakPrevention.Data)stack.get(ItemBreakPrevention.Data.TYPE)).enabled) {
               if (!ElytraItem.isFlyEnabled(stack)) {
                  event.setNewSpeed(0.0F);
               }
            }
         }
      }

      @SubscribeEvent
      @OnlyIn(Dist.CLIENT)
      public void onTooltip(ItemTooltipEvent event) {
         ItemStack stack = event.getItemStack();
         if (stack.has(ItemBreakPrevention.Data.TYPE) && ((ItemBreakPrevention.Data)stack.get(ItemBreakPrevention.Data.TYPE)).enabled) {
            List<Component> tooltip = event.getToolTip();
            tooltip.add(Component.translatable("info.naturesaura.break_prevention").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
            if (!ElytraItem.isFlyEnabled(stack)) {
               if (!tooltip.isEmpty()) {
                  Component head = tooltip.getFirst();
                  if (head instanceof MutableComponent) {
                     ((MutableComponent)head).append(Component.translatable("info.naturesaura.broken").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
                  }
               }
            }
         }
      }
   }
}
