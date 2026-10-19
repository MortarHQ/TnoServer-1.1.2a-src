package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/LivingDropsEvent")
@NativeTypeRegistration(value = LivingDropsEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingDropsEvent")
public class ExpandLivingDropsEvent {
   @ZenEvent.Bus
   public static final IEventBus<LivingDropsEvent> BUS = IEventBus.cancelable(
      LivingDropsEvent.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("source")
   public static DamageSource getSource(LivingDropsEvent internal) {
      return internal.getSource();
   }

   @ZenCodeType.Getter("drops")
   public static List<IItemStack> getDrops(LivingDropsEvent internal) {
      return internal.getDrops().stream().<ItemStack>map(ItemEntity::getItem).map(IItemStack::of).collect(Collectors.toList());
   }

   @ZenCodeType.Setter("drops")
   public static void setDrops(LivingDropsEvent internal, List<IItemStack> drops) {
      internal.getDrops().clear();
      drops.forEach(drop -> addDrop(internal, drop));
   }

   @ZenCodeType.Getter("isRecentlyHit")
   public static boolean isRecentlyHit(LivingDropsEvent internal) {
      return internal.isRecentlyHit();
   }

   @ZenCodeType.Method
   public static void addDrop(LivingDropsEvent internal, IItemStack stack) {
      LivingEntity entity = internal.getEntity();
      internal.getDrops().add(new ItemEntity(entity.level(), entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ() + 0.5, stack.getImmutableInternal()));
   }

   @ZenCodeType.Method
   public static void removeDrop(LivingDropsEvent internal, IIngredient ingredient) {
      internal.getDrops().removeIf(itemEntity -> ingredient.matches(IItemStack.ofMutable(itemEntity.getItem())));
   }
}
