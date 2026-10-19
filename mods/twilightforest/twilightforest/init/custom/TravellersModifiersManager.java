package twilightforest.init.custom;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.components.item.ItemDisplayContents;
import twilightforest.init.TFAttributeModifiers;
import twilightforest.init.TFDataComponents;
import twilightforest.item.travellers_gear.TravellersArmorBeltItem;
import twilightforest.item.travellers_gear.modifiers.BuiltinTravellersComponentModifier;
import twilightforest.item.travellers_gear.modifiers.InsertableTravellersModifier;
import twilightforest.item.travellers_gear.modifiers.TooltipStringInterpolator;
import twilightforest.item.travellers_gear.modifiers.TransferableComponentModifier;
import twilightforest.item.travellers_gear.modifiers.TransferableTravellersModifier;
import twilightforest.item.travellers_gear.modifiers.TravellersComponentModifier;
import twilightforest.item.travellers_gear.modifiers.TravellersEntryModifier;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;

public class TravellersModifiersManager {
   public static final ResourceKey<TravellersModifier> AUTO_REPAIR_MODIFIER = makeKey("auto_repair");
   public static final ResourceKey<TravellersModifier> ZOOM_ABILITY = makeKey("zoom");
   public static final ResourceKey<TravellersModifier> AQUATIC_AGILITY_MODIFIER = makeKey("aquatic_agility");
   public static final ResourceKey<TravellersModifier> RED_THREAD_VISION_MODIFIER = makeKey("red_thread_vision");
   public static final ResourceKey<TravellersModifier> ALL_NIGHT_GOGGLES_MODIFIER = makeKey("all_night_goggles");
   public static final ResourceKey<TravellersModifier> ITEM_DISPLAY_MODIFIER = makeKey("item_display");
   public static final ResourceKey<TravellersModifier> SWIFT_SWIM_ABILITY = makeKey("swift_swim");
   public static final ResourceKey<TravellersModifier> STEALTH_MODIFIER = makeKey("stealth");
   public static final ResourceKey<TravellersModifier> ARROW_MAGNETISM_MODIFIER = makeKey("arrow_magnetism");
   public static final ResourceKey<TravellersModifier> EFFICIENT_EATER_MODIFIER = makeKey("efficient_eater");
   public static final ResourceKey<TravellersModifier> PERFECT_DODGE_MODIFIER = makeKey("perfect_dodge");
   public static final ResourceKey<TravellersModifier> HASTE_MODIFIER = makeKey("haste");
   public static final ResourceKey<TravellersModifier> SWAP_HOTBAR_ABILITY = makeKey("swap_hotbar_ability");
   public static final ResourceKey<TravellersModifier> SWAP_HOTBAR_MODIFIER = makeKey("swap_hotbar");
   public static final ResourceKey<TravellersModifier> HIGH_JUMP_ABILITY = makeKey("high_jump");
   public static final ResourceKey<TravellersModifier> GRADUAL_GLIDE_MODIFIER = makeKey("gradual_glide");
   public static final ResourceKey<TravellersModifier> AGILE_RANGER_MODIFIER = makeKey("agile_ranger");
   public static final ResourceKey<TravellersModifier> DOUBLE_JUMP_MODIFIER = makeKey("double_jump");
   public static final ResourceKey<TravellersModifier> SIDESTEP_MODIFIER = makeKey("side_step");
   public static final ResourceKey<TravellersModifier> STEP_UP_ABILITY = makeKey("step_up");
   public static final ResourceKey<TravellersModifier> STRAIGHT_AHEAD_MODIFIER = makeKey("straight_ahead");
   public static final ResourceKey<TravellersModifier> SLIMY_SOLES_MODIFIER = makeKey("slimy_soles");
   public static final ResourceKey<TravellersModifier> UNRESTRAINED_MODIFIER = makeKey("unrestrained");
   public static final ResourceKey<TravellersModifier> WATER_WALK_MODIFIER = makeKey("water_walk");
   public static final Set<ResourceKey<TravellersModifier>> ALWAYS_ACTIVE = Set.of(AUTO_REPAIR_MODIFIER);
   private static final Map<ResourceKey<TravellersModifier>, TravellersModifier> CACHED_MODIFIERS = new ConcurrentHashMap<>();
   private static final Set<ResourceKey<TravellersModifier>> MISSING_MODIFIERS = ConcurrentHashMap.newKeySet();

   private static ResourceKey<TravellersModifier> makeKey(String name) {
      return ResourceKey.create(TFRegistries.Keys.TRAVELLERS_MODIFIERS, TwilightForestMod.prefix(name));
   }

