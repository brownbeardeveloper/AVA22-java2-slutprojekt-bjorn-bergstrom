package File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import Properties.FileProperties;

public abstract class FileManager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static boolean writeDataCollectionToFile() 
	{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileProperties.getConfigFilePath()))) 
        {   
        	DataCollection dataCollection = DataCollection.getInstance();
        	oos.writeObject(dataCollection);
    	    return true;
    	} 
    	catch (Exception ex) 
    	{
    	    ex.printStackTrace();
    	    return false;
    	} 
	}
	
	public static boolean saveDataCollectionToFile() 
	{
		if(fileExists(FileProperties.getConfigFilePath())) 
    	{
    		if(writeDataCollectionToFile()) 
    		{
    			System.out.println("writting to this file");
        		return true;
        	}	
    	}
    	else if (createFile(FileProperties.getConfigFilePath()))
    	{
    		if(writeDataCollectionToFile()) 
    		{
        		return true;
        	}	
    	}
		return false;
	}
	
	public static DataCollection getDataCollectionFromFile()
    {
		DataCollection collection = null;
    	
    	if(fileExists(FileProperties.getConfigFilePath())) 
    	{
        	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FileProperties.getConfigFilePath()))) 
        	{
        		collection = (DataCollection) in.readObject();
        	} 
        	catch (IOException | ClassNotFoundException e) 
        	{
                e.printStackTrace();
        	}
    	}
    	return collection;
    }
	
	/**
     * Creates a new file with the specified name.
     *
     * @param fileName The name of the file to be created.
     * @return Returns true if the file was successfully created, false otherwise.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private static boolean createFile(String fileName)  
    {
        File file = new File(fileName);
        try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
   
    /**
     * Checks if the file with the specified name exists.
     *
     * @param fileName The name of the file to check.
     * @return Returns true if the file exists, false otherwise.
     */
    private static boolean fileExists(String fileName) 
    {
        File file = new File(fileName);
        return file.exists();
    }
}
