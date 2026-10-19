package com.mortal_cultivation.ability.skill.unique;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.block.entity.AlchemyFurnaceBlockEntity;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class AlchemyProdigySkill extends LegacySkill {
   private static final Set<BlockPos> acceleratedFurnaces = new HashSet<>();
   private static final Set<UUID> enabledPlayers = new HashSet<>();

   public AlchemyProdigySkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/unique/alchemy_prodigy.png");
   }

   @Override
   public double learningCost() {
      return 2000.0;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   public void applyPassiveEffect(LivingEntity entity, ManasSkillInstance instance) {
      if (!entity.level().isClientSide) {
         Level level = entity.level();
         double range = instance.isMastered(entity) ? 10.0 : 5.0;
         AABB searchArea = new AABB(
            entity.getX() - range, entity.getY() - range, entity.getZ() - range, entity.getX() + range, entity.getY() + range, entity.getZ() + range
         );

         for (int x = (int)searchArea.minX; x <= (int)searchArea.maxX; x++) {
            for (int y = (int)searchArea.minY; y <= (int)searchArea.maxY; y++) {
               for (int z = (int)searchArea.minZ; z <= (int)searchArea.maxZ; z++) {
                  BlockPos pos = new BlockPos(x, y, z);
                  if (level.getBlockEntity(pos) instanceof AlchemyFurnaceBlockEntity furnace && !acceleratedFurnaces.contains(pos)) {
                     try {
                        int currentCookTimeTotal = furnace.getContainerData().get(3);
                        if (currentCookTimeTotal > 0) {
                           int reducedTime = (int)(currentCookTimeTotal * 0.6);
                           furnace.getContainerData().set(3, reducedTime);
                           acceleratedFurnaces.add(pos);
                        }
                     } catch (Exception var15) {
                     }
                  }
               }
            }
         }
      }
   }

   public static void clearAcceleratedFurnace(BlockPos pos) {
      acceleratedFurnaces.remove(pos);
   }

   public static void setPlayerEnabled(UUID playerId, boolean enabled) {
      if (enabled) {
         enabledPlayers.add(playerId);
      } else {
         enabledPlayers.remove(playerId);
      }
   }

   public static boolean isPlayerHasAlchemyProdigy(Player player) {
      return enabledPlayers.contains(player.getUUID());
   }

   public static boolean isPlayerMastered(Player player) {
      return SkillAPI.getSkillsFrom(player)
         .getSkill((ManasSkill)CultivationSkills.ALCHEMY_PRODIGY.get())
         .map(instance -> instance.isMastered(player))
         .orElse(false);
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         boolean currentState = enabledPlayers.contains(player.getUUID());
         setPlayerEnabled(player.getUUID(), !currentState);
         if (!currentState) {
            player.displayClientMessage(Component.translatable("mortal_cultivation.skill.alchemy_prodigy.enabled"), true);
         } else {
            player.displayClientMessage(Component.translatable("mortal_cultivation.skill.alchemy_prodigy.disabled"), true);
         }
      }
   }
}
