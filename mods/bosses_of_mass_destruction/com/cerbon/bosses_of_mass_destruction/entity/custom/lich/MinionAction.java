package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.CompoundTagEntityProvider;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.IMobSpawner;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobEntitySpawnPredicate;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobPlacementLogic;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.RangedSpawnPosition;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.SimpleMobSpawner;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.ParticleUtils;
import com.cerbon.cerbons_api.api.static_utilities.RegistryUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class MinionAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   public static final int minionSummonCooldown = 80;
   public static final int minionSummonParticleDelay = 10;
   public static final int minionSummonDelay = 40;
   public static final int minionRuneToMinionSpawnDelay = 40;
   public static final String summonId = "minecraft:phantom";
   public static final CompoundTag summonNbt;
   public static final EntityType<?> summonEntityType;

   public MinionAction(LichEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      Entity target = this.entity.getTarget();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      }

      this.performMinionSummon((ServerPlayer)target);
      return 80;
   }

   private void performMinionSummon(ServerPlayer target) {
      this.eventScheduler.addEvent(new TimedEvent(() -> this.beginSummonSingleMob(target), 40, 1, this.shouldCancel));
   }

   void beginSummonSingleMob(ServerPlayer target) {
      CompoundTag compoundTag = summonNbt.copy();
      compoundTag.putString("id", "minecraft:phantom");
      SimpleMobSpawner mobSpawner = new SimpleMobSpawner(target.serverLevel());
      CompoundTagEntityProvider entityProvider = new CompoundTagEntityProvider(compoundTag, target.serverLevel());
      MobEntitySpawnPredicate spawnPredicate = new MobEntitySpawnPredicate(target.level());
      IMobSpawner summonCircleBeforeSpawn = (pos, summon) -> {
         ParticleUtils.spawnParticle(target.serverLevel(), (ParticleOptions)BMDParticles.MAGIC_CIRCLE.get(), pos, Vec3.ZERO, 0, 0.0);
         SoundUtils.playSound(target.serverLevel(), pos, (SoundEvent)BMDSounds.MINION_RUNE.get(), SoundSource.HOSTILE, 1.0F, 64.0, null);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            mobSpawner.spawn(pos, summon);
            this.entity.playSound((SoundEvent)BMDSounds.MINION_SUMMON.get(), 0.7F, 1.0F);
         }, 40, 1, this.shouldCancel));
      };
      new MobPlacementLogic(new RangedSpawnPosition(target.position(), 4.0, 8.0), entityProvider, spawnPredicate, summonCircleBeforeSpawn).tryPlacement(30);
   }

   static {
      try {
         summonNbt = TagParser.parseTag("{Health:14,Size:2,Attributes:[{Name:\"generic.max_health\",Base:14f}]}");
      } catch (CommandSyntaxException e) {
         throw new RuntimeException(e);
      }

      summonEntityType = RegistryUtils.getEntityTypeByKey("minecraft:phantom");
   }
}
