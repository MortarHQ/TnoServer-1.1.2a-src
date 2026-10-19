package xaero.hud.minimap.radar.category.rule;

import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import xaero.common.XaeroMinimapSession;
import xaero.common.core.XaeroMinimapCore;
import xaero.hud.category.rule.ObjectCategoryHardRule;
import xaero.hud.minimap.radar.category.EntityRadarCategoryConstants;
import xaero.hud.minimap.radar.util.RadarUtils;

public final class EntityRadarCategoryHardRules {
   public static final List<ObjectCategoryHardRule<Entity, Player>> HARD_RULES_LIST = EntityRadarCategoryConstants.LIST_FACTORY.get();
   public static final Map<String, ObjectCategoryHardRule<Entity, Player>> HARD_RULES = EntityRadarCategoryConstants.MAP_FACTORY.get();
   public static final ObjectCategoryHardRule<Entity, Player> IS_NOTHING = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("nothing")
      .setPredicate((e, p) -> false)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_ANYTHING = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("anything")
      .setPredicate((e, p) -> true)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_LIVING = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("living")
      .setPredicate((e, p) -> e instanceof LivingEntity)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_PLAYER = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("players")
      .setPredicate((e, p) -> e instanceof Player)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_SAME_TEAM = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("same-team")
      .setPredicate((e, p) -> p.getTeam() == e.getTeam())
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_HOSTILE = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("hostile")
      .setPredicate((e, p) -> RadarUtils.isHostile(e))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_TAMED = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("tamed")
      .setPredicate((e, p) -> RadarUtils.isTamed(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_ITEM = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("items")
      .setPredicate((e, p) -> e instanceof ItemEntity)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_FRIENDLY = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("friendly")
      .setPredicate((e, p) -> !IS_HOSTILE.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_OTHER_TEAMS = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("other-teams")
      .setPredicate((e, p) -> !IS_SAME_TEAM.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_BABY = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("baby")
      .setPredicate((e, p) -> e instanceof LivingEntity && ((LivingEntity)e).isBaby())
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_VANILLA = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("vanilla")
      .setPredicate((e, p) -> {
         EntityType<?> type = e.getType();
         ResourceLocation resourceLocation = type == null ? null : EntityType.getKey(type);
         return resourceLocation == null ? false : resourceLocation.getNamespace().equals("minecraft");
      })
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_MODDED = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("modded")
      .setPredicate((e, p) -> !IS_VANILLA.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_ABOVE_GROUND = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("above-ground")
      .setPredicate((e, p) -> e.level().getBrightness(LightLayer.SKY, e.blockPosition()) == 15)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_BELOW_GROUND = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("below-ground")
      .setPredicate((e, p) -> !IS_ABOVE_GROUND.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_MY_GROUND = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("my-ground")
      .setPredicate((e, p) -> {
         if (Minecraft.getInstance().cameraEntity == null) {
            return true;
         }

         boolean caveMode = XaeroMinimapSession.getCurrentSession().getMinimapProcessor().isCaveModeDisplayed();
         return IS_ABOVE_GROUND.isFollowedBy(e, p) == !caveMode;
      })
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_NOT_MY_GROUND = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("not-my-ground")
      .setPredicate((e, p) -> !IS_MY_GROUND.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_LIT = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("block-lit")
      .setPredicate((e, p) -> e.level().getBrightness(LightLayer.BLOCK, e.blockPosition()) > 0)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_UNLIT = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("block-unlit")
      .setPredicate((e, p) -> !IS_LIT.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> HAS_CUSTOM_NAME = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("has-custom-name")
      .setPredicate((e, p) -> e.hasCustomName())
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> NO_CUSTOM_NAME = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("no-custom-name")
      .setPredicate((e, p) -> !HAS_CUSTOM_NAME.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_TRACKED = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("tracked")
      .setPredicate((e, p) -> !(e instanceof Player) ? false : XaeroMinimapCore.modMain.getTrackedPlayerRenderer().getCollector().playerExists(e.getUUID()))
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_IN_TEAM = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("in-a-team")
      .setPredicate((e, p) -> e.getTeam() != null)
      .build(HARD_RULES, HARD_RULES_LIST);
   public static final ObjectCategoryHardRule<Entity, Player> IS_TEAMLESS = new ObjectCategoryHardRule.Builder<Entity, Player>()
      .setName("teamless")
      .setPredicate((e, p) -> !IS_IN_TEAM.isFollowedBy(e, p))
      .build(HARD_RULES, HARD_RULES_LIST);
}
