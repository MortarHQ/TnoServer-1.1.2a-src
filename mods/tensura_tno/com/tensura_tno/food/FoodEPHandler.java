package com.tensura_tno.food;

import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;

public final class FoodEPHandler {
   private static final ConcurrentHashMap<UUID, Long> LAST_EAT_TICK = new ConcurrentHashMap<>();

   private FoodEPHandler() {
   }

   public static void onItemCrafted(ItemCraftedEvent event) {
      if (!event.getEntity().level().isClientSide()) {
         CookStamp.tryStamp(event.getCrafting(), event.getEntity());
      }
   }

   public static void onItemSmelted(ItemSmeltedEvent event) {
      if (!event.getEntity().level().isClientSide()) {
         CookStamp.tryStamp(event.getSmelting(), event.getEntity());
      }
   }

   public static void onItemUseStart(Start event) {
      if (event.getEntity() instanceof Player player) {
         if (!player.level().isClientSide()) {
            ItemStack stack = event.getItem();
            if (stack.getFoodProperties(player) != null) {
               CookStamp.tryStamp(stack, player);
            }
         }
      }
   }

   public static void onItemUseFinish(Finish event) {
      ItemStack stack = event.getItem();
      if (stack.getFoodProperties(event.getEntity()) != null) {
         LivingEntity entity = event.getEntity();
         if (!entity.level().isClientSide()) {
            long now = entity.level().getGameTime();
            Long prev = LAST_EAT_TICK.put(entity.getUUID(), now);
            if (prev == null || prev != now) {
               if (CookStamp.isStamped(stack)) {
                  double bonus = CookStamp.getBonus(stack);
                  double effectScale = CookStamp.getEffectScale(stack);
                  double ep = FoodEPManager.resolveEP(stack, entity);
                  if (ep > 0.0) {
                     addMaxEPCorrectly(entity, ep * bonus * effectScale);
                  }

                  double mp = FoodEPManager.resolveMP(stack, entity);
                  if (mp > 0.0) {
                     EnergyHelper.gainMagicule(entity, mp * effectScale, GainType.NORMAL);
                  }

                  double ap = FoodEPManager.resolveAP(stack, entity);
                  if (ap > 0.0) {
                     EnergyHelper.gainAura(entity, ap * effectScale, GainType.NORMAL);
                  }
               }
            }
         }
      }
   }

   private static void addMaxEPCorrectly(LivingEntity entity, double totalEP) {
      double half = totalEP / 2.0;
      AttributeInstance aura = entity.getAttribute(TensuraAttributes.MAX_AURA);
      if (aura != null) {
         aura.setBaseValue(aura.getBaseValue() + half);
      }

      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
      if (magicule != null) {
         magicule.setBaseValue(magicule.getBaseValue() + half);
      }
   }

   public static void onTooltip(ItemTooltipEvent event) {
      ItemStack stack = event.getItemStack();
      if (stack.getFoodProperties(null) != null) {
         if (CookStamp.isStamped(stack)) {
            List<Component> tooltip = event.getToolTip();
            double bonus = CookStamp.getBonus(stack);
            double effectScale = CookStamp.getEffectScale(stack);
            int bonusPct = (int)Math.round((bonus - 1.0) * 100.0);
            int scalePct = (int)Math.round(effectScale * 100.0);
            String epStr = FoodEPManager.getTooltipEP(stack);
            if (epStr != null && !isZero(epStr)) {
               Component epLine = Component.translatable("tensura_tno.food.tooltip.ep", new Object[]{epStr}).withStyle(ChatFormatting.DARK_AQUA);
               if (bonusPct != 0) {
                  String sign = bonusPct > 0 ? "+" : "";
                  epLine = epLine.copy()
                     .append(Component.literal(" (" + sign + bonusPct + "%)").withStyle(bonusPct > 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
               }

               if (scalePct != 100) {
                  epLine = epLine.copy().append(Component.literal(" (" + scalePct + "%)").withStyle(ChatFormatting.GRAY));
               }

               tooltip.add(epLine);
            }

            String mpStr = FoodEPManager.getTooltipMP(stack);
            if (mpStr != null && !isZero(mpStr)) {
               tooltip.add(Component.translatable("tensura_tno.food.tooltip.mp", new Object[]{mpStr}).withStyle(ChatFormatting.BLUE));
            }

            String apStr = FoodEPManager.getTooltipAP(stack);
            if (apStr != null && !isZero(apStr)) {
               tooltip.add(Component.translatable("tensura_tno.food.tooltip.ap", new Object[]{apStr}).withStyle(ChatFormatting.GOLD));
            }
         }
      }
   }

   private static boolean isZero(String value) {
      try {
         String v = value.trim();
         if (v.endsWith("%")) {
            v = v.substring(0, v.length() - 1);
         }

         return Double.parseDouble(v) == 0.0;
      } catch (NumberFormatException e) {
         return false;
      }
   }
}
