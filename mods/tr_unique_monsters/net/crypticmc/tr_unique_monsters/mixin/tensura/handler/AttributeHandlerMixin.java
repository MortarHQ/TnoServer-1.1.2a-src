package net.crypticmc.tr_unique_monsters.mixin.tensura.handler;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.handler.AttributeHandler;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Locale;
import net.crypticmc.tr_unique_monsters.config.configs.BaseConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AttributeHandler.class, remap = false)
public class AttributeHandlerMixin {
   @Inject(
      method = {
            "initEntityExistence(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/LevelAccessor;Lio/github/manasmods/tensura/storage/ep/IExistence;Lio/github/manasmods/tensura/data/existence/EntityExistenceData;)V",
            "initEntityExistence(Lnet/minecraft/class_1309;Lnet/minecraft/class_1936;Lio/github/manasmods/tensura/storage/ep/IExistence;Lio/github/manasmods/tensura/data/existence/EntityExistenceData;)V"
      },
      at = @At("TAIL"),
      remap = false,
      require = 0
   )
   private static void tr_uniqueMonsters$applyUniqueMonsterBonus(
      LivingEntity entity, LevelAccessor level, IExistence existence, EntityExistenceData data, CallbackInfo ci
   ) {
      BaseConfig config = (BaseConfig)ConfigRegistry.getConfig(BaseConfig.class);
      if (config != null) {
         ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
         String namespace = entityId.getNamespace();
         if (!config.uniqueMonsterBlacklist.contains(entityId.toString()) && !namespace.equals("beyond_gacha_c")) {
            if (level instanceof Level fullLevel) {
               String dimensionId = fullLevel.dimension().location().toString();
               if (config.uniqueMonsterDimensionBlacklist != null && config.uniqueMonsterDimensionBlacklist.contains(dimensionId)) {
                  return;
               }
            }

            AttributeHandlerMixin.EntityOverride entityOverride = getEntityOverride(config, entityId);
            double configuredChance = entityOverride != null ? entityOverride.monsterChance() : config.uniqueMonsterChance;
            double configuredMaxMultiplier = entityOverride != null ? entityOverride.maxEpMultiplier() : config.maxEpMultiplier;
            double chance = Math.max(0.0, Math.min(configuredChance, 100.0));
            if (!(chance <= 0.0)) {
               RandomSource random = level.getRandom();
               if (!(chance < 100.0) || !(random.nextDouble() * 100.0 > chance)) {
                  double maxMultiplier = Math.min(configuredMaxMultiplier, 10.0);
                  if (!(maxMultiplier < 1.1)) {
                     double multiplier = 1.1 + (maxMultiplier - 1.1) * random.nextDouble();
                     EnergyHelper.multiplyMaxEP(entity, multiplier);
                     existence.setAura(existence.getAura() * multiplier);
                     existence.setMagicule(existence.getMagicule() * multiplier);
                     AttributeInstance scaleAttribute = entity.getAttribute(Attributes.SCALE);
                     if (scaleAttribute != null) {
                        double scaleFactor = 1.0 + (multiplier - 1.0) * 0.2;
                        scaleFactor = Math.min(scaleFactor, 1.3);
                        scaleAttribute.setBaseValue(scaleAttribute.getBaseValue() * scaleFactor);
                     }

                     existence.markDirty();
                     ResourceLocation markerId = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "unique_marker");
                     AttributeInstance maxHp = entity.getAttribute(Attributes.MAX_HEALTH);
                     if (maxHp != null && !maxHp.hasModifier(markerId)) {
                        maxHp.addOrReplacePermanentModifier(new AttributeModifier(markerId, 0.0, Operation.ADD_VALUE));
                     }

                     entity.addTag("tr_unique_monster");
                  }
               }
            }
         }
      }
   }

   private static AttributeHandlerMixin.EntityOverride getEntityOverride(BaseConfig config, ResourceLocation entityId) {
      if (config.uniqueMonsterEntityOverrides != null && !config.uniqueMonsterEntityOverrides.isEmpty()) {
         String targetId = entityId.toString();

         for (String entry : config.uniqueMonsterEntityOverrides) {
            AttributeHandlerMixin.EntityOverride parsed = parseOverride(entry);
            if (parsed != null && parsed.entityId().equals(targetId)) {
               return parsed;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static AttributeHandlerMixin.EntityOverride parseOverride(String rawEntry) {
      if (rawEntry == null) {
         return null;
      }

      String[] tokens = rawEntry.trim().split("[,;|\\s]+");
      String entityId = null;
      Double chance = null;
      Double maxMultiplier = null;

      for (String token : tokens) {
         if (token != null && !token.isBlank()) {
            if (entityId == null && ResourceLocation.tryParse(token) != null) {
               entityId = token;
            } else {
               int colonIndex = token.indexOf(58);
               label56:
               if (colonIndex > 0) {
                  String key = token.substring(0, colonIndex).toLowerCase(Locale.ROOT);
                  String value = token.substring(colonIndex + 1);
                  if (!"monsterchance".equals(key) && !"chance".equals(key)) {
                     if (!"maxepmultiplier".equals(key) && !"max".equals(key)) {
                        break label56;
                     }

                     Double parsedValue = parseDouble(value);
                     if (parsedValue != null) {
                        maxMultiplier = parsedValue;
                     }
                     continue;
                  }

                  Double parsedValue = parseDouble(value);
                  if (parsedValue != null) {
                     chance = parsedValue;
                  }
                  continue;
               }

               Double parsedNumber = parseDouble(token);
               if (parsedNumber != null) {
                  if (chance == null) {
                     chance = parsedNumber;
                  } else if (maxMultiplier == null) {
                     maxMultiplier = parsedNumber;
                  }
               }
            }
         }
      }

      return entityId != null && chance != null && maxMultiplier != null ? new AttributeHandlerMixin.EntityOverride(entityId, chance, maxMultiplier) : null;
   }

   private static Double parseDouble(String value) {
      if (value != null && !value.isBlank()) {
         try {
            return Double.parseDouble(value);
         } catch (NumberFormatException ignored) {
            return null;
         }
      } else {
         return null;
      }
   }

   private record EntityOverride(String entityId, double monsterChance, double maxEpMultiplier) {
   }
}
