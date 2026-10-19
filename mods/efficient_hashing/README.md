# Efficient Hashing（`efficient_hashing`）

> jar：`[优化]efficienthashing.jar` ｜ 版本：`1.0.0` ｜ 作者：Zank
> 规模：**5 个类 / 4 个包 / 68 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 0

**官方描述**：Better hashing algorithm for Vec3i & BlockPos

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `zank.mods.efficient_hashing` | 2 | `EfficientHashing`、`PhiMix` |
| `zank.mods.efficient_hashing.neoforge` | 1 | `EfficientHashingMod` |
| `zank.mods.efficient_hashing.mixin` | 1 | `MixinVec3i` |
| `architectury_inject_EfficientHashing_1211_5e008906780b462eb9a66a94f1837c22_c082c89a24bb9e09c5527ac9fff634a46801a14100dada67e363bfafd52f829defficient_hashingcommon1001211devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 5）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EfficientHashingMod` | class | — | Mod | 0 | 12 | [EfficientHashingMod.java](zank/mods/efficient_hashing/neoforge/EfficientHashingMod.java) |
| `MixinVec3i` | class | — | Mixin | 1 | 24 | [MixinVec3i.java](zank/mods/efficient_hashing/mixin/MixinVec3i.java) |
| `EfficientHashing` | class | — | — | 1 | 13 | [EfficientHashing.java](zank/mods/efficient_hashing/EfficientHashing.java) |
| `PhiMix` | class | — | — | 1 | 11 | [PhiMix.java](zank/mods/efficient_hashing/PhiMix.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_EfficientHashing_1211_5e008906780b462eb9a66a94f1837c22_c082c89a24bb9e09c5527ac9fff634a46801a14100dada67e363bfafd52f829defficient_hashingcommon1001211devjar/PlatformMethods.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinVec3i` | `Vec3i` | [MixinVec3i.java](zank/mods/efficient_hashing/mixin/MixinVec3i.java) |

## 全部类（5）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EfficientHashingMod` | class | — | Mod | 0 | 12 | [EfficientHashingMod.java](zank/mods/efficient_hashing/neoforge/EfficientHashingMod.java) |
| `MixinVec3i` | class | — | Mixin | 1 | 24 | [MixinVec3i.java](zank/mods/efficient_hashing/mixin/MixinVec3i.java) |
| `EfficientHashing` | class | — | — | 1 | 13 | [EfficientHashing.java](zank/mods/efficient_hashing/EfficientHashing.java) |
| `PhiMix` | class | — | — | 1 | 11 | [PhiMix.java](zank/mods/efficient_hashing/PhiMix.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_EfficientHashing_1211_5e008906780b462eb9a66a94f1837c22_c082c89a24bb9e09c5527ac9fff634a46801a14100dada67e363bfafd52f829defficient_hashingcommon1001211devjar/PlatformMethods.java) |
