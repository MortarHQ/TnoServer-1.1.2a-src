package com.bobmowzie.mowziesmobs.server.ability;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class Ability<T extends LivingEntity> {
   private final AbilitySection[] sectionTrack;
   protected int cooldownMax;
   private final AbilityType<T, ? extends Ability> abilityType;
   private final T user;
   private final AbilityData abilityData;
   private int ticksInUse;
   private int ticksInSection;
   private int currentSectionIndex;
   private boolean isUsing;
   private int cooldownTimer;
   protected Random rand;
   protected RawAnimation activeAnimation;

   public Ability(AbilityType<T, ? extends Ability> abilityType, T user, AbilitySection[] sectionTrack, int cooldownMax) {
      this.abilityType = abilityType;
      this.user = user;
      this.abilityData = DataHandler.getData(user, DataHandler.ABILITY_DATA);
      this.sectionTrack = sectionTrack;
      this.cooldownMax = cooldownMax;
      this.rand = new Random();
   }

   public Ability(AbilityType<T, ? extends Ability> abilityType, T user, AbilitySection[] sectionTrack) {
      this(abilityType, user, sectionTrack, 0);
   }

   public void start() {
      if (!this.runsInBackground()) {
         this.abilityData.setActiveAbility(this);
      }

      this.ticksInUse = 0;
      this.ticksInSection = 0;
      this.currentSectionIndex = 0;
      this.isUsing = true;
      this.beginSection(this.getSectionTrack()[0]);
   }

   public void playAnimation(RawAnimation animation) {
      if (this.getUser() instanceof MowzieGeckoEntity && this.getUser().level().isClientSide()) {
         MowzieGeckoEntity entity = (MowzieGeckoEntity)this.getUser();
         this.activeAnimation = animation;
         MowzieAnimationController<MowzieGeckoEntity> controller = entity.getController();
         if (controller != null) {
            controller.playAnimation((T)entity, animation);
         }
      }
   }

   public void tick() {
      if (this.isUsing()) {
         if (this.getUser().isEffectiveAi() && !this.canContinueUsing()) {
            AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.getUser(), this.abilityType);
         }

         this.tickUsing();
         this.ticksInUse++;
         this.ticksInSection++;
         AbilitySection section = this.getCurrentSection();
         if (section instanceof AbilitySection.AbilitySectionInstant) {
            this.nextSection();
         } else if (section instanceof AbilitySection.AbilitySectionDuration sectionDuration && this.ticksInSection > sectionDuration.duration) {
            this.nextSection();
         }
      } else {
         this.tickNotUsing();
         if (this.getCooldownTimer() > 0) {
            this.cooldownTimer--;
         }
      }
   }

   public void tickUsing() {
   }

   public void tickNotUsing() {
   }

   public void end() {
      this.ticksInUse = 0;
      this.ticksInSection = 0;
      this.isUsing = false;
      this.cooldownTimer = this.getMaxCooldown();
      this.currentSectionIndex = 0;
      if (!this.runsInBackground()) {
         this.abilityData.setActiveAbility(null);
      }
   }

   public void interrupt() {
      this.end();
   }

   public void complete() {
      this.end();
   }

   public boolean canUse() {
      if (this.getUser().hasEffect(EffectHandler.FROZEN)) {
         return false;
      }

      boolean toReturn = (!this.isUsing() || this.canCancelSelf()) && this.cooldownTimer == 0;
      if (!this.runsInBackground()) {
         toReturn = toReturn
            && (
               this.abilityData.getActiveAbility() == null || this.canCancelActiveAbility() || this.abilityData.getActiveAbility().canBeCanceledByAbility(this)
            );
      }

      return toReturn;
   }

   public boolean tryAbility() {
      return true;
   }

   public boolean canCancelActiveAbility() {
      return false;
   }

   public Ability<?> getActiveAbility() {
      AbilityData data = this.getAbilityCapability();
      return data == null ? null : this.getAbilityCapability().getActiveAbility();
   }

   public boolean canCancelSelf() {
      return false;
   }

   public boolean canBeCanceledByAbility(Ability<?> ability) {
      return false;
   }

   protected boolean canContinueUsing() {
      return !this.getUser().hasEffect(EffectHandler.FROZEN);
   }

   public boolean isUsing() {
      return this.isUsing;
   }

   public T getUser() {
      return this.user;
   }

   public Level getLevel() {
      return this.user.level();
   }

   public int getTicksInUse() {
      return this.ticksInUse;
   }

   public int getTicksInSection() {
      return this.ticksInSection;
   }

   public int getCooldownTimer() {
      return this.cooldownTimer;
   }

   public void nextSection() {
      this.jumpToSection(this.currentSectionIndex + 1);
   }

   public void jumpToSection(int sectionIndex) {
      this.endSection(this.getCurrentSection());
      this.currentSectionIndex = sectionIndex;
      this.ticksInSection = 0;
      if (this.currentSectionIndex >= this.getSectionTrack().length) {
         this.complete();
      } else {
         this.beginSection(this.getCurrentSection());
      }
   }

   protected void endSection(AbilitySection section) {
   }

   protected void beginSection(AbilitySection section) {
   }

   public AbilitySection getCurrentSection() {
      return this.currentSectionIndex >= this.getSectionTrack().length ? null : this.getSectionTrack()[this.currentSectionIndex];
   }

   public int getCurrentSectionIndex() {
      return this.currentSectionIndex;
   }

   public boolean damageInterrupts() {
      return false;
   }

   public int damageInterruptThreshold() {
      return 3;
   }

   public void onTakeDamage(Post event) {
      if (this.isUsing() && event.getNewDamage() >= this.damageInterruptThreshold() && this.damageInterrupts()) {
         AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.getUser(), this.getAbilityType());
      }
   }

   public boolean runsInBackground() {
      return false;
   }

   public boolean preventsAttacking() {
      return true;
   }

   public boolean preventsBlockBreakingBuilding() {
      return true;
   }

   public boolean preventsInteracting() {
      return true;
   }

   public boolean preventsItemUse(ItemStack stack) {
      return true;
   }

   public AbilitySection[] getSectionTrack() {
      return this.sectionTrack;
   }

   public int getMaxCooldown() {
      return this.cooldownMax;
   }

   public AbilityData getAbilityCapability() {
      return this.abilityData;
   }

   public <E extends GeoEntity> PlayState animationPredicate(AnimationState<E> e, GeckoPlayer.Perspective perspective) {
      if (this.activeAnimation != null && !this.activeAnimation.getAnimationStages().isEmpty()) {
         e.getController().setAnimation(this.activeAnimation);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public void codeAnimations(MowzieGeoModel<? extends GeoEntity> model, float partialTick) {
   }

   public boolean isAnimating() {
      return this.isUsing();
   }

   public AbilityType<T, ? extends Ability> getAbilityType() {
      return this.abilityType;
   }

   public List<LivingEntity> getEntityLivingBaseNearby(LivingEntity player, double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(player, (Class<T>)LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double r) {
      return player.level().getEntitiesOfClass(entityClass, player.getBoundingBox().inflate(r, r, r), e -> e != player && player.distanceTo(e) <= r);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return player.level().getEntitiesOfClass(entityClass, player.getBoundingBox().inflate(dX, dY, dZ), e -> e != player && player.distanceTo(e) <= r);
   }

   public CompoundTag writeNBT() {
      CompoundTag compound = new CompoundTag();
      if (this.isUsing()) {
         compound.putInt("ticks_in_use", this.ticksInUse);
         compound.putInt("ticks_in_section", this.ticksInSection);
         compound.putInt("current_section", this.currentSectionIndex);
      } else if (this.cooldownTimer > 0) {
         compound.putInt("cooldown_timer", this.cooldownTimer);
      }

      return compound;
   }

   public void readNBT(Tag nbt) {
      CompoundTag compound = (CompoundTag)nbt;
      this.isUsing = compound.contains("ticks_in_use");
      if (this.isUsing) {
         this.ticksInUse = compound.getInt("ticks_in_use");
         this.ticksInSection = compound.getInt("ticks_in_section");
         this.currentSectionIndex = compound.getInt("current_section");
      } else {
         this.cooldownTimer = compound.getInt("cooldown_timer");
      }
   }

   public void onRenderTick(RenderFrameEvent event) {
   }
}
