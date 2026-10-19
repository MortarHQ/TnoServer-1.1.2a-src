package artifacts.integration.accessories;

import artifacts.item.WearableArtifactItem;
import artifacts.registry.ModDataComponents;
import artifacts.util.DamageSourceHelper;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public record WearableArtifactAccessory(WearableArtifactItem item) implements Accessory {
   public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
      return DamageSourceHelper.shouldDestroyWornItemsOnDeath(reference.entity()) ? DropRule.DESTROY : super.getDropRule(stack, reference, source);
   }

   public SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
      SoundEvent soundEvent = (SoundEvent)stack.get(ModDataComponents.EQUIP_SOUND.get());
      return soundEvent != null ? new SoundEventData(Holder.direct(soundEvent), 1.0F, 1.0F) : super.getEquipSound(stack, reference);
   }

   public boolean canEquipFromUse(ItemStack stack) {
      return stack.get(DataComponents.FOOD) == null;
   }
}
