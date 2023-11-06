package Properties;

public abstract class FileProperties 
{
	private static final String configFilePath = "config.txt";
	
	public static String getConfigFilePath()
	{
		return configFilePath;
	}
}
