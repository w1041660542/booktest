package loggerdemo;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class LoggerDemo1 {

	public static void main(String[] args) {
		
		Logger.getGlobal().info("File->Open menu item selected");
		//丢下栈
		Thread.dumpStack();
		
		//堆栈跟追显示在System.err上，利用printStackTrace(PrintWriter)
		//显示到指定的文件中
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		new Throwable().printStackTrace(new PrintWriter(out));   
		String description = out.toString();
	}

}
