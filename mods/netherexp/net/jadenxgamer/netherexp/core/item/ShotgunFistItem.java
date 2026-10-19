package net.jadenxgamer.netherexp.core.item;

import java.awt.Color;
import java.util.List;
import java.util.function.Predicate;
import net.jadenxgamer.elysium_api.api.client.screen_flash.ScreenFlash;
import net.jadenxgamer.elysium_api.api.util.ClientItemData;
import net.jadenxgamer.netherexp.client.rendering.keyframe.ItemAnimationState;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNEEnchantments;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class ShotgunFistItem extends ProjectileWeaponItem {
   public static final ItemAnimationState fire = new ItemAnimationState();

   public ShotgunFistItem(Properties properties) {
      super(properties);
   }

   public int getDefaultProjectileRange() {
      return 15;
   }

   public boolean isEnchantable(ItemStack stack) {
      return true;
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (level.isClientSide()) {
         if (entity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(this)) {
               ClientItemData.getOrCreate(stack).put("isSmoking", true);
            } else {
               ClientItemData.getOrCreate(stack).remove("isSmoking");
            }
         }

         super.inventoryTick(stack, level, entity, slotId, isSelected);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack shotgun = player.getItemInHand(hand);
      ItemStack projectileStack = player.getProjectile(shotgun);
      if (projectileStack.isEmpty() && !player.getAbilities().instabuild) {
         return InteractionResultHolder.pass(shotgun);
      }

      List<ItemStack> draw = draw(shotgun, projectileStack, player);
      float baseVelocity = 1.5F;
      int baseInaccuracy = 20;
      this.shoot(level, player, hand, shotgun, draw, baseVelocity, baseInaccuracy, false, null);
      if (!player.getAbilities().instabuild) {
         shotgun.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
      }

      level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      return InteractionResultHolder.pass(shotgun);
   }

   public ItemEnchantments getAllEnchantments(ItemStack stack, RegistryLookup<Enchantment> lookup) {
      return super.getAllEnchantments(stack, lookup);
   }

   protected void shoot(
      Level level,
      LivingEntity shooter,
      InteractionHand hand,
      ItemStack shotgun,
      List<ItemStack> projectileItems,
      float velocity,
      float inaccuracy,
      boolean isCrit,
      @Nullable LivingEntity target
   ) {
      int count = this.calculateCount(shotgun);
      int cooldown = this.calculateCooldown(shotgun);
      double selfRecoil = this.calculateSelfRecoil(shooter, shotgun);
      boolean isBlank = false;
      if (level.isClientSide()) {
         VFXHelper.shotgunScreenShake(shooter.position(), 8.0F, Easing.LINEAR);
         fire.start(shooter.tickCount, shooter);
         ClientItemData.getOrCreate(shotgun).put("shootFlash", true);
      } else if (!projectileItems.isEmpty()) {
         Item shellItem = projectileItems.getFirst().getItem();
         isBlank = projectileItems.getFirst().is(JNEItems.BLANK_SHOTGUN_SHELL.get());

         for (int i = 0; i < count; i++) {
            Vec3 look = shooter.getLookAngle();
            if (!(shellItem instanceof ShotgunShellItem shotgunShellItem)) {
               break;
            }

            Projectile pellet = shotgunShellItem.createPellet(level, shooter, shooter.getX(), shooter.getY() + 1.0, shooter.getZ());
            pellet.shoot(look.x, look.y, look.z, velocity, inaccuracy);
            level.addFreshEntity(pellet);
         }
      }

      int counterforce = HolderHelper.getEnchantmentLevel(JNEEnchantments.COUNTERFORCE, shotgun);
      Vec3 pushBack = new Vec3(-shooter.getLookAngle().x, -shooter.getLookAngle().y, -shooter.getLookAngle().z).normalize();
      if (counterforce > 0 && !shooter.onGround() && (shooter.fallDistance <= 0.0 || shooter.isInFluidType())) {
         pushBack = new Vec3(shooter.getLookAngle().x, shooter.getLookAngle().y, shooter.getLookAngle().z).normalize();
         if (!isBlank) {
            cooldown += 30;
         }

         if ((Integer)JNEConfigs.COUNTERFORCE_IFRAMES.get() > 0) {
            shooter.invulnerableTime = (Integer)JNEConfigs.COUNTERFORCE_IFRAMES.get();
            level.playSound(null, shooter.blockPosition(), JNESoundEvents.SHOTGUN_COUNTERFORCE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            ScreenFlash.triggerScreenFlash(5, (Integer)JNEConfigs.COUNTERFORCE_IFRAMES.get() / 4, 5, 855703551, false, true);
         }
      }

      if (shooter instanceof Player player) {
         player.getCooldowns().addCooldown(this, cooldown);
      }

      shooter.push(pushBack.scale(selfRecoil));
   }

   private double calculateSelfRecoil(LivingEntity shooter, ItemStack shotgun) {
      double base = (Double)JNEConfigs.SHOTGUN_SELF_RECOIL.get();
      int recoil = HolderHelper.getEnchantmentLevel(JNEEnchantments.RECOIL, shotgun);
      if (recoil > 0) {
         base += recoil / 10.0;
      }

      Vec3 raycastStart = shooter.getEyePosition(1.0F);
      Vec3 raycastEnd = raycastStart.add(shooter.getViewVector(1.0F).scale((Double)JNEConfigs.POINT_BLANK_SELF_RECOIL_DISTANCE.get()));
      AABB pointBlankBoundingBox = new AABB(raycastStart, raycastEnd);
      EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
         shooter.level(), shooter, raycastStart, raycastEnd, pointBlankBoundingBox, victim -> victim instanceof LivingEntity && victim != shooter
      );
      if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity living && living.isAlive()) {
         base += JNEConfigs.POINT_BLANK_SELF_RECOIL_BONUS.get();
         if (shooter.level().isClientSide) {
            ShotgunFistItem.Client.pointBlankParticle(shooter.level(), raycastStart.add(shooter.getViewVector(1.0F).scale(1.0)));
         }
      }

      return base;
   }

   private int calculateCount(ItemStack shotgun) {
      int base = (Integer)JNEConfigs.SHOTGUN_FIST_BULLETS.get();
      int volley = HolderHelper.getEnchantmentLevel(JNEEnchantments.VOLLEY, shotgun);
      int quickCharge = HolderHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, shotgun);
      if (volley > 0) {
         base += volley * 5;
      } else if (quickCharge > 0) {
         base -= quickCharge * 5;
      }

      return base;
   }

   private int calculateCooldown(ItemStack shotgun) {
      int base = (Integer)JNEConfigs.SHOTGUN_FIST_COOLDOWN.get();
      int volley = HolderHelper.getEnchantmentLevel(JNEEnchantments.VOLLEY, shotgun);
      int quickCharge = HolderHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, shotgun);
      if (volley > 0) {
         base += volley * 15;
      } else if (quickCharge > 0) {
         base -= quickCharge * 8;
      }

      return base;
   }

   protected void shootProjectile(
      LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target
   ) {
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !oldStack.is(JNEItems.SHOTGUN_FIST.get()) || !newStack.is(JNEItems.SHOTGUN_FIST.get());
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.NONE;
   }

   public Predicate<ItemStack> getAllSupportedProjectiles() {
      return stack -> stack.is(JNETags.Items.SHOTGUN_SHELLS);
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
      return repairCandidate.is(Items.NETHERITE_SCRAP);
   }

   public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
      return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.QUICK_CHARGE);
   }

   public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
      return JNEItems.SHOTGUN_SHELL.get().getDefaultInstance();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void pointBlankParticle(Level level, Vec3 pos) {
         WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
            .setFullBrightLighting()
            .setColorData(ColorParticleData.create(new Color(16777215)).build())
            .setScaleData(GenericParticleData.create(0.1F, 1.5F).setEasing(Easing.SINE_OUT).build())
            .setTransparencyData(GenericParticleData.create(0.7F, 0.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setLifetime(10)
            .disableNoClip()
            .spawn(level, pos.x, pos.y, pos.z);
      }

      public static void shotgunFlashParticle(Level level, RandomSource random, double x, double y, double z) {
         LodestoneWorldParticleType particle = JNEParticleTypes.SHOTGUN_FLASH.get();
         WorldParticleBuilder.create(particle)
            .setFullBrightLighting()
            .setScaleData(GenericParticleData.create(0.01F, 1.45F, 0.0F).build())
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 2.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(5)
            .enableNoClip()
            .addMotion(0.0, 0.0, 0.0)
            .spawn(level, x, y, z);
      }

      public static void cooldownParticle(LivingEntity user, Level level, RandomSource random, double x, double y, double z) {
         LodestoneWorldParticleType particle = CommonParticles.SMOKE_VARIANTS[random.nextInt(CommonParticles.SMOKE_VARIANTS.length)];
         Color startColor = new Color(1812914);
         Color endColor = new Color(1123105);
         Vec3 look = user.getLookAngle();
         float pushFactor = 0.1F;
         double motionX = random.nextDouble() / 10.0 + look.x * pushFactor;
         double motionY = -0.09 + random.nextDouble() / 64.0 + look.y * pushFactor;
         double motionZ = random.nextDouble() / 10.0 + look.z * pushFactor;
         WorldParticleBuilder.create(particle)
            .setFullBrightLighting()
            .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.23F, 0.3F), 0.75F).build())
            .setTransparencyData(GenericParticleData.create(0.75F, 0.4F, 0.0F).setEasing(Easing.BOUNCE_OUT).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(startColor, endColor).setEasing(Easing.SINE_IN_OUT).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(random, 10, 15))
            .disableNoClip()
            .addMotion(motionX, motionY, motionZ)
            .spawn(level, x, y, z);
      }
   }
}
