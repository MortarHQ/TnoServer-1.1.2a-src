# Reliquified L_Ender's Cataclysm（`reliquified_lenders_cataclysm`）

> jar：`[遗物×灾变]reliquifiedlenderscataclysm.jar` ｜ 版本：`0.1.1` ｜ 作者：Octo-Studios
> 规模：**26 个类 / 15 个包 / 2485 行** ｜ 事件处理类 6 ｜ Mixin 1 ｜ 注册/管理类 5

**依赖**：`minecraft`、`neoforge`、`curios`、`octolib`、`relics`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `it.hurts.octostudios.reliquified_lenders_cataclysm.init` | 4 | `RemoteRegistry`、`ItemRegistry`、`EntityRegistry` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.entities` | 3 | `VoidVortexModifiedEntity`、`ScreenShakeSoundedEntity`、`VoidShardModifiedEntity` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.network.packets` | 3 | `VoidVortexParticlesPacket`、`VacuumGloveParticlesPacket`、`VoidVortexMotionPacket` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.utils` | 3 | `ItemUtils`、`RECMathUtils`、`RECParticleUtils` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.charm` | 2 | `VoidVortexInBottleItem`、`ScouringEyeItem` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.utils.relics` | 2 | `ScouringEyeUtils`、`VoidCloakUtils` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.network` | 1 | `RECNetworkHandler` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.head` | 1 | `VoidBubbleItem` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.back` | 1 | `VoidCloakItem` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm` | 1 | `ReliquifiedLendersCataclysm` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.mixin` | 1 | `MinecraftMixin` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.hands` | 1 | `VacuumGloveItem` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.data` | 1 | `RECLootEntries` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.client.renderer` | 1 | `VoidVortexModifiedRenderer` |
| `it.hurts.octostudios.reliquified_lenders_cataclysm.items.base` | 1 | `RECItem` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 26）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RECNetworkHandler` | class | — | EventBusSubscriber | 1 | 23 | [RECNetworkHandler.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/RECNetworkHandler.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 1 | 18 | [RemoteRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/RemoteRegistry.java) |
| `VoidBubbleItem` | class | RECItem | EventBusSubscriber | 10 | 223 | [VoidBubbleItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/head/VoidBubbleItem.java) |
| `VoidCloakItem` | class | RECItem | EventBusSubscriber | 5 | 214 | [VoidCloakItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/back/VoidCloakItem.java) |
| `VoidVortexInBottleItem` | class | RECItem | EventBusSubscriber | 7 | 153 | [VoidVortexInBottleItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/charm/VoidVortexInBottleItem.java) |
| `ScouringEyeItem` | class | RECItem | EventBusSubscriber | 5 | 191 | [ScouringEyeItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/charm/ScouringEyeItem.java) |
| `ReliquifiedLendersCataclysm` | class | — | Mod | 1 | 24 | [ReliquifiedLendersCataclysm.java](it/hurts/octostudios/reliquified_lenders_cataclysm/ReliquifiedLendersCataclysm.java) |
| `VoidVortexModifiedEntity` | class | Entity | — | 33 | 392 | [VoidVortexModifiedEntity.java](it/hurts/octostudios/reliquified_lenders_cataclysm/entities/VoidVortexModifiedEntity.java) |
| `ItemRegistry` | class | — | — | 1 | 26 | [ItemRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 47 | [EntityRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/EntityRegistry.java) |
| `RECDataComponentRegistry` | class | — | — | 4 | 44 | [RECDataComponentRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/RECDataComponentRegistry.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 37 | [MinecraftMixin.java](it/hurts/octostudios/reliquified_lenders_cataclysm/mixin/MinecraftMixin.java) |
| `VoidVortexParticlesPacket` | record | CustomPacketPayload | — | 6 | 121 | [VoidVortexParticlesPacket.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/packets/VoidVortexParticlesPacket.java) |
| `VacuumGloveItem` | class | RECItem | — | 11 | 197 | [VacuumGloveItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/hands/VacuumGloveItem.java) |
| `ScreenShakeSoundedEntity` | class | ScreenShake_Entity | — | 2 | 44 | [ScreenShakeSoundedEntity.java](it/hurts/octostudios/reliquified_lenders_cataclysm/entities/ScreenShakeSoundedEntity.java) |
| `ItemUtils` | class | — | — | 8 | 62 | [ItemUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/ItemUtils.java) |
| `ScouringEyeUtils` | class | — | — | 19 | 143 | [ScouringEyeUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/relics/ScouringEyeUtils.java) |
| `VacuumGloveParticlesPacket` | record | CustomPacketPayload | — | 4 | 62 | [VacuumGloveParticlesPacket.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/packets/VacuumGloveParticlesPacket.java) |
| `VoidVortexMotionPacket` | record | CustomPacketPayload | — | 4 | 48 | [VoidVortexMotionPacket.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/packets/VoidVortexMotionPacket.java) |
| `RECLootEntries` | class | — | — | 0 | 19 | [RECLootEntries.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/base/data/RECLootEntries.java) |
| `VoidVortexModifiedRenderer` | class | EntityRenderer | — | 6 | 97 | [VoidVortexModifiedRenderer.java](it/hurts/octostudios/reliquified_lenders_cataclysm/client/renderer/VoidVortexModifiedRenderer.java) |
| `RECItem` | class | RelicItem | — | 2 | 15 | [RECItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/base/RECItem.java) |
| `VoidShardModifiedEntity` | class | Void_Shard_Entity | — | 1 | 37 | [VoidShardModifiedEntity.java](it/hurts/octostudios/reliquified_lenders_cataclysm/entities/VoidShardModifiedEntity.java) |
| `VoidCloakUtils` | class | — | — | 9 | 138 | [VoidCloakUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/relics/VoidCloakUtils.java) |
| `RECMathUtils` | class | — | — | 4 | 22 | [RECMathUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/RECMathUtils.java) |
| `RECParticleUtils` | class | — | — | 2 | 88 | [RECParticleUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/RECParticleUtils.java) |

## 事件处理（`@SubscribeEvent`，6 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `VoidCloakItem` | 3 | `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.back` | [VoidCloakItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/back/VoidCloakItem.java) |
| `VoidBubbleItem` | 2 | `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.head` | [VoidBubbleItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/head/VoidBubbleItem.java) |
| `ScouringEyeItem` | 2 | `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.charm` | [ScouringEyeItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/charm/ScouringEyeItem.java) |
| `RECNetworkHandler` | 1 | `it.hurts.octostudios.reliquified_lenders_cataclysm.network` | [RECNetworkHandler.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/RECNetworkHandler.java) |
| `RemoteRegistry` | 1 | `it.hurts.octostudios.reliquified_lenders_cataclysm.init` | [RemoteRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/RemoteRegistry.java) |
| `VoidVortexInBottleItem` | 1 | `it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.charm` | [VoidVortexInBottleItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/charm/VoidVortexInBottleItem.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](it/hurts/octostudios/reliquified_lenders_cataclysm/mixin/MinecraftMixin.java) |

## 全部类（26）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RECNetworkHandler` | class | — | EventBusSubscriber | 1 | 23 | [RECNetworkHandler.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/RECNetworkHandler.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 1 | 18 | [RemoteRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/RemoteRegistry.java) |
| `VoidBubbleItem` | class | RECItem | EventBusSubscriber | 10 | 223 | [VoidBubbleItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/head/VoidBubbleItem.java) |
| `VoidCloakItem` | class | RECItem | EventBusSubscriber | 5 | 214 | [VoidCloakItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/back/VoidCloakItem.java) |
| `VoidVortexInBottleItem` | class | RECItem | EventBusSubscriber | 7 | 153 | [VoidVortexInBottleItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/charm/VoidVortexInBottleItem.java) |
| `ScouringEyeItem` | class | RECItem | EventBusSubscriber | 5 | 191 | [ScouringEyeItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/charm/ScouringEyeItem.java) |
| `ReliquifiedLendersCataclysm` | class | — | Mod | 1 | 24 | [ReliquifiedLendersCataclysm.java](it/hurts/octostudios/reliquified_lenders_cataclysm/ReliquifiedLendersCataclysm.java) |
| `VoidVortexModifiedEntity` | class | Entity | — | 33 | 392 | [VoidVortexModifiedEntity.java](it/hurts/octostudios/reliquified_lenders_cataclysm/entities/VoidVortexModifiedEntity.java) |
| `ItemRegistry` | class | — | — | 1 | 26 | [ItemRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 47 | [EntityRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/EntityRegistry.java) |
| `RECDataComponentRegistry` | class | — | — | 4 | 44 | [RECDataComponentRegistry.java](it/hurts/octostudios/reliquified_lenders_cataclysm/init/RECDataComponentRegistry.java) |
| `MinecraftMixin` | class | — | Mixin | 1 | 37 | [MinecraftMixin.java](it/hurts/octostudios/reliquified_lenders_cataclysm/mixin/MinecraftMixin.java) |
| `VoidVortexParticlesPacket` | record | CustomPacketPayload | — | 6 | 121 | [VoidVortexParticlesPacket.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/packets/VoidVortexParticlesPacket.java) |
| `VacuumGloveItem` | class | RECItem | — | 11 | 197 | [VacuumGloveItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/relics/hands/VacuumGloveItem.java) |
| `ScreenShakeSoundedEntity` | class | ScreenShake_Entity | — | 2 | 44 | [ScreenShakeSoundedEntity.java](it/hurts/octostudios/reliquified_lenders_cataclysm/entities/ScreenShakeSoundedEntity.java) |
| `ItemUtils` | class | — | — | 8 | 62 | [ItemUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/ItemUtils.java) |
| `ScouringEyeUtils` | class | — | — | 19 | 143 | [ScouringEyeUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/relics/ScouringEyeUtils.java) |
| `VacuumGloveParticlesPacket` | record | CustomPacketPayload | — | 4 | 62 | [VacuumGloveParticlesPacket.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/packets/VacuumGloveParticlesPacket.java) |
| `VoidVortexMotionPacket` | record | CustomPacketPayload | — | 4 | 48 | [VoidVortexMotionPacket.java](it/hurts/octostudios/reliquified_lenders_cataclysm/network/packets/VoidVortexMotionPacket.java) |
| `RECLootEntries` | class | — | — | 0 | 19 | [RECLootEntries.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/base/data/RECLootEntries.java) |
| `VoidVortexModifiedRenderer` | class | EntityRenderer | — | 6 | 97 | [VoidVortexModifiedRenderer.java](it/hurts/octostudios/reliquified_lenders_cataclysm/client/renderer/VoidVortexModifiedRenderer.java) |
| `RECItem` | class | RelicItem | — | 2 | 15 | [RECItem.java](it/hurts/octostudios/reliquified_lenders_cataclysm/items/base/RECItem.java) |
| `VoidShardModifiedEntity` | class | Void_Shard_Entity | — | 1 | 37 | [VoidShardModifiedEntity.java](it/hurts/octostudios/reliquified_lenders_cataclysm/entities/VoidShardModifiedEntity.java) |
| `VoidCloakUtils` | class | — | — | 9 | 138 | [VoidCloakUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/relics/VoidCloakUtils.java) |
| `RECMathUtils` | class | — | — | 4 | 22 | [RECMathUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/RECMathUtils.java) |
| `RECParticleUtils` | class | — | — | 2 | 88 | [RECParticleUtils.java](it/hurts/octostudios/reliquified_lenders_cataclysm/utils/RECParticleUtils.java) |
