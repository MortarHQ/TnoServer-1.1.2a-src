package com.mortal_cultivation.ability.skill.unique;

import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class GreatSageEvolutionTracker {
   private static final String NBT_KEY = "GreatSageEvolution";
   private static final String TNT_COUNT_KEY = "TntDetonated";
   private static final String APPLE_COUNT_KEY = "ApplesEaten";
   private static final String WALK_DISTANCE_KEY = "WalkDistance";
   private static final String LAST_POS_X_KEY = "LastPosX";
   private static final String LAST_POS_Y_KEY = "LastPosY";
   private static final String LAST_POS_Z_KEY = "LastPosZ";
   public static final int REQUIRED_TNT = 100;
   public static final int REQUIRED_GOLDEN_APPLES = 20;
   public static final int REQUIRED_WALK_DISTANCE = 500000;

   @SubscribeEvent
   public static void onExplosion(Detonate event) {
      if (!event.getLevel().isClientSide()) {
         Explosion explosion = event.getExplosion();
         if (explosion.getIndirectSourceEntity() instanceof Player player) {
            incrementTntCount(player);
         } else {
            Vec3 pos = explosion.center();

            for (Player player : event.getLevel()
               .getEntitiesOfClass(Player.class, new AABB(pos.x - 10.0, pos.y - 10.0, pos.z - 10.0, pos.x + 10.0, pos.y + 10.0, pos.z + 10.0))) {
               if (hasMischiefSkill(player)) {
                  incrementTntCount(player);
                  break;
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onItemUseFinish(Finish event) {
      if (event.getEntity() instanceof Player player) {
         if (!player.level().isClientSide()) {
            ItemStack item = event.getItem();
            if ((item.is(Items.GOLDEN_APPLE) || item.is(Items.ENCHANTED_GOLDEN_APPLE)) && hasMischiefSkill(player)) {
               incrementGoldenAppleCount(player);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide()) {
         if (event.getEntity() instanceof ServerPlayer player) {
            if (player.tickCount % 20 == 0) {
               if (hasMischiefSkill(player)) {
                  CompoundTag data = getEvolutionData(player);
                  double lastX = data.getDouble("LastPosX");
                  double lastY = data.getDouble("LastPosY");
                  double lastZ = data.getDouble("LastPosZ");
                  double currentX = player.getX();
                  double currentY = player.getY();
                  double currentZ = player.getZ();
                  if (data.contains("LastPosX")) {
                     double distance = Math.sqrt(Math.pow(currentX - lastX, 2.0) + Math.pow(currentY - lastY, 2.0) + Math.pow(currentZ - lastZ, 2.0));
                     if (distance > 0.0 && distance < 50.0) {
                        int currentWalk = data.getInt("WalkDistance");
                        data.putInt("WalkDistance", currentWalk + (int)distance);
                     }
                  }

                  data.putDouble("LastPosX", currentX);
                  data.putDouble("LastPosY", currentY);
                  data.putDouble("LastPosZ", currentZ);
                  saveEvolutionData(player, data);
                  if (player.tickCount % 6000 == 0) {
                     checkEvolutionConditions(player);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         CompoundTag data = getEvolutionData(player);
         data.remove("LastPosX");
         data.remove("LastPosY");
         data.remove("LastPosZ");
         saveEvolutionData(player, data);
      }
   }

   private static void incrementTntCount(Player player) {
      CompoundTag data = getEvolutionData(player);
      int count = data.getInt("TntDetonated") + 1;
      data.putInt("TntDetonated", count);
      saveEvolutionData(player, data);
      if (count == 100) {
         checkEvolutionConditions(player);
      }
   }

   private static void incrementGoldenAppleCount(Player player) {
      CompoundTag data = getEvolutionData(player);
      int count = data.getInt("ApplesEaten") + 1;
      data.putInt("ApplesEaten", count);
      saveEvolutionData(player, data);
      if (count == 20) {
         checkEvolutionConditions(player);
      }
   }

   public static void checkEvolutionConditions(Player player) {
      if (hasMischiefSkill(player)) {
         SkillStorage skills = SkillAPI.getSkillsFrom(player);
         Optional<ManasSkillInstance> mischiefOpt = skills.getSkill((ManasSkill)CultivationSkills.MISCHIEF.get());
         if (!mischiefOpt.isEmpty()) {
            ManasSkillInstance mischiefInstance = mischiefOpt.get();
            if (!(mischiefInstance.getMastery() < 1000.0)) {
               CompoundTag data = getEvolutionData(player);
               int tntCount = data.getInt("TntDetonated");
               int goldenAppleCount = data.getInt("ApplesEaten");
               int walkDistance = data.getInt("WalkDistance");
               if (tntCount >= 100 && goldenAppleCount >= 20 && walkDistance >= 500000) {
                  evolveToGreatSage(player);
               }
            }
         }
      }
   }

   public static boolean canEvolve(Player player) {
      SkillStorage skills = SkillAPI.getSkillsFrom(player);
      Optional<ManasSkillInstance> mischiefOpt = skills.getSkill((ManasSkill)CultivationSkills.MISCHIEF.get());
      if (mischiefOpt.isEmpty()) {
         return false;
      }

      if (mischiefOpt.get().getMastery() < 1000.0) {
         return false;
      }

      CompoundTag data = getEvolutionData(player);
      return data.getInt("TntDetonated") >= 100 && data.getInt("ApplesEaten") >= 20 && data.getInt("WalkDistance") >= 500000;
   }

   public static boolean evolveToGreatSage(Player player) {
      SkillStorage skills = SkillAPI.getSkillsFrom(player);
      Optional<ManasSkillInstance> mischiefOpt = skills.getSkill((ManasSkill)CultivationSkills.MISCHIEF.get());
      if (mischiefOpt.isEmpty()) {
         player.displayClientMessage(Component.translatable("mortal_cultivation.evolution.great_sage.not_ready").withStyle(ChatFormatting.RED), false);
         return false;
      }

      ManasSkillInstance mischiefInstance = mischiefOpt.get();
      if (mischiefInstance.getMastery() < 1000.0) {
         player.displayClientMessage(Component.translatable("mortal_cultivation.evolution.great_sage.need_mastery").withStyle(ChatFormatting.RED), false);
         return false;
      }

      CompoundTag data = getEvolutionData(player);
      int tntCount = data.getInt("TntDetonated");
      int goldenAppleCount = data.getInt("ApplesEaten");
      int walkDistance = data.getInt("WalkDistance");
      if (tntCount >= 100 && goldenAppleCount >= 20 && walkDistance >= 500000) {
         skills.forgetSkill((ManasSkill)CultivationSkills.MISCHIEF.get());
         ManasSkillInstance greatSageInstance = new TensuraSkillInstance((ManasSkill)CultivationSkills.GREAT_SAGE.get());
         greatSageInstance.setMastery(0.0);
         skills.learnSkill(greatSageInstance);
         player.getPersistentData().remove("GreatSageEvolution");
         player.displayClientMessage(
            Component.translatable("mortal_cultivation.evolution.great_sage.evolved").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}),
            false
         );
         if (player.level() instanceof ServerLevel level) {
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.TOTEM_OF_UNDYING, 5.0);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
         }

         return true;
      } else {
         player.displayClientMessage(Component.translatable("mortal_cultivation.evolution.great_sage.not_ready").withStyle(ChatFormatting.RED), false);
         return false;
      }
   }

   public static int[] getEvolutionProgress(Player player) {
      CompoundTag data = getEvolutionData(player);
      return new int[]{data.getInt("TntDetonated"), data.getInt("ApplesEaten"), data.getInt("WalkDistance")};
   }

   private static boolean hasMischiefSkill(Player player) {
      SkillStorage skills = SkillAPI.getSkillsFrom(player);
      return skills.getSkill((ManasSkill)CultivationSkills.MISCHIEF.get()).isPresent();
   }

   private static CompoundTag getEvolutionData(Player player) {
      CompoundTag persistentData = player.getPersistentData();
      if (!persistentData.contains("GreatSageEvolution")) {
         persistentData.put("GreatSageEvolution", new CompoundTag());
      }

      return persistentData.getCompound("GreatSageEvolution");
   }

   private static void saveEvolutionData(Player player, CompoundTag data) {
      player.getPersistentData().put("GreatSageEvolution", data);
   }
}
