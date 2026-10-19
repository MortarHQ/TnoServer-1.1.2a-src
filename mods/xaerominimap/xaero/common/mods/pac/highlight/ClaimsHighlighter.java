package xaero.common.mods.pac.highlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.highlight.ChunkHighlighter;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.info.render.compile.InfoDisplayCompiler;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.util.TextSplitter;
import xaero.pac.client.claims.api.IClientClaimsManagerAPI;
import xaero.pac.client.claims.api.IClientDimensionClaimsManagerAPI;
import xaero.pac.client.claims.player.api.IClientPlayerClaimInfoAPI;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.server.player.config.PlayerConfig;

public class ClaimsHighlighter extends ChunkHighlighter {
   private final IClientClaimsManagerAPI claimsManager;
   private final IXaeroMinimap modMain;
   private final ClientConfigManager configManager;
   private List<Component> cachedTooltip;
   private IPlayerChunkClaimAPI cachedTooltipFor;
   private int cachedForWidth;
   private String cachedForCustomName;
   private int cachedForClaimsColor;

   public ClaimsHighlighter(IXaeroMinimap modMain, IClientClaimsManagerAPI claimsManager) {
      super(true);
      this.modMain = modMain;
      this.configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      this.claimsManager = claimsManager;
   }

   @Override
   public boolean regionHasHighlights(ResourceKey<Level> dimension, int regionX, int regionZ) {
      IClientDimensionClaimsManagerAPI claimsDimension = this.claimsManager.getDimension(dimension.location());
      return claimsDimension == null ? false : claimsDimension.getRegion(regionX, regionZ) != null;
   }

   @Override
   protected int[] getColors(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      if (!(Boolean)this.configManager.getEffective(MinimapProfiledConfigOptions.OPAC_CLAIMS)) {
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
      IClientPlayerClaimInfoAPI claimInfo = this.claimsManager.getPlayerInfo(currentClaim.getPlayerId());
      int claimColor = this.getClaimsColor(currentClaim, claimInfo);
      int claimColorFormatted = (claimColor & 0xFF) << 24 | (claimColor >> 8 & 0xFF) << 16 | (claimColor >> 16 & 0xFF) << 8;
      int borderOpacity = (Integer)this.configManager.getEffective(MinimapProfiledConfigOptions.OPAC_CLAIMS_BORDER_OPACITY);
      int fillOpacity = (Integer)this.configManager.getEffective(MinimapProfiledConfigOptions.OPAC_CLAIMS_FILL_OPACITY);
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
   public boolean chunkIsHighlit(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
      return this.claimsManager.get(dimension.location(), chunkX, chunkZ) != null;
   }

   @Override
   public void addChunkHighlightTooltips(InfoDisplayCompiler compiler, ResourceKey<Level> dimension, int chunkX, int chunkZ, int width) {
      if ((Boolean)this.configManager.getEffective(MinimapProfiledConfigOptions.OPAC_CURRENT_CLAIM)) {
         IPlayerChunkClaimAPI currentClaim = this.claimsManager.get(dimension.location(), chunkX, chunkZ);
         if (currentClaim != null) {
            UUID currentClaimId = currentClaim.getPlayerId();
            IClientPlayerClaimInfoAPI claimInfo = this.claimsManager.getPlayerInfo(currentClaimId);
            String customName = this.getClaimsName(currentClaim, claimInfo);
            int actualClaimsColor = this.getClaimsColor(currentClaim, claimInfo);
            int claimsColor = actualClaimsColor | 0xFF000000;
            if (!Objects.equals(currentClaim, this.cachedTooltipFor)
               || this.cachedForWidth != width
               || this.cachedForClaimsColor != claimsColor
               || !Objects.equals(customName, this.cachedForCustomName)) {
               MutableComponent tooltip = Component.literal("□ ").withStyle(s -> s.withColor(claimsColor));
               if (Objects.equals(currentClaimId, PlayerConfig.SERVER_CLAIM_UUID)) {
                  tooltip.getSiblings()
                     .add(
                        Component.translatable(
                              "gui.xaero_pac_server_claim_tooltip",
                              new Object[]{currentClaim.isForceloadable() ? Component.translatable("gui.xaero_pac_marked_for_forceload") : ""}
                           )
                           .withStyle(ChatFormatting.WHITE)
                     );
               } else if (Objects.equals(currentClaimId, PlayerConfig.EXPIRED_CLAIM_UUID)) {
                  tooltip.getSiblings()
                     .add(
                        Component.translatable(
                              "gui.xaero_pac_expired_claim_tooltip",
                              new Object[]{currentClaim.isForceloadable() ? Component.translatable("gui.xaero_pac_marked_for_forceload") : ""}
                           )
                           .withStyle(ChatFormatting.WHITE)
                     );
               } else {
                  tooltip.getSiblings()
                     .add(
                        Component.translatable(
                              "gui.xaero_pac_claim_tooltip",
                              new Object[]{
                                 claimInfo.getPlayerUsername(),
                                 currentClaim.isForceloadable() ? Component.translatable("gui.xaero_pac_marked_for_forceload") : ""
                              }
                           )
                           .withStyle(ChatFormatting.WHITE)
                     );
               }

               if (!customName.isEmpty()) {
                  tooltip.getSiblings().add(0, Component.literal(I18n.get(customName, new Object[0]) + " - ").withStyle(ChatFormatting.WHITE));
               }

               this.cachedTooltip = new ArrayList<>();
               TextSplitter.splitTextIntoLines(this.cachedTooltip, width, width, tooltip, null);
               this.cachedTooltipFor = currentClaim;
               this.cachedForWidth = width;
               this.cachedForCustomName = customName;
               this.cachedForClaimsColor = claimsColor;
            }

            for (int i = 0; i < this.cachedTooltip.size(); i++) {
               compiler.addLine(this.cachedTooltip.get(i));
            }
         }
      }
   }

   private String getClaimsName(IPlayerChunkClaimAPI currentClaim, IClientPlayerClaimInfoAPI claimInfo) {
      int subConfigIndex = currentClaim.getSubConfigIndex();
      String customName = claimInfo.getClaimsName(subConfigIndex);
      if (subConfigIndex != -1 && customName == null) {
         customName = claimInfo.getClaimsName();
      }

      return customName;
   }

   private int getClaimsColor(IPlayerChunkClaimAPI currentClaim, IClientPlayerClaimInfoAPI claimInfo) {
      int subConfigIndex = currentClaim.getSubConfigIndex();
      Integer actualClaimsColor = claimInfo.getClaimsColor(subConfigIndex);
      if (subConfigIndex != -1 && actualClaimsColor == null) {
         actualClaimsColor = claimInfo.getClaimsColor();
      }

      return actualClaimsColor;
   }
}
