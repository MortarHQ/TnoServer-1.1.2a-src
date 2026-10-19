package me.kall.nanhealthfixer;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

@Mod("nanhealthfixer")
public final class NaNHealthFixer {
   public static final String MOD_ID = "nanhealthfixer";

   public NaNHealthFixer(IEventBus modBus, Dist dist, ModContainer container) {
      IEventBus bus = NeoForge.EVENT_BUS;
      bus.addListener(event -> this.validateHealth(event.getEntity()));
      bus.addListener(event -> {
         if (event.getEntity() instanceof LivingEntity entity) {
            this.validateHealth(entity);
         }
      });
      bus.addListener(event -> this.validateHealth(event.getEntity()));
      bus.addListener(event -> this.validateHealth(event.getEntity()));
   }

   private void validateHealth(@NotNull LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         float health = entity.getHealth();
         if (Float.isNaN(health) || health < 0.0F) {
            entity.setHealth(0.0F);
         }
      }
   }
}
