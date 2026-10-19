package xaero.hud.minimap.radar.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import xaero.common.HudMod;
import xaero.common.effect.Effects;
import xaero.common.minimap.mcworld.MinimapClientWorldDataHelper;
import xaero.common.misc.Misc;
import xaero.common.settings.ModSettings;
import xaero.hud.category.rule.resolver.ObjectCategoryRuleResolver;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.hud.minimap.radar.category.setting.EntityRadarCategorySettings;
import xaero.lib.client.config.ClientConfigManager;

public class RadarStateUpdater {
   private final EntityRadarCategoryManager categoryManager;
   private final RadarState state;
   private Entity lastRenderEntity;
   private final Map<EntityRadarCategory, Map<EntityRadarCategory, RadarList>> updateMap;

   public RadarStateUpdater(EntityRadarCategoryManager categoryManager, RadarState state) {
      this.categoryManager = categoryManager;
      this.state = state;
      this.updateMap = new HashMap<>();
   }

   public void update(ClientLevel world, Entity renderEntity, Player player) {
      if (renderEntity == null) {
         renderEntity = this.lastRenderEntity;
      }

      List<RadarList> radarLists = this.state.getUpdatableLists();
      EntityRadarCategory rootCategory = this.categoryManager.getRootCategory();
      EntityRadarCategory syncedRootCategory = this.categoryManager.getEffectiveSyncedRootCategory();
      this.ensureCategories(this.state, rootCategory, syncedRootCategory, radarLists);
      radarLists.forEach(RadarList::clearEntities);
      if (!HudMod.INSTANCE.isFairPlay()) {
         ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
         boolean displayRadar = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_RADAR);
         if (displayRadar || this.isWorldMapRadarEnabled()) {
            if (world != null) {
               if (renderEntity != null) {
                  if (player != null) {
                     if (!Misc.hasEffect(player, Effects.NO_RADAR)) {
                        if (!Misc.hasEffect(player, Effects.NO_RADAR_HARMFUL)) {
                           if (HudMod.INSTANCE.getHudConfigs().getClientConfigManager().getServerSynced().isChannelPresentOnServer()
                              || MinimapClientWorldDataHelper.getWorldData(world).getSyncedRules().allowRadarOnServer) {
                              ObjectCategoryRuleResolver categoryRuleResolver = this.categoryManager.getRuleResolver();
                              Iterable<Entity> worldEntities = world.entitiesForRendering();
                              boolean shouldHideInvisible = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.RADAR_HIDE_INVISIBLE);

                              for (Entity entity : worldEntities) {
                                 if (entity != null && (!shouldHideInvisible || !this.isInvisibleTo(entity, player))) {
                                    EntityRadarCategory entityCategory = categoryRuleResolver.resolve(rootCategory, entity, player);
                                    if (entityCategory != null) {
                                       EntityRadarCategory syncedEntityCategory;
                                       if (syncedRootCategory == null) {
                                          syncedEntityCategory = null;
                                       } else {
                                          syncedEntityCategory = categoryRuleResolver.resolve(syncedRootCategory, entity, player);
                                          if (syncedEntityCategory == null) {
                                             continue;
                                          }
                                       }

                                       RadarList radarList = this.updateMap
                                          .get(entityCategory)
                                          .get(syncedEntityCategory == null ? entityCategory : syncedEntityCategory);
                                       if (radarList.getEffective(EntityRadarCategorySettings.DISPLAYED)) {
                                          double offY = renderEntity.getY() - entity.getY();
                                          int heightLimit = radarList.getEffective(EntityRadarCategorySettings.HEIGHT_LIMIT).intValue();
                                          if (!(offY * offY > heightLimit * heightLimit)) {
                                             int entityNumber = radarList.getEffective(EntityRadarCategorySettings.ENTITY_NUMBER).intValue();
                                             if (entityNumber == 0 || radarList.size() < entityNumber) {
                                                radarList.add(entity);
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void ensureCategories(RadarState state, EntityRadarCategory rootCategory, EntityRadarCategory syncedRootCategory, List<RadarList> radarLists) {
      boolean reversedOrder = ModSettings.keyReverseEntityRadar.isDown();
      if (state.getListsGeneratedForConfig() != rootCategory || state.getListsGeneratedForSyncedConfig() != syncedRootCategory) {
         this.updateMap.clear();
         radarLists.clear();
         this.traceAddCategories(rootCategory, syncedRootCategory, radarLists);
         Collections.sort(radarLists);
         state.setListsGeneratedForConfig(rootCategory);
         state.setListsGeneratedForSyncedConfig(syncedRootCategory);
         state.setListsReversedOrder(false);
      }

      if (state.getListsReversedOrder() != reversedOrder) {
         Collections.reverse(radarLists);
         state.setListsReversedOrder(reversedOrder);
      }
   }

   private void traceAddCategories(EntityRadarCategory category, EntityRadarCategory syncedRootCategory, List<RadarList> radarLists) {
      category.getDirectSubCategoryIterator().forEachRemaining(sb -> this.traceAddCategories(sb, syncedRootCategory, radarLists));
      if (syncedRootCategory == null) {
         RadarList radarList = RadarList.Builder.getDefault().build().setClientCategory(category).setSyncedCategory(null);
         this.putOnUpdateMap(category, category, radarList);
         radarLists.add(radarList);
      } else {
         this.traceAddSyncedCategories(category, syncedRootCategory, radarLists);
      }
   }

   private void traceAddSyncedCategories(EntityRadarCategory category, EntityRadarCategory syncedCategory, List<RadarList> radarLists) {
      syncedCategory.getDirectSubCategoryIterator().forEachRemaining(sb -> this.traceAddSyncedCategories(category, sb, radarLists));
      RadarList radarList = RadarList.Builder.getDefault().build().setClientCategory(category).setSyncedCategory(syncedCategory);
      this.putOnUpdateMap(category, syncedCategory, radarList);
      radarLists.add(radarList);
   }

   private void putOnUpdateMap(EntityRadarCategory category, EntityRadarCategory syncedCategory, RadarList radarList) {
      Map<EntityRadarCategory, RadarList> syncedToListMap = this.updateMap.get(category);
      if (syncedToListMap == null) {
         this.updateMap.put(category, syncedToListMap = new HashMap<>());
      }

      syncedToListMap.put(syncedCategory, radarList);
   }

   private boolean isWorldMapRadarEnabled() {
      return !HudMod.INSTANCE.getSupportMods().worldmap() ? false : HudMod.INSTANCE.getSupportMods().worldmapSupport.worldMapIsRenderingRadar();
   }

   private boolean isInvisibleTo(Entity entity, Player player) {
      return entity.isInvisibleTo(player) || this.shouldHideForSneaking(entity, player);
   }

   private boolean shouldHideForSneaking(Entity e, Player p) {
      if (!e.isShiftKeyDown()) {
         return false;
      }

      Team team = e.getTeam();
      return team == null || team != p.getTeam();
   }

   public void setLastRenderViewEntity(Entity lastRenderEntity) {
      this.lastRenderEntity = lastRenderEntity;
   }
}
