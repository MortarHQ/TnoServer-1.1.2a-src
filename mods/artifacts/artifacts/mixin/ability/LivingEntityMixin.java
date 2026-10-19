package artifacts.mixin.ability;

import artifacts.event.ArtifactHooks;
import artifacts.extensions.ability.LivingEntityExtensions;
import java.util.Map;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityExtensions {
   @Unique
   private boolean artifacts$hasTickingAbilities;

   public LivingEntityMixin(EntityType<?> entityType, Level level) {
      super(entityType, level);
      throw new UnsupportedOperationException();
   }

   @Unique
   @Override
   public boolean artifacts$hasTickingAbilities() {
      return this.artifacts$hasTickingAbilities;
   }

   @Unique
   @Override
   public void artifacts$setTickingAbilities(boolean hasTickingAbilities) {
      this.artifacts$hasTickingAbilities = hasTickingAbilities;
   }

   @Shadow
   protected abstract ItemStack getLastArmorItem(EquipmentSlot var1);

   @Shadow
   public abstract ItemStack getItemBySlot(EquipmentSlot var1);

   @Inject(method = "handleEquipmentChanges(Ljava/util/Map;)V", at = @At("HEAD"))
   private void handleEquipmentChanges(Map<EquipmentSlot, ItemStack> map, CallbackInfo info) {
      for (EquipmentSlot slot : map.keySet()) {
         if (slot.isArmor()) {
            ItemStack oldStack = this.getLastArmorItem(slot);
            ItemStack newStack = this.getItemBySlot(slot);
            ArtifactHooks.onItemChanged((LivingEntity)this, oldStack, newStack);
         }
      }
   }
}
