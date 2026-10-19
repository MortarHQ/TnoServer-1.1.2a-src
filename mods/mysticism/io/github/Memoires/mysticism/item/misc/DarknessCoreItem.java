package io.github.Memoires.mysticism.item.misc;

import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.race.RaceHelper;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DarknessCoreItem extends Item {
   public DarknessCoreItem() {
      super(new Properties().arch$tab(TensuraCreativeTabs.MISCELLANEOUS).fireResistant().rarity(Rarity.EPIC));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
      list.add(Component.translatable("tooltip.mysticism.darkness_core").withStyle(ChatFormatting.DARK_PURPLE));
   }

   public boolean isFoil(ItemStack pStack) {
      return true;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity entity) {
      return 10000;
   }

   public UseAnim getUseAnimation(ItemStack pStack) {
      return UseAnim.BOW;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pHand) {
      ItemStack stack = player.getItemInHand(pHand);
      if (player.getCooldowns().isOnCooldown(stack.getItem()) && !player.hasInfiniteMaterials()) {
         return InteractionResultHolder.fail(stack);
      }

      player.startUsingItem(pHand);
      return InteractionResultHolder.consume(stack);
   }

   public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
      TensuraParticleHelper.addServerParticlesAroundSelf(pLivingEntity, ParticleTypes.ENCHANTED_HIT);
   }

   public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level level, @NotNull LivingEntity entity, int pTimeLeft) {
      int useTicks = this.getUseDuration(pStack, entity) - pTimeLeft;
      if (useTicks >= 10 && entity instanceof Player player) {
         if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, pStack);
            Races playerRace = RaceAPI.getRaceFrom(player);
            Optional<ManasRaceInstance> raceInstanceOpt = playerRace.getRace();
            if (raceInstanceOpt.isPresent()) {
               ManasRaceInstance raceInstance = raceInstanceOpt.get();
               if (raceInstance.getRace() == MysticismRaces.FALLEN_LESSER_ANGEL.get()) {
                  RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.LESSER_FALLEN.get(), true, true);
                  serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_evolution").withStyle(ChatFormatting.DARK_PURPLE));
               } else if (raceInstance.getRace() == MysticismRaces.FALLEN_GREATER_ANGEL.get()) {
                  RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.GREATER_FALLEN.get(), true, true);
                  serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_evolution").withStyle(ChatFormatting.DARK_PURPLE));
               } else if (raceInstance.getRace() == MysticismRaces.FALLEN_ARCH_ANGEL.get()) {
                  RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.ARCH_FALLEN.get(), true, true);
                  serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_evolution").withStyle(ChatFormatting.DARK_PURPLE));
               } else if (raceInstance.getRace() == MysticismRaces.FALLEN_CHERUB.get()) {
                  RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN_LORD.get(), true, true);
                  serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_evolution").withStyle(ChatFormatting.DARK_PURPLE));
               } else {
                  if (raceInstance.getRace() != MysticismRaces.FALLEN_SERAPH.get()) {
                     serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_evolution_fail").withStyle(ChatFormatting.RED));
                     return;
                  }

                  RaceHelper.evolveRace(serverPlayer, (ManasRace)MysticismRaces.FALLEN.get(), true, true);
                  serverPlayer.sendSystemMessage(Component.translatable("mysticism.fallen_evolution").withStyle(ChatFormatting.DARK_PURPLE));
               }
            }
         }

         if (!player.hasInfiniteMaterials()) {
            pStack.shrink(1);
         }

         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         if (existence.getAlignment().equals(Alignment.DEFAULT)) {
            existence.setAlignment(Alignment.MAJIN);
         }

         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.EXPLOSION_EMITTER, 0.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.SOLAR_FLASH.get(), 1.0);
      }
   }
}
