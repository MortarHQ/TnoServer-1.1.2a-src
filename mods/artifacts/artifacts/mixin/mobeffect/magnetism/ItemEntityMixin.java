package artifacts.mixin.mobeffect.magnetism;

import artifacts.extensions.mobeffect.magnetism.ItemEntityExtensions;
import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemEntity.class)
public class ItemEntityMixin implements ItemEntityExtensions {
   @Unique
   private UUID artifacts$thrower;

   @Override
   public void artifacts$setThrower(LivingEntity entity) {
      this.artifacts$thrower = entity.getUUID();
   }

   @Override
   public boolean artifacts$wasThrownBy(LivingEntity entity) {
      return entity.getUUID().equals(this.artifacts$thrower);
   }
}
