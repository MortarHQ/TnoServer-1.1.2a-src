# FTB Teams（`ftbteams`）

> jar：`[FTB 团队] ftb-teams.jar` ｜ 版本：`2101.1.10` ｜ 作者：FTB Team
> 规模：**88 个类 / 12 个包 / 7447 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 6

**依赖**：`neoforge`、`minecraft`、`architectury`、`ftblibrary`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftbteams.api.property` | 18 | `TeamProperty`、`TeamPropertyArgument`、`ColorProperty` |
| `dev.ftb.mods.ftbteams.data` | 16 | `TeamManagerImpl`、`ClientTeamManagerImpl`、`FTBTeamsCommands` |
| `dev.ftb.mods.ftbteams.net` | 14 | `PlayerGUIOperationMessage`、`CreatePartyMessage`、`SyncMessageHistoryMessage` |
| `dev.ftb.mods.ftbteams.api.event` | 13 | `TeamManagerEvent`、`ClientTeamPropertiesChangedEvent`、`TeamCollectPropertiesEvent` |
| `dev.ftb.mods.ftbteams.client.gui` | 9 | `MyTeamScreen`、`CreatePartyScreen`、`BaseInvitationScreen` |
| `dev.ftb.mods.ftbteams.api` | 8 | `CustomPartyCreationHandler`、`TeamManager`、`TeamStagesHelper` |
| `dev.ftb.mods.ftbteams.neoforge` | 2 | `FTBTeamsNeoForge`、`ArgumentTypes` |
| `dev.ftb.mods.ftbteams.api.client` | 2 | `ClientTeamManager`、`KnownClientPlayer` |
| `dev.ftb.mods.ftbteams.client` | 2 | `FTBTeamsClient`、`KnownClientPlayerNet` |
| `dev.ftb.mods.ftbteams` | 2 | `FTBTeams`、`FTBTeamsAPIImpl` |
| `dev.ftb.mods.ftbteams.config` | 1 | `ServerConfig` |
| `architectury_inject_FTBTeams121_common_8398905a4d6a419a8e489951fc7e3f58_07d49edeb1e7f5375db394665932fc8ceafc1e709d536833f97ba698e6e52ce0ftbteams2101110devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TeamManagerImpl` | class | TeamManager | — | 33 | 472 | [TeamManagerImpl.java](dev/ftb/mods/ftbteams/data/TeamManagerImpl.java) |
| `ClientTeamManagerImpl` | class | ClientTeamManager | — | 23 | 223 | [ClientTeamManagerImpl.java](dev/ftb/mods/ftbteams/data/ClientTeamManagerImpl.java) |
| `FTBTeamsNeoForge` | class | — | Mod | 0 | 14 | [FTBTeamsNeoForge.java](dev/ftb/mods/ftbteams/neoforge/FTBTeamsNeoForge.java) |
| `FTBTeamsCommands` | class | — | — | 27 | 586 | [FTBTeamsCommands.java](dev/ftb/mods/ftbteams/data/FTBTeamsCommands.java) |
| `MyTeamScreen` | class | BaseScreen | — | 34 | 431 | [MyTeamScreen.java](dev/ftb/mods/ftbteams/client/gui/MyTeamScreen.java) |
| `TeamManagerEvent` | class | — | — | 2 | 28 | [TeamManagerEvent.java](dev/ftb/mods/ftbteams/api/event/TeamManagerEvent.java) |
| `AbstractTeamBase` | class | Team | — | 35 | 262 | [AbstractTeamBase.java](dev/ftb/mods/ftbteams/data/AbstractTeamBase.java) |
| `ClientTeamManager` | interface | — | — | 0 | 32 | [ClientTeamManager.java](dev/ftb/mods/ftbteams/api/client/ClientTeamManager.java) |
| `PartyTeam` | class | AbstractTeam | — | 22 | 436 | [PartyTeam.java](dev/ftb/mods/ftbteams/data/PartyTeam.java) |
| `ClientTeam` | class | AbstractTeamBase | — | 21 | 172 | [ClientTeam.java](dev/ftb/mods/ftbteams/data/ClientTeam.java) |
| `CreatePartyScreen` | class | BaseScreen | — | 18 | 219 | [CreatePartyScreen.java](dev/ftb/mods/ftbteams/client/gui/CreatePartyScreen.java) |
| `CustomPartyCreationHandler` | interface | — | — | 0 | 10 | [CustomPartyCreationHandler.java](dev/ftb/mods/ftbteams/api/CustomPartyCreationHandler.java) |
| `TeamManager` | interface | — | — | 0 | 52 | [TeamManager.java](dev/ftb/mods/ftbteams/api/TeamManager.java) |
| `ClientTeamPropertiesChangedEvent` | class | — | — | 2 | 23 | [ClientTeamPropertiesChangedEvent.java](dev/ftb/mods/ftbteams/api/event/ClientTeamPropertiesChangedEvent.java) |
| `BaseInvitationScreen` | class | BaseScreen | — | 16 | 142 | [BaseInvitationScreen.java](dev/ftb/mods/ftbteams/client/gui/BaseInvitationScreen.java) |
| `ServerConfig` | interface | — | — | 0 | 34 | [ServerConfig.java](dev/ftb/mods/ftbteams/config/ServerConfig.java) |
| `AbstractTeam` | class | AbstractTeamBase | — | 16 | 296 | [AbstractTeam.java](dev/ftb/mods/ftbteams/data/AbstractTeam.java) |
| `TeamProperty` | class | — | — | 24 | 136 | [TeamProperty.java](dev/ftb/mods/ftbteams/api/property/TeamProperty.java) |
| `FTBTeamsClient` | class | — | — | 12 | 133 | [FTBTeamsClient.java](dev/ftb/mods/ftbteams/client/FTBTeamsClient.java) |
| `TeamArgument` | class | ArgumentType<TeamArgumentProvider> | — | 15 | 195 | [TeamArgument.java](dev/ftb/mods/ftbteams/data/TeamArgument.java) |
| `PlayerTeam` | class | AbstractTeam | — | 18 | 128 | [PlayerTeam.java](dev/ftb/mods/ftbteams/data/PlayerTeam.java) |
| `TeamCollectPropertiesEvent` | class | — | — | 1 | 17 | [TeamCollectPropertiesEvent.java](dev/ftb/mods/ftbteams/api/event/TeamCollectPropertiesEvent.java) |
| `TeamInfoEvent` | class | TeamEvent | — | 2 | 23 | [TeamInfoEvent.java](dev/ftb/mods/ftbteams/api/event/TeamInfoEvent.java) |
| `PlayerLeftPartyTeamEvent` | class | TeamEvent | — | 4 | 39 | [PlayerLeftPartyTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerLeftPartyTeamEvent.java) |
| `PlayerLoggedInAfterTeamEvent` | class | TeamEvent | — | 1 | 18 | [PlayerLoggedInAfterTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerLoggedInAfterTeamEvent.java) |
| `TeamCreatedEvent` | class | TeamEvent | — | 2 | 27 | [TeamCreatedEvent.java](dev/ftb/mods/ftbteams/api/event/TeamCreatedEvent.java) |
| `PlayerChangedTeamEvent` | class | TeamEvent | — | 3 | 34 | [PlayerChangedTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerChangedTeamEvent.java) |
| `PlayerJoinedPartyTeamEvent` | class | TeamEvent | — | 2 | 26 | [PlayerJoinedPartyTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerJoinedPartyTeamEvent.java) |
| `TeamPropertiesChangedEvent` | class | TeamEvent | — | 1 | 18 | [TeamPropertiesChangedEvent.java](dev/ftb/mods/ftbteams/api/event/TeamPropertiesChangedEvent.java) |
| `PlayerTransferredTeamOwnershipEvent` | class | TeamEvent | — | 3 | 42 | [PlayerTransferredTeamOwnershipEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerTransferredTeamOwnershipEvent.java) |
| `TeamEvent` | class | — | — | 1 | 38 | [TeamEvent.java](dev/ftb/mods/ftbteams/api/event/TeamEvent.java) |
| `TeamAllyEvent` | class | TeamEvent | — | 2 | 26 | [TeamAllyEvent.java](dev/ftb/mods/ftbteams/api/event/TeamAllyEvent.java) |
| `FTBTeams` | class | — | — | 11 | 177 | [FTBTeams.java](dev/ftb/mods/ftbteams/FTBTeams.java) |
| `FTBTUtils` | class | — | — | 5 | 70 | [FTBTUtils.java](dev/ftb/mods/ftbteams/data/FTBTUtils.java) |
| `TeamPropertyCollectionImpl` | class | TeamPropertyCollection | — | 12 | 173 | [TeamPropertyCollectionImpl.java](dev/ftb/mods/ftbteams/data/TeamPropertyCollectionImpl.java) |
| `TeamStagesHelper` | class | — | — | 8 | 67 | [TeamStagesHelper.java](dev/ftb/mods/ftbteams/api/TeamStagesHelper.java) |
| `TeamRank` | enum | StringRepresentable | — | 12 | 82 | [TeamRank.java](dev/ftb/mods/ftbteams/api/TeamRank.java) |
| `TeamPropertyArgument` | class | ArgumentTypeInfo<TeamPropertyArgumen | — | 10 | 87 | [TeamPropertyArgument.java](dev/ftb/mods/ftbteams/api/property/TeamPropertyArgument.java) |
| `KnownClientPlayer` | record | — | — | 4 | 34 | [KnownClientPlayer.java](dev/ftb/mods/ftbteams/api/client/KnownClientPlayer.java) |
| `KnownClientPlayerNet` | class | — | — | 2 | 46 | [KnownClientPlayerNet.java](dev/ftb/mods/ftbteams/client/KnownClientPlayerNet.java) |
| `AllyScreen` | class | BaseInvitationScreen | — | 4 | 60 | [AllyScreen.java](dev/ftb/mods/ftbteams/client/gui/AllyScreen.java) |
| `InviteScreen` | class | BaseInvitationScreen | — | 3 | 32 | [InviteScreen.java](dev/ftb/mods/ftbteams/client/gui/InviteScreen.java) |
| `FTBTeamsAPIImpl` | enum | FTBTeamsAPI.API | — | 9 | 62 | [FTBTeamsAPIImpl.java](dev/ftb/mods/ftbteams/FTBTeamsAPIImpl.java) |
| `PlayerGUIOperationMessage` | record | CustomPacketPayload | — | 6 | 154 | [PlayerGUIOperationMessage.java](dev/ftb/mods/ftbteams/net/PlayerGUIOperationMessage.java) |
| `ColorProperty` | class | TeamProperty | — | 5 | 50 | [ColorProperty.java](dev/ftb/mods/ftbteams/api/property/ColorProperty.java) |
| `StringMapProperty` | class | TeamProperty | — | 9 | 122 | [StringMapProperty.java](dev/ftb/mods/ftbteams/api/property/StringMapProperty.java) |
| `IntProperty` | class | TeamProperty | — | 8 | 85 | [IntProperty.java](dev/ftb/mods/ftbteams/api/property/IntProperty.java) |
| `StringSetProperty` | class | TeamProperty | — | 9 | 78 | [StringSetProperty.java](dev/ftb/mods/ftbteams/api/property/StringSetProperty.java) |
| `PrivacyProperty` | class | TeamProperty | — | 5 | 48 | [PrivacyProperty.java](dev/ftb/mods/ftbteams/api/property/PrivacyProperty.java) |
| `EnumProperty` | class | TeamProperty | — | 8 | 92 | [EnumProperty.java](dev/ftb/mods/ftbteams/api/property/EnumProperty.java) |
| `StringListProperty` | class | TeamProperty | — | 9 | 76 | [StringListProperty.java](dev/ftb/mods/ftbteams/api/property/StringListProperty.java) |
| `BooleanProperty` | class | TeamProperty | — | 8 | 71 | [BooleanProperty.java](dev/ftb/mods/ftbteams/api/property/BooleanProperty.java) |
| `BigIntegerProperty` | class | TeamProperty | — | 8 | 70 | [BigIntegerProperty.java](dev/ftb/mods/ftbteams/api/property/BigIntegerProperty.java) |
| `DoubleProperty` | class | TeamProperty | — | 8 | 81 | [DoubleProperty.java](dev/ftb/mods/ftbteams/api/property/DoubleProperty.java) |
| `TeamPropertyValue` | class | — | — | 8 | 52 | [TeamPropertyValue.java](dev/ftb/mods/ftbteams/api/property/TeamPropertyValue.java) |
| `ArgumentTypes` | class | — | — | 0 | 20 | [ArgumentTypes.java](dev/ftb/mods/ftbteams/neoforge/ArgumentTypes.java) |
| `CreatePartyMessage` | record | CustomPacketPayload | — | 2 | 50 | [CreatePartyMessage.java](dev/ftb/mods/ftbteams/net/CreatePartyMessage.java) |
| `SyncMessageHistoryMessage` | record | CustomPacketPayload | — | 3 | 42 | [SyncMessageHistoryMessage.java](dev/ftb/mods/ftbteams/net/SyncMessageHistoryMessage.java) |
| `UpdatePresenceMessage` | record | CustomPacketPayload | — | 2 | 27 | [UpdatePresenceMessage.java](dev/ftb/mods/ftbteams/net/UpdatePresenceMessage.java) |
| `SendMessageResponseMessage` | record | CustomPacketPayload | — | 2 | 33 | [SendMessageResponseMessage.java](dev/ftb/mods/ftbteams/net/SendMessageResponseMessage.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ServerConfig` | — | [ServerConfig.java](dev/ftb/mods/ftbteams/config/ServerConfig.java) |

