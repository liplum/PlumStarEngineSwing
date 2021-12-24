package net.liplum.util;

import java.util.Map;
import java.util.Map.Entry;

public class StringTools {

    /**
     * @param remappingRegulation The regulation of remapping.
     * @param str                 The String with waiting for remapping.
     */
    public static void remappingChar(Map<Character, Character> remappingRegulation, String str) {
        if (remappingRegulation != null)
            if (remappingRegulation.entrySet().size() > 0)
                for (Entry<Character, Character> entry : remappingRegulation.entrySet())
                    str.replace(entry.getKey(), entry.getValue());
    }
}
