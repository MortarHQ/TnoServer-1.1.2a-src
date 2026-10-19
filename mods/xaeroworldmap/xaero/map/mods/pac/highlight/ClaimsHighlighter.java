package xaero.map.mods.pac.highlight;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.highlight.ChunkHighlighter;
import xaero.pac.client.claims.api.IClientClaimsManagerAPI;
import xaero.pac.client.claims.api.IClientDimensionClaimsManagerAPI;
import xaero.pac.client.claims.api.IClientRegionClaimsAPI;
import xaero.pac.client.claims.player.api.IClientPlayerClaimInfoAPI;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.claims.player.api.IPlayerClaimPosListAPI;
import xaero.pac.common.claims.player.api.IPlayerDimensionClaimsAPI;
import xaero.pac.common.server.player.config.PlayerConfig;

public class ClaimsHighlighter extends ChunkHighlighter {
   private final IClientClaimsManagerAPI<IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>>, IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI>> claimsManager;
   private Component cachedTooltip;
   private IPlayerChunkClaimAPI cachedTooltipFor;
   private String cachedForCustomName;
   private int cachedForClaimsColor;

   public ClaimsHighlighter(
      IClientClaimsManagerAPI<IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>>, IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI>> claimsManager
   ) {
      super(true);
      this.claimsManager = claimsManager;
   }

   @Override
   public boolean regionHasHighlights(ResourceKey<Level> dimension, int regionX, int regionZ) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      if (!(Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS)) {
         return false;
      }

      IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI> claimsDimension = this.claimsManager.getDimension(dimension.location());
      return claimsDimension == null ? false : claimsDimension.getRegion(regionX, regionZ) != null;
   }

   @Override
   protected int[] getColors(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      if (!(Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS)) {
         return null;
      }

      IPlayerChunkClaimAPI currentClaim = this.claimsManager.get(dimension.location(), chunkX, chunkZ);
      if (currentClaim == null) {
         return null;
      }

      IPlayerChunkClaimAPI topClaim = this.claimsManager.get(dimension.location(), chunkX, chunkZ - 1);
      IPlayerChunkClaimAPI rightClaim = this.claimsManager.get(dimension.location(), chunkX + 1, chunkZ);
      IPlayerChunkClaimAPI bottomClaim = this.claimsManager.get(dimension.location(), chunkX, chunkZ + 1);
      IPlayerChunkClaimAPI leftClaim = this.claimsManager.get(dimension.location(), chunkX - 1, chunkZ);
      IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo = this.claimsManager.getPlayerInfo(currentClaim.getPlayerId());
      int claimColor = this.getClaimsColor(currentClaim, claimInfo);
      int claimColorFormatted = (claimColor & 0xFF) << 24 | (claimColor >> 8 & 0xFF) << 16 | (claimColor >> 16 & 0xFF) << 8;
      int fillOpacity = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS_FILL_OPACITY);
      int borderOpacity = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS_BORDER_OPACITY);
      int centerColor = claimColorFormatted | 255 * fillOpacity / 100;
      int sideColor = claimColorFormatted | 255 * borderOpacity / 100;
      this.resultStore[0] = centerColor;
      this.resultStore[1] = topClaim != currentClaim ? sideColor : centerColor;
      this.resultStore[2] = rightClaim != currentClaim ? sideColor : centerColor;
      this.resultStore[3] = bottomClaim != currentClaim ? sideColor : centerColor;
      this.resultStore[4] = leftClaim != currentClaim ? sideColor : centerColor;
      return this.resultStore;
   }

   @Override
   public int calculateRegionHash(ResourceKey<Level> dimension, int regionX, int regionZ) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      if (!(Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS)) {
         return 0;
      }

      IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI> claimsDimension = this.claimsManager.getDimension(dimension.location());
      if (claimsDimension == null) {
         return 0;
      }

      IClientRegionClaimsAPI claimsRegion = claimsDimension.getRegion(regionX, regionZ);
      if (claimsRegion == null) {
         return 0;
      }

      IClientRegionClaimsAPI topRegion = claimsDimension.getRegion(regionX, regionZ - 1);
      IClientRegionClaimsAPI rightRegion = claimsDimension.getRegion(regionX + 1, regionZ);
      IClientRegionClaimsAPI bottomRegion = claimsDimension.getRegion(regionX, regionZ + 1);
      IClientRegionClaimsAPI leftRegion = claimsDimension.getRegion(regionX - 1, regionZ);
      long accumulator = 0L;
      accumulator = accumulator * 37L + ((Integer)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS_BORDER_OPACITY)).intValue();
      accumulator = accumulator * 37L + ((Integer)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS_FILL_OPACITY)).intValue();

      for (int i = 0; i < 32; i++) {
         accumulator = this.accountClaim(accumulator, topRegion != null ? topRegion.get(i, 31) : null);
         accumulator = this.accountClaim(accumulator, rightRegion != null ? rightRegion.get(0, i) : null);
         accumulator = this.accountClaim(accumulator, bottomRegion != null ? bottomRegion.get(i, 0) : null);
         accumulator = this.accountClaim(accumulator, leftRegion != null ? leftRegion.get(31, i) : null);

         for (int j = 0; j < 32; j++) {
            IPlayerChunkClaimAPI claim = claimsRegion.get(i, j);
            accumulator = this.accountClaim(accumulator, claim);
         }
      }

      return (int)(accumulator >> 32) * 37 + (int)(accumulator & -1L);
   }

   private long accountClaim(long accumulator, IPlayerChunkClaimAPI claim) {
      if (claim != null) {
         UUID playerId = claim.getPlayerId();
         accumulator += playerId.getLeastSignificantBits();
         accumulator *= 37L;
         accumulator += claim.getPlayerId().getMostSignificantBits();
         accumulator *= 37L;
         IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo = this.claimsManager.getPlayerInfo(playerId);
         accumulator += this.getClaimsColor(claim, claimInfo);
         accumulator *= 37L;
         accumulator += claim.isForceloadable() ? 1L : 0L;
         accumulator *= 37L;
         accumulator += claim.getSubConfigIndex();
      }

      return accumulator * 37L;
   }

   @Override
   public boolean chunkIsHighlit(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      return this.claimsManager.get(dimension.location(), chunkX, chunkZ) != null;
   }

   @Override
   public Component getChunkHighlightSubtleTooltip(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      IPlayerChunkClaimAPI currentClaim = this.claimsManager.get(dimension.location(), chunkX, chunkZ);
      if (currentClaim == null) {
         return null;
      }

      UUID currentClaimId = currentClaim.getPlayerId();
      IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo = this.claimsManager.getPlayerInfo(currentClaimId);
      String customName = this.getClaimsName(currentClaim, claimInfo);
      int actualClaimsColor = this.getClaimsColor(currentClaim, claimInfo);
      int claimsColor = actualClaimsColor | 0xFF000000;
      if (!Objects.equals(currentClaim, this.cachedTooltipFor)
         || this.cachedForClaimsColor != claimsColor
         || !Objects.equals(customName, this.cachedForCustomName)) {
         this.cachedTooltip = Component.literal("□ ").withStyle(s -> s.withColor(claimsColor));
         if (Objects.equals(currentClaimId, PlayerConfig.SERVER_CLAIM_UUID)) {
            this.cachedTooltip
               .getSiblings()
               .add(
                  Component.translatable(
                        "gui.xaero_wm_pac_server_claim_tooltip",
                        new Object[]{currentClaim.isForceloadable() ? Component.translatable("gui.xaero_wm_pac_marked_for_forceload") : ""}
                     )
                     .withStyle(ChatFormatting.WHITE)
               );
         } else if (Objects.equals(currentClaimId, PlayerConfig.EXPIRED_CLAIM_UUID)) {
            this.cachedTooltip
               .getSiblings()
               .add(
                  Component.translatable(
                        "gui.xaero_wm_pac_expired_claim_tooltip",
                        new Object[]{currentClaim.isForceloadable() ? Component.translatable("gui.xaero_wm_pac_marked_for_forceload") : ""}
                     )
                     .withStyle(ChatFormatting.WHITE)
               );
         } else {
            this.cachedTooltip
               .getSiblings()
               .add(
                  Component.translatable(
                        "gui.xaero_wm_pac_claim_tooltip",
                        new Object[]{
                           claimInfo.getPlayerUsername(), currentClaim.isForceloadable() ? Component.translatable("gui.xaero_wm_pac_marked_for_forceload") : ""
                        }
                     )
                     .withStyle(ChatFormatting.WHITE)
               );
         }

         if (!customName.isEmpty()) {
            this.cachedTooltip.getSiblings().add(0, Component.literal(I18n.get(customName, new Object[0]) + " - ").withStyle(ChatFormatting.WHITE));
         }

         this.cachedTooltipFor = currentClaim;
         this.cachedForCustomName = customName;
         this.cachedForClaimsColor = claimsColor;
      }

      return this.cachedTooltip;
   }

   @Override
   public Component getChunkHighlightBluntTooltip(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      return null;
   }

   @Override
   public void addMinimapBlockHighlightTooltips(List<Component> list, ResourceKey<Level> dimension, int blockX, int blockZ, int width) {
   }

   private String getClaimsName(IPlayerChunkClaimAPI currentClaim, IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo) {
      int subConfigIndex = currentClaim.getSubConfigIndex();
      String customName = claimInfo.getClaimsName(subConfigIndex);
      if (subConfigIndex != -1 && customName == null) {
         customName = claimInfo.getClaimsName();
      }

      return customName;
   }

   private int getClaimsColor(IPlayerChunkClaimAPI currentClaim, IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo) {
      int subConfigIndex = currentClaim.getSubConfigIndex();
      Integer actualClaimsColor = claimInfo.getClaimsColor(subConfigIndex);
      if (subConfigIndex != -1 && actualClaimsColor == null) {
         actualClaimsColor = claimInfo.getClaimsColor();
      }

      return actualClaimsColor;
   }
}
