package artifacts.neoforge.integration.curios;

import artifacts.item.WearableArtifactItem;
import artifacts.registry.ModDataComponents;
import artifacts.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

public record WearableArtifactCurio(WearableArtifactItem item) implements ICurioItem {
   public DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
      return DamageSourceHelper.shouldDestroyWornItemsOnDeath(slotContext.entity())
         ? DropRule.DESTROY
         : super.getDropRule(slotContext, source, recentlyHit, stack);
   }

   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return this.item.getFoodProperties(stack, slotContext.entity()) == null;
   }

   public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
      SoundEvent equipSound = (SoundEvent)stack.get(ModDataComponents.EQUIP_SOUND);
      if (equipSound != null) {
         LivingEntity entity = slotContext.entity();
         Vec3 pos = entity.position();
         slotContext.entity().level().playSound(null, pos.x(), pos.y(), pos.z(), equipSound, slotContext.entity().getSoundSource());
      }
   }
}