   public static void bootstrap(BootstrapContext<TravellersModifier> context) {
      context.register(
         AUTO_REPAIR_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.ARMOR, (DataComponentType<Float>)TFDataComponents.AUTO_REPAIR_PROBABILITY.get(), 0.001F, componentText(AUTO_REPAIR_MODIFIER)
         )
      );
      context.register(
         ZOOM_ABILITY, new BuiltinTravellersComponentModifier(EquipmentSlotGroup.HEAD, (DataComponentType<?>)TFDataComponents.ZOOM_ABILITY_MODIFIER.get())
      );
      context.register(
         AQUATIC_AGILITY_MODIFIER,
         new TravellersEntryModifier(
            EquipmentSlotGroup.HEAD,
            List.of(
               new Entry(Attributes.OXYGEN_BONUS, TFAttributeModifiers.TRAVELLERS_AQUATIC_AGILITY_OXYGEN, EquipmentSlotGroup.HEAD),
               new Entry(Attributes.SUBMERGED_MINING_SPEED, TFAttributeModifiers.TRAVELLERS_AQUATIC_AGILITY_MINING, EquipmentSlotGroup.HEAD)
            ),
            TFDataComponents.AQUATIC_AGILITY,
            componentText(AQUATIC_AGILITY_MODIFIER),
            false
         )
      );
      context.register(
         RED_THREAD_VISION_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.HEAD,
            (DataComponentType<Unit>)TFDataComponents.RED_THREAD_VISION.get(),
            Unit.INSTANCE,
            componentText(RED_THREAD_VISION_MODIFIER)
         )
      );
      context.register(
         ALL_NIGHT_GOGGLES_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.HEAD,
            (DataComponentType<Unit>)TFDataComponents.ALL_NIGHT_GOGGLES.get(),
            Unit.INSTANCE,
            componentText(ALL_NIGHT_GOGGLES_MODIFIER)
         )
      );
      context.register(
         ITEM_DISPLAY_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.HEAD,
            (DataComponentType<ItemDisplayContents>)TFDataComponents.ITEM_DISPLAY.get(),
            ItemDisplayContents.EMPTY,
            componentText(ITEM_DISPLAY_MODIFIER)
         )
      );
      context.register(
         SWIFT_SWIM_ABILITY,
         new TravellersEntryModifier(
            EquipmentSlotGroup.CHEST,
            List.of(new Entry(Attributes.WATER_MOVEMENT_EFFICIENCY, TFAttributeModifiers.TRAVELLERS_SWIFT_SWIM, EquipmentSlotGroup.CHEST)),
            TFDataComponents.SWIFT_SWIM,
            true
         )
      );
      context.register(
         STEALTH_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.CHEST, (DataComponentType<Unit>)TFDataComponents.STEALTH_CROUCHING.get(), Unit.INSTANCE, componentText(STEALTH_MODIFIER)
         )
      );
      context.register(
         ARROW_MAGNETISM_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.CHEST, (DataComponentType<Unit>)TFDataComponents.ARROW_MAGNETISM.get(), Unit.INSTANCE, componentText(ARROW_MAGNETISM_MODIFIER)
         )
      );
      context.register(
         EFFICIENT_EATER_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.CHEST, (DataComponentType<Float>)TFDataComponents.EFFICIENT_EATER.get(), 2.0F, componentText(EFFICIENT_EATER_MODIFIER)
         )
      );
      context.register(
         PERFECT_DODGE_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.CHEST, (DataComponentType<Float>)TFDataComponents.PERFECT_DODGE_PROBABILITY.get(), 0.3F, componentText(PERFECT_DODGE_MODIFIER)
         )
      );
      context.register(
         HASTE_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.CHEST, (DataComponentType<Integer>)TFDataComponents.HASTE_AMPLIFIER.get(), 1, componentText(HASTE_MODIFIER)
         )
      );
      context.register(
         SWAP_HOTBAR_ABILITY, new BuiltinTravellersComponentModifier(EquipmentSlotGroup.LEGS, (DataComponentType<?>)TFDataComponents.SWAP_HOTBAR_ABILITY.get())
      );
      context.register(
         SWAP_HOTBAR_MODIFIER,
         new TransferableComponentModifier(
            EquipmentSlotGroup.LEGS,
            (DataComponentType<Unit>)TFDataComponents.SWAP_HOTBAR_MODIFIER.get(),
            DataComponents.CONTAINER,
            TravellersArmorBeltItem.DEFAULT_EMPTY_BELT_CONTAINER,
            componentText(SWAP_HOTBAR_MODIFIER)
         )
      );
      context.register(
         HIGH_JUMP_ABILITY, new BuiltinTravellersComponentModifier(EquipmentSlotGroup.LEGS, (DataComponentType<?>)TFDataComponents.HIGH_JUMP_AMPLIFIER.get())
      );
      context.register(
         GRADUAL_GLIDE_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.LEGS,
            (DataComponentType<Float>)TFDataComponents.GRADUALLY_GLIDING_MULTIPLIER.get(),
            0.8333333F,
            componentText(GRADUAL_GLIDE_MODIFIER)
         )
      );
      context.register(
         AGILE_RANGER_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.LEGS, (DataComponentType<Float>)TFDataComponents.AGILE_RANGER_MODIFIER.get(), 5.0F, componentText(AGILE_RANGER_MODIFIER)
         )
      );
      context.register(
         DOUBLE_JUMP_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.LEGS, (DataComponentType<Unit>)TFDataComponents.DOUBLE_JUMP.get(), Unit.INSTANCE, componentText(DOUBLE_JUMP_MODIFIER)
         )
      );
      context.register(
         SIDESTEP_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.LEGS,
            (DataComponentType<Long>)TFDataComponents.SIDESTEP_COOLDOWN.get(),
            40L,
            componentText(SIDESTEP_MODIFIER, Component.keybind("key.left"), Component.keybind("key.right"))
         )
      );
      context.register(
         STEP_UP_ABILITY,
         new TravellersEntryModifier(
            EquipmentSlotGroup.FEET,
            List.of(new Entry(Attributes.STEP_HEIGHT, TFAttributeModifiers.TRAVELLERS_HIGH_STEP, EquipmentSlotGroup.FEET)),
            TFDataComponents.HIGH_STEP,
            true
         )
      );
      context.register(
         STRAIGHT_AHEAD_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.FEET, (DataComponentType<Double>)TFDataComponents.STRAIGHT_AHEAD_MULTIPLIER.get(), 1.4, componentText(STRAIGHT_AHEAD_MODIFIER)
         )
      );
      context.register(
         SLIMY_SOLES_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.FEET, (DataComponentType<Float>)TFDataComponents.SLIMY_SOLES_COEFFICIENT.get(), 0.5F, componentText(SLIMY_SOLES_MODIFIER)
         )
      );
      context.register(
         UNRESTRAINED_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.FEET, (DataComponentType<Unit>)TFDataComponents.UNRESTRAINED.get(), Unit.INSTANCE, componentText(UNRESTRAINED_MODIFIER)
         )
      );
      context.register(
         WATER_WALK_MODIFIER,
         new TravellersComponentModifier(
            EquipmentSlotGroup.FEET, (DataComponentType<Unit>)TFDataComponents.WATER_WALK.get(), Unit.INSTANCE, componentText(WATER_WALK_MODIFIER)
         )
      );
   }

   private static List<Component> componentText(ResourceKey<TravellersModifier> modifier, Object... args) {
      return List.of(Component.translatable(modifier.location().toLanguageKey("travellers_gear.modifier", "description"), args));
   }

   public static boolean isModifierActive(Provider registries, ItemStack stack, ResourceKey<TravellersModifier> modifierKey, boolean spectator) {
      return getCachedModifier(registries, modifierKey).map(modifier -> modifier.isActive(stack, modifierKey, spectator)).orElse(false);
   }

   public static boolean isModifierActive(Entity entity, ItemStack stack, ResourceKey<TravellersModifier> modifierKey) {
      return isModifierActive(entity.registryAccess(), stack, modifierKey, entity.isSpectator());
   }

   public static boolean isModifierActive(Entity entity, ResourceKey<TravellersModifier> modifierKey) {
      return entity instanceof LivingEntity livingEntity && isModifierActive(livingEntity, modifierKey);
   }

   public static boolean isModifierActive(LivingEntity livingEntity, ResourceKey<TravellersModifier> modifierKey) {
      Optional<TravellersModifier> modifier = getCachedModifier(livingEntity.registryAccess(), modifierKey);
      if (modifier.isEmpty()) {
         return false;
      }

      ItemStack equippedStack = getStackForGroup(livingEntity, modifier.get().group());
      return !equippedStack.isEmpty() && modifier.get().isActive(equippedStack, modifierKey, livingEntity.isSpectator());
   }

   public static boolean hasTravellersModifier(Provider registries, ItemStack stack, ResourceKey<TravellersModifier> modifierKey) {
      return getCachedModifier(registries, modifierKey).map(modifier -> modifier.hasModifier(stack)).orElse(false);
   }

   public static boolean addModifier(Provider registries, ItemStack stack, ResourceKey<TravellersModifier> modifierKey) {
      Optional<TravellersModifier> modifier = getCachedModifier(registries, modifierKey);
      return !modifier.isEmpty() && modifier.get() instanceof InsertableTravellersModifier insertableTravellersModifier
         ? insertableTravellersModifier.addModifier(stack)
         : false;
   }

   public static boolean transferModifier(Provider registries, ItemStack stack, List<Ingredient> ingredients, ResourceKey<TravellersModifier> modifierKey) {
      Optional<TravellersModifier> modifier = getCachedModifier(registries, modifierKey);
      return !modifier.isEmpty() && modifier.get() instanceof TransferableTravellersModifier transferableTravellersModifier
         ? transferableTravellersModifier.transfer(stack, ingredients)
         : false;
   }

   public static int getModifierDataComponentProviders(Provider registries, List<Ingredient> ingredients, ResourceKey<TravellersModifier> modifierKey) {
      Optional<TravellersModifier> modifier = getCachedModifier(registries, modifierKey);
      return !modifier.isEmpty() && modifier.get() instanceof TransferableComponentModifier transferableComponentModifier
         ? transferableComponentModifier.findDataComponentProviders(ingredients).size()
         : 0;
   }

   public static MutableComponent getModifierTooltipComponent(Reference<TravellersModifier> modifier) {
      return TooltipStringInterpolator.render(modifier.getKey().location().toLanguageKey(((TravellersModifier)modifier.value()).getPrefix()));
   }

   public static List<Reference<TravellersModifier>> findAllInsertableModifiers(Provider registries, ItemStack stack) {
      return registries.lookupOrThrow(TFRegistries.Keys.TRAVELLERS_MODIFIERS)
         .listElements()
         .filter(
            travellersModifier -> travellersModifier.value() instanceof InsertableTravellersModifier
               && !((TravellersModifier)travellersModifier.value()).isAbility()
               && ((TravellersModifier)travellersModifier.value()).hasModifier(stack)
         )
         .toList();
   }

   public static List<Reference<TravellersModifier>> findAllInsertableModifiers(Entity entity, ItemStack stack) {
      return findAllInsertableModifiers(entity.registryAccess(), stack);
   }

   public static List<Reference<TravellersModifier>> findAllInsertableModifiers(Level level, ItemStack stack) {
      return findAllInsertableModifiers(level.registryAccess(), stack);
   }

   public static List<Reference<TravellersModifier>> findAllAbilityModifiers(Provider registries, ItemStack stack) {
      return registries.lookupOrThrow(TFRegistries.Keys.TRAVELLERS_MODIFIERS)
         .listElements()
         .filter(
            travellersModifier -> ((TravellersModifier)travellersModifier.value()).isAbility()
               && ((TravellersModifier)travellersModifier.value()).hasModifier(stack)
         )
         .toList();
   }

   public static long countInsertableModifiers(Provider registries, ItemStack stack) {
      return findAllInsertableModifiers(registries, stack).size();
   }

   public static boolean isModifierEnabled(Provider registries, ResourceKey<TravellersModifier> modifierKey) {
      return getCachedModifier(registries, modifierKey).isPresent();
   }

   private static ItemStack getStackForGroup(LivingEntity livingEntity, EquipmentSlotGroup group) {
      EquipmentSlot matchedSlot = null;

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         if (slot.isArmor() && group.test(slot)) {
            if (matchedSlot != null) {
               return ItemStack.EMPTY;
            }

            matchedSlot = slot;
         }
      }

      return matchedSlot == null ? ItemStack.EMPTY : livingEntity.getItemBySlot(matchedSlot);
   }

   public static void clearCache() {
      CACHED_MODIFIERS.clear();
      MISSING_MODIFIERS.clear();
   }

   private static Optional<TravellersModifier> getCachedModifier(Provider registries, ResourceKey<TravellersModifier> modifierKey) {
      TravellersModifier cached = CACHED_MODIFIERS.get(modifierKey);
      if (cached != null) {
         return Optional.of(cached);
      } else if (MISSING_MODIFIERS.contains(modifierKey)) {
         return Optional.empty();
      } else {
         Optional<Reference<TravellersModifier>> modifier = registries.holder(modifierKey);
         if (modifier.isPresent()) {
            CACHED_MODIFIERS.put(modifierKey, (TravellersModifier)modifier.get().value());
            return Optional.of((TravellersModifier)modifier.get().value());
         } else {
            TwilightForestMod.LOGGER.warn("Travellers modifier {} is not present in the registry", modifierKey.location());
            MISSING_MODIFIERS.add(modifierKey);
            return Optional.empty();
         }
      }
   }

   public static final class CacheInvalidationReloadListener extends SimplePreparableReloadListener<Unit> {
      public static final TravellersModifiersManager.CacheInvalidationReloadListener INSTANCE = new TravellersModifiersManager.CacheInvalidationReloadListener();

      private CacheInvalidationReloadListener() {
      }

      protected Unit prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
         return Unit.INSTANCE;
      }

      protected void apply(Unit object, ResourceManager resourceManager, ProfilerFiller profiler) {
         TravellersModifiersManager.clearCache();
      }
   }
}
