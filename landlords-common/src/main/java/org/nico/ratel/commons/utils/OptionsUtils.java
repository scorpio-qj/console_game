package org.nico.ratel.commons.utils;

public class OptionsUtils {

	public static int getOptions(String line) {
		int option = -1;
		try {
			option = Integer.parseInt(line);
		} catch (Exception ignored) {}
		return option;
	}


	public static class OPTIONS_NUMBER{

		public static final int ONE_1=1;
		public static final int TWO_2=2;
		public static final int THREE_3=3;
		public static final int FOUR_4=4;
		public static final int FIVE_5=5;
		public static final int SIX_6=6;
		public static final int SEVEN_7=7;
		public static final int EIGHT_8=8;
		public static final int NINE_9=9;
		public static final int TEN_10=10;

	}

	/**
	 * 退出命令
	 * @param cmd
	 * @return
	 */
	public static boolean CMD_EXIT(String cmd){
		return cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("e");
	}

	/**
	 * 回退命令
	 */
	public static boolean CMD_BACK(String cmd){
		return cmd.equalsIgnoreCase("back");
	}

}
