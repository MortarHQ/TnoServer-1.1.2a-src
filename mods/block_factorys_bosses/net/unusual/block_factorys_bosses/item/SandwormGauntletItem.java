package net.unusual.block_factorys_bosses.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.projectile.PoisonSpitPrEntity;
import net.unusual.block_factorys_bosses.geckolib.item.SandwormGauntletRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.component.OperationMode;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@EventBusSubscriber
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SandwormGauntletItem extends Item implements GeoItem {
   private static final String MODE_EARTHQUAKE = "earthquake";
   private static final String MODE_POISON_BARRAGE = "poison_barrage";
   private static final ResourceLocation ATTRIBUTE_KEY_SLOW = BossesRise.prefix("sandworm_gauntlet/slow");
   private static final ResourceLocation ATTRIBUTE_KEY_FREEZE = BossesRise.prefix("sandworm_gauntlet/freeze");
   private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

   public SandwormGauntletItem() {
      super(new Properties().durability(1000).rarity(Rarity.UNCOMMON));
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, state -> PlayState.STOP));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animatableInstanceCache;
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 1;
   }

   public int getBarColor(ItemStack stack) {
      return 9697806;
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 72000;
   }

   public int getUseTick(ItemStack stack, LivingEntity entity, int timeLeft) {
      return this.getUseDuration(stack, entity) - timeLeft;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
      Player player = context.getPlayer();
      if (player != null && player.isCrouching() && context.getClickedFace() == Direction.UP) {
         OperationMode.set(stack, "earthquake");
         return InteractionResult.PASS;
      } else {
         return super.onItemUseFirst(stack, context);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (OperationMode.get(stack) == null) {
         OperationMode.set(stack, "poison_barrage");
      }

      switch (OperationMode.get(stack)) {
         case "earthquake":
            this.onEarthquakeStart(stack);
            break;
         case "poison_barrage":
            this.onPoisonBarrageStart(stack);
         case null:
      }

      player.startUsingItem(hand);
      return InteractionResultHolder.consume(stack);
   }

   public void onUseTick(Level level, LivingEntity user, ItemStack stack, int timeLeft) {
      if (level instanceof ServerLevel serverLevel) {
         if (OperationMode.isMode(stack, "earthquake")) {
            this.onUseTickWithEarthquake(stack, serverLevel, user, timeLeft);
         }
      }
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
      if (OperationMode.isMode(stack, "poison_barrage")) {
         this.releaseUsingWithPoisonBarrage(stack, level, user, timeLeft);
      }

      this.endUse(stack);
   }

   public boolean onDroppedByPlayer(ItemStack stack, Player player) {
      this.endUse(stack);
      return super.onDroppedByPlayer(stack, player);
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(itemstack, context, list, flag);
      list.add(Component.translatable("item.block_factorys_bosses.sandworm_gauntlet.description_0"));
      list.add(Component.translatable("item.block_factorys_bosses.sandworm_gauntlet.description_1"));
   }

   private void endUse(ItemStack stack) {
      OperationMode.set(stack, null);
      ItemAttributeModifiers itemModifiers = stack.getAttributeModifiers();
      List<Entry> modifiers = itemModifiers.modifiers().stream().filter(modifier -> {
         AttributeModifier id = modifier.modifier();
         return !id.is(ATTRIBUTE_KEY_FREEZE) && !id.is(ATTRIBUTE_KEY_SLOW);
      }).toList();
      stack.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers, itemModifiers.showInTooltip()));
   }

   private void onEarthquakeStart(ItemStack stack) {
      stack.set(
         DataComponents.ATTRIBUTE_MODIFIERS,
         stack.getAttributeModifiers()
            .withModifierAdded(
               Attributes.MOVEMENT_SPEED, new AttributeModifier(ATTRIBUTE_KEY_FREEZE, Double.NEGATIVE_INFINITY, Operation.ADD_VALUE), EquipmentSlotGroup.ANY
            )
            .withModifierAdded(
               Attributes.JUMP_STRENGTH, new AttributeModifier(ATTRIBUTE_KEY_FREEZE, Double.NEGATIVE_INFINITY, Operation.ADD_VALUE), EquipmentSlotGroup.ANY
            )
      );
   }

   private void onUseTickWithEarthquake(ItemStack stack, ServerLevel level, LivingEntity user, int timeLeft) {
      int useTick = this.getUseTick(stack, user, timeLeft);
      int ticksActive = useTick - 30;
      if (ticksActive >= 0) {
         if (ticksActive % 20 == 0) {
            stack.hurtAndBreak(10, level, user, item -> {});
            double maxDistance = 14.0;
            double maxDistanceSquared = 196.0;
            double minDistance = 3.0;
            double minDistanceSquared = 9.0;
            List<LivingEntity> targetEntities = level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(user.position(), 28.0, 8.0, 28.0), target -> {
               if (target == user) {
                  return false;
               }

               double distanceSquared = target.distanceToSqr(user);
               return distanceSquared < 196.0 && distanceSquared > 9.0;
            });
            int targetCount = 4;
            ArrayList<Vec3> targetPositions = new ArrayList<>(targetCount);
            RandomSource random = RandomSource.create();

            while (!targetEntities.isEmpty() && targetPositions.size() < targetCount) {
               targetPositions.add(targetEntities.remove(random.nextInt(targetEntities.size())).position());
            }

            while (targetPositions.size() < targetCount) {
               float angle = random.nextFloat() * (float) (Math.PI * 2);
               double distance = 3.0 + random.nextFloat() * 11.0;
               targetPositions.add(user.position().add(Mth.sin(angle) * distance, 0.0, Mth.cos(angle) * distance));
            }

            for (Vec3 targetPosition : targetPositions) {
               SandColumnEntity.spawnSandColumn(level, targetPosition, 0, user);
            }
         }
      }
   }

   private float getPoisonChargeForce(int usageTicks) {
      return Math.clamp(0.5F + (usageTicks - 7) / 5.0F, 0.5F, 1.0F);
   }

   private int getPoisonChargeCount(int usageTicks) {
      return Math.clamp((usageTicks - 3 + 7) / 10, 0, 4);
   }

   private void onPoisonBarrageStart(ItemStack stack) {
      stack.set(
         DataComponents.ATTRIBUTE_MODIFIERS,
         stack.getAttributeModifiers()
            .withModifierAdded(Attributes.MOVEMENT_SPEED, new AttributeModifier(ATTRIBUTE_KEY_SLOW, 0.5, Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.ANY)
      );
   }

   private void releaseUsingWithPoisonBarrage(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
      int useTick = this.getUseTick(stack, user, timeLeft);
      int poisonChargeCount = this.getPoisonChargeCount(useTick);
      if (poisonChargeCount >= 1) {
         float poisonChargeForce = this.getPoisonChargeForce(useTick);
         user.playSound((SoundEvent)BossesRiseSounds.SANDWORM_GAUNTLET_POISON_BARRAGE_RELEASE.value());
         if (!level.isClientSide()) {
            Vec3 pos = user.getEyePosition();
            Vec3 direction = user.getViewVector(1.0F);

            for (int count = 0; count < poisonChargeCount; count++) {
               PoisonSpitPrEntity projectile = new PoisonSpitPrEntity(
                  (EntityType<? extends PoisonSpitPrEntity>)BossesRiseEntities.POISON_SPIT_PR.get(), pos.x, pos.y, pos.z, level, stack
               );
               projectile.setOwner(user);
               projectile.setSilent(true);
               projectile.setBaseDamage(6.0);
               projectile.setKnockback(5);
               projectile.shoot(direction.x, direction.y, direction.z, poisonChargeForce * 1.5F, 8.0F + poisonChargeCount * 5.0F);
               level.addFreshEntity(projectile);
            }

            if (user instanceof Player player) {
               player.awardStat(Stats.ITEM_USED.get(this));
            }
         }
      }
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         @Nullable
         private SandwormGauntletRenderer renderer;

         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            if (this.renderer == null) {
               this.renderer = new SandwormGauntletRenderer();
            }

            return this.renderer;
         }
      });
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(new SandwormGauntletItem.SandwormGauntletItemClientExtensions(), new Item[]{(Item)BossesRiseItems.SANDWORM_GAUNTLET.get()});
   }

   public static class SandwormGauntletItemClientExtensions implements IClientItemExtensions {
      @Nullable
      public ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
         return ArmPose.BOW_AND_ARROW;
      }
   }
}
