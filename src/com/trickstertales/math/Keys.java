package com.trickstertales.math;

import com.badlogic.gdx.Input;

public class Keys {
	
	public static final int KEY_LEFT = Input.Keys.LEFT;
	public static final int KEY_RIGHT = Input.Keys.RIGHT;
	public static final int KEY_UP = Input.Keys.UP;
	public static final int KEY_DOWN = Input.Keys.DOWN;
	public static final int KEY_JUMP = Input.Keys.SPACE;
	public static final int KEY_RESTART = Input.Keys.R;
	public static final int KEY_PAUSE = Input.Keys.ESCAPE;
	public static final int KEY_SELECT = Input.Keys.ENTER;
	public static final int KEY_ATTACK = Input.Keys.X;
	
	public static String toString(int keycode) {
		switch (keycode) {
		// META* variables should not be used with this method.
		case 0:
			return "UNKNOWN";
		case 1:
			return "SOFT_LEFT";
		case 2:
			return "SOFT_RIGHT";
		case 3:
			return "HOME";
		case 4:
			return "BACK";
		case 5:
			return "CALL";
		case 6:
			return "ENDCALL";
		case 7:
			return "NUM_0";
		case 8:
			return "NUM_1";
		case 9:
			return "NUM_2";
		case 10:
			return "NUM_3";
		case 11:
			return "NUM_4";
		case 12:
			return "NUM_5";
		case 13:
			return "NUM_6";
		case 14:
			return "NUM_7";
		case 15:
			return "NUM_8";
		case 16:
			return "NUM_9";
		case 17:
			return "*"; // STAR
		case 18:
			return "#";  // POUND
		case 19:
			return "UP";
		case 20:
			return "DOWN";
		case 21:
			return "LEFT";
		case 22:
			return "RIGHT";
		case 23:
			return "CENTER";
		case 24:
			return "VOLUME_UP";
		case 25:
			return "VOLUME_DOWN";
		case 26:
			return "POWER";
		case 27:
			return "CAMERA";
		case 28:
			return "CLEAR";
		case 29:
			return "A";
		case 30:
			return "B";
		case 31:
			return "C";
		case 32:
			return "D";
		case 33:
			return "E";
		case 34:
			return "F";
		case 35:
			return "G";
		case 36:
			return "H";
		case 37:
			return "I";
		case 38:
			return "J";
		case 39:
			return "K";
		case 40:
			return "L";
		case 41:
			return "M";
		case 42:
			return "N";
		case 43:
			return "O";
		case 44:
			return "P";
		case 45:
			return "Q";
		case 46:
			return "R";
		case 47:
			return "S";
		case 48:
			return "T";
		case 49:
			return "U";
		case 50:
			return "V";
		case 51:
			return "W";
		case 52:
			 return "X";
		case 53:
			 return "Y";
		case 54:
			 return "Z";
		case 55:
			 return ","; // COMMA
		case 56:
			 return "."; // PERIOD
		case 57:
			 return "ALT_LEFT";
		case 58:
			 return "ALT_RIGHT";
		case 59:
			 return "SHIFT_LEFT";
		case 60:
			 return "SHIFT_RIGHT";
		case 61:
			 return "TAB";
		case 62:
			 return " "; // SPACE
		case 63:
			 return "SYM";
		case 64:
			 return "EXPLORER";
		case 65:
			 return "ENVELOPE";
		case 66:
			 return "ENTER";
		case 67:
			 return "DEL";  // DEL and BACKSPACE
		case 68:
			 return "`"; // GRAVE (the tilde key)
		case 69:
			 return "-"; // MINUS
		case 70:
			 return "="; // EQUALS
		case 71:
			 return "["; // LEFT_BRACKET
		case 72:
			 return "]";  // RIGHT_BRACKET
		case 73:
			 return "\\"; // BACKSLASH (escaped)
		case 74:
			 return ";"; // SEMICOLON
		case 75:
			 return "'"; // APOSTROPHE
		case 76:
			 return "/"; // SLASH
		case 77:
			 return "@"; // AT
		case 78:
			 return "NUM";
		case 79:
			 return "HEADSETHOOK";
		case 80:
			 return "FOCUS";
		case 81:
			 return "PLUS";
		case 82:
			 return "MENU";
		case 83:
			 return "NOTIFICATION";
		case 84:
			 return "SEARCH";
		case 85:
			 return "MEDIA_PLAY_PAUSE";
		case 86:
			 return "MEDIA_STOP";
		case 87:
			 return "MEDIA_NEXT";
		case 88:
			 return "MEDIA_PREVIOUS";
		case 89:
			 return "MEDIA_REWIND";
		case 90:
			 return "MEDIA_FAST_FORWARD";
		case 91:
			 return "MUTE";
		case 92:
			 return "PAGE_UP";
		case 93:
			 return "PAGE_DOWN";
		case 94:
			 return "PICTSYMBOLS";
		case 95:
			 return "SWITCH_CHARSET";
		case 96:
			 return "BUTTON_A";
		case 97:
			 return "BUTTON_B";
		case 98:
			 return "BUTTON_C";
		case 99:
			 return "BUTTON_X";
		case 100:
			 return "BUTTON_Y";
		case 101:
			 return "BUTTON_Z";
		case 102:
			 return "BUTTON_L1";
		case 103:
			 return "BUTTON_R1";
		case 104:
			 return "BUTTON_L2";
		case 105:
			 return "BUTTON_R2";
		case 106:
			 return "BUTTON_THUMBL";
		case 107:
			 return "BUTTON_THUMBR";
		case 108:
			 return "BUTTON_START";
		case 109:
			 return "BUTTON_SELECT";
		case 110:
			 return "BUTTON_MODE";
		case 112:
			 return "FORWARD_DEL";
		case 129:
			 return "CONTROL_LEFT";
		case 130:
			 return "CONTROL_RIGHT";
		case 131:
			 return "ESCAPE";
		case 132:
			 return "END";
		case 133:
			 return "INSERT";
		case 144:
			 return "NUMPAD_0";
		case 145:
			 return "NUMPAD_1";
		case 146:
			 return "NUMPAD_2";
		case 147:
			 return "NUMPAD_3";
		case 148:
			 return "NUMPAD_4";
		case 149:
			 return "NUMPAD_5";
		case 150:
			 return "NUMPAD_6";
		case 151:
			 return "NUMPAD_7";
		case 152:
			 return "NUMPAD_8";
		case 153:
			 return "NUMPAD_9";
		case 243:
			 return ":"; // COLON
		case 244:
			 return "F1";
		case 245:
			 return "F2";
		case 246:
			 return "F3";
		case 247:
			 return "F4";
		case 248:
			 return "F5";
		case 249:
			 return "F6";
		case 250:
			 return "F7";
		case 251:
			 return "F8";
		case 252:
			 return "F9";
		case 253:
			 return "F10";
		case 254:
			 return "F11";
		case 255:
			 return "F12"; // also BUTTON_CIRCLE
		default:
			 // key name not found
			 return null;
		}

		}


}
