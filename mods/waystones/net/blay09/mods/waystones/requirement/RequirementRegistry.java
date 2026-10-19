package net.blay09.mods.waystones.requirement;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.requirement.ConditionResolver;
import net.blay09.mods.waystones.api.requirement.ParameterSerializer;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.RequirementType;
import net.blay09.mods.waystones.api.requirement.VariableResolver;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.api.requirement.WarpRequirementModifierFunction;
import net.blay09.mods.waystones.api.requirement.WarpRequirementsContext;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneTeleportManager;
import net.blay09.mods.waystones.tag.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RequirementRegistry {
   private static final Map<ResourceLocation, RequirementType<?>> requirementTypes = new HashMap<>();
   private static final Map<ResourceLocation, RequirementFunction<?, ?>> requirementFunctions = new HashMap<>();
   private static final Map<Class<?>, ParameterSerializer<?>> parameterSerializers = new HashMap<>();
   private static final Map<ResourceLocation, VariableResolver> variableResolvers = new HashMap<>();
   private static final Map<ResourceLocation, ConditionResolver<?>> conditionResolvers = new HashMap<>();

   public static void registerDefaults() {
      ExperiencePointsRequirementType experiencePointRequirements = new ExperiencePointsRequirementType();
      ExperienceLevelRequirementType experienceLevelRequirements = new ExperienceLevelRequirementType();
      CooldownRequirementType cooldownRequirements = new CooldownRequirementType();
      SoftCooldownRequirementType softCooldownRequirements = new SoftCooldownRequirementType();
      ItemRequirementType itemRequirements = new ItemRequirementType();
      register(experiencePointRequirements);
      register(experienceLevelRequirements);
      register(cooldownRequirements);
      register(softCooldownRequirements);
      register(itemRequirements);
      registerModifier("add_level_cost", experienceLevelRequirements, RequirementRegistry.FloatParameter.class, (cost, context, parameters) -> {
         cost.setLevels((int)(cost.getLevels() + parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("multiply_level_cost", experienceLevelRequirements, RequirementRegistry.FloatParameter.class, (cost, context, parameters) -> {
         cost.setLevels((int)(cost.getLevels() * parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier(
         "scaled_add_level_cost", experienceLevelRequirements, RequirementRegistry.VariableScaledParameter.class, (cost, context, parameters) -> {
            float sourceValue = context.getContextValue(parameters.id.value);
            cost.setLevels((int)(cost.getLevels() + sourceValue * parameters.scale.value));
            return cost;
         }, () -> WaystonesConfig.getActive().teleports.enableCosts
      );
      registerModifier(
         "scaled_multiply_level_cost", experienceLevelRequirements, RequirementRegistry.VariableScaledParameter.class, (cost, context, parameters) -> {
            float sourceValue = context.getContextValue(parameters.id.value);
            cost.setLevels((int)(cost.getLevels() * sourceValue * parameters.scale.value));
            return cost;
         }, () -> WaystonesConfig.getActive().teleports.enableCosts
      );
      registerModifier("min_level_cost", experienceLevelRequirements, RequirementRegistry.IntParameter.class, (cost, context, parameters) -> {
         cost.setLevels(Math.max(cost.getLevels(), parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("max_level_cost", experienceLevelRequirements, RequirementRegistry.IntParameter.class, (cost, context, parameters) -> {
         cost.setLevels(Math.min(cost.getLevels(), parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("add_xp_cost", experiencePointRequirements, RequirementRegistry.IntParameter.class, (cost, context, parameters) -> {
         cost.setPoints(cost.getPoints() + parameters.value);
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("multiply_xp_cost", experiencePointRequirements, RequirementRegistry.FloatParameter.class, (cost, context, parameters) -> {
         cost.setPoints((int)(cost.getPoints() * parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("scaled_add_xp_cost", experiencePointRequirements, RequirementRegistry.VariableScaledParameter.class, (cost, context, parameters) -> {
         float sourceValue = context.getContextValue(parameters.id.value);
         cost.setPoints((int)(cost.getPoints() + sourceValue * parameters.scale.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier(
         "scaled_multiply_level_cost", experiencePointRequirements, RequirementRegistry.VariableScaledParameter.class, (cost, context, parameters) -> {
            float sourceValue = context.getContextValue(parameters.id.value);
            cost.setPoints((int)(cost.getPoints() * sourceValue * parameters.scale.value));
            return cost;
         }, () -> WaystonesConfig.getActive().teleports.enableCosts
      );
      registerModifier("min_xp_cost", experiencePointRequirements, RequirementRegistry.IntParameter.class, (cost, context, parameters) -> {
         cost.setPoints(Math.max(cost.getPoints(), parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("max_xp_cost", experiencePointRequirements, RequirementRegistry.IntParameter.class, (cost, context, parameters) -> {
         cost.setPoints(Math.min(cost.getPoints(), parameters.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("add_cooldown", cooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)(cost.getCooldownSeconds() + parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("multiply_cooldown", cooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)(cost.getCooldownSeconds() * parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("scaled_add_cooldown", cooldownRequirements, RequirementRegistry.VariableScaledCooldownParameter.class, (cost, context, parameters) -> {
         float sourceValue = context.getContextValue(parameters.variable.value);
         cost.setCooldown(parameters.cooldown.value, (int)(cost.getCooldownSeconds() + sourceValue * parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier(
         "scaled_multiply_cooldown", cooldownRequirements, RequirementRegistry.VariableScaledCooldownParameter.class, (cost, context, parameters) -> {
            float sourceValue = context.getContextValue(parameters.variable.value);
            cost.setCooldown(parameters.cooldown.value, (int)(cost.getCooldownSeconds() * sourceValue * parameters.seconds.value));
            return cost;
         }, () -> WaystonesConfig.getActive().teleports.enableCooldowns
      );
      registerModifier("min_cooldown", cooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)Math.max(cost.getCooldownSeconds(), parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("max_cooldown", cooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)Math.min(cost.getCooldownSeconds(), parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("add_soft_cooldown", softCooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)(cost.getCooldownSeconds() + parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("multiply_soft_cooldown", softCooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)(cost.getCooldownSeconds() * parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier(
         "scaled_add_soft_cooldown", softCooldownRequirements, RequirementRegistry.VariableScaledCooldownParameter.class, (cost, context, parameters) -> {
            float sourceValue = context.getContextValue(parameters.variable.value);
            cost.setCooldown(parameters.cooldown.value, (int)(cost.getCooldownSeconds() + sourceValue * parameters.seconds.value));
            return cost;
         }, () -> WaystonesConfig.getActive().teleports.enableCooldowns
      );
      registerModifier(
         "scaled_multiply_soft_cooldown", softCooldownRequirements, RequirementRegistry.VariableScaledCooldownParameter.class, (cost, context, parameters) -> {
            float sourceValue = context.getContextValue(parameters.variable.value);
            cost.setCooldown(parameters.cooldown.value, (int)(cost.getCooldownSeconds() * sourceValue * parameters.seconds.value));
            return cost;
         }, () -> WaystonesConfig.getActive().teleports.enableCooldowns
      );
      registerModifier("min_soft_cooldown", softCooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)Math.max(cost.getCooldownSeconds(), parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("max_soft_cooldown", softCooldownRequirements, RequirementRegistry.CooldownParameter.class, (cost, context, parameters) -> {
         cost.setCooldown(parameters.id.value, (int)Math.min(cost.getCooldownSeconds(), parameters.seconds.value));
         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCooldowns);
      registerModifier("add_item_cost", itemRequirements, RequirementRegistry.ItemParameter.class, (cost, context, parameters) -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item.value);
         if (cost.getItemStack().getItem() != item) {
            cost.setItemStack(new ItemStack(item));
            cost.setCount((int)parameters.count.value);
         } else {
            cost.setCount((int)(cost.getCount() + parameters.count.value));
         }

         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("multiply_item_cost", itemRequirements, RequirementRegistry.ItemParameter.class, (cost, context, parameters) -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item.value);
         if (cost.getItemStack().getItem() != item) {
            cost.setItemStack(new ItemStack(item));
            cost.setCount((int)parameters.count.value);
         } else {
            cost.setCount((int)(cost.getCount() * parameters.count.value));
         }

         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("scaled_add_item_cost", itemRequirements, RequirementRegistry.VariableScaledItemParameter.class, (cost, context, parameters) -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item.value);
         if (cost.getItemStack().getItem() != item) {
            cost.setItemStack(new ItemStack(item));
            cost.setCount((int)(context.getContextValue(parameters.variable.value) * parameters.count.value));
         } else {
            cost.setCount((int)(cost.getCount() + context.getContextValue(parameters.variable.value) * parameters.count.value));
         }

         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("scaled_multiply_item_cost", itemRequirements, RequirementRegistry.VariableScaledItemParameter.class, (cost, context, parameters) -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item.value);
         if (cost.getItemStack().getItem() != item) {
            cost.setItemStack(new ItemStack(item));
            cost.setCount((int)(context.getContextValue(parameters.variable.value) * parameters.count.value));
         } else {
            cost.setCount((int)(cost.getCount() * context.getContextValue(parameters.variable.value) * parameters.count.value));
         }

         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("min_item_cost", itemRequirements, RequirementRegistry.ItemParameter.class, (cost, context, parameters) -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item.value);
         if (cost.getItemStack().getItem() != item) {
            cost.setItemStack(new ItemStack(item));
            cost.setCount((int)parameters.count.value);
         } else {
            cost.setCount(Math.max(cost.getCount(), (int)parameters.count.value));
         }

         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier("max_item_cost", itemRequirements, RequirementRegistry.ItemParameter.class, (cost, context, parameters) -> {
         Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item.value);
         if (cost.getItemStack().getItem() != item) {
            cost.setItemStack(new ItemStack(item));
            cost.setCount((int)parameters.count.value);
         } else {
            cost.setCount(Math.min(cost.getCount(), (int)parameters.count.value));
         }

         return cost;
      }, () -> WaystonesConfig.getActive().teleports.enableCosts);
      registerModifier(
         "dismount",
         createDefaultType("dismount", DismountRequirement.class),
         RequirementRegistry.NoParameter.class,
         (cost, context, parameters) -> cost,
         () -> true
      );
      registerModifier(
         "refuse", createDefaultType("refuse", RefuseRequirement.class), RequirementRegistry.ComponentParameter.class, (cost, context, parameters) -> {
            cost.setMessage(parameters.value);
            return cost;
         }, () -> true
      );
      registerSerializer(RequirementRegistry.NoParameter.class, it -> RequirementRegistry.NoParameter.INSTANCE);
      registerSerializer(RequirementRegistry.IntParameter.class, it -> new RequirementRegistry.IntParameter(Integer.parseInt(it)));
      registerSerializer(RequirementRegistry.FloatParameter.class, it -> new RequirementRegistry.FloatParameter(Float.parseFloat(it)));
      registerSerializer(RequirementRegistry.StringParameter.class, RequirementRegistry.StringParameter::new);
      registerSerializer(RequirementRegistry.IdParameter.class, it -> new RequirementRegistry.IdParameter(ResourceLocation.parse(it)));
      registerSerializer(
         RequirementRegistry.TaggableIdParameter.class,
         it -> it.startsWith("#")
            ? new RequirementRegistry.TaggableIdParameter(ResourceLocation.tryParse(it.substring(1)), true)
            : new RequirementRegistry.TaggableIdParameter(ResourceLocation.tryParse(it), false)
      );
      registerSerializer(
         RequirementRegistry.WaystonesIdParameter.class,
         it -> new RequirementRegistry.WaystonesIdParameter(RequirementModifierParser.waystonesResourceLocation(it))
      );
      registerSerializer(
         RequirementRegistry.ComponentParameter.class,
         it -> new RequirementRegistry.ComponentParameter(it.startsWith("$") ? Component.translatable(it.substring(1)) : Component.literal(it))
      );
      registerDefaultSerializer(RequirementRegistry.VariableScaledParameter.class);
      registerDefaultSerializer(RequirementRegistry.CooldownParameter.class);
      registerDefaultSerializer(RequirementRegistry.VariableScaledCooldownParameter.class);
      registerDefaultSerializer(RequirementRegistry.ItemParameter.class);
      registerDefaultSerializer(RequirementRegistry.VariableScaledItemParameter.class);
      registerConditionResolver(
         "source_name_equals",
         RequirementRegistry.StringParameter.class,
         (context, parameters) -> context.getFromWaystone().map(waystone -> parameters.value().equals(waystone.getName().getString())).orElse(false)
      );
      registerConditionResolver(
         "source_name_contains",
         RequirementRegistry.StringParameter.class,
         (context, parameters) -> context.getFromWaystone().map(waystone -> waystone.getName().getString().contains(parameters.value())).orElse(false)
      );
      registerBoundConditionResolver("is_interdimensional", RequirementRegistry.NoParameter.class, (context, parameters) -> context.isDimensionalTeleport());
      registerConditionResolver(
         "source_is_warp_plate",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getFromWaystone().map(waystone -> waystone.getWaystoneType().equals(WaystoneTypes.WARP_PLATE)).orElse(false)
      );
      registerConditionResolver(
         "source_is_portstone", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getFlags().contains(TeleportFlags.PORTSTONE)
      );
      registerConditionResolver(
         "source_is_waystone",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getFromWaystone().map(waystone -> waystone.getWaystoneType().equals(WaystoneTypes.WAYSTONE)).orElse(false)
      );
      registerConditionResolver(
         "source_is_sharestone",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getFromWaystone().map(waystone -> WaystoneTypes.isSharestone(waystone.getWaystoneType())).orElse(false)
      );

      for (ResourceLocation sharestoneType : WaystoneTypes.SHARESTONES) {
         registerConditionResolver(
            "source_is_" + sharestoneType.getPath(),
            RequirementRegistry.NoParameter.class,
            (context, parameters) -> sharestoneType.equals(context.getTargetWaystone().getWaystoneType())
         );
      }

      registerConditionResolver(
         "source_is_inventory_button",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getFlags().contains(TeleportFlags.INVENTORY_BUTTON)
      );
      registerConditionResolver(
         "source_is_scroll", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getWarpItem().is(ModItemTags.SCROLLS)
      );
      registerConditionResolver(
         "source_is_bound_scroll", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getWarpItem().is(ModItemTags.BOUND_SCROLLS)
      );
      registerConditionResolver(
         "source_is_return_scroll", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getWarpItem().is(ModItemTags.RETURN_SCROLLS)
      );
      registerConditionResolver(
         "source_is_warp_scroll", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getWarpItem().is(ModItemTags.WARP_SCROLLS)
      );
      registerConditionResolver(
         "source_is_warp_stone", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getWarpItem().is(ModItemTags.WARP_STONES)
      );
      registerBoundConditionResolver(
         "target_name_equals",
         RequirementRegistry.StringParameter.class,
         (context, parameters) -> parameters.value().equals(context.getTargetWaystone().getName().getString())
      );
      registerBoundConditionResolver(
         "target_name_contains",
         RequirementRegistry.StringParameter.class,
         (context, parameters) -> context.getTargetWaystone().getName().getString().contains(parameters.value())
      );
      registerBoundConditionResolver(
         "target_is_warp_plate",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getTargetWaystone().getWaystoneType().equals(WaystoneTypes.WARP_PLATE)
      );
      registerBoundConditionResolver(
         "target_is_global",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getTargetWaystone().getVisibility() == WaystoneVisibility.GLOBAL
      );
      registerBoundConditionResolver(
         "target_is_sharestone",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> WaystoneTypes.isSharestone(context.getTargetWaystone().getWaystoneType())
      );

      for (ResourceLocation sharestoneType : WaystoneTypes.SHARESTONES) {
         registerBoundConditionResolver(
            "target_is_" + sharestoneType.getPath(),
            RequirementRegistry.NoParameter.class,
            (context, parameters) -> sharestoneType.equals(context.getTargetWaystone().getWaystoneType())
         );
      }

      registerBoundConditionResolver(
         "target_is_waystone",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getTargetWaystone().getWaystoneType().equals(WaystoneTypes.WAYSTONE)
      );
      registerConditionResolver("is_on_any_vehicle", RequirementRegistry.NoParameter.class, (context, parameters) -> context.getEntity().getVehicle() != null);
      registerConditionResolver(
         "is_on_vehicle",
         RequirementRegistry.TaggableIdParameter.class,
         (context, parameters) -> {
            Entity vehicle = context.getEntity().getVehicle();
            if (vehicle != null) {
               return parameters.isTag()
                  ? vehicle.getType().is(TagKey.create(Registries.ENTITY_TYPE, parameters.value()))
                  : BuiltInRegistries.ENTITY_TYPE.getKey(vehicle.getType()).equals(parameters.value());
            } else {
               return false;
            }
         }
      );
      registerConditionResolver(
         "is_with_passengers",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> !WaystoneTeleportManager.findPassengers(context.getEntity()).isEmpty()
      );
      registerConditionResolver(
         "is_with_pets", RequirementRegistry.NoParameter.class, (context, parameters) -> !WaystoneTeleportManager.findPets(context.getEntity()).isEmpty()
      );
      registerConditionResolver(
         "is_with_leashed",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> !WaystoneTeleportManager.findLeashedAnimals(context.getEntity()).isEmpty()
      );
      registerConditionResolver(
         "source_is_dimension",
         RequirementRegistry.IdParameter.class,
         (context, parameters) -> context.getFromWaystone()
            .map(waystone -> waystone.getDimension().location())
            .orElseGet(() -> context.getEntity().level().dimension().location())
            .equals(parameters.value)
      );
      registerBoundConditionResolver(
         "target_is_dimension",
         RequirementRegistry.IdParameter.class,
         (context, parameters) -> context.getTargetWaystone().getDimension().location().equals(parameters.value)
      );
      registerConditionResolver(
         "involves_dimension",
         RequirementRegistry.IdParameter.class,
         (context, parameters) -> context.getTargetWaystone().getDimension().location().equals(parameters.value)
            || context.getFromWaystone()
               .map(waystone -> waystone.getDimension().location())
               .orElseGet(() -> context.getEntity().level().dimension().location())
               .equals(parameters.value)
      );
      registerBoundConditionResolver(
         "is_within_distance",
         RequirementRegistry.FloatParameter.class,
         (context, parameters) -> (float)Math.sqrt(context.getEntity().distanceToSqr(context.getTargetWaystone().getPos().getCenter())) <= parameters.value
      );
      registerConditionResolver(
         "has_cooldown",
         RequirementRegistry.WaystonesIdParameter.class,
         (context, parameters) -> context.getEntity() instanceof Player player
            ? PlayerWaystoneManager.getCooldownMillisLeft(player, parameters.value()) > 0L
            : false
      );
      registerConditionResolver("has_item", RequirementRegistry.ItemParameter.class, (context, parameters) -> {
         if (context.getEntity() instanceof Player player) {
            Item item = (Item)BuiltInRegistries.ITEM.get(parameters.item().value());
            return InventoryItemResolver.countMatchingInPlayerInventory(player, stack -> stack.is(item)) >= parameters.count().value();
         } else {
            return false;
         }
      });
      registerConditionResolver(
         "has_empty_inventory",
         RequirementRegistry.NoParameter.class,
         (context, parameters) -> context.getEntity() instanceof Player player ? player.getInventory().isEmpty() : true
      );
      registerConditionResolver("is_wearing_any_armor", RequirementRegistry.NoParameter.class, (context, parameters) -> {
         if (context.getEntity() instanceof LivingEntity livingEntity) {
            for (ItemStack itemStack : livingEntity.getArmorAndBodyArmorSlots()) {
               if (!itemStack.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      });
      registerConditionResolver(
         "has_cooldown_above",
         RequirementRegistry.CooldownAboveParameter.class,
         (context, parameters) -> context.getEntity() instanceof Player player
            ? (float)PlayerWaystoneManager.getCooldownMillisLeft(player, parameters.cooldown().value()) / 1000.0F > parameters.seconds().value()
            : false
      );
      registerConditionResolver(
         "has_tag", RequirementRegistry.StringParameter.class, (context, parameters) -> context.getEntity().getTags().contains(parameters.value())
      );
      registerBoundVariableResolver("distance", it -> (float)Math.sqrt(it.getEntity().distanceToSqr(it.getTargetWaystone().getPos().getCenter())));
      registerVariableResolver("leashed", it -> (float)WaystoneTeleportManager.findLeashedAnimals(it.getEntity()).size());
      registerVariableResolver("pets", it -> (float)WaystoneTeleportManager.findPets(it.getEntity()).size());
      registerVariableResolver("passengers", it -> (float)WaystoneTeleportManager.findPassengers(it.getEntity()).size());
   }

   private static <T extends WarpRequirement> RequirementType<T> createDefaultType(final String name, final Class<T> requirementClass) {
      var requirementType = new RequirementType<T>() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", name);
         }

         @Override
         public T createInstance() {
            try {
               return requirementClass.getConstructor().newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      };
      register(requirementType);
      return requirementType;
   }

   public static void register(RequirementType<?> requirementType) {
      requirementTypes.put(requirementType.getId(), requirementType);
   }

   public static void register(RequirementFunction<?, ?> requirementFunction) {
      requirementFunctions.put(requirementFunction.getId(), requirementFunction);
   }

   public static void register(ParameterSerializer<?> parameterSerializer) {
      parameterSerializers.put(parameterSerializer.getType(), parameterSerializer);
   }

   public static void register(VariableResolver variableResolver) {
      variableResolvers.put(variableResolver.getId(), variableResolver);
   }

   public static void register(ConditionResolver<?> conditionResolver) {
      conditionResolvers.put(conditionResolver.getId(), conditionResolver);
   }

   public static void registerVariableResolver(final String name, final Function<WaystoneTeleportContext, Float> resolver) {
      register(new VariableResolver() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", name);
         }

         @Override
         public float resolve(WaystoneTeleportContext context) {
            return resolver.apply(context);
         }
      });
   }

   public static void registerBoundVariableResolver(final String name, final Function<WaystoneTeleportContext, Float> resolver) {
      register(new VariableResolver() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", name);
         }

         @Override
         public float resolve(WaystoneTeleportContext context) {
            return !context.getTargetWaystone().isValid() ? 0.0F : resolver.apply(context);
         }
      });
   }

   public static <P> void registerConditionResolver(
      final String name, final Class<P> parameterType, final BiFunction<WaystoneTeleportContext, P, Boolean> resolver
   ) {
      register(new ConditionResolver<P>() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", name);
         }

         @Override
         public Class<P> getParameterType() {
            return parameterType;
         }

         @Override
         public boolean matches(WaystoneTeleportContext context, P parameters) {
            return resolver.apply(context, parameters);
         }
      });
      int index = name.indexOf("is_");
      final String notName = index != -1 ? name.substring(0, index + 3) + "not_" + name.substring(index + 3) : "not_" + name;
      register(new ConditionResolver<P>() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", notName);
         }

         @Override
         public Class<P> getParameterType() {
            return parameterType;
         }

         @Override
         public boolean matches(WaystoneTeleportContext context, P parameters) {
            return !resolver.apply(context, parameters);
         }
      });
   }

   public static <P> void registerBoundConditionResolver(
      final String name, final Class<P> parameterType, final BiFunction<WaystoneTeleportContext, P, Boolean> resolver
   ) {
      register(new ConditionResolver<P>() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", name);
         }

         @Override
         public Class<P> getParameterType() {
            return parameterType;
         }

         @Override
         public boolean matches(WaystoneTeleportContext context, P parameters) {
            return !context.getTargetWaystone().isValid() ? false : resolver.apply(context, parameters);
         }
      });
      int index = name.indexOf("is_");
      final String notName = index != -1 ? name.substring(0, index + 3) + "not_" + name.substring(index + 3) : "not_" + name;
      register(new ConditionResolver<P>() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", notName);
         }

         @Override
         public Class<P> getParameterType() {
            return parameterType;
         }

         @Override
         public boolean matches(WaystoneTeleportContext context, P parameters) {
            return !context.getTargetWaystone().isValid() ? false : !resolver.apply(context, parameters);
         }
      });
   }

   public static <T> void registerDefaultSerializer(Class<T> type) {
      registerSerializer(type, it -> RequirementModifierParser.deserializeParameterList(type, it));
   }

   public static <T> void registerSerializer(final Class<T> type, final Function<String, T> deserializer) {
      register(new ParameterSerializer<T>() {
         @Override
         public Class<T> getType() {
            return type;
         }

         @Override
         public T deserialize(String value) {
            return deserializer.apply(value);
         }
      });
   }

   private static <T extends WarpRequirement, P> void registerModifier(
      final String name,
      final RequirementType<T> requirementType,
      final Class<P> parameterType,
      final WarpRequirementModifierFunction<T, P> function,
      final Supplier<Boolean> predicate
   ) {
      register(new RequirementFunction<T, P>() {
         @Override
         public ResourceLocation getId() {
            return ResourceLocation.fromNamespaceAndPath("waystones", name);
         }

         @Override
         public ResourceLocation getRequirementType() {
            return requirementType.getId();
         }

         @Override
         public Class<P> getParameterType() {
            return parameterType;
         }

         public T apply(T requirement, WarpRequirementsContext context, P parameters) {
            return function.apply(requirement, context, parameters);
         }

         @Override
         public boolean isEnabled() {
            return predicate.get();
         }
      });
   }

   public static <T extends WarpRequirement> RequirementType<T> getRequirementType(ResourceLocation id) {
      return (RequirementType<T>)requirementTypes.get(id);
   }

   public static <T extends WarpRequirement, P> RequirementFunction<T, P> getRequirementFunction(ResourceLocation id) {
      return (RequirementFunction<T, P>)requirementFunctions.get(id);
   }

   public static VariableResolver getVariableResolver(ResourceLocation id) {
      return variableResolvers.get(id);
   }

   public static ConditionResolver<?> getConditionResolver(ResourceLocation id) {
      return conditionResolvers.get(id);
   }

   public static <T> ParameterSerializer<T> getParameterSerializer(Class<T> type) {
      return (ParameterSerializer<T>)parameterSerializers.get(type);
   }

   public record ComponentParameter(Component value) {
   }

   public record CooldownAboveParameter(RequirementRegistry.WaystonesIdParameter cooldown, RequirementRegistry.FloatParameter seconds) {
   }

   public record CooldownParameter(RequirementRegistry.WaystonesIdParameter id, RequirementRegistry.FloatParameter seconds) {
   }

   public record FloatParameter(float value) {
   }

   public record IdParameter(ResourceLocation value) {
   }

   public record IntParameter(int value) {
   }

   public record ItemParameter(RequirementRegistry.IdParameter item, RequirementRegistry.FloatParameter count) {
   }

   public record NoParameter() {
      public static final RequirementRegistry.NoParameter INSTANCE = new RequirementRegistry.NoParameter();
   }

   public record StringParameter(String value) {
   }

   public record TaggableIdParameter(ResourceLocation value, boolean isTag) {
   }

   public record VariableScaledCooldownParameter(
      RequirementRegistry.WaystonesIdParameter variable, RequirementRegistry.WaystonesIdParameter cooldown, RequirementRegistry.FloatParameter seconds
   ) {
   }

   public record VariableScaledItemParameter(
      RequirementRegistry.WaystonesIdParameter variable, RequirementRegistry.IdParameter item, RequirementRegistry.FloatParameter count
   ) {
   }

   public record VariableScaledParameter(RequirementRegistry.WaystonesIdParameter id, RequirementRegistry.FloatParameter scale) {
   }

   public record WaystonesIdParameter(ResourceLocation value) {
   }
}
