package com.tensura_tno.race.fox_spirit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public final class SpiritSummonLifecycle {
   private static final String TAG_SUMMONING = "tensura_tno_spirit_summoning";
   private static final String TAG_LAST_PROGRESS = "tensura_tno_spirit_summoning_progress";
   private static final String TAG_WAS_INVULNERABLE = "tensura_tno_spirit_summoning_invulnerable";
   private static final int ABANDONED_TICKS = 100;

   private SpiritSummonLifecycle() {
   }

   public static boolean isSummoning(Entity entity) {
      Entity root = SpiritSummonEntityHelper.getRootEntity(entity);
      return root != null && root.getPersistentData().getBoolean("tensura_tno_spirit_summoning");
   }

   public static void begin(Mob summon, ServerPlayer owner) {
      SpiritSummonEntityHelper.markSpiritSummon(summon, owner);
      CompoundTag tag = summon.getPersistentData();
      tag.putBoolean("tensura_tno_spirit_summoning_invulnerable", summon.isInvulnerable());
      tag.putBoolean("tensura_tno_spirit_summoning", true);
      refresh(summon);
   }

   public static void refresh(Mob summon) {
      summon.getPersistentData().putLong("tensura_tno_spirit_summoning_progress", summon.level().getGameTime());
      summon.setInvulnerable(true);
      summon.setNoAi(true);
      summon.noPhysics = true;
      summon.setDeltaMovement(Vec3.ZERO);
   }

   public static boolean isAbandoned(Mob summon) {
      long elapsed = summon.level().getGameTime() - summon.getPersistentData().getLong("tensura_tno_spirit_summoning_progress");
      return elapsed < 0L || elapsed > 100L;
   }

   public static void finish(Mob summon) {
      CompoundTag tag = summon.getPersistentData();
      summon.setInvulnerable(tag.getBoolean("tensura_tno_spirit_summoning_invulnerable"));
      summon.setNoAi(false);
      summon.noPhysics = false;
      tag.remove("tensura_tno_spirit_summoning");
      tag.remove("tensura_tno_spirit_summoning_progress");
      tag.remove("tensura_tno_spirit_summoning_invulnerable");
   }
}
