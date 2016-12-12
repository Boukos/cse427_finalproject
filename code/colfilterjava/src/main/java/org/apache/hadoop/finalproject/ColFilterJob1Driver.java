package org.apache.hadoop.finalproject;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class ColFilterJob1Driver extends Configured implements Tool{
	  @Override
	  public int run(String[] args) throws Exception {

	    if (args.length != 2) {
	      System.out.printf("Usage: ColFilterJob1Driver <input dir> <output dir>\n");
	      return -1;
	    }

	    Job job = new Job(getConf());
	    job.setJarByClass(ColFilterJob1Driver.class);
	    job.setJobName("Collaborative Filtering Job 1");

	    
	    FileInputFormat.setInputPaths(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.setMapperClass(ColFilterJob1Mapper.class);
	    job.setReducerClass(ColFilterJob1Reducer.class);
	    
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(IntPairWritable.class);

	    job.setOutputKeyClass(IntPairWritable.class);
	    job.setOutputValueClass(IntPairWritable.class);

	    boolean success = job.waitForCompletion(true);
	    return success ? 0 : 1;
	  }

	  public static void main(String[] args) throws Exception {
	    int exitCode = ToolRunner.run(new Configuration(), new ColFilterJob1Driver(), args);
	    System.exit(exitCode);
	  }

}
