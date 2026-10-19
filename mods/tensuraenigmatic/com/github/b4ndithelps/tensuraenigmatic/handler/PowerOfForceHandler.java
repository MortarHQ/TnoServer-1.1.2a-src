package com.github.b4ndithelps.tensuraenigmatic.handler;

import com.github.b4ndithelps.tensuraenigmatic.TensuraEnigmatic;
import com.github.b4ndithelps.tensuraenigmatic.config.TensuraEnigmaticConfig;
import com.github.b4ndithelps.tensuraenigmatic.items.PowerOfForceRing;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.util.CurioHelper;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class PowerOfForceHandler {
   private static final Set<ResourceLocation> EARTH_SPIRIT_ENTITY_IDS = Set.of(
      ResourceLocation.fromNamespaceAndPath("tensura", "beast_gnome"), ResourceLocation.fromNamespaceAndPath("tensura", "war_gnome")
   );

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide() && player.tickCount % 20 == 0 && CurioHelper.hasCurio(player, TensuraEnigmaticItems.POWER_OF_FORCE)) {
         double maxAura = TensuraEnergyHelper.getMaxAura(player);
         TensuraEnergyHelper.restoreAura(player, maxAura * PowerOfForceRing.getAuraRecoveryPercentage() / 100.0);
      }
   }

   @SubscribeEvent
   public static void onLivingDrops(LivingDropsEvent event) {
      if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof Player) {
         ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
         if (EARTH_SPIRIT_ENTITY_IDS.contains(entityId)
            && !(event.getEntity().getRandom().nextDouble() >= (Double)TensuraEnigmaticConfig.POWER_OF_FORCE_DROP_CHANCE.get())) {
            ItemStack ring = TensuraEnigmaticItems.POWER_OF_FORCE.toStack();
            Entity entity = event.getEntity();
            event.getDrops().add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), ring));
            TensuraEnigmatic.LOGGER.debug("Dropped Power of Force from {}.", entityId);
         }
      }
   }
}
