package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class MitamaOfferingMenu extends AbstractContainerMenu {
   private final MitamaFaithSkill.MitamaMenuKind kind;
   private final UUID targetUuid;
   private final String targetName;
   private final List<MitamaFaithSkill.MitamaOfferingEntry> entries = new ArrayList<>();
   private final double magicule;
   private final double aura;
   private final double ep;
   private final int souls;
   private final double worship;

   private MitamaOfferingMenu(
      int containerId,
      Inventory inventory,
      MitamaFaithSkill.MitamaMenuKind kind,
      UUID targetUuid,
      String targetName,
      List<MitamaFaithSkill.MitamaOfferingEntry> entries,
      double magicule,
      double aura,
      double ep,
      int souls,
      double worship
   ) {
      super((MenuType)ModMenus.MITAMA_OFFERING_MENU.get(), containerId);
      this.kind = kind;
      this.targetUuid = targetUuid;
      this.targetName = targetName;
      this.entries.addAll(entries);
      this.magicule = magicule;
      this.aura = aura;
      this.ep = ep;
      this.souls = souls;
      this.worship = worship;
   }

   public static MitamaOfferingMenu server(
      int containerId, Inventory inventory, ServerPlayer player, MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid, String targetName
   ) {
      List<MitamaFaithSkill.MitamaOfferingEntry> entries = MitamaFaithSkill.buildSkillEntries(player, kind, targetUuid);
      LivingEntity source = sourceForValues(player, kind, targetUuid);
      IExistence existence = TensuraStorages.getExistenceFrom(source);
      double worship = visibleWorship(player, kind, targetUuid);
      return new MitamaOfferingMenu(
         containerId,
         inventory,
         kind,
         targetUuid,
         targetName,
         entries,
         EnergyHelper.getBaseMaxMagicule(source),
         EnergyHelper.getBaseMaxAura(source),
         EnergyHelper.getBaseMaxEP(source),
         existence.getSoulPoints(),
         worship
      );
   }

   public static MitamaOfferingMenu client(int containerId, Inventory inventory, FriendlyByteBuf buf) {
      MitamaFaithSkill.MitamaMenuKind kind = (MitamaFaithSkill.MitamaMenuKind)buf.readEnum(MitamaFaithSkill.MitamaMenuKind.class);
      UUID targetUuid = buf.readUUID();
      String targetName = buf.readUtf();
      double magicule = buf.readDouble();
      double aura = buf.readDouble();
      double ep = buf.readDouble();
      int souls = buf.readVarInt();
      double worship = buf.readDouble();
      int size = buf.readVarInt();
      List<MitamaFaithSkill.MitamaOfferingEntry> entries = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         ResourceLocation id = buf.readResourceLocation();
         String name = buf.readUtf();
         String type = buf.readUtf();
         double mastery = buf.readDouble();
         double value = buf.readDouble();
         entries.add(new MitamaFaithSkill.MitamaOfferingEntry(id, name, type, mastery, value));
      }

      return new MitamaOfferingMenu(containerId, inventory, kind, targetUuid, targetName, entries, magicule, aura, ep, souls, worship);
   }

   public static void writeExtraData(ServerPlayer player, FriendlyByteBuf buf, MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid, String targetName) {
      List<MitamaFaithSkill.MitamaOfferingEntry> entries = MitamaFaithSkill.buildSkillEntries(player, kind, targetUuid);
      LivingEntity source = sourceForValues(player, kind, targetUuid);
      IExistence existence = TensuraStorages.getExistenceFrom(source);
      buf.writeEnum(kind);
      buf.writeUUID(targetUuid);
      buf.writeUtf(targetName);
      buf.writeDouble(EnergyHelper.getBaseMaxMagicule(source));
      buf.writeDouble(EnergyHelper.getBaseMaxAura(source));
      buf.writeDouble(EnergyHelper.getBaseMaxEP(source));
      buf.writeVarInt(existence.getSoulPoints());
      buf.writeDouble(visibleWorship(player, kind, targetUuid));
      buf.writeVarInt(entries.size());

      for (MitamaFaithSkill.MitamaOfferingEntry entry : entries) {
         buf.writeResourceLocation(entry.id());
         buf.writeUtf(entry.name());
         buf.writeUtf(entry.type());
         buf.writeDouble(entry.mastery());
         buf.writeDouble(entry.value());
      }
   }

   private static LivingEntity sourceForValues(ServerPlayer player, MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid) {
      if (kind == MitamaFaithSkill.MitamaMenuKind.DONATE) {
         ServerPlayer onlineGod = player.server.getPlayerList().getPlayer(targetUuid);
         if (onlineGod == null) {
            LivingEntity target = MitamaFaithSkill.findLivingByUuid(player, targetUuid);
            if (target != null) {
               return target;
            }
         }
      }

      return player;
   }

   private static double visibleWorship(ServerPlayer player, MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid) {
      ServerPlayer god = player;
      if (kind == MitamaFaithSkill.MitamaMenuKind.DONATE) {
         god = player.server.getPlayerList().getPlayer(targetUuid);
         if (god == null) {
            return 0.0;
         }
      }

      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      return optional.map(MitamaFaithSkill::getWorship).orElse(0.0);
   }

   public MitamaFaithSkill.MitamaMenuKind getKind() {
      return this.kind;
   }

   public UUID getTargetUuid() {
      return this.targetUuid;
   }

   public String getTargetName() {
      return this.targetName;
   }

   public List<MitamaFaithSkill.MitamaOfferingEntry> getEntries() {
      return this.entries;
   }

   public double getMagicule() {
      return this.magicule;
   }

   public double getAura() {
      return this.aura;
   }

   public double getEp() {
      return this.ep;
   }

   public int getSouls() {
      return this.souls;
   }

   public double getWorship() {
      return this.worship;
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }
}
