package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiLeapInAir {
   public static final AnimationDefinition LEAP_IN_AIR;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 2.37
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "CTR_leg_left.002"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 35
      // 0013: anewarray 28
      // 0016: dup
      // 0017: bipush 0
      // 0018: new net/minecraft/client/animation/Keyframe
      // 001b: dup
      // 001c: fconst_0
      // 001d: ldc 1.976
      // 001f: ldc 2.82
      // 0021: ldc -13.051
      // 0023: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 002c: aastore
      // 002d: dup
      // 002e: bipush 1
      // 002f: new net/minecraft/client/animation/Keyframe
      // 0032: dup
      // 0033: ldc 0.04
      // 0035: ldc 1.885
      // 0037: ldc 6.424
      // 0039: ldc -12.625
      // 003b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0041: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0044: aastore
      // 0045: dup
      // 0046: bipush 2
      // 0047: new net/minecraft/client/animation/Keyframe
      // 004a: dup
      // 004b: ldc 0.08
      // 004d: ldc 1.636
      // 004f: ldc 11.26
      // 0051: ldc -11.456
      // 0053: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0056: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0059: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 005c: aastore
      // 005d: dup
      // 005e: bipush 3
      // 005f: new net/minecraft/client/animation/Keyframe
      // 0062: dup
      // 0063: ldc 0.12
      // 0065: ldc 1.261
      // 0067: ldc 16.739
      // 0069: ldc -9.71
      // 006b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0071: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0074: aastore
      // 0075: dup
      // 0076: bipush 4
      // 0077: new net/minecraft/client/animation/Keyframe
      // 007a: dup
      // 007b: ldc 0.17
      // 007d: ldc 0.794
      // 007f: ldc 22.208
      // 0081: ldc -7.552
      // 0083: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0086: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0089: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 008c: aastore
      // 008d: dup
      // 008e: bipush 5
      // 008f: new net/minecraft/client/animation/Keyframe
      // 0092: dup
      // 0093: ldc 0.21
      // 0095: ldc 0.267
      // 0097: ldc 27.167
      // 0099: ldc -5.146
      // 009b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 009e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00a4: aastore
      // 00a5: dup
      // 00a6: bipush 6
      // 00a8: new net/minecraft/client/animation/Keyframe
      // 00ab: dup
      // 00ac: ldc 0.25
      // 00ae: ldc -0.285
      // 00b0: ldc 31.345
      // 00b2: ldc -2.658
      // 00b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00bd: aastore
      // 00be: dup
      // 00bf: bipush 7
      // 00c1: new net/minecraft/client/animation/Keyframe
      // 00c4: dup
      // 00c5: ldc 0.29
      // 00c7: ldc -0.829
      // 00c9: ldc 34.651
      // 00cb: ldc -0.252
      // 00cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00d6: aastore
      // 00d7: dup
      // 00d8: bipush 8
      // 00da: new net/minecraft/client/animation/Keyframe
      // 00dd: dup
      // 00de: ldc 0.33
      // 00e0: ldc -1.333
      // 00e2: ldc 37.099
      // 00e4: ldc 1.906
      // 00e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00ef: aastore
      // 00f0: dup
      // 00f1: bipush 9
      // 00f3: new net/minecraft/client/animation/Keyframe
      // 00f6: dup
      // 00f7: ldc 0.38
      // 00f9: ldc -1.762
      // 00fb: ldc 38.752
      // 00fd: ldc 3.652
      // 00ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0102: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0105: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0108: aastore
      // 0109: dup
      // 010a: bipush 10
      // 010c: new net/minecraft/client/animation/Keyframe
      // 010f: dup
      // 0110: ldc 0.42
      // 0112: ldc -2.083
      // 0114: ldc 39.687
      // 0116: ldc 4.82
      // 0118: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 011b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 011e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0121: aastore
      // 0122: dup
      // 0123: bipush 11
      // 0125: new net/minecraft/client/animation/Keyframe
      // 0128: dup
      // 0129: ldc 0.46
      // 012b: ldc -2.264
      // 012d: ldc 39.983
      // 012f: ldc 5.246
      // 0131: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0134: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0137: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 013a: aastore
      // 013b: dup
      // 013c: bipush 12
      // 013e: new net/minecraft/client/animation/Keyframe
      // 0141: dup
      // 0142: ldc 0.58
      // 0144: ldc -2.515
      // 0146: ldc 40.098
      // 0148: ldc 5.24
      // 014a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 014d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0150: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0153: aastore
      // 0154: dup
      // 0155: bipush 13
      // 0157: new net/minecraft/client/animation/Keyframe
      // 015a: dup
      // 015b: ldc 0.62
      // 015d: ldc -2.579
      // 015f: ldc 40.139
      // 0161: ldc 5.232
      // 0163: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0166: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0169: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 016c: aastore
      // 016d: dup
      // 016e: bipush 14
      // 0170: new net/minecraft/client/animation/Keyframe
      // 0173: dup
      // 0174: ldc 0.75
      // 0176: ldc -2.723
      // 0178: ldc 40.258
      // 017a: ldc 5.171
      // 017c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 017f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0182: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0185: aastore
      // 0186: dup
      // 0187: bipush 15
      // 0189: new net/minecraft/client/animation/Keyframe
      // 018c: dup
      // 018d: ldc 0.83
      // 018f: ldc -2.785
      // 0191: ldc 40.331
      // 0193: ldc 5.087
      // 0195: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0198: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 019b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 019e: aastore
      // 019f: dup
      // 01a0: bipush 16
      // 01a2: new net/minecraft/client/animation/Keyframe
      // 01a5: dup
      // 01a6: ldc 0.88
      // 01a8: ldc -2.807
      // 01aa: ldc 40.365
      // 01ac: ldc 5.027
      // 01ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01b7: aastore
      // 01b8: dup
      // 01b9: bipush 17
      // 01bb: new net/minecraft/client/animation/Keyframe
      // 01be: dup
      // 01bf: ldc 0.96
      // 01c1: ldc -2.837
      // 01c3: ldc 40.428
      // 01c5: ldc 4.867
      // 01c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d0: aastore
      // 01d1: dup
      // 01d2: bipush 18
      // 01d4: new net/minecraft/client/animation/Keyframe
      // 01d7: dup
      // 01d8: ldc 1.04
      // 01da: ldc -2.852
      // 01dc: ldc 40.481
      // 01de: ldc 4.645
      // 01e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01e9: aastore
      // 01ea: dup
      // 01eb: bipush 19
      // 01ed: new net/minecraft/client/animation/Keyframe
      // 01f0: dup
      // 01f1: ldc 1.17
      // 01f3: ldc -2.859
      // 01f5: ldc 40.531
      // 01f7: ldc 4.169
      // 01f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0202: aastore
      // 0203: dup
      // 0204: bipush 20
      // 0206: new net/minecraft/client/animation/Keyframe
      // 0209: dup
      // 020a: ldc 1.21
      // 020c: ldc -2.859
      // 020e: ldc 40.536
      // 0210: ldc 3.967
      // 0212: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0215: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0218: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 021b: aastore
      // 021c: dup
      // 021d: bipush 21
      // 021f: new net/minecraft/client/animation/Keyframe
      // 0222: dup
      // 0223: ldc 1.25
      // 0225: ldc -2.815
      // 0227: ldc 40.455
      // 0229: ldc 3.566
      // 022b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 022e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0231: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0234: aastore
      // 0235: dup
      // 0236: bipush 22
      // 0238: new net/minecraft/client/animation/Keyframe
      // 023b: dup
      // 023c: ldc 1.29
      // 023e: ldc -2.692
      // 0240: ldc 40.226
      // 0242: ldc 2.824
      // 0244: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0247: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 024a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 024d: aastore
      // 024e: dup
      // 024f: bipush 23
      // 0251: new net/minecraft/client/animation/Keyframe
      // 0254: dup
      // 0255: ldc 1.33
      // 0257: ldc -2.502
      // 0259: ldc 39.873
      // 025b: ldc 1.796
      // 025d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0260: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0263: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0266: aastore
      // 0267: dup
      // 0268: bipush 24
      // 026a: new net/minecraft/client/animation/Keyframe
      // 026d: dup
      // 026e: ldc 1.38
      // 0270: ldc -2.257
      // 0272: ldc 39.418
      // 0274: ldc 0.537
      // 0276: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0279: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 027c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 027f: aastore
      // 0280: dup
      // 0281: bipush 25
      // 0283: new net/minecraft/client/animation/Keyframe
      // 0286: dup
      // 0287: ldc 1.42
      // 0289: ldc -1.97
      // 028b: ldc 38.884
      // 028d: ldc -0.899
      // 028f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0292: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0295: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0298: aastore
      // 0299: dup
      // 029a: bipush 26
      // 029c: new net/minecraft/client/animation/Keyframe
      // 029f: dup
      // 02a0: ldc 1.46
      // 02a2: ldc -1.652
      // 02a4: ldc 38.293
      // 02a6: ldc -2.458
      // 02a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02b1: aastore
      // 02b2: dup
      // 02b3: bipush 27
      // 02b5: new net/minecraft/client/animation/Keyframe
      // 02b8: dup
      // 02b9: ldc 1.54
      // 02bb: ldc -0.973
      // 02bd: ldc 37.033
      // 02bf: ldc -5.722
      // 02c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02ca: aastore
      // 02cb: dup
      // 02cc: bipush 28
      // 02ce: new net/minecraft/client/animation/Keyframe
      // 02d1: dup
      // 02d2: ldc 1.58
      // 02d4: ldc -0.637
      // 02d6: ldc 36.408
      // 02d8: ldc -7.32
      // 02da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e3: aastore
      // 02e4: dup
      // 02e5: bipush 29
      // 02e7: new net/minecraft/client/animation/Keyframe
      // 02ea: dup
      // 02eb: ldc 1.62
      // 02ed: ldc -0.319
      // 02ef: ldc 35.817
      // 02f1: ldc -8.821
      // 02f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02fc: aastore
      // 02fd: dup
      // 02fe: bipush 30
      // 0300: new net/minecraft/client/animation/Keyframe
      // 0303: dup
      // 0304: ldc 1.67
      // 0306: ldc -0.031
      // 0308: ldc 35.283
      // 030a: ldc -10.172
      // 030c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 030f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0312: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0315: aastore
      // 0316: dup
      // 0317: bipush 31
      // 0319: new net/minecraft/client/animation/Keyframe
      // 031c: dup
      // 031d: ldc 1.71
      // 031f: ldc 0.214
      // 0321: ldc 34.828
      // 0323: ldc -11.317
      // 0325: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0328: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 032b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 032e: aastore
      // 032f: dup
      // 0330: bipush 32
      // 0332: new net/minecraft/client/animation/Keyframe
      // 0335: dup
      // 0336: ldc 1.75
      // 0338: ldc 0.404
      // 033a: ldc 34.475
      // 033c: ldc -12.203
      // 033e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0341: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0344: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0347: aastore
      // 0348: dup
      // 0349: bipush 33
      // 034b: new net/minecraft/client/animation/Keyframe
      // 034e: dup
      // 034f: ldc 1.79
      // 0351: ldc 0.527
      // 0353: ldc 34.246
      // 0355: ldc -12.775
      // 0357: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 035a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 035d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0360: aastore
      // 0361: dup
      // 0362: bipush 34
      // 0364: new net/minecraft/client/animation/Keyframe
      // 0367: dup
      // 0368: ldc 1.83
      // 036a: ldc 0.57
      // 036c: ldc 34.165
      // 036e: ldc -12.977
      // 0370: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0373: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0376: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0379: aastore
      // 037a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 037d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0380: ldc "CTR_hips"
      // 0382: new net/minecraft/client/animation/AnimationChannel
      // 0385: dup
      // 0386: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0389: bipush 28
      // 038b: anewarray 28
      // 038e: dup
      // 038f: bipush 0
      // 0390: new net/minecraft/client/animation/Keyframe
      // 0393: dup
      // 0394: ldc 0.54
      // 0396: ldc -0.869
      // 0398: ldc -6.472
      // 039a: ldc -3.557
      // 039c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 039f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03a5: aastore
      // 03a6: dup
      // 03a7: bipush 1
      // 03a8: new net/minecraft/client/animation/Keyframe
      // 03ab: dup
      // 03ac: ldc 0.58
      // 03ae: ldc -7.489
      // 03b0: ldc -8.817
      // 03b2: ldc -5.927
      // 03b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03bd: aastore
      // 03be: dup
      // 03bf: bipush 2
      // 03c0: new net/minecraft/client/animation/Keyframe
      // 03c3: dup
      // 03c4: ldc 0.62
      // 03c6: ldc -13.614
      // 03c8: ldc -11.002
      // 03ca: ldc -8.138
      // 03cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03d5: aastore
      // 03d6: dup
      // 03d7: bipush 3
      // 03d8: new net/minecraft/client/animation/Keyframe
      // 03db: dup
      // 03dc: ldc 0.66
      // 03de: ldc -18.884
      // 03e0: ldc -12.786
      // 03e2: ldc -10.066
      // 03e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03ed: aastore
      // 03ee: dup
      // 03ef: bipush 4
      // 03f0: new net/minecraft/client/animation/Keyframe
      // 03f3: dup
      // 03f4: ldc 0.71
      // 03f6: ldc -23.165
      // 03f8: ldc -13.941
      // 03fa: ldc -11.577
      // 03fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0402: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0405: aastore
      // 0406: dup
      // 0407: bipush 5
      // 0408: new net/minecraft/client/animation/Keyframe
      // 040b: dup
      // 040c: ldc 0.75
      // 040e: ldc -26.485
      // 0410: ldc -14.258
      // 0412: ldc -12.529
      // 0414: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0417: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 041a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 041d: aastore
      // 041e: dup
      // 041f: bipush 6
      // 0421: new net/minecraft/client/animation/Keyframe
      // 0424: dup
      // 0425: ldc 0.79
      // 0427: ldc -28.833
      // 0429: ldc -14.081
      // 042b: ldc -13.121
      // 042d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0430: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0433: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0436: aastore
      // 0437: dup
      // 0438: bipush 7
      // 043a: new net/minecraft/client/animation/Keyframe
      // 043d: dup
      // 043e: ldc 0.83
      // 0440: ldc -30.181
      // 0442: ldc -13.888
      // 0444: ldc -13.648
      // 0446: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0449: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 044c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 044f: aastore
      // 0450: dup
      // 0451: bipush 8
      // 0453: new net/minecraft/client/animation/Keyframe
      // 0456: dup
      // 0457: ldc 0.87
      // 0459: ldc -30.607
      // 045b: ldc -13.684
      // 045d: ldc -14.112
      // 045f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0462: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0465: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0468: aastore
      // 0469: dup
      // 046a: bipush 9
      // 046c: new net/minecraft/client/animation/Keyframe
      // 046f: dup
      // 0470: ldc 0.92
      // 0472: ldc -30.232
      // 0474: ldc -13.474
      // 0476: ldc -14.513
      // 0478: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 047b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 047e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0481: aastore
      // 0482: dup
      // 0483: bipush 10
      // 0485: new net/minecraft/client/animation/Keyframe
      // 0488: dup
      // 0489: ldc 0.96
      // 048b: ldc -29.082
      // 048d: ldc -13.263
      // 048f: ldc -14.852
      // 0491: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0494: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0497: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 049a: aastore
      // 049b: dup
      // 049c: bipush 11
      // 049e: new net/minecraft/client/animation/Keyframe
      // 04a1: dup
      // 04a2: fconst_1
      // 04a3: ldc -27.087
      // 04a5: ldc -13.055
      // 04a7: ldc -15.13
      // 04a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04b2: aastore
      // 04b3: dup
      // 04b4: bipush 12
      // 04b6: new net/minecraft/client/animation/Keyframe
      // 04b9: dup
      // 04ba: ldc 1.04
      // 04bc: ldc -24.169
      // 04be: ldc -12.856
      // 04c0: ldc -15.349
      // 04c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04cb: aastore
      // 04cc: dup
      // 04cd: bipush 13
      // 04cf: new net/minecraft/client/animation/Keyframe
      // 04d2: dup
      // 04d3: ldc 1.08
      // 04d5: ldc -20.234
      // 04d7: ldc -12.668
      // 04d9: ldc -15.511
      // 04db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04e4: aastore
      // 04e5: dup
      // 04e6: bipush 14
      // 04e8: new net/minecraft/client/animation/Keyframe
      // 04eb: dup
      // 04ec: ldc 1.12
      // 04ee: ldc -18.78
      // 04f0: ldc -12.681
      // 04f2: ldc -15.635
      // 04f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04fd: aastore
      // 04fe: dup
      // 04ff: bipush 15
      // 0501: new net/minecraft/client/animation/Keyframe
      // 0504: dup
      // 0505: ldc 1.16
      // 0507: ldc -18.591
      // 0509: ldc -12.749
      // 050b: ldc -15.74
      // 050d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0510: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0513: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0516: aastore
      // 0517: dup
      // 0518: bipush 16
      // 051a: new net/minecraft/client/animation/Keyframe
      // 051d: dup
      // 051e: ldc 1.21
      // 0520: ldc -18.706
      // 0522: ldc_w -12.821
      // 0525: ldc_w -15.83
      // 0528: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 052b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 052e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0531: aastore
      // 0532: dup
      // 0533: bipush 17
      // 0535: new net/minecraft/client/animation/Keyframe
      // 0538: dup
      // 0539: ldc_w 1.37
      // 053c: ldc_w -19.883
      // 053f: ldc_w -13.025
      // 0542: ldc_w -16.028
      // 0545: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0548: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 054b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 054e: aastore
      // 054f: dup
      // 0550: bipush 18
      // 0552: new net/minecraft/client/animation/Keyframe
      // 0555: dup
      // 0556: ldc 1.46
      // 0558: ldc_w -20.356
      // 055b: ldc_w -13.034
      // 055e: ldc_w -16.007
      // 0561: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0564: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0567: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 056a: aastore
      // 056b: dup
      // 056c: bipush 19
      // 056e: new net/minecraft/client/animation/Keyframe
      // 0571: dup
      // 0572: ldc 1.54
      // 0574: ldc_w -20.489
      // 0577: ldc_w -12.938
      // 057a: ldc_w -15.865
      // 057d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0580: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0583: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0586: aastore
      // 0587: dup
      // 0588: bipush 20
      // 058a: new net/minecraft/client/animation/Keyframe
      // 058d: dup
      // 058e: ldc 1.71
      // 0590: ldc_w -20.26
      // 0593: ldc_w -12.685
      // 0596: ldc_w -15.533
      // 0599: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 059c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 059f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05a2: aastore
      // 05a3: dup
      // 05a4: bipush 21
      // 05a6: new net/minecraft/client/animation/Keyframe
      // 05a9: dup
      // 05aa: ldc 1.79
      // 05ac: ldc_w -20.187
      // 05af: ldc_w -12.685
      // 05b2: ldc_w -15.537
      // 05b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05be: aastore
      // 05bf: dup
      // 05c0: bipush 22
      // 05c2: new net/minecraft/client/animation/Keyframe
      // 05c5: dup
      // 05c6: ldc_w 1.92
      // 05c9: ldc_w -19.791
      // 05cc: ldc_w -12.878
      // 05cf: ldc_w -15.831
      // 05d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05db: aastore
      // 05dc: dup
      // 05dd: bipush 23
      // 05df: new net/minecraft/client/animation/Keyframe
      // 05e2: dup
      // 05e3: fconst_2
      // 05e4: ldc_w -19.801
      // 05e7: ldc_w -12.994
      // 05ea: ldc_w -15.991
      // 05ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05f6: aastore
      // 05f7: dup
      // 05f8: bipush 24
      // 05fa: new net/minecraft/client/animation/Keyframe
      // 05fd: dup
      // 05fe: ldc_w 2.12
      // 0601: ldc_w -20.36
      // 0604: ldc_w -13.033
      // 0607: ldc_w -16.005
      // 060a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 060d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0610: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0613: aastore
      // 0614: dup
      // 0615: bipush 25
      // 0617: new net/minecraft/client/animation/Keyframe
      // 061a: dup
      // 061b: ldc_w 2.16
      // 061e: ldc_w -20.468
      // 0621: ldc_w -12.991
      // 0624: ldc_w -15.939
      // 0627: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 062a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 062d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0630: aastore
      // 0631: dup
      // 0632: bipush 26
      // 0634: new net/minecraft/client/animation/Keyframe
      // 0637: dup
      // 0638: ldc_w 2.33
      // 063b: ldc_w -20.268
      // 063e: ldc_w -12.691
      // 0641: ldc_w -15.54
      // 0644: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0647: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 064a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 064d: aastore
      // 064e: dup
      // 064f: bipush 27
      // 0651: new net/minecraft/client/animation/Keyframe
      // 0654: dup
      // 0655: ldc 2.37
      // 0657: ldc -20.234
      // 0659: ldc -12.668
      // 065b: ldc -15.511
      // 065d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0660: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0663: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0666: aastore
      // 0667: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 066a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 066d: ldc "CTR_hips"
      // 066f: new net/minecraft/client/animation/AnimationChannel
      // 0672: dup
      // 0673: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0676: bipush 42
      // 0678: anewarray 28
      // 067b: dup
      // 067c: bipush 0
      // 067d: new net/minecraft/client/animation/Keyframe
      // 0680: dup
      // 0681: ldc 0.54
      // 0683: ldc_w -2.369
      // 0686: ldc_w 25.268
      // 0689: ldc_w 4.917
      // 068c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 068f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0692: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0695: aastore
      // 0696: dup
      // 0697: bipush 1
      // 0698: new net/minecraft/client/animation/Keyframe
      // 069b: dup
      // 069c: ldc 0.58
      // 069e: ldc_w -2.526
      // 06a1: ldc_w 28.431
      // 06a4: ldc_w 4.457
      // 06a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06b0: aastore
      // 06b1: dup
      // 06b2: bipush 2
      // 06b3: new net/minecraft/client/animation/Keyframe
      // 06b6: dup
      // 06b7: ldc 0.62
      // 06b9: ldc_w -2.916
      // 06bc: ldc_w 30.86
      // 06bf: ldc_w 4.064
      // 06c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06cb: aastore
      // 06cc: dup
      // 06cd: bipush 3
      // 06ce: new net/minecraft/client/animation/Keyframe
      // 06d1: dup
      // 06d2: ldc 0.66
      // 06d4: ldc_w -3.424
      // 06d7: ldc_w 32.705
      // 06da: ldc_w 3.757
      // 06dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06e6: aastore
      // 06e7: dup
      // 06e8: bipush 4
      // 06e9: new net/minecraft/client/animation/Keyframe
      // 06ec: dup
      // 06ed: ldc 0.71
      // 06ef: ldc_w -3.931
      // 06f2: ldc_w 34.08
      // 06f5: ldc_w 3.543
      // 06f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0701: aastore
      // 0702: dup
      // 0703: bipush 5
      // 0704: new net/minecraft/client/animation/Keyframe
      // 0707: dup
      // 0708: ldc 0.75
      // 070a: ldc_w -4.322
      // 070d: ldc_w 35.067
      // 0710: ldc_w 3.419
      // 0713: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0716: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0719: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 071c: aastore
      // 071d: dup
      // 071e: bipush 6
      // 0720: new net/minecraft/client/animation/Keyframe
      // 0723: dup
      // 0724: ldc 0.79
      // 0726: ldc_w -4.478
      // 0729: ldc_w 35.731
      // 072c: ldc_w 3.379
      // 072f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0732: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0735: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0738: aastore
      // 0739: dup
      // 073a: bipush 7
      // 073c: new net/minecraft/client/animation/Keyframe
      // 073f: dup
      // 0740: ldc 0.83
      // 0742: ldc_w -4.464
      // 0745: ldc_w 36.166
      // 0748: ldc_w 3.412
      // 074b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 074e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0751: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0754: aastore
      // 0755: dup
      // 0756: bipush 8
      // 0758: new net/minecraft/client/animation/Keyframe
      // 075b: dup
      // 075c: ldc 0.87
      // 075e: ldc_w -4.427
      // 0761: ldc_w 36.41
      // 0764: ldc_w 3.509
      // 0767: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 076a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 076d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0770: aastore
      // 0771: dup
      // 0772: bipush 9
      // 0774: new net/minecraft/client/animation/Keyframe
      // 0777: dup
      // 0778: ldc 0.92
      // 077a: ldc_w -4.372
      // 077d: ldc_w 36.425
      // 0780: ldc_w 3.667
      // 0783: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0786: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0789: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 078c: aastore
      // 078d: dup
      // 078e: bipush 10
      // 0790: new net/minecraft/client/animation/Keyframe
      // 0793: dup
      // 0794: ldc 0.96
      // 0796: ldc_w -4.305
      // 0799: ldc_w 36.163
      // 079c: ldc_w 3.883
      // 079f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07a8: aastore
      // 07a9: dup
      // 07aa: bipush 11
      // 07ac: new net/minecraft/client/animation/Keyframe
      // 07af: dup
      // 07b0: fconst_1
      // 07b1: ldc_w -4.234
      // 07b4: ldc_w 35.556
      // 07b7: ldc_w 4.155
      // 07ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07c3: aastore
      // 07c4: dup
      // 07c5: bipush 12
      // 07c7: new net/minecraft/client/animation/Keyframe
      // 07ca: dup
      // 07cb: ldc 1.04
      // 07cd: ldc_w -4.162
      // 07d0: ldc_w 34.51
      // 07d3: ldc_w 4.479
      // 07d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07df: aastore
      // 07e0: dup
      // 07e1: bipush 13
      // 07e3: new net/minecraft/client/animation/Keyframe
      // 07e6: dup
      // 07e7: ldc 1.08
      // 07e9: ldc_w -4.098
      // 07ec: ldc_w 32.891
      // 07ef: ldc_w 4.853
      // 07f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07fb: aastore
      // 07fc: dup
      // 07fd: bipush 14
      // 07ff: new net/minecraft/client/animation/Keyframe
      // 0802: dup
      // 0803: ldc 1.12
      // 0805: ldc_w -4.045
      // 0808: ldc_w 32.45
      // 080b: ldc_w 4.933
      // 080e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0811: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0814: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0817: aastore
      // 0818: dup
      // 0819: bipush 15
      // 081b: new net/minecraft/client/animation/Keyframe
      // 081e: dup
      // 081f: ldc 1.16
      // 0821: ldc_w -4.003
      // 0824: ldc_w 32.667
      // 0827: ldc_w 4.896
      // 082a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 082d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0830: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0833: aastore
      // 0834: dup
      // 0835: bipush 16
      // 0837: new net/minecraft/client/animation/Keyframe
      // 083a: dup
      // 083b: ldc 1.21
      // 083d: ldc_w -3.972
      // 0840: ldc_w 33.013
      // 0843: ldc_w 4.837
      // 0846: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0849: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 084c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 084f: aastore
      // 0850: dup
      // 0851: bipush 17
      // 0853: new net/minecraft/client/animation/Keyframe
      // 0856: dup
      // 0857: ldc 1.29
      // 0859: ldc_w -3.934
      // 085c: ldc_w 33.746
      // 085f: ldc_w 4.713
      // 0862: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0865: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0868: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 086b: aastore
      // 086c: dup
      // 086d: bipush 18
      // 086f: new net/minecraft/client/animation/Keyframe
      // 0872: dup
      // 0873: ldc 1.33
      // 0875: ldc_w -3.924
      // 0878: ldc_w 34.06
      // 087b: ldc_w 4.66
      // 087e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0881: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0884: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0887: aastore
      // 0888: dup
      // 0889: bipush 19
      // 088b: new net/minecraft/client/animation/Keyframe
      // 088e: dup
      // 088f: ldc_w 1.37
      // 0892: ldc_w -3.92
      // 0895: ldc_w 34.31
      // 0898: ldc_w 4.618
      // 089b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 089e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08a4: aastore
      // 08a5: dup
      // 08a6: bipush 20
      // 08a8: new net/minecraft/client/animation/Keyframe
      // 08ab: dup
      // 08ac: ldc 1.42
      // 08ae: ldc_w -3.919
      // 08b1: ldc_w 34.478
      // 08b4: ldc_w 4.589
      // 08b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08c0: aastore
      // 08c1: dup
      // 08c2: bipush 21
      // 08c4: new net/minecraft/client/animation/Keyframe
      // 08c7: dup
      // 08c8: ldc 1.46
      // 08ca: ldc_w -3.926
      // 08cd: ldc_w 34.507
      // 08d0: ldc_w 4.583
      // 08d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08dc: aastore
      // 08dd: dup
      // 08de: bipush 22
      // 08e0: new net/minecraft/client/animation/Keyframe
      // 08e3: dup
      // 08e4: ldc_w 1.5
      // 08e7: ldc_w -3.947
      // 08ea: ldc_w 34.38
      // 08ed: ldc_w 4.604
      // 08f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08f9: aastore
      // 08fa: dup
      // 08fb: bipush 23
      // 08fd: new net/minecraft/client/animation/Keyframe
      // 0900: dup
      // 0901: ldc 1.54
      // 0903: ldc_w -3.975
      // 0906: ldc_w 34.137
      // 0909: ldc_w 4.644
      // 090c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 090f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0912: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0915: aastore
      // 0916: dup
      // 0917: bipush 24
      // 0919: new net/minecraft/client/animation/Keyframe
      // 091c: dup
      // 091d: ldc 1.62
      // 091f: ldc_w -4.041
      // 0922: ldc_w 33.491
      // 0925: ldc_w 4.753
      // 0928: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 092b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 092e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0931: aastore
      // 0932: dup
      // 0933: bipush 25
      // 0935: new net/minecraft/client/animation/Keyframe
      // 0938: dup
      // 0939: ldc_w 1.66
      // 093c: ldc_w -4.07
      // 093f: ldc_w 33.19
      // 0942: ldc_w 4.803
      // 0945: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0948: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 094b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 094e: aastore
      // 094f: dup
      // 0950: bipush 26
      // 0952: new net/minecraft/client/animation/Keyframe
      // 0955: dup
      // 0956: ldc 1.71
      // 0958: ldc_w -4.09
      // 095b: ldc_w 32.974
      // 095e: ldc_w 4.839
      // 0961: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0964: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0967: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 096a: aastore
      // 096b: dup
      // 096c: bipush 27
      // 096e: new net/minecraft/client/animation/Keyframe
      // 0971: dup
      // 0972: ldc 1.75
      // 0974: ldc_w -4.098
      // 0977: ldc_w 32.891
      // 097a: ldc_w 4.853
      // 097d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0980: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0983: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0986: aastore
      // 0987: dup
      // 0988: bipush 28
      // 098a: new net/minecraft/client/animation/Keyframe
      // 098d: dup
      // 098e: ldc 1.79
      // 0990: ldc_w -4.09
      // 0993: ldc_w 32.946
      // 0996: ldc_w 4.844
      // 0999: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 099c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 099f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09a2: aastore
      // 09a3: dup
      // 09a4: bipush 29
      // 09a6: new net/minecraft/client/animation/Keyframe
      // 09a9: dup
      // 09aa: ldc 1.83
      // 09ac: ldc_w -4.07
      // 09af: ldc_w 33.108
      // 09b2: ldc_w 4.818
      // 09b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09be: aastore
      // 09bf: dup
      // 09c0: bipush 30
      // 09c2: new net/minecraft/client/animation/Keyframe
      // 09c5: dup
      // 09c6: ldc_w 1.87
      // 09c9: ldc_w -4.041
      // 09cc: ldc_w 33.353
      // 09cf: ldc_w 4.777
      // 09d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09db: aastore
      // 09dc: dup
      // 09dd: bipush 31
      // 09df: new net/minecraft/client/animation/Keyframe
      // 09e2: dup
      // 09e3: ldc_w 1.92
      // 09e6: ldc_w -4.008
      // 09e9: ldc_w 33.635
      // 09ec: ldc_w 4.731
      // 09ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09f8: aastore
      // 09f9: dup
      // 09fa: bipush 32
      // 09fc: new net/minecraft/client/animation/Keyframe
      // 09ff: dup
      // 0a00: ldc_w 1.96
      // 0a03: ldc_w -3.975
      // 0a06: ldc_w 33.908
      // 0a09: ldc_w 4.685
      // 0a0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a15: aastore
      // 0a16: dup
      // 0a17: bipush 33
      // 0a19: new net/minecraft/client/animation/Keyframe
      // 0a1c: dup
      // 0a1d: fconst_2
      // 0a1e: ldc_w -3.947
      // 0a21: ldc_w 34.145
      // 0a24: ldc_w 4.646
      // 0a27: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a30: aastore
      // 0a31: dup
      // 0a32: bipush 34
      // 0a34: new net/minecraft/client/animation/Keyframe
      // 0a37: dup
      // 0a38: ldc_w 2.04
      // 0a3b: ldc_w -3.926
      // 0a3e: ldc_w 34.335
      // 0a41: ldc_w 4.614
      // 0a44: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a4d: aastore
      // 0a4e: dup
      // 0a4f: bipush 35
      // 0a51: new net/minecraft/client/animation/Keyframe
      // 0a54: dup
      // 0a55: ldc_w 2.08
      // 0a58: ldc_w -3.919
      // 0a5b: ldc_w 34.478
      // 0a5e: ldc_w 4.589
      // 0a61: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a6a: aastore
      // 0a6b: dup
      // 0a6c: bipush 36
      // 0a6e: new net/minecraft/client/animation/Keyframe
      // 0a71: dup
      // 0a72: ldc_w 2.12
      // 0a75: ldc_w -3.928
      // 0a78: ldc_w 34.506
      // 0a7b: ldc_w 4.584
      // 0a7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a87: aastore
      // 0a88: dup
      // 0a89: bipush 37
      // 0a8b: new net/minecraft/client/animation/Keyframe
      // 0a8e: dup
      // 0a8f: ldc_w 2.16
      // 0a92: ldc_w -3.954
      // 0a95: ldc_w 34.357
      // 0a98: ldc_w 4.608
      // 0a9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aa1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aa4: aastore
      // 0aa5: dup
      // 0aa6: bipush 38
      // 0aa8: new net/minecraft/client/animation/Keyframe
      // 0aab: dup
      // 0aac: ldc_w 2.21
      // 0aaf: ldc_w -3.989
      // 0ab2: ldc_w 34.061
      // 0ab5: ldc_w 4.657
      // 0ab8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0abb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0abe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ac1: aastore
      // 0ac2: dup
      // 0ac3: bipush 39
      // 0ac5: new net/minecraft/client/animation/Keyframe
      // 0ac8: dup
      // 0ac9: ldc_w 2.29
      // 0acc: ldc_w -4.062
      // 0acf: ldc_w 33.292
      // 0ad2: ldc_w 4.786
      // 0ad5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ad8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0adb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ade: aastore
      // 0adf: dup
      // 0ae0: bipush 40
      // 0ae2: new net/minecraft/client/animation/Keyframe
      // 0ae5: dup
      // 0ae6: ldc_w 2.33
      // 0ae9: ldc_w -4.088
      // 0aec: ldc_w 33.002
      // 0aef: ldc_w 4.835
      // 0af2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0af5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0af8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0afb: aastore
      // 0afc: dup
      // 0afd: bipush 41
      // 0aff: new net/minecraft/client/animation/Keyframe
      // 0b02: dup
      // 0b03: ldc 2.37
      // 0b05: ldc_w -4.098
      // 0b08: ldc_w 32.891
      // 0b0b: ldc_w 4.853
      // 0b0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b17: aastore
      // 0b18: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b1b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b1e: ldc_w "CTR_chest"
      // 0b21: new net/minecraft/client/animation/AnimationChannel
      // 0b24: dup
      // 0b25: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0b28: bipush 38
      // 0b2a: anewarray 28
      // 0b2d: dup
      // 0b2e: bipush 0
      // 0b2f: new net/minecraft/client/animation/Keyframe
      // 0b32: dup
      // 0b33: ldc 0.54
      // 0b35: ldc_w -13.64
      // 0b38: ldc_w 3.23
      // 0b3b: ldc_w -9.816
      // 0b3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b47: aastore
      // 0b48: dup
      // 0b49: bipush 1
      // 0b4a: new net/minecraft/client/animation/Keyframe
      // 0b4d: dup
      // 0b4e: ldc 0.58
      // 0b50: ldc_w -13.904
      // 0b53: ldc_w 2.167
      // 0b56: ldc_w -9.14
      // 0b59: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b62: aastore
      // 0b63: dup
      // 0b64: bipush 2
      // 0b65: new net/minecraft/client/animation/Keyframe
      // 0b68: dup
      // 0b69: ldc 0.62
      // 0b6b: ldc_w -14.029
      // 0b6e: ldc_w 1.322
      // 0b71: ldc_w -7.464
      // 0b74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b7d: aastore
      // 0b7e: dup
      // 0b7f: bipush 3
      // 0b80: new net/minecraft/client/animation/Keyframe
      // 0b83: dup
      // 0b84: ldc 0.66
      // 0b86: ldc_w -14.098
      // 0b89: ldc_w 0.689
      // 0b8c: ldc_w -5.293
      // 0b8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b98: aastore
      // 0b99: dup
      // 0b9a: bipush 4
      // 0b9b: new net/minecraft/client/animation/Keyframe
      // 0b9e: dup
      // 0b9f: ldc 0.71
      // 0ba1: ldc_w -14.158
      // 0ba4: ldc_w 0.252
      // 0ba7: ldc_w -3.125
      // 0baa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bb3: aastore
      // 0bb4: dup
      // 0bb5: bipush 5
      // 0bb6: new net/minecraft/client/animation/Keyframe
      // 0bb9: dup
      // 0bba: ldc 0.75
      // 0bbc: ldc_w -14.239
      // 0bbf: ldc_w -0.002
      // 0bc2: ldc_w -1.459
      // 0bc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bce: aastore
      // 0bcf: dup
      // 0bd0: bipush 6
      // 0bd2: new net/minecraft/client/animation/Keyframe
      // 0bd5: dup
      // 0bd6: ldc 0.79
      // 0bd8: ldc_w -14.372
      // 0bdb: ldc_w -0.084
      // 0bde: ldc_w -0.792
      // 0be1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0be4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bea: aastore
      // 0beb: dup
      // 0bec: bipush 7
      // 0bee: new net/minecraft/client/animation/Keyframe
      // 0bf1: dup
      // 0bf2: ldc 0.83
      // 0bf4: ldc_w -14.614
      // 0bf7: ldc_w -0.019
      // 0bfa: ldc_w -1.771
      // 0bfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c06: aastore
      // 0c07: dup
      // 0c08: bipush 8
      // 0c0a: new net/minecraft/client/animation/Keyframe
      // 0c0d: dup
      // 0c0e: ldc 0.87
      // 0c10: ldc_w -14.96
      // 0c13: ldc 0.17
      // 0c15: ldc_w -4.3
      // 0c18: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c21: aastore
      // 0c22: dup
      // 0c23: bipush 9
      // 0c25: new net/minecraft/client/animation/Keyframe
      // 0c28: dup
      // 0c29: ldc 0.92
      // 0c2b: ldc_w -15.351
      // 0c2e: ldc_w 0.475
      // 0c31: ldc_w -7.77
      // 0c34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c3d: aastore
      // 0c3e: dup
      // 0c3f: bipush 10
      // 0c41: new net/minecraft/client/animation/Keyframe
      // 0c44: dup
      // 0c45: ldc 0.96
      // 0c47: ldc_w -15.733
      // 0c4a: ldc_w 0.893
      // 0c4d: ldc_w -11.57
      // 0c50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c59: aastore
      // 0c5a: dup
      // 0c5b: bipush 11
      // 0c5d: new net/minecraft/client/animation/Keyframe
      // 0c60: dup
      // 0c61: fconst_1
      // 0c62: ldc_w -16.034
      // 0c65: ldc_w 1.436
      // 0c68: ldc_w -15.091
      // 0c6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c74: aastore
      // 0c75: dup
      // 0c76: bipush 12
      // 0c78: new net/minecraft/client/animation/Keyframe
      // 0c7b: dup
      // 0c7c: ldc 1.04
      // 0c7e: ldc_w -16.151
      // 0c81: ldc_w 2.135
      // 0c84: ldc_w -17.721
      // 0c87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c90: aastore
      // 0c91: dup
      // 0c92: bipush 13
      // 0c94: new net/minecraft/client/animation/Keyframe
      // 0c97: dup
      // 0c98: ldc 1.08
      // 0c9a: ldc_w -15.92
      // 0c9d: ldc_w 3.041
      // 0ca0: ldc_w -18.844
      // 0ca3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ca6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ca9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cac: aastore
      // 0cad: dup
      // 0cae: bipush 14
      // 0cb0: new net/minecraft/client/animation/Keyframe
      // 0cb3: dup
      // 0cb4: ldc 1.12
      // 0cb6: ldc_w -15.223
      // 0cb9: ldc_w 3.576
      // 0cbc: ldc_w -18.977
      // 0cbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cc8: aastore
      // 0cc9: dup
      // 0cca: bipush 15
      // 0ccc: new net/minecraft/client/animation/Keyframe
      // 0ccf: dup
      // 0cd0: ldc 1.16
      // 0cd2: ldc_w -14.518
      // 0cd5: ldc_w 3.883
      // 0cd8: ldc_w -19.073
      // 0cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ce4: aastore
      // 0ce5: dup
      // 0ce6: bipush 16
      // 0ce8: new net/minecraft/client/animation/Keyframe
      // 0ceb: dup
      // 0cec: ldc 1.25
      // 0cee: ldc_w -13.356
      // 0cf1: ldc_w 4.266
      // 0cf4: ldc_w -19.191
      // 0cf7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cfa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cfd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d00: aastore
      // 0d01: dup
      // 0d02: bipush 17
      // 0d04: new net/minecraft/client/animation/Keyframe
      // 0d07: dup
      // 0d08: ldc 1.29
      // 0d0a: ldc_w -12.927
      // 0d0d: ldc_w 4.378
      // 0d10: ldc_w -19.22
      // 0d13: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d1c: aastore
      // 0d1d: dup
      // 0d1e: bipush 18
      // 0d20: new net/minecraft/client/animation/Keyframe
      // 0d23: dup
      // 0d24: ldc 1.33
      // 0d26: ldc_w -12.614
      // 0d29: ldc_w 4.442
      // 0d2c: ldc_w -19.235
      // 0d2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d38: aastore
      // 0d39: dup
      // 0d3a: bipush 19
      // 0d3c: new net/minecraft/client/animation/Keyframe
      // 0d3f: dup
      // 0d40: ldc_w 1.37
      // 0d43: ldc_w -12.429
      // 0d46: ldc_w 4.46
      // 0d49: ldc_w -19.241
      // 0d4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d55: aastore
      // 0d56: dup
      // 0d57: bipush 20
      // 0d59: new net/minecraft/client/animation/Keyframe
      // 0d5c: dup
      // 0d5d: ldc 1.42
      // 0d5f: ldc_w -12.387
      // 0d62: ldc_w 4.43
      // 0d65: ldc_w -19.24
      // 0d68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d71: aastore
      // 0d72: dup
      // 0d73: bipush 21
      // 0d75: new net/minecraft/client/animation/Keyframe
      // 0d78: dup
      // 0d79: ldc 1.46
      // 0d7b: ldc_w -12.556
      // 0d7e: ldc_w 4.332
      // 0d81: ldc_w -19.222
      // 0d84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d8d: aastore
      // 0d8e: dup
      // 0d8f: bipush 22
      // 0d91: new net/minecraft/client/animation/Keyframe
      // 0d94: dup
      // 0d95: ldc_w 1.5
      // 0d98: ldc_w -12.954
      // 0d9b: ldc_w 4.16
      // 0d9e: ldc_w -19.176
      // 0da1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0da4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0da7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0daa: aastore
      // 0dab: dup
      // 0dac: bipush 23
      // 0dae: new net/minecraft/client/animation/Keyframe
      // 0db1: dup
      // 0db2: ldc_w 1.66
      // 0db5: ldc_w -15.372
      // 0db8: ldc_w 3.24
      // 0dbb: ldc_w -18.905
      // 0dbe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dc1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dc4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dc7: aastore
      // 0dc8: dup
      // 0dc9: bipush 24
      // 0dcb: new net/minecraft/client/animation/Keyframe
      // 0dce: dup
      // 0dcf: ldc 1.71
      // 0dd1: ldc_w -15.771
      // 0dd4: ldc_w 3.095
      // 0dd7: ldc_w -18.861
      // 0dda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ddd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0de0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0de3: aastore
      // 0de4: dup
      // 0de5: bipush 25
      // 0de7: new net/minecraft/client/animation/Keyframe
      // 0dea: dup
      // 0deb: ldc 1.75
      // 0ded: ldc_w -15.92
      // 0df0: ldc_w 3.041
      // 0df3: ldc_w -18.844
      // 0df6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0df9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dff: aastore
      // 0e00: dup
      // 0e01: bipush 26
      // 0e03: new net/minecraft/client/animation/Keyframe
      // 0e06: dup
      // 0e07: ldc 1.79
      // 0e09: ldc_w -15.747
      // 0e0c: ldc_w 3.116
      // 0e0f: ldc_w -18.862
      // 0e12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e1b: aastore
      // 0e1c: dup
      // 0e1d: bipush 27
      // 0e1f: new net/minecraft/client/animation/Keyframe
      // 0e22: dup
      // 0e23: ldc 1.83
      // 0e25: ldc_w -15.252
      // 0e28: ldc_w 3.332
      // 0e2b: ldc_w -18.908
      // 0e2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e37: aastore
      // 0e38: dup
      // 0e39: bipush 28
      // 0e3b: new net/minecraft/client/animation/Keyframe
      // 0e3e: dup
      // 0e3f: ldc_w 1.87
      // 0e42: ldc_w -14.542
      // 0e45: ldc_w 3.637
      // 0e48: ldc_w -18.974
      // 0e4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e54: aastore
      // 0e55: dup
      // 0e56: bipush 29
      // 0e58: new net/minecraft/client/animation/Keyframe
      // 0e5b: dup
      // 0e5c: ldc_w 1.96
      // 0e5f: ldc_w -13.146
      // 0e62: ldc_w 4.208
      // 0e65: ldc_w -19.122
      // 0e68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e71: aastore
      // 0e72: dup
      // 0e73: bipush 30
      // 0e75: new net/minecraft/client/animation/Keyframe
      // 0e78: dup
      // 0e79: fconst_2
      // 0e7a: ldc_w -12.691
      // 0e7d: ldc_w 4.374
      // 0e80: ldc_w -19.184
      // 0e83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e8c: aastore
      // 0e8d: dup
      // 0e8e: bipush 31
      // 0e90: new net/minecraft/client/animation/Keyframe
      // 0e93: dup
      // 0e94: ldc_w 2.04
      // 0e97: ldc_w -12.442
      // 0e9a: ldc_w 4.446
      // 0e9d: ldc_w -19.226
      // 0ea0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ea3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ea9: aastore
      // 0eaa: dup
      // 0eab: bipush 32
      // 0ead: new net/minecraft/client/animation/Keyframe
      // 0eb0: dup
      // 0eb1: ldc_w 2.08
      // 0eb4: ldc_w -12.387
      // 0eb7: ldc_w 4.43
      // 0eba: ldc_w -19.24
      // 0ebd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ec0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ec3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ec6: aastore
      // 0ec7: dup
      // 0ec8: bipush 33
      // 0eca: new net/minecraft/client/animation/Keyframe
      // 0ecd: dup
      // 0ece: ldc_w 2.12
      // 0ed1: ldc_w -12.564
      // 0ed4: ldc_w 4.328
      // 0ed7: ldc_w -19.217
      // 0eda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0edd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ee0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ee3: aastore
      // 0ee4: dup
      // 0ee5: bipush 34
      // 0ee7: new net/minecraft/client/animation/Keyframe
      // 0eea: dup
      // 0eeb: ldc_w 2.21
      // 0eee: ldc_w -13.673
      // 0ef1: ldc_w 3.875
      // 0ef4: ldc_w -19.083
      // 0ef7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0efa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0efd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f00: aastore
      // 0f01: dup
      // 0f02: bipush 35
      // 0f04: new net/minecraft/client/animation/Keyframe
      // 0f07: dup
      // 0f08: ldc_w 2.29
      // 0f0b: ldc_w -15.183
      // 0f0e: ldc_w 3.309
      // 0f11: ldc_w -18.922
      // 0f14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f1d: aastore
      // 0f1e: dup
      // 0f1f: bipush 36
      // 0f21: new net/minecraft/client/animation/Keyframe
      // 0f24: dup
      // 0f25: ldc_w 2.33
      // 0f28: ldc_w -15.72
      // 0f2b: ldc_w 3.113
      // 0f2e: ldc_w -18.866
      // 0f31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f3a: aastore
      // 0f3b: dup
      // 0f3c: bipush 37
      // 0f3e: new net/minecraft/client/animation/Keyframe
      // 0f41: dup
      // 0f42: ldc 2.37
      // 0f44: ldc_w -15.92
      // 0f47: ldc_w 3.041
      // 0f4a: ldc_w -18.844
      // 0f4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f56: aastore
      // 0f57: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f5a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f5d: ldc_w "CTR_head"
      // 0f60: new net/minecraft/client/animation/AnimationChannel
      // 0f63: dup
      // 0f64: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f67: bipush 38
      // 0f69: anewarray 28
      // 0f6c: dup
      // 0f6d: bipush 0
      // 0f6e: new net/minecraft/client/animation/Keyframe
      // 0f71: dup
      // 0f72: ldc 0.54
      // 0f74: ldc_w 38.456
      // 0f77: ldc_w -7.164
      // 0f7a: ldc_w -1.091
      // 0f7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f86: aastore
      // 0f87: dup
      // 0f88: bipush 1
      // 0f89: new net/minecraft/client/animation/Keyframe
      // 0f8c: dup
      // 0f8d: ldc 0.58
      // 0f8f: ldc_w 45.819
      // 0f92: ldc_w -5.03
      // 0f95: ldc_w -4.882
      // 0f98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fa1: aastore
      // 0fa2: dup
      // 0fa3: bipush 2
      // 0fa4: new net/minecraft/client/animation/Keyframe
      // 0fa7: dup
      // 0fa8: ldc 0.62
      // 0faa: ldc_w 52.176
      // 0fad: ldc_w -2.435
      // 0fb0: ldc_w -9.115
      // 0fb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fbc: aastore
      // 0fbd: dup
      // 0fbe: bipush 3
      // 0fbf: new net/minecraft/client/animation/Keyframe
      // 0fc2: dup
      // 0fc3: ldc 0.66
      // 0fc5: ldc_w 57.107
      // 0fc8: ldc_w 0.273
      // 0fcb: ldc_w -13.056
      // 0fce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd7: aastore
      // 0fd8: dup
      // 0fd9: bipush 4
      // 0fda: new net/minecraft/client/animation/Keyframe
      // 0fdd: dup
      // 0fde: ldc 0.71
      // 0fe0: ldc_w 60.461
      // 0fe3: ldc_w 2.672
      // 0fe6: ldc_w -16.093
      // 0fe9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ff2: aastore
      // 0ff3: dup
      // 0ff4: bipush 5
      // 0ff5: new net/minecraft/client/animation/Keyframe
      // 0ff8: dup
      // 0ff9: ldc 0.75
      // 0ffb: ldc_w 62.265
      // 0ffe: ldc_w 4.365
      // 1001: ldc_w -17.679
      // 1004: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1007: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 100a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 100d: aastore
      // 100e: dup
      // 100f: bipush 6
      // 1011: new net/minecraft/client/animation/Keyframe
      // 1014: dup
      // 1015: ldc 0.79
      // 1017: ldc_w 62.539
      // 101a: ldc_w 5.359
      // 101d: ldc_w -17.818
      // 1020: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1023: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1026: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1029: aastore
      // 102a: dup
      // 102b: bipush 7
      // 102d: new net/minecraft/client/animation/Keyframe
      // 1030: dup
      // 1031: ldc 0.83
      // 1033: ldc_w 61.26
      // 1036: ldc_w 5.901
      // 1039: ldc_w -16.425
      // 103c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 103f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1042: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1045: aastore
      // 1046: dup
      // 1047: bipush 8
      // 1049: new net/minecraft/client/animation/Keyframe
      // 104c: dup
      // 104d: ldc 0.87
      // 104f: ldc_w 58.436
      // 1052: ldc_w 6.028
      // 1055: ldc_w -13.629
      // 1058: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 105b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 105e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1061: aastore
      // 1062: dup
      // 1063: bipush 9
      // 1065: new net/minecraft/client/animation/Keyframe
      // 1068: dup
      // 1069: ldc 0.92
      // 106b: ldc_w 54.105
      // 106e: ldc_w 5.804
      // 1071: ldc_w -10.051
      // 1074: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1077: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 107a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 107d: aastore
      // 107e: dup
      // 107f: bipush 10
      // 1081: new net/minecraft/client/animation/Keyframe
      // 1084: dup
      // 1085: ldc 0.96
      // 1087: ldc_w 48.217
      // 108a: ldc_w 5.291
      // 108d: ldc_w -6.315
      // 1090: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1093: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1096: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1099: aastore
      // 109a: dup
      // 109b: bipush 11
      // 109d: new net/minecraft/client/animation/Keyframe
      // 10a0: dup
      // 10a1: fconst_1
      // 10a2: ldc_w 40.639
      // 10a5: ldc_w 4.539
      // 10a8: ldc_w -3.048
      // 10ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10b4: aastore
      // 10b5: dup
      // 10b6: bipush 12
      // 10b8: new net/minecraft/client/animation/Keyframe
      // 10bb: dup
      // 10bc: ldc 1.04
      // 10be: ldc_w 31.858
      // 10c1: ldc_w 3.58
      // 10c4: ldc -0.869
      // 10c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10cf: aastore
      // 10d0: dup
      // 10d1: bipush 13
      // 10d3: new net/minecraft/client/animation/Keyframe
      // 10d6: dup
      // 10d7: ldc 1.08
      // 10d9: ldc_w 24.812
      // 10dc: ldc_w 2.416
      // 10df: ldc_w -0.386
      // 10e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10eb: aastore
      // 10ec: dup
      // 10ed: bipush 14
      // 10ef: new net/minecraft/client/animation/Keyframe
      // 10f2: dup
      // 10f3: ldc 1.12
      // 10f5: ldc_w 22.635
      // 10f8: ldc_w 1.897
      // 10fb: ldc_w -0.583
      // 10fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1101: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1104: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1107: aastore
      // 1108: dup
      // 1109: bipush 15
      // 110b: new net/minecraft/client/animation/Keyframe
      // 110e: dup
      // 110f: ldc 1.16
      // 1111: ldc_w 21.721
      // 1114: ldc 1.71
      // 1116: ldc_w -0.619
      // 1119: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 111c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 111f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1122: aastore
      // 1123: dup
      // 1124: bipush 16
      // 1126: new net/minecraft/client/animation/Keyframe
      // 1129: dup
      // 112a: ldc 1.21
      // 112c: ldc_w 21.193
      // 112f: ldc_w 1.619
      // 1132: ldc_w -0.621
      // 1135: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1138: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 113b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 113e: aastore
      // 113f: dup
      // 1140: bipush 17
      // 1142: new net/minecraft/client/animation/Keyframe
      // 1145: dup
      // 1146: ldc 1.25
      // 1148: ldc_w 20.894
      // 114b: ldc_w 1.582
      // 114e: ldc_w -0.61
      // 1151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 115a: aastore
      // 115b: dup
      // 115c: bipush 18
      // 115e: new net/minecraft/client/animation/Keyframe
      // 1161: dup
      // 1162: ldc 1.29
      // 1164: ldc_w 20.761
      // 1167: ldc_w 1.582
      // 116a: ldc_w -0.594
      // 116d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1170: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1173: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1176: aastore
      // 1177: dup
      // 1178: bipush 19
      // 117a: new net/minecraft/client/animation/Keyframe
      // 117d: dup
      // 117e: ldc 1.33
      // 1180: ldc_w 20.762
      // 1183: ldc_w 1.609
      // 1186: ldc_w -0.573
      // 1189: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 118c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 118f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1192: aastore
      // 1193: dup
      // 1194: bipush 20
      // 1196: new net/minecraft/client/animation/Keyframe
      // 1199: dup
      // 119a: ldc 1.42
      // 119c: ldc_w 21.114
      // 119f: ldc_w 1.724
      // 11a2: ldc_w -0.515
      // 11a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ae: aastore
      // 11af: dup
      // 11b0: bipush 21
      // 11b2: new net/minecraft/client/animation/Keyframe
      // 11b5: dup
      // 11b6: ldc 1.46
      // 11b8: ldc_w 21.485
      // 11bb: ldc_w 1.809
      // 11be: ldc_w -0.485
      // 11c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ca: aastore
      // 11cb: dup
      // 11cc: bipush 22
      // 11ce: new net/minecraft/client/animation/Keyframe
      // 11d1: dup
      // 11d2: ldc 1.62
      // 11d4: ldc_w 23.834
      // 11d7: ldc_w 2.247
      // 11da: ldc_w -0.413
      // 11dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11e6: aastore
      // 11e7: dup
      // 11e8: bipush 23
      // 11ea: new net/minecraft/client/animation/Keyframe
      // 11ed: dup
      // 11ee: ldc_w 1.66
      // 11f1: ldc_w 24.339
      // 11f4: ldc_w 2.335
      // 11f7: ldc_w -0.399
      // 11fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1200: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1203: aastore
      // 1204: dup
      // 1205: bipush 24
      // 1207: new net/minecraft/client/animation/Keyframe
      // 120a: dup
      // 120b: ldc 1.71
      // 120d: ldc_w 24.685
      // 1210: ldc_w 2.394
      // 1213: ldc_w -0.389
      // 1216: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1219: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 121c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 121f: aastore
      // 1220: dup
      // 1221: bipush 25
      // 1223: new net/minecraft/client/animation/Keyframe
      // 1226: dup
      // 1227: ldc 1.75
      // 1229: ldc_w 24.812
      // 122c: ldc_w 2.416
      // 122f: ldc_w -0.386
      // 1232: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1235: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1238: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 123b: aastore
      // 123c: dup
      // 123d: bipush 26
      // 123f: new net/minecraft/client/animation/Keyframe
      // 1242: dup
      // 1243: ldc 1.79
      // 1245: ldc_w 24.588
      // 1248: ldc_w 2.371
      // 124b: ldc_w -0.403
      // 124e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1251: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1254: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1257: aastore
      // 1258: dup
      // 1259: bipush 27
      // 125b: new net/minecraft/client/animation/Keyframe
      // 125e: dup
      // 125f: ldc 1.83
      // 1261: ldc_w 23.958
      // 1264: ldc_w 2.246
      // 1267: ldc_w -0.453
      // 126a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 126d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1270: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1273: aastore
      // 1274: dup
      // 1275: bipush 28
      // 1277: new net/minecraft/client/animation/Keyframe
      // 127a: dup
      // 127b: ldc_w 1.87
      // 127e: ldc_w 23.08
      // 1281: ldc_w 2.07
      // 1284: ldc_w -0.518
      // 1287: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 128a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 128d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1290: aastore
      // 1291: dup
      // 1292: bipush 29
      // 1294: new net/minecraft/client/animation/Keyframe
      // 1297: dup
      // 1298: ldc_w 1.96
      // 129b: ldc_w 21.49
      // 129e: ldc_w 1.759
      // 12a1: ldc_w -0.591
      // 12a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12ad: aastore
      // 12ae: dup
      // 12af: bipush 30
      // 12b1: new net/minecraft/client/animation/Keyframe
      // 12b4: dup
      // 12b5: fconst_2
      // 12b6: ldc_w 21.073
      // 12b9: ldc_w 1.686
      // 12bc: ldc_w -0.582
      // 12bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12c8: aastore
      // 12c9: dup
      // 12ca: bipush 31
      // 12cc: new net/minecraft/client/animation/Keyframe
      // 12cf: dup
      // 12d0: ldc_w 2.04
      // 12d3: ldc_w 20.956
      // 12d6: ldc_w 1.676
      // 12d9: ldc_w -0.552
      // 12dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12e5: aastore
      // 12e6: dup
      // 12e7: bipush 32
      // 12e9: new net/minecraft/client/animation/Keyframe
      // 12ec: dup
      // 12ed: ldc_w 2.08
      // 12f0: ldc_w 21.114
      // 12f3: ldc_w 1.724
      // 12f6: ldc_w -0.515
      // 12f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1302: aastore
      // 1303: dup
      // 1304: bipush 33
      // 1306: new net/minecraft/client/animation/Keyframe
      // 1309: dup
      // 130a: ldc_w 2.12
      // 130d: ldc_w 21.498
      // 1310: ldc_w 1.812
      // 1313: ldc_w -0.487
      // 1316: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1319: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 131c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 131f: aastore
      // 1320: dup
      // 1321: bipush 34
      // 1323: new net/minecraft/client/animation/Keyframe
      // 1326: dup
      // 1327: ldc_w 2.21
      // 132a: ldc_w 22.763
      // 132d: ldc_w 2.056
      // 1330: ldc_w -0.451
      // 1333: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1336: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1339: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 133c: aastore
      // 133d: dup
      // 133e: bipush 35
      // 1340: new net/minecraft/client/animation/Keyframe
      // 1343: dup
      // 1344: ldc_w 2.29
      // 1347: ldc_w 24.172
      // 134a: ldc_w 2.306
      // 134d: ldc_w -0.408
      // 1350: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1353: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1356: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1359: aastore
      // 135a: dup
      // 135b: bipush 36
      // 135d: new net/minecraft/client/animation/Keyframe
      // 1360: dup
      // 1361: ldc_w 2.33
      // 1364: ldc_w 24.641
      // 1367: ldc_w 2.387
      // 136a: ldc_w -0.391
      // 136d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1370: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1373: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1376: aastore
      // 1377: dup
      // 1378: bipush 37
      // 137a: new net/minecraft/client/animation/Keyframe
      // 137d: dup
      // 137e: ldc 2.37
      // 1380: ldc_w 24.812
      // 1383: ldc_w 2.416
      // 1386: ldc_w -0.386
      // 1389: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 138c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 138f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1392: aastore
      // 1393: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1396: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1399: ldc_w "CTR_head"
      // 139c: new net/minecraft/client/animation/AnimationChannel
      // 139f: dup
      // 13a0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 13a3: bipush 1
      // 13a4: anewarray 28
      // 13a7: dup
      // 13a8: bipush 0
      // 13a9: new net/minecraft/client/animation/Keyframe
      // 13ac: dup
      // 13ad: ldc 0.87
      // 13af: fconst_0
      // 13b0: fconst_0
      // 13b1: fconst_0
      // 13b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13bb: aastore
      // 13bc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 13bf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 13c2: ldc_w "CTR_shoulde1r_left"
      // 13c5: new net/minecraft/client/animation/AnimationChannel
      // 13c8: dup
      // 13c9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 13cc: bipush 39
      // 13ce: anewarray 28
      // 13d1: dup
      // 13d2: bipush 0
      // 13d3: new net/minecraft/client/animation/Keyframe
      // 13d6: dup
      // 13d7: ldc 0.54
      // 13d9: ldc_w -34.482
      // 13dc: ldc_w -2.844
      // 13df: ldc_w -1.784
      // 13e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13eb: aastore
      // 13ec: dup
      // 13ed: bipush 1
      // 13ee: new net/minecraft/client/animation/Keyframe
      // 13f1: dup
      // 13f2: ldc 0.58
      // 13f4: ldc_w -35.744
      // 13f7: ldc_w -3.0
      // 13fa: ldc_w -1.798
      // 13fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1400: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1403: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1406: aastore
      // 1407: dup
      // 1408: bipush 2
      // 1409: new net/minecraft/client/animation/Keyframe
      // 140c: dup
      // 140d: ldc 0.62
      // 140f: ldc_w -39.304
      // 1412: ldc_w -3.392
      // 1415: ldc_w -1.896
      // 1418: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 141b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 141e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1421: aastore
      // 1422: dup
      // 1423: bipush 3
      // 1424: new net/minecraft/client/animation/Keyframe
      // 1427: dup
      // 1428: ldc 0.66
      // 142a: ldc_w -44.821
      // 142d: ldc_w -3.908
      // 1430: ldc_w -2.163
      // 1433: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1436: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1439: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 143c: aastore
      // 143d: dup
      // 143e: bipush 4
      // 143f: new net/minecraft/client/animation/Keyframe
      // 1442: dup
      // 1443: ldc 0.71
      // 1445: ldc_w -51.956
      // 1448: ldc_w -4.434
      // 144b: ldc_w -2.682
      // 144e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1451: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1454: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1457: aastore
      // 1458: dup
      // 1459: bipush 5
      // 145a: new net/minecraft/client/animation/Keyframe
      // 145d: dup
      // 145e: ldc 0.75
      // 1460: ldc_w -60.369
      // 1463: ldc_w -4.858
      // 1466: ldc_w -3.539
      // 1469: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 146c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 146f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1472: aastore
      // 1473: dup
      // 1474: bipush 6
      // 1476: new net/minecraft/client/animation/Keyframe
      // 1479: dup
      // 147a: ldc 0.79
      // 147c: ldc_w -69.719
      // 147f: ldc_w -5.068
      // 1482: ldc_w -4.817
      // 1485: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1488: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 148b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 148e: aastore
      // 148f: dup
      // 1490: bipush 7
      // 1492: new net/minecraft/client/animation/Keyframe
      // 1495: dup
      // 1496: ldc 0.83
      // 1498: ldc_w -79.683
      // 149b: ldc_w -5.121
      // 149e: ldc_w -8.539
      // 14a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14aa: aastore
      // 14ab: dup
      // 14ac: bipush 8
      // 14ae: new net/minecraft/client/animation/Keyframe
      // 14b1: dup
      // 14b2: ldc 0.87
      // 14b4: ldc_w -90.0
      // 14b7: ldc_w -5.155
      // 14ba: ldc_w -16.083
      // 14bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14c6: aastore
      // 14c7: dup
      // 14c8: bipush 9
      // 14ca: new net/minecraft/client/animation/Keyframe
      // 14cd: dup
      // 14ce: ldc 0.92
      // 14d0: ldc_w -100.425
      // 14d3: ldc_w -5.174
      // 14d6: ldc_w -26.569
      // 14d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14e2: aastore
      // 14e3: dup
      // 14e4: bipush 10
      // 14e6: new net/minecraft/client/animation/Keyframe
      // 14e9: dup
      // 14ea: ldc 0.96
      // 14ec: ldc_w -110.715
      // 14ef: ldc_w -5.183
      // 14f2: ldc_w -39.119
      // 14f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14fe: aastore
      // 14ff: dup
      // 1500: bipush 11
      // 1502: new net/minecraft/client/animation/Keyframe
      // 1505: dup
      // 1506: fconst_1
      // 1507: ldc_w -120.624
      // 150a: ldc_w -5.185
      // 150d: ldc_w -52.85
      // 1510: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1513: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1516: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1519: aastore
      // 151a: dup
      // 151b: bipush 12
      // 151d: new net/minecraft/client/animation/Keyframe
      // 1520: dup
      // 1521: ldc 1.04
      // 1523: ldc_w -129.907
      // 1526: ldc_w -5.185
      // 1529: ldc_w -66.883
      // 152c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 152f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1532: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1535: aastore
      // 1536: dup
      // 1537: bipush 13
      // 1539: new net/minecraft/client/animation/Keyframe
      // 153c: dup
      // 153d: ldc 1.08
      // 153f: ldc_w -138.321
      // 1542: ldc_w -5.188
      // 1545: ldc_w -80.339
      // 1548: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 154b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 154e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1551: aastore
      // 1552: dup
      // 1553: bipush 14
      // 1555: new net/minecraft/client/animation/Keyframe
      // 1558: dup
      // 1559: ldc 1.12
      // 155b: ldc_w -145.621
      // 155e: ldc_w -5.196
      // 1561: ldc_w -92.336
      // 1564: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1567: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 156a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 156d: aastore
      // 156e: dup
      // 156f: bipush 15
      // 1571: new net/minecraft/client/animation/Keyframe
      // 1574: dup
      // 1575: ldc 1.16
      // 1577: ldc_w -151.562
      // 157a: ldc_w -5.216
      // 157d: ldc_w -101.996
      // 1580: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1583: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1586: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1589: aastore
      // 158a: dup
      // 158b: bipush 16
      // 158d: new net/minecraft/client/animation/Keyframe
      // 1590: dup
      // 1591: ldc 1.21
      // 1593: ldc_w -155.899
      // 1596: ldc_w -5.25
      // 1599: ldc_w -108.437
      // 159c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 159f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15a5: aastore
      // 15a6: dup
      // 15a7: bipush 17
      // 15a9: new net/minecraft/client/animation/Keyframe
      // 15ac: dup
      // 15ad: ldc 1.25
      // 15af: ldc_w -158.388
      // 15b2: ldc_w -5.303
      // 15b5: ldc_w -110.779
      // 15b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15c1: aastore
      // 15c2: dup
      // 15c3: bipush 18
      // 15c5: new net/minecraft/client/animation/Keyframe
      // 15c8: dup
      // 15c9: ldc 1.29
      // 15cb: ldc_w -159.594
      // 15ce: ldc_w -5.461
      // 15d1: ldc_w -110.744
      // 15d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15dd: aastore
      // 15de: dup
      // 15df: bipush 19
      // 15e1: new net/minecraft/client/animation/Keyframe
      // 15e4: dup
      // 15e5: ldc 1.33
      // 15e7: ldc_w -160.326
      // 15ea: ldc_w -5.743
      // 15ed: ldc_w -110.658
      // 15f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15f9: aastore
      // 15fa: dup
      // 15fb: bipush 20
      // 15fd: new net/minecraft/client/animation/Keyframe
      // 1600: dup
      // 1601: ldc_w 1.37
      // 1604: ldc_w -160.702
      // 1607: ldc_w -6.058
      // 160a: ldc_w -110.556
      // 160d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1610: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1613: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1616: aastore
      // 1617: dup
      // 1618: bipush 21
      // 161a: new net/minecraft/client/animation/Keyframe
      // 161d: dup
      // 161e: ldc 1.42
      // 1620: ldc_w -160.841
      // 1623: ldc_w -6.315
      // 1626: ldc_w -110.471
      // 1629: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 162c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 162f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1632: aastore
      // 1633: dup
      // 1634: bipush 22
      // 1636: new net/minecraft/client/animation/Keyframe
      // 1639: dup
      // 163a: ldc 1.46
      // 163c: ldc_w -160.86
      // 163f: ldc_w -6.42
      // 1642: ldc_w -110.435
      // 1645: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1648: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 164b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 164e: aastore
      // 164f: dup
      // 1650: bipush 23
      // 1652: new net/minecraft/client/animation/Keyframe
      // 1655: dup
      // 1656: ldc_w 1.5
      // 1659: ldc_w -160.723
      // 165c: ldc_w -6.359
      // 165f: ldc_w -110.454
      // 1662: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1665: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1668: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 166b: aastore
      // 166c: dup
      // 166d: bipush 24
      // 166f: new net/minecraft/client/animation/Keyframe
      // 1672: dup
      // 1673: ldc 1.54
      // 1675: ldc_w -160.37
      // 1678: ldc_w -6.199
      // 167b: ldc_w -110.503
      // 167e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1681: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1684: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1687: aastore
      // 1688: dup
      // 1689: bipush 25
      // 168b: new net/minecraft/client/animation/Keyframe
      // 168e: dup
      // 168f: ldc_w 1.66
      // 1692: ldc_w -158.878
      // 1695: ldc_w -5.525
      // 1698: ldc_w -110.711
      // 169b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 169e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16a4: aastore
      // 16a5: dup
      // 16a6: bipush 26
      // 16a8: new net/minecraft/client/animation/Keyframe
      // 16ab: dup
      // 16ac: ldc 1.71
      // 16ae: ldc_w -158.525
      // 16b1: ldc_w -5.365
      // 16b4: ldc_w -110.76
      // 16b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16c0: aastore
      // 16c1: dup
      // 16c2: bipush 27
      // 16c4: new net/minecraft/client/animation/Keyframe
      // 16c7: dup
      // 16c8: ldc 1.75
      // 16ca: ldc_w -158.388
      // 16cd: ldc_w -5.303
      // 16d0: ldc_w -110.779
      // 16d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16dc: aastore
      // 16dd: dup
      // 16de: bipush 28
      // 16e0: new net/minecraft/client/animation/Keyframe
      // 16e3: dup
      // 16e4: ldc 1.79
      // 16e6: ldc_w -158.494
      // 16e9: ldc_w -5.351
      // 16ec: ldc_w -110.765
      // 16ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16f8: aastore
      // 16f9: dup
      // 16fa: bipush 29
      // 16fc: new net/minecraft/client/animation/Keyframe
      // 16ff: dup
      // 1700: ldc 1.83
      // 1702: ldc_w -158.774
      // 1705: ldc_w -5.478
      // 1708: ldc_w -110.726
      // 170b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 170e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1711: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1714: aastore
      // 1715: dup
      // 1716: bipush 30
      // 1718: new net/minecraft/client/animation/Keyframe
      // 171b: dup
      // 171c: ldc_w 1.92
      // 171f: ldc_w -159.624
      // 1722: ldc_w -5.862
      // 1725: ldc_w -110.607
      // 1728: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 172b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 172e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1731: aastore
      // 1732: dup
      // 1733: bipush 31
      // 1735: new net/minecraft/client/animation/Keyframe
      // 1738: dup
      // 1739: fconst_2
      // 173a: ldc_w -160.474
      // 173d: ldc_w -6.246
      // 1740: ldc_w -110.489
      // 1743: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1746: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1749: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 174c: aastore
      // 174d: dup
      // 174e: bipush 32
      // 1750: new net/minecraft/client/animation/Keyframe
      // 1753: dup
      // 1754: ldc_w 2.04
      // 1757: ldc_w -160.754
      // 175a: ldc_w -6.372
      // 175d: ldc_w -110.45
      // 1760: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1763: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1766: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1769: aastore
      // 176a: dup
      // 176b: bipush 33
      // 176d: new net/minecraft/client/animation/Keyframe
      // 1770: dup
      // 1771: ldc_w 2.08
      // 1774: ldc_w -160.86
      // 1777: ldc_w -6.42
      // 177a: ldc_w -110.435
      // 177d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1780: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1783: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1786: aastore
      // 1787: dup
      // 1788: bipush 34
      // 178a: new net/minecraft/client/animation/Keyframe
      // 178d: dup
      // 178e: ldc_w 2.12
      // 1791: ldc_w -160.723
      // 1794: ldc_w -6.359
      // 1797: ldc_w -110.454
      // 179a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 179d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17a3: aastore
      // 17a4: dup
      // 17a5: bipush 35
      // 17a7: new net/minecraft/client/animation/Keyframe
      // 17aa: dup
      // 17ab: ldc_w 2.21
      // 17ae: ldc_w -159.887
      // 17b1: ldc_w -5.981
      // 17b4: ldc_w -110.571
      // 17b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17c0: aastore
      // 17c1: dup
      // 17c2: bipush 36
      // 17c4: new net/minecraft/client/animation/Keyframe
      // 17c7: dup
      // 17c8: ldc_w 2.29
      // 17cb: ldc_w -158.878
      // 17ce: ldc_w -5.525
      // 17d1: ldc_w -110.711
      // 17d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17dd: aastore
      // 17de: dup
      // 17df: bipush 37
      // 17e1: new net/minecraft/client/animation/Keyframe
      // 17e4: dup
      // 17e5: ldc_w 2.33
      // 17e8: ldc_w -158.525
      // 17eb: ldc_w -5.365
      // 17ee: ldc_w -110.76
      // 17f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17fa: aastore
      // 17fb: dup
      // 17fc: bipush 38
      // 17fe: new net/minecraft/client/animation/Keyframe
      // 1801: dup
      // 1802: ldc 2.37
      // 1804: ldc_w -158.388
      // 1807: ldc_w -5.303
      // 180a: ldc_w -110.779
      // 180d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1810: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1813: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1816: aastore
      // 1817: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 181a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 181d: ldc_w "CTR_shoulde1r_left"
      // 1820: new net/minecraft/client/animation/AnimationChannel
      // 1823: dup
      // 1824: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1827: bipush 7
      // 1829: anewarray 28
      // 182c: dup
      // 182d: bipush 0
      // 182e: new net/minecraft/client/animation/Keyframe
      // 1831: dup
      // 1832: ldc 0.54
      // 1834: ldc_w 7.153
      // 1837: ldc_w 0.9
      // 183a: ldc_w -16.763
      // 183d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1840: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1843: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1846: aastore
      // 1847: dup
      // 1848: bipush 1
      // 1849: new net/minecraft/client/animation/Keyframe
      // 184c: dup
      // 184d: ldc 0.58
      // 184f: ldc_w 7.19
      // 1852: ldc_w 0.194
      // 1855: ldc_w -16.633
      // 1858: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 185b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 185e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1861: aastore
      // 1862: dup
      // 1863: bipush 2
      // 1864: new net/minecraft/client/animation/Keyframe
      // 1867: dup
      // 1868: ldc 0.62
      // 186a: ldc_w 7.283
      // 186d: ldc_w -1.573
      // 1870: ldc_w -16.309
      // 1873: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1876: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1879: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 187c: aastore
      // 187d: dup
      // 187e: bipush 3
      // 187f: new net/minecraft/client/animation/Keyframe
      // 1882: dup
      // 1883: ldc 0.66
      // 1885: ldc_w 7.405
      // 1888: ldc_w -3.869
      // 188b: ldc_w -15.887
      // 188e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1891: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1894: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1897: aastore
      // 1898: dup
      // 1899: bipush 4
      // 189a: new net/minecraft/client/animation/Keyframe
      // 189d: dup
      // 189e: ldc 0.71
      // 18a0: ldc_w 7.526
      // 18a3: ldc_w -6.165
      // 18a6: ldc_w -15.466
      // 18a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18b2: aastore
      // 18b3: dup
      // 18b4: bipush 5
      // 18b5: new net/minecraft/client/animation/Keyframe
      // 18b8: dup
      // 18b9: ldc 0.75
      // 18bb: ldc_w 7.62
      // 18be: ldc_w -7.931
      // 18c1: ldc_w -15.142
      // 18c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18cd: aastore
      // 18ce: dup
      // 18cf: bipush 6
      // 18d1: new net/minecraft/client/animation/Keyframe
      // 18d4: dup
      // 18d5: ldc 0.79
      // 18d7: ldc_w 7.657
      // 18da: ldc_w -8.638
      // 18dd: ldc_w -15.012
      // 18e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18e9: aastore
      // 18ea: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 18ed: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 18f0: ldc_w "CTR_upperarm_left"
      // 18f3: new net/minecraft/client/animation/AnimationChannel
      // 18f6: dup
      // 18f7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 18fa: bipush 9
      // 18fc: anewarray 28
      // 18ff: dup
      // 1900: bipush 0
      // 1901: new net/minecraft/client/animation/Keyframe
      // 1904: dup
      // 1905: ldc 0.79
      // 1907: ldc_w 29.348
      // 190a: ldc_w 5.814
      // 190d: ldc_w 9.647
      // 1910: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1913: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1916: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1919: aastore
      // 191a: dup
      // 191b: bipush 1
      // 191c: new net/minecraft/client/animation/Keyframe
      // 191f: dup
      // 1920: ldc 0.83
      // 1922: ldc_w 26.579
      // 1925: ldc_w 11.287
      // 1928: ldc_w 8.676
      // 192b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 192e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1931: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1934: aastore
      // 1935: dup
      // 1936: bipush 2
      // 1937: new net/minecraft/client/animation/Keyframe
      // 193a: dup
      // 193b: ldc 0.87
      // 193d: ldc_w 19.655
      // 1940: ldc_w 24.969
      // 1943: ldc_w 6.249
      // 1946: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1949: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 194c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 194f: aastore
      // 1950: dup
      // 1951: bipush 3
      // 1952: new net/minecraft/client/animation/Keyframe
      // 1955: dup
      // 1956: ldc 0.92
      // 1958: ldc_w 10.654
      // 195b: ldc_w 42.756
      // 195e: ldc_w 3.094
      // 1961: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1964: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1967: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 196a: aastore
      // 196b: dup
      // 196c: bipush 4
      // 196d: new net/minecraft/client/animation/Keyframe
      // 1970: dup
      // 1971: ldc 0.96
      // 1973: ldc_w 1.654
      // 1976: ldc_w 60.543
      // 1979: ldc_w -0.06
      // 197c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 197f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1982: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1985: aastore
      // 1986: dup
      // 1987: bipush 5
      // 1988: new net/minecraft/client/animation/Keyframe
      // 198b: dup
      // 198c: fconst_1
      // 198d: ldc_w -5.27
      // 1990: ldc_w 74.225
      // 1993: ldc_w -2.487
      // 1996: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1999: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 199c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 199f: aastore
      // 19a0: dup
      // 19a1: bipush 6
      // 19a3: new net/minecraft/client/animation/Keyframe
      // 19a6: dup
      // 19a7: ldc 1.04
      // 19a9: ldc_w -8.039
      // 19ac: ldc_w 79.698
      // 19af: ldc_w -3.458
      // 19b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19bb: aastore
      // 19bc: dup
      // 19bd: bipush 7
      // 19bf: new net/minecraft/client/animation/Keyframe
      // 19c2: dup
      // 19c3: ldc 1.08
      // 19c5: ldc_w -5.186
      // 19c8: ldc_w 60.563
      // 19cb: ldc_w -2.061
      // 19ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19d7: aastore
      // 19d8: dup
      // 19d9: bipush 8
      // 19db: new net/minecraft/client/animation/Keyframe
      // 19de: dup
      // 19df: ldc 2.37
      // 19e1: ldc_w -5.186
      // 19e4: ldc_w 60.563
      // 19e7: ldc_w -2.062
      // 19ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19f3: aastore
      // 19f4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 19f7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 19fa: ldc_w "CTR_lowerarm_left"
      // 19fd: new net/minecraft/client/animation/AnimationChannel
      // 1a00: dup
      // 1a01: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1a04: bipush 30
      // 1a06: anewarray 28
      // 1a09: dup
      // 1a0a: bipush 0
      // 1a0b: new net/minecraft/client/animation/Keyframe
      // 1a0e: dup
      // 1a0f: ldc 0.54
      // 1a11: ldc_w -10.61
      // 1a14: ldc_w -24.89
      // 1a17: ldc_w 2.675
      // 1a1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a23: aastore
      // 1a24: dup
      // 1a25: bipush 1
      // 1a26: new net/minecraft/client/animation/Keyframe
      // 1a29: dup
      // 1a2a: ldc 0.58
      // 1a2c: ldc_w -10.626
      // 1a2f: ldc_w -26.067
      // 1a32: ldc_w 2.687
      // 1a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a3e: aastore
      // 1a3f: dup
      // 1a40: bipush 2
      // 1a41: new net/minecraft/client/animation/Keyframe
      // 1a44: dup
      // 1a45: ldc 0.62
      // 1a47: ldc_w -10.675
      // 1a4a: ldc_w -29.009
      // 1a4d: ldc_w 2.711
      // 1a50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a59: aastore
      // 1a5a: dup
      // 1a5b: bipush 3
      // 1a5c: new net/minecraft/client/animation/Keyframe
      // 1a5f: dup
      // 1a60: ldc 0.66
      // 1a62: ldc_w -10.749
      // 1a65: ldc_w -32.834
      // 1a68: ldc_w 2.731
      // 1a6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a74: aastore
      // 1a75: dup
      // 1a76: bipush 4
      // 1a77: new net/minecraft/client/animation/Keyframe
      // 1a7a: dup
      // 1a7b: ldc 0.71
      // 1a7d: ldc_w -10.827
      // 1a80: ldc_w -36.659
      // 1a83: ldc_w 2.71
      // 1a86: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a8f: aastore
      // 1a90: dup
      // 1a91: bipush 5
      // 1a92: new net/minecraft/client/animation/Keyframe
      // 1a95: dup
      // 1a96: ldc 0.75
      // 1a98: ldc_w -10.855
      // 1a9b: ldc_w -39.602
      // 1a9e: ldc_w 2.584
      // 1aa1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aa4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aaa: aastore
      // 1aab: dup
      // 1aac: bipush 6
      // 1aae: new net/minecraft/client/animation/Keyframe
      // 1ab1: dup
      // 1ab2: ldc 0.79
      // 1ab4: ldc_w -10.786
      // 1ab7: ldc_w -40.779
      // 1aba: ldc_w 2.289
      // 1abd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ac0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ac3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ac6: aastore
      // 1ac7: dup
      // 1ac8: bipush 7
      // 1aca: new net/minecraft/client/animation/Keyframe
      // 1acd: dup
      // 1ace: ldc 0.83
      // 1ad0: ldc_w -9.836
      // 1ad3: ldc_w -37.994
      // 1ad6: ldc_w 0.98
      // 1ad9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1adc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1adf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ae2: aastore
      // 1ae3: dup
      // 1ae4: bipush 8
      // 1ae6: new net/minecraft/client/animation/Keyframe
      // 1ae9: dup
      // 1aea: ldc 0.87
      // 1aec: ldc_w -8.75
      // 1aef: ldc_w -31.339
      // 1af2: ldc_w -0.664
      // 1af5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1af8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1afb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1afe: aastore
      // 1aff: dup
      // 1b00: bipush 9
      // 1b02: new net/minecraft/client/animation/Keyframe
      // 1b05: dup
      // 1b06: ldc 0.92
      // 1b08: ldc_w -8.387
      // 1b0b: ldc_w -23.389
      // 1b0e: ldc_w -1.778
      // 1b11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b1a: aastore
      // 1b1b: dup
      // 1b1c: bipush 10
      // 1b1e: new net/minecraft/client/animation/Keyframe
      // 1b21: dup
      // 1b22: ldc 0.96
      // 1b24: ldc_w -8.53
      // 1b27: ldc_w -16.729
      // 1b2a: ldc_w -2.428
      // 1b2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b36: aastore
      // 1b37: dup
      // 1b38: bipush 11
      // 1b3a: new net/minecraft/client/animation/Keyframe
      // 1b3d: dup
      // 1b3e: fconst_1
      // 1b3f: ldc_w -8.836
      // 1b42: ldc_w -13.938
      // 1b45: ldc_w -2.662
      // 1b48: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b51: aastore
      // 1b52: dup
      // 1b53: bipush 12
      // 1b55: new net/minecraft/client/animation/Keyframe
      // 1b58: dup
      // 1b59: ldc 1.04
      // 1b5b: ldc_w -8.931
      // 1b5e: ldc_w -15.355
      // 1b61: ldc_w -2.775
      // 1b64: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b6d: aastore
      // 1b6e: dup
      // 1b6f: bipush 13
      // 1b71: new net/minecraft/client/animation/Keyframe
      // 1b74: dup
      // 1b75: ldc 1.08
      // 1b77: ldc_w -8.685
      // 1b7a: ldc_w -18.873
      // 1b7d: ldc_w -3.083
      // 1b80: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b89: aastore
      // 1b8a: dup
      // 1b8b: bipush 14
      // 1b8d: new net/minecraft/client/animation/Keyframe
      // 1b90: dup
      // 1b91: ldc 1.12
      // 1b93: ldc_w -8.181
      // 1b96: ldc_w -23.385
      // 1b99: ldc_w -3.552
      // 1b9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ba2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ba5: aastore
      // 1ba6: dup
      // 1ba7: bipush 15
      // 1ba9: new net/minecraft/client/animation/Keyframe
      // 1bac: dup
      // 1bad: ldc 1.16
      // 1baf: ldc_w -7.557
      // 1bb2: ldc_w -27.79
      // 1bb5: ldc_w -4.088
      // 1bb8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bbb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bbe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bc1: aastore
      // 1bc2: dup
      // 1bc3: bipush 16
      // 1bc5: new net/minecraft/client/animation/Keyframe
      // 1bc8: dup
      // 1bc9: ldc 1.21
      // 1bcb: ldc_w -7.113
      // 1bce: ldc_w -30.988
      // 1bd1: ldc_w -4.428
      // 1bd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bdd: aastore
      // 1bde: dup
      // 1bdf: bipush 17
      // 1be1: new net/minecraft/client/animation/Keyframe
      // 1be4: dup
      // 1be5: ldc 1.25
      // 1be7: ldc_w -6.955
      // 1bea: ldc_w -33.054
      // 1bed: ldc_w -4.448
      // 1bf0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bf3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bf6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bf9: aastore
      // 1bfa: dup
      // 1bfb: bipush 18
      // 1bfd: new net/minecraft/client/animation/Keyframe
      // 1c00: dup
      // 1c01: ldc 1.29
      // 1c03: ldc_w -6.907
      // 1c06: ldc_w -34.811
      // 1c09: ldc_w -4.295
      // 1c0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c15: aastore
      // 1c16: dup
      // 1c17: bipush 19
      // 1c19: new net/minecraft/client/animation/Keyframe
      // 1c1c: dup
      // 1c1d: ldc 1.33
      // 1c1f: ldc_w -6.988
      // 1c22: ldc_w -36.282
      // 1c25: ldc_w -3.963
      // 1c28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c31: aastore
      // 1c32: dup
      // 1c33: bipush 20
      // 1c35: new net/minecraft/client/animation/Keyframe
      // 1c38: dup
      // 1c39: ldc_w 1.37
      // 1c3c: ldc_w -7.207
      // 1c3f: ldc_w -37.493
      // 1c42: ldc_w -3.456
      // 1c45: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c4e: aastore
      // 1c4f: dup
      // 1c50: bipush 21
      // 1c52: new net/minecraft/client/animation/Keyframe
      // 1c55: dup
      // 1c56: ldc 1.42
      // 1c58: ldc_w -7.556
      // 1c5b: ldc_w -38.467
      // 1c5e: ldc_w -2.795
      // 1c61: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c6a: aastore
      // 1c6b: dup
      // 1c6c: bipush 22
      // 1c6e: new net/minecraft/client/animation/Keyframe
      // 1c71: dup
      // 1c72: ldc 1.46
      // 1c74: ldc_w -8.011
      // 1c77: ldc_w -39.229
      // 1c7a: ldc_w -2.015
      // 1c7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c86: aastore
      // 1c87: dup
      // 1c88: bipush 23
      // 1c8a: new net/minecraft/client/animation/Keyframe
      // 1c8d: dup
      // 1c8e: ldc_w 1.5
      // 1c91: ldc_w -8.537
      // 1c94: ldc_w -39.803
      // 1c97: ldc_w -1.165
      // 1c9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ca0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ca3: aastore
      // 1ca4: dup
      // 1ca5: bipush 24
      // 1ca7: new net/minecraft/client/animation/Keyframe
      // 1caa: dup
      // 1cab: ldc 1.54
      // 1cad: ldc_w -9.09
      // 1cb0: ldc_w -40.215
      // 1cb3: ldc_w -0.301
      // 1cb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cbf: aastore
      // 1cc0: dup
      // 1cc1: bipush 25
      // 1cc3: new net/minecraft/client/animation/Keyframe
      // 1cc6: dup
      // 1cc7: ldc 1.58
      // 1cc9: ldc_w -9.624
      // 1ccc: ldc_w -40.491
      // 1ccf: ldc_w 0.517
      // 1cd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cdb: aastore
      // 1cdc: dup
      // 1cdd: bipush 26
      // 1cdf: new net/minecraft/client/animation/Keyframe
      // 1ce2: dup
      // 1ce3: ldc 1.62
      // 1ce5: ldc_w -10.094
      // 1ce8: ldc_w -40.658
      // 1ceb: ldc_w 1.233
      // 1cee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cf1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cf7: aastore
      // 1cf8: dup
      // 1cf9: bipush 27
      // 1cfb: new net/minecraft/client/animation/Keyframe
      // 1cfe: dup
      // 1cff: ldc_w 1.66
      // 1d02: ldc_w -10.464
      // 1d05: ldc_w -40.743
      // 1d08: ldc_w 1.795
      // 1d0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d14: aastore
      // 1d15: dup
      // 1d16: bipush 28
      // 1d18: new net/minecraft/client/animation/Keyframe
      // 1d1b: dup
      // 1d1c: ldc 1.71
      // 1d1e: ldc_w -10.702
      // 1d21: ldc_w -40.775
      // 1d24: ldc_w 2.16
      // 1d27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d30: aastore
      // 1d31: dup
      // 1d32: bipush 29
      // 1d34: new net/minecraft/client/animation/Keyframe
      // 1d37: dup
      // 1d38: ldc 1.75
      // 1d3a: ldc_w -10.786
      // 1d3d: ldc_w -40.779
      // 1d40: ldc_w 2.289
      // 1d43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d4c: aastore
      // 1d4d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d50: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d53: ldc_w "CTR_upperarm_left.001"
      // 1d56: new net/minecraft/client/animation/AnimationChannel
      // 1d59: dup
      // 1d5a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d5d: bipush 29
      // 1d5f: anewarray 28
      // 1d62: dup
      // 1d63: bipush 0
      // 1d64: new net/minecraft/client/animation/Keyframe
      // 1d67: dup
      // 1d68: fconst_0
      // 1d69: ldc_w -0.87
      // 1d6c: ldc_w -70.789
      // 1d6f: ldc_w 2.17
      // 1d72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d7b: aastore
      // 1d7c: dup
      // 1d7d: bipush 1
      // 1d7e: new net/minecraft/client/animation/Keyframe
      // 1d81: dup
      // 1d82: ldc 0.04
      // 1d84: ldc_w 4.567
      // 1d87: ldc_w -80.834
      // 1d8a: ldc_w -2.922
      // 1d8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d96: aastore
      // 1d97: dup
      // 1d98: bipush 2
      // 1d99: new net/minecraft/client/animation/Keyframe
      // 1d9c: dup
      // 1d9d: ldc 0.08
      // 1d9f: ldc_w 15.332
      // 1da2: ldc_w -89.737
      // 1da5: ldc_w -13.234
      // 1da8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1db1: aastore
      // 1db2: dup
      // 1db3: bipush 3
      // 1db4: new net/minecraft/client/animation/Keyframe
      // 1db7: dup
      // 1db8: ldc 0.12
      // 1dba: ldc_w 30.034
      // 1dbd: ldc_w -90.247
      // 1dc0: ldc_w -27.395
      // 1dc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dcc: aastore
      // 1dcd: dup
      // 1dce: bipush 4
      // 1dcf: new net/minecraft/client/animation/Keyframe
      // 1dd2: dup
      // 1dd3: ldc 0.17
      // 1dd5: ldc_w 43.065
      // 1dd8: ldc_w -80.358
      // 1ddb: ldc_w -39.774
      // 1dde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1de1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1de4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1de7: aastore
      // 1de8: dup
      // 1de9: bipush 5
      // 1dea: new net/minecraft/client/animation/Keyframe
      // 1ded: dup
      // 1dee: ldc 0.21
      // 1df0: ldc_w 51.273
      // 1df3: ldc_w -64.246
      // 1df6: ldc_w -47.137
      // 1df9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e02: aastore
      // 1e03: dup
      // 1e04: bipush 6
      // 1e06: new net/minecraft/client/animation/Keyframe
      // 1e09: dup
      // 1e0a: ldc 0.25
      // 1e0c: ldc_w 55.565
      // 1e0f: ldc_w -46.158
      // 1e12: ldc_w -50.18
      // 1e15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e1e: aastore
      // 1e1f: dup
      // 1e20: bipush 7
      // 1e22: new net/minecraft/client/animation/Keyframe
      // 1e25: dup
      // 1e26: ldc 0.29
      // 1e28: ldc_w 55.209
      // 1e2b: ldc_w -22.276
      // 1e2e: ldc_w -46.402
      // 1e31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e3a: aastore
      // 1e3b: dup
      // 1e3c: bipush 8
      // 1e3e: new net/minecraft/client/animation/Keyframe
      // 1e41: dup
      // 1e42: ldc 0.33
      // 1e44: ldc_w -29.542
      // 1e47: ldc_w 3.019
      // 1e4a: ldc_w -113.487
      // 1e4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e56: aastore
      // 1e57: dup
      // 1e58: bipush 9
      // 1e5a: new net/minecraft/client/animation/Keyframe
      // 1e5d: dup
      // 1e5e: ldc 0.38
      // 1e60: ldc_w 28.025
      // 1e63: ldc_w 19.024
      // 1e66: ldc_w -30.811
      // 1e69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e72: aastore
      // 1e73: dup
      // 1e74: bipush 10
      // 1e76: new net/minecraft/client/animation/Keyframe
      // 1e79: dup
      // 1e7a: ldc 0.42
      // 1e7c: ldc_w 16.6
      // 1e7f: ldc_w 26.961
      // 1e82: ldc_w -15.541
      // 1e85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e8e: aastore
      // 1e8f: dup
      // 1e90: bipush 11
      // 1e92: new net/minecraft/client/animation/Keyframe
      // 1e95: dup
      // 1e96: ldc 0.46
      // 1e98: ldc_w 6.077
      // 1e9b: ldc_w 29.488
      // 1e9e: ldc_w -3.086
      // 1ea1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ea4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ea7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eaa: aastore
      // 1eab: dup
      // 1eac: bipush 12
      // 1eae: new net/minecraft/client/animation/Keyframe
      // 1eb1: dup
      // 1eb2: ldc_w 0.5
      // 1eb5: ldc_w -1.116
      // 1eb8: ldc_w 32.752
      // 1ebb: ldc_w 5.076
      // 1ebe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ec1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ec4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ec7: aastore
      // 1ec8: dup
      // 1ec9: bipush 13
      // 1ecb: new net/minecraft/client/animation/Keyframe
      // 1ece: dup
      // 1ecf: ldc 0.54
      // 1ed1: ldc_w 0.543
      // 1ed4: ldc_w 46.258
      // 1ed7: ldc_w 2.579
      // 1eda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1edd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ee0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ee3: aastore
      // 1ee4: dup
      // 1ee5: bipush 14
      // 1ee7: new net/minecraft/client/animation/Keyframe
      // 1eea: dup
      // 1eeb: ldc 0.58
      // 1eed: ldc_w -4.253
      // 1ef0: ldc_w 50.388
      // 1ef3: ldc_w 7.686
      // 1ef6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ef9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1efc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eff: aastore
      // 1f00: dup
      // 1f01: bipush 15
      // 1f03: new net/minecraft/client/animation/Keyframe
      // 1f06: dup
      // 1f07: ldc 0.62
      // 1f09: ldc_w -8.461
      // 1f0c: ldc_w 53.858
      // 1f0f: ldc_w 12.115
      // 1f12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f1b: aastore
      // 1f1c: dup
      // 1f1d: bipush 16
      // 1f1f: new net/minecraft/client/animation/Keyframe
      // 1f22: dup
      // 1f23: ldc_w 0.67
      // 1f26: ldc_w -11.523
      // 1f29: ldc_w 56.15
      // 1f2c: ldc_w 15.318
      // 1f2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f38: aastore
      // 1f39: dup
      // 1f3a: bipush 17
      // 1f3c: new net/minecraft/client/animation/Keyframe
      // 1f3f: dup
      // 1f40: ldc 0.71
      // 1f42: ldc_w -12.958
      // 1f45: ldc_w 57.202
      // 1f48: ldc_w 16.813
      // 1f4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f54: aastore
      // 1f55: dup
      // 1f56: bipush 18
      // 1f58: new net/minecraft/client/animation/Keyframe
      // 1f5b: dup
      // 1f5c: ldc 0.75
      // 1f5e: ldc_w -13.314
      // 1f61: ldc_w 57.644
      // 1f64: ldc_w 17.176
      // 1f67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f70: aastore
      // 1f71: dup
      // 1f72: bipush 19
      // 1f74: new net/minecraft/client/animation/Keyframe
      // 1f77: dup
      // 1f78: ldc 0.79
      // 1f7a: ldc_w -13.479
      // 1f7d: ldc_w 58.09
      // 1f80: ldc_w 17.334
      // 1f83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f8c: aastore
      // 1f8d: dup
      // 1f8e: bipush 20
      // 1f90: new net/minecraft/client/animation/Keyframe
      // 1f93: dup
      // 1f94: ldc 0.83
      // 1f96: ldc_w -13.521
      // 1f99: ldc_w 58.506
      // 1f9c: ldc_w 17.362
      // 1f9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fa8: aastore
      // 1fa9: dup
      // 1faa: bipush 21
      // 1fac: new net/minecraft/client/animation/Keyframe
      // 1faf: dup
      // 1fb0: ldc 0.92
      // 1fb2: ldc_w -13.519
      // 1fb5: ldc_w 59.115
      // 1fb8: ldc_w 17.335
      // 1fbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fc4: aastore
      // 1fc5: dup
      // 1fc6: bipush 22
      // 1fc8: new net/minecraft/client/animation/Keyframe
      // 1fcb: dup
      // 1fcc: ldc 0.96
      // 1fce: ldc_w -13.54
      // 1fd1: ldc_w 59.25
      // 1fd4: ldc_w 17.352
      // 1fd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fe0: aastore
      // 1fe1: dup
      // 1fe2: bipush 23
      // 1fe4: new net/minecraft/client/animation/Keyframe
      // 1fe7: dup
      // 1fe8: ldc 1.04
      // 1fea: ldc_w -13.482
      // 1fed: ldc_w 59.231
      // 1ff0: ldc_w 17.291
      // 1ff3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ff6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ff9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ffc: aastore
      // 1ffd: dup
      // 1ffe: bipush 24
      // 2000: new net/minecraft/client/animation/Keyframe
      // 2003: dup
      // 2004: ldc 1.21
      // 2006: ldc_w -13.321
      // 2009: ldc_w 58.912
      // 200c: ldc_w 17.131
      // 200f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2012: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2015: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2018: aastore
      // 2019: dup
      // 201a: bipush 25
      // 201c: new net/minecraft/client/animation/Keyframe
      // 201f: dup
      // 2020: ldc 1.38
      // 2022: ldc_w -13.324
      // 2025: ldc_w 58.538
      // 2028: ldc_w 17.15
      // 202b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 202e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2031: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2034: aastore
      // 2035: dup
      // 2036: bipush 26
      // 2038: new net/minecraft/client/animation/Keyframe
      // 203b: dup
      // 203c: ldc 1.46
      // 203e: ldc_w -13.4
      // 2041: ldc_w 58.614
      // 2044: ldc_w 17.228
      // 2047: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 204a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 204d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2050: aastore
      // 2051: dup
      // 2052: bipush 27
      // 2054: new net/minecraft/client/animation/Keyframe
      // 2057: dup
      // 2058: ldc 1.58
      // 205a: ldc_w -13.486
      // 205d: ldc_w 59.04
      // 2060: ldc_w 17.303
      // 2063: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2066: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2069: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 206c: aastore
      // 206d: dup
      // 206e: bipush 28
      // 2070: new net/minecraft/client/animation/Keyframe
      // 2073: dup
      // 2074: ldc 1.83
      // 2076: ldc_w -13.321
      // 2079: ldc_w 58.912
      // 207c: ldc_w 17.131
      // 207f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2082: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2085: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2088: aastore
      // 2089: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 208c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 208f: ldc_w "CTR_upperarm_left.001"
      // 2092: new net/minecraft/client/animation/AnimationChannel
      // 2095: dup
      // 2096: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2099: bipush 19
      // 209b: anewarray 28
      // 209e: dup
      // 209f: bipush 0
      // 20a0: new net/minecraft/client/animation/Keyframe
      // 20a3: dup
      // 20a4: fconst_0
      // 20a5: dconst_1
      // 20a6: dconst_1
      // 20a7: ldc2_w 0.8029999732971191
      // 20aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 20ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20b3: aastore
      // 20b4: dup
      // 20b5: bipush 1
      // 20b6: new net/minecraft/client/animation/Keyframe
      // 20b9: dup
      // 20ba: ldc 0.04
      // 20bc: dconst_1
      // 20bd: dconst_1
      // 20be: ldc2_w 0.8889999985694885
      // 20c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 20c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20ca: aastore
      // 20cb: dup
      // 20cc: bipush 2
      // 20cd: new net/minecraft/client/animation/Keyframe
      // 20d0: dup
      // 20d1: ldc 0.08
      // 20d3: dconst_1
      // 20d4: dconst_1
      // 20d5: ldc2_w 1.0190000534057617
      // 20d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 20db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20e1: aastore
      // 20e2: dup
      // 20e3: bipush 3
      // 20e4: new net/minecraft/client/animation/Keyframe
      // 20e7: dup
      // 20e8: ldc 0.17
      // 20ea: dconst_1
      // 20eb: dconst_1
      // 20ec: ldc2_w 1.4579999446868896
      // 20ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 20f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20f8: aastore
      // 20f9: dup
      // 20fa: bipush 4
      // 20fb: new net/minecraft/client/animation/Keyframe
      // 20fe: dup
      // 20ff: ldc 0.21
      // 2101: dconst_1
      // 2102: dconst_1
      // 2103: ldc2_w 1.7100000381469727
      // 2106: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2109: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 210c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 210f: aastore
      // 2110: dup
      // 2111: bipush 5
      // 2112: new net/minecraft/client/animation/Keyframe
      // 2115: dup
      // 2116: ldc 0.29
      // 2118: dconst_1
      // 2119: dconst_1
      // 211a: ldc2_w 2.1710000038146973
      // 211d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2120: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2123: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2126: aastore
      // 2127: dup
      // 2128: bipush 6
      // 212a: new net/minecraft/client/animation/Keyframe
      // 212d: dup
      // 212e: ldc 0.33
      // 2130: dconst_1
      // 2131: dconst_1
      // 2132: ldc2_w 2.437000036239624
      // 2135: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2138: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 213b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 213e: aastore
      // 213f: dup
      // 2140: bipush 7
      // 2142: new net/minecraft/client/animation/Keyframe
      // 2145: dup
      // 2146: ldc 0.38
      // 2148: dconst_1
      // 2149: dconst_1
      // 214a: ldc2_w 2.687999963760376
      // 214d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2150: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2153: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2156: aastore
      // 2157: dup
      // 2158: bipush 8
      // 215a: new net/minecraft/client/animation/Keyframe
      // 215d: dup
      // 215e: ldc 0.42
      // 2160: dconst_1
      // 2161: dconst_1
      // 2162: ldc2_w 2.8369998931884766
      // 2165: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2168: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 216b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 216e: aastore
      // 216f: dup
      // 2170: bipush 9
      // 2172: new net/minecraft/client/animation/Keyframe
      // 2175: dup
      // 2176: ldc 0.46
      // 2178: dconst_1
      // 2179: dconst_1
      // 217a: ldc2_w 2.869999885559082
      // 217d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2186: aastore
      // 2187: dup
      // 2188: bipush 10
      // 218a: new net/minecraft/client/animation/Keyframe
      // 218d: dup
      // 218e: ldc_w 0.5
      // 2191: dconst_1
      // 2192: dconst_1
      // 2193: ldc2_w 2.859999895095825
      // 2196: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2199: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 219c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 219f: aastore
      // 21a0: dup
      // 21a1: bipush 11
      // 21a3: new net/minecraft/client/animation/Keyframe
      // 21a6: dup
      // 21a7: ldc 0.54
      // 21a9: dconst_1
      // 21aa: dconst_1
      // 21ab: ldc2_w 2.938999891281128
      // 21ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 21b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21b7: aastore
      // 21b8: dup
      // 21b9: bipush 12
      // 21bb: new net/minecraft/client/animation/Keyframe
      // 21be: dup
      // 21bf: ldc 0.58
      // 21c1: dconst_1
      // 21c2: dconst_1
      // 21c3: ldc2_w 2.924999952316284
      // 21c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 21c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21cf: aastore
      // 21d0: dup
      // 21d1: bipush 13
      // 21d3: new net/minecraft/client/animation/Keyframe
      // 21d6: dup
      // 21d7: ldc 0.71
      // 21d9: dconst_1
      // 21da: dconst_1
      // 21db: ldc2_w 2.9149999618530273
      // 21de: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 21e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21e7: aastore
      // 21e8: dup
      // 21e9: bipush 14
      // 21eb: new net/minecraft/client/animation/Keyframe
      // 21ee: dup
      // 21ef: ldc 0.92
      // 21f1: dconst_1
      // 21f2: dconst_1
      // 21f3: ldc2_w 2.9560000896453857
      // 21f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 21f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ff: aastore
      // 2200: dup
      // 2201: bipush 15
      // 2203: new net/minecraft/client/animation/Keyframe
      // 2206: dup
      // 2207: ldc 1.25
      // 2209: dconst_1
      // 220a: dconst_1
      // 220b: ldc2_w 2.9489998817443848
      // 220e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2211: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2214: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2217: aastore
      // 2218: dup
      // 2219: bipush 16
      // 221b: new net/minecraft/client/animation/Keyframe
      // 221e: dup
      // 221f: ldc 1.42
      // 2221: dconst_1
      // 2222: dconst_1
      // 2223: ldc2_w 2.940000057220459
      // 2226: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2229: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 222c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 222f: aastore
      // 2230: dup
      // 2231: bipush 17
      // 2233: new net/minecraft/client/animation/Keyframe
      // 2236: dup
      // 2237: ldc 1.62
      // 2239: dconst_1
      // 223a: dconst_1
      // 223b: ldc2_w 2.9590001106262207
      // 223e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2241: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2244: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2247: aastore
      // 2248: dup
      // 2249: bipush 18
      // 224b: new net/minecraft/client/animation/Keyframe
      // 224e: dup
      // 224f: ldc 1.83
      // 2251: dconst_1
      // 2252: dconst_1
      // 2253: ldc2_w 2.950000047683716
      // 2256: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2259: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 225c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 225f: aastore
      // 2260: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2263: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2266: ldc_w "CTR_shoulder_right"
      // 2269: new net/minecraft/client/animation/AnimationChannel
      // 226c: dup
      // 226d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2270: bipush 38
      // 2272: anewarray 28
      // 2275: dup
      // 2276: bipush 0
      // 2277: new net/minecraft/client/animation/Keyframe
      // 227a: dup
      // 227b: ldc_w 0.53
      // 227e: ldc_w -24.797
      // 2281: ldc_w -30.813
      // 2284: ldc_w 4.414
      // 2287: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 228a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 228d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2290: aastore
      // 2291: dup
      // 2292: bipush 1
      // 2293: new net/minecraft/client/animation/Keyframe
      // 2296: dup
      // 2297: ldc 0.57
      // 2299: ldc_w -25.521
      // 229c: ldc_w -31.361
      // 229f: ldc_w 4.437
      // 22a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ab: aastore
      // 22ac: dup
      // 22ad: bipush 2
      // 22ae: new net/minecraft/client/animation/Keyframe
      // 22b1: dup
      // 22b2: ldc_w 0.61
      // 22b5: ldc_w -27.736
      // 22b8: ldc_w -32.774
      // 22bb: ldc_w 4.598
      // 22be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22c7: aastore
      // 22c8: dup
      // 22c9: bipush 3
      // 22ca: new net/minecraft/client/animation/Keyframe
      // 22cd: dup
      // 22ce: ldc_w 0.65
      // 22d1: ldc_w -31.51
      // 22d4: ldc_w -34.706
      // 22d7: ldc_w 5.033
      // 22da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22e3: aastore
      // 22e4: dup
      // 22e5: bipush 4
      // 22e6: new net/minecraft/client/animation/Keyframe
      // 22e9: dup
      // 22ea: ldc_w 0.7
      // 22ed: ldc_w -36.909
      // 22f0: ldc_w -36.811
      // 22f3: ldc_w 5.881
      // 22f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ff: aastore
      // 2300: dup
      // 2301: bipush 5
      // 2302: new net/minecraft/client/animation/Keyframe
      // 2305: dup
      // 2306: ldc_w 0.74
      // 2309: ldc_w -43.999
      // 230c: ldc_w -38.743
      // 230f: ldc_w 7.279
      // 2312: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2315: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2318: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 231b: aastore
      // 231c: dup
      // 231d: bipush 6
      // 231f: new net/minecraft/client/animation/Keyframe
      // 2322: dup
      // 2323: ldc_w 0.78
      // 2326: ldc_w -52.847
      // 2329: ldc_w -40.156
      // 232c: ldc_w 9.364
      // 232f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2332: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2335: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2338: aastore
      // 2339: dup
      // 233a: bipush 7
      // 233c: new net/minecraft/client/animation/Keyframe
      // 233f: dup
      // 2340: ldc_w 0.82
      // 2343: ldc_w -63.52
      // 2346: ldc_w -40.704
      // 2349: ldc_w 12.275
      // 234c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 234f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2352: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2355: aastore
      // 2356: dup
      // 2357: bipush 8
      // 2359: new net/minecraft/client/animation/Keyframe
      // 235c: dup
      // 235d: ldc_w 0.86
      // 2360: ldc_w -75.963
      // 2363: ldc_w -39.215
      // 2366: ldc_w 19.292
      // 2369: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 236c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 236f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2372: aastore
      // 2373: dup
      // 2374: bipush 9
      // 2376: new net/minecraft/client/animation/Keyframe
      // 2379: dup
      // 237a: ldc_w 0.91
      // 237d: ldc_w -89.637
      // 2380: ldc_w -35.191
      // 2383: ldc_w 32.378
      // 2386: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2389: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 238c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 238f: aastore
      // 2390: dup
      // 2391: bipush 10
      // 2393: new net/minecraft/client/animation/Keyframe
      // 2396: dup
      // 2397: ldc_w 0.95
      // 239a: ldc_w -103.885
      // 239d: ldc_w -29.297
      // 23a0: ldc_w 49.693
      // 23a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ac: aastore
      // 23ad: dup
      // 23ae: bipush 11
      // 23b0: new net/minecraft/client/animation/Keyframe
      // 23b3: dup
      // 23b4: ldc_w 0.99
      // 23b7: ldc_w -118.047
      // 23ba: ldc_w -22.201
      // 23bd: ldc_w 69.394
      // 23c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23c9: aastore
      // 23ca: dup
      // 23cb: bipush 12
      // 23cd: new net/minecraft/client/animation/Keyframe
      // 23d0: dup
      // 23d1: ldc_w 1.03
      // 23d4: ldc_w -131.465
      // 23d7: ldc_w -14.567
      // 23da: ldc_w 89.642
      // 23dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23e6: aastore
      // 23e7: dup
      // 23e8: bipush 13
      // 23ea: new net/minecraft/client/animation/Keyframe
      // 23ed: dup
      // 23ee: ldc_w 1.07
      // 23f1: ldc_w -143.478
      // 23f4: ldc_w -7.062
      // 23f7: ldc_w 108.595
      // 23fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2400: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2403: aastore
      // 2404: dup
      // 2405: bipush 14
      // 2407: new net/minecraft/client/animation/Keyframe
      // 240a: dup
      // 240b: ldc_w 1.11
      // 240e: ldc_w -153.428
      // 2411: ldc_w -0.351
      // 2414: ldc_w 124.412
      // 2417: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 241a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 241d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2420: aastore
      // 2421: dup
      // 2422: bipush 15
      // 2424: new net/minecraft/client/animation/Keyframe
      // 2427: dup
      // 2428: ldc_w 1.15
      // 242b: ldc_w -160.657
      // 242e: ldc_w 4.899
      // 2431: ldc_w 135.252
      // 2434: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2437: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 243a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 243d: aastore
      // 243e: dup
      // 243f: bipush 16
      // 2441: new net/minecraft/client/animation/Keyframe
      // 2444: dup
      // 2445: ldc_w 1.2
      // 2448: ldc_w -164.505
      // 244b: ldc_w 8.024
      // 244e: ldc_w 139.274
      // 2451: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2454: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2457: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 245a: aastore
      // 245b: dup
      // 245c: bipush 17
      // 245e: new net/minecraft/client/animation/Keyframe
      // 2461: dup
      // 2462: ldc_w 1.24
      // 2465: ldc_w -166.137
      // 2468: ldc_w 9.574
      // 246b: ldc_w 139.212
      // 246e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2471: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2474: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2477: aastore
      // 2478: dup
      // 2479: bipush 18
      // 247b: new net/minecraft/client/animation/Keyframe
      // 247e: dup
      // 247f: ldc_w 1.28
      // 2482: ldc_w -167.232
      // 2485: ldc_w 10.613
      // 2488: ldc_w 139.057
      // 248b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 248e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2491: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2494: aastore
      // 2495: dup
      // 2496: bipush 19
      // 2498: new net/minecraft/client/animation/Keyframe
      // 249b: dup
      // 249c: ldc_w 1.32
      // 249f: ldc_w -167.896
      // 24a2: ldc_w 11.243
      // 24a5: ldc_w 138.854
      // 24a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24b1: aastore
      // 24b2: dup
      // 24b3: bipush 20
      // 24b5: new net/minecraft/client/animation/Keyframe
      // 24b8: dup
      // 24b9: ldc_w 1.36
      // 24bc: ldc_w -168.236
      // 24bf: ldc_w 11.567
      // 24c2: ldc_w 138.652
      // 24c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24ce: aastore
      // 24cf: dup
      // 24d0: bipush 21
      // 24d2: new net/minecraft/client/animation/Keyframe
      // 24d5: dup
      // 24d6: ldc_w 1.45
      // 24d9: ldc_w -168.38
      // 24dc: ldc_w 11.703
      // 24df: ldc_w 138.434
      // 24e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24eb: aastore
      // 24ec: dup
      // 24ed: bipush 22
      // 24ef: new net/minecraft/client/animation/Keyframe
      // 24f2: dup
      // 24f3: ldc_w 1.49
      // 24f6: ldc_w -168.165
      // 24f9: ldc_w 11.499
      // 24fc: ldc_w 138.481
      // 24ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2502: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2505: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2508: aastore
      // 2509: dup
      // 250a: bipush 23
      // 250c: new net/minecraft/client/animation/Keyframe
      // 250f: dup
      // 2510: ldc_w 1.53
      // 2513: ldc_w -167.612
      // 2516: ldc_w 10.974
      // 2519: ldc_w 138.601
      // 251c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 251f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2522: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2525: aastore
      // 2526: dup
      // 2527: bipush 24
      // 2529: new net/minecraft/client/animation/Keyframe
      // 252c: dup
      // 252d: ldc_w 1.65
      // 2530: ldc_w -165.273
      // 2533: ldc_w 8.753
      // 2536: ldc_w 139.108
      // 2539: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 253c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 253f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2542: aastore
      // 2543: dup
      // 2544: bipush 25
      // 2546: new net/minecraft/client/animation/Keyframe
      // 2549: dup
      // 254a: ldc_w 1.7
      // 254d: ldc_w -164.719
      // 2550: ldc_w 8.228
      // 2553: ldc_w 139.228
      // 2556: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2559: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 255c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 255f: aastore
      // 2560: dup
      // 2561: bipush 26
      // 2563: new net/minecraft/client/animation/Keyframe
      // 2566: dup
      // 2567: ldc_w 1.74
      // 256a: ldc_w -164.505
      // 256d: ldc_w 8.024
      // 2570: ldc_w 139.274
      // 2573: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2576: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2579: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 257c: aastore
      // 257d: dup
      // 257e: bipush 27
      // 2580: new net/minecraft/client/animation/Keyframe
      // 2583: dup
      // 2584: ldc_w 1.78
      // 2587: ldc_w -164.671
      // 258a: ldc_w 8.182
      // 258d: ldc_w 139.238
      // 2590: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2593: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2596: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2599: aastore
      // 259a: dup
      // 259b: bipush 28
      // 259d: new net/minecraft/client/animation/Keyframe
      // 25a0: dup
      // 25a1: ldc_w 1.82
      // 25a4: ldc_w -165.11
      // 25a7: ldc_w 8.599
      // 25aa: ldc_w 139.143
      // 25ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25b6: aastore
      // 25b7: dup
      // 25b8: bipush 29
      // 25ba: new net/minecraft/client/animation/Keyframe
      // 25bd: dup
      // 25be: ldc_w 1.91
      // 25c1: ldc_w -166.442
      // 25c4: ldc_w 9.863
      // 25c7: ldc_w 138.854
      // 25ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25d3: aastore
      // 25d4: dup
      // 25d5: bipush 30
      // 25d7: new net/minecraft/client/animation/Keyframe
      // 25da: dup
      // 25db: ldc_w 1.99
      // 25de: ldc_w -167.774
      // 25e1: ldc_w 11.128
      // 25e4: ldc_w 138.566
      // 25e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25f0: aastore
      // 25f1: dup
      // 25f2: bipush 31
      // 25f4: new net/minecraft/client/animation/Keyframe
      // 25f7: dup
      // 25f8: ldc_w 2.03
      // 25fb: ldc_w -168.213
      // 25fe: ldc_w 11.545
      // 2601: ldc_w 138.471
      // 2604: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2607: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 260a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 260d: aastore
      // 260e: dup
      // 260f: bipush 32
      // 2611: new net/minecraft/client/animation/Keyframe
      // 2614: dup
      // 2615: ldc_w 2.07
      // 2618: ldc_w -168.38
      // 261b: ldc_w 11.703
      // 261e: ldc_w 138.434
      // 2621: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2624: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2627: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 262a: aastore
      // 262b: dup
      // 262c: bipush 33
      // 262e: new net/minecraft/client/animation/Keyframe
      // 2631: dup
      // 2632: ldc_w 2.11
      // 2635: ldc_w -168.165
      // 2638: ldc_w 11.499
      // 263b: ldc_w 138.481
      // 263e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2641: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2644: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2647: aastore
      // 2648: dup
      // 2649: bipush 34
      // 264b: new net/minecraft/client/animation/Keyframe
      // 264e: dup
      // 264f: ldc_w 2.2
      // 2652: ldc_w -166.855
      // 2655: ldc_w 10.255
      // 2658: ldc_w 138.765
      // 265b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 265e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2661: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2664: aastore
      // 2665: dup
      // 2666: bipush 35
      // 2668: new net/minecraft/client/animation/Keyframe
      // 266b: dup
      // 266c: ldc_w 2.28
      // 266f: ldc_w -165.273
      // 2672: ldc_w 8.753
      // 2675: ldc_w 139.108
      // 2678: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 267b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 267e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2681: aastore
      // 2682: dup
      // 2683: bipush 36
      // 2685: new net/minecraft/client/animation/Keyframe
      // 2688: dup
      // 2689: ldc_w 2.32
      // 268c: ldc_w -164.719
      // 268f: ldc_w 8.228
      // 2692: ldc_w 139.228
      // 2695: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2698: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 269b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 269e: aastore
      // 269f: dup
      // 26a0: bipush 37
      // 26a2: new net/minecraft/client/animation/Keyframe
      // 26a5: dup
      // 26a6: ldc_w 2.36
      // 26a9: ldc_w -164.505
      // 26ac: ldc_w 8.024
      // 26af: ldc_w 139.274
      // 26b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26bb: aastore
      // 26bc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 26bf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 26c2: ldc_w "CTR_shoulder_right"
      // 26c5: new net/minecraft/client/animation/AnimationChannel
      // 26c8: dup
      // 26c9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 26cc: bipush 8
      // 26ce: anewarray 28
      // 26d1: dup
      // 26d2: bipush 0
      // 26d3: new net/minecraft/client/animation/Keyframe
      // 26d6: dup
      // 26d7: ldc_w 0.53
      // 26da: ldc_w 3.322
      // 26dd: ldc_w 7.687
      // 26e0: ldc_w -13.971
      // 26e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26ec: aastore
      // 26ed: dup
      // 26ee: bipush 1
      // 26ef: new net/minecraft/client/animation/Keyframe
      // 26f2: dup
      // 26f3: ldc 0.57
      // 26f5: ldc_w 3.009
      // 26f8: ldc_w 7.046
      // 26fb: ldc_w -13.545
      // 26fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2701: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2704: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2707: aastore
      // 2708: dup
      // 2709: bipush 2
      // 270a: new net/minecraft/client/animation/Keyframe
      // 270d: dup
      // 270e: ldc_w 0.61
      // 2711: ldc_w 2.202
      // 2714: ldc_w 5.393
      // 2717: ldc_w -12.448
      // 271a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 271d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2720: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2723: aastore
      // 2724: dup
      // 2725: bipush 3
      // 2726: new net/minecraft/client/animation/Keyframe
      // 2729: dup
      // 272a: ldc_w 0.65
      // 272d: ldc_w 1.098
      // 2730: ldc_w 3.133
      // 2733: ldc_w -10.949
      // 2736: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2739: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 273c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 273f: aastore
      // 2740: dup
      // 2741: bipush 4
      // 2742: new net/minecraft/client/animation/Keyframe
      // 2745: dup
      // 2746: ldc_w 0.7
      // 2749: ldc_w -0.105
      // 274c: ldc_w 0.67
      // 274f: ldc_w -9.315
      // 2752: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2755: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2758: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 275b: aastore
      // 275c: dup
      // 275d: bipush 5
      // 275e: new net/minecraft/client/animation/Keyframe
      // 2761: dup
      // 2762: ldc_w 0.74
      // 2765: ldc_w -1.209
      // 2768: ldc_w -1.59
      // 276b: ldc_w -7.815
      // 276e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2771: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2774: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2777: aastore
      // 2778: dup
      // 2779: bipush 6
      // 277b: new net/minecraft/client/animation/Keyframe
      // 277e: dup
      // 277f: ldc_w 0.78
      // 2782: ldc_w -2.016
      // 2785: ldc_w -3.243
      // 2788: ldc_w -6.718
      // 278b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 278e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2791: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2794: aastore
      // 2795: dup
      // 2796: bipush 7
      // 2798: new net/minecraft/client/animation/Keyframe
      // 279b: dup
      // 279c: ldc_w 0.82
      // 279f: ldc_w -2.329
      // 27a2: ldc_w -3.884
      // 27a5: ldc_w -6.293
      // 27a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27b1: aastore
      // 27b2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 27b5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 27b8: ldc_w "CTR_upperarm_right"
      // 27bb: new net/minecraft/client/animation/AnimationChannel
      // 27be: dup
      // 27bf: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 27c2: bipush 14
      // 27c4: anewarray 28
      // 27c7: dup
      // 27c8: bipush 0
      // 27c9: new net/minecraft/client/animation/Keyframe
      // 27cc: dup
      // 27cd: ldc 0.54
      // 27cf: ldc_w 29.625
      // 27d2: ldc_w 1.921
      // 27d5: ldc_w -23.104
      // 27d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27e1: aastore
      // 27e2: dup
      // 27e3: bipush 1
      // 27e4: new net/minecraft/client/animation/Keyframe
      // 27e7: dup
      // 27e8: ldc 0.58
      // 27ea: ldc_w 28.598
      // 27ed: ldc_w 1.832
      // 27f0: ldc_w -22.441
      // 27f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27fc: aastore
      // 27fd: dup
      // 27fe: bipush 2
      // 27ff: new net/minecraft/client/animation/Keyframe
      // 2802: dup
      // 2803: ldc 0.62
      // 2805: ldc_w 25.817
      // 2808: ldc_w 1.495
      // 280b: ldc_w -20.784
      // 280e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2811: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2814: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2817: aastore
      // 2818: dup
      // 2819: bipush 3
      // 281a: new net/minecraft/client/animation/Keyframe
      // 281d: dup
      // 281e: ldc 0.66
      // 2820: ldc_w 21.733
      // 2823: ldc_w 0.806
      // 2826: ldc_w -18.631
      // 2829: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 282c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 282f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2832: aastore
      // 2833: dup
      // 2834: bipush 4
      // 2835: new net/minecraft/client/animation/Keyframe
      // 2838: dup
      // 2839: ldc 0.71
      // 283b: ldc_w 16.794
      // 283e: ldc_w -0.339
      // 2841: ldc_w -16.477
      // 2844: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2847: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 284a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 284d: aastore
      // 284e: dup
      // 284f: bipush 5
      // 2850: new net/minecraft/client/animation/Keyframe
      // 2853: dup
      // 2854: ldc 0.75
      // 2856: ldc_w 11.45
      // 2859: ldc_w -2.045
      // 285c: ldc_w -14.821
      // 285f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2862: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2865: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2868: aastore
      // 2869: dup
      // 286a: bipush 6
      // 286c: new net/minecraft/client/animation/Keyframe
      // 286f: dup
      // 2870: ldc 0.79
      // 2872: ldc_w 6.152
      // 2875: ldc_w -4.417
      // 2878: ldc_w -14.158
      // 287b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 287e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2881: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2884: aastore
      // 2885: dup
      // 2886: bipush 7
      // 2888: new net/minecraft/client/animation/Keyframe
      // 288b: dup
      // 288c: ldc 0.83
      // 288e: ldc_w 1.285
      // 2891: ldc_w -7.486
      // 2894: ldc_w -15.459
      // 2897: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 289a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 289d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28a0: aastore
      // 28a1: dup
      // 28a2: bipush 8
      // 28a4: new net/minecraft/client/animation/Keyframe
      // 28a7: dup
      // 28a8: ldc 0.87
      // 28aa: ldc_w -3.02
      // 28ad: ldc_w -10.992
      // 28b0: ldc_w -18.815
      // 28b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28bc: aastore
      // 28bd: dup
      // 28be: bipush 9
      // 28c0: new net/minecraft/client/animation/Keyframe
      // 28c3: dup
      // 28c4: ldc 0.92
      // 28c6: ldc_w -6.695
      // 28c9: ldc_w -14.605
      // 28cc: ldc_w -23.403
      // 28cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28d8: aastore
      // 28d9: dup
      // 28da: bipush 10
      // 28dc: new net/minecraft/client/animation/Keyframe
      // 28df: dup
      // 28e0: ldc 0.96
      // 28e2: ldc_w -9.671
      // 28e5: ldc_w -17.992
      // 28e8: ldc_w -28.402
      // 28eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28f4: aastore
      // 28f5: dup
      // 28f6: bipush 11
      // 28f8: new net/minecraft/client/animation/Keyframe
      // 28fb: dup
      // 28fc: fconst_1
      // 28fd: ldc_w -11.88
      // 2900: ldc_w -20.823
      // 2903: ldc_w -32.991
      // 2906: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2909: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 290c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 290f: aastore
      // 2910: dup
      // 2911: bipush 12
      // 2913: new net/minecraft/client/animation/Keyframe
      // 2916: dup
      // 2917: ldc 1.04
      // 2919: ldc_w -13.256
      // 291c: ldc_w -22.764
      // 291f: ldc_w -36.346
      // 2922: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2925: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2928: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 292b: aastore
      // 292c: dup
      // 292d: bipush 13
      // 292f: new net/minecraft/client/animation/Keyframe
      // 2932: dup
      // 2933: ldc 1.08
      // 2935: ldc_w -13.73
      // 2938: ldc_w -23.485
      // 293b: ldc_w -37.647
      // 293e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2941: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2944: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2947: aastore
      // 2948: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 294b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 294e: ldc_w "CTR_lowerarm_right"
      // 2951: new net/minecraft/client/animation/AnimationChannel
      // 2954: dup
      // 2955: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2958: bipush 16
      // 295a: anewarray 28
      // 295d: dup
      // 295e: bipush 0
      // 295f: new net/minecraft/client/animation/Keyframe
      // 2962: dup
      // 2963: ldc 0.54
      // 2965: ldc_w -2.775
      // 2968: ldc_w 10.903
      // 296b: ldc_w 5.925
      // 296e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2971: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2974: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2977: aastore
      // 2978: dup
      // 2979: bipush 1
      // 297a: new net/minecraft/client/animation/Keyframe
      // 297d: dup
      // 297e: ldc 0.58
      // 2980: ldc_w -1.194
      // 2983: ldc_w 10.267
      // 2986: ldc_w 6.369
      // 2989: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 298c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 298f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2992: aastore
      // 2993: dup
      // 2994: bipush 2
      // 2995: new net/minecraft/client/animation/Keyframe
      // 2998: dup
      // 2999: ldc 0.62
      // 299b: ldc_w 2.696
      // 299e: ldc_w 8.667
      // 29a1: ldc_w 7.432
      // 29a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29ad: aastore
      // 29ae: dup
      // 29af: bipush 3
      // 29b0: new net/minecraft/client/animation/Keyframe
      // 29b3: dup
      // 29b4: ldc 0.66
      // 29b6: ldc_w 7.625
      // 29b9: ldc_w 6.572
      // 29bc: ldc_w 8.717
      // 29bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29c8: aastore
      // 29c9: dup
      // 29ca: bipush 4
      // 29cb: new net/minecraft/client/animation/Keyframe
      // 29ce: dup
      // 29cf: ldc 0.71
      // 29d1: ldc_w 12.424
      // 29d4: ldc_w 4.464
      // 29d7: ldc_w 9.909
      // 29da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29e3: aastore
      // 29e4: dup
      // 29e5: bipush 5
      // 29e6: new net/minecraft/client/animation/Keyframe
      // 29e9: dup
      // 29ea: ldc 0.75
      // 29ec: ldc_w 16.036
      // 29ef: ldc_w 2.834
      // 29f2: ldc_w 10.771
      // 29f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29fe: aastore
      // 29ff: dup
      // 2a00: bipush 6
      // 2a02: new net/minecraft/client/animation/Keyframe
      // 2a05: dup
      // 2a06: ldc 0.79
      // 2a08: ldc_w 17.463
      // 2a0b: ldc_w 2.18
      // 2a0e: ldc_w 11.104
      // 2a11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a1a: aastore
      // 2a1b: dup
      // 2a1c: bipush 7
      // 2a1e: new net/minecraft/client/animation/Keyframe
      // 2a21: dup
      // 2a22: ldc 0.83
      // 2a24: ldc_w 16.881
      // 2a27: ldc_w 2.949
      // 2a2a: ldc_w 11.065
      // 2a2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a36: aastore
      // 2a37: dup
      // 2a38: bipush 8
      // 2a3a: new net/minecraft/client/animation/Keyframe
      // 2a3d: dup
      // 2a3e: ldc 0.87
      // 2a40: ldc_w 15.316
      // 2a43: ldc_w 5.007
      // 2a46: ldc_w 10.957
      // 2a49: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a52: aastore
      // 2a53: dup
      // 2a54: bipush 9
      // 2a56: new net/minecraft/client/animation/Keyframe
      // 2a59: dup
      // 2a5a: ldc 0.92
      // 2a5c: ldc_w 13.029
      // 2a5f: ldc_w 7.988
      // 2a62: ldc_w 10.79
      // 2a65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a6e: aastore
      // 2a6f: dup
      // 2a70: bipush 10
      // 2a72: new net/minecraft/client/animation/Keyframe
      // 2a75: dup
      // 2a76: ldc 0.96
      // 2a78: ldc_w 10.284
      // 2a7b: ldc_w 11.522
      // 2a7e: ldc_w 10.572
      // 2a81: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a84: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a87: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a8a: aastore
      // 2a8b: dup
      // 2a8c: bipush 11
      // 2a8e: new net/minecraft/client/animation/Keyframe
      // 2a91: dup
      // 2a92: fconst_1
      // 2a93: ldc_w 7.346
      // 2a96: ldc_w 15.24
      // 2a99: ldc_w 10.31
      // 2a9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aa2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aa5: aastore
      // 2aa6: dup
      // 2aa7: bipush 12
      // 2aa9: new net/minecraft/client/animation/Keyframe
      // 2aac: dup
      // 2aad: ldc 1.04
      // 2aaf: ldc_w 4.496
      // 2ab2: ldc_w 18.774
      // 2ab5: ldc_w 10.017
      // 2ab8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2abb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2abe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ac1: aastore
      // 2ac2: dup
      // 2ac3: bipush 13
      // 2ac5: new net/minecraft/client/animation/Keyframe
      // 2ac8: dup
      // 2ac9: ldc 1.08
      // 2acb: ldc_w 2.034
      // 2ace: ldc_w 21.753
      // 2ad1: ldc_w 9.723
      // 2ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2add: aastore
      // 2ade: dup
      // 2adf: bipush 14
      // 2ae1: new net/minecraft/client/animation/Keyframe
      // 2ae4: dup
      // 2ae5: ldc 1.12
      // 2ae7: ldc_w 0.296
      // 2aea: ldc_w 23.811
      // 2aed: ldc_w 9.488
      // 2af0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2af3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2af6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2af9: aastore
      // 2afa: dup
      // 2afb: bipush 15
      // 2afd: new net/minecraft/client/animation/Keyframe
      // 2b00: dup
      // 2b01: ldc 1.16
      // 2b03: ldc_w -0.363
      // 2b06: ldc_w 24.579
      // 2b09: ldc_w 9.391
      // 2b0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b15: aastore
      // 2b16: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2b19: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b1c: ldc_w "CTR_upperarm_right.001"
      // 2b1f: new net/minecraft/client/animation/AnimationChannel
      // 2b22: dup
      // 2b23: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2b26: bipush 36
      // 2b28: anewarray 28
      // 2b2b: dup
      // 2b2c: bipush 0
      // 2b2d: new net/minecraft/client/animation/Keyframe
      // 2b30: dup
      // 2b31: fconst_0
      // 2b32: ldc_w -18.7
      // 2b35: ldc_w 36.754
      // 2b38: ldc_w -15.027
      // 2b3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b44: aastore
      // 2b45: dup
      // 2b46: bipush 1
      // 2b47: new net/minecraft/client/animation/Keyframe
      // 2b4a: dup
      // 2b4b: ldc 0.04
      // 2b4d: ldc_w -16.333
      // 2b50: ldc_w 34.163
      // 2b53: ldc_w -12.768
      // 2b56: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b5f: aastore
      // 2b60: dup
      // 2b61: bipush 2
      // 2b62: new net/minecraft/client/animation/Keyframe
      // 2b65: dup
      // 2b66: ldc 0.08
      // 2b68: ldc_w -12.674
      // 2b6b: ldc_w 32.575
      // 2b6e: ldc_w -9.394
      // 2b71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b7a: aastore
      // 2b7b: dup
      // 2b7c: bipush 3
      // 2b7d: new net/minecraft/client/animation/Keyframe
      // 2b80: dup
      // 2b81: ldc 0.12
      // 2b83: ldc_w -8.239
      // 2b86: ldc_w 31.986
      // 2b89: ldc_w -5.372
      // 2b8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b95: aastore
      // 2b96: dup
      // 2b97: bipush 4
      // 2b98: new net/minecraft/client/animation/Keyframe
      // 2b9b: dup
      // 2b9c: ldc 0.17
      // 2b9e: ldc_w -3.224
      // 2ba1: ldc_w 32.308
      // 2ba4: ldc_w -0.847
      // 2ba7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2baa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bb0: aastore
      // 2bb1: dup
      // 2bb2: bipush 5
      // 2bb3: new net/minecraft/client/animation/Keyframe
      // 2bb6: dup
      // 2bb7: ldc 0.21
      // 2bb9: ldc_w 2.493
      // 2bbc: ldc_w 33.226
      // 2bbf: ldc_w 4.328
      // 2bc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bcb: aastore
      // 2bcc: dup
      // 2bcd: bipush 6
      // 2bcf: new net/minecraft/client/animation/Keyframe
      // 2bd2: dup
      // 2bd3: ldc 0.25
      // 2bd5: ldc_w 8.887
      // 2bd8: ldc_w 33.453
      // 2bdb: ldc_w 10.131
      // 2bde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2be1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2be4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2be7: aastore
      // 2be8: dup
      // 2be9: bipush 7
      // 2beb: new net/minecraft/client/animation/Keyframe
      // 2bee: dup
      // 2bef: ldc 0.29
      // 2bf1: ldc_w 15.924
      // 2bf4: ldc_w 32.228
      // 2bf7: ldc_w 16.466
      // 2bfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c03: aastore
      // 2c04: dup
      // 2c05: bipush 8
      // 2c07: new net/minecraft/client/animation/Keyframe
      // 2c0a: dup
      // 2c0b: ldc 0.33
      // 2c0d: ldc_w 22.917
      // 2c10: ldc_w 26.9
      // 2c13: ldc_w 22.378
      // 2c16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c1f: aastore
      // 2c20: dup
      // 2c21: bipush 9
      // 2c23: new net/minecraft/client/animation/Keyframe
      // 2c26: dup
      // 2c27: ldc 0.38
      // 2c29: ldc_w 27.95
      // 2c2c: ldc_w 17.122
      // 2c2f: ldc_w 25.232
      // 2c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c3b: aastore
      // 2c3c: dup
      // 2c3d: bipush 10
      // 2c3f: new net/minecraft/client/animation/Keyframe
      // 2c42: dup
      // 2c43: ldc 0.42
      // 2c45: ldc_w 32.477
      // 2c48: ldc_w 5.622
      // 2c4b: ldc_w 21.666
      // 2c4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c57: aastore
      // 2c58: dup
      // 2c59: bipush 11
      // 2c5b: new net/minecraft/client/animation/Keyframe
      // 2c5e: dup
      // 2c5f: ldc 0.46
      // 2c61: ldc_w 10.365
      // 2c64: ldc_w -5.278
      // 2c67: ldc_w 38.026
      // 2c6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c73: aastore
      // 2c74: dup
      // 2c75: bipush 12
      // 2c77: new net/minecraft/client/animation/Keyframe
      // 2c7a: dup
      // 2c7b: ldc_w 0.5
      // 2c7e: ldc_w 14.695
      // 2c81: ldc_w -14.033
      // 2c84: ldc_w 22.642
      // 2c87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c90: aastore
      // 2c91: dup
      // 2c92: bipush 13
      // 2c94: new net/minecraft/client/animation/Keyframe
      // 2c97: dup
      // 2c98: ldc 0.54
      // 2c9a: ldc_w 9.243
      // 2c9d: ldc_w -21.328
      // 2ca0: ldc_w 13.81
      // 2ca3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ca6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ca9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cac: aastore
      // 2cad: dup
      // 2cae: bipush 14
      // 2cb0: new net/minecraft/client/animation/Keyframe
      // 2cb3: dup
      // 2cb4: ldc 0.58
      // 2cb6: ldc_w 2.539
      // 2cb9: ldc_w -28.226
      // 2cbc: ldc_w 5.512
      // 2cbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cc8: aastore
      // 2cc9: dup
      // 2cca: bipush 15
      // 2ccc: new net/minecraft/client/animation/Keyframe
      // 2ccf: dup
      // 2cd0: ldc 0.62
      // 2cd2: ldc_w -2.946
      // 2cd5: ldc_w -33.064
      // 2cd8: ldc_w -0.738
      // 2cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ce4: aastore
      // 2ce5: dup
      // 2ce6: bipush 16
      // 2ce8: new net/minecraft/client/animation/Keyframe
      // 2ceb: dup
      // 2cec: ldc_w 0.67
      // 2cef: ldc_w -5.512
      // 2cf2: ldc_w -35.581
      // 2cf5: ldc_w -3.576
      // 2cf8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cfb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cfe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d01: aastore
      // 2d02: dup
      // 2d03: bipush 17
      // 2d05: new net/minecraft/client/animation/Keyframe
      // 2d08: dup
      // 2d09: ldc 0.71
      // 2d0b: ldc_w -6.036
      // 2d0e: ldc_w -36.796
      // 2d11: ldc_w -4.14
      // 2d14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d1d: aastore
      // 2d1e: dup
      // 2d1f: bipush 18
      // 2d21: new net/minecraft/client/animation/Keyframe
      // 2d24: dup
      // 2d25: ldc 0.75
      // 2d27: ldc_w -6.366
      // 2d2a: ldc_w -37.638
      // 2d2d: ldc_w -4.494
      // 2d30: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d39: aastore
      // 2d3a: dup
      // 2d3b: bipush 19
      // 2d3d: new net/minecraft/client/animation/Keyframe
      // 2d40: dup
      // 2d41: ldc 0.79
      // 2d43: ldc_w -6.543
      // 2d46: ldc_w -38.188
      // 2d49: ldc_w -4.682
      // 2d4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d55: aastore
      // 2d56: dup
      // 2d57: bipush 20
      // 2d59: new net/minecraft/client/animation/Keyframe
      // 2d5c: dup
      // 2d5d: ldc 0.83
      // 2d5f: ldc_w -6.614
      // 2d62: ldc_w -38.521
      // 2d65: ldc_w -4.755
      // 2d68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d71: aastore
      // 2d72: dup
      // 2d73: bipush 21
      // 2d75: new net/minecraft/client/animation/Keyframe
      // 2d78: dup
      // 2d79: ldc 0.92
      // 2d7b: ldc_w -6.664
      // 2d7e: ldc_w -38.838
      // 2d81: ldc_w -4.806
      // 2d84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d8d: aastore
      // 2d8e: dup
      // 2d8f: bipush 22
      // 2d91: new net/minecraft/client/animation/Keyframe
      // 2d94: dup
      // 2d95: ldc 0.96
      // 2d97: ldc_w -6.668
      // 2d9a: ldc_w -38.806
      // 2d9d: ldc_w -4.811
      // 2da0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2da3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2da6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2da9: aastore
      // 2daa: dup
      // 2dab: bipush 23
      // 2dad: new net/minecraft/client/animation/Keyframe
      // 2db0: dup
      // 2db1: fconst_1
      // 2db2: ldc_w -6.581
      // 2db5: ldc_w -38.528
      // 2db8: ldc_w -4.718
      // 2dbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dc4: aastore
      // 2dc5: dup
      // 2dc6: bipush 24
      // 2dc8: new net/minecraft/client/animation/Keyframe
      // 2dcb: dup
      // 2dcc: ldc 1.12
      // 2dce: ldc_w -6.117
      // 2dd1: ldc_w -37.125
      // 2dd4: ldc_w -4.226
      // 2dd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ddd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2de0: aastore
      // 2de1: dup
      // 2de2: bipush 25
      // 2de4: new net/minecraft/client/animation/Keyframe
      // 2de7: dup
      // 2de8: ldc 1.17
      // 2dea: ldc_w -6.004
      // 2ded: ldc_w -36.773
      // 2df0: ldc_w -4.105
      // 2df3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2df6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2df9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dfc: aastore
      // 2dfd: dup
      // 2dfe: bipush 26
      // 2e00: new net/minecraft/client/animation/Keyframe
      // 2e03: dup
      // 2e04: ldc 1.21
      // 2e06: ldc_w -5.959
      // 2e09: ldc_w -36.635
      // 2e0c: ldc_w -4.058
      // 2e0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e18: aastore
      // 2e19: dup
      // 2e1a: bipush 27
      // 2e1c: new net/minecraft/client/animation/Keyframe
      // 2e1f: dup
      // 2e20: ldc 1.25
      // 2e22: ldc_w -5.98
      // 2e25: ldc_w -36.705
      // 2e28: ldc_w -4.08
      // 2e2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e34: aastore
      // 2e35: dup
      // 2e36: bipush 28
      // 2e38: new net/minecraft/client/animation/Keyframe
      // 2e3b: dup
      // 2e3c: ldc 1.38
      // 2e3e: ldc_w -6.2
      // 2e41: ldc_w -37.464
      // 2e44: ldc_w -4.314
      // 2e47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e50: aastore
      // 2e51: dup
      // 2e52: bipush 29
      // 2e54: new net/minecraft/client/animation/Keyframe
      // 2e57: dup
      // 2e58: ldc_w 1.5
      // 2e5b: ldc_w -6.555
      // 2e5e: ldc_w -38.539
      // 2e61: ldc_w -4.69
      // 2e64: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e6d: aastore
      // 2e6e: dup
      // 2e6f: bipush 30
      // 2e71: new net/minecraft/client/animation/Keyframe
      // 2e74: dup
      // 2e75: ldc 1.54
      // 2e77: ldc_w -6.618
      // 2e7a: ldc_w -38.727
      // 2e7d: ldc_w -4.757
      // 2e80: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e89: aastore
      // 2e8a: dup
      // 2e8b: bipush 31
      // 2e8d: new net/minecraft/client/animation/Keyframe
      // 2e90: dup
      // 2e91: ldc 1.58
      // 2e93: ldc_w -6.598
      // 2e96: ldc_w -38.68
      // 2e99: ldc_w -4.735
      // 2e9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ea2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ea5: aastore
      // 2ea6: dup
      // 2ea7: bipush 32
      // 2ea9: new net/minecraft/client/animation/Keyframe
      // 2eac: dup
      // 2ead: ldc 1.67
      // 2eaf: ldc_w -6.369
      // 2eb2: ldc_w -37.994
      // 2eb5: ldc_w -4.493
      // 2eb8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ebb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ebe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ec1: aastore
      // 2ec2: dup
      // 2ec3: bipush 33
      // 2ec5: new net/minecraft/client/animation/Keyframe
      // 2ec8: dup
      // 2ec9: ldc 1.75
      // 2ecb: ldc_w -6.094
      // 2ece: ldc_w -37.092
      // 2ed1: ldc_w -4.201
      // 2ed4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ed7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2edd: aastore
      // 2ede: dup
      // 2edf: bipush 34
      // 2ee1: new net/minecraft/client/animation/Keyframe
      // 2ee4: dup
      // 2ee5: ldc 1.79
      // 2ee7: ldc_w -5.997
      // 2eea: ldc_w -36.765
      // 2eed: ldc_w -4.099
      // 2ef0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ef3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ef6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ef9: aastore
      // 2efa: dup
      // 2efb: bipush 35
      // 2efd: new net/minecraft/client/animation/Keyframe
      // 2f00: dup
      // 2f01: ldc 1.83
      // 2f03: ldc_w -5.959
      // 2f06: ldc_w -36.635
      // 2f09: ldc_w -4.058
      // 2f0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f15: aastore
      // 2f16: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2f19: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2f1c: ldc_w "CTR_upperarm_right.001"
      // 2f1f: new net/minecraft/client/animation/AnimationChannel
      // 2f22: dup
      // 2f23: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2f26: bipush 20
      // 2f28: anewarray 28
      // 2f2b: dup
      // 2f2c: bipush 0
      // 2f2d: new net/minecraft/client/animation/Keyframe
      // 2f30: dup
      // 2f31: fconst_0
      // 2f32: dconst_1
      // 2f33: dconst_1
      // 2f34: ldc2_w 1.1030000448226929
      // 2f37: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f40: aastore
      // 2f41: dup
      // 2f42: bipush 1
      // 2f43: new net/minecraft/client/animation/Keyframe
      // 2f46: dup
      // 2f47: ldc 0.17
      // 2f49: dconst_1
      // 2f4a: dconst_1
      // 2f4b: ldc2_w 1.4290000200271606
      // 2f4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f57: aastore
      // 2f58: dup
      // 2f59: bipush 2
      // 2f5a: new net/minecraft/client/animation/Keyframe
      // 2f5d: dup
      // 2f5e: ldc 0.21
      // 2f60: dconst_1
      // 2f61: dconst_1
      // 2f62: ldc2_w 1.5490000247955322
      // 2f65: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f6e: aastore
      // 2f6f: dup
      // 2f70: bipush 3
      // 2f71: new net/minecraft/client/animation/Keyframe
      // 2f74: dup
      // 2f75: ldc 0.25
      // 2f77: dconst_1
      // 2f78: dconst_1
      // 2f79: ldc2_w 1.7079999446868896
      // 2f7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f85: aastore
      // 2f86: dup
      // 2f87: bipush 4
      // 2f88: new net/minecraft/client/animation/Keyframe
      // 2f8b: dup
      // 2f8c: ldc 0.29
      // 2f8e: dconst_1
      // 2f8f: dconst_1
      // 2f90: ldc2_w 1.9019999504089355
      // 2f93: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f9c: aastore
      // 2f9d: dup
      // 2f9e: bipush 5
      // 2f9f: new net/minecraft/client/animation/Keyframe
      // 2fa2: dup
      // 2fa3: ldc 0.33
      // 2fa5: dconst_1
      // 2fa6: dconst_1
      // 2fa7: ldc2_w 2.1449999809265137
      // 2faa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fb3: aastore
      // 2fb4: dup
      // 2fb5: bipush 6
      // 2fb7: new net/minecraft/client/animation/Keyframe
      // 2fba: dup
      // 2fbb: ldc 0.38
      // 2fbd: dconst_1
      // 2fbe: dconst_1
      // 2fbf: ldc2_w 2.427000045776367
      // 2fc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fcb: aastore
      // 2fcc: dup
      // 2fcd: bipush 7
      // 2fcf: new net/minecraft/client/animation/Keyframe
      // 2fd2: dup
      // 2fd3: ldc 0.42
      // 2fd5: dconst_1
      // 2fd6: dconst_1
      // 2fd7: ldc2_w 2.7079999446868896
      // 2fda: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fe0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fe3: aastore
      // 2fe4: dup
      // 2fe5: bipush 8
      // 2fe7: new net/minecraft/client/animation/Keyframe
      // 2fea: dup
      // 2feb: ldc 0.46
      // 2fed: dconst_1
      // 2fee: dconst_1
      // 2fef: ldc2_w 2.946000099182129
      // 2ff2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2ff5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ff8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ffb: aastore
      // 2ffc: dup
      // 2ffd: bipush 9
      // 2fff: new net/minecraft/client/animation/Keyframe
      // 3002: dup
      // 3003: ldc_w 0.5
      // 3006: dconst_1
      // 3007: dconst_1
      // 3008: ldc2_w 3.1040000915527344
      // 300b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 300e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3011: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3014: aastore
      // 3015: dup
      // 3016: bipush 10
      // 3018: new net/minecraft/client/animation/Keyframe
      // 301b: dup
      // 301c: ldc 0.54
      // 301e: dconst_1
      // 301f: dconst_1
      // 3020: ldc2_w 3.1619999408721924
      // 3023: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 302c: aastore
      // 302d: dup
      // 302e: bipush 11
      // 3030: new net/minecraft/client/animation/Keyframe
      // 3033: dup
      // 3034: ldc 0.58
      // 3036: dconst_1
      // 3037: dconst_1
      // 3038: ldc2_w 3.200000047683716
      // 303b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 303e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3041: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3044: aastore
      // 3045: dup
      // 3046: bipush 12
      // 3048: new net/minecraft/client/animation/Keyframe
      // 304b: dup
      // 304c: ldc 0.62
      // 304e: dconst_1
      // 304f: dconst_1
      // 3050: ldc2_w 3.2239999771118164
      // 3053: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3056: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3059: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 305c: aastore
      // 305d: dup
      // 305e: bipush 13
      // 3060: new net/minecraft/client/animation/Keyframe
      // 3063: dup
      // 3064: ldc 0.79
      // 3066: dconst_1
      // 3067: dconst_1
      // 3068: ldc2_w 3.2850000858306885
      // 306b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 306e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3071: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3074: aastore
      // 3075: dup
      // 3076: bipush 14
      // 3078: new net/minecraft/client/animation/Keyframe
      // 307b: dup
      // 307c: ldc 0.92
      // 307e: dconst_1
      // 307f: dconst_1
      // 3080: ldc2_w 3.305999994277954
      // 3083: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3086: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3089: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 308c: aastore
      // 308d: dup
      // 308e: bipush 15
      // 3090: new net/minecraft/client/animation/Keyframe
      // 3093: dup
      // 3094: ldc 1.17
      // 3096: dconst_1
      // 3097: dconst_1
      // 3098: ldc2_w 3.2679998874664307
      // 309b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 309e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30a4: aastore
      // 30a5: dup
      // 30a6: bipush 16
      // 30a8: new net/minecraft/client/animation/Keyframe
      // 30ab: dup
      // 30ac: ldc 1.38
      // 30ae: dconst_1
      // 30af: dconst_1
      // 30b0: ldc2_w 3.2769999504089355
      // 30b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 30b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30bc: aastore
      // 30bd: dup
      // 30be: bipush 17
      // 30c0: new net/minecraft/client/animation/Keyframe
      // 30c3: dup
      // 30c4: ldc 1.54
      // 30c6: dconst_1
      // 30c7: dconst_1
      // 30c8: ldc2_w 3.302000045776367
      // 30cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 30ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30d4: aastore
      // 30d5: dup
      // 30d6: bipush 18
      // 30d8: new net/minecraft/client/animation/Keyframe
      // 30db: dup
      // 30dc: ldc 1.71
      // 30de: dconst_1
      // 30df: dconst_1
      // 30e0: ldc2_w 3.2850000858306885
      // 30e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 30e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30ec: aastore
      // 30ed: dup
      // 30ee: bipush 19
      // 30f0: new net/minecraft/client/animation/Keyframe
      // 30f3: dup
      // 30f4: ldc 1.83
      // 30f6: dconst_1
      // 30f7: dconst_1
      // 30f8: ldc2_w 3.2660000324249268
      // 30fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 30fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3101: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3104: aastore
      // 3105: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3108: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 310b: ldc_w "CTR_leg_left"
      // 310e: new net/minecraft/client/animation/AnimationChannel
      // 3111: dup
      // 3112: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3115: bipush 36
      // 3117: anewarray 28
      // 311a: dup
      // 311b: bipush 0
      // 311c: new net/minecraft/client/animation/Keyframe
      // 311f: dup
      // 3120: ldc 0.54
      // 3122: ldc_w -177.147
      // 3125: ldc_w 62.258
      // 3128: ldc_w 182.853
      // 312b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 312e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3131: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3134: aastore
      // 3135: dup
      // 3136: bipush 1
      // 3137: new net/minecraft/client/animation/Keyframe
      // 313a: dup
      // 313b: ldc 0.58
      // 313d: ldc_w -174.977
      // 3140: ldc_w 53.025
      // 3143: ldc_w 185.023
      // 3146: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3149: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 314c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 314f: aastore
      // 3150: dup
      // 3151: bipush 2
      // 3152: new net/minecraft/client/animation/Keyframe
      // 3155: dup
      // 3156: ldc 0.62
      // 3158: ldc_w -173.456
      // 315b: ldc_w 43.693
      // 315e: ldc_w 186.544
      // 3161: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3164: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3167: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 316a: aastore
      // 316b: dup
      // 316c: bipush 3
      // 316d: new net/minecraft/client/animation/Keyframe
      // 3170: dup
      // 3171: ldc 0.66
      // 3173: ldc_w -172.518
      // 3176: ldc_w 34.899
      // 3179: ldc_w 187.482
      // 317c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 317f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3182: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3185: aastore
      // 3186: dup
      // 3187: bipush 4
      // 3188: new net/minecraft/client/animation/Keyframe
      // 318b: dup
      // 318c: ldc 0.71
      // 318e: ldc_w -172.109
      // 3191: ldc_w 26.866
      // 3194: ldc_w 187.891
      // 3197: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 319a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 319d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31a0: aastore
      // 31a1: dup
      // 31a2: bipush 5
      // 31a3: new net/minecraft/client/animation/Keyframe
      // 31a6: dup
      // 31a7: ldc 0.75
      // 31a9: ldc_w -172.132
      // 31ac: ldc_w 19.569
      // 31af: ldc_w 187.868
      // 31b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31bb: aastore
      // 31bc: dup
      // 31bd: bipush 6
      // 31bf: new net/minecraft/client/animation/Keyframe
      // 31c2: dup
      // 31c3: ldc 0.79
      // 31c5: ldc_w -172.226
      // 31c8: ldc_w 13.235
      // 31cb: ldc_w 187.774
      // 31ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31d7: aastore
      // 31d8: dup
      // 31d9: bipush 7
      // 31db: new net/minecraft/client/animation/Keyframe
      // 31de: dup
      // 31df: ldc 0.83
      // 31e1: ldc_w -172.202
      // 31e4: ldc_w 8.288
      // 31e7: ldc_w 187.798
      // 31ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31f3: aastore
      // 31f4: dup
      // 31f5: bipush 8
      // 31f7: new net/minecraft/client/animation/Keyframe
      // 31fa: dup
      // 31fb: ldc 0.87
      // 31fd: ldc_w -172.181
      // 3200: ldc_w 4.851
      // 3203: ldc_w 187.819
      // 3206: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3209: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 320c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 320f: aastore
      // 3210: dup
      // 3211: bipush 9
      // 3213: new net/minecraft/client/animation/Keyframe
      // 3216: dup
      // 3217: ldc 0.92
      // 3219: ldc_w -172.168
      // 321c: ldc_w 2.889
      // 321f: ldc_w 187.832
      // 3222: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3225: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3228: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 322b: aastore
      // 322c: dup
      // 322d: bipush 10
      // 322f: new net/minecraft/client/animation/Keyframe
      // 3232: dup
      // 3233: ldc 0.96
      // 3235: ldc_w -172.18
      // 3238: ldc_w 2.393
      // 323b: ldc_w 187.82
      // 323e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3241: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3244: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3247: aastore
      // 3248: dup
      // 3249: bipush 11
      // 324b: new net/minecraft/client/animation/Keyframe
      // 324e: dup
      // 324f: fconst_1
      // 3250: ldc_w -172.238
      // 3253: ldc_w 3.404
      // 3256: ldc_w 187.762
      // 3259: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 325c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 325f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3262: aastore
      // 3263: dup
      // 3264: bipush 12
      // 3266: new net/minecraft/client/animation/Keyframe
      // 3269: dup
      // 326a: ldc 1.08
      // 326c: ldc_w -172.425
      // 326f: ldc_w 3.533
      // 3272: ldc_w 187.575
      // 3275: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3278: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 327b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 327e: aastore
      // 327f: dup
      // 3280: bipush 13
      // 3282: new net/minecraft/client/animation/Keyframe
      // 3285: dup
      // 3286: ldc 1.12
      // 3288: ldc_w -172.276
      // 328b: ldc_w 4.267
      // 328e: ldc_w 187.724
      // 3291: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3294: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3297: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 329a: aastore
      // 329b: dup
      // 329c: bipush 14
      // 329e: new net/minecraft/client/animation/Keyframe
      // 32a1: dup
      // 32a2: ldc 1.16
      // 32a4: ldc_w -172.096
      // 32a7: ldc_w 4.613
      // 32aa: ldc_w 187.904
      // 32ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32b6: aastore
      // 32b7: dup
      // 32b8: bipush 15
      // 32ba: new net/minecraft/client/animation/Keyframe
      // 32bd: dup
      // 32be: ldc 1.25
      // 32c0: ldc_w -171.797
      // 32c3: ldc_w 4.994
      // 32c6: ldc_w 188.203
      // 32c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32d2: aastore
      // 32d3: dup
      // 32d4: bipush 16
      // 32d6: new net/minecraft/client/animation/Keyframe
      // 32d9: dup
      // 32da: ldc_w 1.37
      // 32dd: ldc_w -171.535
      // 32e0: ldc_w 5.14
      // 32e3: ldc_w 188.465
      // 32e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32ef: aastore
      // 32f0: dup
      // 32f1: bipush 17
      // 32f3: new net/minecraft/client/animation/Keyframe
      // 32f6: dup
      // 32f7: ldc 1.54
      // 32f9: ldc_w -171.572
      // 32fc: ldc_w 4.861
      // 32ff: ldc_w 188.428
      // 3302: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3305: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3308: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 330b: aastore
      // 330c: dup
      // 330d: bipush 18
      // 330f: new net/minecraft/client/animation/Keyframe
      // 3312: dup
      // 3313: ldc 1.62
      // 3315: ldc_w -171.722
      // 3318: ldc_w 4.728
      // 331b: ldc_w 188.278
      // 331e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3321: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3324: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3327: aastore
      // 3328: dup
      // 3329: bipush 19
      // 332b: new net/minecraft/client/animation/Keyframe
      // 332e: dup
      // 332f: ldc_w 1.66
      // 3332: ldc_w -171.787
      // 3335: ldc_w 4.767
      // 3338: ldc_w 188.213
      // 333b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 333e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3341: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3344: aastore
      // 3345: dup
      // 3346: bipush 20
      // 3348: new net/minecraft/client/animation/Keyframe
      // 334b: dup
      // 334c: ldc 1.71
      // 334e: ldc_w -171.826
      // 3351: ldc_w 4.931
      // 3354: ldc_w 188.174
      // 3357: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 335a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 335d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3360: aastore
      // 3361: dup
      // 3362: bipush 21
      // 3364: new net/minecraft/client/animation/Keyframe
      // 3367: dup
      // 3368: ldc 1.75
      // 336a: ldc_w -171.823
      // 336d: ldc_w 5.261
      // 3370: ldc_w 188.177
      // 3373: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3376: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3379: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 337c: aastore
      // 337d: dup
      // 337e: bipush 22
      // 3380: new net/minecraft/client/animation/Keyframe
      // 3383: dup
      // 3384: ldc 1.79
      // 3386: ldc_w -171.787
      // 3389: ldc_w 6.28
      // 338c: ldc_w 188.212
      // 338f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3392: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3395: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3398: aastore
      // 3399: dup
      // 339a: bipush 23
      // 339c: new net/minecraft/client/animation/Keyframe
      // 339f: dup
      // 33a0: ldc 1.83
      // 33a2: ldc_w -171.741
      // 33a5: ldc_w 8.378
      // 33a8: ldc_w 188.259
      // 33ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33b4: aastore
      // 33b5: dup
      // 33b6: bipush 24
      // 33b8: new net/minecraft/client/animation/Keyframe
      // 33bb: dup
      // 33bc: ldc_w 1.87
      // 33bf: ldc_w -171.692
      // 33c2: ldc_w 11.332
      // 33c5: ldc_w 188.308
      // 33c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33d1: aastore
      // 33d2: dup
      // 33d3: bipush 25
      // 33d5: new net/minecraft/client/animation/Keyframe
      // 33d8: dup
      // 33d9: ldc_w 1.92
      // 33dc: ldc_w -171.655
      // 33df: ldc_w 14.866
      // 33e2: ldc_w 188.345
      // 33e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33ee: aastore
      // 33ef: dup
      // 33f0: bipush 26
      // 33f2: new net/minecraft/client/animation/Keyframe
      // 33f5: dup
      // 33f6: ldc_w 1.96
      // 33f9: ldc_w -171.645
      // 33fc: ldc_w 18.721
      // 33ff: ldc_w 188.355
      // 3402: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3405: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3408: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 340b: aastore
      // 340c: dup
      // 340d: bipush 27
      // 340f: new net/minecraft/client/animation/Keyframe
      // 3412: dup
      // 3413: ldc_w 2.04
      // 3416: ldc_w -171.737
      // 3419: ldc_w 26.59
      // 341c: ldc_w 188.261
      // 341f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3422: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3425: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3428: aastore
      // 3429: dup
      // 342a: bipush 28
      // 342c: new net/minecraft/client/animation/Keyframe
      // 342f: dup
      // 3430: ldc_w 2.08
      // 3433: ldc_w -171.844
      // 3436: ldc_w 30.287
      // 3439: ldc_w 188.155
      // 343c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 343f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3442: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3445: aastore
      // 3446: dup
      // 3447: bipush 29
      // 3449: new net/minecraft/client/animation/Keyframe
      // 344c: dup
      // 344d: ldc_w 2.12
      // 3450: ldc_w -172.0
      // 3453: ldc_w 33.745
      // 3456: ldc_w 188.0
      // 3459: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 345c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 345f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3462: aastore
      // 3463: dup
      // 3464: bipush 30
      // 3466: new net/minecraft/client/animation/Keyframe
      // 3469: dup
      // 346a: ldc_w 2.16
      // 346d: ldc_w -172.206
      // 3470: ldc_w 36.986
      // 3473: ldc_w 187.794
      // 3476: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3479: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 347c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 347f: aastore
      // 3480: dup
      // 3481: bipush 31
      // 3483: new net/minecraft/client/animation/Keyframe
      // 3486: dup
      // 3487: ldc_w 2.21
      // 348a: ldc_w -172.448
      // 348d: ldc_w 39.952
      // 3490: ldc_w 187.552
      // 3493: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3496: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3499: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 349c: aastore
      // 349d: dup
      // 349e: bipush 32
      // 34a0: new net/minecraft/client/animation/Keyframe
      // 34a3: dup
      // 34a4: ldc_w 2.25
      // 34a7: ldc_w -172.701
      // 34aa: ldc_w 42.548
      // 34ad: ldc_w 187.299
      // 34b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34b9: aastore
      // 34ba: dup
      // 34bb: bipush 33
      // 34bd: new net/minecraft/client/animation/Keyframe
      // 34c0: dup
      // 34c1: ldc_w 2.29
      // 34c4: ldc_w -172.932
      // 34c7: ldc_w 44.632
      // 34ca: ldc_w 187.068
      // 34cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34d6: aastore
      // 34d7: dup
      // 34d8: bipush 34
      // 34da: new net/minecraft/client/animation/Keyframe
      // 34dd: dup
      // 34de: ldc_w 2.33
      // 34e1: ldc_w -173.098
      // 34e4: ldc_w 46.021
      // 34e7: ldc_w 186.902
      // 34ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34f3: aastore
      // 34f4: dup
      // 34f5: bipush 35
      // 34f7: new net/minecraft/client/animation/Keyframe
      // 34fa: dup
      // 34fb: ldc 2.37
      // 34fd: ldc_w -173.16
      // 3500: ldc_w 46.524
      // 3503: ldc_w 186.84
      // 3506: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3509: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 350c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 350f: aastore
      // 3510: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3513: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3516: ldc_w "CTR_leg_left"
      // 3519: new net/minecraft/client/animation/AnimationChannel
      // 351c: dup
      // 351d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3520: bipush 42
      // 3522: anewarray 28
      // 3525: dup
      // 3526: bipush 0
      // 3527: new net/minecraft/client/animation/Keyframe
      // 352a: dup
      // 352b: ldc 0.54
      // 352d: ldc_w 3.6
      // 3530: ldc_w -16.493
      // 3533: ldc_w -11.104
      // 3536: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3539: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 353c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 353f: aastore
      // 3540: dup
      // 3541: bipush 1
      // 3542: new net/minecraft/client/animation/Keyframe
      // 3545: dup
      // 3546: ldc 0.58
      // 3548: ldc_w 3.576
      // 354b: ldc_w -16.276
      // 354e: ldc_w -10.928
      // 3551: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3554: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3557: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 355a: aastore
      // 355b: dup
      // 355c: bipush 2
      // 355d: new net/minecraft/client/animation/Keyframe
      // 3560: dup
      // 3561: ldc 0.62
      // 3563: ldc_w 3.507
      // 3566: ldc_w -15.682
      // 3569: ldc_w -10.444
      // 356c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 356f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3572: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3575: aastore
      // 3576: dup
      // 3577: bipush 3
      // 3578: new net/minecraft/client/animation/Keyframe
      // 357b: dup
      // 357c: ldc 0.71
      // 357e: ldc_w 3.247
      // 3581: ldc_w -13.696
      // 3584: ldc_w -8.827
      // 3587: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 358a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 358d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3590: aastore
      // 3591: dup
      // 3592: bipush 4
      // 3593: new net/minecraft/client/animation/Keyframe
      // 3596: dup
      // 3597: ldc 0.75
      // 3599: ldc_w 3.062
      // 359c: ldc_w -12.473
      // 359f: ldc_w -7.831
      // 35a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35ab: aastore
      // 35ac: dup
      // 35ad: bipush 5
      // 35ae: new net/minecraft/client/animation/Keyframe
      // 35b1: dup
      // 35b2: ldc 0.79
      // 35b4: ldc_w 2.875
      // 35b7: ldc_w -10.881
      // 35ba: ldc_w -6.874
      // 35bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35c6: aastore
      // 35c7: dup
      // 35c8: bipush 6
      // 35ca: new net/minecraft/client/animation/Keyframe
      // 35cd: dup
      // 35ce: ldc 0.83
      // 35d0: ldc_w 2.697
      // 35d3: ldc_w -8.933
      // 35d6: ldc_w -6.137
      // 35d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e2: aastore
      // 35e3: dup
      // 35e4: bipush 7
      // 35e6: new net/minecraft/client/animation/Keyframe
      // 35e9: dup
      // 35ea: ldc 0.87
      // 35ec: ldc_w 2.465
      // 35ef: ldc_w -7.459
      // 35f2: ldc_w -5.459
      // 35f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35fe: aastore
      // 35ff: dup
      // 3600: bipush 8
      // 3602: new net/minecraft/client/animation/Keyframe
      // 3605: dup
      // 3606: ldc 0.92
      // 3608: ldc_w 2.197
      // 360b: ldc_w -6.339
      // 360e: ldc_w -4.892
      // 3611: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3614: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3617: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 361a: aastore
      // 361b: dup
      // 361c: bipush 9
      // 361e: new net/minecraft/client/animation/Keyframe
      // 3621: dup
      // 3622: ldc 0.96
      // 3624: ldc_w 1.918
      // 3627: ldc_w -5.425
      // 362a: ldc_w -4.564
      // 362d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3630: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3633: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3636: aastore
      // 3637: dup
      // 3638: bipush 10
      // 363a: new net/minecraft/client/animation/Keyframe
      // 363d: dup
      // 363e: fconst_1
      // 363f: ldc_w 1.652
      // 3642: ldc_w -4.528
      // 3645: ldc_w -4.628
      // 3648: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 364b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 364e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3651: aastore
      // 3652: dup
      // 3653: bipush 11
      // 3655: new net/minecraft/client/animation/Keyframe
      // 3658: dup
      // 3659: ldc 1.04
      // 365b: ldc_w 1.421
      // 365e: ldc_w -3.638
      // 3661: ldc_w -5.944
      // 3664: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3667: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 366a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 366d: aastore
      // 366e: dup
      // 366f: bipush 12
      // 3671: new net/minecraft/client/animation/Keyframe
      // 3674: dup
      // 3675: ldc 1.08
      // 3677: ldc_w 1.408
      // 367a: ldc_w -2.144
      // 367d: ldc_w -7.507
      // 3680: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3683: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3686: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3689: aastore
      // 368a: dup
      // 368b: bipush 13
      // 368d: new net/minecraft/client/animation/Keyframe
      // 3690: dup
      // 3691: ldc 1.12
      // 3693: ldc_w 1.487
      // 3696: ldc_w -1.571
      // 3699: ldc_w -7.656
      // 369c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 369f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a5: aastore
      // 36a6: dup
      // 36a7: bipush 14
      // 36a9: new net/minecraft/client/animation/Keyframe
      // 36ac: dup
      // 36ad: ldc 1.16
      // 36af: ldc_w 1.513
      // 36b2: ldc_w -1.699
      // 36b5: ldc_w -7.566
      // 36b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36c1: aastore
      // 36c2: dup
      // 36c3: bipush 15
      // 36c5: new net/minecraft/client/animation/Keyframe
      // 36c8: dup
      // 36c9: ldc 1.21
      // 36cb: ldc_w 1.519
      // 36ce: ldc_w -1.971
      // 36d1: ldc_w -7.436
      // 36d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36dd: aastore
      // 36de: dup
      // 36df: bipush 16
      // 36e1: new net/minecraft/client/animation/Keyframe
      // 36e4: dup
      // 36e5: ldc 1.29
      // 36e7: ldc_w 1.5
      // 36ea: ldc_w -2.58
      // 36ed: ldc_w -7.179
      // 36f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36f9: aastore
      // 36fa: dup
      // 36fb: bipush 17
      // 36fd: new net/minecraft/client/animation/Keyframe
      // 3700: dup
      // 3701: ldc 1.33
      // 3703: ldc_w 1.486
      // 3706: ldc_w -2.841
      // 3709: ldc_w -7.076
      // 370c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 370f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3712: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3715: aastore
      // 3716: dup
      // 3717: bipush 18
      // 3719: new net/minecraft/client/animation/Keyframe
      // 371c: dup
      // 371d: ldc_w 1.37
      // 3720: ldc_w 1.473
      // 3723: ldc_w -3.04
      // 3726: ldc_w -7.0
      // 3729: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 372c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 372f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3732: aastore
      // 3733: dup
      // 3734: bipush 19
      // 3736: new net/minecraft/client/animation/Keyframe
      // 3739: dup
      // 373a: ldc 1.42
      // 373c: ldc_w 1.466
      // 373f: ldc_w -3.159
      // 3742: ldc_w -6.957
      // 3745: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3748: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 374b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 374e: aastore
      // 374f: dup
      // 3750: bipush 20
      // 3752: new net/minecraft/client/animation/Keyframe
      // 3755: dup
      // 3756: ldc 1.46
      // 3758: ldc_w 1.469
      // 375b: ldc_w -3.143
      // 375e: ldc_w -6.968
      // 3761: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3764: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3767: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 376a: aastore
      // 376b: dup
      // 376c: bipush 21
      // 376e: new net/minecraft/client/animation/Keyframe
      // 3771: dup
      // 3772: ldc_w 1.5
      // 3775: ldc_w 1.485
      // 3778: ldc_w -2.971
      // 377b: ldc_w -7.042
      // 377e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3781: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3784: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3787: aastore
      // 3788: dup
      // 3789: bipush 22
      // 378b: new net/minecraft/client/animation/Keyframe
      // 378e: dup
      // 378f: ldc 1.54
      // 3791: ldc_w 1.509
      // 3794: ldc_w -2.683
      // 3797: ldc_w -7.162
      // 379a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 379d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37a3: aastore
      // 37a4: dup
      // 37a5: bipush 23
      // 37a7: new net/minecraft/client/animation/Keyframe
      // 37aa: dup
      // 37ab: ldc 1.58
      // 37ad: ldc_w 1.537
      // 37b0: ldc_w -2.32
      // 37b3: ldc_w -7.31
      // 37b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37bf: aastore
      // 37c0: dup
      // 37c1: bipush 24
      // 37c3: new net/minecraft/client/animation/Keyframe
      // 37c6: dup
      // 37c7: ldc_w 1.66
      // 37ca: ldc_w 1.592
      // 37cd: ldc_w -1.557
      // 37d0: ldc_w -7.605
      // 37d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37dc: aastore
      // 37dd: dup
      // 37de: bipush 25
      // 37e0: new net/minecraft/client/animation/Keyframe
      // 37e3: dup
      // 37e4: ldc 1.71
      // 37e6: ldc_w 1.616
      // 37e9: ldc_w -1.25
      // 37ec: ldc_w -7.703
      // 37ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37f8: aastore
      // 37f9: dup
      // 37fa: bipush 26
      // 37fc: new net/minecraft/client/animation/Keyframe
      // 37ff: dup
      // 3800: ldc 1.75
      // 3802: ldc_w 1.637
      // 3805: ldc_w -1.056
      // 3808: ldc_w -7.735
      // 380b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 380e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3811: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3814: aastore
      // 3815: dup
      // 3816: bipush 27
      // 3818: new net/minecraft/client/animation/Keyframe
      // 381b: dup
      // 381c: ldc 1.79
      // 381e: ldc_w 1.656
      // 3821: ldc_w -0.928
      // 3824: ldc_w -7.652
      // 3827: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 382a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 382d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3830: aastore
      // 3831: dup
      // 3832: bipush 28
      // 3834: new net/minecraft/client/animation/Keyframe
      // 3837: dup
      // 3838: ldc 1.83
      // 383a: ldc_w 1.674
      // 383d: ldc_w -0.824
      // 3840: ldc_w -7.419
      // 3843: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3846: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3849: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 384c: aastore
      // 384d: dup
      // 384e: bipush 29
      // 3850: new net/minecraft/client/animation/Keyframe
      // 3853: dup
      // 3854: ldc_w 1.87
      // 3857: ldc_w 1.684
      // 385a: ldc_w -0.793
      // 385d: ldc_w -7.057
      // 3860: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3863: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3866: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3869: aastore
      // 386a: dup
      // 386b: bipush 30
      // 386d: new net/minecraft/client/animation/Keyframe
      // 3870: dup
      // 3871: ldc_w 1.92
      // 3874: ldc_w 1.679
      // 3877: ldc_w -0.868
      // 387a: ldc_w -6.61
      // 387d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3880: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3883: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3886: aastore
      // 3887: dup
      // 3888: bipush 31
      // 388a: new net/minecraft/client/animation/Keyframe
      // 388d: dup
      // 388e: ldc_w 1.96
      // 3891: ldc_w 1.656
      // 3894: ldc_w -1.066
      // 3897: ldc_w -6.13
      // 389a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 389d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38a3: aastore
      // 38a4: dup
      // 38a5: bipush 32
      // 38a7: new net/minecraft/client/animation/Keyframe
      // 38aa: dup
      // 38ab: fconst_2
      // 38ac: ldc_w 1.612
      // 38af: ldc_w -1.395
      // 38b2: ldc_w -5.665
      // 38b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38be: aastore
      // 38bf: dup
      // 38c0: bipush 33
      // 38c2: new net/minecraft/client/animation/Keyframe
      // 38c5: dup
      // 38c6: ldc_w 2.04
      // 38c9: ldc_w 1.55
      // 38cc: ldc_w -1.853
      // 38cf: ldc_w -5.258
      // 38d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38db: aastore
      // 38dc: dup
      // 38dd: bipush 34
      // 38df: new net/minecraft/client/animation/Keyframe
      // 38e2: dup
      // 38e3: ldc_w 2.08
      // 38e6: ldc_w 1.472
      // 38e9: ldc_w -2.422
      // 38ec: ldc_w -4.94
      // 38ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38f8: aastore
      // 38f9: dup
      // 38fa: bipush 35
      // 38fc: new net/minecraft/client/animation/Keyframe
      // 38ff: dup
      // 3900: ldc_w 2.12
      // 3903: ldc_w 1.391
      // 3906: ldc_w -2.986
      // 3909: ldc_w -4.712
      // 390c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 390f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3912: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3915: aastore
      // 3916: dup
      // 3917: bipush 36
      // 3919: new net/minecraft/client/animation/Keyframe
      // 391c: dup
      // 391d: ldc_w 2.16
      // 3920: ldc_w 1.321
      // 3923: ldc_w -3.429
      // 3926: ldc_w -4.54
      // 3929: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 392c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 392f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3932: aastore
      // 3933: dup
      // 3934: bipush 37
      // 3936: new net/minecraft/client/animation/Keyframe
      // 3939: dup
      // 393a: ldc_w 2.21
      // 393d: ldc_w 1.264
      // 3940: ldc_w -3.735
      // 3943: ldc_w -4.4
      // 3946: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3949: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 394c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 394f: aastore
      // 3950: dup
      // 3951: bipush 38
      // 3953: new net/minecraft/client/animation/Keyframe
      // 3956: dup
      // 3957: ldc_w 2.25
      // 395a: ldc_w 1.219
      // 395d: ldc_w -3.914
      // 3960: ldc_w -4.273
      // 3963: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3966: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3969: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 396c: aastore
      // 396d: dup
      // 396e: bipush 39
      // 3970: new net/minecraft/client/animation/Keyframe
      // 3973: dup
      // 3974: ldc_w 2.29
      // 3977: ldc_w 1.185
      // 397a: ldc_w -4.002
      // 397d: ldc_w -4.162
      // 3980: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3983: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3986: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3989: aastore
      // 398a: dup
      // 398b: bipush 40
      // 398d: new net/minecraft/client/animation/Keyframe
      // 3990: dup
      // 3991: ldc_w 2.33
      // 3994: ldc_w 1.164
      // 3997: ldc_w -4.037
      // 399a: ldc_w -4.082
      // 399d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39a6: aastore
      // 39a7: dup
      // 39a8: bipush 41
      // 39aa: new net/minecraft/client/animation/Keyframe
      // 39ad: dup
      // 39ae: ldc 2.37
      // 39b0: ldc_w 1.156
      // 39b3: ldc_w -4.045
      // 39b6: ldc_w -4.051
      // 39b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39c2: aastore
      // 39c3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 39c6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 39c9: ldc_w "CTR_leg_right"
      // 39cc: new net/minecraft/client/animation/AnimationChannel
      // 39cf: dup
      // 39d0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 39d3: bipush 36
      // 39d5: anewarray 28
      // 39d8: dup
      // 39d9: bipush 0
      // 39da: new net/minecraft/client/animation/Keyframe
      // 39dd: dup
      // 39de: ldc 0.54
      // 39e0: ldc_w 0.852
      // 39e3: ldc_w -7.871
      // 39e6: ldc_w -0.852
      // 39e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39f2: aastore
      // 39f3: dup
      // 39f4: bipush 1
      // 39f5: new net/minecraft/client/animation/Keyframe
      // 39f8: dup
      // 39f9: ldc 0.58
      // 39fb: ldc_w -0.557
      // 39fe: ldc_w 2.403
      // 3a01: ldc_w 0.557
      // 3a04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a0d: aastore
      // 3a0e: dup
      // 3a0f: bipush 2
      // 3a10: new net/minecraft/client/animation/Keyframe
      // 3a13: dup
      // 3a14: ldc 0.62
      // 3a16: ldc_w -1.744
      // 3a19: ldc_w 13.179
      // 3a1c: ldc_w 1.744
      // 3a1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a28: aastore
      // 3a29: dup
      // 3a2a: bipush 3
      // 3a2b: new net/minecraft/client/animation/Keyframe
      // 3a2e: dup
      // 3a2f: ldc 0.66
      // 3a31: ldc_w -2.667
      // 3a34: ldc_w 23.761
      // 3a37: ldc_w 2.667
      // 3a3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a43: aastore
      // 3a44: dup
      // 3a45: bipush 4
      // 3a46: new net/minecraft/client/animation/Keyframe
      // 3a49: dup
      // 3a4a: ldc 0.71
      // 3a4c: ldc_w -3.318
      // 3a4f: ldc_w 33.482
      // 3a52: ldc_w 3.318
      // 3a55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a5e: aastore
      // 3a5f: dup
      // 3a60: bipush 5
      // 3a61: new net/minecraft/client/animation/Keyframe
      // 3a64: dup
      // 3a65: ldc 0.75
      // 3a67: ldc_w -3.775
      // 3a6a: ldc_w 41.622
      // 3a6d: ldc_w 3.775
      // 3a70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a79: aastore
      // 3a7a: dup
      // 3a7b: bipush 6
      // 3a7d: new net/minecraft/client/animation/Keyframe
      // 3a80: dup
      // 3a81: ldc 0.79
      // 3a83: ldc_w -4.284
      // 3a86: ldc_w 47.888
      // 3a89: ldc_w 4.284
      // 3a8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a95: aastore
      // 3a96: dup
      // 3a97: bipush 7
      // 3a99: new net/minecraft/client/animation/Keyframe
      // 3a9c: dup
      // 3a9d: ldc 0.83
      // 3a9f: ldc_w -4.878
      // 3aa2: ldc_w 52.306
      // 3aa5: ldc_w 4.878
      // 3aa8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3aab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ab1: aastore
      // 3ab2: dup
      // 3ab3: bipush 8
      // 3ab5: new net/minecraft/client/animation/Keyframe
      // 3ab8: dup
      // 3ab9: ldc 0.87
      // 3abb: ldc_w -5.402
      // 3abe: ldc_w 54.879
      // 3ac1: ldc_w 5.402
      // 3ac4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ac7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3acd: aastore
      // 3ace: dup
      // 3acf: bipush 9
      // 3ad1: new net/minecraft/client/animation/Keyframe
      // 3ad4: dup
      // 3ad5: ldc 0.92
      // 3ad7: ldc_w -5.836
      // 3ada: ldc_w 55.827
      // 3add: ldc_w 5.836
      // 3ae0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ae3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ae6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ae9: aastore
      // 3aea: dup
      // 3aeb: bipush 10
      // 3aed: new net/minecraft/client/animation/Keyframe
      // 3af0: dup
      // 3af1: ldc 0.96
      // 3af3: ldc_w -6.177
      // 3af6: ldc_w 55.311
      // 3af9: ldc_w 6.177
      // 3afc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3aff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b05: aastore
      // 3b06: dup
      // 3b07: bipush 11
      // 3b09: new net/minecraft/client/animation/Keyframe
      // 3b0c: dup
      // 3b0d: fconst_1
      // 3b0e: ldc_w -6.761
      // 3b11: ldc_w 55.229
      // 3b14: ldc_w 6.761
      // 3b17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b20: aastore
      // 3b21: dup
      // 3b22: bipush 12
      // 3b24: new net/minecraft/client/animation/Keyframe
      // 3b27: dup
      // 3b28: ldc 1.04
      // 3b2a: ldc_w -7.653
      // 3b2d: ldc_w 56.115
      // 3b30: ldc_w 7.653
      // 3b33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b3c: aastore
      // 3b3d: dup
      // 3b3e: bipush 13
      // 3b40: new net/minecraft/client/animation/Keyframe
      // 3b43: dup
      // 3b44: ldc 1.08
      // 3b46: ldc_w -8.36
      // 3b49: ldc_w 55.723
      // 3b4c: ldc_w 8.36
      // 3b4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b58: aastore
      // 3b59: dup
      // 3b5a: bipush 14
      // 3b5c: new net/minecraft/client/animation/Keyframe
      // 3b5f: dup
      // 3b60: ldc 1.12
      // 3b62: ldc_w -8.491
      // 3b65: ldc_w 54.841
      // 3b68: ldc_w 8.491
      // 3b6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b74: aastore
      // 3b75: dup
      // 3b76: bipush 15
      // 3b78: new net/minecraft/client/animation/Keyframe
      // 3b7b: dup
      // 3b7c: ldc 1.16
      // 3b7e: ldc_w -8.496
      // 3b81: ldc_w 54.249
      // 3b84: ldc_w 8.496
      // 3b87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b90: aastore
      // 3b91: dup
      // 3b92: bipush 16
      // 3b94: new net/minecraft/client/animation/Keyframe
      // 3b97: dup
      // 3b98: ldc_w 1.37
      // 3b9b: ldc_w -8.217
      // 3b9e: ldc_w 51.935
      // 3ba1: ldc_w 8.217
      // 3ba4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ba7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3baa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bad: aastore
      // 3bae: dup
      // 3baf: bipush 17
      // 3bb1: new net/minecraft/client/animation/Keyframe
      // 3bb4: dup
      // 3bb5: ldc 1.42
      // 3bb7: ldc_w -8.144
      // 3bba: ldc_w 51.485
      // 3bbd: ldc_w 8.144
      // 3bc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bc9: aastore
      // 3bca: dup
      // 3bcb: bipush 18
      // 3bcd: new net/minecraft/client/animation/Keyframe
      // 3bd0: dup
      // 3bd1: ldc_w 1.5
      // 3bd4: ldc_w -7.984
      // 3bd7: ldc_w 50.506
      // 3bda: ldc_w 7.984
      // 3bdd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3be0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3be3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3be6: aastore
      // 3be7: dup
      // 3be8: bipush 19
      // 3bea: new net/minecraft/client/animation/Keyframe
      // 3bed: dup
      // 3bee: ldc 1.58
      // 3bf0: ldc_w -7.791
      // 3bf3: ldc_w 49.258
      // 3bf6: ldc_w 7.791
      // 3bf9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c02: aastore
      // 3c03: dup
      // 3c04: bipush 20
      // 3c06: new net/minecraft/client/animation/Keyframe
      // 3c09: dup
      // 3c0a: ldc 1.62
      // 3c0c: ldc_w -7.678
      // 3c0f: ldc_w 48.455
      // 3c12: ldc_w 7.678
      // 3c15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c1e: aastore
      // 3c1f: dup
      // 3c20: bipush 21
      // 3c22: new net/minecraft/client/animation/Keyframe
      // 3c25: dup
      // 3c26: ldc 1.71
      // 3c28: ldc_w -7.412
      // 3c2b: ldc_w 46.336
      // 3c2e: ldc_w 7.412
      // 3c31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c3a: aastore
      // 3c3b: dup
      // 3c3c: bipush 22
      // 3c3e: new net/minecraft/client/animation/Keyframe
      // 3c41: dup
      // 3c42: ldc 1.75
      // 3c44: ldc_w -7.257
      // 3c47: ldc_w 44.962
      // 3c4a: ldc_w 7.257
      // 3c4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c56: aastore
      // 3c57: dup
      // 3c58: bipush 23
      // 3c5a: new net/minecraft/client/animation/Keyframe
      // 3c5d: dup
      // 3c5e: ldc 1.79
      // 3c60: ldc_w -7.101
      // 3c63: ldc_w 43.245
      // 3c66: ldc_w 7.101
      // 3c69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c72: aastore
      // 3c73: dup
      // 3c74: bipush 24
      // 3c76: new net/minecraft/client/animation/Keyframe
      // 3c79: dup
      // 3c7a: ldc 1.83
      // 3c7c: ldc_w -6.959
      // 3c7f: ldc_w 41.132
      // 3c82: ldc_w 6.959
      // 3c85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c8e: aastore
      // 3c8f: dup
      // 3c90: bipush 25
      // 3c92: new net/minecraft/client/animation/Keyframe
      // 3c95: dup
      // 3c96: ldc_w 1.87
      // 3c99: ldc_w -6.827
      // 3c9c: ldc_w 38.741
      // 3c9f: ldc_w 6.827
      // 3ca2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ca5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ca8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cab: aastore
      // 3cac: dup
      // 3cad: bipush 26
      // 3caf: new net/minecraft/client/animation/Keyframe
      // 3cb2: dup
      // 3cb3: ldc_w 1.92
      // 3cb6: ldc_w -6.698
      // 3cb9: ldc_w 36.212
      // 3cbc: ldc_w 6.698
      // 3cbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cc8: aastore
      // 3cc9: dup
      // 3cca: bipush 27
      // 3ccc: new net/minecraft/client/animation/Keyframe
      // 3ccf: dup
      // 3cd0: fconst_2
      // 3cd1: ldc_w -6.421
      // 3cd4: ldc_w 31.189
      // 3cd7: ldc_w 6.421
      // 3cda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ce0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ce3: aastore
      // 3ce4: dup
      // 3ce5: bipush 28
      // 3ce7: new net/minecraft/client/animation/Keyframe
      // 3cea: dup
      // 3ceb: ldc_w 2.04
      // 3cee: ldc_w -6.264
      // 3cf1: ldc_w 28.835
      // 3cf4: ldc_w 6.264
      // 3cf7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cfa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cfd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d00: aastore
      // 3d01: dup
      // 3d02: bipush 29
      // 3d04: new net/minecraft/client/animation/Keyframe
      // 3d07: dup
      // 3d08: ldc_w 2.08
      // 3d0b: ldc_w -6.09
      // 3d0e: ldc_w 26.652
      // 3d11: ldc_w 6.09
      // 3d14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d1d: aastore
      // 3d1e: dup
      // 3d1f: bipush 30
      // 3d21: new net/minecraft/client/animation/Keyframe
      // 3d24: dup
      // 3d25: ldc_w 2.16
      // 3d28: ldc_w -5.709
      // 3d2b: ldc_w 22.669
      // 3d2e: ldc_w 5.709
      // 3d31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d3a: aastore
      // 3d3b: dup
      // 3d3c: bipush 31
      // 3d3e: new net/minecraft/client/animation/Keyframe
      // 3d41: dup
      // 3d42: ldc_w 2.21
      // 3d45: ldc_w -5.518
      // 3d48: ldc_w 20.86
      // 3d4b: ldc_w 5.518
      // 3d4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d57: aastore
      // 3d58: dup
      // 3d59: bipush 32
      // 3d5b: new net/minecraft/client/animation/Keyframe
      // 3d5e: dup
      // 3d5f: ldc_w 2.25
      // 3d62: ldc_w -5.345
      // 3d65: ldc_w 19.249
      // 3d68: ldc_w 5.34
      // 3d6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d74: aastore
      // 3d75: dup
      // 3d76: bipush 33
      // 3d78: new net/minecraft/client/animation/Keyframe
      // 3d7b: dup
      // 3d7c: ldc_w 2.29
      // 3d7f: ldc_w -5.192
      // 3d82: ldc_w 17.937
      // 3d85: ldc_w 5.192
      // 3d88: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d91: aastore
      // 3d92: dup
      // 3d93: bipush 34
      // 3d95: new net/minecraft/client/animation/Keyframe
      // 3d98: dup
      // 3d99: ldc_w 2.33
      // 3d9c: ldc_w -5.091
      // 3d9f: ldc_w 17.055
      // 3da2: ldc_w 5.091
      // 3da5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3da8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dae: aastore
      // 3daf: dup
      // 3db0: bipush 35
      // 3db2: new net/minecraft/client/animation/Keyframe
      // 3db5: dup
      // 3db6: ldc 2.37
      // 3db8: ldc_w -5.053
      // 3dbb: ldc_w 16.735
      // 3dbe: ldc_w 5.054
      // 3dc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dca: aastore
      // 3dcb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3dce: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3dd1: ldc_w "CTR_leg_right"
      // 3dd4: new net/minecraft/client/animation/AnimationChannel
      // 3dd7: dup
      // 3dd8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ddb: bipush 38
      // 3ddd: anewarray 28
      // 3de0: dup
      // 3de1: bipush 0
      // 3de2: new net/minecraft/client/animation/Keyframe
      // 3de5: dup
      // 3de6: ldc 0.54
      // 3de8: ldc_w 1.039
      // 3deb: ldc_w -12.268
      // 3dee: ldc_w -6.874
      // 3df1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3df4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3df7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dfa: aastore
      // 3dfb: dup
      // 3dfc: bipush 1
      // 3dfd: new net/minecraft/client/animation/Keyframe
      // 3e00: dup
      // 3e01: ldc 0.58
      // 3e03: ldc_w 0.918
      // 3e06: ldc_w -12.025
      // 3e09: ldc_w -6.719
      // 3e0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e15: aastore
      // 3e16: dup
      // 3e17: bipush 2
      // 3e18: new net/minecraft/client/animation/Keyframe
      // 3e1b: dup
      // 3e1c: ldc 0.62
      // 3e1e: ldc_w 0.589
      // 3e21: ldc_w -11.366
      // 3e24: ldc_w -6.297
      // 3e27: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e30: aastore
      // 3e31: dup
      // 3e32: bipush 3
      // 3e33: new net/minecraft/client/animation/Keyframe
      // 3e36: dup
      // 3e37: ldc 0.66
      // 3e39: ldc_w 0.103
      // 3e3c: ldc_w -10.394
      // 3e3f: ldc_w -5.676
      // 3e42: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e4b: aastore
      // 3e4c: dup
      // 3e4d: bipush 4
      // 3e4e: new net/minecraft/client/animation/Keyframe
      // 3e51: dup
      // 3e52: ldc 0.71
      // 3e54: ldc_w -0.486
      // 3e57: ldc_w -9.214
      // 3e5a: ldc_w -4.921
      // 3e5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e66: aastore
      // 3e67: dup
      // 3e68: bipush 5
      // 3e69: new net/minecraft/client/animation/Keyframe
      // 3e6c: dup
      // 3e6d: ldc 0.79
      // 3e6f: ldc_w -1.768
      // 3e72: ldc_w -6.645
      // 3e75: ldc_w -3.279
      // 3e78: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e81: aastore
      // 3e82: dup
      // 3e83: bipush 6
      // 3e85: new net/minecraft/client/animation/Keyframe
      // 3e88: dup
      // 3e89: ldc 0.83
      // 3e8b: ldc_w -2.357
      // 3e8e: ldc_w -5.465
      // 3e91: ldc_w -2.525
      // 3e94: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e9d: aastore
      // 3e9e: dup
      // 3e9f: bipush 7
      // 3ea1: new net/minecraft/client/animation/Keyframe
      // 3ea4: dup
      // 3ea5: ldc 0.87
      // 3ea7: ldc_w -2.843
      // 3eaa: ldc_w -4.493
      // 3ead: ldc_w -1.903
      // 3eb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3eb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eb9: aastore
      // 3eba: dup
      // 3ebb: bipush 8
      // 3ebd: new net/minecraft/client/animation/Keyframe
      // 3ec0: dup
      // 3ec1: ldc 0.92
      // 3ec3: ldc_w -3.172
      // 3ec6: ldc_w -3.834
      // 3ec9: ldc_w -1.482
      // 3ecc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ecf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ed2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ed5: aastore
      // 3ed6: dup
      // 3ed7: bipush 9
      // 3ed9: new net/minecraft/client/animation/Keyframe
      // 3edc: dup
      // 3edd: ldc 0.96
      // 3edf: ldc_w -3.293
      // 3ee2: ldc_w -3.591
      // 3ee5: ldc_w -1.326
      // 3ee8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3eeb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ef1: aastore
      // 3ef2: dup
      // 3ef3: bipush 10
      // 3ef5: new net/minecraft/client/animation/Keyframe
      // 3ef8: dup
      // 3ef9: fconst_1
      // 3efa: ldc_w -3.125
      // 3efd: ldc_w -4.321
      // 3f00: ldc_w -1.859
      // 3f03: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f0c: aastore
      // 3f0d: dup
      // 3f0e: bipush 11
      // 3f10: new net/minecraft/client/animation/Keyframe
      // 3f13: dup
      // 3f14: ldc 1.04
      // 3f16: ldc_w -2.812
      // 3f19: ldc_w -5.677
      // 3f1c: ldc_w -2.848
      // 3f1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f28: aastore
      // 3f29: dup
      // 3f2a: bipush 12
      // 3f2c: new net/minecraft/client/animation/Keyframe
      // 3f2f: dup
      // 3f30: ldc 1.08
      // 3f32: ldc_w -2.198
      // 3f35: ldc_w -5.382
      // 3f38: ldc_w -4.19
      // 3f3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f44: aastore
      // 3f45: dup
      // 3f46: bipush 13
      // 3f48: new net/minecraft/client/animation/Keyframe
      // 3f4b: dup
      // 3f4c: ldc 1.12
      // 3f4e: ldc_w -2.061
      // 3f51: ldc_w -5.077
      // 3f54: ldc_w -4.399
      // 3f57: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f60: aastore
      // 3f61: dup
      // 3f62: bipush 14
      // 3f64: new net/minecraft/client/animation/Keyframe
      // 3f67: dup
      // 3f68: ldc 1.16
      // 3f6a: ldc_w -2.136
      // 3f6d: ldc_w -5.244
      // 3f70: ldc_w -4.252
      // 3f73: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f7c: aastore
      // 3f7d: dup
      // 3f7e: bipush 15
      // 3f80: new net/minecraft/client/animation/Keyframe
      // 3f83: dup
      // 3f84: ldc 1.21
      // 3f86: ldc_w -2.253
      // 3f89: ldc_w -5.505
      // 3f8c: ldc_w -4.043
      // 3f8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f98: aastore
      // 3f99: dup
      // 3f9a: bipush 16
      // 3f9c: new net/minecraft/client/animation/Keyframe
      // 3f9f: dup
      // 3fa0: ldc 1.29
      // 3fa2: ldc_w -2.498
      // 3fa5: ldc_w -6.065
      // 3fa8: ldc_w -3.623
      // 3fab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3fae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fb4: aastore
      // 3fb5: dup
      // 3fb6: bipush 17
      // 3fb8: new net/minecraft/client/animation/Keyframe
      // 3fbb: dup
      // 3fbc: ldc 1.33
      // 3fbe: ldc_w -2.601
      // 3fc1: ldc_w -6.307
      // 3fc4: ldc_w -3.45
      // 3fc7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3fca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fcd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fd0: aastore
      // 3fd1: dup
      // 3fd2: bipush 18
      // 3fd4: new net/minecraft/client/animation/Keyframe
      // 3fd7: dup
      // 3fd8: ldc_w 1.37
      // 3fdb: ldc_w -2.643
      // 3fde: ldc_w -6.407
      // 3fe1: ldc_w -3.381
      // 3fe4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3fe7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fed: aastore
      // 3fee: dup
      // 3fef: bipush 19
      // 3ff1: new net/minecraft/client/animation/Keyframe
      // 3ff4: dup
      // 3ff5: ldc_w 1.5
      // 3ff8: ldc_w -2.643
      // 3ffb: ldc_w -6.407
      // 3ffe: ldc_w -3.381
      // 4001: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4004: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4007: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 400a: aastore
      // 400b: dup
      // 400c: bipush 20
      // 400e: new net/minecraft/client/animation/Keyframe
      // 4011: dup
      // 4012: ldc 1.54
      // 4014: ldc_w -2.612
      // 4017: ldc_w -6.329
      // 401a: ldc_w -3.43
      // 401d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4020: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4023: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4026: aastore
      // 4027: dup
      // 4028: bipush 21
      // 402a: new net/minecraft/client/animation/Keyframe
      // 402d: dup
      // 402e: ldc 1.58
      // 4030: ldc_w -2.501
      // 4033: ldc_w -6.044
      // 4036: ldc_w -3.603
      // 4039: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 403c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 403f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4042: aastore
      // 4043: dup
      // 4044: bipush 22
      // 4046: new net/minecraft/client/animation/Keyframe
      // 4049: dup
      // 404a: ldc_w 1.66
      // 404d: ldc_w -2.262
      // 4050: ldc_w -5.397
      // 4053: ldc_w -3.954
      // 4056: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4059: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 405c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 405f: aastore
      // 4060: dup
      // 4061: bipush 23
      // 4063: new net/minecraft/client/animation/Keyframe
      // 4066: dup
      // 4067: ldc 1.71
      // 4069: ldc_w -2.167
      // 406c: ldc_w -5.117
      // 406f: ldc_w -4.077
      // 4072: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4075: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4078: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 407b: aastore
      // 407c: dup
      // 407d: bipush 24
      // 407f: new net/minecraft/client/animation/Keyframe
      // 4082: dup
      // 4083: ldc 1.75
      // 4085: ldc_w -2.111
      // 4088: ldc_w -4.929
      // 408b: ldc_w -4.13
      // 408e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4091: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4094: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4097: aastore
      // 4098: dup
      // 4099: bipush 25
      // 409b: new net/minecraft/client/animation/Keyframe
      // 409e: dup
      // 409f: ldc 1.79
      // 40a1: ldc_w -2.088
      // 40a4: ldc_w -4.823
      // 40a7: ldc_w -4.122
      // 40aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40b3: aastore
      // 40b4: dup
      // 40b5: bipush 26
      // 40b7: new net/minecraft/client/animation/Keyframe
      // 40ba: dup
      // 40bb: ldc 1.83
      // 40bd: ldc_w -2.084
      // 40c0: ldc_w -4.772
      // 40c3: ldc_w -4.07
      // 40c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40cf: aastore
      // 40d0: dup
      // 40d1: bipush 27
      // 40d3: new net/minecraft/client/animation/Keyframe
      // 40d6: dup
      // 40d7: ldc_w 1.87
      // 40da: ldc_w -2.102
      // 40dd: ldc_w -4.786
      // 40e0: ldc_w -3.983
      // 40e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40ec: aastore
      // 40ed: dup
      // 40ee: bipush 28
      // 40f0: new net/minecraft/client/animation/Keyframe
      // 40f3: dup
      // 40f4: ldc_w 1.92
      // 40f7: ldc_w -2.138
      // 40fa: ldc_w -4.858
      // 40fd: ldc_w -3.876
      // 4100: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4103: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4106: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4109: aastore
      // 410a: dup
      // 410b: bipush 29
      // 410d: new net/minecraft/client/animation/Keyframe
      // 4110: dup
      // 4111: ldc_w 1.96
      // 4114: ldc_w -2.186
      // 4117: ldc_w -4.971
      // 411a: ldc_w -3.768
      // 411d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4120: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4123: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4126: aastore
      // 4127: dup
      // 4128: bipush 30
      // 412a: new net/minecraft/client/animation/Keyframe
      // 412d: dup
      // 412e: ldc_w 2.04
      // 4131: ldc_w -2.298
      // 4134: ldc_w -5.272
      // 4137: ldc_w -3.582
      // 413a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 413d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4140: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4143: aastore
      // 4144: dup
      // 4145: bipush 31
      // 4147: new net/minecraft/client/animation/Keyframe
      // 414a: dup
      // 414b: ldc_w 2.08
      // 414e: ldc_w -2.359
      // 4151: ldc_w -5.448
      // 4154: ldc_w -3.512
      // 4157: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 415a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 415d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4160: aastore
      // 4161: dup
      // 4162: bipush 32
      // 4164: new net/minecraft/client/animation/Keyframe
      // 4167: dup
      // 4168: ldc_w 2.12
      // 416b: ldc_w -2.398
      // 416e: ldc_w -5.559
      // 4171: ldc_w -3.465
      // 4174: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4177: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 417a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 417d: aastore
      // 417e: dup
      // 417f: bipush 33
      // 4181: new net/minecraft/client/animation/Keyframe
      // 4184: dup
      // 4185: ldc_w 2.16
      // 4188: ldc_w -2.396
      // 418b: ldc_w -5.526
      // 418e: ldc_w -3.438
      // 4191: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4194: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4197: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 419a: aastore
      // 419b: dup
      // 419c: bipush 34
      // 419e: new net/minecraft/client/animation/Keyframe
      // 41a1: dup
      // 41a2: ldc_w 2.21
      // 41a5: ldc_w -2.357
      // 41a8: ldc_w -5.363
      // 41ab: ldc_w -3.414
      // 41ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 41b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41b7: aastore
      // 41b8: dup
      // 41b9: bipush 35
      // 41bb: new net/minecraft/client/animation/Keyframe
      // 41be: dup
      // 41bf: ldc_w 2.29
      // 41c2: ldc_w -2.234
      // 41c5: ldc_w -4.841
      // 41c8: ldc_w -3.348
      // 41cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 41ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41d4: aastore
      // 41d5: dup
      // 41d6: bipush 36
      // 41d8: new net/minecraft/client/animation/Keyframe
      // 41db: dup
      // 41dc: ldc_w 2.33
      // 41df: ldc_w -2.185
      // 41e2: ldc_w -4.63
      // 41e5: ldc_w -3.316
      // 41e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 41eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41f1: aastore
      // 41f2: dup
      // 41f3: bipush 37
      // 41f5: new net/minecraft/client/animation/Keyframe
      // 41f8: dup
      // 41f9: ldc 2.37
      // 41fb: ldc_w -2.167
      // 41fe: ldc_w -4.549
      // 4201: ldc_w -3.303
      // 4204: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4207: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 420a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 420d: aastore
      // 420e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4211: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4214: ldc_w "CTR_leg_right.002"
      // 4217: new net/minecraft/client/animation/AnimationChannel
      // 421a: dup
      // 421b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 421e: bipush 42
      // 4220: anewarray 28
      // 4223: dup
      // 4224: bipush 0
      // 4225: new net/minecraft/client/animation/Keyframe
      // 4228: dup
      // 4229: fconst_0
      // 422a: ldc_w -2.884
      // 422d: ldc_w 0.86
      // 4230: ldc_w -2.239
      // 4233: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4236: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4239: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 423c: aastore
      // 423d: dup
      // 423e: bipush 1
      // 423f: new net/minecraft/client/animation/Keyframe
      // 4242: dup
      // 4243: ldc 0.04
      // 4245: ldc_w -3.142
      // 4248: ldc_w 3.605
      // 424b: ldc_w -1.825
      // 424e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4251: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4254: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4257: aastore
      // 4258: dup
      // 4259: bipush 2
      // 425a: new net/minecraft/client/animation/Keyframe
      // 425d: dup
      // 425e: ldc 0.08
      // 4260: ldc_w -3.843
      // 4263: ldc_w 8.133
      // 4266: ldc_w -0.7
      // 4269: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 426c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 426f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4272: aastore
      // 4273: dup
      // 4274: bipush 3
      // 4275: new net/minecraft/client/animation/Keyframe
      // 4278: dup
      // 4279: ldc 0.12
      // 427b: ldc_w -4.876
      // 427e: ldc_w 13.716
      // 4281: ldc_w 0.956
      // 4284: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4287: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 428a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 428d: aastore
      // 428e: dup
      // 428f: bipush 4
      // 4290: new net/minecraft/client/animation/Keyframe
      // 4293: dup
      // 4294: ldc 0.17
      // 4296: ldc_w -6.13
      // 4299: ldc_w 19.327
      // 429c: ldc_w 2.968
      // 429f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42a8: aastore
      // 42a9: dup
      // 42aa: bipush 5
      // 42ab: new net/minecraft/client/animation/Keyframe
      // 42ae: dup
      // 42af: ldc 0.21
      // 42b1: ldc_w -7.494
      // 42b4: ldc_w 24.207
      // 42b7: ldc_w 5.157
      // 42ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42c3: aastore
      // 42c4: dup
      // 42c5: bipush 6
      // 42c7: new net/minecraft/client/animation/Keyframe
      // 42ca: dup
      // 42cb: ldc 0.25
      // 42cd: ldc_w -8.859
      // 42d0: ldc_w 28.05
      // 42d3: ldc_w 7.346
      // 42d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42df: aastore
      // 42e0: dup
      // 42e1: bipush 7
      // 42e3: new net/minecraft/client/animation/Keyframe
      // 42e6: dup
      // 42e7: ldc 0.29
      // 42e9: ldc_w -10.113
      // 42ec: ldc_w 30.842
      // 42ef: ldc_w 9.357
      // 42f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42fb: aastore
      // 42fc: dup
      // 42fd: bipush 8
      // 42ff: new net/minecraft/client/animation/Keyframe
      // 4302: dup
      // 4303: ldc 0.33
      // 4305: ldc_w -11.146
      // 4308: ldc_w 32.684
      // 430b: ldc_w 11.014
      // 430e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4311: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4314: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4317: aastore
      // 4318: dup
      // 4319: bipush 9
      // 431b: new net/minecraft/client/animation/Keyframe
      // 431e: dup
      // 431f: ldc 0.38
      // 4321: ldc_w -11.847
      // 4324: ldc_w 33.703
      // 4327: ldc_w 12.138
      // 432a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 432d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4330: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4333: aastore
      // 4334: dup
      // 4335: bipush 10
      // 4337: new net/minecraft/client/animation/Keyframe
      // 433a: dup
      // 433b: ldc 0.42
      // 433d: ldc_w -12.105
      // 4340: ldc_w 34.019
      // 4343: ldc_w 12.552
      // 4346: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4349: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 434c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 434f: aastore
      // 4350: dup
      // 4351: bipush 11
      // 4353: new net/minecraft/client/animation/Keyframe
      // 4356: dup
      // 4357: ldc 0.46
      // 4359: ldc_w -12.105
      // 435c: ldc_w 33.943
      // 435f: ldc_w 12.551
      // 4362: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4365: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4368: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 436b: aastore
      // 436c: dup
      // 436d: bipush 12
      // 436f: new net/minecraft/client/animation/Keyframe
      // 4372: dup
      // 4373: ldc 0.54
      // 4375: ldc_w -12.099
      // 4378: ldc_w 33.699
      // 437b: ldc_w 12.536
      // 437e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4381: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4384: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4387: aastore
      // 4388: dup
      // 4389: bipush 13
      // 438b: new net/minecraft/client/animation/Keyframe
      // 438e: dup
      // 438f: ldc 0.62
      // 4391: ldc_w -12.076
      // 4394: ldc_w 33.408
      // 4397: ldc_w 12.479
      // 439a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 439d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43a3: aastore
      // 43a4: dup
      // 43a5: bipush 14
      // 43a7: new net/minecraft/client/animation/Keyframe
      // 43aa: dup
      // 43ab: ldc_w 0.67
      // 43ae: ldc_w -12.054
      // 43b1: ldc_w 33.25
      // 43b4: ldc_w 12.425
      // 43b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43c0: aastore
      // 43c1: dup
      // 43c2: bipush 15
      // 43c4: new net/minecraft/client/animation/Keyframe
      // 43c7: dup
      // 43c8: ldc 0.71
      // 43ca: ldc_w -12.024
      // 43cd: ldc_w 33.082
      // 43d0: ldc_w 12.351
      // 43d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43dc: aastore
      // 43dd: dup
      // 43de: bipush 16
      // 43e0: new net/minecraft/client/animation/Keyframe
      // 43e3: dup
      // 43e4: ldc 0.75
      // 43e6: ldc_w -11.985
      // 43e9: ldc_w 32.906
      // 43ec: ldc_w 12.252
      // 43ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43f8: aastore
      // 43f9: dup
      // 43fa: bipush 17
      // 43fc: new net/minecraft/client/animation/Keyframe
      // 43ff: dup
      // 4400: ldc 0.79
      // 4402: ldc_w -11.933
      // 4405: ldc_w 32.721
      // 4408: ldc_w 12.125
      // 440b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 440e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4411: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4414: aastore
      // 4415: dup
      // 4416: bipush 18
      // 4418: new net/minecraft/client/animation/Keyframe
      // 441b: dup
      // 441c: ldc 0.83
      // 441e: ldc_w -11.87
      // 4421: ldc_w 32.527
      // 4424: ldc_w 11.966
      // 4427: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 442a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 442d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4430: aastore
      // 4431: dup
      // 4432: bipush 19
      // 4434: new net/minecraft/client/animation/Keyframe
      // 4437: dup
      // 4438: ldc 0.88
      // 443a: ldc_w -11.792
      // 443d: ldc_w 32.323
      // 4440: ldc_w 11.772
      // 4443: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4446: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4449: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 444c: aastore
      // 444d: dup
      // 444e: bipush 20
      // 4450: new net/minecraft/client/animation/Keyframe
      // 4453: dup
      // 4454: ldc 0.92
      // 4456: ldc_w -11.698
      // 4459: ldc_w 32.108
      // 445c: ldc_w 11.54
      // 445f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4462: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4465: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4468: aastore
      // 4469: dup
      // 446a: bipush 21
      // 446c: new net/minecraft/client/animation/Keyframe
      // 446f: dup
      // 4470: ldc 0.96
      // 4472: ldc_w -11.587
      // 4475: ldc_w 31.88
      // 4478: ldc_w 11.265
      // 447b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 447e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4481: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4484: aastore
      // 4485: dup
      // 4486: bipush 22
      // 4488: new net/minecraft/client/animation/Keyframe
      // 448b: dup
      // 448c: fconst_1
      // 448d: ldc_w -11.458
      // 4490: ldc_w 31.639
      // 4493: ldc_w 10.945
      // 4496: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4499: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 449c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 449f: aastore
      // 44a0: dup
      // 44a1: bipush 23
      // 44a3: new net/minecraft/client/animation/Keyframe
      // 44a6: dup
      // 44a7: ldc 1.04
      // 44a9: ldc_w -11.31
      // 44ac: ldc_w 31.383
      // 44af: ldc_w 10.575
      // 44b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44bb: aastore
      // 44bc: dup
      // 44bd: bipush 24
      // 44bf: new net/minecraft/client/animation/Keyframe
      // 44c2: dup
      // 44c3: ldc 1.08
      // 44c5: ldc_w -11.14
      // 44c8: ldc_w 31.108
      // 44cb: ldc_w 10.153
      // 44ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44d7: aastore
      // 44d8: dup
      // 44d9: bipush 25
      // 44db: new net/minecraft/client/animation/Keyframe
      // 44de: dup
      // 44df: ldc 1.12
      // 44e1: ldc_w -10.947
      // 44e4: ldc_w 30.811
      // 44e7: ldc_w 9.674
      // 44ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44f3: aastore
      // 44f4: dup
      // 44f5: bipush 26
      // 44f7: new net/minecraft/client/animation/Keyframe
      // 44fa: dup
      // 44fb: ldc 1.17
      // 44fd: ldc_w -10.731
      // 4500: ldc_w 30.487
      // 4503: ldc_w 9.136
      // 4506: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4509: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 450c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 450f: aastore
      // 4510: dup
      // 4511: bipush 27
      // 4513: new net/minecraft/client/animation/Keyframe
      // 4516: dup
      // 4517: ldc 1.21
      // 4519: ldc_w -10.488
      // 451c: ldc_w 30.128
      // 451f: ldc_w 8.534
      // 4522: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4525: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4528: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 452b: aastore
      // 452c: dup
      // 452d: bipush 28
      // 452f: new net/minecraft/client/animation/Keyframe
      // 4532: dup
      // 4533: ldc 1.25
      // 4535: ldc_w -10.218
      // 4538: ldc_w 29.726
      // 453b: ldc_w 7.821
      // 453e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4541: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4544: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4547: aastore
      // 4548: dup
      // 4549: bipush 29
      // 454b: new net/minecraft/client/animation/Keyframe
      // 454e: dup
      // 454f: ldc 1.29
      // 4551: ldc_w -9.921
      // 4554: ldc_w 29.287
      // 4557: ldc_w 6.97
      // 455a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 455d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4560: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4563: aastore
      // 4564: dup
      // 4565: bipush 30
      // 4567: new net/minecraft/client/animation/Keyframe
      // 456a: dup
      // 456b: ldc 1.33
      // 456d: ldc_w -9.605
      // 4570: ldc_w 28.819
      // 4573: ldc_w 6.007
      // 4576: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4579: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 457c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 457f: aastore
      // 4580: dup
      // 4581: bipush 31
      // 4583: new net/minecraft/client/animation/Keyframe
      // 4586: dup
      // 4587: ldc 1.38
      // 4589: ldc_w -9.276
      // 458c: ldc_w 28.333
      // 458f: ldc_w 4.962
      // 4592: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 459b: aastore
      // 459c: dup
      // 459d: bipush 32
      // 459f: new net/minecraft/client/animation/Keyframe
      // 45a2: dup
      // 45a3: ldc 1.42
      // 45a5: ldc_w -8.941
      // 45a8: ldc_w 27.839
      // 45ab: ldc_w 3.861
      // 45ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b7: aastore
      // 45b8: dup
      // 45b9: bipush 33
      // 45bb: new net/minecraft/client/animation/Keyframe
      // 45be: dup
      // 45bf: ldc_w 1.5
      // 45c2: ldc_w -8.278
      // 45c5: ldc_w 26.864
      // 45c8: ldc_w 1.605
      // 45cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45d4: aastore
      // 45d5: dup
      // 45d6: bipush 34
      // 45d8: new net/minecraft/client/animation/Keyframe
      // 45db: dup
      // 45dc: ldc 1.54
      // 45de: ldc_w -7.964
      // 45e1: ldc_w 26.402
      // 45e4: ldc_w 0.506
      // 45e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45f0: aastore
      // 45f1: dup
      // 45f2: bipush 35
      // 45f4: new net/minecraft/client/animation/Keyframe
      // 45f7: dup
      // 45f8: ldc 1.58
      // 45fa: ldc_w -7.671
      // 45fd: ldc_w 25.97
      // 4600: ldc_w -0.537
      // 4603: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4606: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4609: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 460c: aastore
      // 460d: dup
      // 460e: bipush 36
      // 4610: new net/minecraft/client/animation/Keyframe
      // 4613: dup
      // 4614: ldc 1.62
      // 4616: ldc_w -7.404
      // 4619: ldc_w 25.579
      // 461c: ldc_w -1.495
      // 461f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4622: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4625: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4628: aastore
      // 4629: dup
      // 462a: bipush 37
      // 462c: new net/minecraft/client/animation/Keyframe
      // 462f: dup
      // 4630: ldc 1.67
      // 4632: ldc_w -7.171
      // 4635: ldc_w 25.236
      // 4638: ldc_w -2.342
      // 463b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 463e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4641: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4644: aastore
      // 4645: dup
      // 4646: bipush 38
      // 4648: new net/minecraft/client/animation/Keyframe
      // 464b: dup
      // 464c: ldc 1.71
      // 464e: ldc_w -6.978
      // 4651: ldc_w 24.953
      // 4654: ldc_w -3.05
      // 4657: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 465a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 465d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4660: aastore
      // 4661: dup
      // 4662: bipush 39
      // 4664: new net/minecraft/client/animation/Keyframe
      // 4667: dup
      // 4668: ldc 1.75
      // 466a: ldc_w -6.832
      // 466d: ldc_w 24.739
      // 4670: ldc_w -3.589
      // 4673: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4676: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4679: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 467c: aastore
      // 467d: dup
      // 467e: bipush 40
      // 4680: new net/minecraft/client/animation/Keyframe
      // 4683: dup
      // 4684: ldc 1.79
      // 4686: ldc_w -6.74
      // 4689: ldc_w 24.604
      // 468c: ldc_w -3.933
      // 468f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4692: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4695: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4698: aastore
      // 4699: dup
      // 469a: bipush 41
      // 469c: new net/minecraft/client/animation/Keyframe
      // 469f: dup
      // 46a0: ldc 1.83
      // 46a2: ldc_w -6.708
      // 46a5: ldc_w 24.556
      // 46a8: ldc_w -4.054
      // 46ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 46ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b4: aastore
      // 46b5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 46b8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 46bb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 46be: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiLeapInAir.LEAP_IN_AIR Lnet/minecraft/client/animation/AnimationDefinition;
      // 46c1: return
   }
}
