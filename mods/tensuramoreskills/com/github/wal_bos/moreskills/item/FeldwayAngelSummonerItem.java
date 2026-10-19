package com.github.wal_bos.moreskills.item;

import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.FeldwayAngelEntity;
import com.github.wal_bos.moreskills.world.entity.MichaelManasEntity;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Comparator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FeldwayAngelSummonerItem extends Item {
   private static final int COOLDOWN = 90;
   private static final int ULTIMATE_COOLDOWN = 180;
   private static final int CATHEDRAL_COOLDOWN = 240;
   private static final int MAX_ACTIVE = 3;
   private static final int MAX_ARMY_ACTIVE = 9;
   private static final int CATHEDRAL_ACTIVE = 30;
   private static final int CALL_FORTH_TIME = 40;
   private static final int CAST_INTERVAL = 5;
   private static final float CHARGE_TOTAL = 100.0F;
   private static final float CHARGE_PER_INTERVAL = 5.0F;
   private static final float CIRCLE_SIZE = 5.0F;
   private static final float HUGE_CIRCLE_SIZE = 18.0F;
   private static final int CIRCLE_DURATION = 180;
   private static final int HUGE_CIRCLE_DURATION = 260;
   private static final double CAST_RANGE = 48.0;
   private static final String TAG_MODE = "FeldRelicMode";
   private static final String TAG_SUMMON_AMOUNT = "SummonAmount";
   private static final String TAG_PENDING_PREFIX = "SummonUUID";
   private static final String TAG_MARKED_TARGET = "MarkedTarget";
   private static final String TAG_MICHAEL_UUID = "MichaelUUID";
   private static final String TAG_ARMY_FLAG = "ArmySummon";
   private static final String TAG_CATHEDRAL_FLAG = "CathedralSummon";
   private static final String TAG_ITEM_COOLDOWN = "ItemCooldownTicks";
   private static final String TAG_ITEM_COOLDOWN_TYPE = "ItemCooldownType";
   private static final String TAG_MAGICULE_IMMUNITY = "MagiculePoisonImmunity";
   private static final String TAG_CIRCLE_UUID = "SummonCircleUUID";
   private static final int MODE_SUMMON = 0;
   private static final int MODE_ATTACK = 1;
   private static final int MODE_ARMY = 2;
   private static final int MODE_DESPAWN = 3;
   private static final int MODE_ULTIMATE = 4;
   private static final int MODE_CATHEDRAL = 5;
   private static final int MODE_IMMUNITY = 6;

   public FeldwayAngelSummonerItem(Properties properties) {
      super(properties.stacksTo(1));
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      super.inventoryTick(stack, level, entity, slotId, isSelected);
      if (!level.isClientSide()) {
         CompoundTag tag = getTag(stack);
         int cd = tag.getInt("ItemCooldownTicks");
         if (cd > 0) {
            tag.putInt("ItemCooldownTicks", cd - 1);
            if (cd - 1 <= 0) {
               tag.remove("ItemCooldownTicks");
               tag.remove("ItemCooldownType");
            }

            saveTag(stack, tag);
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      CompoundTag tag = getTag(stack);
      if (player.isCrouching()) {
         if (!level.isClientSide()) {
            int next = (getMode(tag) + 1) % 7;
            setMode(tag, next);
            saveTag(stack, tag);
            player.displayClientMessage(Component.literal("Mode: " + getModeName(next)).withStyle(ChatFormatting.GOLD), true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.7F, 1.25F);
            if (level instanceof ServerLevel sl) {
               sl.sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY() + 1.0, player.getZ(), 18, 0.35, 0.45, 0.35, 0.03);
               sl.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY() + 1.0, player.getZ(), 8, 0.25, 0.35, 0.25, 0.01);
            }
         }

         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      } else if (level instanceof ServerLevel serverLevel) {
         int mode = getMode(tag);
         if (mode == 3) {
            int removed = despawnOwnedSummons(serverLevel, player, stack);
            if (removed > 0) {
               player.displayClientMessage(
                  Component.literal("Dismissed " + removed + " summon" + (removed == 1 ? "." : "s.")).withStyle(ChatFormatting.WHITE), true
               );
               level.playSound(
                  null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 0.9F, 1.1F
               );
            } else {
               player.displayClientMessage(Component.literal("You have no summons to dismiss.").withStyle(ChatFormatting.RED), true);
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
         } else if (mode == 1) {
            LivingEntity target = getLookTarget(player);
            if (target != null && target.isAlive()) {
               tag.putUUID("MarkedTarget", target.getUUID());
               saveTag(stack, tag);
               int commanded = commandAttack(serverLevel, player, stack, target);
               if (commanded > 0) {
                  player.displayClientMessage(
                     Component.literal("Commanded " + commanded + " summon" + (commanded == 1 ? "." : "s.")).withStyle(ChatFormatting.WHITE), true
                  );
                  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 0.8F, 1.25F);
                  return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
               } else {
                  player.displayClientMessage(Component.literal("You have no summons to command.").withStyle(ChatFormatting.RED), true);
                  return InteractionResultHolder.fail(stack);
               }
            } else {
               player.displayClientMessage(Component.literal("No target selected.").withStyle(ChatFormatting.RED), true);
               return InteractionResultHolder.fail(stack);
            }
         } else if (mode == 6) {
            boolean enabled = !tag.getBoolean("MagiculePoisonImmunity");
            tag.putBoolean("MagiculePoisonImmunity", enabled);
            saveTag(stack, tag);
            if (enabled) {
               player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
               player.displayClientMessage(Component.literal("Magicule Poison Immunity: ON").withStyle(ChatFormatting.GREEN), true);
               level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.8F, 1.35F);
               level.playSound(
                  null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 0.7F, 1.4F
               );
               serverLevel.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.0, player.getZ(), 2, 0.15, 0.15, 0.15, 0.0);
               serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0, player.getZ(), 10, 0.35, 0.45, 0.35, 0.02);
               serverLevel.sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY() + 1.0, player.getZ(), 24, 0.45, 0.55, 0.45, 0.04);
               serverLevel.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY() + 1.0, player.getZ(), 14, 0.3, 0.45, 0.3, 0.01);
            } else {
               player.displayClientMessage(Component.literal("Magicule Poison Immunity: OFF").withStyle(ChatFormatting.DARK_RED), true);
               level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.8F, 0.9F);
               serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1.0, player.getZ(), 10, 0.3, 0.45, 0.3, 0.02);
               serverLevel.sendParticles(ParticleTypes.ASH, player.getX(), player.getY() + 1.0, player.getZ(), 8, 0.25, 0.35, 0.25, 0.01);
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
         } else {
            if (isSummonCooldownActive(stack)) {
               int seconds = Math.max(1, getCooldownTicks(stack) / 20);
               String type = getCooldownType(stack);
               player.displayClientMessage(Component.literal(type + " cooldown: " + seconds + "s").withStyle(ChatFormatting.RED), true);
               return InteractionResultHolder.fail(stack);
            }

            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, player, Fluid.NONE, 48.0);
            if (mode != 0 && mode != 2 && mode != 4 && mode != 5) {
               return InteractionResultHolder.fail(stack);
            }

            if (level.getBlockState(result.getBlockPos().below()).isSolid() && level.getBlockState(result.getBlockPos().below(2)).isSolid()) {
               if (mode != 4) {
                  int current = countActiveAngels(level, player);
                  int max = mode == 5 ? 30 : (mode == 2 ? 9 : 3);
                  if (current >= max) {
                     player.displayClientMessage(
                        Component.literal("You already control the maximum number of summons for this mode.").withStyle(ChatFormatting.RED), true
                     );
                     return InteractionResultHolder.fail(stack);
                  }
               }

               clearPendingData(tag);
               clearCircleData(tag);
               if (mode == 2) {
                  tag.putBoolean("ArmySummon", true);
               } else {
                  tag.remove("ArmySummon");
               }

               if (mode == 5) {
                  tag.putBoolean("CathedralSummon", true);
               } else {
                  tag.remove("CathedralSummon");
               }

               saveTag(stack, tag);
               player.startUsingItem(hand);
               MagicCircle circle = getBoundSummoningCircle(serverLevel, stack);
               float wantedSize = mode == 5 ? 18.0F : 5.0F;
               int wantedLife = mode == 5 ? 260 : 180;
               if (circle == null || circle.getOwner() != player || circle.getChargedEnergy() < 0.0F) {
                  circle = summonMagicCircle(player, result.getLocation(), wantedSize, wantedLife);
                  bindCircle(stack, circle);
                  level.playSound(
                     null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 0.5F, 1.0F
                  );
               } else if (circle.getChargedEnergy() >= 100.0F) {
                  circle.setChargedEnergy(100.0F);
               }

               return InteractionResultHolder.consume(stack);
            } else {
               player.displayClientMessage(Component.literal("Need solid ground to summon.").withStyle(ChatFormatting.RED), true);
               return InteractionResultHolder.fail(stack);
            }
         }
      } else {
         return InteractionResultHolder.consume(stack);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      CompoundTag tag = getTag(stack);
      tooltip.add(
         Component.literal("A sacred relic taken from ")
            .withStyle(ChatFormatting.WHITE)
            .append(Component.literal("Feldway").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(", still bearing the authority to call forth his angels.").withStyle(ChatFormatting.WHITE))
      );
      tooltip.add(Component.literal("Shift Right Click: Change mode").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.literal("Current Mode: " + getModeName(getMode(tag))).withStyle(ChatFormatting.AQUA));
      if (tag.getBoolean("MagiculePoisonImmunity")) {
         tooltip.add(Component.literal("Magicule Poison Immunity: ON").withStyle(ChatFormatting.GREEN));
      } else {
         tooltip.add(Component.literal("Magicule Poison Immunity: OFF").withStyle(ChatFormatting.DARK_RED));
      }

      if (isSummonCooldownActive(stack)) {
         tooltip.add(Component.literal("Cooldown: " + Math.max(1, getCooldownTicks(stack) / 20) + "s").withStyle(ChatFormatting.RED));
      }

      super.appendHoverText(stack, context, tooltip, flag);
   }

   public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseDuration) {
      if (living instanceof Player player) {
         if (level instanceof ServerLevel serverLevel) {
            CompoundTag tag = getTag(stack);
            int mode = getMode(tag);
            if (mode == 0 || mode == 2 || mode == 4 || mode == 5) {
               int heldTicks = this.getUseDuration(stack, living) - remainingUseDuration;
               MagicCircle circle = getBoundSummoningCircle(serverLevel, stack);
               if (circle != null && circle.getOwner() == player) {
                  if (circle.getChargedEnergy() >= 0.0F && circle.getChargedEnergy() < 100.0F) {
                     if (heldTicks > 0 && heldTicks % 5 == 0) {
                        circle.setAge(0);
                        circle.setChargedEnergy(Math.min(circle.getChargedEnergy() + 5.0F, 100.0F));
                        level.playSound(null, circle.getX(), circle.getY(), circle.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.75F, 1.0F);
                     }
                  } else {
                     if (circle.getChargedEnergy() >= 100.0F) {
                        float progress = circle.getChargedEnergy() - 100.0F;
                        circle.setChargedEnergy(circle.getChargedEnergy() + 1.0F);
                        circle.setAge(circle.getLife() - 25);
                        if (progress == 0.0F && !tag.hasUUID("SummonUUID0") && !tag.hasUUID("MichaelUUID")) {
                           if (mode == 4) {
                              createMichaelPending(player, stack, circle.position());
                           } else {
                              createSummons(player, stack, circle.position(), mode);
                           }

                           tag = getTag(stack);
                        }

                        if (mode == 4) {
                           if (tag.hasUUID("MichaelUUID")) {
                              boolean finished = callForthMichael(serverLevel, player, stack, (int)progress);
                              level.playSound(
                                 null,
                                 circle.getX(),
                                 circle.getY(),
                                 circle.getZ(),
                                 (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                                 SoundSource.PLAYERS,
                                 0.7F,
                                 0.9F
                              );
                              if (finished) {
                                 clearCircle(serverLevel, stack);
                                 setCustomCooldown(stack, 180, "Ultimate");
                                 player.swing(player.getUsedItemHand(), true);
                                 player.stopUsingItem();
                              }
                           }

                           return;
                        }

                        if (tag.hasUUID("SummonUUID0")) {
                           boolean finished = callForthSummons(serverLevel, player, stack, (int)progress);
                           level.playSound(
                              null,
                              circle.getX(),
                              circle.getY(),
                              circle.getZ(),
                              (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                              SoundSource.PLAYERS,
                              0.5F,
                              1.0F
                           );
                           if (finished) {
                              clearCircle(serverLevel, stack);
                              setCustomCooldown(stack, mode == 5 ? 240 : 90, mode == 5 ? "Cathedral" : "Summon");
                              player.swing(player.getUsedItemHand(), true);
                              player.stopUsingItem();
                           }
                        } else {
                           player.displayClientMessage(
                              Component.translatable("tensura.skill.stored_magicule", new Object[]{circle.getChargedEnergy(), 100.0F})
                                 .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
                              true
                           );
                           level.playSound(
                              null,
                              circle.getX(),
                              circle.getY(),
                              circle.getZ(),
                              (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                              SoundSource.PLAYERS,
                              0.25F,
                              2.0F
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
      if (level instanceof ServerLevel serverLevel && living instanceof Player player) {
         removeFailedSummons(serverLevel, stack);
         removeFailedMichael(serverLevel, stack);
         clearCircle(serverLevel, stack);
      }

      super.releaseUsing(stack, level, living, timeLeft);
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 72000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   private static boolean isSummonCooldownActive(ItemStack stack) {
      return getCooldownTicks(stack) > 0;
   }

   private static int getCooldownTicks(ItemStack stack) {
      return getTag(stack).getInt("ItemCooldownTicks");
   }

   private static String getCooldownType(ItemStack stack) {
      CompoundTag tag = getTag(stack);
      return tag.contains("ItemCooldownType") ? tag.getString("ItemCooldownType") : "Summon";
   }

   private static void setCustomCooldown(ItemStack stack, int ticks, String type) {
      CompoundTag tag = getTag(stack);
      tag.putInt("ItemCooldownTicks", Math.max(0, ticks));
      tag.putString("ItemCooldownType", type);
      saveTag(stack, tag);
   }

   private static LivingEntity getLookTarget(Player player) {
      Entity entity = ObjectSelectionHelper.getTargetingEntity(player, 48.0, false, true);
      return entity instanceof LivingEntity living && living != player && living.isAlive() ? living : null;
   }

   private static MagicCircle summonMagicCircle(Player owner, Vec3 pos, float size, int life) {
      MagicCircle circle = new MagicCircle(owner.level(), owner);
      circle.setLife(life);
      circle.setChargedEnergy(0.0F);
      circle.setPos(pos);
      circle.setSize(size);
      circle.setVariant(MagicCircleVariant.OTHERWORLDER);
      circle.setSkill((ManasSkillInstance)null);
      circle.setMode(0);
      owner.level().addFreshEntity(circle);
      circle.triggerAnim("controller", "start");
      owner.swing(InteractionHand.MAIN_HAND, true);
      return circle;
   }

   private static void bindCircle(ItemStack stack, MagicCircle circle) {
      if (circle != null) {
         CompoundTag tag = getTag(stack);
         tag.putUUID("SummonCircleUUID", circle.getUUID());
         saveTag(stack, tag);
      }
   }

   private static MagicCircle getBoundSummoningCircle(ServerLevel level, ItemStack stack) {
      CompoundTag tag = getTag(stack);
      if (!tag.hasUUID("SummonCircleUUID")) {
         return null;
      } else {
         return level.getEntity(tag.getUUID("SummonCircleUUID")) instanceof MagicCircle circle && circle.isAlive() ? circle : null;
      }
   }

   private static void clearCircleData(CompoundTag tag) {
      tag.remove("SummonCircleUUID");
   }

   private static void clearCircle(ServerLevel level, ItemStack stack) {
      CompoundTag tag = getTag(stack);
      if (tag.hasUUID("SummonCircleUUID")) {
         if (level.getEntity(tag.getUUID("SummonCircleUUID")) instanceof MagicCircle circle && circle.isAlive()) {
            circle.discard();
         }

         tag.remove("SummonCircleUUID");
         saveTag(stack, tag);
      }
   }

   private static void createSummons(Player player, ItemStack stack, Vec3 position, int mode) {
      Level level = player.level();
      CompoundTag tag = getTag(stack);
      int current = countActiveAngels(level, player);
      int max = mode == 5 ? 30 : (mode == 2 ? 9 : 3);
      int amount = Math.max(0, max - current);
      tag.putInt("SummonAmount", amount);

      for (int i = 0; i < amount; i++) {
         FeldwayAngelEntity angel = (FeldwayAngelEntity)((EntityType)ModEntityTypes.FELDWAY_ANGEL.get()).create(level);
         if (angel != null) {
            angel.setNoAi(true);
            angel.noPhysics = true;
            double angle = amount <= 1 ? 0.0 : (Math.PI * 2) * i / amount;
            double radius = mode == 5 ? 8.0 : (mode == 2 ? 4.5 : 0.0);
            double x = position.x + Math.cos(angle) * radius;
            double z = position.z + Math.sin(angle) * radius;
            double y = position.y - 1.5 * angel.getBbHeight();
            angel.setPos(x, y, z);
            angel.finalizeSpawn(
               (ServerLevelAccessor)level,
               level.getCurrentDifficultyAt(ObjectSelectionHelper.getBlockPos(position)),
               MobSpawnType.TRIGGERED,
               (SpawnGroupData)null
            );
            angel.applySummonedLoadout();
            angel.lookAt(Anchor.EYES, player.getEyePosition());
            level.addFreshEntity(angel);
            tag.putUUID("SummonUUID" + i, angel.getUUID());
         }
      }

      saveTag(stack, tag);
   }

   private static void createMichaelPending(Player player, ItemStack stack, Vec3 position) {
      CompoundTag tag = getTag(stack);
      MichaelManasEntity michael = (MichaelManasEntity)((EntityType)ModEntityTypes.MICHAEL_MANAS.get()).create(player.level());
      if (michael != null) {
         michael.setNoAi(true);
         michael.noPhysics = true;
         michael.moveTo(position.x, position.y - 2.0, position.z, player.getYRot(), 0.0F);
         michael.getPersistentData().putUUID("MichaelPlayerMaster", player.getUUID());
         player.level().addFreshEntity(michael);
         tag.putUUID("MichaelUUID", michael.getUUID());
         saveTag(stack, tag);
      }
   }

   private static boolean callForthMichael(ServerLevel level, Player player, ItemStack stack, int progress) {
      CompoundTag tag = getTag(stack);
      if (!tag.hasUUID("MichaelUUID")) {
         return false;
      }

      if (level.getEntity(tag.getUUID("MichaelUUID")) instanceof MichaelManasEntity michael) {
         boolean finished = progress >= 40;
         MagicCircle circle = getBoundSummoningCircle(level, stack);
         double startY = michael.getY();
         double targetY = circle != null ? circle.getY() + 0.5 : player.getY() + 2.5;
         double lerp = Math.min(1.0, progress / 40.0);
         double y = startY + (targetY - startY) * lerp;
         double x = circle != null ? circle.getX() : player.getX();
         double z = circle != null ? circle.getZ() : player.getZ();
         michael.setPos(x, y, z);
         michael.lookAt(Anchor.EYES, player.getEyePosition());
         if (finished) {
            michael.setNoAi(false);
            michael.noPhysics = false;
            michael.setMasterEntity(player);
            IExistence existence = TensuraStorages.getExistenceFrom(michael);
            existence.setSummoner(player.getUUID());
            existence.setTemporaryOwner(player.getUUID());
            existence.markDirty();
            LivingEntity target = getLookTarget(player);
            if (target != null && target.isAlive()) {
               michael.setTarget(target);
               michael.lookAt(Anchor.EYES, target.getEyePosition());
            }

            tag.remove("MichaelUUID");
            saveTag(stack, tag);
            michael.level()
               .playSound(
                  null, michael.getX(), michael.getY(), michael.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 0.9F, 0.9F
               );
         }

         return finished;
      } else {
         return false;
      }
   }

   private static boolean callForthSummons(ServerLevel level, Player player, ItemStack stack, int progress) {
      CompoundTag tag = getTag(stack);
      int amount = tag.getInt("SummonAmount");
      boolean finished = progress >= 40;
      MagicCircle circle = getBoundSummoningCircle(level, stack);

      for (int i = 0; i < amount; i++) {
         if (tag.hasUUID("SummonUUID" + i) && level.getEntity(tag.getUUID("SummonUUID" + i)) instanceof FeldwayAngelEntity angel) {
            double angle = amount <= 1 ? 0.0 : (Math.PI * 2) * i / amount;
            double radius = amount >= 20 ? 10.0 : (amount > 3 ? 5.0 : 2.6);
            double x = angel.getX();
            double z = angel.getZ();
            if (circle != null) {
               x = circle.getX() + Math.cos(angle) * radius;
               z = circle.getZ() + Math.sin(angle) * radius;
            }

            double startY = angel.getY();
            double targetY = (circle != null ? circle.getY() : angel.getY()) + 0.15;
            double lerp = Math.min(1.0, progress / 40.0);
            double y = startY + (targetY - startY) * lerp;
            angel.setPos(x, y, z);
            angel.lookAt(Anchor.EYES, player.getEyePosition());
            if (finished) {
               finalizeSummon(player, angel);
            }
         }
      }

      if (finished) {
         clearPendingData(tag);
         saveTag(stack, tag);
      }

      return finished;
   }

   private static void finalizeSummon(Player player, FeldwayAngelEntity angel) {
      angel.setNoAi(false);
      angel.noPhysics = false;
      angel.setMasterEntity(player);
      IExistence existence = TensuraStorages.getExistenceFrom(angel);
      existence.setSummoner(player.getUUID());
      existence.setTemporaryOwner(player.getUUID());
      existence.markDirty();
      SubordinateHelper.removeTarget(angel);
      LivingEntity target = getLookTarget(player);
      if (target != null && target.isAlive()) {
         angel.setTarget(target);
         angel.lookAt(Anchor.EYES, target.getEyePosition());
      }

      angel.level()
         .playSound(null, angel.getX(), angel.getY(), angel.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 0.7F, 1.15F);
   }

   private static int commandAttack(ServerLevel level, Player player, ItemStack stack, LivingEntity target) {
      if (target != null && target.isAlive()) {
         int count = 0;

         for (FeldwayAngelEntity angel : getOwnedAngels(level, player)) {
            double tx = target.getX() + (level.random.nextDouble() - 0.5) * 6.0;
            double ty = target.getY() + 3.0 + level.random.nextDouble() * 2.0;
            double tz = target.getZ() + (level.random.nextDouble() - 0.5) * 6.0;
            angel.teleportTo(tx, ty, tz);
            angel.setDeltaMovement(Vec3.ZERO);
            angel.commandAttackTarget(target);
            angel.lookAt(Anchor.EYES, target.getEyePosition());
            count++;
         }

         MichaelManasEntity michael = getOwnedMichael(level, stack, player);
         if (michael != null && michael.isAlive()) {
            double tx = target.getX() + (level.random.nextDouble() - 0.5) * 8.0;
            double ty = target.getY() + 5.0 + level.random.nextDouble() * 2.5;
            double tz = target.getZ() + (level.random.nextDouble() - 0.5) * 8.0;
            michael.teleportTo(tx, ty, tz);
            michael.setDeltaMovement(Vec3.ZERO);
            michael.setTarget(target);
            michael.lookAt(Anchor.EYES, target.getEyePosition());
            count++;
         }

         return count;
      } else {
         return 0;
      }
   }

   private static MichaelManasEntity getOwnedMichael(ServerLevel level, ItemStack stack, Player player) {
      CompoundTag tag = getTag(stack);
      if (tag.hasUUID("MichaelUUID") && level.getEntity(tag.getUUID("MichaelUUID")) instanceof MichaelManasEntity michael && michael.isAlive()) {
         return michael;
      }

      List<MichaelManasEntity> nearby = level.getEntitiesOfClass(
         MichaelManasEntity.class,
         player.getBoundingBox().inflate(256.0),
         e -> e.isAlive()
            && e.getPersistentData().hasUUID("MichaelPlayerMaster")
            && player.getUUID().equals(e.getPersistentData().getUUID("MichaelPlayerMaster"))
      );
      if (nearby.isEmpty()) {
         return null;
      }

      nearby.sort(Comparator.comparingDouble(player::distanceToSqr));
      return nearby.get(0);
   }

   private static int despawnOwnedSummons(ServerLevel level, Player player, ItemStack stack) {
      int removed = 0;

      for (FeldwayAngelEntity angel : getOwnedAngels(level, player)) {
         level.sendParticles(ParticleTypes.FLASH, angel.getX(), angel.getY() + angel.getBbHeight() * 0.5, angel.getZ(), 8, 0.4, 0.4, 0.4, 0.0);
         angel.discard();
         removed++;
      }

      MichaelManasEntity michael = getOwnedMichael(level, stack, player);
      if (michael != null) {
         level.sendParticles(ParticleTypes.FLASH, michael.getX(), michael.getY() + michael.getBbHeight() * 0.5, michael.getZ(), 12, 0.6, 0.6, 0.6, 0.0);
         michael.discard();
         removed++;
      }

      CompoundTag tag = getTag(stack);
      tag.remove("MichaelUUID");
      clearCircleData(tag);
      saveTag(stack, tag);
      return removed;
   }

   private static void removeFailedSummons(ServerLevel level, ItemStack stack) {
      CompoundTag tag = getTag(stack);

      for (int i = 0; i < 30; i++) {
         if (tag.hasUUID("SummonUUID" + i) && level.getEntity(tag.getUUID("SummonUUID" + i)) instanceof Mob mob && mob.isNoAi()) {
            double x = mob.getX();
            double y = mob.getY() + mob.getBbHeight() * 0.5;
            double z = mob.getZ();
            mob.discard();
            level.playSound(null, x, y, z, (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
            level.sendParticles(ParticleTypes.FLASH, x, y, z, 8, 0.2, 0.4, 0.2, 0.0);
            level.sendParticles(ParticleTypes.FLASH, x, y, z, 16, 1.0, 1.0, 1.0, 0.0);
         }
      }

      clearPendingData(tag);
      saveTag(stack, tag);
   }

   private static void removeFailedMichael(ServerLevel level, ItemStack stack) {
      CompoundTag tag = getTag(stack);
      if (tag.hasUUID("MichaelUUID")) {
         if (level.getEntity(tag.getUUID("MichaelUUID")) instanceof MichaelManasEntity michael && michael.isNoAi()) {
            double x = michael.getX();
            double y = michael.getY() + michael.getBbHeight() * 0.5;
            double z = michael.getZ();
            michael.discard();
            level.playSound(null, x, y, z, (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
            level.sendParticles(ParticleTypes.FLASH, x, y, z, 12, 0.6, 0.6, 0.6, 0.0);
            tag.remove("MichaelUUID");
            saveTag(stack, tag);
         }
      }
   }

   private static List<FeldwayAngelEntity> getOwnedAngels(ServerLevel level, Player player) {
      List<FeldwayAngelEntity> angels = level.getEntitiesOfClass(
         FeldwayAngelEntity.class, player.getBoundingBox().inflate(256.0), e -> e.isAlive() && e.isMaster(player)
      );
      angels.sort(Comparator.comparingDouble(player::distanceToSqr));
      return angels;
   }

   private static int countActiveAngels(Level level, Player player) {
      int count = 0;

      for (FeldwayAngelEntity angel : level.getEntitiesOfClass(
         FeldwayAngelEntity.class, player.getBoundingBox().inflate(256.0), e -> e.isAlive() && e.isMaster(player)
      )) {
         if (++count >= 30) {
            return count;
         }
      }

      return count;
   }

   private static int getMode(CompoundTag tag) {
      return Math.max(0, Math.min(6, tag.getInt("FeldRelicMode")));
   }

   private static void setMode(CompoundTag tag, int mode) {
      tag.putInt("FeldRelicMode", mode);
   }

   private static String getModeName(int mode) {
      return switch (mode) {
         case 0 -> "Summon";
         case 1 -> "Attack";
         case 2 -> "Army";
         case 3 -> "Despawn";
         case 4 -> "Ultimate";
         case 5 -> "Cathedral";
         case 6 -> "Immunity";
         default -> "Summon";
      };
   }

   private static void clearPendingData(CompoundTag tag) {
      tag.remove("SummonAmount");
      tag.remove("ArmySummon");
      tag.remove("CathedralSummon");

      for (int i = 0; i < 30; i++) {
         tag.remove("SummonUUID" + i);
      }
   }

   private static CompoundTag getTag(ItemStack stack) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      return data.copyTag();
   }

   public static boolean hasMagiculePoisonImmunityEnabled(ItemStack stack) {
      return getTag(stack).getBoolean("MagiculePoisonImmunity");
   }

   private static void saveTag(ItemStack stack, CompoundTag tag) {
      if (tag.isEmpty()) {
         stack.remove(DataComponents.CUSTOM_DATA);
      } else {
         stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      }
   }
}
