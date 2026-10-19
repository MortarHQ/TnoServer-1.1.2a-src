package com.mortal_cultivation.event;

import com.mortal_cultivation.entity.TaoistArrayEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
public class CultivationAnimationHandler {
   private static boolean wasHolding = false;
   private static boolean isMeditating = false;
   private static TaoistArrayEntity currentArray = null;

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null && mc.level != null) {
         AbstractClientPlayer player = mc.player;
         boolean isHolding = TickingSkill.isTickingSkill(player, (ManasSkill)CultivationSkills.CULTIVATING_THE_MYSTIC_DAO.get());
         if (isHolding != wasHolding) {
            if (isHolding) {
               startMeditation(player);
               sendCreateArrayMessage(player);
            } else {
               stopMeditation(player);
               sendRemoveArrayMessage(player);
            }

            wasHolding = isHolding;
         }
      }
   }

   private static void startMeditation(AbstractClientPlayer player) {
      isMeditating = true;
   }

   private static void stopMeditation(AbstractClientPlayer player) {
      isMeditating = false;
   }

   public static boolean isMeditating() {
      return isMeditating;
   }

   private static void createTaoistArray(AbstractClientPlayer player) {
      try {
         removeTaoistArray();
         Vec3 playerPos = player.position();
         Vec3 arrayPos = new Vec3(playerPos.x, playerPos.y - 0.1, playerPos.z);
         currentArray = new TaoistArrayEntity((EntityType<?>)CultivationEntities.TAOIST_ARRAY.get(), player.level());
         currentArray.setPos(arrayPos.x, arrayPos.y, arrayPos.z);
         player.level().addFreshEntity(currentArray);
      } catch (Exception var3) {
      }
   }

   private static void removeTaoistArray() {
      try {
         if (currentArray != null && !currentArray.isRemoved()) {
            currentArray.discard();
         }

         currentArray = null;
      } catch (Exception var1) {
      }
   }

   private static void sendCreateArrayMessage(AbstractClientPlayer player) {
   }

   private static void sendRemoveArrayMessage(AbstractClientPlayer player) {
   }
}
