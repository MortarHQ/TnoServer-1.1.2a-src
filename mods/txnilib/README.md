# TxniLib（`txnilib`）

> jar：`[优化前置]txnilib.jar` ｜ 版本：`1.0.24` ｜ 作者：Toni
> 规模：**24 个类 / 9 个包 / 1804 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 1

**官方描述**：Yet Another Library Mod

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `toni.lib.animation` | 7 | `AnimationTimeline`、`Transition`、`AnimationEffect` |
| `toni.lib.networking.codecs` | 5 | `StreamCodec`、`StreamEncoder`、`StreamMemberEncoder` |
| `toni.lib.animation.easing` | 4 | `EasingManager`、`EasingFunctionArgs`、`EasingType` |
| `toni.lib.utils` | 3 | `ColorUtils`、`PlatformUtils`、`VersionUtils` |
| `toni.lib` | 1 | `TxniLib` |
| `toni.lib.config` | 1 | `ConfigBase` |
| `toni.lib.networking` | 1 | `ToniPacket` |
| `toni.lib.modifiers` | 1 | `ModifierDefinition` |
| `toni.lib.animation.effects` | 1 | `IAnimationEffect` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TxniLib` | class | — | Mod | 4 | 37 | [TxniLib.java](toni/lib/TxniLib.java) |
| `ConfigBase` | class | ConfigBase.CValue | — | 23 | 227 | [ConfigBase.java](toni/lib/config/ConfigBase.java) |
| `AnimationTimeline` | class | — | — | 27 | 253 | [AnimationTimeline.java](toni/lib/animation/AnimationTimeline.java) |
| `ColorUtils` | class | — | — | 28 | 146 | [ColorUtils.java](toni/lib/utils/ColorUtils.java) |
| `StreamCodec` | interface | — | — | 24 | 276 | [StreamCodec.java](toni/lib/networking/codecs/StreamCodec.java) |
| `EasingManager` | class | — | — | 4 | 180 | [EasingManager.java](toni/lib/animation/easing/EasingManager.java) |
| `ToniPacket` | class | CustomPacketPayload | — | 6 | 55 | [ToniPacket.java](toni/lib/networking/ToniPacket.java) |
| `Transition` | class | IAnimationEffect | — | 11 | 113 | [Transition.java](toni/lib/animation/Transition.java) |
| `AnimationEffect` | class | IAnimationEffect | — | 11 | 91 | [AnimationEffect.java](toni/lib/animation/AnimationEffect.java) |
| `PoseUtils` | class | — | — | 5 | 49 | [PoseUtils.java](toni/lib/animation/PoseUtils.java) |
| `PlatformUtils` | class | — | — | 7 | 40 | [PlatformUtils.java](toni/lib/utils/PlatformUtils.java) |
| `VersionUtils` | class | — | — | 4 | 25 | [VersionUtils.java](toni/lib/utils/VersionUtils.java) |
| `StreamEncoder` | interface | — | — | 0 | 6 | [StreamEncoder.java](toni/lib/networking/codecs/StreamEncoder.java) |
| `StreamMemberEncoder` | interface | — | — | 0 | 6 | [StreamMemberEncoder.java](toni/lib/networking/codecs/StreamMemberEncoder.java) |
| `ByteBufCodecs` | interface | — | — | 2 | 16 | [ByteBufCodecs.java](toni/lib/networking/codecs/ByteBufCodecs.java) |
| `StreamDecoder` | interface | — | — | 0 | 6 | [StreamDecoder.java](toni/lib/networking/codecs/StreamDecoder.java) |
| `ModifierDefinition` | class | — | — | 2 | 26 | [ModifierDefinition.java](toni/lib/modifiers/ModifierDefinition.java) |
| `Binding` | enum | — | — | 0 | 17 | [Binding.java](toni/lib/animation/Binding.java) |
| `IAnimationFunction` | interface | — | — | 0 | 6 | [IAnimationFunction.java](toni/lib/animation/IAnimationFunction.java) |
| `AnimationKeyframe` | class | — | — | 4 | 88 | [AnimationKeyframe.java](toni/lib/animation/AnimationKeyframe.java) |
| `IAnimationEffect` | interface | — | — | 0 | 22 | [IAnimationEffect.java](toni/lib/animation/effects/IAnimationEffect.java) |
| `EasingFunctionArgs` | record | — | — | 1 | 18 | [EasingFunctionArgs.java](toni/lib/animation/easing/EasingFunctionArgs.java) |
| `EasingType` | enum | — | — | 1 | 80 | [EasingType.java](toni/lib/animation/easing/EasingType.java) |
| `Memoizer` | class | — | — | 2 | 21 | [Memoizer.java](toni/lib/animation/easing/Memoizer.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigBase` | — | [ConfigBase.java](toni/lib/config/ConfigBase.java) |

