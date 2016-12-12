package org.apache.hadoop.finalproject;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *  Mapper's input are read from SequenceFile and records are: (K, V)
 *    where 
 *          K is a Text
 *          V is an Integer
 * 
 * @author Mahmoud Parsian
 *
 */
public class TopKMapper extends
 //  Mapper<Text, IntWritable, NullWritable, Text> {
Mapper<LongWritable, Text, IntWritable, NumPairWritable>{
   
   @Override
   public void map(LongWritable k, Text v, Context context)
         throws IOException, InterruptedException {

	   
	   String line=v.toString();
	   String[] words=line.split("\t");
		double sim=Double.parseDouble( words[1]); 
      String items = words[0];//.toString();
      items=items.replace(")","");
      items=items.replace("(","");
      String[] tt=items.split(",");
      int item2=Integer.parseInt(tt[1]);
      int item=Integer.parseInt(tt[0]);
   context.write(new IntWritable(item),new NumPairWritable(item2,sim));
   }
   


}