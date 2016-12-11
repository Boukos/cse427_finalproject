package org.apache.hadoop.finalproject;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;

public class ColFilterJob1Mapper extends Mapper<LongWritable, Text, IntWritable, IntPairWritable>{

	/*
	   * The map method runs once for each line of text in the input file.
	   * The method receives a key of type LongWritable, a value of type
	   * Text, and a Context object.
	   */
	  @Override
	  public void map(LongWritable key, Text value, Context context)
	      throws IOException, InterruptedException {
	
	    /*
	     * Convert the line, which is received as a Text object,
	     * to a String object.
	     */
	    String line = value.toString();
	
	    /*
	     * The line.split("\\W+") call uses regular expressions to split the
	     * line up by non-word characters.
	     * 
	     * If you are not familiar with the use of regular expressions in
	     * Java code, search the web for "Java Regex Tutorial." 
	     */
	    
	    String[] words=line.split(",");
	    if(words.length==3)
	    {
	    	System.out.println("rating " + words[0] + " " + words[1] + " " + words[2]);
//	    	Writable[] output = new Writable[2];
//	    	output[0] = new Text(words[0]);
	    	int rate=(int)Double.parseDouble(words[2]);
//	    	output[1] = new IntWritable(rate);
	    	context.write(new IntWritable(Integer.parseInt(words[1])), new IntPairWritable(Integer.parseInt(words[0]), rate));
		}
	  }
}
