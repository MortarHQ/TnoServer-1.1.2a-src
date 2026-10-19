package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.packet.PacketClient;
import de.ellpeck.naturesaura.packet.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class ItemDeathRing extends ItemImpl {
   public ItemDeathRing() {
      super("death_ring", new Properties().stacksTo(1));
      NeoForge.EVENT_BUS.register(new ItemDeathRing.Events());
   }

   public static class Events {
      @SubscribeEvent
      public void onDeath(LivingDeathEvent event) {
         LivingEntity entity = event.getEntity();
         if (!entity.level().isClientSide && entity instanceof Player) {
            ItemStack equipped = Helper.getEquippedItem(s -> s.getItem() == ModItems.DEATH_RING, (Player)entity, false);
            if (!equipped.isEmpty()) {
               entity.setHealth(entity.getMaxHealth() / 2.0F);
               entity.removeAllEffects();
               entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 500, 1));
               CompoundTag data = new CompoundTag();
               data.putInt("id", entity.getId());
               PacketHandler.sendToAllAround(entity.level(), entity.blockPosition(), 32, new PacketClient(1, data));
               equipped.shrink(1);
               event.setCanceled(true);
            }
         }
      }
   }
}
