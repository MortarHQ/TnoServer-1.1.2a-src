package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoFirstPersonRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animation.RawAnimation;

public class SupernovaAbility extends HeliomancyAbilityBase {
   private boolean leftClickDown;
   private boolean rightClickDown;
   private int timeSinceLeftUp;
   private int timeSinceRightUp;
   private static final int BUFFER = 5;
   private Vec3[] particleEmitter = new Vec3[1];
   private static final RawAnimation SUPERNOVA_ANIM = RawAnimation.begin().thenPlay("supernova");

   public SupernovaAbility(AbilityType<Player, SupernovaAbility> abilityType, Player user) {
      super(abilityType, user, EntityUmvuthi.SupernovaAbility.SECTION_TRACK);
   }

   @Override
   public void start() {
      super.start();
      this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SUPERNOVA_START.get(), 3.0F, 1.0F);
      this.playAnimation(SUPERNOVA_ANIM);
      if (this.getLevel().isClientSide) {
         this.heldItemMainHandVisualOverride = ItemStack.EMPTY;
         this.heldItemOffHandVisualOverride = ItemStack.EMPTY;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() < 84) {
         this.getUser().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 4, false, false));
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.getUser().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2, 1, false, false));
      }

      if (this.getTicksInUse() == 30) {
         this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SUPERNOVA_BLACKHOLE.get(), 2.0F, 1.2F);
      }

      if (this.getTicksInUse() < 30) {
         for (LivingEntity inRange : this.getEntityLivingBaseNearby(this.getUser(), 16.0, 16.0, 16.0, 16.0)) {
            if (!(inRange instanceof Player) || !((Player)inRange).getAbilities().invulnerable) {
               Vec3 diff = inRange.position().subtract(this.getUser().position().add(0.0, 3.0, 0.0));
               diff = diff.normalize().scale(0.03);
               inRange.setDeltaMovement(inRange.getDeltaMovement().subtract(diff));
               if (inRange.getY() < this.getUser().getY() + 3.0) {
                  inRange.setDeltaMovement(inRange.getDeltaMovement().add(0.0, 0.075, 0.0));
               }
            }
         }
      }

      if (this.getLevel().isClientSide) {
         if (this.getUser() == MMCommon.PROXY.getLocalPlayer() && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer(this.getUser(), GeckoPlayer.Perspective.FIRST_PERSON);
            if (geckoPlayer != null) {
               GeckoFirstPersonRenderer renderPlayer = (GeckoFirstPersonRenderer)geckoPlayer.getPlayerRenderer();
               if (renderPlayer.particleEmitterRoot != null) {
                  this.particleEmitter[0] = renderPlayer.particleEmitterRoot;
               }
            }
         } else {
            GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer(this.getUser(), GeckoPlayer.Perspective.THIRD_PERSON);
            if (geckoPlayer != null) {
               GeckoRenderPlayer renderPlayer = (GeckoRenderPlayer)geckoPlayer.getPlayerRenderer();
               if (renderPlayer.particleEmitterRoot != null) {
                  this.particleEmitter[0] = this.getUser()
                     .position()
                     .add(renderPlayer.particleEmitterRoot)
                     .add(0.0, this.getUser().getBbHeight() / 2.0F + 0.3F, 0.0);
               }
            }
         }

         EntityUmvuthi.SupernovaAbility.superNovaEffects(this, this.particleEmitter, this.getLevel());
      }
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getUser().level().isClientSide) {
         EntitySuperNova superNova = new EntitySuperNova(
            (EntityType<? extends EntitySuperNova>)EntityHandler.SUPER_NOVA.get(),
            this.getUser().level(),
            this.getUser(),
            this.getUser().getX(),
            this.getUser().getY() + this.getUser().getBbHeight() / 2.0F,
            this.getUser().getZ()
         );
         this.getUser().level().addFreshEntity(superNova);
         MobEffectInstance sunsBlessingInstance = this.getUser().getEffect(EffectHandler.SUNS_BLESSING);
         if (sunsBlessingInstance != null) {
            int duration = sunsBlessingInstance.getDuration();
            this.getUser().removeEffect(EffectHandler.SUNS_BLESSING);
            int supernovaCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.supernovaCost.get() * 60 * 20;
            if (duration - supernovaCost > 0) {
               this.getUser().addEffect(new MobEffectInstance(EffectHandler.SUNS_BLESSING, duration - supernovaCost, 0, false, false));
            }
         }
      }
   }

   @Override
   public void onLeftMouseDown(Player player) {
      super.onLeftMouseDown(player);
      if (player == this.getUser()) {
         this.leftClickDown = true;
      }
   }

   @Override
   public void onLeftMouseUp(Player player) {
      super.onLeftMouseUp(player);
      if (player == this.getUser()) {
         this.leftClickDown = false;
         this.timeSinceLeftUp = 5;
      }
   }

   @Override
   public void onRightMouseDown(Player player) {
      super.onRightMouseDown(player);
      if (player == this.getUser()) {
         this.rightClickDown = true;
      }
   }

   @Override
   public void onRightMouseUp(Player player) {
      super.onRightMouseUp(player);
      if (player == this.getUser()) {
         this.rightClickDown = false;
         this.timeSinceRightUp = 5;
      }
   }

   public boolean isRightClickDown() {
      return this.rightClickDown || this.timeSinceRightUp > 0;
   }

   public boolean isLeftClickDown() {
      return this.leftClickDown || this.timeSinceLeftUp > 0;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getUser().isShiftKeyDown() && this.isLeftClickDown() && this.isRightClickDown()) {
         AbilityHandler.INSTANCE.sendAbilityMessage((Player & LivingEntity)this.getUser(), AbilityHandler.SUPERNOVA_ABILITY);
      }

      if (this.timeSinceRightUp > 0) {
         this.timeSinceRightUp--;
      }

      if (this.timeSinceLeftUp > 0) {
         this.timeSinceLeftUp--;
      }
   }

   @Override
   public boolean canCancelActiveAbility() {
      Ability<?> ability = this.getActiveAbility();
      return ability != null
         && (ability.getAbilityType() == AbilityHandler.SOLAR_FLARE_ABILITY || ability.getAbilityType() == AbilityHandler.SOLAR_BEAM_ABILITY)
         && ability.getTicksInUse() < 5;
   }
}
