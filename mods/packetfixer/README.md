# PacketFixer（`packetfixer`）

> jar：`[优化]packetfixer.jar` ｜ 版本：`3.3.1` ｜ 作者：TonimatasDEV
> 规模：**43 个类 / 4 个包 / 1019 行** ｜ 事件处理类 0 ｜ Mixin 38 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.tonimatas.packetfixer.mixins.v1_20_5_fabric` | 21 | `ClientboundLevelChunkPacketDataMixin`、`ClientboundCustomQueryPacketMixin`、`ClientboundCustomPayloadPacketMixin` |
| `dev.tonimatas.packetfixer.mixins.v1_20_5_neoforge` | 19 | `ClientboundLevelChunkPacketDataMixin`、`ClientboundCustomQueryPacketMixin`、`ClientboundCustomPayloadPacketMixin` |
| `dev.tonimatas.packetfixer.common` | 2 | `Config`、`Messages` |
| `dev.tonimatas.packetfixer` | 1 | `NeoPacketFixerMod` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 43）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoPacketFixerMod` | class | — | Mod | 0 | 8 | [NeoPacketFixerMod.java](dev/tonimatas/packetfixer/NeoPacketFixerMod.java) |
| `ClientboundLevelChunkPacketDataMixin` | class | — | Mixin | 0 | 24 | [ClientboundLevelChunkPacketDataMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundLevelChunkPacketDataMixin.java) |
| `ClientboundLevelChunkPacketDataMixin` | class | — | Mixin | 0 | 24 | [ClientboundLevelChunkPacketDataMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundLevelChunkPacketDataMixin.java) |
| `ClientboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ClientboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundCustomQueryPacketMixin.java) |
| `ClientboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 21 | [ClientboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundCustomPayloadPacketMixin.java) |
| `ClientboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ClientboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundCustomQueryPacketMixin.java) |
| `ClientboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 21 | [ClientboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundCustomPayloadPacketMixin.java) |
| `ServerboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ServerboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerboundCustomQueryPacketMixin.java) |
| `ServerboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 21 | [ServerboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerboundCustomPayloadPacketMixin.java) |
| `ServerCommonPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerCommonPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerCommonPacketListenerImplMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerGamePacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerGamePacketListenerImplMixin.java) |
| `ServerLoginPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerLoginPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerLoginPacketListenerImplMixin.java) |
| `ServerboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ServerboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerboundCustomQueryPacketMixin.java) |
| `ServerboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 16 | [ServerboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerboundCustomPayloadPacketMixin.java) |
| `ServerCommonPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerCommonPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerCommonPacketListenerImplMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerGamePacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerGamePacketListenerImplMixin.java) |
| `ServerLoginPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerLoginPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerLoginPacketListenerImplMixin.java) |
| `PayloadHelperMixin` | class | — | Mixin | 0 | 17 | [PayloadHelperMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/PayloadHelperMixin.java) |
| `VarIntMixin` | class | — | Mixin | 0 | 26 | [VarIntMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/VarIntMixin.java) |
| `CompressionDecoderMixin` | class | — | Mixin | 0 | 16 | [CompressionDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/CompressionDecoderMixin.java) |
| `Varint21FrameDecoderMixin` | class | — | Mixin | 0 | 36 | [Varint21FrameDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/Varint21FrameDecoderMixin.java) |
| `ConnectionMixin` | class | — | Mixin | 0 | 15 | [ConnectionMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ConnectionMixin.java) |
| `FriendlyByteBufMixin` | class | — | Mixin | 0 | 31 | [FriendlyByteBufMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/FriendlyByteBufMixin.java) |
| `CompressionEncoderMixin` | class | — | Mixin | 0 | 19 | [CompressionEncoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/CompressionEncoderMixin.java) |
| `Varint21LengthFieldPrependerMixin` | class | — | Mixin | 0 | 19 | [Varint21LengthFieldPrependerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/Varint21LengthFieldPrependerMixin.java) |
| `VarLongMixin` | class | — | Mixin | 0 | 26 | [VarLongMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/VarLongMixin.java) |
| `NbtAccounterMixin` | class | — | Mixin | 0 | 22 | [NbtAccounterMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/NbtAccounterMixin.java) |
| `ByteBufCodecsMixin` | interface | — | Mixin | 1 | 16 | [ByteBufCodecsMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ByteBufCodecsMixin.java) |
| `VarIntMixin` | class | — | Mixin | 0 | 26 | [VarIntMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/VarIntMixin.java) |
| `CompressionDecoderMixin` | class | — | Mixin | 0 | 16 | [CompressionDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/CompressionDecoderMixin.java) |
| `Varint21FrameDecoderMixin` | class | — | Mixin | 0 | 36 | [Varint21FrameDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/Varint21FrameDecoderMixin.java) |
| `ConnectionMixin` | class | — | Mixin | 0 | 15 | [ConnectionMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ConnectionMixin.java) |
| `FriendlyByteBufMixin` | class | — | Mixin | 0 | 31 | [FriendlyByteBufMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/FriendlyByteBufMixin.java) |
| `CompressionEncoderMixin` | class | — | Mixin | 0 | 19 | [CompressionEncoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/CompressionEncoderMixin.java) |
| `Varint21LengthFieldPrependerMixin` | class | — | Mixin | 0 | 19 | [Varint21LengthFieldPrependerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/Varint21LengthFieldPrependerMixin.java) |
| `VarLongMixin` | class | — | Mixin | 0 | 26 | [VarLongMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/VarLongMixin.java) |
| `NbtAccounterMixin` | class | — | Mixin | 0 | 22 | [NbtAccounterMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/NbtAccounterMixin.java) |
| `ByteBufCodecsMixin` | interface | — | Mixin | 1 | 16 | [ByteBufCodecsMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ByteBufCodecsMixin.java) |
| `ServerConnectionListenerMixin` | class | — | Mixin | 0 | 15 | [ServerConnectionListenerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerConnectionListenerMixin.java) |
| `Config` | class | — | — | 13 | 107 | [Config.java](dev/tonimatas/packetfixer/common/Config.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 8 | 48 | [MixinPlugin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/MixinPlugin.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 8 | 53 | [MixinPlugin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/MixinPlugin.java) |
| `Messages` | class | — | — | 1 | 8 | [Messages.java](dev/tonimatas/packetfixer/common/Messages.java) |

## Mixin（38 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientboundLevelChunkPacketDataMixin` | `ClientboundLevelChunkPacketData` | [ClientboundLevelChunkPacketDataMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundLevelChunkPacketDataMixin.java) |
| `ClientboundLevelChunkPacketDataMixin` | `ClientboundLevelChunkPacketData` | [ClientboundLevelChunkPacketDataMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundLevelChunkPacketDataMixin.java) |
| `ClientboundCustomQueryPacketMixin` | `ClientboundCustomQueryPacket` | [ClientboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundCustomQueryPacketMixin.java) |
| `ClientboundCustomPayloadPacketMixin` | `ClientboundCustomPayloadPacket` | [ClientboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundCustomPayloadPacketMixin.java) |
| `ClientboundCustomQueryPacketMixin` | `ClientboundCustomQueryPacket` | [ClientboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundCustomQueryPacketMixin.java) |
| `ClientboundCustomPayloadPacketMixin` | `ClientboundCustomPayloadPacket` | [ClientboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundCustomPayloadPacketMixin.java) |
| `ServerboundCustomQueryPacketMixin` | `ServerboundCustomQueryPacket` | [ServerboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerboundCustomQueryPacketMixin.java) |
| `ServerboundCustomPayloadPacketMixin` | `ServerboundCustomPayloadPacket` | [ServerboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerboundCustomPayloadPacketMixin.java) |
| `ServerCommonPacketListenerImplMixin` | `ServerCommonPacketListenerImpl` | [ServerCommonPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerCommonPacketListenerImplMixin.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerGamePacketListenerImplMixin.java) |
| `ServerLoginPacketListenerImplMixin` | `ServerLoginPacketListenerImpl` | [ServerLoginPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerLoginPacketListenerImplMixin.java) |
| `ServerboundCustomQueryPacketMixin` | `ServerboundCustomQueryPacket` | [ServerboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerboundCustomQueryPacketMixin.java) |
| `ServerboundCustomPayloadPacketMixin` | `ServerboundCustomPayloadPacket` | [ServerboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerboundCustomPayloadPacketMixin.java) |
| `ServerCommonPacketListenerImplMixin` | `ServerCommonPacketListenerImpl` | [ServerCommonPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerCommonPacketListenerImplMixin.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerGamePacketListenerImplMixin.java) |
| `ServerLoginPacketListenerImplMixin` | `ServerLoginPacketListenerImpl` | [ServerLoginPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerLoginPacketListenerImplMixin.java) |
| `PayloadHelperMixin` | `PayloadHelper` | [PayloadHelperMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/PayloadHelperMixin.java) |
| `VarIntMixin` | `VarInt` | [VarIntMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/VarIntMixin.java) |
| `CompressionDecoderMixin` | `CompressionDecoder` | [CompressionDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/CompressionDecoderMixin.java) |
| `Varint21FrameDecoderMixin` | `Varint21FrameDecoder` | [Varint21FrameDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/Varint21FrameDecoderMixin.java) |
| `ConnectionMixin` | `Connection` | [ConnectionMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ConnectionMixin.java) |
| `FriendlyByteBufMixin` | `FriendlyByteBuf` | [FriendlyByteBufMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/FriendlyByteBufMixin.java) |
| `CompressionEncoderMixin` | `CompressionEncoder` | [CompressionEncoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/CompressionEncoderMixin.java) |
| `Varint21LengthFieldPrependerMixin` | `Varint21LengthFieldPrepender` | [Varint21LengthFieldPrependerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/Varint21LengthFieldPrependerMixin.java) |
| `VarLongMixin` | `VarLong` | [VarLongMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/VarLongMixin.java) |
| `NbtAccounterMixin` | `NbtAccounter` | [NbtAccounterMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/NbtAccounterMixin.java) |
| `ByteBufCodecsMixin` | `ByteBufCodecs` | [ByteBufCodecsMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ByteBufCodecsMixin.java) |
| `VarIntMixin` | `VarInt` | [VarIntMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/VarIntMixin.java) |
| `CompressionDecoderMixin` | `CompressionDecoder` | [CompressionDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/CompressionDecoderMixin.java) |
| `Varint21FrameDecoderMixin` | `Varint21FrameDecoder` | [Varint21FrameDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/Varint21FrameDecoderMixin.java) |
| `ConnectionMixin` | `Connection` | [ConnectionMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ConnectionMixin.java) |
| `FriendlyByteBufMixin` | `FriendlyByteBuf` | [FriendlyByteBufMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/FriendlyByteBufMixin.java) |
| `CompressionEncoderMixin` | `CompressionEncoder` | [CompressionEncoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/CompressionEncoderMixin.java) |
| `Varint21LengthFieldPrependerMixin` | `Varint21LengthFieldPrepender` | [Varint21LengthFieldPrependerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/Varint21LengthFieldPrependerMixin.java) |
| `VarLongMixin` | `VarLong` | [VarLongMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/VarLongMixin.java) |
| `NbtAccounterMixin` | `NbtAccounter` | [NbtAccounterMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/NbtAccounterMixin.java) |
| `ByteBufCodecsMixin` | `ByteBufCodecs` | [ByteBufCodecsMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ByteBufCodecsMixin.java) |
| `ServerConnectionListenerMixin` | `ServerConnectionListener` | [ServerConnectionListenerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerConnectionListenerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](dev/tonimatas/packetfixer/common/Config.java) |

## 全部类（43）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoPacketFixerMod` | class | — | Mod | 0 | 8 | [NeoPacketFixerMod.java](dev/tonimatas/packetfixer/NeoPacketFixerMod.java) |
| `ClientboundLevelChunkPacketDataMixin` | class | — | Mixin | 0 | 24 | [ClientboundLevelChunkPacketDataMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundLevelChunkPacketDataMixin.java) |
| `ClientboundLevelChunkPacketDataMixin` | class | — | Mixin | 0 | 24 | [ClientboundLevelChunkPacketDataMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundLevelChunkPacketDataMixin.java) |
| `ClientboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ClientboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundCustomQueryPacketMixin.java) |
| `ClientboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 21 | [ClientboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ClientboundCustomPayloadPacketMixin.java) |
| `ClientboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ClientboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundCustomQueryPacketMixin.java) |
| `ClientboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 21 | [ClientboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ClientboundCustomPayloadPacketMixin.java) |
| `ServerboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ServerboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerboundCustomQueryPacketMixin.java) |
| `ServerboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 21 | [ServerboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerboundCustomPayloadPacketMixin.java) |
| `ServerCommonPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerCommonPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerCommonPacketListenerImplMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerGamePacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerGamePacketListenerImplMixin.java) |
| `ServerLoginPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerLoginPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ServerLoginPacketListenerImplMixin.java) |
| `ServerboundCustomQueryPacketMixin` | class | — | Mixin | 0 | 22 | [ServerboundCustomQueryPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerboundCustomQueryPacketMixin.java) |
| `ServerboundCustomPayloadPacketMixin` | class | — | Mixin | 0 | 16 | [ServerboundCustomPayloadPacketMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerboundCustomPayloadPacketMixin.java) |
| `ServerCommonPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerCommonPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerCommonPacketListenerImplMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerGamePacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerGamePacketListenerImplMixin.java) |
| `ServerLoginPacketListenerImplMixin` | class | — | Mixin | 0 | 16 | [ServerLoginPacketListenerImplMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerLoginPacketListenerImplMixin.java) |
| `PayloadHelperMixin` | class | — | Mixin | 0 | 17 | [PayloadHelperMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/PayloadHelperMixin.java) |
| `VarIntMixin` | class | — | Mixin | 0 | 26 | [VarIntMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/VarIntMixin.java) |
| `CompressionDecoderMixin` | class | — | Mixin | 0 | 16 | [CompressionDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/CompressionDecoderMixin.java) |
| `Varint21FrameDecoderMixin` | class | — | Mixin | 0 | 36 | [Varint21FrameDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/Varint21FrameDecoderMixin.java) |
| `ConnectionMixin` | class | — | Mixin | 0 | 15 | [ConnectionMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ConnectionMixin.java) |
| `FriendlyByteBufMixin` | class | — | Mixin | 0 | 31 | [FriendlyByteBufMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/FriendlyByteBufMixin.java) |
| `CompressionEncoderMixin` | class | — | Mixin | 0 | 19 | [CompressionEncoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/CompressionEncoderMixin.java) |
| `Varint21LengthFieldPrependerMixin` | class | — | Mixin | 0 | 19 | [Varint21LengthFieldPrependerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/Varint21LengthFieldPrependerMixin.java) |
| `VarLongMixin` | class | — | Mixin | 0 | 26 | [VarLongMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/VarLongMixin.java) |
| `NbtAccounterMixin` | class | — | Mixin | 0 | 22 | [NbtAccounterMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/NbtAccounterMixin.java) |
| `ByteBufCodecsMixin` | interface | — | Mixin | 1 | 16 | [ByteBufCodecsMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/ByteBufCodecsMixin.java) |
| `VarIntMixin` | class | — | Mixin | 0 | 26 | [VarIntMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/VarIntMixin.java) |
| `CompressionDecoderMixin` | class | — | Mixin | 0 | 16 | [CompressionDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/CompressionDecoderMixin.java) |
| `Varint21FrameDecoderMixin` | class | — | Mixin | 0 | 36 | [Varint21FrameDecoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/Varint21FrameDecoderMixin.java) |
| `ConnectionMixin` | class | — | Mixin | 0 | 15 | [ConnectionMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ConnectionMixin.java) |
| `FriendlyByteBufMixin` | class | — | Mixin | 0 | 31 | [FriendlyByteBufMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/FriendlyByteBufMixin.java) |
| `CompressionEncoderMixin` | class | — | Mixin | 0 | 19 | [CompressionEncoderMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/CompressionEncoderMixin.java) |
| `Varint21LengthFieldPrependerMixin` | class | — | Mixin | 0 | 19 | [Varint21LengthFieldPrependerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/Varint21LengthFieldPrependerMixin.java) |
| `VarLongMixin` | class | — | Mixin | 0 | 26 | [VarLongMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/VarLongMixin.java) |
| `NbtAccounterMixin` | class | — | Mixin | 0 | 22 | [NbtAccounterMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/NbtAccounterMixin.java) |
| `ByteBufCodecsMixin` | interface | — | Mixin | 1 | 16 | [ByteBufCodecsMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ByteBufCodecsMixin.java) |
| `ServerConnectionListenerMixin` | class | — | Mixin | 0 | 15 | [ServerConnectionListenerMixin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/ServerConnectionListenerMixin.java) |
| `Config` | class | — | — | 13 | 107 | [Config.java](dev/tonimatas/packetfixer/common/Config.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 8 | 48 | [MixinPlugin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_neoforge/MixinPlugin.java) |
| `MixinPlugin` | class | IMixinConfigPlugin | — | 8 | 53 | [MixinPlugin.java](dev/tonimatas/packetfixer/mixins/v1_20_5_fabric/MixinPlugin.java) |
| `Messages` | class | — | — | 1 | 8 | [Messages.java](dev/tonimatas/packetfixer/common/Messages.java) |
