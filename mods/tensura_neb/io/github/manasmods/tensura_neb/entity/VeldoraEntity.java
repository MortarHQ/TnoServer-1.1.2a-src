package io.github.manasmods.tensura_neb.entity;

import io.github.manasmods.tensura.entity.human.OtherworlderEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VeldoraEntity extends OtherworlderEntity implements GeoEntity, SmartBrainOwner<VeldoraEntity> {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public VeldoraEntity(EntityType<? extends VeldoraEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes();
   }

   @NotNull
   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.isNoAi()) {
         return InteractionResult.PASS;
      }

      if (this.level().isClientSide()) {
         return InteractionResult.CONSUME;
      }

      player.displayClientMessage(Component.literal("You aren't meant to see this").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false);
      return InteractionResult.sidedSuccess(this.level().isClientSide());
   }

   @NotNull
   protected Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   public List<ExtendedSensor<VeldoraEntity>> getSensors() {
      return ObjectArrayList.of();
   }

   public BrainActivityGroup<VeldoraEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[0]);
   }

   public BrainActivityGroup<VeldoraEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(new Behavior[]{new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))});
   }

   protected PlayState loopController(AnimationState<VeldoraEntity> state) {
      String name;
      if (this.isOrderedToSit()) {
         name = "animation.veldora.logo";
      } else {
         name = "animation.veldora.idle";
      }

      return state.setAndContinue(RawAnimation.begin().thenLoop(name));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "loopController", 10, this::loopController));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
