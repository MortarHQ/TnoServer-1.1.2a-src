package com.tensura_guild.registry;

import com.mojang.serialization.Codec;
import com.tensura_guild.data.DwarfTaskData;
import com.tensura_guild.data.DwarfTaskOfferData;
import com.tensura_guild.data.DwarfTaskProgressData;
import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildMemberPublicData;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.data.GuildTaskBoardData;
import com.tensura_guild.data.GuildTitleData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class GuildAttachments {
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "tensura_guild");
   public static final Supplier<AttachmentType<Set<ResourceLocation>>> SEEN_DIALOGUES = ATTACHMENTS.register(
      "seen_dialogues",
      () -> AttachmentType.builder(() -> new HashSet()).serialize(ResourceLocation.CODEC.listOf().xmap(HashSet::new, ArrayList::new)).copyOnDeath().build()
   );
   public static final Supplier<AttachmentType<GuildSignatureData>> GUILD_SIGNATURE = ATTACHMENTS.register(
      "guild_signature",
      () -> AttachmentType.builder(() -> GuildSignatureData.EMPTY)
         .serialize(GuildSignatureData.CODEC, GuildSignatureData::hasSignature)
         .sync((holder, player) -> holder == player, GuildSignatureData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<GuildLevelData>> GUILD_LEVEL = ATTACHMENTS.register(
      "guild_level",
      () -> AttachmentType.builder(() -> GuildLevelData.DEFAULT)
         .serialize(GuildLevelData.CODEC)
         .sync((holder, player) -> holder == player, GuildLevelData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<GuildMemberData>> GUILD_MEMBER = ATTACHMENTS.register(
      "guild_member",
      () -> AttachmentType.builder(() -> GuildMemberData.EMPTY)
         .serialize(GuildMemberData.CODEC)
         .sync((holder, player) -> holder == player, GuildMemberData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<GuildMemberPublicData>> GUILD_MEMBER_PUBLIC = ATTACHMENTS.register(
      "guild_member_public",
      () -> AttachmentType.builder(() -> GuildMemberPublicData.EMPTY)
         .serialize(GuildMemberPublicData.CODEC)
         .sync((holder, player) -> true, GuildMemberPublicData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<GuildTitleData>> GUILD_TITLES = ATTACHMENTS.register(
      "guild_titles",
      () -> AttachmentType.builder(() -> GuildTitleData.EMPTY)
         .serialize(GuildTitleData.CODEC)
         .sync((holder, player) -> holder == player, GuildTitleData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<Integer>> REPUTATION_COINS = ATTACHMENTS.register(
      "reputation_coins",
      () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).sync((holder, player) -> holder == player, ByteBufCodecs.VAR_INT).copyOnDeath().build()
   );
   public static final Supplier<AttachmentType<Map<String, Integer>>> REPUTATION_SHOP_PURCHASES = ATTACHMENTS.register(
      "reputation_shop_purchases",
      () -> AttachmentType.builder(() -> new HashMap())
         .serialize(Codec.unboundedMap(Codec.STRING, Codec.INT).xmap(HashMap::new, HashMap::new))
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<GuildTaskBoardData>> GUILD_TASK_BOARD = ATTACHMENTS.register(
      "guild_task_board",
      () -> AttachmentType.builder(() -> GuildTaskBoardData.EMPTY)
         .serialize(GuildTaskBoardData.CODEC)
         .sync((holder, player) -> holder == player, GuildTaskBoardData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<DwarfTaskData>> DWARF_TASK = ATTACHMENTS.register(
      "dwarf_task",
      () -> AttachmentType.builder(() -> DwarfTaskData.EMPTY)
         .serialize(DwarfTaskData.CODEC, DwarfTaskData::hasActiveTask)
         .sync((holder, player) -> holder == player, DwarfTaskData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
   public static final Supplier<AttachmentType<DwarfTaskOfferData>> DWARF_TASK_OFFER = ATTACHMENTS.register(
      "dwarf_task_offer",
      () -> AttachmentType.builder(() -> DwarfTaskOfferData.EMPTY)
         .serialize(DwarfTaskOfferData.CODEC, DwarfTaskOfferData::hasState)
         .sync((holder, player) -> true, DwarfTaskOfferData.STREAM_CODEC)
         .build()
   );
   public static final Supplier<AttachmentType<DwarfTaskProgressData>> DWARF_TASK_PROGRESS = ATTACHMENTS.register(
      "dwarf_task_progress",
      () -> AttachmentType.builder(() -> DwarfTaskProgressData.EMPTY)
         .serialize(DwarfTaskProgressData.CODEC, DwarfTaskProgressData::hasState)
         .sync((holder, player) -> holder == player, DwarfTaskProgressData.STREAM_CODEC)
         .copyOnDeath()
         .build()
   );
}
