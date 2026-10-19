package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class HiddenRulerSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.HiddenRuler CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(
         UniqueMysticismSkillConfig.class
      ))
      .HiddenRuler;
   final String key = "HiddenRulerList";
   final String idxKey = "HiddenRulerIndex";

   public HiddenRulerSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/hidden_ruler.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 3 : mode - 1;
      } else {
         return mode == 3 ? 0 : mode + 1;
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player) {
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), 200, 1, false, false, false));
         if (entity instanceof ServerPlayer player) {
            MinecraftServer server = player.server;
            PlayerList playerList = server.getPlayerList();
            ClientboundPlayerInfoRemovePacket removePkt = new ClientboundPlayerInfoRemovePacket(List.of(player.getUUID()));
            Component leaveMsg = Component.translatable("multiplayer.player.left", new Object[]{player.getDisplayName()}).withStyle(ChatFormatting.YELLOW);

            for (ServerPlayer target : playerList.getPlayers()) {
               MobEffectInstance effect = target.getEffect(TensuraMobEffects.PRESENCE_SENSE);
               if (server.getProfilePermissions(target.getGameProfile()) < 2 || effect != null && effect.getAmplifier() > 1) {
                  target.connection.send(removePkt);
                  target.sendSystemMessage(leaveMsg);
               }
            }
         }
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player) {
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
         if (entity instanceof ServerPlayer player) {
            MinecraftServer server = player.server;
            PlayerList playerList = server.getPlayerList();
            ClientboundPlayerInfoUpdatePacket addPkt = ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(player));
            Component joinMsg = Component.translatable("multiplayer.player.joined", new Object[]{player.getDisplayName()}).withStyle(ChatFormatting.YELLOW);

            for (ServerPlayer target : playerList.getPlayers()) {
               MobEffectInstance effect = target.getEffect(TensuraMobEffects.PRESENCE_SENSE);
               if (server.getProfilePermissions(target.getGameProfile()) < 2 || effect != null && effect.getAmplifier() > 1) {
                  target.connection.send(addPkt);
                  target.sendSystemMessage(joinMsg);
               }
            }
         }
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.copyCost;
         case 1 -> CONFIG.pasteCost;
         default -> 1000.0;
         case 3 -> CONFIG.darknessConscriptionCost;
      };
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "hidden_ruler.copy";
         case 1 -> "hidden_ruler.paste";
         case 2 -> "hidden_ruler.clear_bank";
         case 3 -> "hidden_ruler.darkness_conscription";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.contains("HiddenRulerList", 9)) {
         tag.put("HiddenRulerList", new ListTag());
      }

      if (tag.contains("HiddenRulerIndex", 3)) {
         tag.remove("HiddenRulerIndex");
      }

      if (owner instanceof Player player) {
         player.displayClientMessage(Component.translatable("mysticism.skill.hidden_ruler.user_died").withStyle(ChatFormatting.RED), false);
      }

      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
         if (!(entity instanceof Player)) {
            return;
         }

         if (EnergyHelper.isOutOfEnergy(entity, CONFIG.vanishAuraCost, CONFIG.vanishMagiculeCost)) {
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensura.skill.lack_magicule.toggled_off", new Object[]{instance.getSkill().getName()})
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
                  false
               );
            }

            instance.onToggleOff(entity);
            instance.setToggled(false);
         } else {
            TensuraMobEffect.addEffect(
               entity, new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), 200, 1, false, false, false), entity, this
            );
         }
      }

      if (TensuraStorages.getAbilityFrom(entity).isAbilityInActivePreset(this)) {
         List<LivingEntity> list = entity.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               entity.getBoundingBox().inflate(CONFIG.inspireRadius),
               living -> !living.is(entity) && living.isAlive() && living.isAlliedTo(entity)
            );
         if (!list.isEmpty()) {
            int level = instance.isMastered(entity) ? 1 : 0;

            for (LivingEntity target : list) {
               if (Objects.equals(SubordinateHelper.getSubordinateOwnerUUID(target), entity.getUUID())) {
                  return;
               }

               TensuraMobEffect.addEffect(
                  target, new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION), 240, level, false, false, false), entity, this
               );
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 5.0, false);
      CompoundTag skillTag = instance.getOrCreateTag();
      switch (mode) {
         case 0:
            if (entity.isShiftKeyDown()) {
               this.checkList(entity, skillTag);
            } else {
               this.copy(instance, entity, mode, target, skillTag);
            }
            break;
         case 1:
            if (entity.isShiftKeyDown()) {
               this.switchSkill(entity, skillTag);
            } else {
               this.paste(instance, entity, mode, target, skillTag);
            }
            break;
         case 2:
            this.clearBank(instance, entity);
            break;
         case 3:
            this.darknessConscription(instance, entity, mode, target);
      }
   }

   private void copy(ManasSkillInstance instance, LivingEntity entity, int mode, LivingEntity target, CompoundTag skillTag) {
      if (target == null) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").withStyle(ChatFormatting.RED), false);
         }
      } else if (!this.cloneCheck(target, entity)) {
         double targetEP = EnergyHelper.getMaxEP(target);
         double userEP = EnergyHelper.getMaxEP(entity);
         double failureThreshold = instance.isMastered(entity) ? CONFIG.failureThresholdMastered : CONFIG.failureThreshold;
         if (targetEP > userEP * failureThreshold) {
            copyFailed(entity, 2, failureThreshold, instance, mode);
         } else {
            ListTag list = getOrCreateStringList(skillTag);
            if (list.size() >= 5) {
               copyFailed(entity, 1, null, instance, mode);
            }

            entity.swing(InteractionHand.MAIN_HAND, true);
            ServerLevel level = (ServerLevel)entity.level();
            double chance = instance.isMastered(entity) ? CONFIG.copyChanceMastered : CONFIG.copyChance;
            if (!(entity.getRandom().nextInt(100) <= chance)) {
               copyFailed(entity, 0, null, instance, mode);
            } else {
               List<ManasSkillInstance> collection = SkillAPI.getSkillsFrom(target).getLearnedSkills().stream().filter(this::canCopy).toList();
               if (collection.isEmpty()) {
                  copyFailed(entity, 0, null, instance, mode);
               }

               ManasSkill skill = collection.get(target.getRandom().nextInt(collection.size())).getSkill();
               Changeable<ManasSkill> changeable = Changeable.of(skill);
               if (((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(target, entity, false, changeable).isFalse()) {
                  copyFailed(entity, 0, null, instance, mode);
               } else {
                  instance.addMasteryPoint(entity);
                  ManasSkill finalSkill = (ManasSkill)changeable.get();
                  if (finalSkill == null) {
                     copyFailed(entity, 0, null, instance, mode);
                  } else {
                     if (!SkillUtils.hasSkill(entity, finalSkill)) {
                        SkillHelper.learnSkill(entity, finalSkill, -2);
                     } else {
                        if (entity instanceof Player player) {
                           player.displayClientMessage(
                              Component.translatable(
                                    "mysticism.skill.mode.hidden_ruler.copy.success_bank",
                                    new Object[]{Objects.requireNonNull(finalSkill.getName()).getString()}
                                 )
                                 .withStyle(ChatFormatting.YELLOW),
                              false
                           );
                        }

                        Optional<ResourceKey<ManasSkill>> keyOpt = SkillAPI.getSkillRegistry().getKey(finalSkill);
                        keyOpt.ifPresent(key -> {
                           String idStr = key.location().toString();
                           list.add(StringTag.valueOf(idStr));
                        });
                     }

                     int cooldown = instance.isMastered(entity) ? CONFIG.copyCooldownMastered : CONFIG.copyCooldown;
                     instance.setCoolDown(cooldown, mode);
                     level.playSound(
                        null,
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                        TensuraSkill.ABILITY_SOUND,
                        1.0F,
                        1.0F
                     );
                  }
               }
            }
         }
      }
   }

   private void paste(ManasSkillInstance instance, LivingEntity entity, int mode, LivingEntity target, CompoundTag skillTag) {
      if (target == null) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("mysticism.skill.mode.hidden_ruler.no_target").withStyle(ChatFormatting.RED), false);
            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                  TensuraSkill.ABILITY_SOUND,
                  1.0F,
                  1.0F
               );
            instance.setCoolDown(CONFIG.noTargetCooldown, mode);
         }
      } else if (!this.cloneCheck(target, entity)) {
         if (!SubordinateHelper.isSubordinate(entity, target)) {
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("tensura.targeting.not_allowed").withStyle(ChatFormatting.RED), false);
            }

            entity.playSound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), 1.0F, 1.0F);
         } else {
            ListTag list = skillTag.getList("HiddenRulerList", 8);
            List<ManasSkill> copiedSkills = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
               String s = list.getString(i);
               ResourceLocation rl = ResourceLocation.tryParse(s);
               if (rl != null) {
                  ManasSkill sk = (ManasSkill)SkillAPI.getSkillRegistry().get(rl);
                  if (sk != null) {
                     copiedSkills.add(sk);
                  }
               }
            }

            int idx = skillTag.getInt("HiddenRulerIndex");
            ManasSkill chosen;
            if (instance.isMastered(entity) && idx >= 0 && idx < copiedSkills.size()) {
               chosen = copiedSkills.get(idx);
            } else {
               chosen = copiedSkills.get(entity.getRandom().nextInt(copiedSkills.size()));
            }

            String chosenSkillName = skillLabel(chosen);
            if (copiedSkills.isEmpty()) {
               pasteFailed(entity, 1, null, instance, mode);
            }

            if (SkillAPI.getSkillsFrom(target).getSkill(chosen).isPresent()
               && ((ManasSkillInstance)SkillAPI.getSkillsFrom(target).getSkill(chosen).get()).getRemoveTime() == -2) {
               pasteFailed(entity, 2, chosenSkillName, instance, mode);
            }

            entity.swing(InteractionHand.MAIN_HAND, true);
            double chance = instance.isMastered(entity) ? CONFIG.pasteChanceMastered : CONFIG.pasteChance;
            if (entity.getRandom().nextInt(100) <= chance) {
               if (!entity.level().isClientSide()) {
                  TensuraSkillInstance copy = new TensuraSkillInstance(chosen);
                  copy.setToggled(true);
                  copy.getOrCreateTag().putBoolean("GiftedInstance", true);
                  if (SkillHelper.learnSkill(target, copy, -2)) {
                     if (entity instanceof Player player) {
                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.mode.hidden_ruler.paste.success", new Object[]{chosenSkillName})
                              .withStyle(ChatFormatting.GREEN),
                           false
                        );
                     }

                     int cooldown = instance.isMastered(entity) ? CONFIG.pasteCooldownMastered : CONFIG.pasteCooldown;
                     instance.addMasteryPoint(entity);
                     entity.level()
                        .playSound(
                           null,
                           entity.getX(),
                           entity.getY(),
                           entity.getZ(),
                           (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                           TensuraSkill.ABILITY_SOUND,
                           1.0F,
                           1.0F
                        );
                     instance.setCoolDown(cooldown, mode);
                  } else {
                     pasteFailed(entity, 2, chosenSkillName, instance, mode);
                  }
               }
            } else {
               Optional<ResourceKey<ManasSkill>> keyOpt = SkillAPI.getSkillRegistry().getKey(chosen);
               if (keyOpt.isPresent()) {
                  String id = keyOpt.get().location().toString();

                  for (int i = 0; i < list.size(); i++) {
                     if (list.getString(i).equals(id)) {
                        list.remove(i);
                        pasteFailed(entity, 0, null, instance, mode);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean cloneCheck(LivingEntity target, LivingEntity user) {
      if (target instanceof CloneEntity) {
         if (user instanceof Player player) {
            player.displayClientMessage(Component.translatable("mysticism.targeting.clone").withStyle(ChatFormatting.RED), false);
         }

         return true;
      } else {
         return false;
      }
   }

   private void checkList(LivingEntity entity, CompoundTag skillTag) {
      String key = "HiddenRulerList";
      ListTag list = skillTag.getList("HiddenRulerList", 8);
      List<ManasSkill> copiedSkills = new ArrayList<>(list.size());
      if (entity instanceof Player player) {
         for (int i = 0; i < list.size(); i++) {
            String s = list.getString(i);
            ResourceLocation rl = ResourceLocation.tryParse(s);
            if (rl != null) {
               ManasSkill sk = (ManasSkill)SkillAPI.getSkillRegistry().get(rl);
               if (sk != null) {
                  copiedSkills.add(sk);
               }
            }
         }

         if (copiedSkills.isEmpty()) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.hidden_ruler.copy.copy_header")
                  .append(" ")
                  .append(Component.translatable("mysticism.skill.mode.hidden_ruler.copy.no_skills"))
                  .withStyle(ChatFormatting.RED),
               false
            );
            return;
         }

         player.displayClientMessage(
            Component.translatable("mysticism.skill.mode.hidden_ruler.copy.copy_header")
               .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}),
            false
         );

         for (ManasSkill sk : copiedSkills) {
            Component label;
            if (sk.getName() != null) {
               label = sk.getName();
            } else {
               Optional<ResourceKey<ManasSkill>> id = SkillAPI.getSkillRegistry().getKey(sk);
               label = Component.literal(id.<String>map(k -> k.location().toString()).orElse("Unknown Skill"));
            }

            player.displayClientMessage(Component.literal("- ").append(label).withStyle(ChatFormatting.YELLOW), false);
         }
      }
   }

   private void switchSkill(LivingEntity entity, CompoundTag skillTag) {
      ListTag list = skillTag.getList("HiddenRulerList", 8);
      List<ManasSkill> copiedSkills = new ArrayList<>(list.size());

      for (int i = 0; i < list.size(); i++) {
         String s = list.getString(i);
         ResourceLocation rl = ResourceLocation.tryParse(s);
         if (rl != null) {
            ManasSkill sk = (ManasSkill)SkillAPI.getSkillRegistry().get(rl);
            if (sk != null) {
               copiedSkills.add(sk);
            }
         }
      }

      if (!copiedSkills.isEmpty()) {
         int old = skillTag.getInt("HiddenRulerIndex");
         int next = (old + 1) % copiedSkills.size();
         skillTag.putInt("HiddenRulerIndex", next);
         ManasSkill sel = copiedSkills.get(next);
         Optional<ResourceKey<ManasSkill>> optId = SkillAPI.getSkillRegistry().getKey(sel);
         Component label;
         if (sel.getName() != null) {
            label = sel.getName();
         } else {
            label = (Component)optId.<MutableComponent>map(k -> Component.literal(k.location().toString()))
               .orElse(Component.literal("Unknown Skill (Please let Memoires know in the Discord.)"));
         }

         if (entity instanceof Player p) {
            p.displayClientMessage(
               Component.translatable("mysticism.skill.mode.hidden_ruler.paste.selected", new Object[]{label}).withStyle(ChatFormatting.DARK_AQUA), true
            );
         }

         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
      }
   }

   private void clearBank(ManasSkillInstance instance, LivingEntity entity) {
      String key = "HiddenRulerList";
      String idxKey = "HiddenRulerIndex";
      CompoundTag skillTag = instance.getOrCreateTag();
      if (entity instanceof Player player) {
         if (entity.isShiftKeyDown() && instance.getOrCreateTag().getBoolean("ConfirmationRead")) {
            instance.getOrCreateTag().putBoolean("ConfirmationRead", false);
            skillTag.put("HiddenRulerList", new ListTag());
            skillTag.remove("HiddenRulerIndex");
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.hidden_ruler.clear_list.success")
                  .withStyle(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.BOLD}),
               false
            );
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
               );
         } else {
            instance.getOrCreateTag().putBoolean("ConfirmationRead", true);
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.hidden_ruler.clear_list.warning_1")
                  .withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD}),
               false
            );
            player.displayClientMessage(Component.translatable("mysticism.skill.mode.hidden_ruler.clear_list.warning_2").withStyle(ChatFormatting.RED), false);
            TimerUtils.runLater(
               "HiddenRuler",
               60,
               () -> {
                  instance.getOrCreateTag().putBoolean("ConfirmationRead", false);
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.hidden_ruler.clear_list.warning_gone").withStyle(ChatFormatting.GREEN), false
                  );
               }
            );
         }
      }
   }

   private void darknessConscription(ManasSkillInstance instance, LivingEntity entity, int mode, LivingEntity target) {
      int lightLevel = CONFIG.lightLevel;
      if (target != null) {
         if (this.cloneCheck(target, entity)) {
            return;
         }

         int userLight = entity.level().getMaxLocalRawBrightness(entity.blockPosition());
         int targetLight = target.level().getMaxLocalRawBrightness(target.blockPosition());
         if (userLight < lightLevel && targetLight < lightLevel) {
            CharmSkill.charm(instance, entity, mode);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
               );
         } else {
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.hidden_ruler.darkness_conscription.too_bright", new Object[]{lightLevel})
                     .withStyle(ChatFormatting.RED),
                  false
               );
            }

            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                  TensuraSkill.ABILITY_SOUND,
                  1.0F,
                  1.0F
               );
         }

         int cooldown = instance.isMastered(entity) ? CONFIG.darknessConscriptionCooldownMastery : CONFIG.darknessConscriptionCooldown;
         instance.setCoolDown(cooldown, mode);
      }
   }

   private boolean canCopy(ManasSkillInstance instance) {
      if (!instance.isTemporarySkill() && !(instance.getMastery() < 0.0)) {
         return !(instance.getSkill() instanceof Skill skill)
            ? false
            : skill.getType().equals(SkillType.COMMON) || skill.getType().equals(SkillType.EXTRA) || skill.getType().equals(SkillType.INTRINSIC);
      } else {
         return false;
      }
   }

   private static void pasteFailed(LivingEntity entity, int reason, @Nullable String skillName, ManasSkillInstance instance, int mode) {
      if (entity instanceof Player player) {
         switch (reason) {
            case 1:
               player.displayClientMessage(Component.translatable("mysticism.skill.mode.hidden_ruler.paste.no_skills").withStyle(ChatFormatting.RED), false);
               break;
            case 2:
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.hidden_ruler.paste.target_already_knows", new Object[]{skillName}).withStyle(ChatFormatting.RED),
                  false
               );
               break;
            default:
               player.displayClientMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED), false);
         }

         entity.level()
            .playSound(
               null,
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
               TensuraSkill.ABILITY_SOUND,
               1.0F,
               1.0F
            );
         instance.setCoolDown(CONFIG.pasteFailedCooldown, mode);
      }
   }

   private static void copyFailed(LivingEntity entity, int reason, @Nullable Double failureThreshold, ManasSkillInstance instance, int mode) {
      if (entity instanceof Player player) {
         switch (reason) {
            case 1:
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.hidden_ruler.copy.too_many_skills").withStyle(ChatFormatting.RED), false
               );
               break;
            case 2:
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.hidden_ruler.copy.target_too_strong", new Object[]{failureThreshold})
                     .withStyle(ChatFormatting.RED),
                  false
               );
               break;
            default:
               player.displayClientMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED), false);
         }

         entity.level()
            .playSound(
               null,
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
               TensuraSkill.ABILITY_SOUND,
               1.0F,
               1.0F
            );
         instance.setCoolDown(CONFIG.copyFailedCooldown, mode);
      }
   }

   private static String skillLabel(ManasSkill sk) {
      MutableComponent name = sk.getName();
      if (name != null) {
         String s = name.getString();
         if (!s.isEmpty()) {
            return s;
         }
      }

      Optional<ResourceKey<ManasSkill>> keyOpt = SkillAPI.getSkillRegistry().getKey(sk);
      return keyOpt.<String>map(k -> k.location().toString()).orElse("Unknown Skill");
   }

   private static ListTag getOrCreateStringList(CompoundTag root) {
      ListTag list = root.getList("HiddenRulerList", 8);
      if (!root.contains("HiddenRulerList", 9) || list.getElementType() != 8) {
         list = new ListTag();
         root.put("HiddenRulerList", list);
      }

      return list;
   }
}
