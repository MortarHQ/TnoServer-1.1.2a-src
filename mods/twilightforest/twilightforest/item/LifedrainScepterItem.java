package twilightforest.item;

import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.enchantment.RechargeScepterEffect;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEnchantments;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.loot.TFLootTables;
import twilightforest.network.LifedrainParticlePacket;
import twilightforest.network.ParticlePacket;
import twilightforest.util.TFItemStackUtils;
import twilightforest.util.entities.EntityUtil;

public class LifedrainScepterItem extends Item {
   public LifedrainScepterItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.getDamageValue() == stack.getMaxDamage() && !player.getAbilities().instabuild) {
         return InteractionResultHolder.fail(player.getItemInHand(hand));
      }

      player.startUsingItem(hand);
      return InteractionResultHolder.success(player.getItemInHand(hand));
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (entity.tickCount % 20 == 0 && level instanceof ServerLevel serverLevel && stack.has(DataComponents.ENCHANTMENTS) && !isSelected) {
         int renewal = ((ItemEnchantments)stack.get(DataComponents.ENCHANTMENTS)).getLevel(level.holderOrThrow(TFEnchantments.RENEWAL));
         if (renewal > 0) {
            RechargeScepterEffect.applyRecharge(serverLevel, stack, entity);
         }
      }
   }

   public static void animateTargetShatter(ServerLevel level, LivingEntity target) {
      ParticleOptions options = new ItemParticleOption(ParticleTypes.ITEM, Items.ROTTEN_FLESH.getDefaultInstance());
      boolean big = level.getRandom().nextInt(100) == 0;
      double explosionPower = big ? 1.0 : 0.3;
      ParticlePacket particlePacket = new ParticlePacket();
      double gaussFactor = 5.0;

      for (int i = 0; i < 50 + (int)target.dimensions.width() * (big ? 75 : 25); i++) {
         double gaussX = level.getRandom().nextGaussian() * 0.01;
         double gaussY = level.getRandom().nextGaussian() * 0.01;
         double gaussZ = level.getRandom().nextGaussian() * 0.01;
         double speed = level.getRandom().nextFloat() * explosionPower;
         double x = level.getRandom().nextFloat() * target.getBbWidth() * 1.5F - target.getBbWidth()
            - gaussX * gaussFactor
            + level.random.nextGaussian() * gaussX;
         double y = level.getRandom().nextFloat() * target.getBbHeight() - gaussY * gaussFactor + level.random.nextGaussian() * gaussY;
         double z = level.getRandom().nextFloat() * target.getBbWidth() * 1.5F - target.getBbWidth()
            - gaussZ * gaussFactor
            + level.random.nextGaussian() * gaussZ;
         particlePacket.queueParticle(options, false, target.getX() + x, target.getY() + y, target.getZ() + z, x * speed, y * speed, z * speed);
      }

      PacketDistributor.sendToPlayersTrackingEntity(target, particlePacket, new CustomPacketPayload[0]);
   }

   @Nullable
   private Entity getPlayerLookTarget(Level level, LivingEntity living) {
      Entity pointedEntity = null;
      double range = 20.0;
      Vec3 srcVec = living.getEyePosition();
      Vec3 lookVec = living.getViewVector(1.0F);
      Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
      float var9 = 1.0F;
      List<Entity> possibleList = level.getEntities(
         living, living.getBoundingBox().expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range).inflate(var9, var9, var9)
      );
      double hitDist = 0.0;

      for (Entity possibleEntity : possibleList) {
         if (possibleEntity.isPickable()) {
            float borderSize = possibleEntity.getPickRadius();
            AABB collisionBB = possibleEntity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
            if (collisionBB.contains(srcVec)) {
               if (0.0 < hitDist || hitDist == 0.0) {
                  pointedEntity = possibleEntity;
                  hitDist = 0.0;
               }
            } else if (interceptPos.isPresent()) {
               double possibleDist = srcVec.distanceTo(interceptPos.get());
               if (possibleDist < hitDist || hitDist == 0.0) {
                  pointedEntity = possibleEntity;
                  hitDist = possibleDist;
               }
            }
         }
      }

      return pointedEntity;
   }

   public void onUseTick(Level level, LivingEntity living, ItemStack stack, int count) {
      if (stack.getDamageValue() == this.getMaxDamage(stack)) {
         living.stopUsingItem();
      } else {
         if (count % 5 == 0 && this.getPlayerLookTarget(level, living) instanceof LivingEntity target && !(target instanceof ArmorStand)) {
            if (!level.isClientSide() && !target.isDeadOrDying()) {
               PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                  living, new LifedrainParticlePacket(living.getId(), target.getEyePosition()), new CustomPacketPayload[0]
               );
               level.playSound(null, living.blockPosition(), (SoundEvent)TFSounds.LIFE_SCEPTER_DRAIN.get(), SoundSource.PLAYERS);
            }

            DamageSource damageSource = TFDamageTypes.getEntityDamageSource(level, TFDamageTypes.LIFEDRAIN, living);
            if (target.hurt(damageSource, 1.0F) && !level.isClientSide()) {
               if (target.getHealth() <= 1.0F && !target.getType().is(EntityTypes.BOSSES)) {
                  if (!target.getType().is(EntityTagGenerator.LIFEDRAIN_DROPS_NO_FLESH)
                     && level instanceof ServerLevel serverLevel
                     && living instanceof Player player) {
                     LootParams ctx = new Builder(serverLevel)
                        .withParameter(LootContextParams.THIS_ENTITY, target)
                        .withParameter(LootContextParams.ORIGIN, target.getEyePosition())
                        .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                        .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                        .withParameter(LootContextParams.ATTACKING_ENTITY, player)
                        .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, player)
                        .create(LootContextParamSets.ENTITY);
                     serverLevel.getServer()
                        .reloadableRegistries()
                        .getLootTable(TFLootTables.LIFEDRAIN_SCEPTER_KILL_BONUS)
                        .getRandomItems(ctx)
                        .forEach(target::spawnAtLocation);
                     animateTargetShatter(serverLevel, target);
                  }

                  if (target instanceof Mob mob) {
                     mob.spawnAnim();
                  }

                  SoundEvent deathSound = EntityUtil.getDeathSound(target);
                  if (deathSound != null) {
                     level.playSound(null, target.blockPosition(), deathSound, SoundSource.HOSTILE, 1.0F, target.getVoicePitch());
                  }

                  if (!target.isDeadOrDying()) {
                     if (target instanceof Player) {
                        target.hurt(TFDamageTypes.getEntityDamageSource(level, TFDamageTypes.LIFEDRAIN, living), Float.MAX_VALUE);
                     } else {
                        target.die(TFDamageTypes.getEntityDamageSource(level, TFDamageTypes.LIFEDRAIN, living));
                        target.discard();
                     }
                  }
               } else {
                  target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 2));
                  if (count % 10 == 0) {
                     living.heal(1.0F);
                     if (living instanceof Player player) {
                        player.getFoodData().eat(1, 0.1F);
                     }
                  }
               }

               if (living instanceof Player player
                  && !player.getAbilities().instabuild
                  && (!player.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN) || level.getRandom().nextFloat() > 0.05F)) {
                  TFItemStackUtils.hurtButDontBreak(stack, 1, (ServerLevel)level, player);
               }
            }

            if (!level.isClientSide() && target.getHealth() <= living.getHealth()) {
               target.setDeltaMovement(0.0, 0.15, 0.0);
            }
         }
      }
   }

   public static void makeRedMagicTrail(Level level, LivingEntity source, Vec3 target) {
      Vec3 handPos = getPlayerHandPos(source, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));
      double distance = handPos.distanceTo(target);

      for (double i = 0.0; i <= distance * 3.0; i++) {
         Vec3 particlePos = handPos.subtract(target).scale(i / (distance * 3.0));
         particlePos = handPos.subtract(particlePos);
         float r = 1.0F;
         float g = 0.5F;
         float b = 0.5F;
         level.addParticle(
            ColorParticleOption.create((ParticleType)TFParticleType.MAGIC_EFFECT.get(), r, g, b),
            particlePos.x(),
            particlePos.y(),
            particlePos.z(),
            0.0,
            0.0,
            0.0
         );
      }
   }

   private static Vec3 getPlayerHandPos(LivingEntity living, float partialTicks) {
      float armSwing = Mth.sin(Mth.sqrt(living.getAttackAnim(partialTicks)) * (float) Math.PI);
      int hand = living.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
      if (!(living.getMainHandItem().getItem() instanceof LifedrainScepterItem)) {
         hand = -hand;
      }

      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.options.getCameraType().isFirstPerson() && living == minecraft.player) {
         Vec3 vec3 = minecraft.getEntityRenderDispatcher()
            .camera
            .getNearPlane()
            .getPointOnPlane(hand * 0.525F, -0.1F)
            .scale(960.0 / ((Integer)minecraft.options.fov().get()).intValue())
            .yRot(armSwing * 0.5F)
            .xRot(-armSwing * 0.7F);
         return living.getEyePosition(partialTicks).add(vec3);
      } else {
         float yRot = Mth.lerp(partialTicks, living.yBodyRotO, living.yBodyRot) * (float) (Math.PI / 180.0);
         double sin = Mth.sin(yRot);
         double cos = Mth.cos(yRot);
         float scale = living.getScale();
         double offset = hand * 0.35 * scale;
         double factor = 0.8 * scale;
         float crouch = living.isCrouching() ? -0.1875F : 0.0F;
         return living.getEyePosition(partialTicks).add(-cos * offset - sin * factor, crouch - 0.45 * scale, -sin * offset + cos * factor);
      }
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return oldStack.getItem() == newStack.getItem();
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged || newStack.getItem() != oldStack.getItem();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      super.appendHoverText(stack, context, tooltip, flags);
      tooltip.add(
         Component.translatable("item.twilightforest.scepter.desc", new Object[]{stack.getMaxDamage() - stack.getDamageValue()}).withStyle(ChatFormatting.GRAY)
      );
   }
}
