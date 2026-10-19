package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.ability.skill.ultimate.MammonCasinoUltimateSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.utils.Env;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record GreedCasinoGamblePayload(int game, int bet) implements CustomPacketPayload {
   private static final MoreSkillsConfigs.MammonCasino CONFIG = MoreSkillsConfigs.GRAND.mammonCasino;
   private static List<Item> RANDOM_ITEM_POOL;
   public static final Type<GreedCasinoGamblePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "greed_casino_gamble"));
   public static final StreamCodec<FriendlyByteBuf, GreedCasinoGamblePayload> STREAM_CODEC = CustomPacketPayload.codec(
      GreedCasinoGamblePayload::encode, GreedCasinoGamblePayload::new
   );

   public GreedCasinoGamblePayload(FriendlyByteBuf buf) {
      this(buf.readVarInt(), buf.readVarInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeVarInt(this.game);
      buf.writeVarInt(this.bet);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               ServerPlayer player = (ServerPlayer)context.getPlayer();
               if (player != null) {
                  if (MammonCasinoUltimateSkill.hasSkill(player)) {
                     int normalizedBet = normalizeBet(this.bet);
                     int normalizedGame = Math.max(0, Math.min(2, this.game));
                     int chips = MammonCasinoUltimateSkill.getChips(player);
                     if (chips < normalizedBet) {
                        sendResult(player, false, "Not enough chips.", 0, 0, "");
                     } else {
                        MammonCasinoUltimateSkill.addChips(player, -normalizedBet);
                        int pLose = pct(loseChance(normalizedBet));
                        int pUlt = pct(ultimateChance(normalizedBet));
                        int pUnique = pct(uniqueChance(normalizedBet));
                        int pExtra = pct(extraChance(normalizedBet));
                        int pCommon = pct(commonChance(normalizedBet));
                        int pTemp = pct(tempChance(normalizedBet));
                        int pEP = pct(epChance(normalizedBet));
                        int pMP = pct(mpChance(normalizedBet));
                        double rewardMult = gameRewardMultiplier(normalizedGame);
                        int roll = player.getRandom().nextInt(10000);
                        int cursor = 0;
                        if (roll < (cursor = cursor + pLose)) {
                           sendResult(player, false, "LOSS", 0, 0, "");
                        } else if (roll < (cursor = cursor + pUlt)) {
                           GreedCasinoGamblePayload.SkillReward reward = giveRandomSkill(player, SkillType.ULTIMATE, false, -1L);
                           if (reward.ok()) {
                              sendResult(player, true, "JACKPOT ULTIMATE", 0, 0, reward.name());
                           } else {
                              int refund = Math.max(
                                 (Integer)CONFIG.ultimatePoolFailMinimumRefund.get(), normalizedBet / (Integer)CONFIG.ultimatePoolFailRefundDivisor.get()
                              );
                              MammonCasinoUltimateSkill.addChips(player, refund);
                              sendResult(player, false, "NO ULTIMATE POOL -> +" + refund + " chips", 0, 0, "");
                           }
                        } else if (roll < (cursor = cursor + pUnique)) {
                           GreedCasinoGamblePayload.SkillReward reward = giveRandomSkill(player, SkillType.UNIQUE, false, -1L);
                           if (reward.ok()) {
                              sendResult(player, true, "UNIQUE", 0, 0, reward.name());
                           } else {
                              int ep = capInt(Math.round(epAmount(normalizedBet) * (Double)CONFIG.uniquePoolFailEpMultiplier.get() * rewardMult));
                              addEP(player, ep);
                              sendResult(player, true, "EP +" + ep, ep, 0, "");
                           }
                        } else if (roll < (cursor = cursor + pExtra)) {
                           GreedCasinoGamblePayload.SkillReward reward = giveRandomSkill(player, SkillType.EXTRA, false, -1L);
                           if (reward.ok()) {
                              sendResult(player, true, "EXTRA", 0, 0, reward.name());
                           } else {
                              int mp = capInt(Math.round(mpAmount(normalizedBet) * (Double)CONFIG.extraPoolFailMpMultiplier.get() * rewardMult));
                              addMP(player, mp);
                              sendResult(player, true, "MP +" + mp, 0, mp, "");
                           }
                        } else if (roll < (cursor = cursor + pCommon)) {
                           SkillType type = player.getRandom().nextBoolean() ? SkillType.COMMON : SkillType.INTRINSIC;
                           GreedCasinoGamblePayload.SkillReward reward = giveRandomSkill(player, type, false, -1L);
                           if (reward.ok()) {
                              sendResult(player, true, type.name(), 0, 0, reward.name());
                           } else {
                              int refund = Math.max(
                                 (Integer)CONFIG.commonPoolFailMinimumRefund.get(), normalizedBet / (Integer)CONFIG.commonPoolFailRefundDivisor.get()
                              );
                              MammonCasinoUltimateSkill.addChips(player, refund);
                              sendResult(player, false, "NO " + type.name() + " POOL -> +" + refund + " chips", 0, 0, "");
                           }
                        } else if (roll < (cursor = cursor + pTemp)) {
                           SkillType type = rollTemporarySkillType(player);
                           long until = player.level().getGameTime() + ((Integer)CONFIG.temporarySkillDurationTicks.get()).intValue();
                           GreedCasinoGamblePayload.SkillReward reward = giveRandomSkill(player, type, true, until);
                           if (reward.ok()) {
                              sendResult(player, true, "TEMP " + type.name(), 0, 0, reward.name());
                           } else {
                              int refund = Math.max(
                                 (Integer)CONFIG.tempPoolFailMinimumRefund.get(), normalizedBet / (Integer)CONFIG.tempPoolFailRefundDivisor.get()
                              );
                              MammonCasinoUltimateSkill.addChips(player, refund);
                              sendResult(player, false, "NO TEMP POOL -> +" + refund + " chips", 0, 0, "");
                           }
                        } else if (roll < (cursor = cursor + pEP)) {
                           double randomMult = (Double)CONFIG.epRewardRandomMinimumMultiplier.get()
                              + player.getRandom().nextDouble() * (Double)CONFIG.epRewardRandomExtraMultiplier.get();
                           int ep = capInt(Math.round(epAmount(normalizedBet) * rewardMult * randomMult));
                           addEP(player, ep);
                           sendResult(player, true, "EP +" + ep, ep, 0, "");
                        } else if (roll < (cursor = cursor + pMP)) {
                           double randomMult = (Double)CONFIG.mpRewardRandomMinimumMultiplier.get()
                              + player.getRandom().nextDouble() * (Double)CONFIG.mpRewardRandomExtraMultiplier.get();
                           int mp = capInt(Math.round(mpAmount(normalizedBet) * rewardMult * randomMult));
                           addMP(player, mp);
                           sendResult(player, true, "MP +" + mp, 0, mp, "");
                        } else {
                           ItemStack reward = rollRandomItem(player, normalizedBet, normalizedGame);
                           if (reward.isEmpty()) {
                              int chipsBack = itemChipRefund(normalizedBet, normalizedGame);
                              MammonCasinoUltimateSkill.addChips(player, chipsBack);
                              sendResult(player, true, "NO ITEM POOL -> +" + chipsBack + " chips", 0, 0, "");
                           } else {
                              giveItem(player, reward);
                              sendResult(player, true, "ITEM " + reward.getHoverName().getString() + " x" + reward.getCount(), 0, 0, "");
                           }
                        }
                     }
                  }
               }
            }
         );
      }
   }

   @NotNull
   public Type<GreedCasinoGamblePayload> type() {
      return TYPE;
   }

   private static void sendResult(ServerPlayer player, boolean ok, String title, int epGain, int mpGain, String skillName) {
      NetworkManager.sendToPlayer(
         player, new GreedCasinoResultPayload(ok, title, MammonCasinoUltimateSkill.getChips(player), epGain, mpGain, skillName == null ? "" : skillName)
      );
   }

   private static int normalizeBet(int bet) {
      int small = (Integer)CONFIG.smallBet.get();
      int medium = (Integer)CONFIG.mediumBet.get();
      int large = (Integer)CONFIG.largeBet.get();
      if (bet <= small) {
         return small;
      } else {
         return bet <= medium ? medium : large;
      }
   }

   private static int pct(double chance) {
      return Math.max(0, Math.min(10000, (int)Math.round(chance * 100.0)));
   }

   private static boolean isSmall(int bet) {
      return bet == (Integer)CONFIG.smallBet.get();
   }

   private static boolean isMedium(int bet) {
      return bet == (Integer)CONFIG.mediumBet.get();
   }

   private static double loseChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallLoseChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumLoseChance.get() : (Double)CONFIG.largeLoseChance.get();
      }
   }

   private static double ultimateChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallUltimateChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumUltimateChance.get() : (Double)CONFIG.largeUltimateChance.get();
      }
   }

   private static double uniqueChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallUniqueChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumUniqueChance.get() : (Double)CONFIG.largeUniqueChance.get();
      }
   }

   private static double extraChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallExtraChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumExtraChance.get() : (Double)CONFIG.largeExtraChance.get();
      }
   }

   private static double commonChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallCommonChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumCommonChance.get() : (Double)CONFIG.largeCommonChance.get();
      }
   }

   private static double tempChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallTempChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumTempChance.get() : (Double)CONFIG.largeTempChance.get();
      }
   }

   private static double epChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallEpChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumEpChance.get() : (Double)CONFIG.largeEpChance.get();
      }
   }

   private static double mpChance(int bet) {
      if (isSmall(bet)) {
         return (Double)CONFIG.smallMpChance.get();
      } else {
         return isMedium(bet) ? (Double)CONFIG.mediumMpChance.get() : (Double)CONFIG.largeMpChance.get();
      }
   }

   private static int epAmount(int bet) {
      if (isSmall(bet)) {
         return (Integer)CONFIG.smallEpAmount.get();
      } else {
         return isMedium(bet) ? (Integer)CONFIG.mediumEpAmount.get() : (Integer)CONFIG.largeEpAmount.get();
      }
   }

   private static int mpAmount(int bet) {
      if (isSmall(bet)) {
         return (Integer)CONFIG.smallMpAmount.get();
      } else {
         return isMedium(bet) ? (Integer)CONFIG.mediumMpAmount.get() : (Integer)CONFIG.largeMpAmount.get();
      }
   }

   private static double gameRewardMultiplier(int game) {
      if (game == 1) {
         return (Double)CONFIG.game1RewardMultiplier.get();
      } else {
         return game == 2 ? (Double)CONFIG.game2RewardMultiplier.get() : (Double)CONFIG.game0RewardMultiplier.get();
      }
   }

   private static int itemChipRefund(int bet, int game) {
      int base;
      if (isSmall(bet)) {
         base = (Integer)CONFIG.smallItemChipRefund.get();
      } else if (isMedium(bet)) {
         base = (Integer)CONFIG.mediumItemChipRefund.get();
      } else {
         base = (Integer)CONFIG.largeItemChipRefund.get();
      }

      if (game == 2) {
         base = (int)Math.round(base * (Double)CONFIG.game2ItemRefundMultiplier.get());
      } else if (game == 1) {
         base = (int)Math.round(base * (Double)CONFIG.game1ItemRefundMultiplier.get());
      }

      return Math.max(1, base);
   }

   private static ItemStack rollRandomItem(ServerPlayer player, int bet, int game) {
      List<Item> pool = getRandomItemPool();
      if (pool.isEmpty()) {
         return ItemStack.EMPTY;
      }

      Item item = pool.get(player.getRandom().nextInt(pool.size()));
      ItemStack stack = new ItemStack(item);
      if (stack.isEmpty()) {
         return ItemStack.EMPTY;
      }

      int max = Math.max(1, stack.getMaxStackSize());
      int amount = randomItemAmount(player, bet, game, max);
      stack.setCount(amount);
      return stack;
   }

   private static List<Item> getRandomItemPool() {
      if (RANDOM_ITEM_POOL != null) {
         return RANDOM_ITEM_POOL;
      }

      List<Item> pool = new ArrayList<>();

      for (Item item : BuiltInRegistries.ITEM) {
         ItemStack stack = new ItemStack(item);
         if (!stack.isEmpty()) {
            pool.add(item);
         }
      }

      RANDOM_ITEM_POOL = pool;
      return RANDOM_ITEM_POOL;
   }

   private static int randomItemAmount(ServerPlayer player, int bet, int game, int maxStackSize) {
      int base;
      if (isSmall(bet)) {
         base = 1;
      } else if (isMedium(bet)) {
         base = 1 + player.getRandom().nextInt(3);
      } else {
         base = 2 + player.getRandom().nextInt(7);
      }

      if (game == 1) {
         base = (int)Math.round(base * 1.25);
      } else if (game == 2) {
         base = (int)Math.round(base * 1.5);
      }

      return Math.max(1, Math.min(maxStackSize, base));
   }

   private static void giveItem(ServerPlayer player, ItemStack stack) {
      if (!stack.isEmpty()) {
         ItemStack copy = stack.copy();
         if (!player.getInventory().add(copy)) {
            player.drop(copy, false);
         }
      }
   }

   private static SkillType rollTemporarySkillType(ServerPlayer player) {
      int common = (Integer)CONFIG.tempCommonWeight.get();
      int extra = (Integer)CONFIG.tempExtraWeight.get();
      int unique = (Integer)CONFIG.tempUniqueWeight.get();
      int total = Math.max(1, common + extra + unique);
      int roll = player.getRandom().nextInt(total);
      if (roll < common) {
         return SkillType.COMMON;
      } else {
         return roll < common + extra ? SkillType.EXTRA : SkillType.UNIQUE;
      }
   }

   private static int capInt(long value) {
      if (value < 0L) {
         return 0;
      } else {
         return value > 2147483647L ? Integer.MAX_VALUE : (int)value;
      }
   }

   private static void addEP(ServerPlayer player, int totalEpGain) {
      double half = totalEpGain / 2.0;
      EnergyHelper.increaseMaxEP(player, half);
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      existence.setAura(Math.min(EnergyHelper.getMaxAura(player), existence.getAura() + half));
      existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(player), existence.getMagicule() + half));
      existence.markDirty();
   }

   private static void addMP(ServerPlayer player, int amount) {
      double newBase = EnergyHelper.getBaseMaxMagicule(player) + amount;
      EnergyHelper.setMaxMagicule(player, newBase);
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(player), existence.getMagicule() + amount));
      existence.markDirty();
   }

   private static GreedCasinoGamblePayload.SkillReward giveRandomSkill(ServerPlayer player, SkillType type, boolean temporary, long untilGameTime) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      List<ManasSkill> pool = new ArrayList<>();
      Registrar<ManasSkill> registrar = SkillAPI.getSkillRegistry();
      ManasSkill mammon = (ManasSkill)registrar.get(MammonCasinoUltimateSkill.ID);

      for (Entry<ResourceKey<ManasSkill>, ManasSkill> entry : registrar.entrySet()) {
         ManasSkill skill = entry.getValue();
         if (skill instanceof Skill tensuraSkill
            && tensuraSkill.getType() == type
            && !skills.getSkill(skill).isPresent()
            && skill != mammon
            && !registrar.delegate(skill.getRegistryName()).is(TensuraSkillTags.NO_PLUNDERING)) {
            pool.add(skill);
         }
      }

      if (pool.isEmpty()) {
         return new GreedCasinoGamblePayload.SkillReward(false, "");
      }

      ManasSkill picked = pool.get(player.getRandom().nextInt(pool.size()));
      Changeable<ManasSkill> changeable = Changeable.of(picked);
      if (((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(null, player, false, changeable).isFalse()) {
         return new GreedCasinoGamblePayload.SkillReward(false, "");
      }

      ManasSkill finalSkill = (ManasSkill)changeable.get();
      if (finalSkill != null && !skills.getSkill(finalSkill).isPresent()) {
         TensuraSkillInstance instance = new TensuraSkillInstance(finalSkill);
         instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         if (temporary) {
            instance.setRemoveTime((int)Math.min(2147483647L, untilGameTime));
            instance.getOrCreateTag().putBoolean("tms_temp", true);
            instance.getOrCreateTag().putLong("tms_temp_until", untilGameTime);
         }

         if (!SkillHelper.learnSkill(player, instance, instance.getRemoveTime())) {
            return new GreedCasinoGamblePayload.SkillReward(false, "");
         }

         skills.markDirty();
         return new GreedCasinoGamblePayload.SkillReward(true, finalSkill.getName().getString());
      } else {
         return new GreedCasinoGamblePayload.SkillReward(false, "");
      }
   }

   private record SkillReward(boolean ok, String name) {
   }
}
