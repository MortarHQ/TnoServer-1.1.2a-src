package com.bobmowzie.mowziesmobs.server.ability;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.Animation.LoopType;

public class PlayerAbility extends Ability<Player> {
   protected RawAnimation activeFirstPersonAnimation;
   protected ItemStack heldItemMainHandVisualOverride;
   protected ItemStack heldItemOffHandVisualOverride;
   protected PlayerAbility.HandDisplay firstPersonMainHandDisplay;
   protected PlayerAbility.HandDisplay firstPersonOffHandDisplay;
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public PlayerAbility(AbilityType<Player, ? extends Ability<?>> abilityType, Player user, AbilitySection[] sectionTrack, int cooldownMax) {
      super(abilityType, user, sectionTrack, cooldownMax);
      if (user.level().isClientSide) {
         this.activeAnimation = IDLE_ANIM;
         this.heldItemMainHandVisualOverride = null;
         this.heldItemOffHandVisualOverride = null;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
      }
   }

   public PlayerAbility(AbilityType<Player, ? extends Ability> abilityType, Player user, AbilitySection[] sectionTrack) {
      this(abilityType, user, sectionTrack, 0);
   }

   public void playAnimation(RawAnimation animation, GeckoPlayer.Perspective perspective) {
      if (this.getUser() != null && this.getUser().level().isClientSide()) {
         if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
            this.activeFirstPersonAnimation = animation;
         } else {
            this.activeAnimation = animation;
         }

         MowzieAnimationController<GeckoPlayer> controller = GeckoPlayer.getAnimationController(this.getUser(), perspective);
         GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer(this.getUser(), perspective);
         if (controller != null && geckoPlayer != null) {
            controller.playAnimation(geckoPlayer, animation);
         }
      }
   }

   public void playAnimation(String animationName, GeckoPlayer.Perspective perspective, LoopType loopType) {
      this.playAnimation(RawAnimation.begin().then(animationName, loopType), perspective);
   }

   @Override
   public void playAnimation(RawAnimation animation) {
      this.playAnimation(animation, GeckoPlayer.Perspective.FIRST_PERSON);
      this.playAnimation(animation, GeckoPlayer.Perspective.THIRD_PERSON);
   }

   public void playAnimation3rdPerson(RawAnimation animation) {
      this.playAnimation(animation, GeckoPlayer.Perspective.THIRD_PERSON);
   }

   public InteractionHand getActiveHand() {
      return this.getUser().getUsedItemHand();
   }

   public void playAnimation(String animationName, LoopType loopType, boolean separateLeftAndRight1stPerson, boolean separateLeftAndRight3rdPerson) {
      boolean usingMainHand = this.getActiveHand() == InteractionHand.MAIN_HAND;
      boolean isRightHanded = this.getUser().getMainArm() == HumanoidArm.RIGHT;
      if (separateLeftAndRight1stPerson) {
         this.playAnimation(animationName + (usingMainHand ? "_right" : "_left"), GeckoPlayer.Perspective.FIRST_PERSON, loopType);
      } else {
         this.playAnimation(animationName, GeckoPlayer.Perspective.FIRST_PERSON, loopType);
      }

      if (separateLeftAndRight3rdPerson) {
         this.playAnimation(animationName + "_" + (usingMainHand == isRightHanded ? "right" : "left"), GeckoPlayer.Perspective.THIRD_PERSON, loopType);
      } else {
         this.playAnimation(RawAnimation.begin().then(animationName, loopType), GeckoPlayer.Perspective.THIRD_PERSON);
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.getUser().level().isClientSide) {
         this.heldItemMainHandVisualOverride = null;
         this.heldItemOffHandVisualOverride = null;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.DEFAULT;
      }
   }

   @Override
   public boolean canUse() {
      return super.canUse() && !this.getUser().isSpectator();
   }

   @Override
   protected boolean canContinueUsing() {
      return super.canContinueUsing() && !this.getUser().isSpectator();
   }

   @Override
   public <E extends GeoEntity> PlayState animationPredicate(AnimationState<E> e, GeckoPlayer.Perspective perspective) {
      RawAnimation whichAnimation;
      if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
         whichAnimation = this.activeFirstPersonAnimation;
      } else {
         whichAnimation = this.activeAnimation;
      }

      if (whichAnimation != null && !whichAnimation.getAnimationStages().isEmpty()) {
         e.getController().setAnimation(whichAnimation);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public ItemStack heldItemMainHandOverride() {
      return this.heldItemMainHandVisualOverride;
   }

   public ItemStack heldItemOffHandOverride() {
      return this.heldItemOffHandVisualOverride;
   }

   public PlayerAbility.HandDisplay getFirstPersonMainHandDisplay() {
      return this.firstPersonMainHandDisplay;
   }

   public PlayerAbility.HandDisplay getFirstPersonOffHandDisplay() {
      return this.firstPersonOffHandDisplay;
   }

   public void onRightClickEmpty(RightClickEmpty event) {
   }

   public void onRightClickBlock(RightClickBlock event) {
   }

   public void onRightClickWithItem(RightClickItem event) {
   }

   public void onRightClickEntity(EntityInteract event) {
   }

   public void onLeftClickEmpty(LeftClickEmpty event) {
   }

   public void onLeftClickBlock(LeftClickBlock event) {
   }

   public void onLeftClickEntity(AttackEntityEvent event) {
   }

   @Override
   public void onTakeDamage(Post event) {
   }

   public void onJump(LivingJumpEvent event) {
   }

   public void onFall(LivingFallEvent event) {
   }

   public void onRightMouseDown(Player player) {
   }

   public void onLeftMouseDown(Player player) {
   }

   public void onRightMouseUp(Player player) {
   }

   public void onLeftMouseUp(Player player) {
   }

   public void onSneakDown(Player player) {
   }

   public void onSneakUp(Player player) {
   }

   public enum HandDisplay {
      DEFAULT,
      DONT_RENDER,
      FORCE_RENDER;
   }
}
