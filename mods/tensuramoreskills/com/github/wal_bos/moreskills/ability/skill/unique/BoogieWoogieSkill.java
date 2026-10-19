package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoogieWoogieSkill extends Skill {
   private static final String TAG_SELECTED = "selected_targets";
   private static final String TAG_LAST_APPLY = "boogie_last_apply";
   private static final ResourceLocation BOOGIE_WOOGIE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "boogie_woogie");

   public BoogieWoogieSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.BoogieWoogieConfig cfg() {
      return MoreSkillsConfigs.GRAND.boogieWoogie;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/boogie_woogie.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 1 : 0;
      } else {
         return mode == 1 ? 0 : 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "boogie_woogie.self_exchange";
         case 1 -> "boogie_woogie.target_mark";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, BOOGIE_WOOGIE_ID, true);
      if (entity.level() instanceof ServerLevel level) {
         instance.getOrCreateTag().putLong("boogie_last_apply", level.getGameTime() - ((Integer)cfg().applyEveryTicks.get()).intValue());
         instance.markDirty();
         applyBuffs(instance, entity);
      }

      entity.level()
         .playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            SoundEvents.AMETHYST_BLOCK_CHIME,
            SoundSource.PLAYERS,
            ((Double)cfg().toggleOnVolume.get()).floatValue(),
            ((Double)cfg().toggleOnPitch.get()).floatValue()
         );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, BOOGIE_WOOGIE_ID, false);
      clearBuffs(entity);
      entity.level()
         .playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            SoundEvents.AMETHYST_BLOCK_CHIME,
            SoundSource.PLAYERS,
            ((Double)cfg().toggleOffVolume.get()).floatValue(),
            ((Double)cfg().toggleOffPitch.get()).floatValue()
         );
   }

   public void onTick(@NotNull ManasSkillInstance instance, @NotNull LivingEntity entity) {
      if (instance.isToggled()) {
         if (entity.level() instanceof ServerLevel level) {
            CompoundTag var9 = instance.getOrCreateTag();
            long now = level.getGameTime();
            long last = var9.getLong("boogie_last_apply");
            if (now - last >= ((Integer)cfg().applyEveryTicks.get()).intValue()) {
               var9.putLong("boogie_last_apply", now);
               instance.markDirty();
               applyBuffs(instance, entity);
               instance.addMasteryPoint(entity);
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (mode == 0) {
            if (entity.isShiftKeyDown()) {
               this.exchangeOther(level, entity, instance);
            } else {
               this.exchangeSelf(level, entity, instance);
            }
         } else if (entity.isShiftKeyDown()) {
            this.clearSelected(instance);
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("tensuramoreskills.skill.boogie_woogie.targets_cleared").withStyle(ChatFormatting.GRAY), true);
            }
         } else {
            Entity projectile = this.getProjectileCandidate(level, entity);
            if (projectile != null) {
               this.rememberTarget(instance, projectile.getUUID());
               this.notifySelected(entity, projectile);
            } else {
               LivingEntity looked = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().range.get(), false);
               if (looked != null && looked != entity) {
                  this.rememberTarget(instance, looked.getUUID());
                  this.notifySelected(entity, looked);
               }
            }
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      if (!(attacker instanceof Player)) {
         return true;
      }

      if (target == attacker) {
         return true;
      }

      this.rememberTarget(instance, target.getUUID());
      this.notifySelected(attacker, target);
      return true;
   }

   private static void applyBuffs(ManasSkillInstance instance, LivingEntity entity) {
      entity.addEffect(
         new MobEffectInstance(MobEffects.DAMAGE_BOOST, (Integer)cfg().buffDurationTicks.get(), (Integer)cfg().strengthAmplifier.get(), false, false, false)
      );
      entity.addEffect(
         new MobEffectInstance(
            MobEffects.DAMAGE_RESISTANCE, (Integer)cfg().buffDurationTicks.get(), (Integer)cfg().resistanceAmplifier.get(), false, false, false
         )
      );
      if (instance.isMastered(entity)) {
         entity.addEffect(
            new MobEffectInstance(
               MobEffects.MOVEMENT_SPEED, (Integer)cfg().buffDurationTicks.get(), (Integer)cfg().masteredSpeedAmplifier.get(), false, false, false
            )
         );
      } else {
         entity.removeEffect(MobEffects.MOVEMENT_SPEED);
      }
   }

   private static void clearBuffs(LivingEntity entity) {
      entity.removeEffect(MobEffects.DAMAGE_BOOST);
      entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
      entity.removeEffect(MobEffects.MOVEMENT_SPEED);
   }

   private void exchangeSelf(ServerLevel level, LivingEntity self, ManasSkillInstance instance) {
      Entity target = this.firstSelectedInRange(level, self, instance);
      if (target == null) {
         target = ObjectSelectionHelper.getTargetingEntity(self, (Double)cfg().range.get(), false);
      }

      if (target != null && target != self) {
         swap(level, self, target);
         instance.addMasteryPoint(self);
      }
   }

   private void exchangeOther(ServerLevel level, LivingEntity self, ManasSkillInstance instance) {
      List<Entity> chosen = new ArrayList<>();

      for (Entity entity : this.selectedInRange(level, self, instance)) {
         if (entity != self && !chosen.contains(entity)) {
            chosen.add(entity);
         }

         if (chosen.size() >= 2) {
            break;
         }
      }

      if (chosen.size() < 2) {
         List<LivingEntity> nearby = level.getEntitiesOfClass(
            LivingEntity.class,
            self.getBoundingBox().inflate((Double)cfg().range.get()),
            entityx -> entityx.isAlive() && entityx != self && !chosen.contains(entityx)
         );
         nearby.sort(Comparator.comparingDouble(entityx -> entityx.distanceToSqr(self)));

         for (LivingEntity entity : nearby) {
            chosen.add(entity);
            if (chosen.size() >= 2) {
               break;
            }
         }
      }

      if (chosen.size() >= 2) {
         swap(level, chosen.get(0), chosen.get(1));
         instance.addMasteryPoint(self);
      }
   }

   private static void swap(ServerLevel level, Entity first, Entity second) {
      double firstX = first.getX();
      double firstY = first.getY();
      double firstZ = first.getZ();
      float firstYaw = first.getYRot();
      float firstPitch = first.getXRot();
      double secondX = second.getX();
      double secondY = second.getY();
      double secondZ = second.getZ();
      float secondYaw = second.getYRot();
      float secondPitch = second.getXRot();
      first.teleportTo(secondX, secondY, secondZ);
      second.teleportTo(firstX, firstY, firstZ);
      first.setYRot(secondYaw);
      first.setXRot(secondPitch);
      second.setYRot(firstYaw);
      second.setXRot(firstPitch);
      spawnSwapParticles(level, first);
      spawnSwapParticles(level, second);
      level.playSound(
         null,
         first.blockPosition(),
         SoundEvents.AMETHYST_BLOCK_CHIME,
         SoundSource.PLAYERS,
         ((Double)cfg().swapSoundVolume.get()).floatValue(),
         ((Double)cfg().swapSoundPitch.get()).floatValue()
      );
      level.playSound(
         null,
         second.blockPosition(),
         SoundEvents.AMETHYST_BLOCK_CHIME,
         SoundSource.PLAYERS,
         ((Double)cfg().swapSoundVolume.get()).floatValue(),
         ((Double)cfg().swapSoundPitch.get()).floatValue()
      );
   }

   private static void spawnSwapParticles(ServerLevel level, Entity entity) {
      double y = entity.getY() + entity.getBbHeight() * 0.5;
      level.sendParticles(
         ParticleTypes.PORTAL,
         entity.getX(),
         y,
         entity.getZ(),
         (Integer)cfg().swapPortalParticles.get(),
         (Double)cfg().portalParticleHorizontalSpread.get(),
         (Double)cfg().portalParticleVerticalSpread.get(),
         (Double)cfg().portalParticleHorizontalSpread.get(),
         (Double)cfg().portalParticleSpeed.get()
      );
      level.sendParticles(
         ParticleTypes.END_ROD,
         entity.getX(),
         y,
         entity.getZ(),
         (Integer)cfg().swapEndRodParticles.get(),
         (Double)cfg().endRodParticleHorizontalSpread.get(),
         (Double)cfg().endRodParticleVerticalSpread.get(),
         (Double)cfg().endRodParticleHorizontalSpread.get(),
         (Double)cfg().endRodParticleSpeed.get()
      );
   }

   private void rememberTarget(ManasSkillInstance instance, UUID id) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag list = tag.getList("selected_targets", 11);
      ListTag out = new ListTag();

      for (Tag selectedTag : list) {
         if (selectedTag instanceof IntArrayTag arrayTag) {
            UUID existing = uuidFromIntArray(arrayTag);
            if (!existing.equals(id)) {
               out.add(arrayTag.copy());
            }
         }
      }

      out.add(NbtUtils.createUUID(id));

      while (out.size() > cfg().maxSelectedTargets.get()) {
         out.remove(0);
      }

      tag.put("selected_targets", out);
      instance.markDirty();
   }

   private void clearSelected(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("selected_targets");
      instance.markDirty();
   }

   @Nullable
   private Entity firstSelectedInRange(ServerLevel level, LivingEntity self, ManasSkillInstance instance) {
      List<Entity> list = this.selectedInRange(level, self, instance);
      return list.isEmpty() ? null : list.get(0);
   }

   private List<Entity> selectedInRange(ServerLevel level, LivingEntity self, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag list = tag.getList("selected_targets", 11);
      List<Entity> out = new ArrayList<>();
      double range = (Double)cfg().range.get();

      for (Tag selectedTag : list) {
         if (selectedTag instanceof IntArrayTag arrayTag) {
            UUID id = uuidFromIntArray(arrayTag);
            Entity entity = level.getEntity(id);
            if (entity != null && !(entity.distanceToSqr(self) > range * range)) {
               out.add(entity);
            }
         }
      }

      out.sort(Comparator.comparingDouble(entityx -> entityx.distanceToSqr(self)));
      return out;
   }

   private static UUID uuidFromIntArray(IntArrayTag arrayTag) {
      int[] array = arrayTag.getAsIntArray();
      if (array.length != 4) {
         return new UUID(0L, 0L);
      }

      long most = (long)array[0] << 32 | array[1] & 4294967295L;
      long least = (long)array[2] << 32 | array[3] & 4294967295L;
      return new UUID(most, least);
   }

   @Nullable
   private Entity getProjectileCandidate(ServerLevel level, LivingEntity entity) {
      ItemStack stack = entity.getMainHandItem();
      if (!(stack.getItem() instanceof BowItem) && !(stack.getItem() instanceof CrossbowItem) && !(stack.getItem() instanceof TridentItem)) {
         return null;
      }

      double range = (Double)cfg().range.get();
      AABB box = entity.getBoundingBox().inflate(range);
      List<Projectile> projectiles = level.getEntitiesOfClass(
         Projectile.class, box, projectile -> projectile.isAlive() && projectile.distanceToSqr(entity) <= range * range
      );
      return projectiles.isEmpty()
         ? null
         : (Entity)projectiles.stream().min(Comparator.comparingDouble(projectile -> projectile.distanceToSqr(entity))).orElse(null);
   }

   private void notifySelected(LivingEntity entity, Entity target) {
      if (entity instanceof ServerPlayer player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.boogie_woogie.target_selected", new Object[]{target.getName()}).withStyle(ChatFormatting.GOLD),
            true
         );
      }
   }

   public MutableComponent getName() {
      return Component.translatable("tensuramoreskills.skill.boogie_woogie").withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD});
   }

   public MutableComponent getSkillDescription() {
      return Component.translatable("tensuramoreskills.skill.boogie_woogie.description");
   }
}
