package com.example.demowithtests.util.aspect;

import ch.qos.logback.core.pattern.color.ANSIConstants;


public final class ColorConstant extends ANSIConstants {

    public static final String ANSI_RESET = ESC_START + DEFAULT_FG + ESC_END;
    public static final String ANSI_GREEN = ESC_START + GREEN_FG + ESC_END;
    public static final String ANSI_YELLOW = ESC_START + YELLOW_FG + ESC_END;
    public static final String ANSI_BLUE = ESC_START + BLUE_FG + ESC_END;
    public static final String ANSI_CYAN = ESC_START + CYAN_FG + ESC_END;

}
