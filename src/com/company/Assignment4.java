package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ReaderThread implements Runnable{

    protected BlockingQueue<String> blockingQueue = null;
    protected String inputFile;

    public ReaderThread(BlockingQueue<String> blockingQueue, String inputFile){
        this.blockingQueue = blockingQueue;
        this.inputFile = inputFile;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            int count = 0;
            br = new BufferedReader(new FileReader(new File(inputFile)));
            String buffer =null;
            while((buffer=br.readLine())!=null){
                blockingQueue.put(buffer);
//                System.out.println(buffer);
                count++;
            }
            blockingQueue.put("EOF");
            System.out.println("Read " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e){

        }finally{
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

class WriterThread implements Runnable{

    protected BlockingQueue<String> blockingQueue = null;
    protected String outputFile;


    public WriterThread(BlockingQueue<String> blockingQueue, String outputFile){
        this.blockingQueue = blockingQueue;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new File(outputFile));
            int count = 0;
            while(!blockingQueue.isEmpty()){
                String buffer = blockingQueue.take();

                if (buffer.equals("EOF")) break;
                count++;

                String[] splited = buffer.split("\\s+");
                if (splited.length > 2) {
                    int result = 0;
                    int sign = 1;
                    for (int i = 0; i < splited.length; i++) {
                        if (i % 2 == 0) {
                            result += sign * Integer.parseInt(splited[i]);
                        } else {
                            sign = splited[i].equals("+") ? 1 : -1;
                        }
                    }
                    buffer += " = " + result;
                }
//                System.out.println(buffer);
                writer.println(buffer);
            }
            System.out.println("Write " + count);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(InterruptedException e){

        }finally{
            writer.close();
        }
    }
}

public class Assignment4 {
    public static void calc(String inputFile, String outputFile) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

        ReaderThread reader = new ReaderThread(queue, inputFile);
        WriterThread writer = new WriterThread(queue, outputFile);

        new Thread(reader).start();
        new Thread(writer).start();
        System.out.println("Finished...");
    }

    public static void main(String[] args) {
        String inputFile  = "/Users/jzhong7/Downloads/Antra/assignment4/input.txt";
        String outputFile  = "/Users/jzhong7/Downloads/Antra/assignment4/output.txt";

        calc(inputFile, outputFile);

    }
}
