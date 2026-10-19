package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.config.ConfigManager;
import artifacts.neoforge.data.tags.ItemTags;
import artifacts.registry.ModAttributes;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import artifacts.registry.ModMobEffects;
import artifacts.registry.ModSoundEvents;
import artifacts.registry.ModTags;
import artifacts.registry.RegistryHolder;
import com.google.common.base.CaseFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import joptsimple.internal.Strings;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class Language extends LanguageProvider {
   public Language(PackOutput output) {
      super(output, "artifacts", "en_us");
   }

   protected void addTranslations() {
      Artifacts.CONFIG.setup();
      this.addMiscTranslations();
      this.addAbilities();
      this.addAttributes();
      this.addEntities();
      this.addConfigs();
      this.addItems();
      this.addTags();
      this.addTooltips();
      this.addEffects();
      Advancements.TRANSLATIONS.forEach(this::add);
   }

   public void override(String key, String value) {
      try {
         this.add(key, value);
      } catch (IllegalStateException var4) {
      }
   }

   private void addMiscTranslations() {
      this.add("artifacts.creative_tab", "Artifacts");
      ModKeyMappings.register(keyMapping -> {
         List<String> list = Arrays.asList(keyMapping.getName().split("\\."));
         String action = fromSnakeCasedString(list.getLast());
         String itemName = fromSnakeCasedString(list.get(list.size() - 2));
         this.add(keyMapping.getName(), "%s %s".formatted(action, itemName));
      });
      this.add("artifacts.key_category", "Artifacts");
      this.add((SoundEvent)ModSoundEvents.FART.value(), "Fart");
      this.add("curios.identifier.feet", "Feet");
      this.add("curios.modifiers.feet", "When on feet:");
   }

   private void addAbilities() {
      this.addAbilityTooltip(
         ModDataComponents.POST_DAMAGE_EFFECTS.get(), MobEffects.FIRE_RESISTANCE, "Applies a temporary fire resistance effect after taking fire damage"
      );
      this.addAbilityTooltip(
         ModDataComponents.POST_DAMAGE_EFFECTS.get(), MobEffects.MOVEMENT_SPEED, "Increases the wearer's movement speed after taking damage"
      );
      this.addAbilityTooltip(ModDataComponents.POST_EATING_EFFECTS.get(), MobEffects.DIG_SPEED, "Grants a temporary boost to mining speed after eating food");
      this.addAbilityTooltip(ModDataComponents.DAMAGE_ABSORPTION.get(), "chance", "The wearer's melee attacks have a %s%% chance to absorb health");
      this.addAbilityTooltip(ModDataComponents.DAMAGE_ABSORPTION.get(), "constant", "Causes the wearer's melee attacks to absorb health");
      this.addAbilityTooltip(ModDataComponents.ATTACK_EFFECTS.get(), MobEffects.WITHER, "chance", "Melee attacks have a chance to inflict a wither effect");
      this.addAbilityTooltip(
         ModDataComponents.ATTACK_EFFECTS.get(), MobEffects.WITHER, "constant", "Causes the wearer's melee attacks to inflict a wither effect"
      );
      this.addAbilityTooltip(
         ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.ATTACK_BURNING_DURATION, "Causes the wearer's melee attacks to deal fire damage"
      );
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.ATTACK_DAMAGE, "Increases damage dealt by the wearer");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.ATTACK_KNOCKBACK, "Increases knockback dealt by the wearer");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.ATTACK_SPEED, "Increases the wearer's attack speed");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.DRINKING_SPEED, "Decreases the time it takes to drink items");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.EATING_SPEED, "Decreases the time it takes to eat items");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.FALL_DAMAGE_MULTIPLIER, "Reduces fall damage taken by the wearer");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.FLATULENCE, "Increases the wearer's flatulence");
      this.addAbilityTooltip(
         ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.INVINCIBILITY_TICKS, "Increases the length of invincibility after taking damage"
      );
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.JUMP_STRENGTH, "Increases the wearer's jump height");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.KNOCKBACK_RESISTANCE, "Grants immunity to knockback");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.MAX_HEALTH, "Increases the wearer's maximum health");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.MOUNT_SPEED, "Increases the speed of ridden mounts");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.SAFE_FALL_DISTANCE, "Increases the wearer's maximum safe fall distance");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.SCALE, "Shrinks the wearer");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.SLIP_RESISTANCE, "Makes ice less slippery to walk on");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.MOVEMENT_SPEED_ON_SNOW, "Increases the wearer's walking speed on snow");
      this.addAbilityTooltip(
         ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.SPRINTING_SPEED, "Increases the wearer's movement speed while sprinting"
      );
      this.addAbilityTooltip(
         ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.SPRINTING_STEP_HEIGHT, "Increases the wearer's step height while sprinting"
      );
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), "generic.swim_speed", "Improves agility in water");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.BLOCK_BREAK_SPEED, "Increases the wearer's mining speed");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.ENTITY_EXPERIENCE, "Increases experience dropped by creatures");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), ModAttributes.VILLAGER_REPUTATION, "Decreases the trading prices of villagers");
      this.addAbilityTooltip(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), Attributes.OXYGEN_BONUS, "Increases the time the wearer can stay underwater");
      this.addAbilityTooltip(ModDataComponents.DAMAGE_IMMUNITY.get(), ModTags.IS_HOT_FLOOR.location().getPath(), "Grants protection against hot floor damage");
      this.addAbilityTooltip(
         ModDataComponents.DAMAGE_IMMUNITY.get(), DamageTypeTags.IS_LIGHTNING.location().getPath(), "Grants protection against lightning strikes"
      );
      this.addAbilityTooltip(ModDataComponents.DOUBLE_JUMP.get(), "Allows the wearer to double jump");
      this.addAbilityTooltip(ModDataComponents.ENDER_PEARL_HUNGER_COST.get(), "free", "Ender Pearls are not consumed when thrown");
      this.addAbilityTooltip(ModDataComponents.ENDER_PEARL_HUNGER_COST.get(), "cost", "Ender Pearls are not consumed, but cost hunger instead");
      this.addAbilityTooltip(ModDataComponents.POST_EATING_PLANT_GROWTH.get(), "Plants grow after eating when standing on grass");
      this.addAbilityTooltip(
         ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "fortune", "multiple_levels", "Applies %s extra levels of fortune to mined blocks"
      );
      this.addAbilityTooltip(
         ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "fortune", "single_level", "Applies an extra level of fortune to mined blocks"
      );
      this.addAbilityTooltip(
         ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "looting", "multiple_levels", "Applies %s extra levels of looting to killed entities"
      );
      this.addAbilityTooltip(
         ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "looting", "single_level", "Applies an extra level of looting to killed entities"
      );
      this.addAbilityTooltip(
         ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "luck_of_the_sea", "multiple_levels", "Applies %s extra levels of Luck of the Sea when fishing"
      );
      this.addAbilityTooltip(
         ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "luck_of_the_sea", "single_level", "Applies an extra Luck of the Sea when fishing"
      );
      this.addAbilityTooltip(ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "lure", "multiple_levels", "Applies %s extra levels of Lure when fishing");
      this.addAbilityTooltip(ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), "lure", "single_level", "Applies an extra level of Lure when fishing");
      this.addAbilityTooltip(ModDataComponents.MOB_EFFECTS.get(), "water_breathing", "infinite", "Allows the wearer to breathe underwater");
      this.addAbilityTooltip(
         ModDataComponents.MOB_EFFECTS.get(), "water_breathing", "limited", "Allows the wearer to breathe underwater for a limited amount of time"
      );
      this.addAbilityTooltip(ModDataComponents.MOB_EFFECTS.get(), "invisibility", "Turns the wearer invisible");
      this.addAbilityTooltip(ModDataComponents.MOB_EFFECTS.get(), "magnetism", "Attracts nearby items");
      this.addAbilityTooltip(ModDataComponents.MOB_EFFECTS.get(), "night_vision", "full", "Allows the wearer to see in the dark");
      this.addAbilityTooltip(ModDataComponents.MOB_EFFECTS.get(), "night_vision", "partial", "Allows the wearer to see in the dark slightly");
      this.addAbilityTooltip(ModDataComponents.ENDER_PEARL_DAMAGE_IMMUNITY.get(), "Ender Pearls deal no damage");
      this.addAbilityTooltip(ModDataComponents.CURE_EFFECTS.get(), "Greatly reduces the duration of negative effects");
      this.addAbilityTooltip(ModDataComponents.REPLENISH_HUNGER_ON_GRASS.get(), "Slowly replenishes hunger while walking on grass");
      this.addAbilityTooltip(ModDataComponents.CREEPER_REPELLENT.get(), "Creepers avoid the wearer");
      this.addAbilityTooltip(ModDataComponents.PHANTOM_REPELLENT.get(), "Hisses at attacking phantoms");
      this.addAbilityTooltip(
         ModDataComponents.RETALIATION_EFFECTS.get(), "fire", "fire_resistance", "Grants fire resistance after lighting an attacker on fire"
      );
      this.addAbilityTooltip(ModDataComponents.RETALIATION_EFFECTS.get(), "fire", "chance", "Has a %s%% chance to light attackers on fire");
      this.addAbilityTooltip(ModDataComponents.RETALIATION_EFFECTS.get(), "fire", "constant", "Attacking entities are lit on fire");
      this.addAbilityTooltip(ModDataComponents.SINKING.get(), "Allows the wearer to move freely in water");
      this.addAbilityTooltip(ModDataComponents.AUTO_SMELT.get(), "Automatically smelts mined ores");
      this.addAbilityTooltip(ModDataComponents.FLUID_COLLISION.get(), "sneaking", "lava", "Allows the wearer to stand on lava while sneaking");
      this.addAbilityTooltip(ModDataComponents.FLUID_COLLISION.get(), "sprinting", "Allows the wearer to walk on fluids while sprinting");
      this.addAbilityTooltip(ModDataComponents.RETALIATION_EFFECTS.get(), "lightning", "chance", "Has a %s%% chance to strike attackers with lightning");
      this.addAbilityTooltip(ModDataComponents.RETALIATION_EFFECTS.get(), "lightning", "constant", "Attacking entities are struck by lightning");
      this.addAbilityTooltip(ModDataComponents.SWIM_IN_AIR.get(), "keymapping", "Press %s while in the air to start swimming");
      this.addAbilityTooltip(ModDataComponents.SWIM_IN_AIR.get(), "swimming", "Allows the wearer to swim in the air for a limited period of time");
      this.addAbilityTooltip(
         ModDataComponents.DEATH_PROTECTION_TELEPORT.get(), "chance", "A fatal hit has a %s%% chance to teleport you somewhere else instead"
      );
      this.addAbilityTooltip(ModDataComponents.DEATH_PROTECTION_TELEPORT.get(), "constant", "A fatal hit teleports you somewhere else instead");
      this.addAbilityTooltip(ModDataComponents.DEATH_PROTECTION_TELEPORT.get(), "not_consumed", "Not consumed on use");
      this.addAbilityTooltip(ModDataComponents.RETALIATION_EFFECTS.get(), "thorns", "chance", "Has a %s%% chance to damage attackers");
      this.addAbilityTooltip(ModDataComponents.RETALIATION_EFFECTS.get(), "thorns", "constant", "Attacking entities are damaged as well");
      this.addAbilityTooltip(ModDataComponents.TOOL_TIER_UPGRADE.get(), "Increases the wearer's base mining level to %s");
      this.addAbilityTooltip(ModDataComponents.WALK_ON_POWDER_SNOW.get(), "Allows the wearer to walk on Powder Snow");
   }

   private void addAttributes() {
      for (RegistryHolder<Attribute, ?> attribute : ModAttributes.ATTRIBUTES.getEntries()) {
         this.add(attribute.get().getDescriptionId(), fromSnakeCasedString(attribute.unwrapKey().orElseThrow().location().getPath().split("\\.")[1]));
      }

      this.add("generic.swim_speed", "Swim Speed");
   }

   private void addEntities() {
      for (RegistryHolder<EntityType<?>, ?> entityType : ModEntityTypes.ENTITY_TYPES.getEntries()) {
         this.add(entityType.get().getDescriptionId(), fromSnakeCasedString(entityType.unwrapKey().orElseThrow().location().getPath()));
      }

      this.add((SoundEvent)ModSoundEvents.MIMIC_CLOSE.value(), "Mimic closes");
      this.add((SoundEvent)ModSoundEvents.MIMIC_DEATH.value(), "Mimic dies");
      this.add((SoundEvent)ModSoundEvents.MIMIC_HURT.value(), "Mimic hurts");
      this.add((SoundEvent)ModSoundEvents.MIMIC_OPEN.value(), "Mimic hops");
   }

   private void addConfigs() {
      this.add(configTitle(), "Artifacts Config");
      this.add("artifacts.config.enabled.title", "Enabled");
      this.add("artifacts.config.cooldown.title", "Cooldown");
      this.add("artifacts.config.generateAsLoot.title", "Generate as loot");
      this.add("artifacts.config.generateAsLoot.description", "Whether this item can be found in structures or drop from entities");

      for (ConfigManager config : Artifacts.CONFIG.configs) {
         this.add(configTitle(config.getName()), fromCamelCasedString(config.getName()));
         this.addConfigNames(config);
         this.addConfigTooltips(config);
      }
   }

   private void addConfigNames(ConfigManager config) {
      config.getValues().forEach((key, value) -> {
         String[] words = key.split("\\.");
         String name = words[words.length - 1];
         if (!name.equals("cooldown") && !name.equals("enabled") && !name.equals("generateAsLoot")) {
            String translation = fromCamelCasedString(name);
            key = config.getName() + "." + key;
            this.add(configTitle(key), translation);
            StringBuilder categoryKey = new StringBuilder(config.getName());

            for (int i = 0; i < words.length - 1; i++) {
               categoryKey.append('.').append(words[i]);
               if (!BuiltInRegistries.ITEM.containsKey(Artifacts.id(words[i]))) {
                  this.override(configTitle(categoryKey.toString()), fromSnakeCasedString(words[i]));
               }
            }
         }
      });
   }

   private void addConfigTooltips(ConfigManager config) {
      config.getValues().forEach((key, value) -> {
         if (!key.endsWith("generateAsLoot")) {
            List<String> tooltips = config.getDescription(key);
            key = config.getName() + "." + key;
            if (tooltips.size() == 1) {
               this.add(configDescription(key), tooltips.getFirst());
            } else {
               for (int i = 0; i < tooltips.size(); i++) {
                  this.add(concat(configDescription(key), Integer.toString(i)), tooltips.get(i));
               }
            }
         }
      });
   }

   private void addItems() {
      for (Holder<Item> item : ModItems.ITEMS.getEntries()) {
         this.add((Item)item.value(), fromSnakeCasedString(((ResourceKey)item.unwrapKey().orElseThrow()).location().getPath()));
      }

      this.override(((Item)ModItems.ANGLERS_HAT.value()).getDescriptionId(), "Angler's Hat");
      this.override(((Item)ModItems.AQUA_DASHERS.value()).getDescriptionId(), "Aqua-Dashers");
   }

   private void addTags() {
      this.add(ItemTags.ARTIFACTS, "Artifacts");
      this.add(ItemTags.ALL, "Any Slot Equipable Artifacts");
      this.add(ItemTags.BELT, "Belt Slot Equipable Artifacts");
      this.add(ItemTags.FACE, "Face Slot Equipable Artifacts");
      this.add(ItemTags.FEET, "Feet Slot Equipable Artifacts");
      this.add(ItemTags.HANDS, "Hands Slot Equipable Artifacts");
      this.add(ItemTags.HEAD, "Head Slot Equipable Artifacts");
      this.add(ItemTags.NECKLACE, "Necklace Slot Equipable Artifacts");
      this.add(ModTags.ANTIDOTE_VESSEL_CANCELLABLE, "Antidote Vessel Cancellable");
      this.add(ModTags.CAMPSITE_CHESTS, "Campsite Chests");
      this.add(ModTags.CREEPERS, "Creepers");
      this.add(ModTags.MINEABLE_WITH_DIGGING_CLAWS, "Mineable With Digging Claws");
      this.add(ModTags.ROOTED_BOOTS_GRASS, "Rooted Boots Grass");
      this.add(ModTags.SNOW_LAYERS, "Snow Layers");
   }

   private void addTooltips() {
      this.tooltip("attacks_inflict", "Attacks inflict:");
      this.tooltip("cooldown", "+Cooldown (%s)");
      this.tooltip("cosmetic", "Cosmetic");
      this.tooltip("cosmetics_disabled", "Cosmetics disabled (right-click to toggle)");
      this.tooltip("cosmetics_enabled", "Cosmetics enabled (right-click to toggle)");
      this.tooltip("item.everlasting_food", "Not consumed when eaten");
      this.tooltip("item.novelty_drinking_hat", "'Hey! I'm #1, and I let gravity do my drinking!'");
      this.tooltip("item.umbrella.glider", "Slows your fall when held");
      this.tooltip("item.umbrella.shield", "Can be used as a shield");
      this.tooltip("missing_dependency", "Install Curios, Trinkets or Accessories to use this item");
      this.tooltip("per_food_point_restored", "For every food point restored:");
      this.tooltip("plus_mob_effect", "+%s");
      this.tooltip("plus_mob_effect_chance", "+%s (%s%%)");
      this.tooltip("toggle_keymapping", "Press %s to toggle");
      this.tooltip("tool_tier.none", "none");
      this.tooltip("tool_tier.wood", "wood");
      this.tooltip("tool_tier.stone", "stone");
      this.tooltip("tool_tier.iron", "iron");
      this.tooltip("tool_tier.diamond", "diamond");
      this.tooltip("tool_tier.netherite", "netherite");
      this.tooltip("when_hurt", "When hurt:");
      this.tooltip("when_hurt.is_fire", "When hurt by Fire:");
   }

   private void add(SoundEvent soundEvent, String value) {
      this.add("%s.subtitles.%s", BuiltInRegistries.SOUND_EVENT.getKey(soundEvent), value);
   }

   private void add(String key, ResourceLocation id, String value) {
      this.add(key.formatted(id.getNamespace(), id.getPath()), value);
   }

   private void addAbilityTooltip(DataComponentType<?> type, Holder<?> holder, String... s) {
      List<String> list = new ArrayList<>(List.of(s));
      list.addFirst(((ResourceKey)holder.unwrapKey().orElseThrow()).location().getPath());
      this.addAbilityTooltip(type, list.toArray(String[]::new));
   }

   private void addAbilityTooltip(DataComponentType<?> type, String... s) {
      StringBuilder key = new StringBuilder("%s.tooltip.ability.%s");

      for (int i = 0; i < s.length - 1; i++) {
         key.append('.').append(s[i]);
      }

      this.add(key.toString(), Objects.requireNonNull(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type)), s[s.length - 1]);
   }

   private void addEffects() {
      this.addEffect(ModMobEffects.MAGNETISM::value, "Magnetism");
   }

   private void tooltip(String key, String value) {
      this.add("%s.tooltip.%s".formatted("artifacts", key), value);
   }

   private static String fromCamelCasedString(String string) {
      return fromSnakeCasedString(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string));
   }

   private static String fromSnakeCasedString(String string) {
      String[] words = string.split("_");

      for (int i = 0; i < words.length; i++) {
         words[i] = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1);
      }

      return Strings.join(words, " ")
         .replace(" A ", " a ")
         .replace(" An ", " an ")
         .replaceFirst(" In ", " in ")
         .replace(" Of ", " of ")
         .replace(" On ", " on ")
         .replaceFirst(" Per ", " per ")
         .replace(" The ", " the ");
   }

   private static String configDescription(String... names) {
      return key("config", concat(names), "description");
   }

   private static String configTitle(String... names) {
      return key("config", concat(names), "title");
   }

   private static String key(String... names) {
      return concat("artifacts", concat(names));
   }

   private static String concat(String... names) {
      if (names.length == 0) {
         return "";
      }

      StringBuilder builder = new StringBuilder(names[0]);

      for (int i = 1; i < names.length; i++) {
         if (!names[i].isEmpty()) {
            builder.append('.').append(names[i]);
         }
      }

      return builder.toString();
   }
}
