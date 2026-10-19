package dev.ftb.mods.ftbquests.quest.task;

import dev.architectury.registry.registries.RegistrarManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.IconAnimation;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.util.Lazy;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KillTask extends Task {
   private static final ResourceLocation ZOMBIE = ResourceLocation.withDefaultNamespace("zombie");
   private static final Lazy<NameMap<ResourceLocation>> entityNameMap = Lazy.of(KillTask::scanEntityTypes);
   private static final Lazy<NameMap<String>> entityTagMap = Lazy.of(KillTask::scanEntityTags);
   private ResourceLocation entityTypeId = ZOMBIE;
   private TagKey<EntityType<?>> entityTypeTag = null;
   private long value = 100L;
   private String customName = "";
   private String nbtFilter = "";
   private static final Map<ResourceLocation, Icon> entityIcons = new HashMap<>();

   public KillTask(long id, Quest quest) {
      super(id, quest);
   }

   @Override
   public TaskType getType() {
      return TaskTypes.KILL;
   }

   @Override
   public long getMaxProgress() {
      return this.value;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("entity", this.entityTypeId.toString());
      if (this.entityTypeTag != null) {
         nbt.putString("entityTypeTag", this.entityTypeTag.location().toString());
      }

      nbt.putLong("value", this.value);
      if (!this.customName.isEmpty()) {
         nbt.putString("custom_name", this.customName);
      }

      if (!this.nbtFilter.isEmpty()) {
         nbt.putString("nbt_filter", this.nbtFilter);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.entityTypeId = ResourceLocation.tryParse(nbt.getString("entity"));
      this.entityTypeTag = parseTypeTag(nbt.getString("entityTypeTag"));
      this.value = nbt.getLong("value");
      this.customName = nbt.getString("custom_name");
      this.nbtFilter = nbt.getString("nbt_filter");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.entityTypeId.toString());
      buffer.writeUtf(this.entityTypeTag == null ? "" : this.entityTypeTag.location().toString());
      buffer.writeVarLong(this.value);
      buffer.writeUtf(this.customName);
      buffer.writeUtf(this.nbtFilter);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.entityTypeId = ResourceLocation.tryParse(buffer.readUtf());
      this.entityTypeTag = parseTypeTag(buffer.readUtf());
      this.value = buffer.readVarInt();
      this.customName = buffer.readUtf();
      this.nbtFilter = buffer.readUtf();
   }

   @Nullable
   private static TagKey<EntityType<?>> parseTypeTag(String tag) {
      if (tag != null && !tag.isEmpty()) {
         if (tag.startsWith("#")) {
            tag = tag.substring(1);
         }

         ResourceLocation rl = ResourceLocation.tryParse(tag);
         return rl != null && !rl.getPath().isEmpty() ? TagKey.create(Registries.ENTITY_TYPE, rl) : null;
      } else {
         return null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addEnum("entity", this.entityTypeId, v -> this.entityTypeId = v, (NameMap)entityNameMap.get(), ZOMBIE);
      config.addEnum("entity_type_tag", this.getTypeTagStr(), v -> this.entityTypeTag = parseTypeTag(v), (NameMap)entityTagMap.get());
      config.addLong("value", this.value, v -> this.value = v, 100L, 1L, Long.MAX_VALUE);
      config.addString("custom_name", this.customName, v -> this.customName = v, "");
      config.addString("nbt_filter", this.nbtFilter, v -> this.nbtFilter = v, "");
   }

   private String getTypeTagStr() {
      return this.entityTypeTag == null ? "" : this.entityTypeTag.location().toString();
   }

   private static Icon getIconForEntityType(ResourceLocation typeId) {
      return entityIcons.computeIfAbsent(typeId, k -> {
         EntityType<?> entityType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(typeId);
         if (entityType.equals(EntityType.PLAYER)) {
            return Icons.PLAYER;
         }

         Item item = SpawnEggItem.byId(entityType);
         if (item == null) {
            Entity e = entityType.create(FTBQuestsClient.getClientLevel());
            if (e != null) {
               ItemStack stack = e.getPickResult();
               if (stack != null) {
                  item = stack.getItem();
               }
            }
         }

         return ItemIcon.getItemIcon(item != null ? item : Items.SPAWNER);
      });
   }

   @Override
   public void clearCachedData() {
      super.clearCachedData();
      entityNameMap.invalidate();
      entityTagMap.invalidate();
      entityIcons.clear();
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      MutableComponent name = this.entityTypeTag == null
         ? Component.translatable("entity." + this.entityTypeId.toLanguageKey())
         : Component.literal("#" + this.getTypeTagStr());
      return !this.customName.isEmpty()
         ? Component.translatable("ftbquests.task.ftbquests.kill.title_named", new Object[]{this.formatMaxProgress(), name, Component.literal(this.customName)})
         : Component.translatable("ftbquests.task.ftbquests.kill.title", new Object[]{this.formatMaxProgress(), name});
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      if (this.entityTypeTag == null) {
         return getIconForEntityType(this.entityTypeId);
      }

      List<Icon> icons = new ArrayList<>();
      BuiltInRegistries.ENTITY_TYPE
         .getTag(this.entityTypeTag)
         .ifPresent(set -> set.forEach(holder -> holder.unwrapKey().map(k -> icons.add(getIconForEntityType(k.location())))));
      return icons.isEmpty() ? Icons.BARRIER : IconAnimation.fromList(icons, false);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onButtonClicked(Button button, boolean canClick) {
   }

   public void kill(TeamData teamData, LivingEntity e) {
      if (!teamData.isCompleted(this) && this.match(e)) {
         teamData.addProgress(this, 1L);
      }
   }

   private boolean match(LivingEntity e) {
      return this.entityTypeTag == null
         ? this.entityTypeId.equals(RegistrarManager.getId(e.getType(), Registries.ENTITY_TYPE)) && this.nameMatchOK(e) && this.filterMatchOK(e)
         : e.getType().is(this.entityTypeTag) && this.nameMatchOK(e) && this.filterMatchOK(e);
   }

   private boolean nameMatchOK(LivingEntity e) {
      return this.customName.isEmpty()
         || (e instanceof Player p ? p.getGameProfile().getName().equals(this.customName) : e.getName().getString().equals(this.customName));
   }

   private boolean filterMatchOK(LivingEntity e) {
      return ObservationTask.matchEntityNBT(e, this.nbtFilter);
   }

   @NotNull
   private static NameMap<ResourceLocation> scanEntityTypes() {
      List<ResourceLocation> ids = new ArrayList<>();
      BuiltInRegistries.ENTITY_TYPE.forEach(type -> {
         try {
            if (type.create(FTBQuestsClient.getClientLevel()) instanceof LivingEntity) {
               ids.add(type.arch$registryName());
            }
         } catch (Exception e) {
            FTBQuests.LOGGER.warn("Entity creation failed during kill task scanning for {}: {}", type.arch$registryName(), e.getMessage());
         }
      });
      ids.sort((r1, r2) -> {
         Component c1 = Component.translatable("entity." + r1.toLanguageKey());
         Component c2 = Component.translatable("entity." + r2.toLanguageKey());
         return c1.getString().compareTo(c2.getString());
      });
      return NameMap.of(ZOMBIE, ids)
         .name(
            id -> Component.translatable("entity." + id.toLanguageKey())
               .append(Component.empty().withStyle(ChatFormatting.GRAY).append(" [").append(Component.literal(id.toString())).append("]"))
         )
         .icon(KillTask::getIconForEntityType)
         .create();
   }

   @NotNull
   private static NameMap<String> scanEntityTags() {
      List<String> tags = new ArrayList<>(List.of(""));
      tags.addAll(BuiltInRegistries.ENTITY_TYPE.getTags().map(pair -> ((TagKey)pair.getFirst()).location().toString()).sorted().toList());
      return NameMap.of("minecraft:zombies", tags).create();
   }
}
