package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiGroundSmash {
   public static final AnimationDefinition GROUND_SMASH;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 2.21
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "CTR_leg_left.002"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 7
      // 0013: anewarray 28
      // 0016: dup
      // 0017: bipush 0
      // 0018: new net/minecraft/client/animation/Keyframe
      // 001b: dup
      // 001c: ldc 0.33
      // 001e: ldc 2.113
      // 0020: fconst_0
      // 0021: ldc -13.058
      // 0023: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 002c: aastore
      // 002d: dup
      // 002e: bipush 1
      // 002f: new net/minecraft/client/animation/Keyframe
      // 0032: dup
      // 0033: ldc 0.38
      // 0035: ldc 2.113
      // 0037: fconst_0
      // 0038: ldc -9.494
      // 003a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0040: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0043: aastore
      // 0044: dup
      // 0045: bipush 2
      // 0046: new net/minecraft/client/animation/Keyframe
      // 0049: dup
      // 004a: ldc 0.42
      // 004c: ldc 2.113
      // 004e: fconst_0
      // 004f: ldc -2.875
      // 0051: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0054: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0057: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 005a: aastore
      // 005b: dup
      // 005c: bipush 3
      // 005d: new net/minecraft/client/animation/Keyframe
      // 0060: dup
      // 0061: ldc 0.46
      // 0063: ldc 2.113
      // 0065: fconst_0
      // 0066: ldc 0.689
      // 0068: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 006e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0071: aastore
      // 0072: dup
      // 0073: bipush 4
      // 0074: new net/minecraft/client/animation/Keyframe
      // 0077: dup
      // 0078: ldc 1.12
      // 007a: ldc 2.113
      // 007c: fconst_0
      // 007d: ldc 0.689
      // 007f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0082: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0085: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0088: aastore
      // 0089: dup
      // 008a: bipush 5
      // 008b: new net/minecraft/client/animation/Keyframe
      // 008e: dup
      // 008f: ldc 1.17
      // 0091: ldc 2.113
      // 0093: fconst_0
      // 0094: ldc -6.185
      // 0096: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0099: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 009f: aastore
      // 00a0: dup
      // 00a1: bipush 6
      // 00a3: new net/minecraft/client/animation/Keyframe
      // 00a6: dup
      // 00a7: ldc 1.21
      // 00a9: ldc 2.113
      // 00ab: fconst_0
      // 00ac: ldc -13.058
      // 00ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b7: aastore
      // 00b8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 00bb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 00be: ldc "CTR_hips"
      // 00c0: new net/minecraft/client/animation/AnimationChannel
      // 00c3: dup
      // 00c4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 00c7: bipush 47
      // 00c9: anewarray 28
      // 00cc: dup
      // 00cd: bipush 0
      // 00ce: new net/minecraft/client/animation/Keyframe
      // 00d1: dup
      // 00d2: fconst_0
      // 00d3: ldc -0.727
      // 00d5: ldc 0.028
      // 00d7: ldc -4.943
      // 00d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e2: aastore
      // 00e3: dup
      // 00e4: bipush 1
      // 00e5: new net/minecraft/client/animation/Keyframe
      // 00e8: dup
      // 00e9: ldc 0.04
      // 00eb: ldc -0.842
      // 00ed: ldc -0.16
      // 00ef: ldc -4.998
      // 00f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00fa: aastore
      // 00fb: dup
      // 00fc: bipush 2
      // 00fd: new net/minecraft/client/animation/Keyframe
      // 0100: dup
      // 0101: ldc 0.08
      // 0103: ldc -1.213
      // 0105: ldc -0.83
      // 0107: ldc -5.177
      // 0109: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 010c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 010f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0112: aastore
      // 0113: dup
      // 0114: bipush 3
      // 0115: new net/minecraft/client/animation/Keyframe
      // 0118: dup
      // 0119: ldc 0.12
      // 011b: ldc -1.879
      // 011d: ldc -2.226
      // 011f: ldc -5.504
      // 0121: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0124: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0127: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 012a: aastore
      // 012b: dup
      // 012c: bipush 4
      // 012d: new net/minecraft/client/animation/Keyframe
      // 0130: dup
      // 0131: ldc 0.17
      // 0133: ldc -2.868
      // 0135: ldc -4.741
      // 0137: ldc -6.001
      // 0139: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 013c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 013f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0142: aastore
      // 0143: dup
      // 0144: bipush 5
      // 0145: new net/minecraft/client/animation/Keyframe
      // 0148: dup
      // 0149: ldc 0.21
      // 014b: ldc -4.177
      // 014d: ldc -8.686
      // 014f: ldc -6.69
      // 0151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015a: aastore
      // 015b: dup
      // 015c: bipush 6
      // 015e: new net/minecraft/client/animation/Keyframe
      // 0161: dup
      // 0162: ldc 0.25
      // 0164: ldc -5.799
      // 0166: ldc -13.074
      // 0168: ldc -7.57
      // 016a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 016d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0173: aastore
      // 0174: dup
      // 0175: bipush 7
      // 0177: new net/minecraft/client/animation/Keyframe
      // 017a: dup
      // 017b: ldc 0.29
      // 017d: ldc -7.738
      // 017f: ldc -16.321
      // 0181: ldc -8.553
      // 0183: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0186: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0189: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 018c: aastore
      // 018d: dup
      // 018e: bipush 8
      // 0190: new net/minecraft/client/animation/Keyframe
      // 0193: dup
      // 0194: ldc 0.33
      // 0196: ldc -9.851
      // 0198: ldc -18.336
      // 019a: ldc -9.434
      // 019c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 019f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a5: aastore
      // 01a6: dup
      // 01a7: bipush 9
      // 01a9: new net/minecraft/client/animation/Keyframe
      // 01ac: dup
      // 01ad: ldc 0.38
      // 01af: ldc -11.836
      // 01b1: ldc -19.513
      // 01b3: ldc -10.011
      // 01b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01be: aastore
      // 01bf: dup
      // 01c0: bipush 10
      // 01c2: new net/minecraft/client/animation/Keyframe
      // 01c5: dup
      // 01c6: ldc 0.42
      // 01c8: ldc -13.42
      // 01ca: ldc -20.137
      // 01cc: ldc -10.212
      // 01ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d7: aastore
      // 01d8: dup
      // 01d9: bipush 11
      // 01db: new net/minecraft/client/animation/Keyframe
      // 01de: dup
      // 01df: ldc 0.46
      // 01e1: ldc -14.502
      // 01e3: ldc -20.378
      // 01e5: ldc -10.076
      // 01e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01f0: aastore
      // 01f1: dup
      // 01f2: bipush 12
      // 01f4: new net/minecraft/client/animation/Keyframe
      // 01f7: dup
      // 01f8: ldc 0.5
      // 01fa: ldc -15.188
      // 01fc: ldc -20.449
      // 01fe: ldc -9.846
      // 0200: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0203: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0206: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0209: aastore
      // 020a: dup
      // 020b: bipush 13
      // 020d: new net/minecraft/client/animation/Keyframe
      // 0210: dup
      // 0211: ldc 0.54
      // 0213: ldc -15.621
      // 0215: ldc -20.488
      // 0217: ldc -9.701
      // 0219: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 021c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 021f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0222: aastore
      // 0223: dup
      // 0224: bipush 14
      // 0226: new net/minecraft/client/animation/Keyframe
      // 0229: dup
      // 022a: ldc 0.58
      // 022c: ldc -15.852
      // 022e: ldc -20.5
      // 0230: ldc -9.625
      // 0232: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0235: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0238: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 023b: aastore
      // 023c: dup
      // 023d: bipush 15
      // 023f: new net/minecraft/client/animation/Keyframe
      // 0242: dup
      // 0243: ldc 0.62
      // 0245: ldc -15.923
      // 0247: ldc -20.487
      // 0249: ldc -9.606
      // 024b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 024e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0251: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0254: aastore
      // 0255: dup
      // 0256: bipush 16
      // 0258: new net/minecraft/client/animation/Keyframe
      // 025b: dup
      // 025c: ldc 0.67
      // 025e: ldc -15.865
      // 0260: ldc -20.455
      // 0262: ldc -9.632
      // 0264: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0267: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 026a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 026d: aastore
      // 026e: dup
      // 026f: bipush 17
      // 0271: new net/minecraft/client/animation/Keyframe
      // 0274: dup
      // 0275: ldc 0.75
      // 0277: ldc -15.473
      // 0279: ldc -20.358
      // 027b: ldc -9.783
      // 027d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0280: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0283: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0286: aastore
      // 0287: dup
      // 0288: bipush 18
      // 028a: new net/minecraft/client/animation/Keyframe
      // 028d: dup
      // 028e: ldc 0.96
      // 0290: ldc -13.96
      // 0292: ldc -20.198
      // 0294: ldc -10.315
      // 0296: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0299: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 029c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 029f: aastore
      // 02a0: dup
      // 02a1: bipush 19
      // 02a3: new net/minecraft/client/animation/Keyframe
      // 02a6: dup
      // 02a7: fconst_1
      // 02a8: ldc -13.783
      // 02aa: ldc -20.212
      // 02ac: ldc -10.365
      // 02ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02b7: aastore
      // 02b8: dup
      // 02b9: bipush 20
      // 02bb: new net/minecraft/client/animation/Keyframe
      // 02be: dup
      // 02bf: ldc 1.04
      // 02c1: ldc -13.747
      // 02c3: ldc -20.246
      // 02c5: ldc -10.362
      // 02c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02d0: aastore
      // 02d1: dup
      // 02d2: bipush 21
      // 02d4: new net/minecraft/client/animation/Keyframe
      // 02d7: dup
      // 02d8: ldc 1.08
      // 02da: ldc -13.937
      // 02dc: ldc -20.301
      // 02de: ldc -10.278
      // 02e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e9: aastore
      // 02ea: dup
      // 02eb: bipush 22
      // 02ed: new net/minecraft/client/animation/Keyframe
      // 02f0: dup
      // 02f1: ldc 1.12
      // 02f3: ldc -14.502
      // 02f5: ldc -20.378
      // 02f7: ldc -10.076
      // 02f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0302: aastore
      // 0303: dup
      // 0304: bipush 23
      // 0306: new net/minecraft/client/animation/Keyframe
      // 0309: dup
      // 030a: ldc 1.17
      // 030c: ldc -14.122
      // 030e: ldc -19.562
      // 0310: ldc -9.616
      // 0312: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0315: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0318: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 031b: aastore
      // 031c: dup
      // 031d: bipush 24
      // 031f: new net/minecraft/client/animation/Keyframe
      // 0322: dup
      // 0323: ldc 1.21
      // 0325: ldc -9.208
      // 0327: ldc -14.922
      // 0329: ldc -8.465
      // 032b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 032e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0331: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0334: aastore
      // 0335: dup
      // 0336: bipush 25
      // 0338: new net/minecraft/client/animation/Keyframe
      // 033b: dup
      // 033c: ldc 1.25
      // 033e: ldc 26.583
      // 0340: ldc 1.593
      // 0342: ldc -4.893
      // 0344: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0347: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 034a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 034d: aastore
      // 034e: dup
      // 034f: bipush 26
      // 0351: new net/minecraft/client/animation/Keyframe
      // 0354: dup
      // 0355: ldc 1.29
      // 0357: ldc 33.345
      // 0359: ldc 3.29
      // 035b: ldc -4.355
      // 035d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0360: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0363: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0366: aastore
      // 0367: dup
      // 0368: bipush 27
      // 036a: new net/minecraft/client/animation/Keyframe
      // 036d: dup
      // 036e: ldc 1.33
      // 0370: ldc 32.302
      // 0372: ldc 2.75
      // 0374: ldc -4.546
      // 0376: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0379: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 037c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 037f: aastore
      // 0380: dup
      // 0381: bipush 28
      // 0383: new net/minecraft/client/animation/Keyframe
      // 0386: dup
      // 0387: ldc 1.38
      // 0389: ldc 30.159
      // 038b: ldc 2.746
      // 038d: ldc -4.546
      // 038f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0392: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0395: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0398: aastore
      // 0399: dup
      // 039a: bipush 29
      // 039c: new net/minecraft/client/animation/Keyframe
      // 039f: dup
      // 03a0: ldc 1.42
      // 03a2: ldc 29.126
      // 03a4: ldc 2.817
      // 03a6: ldc -4.522
      // 03a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03b1: aastore
      // 03b2: dup
      // 03b3: bipush 30
      // 03b5: new net/minecraft/client/animation/Keyframe
      // 03b8: dup
      // 03b9: ldc 1.46
      // 03bb: ldc 29.391
      // 03bd: ldc 2.892
      // 03bf: ldc -4.498
      // 03c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03ca: aastore
      // 03cb: dup
      // 03cc: bipush 31
      // 03ce: new net/minecraft/client/animation/Keyframe
      // 03d1: dup
      // 03d2: ldc 1.5
      // 03d4: ldc 30.173
      // 03d6: ldc 2.959
      // 03d8: ldc -4.476
      // 03da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03e3: aastore
      // 03e4: dup
      // 03e5: bipush 32
      // 03e7: new net/minecraft/client/animation/Keyframe
      // 03ea: dup
      // 03eb: ldc 1.54
      // 03ed: ldc 30.627
      // 03ef: ldc 3.012
      // 03f1: ldc -4.459
      // 03f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03fc: aastore
      // 03fd: dup
      // 03fe: bipush 33
      // 0400: new net/minecraft/client/animation/Keyframe
      // 0403: dup
      // 0404: ldc 1.58
      // 0406: ldc 30.577
      // 0408: ldc 3.037
      // 040a: ldc -4.451
      // 040c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 040f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0412: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0415: aastore
      // 0416: dup
      // 0417: bipush 34
      // 0419: new net/minecraft/client/animation/Keyframe
      // 041c: dup
      // 041d: ldc 1.62
      // 041f: ldc 30.326
      // 0421: ldc 3.009
      // 0423: ldc -4.461
      // 0425: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0428: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 042b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 042e: aastore
      // 042f: dup
      // 0430: bipush 35
      // 0432: new net/minecraft/client/animation/Keyframe
      // 0435: dup
      // 0436: ldc 1.67
      // 0438: ldc 30.159
      // 043a: ldc 2.75
      // 043c: ldc -4.546
      // 043e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0441: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0444: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0447: aastore
      // 0448: dup
      // 0449: bipush 36
      // 044b: new net/minecraft/client/animation/Keyframe
      // 044e: dup
      // 044f: ldc 1.71
      // 0451: ldc 28.71
      // 0453: ldc 2.303
      // 0455: ldc -4.627
      // 0457: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 045a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 045d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0460: aastore
      // 0461: dup
      // 0462: bipush 37
      // 0464: new net/minecraft/client/animation/Keyframe
      // 0467: dup
      // 0468: ldc 1.75
      // 046a: ldc 25.545
      // 046c: ldc 1.927
      // 046e: ldc -4.683
      // 0470: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0473: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0476: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0479: aastore
      // 047a: dup
      // 047b: bipush 38
      // 047d: new net/minecraft/client/animation/Keyframe
      // 0480: dup
      // 0481: ldc 1.79
      // 0483: ldc 21.57
      // 0485: ldc 1.578
      // 0487: ldc -4.733
      // 0489: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 048c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 048f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0492: aastore
      // 0493: dup
      // 0494: bipush 39
      // 0496: new net/minecraft/client/animation/Keyframe
      // 0499: dup
      // 049a: ldc 1.83
      // 049c: ldc 17.249
      // 049e: ldc 1.253
      // 04a0: ldc -4.778
      // 04a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04ab: aastore
      // 04ac: dup
      // 04ad: bipush 40
      // 04af: new net/minecraft/client/animation/Keyframe
      // 04b2: dup
      // 04b3: ldc 1.88
      // 04b5: ldc 12.886
      // 04b7: ldc 0.952
      // 04b9: ldc -4.82
      // 04bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04c4: aastore
      // 04c5: dup
      // 04c6: bipush 41
      // 04c8: new net/minecraft/client/animation/Keyframe
      // 04cb: dup
      // 04cc: ldc 1.92
      // 04ce: ldc 8.72
      // 04d0: ldc 0.678
      // 04d2: ldc -4.857
      // 04d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04dd: aastore
      // 04de: dup
      // 04df: bipush 42
      // 04e1: new net/minecraft/client/animation/Keyframe
      // 04e4: dup
      // 04e5: ldc 1.96
      // 04e7: ldc 4.973
      // 04e9: ldc 0.436
      // 04eb: ldc -4.889
      // 04ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04f6: aastore
      // 04f7: dup
      // 04f8: bipush 43
      // 04fa: new net/minecraft/client/animation/Keyframe
      // 04fd: dup
      // 04fe: fconst_2
      // 04ff: ldc 1.879
      // 0501: ldc 0.235
      // 0503: ldc -4.916
      // 0505: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0508: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 050b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 050e: aastore
      // 050f: dup
      // 0510: bipush 44
      // 0512: new net/minecraft/client/animation/Keyframe
      // 0515: dup
      // 0516: ldc 2.04
      // 0518: ldc -0.277
      // 051a: ldc 0.089
      // 051c: ldc -4.935
      // 051e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0521: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0524: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0527: aastore
      // 0528: dup
      // 0529: bipush 45
      // 052b: new net/minecraft/client/animation/Keyframe
      // 052e: dup
      // 052f: ldc 2.08
      // 0531: ldc -1.108
      // 0533: ldc 0.028
      // 0535: ldc -4.943
      // 0537: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 053a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 053d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0540: aastore
      // 0541: dup
      // 0542: bipush 46
      // 0544: new net/minecraft/client/animation/Keyframe
      // 0547: dup
      // 0548: ldc 2.21
      // 054a: ldc -0.727
      // 054c: ldc 0.028
      // 054e: ldc -4.943
      // 0550: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0553: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0556: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0559: aastore
      // 055a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 055d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0560: ldc "CTR_hips"
      // 0562: new net/minecraft/client/animation/AnimationChannel
      // 0565: dup
      // 0566: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0569: bipush 46
      // 056b: anewarray 28
      // 056e: dup
      // 056f: bipush 0
      // 0570: new net/minecraft/client/animation/Keyframe
      // 0573: dup
      // 0574: fconst_0
      // 0575: fconst_0
      // 0576: fconst_0
      // 0577: fconst_0
      // 0578: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 057b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 057e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0581: aastore
      // 0582: dup
      // 0583: bipush 1
      // 0584: new net/minecraft/client/animation/Keyframe
      // 0587: dup
      // 0588: ldc 0.04
      // 058a: ldc -0.003
      // 058c: ldc 0.015
      // 058e: ldc 0.079
      // 0590: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0593: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0596: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0599: aastore
      // 059a: dup
      // 059b: bipush 2
      // 059c: new net/minecraft/client/animation/Keyframe
      // 059f: dup
      // 05a0: ldc 0.08
      // 05a2: ldc -0.013
      // 05a4: ldc 0.066
      // 05a6: ldc 0.336
      // 05a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05b1: aastore
      // 05b2: dup
      // 05b3: bipush 3
      // 05b4: new net/minecraft/client/animation/Keyframe
      // 05b7: dup
      // 05b8: ldc 0.12
      // 05ba: ldc -0.03
      // 05bc: ldc 0.158
      // 05be: ldc 0.802
      // 05c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05c9: aastore
      // 05ca: dup
      // 05cb: bipush 4
      // 05cc: new net/minecraft/client/animation/Keyframe
      // 05cf: dup
      // 05d0: ldc 0.17
      // 05d2: ldc -0.057
      // 05d4: ldc 0.302
      // 05d6: ldc 1.51
      // 05d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05e1: aastore
      // 05e2: dup
      // 05e3: bipush 5
      // 05e4: new net/minecraft/client/animation/Keyframe
      // 05e7: dup
      // 05e8: ldc 0.21
      // 05ea: ldc -0.094
      // 05ec: ldc 0.506
      // 05ee: ldc 2.489
      // 05f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05f9: aastore
      // 05fa: dup
      // 05fb: bipush 6
      // 05fd: new net/minecraft/client/animation/Keyframe
      // 0600: dup
      // 0601: ldc 0.25
      // 0603: ldc -0.141
      // 0605: ldc 0.777
      // 0607: ldc 3.743
      // 0609: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 060c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 060f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0612: aastore
      // 0613: dup
      // 0614: bipush 7
      // 0616: new net/minecraft/client/animation/Keyframe
      // 0619: dup
      // 061a: ldc 0.29
      // 061c: ldc_w -0.196
      // 061f: ldc_w 1.109
      // 0622: ldc_w 5.196
      // 0625: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0628: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 062b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 062e: aastore
      // 062f: dup
      // 0630: bipush 8
      // 0632: new net/minecraft/client/animation/Keyframe
      // 0635: dup
      // 0636: ldc 0.33
      // 0638: ldc_w -0.251
      // 063b: ldc_w 1.471
      // 063e: ldc_w 6.648
      // 0641: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0644: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0647: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 064a: aastore
      // 064b: dup
      // 064c: bipush 9
      // 064e: new net/minecraft/client/animation/Keyframe
      // 0651: dup
      // 0652: ldc 0.38
      // 0654: ldc_w -0.297
      // 0657: ldc_w 1.81
      // 065a: ldc_w 7.815
      // 065d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0660: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0663: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0666: aastore
      // 0667: dup
      // 0668: bipush 10
      // 066a: new net/minecraft/client/animation/Keyframe
      // 066d: dup
      // 066e: ldc 0.42
      // 0670: ldc_w -0.325
      // 0673: ldc_w 2.082
      // 0676: ldc_w 8.495
      // 0679: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 067c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 067f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0682: aastore
      // 0683: dup
      // 0684: bipush 11
      // 0686: new net/minecraft/client/animation/Keyframe
      // 0689: dup
      // 068a: ldc 0.46
      // 068c: ldc_w -0.333
      // 068f: ldc_w 2.271
      // 0692: ldc_w 8.654
      // 0695: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0698: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 069b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 069e: aastore
      // 069f: dup
      // 06a0: bipush 12
      // 06a2: new net/minecraft/client/animation/Keyframe
      // 06a5: dup
      // 06a6: ldc 0.5
      // 06a8: ldc_w -0.333
      // 06ab: ldc_w 2.39
      // 06ae: ldc_w 8.585
      // 06b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06ba: aastore
      // 06bb: dup
      // 06bc: bipush 13
      // 06be: new net/minecraft/client/animation/Keyframe
      // 06c1: dup
      // 06c2: ldc 0.54
      // 06c4: ldc_w -0.333
      // 06c7: ldc_w 2.46
      // 06ca: ldc_w 8.542
      // 06cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06d6: aastore
      // 06d7: dup
      // 06d8: bipush 14
      // 06da: new net/minecraft/client/animation/Keyframe
      // 06dd: dup
      // 06de: ldc 0.58
      // 06e0: ldc_w -0.333
      // 06e3: ldc_w 2.495
      // 06e6: ldc_w 8.519
      // 06e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06f2: aastore
      // 06f3: dup
      // 06f4: bipush 15
      // 06f6: new net/minecraft/client/animation/Keyframe
      // 06f9: dup
      // 06fa: ldc 0.62
      // 06fc: ldc_w -0.333
      // 06ff: ldc_w 2.502
      // 0702: ldc_w 8.511
      // 0705: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0708: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 070b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 070e: aastore
      // 070f: dup
      // 0710: bipush 16
      // 0712: new net/minecraft/client/animation/Keyframe
      // 0715: dup
      // 0716: ldc 0.67
      // 0718: ldc_w -0.333
      // 071b: ldc_w 2.487
      // 071e: ldc_w 8.516
      // 0721: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0724: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0727: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 072a: aastore
      // 072b: dup
      // 072c: bipush 17
      // 072e: new net/minecraft/client/animation/Keyframe
      // 0731: dup
      // 0732: ldc_w 0.79
      // 0735: ldc_w -0.333
      // 0738: ldc_w 2.365
      // 073b: ldc_w 8.583
      // 073e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0741: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0744: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0747: aastore
      // 0748: dup
      // 0749: bipush 18
      // 074b: new net/minecraft/client/animation/Keyframe
      // 074e: dup
      // 074f: ldc 0.96
      // 0751: ldc_w -0.333
      // 0754: ldc_w 2.163
      // 0757: ldc_w 8.705
      // 075a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 075d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0760: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0763: aastore
      // 0764: dup
      // 0765: bipush 19
      // 0767: new net/minecraft/client/animation/Keyframe
      // 076a: dup
      // 076b: fconst_1
      // 076c: ldc_w -0.333
      // 076f: ldc_w 2.137
      // 0772: ldc_w 8.723
      // 0775: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0778: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 077b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 077e: aastore
      // 077f: dup
      // 0780: bipush 20
      // 0782: new net/minecraft/client/animation/Keyframe
      // 0785: dup
      // 0786: ldc 1.04
      // 0788: ldc_w -0.333
      // 078b: ldc_w 2.134
      // 078e: ldc_w 8.728
      // 0791: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0794: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0797: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 079a: aastore
      // 079b: dup
      // 079c: bipush 21
      // 079e: new net/minecraft/client/animation/Keyframe
      // 07a1: dup
      // 07a2: ldc 1.08
      // 07a4: ldc_w -0.333
      // 07a7: ldc_w 2.17
      // 07aa: ldc_w 8.709
      // 07ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07b6: aastore
      // 07b7: dup
      // 07b8: bipush 22
      // 07ba: new net/minecraft/client/animation/Keyframe
      // 07bd: dup
      // 07be: ldc 1.12
      // 07c0: ldc_w -0.333
      // 07c3: ldc_w 2.271
      // 07c6: ldc_w 8.654
      // 07c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07d2: aastore
      // 07d3: dup
      // 07d4: bipush 23
      // 07d6: new net/minecraft/client/animation/Keyframe
      // 07d9: dup
      // 07da: ldc 1.17
      // 07dc: ldc_w -0.171
      // 07df: ldc_w 2.471
      // 07e2: ldc_w 8.04
      // 07e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07ee: aastore
      // 07ef: dup
      // 07f0: bipush 24
      // 07f2: new net/minecraft/client/animation/Keyframe
      // 07f5: dup
      // 07f6: ldc 1.21
      // 07f8: ldc 0.17
      // 07fa: ldc_w 2.88
      // 07fd: ldc_w 5.357
      // 0800: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0803: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0806: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0809: aastore
      // 080a: dup
      // 080b: bipush 25
      // 080d: new net/minecraft/client/animation/Keyframe
      // 0810: dup
      // 0811: ldc 1.25
      // 0813: ldc_w 0.588
      // 0816: ldc_w 4.801
      // 0819: ldc_w -5.809
      // 081c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 081f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0822: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0825: aastore
      // 0826: dup
      // 0827: bipush 26
      // 0829: new net/minecraft/client/animation/Keyframe
      // 082c: dup
      // 082d: ldc 1.29
      // 082f: ldc 0.678
      // 0831: ldc_w 5.289
      // 0834: ldc_w -7.252
      // 0837: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 083a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 083d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0840: aastore
      // 0841: dup
      // 0842: bipush 27
      // 0844: new net/minecraft/client/animation/Keyframe
      // 0847: dup
      // 0848: ldc 1.33
      // 084a: ldc_w 0.637
      // 084d: ldc_w 5.077
      // 0850: ldc_w -6.523
      // 0853: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0856: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0859: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 085c: aastore
      // 085d: dup
      // 085e: bipush 28
      // 0860: new net/minecraft/client/animation/Keyframe
      // 0863: dup
      // 0864: ldc 1.38
      // 0866: ldc 0.62
      // 0868: ldc_w 4.983
      // 086b: ldc_w -6.235
      // 086e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0871: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0874: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0877: aastore
      // 0878: dup
      // 0879: bipush 29
      // 087b: new net/minecraft/client/animation/Keyframe
      // 087e: dup
      // 087f: ldc 1.42
      // 0881: ldc_w 0.623
      // 0884: ldc_w 5.008
      // 0887: ldc_w -6.256
      // 088a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 088d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0890: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0893: aastore
      // 0894: dup
      // 0895: bipush 30
      // 0897: new net/minecraft/client/animation/Keyframe
      // 089a: dup
      // 089b: ldc 1.46
      // 089d: ldc_w 0.637
      // 08a0: ldc_w 5.077
      // 08a3: ldc_w -6.523
      // 08a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08af: aastore
      // 08b0: dup
      // 08b1: bipush 31
      // 08b3: new net/minecraft/client/animation/Keyframe
      // 08b6: dup
      // 08b7: ldc 1.5
      // 08b9: ldc_w 0.643
      // 08bc: ldc_w 5.098
      // 08bf: ldc_w -6.677
      // 08c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08cb: aastore
      // 08cc: dup
      // 08cd: bipush 32
      // 08cf: new net/minecraft/client/animation/Keyframe
      // 08d2: dup
      // 08d3: ldc 1.54
      // 08d5: ldc_w 0.644
      // 08d8: ldc_w 5.095
      // 08db: ldc_w -6.672
      // 08de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08e7: aastore
      // 08e8: dup
      // 08e9: bipush 33
      // 08eb: new net/minecraft/client/animation/Keyframe
      // 08ee: dup
      // 08ef: ldc 1.62
      // 08f1: ldc_w 0.64
      // 08f4: ldc_w 5.08
      // 08f7: ldc_w -6.568
      // 08fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0900: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0903: aastore
      // 0904: dup
      // 0905: bipush 34
      // 0907: new net/minecraft/client/animation/Keyframe
      // 090a: dup
      // 090b: ldc 1.67
      // 090d: ldc_w 0.637
      // 0910: ldc_w 5.077
      // 0913: ldc_w -6.523
      // 0916: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0919: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 091c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 091f: aastore
      // 0920: dup
      // 0921: bipush 35
      // 0923: new net/minecraft/client/animation/Keyframe
      // 0926: dup
      // 0927: ldc 1.71
      // 0929: ldc_w 0.587
      // 092c: ldc_w 4.836
      // 092f: ldc_w -5.982
      // 0932: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 093b: aastore
      // 093c: dup
      // 093d: bipush 36
      // 093f: new net/minecraft/client/animation/Keyframe
      // 0942: dup
      // 0943: ldc 1.75
      // 0945: ldc_w 0.504
      // 0948: ldc_w 4.283
      // 094b: ldc_w -5.107
      // 094e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0951: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0954: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0957: aastore
      // 0958: dup
      // 0959: bipush 37
      // 095b: new net/minecraft/client/animation/Keyframe
      // 095e: dup
      // 095f: ldc 1.79
      // 0961: ldc_w 0.412
      // 0964: ldc_w 3.57
      // 0967: ldc_w -4.16
      // 096a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 096d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0970: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0973: aastore
      // 0974: dup
      // 0975: bipush 38
      // 0977: new net/minecraft/client/animation/Keyframe
      // 097a: dup
      // 097b: ldc 1.83
      // 097d: ldc_w 0.32
      // 0980: ldc_w 2.79
      // 0983: ldc_w -3.214
      // 0986: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0989: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 098c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 098f: aastore
      // 0990: dup
      // 0991: bipush 39
      // 0993: new net/minecraft/client/animation/Keyframe
      // 0996: dup
      // 0997: ldc 1.88
      // 0999: ldc_w 0.232
      // 099c: ldc_w 2.007
      // 099f: ldc_w -2.312
      // 09a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09ab: aastore
      // 09ac: dup
      // 09ad: bipush 40
      // 09af: new net/minecraft/client/animation/Keyframe
      // 09b2: dup
      // 09b3: ldc 1.92
      // 09b5: ldc_w 0.15
      // 09b8: ldc_w 1.277
      // 09bb: ldc_w -1.489
      // 09be: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09c7: aastore
      // 09c8: dup
      // 09c9: bipush 41
      // 09cb: new net/minecraft/client/animation/Keyframe
      // 09ce: dup
      // 09cf: ldc 1.96
      // 09d1: ldc_w 0.081
      // 09d4: ldc_w 0.655
      // 09d7: ldc_w -0.786
      // 09da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09e3: aastore
      // 09e4: dup
      // 09e5: bipush 42
      // 09e7: new net/minecraft/client/animation/Keyframe
      // 09ea: dup
      // 09eb: fconst_2
      // 09ec: ldc_w 0.027
      // 09ef: ldc_w 0.204
      // 09f2: ldc_w -0.258
      // 09f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09fe: aastore
      // 09ff: dup
      // 0a00: bipush 43
      // 0a02: new net/minecraft/client/animation/Keyframe
      // 0a05: dup
      // 0a06: ldc 2.04
      // 0a08: fconst_0
      // 0a09: fconst_0
      // 0a0a: fconst_0
      // 0a0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a14: aastore
      // 0a15: dup
      // 0a16: bipush 44
      // 0a18: new net/minecraft/client/animation/Keyframe
      // 0a1b: dup
      // 0a1c: ldc 2.08
      // 0a1e: ldc_w -0.006
      // 0a21: ldc_w -0.027
      // 0a24: ldc_w 0.041
      // 0a27: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a30: aastore
      // 0a31: dup
      // 0a32: bipush 45
      // 0a34: new net/minecraft/client/animation/Keyframe
      // 0a37: dup
      // 0a38: ldc 2.21
      // 0a3a: fconst_0
      // 0a3b: fconst_0
      // 0a3c: fconst_0
      // 0a3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a46: aastore
      // 0a47: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a4a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a4d: ldc_w "CTR_waist"
      // 0a50: new net/minecraft/client/animation/AnimationChannel
      // 0a53: dup
      // 0a54: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a57: bipush 19
      // 0a59: anewarray 28
      // 0a5c: dup
      // 0a5d: bipush 0
      // 0a5e: new net/minecraft/client/animation/Keyframe
      // 0a61: dup
      // 0a62: ldc 0.46
      // 0a64: fconst_0
      // 0a65: fconst_0
      // 0a66: fconst_0
      // 0a67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a70: aastore
      // 0a71: dup
      // 0a72: bipush 1
      // 0a73: new net/minecraft/client/animation/Keyframe
      // 0a76: dup
      // 0a77: ldc 0.5
      // 0a79: ldc_w -0.046
      // 0a7c: ldc_w 0.074
      // 0a7f: ldc_w -0.005
      // 0a82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a8b: aastore
      // 0a8c: dup
      // 0a8d: bipush 2
      // 0a8e: new net/minecraft/client/animation/Keyframe
      // 0a91: dup
      // 0a92: ldc 0.54
      // 0a94: ldc_w -0.176
      // 0a97: ldc_w 0.282
      // 0a9a: ldc_w -0.02
      // 0a9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0aa0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aa3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aa6: aastore
      // 0aa7: dup
      // 0aa8: bipush 3
      // 0aa9: new net/minecraft/client/animation/Keyframe
      // 0aac: dup
      // 0aad: ldc 0.67
      // 0aaf: ldc_w -0.951
      // 0ab2: ldc_w 1.523
      // 0ab5: ldc_w -0.107
      // 0ab8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0abb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0abe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ac1: aastore
      // 0ac2: dup
      // 0ac3: bipush 4
      // 0ac4: new net/minecraft/client/animation/Keyframe
      // 0ac7: dup
      // 0ac8: ldc_w 0.83
      // 0acb: ldc_w -2.433
      // 0ace: ldc_w 3.897
      // 0ad1: ldc_w -0.273
      // 0ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0add: aastore
      // 0ade: dup
      // 0adf: bipush 5
      // 0ae0: new net/minecraft/client/animation/Keyframe
      // 0ae3: dup
      // 0ae4: ldc 0.96
      // 0ae6: ldc_w -3.46
      // 0ae9: ldc_w 5.542
      // 0aec: ldc_w -0.389
      // 0aef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0af2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0af5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0af8: aastore
      // 0af9: dup
      // 0afa: bipush 6
      // 0afc: new net/minecraft/client/animation/Keyframe
      // 0aff: dup
      // 0b00: ldc 1.04
      // 0b02: ldc_w -3.925
      // 0b05: ldc_w 6.286
      // 0b08: ldc_w -0.441
      // 0b0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b14: aastore
      // 0b15: dup
      // 0b16: bipush 7
      // 0b18: new net/minecraft/client/animation/Keyframe
      // 0b1b: dup
      // 0b1c: ldc 1.08
      // 0b1e: ldc_w -4.055
      // 0b21: ldc_w 6.495
      // 0b24: ldc_w -0.456
      // 0b27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b30: aastore
      // 0b31: dup
      // 0b32: bipush 8
      // 0b34: new net/minecraft/client/animation/Keyframe
      // 0b37: dup
      // 0b38: ldc 1.12
      // 0b3a: ldc_w -4.101
      // 0b3d: ldc_w 6.568
      // 0b40: ldc_w -0.461
      // 0b43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b4c: aastore
      // 0b4d: dup
      // 0b4e: bipush 9
      // 0b50: new net/minecraft/client/animation/Keyframe
      // 0b53: dup
      // 0b54: ldc 1.17
      // 0b56: ldc_w -4.02
      // 0b59: ldc_w 6.439
      // 0b5c: ldc_w -0.452
      // 0b5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b68: aastore
      // 0b69: dup
      // 0b6a: bipush 10
      // 0b6c: new net/minecraft/client/animation/Keyframe
      // 0b6f: dup
      // 0b70: ldc 1.21
      // 0b72: ldc_w -3.797
      // 0b75: ldc_w 6.082
      // 0b78: ldc_w -0.427
      // 0b7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b84: aastore
      // 0b85: dup
      // 0b86: bipush 11
      // 0b88: new net/minecraft/client/animation/Keyframe
      // 0b8b: dup
      // 0b8c: ldc 1.25
      // 0b8e: ldc_w -3.46
      // 0b91: ldc_w 5.542
      // 0b94: ldc_w -0.389
      // 0b97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba0: aastore
      // 0ba1: dup
      // 0ba2: bipush 12
      // 0ba4: new net/minecraft/client/animation/Keyframe
      // 0ba7: dup
      // 0ba8: ldc 1.33
      // 0baa: ldc_w -2.558
      // 0bad: ldc_w 4.098
      // 0bb0: ldc_w -0.288
      // 0bb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bbc: aastore
      // 0bbd: dup
      // 0bbe: bipush 13
      // 0bc0: new net/minecraft/client/animation/Keyframe
      // 0bc3: dup
      // 0bc4: ldc 1.38
      // 0bc6: ldc_w -2.051
      // 0bc9: ldc_w 3.284
      // 0bcc: ldc_w -0.23
      // 0bcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bd8: aastore
      // 0bd9: dup
      // 0bda: bipush 14
      // 0bdc: new net/minecraft/client/animation/Keyframe
      // 0bdf: dup
      // 0be0: ldc 1.46
      // 0be2: ldc_w -1.063
      // 0be5: ldc_w 1.703
      // 0be8: ldc_w -0.12
      // 0beb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bf1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bf4: aastore
      // 0bf5: dup
      // 0bf6: bipush 15
      // 0bf8: new net/minecraft/client/animation/Keyframe
      // 0bfb: dup
      // 0bfc: ldc 1.5
      // 0bfe: ldc_w -0.641
      // 0c01: ldc_w 1.026
      // 0c04: ldc_w -0.072
      // 0c07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c10: aastore
      // 0c11: dup
      // 0c12: bipush 16
      // 0c14: new net/minecraft/client/animation/Keyframe
      // 0c17: dup
      // 0c18: ldc 1.54
      // 0c1a: ldc_w -0.304
      // 0c1d: ldc_w 0.487
      // 0c20: ldc_w -0.034
      // 0c23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c2c: aastore
      // 0c2d: dup
      // 0c2e: bipush 17
      // 0c30: new net/minecraft/client/animation/Keyframe
      // 0c33: dup
      // 0c34: ldc 1.58
      // 0c36: ldc_w -0.081
      // 0c39: ldc_w 0.129
      // 0c3c: ldc_w -0.009
      // 0c3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c48: aastore
      // 0c49: dup
      // 0c4a: bipush 18
      // 0c4c: new net/minecraft/client/animation/Keyframe
      // 0c4f: dup
      // 0c50: ldc 1.62
      // 0c52: fconst_0
      // 0c53: fconst_0
      // 0c54: fconst_0
      // 0c55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c5e: aastore
      // 0c5f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c62: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c65: ldc_w "CTR_chest"
      // 0c68: new net/minecraft/client/animation/AnimationChannel
      // 0c6b: dup
      // 0c6c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0c6f: bipush 53
      // 0c71: anewarray 28
      // 0c74: dup
      // 0c75: bipush 0
      // 0c76: new net/minecraft/client/animation/Keyframe
      // 0c79: dup
      // 0c7a: fconst_0
      // 0c7b: ldc_w 0.692
      // 0c7e: ldc_w 4.888
      // 0c81: ldc_w 0.106
      // 0c84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c8d: aastore
      // 0c8e: dup
      // 0c8f: bipush 1
      // 0c90: new net/minecraft/client/animation/Keyframe
      // 0c93: dup
      // 0c94: ldc 0.04
      // 0c96: ldc_w -0.196
      // 0c99: ldc_w 4.602
      // 0c9c: ldc_w -0.559
      // 0c9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ca2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ca5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ca8: aastore
      // 0ca9: dup
      // 0caa: bipush 2
      // 0cab: new net/minecraft/client/animation/Keyframe
      // 0cae: dup
      // 0caf: ldc 0.08
      // 0cb1: ldc_w -2.647
      // 0cb4: ldc_w 3.73
      // 0cb7: ldc_w -2.536
      // 0cba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cc3: aastore
      // 0cc4: dup
      // 0cc5: bipush 3
      // 0cc6: new net/minecraft/client/animation/Keyframe
      // 0cc9: dup
      // 0cca: ldc 0.12
      // 0ccc: ldc_w -6.274
      // 0ccf: ldc_w 2.334
      // 0cd2: ldc_w -5.61
      // 0cd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cdb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cde: aastore
      // 0cdf: dup
      // 0ce0: bipush 4
      // 0ce1: new net/minecraft/client/animation/Keyframe
      // 0ce4: dup
      // 0ce5: ldc 0.17
      // 0ce7: ldc_w -10.654
      // 0cea: ldc 0.62
      // 0cec: ldc_w -9.317
      // 0cef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cf2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cf5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cf8: aastore
      // 0cf9: dup
      // 0cfa: bipush 5
      // 0cfb: new net/minecraft/client/animation/Keyframe
      // 0cfe: dup
      // 0cff: ldc 0.21
      // 0d01: ldc_w -15.45
      // 0d04: ldc_w -1.106
      // 0d07: ldc_w -13.081
      // 0d0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d13: aastore
      // 0d14: dup
      // 0d15: bipush 6
      // 0d17: new net/minecraft/client/animation/Keyframe
      // 0d1a: dup
      // 0d1b: ldc 0.25
      // 0d1d: ldc_w -20.476
      // 0d20: ldc_w -2.581
      // 0d23: ldc_w -16.422
      // 0d26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d2f: aastore
      // 0d30: dup
      // 0d31: bipush 7
      // 0d33: new net/minecraft/client/animation/Keyframe
      // 0d36: dup
      // 0d37: ldc 0.29
      // 0d39: ldc_w -25.631
      // 0d3c: ldc_w -3.666
      // 0d3f: ldc_w -19.056
      // 0d42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d4b: aastore
      // 0d4c: dup
      // 0d4d: bipush 8
      // 0d4f: new net/minecraft/client/animation/Keyframe
      // 0d52: dup
      // 0d53: ldc 0.33
      // 0d55: ldc_w -30.815
      // 0d58: ldc_w -4.332
      // 0d5b: ldc_w -20.88
      // 0d5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d67: aastore
      // 0d68: dup
      // 0d69: bipush 9
      // 0d6b: new net/minecraft/client/animation/Keyframe
      // 0d6e: dup
      // 0d6f: ldc 0.38
      // 0d71: ldc_w -35.887
      // 0d74: ldc_w -4.608
      // 0d77: ldc_w -21.903
      // 0d7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d83: aastore
      // 0d84: dup
      // 0d85: bipush 10
      // 0d87: new net/minecraft/client/animation/Keyframe
      // 0d8a: dup
      // 0d8b: ldc 0.42
      // 0d8d: ldc_w -40.642
      // 0d90: ldc_w -4.537
      // 0d93: ldc_w -22.185
      // 0d96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d9f: aastore
      // 0da0: dup
      // 0da1: bipush 11
      // 0da3: new net/minecraft/client/animation/Keyframe
      // 0da6: dup
      // 0da7: ldc 0.46
      // 0da9: ldc_w -44.817
      // 0dac: ldc_w -4.162
      // 0daf: ldc_w -21.803
      // 0db2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0db5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0db8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dbb: aastore
      // 0dbc: dup
      // 0dbd: bipush 12
      // 0dbf: new net/minecraft/client/animation/Keyframe
      // 0dc2: dup
      // 0dc3: ldc 0.5
      // 0dc5: ldc_w -47.976
      // 0dc8: ldc_w -3.641
      // 0dcb: ldc_w -21.144
      // 0dce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dd7: aastore
      // 0dd8: dup
      // 0dd9: bipush 13
      // 0ddb: new net/minecraft/client/animation/Keyframe
      // 0dde: dup
      // 0ddf: ldc 0.54
      // 0de1: ldc_w -50.139
      // 0de4: ldc_w -3.116
      // 0de7: ldc_w -20.548
      // 0dea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ded: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0df0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0df3: aastore
      // 0df4: dup
      // 0df5: bipush 14
      // 0df7: new net/minecraft/client/animation/Keyframe
      // 0dfa: dup
      // 0dfb: ldc 0.58
      // 0dfd: ldc_w -51.583
      // 0e00: ldc_w -2.603
      // 0e03: ldc_w -20.023
      // 0e06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e0f: aastore
      // 0e10: dup
      // 0e11: bipush 15
      // 0e13: new net/minecraft/client/animation/Keyframe
      // 0e16: dup
      // 0e17: ldc 0.62
      // 0e19: ldc_w -52.496
      // 0e1c: ldc_w -2.116
      // 0e1f: ldc_w -19.576
      // 0e22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e2b: aastore
      // 0e2c: dup
      // 0e2d: bipush 16
      // 0e2f: new net/minecraft/client/animation/Keyframe
      // 0e32: dup
      // 0e33: ldc 0.67
      // 0e35: ldc_w -53.019
      // 0e38: ldc_w -1.672
      // 0e3b: ldc_w -19.214
      // 0e3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e47: aastore
      // 0e48: dup
      // 0e49: bipush 17
      // 0e4b: new net/minecraft/client/animation/Keyframe
      // 0e4e: dup
      // 0e4f: ldc_w 0.71
      // 0e52: ldc_w -53.262
      // 0e55: ldc_w -1.288
      // 0e58: ldc_w -18.943
      // 0e5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e64: aastore
      // 0e65: dup
      // 0e66: bipush 18
      // 0e68: new net/minecraft/client/animation/Keyframe
      // 0e6b: dup
      // 0e6c: ldc 0.75
      // 0e6e: ldc_w -53.314
      // 0e71: ldc_w -0.977
      // 0e74: ldc_w -18.77
      // 0e77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e80: aastore
      // 0e81: dup
      // 0e82: bipush 19
      // 0e84: new net/minecraft/client/animation/Keyframe
      // 0e87: dup
      // 0e88: ldc_w 0.79
      // 0e8b: ldc_w -53.251
      // 0e8e: ldc_w -0.755
      // 0e91: ldc_w -18.697
      // 0e94: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e9d: aastore
      // 0e9e: dup
      // 0e9f: bipush 20
      // 0ea1: new net/minecraft/client/animation/Keyframe
      // 0ea4: dup
      // 0ea5: ldc_w 0.83
      // 0ea8: ldc_w -53.138
      // 0eab: ldc_w -0.632
      // 0eae: ldc_w -18.731
      // 0eb1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eb4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0eb7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0eba: aastore
      // 0ebb: dup
      // 0ebc: bipush 21
      // 0ebe: new net/minecraft/client/animation/Keyframe
      // 0ec1: dup
      // 0ec2: ldc_w 0.88
      // 0ec5: ldc_w -53.034
      // 0ec8: ldc_w -0.617
      // 0ecb: ldc_w -18.873
      // 0ece: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ed1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ed4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ed7: aastore
      // 0ed8: dup
      // 0ed9: bipush 22
      // 0edb: new net/minecraft/client/animation/Keyframe
      // 0ede: dup
      // 0edf: ldc_w 0.92
      // 0ee2: ldc_w -52.995
      // 0ee5: ldc_w -0.719
      // 0ee8: ldc_w -19.126
      // 0eeb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ef4: aastore
      // 0ef5: dup
      // 0ef6: bipush 23
      // 0ef8: new net/minecraft/client/animation/Keyframe
      // 0efb: dup
      // 0efc: ldc 0.96
      // 0efe: ldc_w -53.467
      // 0f01: ldc_w -1.124
      // 0f04: ldc_w -19.6
      // 0f07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f10: aastore
      // 0f11: dup
      // 0f12: bipush 24
      // 0f14: new net/minecraft/client/animation/Keyframe
      // 0f17: dup
      // 0f18: fconst_1
      // 0f19: ldc_w -54.567
      // 0f1c: ldc_w -1.847
      // 0f1f: ldc_w -20.305
      // 0f22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f2b: aastore
      // 0f2c: dup
      // 0f2d: bipush 25
      // 0f2f: new net/minecraft/client/animation/Keyframe
      // 0f32: dup
      // 0f33: ldc 1.04
      // 0f35: ldc_w -55.862
      // 0f38: ldc_w -2.654
      // 0f3b: ldc_w -21.099
      // 0f3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f47: aastore
      // 0f48: dup
      // 0f49: bipush 26
      // 0f4b: new net/minecraft/client/animation/Keyframe
      // 0f4e: dup
      // 0f4f: ldc 1.08
      // 0f51: ldc_w -56.923
      // 0f54: ldc_w -3.346
      // 0f57: ldc_w -21.849
      // 0f5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f63: aastore
      // 0f64: dup
      // 0f65: bipush 27
      // 0f67: new net/minecraft/client/animation/Keyframe
      // 0f6a: dup
      // 0f6b: ldc 1.12
      // 0f6d: ldc_w -57.306
      // 0f70: ldc_w -3.754
      // 0f73: ldc_w -22.433
      // 0f76: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f7f: aastore
      // 0f80: dup
      // 0f81: bipush 28
      // 0f83: new net/minecraft/client/animation/Keyframe
      // 0f86: dup
      // 0f87: ldc 1.17
      // 0f89: ldc_w -56.262
      // 0f8c: ldc_w -1.687
      // 0f8f: ldc_w -17.463
      // 0f92: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f9b: aastore
      // 0f9c: dup
      // 0f9d: bipush 29
      // 0f9f: new net/minecraft/client/animation/Keyframe
      // 0fa2: dup
      // 0fa3: ldc 1.21
      // 0fa5: ldc_w -50.871
      // 0fa8: ldc_w 2.11
      // 0fab: ldc_w -8.425
      // 0fae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fb4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fb7: aastore
      // 0fb8: dup
      // 0fb9: bipush 30
      // 0fbb: new net/minecraft/client/animation/Keyframe
      // 0fbe: dup
      // 0fbf: ldc 1.25
      // 0fc1: ldc_w -35.622
      // 0fc4: ldc_w 5.813
      // 0fc7: ldc_w -0.166
      // 0fca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fcd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd3: aastore
      // 0fd4: dup
      // 0fd5: bipush 31
      // 0fd7: new net/minecraft/client/animation/Keyframe
      // 0fda: dup
      // 0fdb: ldc 1.29
      // 0fdd: ldc_w -9.218
      // 0fe0: ldc_w 7.886
      // 0fe3: ldc_w 3.656
      // 0fe6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fe9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fef: aastore
      // 0ff0: dup
      // 0ff1: bipush 32
      // 0ff3: new net/minecraft/client/animation/Keyframe
      // 0ff6: dup
      // 0ff7: ldc 1.33
      // 0ff9: ldc_w -3.136
      // 0ffc: ldc_w 7.227
      // 0fff: ldc_w -5.144
      // 1002: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1005: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1008: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 100b: aastore
      // 100c: dup
      // 100d: bipush 33
      // 100f: new net/minecraft/client/animation/Keyframe
      // 1012: dup
      // 1013: ldc 1.38
      // 1015: ldc -6.001
      // 1017: ldc_w 7.037
      // 101a: ldc_w -11.03
      // 101d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1020: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1023: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1026: aastore
      // 1027: dup
      // 1028: bipush 34
      // 102a: new net/minecraft/client/animation/Keyframe
      // 102d: dup
      // 102e: ldc 1.42
      // 1030: ldc_w -9.612
      // 1033: ldc_w 7.069
      // 1036: ldc_w -9.889
      // 1039: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 103c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 103f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1042: aastore
      // 1043: dup
      // 1044: bipush 35
      // 1046: new net/minecraft/client/animation/Keyframe
      // 1049: dup
      // 104a: ldc 1.46
      // 104c: ldc_w -11.934
      // 104f: ldc_w 7.133
      // 1052: ldc_w -7.148
      // 1055: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1058: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 105b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 105e: aastore
      // 105f: dup
      // 1060: bipush 36
      // 1062: new net/minecraft/client/animation/Keyframe
      // 1065: dup
      // 1066: ldc 1.5
      // 1068: ldc_w -11.845
      // 106b: ldc_w 7.183
      // 106e: ldc_w -4.602
      // 1071: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1074: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1077: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 107a: aastore
      // 107b: dup
      // 107c: bipush 37
      // 107e: new net/minecraft/client/animation/Keyframe
      // 1081: dup
      // 1082: ldc 1.54
      // 1084: ldc_w -11.635
      // 1087: ldc_w 7.25
      // 108a: ldc_w -4.162
      // 108d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1090: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1093: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1096: aastore
      // 1097: dup
      // 1098: bipush 38
      // 109a: new net/minecraft/client/animation/Keyframe
      // 109d: dup
      // 109e: ldc 1.58
      // 10a0: ldc_w -12.839
      // 10a3: ldc_w 7.321
      // 10a6: ldc_w -4.6
      // 10a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10b2: aastore
      // 10b3: dup
      // 10b4: bipush 39
      // 10b6: new net/minecraft/client/animation/Keyframe
      // 10b9: dup
      // 10ba: ldc 1.62
      // 10bc: ldc_w -13.529
      // 10bf: ldc_w 7.359
      // 10c2: ldc_w -4.597
      // 10c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10ce: aastore
      // 10cf: dup
      // 10d0: bipush 40
      // 10d2: new net/minecraft/client/animation/Keyframe
      // 10d5: dup
      // 10d6: ldc 1.67
      // 10d8: ldc_w -11.332
      // 10db: ldc_w 7.22
      // 10de: ldc_w -4.601
      // 10e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10ea: aastore
      // 10eb: dup
      // 10ec: bipush 41
      // 10ee: new net/minecraft/client/animation/Keyframe
      // 10f1: dup
      // 10f2: ldc 1.71
      // 10f4: ldc_w -6.699
      // 10f7: ldc_w 6.699
      // 10fa: ldc_w -4.021
      // 10fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1100: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1103: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1106: aastore
      // 1107: dup
      // 1108: bipush 42
      // 110a: new net/minecraft/client/animation/Keyframe
      // 110d: dup
      // 110e: ldc 1.75
      // 1110: ldc_w -2.747
      // 1113: ldc_w 6.36
      // 1116: ldc_w -3.385
      // 1119: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 111c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 111f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1122: aastore
      // 1123: dup
      // 1124: bipush 43
      // 1126: new net/minecraft/client/animation/Keyframe
      // 1129: dup
      // 112a: ldc 1.79
      // 112c: ldc_w 0.433
      // 112f: ldc_w 6.069
      // 1132: ldc_w -2.766
      // 1135: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1138: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 113b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 113e: aastore
      // 113f: dup
      // 1140: bipush 44
      // 1142: new net/minecraft/client/animation/Keyframe
      // 1145: dup
      // 1146: ldc 1.83
      // 1148: ldc_w 2.793
      // 114b: ldc_w 5.808
      // 114e: ldc_w -2.175
      // 1151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 115a: aastore
      // 115b: dup
      // 115c: bipush 45
      // 115e: new net/minecraft/client/animation/Keyframe
      // 1161: dup
      // 1162: ldc 1.88
      // 1164: ldc_w 4.326
      // 1167: ldc_w 5.574
      // 116a: ldc_w -1.621
      // 116d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1170: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1173: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1176: aastore
      // 1177: dup
      // 1178: bipush 46
      // 117a: new net/minecraft/client/animation/Keyframe
      // 117d: dup
      // 117e: ldc 1.92
      // 1180: ldc_w 5.084
      // 1183: ldc_w 5.365
      // 1186: ldc_w -1.112
      // 1189: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 118c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 118f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1192: aastore
      // 1193: dup
      // 1194: bipush 47
      // 1196: new net/minecraft/client/animation/Keyframe
      // 1199: dup
      // 119a: ldc 1.96
      // 119c: ldc_w 5.194
      // 119f: ldc_w 5.185
      // 11a2: ldc_w -0.659
      // 11a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ae: aastore
      // 11af: dup
      // 11b0: bipush 48
      // 11b2: new net/minecraft/client/animation/Keyframe
      // 11b5: dup
      // 11b6: fconst_2
      // 11b7: ldc_w 4.838
      // 11ba: ldc_w 5.037
      // 11bd: ldc_w -0.281
      // 11c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11c9: aastore
      // 11ca: dup
      // 11cb: bipush 49
      // 11cd: new net/minecraft/client/animation/Keyframe
      // 11d0: dup
      // 11d1: ldc 2.04
      // 11d3: ldc_w 4.232
      // 11d6: ldc_w 4.931
      // 11d9: ldc_w -0.007
      // 11dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11e5: aastore
      // 11e6: dup
      // 11e7: bipush 50
      // 11e9: new net/minecraft/client/animation/Keyframe
      // 11ec: dup
      // 11ed: ldc 2.08
      // 11ef: ldc_w 3.574
      // 11f2: ldc_w 4.888
      // 11f5: ldc_w 0.106
      // 11f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1201: aastore
      // 1202: dup
      // 1203: bipush 51
      // 1205: new net/minecraft/client/animation/Keyframe
      // 1208: dup
      // 1209: ldc_w 2.17
      // 120c: ldc_w 1.299
      // 120f: ldc_w 4.888
      // 1212: ldc_w 0.106
      // 1215: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1218: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 121b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 121e: aastore
      // 121f: dup
      // 1220: bipush 52
      // 1222: new net/minecraft/client/animation/Keyframe
      // 1225: dup
      // 1226: ldc 2.21
      // 1228: ldc_w 0.692
      // 122b: ldc_w 4.888
      // 122e: ldc_w 0.106
      // 1231: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1234: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1237: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 123a: aastore
      // 123b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 123e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1241: ldc_w "CTR_chest"
      // 1244: new net/minecraft/client/animation/AnimationChannel
      // 1247: dup
      // 1248: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 124b: bipush 21
      // 124d: anewarray 28
      // 1250: dup
      // 1251: bipush 0
      // 1252: new net/minecraft/client/animation/Keyframe
      // 1255: dup
      // 1256: fconst_0
      // 1257: fconst_0
      // 1258: fconst_0
      // 1259: fconst_0
      // 125a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 125d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1260: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1263: aastore
      // 1264: dup
      // 1265: bipush 1
      // 1266: new net/minecraft/client/animation/Keyframe
      // 1269: dup
      // 126a: ldc 0.04
      // 126c: ldc_w -0.016
      // 126f: ldc_w 0.106
      // 1272: ldc_w -0.051
      // 1275: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1278: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 127b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 127e: aastore
      // 127f: dup
      // 1280: bipush 2
      // 1281: new net/minecraft/client/animation/Keyframe
      // 1284: dup
      // 1285: ldc 0.08
      // 1287: ldc_w -0.06
      // 128a: ldc_w 0.399
      // 128d: ldc_w -0.192
      // 1290: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1293: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1296: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1299: aastore
      // 129a: dup
      // 129b: bipush 3
      // 129c: new net/minecraft/client/animation/Keyframe
      // 129f: dup
      // 12a0: ldc 0.17
      // 12a2: ldc_w -0.209
      // 12a5: ldc_w 1.377
      // 12a8: ldc_w -0.663
      // 12ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12b4: aastore
      // 12b5: dup
      // 12b6: bipush 4
      // 12b7: new net/minecraft/client/animation/Keyframe
      // 12ba: dup
      // 12bb: ldc 0.21
      // 12bd: ldc_w -0.3
      // 12c0: ldc_w 1.979
      // 12c3: ldc_w -0.954
      // 12c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12cf: aastore
      // 12d0: dup
      // 12d1: bipush 5
      // 12d2: new net/minecraft/client/animation/Keyframe
      // 12d5: dup
      // 12d6: ldc 0.29
      // 12d8: ldc_w -0.486
      // 12db: ldc_w 3.205
      // 12de: ldc_w -1.544
      // 12e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12ea: aastore
      // 12eb: dup
      // 12ec: bipush 6
      // 12ee: new net/minecraft/client/animation/Keyframe
      // 12f1: dup
      // 12f2: ldc 0.33
      // 12f4: ldc_w -0.569
      // 12f7: ldc_w 3.746
      // 12fa: ldc_w -1.805
      // 12fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1300: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1303: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1306: aastore
      // 1307: dup
      // 1308: bipush 7
      // 130a: new net/minecraft/client/animation/Keyframe
      // 130d: dup
      // 130e: ldc 0.38
      // 1310: ldc_w -0.635
      // 1313: ldc_w 4.183
      // 1316: ldc_w -2.015
      // 1319: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 131c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 131f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1322: aastore
      // 1323: dup
      // 1324: bipush 8
      // 1326: new net/minecraft/client/animation/Keyframe
      // 1329: dup
      // 132a: ldc 0.42
      // 132c: ldc_w -0.679
      // 132f: ldc_w 4.475
      // 1332: ldc_w -2.156
      // 1335: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1338: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 133b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 133e: aastore
      // 133f: dup
      // 1340: bipush 9
      // 1342: new net/minecraft/client/animation/Keyframe
      // 1345: dup
      // 1346: ldc 0.46
      // 1348: ldc_w -0.696
      // 134b: ldc_w 4.582
      // 134e: ldc_w -2.208
      // 1351: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1354: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1357: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 135a: aastore
      // 135b: dup
      // 135c: bipush 10
      // 135e: new net/minecraft/client/animation/Keyframe
      // 1361: dup
      // 1362: ldc 1.67
      // 1364: ldc_w -0.696
      // 1367: ldc_w 4.582
      // 136a: ldc_w -2.208
      // 136d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1370: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1373: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1376: aastore
      // 1377: dup
      // 1378: bipush 11
      // 137a: new net/minecraft/client/animation/Keyframe
      // 137d: dup
      // 137e: ldc 1.71
      // 1380: ldc_w -0.676
      // 1383: ldc_w 4.454
      // 1386: ldc_w -2.146
      // 1389: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 138c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 138f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1392: aastore
      // 1393: dup
      // 1394: bipush 12
      // 1396: new net/minecraft/client/animation/Keyframe
      // 1399: dup
      // 139a: ldc 1.75
      // 139c: ldc_w -0.623
      // 139f: ldc_w 4.106
      // 13a2: ldc_w -1.978
      // 13a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ae: aastore
      // 13af: dup
      // 13b0: bipush 13
      // 13b2: new net/minecraft/client/animation/Keyframe
      // 13b5: dup
      // 13b6: ldc 1.79
      // 13b8: ldc_w -0.545
      // 13bb: ldc_w 3.592
      // 13be: ldc_w -1.731
      // 13c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ca: aastore
      // 13cb: dup
      // 13cc: bipush 14
      // 13ce: new net/minecraft/client/animation/Keyframe
      // 13d1: dup
      // 13d2: ldc 1.83
      // 13d4: ldc_w -0.451
      // 13d7: ldc_w 2.969
      // 13da: ldc_w -1.431
      // 13dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13e6: aastore
      // 13e7: dup
      // 13e8: bipush 15
      // 13ea: new net/minecraft/client/animation/Keyframe
      // 13ed: dup
      // 13ee: ldc 1.88
      // 13f0: ldc_w -0.348
      // 13f3: ldc_w 2.291
      // 13f6: ldc_w -1.104
      // 13f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1402: aastore
      // 1403: dup
      // 1404: bipush 16
      // 1406: new net/minecraft/client/animation/Keyframe
      // 1409: dup
      // 140a: ldc 1.92
      // 140c: ldc_w -0.245
      // 140f: ldc_w 1.613
      // 1412: ldc_w -0.777
      // 1415: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1418: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 141b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 141e: aastore
      // 141f: dup
      // 1420: bipush 17
      // 1422: new net/minecraft/client/animation/Keyframe
      // 1425: dup
      // 1426: ldc 1.96
      // 1428: ldc_w -0.15
      // 142b: ldc_w 0.99
      // 142e: ldc_w -0.477
      // 1431: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1434: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1437: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 143a: aastore
      // 143b: dup
      // 143c: bipush 18
      // 143e: new net/minecraft/client/animation/Keyframe
      // 1441: dup
      // 1442: fconst_2
      // 1443: ldc_w -0.072
      // 1446: ldc_w 0.476
      // 1449: ldc_w -0.229
      // 144c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 144f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1452: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1455: aastore
      // 1456: dup
      // 1457: bipush 19
      // 1459: new net/minecraft/client/animation/Keyframe
      // 145c: dup
      // 145d: ldc 2.04
      // 145f: ldc_w -0.019
      // 1462: ldc_w 0.128
      // 1465: ldc_w -0.061
      // 1468: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 146b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 146e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1471: aastore
      // 1472: dup
      // 1473: bipush 20
      // 1475: new net/minecraft/client/animation/Keyframe
      // 1478: dup
      // 1479: ldc 2.08
      // 147b: fconst_0
      // 147c: fconst_0
      // 147d: fconst_0
      // 147e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1481: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1484: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1487: aastore
      // 1488: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 148b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 148e: ldc_w "CTR_head"
      // 1491: new net/minecraft/client/animation/AnimationChannel
      // 1494: dup
      // 1495: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1498: bipush 54
      // 149a: anewarray 28
      // 149d: dup
      // 149e: bipush 0
      // 149f: new net/minecraft/client/animation/Keyframe
      // 14a2: dup
      // 14a3: fconst_0
      // 14a4: ldc_w 0.412
      // 14a7: ldc_w -4.079
      // 14aa: ldc_w -4.432
      // 14ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14b6: aastore
      // 14b7: dup
      // 14b8: bipush 1
      // 14b9: new net/minecraft/client/animation/Keyframe
      // 14bc: dup
      // 14bd: ldc 0.04
      // 14bf: ldc_w 1.313
      // 14c2: ldc_w -3.78
      // 14c5: ldc_w -4.097
      // 14c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14d1: aastore
      // 14d2: dup
      // 14d3: bipush 2
      // 14d4: new net/minecraft/client/animation/Keyframe
      // 14d7: dup
      // 14d8: ldc 0.08
      // 14da: ldc_w 3.87
      // 14dd: ldc_w -2.993
      // 14e0: ldc_w -3.13
      // 14e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14ec: aastore
      // 14ed: dup
      // 14ee: bipush 3
      // 14ef: new net/minecraft/client/animation/Keyframe
      // 14f2: dup
      // 14f3: ldc 0.12
      // 14f5: ldc_w 9.201
      // 14f8: ldc_w -2.058
      // 14fb: ldc_w -1.806
      // 14fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1501: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1504: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1507: aastore
      // 1508: dup
      // 1509: bipush 4
      // 150a: new net/minecraft/client/animation/Keyframe
      // 150d: dup
      // 150e: ldc 0.17
      // 1510: ldc_w 17.343
      // 1513: ldc_w -1.455
      // 1516: ldc_w -0.829
      // 1519: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 151c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 151f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1522: aastore
      // 1523: dup
      // 1524: bipush 5
      // 1525: new net/minecraft/client/animation/Keyframe
      // 1528: dup
      // 1529: ldc 0.21
      // 152b: ldc_w 26.476
      // 152e: ldc_w -1.388
      // 1531: ldc_w -1.113
      // 1534: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1537: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 153a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 153d: aastore
      // 153e: dup
      // 153f: bipush 6
      // 1541: new net/minecraft/client/animation/Keyframe
      // 1544: dup
      // 1545: ldc 0.25
      // 1547: ldc_w 34.744
      // 154a: ldc_w -1.232
      // 154d: ldc_w -2.335
      // 1550: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1553: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1556: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1559: aastore
      // 155a: dup
      // 155b: bipush 7
      // 155d: new net/minecraft/client/animation/Keyframe
      // 1560: dup
      // 1561: ldc 0.29
      // 1563: ldc_w 41.85
      // 1566: ldc_w -0.493
      // 1569: ldc_w -3.137
      // 156c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 156f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1572: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1575: aastore
      // 1576: dup
      // 1577: bipush 8
      // 1579: new net/minecraft/client/animation/Keyframe
      // 157c: dup
      // 157d: ldc 0.33
      // 157f: ldc_w 48.648
      // 1582: ldc_w 0.413
      // 1585: ldc_w -3.339
      // 1588: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 158b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 158e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1591: aastore
      // 1592: dup
      // 1593: bipush 9
      // 1595: new net/minecraft/client/animation/Keyframe
      // 1598: dup
      // 1599: ldc 0.38
      // 159b: ldc_w 54.829
      // 159e: ldc_w 1.02
      // 15a1: ldc_w -3.347
      // 15a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15ad: aastore
      // 15ae: dup
      // 15af: bipush 10
      // 15b1: new net/minecraft/client/animation/Keyframe
      // 15b4: dup
      // 15b5: ldc 0.42
      // 15b7: ldc_w 60.065
      // 15ba: ldc_w 1.168
      // 15bd: ldc_w -3.555
      // 15c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15c9: aastore
      // 15ca: dup
      // 15cb: bipush 11
      // 15cd: new net/minecraft/client/animation/Keyframe
      // 15d0: dup
      // 15d1: ldc 0.46
      // 15d3: ldc_w 64.129
      // 15d6: ldc_w 0.949
      // 15d9: ldc_w -4.203
      // 15dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15e5: aastore
      // 15e6: dup
      // 15e7: bipush 12
      // 15e9: new net/minecraft/client/animation/Keyframe
      // 15ec: dup
      // 15ed: ldc 0.5
      // 15ef: ldc_w 66.852
      // 15f2: ldc_w 0.608
      // 15f5: ldc_w -5.007
      // 15f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1601: aastore
      // 1602: dup
      // 1603: bipush 13
      // 1605: new net/minecraft/client/animation/Keyframe
      // 1608: dup
      // 1609: ldc 0.54
      // 160b: ldc_w 68.536
      // 160e: ldc_w 0.238
      // 1611: ldc_w -5.612
      // 1614: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1617: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 161a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 161d: aastore
      // 161e: dup
      // 161f: bipush 14
      // 1621: new net/minecraft/client/animation/Keyframe
      // 1624: dup
      // 1625: ldc 0.58
      // 1627: ldc_w 69.6
      // 162a: ldc_w -0.192
      // 162d: ldc_w -6.025
      // 1630: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1633: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1636: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1639: aastore
      // 163a: dup
      // 163b: bipush 15
      // 163d: new net/minecraft/client/animation/Keyframe
      // 1640: dup
      // 1641: ldc 0.62
      // 1643: ldc_w 70.366
      // 1646: ldc_w -0.689
      // 1649: ldc_w -6.249
      // 164c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 164f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1652: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1655: aastore
      // 1656: dup
      // 1657: bipush 16
      // 1659: new net/minecraft/client/animation/Keyframe
      // 165c: dup
      // 165d: ldc 0.67
      // 165f: ldc_w 70.907
      // 1662: ldc_w -1.246
      // 1665: ldc_w -6.292
      // 1668: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 166b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 166e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1671: aastore
      // 1672: dup
      // 1673: bipush 17
      // 1675: new net/minecraft/client/animation/Keyframe
      // 1678: dup
      // 1679: ldc_w 0.71
      // 167c: ldc_w 71.172
      // 167f: ldc_w -1.854
      // 1682: ldc_w -6.168
      // 1685: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1688: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 168b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 168e: aastore
      // 168f: dup
      // 1690: bipush 18
      // 1692: new net/minecraft/client/animation/Keyframe
      // 1695: dup
      // 1696: ldc 0.75
      // 1698: ldc_w 71.242
      // 169b: ldc_w -2.497
      // 169e: ldc_w -5.89
      // 16a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16aa: aastore
      // 16ab: dup
      // 16ac: bipush 19
      // 16ae: new net/minecraft/client/animation/Keyframe
      // 16b1: dup
      // 16b2: ldc_w 0.79
      // 16b5: ldc_w 71.18
      // 16b8: ldc_w -3.162
      // 16bb: ldc_w -5.477
      // 16be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16c7: aastore
      // 16c8: dup
      // 16c9: bipush 20
      // 16cb: new net/minecraft/client/animation/Keyframe
      // 16ce: dup
      // 16cf: ldc_w 0.83
      // 16d2: ldc_w 71.044
      // 16d5: ldc_w -3.832
      // 16d8: ldc_w -4.952
      // 16db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16e4: aastore
      // 16e5: dup
      // 16e6: bipush 21
      // 16e8: new net/minecraft/client/animation/Keyframe
      // 16eb: dup
      // 16ec: ldc_w 0.88
      // 16ef: ldc_w 71.278
      // 16f2: ldc_w -4.501
      // 16f5: ldc_w -4.338
      // 16f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1701: aastore
      // 1702: dup
      // 1703: bipush 22
      // 1705: new net/minecraft/client/animation/Keyframe
      // 1708: dup
      // 1709: ldc_w 0.92
      // 170c: ldc_w 72.2
      // 170f: ldc_w -5.183
      // 1712: ldc_w -3.67
      // 1715: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1718: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 171b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 171e: aastore
      // 171f: dup
      // 1720: bipush 23
      // 1722: new net/minecraft/client/animation/Keyframe
      // 1725: dup
      // 1726: ldc 0.96
      // 1728: ldc_w 74.066
      // 172b: ldc_w -5.854
      // 172e: ldc_w -2.835
      // 1731: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1734: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1737: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 173a: aastore
      // 173b: dup
      // 173c: bipush 24
      // 173e: new net/minecraft/client/animation/Keyframe
      // 1741: dup
      // 1742: fconst_1
      // 1743: ldc_w 76.825
      // 1746: ldc_w -6.569
      // 1749: ldc_w -1.887
      // 174c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 174f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1752: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1755: aastore
      // 1756: dup
      // 1757: bipush 25
      // 1759: new net/minecraft/client/animation/Keyframe
      // 175c: dup
      // 175d: ldc 1.04
      // 175f: ldc_w 79.895
      // 1762: ldc_w -7.429
      // 1765: ldc_w -1.074
      // 1768: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 176b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 176e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1771: aastore
      // 1772: dup
      // 1773: bipush 26
      // 1775: new net/minecraft/client/animation/Keyframe
      // 1778: dup
      // 1779: ldc 1.08
      // 177b: ldc_w 82.719
      // 177e: ldc_w -8.467
      // 1781: ldc_w -0.605
      // 1784: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1787: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 178a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 178d: aastore
      // 178e: dup
      // 178f: bipush 27
      // 1791: new net/minecraft/client/animation/Keyframe
      // 1794: dup
      // 1795: ldc 1.12
      // 1797: ldc_w 84.78
      // 179a: ldc_w -9.67
      // 179d: ldc_w -0.637
      // 17a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17a9: aastore
      // 17aa: dup
      // 17ab: bipush 28
      // 17ad: new net/minecraft/client/animation/Keyframe
      // 17b0: dup
      // 17b1: ldc 1.17
      // 17b3: ldc_w 84.057
      // 17b6: ldc_w -9.435
      // 17b9: ldc_w -5.806
      // 17bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17c5: aastore
      // 17c6: dup
      // 17c7: bipush 29
      // 17c9: new net/minecraft/client/animation/Keyframe
      // 17cc: dup
      // 17cd: ldc 1.21
      // 17cf: ldc_w 55.236
      // 17d2: ldc_w -10.765
      // 17d5: ldc_w -12.151
      // 17d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17e1: aastore
      // 17e2: dup
      // 17e3: bipush 30
      // 17e5: new net/minecraft/client/animation/Keyframe
      // 17e8: dup
      // 17e9: ldc 1.25
      // 17eb: ldc_w -16.657
      // 17ee: ldc_w -10.223
      // 17f1: ldc_w 1.481
      // 17f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17fd: aastore
      // 17fe: dup
      // 17ff: bipush 31
      // 1801: new net/minecraft/client/animation/Keyframe
      // 1804: dup
      // 1805: ldc 1.29
      // 1807: ldc_w -41.533
      // 180a: ldc_w -5.093
      // 180d: ldc_w 1.871
      // 1810: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1813: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1816: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1819: aastore
      // 181a: dup
      // 181b: bipush 32
      // 181d: new net/minecraft/client/animation/Keyframe
      // 1820: dup
      // 1821: ldc 1.33
      // 1823: ldc_w -30.23
      // 1826: ldc_w -2.137
      // 1829: ldc_w 7.978
      // 182c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 182f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1832: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1835: aastore
      // 1836: dup
      // 1837: bipush 33
      // 1839: new net/minecraft/client/animation/Keyframe
      // 183c: dup
      // 183d: ldc 1.38
      // 183f: ldc_w -4.84
      // 1842: ldc_w -1.114
      // 1845: ldc_w 10.379
      // 1848: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 184b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 184e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1851: aastore
      // 1852: dup
      // 1853: bipush 34
      // 1855: new net/minecraft/client/animation/Keyframe
      // 1858: dup
      // 1859: ldc 1.42
      // 185b: ldc_w 18.028
      // 185e: ldc_w -1.189
      // 1861: ldc_w 6.246
      // 1864: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1867: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 186a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 186d: aastore
      // 186e: dup
      // 186f: bipush 35
      // 1871: new net/minecraft/client/animation/Keyframe
      // 1874: dup
      // 1875: ldc 1.46
      // 1877: ldc_w 28.128
      // 187a: ldc_w -1.138
      // 187d: ldc_w 1.701
      // 1880: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1883: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1886: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1889: aastore
      // 188a: dup
      // 188b: bipush 36
      // 188d: new net/minecraft/client/animation/Keyframe
      // 1890: dup
      // 1891: ldc 1.5
      // 1893: ldc_w 25.0
      // 1896: ldc_w -1.027
      // 1899: ldc_w -1.63
      // 189c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 189f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18a5: aastore
      // 18a6: dup
      // 18a7: bipush 37
      // 18a9: new net/minecraft/client/animation/Keyframe
      // 18ac: dup
      // 18ad: ldc 1.54
      // 18af: ldc_w 18.476
      // 18b2: ldc_w -0.988
      // 18b5: ldc_w -2.683
      // 18b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c1: aastore
      // 18c2: dup
      // 18c3: bipush 38
      // 18c5: new net/minecraft/client/animation/Keyframe
      // 18c8: dup
      // 18c9: ldc 1.58
      // 18cb: ldc_w 13.716
      // 18ce: ldc_w -1.092
      // 18d1: ldc_w -2.693
      // 18d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18dd: aastore
      // 18de: dup
      // 18df: bipush 39
      // 18e1: new net/minecraft/client/animation/Keyframe
      // 18e4: dup
      // 18e5: ldc 1.62
      // 18e7: ldc_w 11.98
      // 18ea: ldc_w -1.194
      // 18ed: ldc_w -2.864
      // 18f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18f9: aastore
      // 18fa: dup
      // 18fb: bipush 40
      // 18fd: new net/minecraft/client/animation/Keyframe
      // 1900: dup
      // 1901: ldc 1.67
      // 1903: ldc_w 9.957
      // 1906: ldc_w -1.185
      // 1909: ldc_w -2.883
      // 190c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 190f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1912: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1915: aastore
      // 1916: dup
      // 1917: bipush 41
      // 1919: new net/minecraft/client/animation/Keyframe
      // 191c: dup
      // 191d: ldc 1.71
      // 191f: ldc_w 6.89
      // 1922: ldc_w -1.029
      // 1925: ldc_w -3.196
      // 1928: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 192b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 192e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1931: aastore
      // 1932: dup
      // 1933: bipush 42
      // 1935: new net/minecraft/client/animation/Keyframe
      // 1938: dup
      // 1939: ldc 1.75
      // 193b: ldc_w 7.342
      // 193e: ldc -1.213
      // 1940: ldc_w -3.28
      // 1943: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1946: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1949: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 194c: aastore
      // 194d: dup
      // 194e: bipush 43
      // 1950: new net/minecraft/client/animation/Keyframe
      // 1953: dup
      // 1954: ldc 1.79
      // 1956: ldc_w 10.751
      // 1959: ldc_w -1.531
      // 195c: ldc_w -3.263
      // 195f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1962: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1965: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1968: aastore
      // 1969: dup
      // 196a: bipush 44
      // 196c: new net/minecraft/client/animation/Keyframe
      // 196f: dup
      // 1970: ldc 1.83
      // 1972: ldc_w 15.31
      // 1975: ldc_w -1.93
      // 1978: ldc_w -3.229
      // 197b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 197e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1981: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1984: aastore
      // 1985: dup
      // 1986: bipush 45
      // 1988: new net/minecraft/client/animation/Keyframe
      // 198b: dup
      // 198c: ldc 1.88
      // 198e: ldc_w 19.327
      // 1991: ldc_w -2.378
      // 1994: ldc_w -3.234
      // 1997: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 199a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 199d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19a0: aastore
      // 19a1: dup
      // 19a2: bipush 46
      // 19a4: new net/minecraft/client/animation/Keyframe
      // 19a7: dup
      // 19a8: ldc 1.92
      // 19aa: ldc_w 21.189
      // 19ad: ldc_w -2.846
      // 19b0: ldc_w -3.308
      // 19b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19bc: aastore
      // 19bd: dup
      // 19be: bipush 47
      // 19c0: new net/minecraft/client/animation/Keyframe
      // 19c3: dup
      // 19c4: ldc 1.96
      // 19c6: ldc_w 20.735
      // 19c9: ldc_w -3.298
      // 19cc: ldc_w -3.46
      // 19cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19d8: aastore
      // 19d9: dup
      // 19da: bipush 48
      // 19dc: new net/minecraft/client/animation/Keyframe
      // 19df: dup
      // 19e0: fconst_2
      // 19e1: ldc_w 18.394
      // 19e4: ldc_w -3.69
      // 19e7: ldc_w -3.673
      // 19ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19f3: aastore
      // 19f4: dup
      // 19f5: bipush 49
      // 19f7: new net/minecraft/client/animation/Keyframe
      // 19fa: dup
      // 19fb: ldc 2.04
      // 19fd: ldc_w 14.516
      // 1a00: ldc_w -3.974
      // 1a03: ldc_w -3.899
      // 1a06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a0f: aastore
      // 1a10: dup
      // 1a11: bipush 50
      // 1a13: new net/minecraft/client/animation/Keyframe
      // 1a16: dup
      // 1a17: ldc 2.08
      // 1a19: ldc_w 9.36
      // 1a1c: ldc_w -4.09
      // 1a1f: ldc_w -4.053
      // 1a22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a2b: aastore
      // 1a2c: dup
      // 1a2d: bipush 51
      // 1a2f: new net/minecraft/client/animation/Keyframe
      // 1a32: dup
      // 1a33: ldc_w 2.12
      // 1a36: ldc_w 4.488
      // 1a39: ldc_w -4.092
      // 1a3c: ldc_w -4.185
      // 1a3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a48: aastore
      // 1a49: dup
      // 1a4a: bipush 52
      // 1a4c: new net/minecraft/client/animation/Keyframe
      // 1a4f: dup
      // 1a50: ldc_w 2.17
      // 1a53: ldc_w 1.456
      // 1a56: ldc_w -4.085
      // 1a59: ldc_w -4.35
      // 1a5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a65: aastore
      // 1a66: dup
      // 1a67: bipush 53
      // 1a69: new net/minecraft/client/animation/Keyframe
      // 1a6c: dup
      // 1a6d: ldc 2.21
      // 1a6f: ldc_w 0.412
      // 1a72: ldc_w -4.079
      // 1a75: ldc_w -4.432
      // 1a78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a81: aastore
      // 1a82: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1a85: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1a88: ldc_w "CTR_head"
      // 1a8b: new net/minecraft/client/animation/AnimationChannel
      // 1a8e: dup
      // 1a8f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1a92: bipush 1
      // 1a93: anewarray 28
      // 1a96: dup
      // 1a97: bipush 0
      // 1a98: new net/minecraft/client/animation/Keyframe
      // 1a9b: dup
      // 1a9c: ldc 0.25
      // 1a9e: fconst_0
      // 1a9f: fconst_0
      // 1aa0: fconst_0
      // 1aa1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1aa4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aaa: aastore
      // 1aab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1aae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ab1: ldc_w "CTR_shoulde1r_left"
      // 1ab4: new net/minecraft/client/animation/AnimationChannel
      // 1ab7: dup
      // 1ab8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1abb: bipush 15
      // 1abd: anewarray 28
      // 1ac0: dup
      // 1ac1: bipush 0
      // 1ac2: new net/minecraft/client/animation/Keyframe
      // 1ac5: dup
      // 1ac6: fconst_0
      // 1ac7: fconst_0
      // 1ac8: fconst_0
      // 1ac9: fconst_0
      // 1aca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1acd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ad0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ad3: aastore
      // 1ad4: dup
      // 1ad5: bipush 1
      // 1ad6: new net/minecraft/client/animation/Keyframe
      // 1ad9: dup
      // 1ada: ldc 0.04
      // 1adc: ldc_w -0.021
      // 1adf: ldc_w 0.07
      // 1ae2: ldc_w -0.008
      // 1ae5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ae8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aeb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aee: aastore
      // 1aef: dup
      // 1af0: bipush 2
      // 1af1: new net/minecraft/client/animation/Keyframe
      // 1af4: dup
      // 1af5: ldc 0.17
      // 1af7: ldc_w -0.268
      // 1afa: ldc_w 0.905
      // 1afd: ldc_w -0.103
      // 1b00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b09: aastore
      // 1b0a: dup
      // 1b0b: bipush 3
      // 1b0c: new net/minecraft/client/animation/Keyframe
      // 1b0f: dup
      // 1b10: ldc 0.33
      // 1b12: ldc_w -0.729
      // 1b15: ldc_w 2.463
      // 1b18: ldc_w -0.28
      // 1b1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b24: aastore
      // 1b25: dup
      // 1b26: bipush 4
      // 1b27: new net/minecraft/client/animation/Keyframe
      // 1b2a: dup
      // 1b2b: ldc 0.42
      // 1b2d: ldc_w -0.871
      // 1b30: ldc_w 2.943
      // 1b33: ldc_w -0.334
      // 1b36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b3f: aastore
      // 1b40: dup
      // 1b41: bipush 5
      // 1b42: new net/minecraft/client/animation/Keyframe
      // 1b45: dup
      // 1b46: ldc 0.46
      // 1b48: ldc_w -0.892
      // 1b4b: ldc_w 3.013
      // 1b4e: ldc_w -0.342
      // 1b51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b5a: aastore
      // 1b5b: dup
      // 1b5c: bipush 6
      // 1b5e: new net/minecraft/client/animation/Keyframe
      // 1b61: dup
      // 1b62: ldc 1.33
      // 1b64: ldc_w -0.892
      // 1b67: ldc_w 3.013
      // 1b6a: ldc_w -0.342
      // 1b6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b76: aastore
      // 1b77: dup
      // 1b78: bipush 7
      // 1b7a: new net/minecraft/client/animation/Keyframe
      // 1b7d: dup
      // 1b7e: ldc 1.38
      // 1b80: ldc_w -0.865
      // 1b83: ldc_w 2.922
      // 1b86: ldc_w -0.332
      // 1b89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b92: aastore
      // 1b93: dup
      // 1b94: bipush 8
      // 1b96: new net/minecraft/client/animation/Keyframe
      // 1b99: dup
      // 1b9a: ldc 1.42
      // 1b9c: ldc_w -0.793
      // 1b9f: ldc_w 2.679
      // 1ba2: ldc_w -0.304
      // 1ba5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ba8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bae: aastore
      // 1baf: dup
      // 1bb0: bipush 9
      // 1bb2: new net/minecraft/client/animation/Keyframe
      // 1bb5: dup
      // 1bb6: ldc 1.58
      // 1bb8: ldc_w -0.329
      // 1bbb: ldc_w 1.112
      // 1bbe: ldc_w -0.126
      // 1bc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bca: aastore
      // 1bcb: dup
      // 1bcc: bipush 10
      // 1bce: new net/minecraft/client/animation/Keyframe
      // 1bd1: dup
      // 1bd2: ldc 1.62
      // 1bd4: ldc_w -0.237
      // 1bd7: ldc_w 0.8
      // 1bda: ldc_w -0.091
      // 1bdd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1be0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1be3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1be6: aastore
      // 1be7: dup
      // 1be8: bipush 11
      // 1bea: new net/minecraft/client/animation/Keyframe
      // 1bed: dup
      // 1bee: ldc 1.71
      // 1bf0: ldc_w -0.145
      // 1bf3: ldc_w 0.489
      // 1bf6: ldc_w -0.055
      // 1bf9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c02: aastore
      // 1c03: dup
      // 1c04: bipush 12
      // 1c06: new net/minecraft/client/animation/Keyframe
      // 1c09: dup
      // 1c0a: ldc 1.96
      // 1c0c: ldc_w -0.092
      // 1c0f: ldc_w 0.311
      // 1c12: ldc_w -0.035
      // 1c15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c1e: aastore
      // 1c1f: dup
      // 1c20: bipush 13
      // 1c22: new net/minecraft/client/animation/Keyframe
      // 1c25: dup
      // 1c26: ldc_w 2.12
      // 1c29: ldc_w -0.011
      // 1c2c: ldc_w 0.039
      // 1c2f: ldc_w -0.004
      // 1c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c3b: aastore
      // 1c3c: dup
      // 1c3d: bipush 14
      // 1c3f: new net/minecraft/client/animation/Keyframe
      // 1c42: dup
      // 1c43: ldc 2.21
      // 1c45: fconst_0
      // 1c46: fconst_0
      // 1c47: fconst_0
      // 1c48: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c51: aastore
      // 1c52: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c55: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c58: ldc_w "CTR_shoulde1r_left"
      // 1c5b: new net/minecraft/client/animation/AnimationChannel
      // 1c5e: dup
      // 1c5f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c62: bipush 38
      // 1c64: anewarray 28
      // 1c67: dup
      // 1c68: bipush 0
      // 1c69: new net/minecraft/client/animation/Keyframe
      // 1c6c: dup
      // 1c6d: fconst_0
      // 1c6e: ldc_w -0.112
      // 1c71: ldc_w -0.19
      // 1c74: ldc_w -0.487
      // 1c77: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c80: aastore
      // 1c81: dup
      // 1c82: bipush 1
      // 1c83: new net/minecraft/client/animation/Keyframe
      // 1c86: dup
      // 1c87: ldc 0.04
      // 1c89: ldc_w 0.069
      // 1c8c: ldc_w -0.386
      // 1c8f: ldc_w -0.826
      // 1c92: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c9b: aastore
      // 1c9c: dup
      // 1c9d: bipush 2
      // 1c9e: new net/minecraft/client/animation/Keyframe
      // 1ca1: dup
      // 1ca2: ldc 0.08
      // 1ca4: ldc_w 0.565
      // 1ca7: ldc_w -0.926
      // 1caa: ldc_w -1.753
      // 1cad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cb0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cb3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cb6: aastore
      // 1cb7: dup
      // 1cb8: bipush 3
      // 1cb9: new net/minecraft/client/animation/Keyframe
      // 1cbc: dup
      // 1cbd: ldc 0.17
      // 1cbf: ldc_w 2.223
      // 1cc2: ldc_w -2.729
      // 1cc5: ldc_w -4.852
      // 1cc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ccb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cd1: aastore
      // 1cd2: dup
      // 1cd3: bipush 4
      // 1cd4: new net/minecraft/client/animation/Keyframe
      // 1cd7: dup
      // 1cd8: ldc 0.21
      // 1cda: ldc_w 3.244
      // 1cdd: ldc_w -3.839
      // 1ce0: ldc_w -6.762
      // 1ce3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ce6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ce9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cec: aastore
      // 1ced: dup
      // 1cee: bipush 5
      // 1cef: new net/minecraft/client/animation/Keyframe
      // 1cf2: dup
      // 1cf3: ldc 0.25
      // 1cf5: ldc_w 4.301
      // 1cf8: ldc_w -4.988
      // 1cfb: ldc_w -8.737
      // 1cfe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d07: aastore
      // 1d08: dup
      // 1d09: bipush 6
      // 1d0b: new net/minecraft/client/animation/Keyframe
      // 1d0e: dup
      // 1d0f: ldc 0.29
      // 1d11: ldc_w 5.322
      // 1d14: ldc_w -6.099
      // 1d17: ldc_w -10.647
      // 1d1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d23: aastore
      // 1d24: dup
      // 1d25: bipush 7
      // 1d27: new net/minecraft/client/animation/Keyframe
      // 1d2a: dup
      // 1d2b: ldc 0.33
      // 1d2d: ldc_w 6.239
      // 1d30: ldc_w -7.096
      // 1d33: ldc_w -12.36
      // 1d36: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d3f: aastore
      // 1d40: dup
      // 1d41: bipush 8
      // 1d43: new net/minecraft/client/animation/Keyframe
      // 1d46: dup
      // 1d47: ldc 0.38
      // 1d49: ldc_w 6.98
      // 1d4c: ldc_w -7.902
      // 1d4f: ldc_w -13.746
      // 1d52: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d5b: aastore
      // 1d5c: dup
      // 1d5d: bipush 9
      // 1d5f: new net/minecraft/client/animation/Keyframe
      // 1d62: dup
      // 1d63: ldc 0.42
      // 1d65: ldc_w 7.476
      // 1d68: ldc_w -8.441
      // 1d6b: ldc_w -14.674
      // 1d6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d77: aastore
      // 1d78: dup
      // 1d79: bipush 10
      // 1d7b: new net/minecraft/client/animation/Keyframe
      // 1d7e: dup
      // 1d7f: ldc 0.46
      // 1d81: ldc_w 7.657
      // 1d84: ldc_w -8.638
      // 1d87: ldc_w -15.012
      // 1d8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d93: aastore
      // 1d94: dup
      // 1d95: bipush 11
      // 1d97: new net/minecraft/client/animation/Keyframe
      // 1d9a: dup
      // 1d9b: ldc 1.12
      // 1d9d: ldc_w 7.657
      // 1da0: ldc_w -8.638
      // 1da3: ldc_w -15.012
      // 1da6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1da9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1daf: aastore
      // 1db0: dup
      // 1db1: bipush 12
      // 1db3: new net/minecraft/client/animation/Keyframe
      // 1db6: dup
      // 1db7: ldc 1.17
      // 1db9: ldc_w 7.429
      // 1dbc: ldc_w -7.95
      // 1dbf: ldc_w -14.555
      // 1dc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dcb: aastore
      // 1dcc: dup
      // 1dcd: bipush 13
      // 1dcf: new net/minecraft/client/animation/Keyframe
      // 1dd2: dup
      // 1dd3: ldc 1.21
      // 1dd5: ldc_w 6.773
      // 1dd8: ldc_w -6.157
      // 1ddb: ldc_w -13.4
      // 1dde: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1de1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1de4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1de7: aastore
      // 1de8: dup
      // 1de9: bipush 14
      // 1deb: new net/minecraft/client/animation/Keyframe
      // 1dee: dup
      // 1def: ldc 1.25
      // 1df1: ldc_w 5.732
      // 1df4: ldc_w -3.67
      // 1df7: ldc_w -11.869
      // 1dfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e03: aastore
      // 1e04: dup
      // 1e05: bipush 15
      // 1e07: new net/minecraft/client/animation/Keyframe
      // 1e0a: dup
      // 1e0b: ldc 1.29
      // 1e0d: ldc_w 4.349
      // 1e10: ldc_w -0.896
      // 1e13: ldc_w -10.283
      // 1e16: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e1f: aastore
      // 1e20: dup
      // 1e21: bipush 16
      // 1e23: new net/minecraft/client/animation/Keyframe
      // 1e26: dup
      // 1e27: ldc 1.33
      // 1e29: ldc_w 2.668
      // 1e2c: ldc_w 1.757
      // 1e2f: ldc_w -8.965
      // 1e32: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e3b: aastore
      // 1e3c: dup
      // 1e3d: bipush 17
      // 1e3f: new net/minecraft/client/animation/Keyframe
      // 1e42: dup
      // 1e43: ldc 1.38
      // 1e45: ldc_w 0.819
      // 1e48: ldc_w 3.914
      // 1e4b: ldc_w -8.115
      // 1e4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e57: aastore
      // 1e58: dup
      // 1e59: bipush 18
      // 1e5b: new net/minecraft/client/animation/Keyframe
      // 1e5e: dup
      // 1e5f: ldc 1.42
      // 1e61: ldc_w -0.712
      // 1e64: ldc_w 5.349
      // 1e67: ldc_w -7.45
      // 1e6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e73: aastore
      // 1e74: dup
      // 1e75: bipush 19
      // 1e77: new net/minecraft/client/animation/Keyframe
      // 1e7a: dup
      // 1e7b: ldc 1.46
      // 1e7d: ldc_w -1.35
      // 1e80: ldc_w 5.87
      // 1e83: ldc_w -6.563
      // 1e86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e8f: aastore
      // 1e90: dup
      // 1e91: bipush 20
      // 1e93: new net/minecraft/client/animation/Keyframe
      // 1e96: dup
      // 1e97: ldc 1.5
      // 1e99: ldc_w -1.146
      // 1e9c: ldc_w 5.151
      // 1e9f: ldc_w -5.186
      // 1ea2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ea5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ea8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eab: aastore
      // 1eac: dup
      // 1ead: bipush 21
      // 1eaf: new net/minecraft/client/animation/Keyframe
      // 1eb2: dup
      // 1eb3: ldc 1.54
      // 1eb5: ldc_w -0.697
      // 1eb8: ldc_w 3.542
      // 1ebb: ldc_w -3.593
      // 1ebe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ec1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ec4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ec7: aastore
      // 1ec8: dup
      // 1ec9: bipush 22
      // 1ecb: new net/minecraft/client/animation/Keyframe
      // 1ece: dup
      // 1ecf: ldc 1.58
      // 1ed1: ldc_w -0.248
      // 1ed4: ldc_w 1.86
      // 1ed7: ldc_w -2.197
      // 1eda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1edd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ee0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ee3: aastore
      // 1ee4: dup
      // 1ee5: bipush 23
      // 1ee7: new net/minecraft/client/animation/Keyframe
      // 1eea: dup
      // 1eeb: ldc 1.62
      // 1eed: ldc_w -0.044
      // 1ef0: ldc_w 0.923
      // 1ef3: ldc_w -1.409
      // 1ef6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ef9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1efc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eff: aastore
      // 1f00: dup
      // 1f01: bipush 24
      // 1f03: new net/minecraft/client/animation/Keyframe
      // 1f06: dup
      // 1f07: ldc 1.67
      // 1f09: ldc_w -0.047
      // 1f0c: ldc_w 0.699
      // 1f0f: ldc_w -1.142
      // 1f12: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f1b: aastore
      // 1f1c: dup
      // 1f1d: bipush 25
      // 1f1f: new net/minecraft/client/animation/Keyframe
      // 1f22: dup
      // 1f23: ldc 1.71
      // 1f25: ldc_w -0.054
      // 1f28: ldc_w 0.584
      // 1f2b: ldc_w -1.006
      // 1f2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f37: aastore
      // 1f38: dup
      // 1f39: bipush 26
      // 1f3b: new net/minecraft/client/animation/Keyframe
      // 1f3e: dup
      // 1f3f: ldc 1.75
      // 1f41: ldc_w -0.062
      // 1f44: ldc_w 0.541
      // 1f47: ldc_w -0.955
      // 1f4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f53: aastore
      // 1f54: dup
      // 1f55: bipush 27
      // 1f57: new net/minecraft/client/animation/Keyframe
      // 1f5a: dup
      // 1f5b: ldc 1.79
      // 1f5d: ldc_w -0.065
      // 1f60: ldc_w 0.535
      // 1f63: ldc_w -0.948
      // 1f66: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f6f: aastore
      // 1f70: dup
      // 1f71: bipush 28
      // 1f73: new net/minecraft/client/animation/Keyframe
      // 1f76: dup
      // 1f77: ldc 1.83
      // 1f79: ldc_w -0.051
      // 1f7c: ldc_w 1.445
      // 1f7f: ldc_w -1.233
      // 1f82: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f8b: aastore
      // 1f8c: dup
      // 1f8d: bipush 29
      // 1f8f: new net/minecraft/client/animation/Keyframe
      // 1f92: dup
      // 1f93: ldc 1.88
      // 1f95: ldc_w -0.025
      // 1f98: ldc_w 3.471
      // 1f9b: ldc_w -1.936
      // 1f9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fa1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fa4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fa7: aastore
      // 1fa8: dup
      // 1fa9: bipush 30
      // 1fab: new net/minecraft/client/animation/Keyframe
      // 1fae: dup
      // 1faf: ldc 1.92
      // 1fb1: ldc_w -0.011
      // 1fb4: ldc_w 5.556
      // 1fb7: ldc_w -2.826
      // 1fba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fc3: aastore
      // 1fc4: dup
      // 1fc5: bipush 31
      // 1fc7: new net/minecraft/client/animation/Keyframe
      // 1fca: dup
      // 1fcb: ldc 1.96
      // 1fcd: ldc_w -0.019
      // 1fd0: ldc_w 6.84
      // 1fd3: ldc_w -3.708
      // 1fd6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fd9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fdc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fdf: aastore
      // 1fe0: dup
      // 1fe1: bipush 32
      // 1fe3: new net/minecraft/client/animation/Keyframe
      // 1fe6: dup
      // 1fe7: fconst_2
      // 1fe8: ldc_w -0.072
      // 1feb: ldc_w 7.243
      // 1fee: ldc_w -4.521
      // 1ff1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ff4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ff7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ffa: aastore
      // 1ffb: dup
      // 1ffc: bipush 33
      // 1ffe: new net/minecraft/client/animation/Keyframe
      // 2001: dup
      // 2002: ldc 2.04
      // 2004: ldc_w -0.58
      // 2007: ldc_w 7.157
      // 200a: ldc_w -5.196
      // 200d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2010: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2013: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2016: aastore
      // 2017: dup
      // 2018: bipush 34
      // 201a: new net/minecraft/client/animation/Keyframe
      // 201d: dup
      // 201e: ldc 2.08
      // 2020: ldc_w -1.042
      // 2023: ldc_w 6.558
      // 2026: ldc_w -5.486
      // 2029: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 202c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 202f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2032: aastore
      // 2033: dup
      // 2034: bipush 35
      // 2036: new net/minecraft/client/animation/Keyframe
      // 2039: dup
      // 203a: ldc_w 2.12
      // 203d: ldc_w -0.801
      // 2040: ldc_w 4.352
      // 2043: ldc_w -4.19
      // 2046: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2049: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 204c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 204f: aastore
      // 2050: dup
      // 2051: bipush 36
      // 2053: new net/minecraft/client/animation/Keyframe
      // 2056: dup
      // 2057: ldc_w 2.17
      // 205a: ldc_w -0.353
      // 205d: ldc_w 1.331
      // 2060: ldc_w -1.783
      // 2063: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2066: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2069: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 206c: aastore
      // 206d: dup
      // 206e: bipush 37
      // 2070: new net/minecraft/client/animation/Keyframe
      // 2073: dup
      // 2074: ldc 2.21
      // 2076: ldc_w -0.112
      // 2079: ldc_w -0.19
      // 207c: ldc_w -0.487
      // 207f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2082: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2085: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2088: aastore
      // 2089: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 208c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 208f: ldc_w "CTR_upperarm_left"
      // 2092: new net/minecraft/client/animation/AnimationChannel
      // 2095: dup
      // 2096: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2099: bipush 52
      // 209b: anewarray 28
      // 209e: dup
      // 209f: bipush 0
      // 20a0: new net/minecraft/client/animation/Keyframe
      // 20a3: dup
      // 20a4: fconst_0
      // 20a5: ldc_w -1.675
      // 20a8: ldc_w -2.702
      // 20ab: ldc_w -0.075
      // 20ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20b7: aastore
      // 20b8: dup
      // 20b9: bipush 1
      // 20ba: new net/minecraft/client/animation/Keyframe
      // 20bd: dup
      // 20be: ldc 0.04
      // 20c0: ldc_w -1.468
      // 20c3: ldc_w -2.682
      // 20c6: ldc_w -0.198
      // 20c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20d2: aastore
      // 20d3: dup
      // 20d4: bipush 2
      // 20d5: new net/minecraft/client/animation/Keyframe
      // 20d8: dup
      // 20d9: ldc 0.08
      // 20db: ldc_w -0.828
      // 20de: ldc_w -2.614
      // 20e1: ldc_w -0.491
      // 20e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20ed: aastore
      // 20ee: dup
      // 20ef: bipush 3
      // 20f0: new net/minecraft/client/animation/Keyframe
      // 20f3: dup
      // 20f4: ldc 0.12
      // 20f6: ldc_w 0.14
      // 20f9: ldc_w -2.65
      // 20fc: ldc_w -0.655
      // 20ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2102: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2105: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2108: aastore
      // 2109: dup
      // 210a: bipush 4
      // 210b: new net/minecraft/client/animation/Keyframe
      // 210e: dup
      // 210f: ldc 0.17
      // 2111: ldc_w 1.184
      // 2114: ldc_w -3.331
      // 2117: ldc_w -0.386
      // 211a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 211d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2120: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2123: aastore
      // 2124: dup
      // 2125: bipush 5
      // 2126: new net/minecraft/client/animation/Keyframe
      // 2129: dup
      // 212a: ldc 0.21
      // 212c: ldc_w 2.45
      // 212f: ldc_w -5.618
      // 2132: ldc_w -0.139
      // 2135: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2138: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 213b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 213e: aastore
      // 213f: dup
      // 2140: bipush 6
      // 2142: new net/minecraft/client/animation/Keyframe
      // 2145: dup
      // 2146: ldc 0.25
      // 2148: ldc_w 5.434
      // 214b: ldc_w -9.716
      // 214e: ldc_w -1.58
      // 2151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 215a: aastore
      // 215b: dup
      // 215c: bipush 7
      // 215e: new net/minecraft/client/animation/Keyframe
      // 2161: dup
      // 2162: ldc 0.29
      // 2164: ldc_w 13.973
      // 2167: ldc_w -15.08
      // 216a: ldc_w -8.316
      // 216d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2170: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2173: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2176: aastore
      // 2177: dup
      // 2178: bipush 8
      // 217a: new net/minecraft/client/animation/Keyframe
      // 217d: dup
      // 217e: ldc 0.33
      // 2180: ldc_w 16.462
      // 2183: ldc_w -16.97
      // 2186: ldc_w -8.055
      // 2189: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 218c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 218f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2192: aastore
      // 2193: dup
      // 2194: bipush 9
      // 2196: new net/minecraft/client/animation/Keyframe
      // 2199: dup
      // 219a: ldc 0.38
      // 219c: ldc_w 20.582
      // 219f: ldc_w -17.994
      // 21a2: ldc_w -15.973
      // 21a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ae: aastore
      // 21af: dup
      // 21b0: bipush 10
      // 21b2: new net/minecraft/client/animation/Keyframe
      // 21b5: dup
      // 21b6: ldc 0.42
      // 21b8: ldc_w 25.862
      // 21bb: ldc_w -18.026
      // 21be: ldc_w -35.686
      // 21c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ca: aastore
      // 21cb: dup
      // 21cc: bipush 11
      // 21ce: new net/minecraft/client/animation/Keyframe
      // 21d1: dup
      // 21d2: ldc 0.46
      // 21d4: ldc_w 30.552
      // 21d7: ldc_w -17.706
      // 21da: ldc_w -59.436
      // 21dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21e6: aastore
      // 21e7: dup
      // 21e8: bipush 12
      // 21ea: new net/minecraft/client/animation/Keyframe
      // 21ed: dup
      // 21ee: ldc 0.5
      // 21f0: ldc_w 34.326
      // 21f3: ldc_w -17.868
      // 21f6: ldc_w -79.481
      // 21f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2202: aastore
      // 2203: dup
      // 2204: bipush 13
      // 2206: new net/minecraft/client/animation/Keyframe
      // 2209: dup
      // 220a: ldc 0.54
      // 220c: ldc_w 37.833
      // 220f: ldc_w -17.079
      // 2212: ldc_w -100.091
      // 2215: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2218: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 221b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 221e: aastore
      // 221f: dup
      // 2220: bipush 14
      // 2222: new net/minecraft/client/animation/Keyframe
      // 2225: dup
      // 2226: ldc 0.58
      // 2228: ldc_w 40.445
      // 222b: ldc_w -15.537
      // 222e: ldc_w -118.454
      // 2231: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2234: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2237: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 223a: aastore
      // 223b: dup
      // 223c: bipush 15
      // 223e: new net/minecraft/client/animation/Keyframe
      // 2241: dup
      // 2242: ldc 0.62
      // 2244: ldc_w 41.795
      // 2247: ldc_w -13.625
      // 224a: ldc_w -131.569
      // 224d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2250: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2253: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2256: aastore
      // 2257: dup
      // 2258: bipush 16
      // 225a: new net/minecraft/client/animation/Keyframe
      // 225d: dup
      // 225e: ldc 0.67
      // 2260: ldc_w 42.242
      // 2263: ldc_w -11.715
      // 2266: ldc_w -140.471
      // 2269: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 226c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 226f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2272: aastore
      // 2273: dup
      // 2274: bipush 17
      // 2276: new net/minecraft/client/animation/Keyframe
      // 2279: dup
      // 227a: ldc_w 0.71
      // 227d: ldc_w 42.32
      // 2280: ldc_w -9.987
      // 2283: ldc_w -148.122
      // 2286: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2289: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 228c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 228f: aastore
      // 2290: dup
      // 2291: bipush 18
      // 2293: new net/minecraft/client/animation/Keyframe
      // 2296: dup
      // 2297: ldc 0.75
      // 2299: ldc_w 42.251
      // 229c: ldc_w -8.536
      // 229f: ldc_w -154.385
      // 22a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ab: aastore
      // 22ac: dup
      // 22ad: bipush 19
      // 22af: new net/minecraft/client/animation/Keyframe
      // 22b2: dup
      // 22b3: ldc_w 0.79
      // 22b6: ldc_w 42.22
      // 22b9: ldc_w -7.414
      // 22bc: ldc_w -159.11
      // 22bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22c8: aastore
      // 22c9: dup
      // 22ca: bipush 20
      // 22cc: new net/minecraft/client/animation/Keyframe
      // 22cf: dup
      // 22d0: ldc_w 0.83
      // 22d3: ldc_w 42.366
      // 22d6: ldc_w -6.661
      // 22d9: ldc_w -162.127
      // 22dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22e5: aastore
      // 22e6: dup
      // 22e7: bipush 21
      // 22e9: new net/minecraft/client/animation/Keyframe
      // 22ec: dup
      // 22ed: ldc_w 0.88
      // 22f0: ldc_w 42.771
      // 22f3: ldc_w -6.325
      // 22f6: ldc_w -163.25
      // 22f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2302: aastore
      // 2303: dup
      // 2304: bipush 22
      // 2306: new net/minecraft/client/animation/Keyframe
      // 2309: dup
      // 230a: ldc_w 0.92
      // 230d: ldc_w 43.261
      // 2310: ldc_w -6.202
      // 2313: ldc_w -163.34
      // 2316: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2319: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 231c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 231f: aastore
      // 2320: dup
      // 2321: bipush 23
      // 2323: new net/minecraft/client/animation/Keyframe
      // 2326: dup
      // 2327: ldc 0.96
      // 2329: ldc_w 43.645
      // 232c: ldc_w -6.039
      // 232f: ldc_w -163.4
      // 2332: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2335: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2338: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 233b: aastore
      // 233c: dup
      // 233d: bipush 24
      // 233f: new net/minecraft/client/animation/Keyframe
      // 2342: dup
      // 2343: ldc 1.04
      // 2345: ldc_w 44.122
      // 2348: ldc_w -5.567
      // 234b: ldc_w -163.456
      // 234e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2351: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2354: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2357: aastore
      // 2358: dup
      // 2359: bipush 25
      // 235b: new net/minecraft/client/animation/Keyframe
      // 235e: dup
      // 235f: ldc 1.12
      // 2361: ldc_w 44.267
      // 2364: ldc_w -4.863
      // 2367: ldc_w -163.464
      // 236a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 236d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2370: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2373: aastore
      // 2374: dup
      // 2375: bipush 26
      // 2377: new net/minecraft/client/animation/Keyframe
      // 237a: dup
      // 237b: ldc 1.17
      // 237d: ldc_w 31.679
      // 2380: ldc_w 4.166
      // 2383: ldc_w -159.526
      // 2386: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2389: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 238c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 238f: aastore
      // 2390: dup
      // 2391: bipush 27
      // 2393: new net/minecraft/client/animation/Keyframe
      // 2396: dup
      // 2397: ldc 1.21
      // 2399: ldc_w 36.726
      // 239c: ldc_w -2.95
      // 239f: ldc_w -166.026
      // 23a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ab: aastore
      // 23ac: dup
      // 23ad: bipush 28
      // 23af: new net/minecraft/client/animation/Keyframe
      // 23b2: dup
      // 23b3: ldc 1.25
      // 23b5: ldc_w 54.065
      // 23b8: ldc_w -6.3
      // 23bb: ldc_w -155.033
      // 23be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23c7: aastore
      // 23c8: dup
      // 23c9: bipush 29
      // 23cb: new net/minecraft/client/animation/Keyframe
      // 23ce: dup
      // 23cf: ldc 1.29
      // 23d1: ldc_w 20.339
      // 23d4: ldc_w -77.301
      // 23d7: ldc_w -74.502
      // 23da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23e3: aastore
      // 23e4: dup
      // 23e5: bipush 30
      // 23e7: new net/minecraft/client/animation/Keyframe
      // 23ea: dup
      // 23eb: ldc 1.33
      // 23ed: ldc_w -55.075
      // 23f0: ldc_w -59.764
      // 23f3: ldc_w -21.955
      // 23f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ff: aastore
      // 2400: dup
      // 2401: bipush 31
      // 2403: new net/minecraft/client/animation/Keyframe
      // 2406: dup
      // 2407: ldc 1.38
      // 2409: ldc_w -61.202
      // 240c: ldc_w -47.468
      // 240f: ldc_w -14.449
      // 2412: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2415: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2418: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 241b: aastore
      // 241c: dup
      // 241d: bipush 32
      // 241f: new net/minecraft/client/animation/Keyframe
      // 2422: dup
      // 2423: ldc 1.42
      // 2425: ldc_w -58.162
      // 2428: ldc_w -49.817
      // 242b: ldc_w -10.646
      // 242e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2431: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2434: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2437: aastore
      // 2438: dup
      // 2439: bipush 33
      // 243b: new net/minecraft/client/animation/Keyframe
      // 243e: dup
      // 243f: ldc 1.46
      // 2441: ldc_w -53.277
      // 2444: ldc_w -56.104
      // 2447: ldc_w -11.073
      // 244a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 244d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2450: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2453: aastore
      // 2454: dup
      // 2455: bipush 34
      // 2457: new net/minecraft/client/animation/Keyframe
      // 245a: dup
      // 245b: ldc 1.5
      // 245d: ldc_w -45.487
      // 2460: ldc_w -62.138
      // 2463: ldc_w -17.409
      // 2466: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2469: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 246c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 246f: aastore
      // 2470: dup
      // 2471: bipush 35
      // 2473: new net/minecraft/client/animation/Keyframe
      // 2476: dup
      // 2477: ldc 1.54
      // 2479: ldc_w -36.018
      // 247c: ldc_w -64.637
      // 247f: ldc_w -25.08
      // 2482: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2485: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2488: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 248b: aastore
      // 248c: dup
      // 248d: bipush 36
      // 248f: new net/minecraft/client/animation/Keyframe
      // 2492: dup
      // 2493: ldc 1.58
      // 2495: ldc_w -24.741
      // 2498: ldc_w -65.868
      // 249b: ldc_w -32.553
      // 249e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24a7: aastore
      // 24a8: dup
      // 24a9: bipush 37
      // 24ab: new net/minecraft/client/animation/Keyframe
      // 24ae: dup
      // 24af: ldc 1.62
      // 24b1: ldc_w -16.214
      // 24b4: ldc_w -66.721
      // 24b7: ldc_w -39.029
      // 24ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24c3: aastore
      // 24c4: dup
      // 24c5: bipush 38
      // 24c7: new net/minecraft/client/animation/Keyframe
      // 24ca: dup
      // 24cb: ldc 1.67
      // 24cd: ldc_w -18.427
      // 24d0: ldc_w -66.174
      // 24d3: ldc_w -39.824
      // 24d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24df: aastore
      // 24e0: dup
      // 24e1: bipush 39
      // 24e3: new net/minecraft/client/animation/Keyframe
      // 24e6: dup
      // 24e7: ldc 1.71
      // 24e9: ldc_w -22.66
      // 24ec: ldc_w -66.432
      // 24ef: ldc_w -41.216
      // 24f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24fb: aastore
      // 24fc: dup
      // 24fd: bipush 40
      // 24ff: new net/minecraft/client/animation/Keyframe
      // 2502: dup
      // 2503: ldc 1.75
      // 2505: ldc_w -24.085
      // 2508: ldc_w -68.137
      // 250b: ldc_w -42.859
      // 250e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2511: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2514: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2517: aastore
      // 2518: dup
      // 2519: bipush 41
      // 251b: new net/minecraft/client/animation/Keyframe
      // 251e: dup
      // 251f: ldc 1.79
      // 2521: ldc_w -21.768
      // 2524: ldc_w -70.658
      // 2527: ldc_w -45.92
      // 252a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 252d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2530: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2533: aastore
      // 2534: dup
      // 2535: bipush 42
      // 2537: new net/minecraft/client/animation/Keyframe
      // 253a: dup
      // 253b: ldc 1.83
      // 253d: ldc_w -18.143
      // 2540: ldc_w -73.637
      // 2543: ldc_w -49.253
      // 2546: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2549: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 254c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 254f: aastore
      // 2550: dup
      // 2551: bipush 43
      // 2553: new net/minecraft/client/animation/Keyframe
      // 2556: dup
      // 2557: ldc 1.88
      // 2559: ldc_w -14.959
      // 255c: ldc_w -76.797
      // 255f: ldc_w -51.682
      // 2562: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2565: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2568: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 256b: aastore
      // 256c: dup
      // 256d: bipush 44
      // 256f: new net/minecraft/client/animation/Keyframe
      // 2572: dup
      // 2573: ldc 1.92
      // 2575: ldc_w 2.213
      // 2578: ldc_w -80.242
      // 257b: ldc_w -66.994
      // 257e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2581: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2584: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2587: aastore
      // 2588: dup
      // 2589: bipush 45
      // 258b: new net/minecraft/client/animation/Keyframe
      // 258e: dup
      // 258f: ldc 1.96
      // 2591: ldc_w -32.814
      // 2594: ldc_w -78.873
      // 2597: ldc_w -27.531
      // 259a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 259d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25a3: aastore
      // 25a4: dup
      // 25a5: bipush 46
      // 25a7: new net/minecraft/client/animation/Keyframe
      // 25aa: dup
      // 25ab: fconst_2
      // 25ac: ldc_w -55.823
      // 25af: ldc_w -67.046
      // 25b2: ldc_w 2.695
      // 25b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25be: aastore
      // 25bf: dup
      // 25c0: bipush 47
      // 25c2: new net/minecraft/client/animation/Keyframe
      // 25c5: dup
      // 25c6: ldc 2.04
      // 25c8: ldc_w -48.402
      // 25cb: ldc_w -49.028
      // 25ce: ldc_w 8.329
      // 25d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25da: aastore
      // 25db: dup
      // 25dc: bipush 48
      // 25de: new net/minecraft/client/animation/Keyframe
      // 25e1: dup
      // 25e2: ldc 2.08
      // 25e4: ldc_w -33.035
      // 25e7: ldc_w -27.397
      // 25ea: ldc_w 11.376
      // 25ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25f6: aastore
      // 25f7: dup
      // 25f8: bipush 49
      // 25fa: new net/minecraft/client/animation/Keyframe
      // 25fd: dup
      // 25fe: ldc_w 2.12
      // 2601: ldc_w -18.178
      // 2604: ldc_w -11.524
      // 2607: ldc_w 12.056
      // 260a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 260d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2610: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2613: aastore
      // 2614: dup
      // 2615: bipush 50
      // 2617: new net/minecraft/client/animation/Keyframe
      // 261a: dup
      // 261b: ldc_w 2.17
      // 261e: ldc_w -6.566
      // 2621: ldc_w -4.204
      // 2624: ldc_w 5.778
      // 2627: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 262a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 262d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2630: aastore
      // 2631: dup
      // 2632: bipush 51
      // 2634: new net/minecraft/client/animation/Keyframe
      // 2637: dup
      // 2638: ldc 2.21
      // 263a: ldc_w -1.675
      // 263d: ldc_w -2.702
      // 2640: ldc_w -0.075
      // 2643: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2646: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2649: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 264c: aastore
      // 264d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2650: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2653: ldc_w "CTR_upperarm_left"
      // 2656: new net/minecraft/client/animation/AnimationChannel
      // 2659: dup
      // 265a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 265d: bipush 26
      // 265f: anewarray 28
      // 2662: dup
      // 2663: bipush 0
      // 2664: new net/minecraft/client/animation/Keyframe
      // 2667: dup
      // 2668: fconst_0
      // 2669: fconst_0
      // 266a: fconst_0
      // 266b: fconst_0
      // 266c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 266f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2672: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2675: aastore
      // 2676: dup
      // 2677: bipush 1
      // 2678: new net/minecraft/client/animation/Keyframe
      // 267b: dup
      // 267c: ldc 0.04
      // 267e: ldc_w 0.037
      // 2681: ldc_w 0.07
      // 2684: ldc_w 0.004
      // 2687: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 268a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 268d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2690: aastore
      // 2691: dup
      // 2692: bipush 2
      // 2693: new net/minecraft/client/animation/Keyframe
      // 2696: dup
      // 2697: ldc 0.08
      // 2699: ldc_w 0.134
      // 269c: ldc_w 0.255
      // 269f: ldc_w 0.013
      // 26a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26ab: aastore
      // 26ac: dup
      // 26ad: bipush 3
      // 26ae: new net/minecraft/client/animation/Keyframe
      // 26b1: dup
      // 26b2: ldc 0.12
      // 26b4: ldc_w 0.272
      // 26b7: ldc_w 0.516
      // 26ba: ldc_w 0.026
      // 26bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26c6: aastore
      // 26c7: dup
      // 26c8: bipush 4
      // 26c9: new net/minecraft/client/animation/Keyframe
      // 26cc: dup
      // 26cd: ldc 0.17
      // 26cf: ldc_w 0.43
      // 26d2: ldc_w 0.816
      // 26d5: ldc_w 0.042
      // 26d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26e1: aastore
      // 26e2: dup
      // 26e3: bipush 5
      // 26e4: new net/minecraft/client/animation/Keyframe
      // 26e7: dup
      // 26e8: ldc 0.21
      // 26ea: ldc_w 0.588
      // 26ed: ldc_w 1.116
      // 26f0: ldc_w 0.057
      // 26f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26fc: aastore
      // 26fd: dup
      // 26fe: bipush 6
      // 2700: new net/minecraft/client/animation/Keyframe
      // 2703: dup
      // 2704: ldc 0.25
      // 2706: ldc_w 0.726
      // 2709: ldc_w 1.377
      // 270c: ldc_w 0.07
      // 270f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2712: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2715: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2718: aastore
      // 2719: dup
      // 271a: bipush 7
      // 271c: new net/minecraft/client/animation/Keyframe
      // 271f: dup
      // 2720: ldc 0.29
      // 2722: ldc_w 0.823
      // 2725: ldc_w 1.562
      // 2728: ldc 0.079
      // 272a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 272d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2730: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2733: aastore
      // 2734: dup
      // 2735: bipush 8
      // 2737: new net/minecraft/client/animation/Keyframe
      // 273a: dup
      // 273b: ldc 0.33
      // 273d: ldc_w 0.86
      // 2740: ldc_w 1.632
      // 2743: ldc_w 0.083
      // 2746: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2749: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 274c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 274f: aastore
      // 2750: dup
      // 2751: bipush 9
      // 2753: new net/minecraft/client/animation/Keyframe
      // 2756: dup
      // 2757: ldc 0.38
      // 2759: ldc_w 0.812
      // 275c: ldc_w 1.542
      // 275f: ldc_w 0.078
      // 2762: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2765: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2768: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 276b: aastore
      // 276c: dup
      // 276d: bipush 10
      // 276f: new net/minecraft/client/animation/Keyframe
      // 2772: dup
      // 2773: ldc 0.42
      // 2775: ldc_w 0.69
      // 2778: ldc_w 1.309
      // 277b: ldc_w 0.067
      // 277e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2781: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2784: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2787: aastore
      // 2788: dup
      // 2789: bipush 11
      // 278b: new net/minecraft/client/animation/Keyframe
      // 278e: dup
      // 278f: ldc 0.46
      // 2791: ldc_w 0.522
      // 2794: ldc_w 0.99
      // 2797: ldc_w 0.05
      // 279a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 279d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27a3: aastore
      // 27a4: dup
      // 27a5: bipush 12
      // 27a7: new net/minecraft/client/animation/Keyframe
      // 27aa: dup
      // 27ab: ldc 0.5
      // 27ad: ldc_w 0.339
      // 27b0: ldc_w 0.642
      // 27b3: ldc_w 0.033
      // 27b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27bf: aastore
      // 27c0: dup
      // 27c1: bipush 13
      // 27c3: new net/minecraft/client/animation/Keyframe
      // 27c6: dup
      // 27c7: ldc 0.54
      // 27c9: ldc_w 0.171
      // 27cc: ldc_w 0.324
      // 27cf: ldc_w 0.016
      // 27d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27db: aastore
      // 27dc: dup
      // 27dd: bipush 14
      // 27df: new net/minecraft/client/animation/Keyframe
      // 27e2: dup
      // 27e3: ldc 0.58
      // 27e5: ldc_w 0.048
      // 27e8: ldc_w 0.09
      // 27eb: ldc_w 0.005
      // 27ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27f7: aastore
      // 27f8: dup
      // 27f9: bipush 15
      // 27fb: new net/minecraft/client/animation/Keyframe
      // 27fe: dup
      // 27ff: ldc 0.62
      // 2801: fconst_0
      // 2802: fconst_0
      // 2803: fconst_0
      // 2804: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2807: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 280a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 280d: aastore
      // 280e: dup
      // 280f: bipush 16
      // 2811: new net/minecraft/client/animation/Keyframe
      // 2814: dup
      // 2815: ldc 1.12
      // 2817: fconst_0
      // 2818: fconst_0
      // 2819: fconst_0
      // 281a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 281d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2820: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2823: aastore
      // 2824: dup
      // 2825: bipush 17
      // 2827: new net/minecraft/client/animation/Keyframe
      // 282a: dup
      // 282b: ldc 1.17
      // 282d: ldc_w -6.21
      // 2830: ldc_w 2.299
      // 2833: ldc_w 1.151
      // 2836: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2839: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 283c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 283f: aastore
      // 2840: dup
      // 2841: bipush 18
      // 2843: new net/minecraft/client/animation/Keyframe
      // 2846: dup
      // 2847: ldc 1.21
      // 2849: ldc_w -8.323
      // 284c: ldc_w 2.536
      // 284f: ldc_w 0.248
      // 2852: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2855: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2858: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 285b: aastore
      // 285c: dup
      // 285d: bipush 19
      // 285f: new net/minecraft/client/animation/Keyframe
      // 2862: dup
      // 2863: ldc 1.25
      // 2865: ldc_w -1.224
      // 2868: ldc_w -5.867
      // 286b: ldc_w -0.581
      // 286e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2871: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2874: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2877: aastore
      // 2878: dup
      // 2879: bipush 20
      // 287b: new net/minecraft/client/animation/Keyframe
      // 287e: dup
      // 287f: ldc 1.29
      // 2881: ldc_w -0.772
      // 2884: ldc_w -5.444
      // 2887: ldc_w -0.538
      // 288a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 288d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2890: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2893: aastore
      // 2894: dup
      // 2895: bipush 21
      // 2897: new net/minecraft/client/animation/Keyframe
      // 289a: dup
      // 289b: ldc 1.33
      // 289d: ldc_w -0.445
      // 28a0: ldc_w -4.361
      // 28a3: ldc_w -0.43
      // 28a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28af: aastore
      // 28b0: dup
      // 28b1: bipush 22
      // 28b3: new net/minecraft/client/animation/Keyframe
      // 28b6: dup
      // 28b7: ldc 1.38
      // 28b9: ldc_w -0.222
      // 28bc: ldc_w -2.946
      // 28bf: ldc_w -0.29
      // 28c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28cb: aastore
      // 28cc: dup
      // 28cd: bipush 23
      // 28cf: new net/minecraft/client/animation/Keyframe
      // 28d2: dup
      // 28d3: ldc 1.42
      // 28d5: ldc_w -0.086
      // 28d8: ldc_w -1.528
      // 28db: ldc_w -0.151
      // 28de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28e7: aastore
      // 28e8: dup
      // 28e9: bipush 24
      // 28eb: new net/minecraft/client/animation/Keyframe
      // 28ee: dup
      // 28ef: ldc 1.46
      // 28f1: ldc_w -0.018
      // 28f4: ldc_w -0.437
      // 28f7: ldc_w -0.043
      // 28fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2900: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2903: aastore
      // 2904: dup
      // 2905: bipush 25
      // 2907: new net/minecraft/client/animation/Keyframe
      // 290a: dup
      // 290b: ldc 1.5
      // 290d: fconst_0
      // 290e: fconst_0
      // 290f: fconst_0
      // 2910: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2913: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2916: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2919: aastore
      // 291a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 291d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2920: ldc_w "CTR_lowerarm_left"
      // 2923: new net/minecraft/client/animation/AnimationChannel
      // 2926: dup
      // 2927: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 292a: bipush 49
      // 292c: anewarray 28
      // 292f: dup
      // 2930: bipush 0
      // 2931: new net/minecraft/client/animation/Keyframe
      // 2934: dup
      // 2935: fconst_0
      // 2936: ldc_w 0.994
      // 2939: ldc_w -2.336
      // 293c: ldc_w -0.568
      // 293f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2942: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2945: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2948: aastore
      // 2949: dup
      // 294a: bipush 1
      // 294b: new net/minecraft/client/animation/Keyframe
      // 294e: dup
      // 294f: ldc 0.04
      // 2951: ldc_w 1.092
      // 2954: ldc_w -1.215
      // 2957: ldc -0.727
      // 2959: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 295c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 295f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2962: aastore
      // 2963: dup
      // 2964: bipush 2
      // 2965: new net/minecraft/client/animation/Keyframe
      // 2968: dup
      // 2969: ldc 0.08
      // 296b: ldc_w 1.186
      // 296e: ldc_w 1.846
      // 2971: ldc_w -1.107
      // 2974: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2977: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 297a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 297d: aastore
      // 297e: dup
      // 297f: bipush 3
      // 2980: new net/minecraft/client/animation/Keyframe
      // 2983: dup
      // 2984: ldc 0.12
      // 2986: ldc_w 1.175
      // 2989: ldc_w 5.805
      // 298c: ldc_w -1.705
      // 298f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2992: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2995: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2998: aastore
      // 2999: dup
      // 299a: bipush 4
      // 299b: new net/minecraft/client/animation/Keyframe
      // 299e: dup
      // 299f: ldc 0.17
      // 29a1: ldc_w 1.355
      // 29a4: ldc_w 9.209
      // 29a7: ldc_w -2.66
      // 29aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29b3: aastore
      // 29b4: dup
      // 29b5: bipush 5
      // 29b6: new net/minecraft/client/animation/Keyframe
      // 29b9: dup
      // 29ba: ldc 0.21
      // 29bc: ldc_w 1.559
      // 29bf: ldc_w 12.032
      // 29c2: ldc_w -3.825
      // 29c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29ce: aastore
      // 29cf: dup
      // 29d0: bipush 6
      // 29d2: new net/minecraft/client/animation/Keyframe
      // 29d5: dup
      // 29d6: ldc 0.25
      // 29d8: ldc_w -0.636
      // 29db: ldc_w 17.695
      // 29de: ldc_w -4.371
      // 29e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29ea: aastore
      // 29eb: dup
      // 29ec: bipush 7
      // 29ee: new net/minecraft/client/animation/Keyframe
      // 29f1: dup
      // 29f2: ldc 0.29
      // 29f4: ldc_w -10.553
      // 29f7: ldc_w 33.356
      // 29fa: ldc_w -4.377
      // 29fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a06: aastore
      // 2a07: dup
      // 2a08: bipush 8
      // 2a0a: new net/minecraft/client/animation/Keyframe
      // 2a0d: dup
      // 2a0e: ldc 0.33
      // 2a10: ldc_w -10.251
      // 2a13: ldc_w 32.975
      // 2a16: ldc_w -5.663
      // 2a19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a22: aastore
      // 2a23: dup
      // 2a24: bipush 9
      // 2a26: new net/minecraft/client/animation/Keyframe
      // 2a29: dup
      // 2a2a: ldc 0.38
      // 2a2c: ldc_w -9.347
      // 2a2f: ldc_w 31.849
      // 2a32: ldc_w -6.717
      // 2a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a3e: aastore
      // 2a3f: dup
      // 2a40: bipush 10
      // 2a42: new net/minecraft/client/animation/Keyframe
      // 2a45: dup
      // 2a46: ldc 0.42
      // 2a48: ldc_w -8.054
      // 2a4b: ldc_w 30.25
      // 2a4e: ldc_w -7.461
      // 2a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a5a: aastore
      // 2a5b: dup
      // 2a5c: bipush 11
      // 2a5e: new net/minecraft/client/animation/Keyframe
      // 2a61: dup
      // 2a62: ldc 0.46
      // 2a64: ldc_w -7.059
      // 2a67: ldc_w 28.914
      // 2a6a: ldc_w -7.772
      // 2a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a76: aastore
      // 2a77: dup
      // 2a78: bipush 12
      // 2a7a: new net/minecraft/client/animation/Keyframe
      // 2a7d: dup
      // 2a7e: ldc 0.5
      // 2a80: ldc_w -6.542
      // 2a83: ldc_w 27.504
      // 2a86: ldc_w -7.48
      // 2a89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a92: aastore
      // 2a93: dup
      // 2a94: bipush 13
      // 2a96: new net/minecraft/client/animation/Keyframe
      // 2a99: dup
      // 2a9a: ldc 0.54
      // 2a9c: ldc_w -6.055
      // 2a9f: ldc_w 24.9
      // 2aa2: ldc_w -6.663
      // 2aa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aae: aastore
      // 2aaf: dup
      // 2ab0: bipush 14
      // 2ab2: new net/minecraft/client/animation/Keyframe
      // 2ab5: dup
      // 2ab6: ldc 0.58
      // 2ab8: ldc_w -5.669
      // 2abb: ldc_w 21.074
      // 2abe: ldc_w -5.492
      // 2ac1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ac4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ac7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aca: aastore
      // 2acb: dup
      // 2acc: bipush 15
      // 2ace: new net/minecraft/client/animation/Keyframe
      // 2ad1: dup
      // 2ad2: ldc 0.62
      // 2ad4: ldc_w -5.509
      // 2ad7: ldc_w 16.189
      // 2ada: ldc_w -4.105
      // 2add: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ae0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ae3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ae6: aastore
      // 2ae7: dup
      // 2ae8: bipush 16
      // 2aea: new net/minecraft/client/animation/Keyframe
      // 2aed: dup
      // 2aee: ldc 0.67
      // 2af0: ldc_w -5.678
      // 2af3: ldc_w 10.555
      // 2af6: ldc_w -2.601
      // 2af9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2afc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b02: aastore
      // 2b03: dup
      // 2b04: bipush 17
      // 2b06: new net/minecraft/client/animation/Keyframe
      // 2b09: dup
      // 2b0a: ldc_w 0.71
      // 2b0d: ldc_w -6.223
      // 2b10: ldc_w 4.611
      // 2b13: ldc_w -1.042
      // 2b16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b1f: aastore
      // 2b20: dup
      // 2b21: bipush 18
      // 2b23: new net/minecraft/client/animation/Keyframe
      // 2b26: dup
      // 2b27: ldc 0.75
      // 2b29: ldc_w -7.104
      // 2b2c: ldc_w -1.094
      // 2b2f: ldc_w 0.511
      // 2b32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b3b: aastore
      // 2b3c: dup
      // 2b3d: bipush 19
      // 2b3f: new net/minecraft/client/animation/Keyframe
      // 2b42: dup
      // 2b43: ldc_w 0.79
      // 2b46: ldc_w -8.161
      // 2b49: ldc_w -5.933
      // 2b4c: ldc_w 1.946
      // 2b4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b58: aastore
      // 2b59: dup
      // 2b5a: bipush 20
      // 2b5c: new net/minecraft/client/animation/Keyframe
      // 2b5f: dup
      // 2b60: ldc_w 0.83
      // 2b63: ldc_w -9.085
      // 2b66: ldc_w -9.269
      // 2b69: ldc_w 3.041
      // 2b6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b75: aastore
      // 2b76: dup
      // 2b77: bipush 21
      // 2b79: new net/minecraft/client/animation/Keyframe
      // 2b7c: dup
      // 2b7d: ldc_w 0.88
      // 2b80: ldc_w -9.474
      // 2b83: ldc_w -10.5
      // 2b86: ldc_w 3.477
      // 2b89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b92: aastore
      // 2b93: dup
      // 2b94: bipush 22
      // 2b96: new net/minecraft/client/animation/Keyframe
      // 2b99: dup
      // 2b9a: ldc 1.12
      // 2b9c: ldc_w -9.474
      // 2b9f: ldc_w -10.5
      // 2ba2: ldc_w 3.477
      // 2ba5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ba8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bae: aastore
      // 2baf: dup
      // 2bb0: bipush 23
      // 2bb2: new net/minecraft/client/animation/Keyframe
      // 2bb5: dup
      // 2bb6: ldc 1.17
      // 2bb8: ldc_w -9.471
      // 2bbb: ldc_w -10.409
      // 2bbe: ldc_w 3.475
      // 2bc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bca: aastore
      // 2bcb: dup
      // 2bcc: bipush 24
      // 2bce: new net/minecraft/client/animation/Keyframe
      // 2bd1: dup
      // 2bd2: ldc 1.21
      // 2bd4: ldc_w -9.453
      // 2bd7: ldc_w -9.767
      // 2bda: ldc_w 3.462
      // 2bdd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2be0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2be3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2be6: aastore
      // 2be7: dup
      // 2be8: bipush 25
      // 2bea: new net/minecraft/client/animation/Keyframe
      // 2bed: dup
      // 2bee: ldc 1.25
      // 2bf0: ldc_w -9.405
      // 2bf3: ldc_w -8.025
      // 2bf6: ldc_w 3.43
      // 2bf9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c02: aastore
      // 2c03: dup
      // 2c04: bipush 26
      // 2c06: new net/minecraft/client/animation/Keyframe
      // 2c09: dup
      // 2c0a: ldc 1.29
      // 2c0c: ldc_w -50.552
      // 2c0f: ldc_w -15.755
      // 2c12: ldc_w 40.942
      // 2c15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c1e: aastore
      // 2c1f: dup
      // 2c20: bipush 27
      // 2c22: new net/minecraft/client/animation/Keyframe
      // 2c25: dup
      // 2c26: ldc 1.33
      // 2c28: ldc_w -89.873
      // 2c2b: ldc_w -58.233
      // 2c2e: ldc_w 84.863
      // 2c31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c3a: aastore
      // 2c3b: dup
      // 2c3c: bipush 28
      // 2c3e: new net/minecraft/client/animation/Keyframe
      // 2c41: dup
      // 2c42: ldc 1.38
      // 2c44: ldc_w -25.076
      // 2c47: ldc_w -64.19
      // 2c4a: ldc_w 20.242
      // 2c4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c56: aastore
      // 2c57: dup
      // 2c58: bipush 29
      // 2c5a: new net/minecraft/client/animation/Keyframe
      // 2c5d: dup
      // 2c5e: ldc 1.42
      // 2c60: ldc_w -47.971
      // 2c63: ldc_w -59.844
      // 2c66: ldc_w 43.206
      // 2c69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c72: aastore
      // 2c73: dup
      // 2c74: bipush 30
      // 2c76: new net/minecraft/client/animation/Keyframe
      // 2c79: dup
      // 2c7a: ldc 1.46
      // 2c7c: ldc_w 28.345
      // 2c7f: ldc_w -52.439
      // 2c82: ldc_w -32.98
      // 2c85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c8e: aastore
      // 2c8f: dup
      // 2c90: bipush 31
      // 2c92: new net/minecraft/client/animation/Keyframe
      // 2c95: dup
      // 2c96: ldc 1.5
      // 2c98: ldc_w 9.764
      // 2c9b: ldc_w -48.194
      // 2c9e: ldc_w -14.164
      // 2ca1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ca4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ca7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2caa: aastore
      // 2cab: dup
      // 2cac: bipush 32
      // 2cae: new net/minecraft/client/animation/Keyframe
      // 2cb1: dup
      // 2cb2: ldc 1.54
      // 2cb4: ldc_w 5.612
      // 2cb7: ldc_w -44.349
      // 2cba: ldc_w -9.675
      // 2cbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cc6: aastore
      // 2cc7: dup
      // 2cc8: bipush 33
      // 2cca: new net/minecraft/client/animation/Keyframe
      // 2ccd: dup
      // 2cce: ldc 1.58
      // 2cd0: ldc_w 1.913
      // 2cd3: ldc_w -38.16
      // 2cd6: ldc_w -5.639
      // 2cd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cdc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cdf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ce2: aastore
      // 2ce3: dup
      // 2ce4: bipush 34
      // 2ce6: new net/minecraft/client/animation/Keyframe
      // 2ce9: dup
      // 2cea: ldc 1.62
      // 2cec: ldc_w 0.929
      // 2cef: ldc_w -33.227
      // 2cf2: ldc_w -4.235
      // 2cf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cfe: aastore
      // 2cff: dup
      // 2d00: bipush 35
      // 2d02: new net/minecraft/client/animation/Keyframe
      // 2d05: dup
      // 2d06: ldc 1.67
      // 2d08: ldc_w 5.263
      // 2d0b: ldc_w -35.932
      // 2d0e: ldc_w -7.725
      // 2d11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d1a: aastore
      // 2d1b: dup
      // 2d1c: bipush 36
      // 2d1e: new net/minecraft/client/animation/Keyframe
      // 2d21: dup
      // 2d22: ldc 1.71
      // 2d24: ldc_w 11.837
      // 2d27: ldc_w -38.885
      // 2d2a: ldc_w -13.283
      // 2d2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d36: aastore
      // 2d37: dup
      // 2d38: bipush 37
      // 2d3a: new net/minecraft/client/animation/Keyframe
      // 2d3d: dup
      // 2d3e: ldc 1.75
      // 2d40: ldc_w 12.621
      // 2d43: ldc_w -36.832
      // 2d46: ldc_w -13.273
      // 2d49: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d52: aastore
      // 2d53: dup
      // 2d54: bipush 38
      // 2d56: new net/minecraft/client/animation/Keyframe
      // 2d59: dup
      // 2d5a: ldc 1.79
      // 2d5c: ldc_w 9.687
      // 2d5f: ldc_w -31.504
      // 2d62: ldc_w -9.811
      // 2d65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d6e: aastore
      // 2d6f: dup
      // 2d70: bipush 39
      // 2d72: new net/minecraft/client/animation/Keyframe
      // 2d75: dup
      // 2d76: ldc 1.83
      // 2d78: ldc_w 7.109
      // 2d7b: ldc_w -25.459
      // 2d7e: ldc_w -6.873
      // 2d81: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d84: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d87: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d8a: aastore
      // 2d8b: dup
      // 2d8c: bipush 40
      // 2d8e: new net/minecraft/client/animation/Keyframe
      // 2d91: dup
      // 2d92: ldc 1.88
      // 2d94: ldc_w 5.483
      // 2d97: ldc_w -20.085
      // 2d9a: ldc_w -4.971
      // 2d9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2da0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2da3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2da6: aastore
      // 2da7: dup
      // 2da8: bipush 41
      // 2daa: new net/minecraft/client/animation/Keyframe
      // 2dad: dup
      // 2dae: ldc 1.92
      // 2db0: ldc_w 3.147
      // 2db3: ldc_w -13.177
      // 2db6: ldc_w -2.758
      // 2db9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dc2: aastore
      // 2dc3: dup
      // 2dc4: bipush 42
      // 2dc6: new net/minecraft/client/animation/Keyframe
      // 2dc9: dup
      // 2dca: ldc 1.96
      // 2dcc: ldc_w 5.013
      // 2dcf: ldc_w -14.506
      // 2dd2: ldc_w -3.809
      // 2dd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ddb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dde: aastore
      // 2ddf: dup
      // 2de0: bipush 43
      // 2de2: new net/minecraft/client/animation/Keyframe
      // 2de5: dup
      // 2de6: fconst_2
      // 2de7: ldc_w 16.456
      // 2dea: ldc_w -26.905
      // 2ded: ldc_w -12.832
      // 2df0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2df3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2df6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2df9: aastore
      // 2dfa: dup
      // 2dfb: bipush 44
      // 2dfd: new net/minecraft/client/animation/Keyframe
      // 2e00: dup
      // 2e01: ldc 2.04
      // 2e03: ldc_w 40.062
      // 2e06: ldc_w -37.202
      // 2e09: ldc_w -34.113
      // 2e0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e15: aastore
      // 2e16: dup
      // 2e17: bipush 45
      // 2e19: new net/minecraft/client/animation/Keyframe
      // 2e1c: dup
      // 2e1d: ldc 2.08
      // 2e1f: ldc_w 57.72
      // 2e22: ldc_w -39.814
      // 2e25: ldc_w -50.637
      // 2e28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e31: aastore
      // 2e32: dup
      // 2e33: bipush 46
      // 2e35: new net/minecraft/client/animation/Keyframe
      // 2e38: dup
      // 2e39: ldc_w 2.12
      // 2e3c: ldc_w 33.069
      // 2e3f: ldc_w -34.363
      // 2e42: ldc_w -27.138
      // 2e45: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e4e: aastore
      // 2e4f: dup
      // 2e50: bipush 47
      // 2e52: new net/minecraft/client/animation/Keyframe
      // 2e55: dup
      // 2e56: ldc_w 2.17
      // 2e59: ldc_w 8.685
      // 2e5c: ldc_w -16.228
      // 2e5f: ldc_w -5.857
      // 2e62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e6b: aastore
      // 2e6c: dup
      // 2e6d: bipush 48
      // 2e6f: new net/minecraft/client/animation/Keyframe
      // 2e72: dup
      // 2e73: ldc 2.21
      // 2e75: ldc_w 0.994
      // 2e78: ldc_w -2.336
      // 2e7b: ldc_w -0.568
      // 2e7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e87: aastore
      // 2e88: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2e8b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2e8e: ldc_w "CTR_lowerarm_left"
      // 2e91: new net/minecraft/client/animation/AnimationChannel
      // 2e94: dup
      // 2e95: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2e98: bipush 3
      // 2e99: anewarray 28
      // 2e9c: dup
      // 2e9d: bipush 0
      // 2e9e: new net/minecraft/client/animation/Keyframe
      // 2ea1: dup
      // 2ea2: ldc 1.25
      // 2ea4: dconst_1
      // 2ea5: dconst_1
      // 2ea6: dconst_1
      // 2ea7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2eaa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ead: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eb0: aastore
      // 2eb1: dup
      // 2eb2: bipush 1
      // 2eb3: new net/minecraft/client/animation/Keyframe
      // 2eb6: dup
      // 2eb7: ldc 1.29
      // 2eb9: dconst_1
      // 2eba: ldc2_w 2.4049999713897705
      // 2ebd: dconst_1
      // 2ebe: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2ec1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ec4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ec7: aastore
      // 2ec8: dup
      // 2ec9: bipush 2
      // 2eca: new net/minecraft/client/animation/Keyframe
      // 2ecd: dup
      // 2ece: ldc 1.33
      // 2ed0: dconst_1
      // 2ed1: dconst_1
      // 2ed2: dconst_1
      // 2ed3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2ed6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ed9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2edc: aastore
      // 2edd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2ee0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ee3: ldc_w "CTR_upperarm_left.001"
      // 2ee6: new net/minecraft/client/animation/AnimationChannel
      // 2ee9: dup
      // 2eea: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2eed: bipush 53
      // 2eef: anewarray 28
      // 2ef2: dup
      // 2ef3: bipush 0
      // 2ef4: new net/minecraft/client/animation/Keyframe
      // 2ef7: dup
      // 2ef8: fconst_0
      // 2ef9: ldc_w 3.205
      // 2efc: ldc_w -22.398
      // 2eff: ldc_w -1.822
      // 2f02: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f0b: aastore
      // 2f0c: dup
      // 2f0d: bipush 1
      // 2f0e: new net/minecraft/client/animation/Keyframe
      // 2f11: dup
      // 2f12: ldc 0.04
      // 2f14: ldc_w 3.177
      // 2f17: ldc_w -21.271
      // 2f1a: ldc_w -1.715
      // 2f1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f26: aastore
      // 2f27: dup
      // 2f28: bipush 2
      // 2f29: new net/minecraft/client/animation/Keyframe
      // 2f2c: dup
      // 2f2d: ldc 0.08
      // 2f2f: ldc_w 3.028
      // 2f32: ldc_w -18.144
      // 2f35: ldc_w -1.459
      // 2f38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f41: aastore
      // 2f42: dup
      // 2f43: bipush 3
      // 2f44: new net/minecraft/client/animation/Keyframe
      // 2f47: dup
      // 2f48: ldc 0.12
      // 2f4a: ldc_w 2.634
      // 2f4d: ldc_w -13.508
      // 2f50: ldc_w -1.276
      // 2f53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f5c: aastore
      // 2f5d: dup
      // 2f5e: bipush 4
      // 2f5f: new net/minecraft/client/animation/Keyframe
      // 2f62: dup
      // 2f63: ldc 0.17
      // 2f65: ldc_w 1.566
      // 2f68: ldc_w -7.761
      // 2f6b: ldc_w -1.776
      // 2f6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f77: aastore
      // 2f78: dup
      // 2f79: bipush 5
      // 2f7a: new net/minecraft/client/animation/Keyframe
      // 2f7d: dup
      // 2f7e: ldc 0.21
      // 2f80: ldc_w -8.028
      // 2f83: ldc_w -0.564
      // 2f86: ldc_w -10.719
      // 2f89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f92: aastore
      // 2f93: dup
      // 2f94: bipush 6
      // 2f96: new net/minecraft/client/animation/Keyframe
      // 2f99: dup
      // 2f9a: ldc 0.25
      // 2f9c: ldc_w 8.267
      // 2f9f: ldc_w 9.118
      // 2fa2: ldc_w 6.812
      // 2fa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fae: aastore
      // 2faf: dup
      // 2fb0: bipush 7
      // 2fb2: new net/minecraft/client/animation/Keyframe
      // 2fb5: dup
      // 2fb6: ldc 0.29
      // 2fb8: ldc_w 4.422
      // 2fbb: ldc_w 22.579
      // 2fbe: ldc_w 4.785
      // 2fc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fca: aastore
      // 2fcb: dup
      // 2fcc: bipush 8
      // 2fce: new net/minecraft/client/animation/Keyframe
      // 2fd1: dup
      // 2fd2: ldc 0.33
      // 2fd4: ldc_w 3.822
      // 2fd7: ldc_w 29.01
      // 2fda: ldc_w 5.277
      // 2fdd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fe0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fe3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fe6: aastore
      // 2fe7: dup
      // 2fe8: bipush 9
      // 2fea: new net/minecraft/client/animation/Keyframe
      // 2fed: dup
      // 2fee: ldc 0.38
      // 2ff0: ldc_w 1.243
      // 2ff3: ldc_w 39.797
      // 2ff6: ldc_w 5.754
      // 2ff9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ffc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3002: aastore
      // 3003: dup
      // 3004: bipush 10
      // 3006: new net/minecraft/client/animation/Keyframe
      // 3009: dup
      // 300a: ldc 0.42
      // 300c: ldc_w -2.727
      // 300f: ldc_w 55.328
      // 3012: ldc_w 7.245
      // 3015: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3018: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 301b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 301e: aastore
      // 301f: dup
      // 3020: bipush 11
      // 3022: new net/minecraft/client/animation/Keyframe
      // 3025: dup
      // 3026: ldc 0.46
      // 3028: ldc_w -7.236
      // 302b: ldc_w 69.795
      // 302e: ldc_w 10.343
      // 3031: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3034: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3037: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 303a: aastore
      // 303b: dup
      // 303c: bipush 12
      // 303e: new net/minecraft/client/animation/Keyframe
      // 3041: dup
      // 3042: ldc 0.5
      // 3044: ldc_w -11.573
      // 3047: ldc_w 79.368
      // 304a: ldc_w 14.696
      // 304d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3050: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3053: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3056: aastore
      // 3057: dup
      // 3058: bipush 13
      // 305a: new net/minecraft/client/animation/Keyframe
      // 305d: dup
      // 305e: ldc 0.54
      // 3060: ldc_w -17.392
      // 3063: ldc_w 86.635
      // 3066: ldc_w 20.602
      // 3069: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 306c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 306f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3072: aastore
      // 3073: dup
      // 3074: bipush 14
      // 3076: new net/minecraft/client/animation/Keyframe
      // 3079: dup
      // 307a: ldc 0.58
      // 307c: ldc_w -24.089
      // 307f: ldc_w 90.505
      // 3082: ldc_w 27.447
      // 3085: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3088: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 308b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 308e: aastore
      // 308f: dup
      // 3090: bipush 15
      // 3092: new net/minecraft/client/animation/Keyframe
      // 3095: dup
      // 3096: ldc 0.62
      // 3098: ldc_w -29.924
      // 309b: ldc_w 91.301
      // 309e: ldc_w 33.446
      // 30a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30aa: aastore
      // 30ab: dup
      // 30ac: bipush 16
      // 30ae: new net/minecraft/client/animation/Keyframe
      // 30b1: dup
      // 30b2: ldc 0.67
      // 30b4: ldc_w -34.448
      // 30b7: ldc_w 90.704
      // 30ba: ldc_w 38.117
      // 30bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30c6: aastore
      // 30c7: dup
      // 30c8: bipush 17
      // 30ca: new net/minecraft/client/animation/Keyframe
      // 30cd: dup
      // 30ce: ldc_w 0.71
      // 30d1: ldc_w -38.513
      // 30d4: ldc_w 89.29
      // 30d7: ldc_w 42.336
      // 30da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30e3: aastore
      // 30e4: dup
      // 30e5: bipush 18
      // 30e7: new net/minecraft/client/animation/Keyframe
      // 30ea: dup
      // 30eb: ldc 0.75
      // 30ed: ldc_w -41.941
      // 30f0: ldc_w 87.503
      // 30f3: ldc_w 45.913
      // 30f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30ff: aastore
      // 3100: dup
      // 3101: bipush 19
      // 3103: new net/minecraft/client/animation/Keyframe
      // 3106: dup
      // 3107: ldc_w 0.79
      // 310a: ldc_w -44.623
      // 310d: ldc_w 85.851
      // 3110: ldc_w 48.722
      // 3113: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3116: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3119: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 311c: aastore
      // 311d: dup
      // 311e: bipush 20
      // 3120: new net/minecraft/client/animation/Keyframe
      // 3123: dup
      // 3124: ldc_w 0.83
      // 3127: ldc_w -46.484
      // 312a: ldc_w 84.86
      // 312d: ldc_w 50.665
      // 3130: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3133: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3136: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3139: aastore
      // 313a: dup
      // 313b: bipush 21
      // 313d: new net/minecraft/client/animation/Keyframe
      // 3140: dup
      // 3141: ldc_w 0.88
      // 3144: ldc_w -47.434
      // 3147: ldc_w 84.991
      // 314a: ldc_w 51.631
      // 314d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3150: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3153: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3156: aastore
      // 3157: dup
      // 3158: bipush 22
      // 315a: new net/minecraft/client/animation/Keyframe
      // 315d: dup
      // 315e: ldc_w 0.92
      // 3161: ldc_w -47.915
      // 3164: ldc_w 85.838
      // 3167: ldc_w 52.089
      // 316a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 316d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3173: aastore
      // 3174: dup
      // 3175: bipush 23
      // 3177: new net/minecraft/client/animation/Keyframe
      // 317a: dup
      // 317b: ldc 0.96
      // 317d: ldc_w -48.575
      // 3180: ldc_w 86.608
      // 3183: ldc_w 52.734
      // 3186: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3189: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 318c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 318f: aastore
      // 3190: dup
      // 3191: bipush 24
      // 3193: new net/minecraft/client/animation/Keyframe
      // 3196: dup
      // 3197: fconst_1
      // 3198: ldc_w -49.461
      // 319b: ldc_w 87.164
      // 319e: ldc_w 53.616
      // 31a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31aa: aastore
      // 31ab: dup
      // 31ac: bipush 25
      // 31ae: new net/minecraft/client/animation/Keyframe
      // 31b1: dup
      // 31b2: ldc 1.08
      // 31b4: ldc_w -51.352
      // 31b7: ldc_w 87.805
      // 31ba: ldc_w 55.518
      // 31bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31c6: aastore
      // 31c7: dup
      // 31c8: bipush 26
      // 31ca: new net/minecraft/client/animation/Keyframe
      // 31cd: dup
      // 31ce: ldc 1.12
      // 31d0: ldc_w -51.998
      // 31d3: ldc_w 88.194
      // 31d6: ldc_w 56.162
      // 31d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31e2: aastore
      // 31e3: dup
      // 31e4: bipush 27
      // 31e6: new net/minecraft/client/animation/Keyframe
      // 31e9: dup
      // 31ea: ldc 1.17
      // 31ec: ldc_w -38.047
      // 31ef: ldc_w 85.156
      // 31f2: ldc_w 41.999
      // 31f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31fe: aastore
      // 31ff: dup
      // 3200: bipush 28
      // 3202: new net/minecraft/client/animation/Keyframe
      // 3205: dup
      // 3206: ldc 1.21
      // 3208: ldc_w -17.504
      // 320b: ldc_w 64.427
      // 320e: ldc_w 21.362
      // 3211: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3214: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3217: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 321a: aastore
      // 321b: dup
      // 321c: bipush 29
      // 321e: new net/minecraft/client/animation/Keyframe
      // 3221: dup
      // 3222: ldc 1.25
      // 3224: ldc_w -15.598
      // 3227: ldc_w 51.531
      // 322a: ldc_w 19.922
      // 322d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3230: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3233: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3236: aastore
      // 3237: dup
      // 3238: bipush 30
      // 323a: new net/minecraft/client/animation/Keyframe
      // 323d: dup
      // 323e: ldc 1.29
      // 3240: ldc_w 2.251
      // 3243: ldc_w 18.43
      // 3246: ldc_w 2.734
      // 3249: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 324c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 324f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3252: aastore
      // 3253: dup
      // 3254: bipush 31
      // 3256: new net/minecraft/client/animation/Keyframe
      // 3259: dup
      // 325a: ldc 1.33
      // 325c: ldc_w 2.66
      // 325f: ldc_w 15.664
      // 3262: ldc_w 2.894
      // 3265: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3268: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 326b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 326e: aastore
      // 326f: dup
      // 3270: bipush 32
      // 3272: new net/minecraft/client/animation/Keyframe
      // 3275: dup
      // 3276: ldc 1.38
      // 3278: ldc_w 3.559
      // 327b: ldc_w 11.144
      // 327e: ldc_w 3.727
      // 3281: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3284: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3287: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 328a: aastore
      // 328b: dup
      // 328c: bipush 33
      // 328e: new net/minecraft/client/animation/Keyframe
      // 3291: dup
      // 3292: ldc 1.42
      // 3294: ldc_w 3.105
      // 3297: ldc_w 12.904
      // 329a: ldc_w 3.35
      // 329d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32a6: aastore
      // 32a7: dup
      // 32a8: bipush 34
      // 32aa: new net/minecraft/client/animation/Keyframe
      // 32ad: dup
      // 32ae: ldc 1.46
      // 32b0: ldc_w 2.311
      // 32b3: ldc_w 17.685
      // 32b6: ldc_w 2.821
      // 32b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32c2: aastore
      // 32c3: dup
      // 32c4: bipush 35
      // 32c6: new net/minecraft/client/animation/Keyframe
      // 32c9: dup
      // 32ca: ldc 1.5
      // 32cc: ldc_w 1.733
      // 32cf: ldc_w 23.14
      // 32d2: ldc_w 2.617
      // 32d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32de: aastore
      // 32df: dup
      // 32e0: bipush 36
      // 32e2: new net/minecraft/client/animation/Keyframe
      // 32e5: dup
      // 32e6: ldc 1.54
      // 32e8: ldc_w 1.389
      // 32eb: ldc_w 27.08
      // 32ee: ldc_w 2.609
      // 32f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32fa: aastore
      // 32fb: dup
      // 32fc: bipush 37
      // 32fe: new net/minecraft/client/animation/Keyframe
      // 3301: dup
      // 3302: ldc 1.58
      // 3304: ldc_w 1.177
      // 3307: ldc_w 30.006
      // 330a: ldc_w 2.621
      // 330d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3310: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3313: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3316: aastore
      // 3317: dup
      // 3318: bipush 38
      // 331a: new net/minecraft/client/animation/Keyframe
      // 331d: dup
      // 331e: ldc 1.62
      // 3320: ldc_w 1.017
      // 3323: ldc_w 32.047
      // 3326: ldc_w 2.667
      // 3329: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 332c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 332f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3332: aastore
      // 3333: dup
      // 3334: bipush 39
      // 3336: new net/minecraft/client/animation/Keyframe
      // 3339: dup
      // 333a: ldc 1.67
      // 333c: ldc_w 0.829
      // 333f: ldc_w 32.528
      // 3342: ldc_w 2.853
      // 3345: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3348: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 334b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 334e: aastore
      // 334f: dup
      // 3350: bipush 40
      // 3352: new net/minecraft/client/animation/Keyframe
      // 3355: dup
      // 3356: ldc 1.71
      // 3358: ldc_w 0.618
      // 335b: ldc_w 32.805
      // 335e: ldc_w 3.079
      // 3361: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3364: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3367: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 336a: aastore
      // 336b: dup
      // 336c: bipush 41
      // 336e: new net/minecraft/client/animation/Keyframe
      // 3371: dup
      // 3372: ldc 1.75
      // 3374: ldc_w 0.464
      // 3377: ldc_w 33.667
      // 337a: ldc_w 3.202
      // 337d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3380: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3383: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3386: aastore
      // 3387: dup
      // 3388: bipush 42
      // 338a: new net/minecraft/client/animation/Keyframe
      // 338d: dup
      // 338e: ldc 1.79
      // 3390: ldc_w 0.382
      // 3393: ldc_w 34.868
      // 3396: ldc_w 3.227
      // 3399: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 339c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 339f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33a2: aastore
      // 33a3: dup
      // 33a4: bipush 43
      // 33a6: new net/minecraft/client/animation/Keyframe
      // 33a9: dup
      // 33aa: ldc 1.83
      // 33ac: ldc_w 0.312
      // 33af: ldc_w 36.382
      // 33b2: ldc_w 3.224
      // 33b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33be: aastore
      // 33bf: dup
      // 33c0: bipush 44
      // 33c2: new net/minecraft/client/animation/Keyframe
      // 33c5: dup
      // 33c6: ldc 1.88
      // 33c8: ldc 0.235
      // 33ca: ldc_w 38.003
      // 33cd: ldc_w 3.231
      // 33d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33d9: aastore
      // 33da: dup
      // 33db: bipush 45
      // 33dd: new net/minecraft/client/animation/Keyframe
      // 33e0: dup
      // 33e1: ldc 1.92
      // 33e3: ldc_w 0.218
      // 33e6: ldc_w 39.89
      // 33e9: ldc_w 3.167
      // 33ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33f5: aastore
      // 33f6: dup
      // 33f7: bipush 46
      // 33f9: new net/minecraft/client/animation/Keyframe
      // 33fc: dup
      // 33fd: ldc 1.96
      // 33ff: ldc_w 0.424
      // 3402: ldc_w 36.57
      // 3405: ldc_w 3.088
      // 3408: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 340b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 340e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3411: aastore
      // 3412: dup
      // 3413: bipush 47
      // 3415: new net/minecraft/client/animation/Keyframe
      // 3418: dup
      // 3419: fconst_2
      // 341a: ldc_w 1.279
      // 341d: ldc_w 25.414
      // 3420: ldc_w 2.904
      // 3423: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3426: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3429: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 342c: aastore
      // 342d: dup
      // 342e: bipush 48
      // 3430: new net/minecraft/client/animation/Keyframe
      // 3433: dup
      // 3434: ldc 2.04
      // 3436: ldc_w 3.909
      // 3439: ldc_w 12.832
      // 343c: ldc_w 2.844
      // 343f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3442: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3445: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3448: aastore
      // 3449: dup
      // 344a: bipush 49
      // 344c: new net/minecraft/client/animation/Keyframe
      // 344f: dup
      // 3450: ldc 2.08
      // 3452: ldc_w 2.435
      // 3455: ldc_w -3.273
      // 3458: ldc_w -4.574
      // 345b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 345e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3461: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3464: aastore
      // 3465: dup
      // 3466: bipush 50
      // 3468: new net/minecraft/client/animation/Keyframe
      // 346b: dup
      // 346c: ldc_w 2.12
      // 346f: ldc_w 8.031
      // 3472: ldc_w -21.198
      // 3475: ldc_w -3.355
      // 3478: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 347b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 347e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3481: aastore
      // 3482: dup
      // 3483: bipush 51
      // 3485: new net/minecraft/client/animation/Keyframe
      // 3488: dup
      // 3489: ldc_w 2.17
      // 348c: ldc_w 5.456
      // 348f: ldc_w -25.653
      // 3492: ldc_w -2.603
      // 3495: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3498: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 349b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 349e: aastore
      // 349f: dup
      // 34a0: bipush 52
      // 34a2: new net/minecraft/client/animation/Keyframe
      // 34a5: dup
      // 34a6: ldc 2.21
      // 34a8: ldc_w 3.205
      // 34ab: ldc_w -22.398
      // 34ae: ldc_w -1.822
      // 34b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34ba: aastore
      // 34bb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 34be: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 34c1: ldc_w "CTR_upperarm_left.001"
      // 34c4: new net/minecraft/client/animation/AnimationChannel
      // 34c7: dup
      // 34c8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 34cb: bipush 41
      // 34cd: anewarray 28
      // 34d0: dup
      // 34d1: bipush 0
      // 34d2: new net/minecraft/client/animation/Keyframe
      // 34d5: dup
      // 34d6: ldc 0.04
      // 34d8: dconst_1
      // 34d9: dconst_1
      // 34da: ldc2_w 1.7869999408721924
      // 34dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34e6: aastore
      // 34e7: dup
      // 34e8: bipush 1
      // 34e9: new net/minecraft/client/animation/Keyframe
      // 34ec: dup
      // 34ed: ldc 0.08
      // 34ef: dconst_1
      // 34f0: dconst_1
      // 34f1: ldc2_w 1.784000039100647
      // 34f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34fd: aastore
      // 34fe: dup
      // 34ff: bipush 2
      // 3500: new net/minecraft/client/animation/Keyframe
      // 3503: dup
      // 3504: ldc 0.12
      // 3506: dconst_1
      // 3507: dconst_1
      // 3508: ldc2_w 1.7710000276565552
      // 350b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 350e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3511: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3514: aastore
      // 3515: dup
      // 3516: bipush 3
      // 3517: new net/minecraft/client/animation/Keyframe
      // 351a: dup
      // 351b: ldc 0.17
      // 351d: dconst_1
      // 351e: dconst_1
      // 351f: ldc2_w 1.7380000352859497
      // 3522: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3525: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3528: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 352b: aastore
      // 352c: dup
      // 352d: bipush 4
      // 352e: new net/minecraft/client/animation/Keyframe
      // 3531: dup
      // 3532: ldc 0.21
      // 3534: dconst_1
      // 3535: dconst_1
      // 3536: ldc2_w 1.6799999475479126
      // 3539: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 353c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 353f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3542: aastore
      // 3543: dup
      // 3544: bipush 5
      // 3545: new net/minecraft/client/animation/Keyframe
      // 3548: dup
      // 3549: ldc 0.25
      // 354b: dconst_1
      // 354c: dconst_1
      // 354d: ldc2_w 1.6319999694824219
      // 3550: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3553: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3556: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3559: aastore
      // 355a: dup
      // 355b: bipush 6
      // 355d: new net/minecraft/client/animation/Keyframe
      // 3560: dup
      // 3561: ldc 0.29
      // 3563: dconst_1
      // 3564: dconst_1
      // 3565: ldc2_w 1.6579999923706055
      // 3568: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 356b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 356e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3571: aastore
      // 3572: dup
      // 3573: bipush 7
      // 3575: new net/minecraft/client/animation/Keyframe
      // 3578: dup
      // 3579: ldc 0.33
      // 357b: dconst_1
      // 357c: dconst_1
      // 357d: ldc2_w 1.5980000495910645
      // 3580: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3583: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3586: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3589: aastore
      // 358a: dup
      // 358b: bipush 8
      // 358d: new net/minecraft/client/animation/Keyframe
      // 3590: dup
      // 3591: ldc 0.38
      // 3593: dconst_1
      // 3594: dconst_1
      // 3595: ldc2_w 1.6339999437332153
      // 3598: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 359b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 359e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35a1: aastore
      // 35a2: dup
      // 35a3: bipush 9
      // 35a5: new net/minecraft/client/animation/Keyframe
      // 35a8: dup
      // 35a9: ldc 0.42
      // 35ab: dconst_1
      // 35ac: dconst_1
      // 35ad: ldc2_w 1.7569999694824219
      // 35b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35b9: aastore
      // 35ba: dup
      // 35bb: bipush 10
      // 35bd: new net/minecraft/client/animation/Keyframe
      // 35c0: dup
      // 35c1: ldc 0.46
      // 35c3: dconst_1
      // 35c4: dconst_1
      // 35c5: ldc2_w 1.8380000591278076
      // 35c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35d1: aastore
      // 35d2: dup
      // 35d3: bipush 11
      // 35d5: new net/minecraft/client/animation/Keyframe
      // 35d8: dup
      // 35d9: ldc 0.5
      // 35db: dconst_1
      // 35dc: dconst_1
      // 35dd: ldc2_w 1.843000054359436
      // 35e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e9: aastore
      // 35ea: dup
      // 35eb: bipush 12
      // 35ed: new net/minecraft/client/animation/Keyframe
      // 35f0: dup
      // 35f1: ldc 0.54
      // 35f3: dconst_1
      // 35f4: dconst_1
      // 35f5: ldc2_w 1.8040000200271606
      // 35f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3601: aastore
      // 3602: dup
      // 3603: bipush 13
      // 3605: new net/minecraft/client/animation/Keyframe
      // 3608: dup
      // 3609: ldc 0.62
      // 360b: dconst_1
      // 360c: dconst_1
      // 360d: ldc2_w 1.6510000228881836
      // 3610: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3613: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3616: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3619: aastore
      // 361a: dup
      // 361b: bipush 14
      // 361d: new net/minecraft/client/animation/Keyframe
      // 3620: dup
      // 3621: ldc 0.67
      // 3623: dconst_1
      // 3624: dconst_1
      // 3625: ldc2_w 1.5829999446868896
      // 3628: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 362b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 362e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3631: aastore
      // 3632: dup
      // 3633: bipush 15
      // 3635: new net/minecraft/client/animation/Keyframe
      // 3638: dup
      // 3639: ldc_w 0.71
      // 363c: dconst_1
      // 363d: dconst_1
      // 363e: ldc2_w 1.5190000534057617
      // 3641: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3644: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3647: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 364a: aastore
      // 364b: dup
      // 364c: bipush 16
      // 364e: new net/minecraft/client/animation/Keyframe
      // 3651: dup
      // 3652: ldc 0.75
      // 3654: dconst_1
      // 3655: dconst_1
      // 3656: ldc2_w 1.465000033378601
      // 3659: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 365c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 365f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3662: aastore
      // 3663: dup
      // 3664: bipush 17
      // 3666: new net/minecraft/client/animation/Keyframe
      // 3669: dup
      // 366a: ldc_w 0.79
      // 366d: dconst_1
      // 366e: dconst_1
      // 366f: ldc2_w 1.4220000505447388
      // 3672: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3675: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3678: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 367b: aastore
      // 367c: dup
      // 367d: bipush 18
      // 367f: new net/minecraft/client/animation/Keyframe
      // 3682: dup
      // 3683: ldc_w 0.83
      // 3686: dconst_1
      // 3687: dconst_1
      // 3688: ldc2_w 1.3969999551773071
      // 368b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 368e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3691: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3694: aastore
      // 3695: dup
      // 3696: bipush 19
      // 3698: new net/minecraft/client/animation/Keyframe
      // 369b: dup
      // 369c: ldc_w 0.88
      // 369f: dconst_1
      // 36a0: dconst_1
      // 36a1: ldc2_w 1.3899999856948853
      // 36a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36ad: aastore
      // 36ae: dup
      // 36af: bipush 20
      // 36b1: new net/minecraft/client/animation/Keyframe
      // 36b4: dup
      // 36b5: ldc 0.96
      // 36b7: dconst_1
      // 36b8: dconst_1
      // 36b9: ldc2_w 1.3930000066757202
      // 36bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36c5: aastore
      // 36c6: dup
      // 36c7: bipush 21
      // 36c9: new net/minecraft/client/animation/Keyframe
      // 36cc: dup
      // 36cd: ldc 1.12
      // 36cf: dconst_1
      // 36d0: dconst_1
      // 36d1: ldc2_w 1.347000002861023
      // 36d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36dd: aastore
      // 36de: dup
      // 36df: bipush 22
      // 36e1: new net/minecraft/client/animation/Keyframe
      // 36e4: dup
      // 36e5: ldc 1.17
      // 36e7: dconst_1
      // 36e8: dconst_1
      // 36e9: ldc2_w 1.4470000267028809
      // 36ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36f5: aastore
      // 36f6: dup
      // 36f7: bipush 23
      // 36f9: new net/minecraft/client/animation/Keyframe
      // 36fc: dup
      // 36fd: ldc 1.21
      // 36ff: dconst_1
      // 3700: dconst_1
      // 3701: ldc2_w 2.117000102996826
      // 3704: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3707: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 370a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 370d: aastore
      // 370e: dup
      // 370f: bipush 24
      // 3711: new net/minecraft/client/animation/Keyframe
      // 3714: dup
      // 3715: ldc 1.25
      // 3717: dconst_1
      // 3718: dconst_1
      // 3719: ldc2_w 2.1389999389648438
      // 371c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 371f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3722: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3725: aastore
      // 3726: dup
      // 3727: bipush 25
      // 3729: new net/minecraft/client/animation/Keyframe
      // 372c: dup
      // 372d: ldc 1.29
      // 372f: dconst_1
      // 3730: dconst_1
      // 3731: ldc2_w 1.4579999446868896
      // 3734: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3737: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 373a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 373d: aastore
      // 373e: dup
      // 373f: bipush 26
      // 3741: new net/minecraft/client/animation/Keyframe
      // 3744: dup
      // 3745: ldc 1.33
      // 3747: dconst_1
      // 3748: dconst_1
      // 3749: ldc2_w 1.4819999933242798
      // 374c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 374f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3752: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3755: aastore
      // 3756: dup
      // 3757: bipush 27
      // 3759: new net/minecraft/client/animation/Keyframe
      // 375c: dup
      // 375d: ldc 1.42
      // 375f: dconst_1
      // 3760: dconst_1
      // 3761: ldc2_w 1.5759999752044678
      // 3764: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3767: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 376a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 376d: aastore
      // 376e: dup
      // 376f: bipush 28
      // 3771: new net/minecraft/client/animation/Keyframe
      // 3774: dup
      // 3775: ldc 1.54
      // 3777: dconst_1
      // 3778: dconst_1
      // 3779: ldc2_w 1.7669999599456787
      // 377c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 377f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3782: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3785: aastore
      // 3786: dup
      // 3787: bipush 29
      // 3789: new net/minecraft/client/animation/Keyframe
      // 378c: dup
      // 378d: ldc 1.58
      // 378f: dconst_1
      // 3790: dconst_1
      // 3791: ldc2_w 1.815000057220459
      // 3794: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3797: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 379a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 379d: aastore
      // 379e: dup
      // 379f: bipush 30
      // 37a1: new net/minecraft/client/animation/Keyframe
      // 37a4: dup
      // 37a5: ldc 1.62
      // 37a7: dconst_1
      // 37a8: dconst_1
      // 37a9: ldc2_w 1.8480000495910645
      // 37ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 37af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37b5: aastore
      // 37b6: dup
      // 37b7: bipush 31
      // 37b9: new net/minecraft/client/animation/Keyframe
      // 37bc: dup
      // 37bd: ldc 1.71
      // 37bf: dconst_1
      // 37c0: dconst_1
      // 37c1: ldc2_w 1.878000020980835
      // 37c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 37c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37cd: aastore
      // 37ce: dup
      // 37cf: bipush 32
      // 37d1: new net/minecraft/client/animation/Keyframe
      // 37d4: dup
      // 37d5: ldc 1.88
      // 37d7: dconst_1
      // 37d8: dconst_1
      // 37d9: ldc2_w 1.8619999885559082
      // 37dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 37df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37e5: aastore
      // 37e6: dup
      // 37e7: bipush 33
      // 37e9: new net/minecraft/client/animation/Keyframe
      // 37ec: dup
      // 37ed: ldc 1.92
      // 37ef: dconst_1
      // 37f0: dconst_1
      // 37f1: ldc2_w 1.8630000352859497
      // 37f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 37f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37fd: aastore
      // 37fe: dup
      // 37ff: bipush 34
      // 3801: new net/minecraft/client/animation/Keyframe
      // 3804: dup
      // 3805: ldc 1.96
      // 3807: dconst_1
      // 3808: dconst_1
      // 3809: ldc2_w 1.7829999923706055
      // 380c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 380f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3812: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3815: aastore
      // 3816: dup
      // 3817: bipush 35
      // 3819: new net/minecraft/client/animation/Keyframe
      // 381c: dup
      // 381d: fconst_2
      // 381e: dconst_1
      // 381f: dconst_1
      // 3820: ldc2_w 1.5789999961853027
      // 3823: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3826: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3829: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 382c: aastore
      // 382d: dup
      // 382e: bipush 36
      // 3830: new net/minecraft/client/animation/Keyframe
      // 3833: dup
      // 3834: ldc 2.04
      // 3836: dconst_1
      // 3837: dconst_1
      // 3838: ldc2_w 1.343999981880188
      // 383b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 383e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3841: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3844: aastore
      // 3845: dup
      // 3846: bipush 37
      // 3848: new net/minecraft/client/animation/Keyframe
      // 384b: dup
      // 384c: ldc 2.08
      // 384e: dconst_1
      // 384f: dconst_1
      // 3850: ldc2_w 1.1859999895095825
      // 3853: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3856: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3859: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 385c: aastore
      // 385d: dup
      // 385e: bipush 38
      // 3860: new net/minecraft/client/animation/Keyframe
      // 3863: dup
      // 3864: ldc_w 2.12
      // 3867: dconst_1
      // 3868: dconst_1
      // 3869: ldc2_w 1.2760000228881836
      // 386c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 386f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3872: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3875: aastore
      // 3876: dup
      // 3877: bipush 39
      // 3879: new net/minecraft/client/animation/Keyframe
      // 387c: dup
      // 387d: ldc_w 2.17
      // 3880: dconst_1
      // 3881: dconst_1
      // 3882: ldc2_w 1.5859999656677246
      // 3885: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3888: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 388b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 388e: aastore
      // 388f: dup
      // 3890: bipush 40
      // 3892: new net/minecraft/client/animation/Keyframe
      // 3895: dup
      // 3896: ldc 2.21
      // 3898: dconst_1
      // 3899: dconst_1
      // 389a: ldc2_w 1.7869999408721924
      // 389d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 38a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38a6: aastore
      // 38a7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 38aa: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 38ad: ldc_w "CTR_shoulder_right"
      // 38b0: new net/minecraft/client/animation/AnimationChannel
      // 38b3: dup
      // 38b4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 38b7: bipush 31
      // 38b9: anewarray 28
      // 38bc: dup
      // 38bd: bipush 0
      // 38be: new net/minecraft/client/animation/Keyframe
      // 38c1: dup
      // 38c2: fconst_0
      // 38c3: ldc_w 0.016
      // 38c6: ldc_w -1.1
      // 38c9: ldc_w -0.172
      // 38cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38d5: aastore
      // 38d6: dup
      // 38d7: bipush 1
      // 38d8: new net/minecraft/client/animation/Keyframe
      // 38db: dup
      // 38dc: ldc 0.04
      // 38de: ldc_w -0.038
      // 38e1: ldc_w -1.165
      // 38e4: ldc_w -0.315
      // 38e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38f0: aastore
      // 38f1: dup
      // 38f2: bipush 2
      // 38f3: new net/minecraft/client/animation/Keyframe
      // 38f6: dup
      // 38f7: ldc 0.08
      // 38f9: ldc_w -0.188
      // 38fc: ldc_w -1.343
      // 38ff: ldc_w -0.706
      // 3902: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3905: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3908: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 390b: aastore
      // 390c: dup
      // 390d: bipush 3
      // 390e: new net/minecraft/client/animation/Keyframe
      // 3911: dup
      // 3912: ldc 0.17
      // 3914: ldc_w -0.688
      // 3917: ldc_w -1.937
      // 391a: ldc_w -2.012
      // 391d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3920: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3923: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3926: aastore
      // 3927: dup
      // 3928: bipush 4
      // 3929: new net/minecraft/client/animation/Keyframe
      // 392c: dup
      // 392d: ldc 0.21
      // 392f: ldc_w -0.997
      // 3932: ldc_w -2.303
      // 3935: ldc_w -2.816
      // 3938: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 393b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 393e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3941: aastore
      // 3942: dup
      // 3943: bipush 5
      // 3944: new net/minecraft/client/animation/Keyframe
      // 3947: dup
      // 3948: ldc 0.25
      // 394a: ldc_w -1.316
      // 394d: ldc_w -2.681
      // 3950: ldc_w -3.649
      // 3953: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3956: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3959: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 395c: aastore
      // 395d: dup
      // 395e: bipush 6
      // 3960: new net/minecraft/client/animation/Keyframe
      // 3963: dup
      // 3964: ldc 0.29
      // 3966: ldc_w -1.624
      // 3969: ldc_w -3.047
      // 396c: ldc_w -4.454
      // 396f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3972: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3975: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3978: aastore
      // 3979: dup
      // 397a: bipush 7
      // 397c: new net/minecraft/client/animation/Keyframe
      // 397f: dup
      // 3980: ldc 0.33
      // 3982: ldc_w -1.901
      // 3985: ldc_w -3.376
      // 3988: ldc_w -5.176
      // 398b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 398e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3991: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3994: aastore
      // 3995: dup
      // 3996: bipush 8
      // 3998: new net/minecraft/client/animation/Keyframe
      // 399b: dup
      // 399c: ldc 0.38
      // 399e: ldc_w -2.125
      // 39a1: ldc_w -3.641
      // 39a4: ldc_w -5.76
      // 39a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39b0: aastore
      // 39b1: dup
      // 39b2: bipush 9
      // 39b4: new net/minecraft/client/animation/Keyframe
      // 39b7: dup
      // 39b8: ldc 0.42
      // 39ba: ldc_w -2.274
      // 39bd: ldc_w -3.819
      // 39c0: ldc_w -6.15
      // 39c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39cc: aastore
      // 39cd: dup
      // 39ce: bipush 10
      // 39d0: new net/minecraft/client/animation/Keyframe
      // 39d3: dup
      // 39d4: ldc 0.46
      // 39d6: ldc_w -2.329
      // 39d9: ldc_w -3.884
      // 39dc: ldc_w -6.293
      // 39df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39e8: aastore
      // 39e9: dup
      // 39ea: bipush 11
      // 39ec: new net/minecraft/client/animation/Keyframe
      // 39ef: dup
      // 39f0: ldc 1.12
      // 39f2: ldc_w -2.329
      // 39f5: ldc_w -3.884
      // 39f8: ldc_w -6.293
      // 39fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a04: aastore
      // 3a05: dup
      // 3a06: bipush 12
      // 3a08: new net/minecraft/client/animation/Keyframe
      // 3a0b: dup
      // 3a0c: ldc 1.17
      // 3a0e: ldc_w -1.937
      // 3a11: ldc_w -3.404
      // 3a14: ldc_w -4.715
      // 3a17: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a20: aastore
      // 3a21: dup
      // 3a22: bipush 13
      // 3a24: new net/minecraft/client/animation/Keyframe
      // 3a27: dup
      // 3a28: ldc 1.21
      // 3a2a: ldc_w -1.052
      // 3a2d: ldc_w -2.069
      // 3a30: ldc_w -1.244
      // 3a33: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a3c: aastore
      // 3a3d: dup
      // 3a3e: bipush 14
      // 3a40: new net/minecraft/client/animation/Keyframe
      // 3a43: dup
      // 3a44: ldc 1.25
      // 3a46: ldc_w -0.112
      // 3a49: ldc_w -0.036
      // 3a4c: ldc_w 2.227
      // 3a4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a58: aastore
      // 3a59: dup
      // 3a5a: bipush 15
      // 3a5c: new net/minecraft/client/animation/Keyframe
      // 3a5f: dup
      // 3a60: ldc 1.29
      // 3a62: ldc_w 0.444
      // 3a65: ldc_w 2.536
      // 3a68: ldc_w 3.805
      // 3a6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a74: aastore
      // 3a75: dup
      // 3a76: bipush 16
      // 3a78: new net/minecraft/client/animation/Keyframe
      // 3a7b: dup
      // 3a7c: ldc 1.33
      // 3a7e: ldc_w 0.589
      // 3a81: ldc_w 5.426
      // 3a84: ldc_w 3.262
      // 3a87: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a90: aastore
      // 3a91: dup
      // 3a92: bipush 17
      // 3a94: new net/minecraft/client/animation/Keyframe
      // 3a97: dup
      // 3a98: ldc 1.38
      // 3a9a: ldc_w 0.636
      // 3a9d: ldc_w 8.159
      // 3aa0: ldc_w 1.955
      // 3aa3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3aa6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aa9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aac: aastore
      // 3aad: dup
      // 3aae: bipush 18
      // 3ab0: new net/minecraft/client/animation/Keyframe
      // 3ab3: dup
      // 3ab4: ldc 1.42
      // 3ab6: ldc_w 0.66
      // 3ab9: ldc_w 10.195
      // 3abc: ldc_w 0.37
      // 3abf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ac2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ac5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ac8: aastore
      // 3ac9: dup
      // 3aca: bipush 19
      // 3acc: new net/minecraft/client/animation/Keyframe
      // 3acf: dup
      // 3ad0: ldc 1.46
      // 3ad2: ldc_w 0.736
      // 3ad5: ldc_w 10.993
      // 3ad8: ldc_w -1.009
      // 3adb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ade: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ae1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ae4: aastore
      // 3ae5: dup
      // 3ae6: bipush 20
      // 3ae8: new net/minecraft/client/animation/Keyframe
      // 3aeb: dup
      // 3aec: ldc 1.5
      // 3aee: ldc_w 0.912
      // 3af1: ldc_w 10.512
      // 3af4: ldc_w -1.868
      // 3af7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3afa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3afd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b00: aastore
      // 3b01: dup
      // 3b02: bipush 21
      // 3b04: new net/minecraft/client/animation/Keyframe
      // 3b07: dup
      // 3b08: ldc 1.54
      // 3b0a: ldc_w 1.136
      // 3b0d: ldc_w 9.453
      // 3b10: ldc_w -2.309
      // 3b13: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b1c: aastore
      // 3b1d: dup
      // 3b1e: bipush 22
      // 3b20: new net/minecraft/client/animation/Keyframe
      // 3b23: dup
      // 3b24: ldc 1.58
      // 3b26: ldc_w 1.328
      // 3b29: ldc_w 8.394
      // 3b2c: ldc_w -2.472
      // 3b2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b38: aastore
      // 3b39: dup
      // 3b3a: bipush 23
      // 3b3c: new net/minecraft/client/animation/Keyframe
      // 3b3f: dup
      // 3b40: ldc 1.62
      // 3b42: ldc_w 1.409
      // 3b45: ldc_w 7.913
      // 3b48: ldc_w -2.495
      // 3b4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b54: aastore
      // 3b55: dup
      // 3b56: bipush 24
      // 3b58: new net/minecraft/client/animation/Keyframe
      // 3b5b: dup
      // 3b5c: ldc 1.96
      // 3b5e: ldc_w 1.409
      // 3b61: ldc_w 7.913
      // 3b64: ldc_w -2.495
      // 3b67: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b70: aastore
      // 3b71: dup
      // 3b72: bipush 25
      // 3b74: new net/minecraft/client/animation/Keyframe
      // 3b77: dup
      // 3b78: fconst_2
      // 3b79: ldc_w 0.348
      // 3b7c: ldc_w 3.853
      // 3b7f: ldc_w -0.099
      // 3b82: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b8b: aastore
      // 3b8c: dup
      // 3b8d: bipush 26
      // 3b8f: new net/minecraft/client/animation/Keyframe
      // 3b92: dup
      // 3b93: ldc 2.04
      // 3b95: ldc_w -0.713
      // 3b98: ldc_w -0.451
      // 3b9b: ldc_w 2.298
      // 3b9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ba1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ba4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ba7: aastore
      // 3ba8: dup
      // 3ba9: bipush 27
      // 3bab: new net/minecraft/client/animation/Keyframe
      // 3bae: dup
      // 3baf: ldc 2.08
      // 3bb1: ldc_w -0.599
      // 3bb4: ldc_w -0.826
      // 3bb7: ldc_w 1.912
      // 3bba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bc3: aastore
      // 3bc4: dup
      // 3bc5: bipush 28
      // 3bc7: new net/minecraft/client/animation/Keyframe
      // 3bca: dup
      // 3bcb: ldc_w 2.12
      // 3bce: ldc_w -0.348
      // 3bd1: ldc_w -1.019
      // 3bd4: ldc_w 1.063
      // 3bd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3be0: aastore
      // 3be1: dup
      // 3be2: bipush 29
      // 3be4: new net/minecraft/client/animation/Keyframe
      // 3be7: dup
      // 3be8: ldc_w 2.17
      // 3beb: ldc_w -0.098
      // 3bee: ldc_w -1.09
      // 3bf1: ldc_w 0.214
      // 3bf4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bf7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bfd: aastore
      // 3bfe: dup
      // 3bff: bipush 30
      // 3c01: new net/minecraft/client/animation/Keyframe
      // 3c04: dup
      // 3c05: ldc 2.21
      // 3c07: ldc_w 0.016
      // 3c0a: ldc_w -1.1
      // 3c0d: ldc_w -0.172
      // 3c10: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c19: aastore
      // 3c1a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3c1d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3c20: ldc_w "CTR_upperarm_right"
      // 3c23: new net/minecraft/client/animation/AnimationChannel
      // 3c26: dup
      // 3c27: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3c2a: bipush 54
      // 3c2c: anewarray 28
      // 3c2f: dup
      // 3c30: bipush 0
      // 3c31: new net/minecraft/client/animation/Keyframe
      // 3c34: dup
      // 3c35: fconst_0
      // 3c36: ldc_w 0.657
      // 3c39: ldc_w -1.984
      // 3c3c: ldc_w 0.431
      // 3c3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c48: aastore
      // 3c49: dup
      // 3c4a: bipush 1
      // 3c4b: new net/minecraft/client/animation/Keyframe
      // 3c4e: dup
      // 3c4f: ldc 0.04
      // 3c51: ldc_w 1.4
      // 3c54: ldc_w -1.499
      // 3c57: ldc_w 0.716
      // 3c5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c63: aastore
      // 3c64: dup
      // 3c65: bipush 2
      // 3c66: new net/minecraft/client/animation/Keyframe
      // 3c69: dup
      // 3c6a: ldc 0.08
      // 3c6c: ldc_w 2.799
      // 3c6f: ldc_w -0.109
      // 3c72: ldc_w 1.784
      // 3c75: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c7e: aastore
      // 3c7f: dup
      // 3c80: bipush 3
      // 3c81: new net/minecraft/client/animation/Keyframe
      // 3c84: dup
      // 3c85: ldc 0.12
      // 3c87: ldc_w 4.233
      // 3c8a: ldc 1.71
      // 3c8c: ldc_w 3.866
      // 3c8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c98: aastore
      // 3c99: dup
      // 3c9a: bipush 4
      // 3c9b: new net/minecraft/client/animation/Keyframe
      // 3c9e: dup
      // 3c9f: ldc 0.17
      // 3ca1: ldc_w 5.75
      // 3ca4: ldc_w 3.008
      // 3ca7: ldc_w 7.833
      // 3caa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cb3: aastore
      // 3cb4: dup
      // 3cb5: bipush 5
      // 3cb6: new net/minecraft/client/animation/Keyframe
      // 3cb9: dup
      // 3cba: ldc 0.21
      // 3cbc: ldc_w 6.768
      // 3cbf: ldc_w 2.983
      // 3cc2: ldc_w 12.098
      // 3cc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ccb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cce: aastore
      // 3ccf: dup
      // 3cd0: bipush 6
      // 3cd2: new net/minecraft/client/animation/Keyframe
      // 3cd5: dup
      // 3cd6: ldc 0.25
      // 3cd8: ldc_w 6.185
      // 3cdb: ldc_w 2.413
      // 3cde: ldc_w 10.596
      // 3ce1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ce4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ce7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cea: aastore
      // 3ceb: dup
      // 3cec: bipush 7
      // 3cee: new net/minecraft/client/animation/Keyframe
      // 3cf1: dup
      // 3cf2: ldc 0.29
      // 3cf4: ldc_w 5.723
      // 3cf7: ldc_w 2.289
      // 3cfa: ldc_w 11.829
      // 3cfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d06: aastore
      // 3d07: dup
      // 3d08: bipush 8
      // 3d0a: new net/minecraft/client/animation/Keyframe
      // 3d0d: dup
      // 3d0e: ldc 0.33
      // 3d10: ldc_w 5.383
      // 3d13: ldc_w 2.607
      // 3d16: ldc_w 16.482
      // 3d19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d22: aastore
      // 3d23: dup
      // 3d24: bipush 9
      // 3d26: new net/minecraft/client/animation/Keyframe
      // 3d29: dup
      // 3d2a: ldc 0.38
      // 3d2c: ldc_w 4.93
      // 3d2f: ldc_w 3.483
      // 3d32: ldc_w 25.817
      // 3d35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d3e: aastore
      // 3d3f: dup
      // 3d40: bipush 10
      // 3d42: new net/minecraft/client/animation/Keyframe
      // 3d45: dup
      // 3d46: ldc 0.42
      // 3d48: ldc_w 3.905
      // 3d4b: ldc_w 4.923
      // 3d4e: ldc_w 39.58
      // 3d51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d5a: aastore
      // 3d5b: dup
      // 3d5c: bipush 11
      // 3d5e: new net/minecraft/client/animation/Keyframe
      // 3d61: dup
      // 3d62: ldc 0.46
      // 3d64: ldc_w 2.037
      // 3d67: ldc_w 6.519
      // 3d6a: ldc_w 55.263
      // 3d6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d76: aastore
      // 3d77: dup
      // 3d78: bipush 12
      // 3d7a: new net/minecraft/client/animation/Keyframe
      // 3d7d: dup
      // 3d7e: ldc 0.5
      // 3d80: ldc_w -0.222
      // 3d83: ldc_w 8.016
      // 3d86: ldc_w 69.886
      // 3d89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d92: aastore
      // 3d93: dup
      // 3d94: bipush 13
      // 3d96: new net/minecraft/client/animation/Keyframe
      // 3d99: dup
      // 3d9a: ldc 0.54
      // 3d9c: ldc_w -1.495
      // 3d9f: ldc_w 9.063
      // 3da2: ldc_w 80.01
      // 3da5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3da8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dae: aastore
      // 3daf: dup
      // 3db0: bipush 14
      // 3db2: new net/minecraft/client/animation/Keyframe
      // 3db5: dup
      // 3db6: ldc 0.58
      // 3db8: ldc_w -1.52
      // 3dbb: ldc_w 9.614
      // 3dbe: ldc_w 87.315
      // 3dc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dca: aastore
      // 3dcb: dup
      // 3dcc: bipush 15
      // 3dce: new net/minecraft/client/animation/Keyframe
      // 3dd1: dup
      // 3dd2: ldc 0.62
      // 3dd4: ldc_w -1.097
      // 3dd7: ldc_w 10.017
      // 3dda: ldc_w 95.327
      // 3ddd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3de0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3de3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3de6: aastore
      // 3de7: dup
      // 3de8: bipush 16
      // 3dea: new net/minecraft/client/animation/Keyframe
      // 3ded: dup
      // 3dee: ldc 0.67
      // 3df0: ldc_w -0.31
      // 3df3: ldc_w 10.086
      // 3df6: ldc_w 102.825
      // 3df9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e02: aastore
      // 3e03: dup
      // 3e04: bipush 17
      // 3e06: new net/minecraft/client/animation/Keyframe
      // 3e09: dup
      // 3e0a: ldc_w 0.71
      // 3e0d: ldc 0.689
      // 3e0f: ldc_w 9.791
      // 3e12: ldc_w 109.517
      // 3e15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e1e: aastore
      // 3e1f: dup
      // 3e20: bipush 18
      // 3e22: new net/minecraft/client/animation/Keyframe
      // 3e25: dup
      // 3e26: ldc 0.75
      // 3e28: ldc_w 1.771
      // 3e2b: ldc_w 9.157
      // 3e2e: ldc_w 115.136
      // 3e31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e3a: aastore
      // 3e3b: dup
      // 3e3c: bipush 19
      // 3e3e: new net/minecraft/client/animation/Keyframe
      // 3e41: dup
      // 3e42: ldc_w 0.79
      // 3e45: ldc_w 2.857
      // 3e48: ldc_w 8.246
      // 3e4b: ldc_w 119.45
      // 3e4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e57: aastore
      // 3e58: dup
      // 3e59: bipush 20
      // 3e5b: new net/minecraft/client/animation/Keyframe
      // 3e5e: dup
      // 3e5f: ldc_w 0.83
      // 3e62: ldc_w 3.925
      // 3e65: ldc_w 7.15
      // 3e68: ldc_w 122.265
      // 3e6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e74: aastore
      // 3e75: dup
      // 3e76: bipush 21
      // 3e78: new net/minecraft/client/animation/Keyframe
      // 3e7b: dup
      // 3e7c: ldc_w 0.88
      // 3e7f: ldc_w 5.002
      // 3e82: ldc_w 5.979
      // 3e85: ldc_w 123.416
      // 3e88: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e91: aastore
      // 3e92: dup
      // 3e93: bipush 22
      // 3e95: new net/minecraft/client/animation/Keyframe
      // 3e98: dup
      // 3e99: ldc_w 0.92
      // 3e9c: ldc_w 6.09
      // 3e9f: ldc_w 4.855
      // 3ea2: ldc_w 123.668
      // 3ea5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ea8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eae: aastore
      // 3eaf: dup
      // 3eb0: bipush 23
      // 3eb2: new net/minecraft/client/animation/Keyframe
      // 3eb5: dup
      // 3eb6: ldc 0.96
      // 3eb8: ldc_w 7.204
      // 3ebb: ldc_w 3.885
      // 3ebe: ldc_w 123.87
      // 3ec1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ec4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ec7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eca: aastore
      // 3ecb: dup
      // 3ecc: bipush 24
      // 3ece: new net/minecraft/client/animation/Keyframe
      // 3ed1: dup
      // 3ed2: fconst_1
      // 3ed3: ldc_w 8.204
      // 3ed6: ldc_w 3.082
      // 3ed9: ldc_w 125.785
      // 3edc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3edf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ee2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ee5: aastore
      // 3ee6: dup
      // 3ee7: bipush 25
      // 3ee9: new net/minecraft/client/animation/Keyframe
      // 3eec: dup
      // 3eed: ldc 1.04
      // 3eef: ldc_w 8.772
      // 3ef2: ldc_w 2.351
      // 3ef5: ldc_w 130.437
      // 3ef8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3efb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3efe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f01: aastore
      // 3f02: dup
      // 3f03: bipush 26
      // 3f05: new net/minecraft/client/animation/Keyframe
      // 3f08: dup
      // 3f09: ldc 1.08
      // 3f0b: ldc_w 8.749
      // 3f0e: ldc_w 1.731
      // 3f11: ldc_w 136.708
      // 3f14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f1d: aastore
      // 3f1e: dup
      // 3f1f: bipush 27
      // 3f21: new net/minecraft/client/animation/Keyframe
      // 3f24: dup
      // 3f25: ldc 1.12
      // 3f27: ldc_w 8.148
      // 3f2a: ldc_w 1.394
      // 3f2d: ldc_w 143.49
      // 3f30: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f39: aastore
      // 3f3a: dup
      // 3f3b: bipush 28
      // 3f3d: new net/minecraft/client/animation/Keyframe
      // 3f40: dup
      // 3f41: ldc 1.17
      // 3f43: ldc_w 5.55
      // 3f46: ldc_w 6.907
      // 3f49: ldc_w 149.881
      // 3f4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f55: aastore
      // 3f56: dup
      // 3f57: bipush 29
      // 3f59: new net/minecraft/client/animation/Keyframe
      // 3f5c: dup
      // 3f5d: ldc 1.21
      // 3f5f: ldc_w 15.028
      // 3f62: ldc_w 16.204
      // 3f65: ldc_w 179.205
      // 3f68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f71: aastore
      // 3f72: dup
      // 3f73: bipush 30
      // 3f75: new net/minecraft/client/animation/Keyframe
      // 3f78: dup
      // 3f79: ldc 1.25
      // 3f7b: ldc_w -1.037
      // 3f7e: ldc_w 15.462
      // 3f81: ldc_w 181.766
      // 3f84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f8d: aastore
      // 3f8e: dup
      // 3f8f: bipush 31
      // 3f91: new net/minecraft/client/animation/Keyframe
      // 3f94: dup
      // 3f95: ldc 1.29
      // 3f97: ldc_w -18.334
      // 3f9a: ldc_w 52.423
      // 3f9d: ldc_w 70.188
      // 3fa0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fa3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fa6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fa9: aastore
      // 3faa: dup
      // 3fab: bipush 32
      // 3fad: new net/minecraft/client/animation/Keyframe
      // 3fb0: dup
      // 3fb1: ldc 1.33
      // 3fb3: ldc_w 17.331
      // 3fb6: ldc_w 55.679
      // 3fb9: ldc_w 80.926
      // 3fbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fc5: aastore
      // 3fc6: dup
      // 3fc7: bipush 33
      // 3fc9: new net/minecraft/client/animation/Keyframe
      // 3fcc: dup
      // 3fcd: ldc 1.38
      // 3fcf: ldc_w 29.659
      // 3fd2: ldc_w 54.258
      // 3fd5: ldc_w 89.03
      // 3fd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fdb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fe1: aastore
      // 3fe2: dup
      // 3fe3: bipush 34
      // 3fe5: new net/minecraft/client/animation/Keyframe
      // 3fe8: dup
      // 3fe9: ldc 1.42
      // 3feb: ldc_w 20.541
      // 3fee: ldc_w 54.095
      // 3ff1: ldc_w 73.949
      // 3ff4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ff7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ffa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ffd: aastore
      // 3ffe: dup
      // 3fff: bipush 35
      // 4001: new net/minecraft/client/animation/Keyframe
      // 4004: dup
      // 4005: ldc 1.46
      // 4007: ldc_w 6.785
      // 400a: ldc_w 52.85
      // 400d: ldc_w 56.909
      // 4010: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4013: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4016: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4019: aastore
      // 401a: dup
      // 401b: bipush 36
      // 401d: new net/minecraft/client/animation/Keyframe
      // 4020: dup
      // 4021: ldc 1.5
      // 4023: ldc_w -5.154
      // 4026: ldc_w 50.909
      // 4029: ldc_w 47.038
      // 402c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 402f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4032: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4035: aastore
      // 4036: dup
      // 4037: bipush 37
      // 4039: new net/minecraft/client/animation/Keyframe
      // 403c: dup
      // 403d: ldc 1.54
      // 403f: ldc_w -9.864
      // 4042: ldc_w 50.23
      // 4045: ldc_w 45.693
      // 4048: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 404b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 404e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4051: aastore
      // 4052: dup
      // 4053: bipush 38
      // 4055: new net/minecraft/client/animation/Keyframe
      // 4058: dup
      // 4059: ldc 1.58
      // 405b: ldc_w -9.982
      // 405e: ldc_w 50.187
      // 4061: ldc_w 46.054
      // 4064: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4067: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 406a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 406d: aastore
      // 406e: dup
      // 406f: bipush 39
      // 4071: new net/minecraft/client/animation/Keyframe
      // 4074: dup
      // 4075: ldc 1.62
      // 4077: ldc_w -7.997
      // 407a: ldc_w 48.358
      // 407d: ldc_w 46.762
      // 4080: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4083: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4086: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4089: aastore
      // 408a: dup
      // 408b: bipush 40
      // 408d: new net/minecraft/client/animation/Keyframe
      // 4090: dup
      // 4091: ldc 1.67
      // 4093: ldc_w -8.429
      // 4096: ldc_w 44.548
      // 4099: ldc_w 43.925
      // 409c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 409f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40a5: aastore
      // 40a6: dup
      // 40a7: bipush 41
      // 40a9: new net/minecraft/client/animation/Keyframe
      // 40ac: dup
      // 40ad: ldc 1.71
      // 40af: ldc_w -11.413
      // 40b2: ldc_w 39.286
      // 40b5: ldc_w 35.036
      // 40b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40c1: aastore
      // 40c2: dup
      // 40c3: bipush 42
      // 40c5: new net/minecraft/client/animation/Keyframe
      // 40c8: dup
      // 40c9: ldc 1.75
      // 40cb: ldc_w -13.077
      // 40ce: ldc_w 33.036
      // 40d1: ldc_w 20.702
      // 40d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40dd: aastore
      // 40de: dup
      // 40df: bipush 43
      // 40e1: new net/minecraft/client/animation/Keyframe
      // 40e4: dup
      // 40e5: ldc 1.79
      // 40e7: ldc_w -14.435
      // 40ea: ldc_w 26.429
      // 40ed: ldc_w 3.526
      // 40f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40f9: aastore
      // 40fa: dup
      // 40fb: bipush 44
      // 40fd: new net/minecraft/client/animation/Keyframe
      // 4100: dup
      // 4101: ldc 1.83
      // 4103: ldc_w -14.835
      // 4106: ldc_w 20.024
      // 4109: ldc_w -11.7
      // 410c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 410f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4112: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4115: aastore
      // 4116: dup
      // 4117: bipush 45
      // 4119: new net/minecraft/client/animation/Keyframe
      // 411c: dup
      // 411d: ldc 1.88
      // 411f: ldc_w -13.431
      // 4122: ldc_w 14.564
      // 4125: ldc_w -21.31
      // 4128: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 412b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 412e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4131: aastore
      // 4132: dup
      // 4133: bipush 46
      // 4135: new net/minecraft/client/animation/Keyframe
      // 4138: dup
      // 4139: ldc 1.92
      // 413b: ldc_w -10.757
      // 413e: ldc_w 10.084
      // 4141: ldc_w -25.208
      // 4144: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4147: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 414a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 414d: aastore
      // 414e: dup
      // 414f: bipush 47
      // 4151: new net/minecraft/client/animation/Keyframe
      // 4154: dup
      // 4155: ldc 1.96
      // 4157: ldc_w -7.92
      // 415a: ldc_w 6.23
      // 415d: ldc_w -25.287
      // 4160: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4163: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4166: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4169: aastore
      // 416a: dup
      // 416b: bipush 48
      // 416d: new net/minecraft/client/animation/Keyframe
      // 4170: dup
      // 4171: fconst_2
      // 4172: ldc_w -3.247
      // 4175: ldc_w 4.933
      // 4178: ldc_w -14.225
      // 417b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 417e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4181: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4184: aastore
      // 4185: dup
      // 4186: bipush 49
      // 4188: new net/minecraft/client/animation/Keyframe
      // 418b: dup
      // 418c: ldc 2.04
      // 418e: ldc_w 1.346
      // 4191: ldc_w 3.516
      // 4194: ldc_w 0.397
      // 4197: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 419a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 419d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41a0: aastore
      // 41a1: dup
      // 41a2: bipush 50
      // 41a4: new net/minecraft/client/animation/Keyframe
      // 41a7: dup
      // 41a8: ldc 2.08
      // 41aa: ldc_w 1.924
      // 41ad: ldc_w 1.096
      // 41b0: ldc_w 2.449
      // 41b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41bc: aastore
      // 41bd: dup
      // 41be: bipush 51
      // 41c0: new net/minecraft/client/animation/Keyframe
      // 41c3: dup
      // 41c4: ldc_w 2.12
      // 41c7: ldc_w 1.511
      // 41ca: ldc_w -0.685
      // 41cd: ldc_w 1.806
      // 41d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41d9: aastore
      // 41da: dup
      // 41db: bipush 52
      // 41dd: new net/minecraft/client/animation/Keyframe
      // 41e0: dup
      // 41e1: ldc_w 2.17
      // 41e4: ldc_w 0.943
      // 41e7: ldc_w -1.677
      // 41ea: ldc_w 0.897
      // 41ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41f6: aastore
      // 41f7: dup
      // 41f8: bipush 53
      // 41fa: new net/minecraft/client/animation/Keyframe
      // 41fd: dup
      // 41fe: ldc 2.21
      // 4200: ldc_w 0.657
      // 4203: ldc_w -1.984
      // 4206: ldc_w 0.431
      // 4209: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 420c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 420f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4212: aastore
      // 4213: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4216: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4219: ldc_w "CTR_upperarm_right"
      // 421c: new net/minecraft/client/animation/AnimationChannel
      // 421f: dup
      // 4220: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4223: bipush 30
      // 4225: anewarray 28
      // 4228: dup
      // 4229: bipush 0
      // 422a: new net/minecraft/client/animation/Keyframe
      // 422d: dup
      // 422e: fconst_0
      // 422f: fconst_0
      // 4230: fconst_0
      // 4231: fconst_0
      // 4232: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4235: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4238: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 423b: aastore
      // 423c: dup
      // 423d: bipush 1
      // 423e: new net/minecraft/client/animation/Keyframe
      // 4241: dup
      // 4242: ldc 0.04
      // 4244: ldc_w 0.039
      // 4247: ldc_w -0.069
      // 424a: ldc_w 0.003
      // 424d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4250: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4253: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4256: aastore
      // 4257: dup
      // 4258: bipush 2
      // 4259: new net/minecraft/client/animation/Keyframe
      // 425c: dup
      // 425d: ldc 0.08
      // 425f: ldc_w 0.14
      // 4262: ldc_w -0.252
      // 4265: ldc_w 0.011
      // 4268: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 426b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 426e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4271: aastore
      // 4272: dup
      // 4273: bipush 3
      // 4274: new net/minecraft/client/animation/Keyframe
      // 4277: dup
      // 4278: ldc 0.12
      // 427a: ldc_w 0.284
      // 427d: ldc_w -0.51
      // 4280: ldc_w 0.022
      // 4283: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4286: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4289: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 428c: aastore
      // 428d: dup
      // 428e: bipush 4
      // 428f: new net/minecraft/client/animation/Keyframe
      // 4292: dup
      // 4293: ldc 0.17
      // 4295: ldc_w 0.449
      // 4298: ldc_w -0.806
      // 429b: ldc_w 0.035
      // 429e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42a7: aastore
      // 42a8: dup
      // 42a9: bipush 5
      // 42aa: new net/minecraft/client/animation/Keyframe
      // 42ad: dup
      // 42ae: ldc 0.21
      // 42b0: ldc_w 0.613
      // 42b3: ldc_w -1.102
      // 42b6: ldc_w 0.048
      // 42b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42c2: aastore
      // 42c3: dup
      // 42c4: bipush 6
      // 42c6: new net/minecraft/client/animation/Keyframe
      // 42c9: dup
      // 42ca: ldc 0.25
      // 42cc: ldc_w 0.757
      // 42cf: ldc_w -1.361
      // 42d2: ldc_w 0.059
      // 42d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42de: aastore
      // 42df: dup
      // 42e0: bipush 7
      // 42e2: new net/minecraft/client/animation/Keyframe
      // 42e5: dup
      // 42e6: ldc 0.29
      // 42e8: ldc_w 0.859
      // 42eb: ldc_w -1.543
      // 42ee: ldc_w 0.067
      // 42f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42fa: aastore
      // 42fb: dup
      // 42fc: bipush 8
      // 42fe: new net/minecraft/client/animation/Keyframe
      // 4301: dup
      // 4302: ldc 0.33
      // 4304: ldc_w 0.897
      // 4307: ldc_w -1.613
      // 430a: ldc_w 0.07
      // 430d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4310: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4313: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4316: aastore
      // 4317: dup
      // 4318: bipush 9
      // 431a: new net/minecraft/client/animation/Keyframe
      // 431d: dup
      // 431e: ldc 0.38
      // 4320: ldc_w 0.841
      // 4323: ldc_w -1.45
      // 4326: ldc_w 0.062
      // 4329: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 432c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 432f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4332: aastore
      // 4333: dup
      // 4334: bipush 10
      // 4336: new net/minecraft/client/animation/Keyframe
      // 4339: dup
      // 433a: ldc 0.42
      // 433c: ldc_w 0.693
      // 433f: ldc_w -1.061
      // 4342: ldc_w 0.045
      // 4345: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4348: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 434b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 434e: aastore
      // 434f: dup
      // 4350: bipush 11
      // 4352: new net/minecraft/client/animation/Keyframe
      // 4355: dup
      // 4356: ldc 0.46
      // 4358: ldc_w 0.483
      // 435b: ldc_w -0.591
      // 435e: ldc_w 0.025
      // 4361: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4364: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4367: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 436a: aastore
      // 436b: dup
      // 436c: bipush 12
      // 436e: new net/minecraft/client/animation/Keyframe
      // 4371: dup
      // 4372: ldc 0.5
      // 4374: ldc_w 0.242
      // 4377: ldc_w -0.188
      // 437a: ldc_w 0.007
      // 437d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4380: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4383: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4386: aastore
      // 4387: dup
      // 4388: bipush 13
      // 438a: new net/minecraft/client/animation/Keyframe
      // 438d: dup
      // 438e: ldc 0.54
      // 4390: fconst_0
      // 4391: fconst_0
      // 4392: fconst_0
      // 4393: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4396: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4399: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 439c: aastore
      // 439d: dup
      // 439e: bipush 14
      // 43a0: new net/minecraft/client/animation/Keyframe
      // 43a3: dup
      // 43a4: ldc 0.62
      // 43a6: ldc_w -0.426
      // 43a9: ldc_w 0.055
      // 43ac: ldc_w 0.024
      // 43af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43b8: aastore
      // 43b9: dup
      // 43ba: bipush 15
      // 43bc: new net/minecraft/client/animation/Keyframe
      // 43bf: dup
      // 43c0: ldc 0.67
      // 43c2: ldc_w -0.612
      // 43c5: ldc_w 0.076
      // 43c8: ldc_w 0.05
      // 43cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43d4: aastore
      // 43d5: dup
      // 43d6: bipush 16
      // 43d8: new net/minecraft/client/animation/Keyframe
      // 43db: dup
      // 43dc: ldc 0.75
      // 43de: ldc_w -0.931
      // 43e1: ldc_w 0.107
      // 43e4: ldc_w 0.124
      // 43e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43f0: aastore
      // 43f1: dup
      // 43f2: bipush 17
      // 43f4: new net/minecraft/client/animation/Keyframe
      // 43f7: dup
      // 43f8: ldc_w 0.83
      // 43fb: ldc_w -1.182
      // 43fe: ldc_w 0.125
      // 4401: ldc_w 0.212
      // 4404: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4407: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 440a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 440d: aastore
      // 440e: dup
      // 440f: bipush 18
      // 4411: new net/minecraft/client/animation/Keyframe
      // 4414: dup
      // 4415: ldc 0.96
      // 4417: ldc_w -1.438
      // 441a: ldc_w 0.136
      // 441d: ldc_w 0.34
      // 4420: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4423: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4426: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4429: aastore
      // 442a: dup
      // 442b: bipush 19
      // 442d: new net/minecraft/client/animation/Keyframe
      // 4430: dup
      // 4431: ldc 1.04
      // 4433: ldc_w -1.531
      // 4436: ldc_w 0.137
      // 4439: ldc_w 0.401
      // 443c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 443f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4442: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4445: aastore
      // 4446: dup
      // 4447: bipush 20
      // 4449: new net/minecraft/client/animation/Keyframe
      // 444c: dup
      // 444d: ldc 1.12
      // 444f: ldc_w -1.566
      // 4452: ldc_w 0.136
      // 4455: ldc_w 0.424
      // 4458: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 445b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 445e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4461: aastore
      // 4462: dup
      // 4463: bipush 21
      // 4465: new net/minecraft/client/animation/Keyframe
      // 4468: dup
      // 4469: ldc 1.17
      // 446b: ldc_w -2.07
      // 446e: ldc_w -0.197
      // 4471: ldc_w -0.108
      // 4474: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4477: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 447a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 447d: aastore
      // 447e: dup
      // 447f: bipush 22
      // 4481: new net/minecraft/client/animation/Keyframe
      // 4484: dup
      // 4485: ldc 1.21
      // 4487: ldc_w -4.531
      // 448a: ldc_w -0.569
      // 448d: ldc_w -0.437
      // 4490: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4493: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4496: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4499: aastore
      // 449a: dup
      // 449b: bipush 23
      // 449d: new net/minecraft/client/animation/Keyframe
      // 44a0: dup
      // 44a1: ldc 1.25
      // 44a3: ldc_w -6.073
      // 44a6: ldc_w 8.773
      // 44a9: ldc_w -0.549
      // 44ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44b5: aastore
      // 44b6: dup
      // 44b7: bipush 24
      // 44b9: new net/minecraft/client/animation/Keyframe
      // 44bc: dup
      // 44bd: ldc 1.29
      // 44bf: ldc_w -5.623
      // 44c2: ldc_w 8.123
      // 44c5: ldc_w -0.509
      // 44c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44d1: aastore
      // 44d2: dup
      // 44d3: bipush 25
      // 44d5: new net/minecraft/client/animation/Keyframe
      // 44d8: dup
      // 44d9: ldc 1.33
      // 44db: ldc -4.498
      // 44dd: ldc_w 6.498
      // 44e0: ldc_w -0.407
      // 44e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44ec: aastore
      // 44ed: dup
      // 44ee: bipush 26
      // 44f0: new net/minecraft/client/animation/Keyframe
      // 44f3: dup
      // 44f4: ldc 1.38
      // 44f6: ldc_w -3.036
      // 44f9: ldc_w 4.386
      // 44fc: ldc_w -0.275
      // 44ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4502: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4505: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4508: aastore
      // 4509: dup
      // 450a: bipush 27
      // 450c: new net/minecraft/client/animation/Keyframe
      // 450f: dup
      // 4510: ldc 1.42
      // 4512: ldc_w -1.574
      // 4515: ldc_w 2.274
      // 4518: ldc_w -0.142
      // 451b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 451e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4521: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4524: aastore
      // 4525: dup
      // 4526: bipush 28
      // 4528: new net/minecraft/client/animation/Keyframe
      // 452b: dup
      // 452c: ldc 1.46
      // 452e: ldc_w -0.45
      // 4531: ldc_w 0.65
      // 4534: ldc_w -0.041
      // 4537: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 453a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 453d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4540: aastore
      // 4541: dup
      // 4542: bipush 29
      // 4544: new net/minecraft/client/animation/Keyframe
      // 4547: dup
      // 4548: ldc 1.5
      // 454a: fconst_0
      // 454b: fconst_0
      // 454c: fconst_0
      // 454d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4550: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4553: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4556: aastore
      // 4557: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 455a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 455d: ldc_w "CTR_lowerarm_right"
      // 4560: new net/minecraft/client/animation/AnimationChannel
      // 4563: dup
      // 4564: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4567: bipush 53
      // 4569: anewarray 28
      // 456c: dup
      // 456d: bipush 0
      // 456e: new net/minecraft/client/animation/Keyframe
      // 4571: dup
      // 4572: fconst_0
      // 4573: ldc_w -0.021
      // 4576: ldc_w -1.718
      // 4579: ldc_w -0.014
      // 457c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 457f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4582: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4585: aastore
      // 4586: dup
      // 4587: bipush 1
      // 4588: new net/minecraft/client/animation/Keyframe
      // 458b: dup
      // 458c: ldc 0.04
      // 458e: ldc_w -1.161
      // 4591: ldc_w -2.794
      // 4594: ldc_w -0.332
      // 4597: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 459a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 459d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45a0: aastore
      // 45a1: dup
      // 45a2: bipush 2
      // 45a3: new net/minecraft/client/animation/Keyframe
      // 45a6: dup
      // 45a7: ldc 0.08
      // 45a9: ldc_w -2.642
      // 45ac: ldc_w -6.293
      // 45af: ldc_w -0.555
      // 45b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45bb: aastore
      // 45bc: dup
      // 45bd: bipush 3
      // 45be: new net/minecraft/client/animation/Keyframe
      // 45c1: dup
      // 45c2: ldc 0.12
      // 45c4: ldc_w -2.916
      // 45c7: ldc_w -12.799
      // 45ca: ldc_w -0.146
      // 45cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45d6: aastore
      // 45d7: dup
      // 45d8: bipush 4
      // 45d9: new net/minecraft/client/animation/Keyframe
      // 45dc: dup
      // 45dd: ldc 0.17
      // 45df: ldc_w -1.942
      // 45e2: ldc_w -24.028
      // 45e5: ldc_w 0.746
      // 45e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45f1: aastore
      // 45f2: dup
      // 45f3: bipush 5
      // 45f4: new net/minecraft/client/animation/Keyframe
      // 45f7: dup
      // 45f8: ldc 0.21
      // 45fa: ldc_w -0.677
      // 45fd: ldc_w -35.909
      // 4600: ldc_w 1.64
      // 4603: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4606: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4609: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 460c: aastore
      // 460d: dup
      // 460e: bipush 6
      // 4610: new net/minecraft/client/animation/Keyframe
      // 4613: dup
      // 4614: ldc 0.25
      // 4616: ldc_w -0.85
      // 4619: ldc_w -33.889
      // 461c: ldc_w 2.528
      // 461f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4622: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4625: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4628: aastore
      // 4629: dup
      // 462a: bipush 7
      // 462c: new net/minecraft/client/animation/Keyframe
      // 462f: dup
      // 4630: ldc 0.29
      // 4632: ldc_w -0.943
      // 4635: ldc_w -31.832
      // 4638: ldc_w 3.634
      // 463b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 463e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4641: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4644: aastore
      // 4645: dup
      // 4646: bipush 8
      // 4648: new net/minecraft/client/animation/Keyframe
      // 464b: dup
      // 464c: ldc 0.33
      // 464e: ldc_w -0.896
      // 4651: ldc_w -30.007
      // 4654: ldc_w 4.977
      // 4657: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 465a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 465d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4660: aastore
      // 4661: dup
      // 4662: bipush 9
      // 4664: new net/minecraft/client/animation/Keyframe
      // 4667: dup
      // 4668: ldc 0.38
      // 466a: ldc_w -0.424
      // 466d: ldc_w -28.953
      // 4670: ldc_w 6.412
      // 4673: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4676: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4679: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 467c: aastore
      // 467d: dup
      // 467e: bipush 10
      // 4680: new net/minecraft/client/animation/Keyframe
      // 4683: dup
      // 4684: ldc 0.42
      // 4686: ldc_w 0.657
      // 4689: ldc_w -28.833
      // 468c: ldc_w 7.807
      // 468f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4692: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4695: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4698: aastore
      // 4699: dup
      // 469a: bipush 11
      // 469c: new net/minecraft/client/animation/Keyframe
      // 469f: dup
      // 46a0: ldc 0.46
      // 46a2: ldc_w 2.155
      // 46a5: ldc_w -29.479
      // 46a8: ldc_w 9.178
      // 46ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b4: aastore
      // 46b5: dup
      // 46b6: bipush 12
      // 46b8: new net/minecraft/client/animation/Keyframe
      // 46bb: dup
      // 46bc: ldc 0.5
      // 46be: ldc_w 3.781
      // 46c1: ldc_w -30.535
      // 46c4: ldc_w 10.438
      // 46c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46d0: aastore
      // 46d1: dup
      // 46d2: bipush 13
      // 46d4: new net/minecraft/client/animation/Keyframe
      // 46d7: dup
      // 46d8: ldc 0.54
      // 46da: ldc_w 5.212
      // 46dd: ldc_w -31.453
      // 46e0: ldc_w 11.394
      // 46e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46ec: aastore
      // 46ed: dup
      // 46ee: bipush 14
      // 46f0: new net/minecraft/client/animation/Keyframe
      // 46f3: dup
      // 46f4: ldc 0.58
      // 46f6: ldc_w 6.123
      // 46f9: ldc_w -31.543
      // 46fc: ldc_w 11.808
      // 46ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4702: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4705: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4708: aastore
      // 4709: dup
      // 470a: bipush 15
      // 470c: new net/minecraft/client/animation/Keyframe
      // 470f: dup
      // 4710: ldc 0.62
      // 4712: ldc_w 6.298
      // 4715: ldc_w -29.761
      // 4718: ldc_w 11.656
      // 471b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 471e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4721: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4724: aastore
      // 4725: dup
      // 4726: bipush 16
      // 4728: new net/minecraft/client/animation/Keyframe
      // 472b: dup
      // 472c: ldc 0.67
      // 472e: ldc_w 5.759
      // 4731: ldc_w -25.873
      // 4734: ldc_w 11.214
      // 4737: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 473a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 473d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4740: aastore
      // 4741: dup
      // 4742: bipush 17
      // 4744: new net/minecraft/client/animation/Keyframe
      // 4747: dup
      // 4748: ldc_w 0.71
      // 474b: ldc_w 4.621
      // 474e: ldc_w -20.44
      // 4751: ldc_w 10.688
      // 4754: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4757: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 475a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 475d: aastore
      // 475e: dup
      // 475f: bipush 18
      // 4761: new net/minecraft/client/animation/Keyframe
      // 4764: dup
      // 4765: ldc 0.75
      // 4767: ldc_w 3.087
      // 476a: ldc_w -14.281
      // 476d: ldc_w 10.196
      // 4770: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4773: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4776: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4779: aastore
      // 477a: dup
      // 477b: bipush 19
      // 477d: new net/minecraft/client/animation/Keyframe
      // 4780: dup
      // 4781: ldc_w 0.79
      // 4784: ldc_w 1.468
      // 4787: ldc_w -8.426
      // 478a: ldc_w 9.79
      // 478d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4790: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4793: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4796: aastore
      // 4797: dup
      // 4798: bipush 20
      // 479a: new net/minecraft/client/animation/Keyframe
      // 479d: dup
      // 479e: ldc_w 0.83
      // 47a1: ldc_w 0.167
      // 47a4: ldc_w -4.049
      // 47a7: ldc_w 9.503
      // 47aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47b3: aastore
      // 47b4: dup
      // 47b5: bipush 21
      // 47b7: new net/minecraft/client/animation/Keyframe
      // 47ba: dup
      // 47bb: ldc_w 0.88
      // 47be: ldc_w -0.363
      // 47c1: ldc_w -2.339
      // 47c4: ldc_w 9.391
      // 47c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47d0: aastore
      // 47d1: dup
      // 47d2: bipush 22
      // 47d4: new net/minecraft/client/animation/Keyframe
      // 47d7: dup
      // 47d8: ldc 0.96
      // 47da: ldc_w -0.363
      // 47dd: ldc_w -2.339
      // 47e0: ldc_w 9.391
      // 47e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47ec: aastore
      // 47ed: dup
      // 47ee: bipush 23
      // 47f0: new net/minecraft/client/animation/Keyframe
      // 47f3: dup
      // 47f4: fconst_1
      // 47f5: ldc_w -0.363
      // 47f8: ldc_w -1.845
      // 47fb: ldc_w 9.391
      // 47fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4801: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4804: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4807: aastore
      // 4808: dup
      // 4809: bipush 24
      // 480b: new net/minecraft/client/animation/Keyframe
      // 480e: dup
      // 480f: ldc 1.04
      // 4811: ldc_w -0.363
      // 4814: ldc_w -0.571
      // 4817: ldc_w 9.391
      // 481a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 481d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4820: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4823: aastore
      // 4824: dup
      // 4825: bipush 25
      // 4827: new net/minecraft/client/animation/Keyframe
      // 482a: dup
      // 482b: ldc 1.08
      // 482d: ldc_w -0.363
      // 4830: ldc_w 1.171
      // 4833: ldc_w 9.391
      // 4836: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4839: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 483c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 483f: aastore
      // 4840: dup
      // 4841: bipush 26
      // 4843: new net/minecraft/client/animation/Keyframe
      // 4846: dup
      // 4847: ldc 1.12
      // 4849: ldc_w -0.363
      // 484c: ldc_w 3.069
      // 484f: ldc_w 9.391
      // 4852: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4855: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4858: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 485b: aastore
      // 485c: dup
      // 485d: bipush 27
      // 485f: new net/minecraft/client/animation/Keyframe
      // 4862: dup
      // 4863: ldc 1.17
      // 4865: ldc_w -2.143
      // 4868: ldc_w 10.254
      // 486b: ldc_w 7.409
      // 486e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4871: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4874: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4877: aastore
      // 4878: dup
      // 4879: bipush 28
      // 487b: new net/minecraft/client/animation/Keyframe
      // 487e: dup
      // 487f: ldc 1.21
      // 4881: ldc_w -6.515
      // 4884: ldc_w 17.237
      // 4887: ldc_w 4.744
      // 488a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 488d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4890: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4893: aastore
      // 4894: dup
      // 4895: bipush 29
      // 4897: new net/minecraft/client/animation/Keyframe
      // 489a: dup
      // 489b: ldc 1.25
      // 489d: ldc_w -3.951
      // 48a0: ldc_w -0.479
      // 48a3: ldc_w 13.384
      // 48a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48af: aastore
      // 48b0: dup
      // 48b1: bipush 30
      // 48b3: new net/minecraft/client/animation/Keyframe
      // 48b6: dup
      // 48b7: ldc 1.29
      // 48b9: ldc_w 53.144
      // 48bc: ldc_w -15.159
      // 48bf: ldc_w 50.766
      // 48c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48cb: aastore
      // 48cc: dup
      // 48cd: bipush 31
      // 48cf: new net/minecraft/client/animation/Keyframe
      // 48d2: dup
      // 48d3: ldc 1.33
      // 48d5: ldc_w -24.378
      // 48d8: ldc_w 25.225
      // 48db: ldc_w 4.488
      // 48de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48e7: aastore
      // 48e8: dup
      // 48e9: bipush 32
      // 48eb: new net/minecraft/client/animation/Keyframe
      // 48ee: dup
      // 48ef: ldc 1.38
      // 48f1: ldc_w -19.568
      // 48f4: ldc_w 22.347
      // 48f7: ldc_w 6.251
      // 48fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4900: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4903: aastore
      // 4904: dup
      // 4905: bipush 33
      // 4907: new net/minecraft/client/animation/Keyframe
      // 490a: dup
      // 490b: ldc 1.42
      // 490d: ldc_w -16.751
      // 4910: ldc_w 29.543
      // 4913: ldc_w 5.955
      // 4916: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4919: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 491c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 491f: aastore
      // 4920: dup
      // 4921: bipush 34
      // 4923: new net/minecraft/client/animation/Keyframe
      // 4926: dup
      // 4927: ldc 1.46
      // 4929: ldc_w -15.557
      // 492c: ldc_w 38.711
      // 492f: ldc_w 4.777
      // 4932: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 493b: aastore
      // 493c: dup
      // 493d: bipush 35
      // 493f: new net/minecraft/client/animation/Keyframe
      // 4942: dup
      // 4943: ldc 1.5
      // 4945: ldc_w -22.556
      // 4948: ldc_w 45.533
      // 494b: ldc_w -3.802
      // 494e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4951: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4954: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4957: aastore
      // 4958: dup
      // 4959: bipush 36
      // 495b: new net/minecraft/client/animation/Keyframe
      // 495e: dup
      // 495f: ldc 1.54
      // 4961: ldc_w -30.08
      // 4964: ldc_w 45.78
      // 4967: ldc_w -12.433
      // 496a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 496d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4970: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4973: aastore
      // 4974: dup
      // 4975: bipush 37
      // 4977: new net/minecraft/client/animation/Keyframe
      // 497a: dup
      // 497b: ldc 1.58
      // 497d: ldc_w -25.697
      // 4980: ldc_w 42.05
      // 4983: ldc_w -9.155
      // 4986: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4989: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 498c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 498f: aastore
      // 4990: dup
      // 4991: bipush 38
      // 4993: new net/minecraft/client/animation/Keyframe
      // 4996: dup
      // 4997: ldc 1.62
      // 4999: ldc_w -27.499
      // 499c: ldc_w 39.528
      // 499f: ldc_w -12.015
      // 49a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49ab: aastore
      // 49ac: dup
      // 49ad: bipush 39
      // 49af: new net/minecraft/client/animation/Keyframe
      // 49b2: dup
      // 49b3: ldc 1.67
      // 49b5: ldc_w -59.74
      // 49b8: ldc_w 43.499
      // 49bb: ldc_w -44.826
      // 49be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49c7: aastore
      // 49c8: dup
      // 49c9: bipush 40
      // 49cb: new net/minecraft/client/animation/Keyframe
      // 49ce: dup
      // 49cf: ldc 1.71
      // 49d1: ldc_w -137.496
      // 49d4: ldc_w 40.174
      // 49d7: ldc_w -122.656
      // 49da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49e3: aastore
      // 49e4: dup
      // 49e5: bipush 41
      // 49e7: new net/minecraft/client/animation/Keyframe
      // 49ea: dup
      // 49eb: ldc 1.75
      // 49ed: ldc_w -155.827
      // 49f0: ldc_w 32.109
      // 49f3: ldc_w -141.105
      // 49f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49ff: aastore
      // 4a00: dup
      // 4a01: bipush 42
      // 4a03: new net/minecraft/client/animation/Keyframe
      // 4a06: dup
      // 4a07: ldc 1.79
      // 4a09: ldc_w -160.149
      // 4a0c: ldc_w 27.686
      // 4a0f: ldc_w -145.867
      // 4a12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a1b: aastore
      // 4a1c: dup
      // 4a1d: bipush 43
      // 4a1f: new net/minecraft/client/animation/Keyframe
      // 4a22: dup
      // 4a23: ldc 1.83
      // 4a25: ldc_w -158.243
      // 4a28: ldc_w 29.191
      // 4a2b: ldc_w -144.982
      // 4a2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a37: aastore
      // 4a38: dup
      // 4a39: bipush 44
      // 4a3b: new net/minecraft/client/animation/Keyframe
      // 4a3e: dup
      // 4a3f: ldc 1.88
      // 4a41: ldc_w -147.381
      // 4a44: ldc_w 35.868
      // 4a47: ldc_w -135.711
      // 4a4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a53: aastore
      // 4a54: dup
      // 4a55: bipush 45
      // 4a57: new net/minecraft/client/animation/Keyframe
      // 4a5a: dup
      // 4a5b: ldc 1.92
      // 4a5d: ldc_w -104.616
      // 4a60: ldc_w 42.893
      // 4a63: ldc_w -94.863
      // 4a66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a6f: aastore
      // 4a70: dup
      // 4a71: bipush 46
      // 4a73: new net/minecraft/client/animation/Keyframe
      // 4a76: dup
      // 4a77: ldc 1.96
      // 4a79: ldc_w -39.727
      // 4a7c: ldc_w 39.871
      // 4a7f: ldc_w -31.931
      // 4a82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a8b: aastore
      // 4a8c: dup
      // 4a8d: bipush 47
      // 4a8f: new net/minecraft/client/animation/Keyframe
      // 4a92: dup
      // 4a93: fconst_2
      // 4a94: ldc_w -11.309
      // 4a97: ldc_w 21.828
      // 4a9a: ldc_w -5.987
      // 4a9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aa0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4aa3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4aa6: aastore
      // 4aa7: dup
      // 4aa8: bipush 48
      // 4aaa: new net/minecraft/client/animation/Keyframe
      // 4aad: dup
      // 4aae: ldc 2.04
      // 4ab0: ldc_w -3.905
      // 4ab3: ldc_w -1.766
      // 4ab6: ldc_w -1.005
      // 4ab9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4abc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4abf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ac2: aastore
      // 4ac3: dup
      // 4ac4: bipush 49
      // 4ac6: new net/minecraft/client/animation/Keyframe
      // 4ac9: dup
      // 4aca: ldc 2.08
      // 4acc: ldc_w -2.358
      // 4acf: ldc_w -5.51
      // 4ad2: ldc_w -0.577
      // 4ad5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ad8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4adb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ade: aastore
      // 4adf: dup
      // 4ae0: bipush 50
      // 4ae2: new net/minecraft/client/animation/Keyframe
      // 4ae5: dup
      // 4ae6: ldc_w 2.12
      // 4ae9: ldc_w -1.314
      // 4aec: ldc_w -3.981
      // 4aef: ldc_w -0.373
      // 4af2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4af5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4af8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4afb: aastore
      // 4afc: dup
      // 4afd: bipush 51
      // 4aff: new net/minecraft/client/animation/Keyframe
      // 4b02: dup
      // 4b03: ldc_w 2.17
      // 4b06: ldc_w -0.405
      // 4b09: ldc_w -2.431
      // 4b0c: ldc_w -0.132
      // 4b0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b18: aastore
      // 4b19: dup
      // 4b1a: bipush 52
      // 4b1c: new net/minecraft/client/animation/Keyframe
      // 4b1f: dup
      // 4b20: ldc 2.21
      // 4b22: ldc_w -0.021
      // 4b25: ldc_w -1.718
      // 4b28: ldc_w -0.014
      // 4b2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b34: aastore
      // 4b35: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4b38: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4b3b: ldc_w "CTR_lowerarm_right"
      // 4b3e: new net/minecraft/client/animation/AnimationChannel
      // 4b41: dup
      // 4b42: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4b45: bipush 3
      // 4b46: anewarray 28
      // 4b49: dup
      // 4b4a: bipush 0
      // 4b4b: new net/minecraft/client/animation/Keyframe
      // 4b4e: dup
      // 4b4f: ldc 1.25
      // 4b51: dconst_1
      // 4b52: dconst_1
      // 4b53: dconst_1
      // 4b54: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b5d: aastore
      // 4b5e: dup
      // 4b5f: bipush 1
      // 4b60: new net/minecraft/client/animation/Keyframe
      // 4b63: dup
      // 4b64: ldc 1.29
      // 4b66: ldc2_w 2.0840001106262207
      // 4b69: dconst_1
      // 4b6a: dconst_1
      // 4b6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b74: aastore
      // 4b75: dup
      // 4b76: bipush 2
      // 4b77: new net/minecraft/client/animation/Keyframe
      // 4b7a: dup
      // 4b7b: ldc 1.33
      // 4b7d: dconst_1
      // 4b7e: dconst_1
      // 4b7f: dconst_1
      // 4b80: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b89: aastore
      // 4b8a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4b8d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4b90: ldc_w "CTR_upperarm_right.001"
      // 4b93: new net/minecraft/client/animation/AnimationChannel
      // 4b96: dup
      // 4b97: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4b9a: bipush 54
      // 4b9c: anewarray 28
      // 4b9f: dup
      // 4ba0: bipush 0
      // 4ba1: new net/minecraft/client/animation/Keyframe
      // 4ba4: dup
      // 4ba5: fconst_0
      // 4ba6: ldc_w 0.816
      // 4ba9: ldc_w 17.827
      // 4bac: ldc_w 2.044
      // 4baf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bb8: aastore
      // 4bb9: dup
      // 4bba: bipush 1
      // 4bbb: new net/minecraft/client/animation/Keyframe
      // 4bbe: dup
      // 4bbf: ldc 0.04
      // 4bc1: ldc_w 0.419
      // 4bc4: ldc_w 16.662
      // 4bc7: ldc_w 1.807
      // 4bca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bcd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bd0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bd3: aastore
      // 4bd4: dup
      // 4bd5: bipush 2
      // 4bd6: new net/minecraft/client/animation/Keyframe
      // 4bd9: dup
      // 4bda: ldc 0.08
      // 4bdc: ldc_w -0.203
      // 4bdf: ldc_w 13.032
      // 4be2: ldc_w 1.372
      // 4be5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4be8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4beb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bee: aastore
      // 4bef: dup
      // 4bf0: bipush 3
      // 4bf1: new net/minecraft/client/animation/Keyframe
      // 4bf4: dup
      // 4bf5: ldc 0.12
      // 4bf7: ldc_w -0.609
      // 4bfa: ldc_w 6.999
      // 4bfd: ldc_w 0.937
      // 4c00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c09: aastore
      // 4c0a: dup
      // 4c0b: bipush 4
      // 4c0c: new net/minecraft/client/animation/Keyframe
      // 4c0f: dup
      // 4c10: ldc 0.17
      // 4c12: ldc_w -37.168
      // 4c15: ldc_w -1.835
      // 4c18: ldc_w 36.799
      // 4c1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c24: aastore
      // 4c25: dup
      // 4c26: bipush 5
      // 4c27: new net/minecraft/client/animation/Keyframe
      // 4c2a: dup
      // 4c2b: ldc 0.21
      // 4c2d: ldc_w -1.664
      // 4c30: ldc_w -11.326
      // 4c33: ldc_w 0.79
      // 4c36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c3f: aastore
      // 4c40: dup
      // 4c41: bipush 6
      // 4c43: new net/minecraft/client/animation/Keyframe
      // 4c46: dup
      // 4c47: ldc 0.25
      // 4c49: ldc_w -1.752
      // 4c4c: ldc_w -16.096
      // 4c4f: ldc_w 0.685
      // 4c52: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c5b: aastore
      // 4c5c: dup
      // 4c5d: bipush 7
      // 4c5f: new net/minecraft/client/animation/Keyframe
      // 4c62: dup
      // 4c63: ldc 0.29
      // 4c65: ldc_w -1.751
      // 4c68: ldc_w -23.794
      // 4c6b: ldc_w 0.619
      // 4c6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c77: aastore
      // 4c78: dup
      // 4c79: bipush 8
      // 4c7b: new net/minecraft/client/animation/Keyframe
      // 4c7e: dup
      // 4c7f: ldc 0.33
      // 4c81: ldc_w -1.779
      // 4c84: ldc_w -33.989
      // 4c87: ldc_w 0.529
      // 4c8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c93: aastore
      // 4c94: dup
      // 4c95: bipush 9
      // 4c97: new net/minecraft/client/animation/Keyframe
      // 4c9a: dup
      // 4c9b: ldc 0.38
      // 4c9d: ldc_w -1.845
      // 4ca0: ldc_w -46.373
      // 4ca3: ldc_w 0.376
      // 4ca6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ca9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4caf: aastore
      // 4cb0: dup
      // 4cb1: bipush 10
      // 4cb3: new net/minecraft/client/animation/Keyframe
      // 4cb6: dup
      // 4cb7: ldc 0.42
      // 4cb9: ldc_w -2.3
      // 4cbc: ldc_w -59.712
      // 4cbf: ldc_w -0.353
      // 4cc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ccb: aastore
      // 4ccc: dup
      // 4ccd: bipush 11
      // 4ccf: new net/minecraft/client/animation/Keyframe
      // 4cd2: dup
      // 4cd3: ldc 0.46
      // 4cd5: ldc_w -3.782
      // 4cd8: ldc_w -72.035
      // 4cdb: ldc_w -2.576
      // 4cde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ce1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ce4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ce7: aastore
      // 4ce8: dup
      // 4ce9: bipush 12
      // 4ceb: new net/minecraft/client/animation/Keyframe
      // 4cee: dup
      // 4cef: ldc 0.5
      // 4cf1: ldc_w -6.473
      // 4cf4: ldc_w -81.77
      // 4cf7: ldc_w -6.607
      // 4cfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d03: aastore
      // 4d04: dup
      // 4d05: bipush 13
      // 4d07: new net/minecraft/client/animation/Keyframe
      // 4d0a: dup
      // 4d0b: ldc 0.54
      // 4d0d: ldc_w -9.116
      // 4d10: ldc_w -87.716
      // 4d13: ldc_w -10.718
      // 4d16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d1f: aastore
      // 4d20: dup
      // 4d21: bipush 14
      // 4d23: new net/minecraft/client/animation/Keyframe
      // 4d26: dup
      // 4d27: ldc 0.58
      // 4d29: ldc_w -11.075
      // 4d2c: ldc_w -91.594
      // 4d2f: ldc_w -13.699
      // 4d32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d3b: aastore
      // 4d3c: dup
      // 4d3d: bipush 15
      // 4d3f: new net/minecraft/client/animation/Keyframe
      // 4d42: dup
      // 4d43: ldc 0.62
      // 4d45: ldc_w -13.336
      // 4d48: ldc_w -95.246
      // 4d4b: ldc_w -16.858
      // 4d4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d57: aastore
      // 4d58: dup
      // 4d59: bipush 16
      // 4d5b: new net/minecraft/client/animation/Keyframe
      // 4d5e: dup
      // 4d5f: ldc 0.67
      // 4d61: ldc_w -15.556
      // 4d64: ldc_w -98.229
      // 4d67: ldc_w -19.98
      // 4d6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d73: aastore
      // 4d74: dup
      // 4d75: bipush 17
      // 4d77: new net/minecraft/client/animation/Keyframe
      // 4d7a: dup
      // 4d7b: ldc_w 0.71
      // 4d7e: ldc_w -17.589
      // 4d81: ldc_w -100.575
      // 4d84: ldc_w -22.858
      // 4d87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d90: aastore
      // 4d91: dup
      // 4d92: bipush 18
      // 4d94: new net/minecraft/client/animation/Keyframe
      // 4d97: dup
      // 4d98: ldc 0.75
      // 4d9a: ldc_w -19.256
      // 4d9d: ldc_w -102.358
      // 4da0: ldc_w -25.231
      // 4da3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4da6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4da9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dac: aastore
      // 4dad: dup
      // 4dae: bipush 19
      // 4db0: new net/minecraft/client/animation/Keyframe
      // 4db3: dup
      // 4db4: ldc_w 0.79
      // 4db7: ldc_w -20.367
      // 4dba: ldc_w -103.679
      // 4dbd: ldc_w -26.815
      // 4dc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dc9: aastore
      // 4dca: dup
      // 4dcb: bipush 20
      // 4dcd: new net/minecraft/client/animation/Keyframe
      // 4dd0: dup
      // 4dd1: ldc_w 0.83
      // 4dd4: ldc_w -20.755
      // 4dd7: ldc_w -104.625
      // 4dda: ldc_w -27.363
      // 4ddd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4de0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4de3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4de6: aastore
      // 4de7: dup
      // 4de8: bipush 21
      // 4dea: new net/minecraft/client/animation/Keyframe
      // 4ded: dup
      // 4dee: ldc_w 0.88
      // 4df1: ldc_w -20.318
      // 4df4: ldc_w -105.201
      // 4df7: ldc_w -26.735
      // 4dfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e03: aastore
      // 4e04: dup
      // 4e05: bipush 22
      // 4e07: new net/minecraft/client/animation/Keyframe
      // 4e0a: dup
      // 4e0b: ldc_w 0.92
      // 4e0e: ldc_w -19.53
      // 4e11: ldc_w -105.478
      // 4e14: ldc_w -25.618
      // 4e17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e20: aastore
      // 4e21: dup
      // 4e22: bipush 23
      // 4e24: new net/minecraft/client/animation/Keyframe
      // 4e27: dup
      // 4e28: ldc 0.96
      // 4e2a: ldc_w -19.137
      // 4e2d: ldc_w -105.49
      // 4e30: ldc_w -25.045
      // 4e33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e3c: aastore
      // 4e3d: dup
      // 4e3e: bipush 24
      // 4e40: new net/minecraft/client/animation/Keyframe
      // 4e43: dup
      // 4e44: fconst_1
      // 4e45: ldc_w -19.864
      // 4e48: ldc_w -105.853
      // 4e4b: ldc_w -25.356
      // 4e4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e57: aastore
      // 4e58: dup
      // 4e59: bipush 25
      // 4e5b: new net/minecraft/client/animation/Keyframe
      // 4e5e: dup
      // 4e5f: ldc 1.04
      // 4e61: ldc_w -21.881
      // 4e64: ldc_w -106.879
      // 4e67: ldc_w -26.398
      // 4e6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e73: aastore
      // 4e74: dup
      // 4e75: bipush 26
      // 4e77: new net/minecraft/client/animation/Keyframe
      // 4e7a: dup
      // 4e7b: ldc 1.08
      // 4e7d: ldc_w -24.63
      // 4e80: ldc_w -108.093
      // 4e83: ldc_w -27.767
      // 4e86: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e8f: aastore
      // 4e90: dup
      // 4e91: bipush 27
      // 4e93: new net/minecraft/client/animation/Keyframe
      // 4e96: dup
      // 4e97: ldc 1.12
      // 4e99: ldc_w -27.56
      // 4e9c: ldc_w -109.258
      // 4e9f: ldc_w -29.108
      // 4ea2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ea5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ea8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eab: aastore
      // 4eac: dup
      // 4ead: bipush 28
      // 4eaf: new net/minecraft/client/animation/Keyframe
      // 4eb2: dup
      // 4eb3: ldc 1.17
      // 4eb5: ldc_w -24.034
      // 4eb8: ldc_w -91.346
      // 4ebb: ldc_w -24.432
      // 4ebe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ec1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ec4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ec7: aastore
      // 4ec8: dup
      // 4ec9: bipush 29
      // 4ecb: new net/minecraft/client/animation/Keyframe
      // 4ece: dup
      // 4ecf: ldc 1.21
      // 4ed1: ldc_w -20.519
      // 4ed4: ldc_w -84.319
      // 4ed7: ldc_w -19.739
      // 4eda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4edd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ee0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ee3: aastore
      // 4ee4: dup
      // 4ee5: bipush 30
      // 4ee7: new net/minecraft/client/animation/Keyframe
      // 4eea: dup
      // 4eeb: ldc 1.25
      // 4eed: ldc_w -24.222
      // 4ef0: ldc_w -60.937
      // 4ef3: ldc_w -23.146
      // 4ef6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ef9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4efc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eff: aastore
      // 4f00: dup
      // 4f01: bipush 31
      // 4f03: new net/minecraft/client/animation/Keyframe
      // 4f06: dup
      // 4f07: ldc 1.29
      // 4f09: ldc_w 0.956
      // 4f0c: ldc_w -17.087
      // 4f0f: ldc_w 4.095
      // 4f12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f1b: aastore
      // 4f1c: dup
      // 4f1d: bipush 32
      // 4f1f: new net/minecraft/client/animation/Keyframe
      // 4f22: dup
      // 4f23: ldc 1.33
      // 4f25: ldc_w -1.517
      // 4f28: ldc_w -15.834
      // 4f2b: ldc_w 0.992
      // 4f2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f37: aastore
      // 4f38: dup
      // 4f39: bipush 33
      // 4f3b: new net/minecraft/client/animation/Keyframe
      // 4f3e: dup
      // 4f3f: ldc 1.38
      // 4f41: ldc_w -1.542
      // 4f44: ldc_w -16.468
      // 4f47: ldc_w 0.949
      // 4f4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f53: aastore
      // 4f54: dup
      // 4f55: bipush 34
      // 4f57: new net/minecraft/client/animation/Keyframe
      // 4f5a: dup
      // 4f5b: ldc 1.42
      // 4f5d: ldc_w -1.618
      // 4f60: ldc_w -14.336
      // 4f63: ldc_w 0.89
      // 4f66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f6f: aastore
      // 4f70: dup
      // 4f71: bipush 35
      // 4f73: new net/minecraft/client/animation/Keyframe
      // 4f76: dup
      // 4f77: ldc 1.46
      // 4f79: ldc_w -1.666
      // 4f7c: ldc_w -11.914
      // 4f7f: ldc_w 0.885
      // 4f82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f8b: aastore
      // 4f8c: dup
      // 4f8d: bipush 36
      // 4f8f: new net/minecraft/client/animation/Keyframe
      // 4f92: dup
      // 4f93: ldc 1.5
      // 4f95: ldc_w -1.618
      // 4f98: ldc_w -10.915
      // 4f9b: ldc_w 0.992
      // 4f9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fa1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fa4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fa7: aastore
      // 4fa8: dup
      // 4fa9: bipush 37
      // 4fab: new net/minecraft/client/animation/Keyframe
      // 4fae: dup
      // 4faf: ldc 1.54
      // 4fb1: ldc_w -1.581
      // 4fb4: ldc_w -10.721
      // 4fb7: ldc_w 1.057
      // 4fba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fc3: aastore
      // 4fc4: dup
      // 4fc5: bipush 38
      // 4fc7: new net/minecraft/client/animation/Keyframe
      // 4fca: dup
      // 4fcb: ldc 1.58
      // 4fcd: ldc_w -1.619
      // 4fd0: ldc_w -9.855
      // 4fd3: ldc_w 1.046
      // 4fd6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fd9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fdc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fdf: aastore
      // 4fe0: dup
      // 4fe1: bipush 39
      // 4fe3: new net/minecraft/client/animation/Keyframe
      // 4fe6: dup
      // 4fe7: ldc 1.62
      // 4fe9: ldc_w -1.518
      // 4fec: ldc_w -10.992
      // 4fef: ldc_w 1.138
      // 4ff2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ff5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ff8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ffb: aastore
      // 4ffc: dup
      // 4ffd: bipush 40
      // 4fff: new net/minecraft/client/animation/Keyframe
      // 5002: dup
      // 5003: ldc 1.67
      // 5005: ldc_w -1.416
      // 5008: ldc_w -11.31
      // 500b: ldc_w 1.272
      // 500e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5011: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5014: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5017: aastore
      // 5018: dup
      // 5019: bipush 41
      // 501b: new net/minecraft/client/animation/Keyframe
      // 501e: dup
      // 501f: ldc 1.71
      // 5021: ldc_w -1.566
      // 5024: ldc_w -7.417
      // 5027: ldc_w 1.392
      // 502a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 502d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5030: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5033: aastore
      // 5034: dup
      // 5035: bipush 42
      // 5037: new net/minecraft/client/animation/Keyframe
      // 503a: dup
      // 503b: ldc 1.75
      // 503d: ldc_w -1.205
      // 5040: ldc_w 3.007
      // 5043: ldc_w 0.493
      // 5046: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5049: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 504c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 504f: aastore
      // 5050: dup
      // 5051: bipush 43
      // 5053: new net/minecraft/client/animation/Keyframe
      // 5056: dup
      // 5057: ldc 1.79
      // 5059: ldc_w -1.897
      // 505c: ldc_w 19.962
      // 505f: ldc 0.33
      // 5061: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5064: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5067: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 506a: aastore
      // 506b: dup
      // 506c: bipush 44
      // 506e: new net/minecraft/client/animation/Keyframe
      // 5071: dup
      // 5072: ldc 1.83
      // 5074: ldc_w -2.182
      // 5077: ldc_w 39.846
      // 507a: ldc_w 0.095
      // 507d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5080: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5083: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5086: aastore
      // 5087: dup
      // 5088: bipush 45
      // 508a: new net/minecraft/client/animation/Keyframe
      // 508d: dup
      // 508e: ldc 1.88
      // 5090: ldc_w -1.841
      // 5093: ldc_w 54.603
      // 5096: ldc_w 0.419
      // 5099: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 509c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 509f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50a2: aastore
      // 50a3: dup
      // 50a4: bipush 46
      // 50a6: new net/minecraft/client/animation/Keyframe
      // 50a9: dup
      // 50aa: ldc 1.92
      // 50ac: ldc_w -1.018
      // 50af: ldc_w 59.723
      // 50b2: ldc_w 1.203
      // 50b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50be: aastore
      // 50bf: dup
      // 50c0: bipush 47
      // 50c2: new net/minecraft/client/animation/Keyframe
      // 50c5: dup
      // 50c6: ldc 1.96
      // 50c8: ldc_w -0.229
      // 50cb: ldc_w 57.266
      // 50ce: ldc_w 1.946
      // 50d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50da: aastore
      // 50db: dup
      // 50dc: bipush 48
      // 50de: new net/minecraft/client/animation/Keyframe
      // 50e1: dup
      // 50e2: fconst_2
      // 50e3: ldc_w -0.065
      // 50e6: ldc_w 42.547
      // 50e9: ldc_w 2.056
      // 50ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50f5: aastore
      // 50f6: dup
      // 50f7: bipush 49
      // 50f9: new net/minecraft/client/animation/Keyframe
      // 50fc: dup
      // 50fd: ldc 2.04
      // 50ff: ldc_w -0.355
      // 5102: ldc_w 24.979
      // 5105: ldc_w 1.693
      // 5108: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 510b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 510e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5111: aastore
      // 5112: dup
      // 5113: bipush 50
      // 5115: new net/minecraft/client/animation/Keyframe
      // 5118: dup
      // 5119: ldc 2.08
      // 511b: ldc_w -0.213
      // 511e: ldc_w 20.385
      // 5121: ldc_w 1.723
      // 5124: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5127: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 512a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 512d: aastore
      // 512e: dup
      // 512f: bipush 51
      // 5131: new net/minecraft/client/animation/Keyframe
      // 5134: dup
      // 5135: ldc_w 2.12
      // 5138: ldc_w 0.166
      // 513b: ldc_w 19.073
      // 513e: ldc_w 1.875
      // 5141: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5144: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5147: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 514a: aastore
      // 514b: dup
      // 514c: bipush 52
      // 514e: new net/minecraft/client/animation/Keyframe
      // 5151: dup
      // 5152: ldc_w 2.17
      // 5155: ldc_w 0.598
      // 5158: ldc_w 18.168
      // 515b: ldc_w 1.996
      // 515e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5161: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5164: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5167: aastore
      // 5168: dup
      // 5169: bipush 53
      // 516b: new net/minecraft/client/animation/Keyframe
      // 516e: dup
      // 516f: ldc 2.21
      // 5171: ldc_w 0.816
      // 5174: ldc_w 17.827
      // 5177: ldc_w 2.044
      // 517a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 517d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5180: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5183: aastore
      // 5184: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5187: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 518a: ldc_w "CTR_upperarm_right.001"
      // 518d: new net/minecraft/client/animation/AnimationChannel
      // 5190: dup
      // 5191: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5194: bipush 41
      // 5196: anewarray 28
      // 5199: dup
      // 519a: bipush 0
      // 519b: new net/minecraft/client/animation/Keyframe
      // 519e: dup
      // 519f: fconst_0
      // 51a0: dconst_1
      // 51a1: dconst_1
      // 51a2: ldc2_w 1.6740000247955322
      // 51a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51ae: aastore
      // 51af: dup
      // 51b0: bipush 1
      // 51b1: new net/minecraft/client/animation/Keyframe
      // 51b4: dup
      // 51b5: ldc 0.12
      // 51b7: dconst_1
      // 51b8: dconst_1
      // 51b9: ldc2_w 1.649999976158142
      // 51bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51c5: aastore
      // 51c6: dup
      // 51c7: bipush 2
      // 51c8: new net/minecraft/client/animation/Keyframe
      // 51cb: dup
      // 51cc: ldc 0.17
      // 51ce: dconst_1
      // 51cf: dconst_1
      // 51d0: ldc2_w 1.6670000553131104
      // 51d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51dc: aastore
      // 51dd: dup
      // 51de: bipush 3
      // 51df: new net/minecraft/client/animation/Keyframe
      // 51e2: dup
      // 51e3: ldc 0.21
      // 51e5: dconst_1
      // 51e6: dconst_1
      // 51e7: ldc2_w 1.7000000476837158
      // 51ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51f3: aastore
      // 51f4: dup
      // 51f5: bipush 4
      // 51f6: new net/minecraft/client/animation/Keyframe
      // 51f9: dup
      // 51fa: ldc 0.25
      // 51fc: dconst_1
      // 51fd: dconst_1
      // 51fe: ldc2_w 1.656000018119812
      // 5201: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5204: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5207: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 520a: aastore
      // 520b: dup
      // 520c: bipush 5
      // 520d: new net/minecraft/client/animation/Keyframe
      // 5210: dup
      // 5211: ldc 0.29
      // 5213: dconst_1
      // 5214: dconst_1
      // 5215: ldc2_w 1.6319999694824219
      // 5218: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 521b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 521e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5221: aastore
      // 5222: dup
      // 5223: bipush 6
      // 5225: new net/minecraft/client/animation/Keyframe
      // 5228: dup
      // 5229: ldc 0.33
      // 522b: dconst_1
      // 522c: dconst_1
      // 522d: ldc2_w 1.6369999647140503
      // 5230: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5233: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5236: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5239: aastore
      // 523a: dup
      // 523b: bipush 7
      // 523d: new net/minecraft/client/animation/Keyframe
      // 5240: dup
      // 5241: ldc 0.38
      // 5243: dconst_1
      // 5244: dconst_1
      // 5245: ldc2_w 1.6829999685287476
      // 5248: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 524b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 524e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5251: aastore
      // 5252: dup
      // 5253: bipush 8
      // 5255: new net/minecraft/client/animation/Keyframe
      // 5258: dup
      // 5259: ldc 0.42
      // 525b: dconst_1
      // 525c: dconst_1
      // 525d: ldc2_w 1.753000020980835
      // 5260: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5263: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5266: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5269: aastore
      // 526a: dup
      // 526b: bipush 9
      // 526d: new net/minecraft/client/animation/Keyframe
      // 5270: dup
      // 5271: ldc 0.46
      // 5273: dconst_1
      // 5274: dconst_1
      // 5275: ldc2_w 1.8079999685287476
      // 5278: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 527b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 527e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5281: aastore
      // 5282: dup
      // 5283: bipush 10
      // 5285: new net/minecraft/client/animation/Keyframe
      // 5288: dup
      // 5289: ldc 0.5
      // 528b: dconst_1
      // 528c: dconst_1
      // 528d: ldc2_w 1.8229999542236328
      // 5290: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5293: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5296: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5299: aastore
      // 529a: dup
      // 529b: bipush 11
      // 529d: new net/minecraft/client/animation/Keyframe
      // 52a0: dup
      // 52a1: ldc 0.58
      // 52a3: dconst_1
      // 52a4: dconst_1
      // 52a5: ldc2_w 1.7790000438690186
      // 52a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52b1: aastore
      // 52b2: dup
      // 52b3: bipush 12
      // 52b5: new net/minecraft/client/animation/Keyframe
      // 52b8: dup
      // 52b9: ldc_w 0.79
      // 52bc: dconst_1
      // 52bd: dconst_1
      // 52be: ldc2_w 1.63100004196167
      // 52c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52ca: aastore
      // 52cb: dup
      // 52cc: bipush 13
      // 52ce: new net/minecraft/client/animation/Keyframe
      // 52d1: dup
      // 52d2: ldc_w 0.83
      // 52d5: dconst_1
      // 52d6: dconst_1
      // 52d7: ldc2_w 1.6130000352859497
      // 52da: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52e3: aastore
      // 52e4: dup
      // 52e5: bipush 14
      // 52e7: new net/minecraft/client/animation/Keyframe
      // 52ea: dup
      // 52eb: ldc_w 0.92
      // 52ee: dconst_1
      // 52ef: dconst_1
      // 52f0: ldc2_w 1.593999981880188
      // 52f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52fc: aastore
      // 52fd: dup
      // 52fe: bipush 15
      // 5300: new net/minecraft/client/animation/Keyframe
      // 5303: dup
      // 5304: ldc 0.96
      // 5306: dconst_1
      // 5307: dconst_1
      // 5308: ldc2_w 1.5729999542236328
      // 530b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 530e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5311: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5314: aastore
      // 5315: dup
      // 5316: bipush 16
      // 5318: new net/minecraft/client/animation/Keyframe
      // 531b: dup
      // 531c: fconst_1
      // 531d: dconst_1
      // 531e: dconst_1
      // 531f: ldc2_w 1.527999997138977
      // 5322: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5325: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5328: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 532b: aastore
      // 532c: dup
      // 532d: bipush 17
      // 532f: new net/minecraft/client/animation/Keyframe
      // 5332: dup
      // 5333: ldc 1.12
      // 5335: dconst_1
      // 5336: dconst_1
      // 5337: ldc2_w 1.3270000219345093
      // 533a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 533d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5340: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5343: aastore
      // 5344: dup
      // 5345: bipush 18
      // 5347: new net/minecraft/client/animation/Keyframe
      // 534a: dup
      // 534b: ldc 1.17
      // 534d: dconst_1
      // 534e: dconst_1
      // 534f: ldc2_w 1.534000039100647
      // 5352: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5355: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5358: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 535b: aastore
      // 535c: dup
      // 535d: bipush 19
      // 535f: new net/minecraft/client/animation/Keyframe
      // 5362: dup
      // 5363: ldc 1.21
      // 5365: dconst_1
      // 5366: dconst_1
      // 5367: ldc2_w 2.359999895095825
      // 536a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 536d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5370: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5373: aastore
      // 5374: dup
      // 5375: bipush 20
      // 5377: new net/minecraft/client/animation/Keyframe
      // 537a: dup
      // 537b: ldc 1.25
      // 537d: dconst_1
      // 537e: dconst_1
      // 537f: ldc2_w 2.25
      // 5382: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5385: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5388: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 538b: aastore
      // 538c: dup
      // 538d: bipush 21
      // 538f: new net/minecraft/client/animation/Keyframe
      // 5392: dup
      // 5393: ldc 1.29
      // 5395: dconst_1
      // 5396: dconst_1
      // 5397: ldc2_w 1.5839999914169312
      // 539a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 539d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53a3: aastore
      // 53a4: dup
      // 53a5: bipush 22
      // 53a7: new net/minecraft/client/animation/Keyframe
      // 53aa: dup
      // 53ab: ldc 1.33
      // 53ad: dconst_1
      // 53ae: dconst_1
      // 53af: ldc2_w 1.5069999694824219
      // 53b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53bb: aastore
      // 53bc: dup
      // 53bd: bipush 23
      // 53bf: new net/minecraft/client/animation/Keyframe
      // 53c2: dup
      // 53c3: ldc 1.38
      // 53c5: dconst_1
      // 53c6: dconst_1
      // 53c7: ldc2_w 1.5019999742507935
      // 53ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53d3: aastore
      // 53d4: dup
      // 53d5: bipush 24
      // 53d7: new net/minecraft/client/animation/Keyframe
      // 53da: dup
      // 53db: ldc 1.46
      // 53dd: dconst_1
      // 53de: dconst_1
      // 53df: ldc2_w 1.475000023841858
      // 53e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53eb: aastore
      // 53ec: dup
      // 53ed: bipush 25
      // 53ef: new net/minecraft/client/animation/Keyframe
      // 53f2: dup
      // 53f3: ldc 1.58
      // 53f5: dconst_1
      // 53f6: dconst_1
      // 53f7: ldc2_w 1.468000054359436
      // 53fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5400: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5403: aastore
      // 5404: dup
      // 5405: bipush 26
      // 5407: new net/minecraft/client/animation/Keyframe
      // 540a: dup
      // 540b: ldc 1.62
      // 540d: dconst_1
      // 540e: dconst_1
      // 540f: ldc2_w 1.4809999465942383
      // 5412: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5415: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5418: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 541b: aastore
      // 541c: dup
      // 541d: bipush 27
      // 541f: new net/minecraft/client/animation/Keyframe
      // 5422: dup
      // 5423: ldc 1.67
      // 5425: dconst_1
      // 5426: dconst_1
      // 5427: ldc2_w 1.465999960899353
      // 542a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 542d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5430: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5433: aastore
      // 5434: dup
      // 5435: bipush 28
      // 5437: new net/minecraft/client/animation/Keyframe
      // 543a: dup
      // 543b: ldc 1.71
      // 543d: dconst_1
      // 543e: dconst_1
      // 543f: ldc2_w 1.3940000534057617
      // 5442: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5445: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5448: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 544b: aastore
      // 544c: dup
      // 544d: bipush 29
      // 544f: new net/minecraft/client/animation/Keyframe
      // 5452: dup
      // 5453: ldc 1.75
      // 5455: dconst_1
      // 5456: dconst_1
      // 5457: ldc2_w 1.2619999647140503
      // 545a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 545d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5460: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5463: aastore
      // 5464: dup
      // 5465: bipush 30
      // 5467: new net/minecraft/client/animation/Keyframe
      // 546a: dup
      // 546b: ldc 1.79
      // 546d: dconst_1
      // 546e: dconst_1
      // 546f: ldc2_w 1.1009999513626099
      // 5472: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5475: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5478: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 547b: aastore
      // 547c: dup
      // 547d: bipush 31
      // 547f: new net/minecraft/client/animation/Keyframe
      // 5482: dup
      // 5483: ldc 1.83
      // 5485: dconst_1
      // 5486: dconst_1
      // 5487: ldc2_w 0.9879999756813049
      // 548a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 548d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5490: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5493: aastore
      // 5494: dup
      // 5495: bipush 32
      // 5497: new net/minecraft/client/animation/Keyframe
      // 549a: dup
      // 549b: ldc 1.88
      // 549d: dconst_1
      // 549e: dconst_1
      // 549f: ldc2_w 0.9700000286102295
      // 54a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54ab: aastore
      // 54ac: dup
      // 54ad: bipush 33
      // 54af: new net/minecraft/client/animation/Keyframe
      // 54b2: dup
      // 54b3: ldc 1.92
      // 54b5: dconst_1
      // 54b6: dconst_1
      // 54b7: ldc2_w 1.0199999809265137
      // 54ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54c3: aastore
      // 54c4: dup
      // 54c5: bipush 34
      // 54c7: new net/minecraft/client/animation/Keyframe
      // 54ca: dup
      // 54cb: ldc 1.96
      // 54cd: dconst_1
      // 54ce: dconst_1
      // 54cf: ldc2_w 1.1030000448226929
      // 54d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54db: aastore
      // 54dc: dup
      // 54dd: bipush 35
      // 54df: new net/minecraft/client/animation/Keyframe
      // 54e2: dup
      // 54e3: fconst_2
      // 54e4: dconst_1
      // 54e5: dconst_1
      // 54e6: ldc2_w 1.25600004196167
      // 54e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54f2: aastore
      // 54f3: dup
      // 54f4: bipush 36
      // 54f6: new net/minecraft/client/animation/Keyframe
      // 54f9: dup
      // 54fa: ldc 2.04
      // 54fc: dconst_1
      // 54fd: dconst_1
      // 54fe: ldc2_w 1.4709999561309814
      // 5501: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5504: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5507: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 550a: aastore
      // 550b: dup
      // 550c: bipush 37
      // 550e: new net/minecraft/client/animation/Keyframe
      // 5511: dup
      // 5512: ldc 2.08
      // 5514: dconst_1
      // 5515: dconst_1
      // 5516: ldc2_w 1.5779999494552612
      // 5519: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 551c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 551f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5522: aastore
      // 5523: dup
      // 5524: bipush 38
      // 5526: new net/minecraft/client/animation/Keyframe
      // 5529: dup
      // 552a: ldc_w 2.12
      // 552d: dconst_1
      // 552e: dconst_1
      // 552f: ldc2_w 1.6349999904632568
      // 5532: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5535: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5538: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 553b: aastore
      // 553c: dup
      // 553d: bipush 39
      // 553f: new net/minecraft/client/animation/Keyframe
      // 5542: dup
      // 5543: ldc_w 2.17
      // 5546: dconst_1
      // 5547: dconst_1
      // 5548: ldc2_w 1.6649999618530273
      // 554b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 554e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5551: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5554: aastore
      // 5555: dup
      // 5556: bipush 40
      // 5558: new net/minecraft/client/animation/Keyframe
      // 555b: dup
      // 555c: ldc 2.21
      // 555e: dconst_1
      // 555f: dconst_1
      // 5560: ldc2_w 1.6740000247955322
      // 5563: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5566: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5569: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 556c: aastore
      // 556d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5570: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5573: ldc_w "CTR_leg_left"
      // 5576: new net/minecraft/client/animation/AnimationChannel
      // 5579: dup
      // 557a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 557d: bipush 43
      // 557f: anewarray 28
      // 5582: dup
      // 5583: bipush 0
      // 5584: new net/minecraft/client/animation/Keyframe
      // 5587: dup
      // 5588: fconst_0
      // 5589: ldc_w -177.066
      // 558c: ldc_w 52.262
      // 558f: ldc_w 182.934
      // 5592: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 559b: aastore
      // 559c: dup
      // 559d: bipush 1
      // 559e: new net/minecraft/client/animation/Keyframe
      // 55a1: dup
      // 55a2: ldc 0.04
      // 55a4: ldc_w -177.043
      // 55a7: ldc_w 51.984
      // 55aa: ldc_w 182.957
      // 55ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55b6: aastore
      // 55b7: dup
      // 55b8: bipush 2
      // 55b9: new net/minecraft/client/animation/Keyframe
      // 55bc: dup
      // 55bd: ldc 0.08
      // 55bf: ldc_w -176.957
      // 55c2: ldc_w 51.41
      // 55c5: ldc_w 183.043
      // 55c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55d1: aastore
      // 55d2: dup
      // 55d3: bipush 3
      // 55d4: new net/minecraft/client/animation/Keyframe
      // 55d7: dup
      // 55d8: ldc 0.12
      // 55da: ldc_w -176.75
      // 55dd: ldc_w 50.986
      // 55e0: ldc_w 183.25
      // 55e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55ec: aastore
      // 55ed: dup
      // 55ee: bipush 4
      // 55ef: new net/minecraft/client/animation/Keyframe
      // 55f2: dup
      // 55f3: ldc 0.17
      // 55f5: ldc_w -176.29
      // 55f8: ldc_w 51.273
      // 55fb: ldc_w 183.71
      // 55fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5601: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5604: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5607: aastore
      // 5608: dup
      // 5609: bipush 5
      // 560a: new net/minecraft/client/animation/Keyframe
      // 560d: dup
      // 560e: ldc 0.21
      // 5610: ldc_w -175.356
      // 5613: ldc_w 52.699
      // 5616: ldc_w 184.644
      // 5619: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 561c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 561f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5622: aastore
      // 5623: dup
      // 5624: bipush 6
      // 5626: new net/minecraft/client/animation/Keyframe
      // 5629: dup
      // 562a: ldc 0.25
      // 562c: ldc_w -173.945
      // 562f: ldc_w 54.602
      // 5632: ldc_w 186.055
      // 5635: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5638: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 563b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 563e: aastore
      // 563f: dup
      // 5640: bipush 7
      // 5642: new net/minecraft/client/animation/Keyframe
      // 5645: dup
      // 5646: ldc 0.29
      // 5648: ldc_w -172.528
      // 564b: ldc_w 55.903
      // 564e: ldc_w 187.472
      // 5651: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5654: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5657: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 565a: aastore
      // 565b: dup
      // 565c: bipush 8
      // 565e: new net/minecraft/client/animation/Keyframe
      // 5661: dup
      // 5662: ldc 0.33
      // 5664: ldc_w -171.473
      // 5667: ldc_w 56.64
      // 566a: ldc_w 188.527
      // 566d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5670: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5673: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5676: aastore
      // 5677: dup
      // 5678: bipush 9
      // 567a: new net/minecraft/client/animation/Keyframe
      // 567d: dup
      // 567e: ldc 0.38
      // 5680: ldc_w -172.111
      // 5683: ldc_w 52.186
      // 5686: ldc_w 187.889
      // 5689: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 568c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 568f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5692: aastore
      // 5693: dup
      // 5694: bipush 10
      // 5696: new net/minecraft/client/animation/Keyframe
      // 5699: dup
      // 569a: ldc 0.42
      // 569c: ldc_w -173.958
      // 569f: ldc_w 43.266
      // 56a2: ldc_w 186.042
      // 56a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56ae: aastore
      // 56af: dup
      // 56b0: bipush 11
      // 56b2: new net/minecraft/client/animation/Keyframe
      // 56b5: dup
      // 56b6: ldc 0.46
      // 56b8: ldc_w -175.374
      // 56bb: ldc_w 38.24
      // 56be: ldc_w 184.626
      // 56c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56ca: aastore
      // 56cb: dup
      // 56cc: bipush 12
      // 56ce: new net/minecraft/client/animation/Keyframe
      // 56d1: dup
      // 56d2: ldc 0.58
      // 56d4: ldc_w -175.576
      // 56d7: ldc_w 35.96
      // 56da: ldc_w 184.424
      // 56dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56e6: aastore
      // 56e7: dup
      // 56e8: bipush 13
      // 56ea: new net/minecraft/client/animation/Keyframe
      // 56ed: dup
      // 56ee: ldc 0.62
      // 56f0: ldc_w -175.572
      // 56f3: ldc_w 35.185
      // 56f6: ldc_w 184.428
      // 56f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5702: aastore
      // 5703: dup
      // 5704: bipush 14
      // 5706: new net/minecraft/client/animation/Keyframe
      // 5709: dup
      // 570a: ldc 0.67
      // 570c: ldc_w -175.537
      // 570f: ldc_w 34.391
      // 5712: ldc_w 184.463
      // 5715: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5718: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 571b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 571e: aastore
      // 571f: dup
      // 5720: bipush 15
      // 5722: new net/minecraft/client/animation/Keyframe
      // 5725: dup
      // 5726: ldc_w 0.83
      // 5729: ldc_w -175.112
      // 572c: ldc_w 31.162
      // 572f: ldc_w 184.888
      // 5732: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5735: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5738: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 573b: aastore
      // 573c: dup
      // 573d: bipush 16
      // 573f: new net/minecraft/client/animation/Keyframe
      // 5742: dup
      // 5743: ldc_w 0.88
      // 5746: ldc_w -174.941
      // 5749: ldc_w 30.396
      // 574c: ldc_w 185.06
      // 574f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5752: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5755: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5758: aastore
      // 5759: dup
      // 575a: bipush 17
      // 575c: new net/minecraft/client/animation/Keyframe
      // 575f: dup
      // 5760: ldc 1.08
      // 5762: ldc_w -173.845
      // 5765: ldc_w 27.076
      // 5768: ldc_w 186.155
      // 576b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 576e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5771: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5774: aastore
      // 5775: dup
      // 5776: bipush 18
      // 5778: new net/minecraft/client/animation/Keyframe
      // 577b: dup
      // 577c: ldc 1.12
      // 577e: ldc_w -173.645
      // 5781: ldc_w 26.296
      // 5784: ldc_w 186.354
      // 5787: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 578a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 578d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5790: aastore
      // 5791: dup
      // 5792: bipush 19
      // 5794: new net/minecraft/client/animation/Keyframe
      // 5797: dup
      // 5798: ldc 1.17
      // 579a: ldc_w -172.412
      // 579d: ldc_w 36.964
      // 57a0: ldc_w 187.588
      // 57a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57ac: aastore
      // 57ad: dup
      // 57ae: bipush 20
      // 57b0: new net/minecraft/client/animation/Keyframe
      // 57b3: dup
      // 57b4: ldc 1.21
      // 57b6: ldc_w -172.813
      // 57b9: ldc_w 44.692
      // 57bc: ldc_w 187.187
      // 57bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57c8: aastore
      // 57c9: dup
      // 57ca: bipush 21
      // 57cc: new net/minecraft/client/animation/Keyframe
      // 57cf: dup
      // 57d0: ldc 1.25
      // 57d2: ldc_w -178.303
      // 57d5: ldc_w 66.506
      // 57d8: ldc_w 181.697
      // 57db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57e4: aastore
      // 57e5: dup
      // 57e6: bipush 22
      // 57e8: new net/minecraft/client/animation/Keyframe
      // 57eb: dup
      // 57ec: ldc 1.29
      // 57ee: ldc_w -177.156
      // 57f1: ldc_w 66.736
      // 57f4: ldc_w 182.844
      // 57f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5800: aastore
      // 5801: dup
      // 5802: bipush 23
      // 5804: new net/minecraft/client/animation/Keyframe
      // 5807: dup
      // 5808: ldc 1.33
      // 580a: ldc_w -176.533
      // 580d: ldc_w 64.198
      // 5810: ldc_w 183.467
      // 5813: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5816: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5819: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 581c: aastore
      // 581d: dup
      // 581e: bipush 24
      // 5820: new net/minecraft/client/animation/Keyframe
      // 5823: dup
      // 5824: ldc 1.38
      // 5826: ldc_w -176.032
      // 5829: ldc_w 61.527
      // 582c: ldc_w 183.968
      // 582f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5832: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5835: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5838: aastore
      // 5839: dup
      // 583a: bipush 25
      // 583c: new net/minecraft/client/animation/Keyframe
      // 583f: dup
      // 5840: ldc 1.42
      // 5842: ldc_w -175.653
      // 5845: ldc_w 61.815
      // 5848: ldc_w 184.347
      // 584b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 584e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5851: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5854: aastore
      // 5855: dup
      // 5856: bipush 26
      // 5858: new net/minecraft/client/animation/Keyframe
      // 585b: dup
      // 585c: ldc 1.46
      // 585e: ldc_w -175.352
      // 5861: ldc_w 64.123
      // 5864: ldc_w 184.648
      // 5867: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 586a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 586d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5870: aastore
      // 5871: dup
      // 5872: bipush 27
      // 5874: new net/minecraft/client/animation/Keyframe
      // 5877: dup
      // 5878: ldc 1.5
      // 587a: ldc_w -175.192
      // 587d: ldc_w 67.333
      // 5880: ldc_w 184.808
      // 5883: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5886: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5889: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 588c: aastore
      // 588d: dup
      // 588e: bipush 28
      // 5890: new net/minecraft/client/animation/Keyframe
      // 5893: dup
      // 5894: ldc 1.54
      // 5896: ldc_w -175.14
      // 5899: ldc_w 70.081
      // 589c: ldc_w 184.86
      // 589f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58a8: aastore
      // 58a9: dup
      // 58aa: bipush 29
      // 58ac: new net/minecraft/client/animation/Keyframe
      // 58af: dup
      // 58b0: ldc 1.58
      // 58b2: ldc_w -175.131
      // 58b5: ldc_w 71.662
      // 58b8: ldc_w 184.869
      // 58bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58c4: aastore
      // 58c5: dup
      // 58c6: bipush 30
      // 58c8: new net/minecraft/client/animation/Keyframe
      // 58cb: dup
      // 58cc: ldc 1.62
      // 58ce: ldc_w -175.139
      // 58d1: ldc_w 72.112
      // 58d4: ldc_w 184.861
      // 58d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58e0: aastore
      // 58e1: dup
      // 58e2: bipush 31
      // 58e4: new net/minecraft/client/animation/Keyframe
      // 58e7: dup
      // 58e8: ldc 1.67
      // 58ea: ldc_w -175.118
      // 58ed: ldc_w 72.181
      // 58f0: ldc_w 184.882
      // 58f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58fc: aastore
      // 58fd: dup
      // 58fe: bipush 32
      // 5900: new net/minecraft/client/animation/Keyframe
      // 5903: dup
      // 5904: ldc 1.71
      // 5906: ldc_w -175.288
      // 5909: ldc_w 71.757
      // 590c: ldc_w 184.712
      // 590f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5912: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5915: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5918: aastore
      // 5919: dup
      // 591a: bipush 33
      // 591c: new net/minecraft/client/animation/Keyframe
      // 591f: dup
      // 5920: ldc 1.75
      // 5922: ldc_w -175.577
      // 5925: ldc_w 69.917
      // 5928: ldc_w 184.423
      // 592b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 592e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5931: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5934: aastore
      // 5935: dup
      // 5936: bipush 34
      // 5938: new net/minecraft/client/animation/Keyframe
      // 593b: dup
      // 593c: ldc 1.79
      // 593e: ldc_w -175.883
      // 5941: ldc_w 67.325
      // 5944: ldc_w 184.117
      // 5947: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 594a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 594d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5950: aastore
      // 5951: dup
      // 5952: bipush 35
      // 5954: new net/minecraft/client/animation/Keyframe
      // 5957: dup
      // 5958: ldc 1.83
      // 595a: ldc_w -176.18
      // 595d: ldc_w 64.428
      // 5960: ldc_w 183.82
      // 5963: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5966: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5969: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 596c: aastore
      // 596d: dup
      // 596e: bipush 36
      // 5970: new net/minecraft/client/animation/Keyframe
      // 5973: dup
      // 5974: ldc 1.88
      // 5976: ldc_w -176.456
      // 5979: ldc_w 61.513
      // 597c: ldc_w 183.544
      // 597f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5982: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5985: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5988: aastore
      // 5989: dup
      // 598a: bipush 37
      // 598c: new net/minecraft/client/animation/Keyframe
      // 598f: dup
      // 5990: ldc 1.92
      // 5992: ldc_w -176.7
      // 5995: ldc_w 58.772
      // 5998: ldc_w 183.299
      // 599b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 599e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59a4: aastore
      // 59a5: dup
      // 59a6: bipush 38
      // 59a8: new net/minecraft/client/animation/Keyframe
      // 59ab: dup
      // 59ac: ldc 1.96
      // 59ae: ldc_w -176.9
      // 59b1: ldc_w 56.337
      // 59b4: ldc_w 183.1
      // 59b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59c0: aastore
      // 59c1: dup
      // 59c2: bipush 39
      // 59c4: new net/minecraft/client/animation/Keyframe
      // 59c7: dup
      // 59c8: fconst_2
      // 59c9: ldc_w -177.038
      // 59cc: ldc_w 54.296
      // 59cf: ldc_w 182.962
      // 59d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59db: aastore
      // 59dc: dup
      // 59dd: bipush 40
      // 59df: new net/minecraft/client/animation/Keyframe
      // 59e2: dup
      // 59e3: ldc 2.04
      // 59e5: ldc_w -177.084
      // 59e8: ldc_w 52.687
      // 59eb: ldc_w 182.916
      // 59ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59f7: aastore
      // 59f8: dup
      // 59f9: bipush 41
      // 59fb: new net/minecraft/client/animation/Keyframe
      // 59fe: dup
      // 59ff: ldc 2.08
      // 5a01: ldc_w -177.077
      // 5a04: ldc_w 51.954
      // 5a07: ldc_w 182.923
      // 5a0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a13: aastore
      // 5a14: dup
      // 5a15: bipush 42
      // 5a17: new net/minecraft/client/animation/Keyframe
      // 5a1a: dup
      // 5a1b: ldc 2.21
      // 5a1d: ldc_w -177.066
      // 5a20: ldc_w 52.262
      // 5a23: ldc_w 182.934
      // 5a26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a2f: aastore
      // 5a30: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5a33: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5a36: ldc_w "CTR_leg_left"
      // 5a39: new net/minecraft/client/animation/AnimationChannel
      // 5a3c: dup
      // 5a3d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5a40: bipush 52
      // 5a42: anewarray 28
      // 5a45: dup
      // 5a46: bipush 0
      // 5a47: new net/minecraft/client/animation/Keyframe
      // 5a4a: dup
      // 5a4b: fconst_0
      // 5a4c: ldc_w 0.878
      // 5a4f: ldc_w -1.018
      // 5a52: ldc_w -0.451
      // 5a55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a5e: aastore
      // 5a5f: dup
      // 5a60: bipush 1
      // 5a61: new net/minecraft/client/animation/Keyframe
      // 5a64: dup
      // 5a65: ldc 0.04
      // 5a67: ldc_w 0.877
      // 5a6a: ldc_w -0.938
      // 5a6d: ldc_w -0.678
      // 5a70: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a79: aastore
      // 5a7a: dup
      // 5a7b: bipush 2
      // 5a7c: new net/minecraft/client/animation/Keyframe
      // 5a7f: dup
      // 5a80: ldc 0.08
      // 5a82: ldc_w 0.878
      // 5a85: ldc_w -0.75
      // 5a88: ldc_w -1.27
      // 5a8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a94: aastore
      // 5a95: dup
      // 5a96: bipush 3
      // 5a97: new net/minecraft/client/animation/Keyframe
      // 5a9a: dup
      // 5a9b: ldc 0.12
      // 5a9d: ldc_w 0.899
      // 5aa0: ldc_w -0.542
      // 5aa3: ldc_w -2.09
      // 5aa6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5aa9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aaf: aastore
      // 5ab0: dup
      // 5ab1: bipush 4
      // 5ab2: new net/minecraft/client/animation/Keyframe
      // 5ab5: dup
      // 5ab6: ldc 0.17
      // 5ab8: ldc_w 0.976
      // 5abb: ldc_w -0.414
      // 5abe: ldc_w -2.98
      // 5ac1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ac4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ac7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aca: aastore
      // 5acb: dup
      // 5acc: bipush 5
      // 5acd: new net/minecraft/client/animation/Keyframe
      // 5ad0: dup
      // 5ad1: ldc 0.21
      // 5ad3: ldc_w 1.162
      // 5ad6: ldc_w -0.504
      // 5ad9: ldc_w -3.823
      // 5adc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5adf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ae2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ae5: aastore
      // 5ae6: dup
      // 5ae7: bipush 6
      // 5ae9: new net/minecraft/client/animation/Keyframe
      // 5aec: dup
      // 5aed: ldc 0.25
      // 5aef: ldc_w 1.421
      // 5af2: ldc_w -0.943
      // 5af5: ldc_w -4.678
      // 5af8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5afb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5afe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b01: aastore
      // 5b02: dup
      // 5b03: bipush 7
      // 5b05: new net/minecraft/client/animation/Keyframe
      // 5b08: dup
      // 5b09: ldc 0.29
      // 5b0b: ldc_w 1.572
      // 5b0e: ldc_w -1.721
      // 5b11: ldc_w -5.606
      // 5b14: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b1d: aastore
      // 5b1e: dup
      // 5b1f: bipush 8
      // 5b21: new net/minecraft/client/animation/Keyframe
      // 5b24: dup
      // 5b25: ldc 0.33
      // 5b27: ldc_w 1.507
      // 5b2a: ldc_w -2.718
      // 5b2d: ldc_w -6.422
      // 5b30: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b39: aastore
      // 5b3a: dup
      // 5b3b: bipush 9
      // 5b3d: new net/minecraft/client/animation/Keyframe
      // 5b40: dup
      // 5b41: ldc 0.38
      // 5b43: ldc_w 0.809
      // 5b46: ldc_w -3.564
      // 5b49: ldc_w -5.952
      // 5b4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b55: aastore
      // 5b56: dup
      // 5b57: bipush 10
      // 5b59: new net/minecraft/client/animation/Keyframe
      // 5b5c: dup
      // 5b5d: ldc 0.42
      // 5b5f: ldc_w -0.035
      // 5b62: ldc_w -2.5
      // 5b65: ldc_w -3.889
      // 5b68: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b71: aastore
      // 5b72: dup
      // 5b73: bipush 11
      // 5b75: new net/minecraft/client/animation/Keyframe
      // 5b78: dup
      // 5b79: ldc 0.46
      // 5b7b: ldc_w -0.861
      // 5b7e: ldc_w -5.202
      // 5b81: ldc_w -3.593
      // 5b84: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b8d: aastore
      // 5b8e: dup
      // 5b8f: bipush 12
      // 5b91: new net/minecraft/client/animation/Keyframe
      // 5b94: dup
      // 5b95: ldc 0.5
      // 5b97: ldc_w -0.863
      // 5b9a: ldc_w -5.336
      // 5b9d: ldc_w -3.674
      // 5ba0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ba3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ba6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ba9: aastore
      // 5baa: dup
      // 5bab: bipush 13
      // 5bad: new net/minecraft/client/animation/Keyframe
      // 5bb0: dup
      // 5bb1: ldc 0.54
      // 5bb3: ldc_w -0.862
      // 5bb6: ldc_w -5.391
      // 5bb9: ldc_w -3.863
      // 5bbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bc5: aastore
      // 5bc6: dup
      // 5bc7: bipush 14
      // 5bc9: new net/minecraft/client/animation/Keyframe
      // 5bcc: dup
      // 5bcd: ldc 0.58
      // 5bcf: ldc_w -0.856
      // 5bd2: ldc_w -5.384
      // 5bd5: ldc_w -4.15
      // 5bd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bdb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5be1: aastore
      // 5be2: dup
      // 5be3: bipush 15
      // 5be5: new net/minecraft/client/animation/Keyframe
      // 5be8: dup
      // 5be9: ldc 0.62
      // 5beb: ldc_w -0.841
      // 5bee: ldc_w -5.333
      // 5bf1: ldc -4.522
      // 5bf3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bf6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bf9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bfc: aastore
      // 5bfd: dup
      // 5bfe: bipush 16
      // 5c00: new net/minecraft/client/animation/Keyframe
      // 5c03: dup
      // 5c04: ldc 0.67
      // 5c06: ldc_w -0.815
      // 5c09: ldc_w -5.246
      // 5c0c: ldc_w -4.967
      // 5c0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c18: aastore
      // 5c19: dup
      // 5c1a: bipush 17
      // 5c1c: new net/minecraft/client/animation/Keyframe
      // 5c1f: dup
      // 5c20: ldc_w 0.71
      // 5c23: ldc_w -0.775
      // 5c26: ldc_w -5.135
      // 5c29: ldc_w -5.468
      // 5c2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c35: aastore
      // 5c36: dup
      // 5c37: bipush 18
      // 5c39: new net/minecraft/client/animation/Keyframe
      // 5c3c: dup
      // 5c3d: ldc 0.75
      // 5c3f: ldc_w -0.717
      // 5c42: ldc_w -5.006
      // 5c45: ldc_w -6.011
      // 5c48: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c51: aastore
      // 5c52: dup
      // 5c53: bipush 19
      // 5c55: new net/minecraft/client/animation/Keyframe
      // 5c58: dup
      // 5c59: ldc_w 0.79
      // 5c5c: ldc_w -0.639
      // 5c5f: ldc_w -4.868
      // 5c62: ldc_w -6.58
      // 5c65: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c6e: aastore
      // 5c6f: dup
      // 5c70: bipush 20
      // 5c72: new net/minecraft/client/animation/Keyframe
      // 5c75: dup
      // 5c76: ldc_w 0.83
      // 5c79: ldc_w -0.538
      // 5c7c: ldc_w -4.725
      // 5c7f: ldc_w -7.157
      // 5c82: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c8b: aastore
      // 5c8c: dup
      // 5c8d: bipush 21
      // 5c8f: new net/minecraft/client/animation/Keyframe
      // 5c92: dup
      // 5c93: ldc_w 0.88
      // 5c96: ldc_w -0.41
      // 5c99: ldc_w -4.587
      // 5c9c: ldc_w -7.726
      // 5c9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ca2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ca5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ca8: aastore
      // 5ca9: dup
      // 5caa: bipush 22
      // 5cac: new net/minecraft/client/animation/Keyframe
      // 5caf: dup
      // 5cb0: ldc_w 0.92
      // 5cb3: ldc_w -0.254
      // 5cb6: ldc -4.459
      // 5cb8: ldc_w -8.27
      // 5cbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cc4: aastore
      // 5cc5: dup
      // 5cc6: bipush 23
      // 5cc8: new net/minecraft/client/animation/Keyframe
      // 5ccb: dup
      // 5ccc: ldc 0.96
      // 5cce: ldc_w -0.067
      // 5cd1: ldc_w -4.353
      // 5cd4: ldc_w -8.77
      // 5cd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ce0: aastore
      // 5ce1: dup
      // 5ce2: bipush 24
      // 5ce4: new net/minecraft/client/animation/Keyframe
      // 5ce7: dup
      // 5ce8: fconst_1
      // 5ce9: ldc_w 0.152
      // 5cec: ldc_w -4.279
      // 5cef: ldc_w -9.21
      // 5cf2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cf5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cf8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cfb: aastore
      // 5cfc: dup
      // 5cfd: bipush 25
      // 5cff: new net/minecraft/client/animation/Keyframe
      // 5d02: dup
      // 5d03: ldc 1.04
      // 5d05: ldc_w 0.404
      // 5d08: ldc_w -4.256
      // 5d0b: ldc_w -9.572
      // 5d0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d17: aastore
      // 5d18: dup
      // 5d19: bipush 26
      // 5d1b: new net/minecraft/client/animation/Keyframe
      // 5d1e: dup
      // 5d1f: ldc 1.08
      // 5d21: ldc_w 0.688
      // 5d24: ldc_w -4.311
      // 5d27: ldc_w -9.842
      // 5d2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d33: aastore
      // 5d34: dup
      // 5d35: bipush 27
      // 5d37: new net/minecraft/client/animation/Keyframe
      // 5d3a: dup
      // 5d3b: ldc 1.12
      // 5d3d: fconst_1
      // 5d3e: ldc_w -4.494
      // 5d41: ldc_w -10.002
      // 5d44: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d4d: aastore
      // 5d4e: dup
      // 5d4f: bipush 28
      // 5d51: new net/minecraft/client/animation/Keyframe
      // 5d54: dup
      // 5d55: ldc 1.17
      // 5d57: ldc_w 1.227
      // 5d5a: ldc_w -3.006
      // 5d5d: ldc_w -10.083
      // 5d60: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d69: aastore
      // 5d6a: dup
      // 5d6b: bipush 29
      // 5d6d: new net/minecraft/client/animation/Keyframe
      // 5d70: dup
      // 5d71: ldc 1.21
      // 5d73: ldc_w 1.375
      // 5d76: ldc_w 0.63
      // 5d79: ldc_w -10.393
      // 5d7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d85: aastore
      // 5d86: dup
      // 5d87: bipush 30
      // 5d89: new net/minecraft/client/animation/Keyframe
      // 5d8c: dup
      // 5d8d: ldc 1.25
      // 5d8f: ldc_w -1.611
      // 5d92: ldc_w -3.221
      // 5d95: ldc_w -2.276
      // 5d98: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5da1: aastore
      // 5da2: dup
      // 5da3: bipush 31
      // 5da5: new net/minecraft/client/animation/Keyframe
      // 5da8: dup
      // 5da9: ldc 1.29
      // 5dab: ldc_w -0.701
      // 5dae: ldc_w -2.015
      // 5db1: ldc_w -3.6
      // 5db4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5db7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dbd: aastore
      // 5dbe: dup
      // 5dbf: bipush 32
      // 5dc1: new net/minecraft/client/animation/Keyframe
      // 5dc4: dup
      // 5dc5: ldc 1.33
      // 5dc7: ldc_w -0.017
      // 5dca: ldc_w -0.718
      // 5dcd: ldc_w -4.567
      // 5dd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dd9: aastore
      // 5dda: dup
      // 5ddb: bipush 33
      // 5ddd: new net/minecraft/client/animation/Keyframe
      // 5de0: dup
      // 5de1: ldc 1.38
      // 5de3: ldc_w 0.518
      // 5de6: ldc_w -0.498
      // 5de9: ldc_w -5.144
      // 5dec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5def: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5df2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5df5: aastore
      // 5df6: dup
      // 5df7: bipush 34
      // 5df9: new net/minecraft/client/animation/Keyframe
      // 5dfc: dup
      // 5dfd: ldc 1.42
      // 5dff: ldc_w 0.892
      // 5e02: ldc_w -1.079
      // 5e05: ldc_w -4.706
      // 5e08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e11: aastore
      // 5e12: dup
      // 5e13: bipush 35
      // 5e15: new net/minecraft/client/animation/Keyframe
      // 5e18: dup
      // 5e19: ldc 1.46
      // 5e1b: ldc_w 1.138
      // 5e1e: ldc_w -1.985
      // 5e21: ldc_w -3.712
      // 5e24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e2d: aastore
      // 5e2e: dup
      // 5e2f: bipush 36
      // 5e31: new net/minecraft/client/animation/Keyframe
      // 5e34: dup
      // 5e35: ldc 1.5
      // 5e37: ldc_w 1.271
      // 5e3a: ldc_w -2.907
      // 5e3d: ldc_w -2.551
      // 5e40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e49: aastore
      // 5e4a: dup
      // 5e4b: bipush 37
      // 5e4d: new net/minecraft/client/animation/Keyframe
      // 5e50: dup
      // 5e51: ldc 1.54
      // 5e53: ldc_w 1.334
      // 5e56: ldc_w -3.722
      // 5e59: ldc_w -1.585
      // 5e5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e65: aastore
      // 5e66: dup
      // 5e67: bipush 38
      // 5e69: new net/minecraft/client/animation/Keyframe
      // 5e6c: dup
      // 5e6d: ldc 1.58
      // 5e6f: ldc_w 1.359
      // 5e72: ldc_w -4.312
      // 5e75: ldc_w -0.974
      // 5e78: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e81: aastore
      // 5e82: dup
      // 5e83: bipush 39
      // 5e85: new net/minecraft/client/animation/Keyframe
      // 5e88: dup
      // 5e89: ldc 1.62
      // 5e8b: ldc_w 1.364
      // 5e8e: ldc_w -4.539
      // 5e91: ldc_w -0.756
      // 5e94: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e9d: aastore
      // 5e9e: dup
      // 5e9f: bipush 40
      // 5ea1: new net/minecraft/client/animation/Keyframe
      // 5ea4: dup
      // 5ea5: ldc 1.67
      // 5ea7: ldc_w 1.367
      // 5eaa: ldc_w -4.542
      // 5ead: ldc_w -0.753
      // 5eb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5eb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5eb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5eb9: aastore
      // 5eba: dup
      // 5ebb: bipush 41
      // 5ebd: new net/minecraft/client/animation/Keyframe
      // 5ec0: dup
      // 5ec1: ldc 1.71
      // 5ec3: ldc_w 1.343
      // 5ec6: ldc_w -4.348
      // 5ec9: ldc_w -0.592
      // 5ecc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ecf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ed2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ed5: aastore
      // 5ed6: dup
      // 5ed7: bipush 42
      // 5ed9: new net/minecraft/client/animation/Keyframe
      // 5edc: dup
      // 5edd: ldc 1.75
      // 5edf: ldc_w 1.295
      // 5ee2: ldc_w -3.927
      // 5ee5: ldc_w -0.367
      // 5ee8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5eeb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5eee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ef1: aastore
      // 5ef2: dup
      // 5ef3: bipush 43
      // 5ef5: new net/minecraft/client/animation/Keyframe
      // 5ef8: dup
      // 5ef9: ldc 1.79
      // 5efb: ldc_w 1.233
      // 5efe: ldc_w -3.397
      // 5f01: ldc_w -0.171
      // 5f04: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f0d: aastore
      // 5f0e: dup
      // 5f0f: bipush 44
      // 5f11: new net/minecraft/client/animation/Keyframe
      // 5f14: dup
      // 5f15: ldc 1.83
      // 5f17: ldc_w 1.159
      // 5f1a: ldc_w -2.825
      // 5f1d: ldc_w -0.04
      // 5f20: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f29: aastore
      // 5f2a: dup
      // 5f2b: bipush 45
      // 5f2d: new net/minecraft/client/animation/Keyframe
      // 5f30: dup
      // 5f31: ldc 1.88
      // 5f33: ldc 1.08
      // 5f35: ldc_w -2.264
      // 5f38: ldc 0.015
      // 5f3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f43: aastore
      // 5f44: dup
      // 5f45: bipush 46
      // 5f47: new net/minecraft/client/animation/Keyframe
      // 5f4a: dup
      // 5f4b: ldc 1.92
      // 5f4d: ldc_w 1.002
      // 5f50: ldc_w -1.759
      // 5f53: ldc_w -0.002
      // 5f56: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f5f: aastore
      // 5f60: dup
      // 5f61: bipush 47
      // 5f63: new net/minecraft/client/animation/Keyframe
      // 5f66: dup
      // 5f67: ldc 1.96
      // 5f69: ldc_w 0.933
      // 5f6c: ldc_w -1.353
      // 5f6f: ldc_w -0.081
      // 5f72: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f7b: aastore
      // 5f7c: dup
      // 5f7d: bipush 48
      // 5f7f: new net/minecraft/client/animation/Keyframe
      // 5f82: dup
      // 5f83: fconst_2
      // 5f84: ldc_w 0.885
      // 5f87: ldc_w -1.085
      // 5f8a: ldc_w -0.209
      // 5f8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f96: aastore
      // 5f97: dup
      // 5f98: bipush 49
      // 5f9a: new net/minecraft/client/animation/Keyframe
      // 5f9d: dup
      // 5f9e: ldc 2.04
      // 5fa0: ldc_w 0.872
      // 5fa3: ldc_w -0.999
      // 5fa6: ldc_w -0.384
      // 5fa9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5faf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fb2: aastore
      // 5fb3: dup
      // 5fb4: bipush 50
      // 5fb6: new net/minecraft/client/animation/Keyframe
      // 5fb9: dup
      // 5fba: ldc 2.08
      // 5fbc: ldc_w 0.876
      // 5fbf: ldc_w -1.012
      // 5fc2: ldc_w -0.49
      // 5fc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fce: aastore
      // 5fcf: dup
      // 5fd0: bipush 51
      // 5fd2: new net/minecraft/client/animation/Keyframe
      // 5fd5: dup
      // 5fd6: ldc 2.21
      // 5fd8: ldc_w 0.878
      // 5fdb: ldc_w -1.018
      // 5fde: ldc_w -0.451
      // 5fe1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fe4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fe7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fea: aastore
      // 5feb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5fee: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5ff1: ldc_w "CTR_leg_right"
      // 5ff4: new net/minecraft/client/animation/AnimationChannel
      // 5ff7: dup
      // 5ff8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5ffb: bipush 46
      // 5ffd: anewarray 28
      // 6000: dup
      // 6001: bipush 0
      // 6002: new net/minecraft/client/animation/Keyframe
      // 6005: dup
      // 6006: fconst_0
      // 6007: ldc_w -2.789
      // 600a: ldc_w 0.901
      // 600d: ldc_w 2.789
      // 6010: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6013: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6016: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6019: aastore
      // 601a: dup
      // 601b: bipush 1
      // 601c: new net/minecraft/client/animation/Keyframe
      // 601f: dup
      // 6020: ldc 0.04
      // 6022: ldc_w -2.791
      // 6025: ldc_w 0.965
      // 6028: ldc_w 2.791
      // 602b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 602e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6031: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6034: aastore
      // 6035: dup
      // 6036: bipush 2
      // 6037: new net/minecraft/client/animation/Keyframe
      // 603a: dup
      // 603b: ldc 0.08
      // 603d: ldc_w -2.796
      // 6040: ldc_w 1.218
      // 6043: ldc_w 2.796
      // 6046: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6049: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 604c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 604f: aastore
      // 6050: dup
      // 6051: bipush 3
      // 6052: new net/minecraft/client/animation/Keyframe
      // 6055: dup
      // 6056: ldc 0.12
      // 6058: ldc_w -2.804
      // 605b: ldc_w 1.799
      // 605e: ldc_w 2.804
      // 6061: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6064: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6067: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 606a: aastore
      // 606b: dup
      // 606c: bipush 4
      // 606d: new net/minecraft/client/animation/Keyframe
      // 6070: dup
      // 6071: ldc 0.17
      // 6073: ldc_w -2.822
      // 6076: ldc_w 2.942
      // 6079: ldc_w 2.822
      // 607c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 607f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6082: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6085: aastore
      // 6086: dup
      // 6087: bipush 5
      // 6088: new net/minecraft/client/animation/Keyframe
      // 608b: dup
      // 608c: ldc 0.21
      // 608e: ldc_w -2.873
      // 6091: ldc_w 4.816
      // 6094: ldc_w 2.873
      // 6097: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 609a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 609d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60a0: aastore
      // 60a1: dup
      // 60a2: bipush 6
      // 60a4: new net/minecraft/client/animation/Keyframe
      // 60a7: dup
      // 60a8: ldc 0.25
      // 60aa: ldc_w -3.074
      // 60ad: ldc_w 6.777
      // 60b0: ldc_w 3.074
      // 60b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60bc: aastore
      // 60bd: dup
      // 60be: bipush 7
      // 60c0: new net/minecraft/client/animation/Keyframe
      // 60c3: dup
      // 60c4: ldc 0.29
      // 60c6: ldc_w -3.531
      // 60c9: ldc_w 7.927
      // 60cc: ldc_w 3.531
      // 60cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60d8: aastore
      // 60d9: dup
      // 60da: bipush 8
      // 60dc: new net/minecraft/client/animation/Keyframe
      // 60df: dup
      // 60e0: ldc 0.33
      // 60e2: ldc_w -4.098
      // 60e5: ldc_w 8.434
      // 60e8: ldc_w 4.098
      // 60eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60f4: aastore
      // 60f5: dup
      // 60f6: bipush 9
      // 60f8: new net/minecraft/client/animation/Keyframe
      // 60fb: dup
      // 60fc: ldc 0.38
      // 60fe: ldc_w -4.534
      // 6101: ldc_w 8.8
      // 6104: ldc_w 4.534
      // 6107: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 610a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 610d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6110: aastore
      // 6111: dup
      // 6112: bipush 10
      // 6114: new net/minecraft/client/animation/Keyframe
      // 6117: dup
      // 6118: ldc 0.42
      // 611a: ldc_w -4.703
      // 611d: ldc_w 9.346
      // 6120: ldc_w 4.703
      // 6123: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6126: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6129: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 612c: aastore
      // 612d: dup
      // 612e: bipush 11
      // 6130: new net/minecraft/client/animation/Keyframe
      // 6133: dup
      // 6134: ldc 0.5
      // 6136: ldc_w -4.413
      // 6139: ldc_w 10.917
      // 613c: ldc_w 4.413
      // 613f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6142: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6145: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6148: aastore
      // 6149: dup
      // 614a: bipush 12
      // 614c: new net/minecraft/client/animation/Keyframe
      // 614f: dup
      // 6150: ldc 0.54
      // 6152: ldc_w -4.299
      // 6155: ldc_w 11.395
      // 6158: ldc_w 4.299
      // 615b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 615e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6161: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6164: aastore
      // 6165: dup
      // 6166: bipush 13
      // 6168: new net/minecraft/client/animation/Keyframe
      // 616b: dup
      // 616c: ldc 0.58
      // 616e: ldc_w -4.24
      // 6171: ldc_w 11.646
      // 6174: ldc_w 4.24
      // 6177: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 617a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 617d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6180: aastore
      // 6181: dup
      // 6182: bipush 14
      // 6184: new net/minecraft/client/animation/Keyframe
      // 6187: dup
      // 6188: ldc 0.62
      // 618a: ldc_w -4.225
      // 618d: ldc_w 11.714
      // 6190: ldc_w 4.225
      // 6193: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6196: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6199: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 619c: aastore
      // 619d: dup
      // 619e: bipush 15
      // 61a0: new net/minecraft/client/animation/Keyframe
      // 61a3: dup
      // 61a4: ldc 0.67
      // 61a6: ldc_w -4.245
      // 61a9: ldc_w 11.637
      // 61ac: ldc_w 4.245
      // 61af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61b8: aastore
      // 61b9: dup
      // 61ba: bipush 16
      // 61bc: new net/minecraft/client/animation/Keyframe
      // 61bf: dup
      // 61c0: ldc 0.75
      // 61c2: ldc_w -4.361
      // 61c5: ldc_w 11.17
      // 61c8: ldc_w 4.361
      // 61cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61d4: aastore
      // 61d5: dup
      // 61d6: bipush 17
      // 61d8: new net/minecraft/client/animation/Keyframe
      // 61db: dup
      // 61dc: ldc_w 0.92
      // 61df: ldc_w -4.712
      // 61e2: ldc_w 9.767
      // 61e5: ldc_w 4.712
      // 61e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61f1: aastore
      // 61f2: dup
      // 61f3: bipush 18
      // 61f5: new net/minecraft/client/animation/Keyframe
      // 61f8: dup
      // 61f9: ldc 0.96
      // 61fb: ldc_w -4.782
      // 61fe: ldc_w 9.486
      // 6201: ldc_w 4.782
      // 6204: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6207: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 620a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 620d: aastore
      // 620e: dup
      // 620f: bipush 19
      // 6211: new net/minecraft/client/animation/Keyframe
      // 6214: dup
      // 6215: fconst_1
      // 6216: ldc_w -4.824
      // 6219: ldc_w 9.306
      // 621c: ldc_w 4.824
      // 621f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6222: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6225: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6228: aastore
      // 6229: dup
      // 622a: bipush 20
      // 622c: new net/minecraft/client/animation/Keyframe
      // 622f: dup
      // 6230: ldc 1.04
      // 6232: ldc_w -4.824
      // 6235: ldc_w 9.287
      // 6238: ldc_w 4.824
      // 623b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 623e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6241: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6244: aastore
      // 6245: dup
      // 6246: bipush 21
      // 6248: new net/minecraft/client/animation/Keyframe
      // 624b: dup
      // 624c: ldc 1.08
      // 624e: ldc_w -4.759
      // 6251: ldc_w 9.517
      // 6254: ldc_w 4.76
      // 6257: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 625a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 625d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6260: aastore
      // 6261: dup
      // 6262: bipush 22
      // 6264: new net/minecraft/client/animation/Keyframe
      // 6267: dup
      // 6268: ldc 1.12
      // 626a: ldc_w -4.598
      // 626d: ldc_w 10.154
      // 6270: ldc_w 4.598
      // 6273: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6276: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6279: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 627c: aastore
      // 627d: dup
      // 627e: bipush 23
      // 6280: new net/minecraft/client/animation/Keyframe
      // 6283: dup
      // 6284: ldc 1.17
      // 6286: ldc_w -4.348
      // 6289: ldc_w 10.333
      // 628c: ldc_w 4.348
      // 628f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6292: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6295: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6298: aastore
      // 6299: dup
      // 629a: bipush 24
      // 629c: new net/minecraft/client/animation/Keyframe
      // 629f: dup
      // 62a0: ldc 1.21
      // 62a2: ldc_w -3.715
      // 62a5: ldc_w 7.355
      // 62a8: ldc_w 3.715
      // 62ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62b4: aastore
      // 62b5: dup
      // 62b6: bipush 25
      // 62b8: new net/minecraft/client/animation/Keyframe
      // 62bb: dup
      // 62bc: ldc 1.25
      // 62be: ldc_w -4.256
      // 62c1: ldc_w -20.058
      // 62c4: ldc_w 4.256
      // 62c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62d0: aastore
      // 62d1: dup
      // 62d2: bipush 26
      // 62d4: new net/minecraft/client/animation/Keyframe
      // 62d7: dup
      // 62d8: ldc 1.29
      // 62da: ldc_w -3.808
      // 62dd: ldc_w -44.707
      // 62e0: ldc_w 3.808
      // 62e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62ec: aastore
      // 62ed: dup
      // 62ee: bipush 27
      // 62f0: new net/minecraft/client/animation/Keyframe
      // 62f3: dup
      // 62f4: ldc 1.33
      // 62f6: ldc_w -1.804
      // 62f9: ldc_w -64.038
      // 62fc: ldc_w 1.804
      // 62ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6302: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6305: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6308: aastore
      // 6309: dup
      // 630a: bipush 28
      // 630c: new net/minecraft/client/animation/Keyframe
      // 630f: dup
      // 6310: ldc 1.38
      // 6312: ldc_w -1.569
      // 6315: ldc_w -62.226
      // 6318: ldc_w 1.569
      // 631b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 631e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6321: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6324: aastore
      // 6325: dup
      // 6326: bipush 29
      // 6328: new net/minecraft/client/animation/Keyframe
      // 632b: dup
      // 632c: ldc 1.42
      // 632e: ldc_w -1.467
      // 6331: ldc_w -61.04
      // 6334: ldc_w 1.467
      // 6337: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 633a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 633d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6340: aastore
      // 6341: dup
      // 6342: bipush 30
      // 6344: new net/minecraft/client/animation/Keyframe
      // 6347: dup
      // 6348: ldc 1.46
      // 634a: ldc_w -1.521
      // 634d: ldc_w -60.827
      // 6350: ldc_w 1.521
      // 6353: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6356: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6359: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 635c: aastore
      // 635d: dup
      // 635e: bipush 31
      // 6360: new net/minecraft/client/animation/Keyframe
      // 6363: dup
      // 6364: ldc 1.54
      // 6366: ldc_w -1.575
      // 6369: ldc_w -61.982
      // 636c: ldc_w 1.575
      // 636f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6372: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6375: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6378: aastore
      // 6379: dup
      // 637a: bipush 32
      // 637c: new net/minecraft/client/animation/Keyframe
      // 637f: dup
      // 6380: ldc 1.58
      // 6382: ldc_w -1.543
      // 6385: ldc_w -62.028
      // 6388: ldc_w 1.543
      // 638b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 638e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6391: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6394: aastore
      // 6395: dup
      // 6396: bipush 33
      // 6398: new net/minecraft/client/animation/Keyframe
      // 639b: dup
      // 639c: ldc 1.62
      // 639e: ldc_w -1.526
      // 63a1: ldc_w -61.849
      // 63a4: ldc_w 1.526
      // 63a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63b0: aastore
      // 63b1: dup
      // 63b2: bipush 34
      // 63b4: new net/minecraft/client/animation/Keyframe
      // 63b7: dup
      // 63b8: ldc 1.67
      // 63ba: ldc_w -1.659
      // 63bd: ldc_w -61.624
      // 63c0: ldc_w 1.659
      // 63c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63cc: aastore
      // 63cd: dup
      // 63ce: bipush 35
      // 63d0: new net/minecraft/client/animation/Keyframe
      // 63d3: dup
      // 63d4: ldc 1.71
      // 63d6: ldc_w -1.593
      // 63d9: ldc_w -60.934
      // 63dc: ldc 1.593
      // 63de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63e7: aastore
      // 63e8: dup
      // 63e9: bipush 36
      // 63eb: new net/minecraft/client/animation/Keyframe
      // 63ee: dup
      // 63ef: ldc 1.75
      // 63f1: ldc_w -1.301
      // 63f4: ldc_w -59.191
      // 63f7: ldc_w 1.301
      // 63fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6400: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6403: aastore
      // 6404: dup
      // 6405: bipush 37
      // 6407: new net/minecraft/client/animation/Keyframe
      // 640a: dup
      // 640b: ldc 1.79
      // 640d: ldc_w -0.968
      // 6410: ldc_w -56.828
      // 6413: ldc_w 0.968
      // 6416: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6419: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 641c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 641f: aastore
      // 6420: dup
      // 6421: bipush 38
      // 6423: new net/minecraft/client/animation/Keyframe
      // 6426: dup
      // 6427: ldc 1.83
      // 6429: ldc_w -0.644
      // 642c: ldc_w -54.18
      // 642f: ldc_w 0.644
      // 6432: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6435: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6438: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 643b: aastore
      // 643c: dup
      // 643d: bipush 39
      // 643f: new net/minecraft/client/animation/Keyframe
      // 6442: dup
      // 6443: ldc 1.88
      // 6445: ldc_w -0.352
      // 6448: ldc_w -51.465
      // 644b: ldc_w 0.352
      // 644e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6451: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6454: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6457: aastore
      // 6458: dup
      // 6459: bipush 40
      // 645b: new net/minecraft/client/animation/Keyframe
      // 645e: dup
      // 645f: ldc 1.92
      // 6461: ldc_w -0.773
      // 6464: ldc_w -45.49
      // 6467: ldc_w 0.773
      // 646a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 646d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6470: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6473: aastore
      // 6474: dup
      // 6475: bipush 41
      // 6477: new net/minecraft/client/animation/Keyframe
      // 647a: dup
      // 647b: ldc 1.96
      // 647d: ldc_w -2.04
      // 6480: ldc_w -33.288
      // 6483: ldc 2.04
      // 6485: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6488: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 648b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 648e: aastore
      // 648f: dup
      // 6490: bipush 42
      // 6492: new net/minecraft/client/animation/Keyframe
      // 6495: dup
      // 6496: fconst_2
      // 6497: ldc_w -3.163
      // 649a: ldc_w -16.565
      // 649d: ldc_w 3.163
      // 64a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64a9: aastore
      // 64aa: dup
      // 64ab: bipush 43
      // 64ad: new net/minecraft/client/animation/Keyframe
      // 64b0: dup
      // 64b1: ldc 2.04
      // 64b3: ldc_w -3.092
      // 64b6: ldc_w -3.171
      // 64b9: ldc_w 3.092
      // 64bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64c5: aastore
      // 64c6: dup
      // 64c7: bipush 44
      // 64c9: new net/minecraft/client/animation/Keyframe
      // 64cc: dup
      // 64cd: ldc 2.08
      // 64cf: ldc_w -2.773
      // 64d2: ldc_w 1.217
      // 64d5: ldc_w 2.773
      // 64d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64e1: aastore
      // 64e2: dup
      // 64e3: bipush 45
      // 64e5: new net/minecraft/client/animation/Keyframe
      // 64e8: dup
      // 64e9: ldc 2.21
      // 64eb: ldc_w -2.789
      // 64ee: ldc_w 0.901
      // 64f1: ldc_w 2.789
      // 64f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64fd: aastore
      // 64fe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6501: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6504: ldc_w "CTR_leg_right"
      // 6507: new net/minecraft/client/animation/AnimationChannel
      // 650a: dup
      // 650b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 650e: bipush 47
      // 6510: anewarray 28
      // 6513: dup
      // 6514: bipush 0
      // 6515: new net/minecraft/client/animation/Keyframe
      // 6518: dup
      // 6519: fconst_0
      // 651a: ldc_w 0.698
      // 651d: ldc_w 0.987
      // 6520: ldc_w 0.254
      // 6523: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6526: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6529: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 652c: aastore
      // 652d: dup
      // 652e: bipush 1
      // 652f: new net/minecraft/client/animation/Keyframe
      // 6532: dup
      // 6533: ldc 0.04
      // 6535: ldc_w 0.698
      // 6538: ldc_w 0.988
      // 653b: ldc_w 0.247
      // 653e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6541: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6544: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6547: aastore
      // 6548: dup
      // 6549: bipush 2
      // 654a: new net/minecraft/client/animation/Keyframe
      // 654d: dup
      // 654e: ldc 0.08
      // 6550: ldc_w 0.699
      // 6553: ldc_w 0.989
      // 6556: ldc_w 0.217
      // 6559: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 655c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 655f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6562: aastore
      // 6563: dup
      // 6564: bipush 3
      // 6565: new net/minecraft/client/animation/Keyframe
      // 6568: dup
      // 6569: ldc 0.12
      // 656b: ldc_w 0.697
      // 656e: ldc_w 0.98
      // 6571: ldc_w 0.145
      // 6574: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6577: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 657a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 657d: aastore
      // 657e: dup
      // 657f: bipush 4
      // 6580: new net/minecraft/client/animation/Keyframe
      // 6583: dup
      // 6584: ldc 0.17
      // 6586: ldc_w 0.691
      // 6589: ldc_w 0.944
      // 658c: ldc_w -0.001
      // 658f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6592: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6595: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6598: aastore
      // 6599: dup
      // 659a: bipush 5
      // 659b: new net/minecraft/client/animation/Keyframe
      // 659e: dup
      // 659f: ldc 0.21
      // 65a1: ldc_w 0.682
      // 65a4: ldc_w 0.857
      // 65a7: ldc_w -0.24
      // 65aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 65ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65b3: aastore
      // 65b4: dup
      // 65b5: bipush 6
      // 65b7: new net/minecraft/client/animation/Keyframe
      // 65ba: dup
      // 65bb: ldc 0.25
      // 65bd: ldc_w 0.699
      // 65c0: ldc_w 0.732
      // 65c3: ldc_w -0.486
      // 65c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 65c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65cf: aastore
      // 65d0: dup
      // 65d1: bipush 7
      // 65d3: new net/minecraft/client/animation/Keyframe
      // 65d6: dup
      // 65d7: ldc 0.29
      // 65d9: ldc_w 0.769
      // 65dc: ldc_w 0.607
      // 65df: ldc_w -0.633
      // 65e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 65e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65eb: aastore
      // 65ec: dup
      // 65ed: bipush 8
      // 65ef: new net/minecraft/client/animation/Keyframe
      // 65f2: dup
      // 65f3: ldc 0.33
      // 65f5: ldc_w 0.85
      // 65f8: ldc_w 0.437
      // 65fb: ldc_w -0.716
      // 65fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6601: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6604: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6607: aastore
      // 6608: dup
      // 6609: bipush 9
      // 660b: new net/minecraft/client/animation/Keyframe
      // 660e: dup
      // 660f: ldc 0.38
      // 6611: ldc_w 0.883
      // 6614: ldc_w 0.199
      // 6617: ldc_w -0.795
      // 661a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 661d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6620: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6623: aastore
      // 6624: dup
      // 6625: bipush 10
      // 6627: new net/minecraft/client/animation/Keyframe
      // 662a: dup
      // 662b: ldc 0.42
      // 662d: ldc_w 0.85
      // 6630: ldc_w -0.067
      // 6633: ldc_w -0.894
      // 6636: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6639: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 663c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 663f: aastore
      // 6640: dup
      // 6641: bipush 11
      // 6643: new net/minecraft/client/animation/Keyframe
      // 6646: dup
      // 6647: ldc 0.46
      // 6649: ldc_w 0.769
      // 664c: ldc_w -0.318
      // 664f: ldc_w -1.01
      // 6652: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6655: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6658: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 665b: aastore
      // 665c: dup
      // 665d: bipush 12
      // 665f: new net/minecraft/client/animation/Keyframe
      // 6662: dup
      // 6663: ldc 0.5
      // 6665: ldc 0.689
      // 6667: ldc_w -0.512
      // 666a: ldc_w -1.107
      // 666d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6670: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6673: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6676: aastore
      // 6677: dup
      // 6678: bipush 13
      // 667a: new net/minecraft/client/animation/Keyframe
      // 667d: dup
      // 667e: ldc 0.54
      // 6680: ldc_w 0.641
      // 6683: ldc_w -0.631
      // 6686: ldc_w -1.164
      // 6689: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 668c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 668f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6692: aastore
      // 6693: dup
      // 6694: bipush 14
      // 6696: new net/minecraft/client/animation/Keyframe
      // 6699: dup
      // 669a: ldc 0.58
      // 669c: ldc_w 0.617
      // 669f: ldc_w -0.692
      // 66a2: ldc_w -1.193
      // 66a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66ae: aastore
      // 66af: dup
      // 66b0: bipush 15
      // 66b2: new net/minecraft/client/animation/Keyframe
      // 66b5: dup
      // 66b6: ldc 0.62
      // 66b8: ldc_w 0.612
      // 66bb: ldc_w -0.705
      // 66be: ldc_w -1.2
      // 66c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66ca: aastore
      // 66cb: dup
      // 66cc: bipush 16
      // 66ce: new net/minecraft/client/animation/Keyframe
      // 66d1: dup
      // 66d2: ldc 0.67
      // 66d4: ldc 0.62
      // 66d6: ldc_w -0.682
      // 66d9: ldc_w -1.191
      // 66dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66e5: aastore
      // 66e6: dup
      // 66e7: bipush 17
      // 66e9: new net/minecraft/client/animation/Keyframe
      // 66ec: dup
      // 66ed: ldc_w 0.71
      // 66f0: ldc_w 0.64
      // 66f3: ldc_w -0.631
      // 66f6: ldc_w -1.168
      // 66f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6702: aastore
      // 6703: dup
      // 6704: bipush 18
      // 6706: new net/minecraft/client/animation/Keyframe
      // 6709: dup
      // 670a: ldc_w 0.92
      // 670d: ldc_w 0.817
      // 6710: ldc_w -0.208
      // 6713: ldc_w -0.957
      // 6716: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6719: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 671c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 671f: aastore
      // 6720: dup
      // 6721: bipush 19
      // 6723: new net/minecraft/client/animation/Keyframe
      // 6726: dup
      // 6727: ldc 0.96
      // 6729: ldc_w 0.847
      // 672c: ldc_w -0.139
      // 672f: ldc_w -0.919
      // 6732: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6735: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6738: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 673b: aastore
      // 673c: dup
      // 673d: bipush 20
      // 673f: new net/minecraft/client/animation/Keyframe
      // 6742: dup
      // 6743: fconst_1
      // 6744: ldc_w 0.866
      // 6747: ldc_w -0.097
      // 674a: ldc_w -0.895
      // 674d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6750: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6753: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6756: aastore
      // 6757: dup
      // 6758: bipush 21
      // 675a: new net/minecraft/client/animation/Keyframe
      // 675d: dup
      // 675e: ldc 1.04
      // 6760: ldc_w 0.867
      // 6763: ldc_w -0.095
      // 6766: ldc_w -0.893
      // 6769: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 676c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 676f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6772: aastore
      // 6773: dup
      // 6774: bipush 22
      // 6776: new net/minecraft/client/animation/Keyframe
      // 6779: dup
      // 677a: ldc 1.08
      // 677c: ldc_w 0.839
      // 677f: ldc_w -0.155
      // 6782: ldc_w -0.925
      // 6785: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6788: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 678b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 678e: aastore
      // 678f: dup
      // 6790: bipush 23
      // 6792: new net/minecraft/client/animation/Keyframe
      // 6795: dup
      // 6796: ldc 1.12
      // 6798: ldc_w 0.769
      // 679b: ldc_w -0.318
      // 679e: ldc_w -1.01
      // 67a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67aa: aastore
      // 67ab: dup
      // 67ac: bipush 24
      // 67ae: new net/minecraft/client/animation/Keyframe
      // 67b1: dup
      // 67b2: ldc 1.17
      // 67b4: ldc_w 0.656
      // 67b7: ldc_w -0.61
      // 67ba: ldc_w -1.073
      // 67bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67c6: aastore
      // 67c7: dup
      // 67c8: bipush 25
      // 67ca: new net/minecraft/client/animation/Keyframe
      // 67cd: dup
      // 67ce: ldc 1.21
      // 67d0: ldc_w 0.45
      // 67d3: ldc_w -1.112
      // 67d6: ldc_w -0.934
      // 67d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67e2: aastore
      // 67e3: dup
      // 67e4: bipush 26
      // 67e6: new net/minecraft/client/animation/Keyframe
      // 67e9: dup
      // 67ea: ldc 1.25
      // 67ec: ldc_w -0.025
      // 67ef: ldc_w -3.729
      // 67f2: ldc_w -0.601
      // 67f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67fe: aastore
      // 67ff: dup
      // 6800: bipush 27
      // 6802: new net/minecraft/client/animation/Keyframe
      // 6805: dup
      // 6806: ldc 1.29
      // 6808: ldc_w 0.1
      // 680b: ldc_w -3.73
      // 680e: ldc_w 1.193
      // 6811: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6814: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6817: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 681a: aastore
      // 681b: dup
      // 681c: bipush 28
      // 681e: new net/minecraft/client/animation/Keyframe
      // 6821: dup
      // 6822: ldc 1.33
      // 6824: ldc_w -0.217
      // 6827: ldc_w -4.885
      // 682a: ldc_w 0.53
      // 682d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6830: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6833: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6836: aastore
      // 6837: dup
      // 6838: bipush 29
      // 683a: new net/minecraft/client/animation/Keyframe
      // 683d: dup
      // 683e: ldc 1.38
      // 6840: ldc_w -0.224
      // 6843: ldc_w -4.813
      // 6846: ldc_w 0.502
      // 6849: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 684c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 684f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6852: aastore
      // 6853: dup
      // 6854: bipush 30
      // 6856: new net/minecraft/client/animation/Keyframe
      // 6859: dup
      // 685a: ldc 1.42
      // 685c: ldc_w -0.229
      // 685f: ldc_w -4.772
      // 6862: ldc_w 0.455
      // 6865: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6868: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 686b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 686e: aastore
      // 686f: dup
      // 6870: bipush 31
      // 6872: new net/minecraft/client/animation/Keyframe
      // 6875: dup
      // 6876: ldc 1.46
      // 6878: ldc_w -0.225
      // 687b: ldc_w -4.763
      // 687e: ldc_w 0.457
      // 6881: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6884: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6887: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 688a: aastore
      // 688b: dup
      // 688c: bipush 32
      // 688e: new net/minecraft/client/animation/Keyframe
      // 6891: dup
      // 6892: ldc 1.5
      // 6894: ldc_w -0.222
      // 6897: ldc_w -4.784
      // 689a: ldc_w 0.485
      // 689d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68a6: aastore
      // 68a7: dup
      // 68a8: bipush 33
      // 68aa: new net/minecraft/client/animation/Keyframe
      // 68ad: dup
      // 68ae: ldc 1.58
      // 68b0: ldc_w -0.226
      // 68b3: ldc_w -4.809
      // 68b6: ldc_w 0.478
      // 68b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68c2: aastore
      // 68c3: dup
      // 68c4: bipush 34
      // 68c6: new net/minecraft/client/animation/Keyframe
      // 68c9: dup
      // 68ca: ldc 1.62
      // 68cc: ldc_w -0.227
      // 68cf: ldc_w -4.803
      // 68d2: ldc_w 0.469
      // 68d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68de: aastore
      // 68df: dup
      // 68e0: bipush 35
      // 68e2: new net/minecraft/client/animation/Keyframe
      // 68e5: dup
      // 68e6: ldc 1.67
      // 68e8: ldc_w -0.219
      // 68eb: ldc_w -4.791
      // 68ee: ldc_w 0.492
      // 68f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68fa: aastore
      // 68fb: dup
      // 68fc: bipush 36
      // 68fe: new net/minecraft/client/animation/Keyframe
      // 6901: dup
      // 6902: ldc 1.71
      // 6904: ldc_w -0.212
      // 6907: ldc_w -4.745
      // 690a: ldc_w 0.59
      // 690d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6910: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6913: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6916: aastore
      // 6917: dup
      // 6918: bipush 37
      // 691a: new net/minecraft/client/animation/Keyframe
      // 691d: dup
      // 691e: ldc 1.75
      // 6920: ldc_w -0.204
      // 6923: ldc_w -4.622
      // 6926: ldc_w 0.821
      // 6929: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 692c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 692f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6932: aastore
      // 6933: dup
      // 6934: bipush 38
      // 6936: new net/minecraft/client/animation/Keyframe
      // 6939: dup
      // 693a: ldc 1.79
      // 693c: ldc_w -0.195
      // 693f: ldc_w -4.435
      // 6942: ldc_w 1.112
      // 6945: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6948: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 694b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 694e: aastore
      // 694f: dup
      // 6950: bipush 39
      // 6952: new net/minecraft/client/animation/Keyframe
      // 6955: dup
      // 6956: ldc 1.83
      // 6958: ldc_w -0.189
      // 695b: ldc_w -4.204
      // 695e: ldc_w 1.397
      // 6961: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6964: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6967: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 696a: aastore
      // 696b: dup
      // 696c: bipush 40
      // 696e: new net/minecraft/client/animation/Keyframe
      // 6971: dup
      // 6972: ldc 1.88
      // 6974: ldc_w -0.186
      // 6977: ldc_w -3.952
      // 697a: ldc_w 1.635
      // 697d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6980: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6983: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6986: aastore
      // 6987: dup
      // 6988: bipush 41
      // 698a: new net/minecraft/client/animation/Keyframe
      // 698d: dup
      // 698e: ldc 1.92
      // 6990: ldc_w -0.002
      // 6993: ldc_w -2.834
      // 6996: ldc_w 3.126
      // 6999: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 699c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 699f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69a2: aastore
      // 69a3: dup
      // 69a4: bipush 42
      // 69a6: new net/minecraft/client/animation/Keyframe
      // 69a9: dup
      // 69aa: ldc 1.96
      // 69ac: ldc_w 0.612
      // 69af: ldc_w 0.037
      // 69b2: ldc_w 5.023
      // 69b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69be: aastore
      // 69bf: dup
      // 69c0: bipush 43
      // 69c2: new net/minecraft/client/animation/Keyframe
      // 69c5: dup
      // 69c6: fconst_2
      // 69c7: ldc_w 1.327
      // 69ca: ldc_w 3.089
      // 69cd: ldc_w 4.214
      // 69d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69d9: aastore
      // 69da: dup
      // 69db: bipush 44
      // 69dd: new net/minecraft/client/animation/Keyframe
      // 69e0: dup
      // 69e1: ldc 2.04
      // 69e3: ldc_w 1.086
      // 69e6: ldc_w 2.566
      // 69e9: ldc_w 1.415
      // 69ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69f5: aastore
      // 69f6: dup
      // 69f7: bipush 45
      // 69f9: new net/minecraft/client/animation/Keyframe
      // 69fc: dup
      // 69fd: ldc 2.08
      // 69ff: ldc_w 0.697
      // 6a02: ldc_w 1.01
      // 6a05: ldc_w 0.223
      // 6a08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a11: aastore
      // 6a12: dup
      // 6a13: bipush 46
      // 6a15: new net/minecraft/client/animation/Keyframe
      // 6a18: dup
      // 6a19: ldc 2.21
      // 6a1b: ldc_w 0.698
      // 6a1e: ldc_w 0.987
      // 6a21: ldc_w 0.254
      // 6a24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a2d: aastore
      // 6a2e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6a31: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6a34: ldc_w "CTR_leg_right.002"
      // 6a37: new net/minecraft/client/animation/AnimationChannel
      // 6a3a: dup
      // 6a3b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6a3e: bipush 8
      // 6a40: anewarray 28
      // 6a43: dup
      // 6a44: bipush 0
      // 6a45: new net/minecraft/client/animation/Keyframe
      // 6a48: dup
      // 6a49: ldc 1.25
      // 6a4b: ldc_w -2.707
      // 6a4e: fconst_0
      // 6a4f: ldc_w -2.155
      // 6a52: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a5b: aastore
      // 6a5c: dup
      // 6a5d: bipush 1
      // 6a5e: new net/minecraft/client/animation/Keyframe
      // 6a61: dup
      // 6a62: ldc 1.33
      // 6a64: ldc_w -2.707
      // 6a67: fconst_0
      // 6a68: ldc_w -21.067
      // 6a6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a74: aastore
      // 6a75: dup
      // 6a76: bipush 2
      // 6a77: new net/minecraft/client/animation/Keyframe
      // 6a7a: dup
      // 6a7b: ldc 1.88
      // 6a7d: ldc_w -2.707
      // 6a80: fconst_0
      // 6a81: ldc_w -21.067
      // 6a84: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a8d: aastore
      // 6a8e: dup
      // 6a8f: bipush 3
      // 6a90: new net/minecraft/client/animation/Keyframe
      // 6a93: dup
      // 6a94: ldc 1.92
      // 6a96: ldc_w -2.778
      // 6a99: ldc_w 0.809
      // 6a9c: ldc_w -18.992
      // 6a9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6aa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6aa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6aa8: aastore
      // 6aa9: dup
      // 6aaa: bipush 4
      // 6aab: new net/minecraft/client/animation/Keyframe
      // 6aae: dup
      // 6aaf: ldc 1.96
      // 6ab1: ldc_w -2.908
      // 6ab4: ldc_w 2.313
      // 6ab7: ldc_w -14.118
      // 6aba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6abd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ac0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ac3: aastore
      // 6ac4: dup
      // 6ac5: bipush 5
      // 6ac6: new net/minecraft/client/animation/Keyframe
      // 6ac9: dup
      // 6aca: fconst_2
      // 6acb: ldc_w -2.979
      // 6ace: ldc_w 3.122
      // 6ad1: ldc_w -8.469
      // 6ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6add: aastore
      // 6ade: dup
      // 6adf: bipush 6
      // 6ae1: new net/minecraft/client/animation/Keyframe
      // 6ae4: dup
      // 6ae5: ldc 2.04
      // 6ae7: ldc_w -2.843
      // 6aea: ldc_w 1.561
      // 6aed: ldc_w -3.972
      // 6af0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6af3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6af6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6af9: aastore
      // 6afa: dup
      // 6afb: bipush 7
      // 6afd: new net/minecraft/client/animation/Keyframe
      // 6b00: dup
      // 6b01: ldc 2.08
      // 6b03: ldc_w -2.707
      // 6b06: fconst_0
      // 6b07: ldc_w -2.155
      // 6b0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b13: aastore
      // 6b14: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6b17: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6b1a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 6b1d: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiGroundSmash.GROUND_SMASH Lnet/minecraft/client/animation/AnimationDefinition;
      // 6b20: return
   }
}
