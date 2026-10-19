package artifacts.mixin.accessors;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
   @Accessor("lastHurtByPlayerTime")
   int getLastHurtByPlayerTime();

   @Accessor("lastHurtByPlayer")
   Player getLastHurtByPlayer();
}
