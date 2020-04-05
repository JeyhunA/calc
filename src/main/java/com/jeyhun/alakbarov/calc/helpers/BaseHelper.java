package com.jeyhun.alakbarov.calc.helpers;

/**
 * Class contains general helper methods.
 *
 * @author Jeyhun_Alakbarov
 */
public class BaseHelper
{
	/**
	 * Verify that provided string is a number or not.
	 *
	 * @param str {@link String} that should be checked
	 * @return <code>true</code> if string is a number and <code>false</code> if not.
	 */
	public static boolean isNumber(String str)
	{
		try
		{
			Double.valueOf(str);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Prints message and close application with error.
	 *
	 * @param errorMessage message that should be displayed
	 */
	public static void exitWithError(String errorMessage)
	{
		System.out.println(errorMessage);
		int status = 1;
		System.exit(status);
	}
}
