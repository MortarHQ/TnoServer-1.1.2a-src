package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.limbo.IWantLimboData;
import com.github.wal_bos.moreskills.limbo.IWantLimboMenu;
import com.github.wal_bos.moreskills.limbo.IWantLimboMenuProvider;
import com.github.wal_bos.moreskills.limbo.IWantLimboRoom;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRevealPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRoomPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public final class IWantToBeJustLikeYouHandler {
   private IWantToBeJustLikeYouHandler() {
   }

   public static void init() {
      EntityEvents.DEATH_EVENT_FIRST.register(IWantToBeJustLikeYouHandler::onDeath);
      NeoForge.EVENT_BUS.register(IWantToBeJustLikeYouHandler.class);
   }

   private static EventResult onDeath(LivingEntity entity, DamageSource source) {
      if (entity instanceof ServerPlayer player) {
         if (IWantLimboData.isActive(player)) {
            player.setHealth(Math.max(1.0F, player.getMaxHealth() * 0.05F));
            player.invulnerableTime = Math.max(player.invulnerableTime, 80);
            return EventResult.interruptFalse();
         }

         Optional<ManasSkillInstance> iWant = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.I_WANT_TO_BE_JUST_LIKE_YOU.get());
         if (iWant.isEmpty()) {
            return EventResult.pass();
         }

         iWant.get().addMasteryPoint(player);
         if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return EventResult.pass();
         }

         LivingEntity killer = findKiller(player, source);
         if (killer == null || killer == player || !killer.isAlive()) {
            return EventResult.pass();
         }

         if (IWantLimboData.hasUsedKiller(player, killer)) {
            return EventResult.pass();
         }

         List<IWantLimboData.StoredPoolCard> pool = createPool(player, killer);
         if (pool.isEmpty()) {
            return EventResult.pass();
         }

         List<IWantLimboData.StoredCard> cards = drawCards(pool, player);
         if (cards.isEmpty()) {
            return EventResult.pass();
         }

         IWantLimboData.markKillerUsed(player, killer);
         IWantLimboData.set(
            player,
            killer.getUUID(),
            cards,
            pool,
            iWant.get().isMastered(player) ? 5 : 3,
            player.position(),
            player.level().dimension(),
            player.getYRot(),
            player.getXRot()
         );
         player.setHealth(Math.max(1.0F, player.getMaxHealth() * 0.05F));
         player.invulnerableTime = Math.max(player.invulnerableTime, (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.limboInvulnerabilityTicks.get());
         IWantLimboRoom.enter(player);
         sendRoom(player, true);
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.6F, 0.55F);
         return EventResult.interruptFalse();
      } else {
         return EventResult.pass();
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (IWantLimboData.isActive(player)) {
            player.removeAllEffects();
            player.fallDistance = 0.0F;
            player.setDeltaMovement(0.0, 0.0, 0.0);
            player.invulnerableTime = Math.max(player.invulnerableTime, 20);
            IWantLimboRoom.keepInside(player);
            if (player.tickCount % 10 == 0) {
               removeIntruders(player);
            }

            IWantLimboData.PendingResult pending = IWantLimboData.tickPending(player);
            if (pending.ready()) {
               finish(
                  player,
                  pending.success(),
                  Component.translatable(
                     pending.success()
                        ? "tensuramoreskills.skill.i_want_to_be_just_like_you.revive"
                        : "tensuramoreskills.skill.i_want_to_be_just_like_you.already"
                  )
               );
            } else {
               if (!IWantLimboData.hasPending(player) && !(player.containerMenu instanceof IWantLimboMenu)) {
                  int nearby = nearbyCard(player);
                  if (nearby >= 0) {
                     IWantLimboMenuProvider.open(player, nearby);
                  }
               }

               if (player.tickCount % 20 == 0) {
                  sendRoom(player, true);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onBreak(BreakEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (IWantLimboRoom.isRoomBlock(level, event.getPos())) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onExplosion(Detonate event) {
      if (event.getLevel() instanceof ServerLevel level) {
         event.getAffectedBlocks().removeIf(pos -> IWantLimboRoom.isRoomBlock(level, pos));
      }
   }

   @SubscribeEvent
   public static void onClone(Clone event) {
      if (event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
         IWantLimboData.copyPermanentData(oldPlayer, newPlayer);
         if (IWantLimboData.isActive(oldPlayer)) {
            IWantLimboData.clear(oldPlayer);
            IWantLimboData.clear(newPlayer);
         }
      }
   }

   public static void openCard(ServerPlayer player, int cardId) {
      if (IWantLimboData.isActive(player) && !IWantLimboData.hasPending(player)) {
         if (isValidCard(player, cardId)) {
            if (!(player.distanceToSqr(Vec3.atCenterOf(IWantLimboRoom.cardPos(player, cardId))) > 81.0)) {
               IWantLimboMenuProvider.open(player, cardId);
            }
         }
      }
   }

   public static void rerollCards(ServerPlayer player) {
      if (IWantLimboData.isActive(player) && !IWantLimboData.hasPending(player) && IWantLimboData.rerollsLeft(player) > 0) {
         List<IWantLimboData.StoredPoolCard> pool = IWantLimboData.pool(player);
         if (!pool.isEmpty()) {
            List<IWantLimboData.StoredCard> cards = drawCards(pool, player);
            if (!cards.isEmpty()) {
               IWantLimboData.consumeReroll(player);
               IWantLimboData.updateCards(player, cards);
               IWantLimboMenuProvider.open(player, -1);
               sendRoom(player, true);
               player.level()
                  .playSound(
                     null,
                     player.getX(),
                     player.getY(),
                     player.getZ(),
                     SoundEvents.AMETHYST_BLOCK_CHIME,
                     SoundSource.PLAYERS,
                     0.85F,
                     0.72F + player.getRandom().nextFloat() * 0.18F
                  );
            }
         }
      }
   }

   public static void selectCard(ServerPlayer player, int cardId) {
      if (IWantLimboData.isActive(player) && !IWantLimboData.hasPending(player)) {
         IWantLimboData.StoredCard selected = null;

         for (IWantLimboData.StoredCard card : IWantLimboData.cards(player)) {
            if (card.id() == cardId) {
               selected = card;
               break;
            }
         }

         if (selected != null) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(selected.skill());
            if (skill == null) {
               IWantLimboData.setPending(player, selected.skill(), (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.cardRevealTicks.get(), false);
               NetworkManager.sendToPlayer(player, new IWantLimboRevealPayload(selected.id(), selected.skill()));
            } else {
               Changeable<ManasSkill> changeable = Changeable.of(skill);
               if (((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(null, player, false, changeable).isFalse()) {
                  IWantLimboData.setPending(player, selected.skill(), (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.cardRevealTicks.get(), false);
                  NetworkManager.sendToPlayer(player, new IWantLimboRevealPayload(selected.id(), selected.skill()));
               } else {
                  ManasSkill learnedSkill = (ManasSkill)changeable.get();
                  boolean learned = SkillHelper.learnSkill(
                     player,
                     learnedSkill.createDefaultInstance(),
                     -1,
                     Component.translatable("tensuramoreskills.skill.i_want_to_be_just_like_you.learned", new Object[]{learnedSkill.getChatDisplayName(true)})
                        .withStyle(ChatFormatting.GOLD)
                  );
                  IWantLimboData.setPending(
                     player, learnedSkill.getRegistryName(), (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.cardRevealTicks.get(), learned
                  );
                  NetworkManager.sendToPlayer(player, new IWantLimboRevealPayload(selected.id(), learnedSkill.getRegistryName()));
                  player.level()
                     .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 0.75F);
               }
            }
         }
      }
   }

   private static void finish(ServerPlayer player, boolean success, Component message) {
      IWantLimboData.clear(player);
      sendRoom(player, false);
      player.closeContainer();
      teleportToRespawn(player);
      player.removeAllEffects();
      player.setHealth(
         Math.max(1.0F, player.getMaxHealth() * (float)((Double)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.reviveHealthRatio.get()).doubleValue())
      );
      player.invulnerableTime = Math.max(player.invulnerableTime, (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.reviveInvulnerabilityTicks.get());
      player.fallDistance = 0.0F;
      player.sendSystemMessage(message);
      player.level()
         .playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            success ? SoundEvents.PLAYER_LEVELUP : (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
            SoundSource.PLAYERS,
            1.0F,
            success ? 1.0F : 0.75F
         );
   }

   private static void teleportToRespawn(ServerPlayer player) {
      ServerLevel level = player.server.getLevel(player.getRespawnDimension());
      if (level == null) {
         level = player.server.overworld();
      }

      BlockPos pos = player.getRespawnPosition();
      if (pos == null) {
         pos = level.getSharedSpawnPos();
      }

      player.stopRiding();
      player.ejectPassengers();
      player.teleportTo(level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, player.getRespawnAngle(), 0.0F);
   }

   private static void removeIntruders(ServerPlayer owner) {
      ServerLevel level = owner.server.getLevel(IWantLimboData.limboDimension(owner));
      if (level != null) {
         for (Entity entity : level.getEntitiesOfClass(Entity.class, IWantLimboRoom.bounds(level), entityx -> entityx != owner && entityx.isAlive())) {
            if (!(
               entity instanceof LivingEntity living
                  && SkillAPI.getSkillsFrom(living).getSkill((ManasSkill)AllSkills.I_WANT_TO_BE_JUST_LIKE_YOU.get()).isPresent()
            )) {
               teleportIntruderToZeroSpawn(level, entity);
            }
         }
      }
   }

   private static void teleportIntruderToZeroSpawn(ServerLevel level, Entity entity) {
      int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, 0, 0) + 1;
      y = Math.max(level.getMinBuildHeight() + 2, Math.min(level.getMaxBuildHeight() - 2, y));
      entity.stopRiding();
      entity.ejectPassengers();
      entity.teleportTo(0.5, y, 0.5);
      entity.setDeltaMovement(0.0, 0.0, 0.0);
      entity.fallDistance = 0.0F;
   }

   private static void sendRoom(ServerPlayer player, boolean active) {
      ServerLevel level = player.server.getLevel(IWantLimboData.limboDimension(player));
      if (level == null) {
         level = player.serverLevel();
      }

      BlockPos center = IWantLimboRoom.center(level);
      List<IWantLimboRoomPayload.CardView> views = new ArrayList<>();
      if (active) {
         for (IWantLimboData.StoredCard card : IWantLimboData.cards(player)) {
            BlockPos pos = IWantLimboRoom.cardPos(level, card.id());
            views.add(new IWantLimboRoomPayload.CardView(card.id(), card.rarity(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
         }
      }

      NetworkManager.sendToPlayer(player, new IWantLimboRoomPayload(active, center.getX() + 0.5, center.getY() + 1.0, center.getZ() + 0.5, views));
   }

   private static int nearbyCard(ServerPlayer player) {
      for (IWantLimboData.StoredCard card : IWantLimboData.cards(player)) {
         if (player.distanceToSqr(Vec3.atCenterOf(IWantLimboRoom.cardPos(player, card.id()))) <= 16.0) {
            return card.id();
         }
      }

      return -1;
   }

   private static boolean isValidCard(ServerPlayer player, int cardId) {
      for (IWantLimboData.StoredCard card : IWantLimboData.cards(player)) {
         if (card.id() == cardId) {
            return true;
         }
      }

      return false;
   }

   private static LivingEntity findKiller(ServerPlayer player, DamageSource source) {
      if (source.getEntity() instanceof LivingEntity living) {
         return living;
      } else if (source.getDirectEntity() instanceof LivingEntity living) {
         return living;
      } else {
         LivingEntity killCredit = player.getKillCredit();
         if (killCredit != null) {
            return killCredit;
         }

         LivingEntity lastHurt = player.getLastHurtByMob();
         return lastHurt != null ? lastHurt : null;
      }
   }

   private static List<IWantLimboData.StoredPoolCard> createPool(ServerPlayer player, LivingEntity killer) {
      List<IWantLimboData.StoredPoolCard> weighted = new ArrayList<>();
      Set<ResourceLocation> seen = new HashSet<>();

      for (ManasSkillInstance targetInstance : SkillAPI.getSkillsFrom(killer).getLearnedSkills()) {
         if (targetInstance != null && !targetInstance.isTemporarySkill() && !(targetInstance.getMastery() < 0.0)) {
            ManasSkill skill = targetInstance.getSkill();
            if (skill != null && skill != AllSkills.I_WANT_TO_BE_JUST_LIKE_YOU.get()) {
               ResourceLocation id = skill.getRegistryName();
               if (id != null
                  && seen.add(id)
                  && !SkillAPI.getSkillsFrom(player).getSkill(skill).isPresent()
                  && !SkillAPI.getSkillRegistry().delegate(id).is(TensuraSkillTags.NO_PLUNDERING)) {
                  IWantLimboData.Rarity rarity = rarityOf(skill);
                  int cardWeight = weight(rarity);
                  if (cardWeight > 0) {
                     weighted.add(new IWantLimboData.StoredPoolCard(id, rarity, cardWeight));
                  }
               }
            }
         }
      }

      return weighted;
   }

   private static List<IWantLimboData.StoredCard> drawCards(List<IWantLimboData.StoredPoolCard> pool, ServerPlayer player) {
      List<IWantLimboData.StoredPoolCard> weighted = new ArrayList<>();

      for (IWantLimboData.StoredPoolCard card : pool) {
         int currentWeight = weight(card.rarity());
         if (currentWeight > 0) {
            weighted.add(new IWantLimboData.StoredPoolCard(card.skill(), card.rarity(), currentWeight));
         }
      }

      if (weighted.isEmpty()) {
         return List.of();
      }

      shuffle(weighted, player.getRandom());
      List<IWantLimboData.StoredCard> cards = new ArrayList<>();
      int maxCards = Math.max(1, (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.maxCards.get());

      while (!weighted.isEmpty() && cards.size() < maxCards) {
         IWantLimboData.StoredPoolCard pick = pullWeighted(weighted, player);
         if (pick == null) {
            break;
         }

         cards.add(new IWantLimboData.StoredCard(cards.size(), pick.skill(), pick.rarity()));
      }

      cards.sort(Comparator.comparingInt(IWantLimboData.StoredCard::id));
      return cards;
   }

   private static void shuffle(List<IWantLimboData.StoredPoolCard> list, RandomSource random) {
      for (int i = list.size() - 1; i > 0; i--) {
         int j = random.nextInt(i + 1);
         IWantLimboData.StoredPoolCard temp = list.get(i);
         list.set(i, list.get(j));
         list.set(j, temp);
      }
   }

   private static IWantLimboData.StoredPoolCard pullWeighted(List<IWantLimboData.StoredPoolCard> weighted, ServerPlayer player) {
      weighted.removeIf(cardx -> cardx.weight() <= 0);
      if (weighted.isEmpty()) {
         return null;
      }

      long total = 0L;

      for (IWantLimboData.StoredPoolCard card : weighted) {
         total += card.weight();
      }

      if (total <= 0L) {
         return null;
      }

      double roll = player.getRandom().nextDouble() * total;
      long cursor = 0L;

      for (int i = 0; i < weighted.size(); i++) {
         IWantLimboData.StoredPoolCard card = weighted.get(i);
         cursor += card.weight();
         if (roll < cursor) {
            weighted.remove(i);
            return card;
         }
      }

      return weighted.remove(weighted.size() - 1);
   }

   private static int weight(IWantLimboData.Rarity rarity) {
      return switch (rarity) {
         case BRONZE -> MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.commonWeight.get();
         case SILVER -> MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.extraIntrinsicWeight.get();
         case GOLD -> MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.uniqueWeight.get();
      };
   }

   private static IWantLimboData.Rarity rarityOf(ManasSkill skill) {
      if (skill instanceof Skill tensuraSkill) {
         String name = tensuraSkill.getClass().getName().toLowerCase(Locale.ROOT);
         if (name.contains(".unique.")) {
            return IWantLimboData.Rarity.GOLD;
         }

         if (name.contains(".extra.") || name.contains(".intrinsic.")) {
            return IWantLimboData.Rarity.SILVER;
         }
      }

      String name = skill.getClass().getName().toLowerCase(Locale.ROOT);
      if (name.contains(".unique.")) {
         return IWantLimboData.Rarity.GOLD;
      } else {
         return !name.contains(".extra.") && !name.contains(".intrinsic.") ? IWantLimboData.Rarity.BRONZE : IWantLimboData.Rarity.SILVER;
      }
   }
}
