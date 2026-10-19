package net.unusual.block_factorys_bosses.item;

import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.GhostTentacleEntity;
import net.unusual.block_factorys_bosses.geckolib.item.UndyingTentacleRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.stateless.StatelessAnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class UndyingTentacleItem extends Item implements GeoItem {
   public static final RawAnimation WHIP = RawAnimation.begin()
      .thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability1_start")
      .thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability1_end");
   public static final RawAnimation SUMMON_START = RawAnimation.begin()
      .thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability2_start")
      .thenLoop("animation.block_factory.br.undying_tentacle.tpp.ability2_idle");
   public static final RawAnimation SUMMON_END = RawAnimation.begin().thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability2_end");
   private static final double ITEM_RANGE = 40.0;
   private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

   public UndyingTentacleItem() {
      super(new Properties().durability(1000).rarity(Rarity.UNCOMMON).attributes(createAttributes()));
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
   }

   public static ItemAttributeModifiers createAttributes() {
      Builder builder = ItemAttributeModifiers.builder();
      builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
      builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
      builder.add(
         Attributes.ENTITY_INTERACTION_RANGE,
         new AttributeModifier(ResourceLocation.withDefaultNamespace("base_entity_interaction_range"), 1.0, Operation.ADD_VALUE),
         EquipmentSlotGroup.MAINHAND
      );
      builder.add(
         Attributes.BLOCK_INTERACTION_RANGE,
         new AttributeModifier(ResourceLocation.withDefaultNamespace("base_block_interaction_range"), 1.0, Operation.ADD_VALUE),
         EquipmentSlotGroup.MAINHAND
      );
      return builder.build();
   }

   public void registerControllers(ControllerRegistrar controllers) {
      StatelessAnimationController controller = new StatelessAnimationController(this, "main_controller");
      controller.triggerableAnim("whip", WHIP);
      controller.triggerableAnim("summon_start", SUMMON_START);
      controller.triggerableAnim("summon_end", SUMMON_END);
      controllers.add(controller);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animatableInstanceCache;
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 1;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
      Player player = context.getPlayer();
      return player != null && player.isCrouching() && context.getClickedFace() == Direction.UP ? InteractionResult.PASS : super.onItemUseFirst(stack, context);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (player.isSecondaryUseActive()) {
         player.startUsingItem(hand);
         this.triggerAnim(player, GeoItem.getId(stack), "main_controller", "summon_start");
         if (level instanceof ServerLevel serverLevel) {
            PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.ATTACK_AND_HOLD);
            playSound(serverLevel, player.getEyePosition(), BossesRiseSounds.UNDYING_TENTACLE_CHARGING);
         }

         return InteractionResultHolder.consume(stack);
      } else {
         this.triggerAnim(player, GeoItem.getId(stack), "main_controller", "whip");
         if (level instanceof ServerLevel serverLevel) {
            player.getCooldowns().addCooldown(this, 40);
            stack.hurtAndBreak(1, serverLevel, player, item -> {});
            Vec3 eyePos = player.getEyePosition(1.0F);
            playSound(serverLevel, eyePos, BossesRiseSounds.UNDYING_TENTACLE_THROW_FORTH);
            serverLevel.getEntities(player, player.getBoundingBox().inflate(40.0))
               .stream()
               .map(
                  entity -> {
                     if (entity.getPistonPushReaction() == PushReaction.NORMAL && !entity.getType().is(EntityTypes.BOSSES)) {
                        AABB box = entity.getBoundingBox();
                        Vec3 closestPoint = new Vec3(
                           Mth.clamp(eyePos.x, box.minX, box.maxX), Mth.clamp(eyePos.y, box.minY, box.maxY), Mth.clamp(eyePos.z, box.minZ, box.maxZ)
                        );
                        double distance = closestPoint.distanceTo(eyePos);
                        if (distance > 40.0) {
                           return null;
                        }

                        Vec3 scaled = eyePos.add(player.getViewVector(1.0F).scale(distance));
                        return scaled.distanceTo(closestPoint) < 1.5 && player.hasLineOfSight(entity) ? Pair.of(closestPoint, entity) : null;
                     } else {
                        return null;
                     }
                  }
               )
               .filter(Objects::nonNull)
               .min(Comparator.comparingDouble(entity -> ((Vec3)entity.getFirst()).distanceTo(eyePos)))
               .ifPresentOrElse(
                  pair -> {
                     playSound(serverLevel, (Vec3)pair.getFirst(), BossesRiseSounds.UNDYING_TENTACLE_THROW_GRAB);
                     Vec3 diff = eyePos.subtract((Vec3)pair.getFirst()).scale(0.25);
                     ((Entity)pair.getSecond()).setDeltaMovement(diff);
                  },
                  () -> {
                     HitResult hitresult = serverLevel.clip(
                        new ClipContext(eyePos, eyePos.add(player.getViewVector(1.0F).scale(40.0)), Block.COLLIDER, Fluid.NONE, player)
                     );
                     if (hitresult instanceof BlockHitResult blockHitResult && blockHitResult.getType() != Type.MISS) {
                        Vec3 diff = blockHitResult.getLocation().subtract(player.position()).scale(0.25);
                        playSound(serverLevel, blockHitResult.getLocation(), BossesRiseSounds.UNDYING_TENTACLE_THROW_BACK);
                        SpatialUtil.pushEntity(player, diff);
                     }
                  }
               );
         }

         return new InteractionResultHolder(InteractionResult.CONSUME_PARTIAL, stack);
      }
   }

   protected static void playSound(ServerLevel level, Vec3 vec3, Holder<SoundEvent> sound) {
      level.playSound(null, vec3.x, vec3.y, vec3.z, (SoundEvent)sound.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 40;
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
      this.triggerAnim(user, GeoItem.getId(stack), "main_controller", "summon_end");
      if (user instanceof ServerPlayer player) {
         boolean spawnedAny = false;
         spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.NORTH);
         spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.EAST);
         spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.SOUTH);
         spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.WEST);
         if (!spawnedAny) {
            player.getCooldowns().addCooldown(this, 10);
            return stack;
         }

         player.swing(player.getUsedItemHand());
         player.getCooldowns().addCooldown(this, 40);
         PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.LAND, 12);
         playSound(player.serverLevel(), player.getEyePosition(), BossesRiseSounds.UNDYING_TENTACLE_SMASH_GROUND);
         player.serverLevel().getEntities(player, player.getBoundingBox().inflate(3.0)).forEach(entity -> {
            if (entity.distanceTo(player) < 3.0F) {
               entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 3.0, 0.0));
            }
         });
      }

      return stack;
   }

   private boolean spawnGhost(ServerLevel level, ServerPlayer player, Direction direction) {
      Vec3 spawnPosition = player.position().relative(direction, 3.0);
      if (!level.noBlockCollision(
         null, ((EntityType)BossesRiseEntities.GHOST_TENTACLE.get()).getSpawnAABB(spawnPosition.x(), spawnPosition.y(), spawnPosition.z())
      )) {
         return false;
      }

      GhostTentacleEntity undyingTentacle = new GhostTentacleEntity((EntityType<GhostTentacleEntity>)BossesRiseEntities.GHOST_TENTACLE.get(), level);
      undyingTentacle.setOwnerUUID(player.getUUID());
      undyingTentacle.moveTo(spawnPosition, player.getYRot(), player.getXRot());
      level.addFreshEntity(undyingTentacle);
      undyingTentacle.triggerAnim("main_controller", "spawn");
      undyingTentacle.skipDropExperience();
      return true;
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
      this.triggerAnim(user, GeoItem.getId(stack), "main_controller", "summon_end");
      if (user instanceof ServerPlayer player) {
         player.getCooldowns().addCooldown(this, 40);
         PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.SPIKE_THROW, 9);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(itemstack, context, list, flag);
      list.add(Component.translatable("item.block_factorys_bosses.undying_tentacle.description_0"));
      list.add(Component.translatable("item.block_factorys_bosses.undying_tentacle.description_1"));
      list.add(Component.translatable("item.block_factorys_bosses.undying_tentacle.description_2"));
      list.add(Component.translatable("item.block_factorys_bosses.undying_tentacle.description_3"));
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         @Nullable
         private UndyingTentacleRenderer renderer;

         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            if (this.renderer == null) {
               this.renderer = new UndyingTentacleRenderer();
            }

            return this.renderer;
         }
      });
   }
}
