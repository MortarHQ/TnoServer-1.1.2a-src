package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.event.EventHooks;

public class ExplosionEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "explosions");
   private float strength;
   private int dist;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot >= 0) {
         return false;
      }

      int aura = IAuraChunk.getAuraInArea(level, pos, 85);
      if (aura > -5000000) {
         return false;
      }

      int chance = 140 - Math.abs(aura) / 200000;
      if (chance > 1 && level.random.nextInt(chance) != 0) {
         return false;
      }

      this.strength = Math.min(Math.abs(aura) / 5000000.0F, 5.0F);
      if (this.strength <= 0.0F) {
         return false;
      }

      this.dist = Mth.clamp(Math.abs(aura) / 200000, 25, 100);
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > this.dist * this.dist
            ? IDrainSpotEffect.ActiveType.INACTIVE
            : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(Blocks.TNT);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (level.getGameTime() % 40L == 0L) {
         if (this.calcValues(level, pos, spot)) {
            int x = Mth.floor(pos.getX() + level.random.nextGaussian() * this.dist);
            int z = Mth.floor(pos.getZ() + level.random.nextGaussian() * this.dist);
            BlockPos chosenPos = new BlockPos(x, level.getHeight(Types.WORLD_SURFACE, x, z), z);
            if (chosenPos.distSqr(pos) <= this.dist * this.dist && level.isLoaded(chosenPos)) {
               Explosion explosion = new Explosion(
                  level, null, chosenPos.getX() + 0.5, chosenPos.getY() + 0.5, chosenPos.getZ() + 0.5, this.strength, false, BlockInteraction.DESTROY
               );
               if (!EventHooks.onExplosionStart(level, explosion)) {
                  explosion.explode();
                  explosion.finalizeExplosion(true);
               }
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.explosionEffect.get();
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
