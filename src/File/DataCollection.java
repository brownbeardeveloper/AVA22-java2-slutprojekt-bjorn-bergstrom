package File;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import Factory.Consumer;
import Factory.Producer;
import Model.Buffer;

public class DataCollection implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DataCollection instance;
	Buffer buffer = null;
	Stack<Producer> producerStack = null;
	Stack<Consumer> consumerStack = null;
	ArrayList<String> logList = null;

	public static DataCollection getInstance() 
	{
		if(instance == null) 
		{
			instance = new DataCollection();
		}
		return instance;
	}

    public Stack<Producer> getProducerStack() {
        return producerStack;
    }

    public void setProducerStack(Stack<Producer> stack) {
        this.producerStack = stack;
    }
    
	public Stack<Consumer> getConsumerStack() {
		return consumerStack;
	}

	public void setConsumerStack(Stack<Consumer> consumerStack) {
		this.consumerStack = consumerStack;
	}

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public ArrayList<String> getLogList() {
        return logList;
    }

    public void setLogList(ArrayList<String> logList) {
        this.logList = logList;
    }
}