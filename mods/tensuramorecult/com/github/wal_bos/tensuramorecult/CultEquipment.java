package com.github.wal_bos.tensuramorecult;

import io.github.manasmods.tensura.handler.GearHandler;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public final class CultEquipment {
   public static final Map<String, EquipmentSlot> GEAR = Map.ofEntries(
      Map.entry("azazel_helmet", EquipmentSlot.HEAD),
      Map.entry("azazel_chestplate", EquipmentSlot.CHEST),
      Map.entry("azazel_leggings", EquipmentSlot.LEGS),
      Map.entry("azazel_boots", EquipmentSlot.FEET),
      Map.entry("azazel_spear", EquipmentSlot.MAINHAND),
      Map.entry("azazel_scythe", EquipmentSlot.MAINHAND),
      Map.entry("azazel_shield", EquipmentSlot.OFFHAND),
      Map.entry("azazel_trophy", EquipmentSlot.HEAD),
      Map.entry("azazel_trophy_stage2", EquipmentSlot.HEAD),
      Map.entry("azazel_trophy_stage3", EquipmentSlot.HEAD)
   );

   public static String name(ItemStack stack) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      return id.getNamespace().equals("netherman") ? id.getPath() : "";
   }

   public static boolean weapon(ItemStack stack) {
      return GEAR.get(name(stack)) == EquipmentSlot.MAINHAND;
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void attributes(ItemAttributeModifierEvent event) {
      String gearName = name(event.getItemStack());
      EquipmentSlot slot = GEAR.get(gearName);
      if (slot != null) {
         if (slot == EquipmentSlot.MAINHAND) {
            double attackFloor = (Double)CultConfig.WEAPON_ATTACK_FLOOR.get();
            if ((Boolean)CultConfig.MATCH_HIHIIROKANE_ATTACK.get()) {
               String benchmarkId = gearName.equals("azazel_spear") ? "hihiirokane_spear" : "hihiirokane_scythe";
               ItemStack benchmark = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("tensura", benchmarkId)));
               double[] attack = new double[]{0.0};
               benchmark.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
                  if (attribute.equals(Attributes.ATTACK_DAMAGE) && modifier.operation() == Operation.ADD_VALUE) {
                     attack[0] += modifier.amount();
                  }
               });
               attackFloor = Math.max(attackFloor, attack[0]);
            }

            floor(event, Attributes.ATTACK_DAMAGE, attackFloor, slot, "attack");
            add(event, TensuraAttributes.MAX_AURA, (Double)CultConfig.WEAPON_AURA.get(), slot, "aura");
            add(event, TensuraAttributes.MAX_MAGICULE, (Double)CultConfig.WEAPON_MAGICULE.get(), slot, "magicule");
         } else if (slot == EquipmentSlot.OFFHAND) {
            add(event, TensuraAttributes.MAX_AURA, (Double)CultConfig.SHIELD_AURA.get(), slot, "aura");
            add(event, TensuraAttributes.MAX_MAGICULE, (Double)CultConfig.SHIELD_MAGICULE.get(), slot, "magicule");
            add(event, TensuraAttributes.MAX_SPIRITUAL_HEALTH, (Double)CultConfig.SHIELD_SPIRITUAL_HEALTH.get(), slot, "spiritual_health");
            add(event, TensuraAttributes.MAGIC_RESISTANCE, (Double)CultConfig.SHIELD_MAGIC_RESISTANCE.get(), slot, "magic_resistance");
         } else {
            double armor = switch (slot) {
               case HEAD -> CultConfig.HELMET_ARMOR.get();
               case CHEST -> CultConfig.CHEST_ARMOR.get();
               case LEGS -> CultConfig.LEGS_ARMOR.get();
               case FEET -> CultConfig.BOOTS_ARMOR.get();
               default -> 0.0;
            };
            floor(event, Attributes.ARMOR, armor, slot, "armor");
            floor(event, Attributes.ARMOR_TOUGHNESS, (Double)CultConfig.ARMOR_TOUGHNESS.get(), slot, "toughness");
            floor(event, Attributes.KNOCKBACK_RESISTANCE, (Double)CultConfig.ARMOR_KNOCKBACK_RESISTANCE.get(), slot, "knockback");
            add(event, TensuraAttributes.MAX_AURA, (Double)CultConfig.ARMOR_AURA.get(), slot, "aura");
            add(event, TensuraAttributes.MAX_MAGICULE, (Double)CultConfig.ARMOR_MAGICULE.get(), slot, "magicule");
            add(event, TensuraAttributes.MAX_SPIRITUAL_HEALTH, (Double)CultConfig.ARMOR_SPIRITUAL_HEALTH.get(), slot, "spiritual_health");
            add(event, TensuraAttributes.FLAME_RESISTANCE, (Double)CultConfig.ARMOR_FLAME_RESISTANCE.get(), slot, "flame_resistance");
            add(event, TensuraAttributes.DARKNESS_RESISTANCE, (Double)CultConfig.ARMOR_DARKNESS_RESISTANCE.get(), slot, "darkness_resistance");
         }
      }
   }

   private static void floor(ItemAttributeModifierEvent event, Holder<Attribute> attribute, double minimum, EquipmentSlot slot, String key) {
      double amount = event.getModifiers()
         .stream()
         .filter(entry -> entry.attribute().equals(attribute) && entry.slot().test(slot) && entry.modifier().operation() == Operation.ADD_VALUE)
         .mapToDouble(entry -> entry.modifier().amount())
         .sum();
      if (amount < minimum) {
         add(event, attribute, minimum - amount, slot, key);
      }
   }

   private static void add(ItemAttributeModifierEvent event, Holder<Attribute> attribute, double value, EquipmentSlot slot, String key) {
      if (value != 0.0) {
         event.addModifier(
            attribute,
            new AttributeModifier(TensuraMoreCult.id("gear_" + key + "_" + slot.getName()), value, Operation.ADD_VALUE),
            EquipmentSlotGroup.bySlot(slot)
         );
      }
   }

   private static double fallbackEP(EquipmentSlot slot) {
      if (slot == EquipmentSlot.MAINHAND) {
         return (Double)CultConfig.WEAPON_ITEM_EP.get();
      } else {
         return slot == EquipmentSlot.OFFHAND ? (Double)CultConfig.SHIELD_ITEM_EP.get() : (Double)CultConfig.ARMOR_ITEM_EP.get();
      }
   }

   private static double legacyEP(EquipmentSlot slot) {
      return slot == EquipmentSlot.MAINHAND ? 10000.0 : 15000.0;
   }

   public static void initialize(Level level, ItemStack stack) {
      if (!level.isClientSide() && !stack.isEmpty()) {
         String gearName = name(stack);
         EquipmentSlot slot = GEAR.get(gearName);
         if (slot != null) {
            if ((Boolean)CultConfig.MATCH_HIHIIROKANE_DURABILITY.get() && stack.isDamageableItem()) {
               String benchmarkId = switch (slot) {
                  case HEAD -> "hihiirokane_helmet";
                  case CHEST -> "hihiirokane_chestplate";
                  case LEGS -> "hihiirokane_leggings";
                  case FEET -> "hihiirokane_boots";
                  default -> "hihiirokane_sword";
               };
               int durability = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("tensura", benchmarkId)))
                  .getMaxDamage();
               int oldMaximum = stack.getMaxDamage();
               if (durability > oldMaximum) {
                  int damage = oldMaximum <= 0 ? 0 : (int)Math.ceil((double)stack.getDamageValue() * durability / oldMaximum);
                  stack.set(DataComponents.MAX_DAMAGE, durability);
                  stack.setDamageValue(damage);
               }
            }

            if ((Double)stack.getOrDefault((DataComponentType)TensuraDataComponents.MAX_EP.get(), 0.0) <= 0.0) {
               GearHandler.initiateGearExistence(level, stack);
            }

            double currentMax = (Double)stack.getOrDefault((DataComponentType)TensuraDataComponents.MAX_EP.get(), 0.0);
            double configuredMax = fallbackEP(slot);
            boolean legacy = Math.abs(currentMax - legacyEP(slot)) < 0.001;
            if (currentMax <= 0.0 || legacy) {
               stack.set((DataComponentType)TensuraDataComponents.MAX_EP.get(), configuredMax);
               if (!stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
                  stack.set((DataComponentType)TensuraDataComponents.EP.get(), configuredMax);
               }

               if (!stack.has((DataComponentType)TensuraDataComponents.EP_DURABILITY.get())) {
                  stack.set((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), configuredMax);
               } else if (legacy) {
                  double durability = (Double)stack.getOrDefault((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), configuredMax);
                  if (durability > configuredMax) {
                     stack.set((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), configuredMax);
                  }
               }
            }

            if (!stack.has((DataComponentType)TensuraDataComponents.EP_GAIN.get())) {
               stack.set((DataComponentType)TensuraDataComponents.EP_GAIN.get(), 0.0);
            }
         }
      }
   }

   @SubscribeEvent
   public static void inventory(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide() && player.tickCount % 40 == 0) {
         for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            initialize(player.level(), player.getInventory().getItem(i));
         }
      }
   }

   @SubscribeEvent
   public static void equipment(LivingEquipmentChangeEvent event) {
      initialize(event.getEntity().level(), event.getTo());
   }

   @SubscribeEvent
   public static void dropped(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof ItemEntity item) {
         initialize(event.getLevel(), item.getItem());
      }
   }

   private CultEquipment() {
   }
}
