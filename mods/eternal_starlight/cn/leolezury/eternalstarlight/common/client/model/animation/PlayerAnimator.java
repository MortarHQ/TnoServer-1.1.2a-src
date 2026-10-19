package cn.leolezury.eternalstarlight.common.client.model.animation;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class PlayerAnimator {
   public static final HashMap<PlayerAnimator.AnimationTrigger, PlayerAnimator.AnimationStateFunction> ANIMATIONS = new HashMap<>();
   public static boolean renderingFirstPersonPlayer = false;

   public static void register(PlayerAnimator.AnimationTrigger trigger, PlayerAnimator.AnimationStateFunction function) {
      ANIMATIONS.put(trigger, function);
   }

   public interface AnimationStateFunction {
      PlayerAnimator.PlayerAnimationState get(AbstractClientPlayer var1);
   }

   public interface AnimationTransformer {
      boolean shouldApply(PlayerAnimator.PlayerAnimationState var1, AbstractClientPlayer var2, PlayerModel<?> var3);

      void preAnimate(PlayerAnimator.PlayerAnimationState var1, AbstractClientPlayer var2, PlayerModel<?> var3);

      void postAnimate(PlayerAnimator.PlayerAnimationState var1, AbstractClientPlayer var2, PlayerModel<?> var3);

      Optional<ModelPart> modifyModelPart(PlayerAnimator.PlayerAnimationState var1, AbstractClientPlayer var2, PlayerModel<?> var3, String var4);

      float modifyTicks(PlayerAnimator.PlayerAnimationState var1, AbstractClientPlayer var2, PlayerModel<?> var3, float var4);

      float modifyScale(PlayerAnimator.PlayerAnimationState var1, AbstractClientPlayer var2, PlayerModel<?> var3, float var4);
   }

   public interface AnimationTrigger {
      boolean shouldPlay(AbstractClientPlayer var1);

      float animateTicks(AbstractClientPlayer var1, float var2);
   }

   public record CastSpellAnimationTrigger(Supplier<? extends AbstractSpell> spellSupplier) implements PlayerAnimator.AnimationTrigger {
      @Override
      public boolean shouldPlay(AbstractClientPlayer player) {
         return player instanceof SpellCaster
            && ESDataAttachments.SPELL_CAST_DATA.getData(player).hasSpell()
            && ESDataAttachments.SPELL_CAST_DATA.getData(player).spell() == this.spellSupplier.get();
      }

      @Override
      public float animateTicks(AbstractClientPlayer player, float ageInTicks) {
         return !(player instanceof SpellCaster)
            ? 0.0F
            : Math.min(
               ESDataAttachments.SPELL_CAST_DATA.getData(player).spell().spellProperties().totalTicks(),
               ESDataAttachments.SPELL_CAST_DATA.getData(player).castTicks()
                  + Minecraft.getInstance()
                     .getTimer()
                     .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally())
            );
      }
   }

   public static class CastSpellHandAnimationTransformer extends PlayerAnimator.UseItemHandAnimationTransformer {
      @Override
      public boolean shouldApply(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
         return player instanceof SpellCaster && ESDataAttachments.SPELL_CAST_DATA.getData(player).offhand();
      }
   }

   public static class CopyOuterLayerAnimationTransformer implements PlayerAnimator.AnimationTransformer {
      @Override
      public boolean shouldApply(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
         return true;
      }

      @Override
      public void preAnimate(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
      }

      @Override
      public void postAnimate(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
         model.hat.copyFrom(model.head);
         model.jacket.copyFrom(model.body);
         model.leftSleeve.copyFrom(model.leftArm);
         model.rightSleeve.copyFrom(model.rightArm);
         model.leftPants.copyFrom(model.leftLeg);
         model.rightPants.copyFrom(model.rightLeg);
      }

      @Override
      public Optional<ModelPart> modifyModelPart(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, String original) {
         return Optional.empty();
      }

      @Override
      public float modifyTicks(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original) {
         return original;
      }

      @Override
      public float modifyScale(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original) {
         return original;
      }
   }

   public record PlayerAnimationState(
      AnimationDefinition definition,
      AnimationDefinition firstPersonDefinition,
      List<PlayerAnimator.AnimationTransformer> transformers,
      boolean renderLeftArm,
      boolean renderRightArm,
      boolean resetLeftArmBeforeAnimation,
      boolean resetRightArmBeforeAnimation
   ) {
      public AnimationDefinition chooseDefinition() {
         return PlayerAnimator.renderingFirstPersonPlayer ? this.firstPersonDefinition() : this.definition();
      }
   }

   public record UseItemAnimationTrigger(Supplier<? extends Item> itemSupplier) implements PlayerAnimator.AnimationTrigger {
      @Override
      public boolean shouldPlay(AbstractClientPlayer player) {
         return player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).is(this.itemSupplier.get());
      }

      @Override
      public float animateTicks(AbstractClientPlayer player, float ageInTicks) {
         return Math.min(
            player.getUseItem().getUseDuration(player),
            player.getTicksUsingItem()
               + Minecraft.getInstance()
                  .getTimer()
                  .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally())
         );
      }
   }

   public static class UseItemHandAnimationTransformer implements PlayerAnimator.AnimationTransformer {
      private Vec3 hatOriginalPos;
      private Vec3 hatOriginalRot;
      private Vec3 headOriginalPos;
      private Vec3 headOriginalRot;
      private Vec3 bodyOriginalPos;
      private Vec3 bodyOriginalRot;
      private Vec3 jacketOriginalPos;
      private Vec3 jacketOriginalRot;
      private Vec3 rightArmOriginalPos;
      private Vec3 rightArmOriginalRot;
      private Vec3 rightSleeveOriginalPos;
      private Vec3 rightSleeveOriginalRot;
      private Vec3 leftArmOriginalPos;
      private Vec3 leftArmOriginalRot;
      private Vec3 leftSleeveOriginalPos;
      private Vec3 leftSleeveOriginalRot;
      private Vec3 rightLegOriginalPos;
      private Vec3 rightLegOriginalRot;
      private Vec3 rightPantsOriginalPos;
      private Vec3 rightPantsOriginalRot;
      private Vec3 leftLegOriginalPos;
      private Vec3 leftLegOriginalRot;
      private Vec3 leftPantsOriginalPos;
      private Vec3 leftPantsOriginalRot;

      @Override
      public boolean shouldApply(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
         return player.getUsedItemHand() == InteractionHand.OFF_HAND;
      }

      @Override
      public void preAnimate(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
         this.hatOriginalPos = this.makeModelPartPos(model.hat);
         this.hatOriginalRot = this.makeModelPartRot(model.hat);
         this.headOriginalPos = this.makeModelPartPos(model.head);
         this.headOriginalRot = this.makeModelPartRot(model.head);
         this.bodyOriginalPos = this.makeModelPartPos(model.body);
         this.bodyOriginalRot = this.makeModelPartRot(model.body);
         this.jacketOriginalPos = this.makeModelPartPos(model.jacket);
         this.jacketOriginalRot = this.makeModelPartRot(model.jacket);
         this.rightArmOriginalPos = this.makeModelPartPos(model.rightArm);
         this.rightArmOriginalRot = this.makeModelPartRot(model.rightArm);
         this.rightSleeveOriginalPos = this.makeModelPartPos(model.rightSleeve);
         this.rightSleeveOriginalRot = this.makeModelPartRot(model.rightSleeve);
         this.leftArmOriginalPos = this.makeModelPartPos(model.leftArm);
         this.leftArmOriginalRot = this.makeModelPartRot(model.leftArm);
         this.leftSleeveOriginalPos = this.makeModelPartPos(model.leftSleeve);
         this.leftSleeveOriginalRot = this.makeModelPartRot(model.leftSleeve);
         this.rightLegOriginalPos = this.makeModelPartPos(model.rightLeg);
         this.rightLegOriginalRot = this.makeModelPartRot(model.rightLeg);
         this.rightPantsOriginalPos = this.makeModelPartPos(model.rightPants);
         this.rightPantsOriginalRot = this.makeModelPartRot(model.rightPants);
         this.leftLegOriginalPos = this.makeModelPartPos(model.leftLeg);
         this.leftLegOriginalRot = this.makeModelPartRot(model.leftLeg);
         this.leftPantsOriginalPos = this.makeModelPartPos(model.leftPants);
         this.leftPantsOriginalRot = this.makeModelPartRot(model.leftPants);
      }

      @Override
      public void postAnimate(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model) {
         this.transformModelPart(model.hat, this.hatOriginalPos, this.hatOriginalRot);
         this.transformModelPart(model.head, this.headOriginalPos, this.headOriginalRot);
         this.transformModelPart(model.body, this.bodyOriginalPos, this.bodyOriginalRot);
         this.transformModelPart(model.jacket, this.jacketOriginalPos, this.jacketOriginalRot);
         this.transformModelPartLeftAndRight(
            model.leftArm, model.rightArm, this.leftArmOriginalPos, this.leftArmOriginalRot, this.rightArmOriginalPos, this.rightArmOriginalRot
         );
         this.transformModelPartLeftAndRight(
            model.leftSleeve,
            model.rightSleeve,
            this.leftSleeveOriginalPos,
            this.leftSleeveOriginalRot,
            this.rightSleeveOriginalPos,
            this.rightSleeveOriginalRot
         );
         this.transformModelPartLeftAndRight(
            model.leftLeg, model.rightLeg, this.leftLegOriginalPos, this.leftLegOriginalRot, this.rightLegOriginalPos, this.rightLegOriginalRot
         );
         this.transformModelPartLeftAndRight(
            model.leftPants, model.rightPants, this.leftPantsOriginalPos, this.leftPantsOriginalRot, this.rightPantsOriginalPos, this.rightPantsOriginalRot
         );
      }

      @Override
      public Optional<ModelPart> modifyModelPart(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, String original) {
         switch (original) {
            case "hat":
               return Optional.of(model.hat);
            case "head":
               return Optional.of(model.head);
            case "body":
               return Optional.of(model.body);
            case "jacket":
               return Optional.of(model.jacket);
            case "right_arm":
               return Optional.of(model.leftArm);
            case "right_sleeve":
               return Optional.of(model.leftSleeve);
            case "left_arm":
               return Optional.of(model.rightArm);
            case "left_sleeve":
               return Optional.of(model.rightSleeve);
            case "right_leg":
               return Optional.of(model.leftLeg);
            case "right_pants":
               return Optional.of(model.leftPants);
            case "left_leg":
               return Optional.of(model.rightLeg);
            case "left_pants":
               return Optional.of(model.rightPants);
            default:
               return Optional.empty();
         }
      }

      @Override
      public float modifyTicks(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original) {
         return original;
      }

      @Override
      public float modifyScale(PlayerAnimator.PlayerAnimationState state, AbstractClientPlayer player, PlayerModel<?> model, float original) {
         return original;
      }

      private void transformModelPart(ModelPart part, Vec3 originalPos, Vec3 originalRot) {
         part.x = (float)(part.x - 2.0 * (part.x - originalPos.x));
         part.yRot = (float)(part.yRot - 2.0 * (part.yRot - originalRot.y));
         part.zRot = (float)(part.zRot - 2.0 * (part.zRot - originalRot.z));
      }

      private void transformModelPartLeftAndRight(
         ModelPart left, ModelPart right, Vec3 leftOriginalPos, Vec3 leftOriginalRot, Vec3 rightOriginalPos, Vec3 rightOriginalRot
      ) {
         this.transformModelPart(left, leftOriginalPos, leftOriginalRot);
         this.transformModelPart(right, rightOriginalPos, rightOriginalRot);
      }

      private Vec3 makeModelPartPos(ModelPart part) {
         return new Vec3(part.x, part.y, part.z);
      }

      private Vec3 makeModelPartRot(ModelPart part) {
         return new Vec3(part.xRot, part.yRot, part.zRot);
      }
   }
}
