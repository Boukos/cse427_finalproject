package org.apache.hadoop.finalproject;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class ColFilterDriver extends Configured implements Tool{
	  @Override
	  public int run(String[] args) throws Exception {

	    if (args.length != 2) {
	      System.out.printf("Usage: WordCountDriver <input dir> <output dir>\n");
	      return -1;
	    }

	    Job job = new Job(getConf());
	    job.setJarByClass(ColFilterDriver.class);
	    job.setJobName("Word Count with Combiner");

	    FileInputFormat.setInputPaths(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.setMapperClass(ColFilterMapper.class);
	    job.setReducerClass(ColFilterReducer.class);
	    
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    

	    boolean success = job.waitForCompletion(true);
	    return success ? 0 : 1;
	  }

	  public static void main(String[] args) throws Exception {
	    int exitCode = ToolRunner.run(new Configuration(), new ColFilterDriver(), args);
	    System.exit(exitCode);
	  }

}
