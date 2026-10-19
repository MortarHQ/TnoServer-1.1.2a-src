package net.unusual.block_factorys_bosses.item;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.Tags.Items;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.client.renderer.FrozenFistItemRenderer;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IceGauntletItem extends ShieldItem {
   public static final Map<String, AnimationState> IDLE_STATES = new WeakHashMap<>();
   public static final Map<String, AnimationState> SWING_STATES = new WeakHashMap<>();
   public static final Map<ItemStack, Integer> IS_MINING = new WeakHashMap<>();
   public static final float ICE_ANGLE = 80.0F;
   public static final int MAX_PLAYER_ICICLES = 32;

   public IceGauntletItem() {
      super(new Properties().durability(1200).rarity(Rarity.UNCOMMON).attributes(createAttributes()));
   }

   public int getBarColor(ItemStack stack) {
      return 52479;
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
      return repairItem.is(Items.GEMS_DIAMOND);
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
      boolean hurt = super.hurtEnemy(stack, entity, attacker);
      entity.setTicksFrozen(400);
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 0.1, 0.1, 0.1, 0.0);
         level.sendParticles(
            (SimpleParticleType)BossesRiseParticleTypes.SNOW_CLOUD.get(), entity.getX(), entity.getY() + 1.5, entity.getZ(), 2, 0.6, 0.6, 0.6, 0.0
         );
      }

      return hurt;
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(stack, context, list, flag);
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_0"));
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_1"));
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_2"));
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_3"));
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_4"));
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_5"));
      list.add(Component.translatable("item.block_factorys_bosses.ice_gauntlet.description_6"));
   }

   public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      super.inventoryTick(stack, world, entity, slot, selected);
      if (((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getString("itemKey").isEmpty()) {
         CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("itemKey", UUID.randomUUID().toString()));
      }

      int is_mining = IS_MINING.computeIfAbsent(stack, s -> 0);
      if (is_mining > 0) {
         IS_MINING.put(stack, is_mining - 1);
      }

      double swing_animtime = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
      if (swing_animtime > 0.0) {
         CustomData.update(
            DataComponents.CUSTOM_DATA,
            stack,
            tag -> tag.putDouble(
               "swing_animtime", ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("swing_animtime") - 1.0
            )
         );
      }
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      if (player != null) {
         Vec3 clicked = context.getClickLocation();
         AtomicBoolean flag = new AtomicBoolean(false);
         player.level().getEntitiesOfClass(IceSpikeClusterEntity.class, AABB.ofSize(clicked, 3.0, 3.0, 3.0)).forEach(spike -> {
            if (player.level().isClientSide) {
               flag.set(true);
            } else if (!flag.get()) {
               InteractionResult result = spike.interact(player, context.getHand());
               if (result == InteractionResult.CONSUME) {
                  flag.set(true);
               }
            }
         });
         if (flag.get()) {
            return InteractionResult.CONSUME;
         }
      }

      return super.useOn(context);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.ATTACK_AND_HOLD);
      return super.use(level, player, hand);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      IS_MINING.put(stack, 5);
      return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("swing_animtime") == 0.0 ? 1.0F : 0.0F;
   }

   public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
      boolean hit = super.onEntitySwing(stack, entity, hand);
      int is_mining = IS_MINING.computeIfAbsent(stack, s -> 0);
      if (is_mining == 0) {
         double swing_animtime = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
         if (swing_animtime == 0.0 && entity instanceof Player plr) {
            this.playSound(plr, (SoundEvent)BossesRiseSounds.ICE_GAUNTLET_PUNCH.value());
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("itemKey", UUID.randomUUID().toString()));
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("swing_animtime", 18.0));
         }
      }

      return hit;
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof ServerPlayer player
         && level instanceof ServerLevel serverLevel
         && player.isSecondaryUseActive()
         && !player.getCooldowns().isOnCooldown(this)) {
         int i = this.getUseDuration(stack, entityLiving) - timeLeft;
         if (i < 0) {
            return;
         }

         PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.ATTACK_END, 11);
         double interaction = player.blockInteractionRange() * 1.1;
         Vec3 eyePosition = player.getEyePosition(1.0F);
         Vec3 viewVector = player.getViewVector(1.0F);
         Vec3 vec32 = eyePosition.add(viewVector.x * interaction, viewVector.y * interaction, viewVector.z * interaction);
         BlockHitResult blockHitResult = level.clip(new ClipContext(eyePosition, vec32, Block.COLLIDER, Fluid.NONE, player));
         if (blockHitResult.getType() == Type.MISS) {
            return;
         }

         if (player.getXRot() > 85.0F) {
            viewVector = player.calculateViewVector(85.0F, player.getViewYRot(1.0F));
         } else if (player.getXRot() < -85.0F) {
            viewVector = player.calculateViewVector(-85.0F, player.getViewYRot(1.0F));
         }

         GauntletAttachment.iceWave(
            player, serverLevel, Math.min(i / 32.0F + 0.5F, 1.5F) * 12.0F, Math.min(i, 32), blockHitResult.getLocation(), viewVector, 10.0F, 1.0F
         );
         player.getCooldowns().addCooldown(stack.getItem(), 80);
         stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
      }
   }

   @Nullable
   public static Vec3 placeProper(ServerLevel level, LivingEntity living, Vec3 wantedPos, Vec3 eyePosition) {
      BlockHitResult blockHitResult = level.clip(
         new ClipContext(
            new Vec3(wantedPos.x, eyePosition.y, wantedPos.z), new Vec3(wantedPos.x, wantedPos.y - 3.0, wantedPos.z), Block.COLLIDER, Fluid.NONE, living
         )
      );
      return blockHitResult.getType() == Type.MISS ? null : blockHitResult.getLocation();
   }

   public static ItemAttributeModifiers createAttributes() {
      Builder builder = ItemAttributeModifiers.builder();
      builder.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(BossesRise.prefix("effect.kb"), 2.5, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
      builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 4.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
      builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
      return builder.build();
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(
         new IClientItemExtensions() {
            private FrozenFistItemRenderer rendererInstance;

            @OnlyIn(Dist.CLIENT)
            public boolean applyForgeHandTransform(
               PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess
            ) {
               int is_mining = IceGauntletItem.IS_MINING.computeIfAbsent(player.getMainHandItem(), s -> 0);
               double swing_animtime = ((CustomData)player.getMainHandItem().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY))
                  .copyTag()
                  .getDouble("swing_animtime");
               if (is_mining == 0 && swing_animtime > 0.0) {
                  int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                  poseStack.translate(i * 0.56F, -0.52F, -0.72F);
                  if (player.getUseItem() == itemInHand) {
                     poseStack.translate(0.05, 0.05, 0.05);
                  }

                  return true;
               } else {
                  return false;
               }
            }

            @OnlyIn(Dist.CLIENT)
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               if (this.rendererInstance == null) {
                  this.rendererInstance = new FrozenFistItemRenderer(
                     Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()
                  );
               }

               return this.rendererInstance;
            }
         },
         new Item[]{(Item)BossesRiseItems.ICE_GAUNTLET.get()}
      );
   }

   protected void playSound(Player player, SoundEvent soundEvent) {
      player.playSound(soundEvent, 1.0F, 1.0F);
   }
}
