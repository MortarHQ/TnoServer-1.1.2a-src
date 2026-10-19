package xaero.map.mods.pac;

import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.controls.ControlsRegister;
import xaero.map.gui.GuiMap;
import xaero.map.gui.MapTileSelection;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.highlight.HighlighterRegistry;
import xaero.map.mods.SupportMods;
import xaero.map.mods.pac.gui.claim.ClaimResultElementManager;
import xaero.map.mods.pac.gui.claim.element.ClaimResultElementRenderer;
import xaero.map.mods.pac.gui.claim.result.ClientClaimResultListener;
import xaero.map.mods.pac.highlight.ClaimsHighlighter;
import xaero.map.mods.pac.party.OPACPlayerTrackerSystem;
import xaero.map.radar.tracker.PlayerTrackerMapElement;
import xaero.pac.client.api.OpenPACClientAPI;
import xaero.pac.client.claims.api.IClientClaimsManagerAPI;
import xaero.pac.client.claims.api.IClientDimensionClaimsManagerAPI;
import xaero.pac.client.claims.api.IClientRegionClaimsAPI;
import xaero.pac.client.claims.player.api.IClientPlayerClaimInfoAPI;
import xaero.pac.client.parties.party.api.IClientPartyAPI;
import xaero.pac.client.parties.party.api.IClientPartyMemberDynamicInfoSyncableStorageAPI;
import xaero.pac.client.parties.party.api.IClientPartyStorageAPI;
import xaero.pac.client.player.config.api.IPlayerConfigClientStorageAPI;
import xaero.pac.client.player.config.api.IPlayerConfigClientStorageManagerAPI;
import xaero.pac.client.player.config.api.IPlayerConfigStringableOptionClientStorageAPI;
import xaero.pac.client.world.api.IClientWorldDataAPI;
import xaero.pac.client.world.capability.api.ClientWorldCapabilityTypes;
import xaero.pac.client.world.capability.api.ClientWorldMainCapabilityAPI;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.claims.player.api.IPlayerClaimPosListAPI;
import xaero.pac.common.claims.player.api.IPlayerDimensionClaimsAPI;
import xaero.pac.common.parties.party.ally.api.IPartyAllyAPI;
import xaero.pac.common.parties.party.api.IPartyMemberDynamicInfoSyncableAPI;
import xaero.pac.common.parties.party.api.IPartyPlayerInfoAPI;
import xaero.pac.common.parties.party.member.api.IPartyMemberAPI;

public class SupportOpenPartiesAndClaims {
   private final OpenPACClientAPI api = OpenPACClientAPI.get();
   private final IClientClaimsManagerAPI<IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>>, IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI>> claimsManager = this.api
      .getClaimsManager();
   private final IClientPartyStorageAPI<IClientPartyAPI<IPartyMemberAPI, IPartyPlayerInfoAPI, IPartyAllyAPI>, IClientPartyMemberDynamicInfoSyncableStorageAPI<IPartyMemberDynamicInfoSyncableAPI>> partyStorage = this.api
      .getClientPartyStorage();
   private final IPlayerConfigClientStorageManagerAPI<IPlayerConfigClientStorageAPI<IPlayerConfigStringableOptionClientStorageAPI<?>>> playerConfigs = this.api
      .getPlayerConfigClientStorageManager();
   private final ClaimResultElementManager claimResultElementManager = ClaimResultElementManager.Builder.begin().setPac(this).build();
   private final ClaimResultElementRenderer claimResultElementRenderer = ClaimResultElementRenderer.Builder.begin()
      .setManager(this.claimResultElementManager)
      .build();
   private boolean serverModDetected;

   public void register() {
      this.claimsManager.getTracker().register(new ClientClaimChangeListener());
      this.claimsManager.getClaimResultTracker().register(new ClientClaimResultListener());
      WorldMap.playerTrackerSystemManager.register("openpartiesandclaims", new OPACPlayerTrackerSystem(this));
   }

   public IPlayerChunkClaimAPI claimAt(ResourceLocation dimension, int chunkX, int chunkZ) {
      return this.claimsManager.get(dimension, chunkX, chunkZ);
   }

   public void onMapRender(
      Minecraft mc,
      PoseStack matrixStack,
      int scaledMouseX,
      int scaledMouseY,
      float partialTicks,
      ResourceLocation dimension,
      int highlightChunkX,
      int highlightChunkZ
   ) {
   }

