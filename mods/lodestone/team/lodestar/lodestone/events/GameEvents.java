package team.lodestar.lodestone.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import team.lodestar.lodestone.handlers.ItemEventHandler;
import team.lodestar.lodestone.handlers.LodestoneAttributeEventHandler;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.systems.enchanting.LodestoneSlotBasedEnchantmentAttributeEffect;

@EventBusSubscriber
public class GameEvents {
   @SubscribeEvent
   public static void modifyAttributes(ItemAttributeModifierEvent event) {
      LodestoneSlotBasedEnchantmentAttributeEffect.modifyAttributes(event);
   }

   @SubscribeEvent
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      ItemEventHandler.triggerHurtResponses(event);
   }

   @SubscribeEvent
   public static void onDamage(Pre event) {
      LodestoneAttributeEventHandler.processAttributes(event);
      ItemEventHandler.triggerHurtResponses(event);
   }

   @SubscribeEvent
   public static void onDamage(Post event) {
      ItemEventHandler.triggerHurtResponses(event);
      LodestoneAttributeEventHandler.triggerMagicDamage(event);
   }

   @SubscribeEvent
   public static void onDeath(LivingDeathEvent event) {
      ItemEventHandler.triggerDeathResponses(event);
   }

   @SubscribeEvent
   public static void entityJoin(EntityJoinLevelEvent event) {
      WorldEventHandler.playerJoin(event);
   }

   @SubscribeEvent
   public static void worldTick(net.neoforged.neoforge.event.tick.LevelTickEvent.Post event) {
      WorldEventHandler.worldTick(event);
   }
}
