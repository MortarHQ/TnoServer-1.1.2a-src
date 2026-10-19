package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.tuple.Pair;

public class CacheRechargeEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "cache_recharge");
   private int amount;
   private AABB bb;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot < 100000) {
         return false;
      }

      Pair<Integer, Integer> auraAndSpots = IAuraChunk.getAuraAndSpotAmountInArea(level, pos, 20);
      int aura = (Integer)auraAndSpots.getLeft();
      if (aura < 1500000) {
         return false;
      }

      int dist = Mth.clamp(aura / 3500, 3, 15);
      this.bb = new AABB(pos).inflate(dist);
      this.amount = Mth.ceil(aura / 250.0F / ((Integer)auraAndSpots.getRight()).intValue());
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else if (!this.bb.contains(player.getEyePosition())) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return NaturesAuraAPI.instance().isEffectPowderActive(player.level(), player.blockPosition(), NAME)
            ? IDrainSpotEffect.ActiveType.INHIBITED
            : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(ModItems.AURA_CACHE);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (this.calcValues(level, pos, spot)) {
         for (Player player : level.getEntitiesOfClass(Player.class, this.bb)) {
            if (!NaturesAuraAPI.instance().isEffectPowderActive(level, player.blockPosition(), NAME)
               && NaturesAuraAPI.instance().insertAuraIntoPlayer(player, this.amount, true)) {
               NaturesAuraAPI.instance().insertAuraIntoPlayer(player, this.amount, false);
               auraChunk.drainAura(pos, this.amount);
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.cacheRechargeEffect.get();
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
