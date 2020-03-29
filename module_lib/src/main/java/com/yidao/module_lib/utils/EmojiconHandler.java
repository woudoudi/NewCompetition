/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yidao.module_lib.utils;

import android.content.Context;

import com.yidao.module_lib.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
public final class EmojiconHandler {
    private EmojiconHandler() {
    }

    private static final Map<Integer,Integer> sEmojisMap = new HashMap(130);

    static {
        // People
        sEmojisMap.put(0x1f60a, R.drawable.emoji_1f60a);
        sEmojisMap.put(0x263a, R.drawable.emoji_263a);
        sEmojisMap.put(0x1f61a, R.drawable.emoji_1f61a);
        sEmojisMap.put(0x1f61c, R.drawable.emoji_1f61c);
        sEmojisMap.put(0x1f61d, R.drawable.emoji_1f61d);
        sEmojisMap.put(0x1f61b, R.drawable.emoji_1f61b);
        sEmojisMap.put(0x1f60c, R.drawable.emoji_1f60c);
        sEmojisMap.put(0x1f61e, R.drawable.emoji_1f61e);
        sEmojisMap.put(0x1f62d, R.drawable.emoji_1f62d);
        sEmojisMap.put(0x1f62a, R.drawable.emoji_1f62a);
        sEmojisMap.put(0x1f62b, R.drawable.emoji_1f62b);
        sEmojisMap.put(0x1f60b, R.drawable.emoji_1f60b);
        sEmojisMap.put(0x1f60e, R.drawable.emoji_1f60e);
        sEmojisMap.put(0x1f61f, R.drawable.emoji_1f61f);
        sEmojisMap.put(0x1f47f, R.drawable.emoji_1f47f);
        sEmojisMap.put(0x1f62e, R.drawable.emoji_1f62e);
        sEmojisMap.put(0x1f62c, R.drawable.emoji_1f62c);
        sEmojisMap.put(0x1f62f, R.drawable.emoji_1f62f);
        sEmojisMap.put(0x1f60f, R.drawable.emoji_1f60f);
        sEmojisMap.put(0x1f47c, R.drawable.emoji_1f47c);
        sEmojisMap.put(0x1f63a, R.drawable.emoji_1f63a);
        sEmojisMap.put(0x1f63b, R.drawable.emoji_1f63b);
        sEmojisMap.put(0x1f63d, R.drawable.emoji_1f63d);
        sEmojisMap.put(0x1f63c, R.drawable.emoji_1f63c);
        sEmojisMap.put(0x1f44e, R.drawable.emoji_1f44e);
        sEmojisMap.put(0x1f44c, R.drawable.emoji_1f44c);
        sEmojisMap.put(0x1f44b, R.drawable.emoji_1f44b);
        sEmojisMap.put(0x1f64c, R.drawable.emoji_1f64c);
        sEmojisMap.put(0x261d, R.drawable.emoji_261d);
        sEmojisMap.put(0x1f44f, R.drawable.emoji_1f44f);
        sEmojisMap.put(0x1f4aa, R.drawable.emoji_1f4aa);
        sEmojisMap.put(0x1f3c3, R.drawable.emoji_1f3c3);
        sEmojisMap.put(0x2764, R.drawable.emoji_2764);
        sEmojisMap.put(0x1f49e, R.drawable.emoji_1f49e);
        sEmojisMap.put(0x1f48c, R.drawable.emoji_1f48c);
        sEmojisMap.put(0x1f48b, R.drawable.emoji_1f48b);
        sEmojisMap.put(0x1f48e, R.drawable.emoji_1f48e);

        // Objects
        sEmojisMap.put(0x1f49d, R.drawable.emoji_1f49d);
        sEmojisMap.put(0x1f52a, R.drawable.emoji_1f52a);
        sEmojisMap.put(0x2702, R.drawable.emoji_2702);
        sEmojisMap.put(0x1f3b5, R.drawable.emoji_1f3b5);
        sEmojisMap.put(0x1f3b6, R.drawable.emoji_1f3b6);
        sEmojisMap.put(0x1f3b7, R.drawable.emoji_1f3b7);
        sEmojisMap.put(0x1f3b8, R.drawable.emoji_1f3b8);
        sEmojisMap.put(0x1f3c0, R.drawable.emoji_1f3c0);
        sEmojisMap.put(0x1f3c9, R.drawable.emoji_1f3c9);
        sEmojisMap.put(0x1f3c1, R.drawable.emoji_1f3c1);
        sEmojisMap.put(0x1f3c7, R.drawable.emoji_1f3c7);
        sEmojisMap.put(0x1f3c6, R.drawable.emoji_1f3c6);
        sEmojisMap.put(0x1f3c2, R.drawable.emoji_1f3c2);
        sEmojisMap.put(0x1f3ca, R.drawable.emoji_1f3ca);
        sEmojisMap.put(0x1f3c4, R.drawable.emoji_1f3c4);
        // Places

        sEmojisMap.put(0x1f3e3, R.drawable.emoji_1f3e3);
        sEmojisMap.put(0x1f3e5, R.drawable.emoji_1f3e5);
        sEmojisMap.put(0x1f3e6, R.drawable.emoji_1f3e6);
        sEmojisMap.put(0x2708, R.drawable.emoji_2708);
        sEmojisMap.put(0x1f69e, R.drawable.emoji_1f69e);
        sEmojisMap.put(0x1f69d, R.drawable.emoji_1f69d);
        sEmojisMap.put(0x1f68e, R.drawable.emoji_1f68e);
        sEmojisMap.put(0x1f68d, R.drawable.emoji_1f68d);
        sEmojisMap.put(0x3299, R.drawable.emoji_3299);
        sEmojisMap.put(0x3297, R.drawable.emoji_3297);
        sEmojisMap.put(0x1f49f, R.drawable.emoji_1f49f);
        sEmojisMap.put(0x1f3e7, R.drawable.emoji_1f3e7);
        sEmojisMap.put(0x2122, R.drawable.emoji_2122);
        sEmojisMap.put(0x2757, R.drawable.emoji_2757);
        sEmojisMap.put(0x2795, R.drawable.emoji_2795);
        sEmojisMap.put(0x2796, R.drawable.emoji_2796);
        sEmojisMap.put(0x2797, R.drawable.emoji_2797);
        sEmojisMap.put(0x2665, R.drawable.emoji_2665);
        sEmojisMap.put(0x1f917, R.drawable.emoji_1f917);
        sEmojisMap.put(0x1f914, R.drawable.emoji_1f914);
        sEmojisMap.put(0x1f910, R.drawable.emoji_1f910);
        sEmojisMap.put(0x1f913, R.drawable.emoji_1f913);
        sEmojisMap.put(0x2639, R.drawable.emoji_2639);
        sEmojisMap.put(0x1f912, R.drawable.emoji_1f912);
        sEmojisMap.put(0x1f915, R.drawable.emoji_1f915);
        sEmojisMap.put(0x1f911, R.drawable.emoji_1f911);
        sEmojisMap.put(0x1f918, R.drawable.emoji_1f918);
        sEmojisMap.put(0x2763, R.drawable.emoji_2763);
        sEmojisMap.put(0x2620, R.drawable.emoji_2620);
        sEmojisMap.put(0x1f3c5, R.drawable.emoji_1f3c5);
        sEmojisMap.put(0x1f3d3, R.drawable.emoji_1f3d3);
        sEmojisMap.put(0x2622, R.drawable.emoji_2622);
    }

    private static boolean isSoftBankEmoji(char c) {
        return ((c >> 12) == 0xe);
    }

    private static int getEmojiResource(Context context, int codePoint) {
        return sEmojisMap.get(codePoint);
    }

    public static Map<Integer,Integer> getsEmojisMap(){
        return sEmojisMap;
    }
}
