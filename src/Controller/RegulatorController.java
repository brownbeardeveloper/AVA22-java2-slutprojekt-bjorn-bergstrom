package Controller;

import java.awt.Color;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Factory.Producer;
import Factory.Consumer;
import Model.Buffer;
import Properties.FactoryProperties;
import Util.RandomNumberGenerator;
import Util.BufferEventState;
import File.DataCollection;
import File.FileManager;
import View.SwingGUI;

public class RegulatorController implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FileManager fileManager;
	static RegulatorController controller;
    Buffer buffer = new Buffer();
    SwingGUI swingGUI;
    BufferEventState state;
    
    ExecutorService executor = Executors.newFixedThreadPool(FactoryProperties.businessOperations);
    Stack<Producer> producerStack = new Stack<Producer>();
    Stack<Consumer> consumerStack = new Stack<Consumer>();
    ArrayList<String> logList = new ArrayList<String>();
            
    public static RegulatorController getInstance() 
    {
    	if (controller == null) 
    	{
    		controller = new RegulatorController();
    	}
		return controller;
    }

    public void startUp() 
    {
    	swingGUI = SwingGUI.getInstance();
        swingGUI.makeJFrameVisible();
        
        int quantity = RandomNumberGenerator.getInt(3,15); // generate an integer between 3 and 15
        
        // create random quantity of consumers
        for (int i = 0; i < quantity; i++) 
        {
        	addConsumers();
        }
        
        // create another thread to get buffer's average size during every 10 seconds
        AverageBufferThread averageBufferThread = new AverageBufferThread(buffer);
        Thread routineThread = new Thread(averageBufferThread);
        routineThread.start();
    }
    
    private void addConsumers() 
    {
        int sleepTime = RandomNumberGenerator.getInt(1000, 10000); // generate an integer between 1000 and 10000 as millisecond
        Consumer consumer = new Consumer(buffer, sleepTime);
        consumerStack.push(consumer);
        executor.submit(consumer);
        addLogWithTime(consumer + " with sleep time " + String.valueOf(sleepTime) + "ms created");
    }

    public void addProducer() 
    {
        int sleepTime = RandomNumberGenerator.getInt(1000, 10000); // generate an integer between 1000 and 10000 as millisecond
        Producer producer = new Producer(buffer, sleepTime);
        producerStack.push(producer);
        executor.submit(producer);
        addLogWithTime(producer + " with sleep time " + String.valueOf(sleepTime) + "ms created");
		swingGUI.setProducersLabel(producerStack.size());    
	}
    
    public void addLogWithTime(String value) 
    {
    	logList.add(LocalDateTime.now() + " " +  value);
    	System.out.println(LocalDateTime.now() + " " +  value);
    	swingGUI.insertNewLineInTextArea(LocalDateTime.now() + " " +  value);
    }
    
    private void addLogWithoutTime(String value) 
    {
    	logList.add(value);
    	swingGUI.insertNewLineInTextArea(value);
    }
    
    public void deleteProducer() 
    {
        if (!producerStack.isEmpty()) 
        {
            Producer producer = producerStack.pop();
            producer.stop();
            addLogWithTime(producer + " stopped");
            producer = null;
    		swingGUI.setProducersLabel(producerStack.size());    
        } 
        else 
        {
            System.out.println("there's no more producer!!");
        }
    }
    
    public void updateProgressBar(double progress)
    {
		Color color;
		
		// the conditions determine the colour of the progress bar and trigger events 
		// when the available units in the buffer are too low or too high
		if (progress < 10 && state != BufferEventState.UNDER_TEN_PERCENT)
		{
		    color = Color.red;
		    state = BufferEventState.UNDER_TEN_PERCENT;
		    addLogWithTime("the number of available units is too low – 10%");
		} 
		else if (progress >= 10 && progress < 40) 
		{
		    color = Color.yellow;
		    state = BufferEventState.BETWEEN_TEN_AND_NINETY_PERCENT;
		} 
		else if (progress >= 40 && progress < 60) 
		{
		    color = Color.green;
		    state = BufferEventState.BETWEEN_TEN_AND_NINETY_PERCENT;
		} 
		else if (progress >= 60 && progress < 90) 
		{
		    color = Color.yellow;
		    state = BufferEventState.BETWEEN_TEN_AND_NINETY_PERCENT;
		} 
		else if (progress >= 90 && state != BufferEventState.OVER_NINETY_PERCENT)
		{
		    color = Color.red;
		    addLogWithTime("the number of available units is too high – 90%");
		    state = BufferEventState.OVER_NINETY_PERCENT;
		} 
		else
		{
			color = Color.red;
		}
		
		// update the progress bar with the current value and specified color
		swingGUI.SetProgressBar((int) progress, color);
    }
    
    public boolean clearSystemConfig() 
    {
        while (!producerStack.isEmpty()) 
        {
            Producer producer = producerStack.pop();
            producer.stop();
            addLogWithTime(producer + " stopped");
            producer = null;
    		swingGUI.setProducersLabel(producerStack.size());    
        } 
        
        while (!consumerStack.isEmpty()) 
        {
            Consumer consumer = consumerStack.pop();
            consumer.stop();
            addLogWithTime(consumer + " stopped");
            consumer = null;
        } 
        logList.clear();
        buffer.clear();
        swingGUI.resetTextArea();    	
    	return true;
    }
    
    // recreating everything and execute tasks in thread pool
    public void getNewConfigfromFile() 
    {
    	DataCollection instanceFromFile = FileManager.getDataCollectionFromFile();

    	if (clearSystemConfig()) 
    	{
            Stack<Consumer> newConsumerStack = instanceFromFile.getConsumerStack();
            Stack<Producer> newProducerStack = instanceFromFile.getProducerStack();
            ArrayList<String> newLogList = instanceFromFile.getLogList();
            Buffer newBuffer = instanceFromFile.getBuffer();
            
            if(newLogList != null)
            {
            	for(String newLog : newLogList) 
            	{
            		swingGUI.insertNewLineInTextArea(newLog);
            		addLogWithoutTime(newLog);
            	}
            }
            
            if(newConsumerStack != null) 
            {
            	for(Consumer consumer: newConsumerStack) 
            	{
            		int sleepTime = consumer.getSleepTime(); 
            		System.out.println(consumer + " with sleep time " + String.valueOf(sleepTime) + "ms recreated");
                    consumerStack.push(consumer);
            		executor.submit(consumer);
            	}
            }
            
            if(newProducerStack != null)
            {
            	for(Producer producer: newProducerStack) 
            	{
            		int sleepTime = producer.getSleepTime(); 
            		System.out.println(producer + " with sleep time " + String.valueOf(sleepTime) + "ms recreated");
                    producerStack.push(producer);
            		executor.submit(producer);
            		swingGUI.setProducersLabel(producerStack.size());    
            	}
            }
                        
            if(newBuffer != null)
            {
            	buffer = newBuffer;
            	buffer.checkBufferSize();
            }
    	} 
    	else 
    	{
    		System.out.println("File collection is null");
    	}
    }
    
    // saves this application data into a file
    public void saveThisConfigToFile() 
    {
    	DataCollection collection = DataCollection.getInstance();	
    	collection.setProducerStack(producerStack);
    	collection.setConsumerStack(consumerStack);
    	collection.setBuffer(buffer);
    	collection.setLogList(logList);
    	FileManager.saveDataCollectionToFile();
    }
}
