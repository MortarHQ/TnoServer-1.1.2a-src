package com.github.b4ndithelps.tennogamenolife.client.render;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT)
public class ChargeBarRenderer {
   private static boolean isCharging = false;
   private static long chargeStartTime = 0L;
   private static String chargingSkill = "tengeki";

   public static void startCharging() {
      startCharging("tengeki");
   }

   public static void startCharging(String skillName) {
      isCharging = true;
      chargeStartTime = System.currentTimeMillis();
      chargingSkill = skillName;
   }

   public static void stopCharging() {
      isCharging = false;
   }

   @SubscribeEvent
   public static void onRenderGui(Post event) {
      if (isCharging) {
         Minecraft mc = Minecraft.getInstance();
         int screenWidth = mc.getWindow().getGuiScaledWidth();
         int screenHeight = mc.getWindow().getGuiScaledHeight();
         long currentTime = System.currentTimeMillis();
         long chargeTime = (currentTime - chargeStartTime) / 1000L;
         Component component;
         if ("far_cry".equals(chargingSkill)) {
            long farCryChargeTime = 10L;
            if (chargeTime >= farCryChargeTime) {
               component = Component.translatable("skill.far_cry.charge_complete").withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN).withBold(true));
            } else {
               component = Component.translatable("skill.far_cry.charging", new Object[]{chargeTime, farCryChargeTime})
                  .withStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW).withBold(true));
            }
         } else if (chargeTime >= 60L) {
            component = Component.translatable("tennogamenolife.charging_max").withStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW).withBold(true));
         } else if (chargeTime >= 5L) {
            component = Component.translatable("tennogamenolife.charging_complete", new Object[]{chargeTime})
               .withStyle(Style.EMPTY.withColor(ChatFormatting.RED).withBold(true));
         } else {
            component = Component.translatable("tennogamenolife.charging", new Object[]{chargeTime})
               .withStyle(Style.EMPTY.withColor(ChatFormatting.RED).withBold(true));
         }

         event.getGuiGraphics().drawString(mc.font, component, (screenWidth - mc.font.width(component)) / 2, screenHeight - 50, 16777215);
      }
   }
}