   public boolean isFromParty(UUID playerId) {
      IClientPartyAPI<IPartyMemberAPI, IPartyPlayerInfoAPI, IPartyAllyAPI> party = this.partyStorage.getParty();
      return party == null ? false : this.partyStorage.getParty().getMemberInfo(playerId) != null;
   }

   public void openPlayerConfigScreen(Screen escape, Screen parent, PlayerTrackerMapElement<?> player) {
      if (player.getPlayerId().equals(Minecraft.getInstance().player.getUUID())) {
         this.playerConfigs.openMyPlayerConfigScreen(escape, parent);
      } else {
         PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getPlayerId());
         if (info != null) {
            this.playerConfigs.openOtherPlayerConfigScreen(escape, parent, info.getProfile().getName());
         }
      }
   }

   public boolean onMapKeyPressed(Type type, int code, GuiMap screen) {
      if (KeyMappingUtils.inputMatches(type, code, this.getPacClaimsKeyBinding(), 0)) {
         screen.onClaimsButton(null);
         return true;
      } else if (KeyMappingUtils.inputMatches(type, code, this.api.getKeyBindings().getOpenModMenuKeyBinding(), 0)) {
         this.api.openMainMenuScreen(screen, screen);
         return true;
      } else {
         return false;
      }
   }

   public String getControlsTooltip() {
      return I18n.get("gui.xaero_box_controls_pac", new Object[]{KeyMappingUtils.getKeyName(this.api.getKeyBindings().getOpenModMenuKeyBinding())});
   }

   public void registerHighlighters(HighlighterRegistry highlightRegistry) {
      highlightRegistry.register(new ClaimsHighlighter(this.claimsManager));
   }

   public int getClaimDistance() {
      return this.claimsManager.getMaxClaimDistance();
   }

   public ClaimResultElementManager getClaimResultElementManager() {
      return this.claimResultElementManager;
   }

   public ClaimResultElementRenderer getCaimResultElementRenderer() {
      return this.claimResultElementRenderer;
   }

   public void onMapChange(boolean changedDimension) {
      if (changedDimension) {
         this.claimResultElementManager.clear();
      }
   }

   public void addRightClickOptions(GuiMap screen, ArrayList<RightClickOption> options, MapTileSelection mapTileSelection, MapProcessor mapProcessor) {
      if (mapTileSelection != null) {
         if (mapProcessor.getMapWorld().isUsingCustomDimension()) {
            options.add(new RightClickOption("gui.xaero_pac_claim_selection_out_of_dimension", options.size(), screen) {
               @Override
               public void onAction(Screen screen) {
               }
            });
            return;
         }

         boolean hasUnclaimed = false;
         boolean hasClaimed = false;
         boolean hasForceloaded = false;
         boolean hasUnforceloaded = false;
         Minecraft mc = Minecraft.getInstance();
         int fromX = mc.player.chunkPosition().x;
         int fromZ = mc.player.chunkPosition().z;
         int left = mapTileSelection.getLeft();
         int top = mapTileSelection.getTop();
         int right = mapTileSelection.getRight();
         int bottom = mapTileSelection.getBottom();
         int checkLeft = left;
         int checkTop = top;
         int checkRight = right;
         int checkBottom = bottom;
         if (!this.claimsManager.isAdminMode()) {
            int maxClaimDistance = this.claimsManager.getMaxClaimDistance();
            if (checkLeft < fromX - maxClaimDistance) {
               checkLeft = fromX - maxClaimDistance;
            }

            if (checkTop < fromZ - maxClaimDistance) {
               checkTop = fromZ - maxClaimDistance;
            }

            if (checkRight > fromX + maxClaimDistance) {
               checkRight = fromX + maxClaimDistance;
            }

            if (checkBottom > fromZ + maxClaimDistance) {
               checkBottom = fromZ + maxClaimDistance;
            }
         }

         int maxRequestLength = 32;
         if (checkRight - checkLeft >= maxRequestLength) {
            checkRight = checkLeft + maxRequestLength - 1;
         }

         if (checkBottom - checkTop >= maxRequestLength) {
            checkBottom = checkTop + maxRequestLength - 1;
         }

         IPlayerChunkClaimAPI potentialClaimState = this.claimsManager.getPotentialClaimStateReflection();

         label123:
         for (int x = checkLeft; x <= checkRight; x++) {
            for (int z = checkTop; z <= checkBottom; z++) {
               IPlayerChunkClaimAPI claim = this.claimsManager.get(mc.level.dimension().location(), x, z);
               if (claim == null
                  || (this.claimsManager.isAdminMode() || claim.getPlayerId().equals(potentialClaimState.getPlayerId()))
                     && !potentialClaimState.isSameClaimType(claim)) {
                  hasUnclaimed = true;
               }

               if (claim != null) {
                  hasClaimed = true;
                  if (claim.isForceloadable()) {
                     hasForceloaded = true;
                  } else {
                     hasUnforceloaded = true;
                  }
               }

               if (hasUnclaimed && hasClaimed && hasForceloaded && hasUnforceloaded) {
                  break label123;
               }
            }
         }

         if (left < checkLeft) {
            left = checkLeft - 1;
         }

         if (top < checkTop) {
            top = checkTop - 1;
         }

         if (right > checkRight) {
            right = checkRight + 1;
         }

         if (bottom > checkBottom) {
            bottom = checkBottom + 1;
         }

         final int reqLeft = left;
         final int reqTop = top;
         final int reqRight = right;
         final int reqBottom = bottom;
         if (hasUnclaimed) {
            options.add(new RightClickOption("gui.xaero_pac_claim_chunks", options.size(), screen) {
               @Override
               public void onAction(Screen screen) {
                  SupportOpenPartiesAndClaims.this.claimsManager.requestAreaClaim(reqLeft, reqTop, reqRight, reqBottom, false);
               }
            });
         }

         if (hasClaimed) {
            options.add(new RightClickOption("gui.xaero_pac_unclaim_chunks", options.size(), screen) {
               @Override
               public void onAction(Screen screen) {
                  SupportOpenPartiesAndClaims.this.claimsManager.requestAreaUnclaim(reqLeft, reqTop, reqRight, reqBottom, false);
               }
            });
         }

         if (hasUnforceloaded) {
            options.add(new RightClickOption("gui.xaero_pac_forceload_chunks", options.size(), screen) {
               @Override
               public void onAction(Screen screen) {
                  SupportOpenPartiesAndClaims.this.claimsManager.requestAreaForceload(reqLeft, reqTop, reqRight, reqBottom, true, false);
               }
            });
         }

         if (hasForceloaded) {
            options.add(new RightClickOption("gui.xaero_pac_unforceload_chunks", options.size(), screen) {
               @Override
               public void onAction(Screen screen) {
                  SupportOpenPartiesAndClaims.this.claimsManager.requestAreaForceload(reqLeft, reqTop, reqRight, reqBottom, false, false);
               }
            });
         }

         if (!hasUnclaimed && !hasClaimed && !hasUnforceloaded && !hasForceloaded) {
            options.add(new RightClickOption("gui.xaero_pac_claim_selection_out_of_range", options.size(), screen) {
               @Override
               public void onAction(Screen screen) {
               }
            });
         }
      }
   }

   public KeyMapping getPacClaimsKeyBinding() {
      return SupportMods.minimap() ? SupportMods.xaeroMinimap.getToggleClaimsKey() : ControlsRegister.keyTogglePacChunkClaims;
   }

   public void resetDetection() {
      this.serverModDetected = false;
   }

   private void detectStuff() {
      if (!this.serverModDetected) {
         ClientWorldMainCapabilityAPI capability = (ClientWorldMainCapabilityAPI)this.api
            .getCapabilityHelper()
            .getCapability(Minecraft.getInstance().level, ClientWorldCapabilityTypes.MAIN_CAP);
         if (capability != null) {
            IClientWorldDataAPI worldData = capability.getClientWorldData();
            this.serverModDetected = worldData.serverHasMod();
         }
      }
   }

   public boolean serverHasMod() {
      this.detectStuff();
      return this.serverModDetected;
   }

   public Iterator<IPartyMemberDynamicInfoSyncableAPI> getAllyIterator() {
      return this.partyStorage.getPartyMemberDynamicInfoSyncableStorage().getAllStream().iterator();
   }
}
