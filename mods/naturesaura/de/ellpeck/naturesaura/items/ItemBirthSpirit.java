package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

public class ItemBirthSpirit extends ItemGlowing {
   public ItemBirthSpirit() {
      super("birth_spirit");
      NeoForge.EVENT_BUS.register(new ItemBirthSpirit.EventHandler());
   }

   private static class EventHandler {
      @SubscribeEvent
      public void onBabyBorn(BabyEntitySpawnEvent event) {
         LivingEntity parent = event.getParentA();
         if (!parent.level().isClientSide && event.getCausedByPlayer() != null) {
            BlockPos pos = parent.blockPosition();
            int aura = IAuraChunk.getAuraInArea(parent.level(), pos, 30);
            if (aura < 1200000) {
               return;
            }

            int amount = parent.level().random.nextInt(3) + 1;
            ItemEntity item = new ItemEntity(parent.level(), parent.getX(), parent.getY(), parent.getZ(), new ItemStack(ModItems.BIRTH_SPIRIT, amount));
            parent.level().addFreshEntity(item);
            BlockPos spot = IAuraChunk.getHighestSpot(parent.level(), pos, 30, pos);
            IAuraChunk.getAuraChunk(parent.level(), spot).drainAura(spot, 800 * amount);
         }
      }
   }
}
