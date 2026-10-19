package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceTameEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;
import org.jetbrains.annotations.Nullable;

public class DomainOfNegationSkill extends Skill {
   private static final ResourceLocation DOMAIN_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "domain_of_negation");

   public DomainOfNegationSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.DomainOfNegationConfig cfg() {
      return MoreSkillsConfigs.GRAND.domainOfNegation;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/domain.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "domain_of_negation.domain";
         case 1 -> "domain_of_negation.burst";
         default -> super.getModeId(instance, mode);
      };
   }

   @Nullable
   public MutableComponent getName() {
      return Component.translatable("tensuramoreskills.skill.domain_of_negation")
         .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD});
   }

   public MutableComponent getSkillDescription() {
      return Component.translatable("tensuramoreskills.skill.domain_of_negation.description");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> cfg().domainCost.get();
         case 1 -> cfg().burstCost.get();
         default -> 0.0;
      };
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return DomainOfNegationSkill.DomainManager.isOwner(entity);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            DomainOfNegationSkill.DomainManager.DomainState state = DomainOfNegationSkill.DomainManager.getState(player.getUUID());
            if (state != null) {
               if (!state.level.dimension().equals(level.dimension())) {
                  DomainOfNegationSkill.DomainManager.endDomain(player.getUUID());
               } else {
                  double radius = ((Integer)cfg().domainRadius.get()).intValue();
                  double maxDistance = radius * radius;
                  if (player.position().distanceToSqr(Vec3.atCenterOf(state.center)) <= maxDistance) {
                     player.addEffect(
                        new MobEffectInstance(
                           MobEffects.DAMAGE_BOOST, (Integer)cfg().ownerEffectTicks.get(), (Integer)cfg().ownerStrengthAmplifier.get(), false, false, false
                        )
                     );
                     player.addEffect(
                        new MobEffectInstance(
                           MobEffects.DAMAGE_RESISTANCE,
                           (Integer)cfg().ownerEffectTicks.get(),
                           (Integer)cfg().ownerResistanceAmplifier.get(),
                           false,
                           false,
                           false
                        )
                     );
                     player.addEffect(
                        new MobEffectInstance(
                           MobEffects.MOVEMENT_SPEED, (Integer)cfg().ownerEffectTicks.get(), (Integer)cfg().ownerSpeedAmplifier.get(), false, false, false
                        )
                     );
                     player.addEffect(
                        new MobEffectInstance(
                           MobEffects.REGENERATION, (Integer)cfg().ownerEffectTicks.get(), (Integer)cfg().ownerRegenerationAmplifier.get(), false, false, false
                        )
                     );
                     player.addEffect(
                        new MobEffectInstance(
                           TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                           (Integer)cfg().ownerEffectTicks.get(),
                           (Integer)cfg().ownerAntiSkillAmplifier.get(),
                           false,
                           false,
                           false
                        )
                     );
                  }
               }
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            switch (mode) {
               case 0:
                  if (DomainOfNegationSkill.DomainManager.isOwner(player)) {
                     DomainOfNegationSkill.DomainManager.endDomain(player.getUUID());
                     player.displayClientMessage(
                        Component.translatable("tensuramoreskills.skill.domain_of_negation.deactivate").withStyle(ChatFormatting.GRAY), true
                     );
                  } else {
                     BlockPos centerx = player.blockPosition().above(5);
                     DomainOfNegationSkill.DomainManager.startDomain(player, level, centerx);
                     instance.setCoolDown((Integer)cfg().domainCooldownTicks.get(), mode);
                     player.displayClientMessage(
                        Component.translatable("tensuramoreskills.skill.domain_of_negation.activate").withStyle(ChatFormatting.DARK_PURPLE), true
                     );
                     level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.BEACON_ACTIVATE,
                        SoundSource.PLAYERS,
                        ((Double)cfg().activateBeaconVolume.get()).floatValue(),
                        ((Double)cfg().activateBeaconPitch.get()).floatValue()
                     );
                     level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.END_PORTAL_SPAWN,
                        SoundSource.PLAYERS,
                        ((Double)cfg().activatePortalVolume.get()).floatValue(),
                        ((Double)cfg().activatePortalPitch.get()).floatValue()
                     );
                  }
                  break;
               case 1:
                  DomainOfNegationSkill.DomainManager.DomainState state = DomainOfNegationSkill.DomainManager.getState(player.getUUID());
                  BlockPos center = state != null ? state.center : player.blockPosition().above(1);
                  boolean empowered = state != null;
                  this.divineBurst(level, player, center, empowered, state);
                  instance.setCoolDown((Integer)cfg().burstCooldownTicks.get(), mode);
            }
         }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      DomainOfNegationSkill.DomainManager.endDomain(entity.getUUID());
   }

   private void divineBurst(
      ServerLevel level, ServerPlayer owner, BlockPos center, boolean empoweredByDomain, @Nullable DomainOfNegationSkill.DomainManager.DomainState state
   ) {
      double radius = empoweredByDomain ? ((Integer)cfg().domainRadius.get()).intValue() : (Double)cfg().burstRadiusOutsideDomain.get();
      float damage = empoweredByDomain
         ? ((Double)cfg().burstDamageInsideDomain.get()).floatValue()
         : ((Double)cfg().burstDamageOutsideDomain.get()).floatValue();
      Vec3 centerVec = Vec3.atCenterOf(center);
      AABB box = new AABB(centerVec, centerVec).inflate(radius);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity.isAlive() && entity != owner && !entity.isAlliedTo(owner))) {
         target.removeAllEffects();
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
               (Integer)cfg().burstEffectTicks.get(),
               (Integer)cfg().burstAntiSkillAmplifier.get(),
               false,
               false,
               false
            )
         );
         target.addEffect(
            new MobEffectInstance(
               MobEffects.MOVEMENT_SLOWDOWN, (Integer)cfg().burstEffectTicks.get(), (Integer)cfg().burstSlownessAmplifier.get(), false, false, false
            )
         );
         target.addEffect(
            new MobEffectInstance(MobEffects.WEAKNESS, (Integer)cfg().burstEffectTicks.get(), (Integer)cfg().burstWeaknessAmplifier.get(), false, false, false)
         );
         Vec3 direction = centerVec.subtract(target.position());
         if (direction.lengthSqr() > 0.001) {
            Vec3 pull = direction.normalize().scale((Double)cfg().burstPullStrength.get());
            target.push(pull.x, (Double)cfg().burstPullY.get(), pull.z);
            target.hurtMarked = true;
         }

         target.hurt(level.damageSources().magic(), damage);
         if (!target.isAlive() && target instanceof Mob mob && !(target instanceof Player)) {
            DomainOfNegationSkill.DomainManager.trySpawnNegatedServant(level, owner, mob, state);
         }
      }

      level.playSound(
         null,
         center,
         SoundEvents.WITHER_SHOOT,
         SoundSource.PLAYERS,
         ((Double)cfg().burstWitherVolume.get()).floatValue(),
         ((Double)cfg().burstWitherPitch.get()).floatValue()
      );
      level.playSound(
         null,
         center,
         SoundEvents.BEACON_POWER_SELECT,
         SoundSource.PLAYERS,
         ((Double)cfg().burstBeaconVolume.get()).floatValue(),
         ((Double)cfg().burstBeaconPitch.get()).floatValue()
      );
      level.playSound(
         null,
         center,
         SoundEvents.ENDER_DRAGON_GROWL,
         SoundSource.PLAYERS,
         ((Double)cfg().burstDragonVolume.get()).floatValue(),
         ((Double)cfg().burstDragonPitch.get()).floatValue()
      );
   }

   @EventBusSubscriber(modid = "tensuramoreskills")
   public static class DomainManager {
      private static final Map<UUID, DomainOfNegationSkill.DomainManager.DomainState> ACTIVE = new HashMap<>();

      public static boolean isOwner(LivingEntity entity) {
         return ACTIVE.containsKey(entity.getUUID());
      }

      public static DomainOfNegationSkill.DomainManager.DomainState getState(UUID uuid) {
         return ACTIVE.get(uuid);
      }

      public static void startDomain(ServerPlayer owner, ServerLevel level, BlockPos center) {
         endDomain(owner.getUUID());
         DomainOfNegationSkill.DomainManager.DomainState state = new DomainOfNegationSkill.DomainManager.DomainState(owner.getUUID(), level, center);
         ACTIVE.put(owner.getUUID(), state);
         buildFullSphere(state);
      }

      public static void endDomain(UUID ownerId) {
         DomainOfNegationSkill.DomainManager.DomainState state = ACTIVE.remove(ownerId);
         if (state != null) {
            removeShell(state);
            state.level
               .playSound(
                  null,
                  state.center,
                  SoundEvents.BEACON_DEACTIVATE,
                  SoundSource.PLAYERS,
                  ((Double)DomainOfNegationSkill.cfg().deactivateVolume.get()).floatValue(),
                  ((Double)DomainOfNegationSkill.cfg().deactivatePitch.get()).floatValue()
               );
         }
      }

      @SubscribeEvent
      public static void onLevelTick(Post event) {
         if (event.getLevel() instanceof ServerLevel level) {
            if (!ACTIVE.isEmpty()) {
               Iterator<Entry<UUID, DomainOfNegationSkill.DomainManager.DomainState>> iterator = ACTIVE.entrySet().iterator();

               while (iterator.hasNext()) {
                  DomainOfNegationSkill.DomainManager.DomainState state = iterator.next().getValue();
                  if (state.level == level) {
                     state.age++;
                     applySuppression(state);
                     if (state.age >= (Integer)DomainOfNegationSkill.cfg().domainDurationTicks.get()) {
                        removeShell(state);
                        level.playSound(
                           null,
                           state.center,
                           SoundEvents.BEACON_DEACTIVATE,
                           SoundSource.PLAYERS,
                           ((Double)DomainOfNegationSkill.cfg().deactivateVolume.get()).floatValue(),
                           ((Double)DomainOfNegationSkill.cfg().deactivatePitch.get()).floatValue()
                        );
                        iterator.remove();
                     }
                  }
               }
            }
         }
      }

      private static void buildFullSphere(DomainOfNegationSkill.DomainManager.DomainState state) {
         ServerLevel level = state.level;
         BlockPos center = state.center;
         int radius = (Integer)DomainOfNegationSkill.cfg().domainRadius.get();

         for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
               for (int dz = -radius; dz <= radius; dz++) {
                  double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                  if (!(distance < radius - 1.15) && !(distance > radius + 0.15)) {
                     BlockPos pos = center.offset(dx, dy, dz);
                     if (!pos.equals(center) && (level.isEmptyBlock(pos) || level.getBlockState(pos).canBeReplaced())) {
                        level.setBlock(pos, Blocks.BLACK_CONCRETE.defaultBlockState(), (Integer)DomainOfNegationSkill.cfg().shellBlockPlaceFlags.get());
                        state.placedBlocks.add(pos.immutable());
                     }
                  }
               }
            }
         }
      }

      private static void removeShell(DomainOfNegationSkill.DomainManager.DomainState state) {
         for (BlockPos pos : state.placedBlocks) {
            if (state.level.getBlockState(pos).is(Blocks.BLACK_CONCRETE)) {
               state.level.setBlock(pos, Blocks.AIR.defaultBlockState(), (Integer)DomainOfNegationSkill.cfg().shellBlockPlaceFlags.get());
            }
         }

         state.placedBlocks.clear();
      }

      private static void applySuppression(DomainOfNegationSkill.DomainManager.DomainState state) {
         ServerLevel level = state.level;
         MinecraftServer server = level.getServer();
         if (server != null) {
            ServerPlayer owner = server.getPlayerList().getPlayer(state.ownerId);
            Vec3 center = Vec3.atCenterOf(state.center);
            double radius = ((Integer)DomainOfNegationSkill.cfg().domainRadius.get()).intValue();
            double radiusSqr = radius * radius;
            AABB box = new AABB(center, center).inflate(radius + 1.0);

            for (LivingEntity target : level.getEntitiesOfClass(
               LivingEntity.class, box, entity -> entity.isAlive() && (owner == null || entity != owner) && (owner == null || !entity.isAlliedTo(owner))
            )) {
               if (!(target.position().distanceToSqr(center) > radiusSqr)) {
                  target.addEffect(
                     new MobEffectInstance(
                        TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                        (Integer)DomainOfNegationSkill.cfg().targetEffectTicks.get(),
                        (Integer)DomainOfNegationSkill.cfg().targetAntiSkillAmplifier.get(),
                        false,
                        false,
                        false
                     )
                  );
                  target.addEffect(
                     new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        (Integer)DomainOfNegationSkill.cfg().targetEffectTicks.get(),
                        (Integer)DomainOfNegationSkill.cfg().targetSlownessAmplifier.get(),
                        false,
                        false,
                        false
                     )
                  );
                  target.addEffect(
                     new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        (Integer)DomainOfNegationSkill.cfg().targetEffectTicks.get(),
                        (Integer)DomainOfNegationSkill.cfg().targetWeaknessAmplifier.get(),
                        false,
                        false,
                        false
                     )
                  );
                  Vec3 direction = center.subtract(target.position());
                  if (direction.lengthSqr() > 0.001) {
                     Vec3 motion = direction.normalize().scale((Double)DomainOfNegationSkill.cfg().passivePullStrength.get());
                     target.push(motion.x, (Double)DomainOfNegationSkill.cfg().passivePullY.get(), motion.z);
                     target.hurtMarked = true;
                  }

                  if (state.age % (Integer)DomainOfNegationSkill.cfg().passivePulseIntervalTicks.get() == 0) {
                     target.hurt(level.damageSources().magic(), ((Double)DomainOfNegationSkill.cfg().passivePulseDamage.get()).floatValue());
                     if (!target.isAlive() && owner != null && target instanceof Mob mob && !(target instanceof Player)) {
                        trySpawnNegatedServant(level, owner, mob, state);
                     }
                  }
               }
            }
         }
      }

      public static void trySpawnNegatedServant(
         ServerLevel level, ServerPlayer owner, Mob original, @Nullable DomainOfNegationSkill.DomainManager.DomainState state
      ) {
         if (state == null || state.summons < (Integer)DomainOfNegationSkill.cfg().maxSummons.get()) {
            if (original.getType().create(level) instanceof Mob servant) {
               servant.moveTo(original.getX(), original.getY(), original.getZ(), original.getYRot(), original.getXRot());
               servant.setHealth(Math.max(1.0F, servant.getMaxHealth() * ((Double)DomainOfNegationSkill.cfg().servantHealthMultiplier.get()).floatValue()));
               servant.addEffect(
                  new MobEffectInstance(
                     MobEffects.DAMAGE_BOOST,
                     (Integer)DomainOfNegationSkill.cfg().servantBuffTicks.get(),
                     (Integer)DomainOfNegationSkill.cfg().servantStrengthAmplifier.get(),
                     false,
                     false,
                     false
                  )
               );
               servant.addEffect(
                  new MobEffectInstance(
                     MobEffects.DAMAGE_RESISTANCE,
                     (Integer)DomainOfNegationSkill.cfg().servantBuffTicks.get(),
                     (Integer)DomainOfNegationSkill.cfg().servantResistanceAmplifier.get(),
                     false,
                     false,
                     false
                  )
               );
               servant.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                     (Integer)DomainOfNegationSkill.cfg().servantBuffTicks.get(),
                     (Integer)DomainOfNegationSkill.cfg().servantAntiSkillAmplifier.get(),
                     false,
                     false,
                     false
                  )
               );
               level.addFreshEntity(servant);
               if (!((ForceTameEvent)TensuraEntityEvents.FORCE_TAME_EVENT.invoker()).tame(servant, owner, false).isFalse()) {
                  IExistence existence = TensuraStorages.getExistenceFrom(servant);
                  existence.setTemporaryOwner(owner.getUUID());
                  existence.markDirty();
                  if (servant instanceof ISubordinate subordinate) {
                     subordinate.tame(owner);
                  }

                  SubordinateHelper.removeTarget(servant);
                  SubordinateHelper.setWander(servant);
               }

               if (state != null) {
                  state.summons++;
               }

               level.playSound(
                  null,
                  servant.blockPosition(),
                  SoundEvents.EVOKER_CAST_SPELL,
                  SoundSource.PLAYERS,
                  ((Double)DomainOfNegationSkill.cfg().servantSoundVolume.get()).floatValue(),
                  ((Double)DomainOfNegationSkill.cfg().servantSoundPitch.get()).floatValue()
               );
            }
         }
      }

      public static class DomainState {
         public final UUID ownerId;
         public final ServerLevel level;
         public final BlockPos center;
         public final Set<BlockPos> placedBlocks = new HashSet<>();
         public int age;
         public int summons;

         public DomainState(UUID ownerId, ServerLevel level, BlockPos center) {
            this.ownerId = ownerId;
            this.level = level;
            this.center = center;
            this.age = 0;
            this.summons = 0;
         }
      }
   }
}
