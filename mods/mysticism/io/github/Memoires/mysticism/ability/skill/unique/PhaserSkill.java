package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.ability.subclass.IProjectileEjection;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.data.MysticismItemTags;
import io.github.Memoires.mysticism.entity.skill.KamuiPortalEntity;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.entity.projectile.KunaiProjectile;
import io.github.manasmods.tensura.entity.projectile.SevererBladeProjectile;
import io.github.manasmods.tensura.entity.projectile.SpearProjectile;
import io.github.manasmods.tensura.entity.projectile.WebBulletProjectile;
import io.github.manasmods.tensura.item.weapon.SimpleSpearItem;
import io.github.manasmods.tensura.item.weapon.ranged.KunaiItem;
import io.github.manasmods.tensura.item.weapon.ranged.WebCartridgeItem;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PhaserSkill extends Skill implements IProjectileEjection<PhaserSkill> {
   private static final Logger log = LogManager.getLogger(PhaserSkill.class);
   private static final Map<UUID, List<PhaserSkill.HeldPreview>> HELD_PROJ = new ConcurrentHashMap<>();
   protected static final UniqueMysticismSkillConfig.Phaser CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Phaser;
   protected static final ResourceLocation PHASER = ResourceLocation.fromNamespaceAndPath("mysticism", "phaser");

   public PhaserSkill() {
      super(SkillType.UNIQUE);
   }

   public static void resetTags(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.contains("PassiveRunning") || tag.contains("IntangibilityActive")) {
         instance.getOrCreateTag().remove("PassiveRunning");
         instance.getOrCreateTag().remove("IntangibilityActive");
         instance.setCoolDown(CONFIG.intangibilityPassiveCooldown, 2);
      }
   }

   private boolean checkOveruse(ManasSkillInstance instance) {
      CompoundTag skillTag = instance.getOrCreateTag();
      TimerUtils.Timer timer = TimerUtils.getOrCreateTimer("OveruseTimer");
      if (skillTag.getInt("Overuse") > CONFIG.overuseCap) {
         instance.setCoolDowns(180);
         skillTag.putInt("Overuse", 0);
         return true;
      } else {
         skillTag.putInt("Overuse", skillTag.getInt("Overuse") + 1);
         timer.start(300, () -> skillTag.putInt("Overuse", 0));
         return false;
      }
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.ejectCost;
         case 1 -> CONFIG.kamuiCost;
         case 2 -> CONFIG.intangibilityActiveCost;
         default -> 500.0;
      };
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/phaser.png");
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   private void handlePassive(ManasSkillInstance instance, LivingEntity entity) {
      if (!EnergyHelper.isOutOfEnergy(entity, 0.0, CONFIG.intangibilityPassiveCost)) {
         instance.getOrCreateTag().putBoolean("PassiveRunning", true);
         this.checkOveruse(instance);
         entity.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.INTANGIBLE), CONFIG.intangibilityDuration, 0, false, false, false)
         );
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (instance.isMastered(entity) && !instance.getOrCreateTag().getBoolean("MasteryToggle")) {
         return true;
      } else if (entity instanceof Player player && player.isCreative()) {
         return true;
      } else {
         if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return true;
         }

         if (source.isCreativePlayer()) {
            return true;
         }

         if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            return true;
         }

         if (source.getDirectEntity() instanceof Projectile) {
            return true;
         }

         if (instance.getOrCreateTag().getBoolean("IntangibilityActive")) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, TensuraSkill.ABILITY_SOUND, 0.8F, 1.2F);
            return false;
         }

         if (!instance.onCoolDown(2)) {
            if (!instance.getOrCreateTag().getBoolean("PassiveRunning")) {
               this.handlePassive(instance, entity);
            }

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, TensuraSkill.ABILITY_SOUND, 0.8F, 1.2F);
            return false;
         } else {
            return true;
         }
      }
   }

   public void onProjectileHit(
      ManasSkillInstance instance,
      LivingEntity entity,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      if (!instance.isMastered(entity) || instance.getOrCreateTag().getBoolean("MasteryToggle")) {
         if (!(entity instanceof Player player && player.isCreative())) {
            if (projectile.getOwner() != null && projectile.getOwner() == entity) {
               deflection.set(ProjectileDeflection.NONE);
               result.set(ProjectileHitResult.PASS);
            } else {
               if (projectile.getOwner() instanceof Player player && player.isCreative()) {
                  return;
               }

               if (instance.getOrCreateTag().getBoolean("IntangibilityActive")) {
                  entity.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, TensuraSkill.ABILITY_SOUND, 0.8F, 1.2F);
                  deflection.set(ProjectileDeflection.NONE);
                  result.set(ProjectileHitResult.PASS);
               } else if (!instance.onCoolDown(2)) {
                  if (!instance.getOrCreateTag().getBoolean("PassiveRunning")) {
                     this.handlePassive(instance, entity);
                  }

                  entity.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, TensuraSkill.ABILITY_SOUND, 0.8F, 1.2F);
                  deflection.set(ProjectileDeflection.NONE);
                  result.set(ProjectileHitResult.PASS);
               } else {
                  deflection.set(ProjectileDeflection.NONE);
                  result.set(ProjectileHitResult.DEFAULT);
               }
            }
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)MysticismSoundEvents.EYE_OF_INSIGHT.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
         );
      ThoughtAccelerationSkill.onToggle(instance, entity, PHASER, true);
      AttributeHelper.addPresenceSense(entity, CONFIG.presenceSense);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, PHASER, false);
      AttributeHelper.removePresenceSense(entity, CONFIG.presenceSense);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (instance.isMastered(entity)) {
         if (reverse) {
            return mode == 0 ? 3 : mode - 1;
         } else {
            return (mode + 1) % this.getModes(instance);
         }
      } else {
         return mode == 0 ? 1 : 0;
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity living) {
      if (instance.isToggled() && EnergyHelper.isOutOfEnergy(living, 0.0, CONFIG.eyeOfInsightCost)) {
         this.onToggleOff(instance, living);
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "phaser.eject";
         case 1 -> "phaser.authority_of_the_gods";
         case 2 -> "phaser.intangibility";
         case 3 -> "phaser.intangibility_toggle";
         default -> super.getModeId(instance, mode);
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   @NotNull
   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provide) {
      SpatialStorageContainer container = new SpatialStorageContainer(20, 128);
      container.fromTag(instance.getOrCreateTag().getList("PhaserEject", 10), provide);
      return container;
   }

   private static List<Vec3> gridBehindLocal(int count, Random r) {
      double baseDist = 2.0;
      double halfW = 0.8;
      double yCenter = 1.1;
      double ySpan = 0.3;
      int cols = (int)Math.ceil(Math.sqrt(count));
      int rows = (int)Math.ceil((double)count / cols);
      double dx = cols <= 1 ? 0.0 : 2.0 * halfW / (cols - 1);
      double dy = rows <= 1 ? 0.0 : 2.0 * ySpan / (rows - 1);
      List<Vec3> out = new ArrayList<>(count);

      for (int i = 0; i < count; i++) {
         int row = i / cols;
         int col = i % cols;
         double x = -halfW + col * dx;
         double y = rows == 1 ? yCenter : yCenter - ySpan + row * dy;
         x += (r.nextDouble() - 0.5) * 0.84;
         y += (r.nextDouble() - 0.5) * 0.43;
         double dist = baseDist + (r.nextDouble() - 0.5) * 2.0;
         out.add(new Vec3(x, y, -dist));
      }

      return out;
   }

   private static void triggerPortalClose(Entity e) {
      if (e instanceof KamuiPortalEntity p) {
         p.setLife(p.getAge() + 19);
      }
   }

   private boolean isIntangible(LivingEntity entity) {
      return entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.INTANGIBLE));
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         if (entity.isShiftKeyDown()) {
            this.openEjectionMenu(entity, instance);
         }
      } else if (mode == 2) {
         if (!instance.isMastered(entity)) {
            return;
         }

         if (!instance.getOrCreateTag().getBoolean("IntangibilityActive")) {
            if (this.checkOveruse(instance)) {
               return;
            }

            instance.getOrCreateTag().putBoolean("IntangibilityActive", true);
         }

         entity.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.INTANGIBLE), CONFIG.intangibilityDuration, 0, false, false, false)
         );
      } else if (mode == 3) {
         if (!instance.isMastered(entity)) {
            return;
         }

         instance.getOrCreateTag().putBoolean("MasteryToggle", !instance.getOrCreateTag().getBoolean("MasteryToggle"));
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode == 0) {
         ServerLevel level = (ServerLevel)entity.level();
         SpatialStorageContainer container = this.getSpatialStorage(instance, entity.registryAccess());
         if (container.isEmpty()) {
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("mysticism.skill.mode.phaser.eject.empty").withStyle(ChatFormatting.RED), false);
               player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }

            return false;
         }

         if (heldTicks == 1) {
            Random random = new Random();
            List<Integer> slotIdxs = new ArrayList<>();

            for (int i = 0; i < container.getContainerSize(); i++) {
               ItemStack s = container.getItem(i);
               if (!s.isEmpty() && s.is(MysticismItemTags.KAMUI_PROJECTILE)) {
                  slotIdxs.add(i);
               }
            }

            if (slotIdxs.isEmpty()) {
               return false;
            }

            List<Vec3> localOffsets = gridBehindLocal(slotIdxs.size(), random);
            List<PhaserSkill.HeldPreview> created = new ArrayList<>(slotIdxs.size());

            for (int n = 0; n < slotIdxs.size(); n++) {
               int slot = slotIdxs.get(n);
               ItemStack one = container.getItem(slot).copy();
               one.setCount(1);
               Projectile proj = getProjectile(level, entity, one, instance);
               switch (proj) {
                  case null:
                     continue;
                  case AbstractArrow a:
                     a.pickup = entity.hasInfiniteMaterials() ? Pickup.CREATIVE_ONLY : Pickup.ALLOWED;
                     break;
                  case ThrowableItemProjectile tip:
                     tip.setItem(one);
                     break;
                  default:
               }

               Vec3 local = localOffsets.get(n);
               float yawRad = (float)Math.toRadians(-entity.getYHeadRot());
               Vec3 world = local.yRot(yawRad).add(entity.position());
               KamuiPortalEntity portal = new KamuiPortalEntity(level, entity);
               portal.setOwner(entity);
               portal.setPos(world.x, world.y, world.z);
               portal.setSize(5.0F);
               portal.setLife(80);
               portal.setFollowingRotation(true);
               portal.setFollowingOffset(true);
               portal.setFollowOffsetLocal(local);
               portal.setSkill(instance);
               level.addFreshEntity(portal);
               proj.setPos(world.x, world.y, world.z);
               proj.setOwner(entity);
               proj.setNoGravity(true);
               proj.setDeltaMovement(entity.getLookAngle().normalize().scale(0.01));
               level.addFreshEntity(proj);
               created.add(new PhaserSkill.HeldPreview(proj.getUUID(), portal.getUUID(), slot));
            }

            if (created.isEmpty()) {
               return false;
            }

            HELD_PROJ.put(entity.getUUID(), created);
         }

         List<PhaserSkill.HeldPreview> list = HELD_PROJ.get(entity.getUUID());
         if (list == null || list.isEmpty()) {
            return true;
         }

         int fullyCharged = instance.isMastered(entity) ? CONFIG.ejectChargeTicksMastered : CONFIG.ejectChargeTicks;
         if (heldTicks == fullyCharged && entity instanceof Player p) {
            p.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.2F);
         }

         for (PhaserSkill.HeldPreview hp : list) {
            if (level.getEntity(hp.portalId()) instanceof KamuiPortalEntity portal) {
               portal.setLife(portal.getAge() + 59);
               if (level.getEntity(hp.projectileId()) instanceof Projectile proj) {
                  proj.setNoGravity(true);
                  proj.setDeltaMovement(Vec3.ZERO);
                  Vec3 pp = portal.position();
                  float yaw = portal.getYRot();
                  float pitch = 0.0F;
                  proj.moveTo(pp.x, pp.y, pp.z, yaw, pitch);
               }
            }
         }
      } else if (mode == 1) {
         if (entity.isShiftKeyDown()) {
            if (heldTicks == 0) {
               if (this.checkOveruse(instance)) {
                  return false;
               }

               entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PORTAL_TRIGGER, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               KamuiPortalEntity.summonPortalOnEntity(10.0F, 80, entity, entity, this, true, true);
            }

            if (heldTicks == this.getRequiredWarpTicks(instance, entity) && entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.phaser.authority_of_the_gods.charged").withStyle(ChatFormatting.DARK_GRAY), true
               );
            }
         } else {
            if (!instance.isMastered(entity)) {
               return false;
            }

            AttributeInstance rangeInstance = entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            if (rangeInstance != null) {
               double range = rangeInstance.getValue();
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
               if (target != null) {
                  if (heldTicks == 0) {
                     if (this.checkOveruse(instance)) {
                        return false;
                     }

                     entity.level()
                        .playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.PORTAL_TRIGGER, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                     KamuiPortalEntity.summonPortalOnEntity(10.0F, 80, target, target, this, true, true);
                  }

                  if (heldTicks == this.getRequiredWarpTicks(instance, target) && entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.phaser.authority_of_the_gods.charged").withStyle(ChatFormatting.DARK_GRAY), true
                     );
                  }
               }
            }
         }
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 0 && TickingSkill.isTickingSkill(entity, this, 0)) {
         if (entity.isShiftKeyDown()) {
            return;
         }

         ServerLevel level = (ServerLevel)entity.level();
         List<PhaserSkill.HeldPreview> list = HELD_PROJ.remove(entity.getUUID());
         if (list == null || list.isEmpty()) {
            return;
         }

         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 30.0, true);
         Vec3 aimPoint;
         if (target != null) {
            aimPoint = target.getEyePosition();
         } else {
            BlockHitResult bhr = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, 30.0);
            if (bhr != null) {
               aimPoint = bhr.getLocation();
            } else {
               Vec3 eye = entity.getEyePosition();
               aimPoint = eye.add(entity.getLookAngle().scale(30.0));
            }
         }

         int fullyCharged = instance.isMastered(entity) ? CONFIG.ejectChargeTicksMastered : CONFIG.ejectChargeTicks;
         if (heldTicks >= fullyCharged) {
            for (PhaserSkill.HeldPreview hp : list) {
               if (level.getEntity(hp.projectileId) instanceof Projectile p) {
                  Vec3 origin = p.position();
                  Vec3 dir = aimPoint.subtract(origin).normalize();
                  p.setNoGravity(false);
                  p.shoot(dir.x, dir.y, dir.z, 3.0F, 0.0F);
               }

               Entity portal = level.getEntity(hp.portalId);
               if (portal != null) {
                  triggerPortalClose(portal);
               }
            }

            if (entity instanceof Player player) {
               this.reduceProjectileStackCount(instance, player);
            }

            instance.addMasteryPoint(entity);
         } else {
            for (PhaserSkill.HeldPreview hp : list) {
               Entity proj = level.getEntity(hp.projectileId);
               if (proj != null) {
                  proj.discard();
               }

               Entity portal = level.getEntity(hp.portalId);
               if (portal != null) {
                  triggerPortalClose(portal);
               }
            }
         }
      } else if (mode == 1 && TickingSkill.isTickingSkill(entity, this, 1)) {
         if (entity.isShiftKeyDown()) {
            if (heldTicks >= this.getRequiredWarpTicks(instance, entity)) {
               MinecraftServer server = entity.getServer();
               if (server == null) {
                  log.error("Entity has no server reference");
                  return;
               }

               if (entity.level().dimension() == MysticismDimensions.KAMUI_DIMENSION) {
                  ResourceKey<Level> dimensionKey = Level.OVERWORLD;
                  String storedDimension = instance.getOrCreateTag().getString("sourceDimension");
                  ServerLevel toLevel;
                  if (!storedDimension.isEmpty()) {
                     ResourceLocation dimensionLocation = ResourceLocation.tryParse(storedDimension);
                     if (dimensionLocation != null) {
                        ResourceKey<Level> candidate = ResourceKey.create(Registries.DIMENSION, dimensionLocation);
                        ServerLevel candidateLevel = server.getLevel(candidate);
                        if (candidateLevel != null) {
                           dimensionKey = candidate;
                           toLevel = candidateLevel;
                        } else {
                           toLevel = server.getLevel(Level.OVERWORLD);
                        }
                     } else {
                        toLevel = server.getLevel(Level.OVERWORLD);
                     }
                  } else {
                     toLevel = server.getLevel(Level.OVERWORLD);
                  }

                  if (toLevel == null) {
                     log.error("Failed to get destination dimension");
                     return;
                  }

                  Vec3 safe = scaledClampedSafeTarget((ServerLevel)entity.level(), toLevel, entity, entity.getX(), entity.getY(), entity.getZ());
                  SpawnPointHelper.teleportToAcrossDimensions(entity, dimensionKey, safe.x, safe.y, safe.z, entity.getXRot(), entity.getYRot());
               } else {
                  ServerLevel toLevel = server.getLevel(MysticismDimensions.KAMUI_DIMENSION);
                  if (toLevel == null) {
                     log.error("Failed to get Kamui dimension - is it registered?");
                     return;
                  }

                  Vec3 safe = scaledClampedSafeTarget((ServerLevel)entity.level(), toLevel, entity, entity.getX(), entity.getY(), entity.getZ());
                  SpawnPointHelper.teleportToAcrossDimensions(
                     entity, MysticismDimensions.KAMUI_DIMENSION, safe.x, safe.y, safe.z, entity.getXRot(), entity.getYRot()
                  );
               }

               instance.addMasteryPoint(entity);
               instance.setCoolDown(CONFIG.kamuiCooldown, mode);
               entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_TELEPORT, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }
         } else {
            if (!instance.isMastered(entity)) {
               return;
            }

            AttributeInstance rangeInstance = entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            if (rangeInstance != null) {
               double range = rangeInstance.getValue();
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
               if (target != null && heldTicks >= this.getRequiredWarpTicks(instance, target)) {
                  MinecraftServer server = entity.getServer();
                  if (server == null) {
                     log.error("Entity has no server reference");
                     return;
                  }

                  if (entity.level().dimension() == MysticismDimensions.KAMUI_DIMENSION) {
                     ServerLevel toLevel = server.getLevel(Level.OVERWORLD);
                     if (toLevel == null) {
                        log.error("Failed to get Overworld dimension");
                        return;
                     }

                     Vec3 safe = scaledClampedSafeTarget((ServerLevel)target.level(), toLevel, target, target.getX(), target.getY(), target.getZ());
                     SpawnPointHelper.teleportToAcrossDimensions(target, Level.OVERWORLD, safe.x, safe.y, safe.z, target.getXRot(), target.getYRot());
                  } else {
                     ServerLevel toLevel = server.getLevel(MysticismDimensions.KAMUI_DIMENSION);
                     if (toLevel == null) {
                        log.error("Failed to get Kamui dimension - is it registered?");
                        return;
                     }

                     Vec3 safe = scaledClampedSafeTarget((ServerLevel)target.level(), toLevel, target, target.getX(), target.getY(), target.getZ());
                     SpawnPointHelper.teleportToAcrossDimensions(
                        entity, MysticismDimensions.KAMUI_DIMENSION, safe.x, safe.y, safe.z, target.getXRot(), target.getYRot()
                     );
                  }

                  instance.addMasteryPoint(entity);
                  instance.setCoolDown(CONFIG.kamuiCooldown, mode);
                  target.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_TELEPORT, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   private int getRequiredWarpTicks(ManasSkillInstance instance, LivingEntity living) {
      double multiplier = 1.0;
      AttributeInstance scale = living.getAttribute(Attributes.SCALE);
      if (scale != null) {
         multiplier = scale.getValue();
      }

      int baseWarpTicks = instance.isMastered(living) ? CONFIG.kamuiWarpTicks / 2 : CONFIG.kamuiWarpTicks;
      return Mth.ceil(baseWarpTicks * multiplier);
   }

   public static Vec3 findSafeStandPos(ServerLevel level, LivingEntity e, double x, double z) {
      WorldBorder border = level.getWorldBorder();
      BlockPos clampedXZ = border.clampToBounds(Mth.floor(x), 0.0, Mth.floor(z));
      int bx = clampedXZ.getX();
      int bz = clampedXZ.getZ();
      int cx = SectionPos.blockToSectionCoord(bx);
      int cz = SectionPos.blockToSectionCoord(bz);
      level.getChunkSource().getChunk(cx, cz, ChunkStatus.FULL, true);
      int minY = level.getMinBuildHeight() + 1;
      int maxY = level.getMaxBuildHeight() - 2;
      EntityDimensions dims = e.getDimensions(e.getPose());
      Vec3 exact = findSafeAtColumn(level, e, dims, bx, bz, minY, maxY);
      if (exact != null) {
         return exact;
      }

      int radius = 16;

      for (int r = 1; r <= radius; r++) {
         for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
               if (Math.abs(dx) == r || Math.abs(dz) == r) {
                  int px = bx + dx;
                  int pz = bz + dz;
                  if (border.isWithinBounds(new BlockPos(px, minY, pz))) {
                     Vec3 safe = findSafeAtColumn(level, e, dims, px, pz, minY, maxY);
                     if (safe != null) {
                        return safe;
                     }
                  }
               }
            }
         }
      }

      BlockPos platform = new BlockPos(bx, Math.max(level.getSeaLevel(), minY + 8), bz);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            BlockPos floor = platform.offset(dx, 0, dz);
            if (level.getBlockState(floor).isAir()) {
               level.setBlock(floor, Blocks.CYAN_TERRACOTTA.defaultBlockState(), 3);
            }
         }
      }

      return new Vec3(bx + 0.5, platform.getY() + 1.0, bz + 0.5);
   }

   @Nullable
   private static Vec3 findSafeAtColumn(ServerLevel level, LivingEntity e, EntityDimensions dims, int x, int z, int minY, int maxY) {
      MutableBlockPos floor = new MutableBlockPos();

      for (int y = maxY; y >= minY; y--) {
         floor.set(x, y - 1, z);
         BlockState floorState = level.getBlockState(floor);
         BlockState feetState = level.getBlockState(floor.above());
         BlockState headState = level.getBlockState(floor.above(2));
         if (!floorState.isAir()
            && floorState.getFluidState().isEmpty()
            && feetState.getCollisionShape(level, floor.above()).isEmpty()
            && headState.getCollisionShape(level, floor.above(2)).isEmpty()) {
            AABB box = dims.makeBoundingBox(x + 0.5, y, z + 0.5);
            if (level.noCollision(e, box) && level.isUnobstructed(e, Shapes.create(box))) {
               return new Vec3(x + 0.5, y, z + 0.5);
            }
         }
      }

      return null;
   }

   public static Vec3 scaledClampedSafeTarget(ServerLevel from, ServerLevel to, LivingEntity e, double x, double y, double z) {
      double scale = DimensionType.getTeleportationScale(from.dimensionType(), to.dimensionType());
      double sx = x * scale;
      double sz = z * scale;
      WorldBorder border = to.getWorldBorder();
      BlockPos clamped = border.clampToBounds(Mth.floor(sx), Mth.floor(y), Mth.floor(sz));
      return findSafeStandPos(to, e, clamped.getX() + 0.5, clamped.getZ() + 0.5);
   }

   private static Projectile getProjectile(Level level, LivingEntity entity, ItemStack stack, @Nullable ManasSkillInstance instance) {
      if (stack != null && !stack.isEmpty()) {
         if (stack.getItem() instanceof ArrowItem arrowItem) {
            AbstractArrow arrow = arrowItem.createArrow(level, stack, entity, null);
            double baseDamage = arrow.getBaseDamage() * 3.0;
            arrow.setBaseDamage(baseDamage);
            return arrow;
         } else if (stack.getItem() instanceof ExperienceBottleItem) {
            return new ThrownExperienceBottle(level, entity);
         } else if (stack.getItem() instanceof EggItem) {
            return new ThrownEgg(level, entity);
         } else if (stack.getItem() instanceof EnderpearlItem) {
            return new ThrownEnderpearl(level, entity);
         } else if (stack.getItem() instanceof SnowballItem) {
            return new Snowball(level, entity);
         } else if (stack.getItem() instanceof FireworkRocketItem) {
            return new FireworkRocketEntity(level, stack, entity, entity.getX(), entity.getY(), entity.getZ(), true);
         } else if (stack.getItem() instanceof ThrowablePotionItem) {
            return new ThrownPotion(level, entity);
         } else if (stack.getItem() instanceof WebCartridgeItem) {
            return new WebBulletProjectile(level, entity, true, stack, stack);
         } else {
            float baseDamage = instance != null && instance.isMastered(entity) ? CONFIG.itemEjectDamageMastered : CONFIG.itemEjectDamage;
            baseDamage *= 3.0F;
            if (stack.getItem().equals(TensuraToolItems.SEVERER_BLADE.get())) {
               SevererBladeProjectile blade = new SevererBladeProjectile(level, entity, true, stack);
               blade.setSkill(instance);
               blade.setBaseDamage(baseDamage);
               return blade;
            } else if (stack.getItem() instanceof TridentItem) {
               ThrownTrident trident = new ThrownTrident(level, entity, stack);
               trident.setBaseDamage(baseDamage);
               return trident;
            } else if (stack.getItem() instanceof SimpleSpearItem) {
               SpearProjectile spear = new SpearProjectile(level, entity, stack, true);
               spear.setBaseDamage(baseDamage);
               return spear;
            } else if (stack.getItem() instanceof KunaiItem) {
               ItemStack kunai = stack.copy();
               kunai.setCount(1);
               KunaiProjectile kunaiProjectile = new KunaiProjectile(level, entity, kunai, true);
               kunaiProjectile.setBaseDamage(baseDamage);
               return kunaiProjectile;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private record HeldPreview(UUID projectileId, UUID portalId, int slotIdx) {
   }
}
