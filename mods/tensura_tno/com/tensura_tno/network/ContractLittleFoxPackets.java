package com.tensura_tno.network;

import com.tensura_tno.compat.BetterSubsFoxCompat;
import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import com.tensura_tno.registry.TensuraTNOEntities;
import com.tensura_tno.registry.TensuraTNOSkills;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class ContractLittleFoxPackets {
   private static final String TAG_ROOT = "tensura_tno_fox_spirit";
   private static final String TAG_FOX_UUID = "FoxUUID";
   private static final String TAG_RECALLED_STATS = "RecalledStats";
   private static final String TAG_SUMMON_COOLDOWN_UNTIL_TICK = "SummonCooldownUntilTick";
   private static final int SUMMON_COOLDOWN_AFTER_DEATH_TICKS = 1200;
   private static final String K_HP = "HP";
   private static final String K_MAX_HP = "MaxHP";
   private static final String K_RECALLED_TIME = "RecalledTime";
   private static final String K_AURA = "Aura";
   private static final String K_MAX_AURA = "MaxAura";
   private static final String K_MAGIC = "Magic";
   private static final String K_MAX_MAGIC = "MaxMagic";
   private static final String K_SHP = "SHP";
   private static final String K_MAX_SHP = "MaxSHP";
   private static final String K_ATK = "Atk";
   private static final String TAG_TNO_LEARNED_SKILLS = "TnoLearnedSkills";

   public static void registerC2S() {
      NetworkManager.registerReceiver(
         Side.C2S,
         ContractLittleFoxPackets.ActionPayload.TYPE,
         ContractLittleFoxPackets.ActionPayload.STREAM_CODEC,
         (payload, ctx) -> ctx.queue(() -> handleAction(payload, ctx))
      );
   }

   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C,
         ContractLittleFoxPackets.OpenPanelPayload.TYPE,
         ContractLittleFoxPackets.OpenPanelPayload.STREAM_CODEC,
         (payload, ctx) -> ctx.queue(() -> openClientScreen(payload))
      );
   }

   private static void openClientScreen(ContractLittleFoxPackets.OpenPanelPayload payload) {
      try {
         Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
         Object minecraft = minecraftClass.getMethod("getInstance").invoke(null);
         Class<?> screenCls = Class.forName("com.tensura_tno.client.screen.ContractLittleFoxScreen");
         Object currentScreen = minecraftClass.getField("screen").get(minecraft);
         if (screenCls.isInstance(currentScreen)) {
            screenCls.getMethod("updatePayload", ContractLittleFoxPackets.OpenPanelPayload.class).invoke(currentScreen, payload);
            return;
         }

         Object screen = screenCls.getConstructor(ContractLittleFoxPackets.OpenPanelPayload.class).newInstance(payload);
         minecraftClass.getMethod("setScreen", Class.forName("net.minecraft.client.gui.screens.Screen")).invoke(minecraft, screen);
      } catch (Exception var6) {
      }
   }

   public static void openPanel(ServerPlayer player) {
      ContractLittleFoxPackets.OpenPanelPayload payload = buildPanelPayload(player);
      NetworkManager.sendToPlayer(player, payload);
   }

   private static void handleAction(ContractLittleFoxPackets.ActionPayload payload, PacketContext ctx) {
      if (ctx.getPlayer() instanceof ServerPlayer player) {
         Skills skills = SkillAPI.getSkillsFrom(player);
         Optional<ManasSkillInstance> opt = skills.getSkill((ManasSkill)TensuraTNOSkills.CONTRACT_LITTLE_FOX.get());
         if (!opt.isEmpty()) {
            switch (payload.action) {
               case 0:
                  doSummon(player);
                  break;
               case 1:
                  doRecall(player);
            }

            openPanel(player);
         }
      }
   }

   private static void doSummon(ServerPlayer player) {
      if (player.level() instanceof ServerLevel level) {
         FoxSpiritEntity existing = findActiveFox(player);
         if (existing != null && existing.isAlive()) {
            player.displayClientMessage(Component.translatable("tensura_tno.contract_little_fox.already_summoned"), true);
         } else {
            long nowTick = player.level().getGameTime();
            CompoundTag rootForCd = getRoot(player);
            if (rootForCd.contains("SummonCooldownUntilTick", 4)) {
               long until = rootForCd.getLong("SummonCooldownUntilTick");
               if (nowTick < until) {
                  return;
               }

               rootForCd.remove("SummonCooldownUntilTick");
               saveRoot(player, rootForCd);
            }

            FoxSpiritEntity fox = (FoxSpiritEntity)((EntityType)TensuraTNOEntities.FOX_SPIRIT.get()).create(level);
            if (fox != null) {
               Vec3 look = player.getLookAngle();
               double sx = player.getX() + look.x;
               double sy = player.getY();
               double sz = player.getZ() + look.z;
               fox.moveTo(sx, sy, sz, player.getYRot(), 0.0F);
               fox.setSummonerUUID(player.getUUID());
               addTrustedFallback(fox, player.getUUID());
               level.addFreshEntity(fox);
               CompoundTag root = getRoot(player);
               if (root.contains("RecalledStats")) {
                  applyRecalledStats(fox, root.getCompound("RecalledStats"));
                  root.remove("RecalledStats");
               } else {
                  FoxSpiritEntity.applyHalfStatsFrom(fox, player);
               }

               root.putUUID("FoxUUID", fox.getUUID());
               root.remove("SummonCooldownUntilTick");
               saveRoot(player, root);
               level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, 1.6F);
               openPanel(player);
            }
         }
      }
   }

   private static void addTrustedFallback(FoxSpiritEntity fox, UUID summonerId) {
      try {
         Method m = fox.getClass().getSuperclass().getDeclaredMethod("addTrustedUUID", UUID.class);
         m.setAccessible(true);
         m.invoke(fox, summonerId);
      } catch (Exception var3) {
      }
   }

   private static void doRecall(ServerPlayer player) {
      FoxSpiritEntity fox = findActiveFox(player);
      if (fox != null && fox.isAlive()) {
         CompoundTag stats = snapshotStats(fox);
         CompoundTag root = getRoot(player);
         root.put("RecalledStats", stats);
         root.remove("FoxUUID");
         saveRoot(player, root);
         fox.discard();
         ServerLevel level = (ServerLevel)player.level();
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.6F, 1.4F);
      } else {
         player.displayClientMessage(Component.translatable("tensura_tno.contract_little_fox.no_summoned"), true);
      }
   }

   private static CompoundTag snapshotStats(FoxSpiritEntity fox) {
      IExistence existence = TensuraStorages.getExistenceFrom(fox);
      CompoundTag t = new CompoundTag();
      t.putFloat("HP", fox.getHealth());
      t.putLong("RecalledTime", fox.level().getGameTime());
      AttributeInstance maxHpAttr = fox.getAttribute(Attributes.MAX_HEALTH);
      t.putFloat("MaxHP", maxHpAttr != null ? (float)maxHpAttr.getBaseValue() : fox.getMaxHealth());
      t.putDouble("Aura", existence.getAura());
      t.putDouble("MaxAura", EnergyHelper.getBaseMaxAura(fox));
      t.putDouble("Magic", existence.getMagicule());
      t.putDouble("MaxMagic", EnergyHelper.getBaseMaxMagicule(fox));
      t.putDouble("SHP", existence.getSpiritualHealth());
      AttributeInstance maxShpAttr = fox.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      t.putDouble("MaxSHP", maxShpAttr != null ? maxShpAttr.getBaseValue() : 0.0);
      AttributeInstance atkAttr = fox.getAttribute(Attributes.ATTACK_DAMAGE);
      t.putDouble("Atk", atkAttr != null ? atkAttr.getBaseValue() : 0.0);
      CompoundTag skillsNbt = new CompoundTag();
      SkillAPI.getSkillsFrom(fox).save(skillsNbt);
      t.put("TnoLearnedSkills", skillsNbt);
      BetterSubsFoxCompat.saveExpandedSkillStorage(fox, t);
      return t;
   }

   private static void applyRecalledStats(FoxSpiritEntity fox, CompoundTag t) {
      EnergyHelper.setMaxAura(fox, t.getDouble("MaxAura"));
      EnergyHelper.setMaxMagicule(fox, t.getDouble("MaxMagic"));
      AttributeInstance maxHp = fox.getAttribute(Attributes.MAX_HEALTH);
      if (maxHp != null) {
         maxHp.setBaseValue(Math.max(1.0, t.getDouble("MaxHP")));
      }

      AttributeInstance maxShp = fox.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      if (maxShp != null) {
         maxShp.setBaseValue(t.getDouble("MaxSHP"));
      }

      AttributeInstance atk = fox.getAttribute(Attributes.ATTACK_DAMAGE);
      if (atk != null) {
         atk.setBaseValue(t.getDouble("Atk"));
      }

      IExistence existence = TensuraStorages.getExistenceFrom(fox);
      double savedAura = t.getDouble("Aura");
      double maxAuraVal = t.getDouble("MaxAura");
      double savedMagic = t.getDouble("Magic");
      double maxMagicVal = t.getDouble("MaxMagic");
      existence.setSpiritualHealth(t.getDouble("SHP"));
      float savedHp = Math.max(1.0F, t.getFloat("HP"));
      float maxHpVal = fox.getMaxHealth();
      if (t.contains("RecalledTime")) {
         long recalledTime = t.getLong("RecalledTime");
         long now = fox.level().getGameTime();
         long elapsed = Math.max(0L, now - recalledTime);
         float secondsElapsed = (float)elapsed / 20.0F;
         float regenRate = 0.02F * secondsElapsed;
         savedHp = Math.min(maxHpVal, savedHp + maxHpVal * regenRate);
         savedAura = Math.min(maxAuraVal, savedAura + maxAuraVal * regenRate);
         savedMagic = Math.min(maxMagicVal, savedMagic + maxMagicVal * regenRate);
      }

      fox.setHealth(savedHp);
      existence.setAura(savedAura);
      existence.setMagicule(savedMagic);
      existence.markDirty();
      if (t.contains("TnoLearnedSkills", 10)) {
         CompoundTag skillData = t.getCompound("TnoLearnedSkills").copy();
         skillData.putBoolean("resetExistingData", true);
         SkillAPI.getSkillsFrom(fox).load(skillData);
      }

      BetterSubsFoxCompat.loadExpandedSkillStorage(fox, t);
   }

   public static void onPlayerDeath(ServerPlayer player) {
      FoxSpiritEntity fox = findActiveFox(player);
      if (fox != null) {
         fox.discard();
      }

      clearAllState(player);
   }

   public static void onSkillReset(ServerPlayer player) {
      FoxSpiritEntity fox = findActiveFox(player);
      if (fox != null) {
         fox.discard();
      }

      clearAllState(player);
   }

   public static void onFoxSpiritDeath(FoxSpiritEntity fox) {
      UUID summonerId = fox.getSummonerUUID().orElse(null);
      if (summonerId != null) {
         if (fox.level() instanceof ServerLevel level) {
            ServerPlayer owner = level.getServer().getPlayerList().getPlayer(summonerId);
            if (owner != null) {
               clearSummonProgress(owner);
               applyDeathSummonCooldown(owner);
            }
         }
      }
   }

   private static void clearAllState(ServerPlayer player) {
      CompoundTag root = getRoot(player);
      root.remove("FoxUUID");
      root.remove("RecalledStats");
      root.remove("SummonCooldownUntilTick");
      saveRoot(player, root);
   }

   private static void clearSummonProgress(ServerPlayer player) {
      CompoundTag root = getRoot(player);
      root.remove("FoxUUID");
      root.remove("RecalledStats");
      saveRoot(player, root);
   }

   private static void applyDeathSummonCooldown(ServerPlayer player) {
      CompoundTag root = getRoot(player);
      long until = player.level().getGameTime() + 1200L;
      root.putLong("SummonCooldownUntilTick", until);
      saveRoot(player, root);
   }

   @Nullable
   public static FoxSpiritEntity findActiveFox(ServerPlayer player) {
      CompoundTag root = getRoot(player);
      if (!root.hasUUID("FoxUUID")) {
         return null;
      }

      UUID foxId = root.getUUID("FoxUUID");
      if (player.level() instanceof ServerLevel level && level.getEntity(foxId) instanceof FoxSpiritEntity fox && fox.isAlive()) {
         UUID expected = fox.getSummonerUUID().orElse(null);
         if (Objects.equals(expected, player.getUUID())) {
            return fox;
         }
      }

      root.remove("FoxUUID");
      saveRoot(player, root);
      return null;
   }

   private static int computeSummonCooldownSecondsRemaining(ServerPlayer player, CompoundTag root) {
      if (!root.contains("SummonCooldownUntilTick", 4)) {
         return 0;
      }

      long until = root.getLong("SummonCooldownUntilTick");
      long now = player.level().getGameTime();
      return now >= until ? 0 : (int)Math.ceil((until - now) / 20.0);
   }

   private static ContractLittleFoxPackets.OpenPanelPayload buildPanelPayload(ServerPlayer player) {
      FoxSpiritEntity fox = findActiveFox(player);
      CompoundTag root = getRoot(player);
      int cooldownSec = computeSummonCooldownSecondsRemaining(player, root);
      IExistence playerExistence = TensuraStorages.getExistenceFrom(player);
      float pHp = player.getHealth();
      float pMaxHp = player.getMaxHealth();
      float pAura = (float)playerExistence.getAura();
      float pMaxAura = (float)EnergyHelper.getBaseMaxAura(player);
      float pMagic = (float)playerExistence.getMagicule();
      float pMaxMagic = (float)EnergyHelper.getBaseMaxMagicule(player);
      if (fox != null && fox.isAlive()) {
         IExistence fe = TensuraStorages.getExistenceFrom(fox);
         return new ContractLittleFoxPackets.OpenPanelPayload(
            1,
            fox.getHealth(),
            fox.getMaxHealth(),
            (float)fe.getAura(),
            (float)EnergyHelper.getBaseMaxAura(fox),
            (float)fe.getMagicule(),
            (float)EnergyHelper.getBaseMaxMagicule(fox),
            pHp,
            pMaxHp,
            pAura,
            pMaxAura,
            pMagic,
            pMaxMagic,
            fox.getUUID(),
            0
         );
      }

      if (root.contains("RecalledStats")) {
         CompoundTag s = root.getCompound("RecalledStats");
         float recalledHp = s.getFloat("HP");
         float recalledMaxHp = s.getFloat("MaxHP");
         float recalledAura = (float)s.getDouble("Aura");
         float recalledMaxAura = (float)s.getDouble("MaxAura");
         float recalledMagic = (float)s.getDouble("Magic");
         float recalledMaxMagic = (float)s.getDouble("MaxMagic");
         if (s.contains("RecalledTime")) {
            long recalledTime = s.getLong("RecalledTime");
            long now = player.level().getGameTime();
            long elapsed = Math.max(0L, now - recalledTime);
            float secondsElapsed = (float)elapsed / 20.0F;
            float regenRate = 0.02F * secondsElapsed;
            recalledHp = Math.min(recalledMaxHp, recalledHp + recalledMaxHp * regenRate);
            recalledAura = Math.min(recalledMaxAura, recalledAura + recalledMaxAura * regenRate);
            recalledMagic = Math.min(recalledMaxMagic, recalledMagic + recalledMaxMagic * regenRate);
         }

         return new ContractLittleFoxPackets.OpenPanelPayload(
            2,
            recalledHp,
            recalledMaxHp,
            recalledAura,
            recalledMaxAura,
            recalledMagic,
            recalledMaxMagic,
            pHp,
            pMaxHp,
            pAura,
            pMaxAura,
            pMagic,
            pMaxMagic,
            new UUID(0L, 0L),
            cooldownSec
         );
      } else {
         return new ContractLittleFoxPackets.OpenPanelPayload(
            0,
            pMaxHp * 0.5F,
            pMaxHp * 0.5F,
            pMaxAura * 0.5F,
            pMaxAura * 0.5F,
            pMaxMagic * 0.5F,
            pMaxMagic * 0.5F,
            pHp,
            pMaxHp,
            pAura,
            pMaxAura,
            pMagic,
            pMaxMagic,
            new UUID(0L, 0L),
            cooldownSec
         );
      }
   }

   private static CompoundTag getRoot(ServerPlayer player) {
      return player.getPersistentData().getCompound("tensura_tno_fox_spirit");
   }

   private static void saveRoot(ServerPlayer player, CompoundTag tag) {
      player.getPersistentData().put("tensura_tno_fox_spirit", tag);
   }

   private ContractLittleFoxPackets() {
   }

   public record ActionPayload(int action) implements CustomPacketPayload {
      public static final int SUMMON = 0;
      public static final int RECALL = 1;
      public static final Type<ContractLittleFoxPackets.ActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_action"));
      public static final StreamCodec<RegistryFriendlyByteBuf, ContractLittleFoxPackets.ActionPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.VAR_INT, ContractLittleFoxPackets.ActionPayload::action, ContractLittleFoxPackets.ActionPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record OpenPanelPayload(
      int state,
      float foxHp,
      float foxMaxHp,
      float foxAura,
      float foxMaxAura,
      float foxMagic,
      float foxMaxMagic,
      float playerHp,
      float playerMaxHp,
      float playerAura,
      float playerMaxAura,
      float playerMagic,
      float playerMaxMagic,
      UUID foxUuid,
      int summonCooldownSeconds
   ) implements CustomPacketPayload {
      public static final Type<ContractLittleFoxPackets.OpenPanelPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_open_panel")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ContractLittleFoxPackets.OpenPanelPayload> STREAM_CODEC = StreamCodec.of(
         (buf, p) -> {
            buf.writeVarInt(p.state);
            buf.writeFloat(p.foxHp);
            buf.writeFloat(p.foxMaxHp);
            buf.writeFloat(p.foxAura);
            buf.writeFloat(p.foxMaxAura);
            buf.writeFloat(p.foxMagic);
            buf.writeFloat(p.foxMaxMagic);
            buf.writeFloat(p.playerHp);
            buf.writeFloat(p.playerMaxHp);
            buf.writeFloat(p.playerAura);
            buf.writeFloat(p.playerMaxAura);
            buf.writeFloat(p.playerMagic);
            buf.writeFloat(p.playerMaxMagic);
            UUIDUtil.STREAM_CODEC.encode(buf, p.foxUuid == null ? new UUID(0L, 0L) : p.foxUuid);
            buf.writeVarInt(p.summonCooldownSeconds);
         },
         buf -> new ContractLittleFoxPackets.OpenPanelPayload(
            buf.readVarInt(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat(),
            (UUID)UUIDUtil.STREAM_CODEC.decode(buf),
            buf.readVarInt()
         )
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