## 全部类（88）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TeamManagerImpl` | class | TeamManager | — | 33 | 472 | [TeamManagerImpl.java](dev/ftb/mods/ftbteams/data/TeamManagerImpl.java) |
| `ClientTeamManagerImpl` | class | ClientTeamManager | — | 23 | 223 | [ClientTeamManagerImpl.java](dev/ftb/mods/ftbteams/data/ClientTeamManagerImpl.java) |
| `FTBTeamsNeoForge` | class | — | Mod | 0 | 14 | [FTBTeamsNeoForge.java](dev/ftb/mods/ftbteams/neoforge/FTBTeamsNeoForge.java) |
| `FTBTeamsCommands` | class | — | — | 27 | 586 | [FTBTeamsCommands.java](dev/ftb/mods/ftbteams/data/FTBTeamsCommands.java) |
| `MyTeamScreen` | class | BaseScreen | — | 34 | 431 | [MyTeamScreen.java](dev/ftb/mods/ftbteams/client/gui/MyTeamScreen.java) |
| `TeamManagerEvent` | class | — | — | 2 | 28 | [TeamManagerEvent.java](dev/ftb/mods/ftbteams/api/event/TeamManagerEvent.java) |
| `AbstractTeamBase` | class | Team | — | 35 | 262 | [AbstractTeamBase.java](dev/ftb/mods/ftbteams/data/AbstractTeamBase.java) |
| `ClientTeamManager` | interface | — | — | 0 | 32 | [ClientTeamManager.java](dev/ftb/mods/ftbteams/api/client/ClientTeamManager.java) |
| `PartyTeam` | class | AbstractTeam | — | 22 | 436 | [PartyTeam.java](dev/ftb/mods/ftbteams/data/PartyTeam.java) |
| `ClientTeam` | class | AbstractTeamBase | — | 21 | 172 | [ClientTeam.java](dev/ftb/mods/ftbteams/data/ClientTeam.java) |
| `CreatePartyScreen` | class | BaseScreen | — | 18 | 219 | [CreatePartyScreen.java](dev/ftb/mods/ftbteams/client/gui/CreatePartyScreen.java) |
| `CustomPartyCreationHandler` | interface | — | — | 0 | 10 | [CustomPartyCreationHandler.java](dev/ftb/mods/ftbteams/api/CustomPartyCreationHandler.java) |
| `TeamManager` | interface | — | — | 0 | 52 | [TeamManager.java](dev/ftb/mods/ftbteams/api/TeamManager.java) |
| `ClientTeamPropertiesChangedEvent` | class | — | — | 2 | 23 | [ClientTeamPropertiesChangedEvent.java](dev/ftb/mods/ftbteams/api/event/ClientTeamPropertiesChangedEvent.java) |
| `BaseInvitationScreen` | class | BaseScreen | — | 16 | 142 | [BaseInvitationScreen.java](dev/ftb/mods/ftbteams/client/gui/BaseInvitationScreen.java) |
| `ServerConfig` | interface | — | — | 0 | 34 | [ServerConfig.java](dev/ftb/mods/ftbteams/config/ServerConfig.java) |
| `AbstractTeam` | class | AbstractTeamBase | — | 16 | 296 | [AbstractTeam.java](dev/ftb/mods/ftbteams/data/AbstractTeam.java) |
| `TeamProperty` | class | — | — | 24 | 136 | [TeamProperty.java](dev/ftb/mods/ftbteams/api/property/TeamProperty.java) |
| `FTBTeamsClient` | class | — | — | 12 | 133 | [FTBTeamsClient.java](dev/ftb/mods/ftbteams/client/FTBTeamsClient.java) |
| `TeamArgument` | class | ArgumentType<TeamArgumentProvider> | — | 15 | 195 | [TeamArgument.java](dev/ftb/mods/ftbteams/data/TeamArgument.java) |
| `PlayerTeam` | class | AbstractTeam | — | 18 | 128 | [PlayerTeam.java](dev/ftb/mods/ftbteams/data/PlayerTeam.java) |
| `TeamCollectPropertiesEvent` | class | — | — | 1 | 17 | [TeamCollectPropertiesEvent.java](dev/ftb/mods/ftbteams/api/event/TeamCollectPropertiesEvent.java) |
| `TeamInfoEvent` | class | TeamEvent | — | 2 | 23 | [TeamInfoEvent.java](dev/ftb/mods/ftbteams/api/event/TeamInfoEvent.java) |
| `PlayerLeftPartyTeamEvent` | class | TeamEvent | — | 4 | 39 | [PlayerLeftPartyTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerLeftPartyTeamEvent.java) |
| `PlayerLoggedInAfterTeamEvent` | class | TeamEvent | — | 1 | 18 | [PlayerLoggedInAfterTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerLoggedInAfterTeamEvent.java) |
| `TeamCreatedEvent` | class | TeamEvent | — | 2 | 27 | [TeamCreatedEvent.java](dev/ftb/mods/ftbteams/api/event/TeamCreatedEvent.java) |
| `PlayerChangedTeamEvent` | class | TeamEvent | — | 3 | 34 | [PlayerChangedTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerChangedTeamEvent.java) |
| `PlayerJoinedPartyTeamEvent` | class | TeamEvent | — | 2 | 26 | [PlayerJoinedPartyTeamEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerJoinedPartyTeamEvent.java) |
| `TeamPropertiesChangedEvent` | class | TeamEvent | — | 1 | 18 | [TeamPropertiesChangedEvent.java](dev/ftb/mods/ftbteams/api/event/TeamPropertiesChangedEvent.java) |
| `PlayerTransferredTeamOwnershipEvent` | class | TeamEvent | — | 3 | 42 | [PlayerTransferredTeamOwnershipEvent.java](dev/ftb/mods/ftbteams/api/event/PlayerTransferredTeamOwnershipEvent.java) |
| `TeamEvent` | class | — | — | 1 | 38 | [TeamEvent.java](dev/ftb/mods/ftbteams/api/event/TeamEvent.java) |
| `TeamAllyEvent` | class | TeamEvent | — | 2 | 26 | [TeamAllyEvent.java](dev/ftb/mods/ftbteams/api/event/TeamAllyEvent.java) |
| `FTBTeams` | class | — | — | 11 | 177 | [FTBTeams.java](dev/ftb/mods/ftbteams/FTBTeams.java) |
| `FTBTUtils` | class | — | — | 5 | 70 | [FTBTUtils.java](dev/ftb/mods/ftbteams/data/FTBTUtils.java) |
| `TeamPropertyCollectionImpl` | class | TeamPropertyCollection | — | 12 | 173 | [TeamPropertyCollectionImpl.java](dev/ftb/mods/ftbteams/data/TeamPropertyCollectionImpl.java) |
| `TeamStagesHelper` | class | — | — | 8 | 67 | [TeamStagesHelper.java](dev/ftb/mods/ftbteams/api/TeamStagesHelper.java) |
| `TeamRank` | enum | StringRepresentable | — | 12 | 82 | [TeamRank.java](dev/ftb/mods/ftbteams/api/TeamRank.java) |
| `TeamPropertyArgument` | class | ArgumentTypeInfo<TeamPropertyArgumen | — | 10 | 87 | [TeamPropertyArgument.java](dev/ftb/mods/ftbteams/api/property/TeamPropertyArgument.java) |
| `KnownClientPlayer` | record | — | — | 4 | 34 | [KnownClientPlayer.java](dev/ftb/mods/ftbteams/api/client/KnownClientPlayer.java) |
| `KnownClientPlayerNet` | class | — | — | 2 | 46 | [KnownClientPlayerNet.java](dev/ftb/mods/ftbteams/client/KnownClientPlayerNet.java) |
| `AllyScreen` | class | BaseInvitationScreen | — | 4 | 60 | [AllyScreen.java](dev/ftb/mods/ftbteams/client/gui/AllyScreen.java) |
| `InviteScreen` | class | BaseInvitationScreen | — | 3 | 32 | [InviteScreen.java](dev/ftb/mods/ftbteams/client/gui/InviteScreen.java) |
| `FTBTeamsAPIImpl` | enum | FTBTeamsAPI.API | — | 9 | 62 | [FTBTeamsAPIImpl.java](dev/ftb/mods/ftbteams/FTBTeamsAPIImpl.java) |
| `PlayerGUIOperationMessage` | record | CustomPacketPayload | — | 6 | 154 | [PlayerGUIOperationMessage.java](dev/ftb/mods/ftbteams/net/PlayerGUIOperationMessage.java) |
| `ColorProperty` | class | TeamProperty | — | 5 | 50 | [ColorProperty.java](dev/ftb/mods/ftbteams/api/property/ColorProperty.java) |
| `StringMapProperty` | class | TeamProperty | — | 9 | 122 | [StringMapProperty.java](dev/ftb/mods/ftbteams/api/property/StringMapProperty.java) |
| `IntProperty` | class | TeamProperty | — | 8 | 85 | [IntProperty.java](dev/ftb/mods/ftbteams/api/property/IntProperty.java) |
| `StringSetProperty` | class | TeamProperty | — | 9 | 78 | [StringSetProperty.java](dev/ftb/mods/ftbteams/api/property/StringSetProperty.java) |
| `PrivacyProperty` | class | TeamProperty | — | 5 | 48 | [PrivacyProperty.java](dev/ftb/mods/ftbteams/api/property/PrivacyProperty.java) |
| `EnumProperty` | class | TeamProperty | — | 8 | 92 | [EnumProperty.java](dev/ftb/mods/ftbteams/api/property/EnumProperty.java) |
| `StringListProperty` | class | TeamProperty | — | 9 | 76 | [StringListProperty.java](dev/ftb/mods/ftbteams/api/property/StringListProperty.java) |
| `BooleanProperty` | class | TeamProperty | — | 8 | 71 | [BooleanProperty.java](dev/ftb/mods/ftbteams/api/property/BooleanProperty.java) |
| `BigIntegerProperty` | class | TeamProperty | — | 8 | 70 | [BigIntegerProperty.java](dev/ftb/mods/ftbteams/api/property/BigIntegerProperty.java) |
| `DoubleProperty` | class | TeamProperty | — | 8 | 81 | [DoubleProperty.java](dev/ftb/mods/ftbteams/api/property/DoubleProperty.java) |
| `TeamPropertyValue` | class | — | — | 8 | 52 | [TeamPropertyValue.java](dev/ftb/mods/ftbteams/api/property/TeamPropertyValue.java) |
| `ArgumentTypes` | class | — | — | 0 | 20 | [ArgumentTypes.java](dev/ftb/mods/ftbteams/neoforge/ArgumentTypes.java) |
| `CreatePartyMessage` | record | CustomPacketPayload | — | 2 | 50 | [CreatePartyMessage.java](dev/ftb/mods/ftbteams/net/CreatePartyMessage.java) |
| `SyncMessageHistoryMessage` | record | CustomPacketPayload | — | 3 | 42 | [SyncMessageHistoryMessage.java](dev/ftb/mods/ftbteams/net/SyncMessageHistoryMessage.java) |
| `UpdatePresenceMessage` | record | CustomPacketPayload | — | 2 | 27 | [UpdatePresenceMessage.java](dev/ftb/mods/ftbteams/net/UpdatePresenceMessage.java) |
| `SendMessageResponseMessage` | record | CustomPacketPayload | — | 2 | 33 | [SendMessageResponseMessage.java](dev/ftb/mods/ftbteams/net/SendMessageResponseMessage.java) |
| `ToggleChatRedirectionMessage` | enum | CustomPacketPayload | — | 2 | 31 | [ToggleChatRedirectionMessage.java](dev/ftb/mods/ftbteams/net/ToggleChatRedirectionMessage.java) |
| `FTBTeamsNet` | class | — | — | 1 | 22 | [FTBTeamsNet.java](dev/ftb/mods/ftbteams/net/FTBTeamsNet.java) |
| `UpdatePropertiesRequestMessage` | record | CustomPacketPayload | — | 2 | 37 | [UpdatePropertiesRequestMessage.java](dev/ftb/mods/ftbteams/net/UpdatePropertiesRequestMessage.java) |
| `SendMessageMessage` | record | CustomPacketPayload | — | 2 | 29 | [SendMessageMessage.java](dev/ftb/mods/ftbteams/net/SendMessageMessage.java) |
| `UpdatePropertiesResponseMessage` | record | CustomPacketPayload | — | 3 | 39 | [UpdatePropertiesResponseMessage.java](dev/ftb/mods/ftbteams/net/UpdatePropertiesResponseMessage.java) |
| `OpenGUIMessage` | record | CustomPacketPayload | — | 3 | 39 | [OpenGUIMessage.java](dev/ftb/mods/ftbteams/net/OpenGUIMessage.java) |
| `OpenMyTeamGUIMessage` | record | CustomPacketPayload | — | 2 | 32 | [OpenMyTeamGUIMessage.java](dev/ftb/mods/ftbteams/net/OpenMyTeamGUIMessage.java) |
| `ToggleChatResponseMessage` | record | CustomPacketPayload | — | 2 | 26 | [ToggleChatResponseMessage.java](dev/ftb/mods/ftbteams/net/ToggleChatResponseMessage.java) |
| `SyncTeamsMessage` | record | CustomPacketPayload | — | 2 | 38 | [SyncTeamsMessage.java](dev/ftb/mods/ftbteams/net/SyncTeamsMessage.java) |
| `TeamMessageImpl` | record | TeamMessage | — | 2 | 40 | [TeamMessageImpl.java](dev/ftb/mods/ftbteams/data/TeamMessageImpl.java) |
| `TeamArgumentProvider` | interface | — | — | 0 | 11 | [TeamArgumentProvider.java](dev/ftb/mods/ftbteams/data/TeamArgumentProvider.java) |
| `TeamType` | enum | StringRepresentable | — | 4 | 43 | [TeamType.java](dev/ftb/mods/ftbteams/data/TeamType.java) |
| `PlayerPermissions` | record | — | — | 1 | 28 | [PlayerPermissions.java](dev/ftb/mods/ftbteams/data/PlayerPermissions.java) |
| `ServerTeam` | class | AbstractTeam | — | 3 | 34 | [ServerTeam.java](dev/ftb/mods/ftbteams/data/ServerTeam.java) |
| `MethodsReturnNonnullByDefault` | @interface | — | Retention | 0 | 9 | [MethodsReturnNonnullByDefault.java](dev/ftb/mods/ftbteams/api/MethodsReturnNonnullByDefault.java) |
| `Team` | interface | — | — | 0 | 82 | [Team.java](dev/ftb/mods/ftbteams/api/Team.java) |
| `FTBTeamsAPI` | class | — | — | 3 | 54 | [FTBTeamsAPI.java](dev/ftb/mods/ftbteams/api/FTBTeamsAPI.java) |
| `TeamMessage` | interface | — | — | 0 | 13 | [TeamMessage.java](dev/ftb/mods/ftbteams/api/TeamMessage.java) |
| `TeamPropertyCollection` | interface | — | — | 0 | 21 | [TeamPropertyCollection.java](dev/ftb/mods/ftbteams/api/property/TeamPropertyCollection.java) |
| `TeamProperties` | class | — | — | 0 | 22 | [TeamProperties.java](dev/ftb/mods/ftbteams/api/property/TeamProperties.java) |
| `TeamPropertyType` | class | — | — | 4 | 79 | [TeamPropertyType.java](dev/ftb/mods/ftbteams/api/property/TeamPropertyType.java) |
| `PrivacyMode` | enum | StringRepresentable | — | 1 | 23 | [PrivacyMode.java](dev/ftb/mods/ftbteams/api/property/PrivacyMode.java) |
| `StringProperty` | class | TeamProperty | — | 4 | 62 | [StringProperty.java](dev/ftb/mods/ftbteams/api/property/StringProperty.java) |
| `MemberButton` | class | NordButton | — | 2 | 145 | [MemberButton.java](dev/ftb/mods/ftbteams/client/gui/MemberButton.java) |
| `InvitationSetup` | interface | — | — | 0 | 10 | [InvitationSetup.java](dev/ftb/mods/ftbteams/client/gui/InvitationSetup.java) |
| `InvitedButton` | class | NordButton | — | 3 | 55 | [InvitedButton.java](dev/ftb/mods/ftbteams/client/gui/InvitedButton.java) |
| `CreatePartyButton` | class | NordButton | — | 3 | 42 | [CreatePartyButton.java](dev/ftb/mods/ftbteams/client/gui/CreatePartyButton.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_FTBTeams121_common_8398905a4d6a419a8e489951fc7e3f58_07d49edeb1e7f5375db394665932fc8ceafc1e709d536833f97ba698e6e52ce0ftbteams2101110devjar/PlatformMethods.java) |
