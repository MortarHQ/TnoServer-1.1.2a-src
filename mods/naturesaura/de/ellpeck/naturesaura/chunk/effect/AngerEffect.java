package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

public class AngerEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "anger");
   private AABB bb;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot >= 0) {
         return false;
      }

      int aura = IAuraChunk.getAuraInArea(level, pos, 50);
      if (aura > 0) {
         return false;
      }

      int dist = Math.min(Math.abs(aura) / 50000, 75);
      if (dist < 10) {
         return false;
      }

      this.bb = new AABB(pos).inflate(dist);
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return !this.bb.contains(player.getEyePosition()) ? IDrainSpotEffect.ActiveType.INACTIVE : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(Items.FIRE_CHARGE);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (level.getGameTime() % 100L == 0L) {
         if (this.calcValues(level, pos, spot)) {
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, this.bb)) {
               if (entity instanceof NeutralMob) {
                  Player player = level.getNearestPlayer(entity, 25.0);
                  if (player != null) {
                     ((NeutralMob)entity).setTarget(player);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.angerEffect.get();
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
