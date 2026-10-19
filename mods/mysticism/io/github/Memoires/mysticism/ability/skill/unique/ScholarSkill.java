package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.ability.subclass.IBookStorage;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.menu.container.BookStorageContainer;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.advancement.TensuraAdvancements;
import io.github.manasmods.tensura.advancement.TensuraAdvancements.Adventure;
import io.github.manasmods.tensura.advancement.TensuraAdvancements.Basic;
import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.registry.advancement.TensuraCriteriaTriggers;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class ScholarSkill extends Skill implements IBookStorage {
   protected static final UniqueMysticismSkillConfig.Scholar CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Scholar;
   protected static final ResourceLocation SCHOLAR = ResourceLocation.fromNamespaceAndPath("mysticism", "scholar");
   public static final List<ResourceLocation> SCHEMATIC_ADVANCEMENTS = List.of(
      ResourceLocation.fromNamespaceAndPath("minecraft", "story/mine_diamond"),
      ResourceLocation.fromNamespaceAndPath("minecraft", "story/smelt_iron"),
      Basic.GOLD_RUSH,
      Basic.ACQUIRE_SILVERWARE,
      Basic.GETCHA_LEATHERS,
      Adventure.GETCHA_BETTER_LEATHERS,
      Basic.LOW_MAGISTEEL,
      Basic.HIGH_MAGISTEEL,
      Basic.PURE_MAGISTEEL,
      Basic.MITHRIL,
      Basic.ORICHALCUM,
      Basic.ADAMANTITE,
      Basic.HIHIIROKANE,
      Adventure.VIGILANT,
      Adventure.ARACHNOPHOBIC,
      Adventure.BELIEVE_T0_FLY,
      Adventure.GOODNIGHT_SPIDER,
      Adventure.HISS_TORY,
      Adventure.RULER_OF_MONSTERS,
      Adventure.RULER_OF_THE_SKIES,
      Adventure.SHELL_LIZARD,
      Adventure.MASTER_SMITH,
      Adventure.START_SMITHING
   );

   public ScholarSkill() {
      super(SkillType.UNIQUE);
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/scholar.png");
   }

   @Override
   public int getMaximumBonusLevel(ManasSkillInstance instance, Player player) {
      return CONFIG.maxBonusLevel;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.getRemoveTime() == -1) {
         if (entity instanceof ServerPlayer serverPlayer) {
            ITensuraPlayer cap = TensuraStorages.getPlayerDataFrom(serverPlayer);
            boolean unlockedAny = false;

            for (Item item : BuiltInRegistries.ITEM) {
               if (item.getDefaultInstance().is(TensuraItemTags.SCHEMATICS) && !cap.hasSchematic(item)) {
                  cap.unlockSchematic(item.getDefaultInstance());
                  unlockedAny = true;
               }
            }

            if (unlockedAny) {
               cap.markDirty();
               ((PlayerTrigger)TensuraCriteriaTriggers.LEARN_ALL_SCHEMATICS.get()).trigger(serverPlayer);
            }
         }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      if (instance.getRemoveTime() == -1) {
         if (entity instanceof ServerPlayer serverPlayer) {
            ITensuraPlayer cap = TensuraStorages.getPlayerDataFrom(serverPlayer);
            cap.clearSchematics();
            cap.markDirty();

            for (ResourceLocation id : SCHEMATIC_ADVANCEMENTS) {
               TensuraAdvancements.revoke(serverPlayer, id);
            }
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learning != null) {
         learning.addOrReplacePermanentModifier(new AttributeModifier(SCHOLAR, CONFIG.learningPoint, Operation.ADD_VALUE));
      }

      AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (mastery != null) {
         mastery.addOrReplacePermanentModifier(new AttributeModifier(SCHOLAR, CONFIG.masteryPoint, Operation.ADD_VALUE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance projectile = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (projectile != null) {
         projectile.removeModifier(SCHOLAR);
      }

      AttributeInstance negate = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (negate != null) {
         negate.removeModifier(SCHOLAR);
      }
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (entity.level().isClientSide() || amount <= 0.0F) {
         return true;
      }

      if (!this.isInSlot(entity)) {
         return true;
      }

      if (source.getEntity() instanceof LivingEntity livingAttacker) {
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag study = tag.getCompound("ScholarStudy");
         CompoundTag byType = study.getCompound("ByType");
         CompoundTag byPlayer = study.getCompound("ByPlayer");
         boolean attackerIsPlayer = livingAttacker instanceof Player;
         String key;
         int points;
         if (attackerIsPlayer) {
            String uuidKey = livingAttacker.getUUID().toString();
            points = byPlayer.getInt(uuidKey);
            key = livingAttacker.getName().getString();
         } else {
            ResourceLocation typeRL = BuiltInRegistries.ENTITY_TYPE.getKey(livingAttacker.getType());
            if (typeRL == null) {
               return true;
            }

            String typeKey = typeRL.toString();
            points = byType.getInt(typeKey);
            key = typeKey;
         }

         int base = CONFIG.studyDodgeBase;
         int per = CONFIG.studyDodgePerPoint;
         int cap = attackerIsPlayer
            ? (instance.isMastered(entity) ? CONFIG.studyDodgePlayerMaxMastered : CONFIG.studyDodgePlayerMax)
            : (instance.isMastered(entity) ? CONFIG.studyDodgeEntityMaxMastered : CONFIG.studyDodgeEntityMax);
         int dodgeChance = Math.min(base + points * per, cap);
         if (dodgeChance > 0 && entity.getRandom().nextFloat() <= dodgeChance / 100.0F) {
            if (!entity.level().isClientSide()) {
               ServerLevel level = (ServerLevel)entity.level();
               double cx = entity.getX();
               double cy = entity.getY() + entity.getBbHeight() * 0.5;
               double cz = entity.getZ();
               int ringCount = 8;
               double radius = 0.9;

               for (int i = 0; i < ringCount; i++) {
                  double angle = (Math.PI * 2) * i / ringCount + entity.getRandom().nextDouble() * 0.3;
                  double px = cx + Math.cos(angle) * radius;
                  double pz = cz + Math.sin(angle) * radius;
                  level.sendParticles(ParticleTypes.SWEEP_ATTACK, px, cy, pz, 1, 0.0, 0.0, 0.0, 0.0);
               }

               level.sendParticles(ParticleTypes.SWEEP_ATTACK, cx, cy, cz, 4, 0.3, 0.0, 0.3, 0.0);
               level.playSound(null, cx, entity.getY(), cz, SoundEvents.PLAYER_ATTACK_WEAK, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }

            return false;
         } else {
            if (CONFIG.studyChance > 0 && entity.getRandom().nextFloat() <= CONFIG.studyChance / 100.0F) {
               if (attackerIsPlayer) {
                  String uuidKey = livingAttacker.getUUID().toString();
                  byPlayer.putInt(uuidKey, byPlayer.getInt(uuidKey) + 1);
                  study.put("ByPlayer", byPlayer);
               } else {
                  ResourceLocation typeRL = BuiltInRegistries.ENTITY_TYPE.getKey(livingAttacker.getType());
                  if (typeRL != null) {
                     String typeKey = typeRL.toString();
                     byType.putInt(typeKey, byType.getInt(typeKey) + 1);
                     study.put("ByType", byType);
                  }
               }

               tag.put("ScholarStudy", study);
               if (entity instanceof Player p) {
                  p.displayClientMessage(Component.translatable("mysticism.skill.scholar.study_gain", new Object[]{key}).withStyle(ChatFormatting.GREEN), true);
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return super.getModeId(instance, mode);
   }

   public int getMaximumBonusLevel() {
      return 5;
   }

   @Override
   public List<String> getBlacklistEnchantments() {
      return CONFIG.enchantmentBlacklist;
   }

   @Override
   public List<String> getBlackListBonusLevelEnchantments() {
      return CONFIG.maxBonusBlacklist;
   }

   @Override
   public boolean isAllowedToUpgradeEnchantments() {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         this.openBookStorage(player, instance);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F);
      }
   }

   @Override
   public BookStorageContainer getBookStorage(ManasSkillInstance instance, Provider provider) {
      BookStorageContainer container = new BookStorageContainer(135, 1);
      container.fromTag(instance.getOrCreateTag().getList("BookStorage", 10), provider);
      return container;
   }
}