## 全部类（24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TxniLib` | class | — | Mod | 4 | 37 | [TxniLib.java](toni/lib/TxniLib.java) |
| `ConfigBase` | class | ConfigBase.CValue | — | 23 | 227 | [ConfigBase.java](toni/lib/config/ConfigBase.java) |
| `AnimationTimeline` | class | — | — | 27 | 253 | [AnimationTimeline.java](toni/lib/animation/AnimationTimeline.java) |
| `ColorUtils` | class | — | — | 28 | 146 | [ColorUtils.java](toni/lib/utils/ColorUtils.java) |
| `StreamCodec` | interface | — | — | 24 | 276 | [StreamCodec.java](toni/lib/networking/codecs/StreamCodec.java) |
| `EasingManager` | class | — | — | 4 | 180 | [EasingManager.java](toni/lib/animation/easing/EasingManager.java) |
| `ToniPacket` | class | CustomPacketPayload | — | 6 | 55 | [ToniPacket.java](toni/lib/networking/ToniPacket.java) |
| `Transition` | class | IAnimationEffect | — | 11 | 113 | [Transition.java](toni/lib/animation/Transition.java) |
| `AnimationEffect` | class | IAnimationEffect | — | 11 | 91 | [AnimationEffect.java](toni/lib/animation/AnimationEffect.java) |
| `PoseUtils` | class | — | — | 5 | 49 | [PoseUtils.java](toni/lib/animation/PoseUtils.java) |
| `PlatformUtils` | class | — | — | 7 | 40 | [PlatformUtils.java](toni/lib/utils/PlatformUtils.java) |
| `VersionUtils` | class | — | — | 4 | 25 | [VersionUtils.java](toni/lib/utils/VersionUtils.java) |
| `StreamEncoder` | interface | — | — | 0 | 6 | [StreamEncoder.java](toni/lib/networking/codecs/StreamEncoder.java) |
| `StreamMemberEncoder` | interface | — | — | 0 | 6 | [StreamMemberEncoder.java](toni/lib/networking/codecs/StreamMemberEncoder.java) |
| `ByteBufCodecs` | interface | — | — | 2 | 16 | [ByteBufCodecs.java](toni/lib/networking/codecs/ByteBufCodecs.java) |
| `StreamDecoder` | interface | — | — | 0 | 6 | [StreamDecoder.java](toni/lib/networking/codecs/StreamDecoder.java) |
| `ModifierDefinition` | class | — | — | 2 | 26 | [ModifierDefinition.java](toni/lib/modifiers/ModifierDefinition.java) |
| `Binding` | enum | — | — | 0 | 17 | [Binding.java](toni/lib/animation/Binding.java) |
| `IAnimationFunction` | interface | — | — | 0 | 6 | [IAnimationFunction.java](toni/lib/animation/IAnimationFunction.java) |
| `AnimationKeyframe` | class | — | — | 4 | 88 | [AnimationKeyframe.java](toni/lib/animation/AnimationKeyframe.java) |
| `IAnimationEffect` | interface | — | — | 0 | 22 | [IAnimationEffect.java](toni/lib/animation/effects/IAnimationEffect.java) |
| `EasingFunctionArgs` | record | — | — | 1 | 18 | [EasingFunctionArgs.java](toni/lib/animation/easing/EasingFunctionArgs.java) |
| `EasingType` | enum | — | — | 1 | 80 | [EasingType.java](toni/lib/animation/easing/EasingType.java) |
| `Memoizer` | class | — | — | 2 | 21 | [Memoizer.java](toni/lib/animation/easing/Memoizer.java) |
