package team.lodestar.lodestone.handlers;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import team.lodestar.lodestone.LodestoneLib;

public class ItemEventHandler {
   private static final HashSet<ItemEventHandler.EventResponderSource> LOOKUPS = new HashSet<>();
   public static final ItemEventHandler.EventResponderSource HELD_ITEM = registerLookup(
      new ItemEventHandler.EventResponderSource(LodestoneLib.lodestonePath("held_item"), e -> List.of(e.getMainHandItem()))
   );
   public static final ItemEventHandler.EventResponderSource ARMOR = registerLookup(
      new ItemEventHandler.EventResponderSource(LodestoneLib.lodestonePath("armor"), e -> {
         ArrayList<ItemStack> stacks = new ArrayList<>();

         for (ItemStack stack : e.getArmorSlots()) {
            stacks.add(stack);
         }

         return stacks;
      })
   );

   public static void triggerDeathResponses(LivingDeathEvent event) {
      if (!event.isCanceled()) {
         DamageSource source = event.getSource();
         LivingEntity target = event.getEntity();
         LivingEntity attacker = source.getEntity() instanceof LivingEntity livingAttacker ? livingAttacker : target.getLastAttacker();
         getEventResponders(target)
            .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.incomingDeathEvent(event, attacker, target, stack)));
         if (attacker != null) {
            getEventResponders(attacker)
               .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.outgoingDeathEvent(event, attacker, target, stack)));
         }
      }
   }

   public static void triggerHurtResponses(LivingIncomingDamageEvent event) {
      DamageSource source = event.getSource();
      LivingEntity target = event.getEntity();
      LivingEntity attacker = source.getEntity() instanceof LivingEntity livingAttacker ? livingAttacker : target.getLastAttacker();
      getEventResponders(target)
         .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.incomingDamageEvent(event, attacker, target, stack)));
      if (attacker != null) {
         getEventResponders(attacker)
            .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.outgoingDamageEvent(event, attacker, target, stack)));
      }
   }

   public static void triggerHurtResponses(Pre event) {
      DamageSource source = event.getSource();
      LivingEntity target = event.getEntity();
      LivingEntity attacker = source.getEntity() instanceof LivingEntity livingAttacker ? livingAttacker : target.getLastAttacker();
      getEventResponders(target)
         .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.incomingDamageEvent(event, attacker, target, stack)));
      if (attacker != null) {
         getEventResponders(attacker)
            .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.outgoingDamageEvent(event, attacker, target, stack)));
      }
   }

   public static void triggerHurtResponses(Post event) {
      DamageSource source = event.getSource();
      LivingEntity target = event.getEntity();
      LivingEntity attacker = source.getEntity() instanceof LivingEntity livingAttacker ? livingAttacker : target.getLastAttacker();
      getEventResponders(target)
         .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.finalizedIncomingDamageEvent(event, attacker, target, stack)));
      if (attacker != null) {
         getEventResponders(attacker)
            .forEach(lookup -> lookup.run((eventResponderItem, stack) -> eventResponderItem.finalizedOutgoingDamageEvent(event, attacker, target, stack)));
      }
   }

   public static void addAttributeTooltips(AddAttributeTooltipsEvent event) {
      ItemStack stack = event.getStack();
      if (stack.getItem() instanceof ItemEventHandler.IEventResponder eventResponderItem) {
         eventResponderItem.modifyAttributeTooltipEvent(event);
      }
   }

   public static List<ItemEventHandler.EventResponderLookupResult> getEventResponders(LivingEntity entity) {
      return LOOKUPS.stream().map(s -> s.getEventResponders(entity)).toList();
   }

   public static ItemEventHandler.EventResponderSource registerLookup(ItemEventHandler.EventResponderSource lookup) {
      LOOKUPS.add(lookup);
      return lookup;
   }

   public record EventResponderLookupResult(ItemEventHandler.EventResponderSource source, ArrayList<Pair<ItemEventHandler.IEventResponder, ItemStack>> result) {
      public void run(BiConsumer<ItemEventHandler.IEventResponder, ItemStack> consumer) {
         this.run(ItemEventHandler.IEventResponder.class, consumer);
      }

      public <T extends ItemEventHandler.IEventResponder> void run(Class<T> type, BiConsumer<T, ItemStack> consumer) {
         for (Pair<ItemEventHandler.IEventResponder, ItemStack> pair : this.result) {
            if (type.isInstance(pair.getFirst())) {
               consumer.accept(type.cast(pair.getFirst()), (ItemStack)pair.getSecond());
            }
         }
      }
   }

   public static class EventResponderSource {
      public final ResourceLocation id;
      public final Function<LivingEntity, Collection<ItemStack>> stackFunction;
      public final BiFunction<LivingEntity, ItemStack, ItemEventHandler.IEventResponder> mapperFunction;

      public EventResponderSource(ResourceLocation id, Function<LivingEntity, Collection<ItemStack>> stackFunction) {
         this(id, stackFunction, (entity, stack) -> stack.getItem() instanceof ItemEventHandler.IEventResponder eventResponderItem ? eventResponderItem : null);
      }

      public EventResponderSource(
         ResourceLocation id,
         Function<LivingEntity, Collection<ItemStack>> stackFunction,
         BiFunction<LivingEntity, ItemStack, ItemEventHandler.IEventResponder> mapperFunction
      ) {
         this.id = id;
         this.stackFunction = stackFunction;
         this.mapperFunction = mapperFunction;
      }

      public final ItemEventHandler.EventResponderLookupResult getEventResponders(LivingEntity entity) {
         Collection<ItemStack> sourced = this.stackFunction.apply(entity);
         ArrayList<Pair<ItemEventHandler.IEventResponder, ItemStack>> result = new ArrayList<>();

         for (ItemStack stack : sourced) {
            if (this.mapperFunction.apply(entity, stack) instanceof ItemEventHandler.IEventResponder responderItem) {
               result.add(Pair.of(responderItem, stack));
            }
         }

         return new ItemEventHandler.EventResponderLookupResult(this, result);
      }
   }

   public interface IEventResponder {
      default void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
      }

      default void incomingDamageEvent(LivingIncomingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void outgoingDamageEvent(LivingIncomingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void incomingDamageEvent(Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void outgoingDamageEvent(Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void finalizedIncomingDamageEvent(Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void finalizedOutgoingDamageEvent(Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void incomingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }

      default void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
      }
   }
}
