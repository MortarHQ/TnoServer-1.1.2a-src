package com.b1n_ry.yigd;

import com.b1n_ry.yigd.components.ExpComponent;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.components.InventoryComponent;
import com.b1n_ry.yigd.components.RespawnComponent;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.events.YigdEvents;
import com.b1n_ry.yigd.util.DropRule;
import com.b1n_ry.yigd.util.GraveOverrideAreas;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;

public class DeathHandler {
   private final GraveComponent graveComponent;
   private final Direction playerDirection;
   private final DeathContext context;
   private final RespawnComponent respawnComponent;

   public DeathHandler(ServerPlayer player, ServerLevel world, Vec3 pos, DamageSource deathSource) {
      YigdConfig config = YigdConfig.getConfig();
      UUID killerId;
      if (deathSource.getEntity() instanceof ServerPlayer killer) {
         killerId = killer.getUUID();
      } else {
         killerId = null;
      }

      DeathContext context = new DeathContext(player, world, pos, deathSource);
      RespawnComponent respawnComponent = new RespawnComponent(player);
      InventoryComponent inventoryComponent = new InventoryComponent(player);
      ExpComponent expComponent = new ExpComponent(player);
      InventoryComponent.clearPlayer(player);
      ExpComponent.clearXp(player);
      inventoryComponent.onDeath(context);
      InventoryComponent soulboundInventory = inventoryComponent.filteredInv(dropRule -> dropRule == DropRule.KEEP);
      respawnComponent.setSoulboundInventory(soulboundInventory);
      ExpComponent keepExp = expComponent.getSoulboundExp();
      respawnComponent.setSoulboundExp(keepExp);
      ResolvableProfile profile = new ResolvableProfile(player.getGameProfile());
      Vec3 graveGenerationPos = !config.graveConfig.generateOnLastGroundPos ? pos : (Vec3)player.getData(Yigd.LAST_GROUND_POS);
      GraveComponent graveComponent = new GraveComponent(
         profile, inventoryComponent, expComponent, world, graveGenerationPos.add(0.0, 0.5, 0.0), deathSource.getLocalizedDeathMessage(player), killerId
      );
      if (!graveComponent.isEmpty()) {
         graveComponent.backUp();
      } else {
         Yigd.LOGGER.info("Did not backup data (grave data empty)");
      }

      respawnComponent.primeForRespawn(profile);
      Direction playerDirection = player.getDirection();
      this.graveComponent = graveComponent;
      this.playerDirection = playerDirection;
      this.context = context;
      this.respawnComponent = respawnComponent;
   }

   public void addItem(ItemStack stack) {
      InventoryComponent inventoryComponent = this.graveComponent.getInventoryComponent();
      inventoryComponent.addExtraGraveItem(new GraveItem(stack, GraveOverrideAreas.INSTANCE.defaultDropRule));
   }

   public void finalizeDeath() {
      YigdEvents.DelayGraveGenerationEvent event = (YigdEvents.DelayGraveGenerationEvent)NeoForge.EVENT_BUS
         .post(new YigdEvents.DelayGraveGenerationEvent(this.graveComponent, this.playerDirection, this.context, this.respawnComponent, "vanilla"));
      if (!event.generationIsDelayed()) {
         this.graveComponent.generateOrDrop(this.playerDirection, this.context, this.respawnComponent);
      }
   }
}
