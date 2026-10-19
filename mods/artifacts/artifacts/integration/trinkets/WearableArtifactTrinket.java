package artifacts.integration.trinkets;

import artifacts.item.WearableArtifactItem;
import artifacts.registry.ModDataComponents;
import artifacts.util.DamageSourceHelper;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record WearableArtifactTrinket(WearableArtifactItem item) implements Trinket {
   public DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
      return DamageSourceHelper.shouldDestroyWornItemsOnDeath(entity) ? DropRule.DESTROY : super.getDropRule(stack, slot, entity);
   }

   public boolean canEquipFromUse(ItemStack stack, LivingEntity entity) {
      return !stack.has(DataComponents.FOOD);
   }

   public Holder<SoundEvent> getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
      SoundEvent equipSound = (SoundEvent)stack.get(ModDataComponents.EQUIP_SOUND.get());
      return equipSound != null ? Holder.direct(equipSound) : super.getEquipSound(stack, slot, entity);
   }
}
