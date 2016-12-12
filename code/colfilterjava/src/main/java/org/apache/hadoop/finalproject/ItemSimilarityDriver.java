package org.apache.hadoop.finalproject;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class ItemSimilarityDriver extends Configured implements Tool {

  public static void main(String[] args) throws Exception {
int exitCode=ToolRunner.run(new Configuration(), new ItemSimilarityDriver (), args);
System.exit(exitCode);
  }
    /*
     * Validate that two arguments were passed from the command line.
     */ 
 
    /*
     * Instantiate a Job object for your job's configuration. 
     */
public int run (String[] args) throws Exception {

    
    Job job = new Job(getConf());

   // Job job = new Job();
    
    /*
     * Specify the jar file that contains your driver, mapper, and reducer.
     * Hadoop will transfer this jar file to nodes in your cluster running 
     * mapper and reducer tasks.
     */
    job.setJarByClass(ItemSimilarityDriver.class);
    
    /*
     * Specify an easily-decipherable name for the job.
     * This job name will appear in reports and logs.
     */
    job.setJobName("Item Similarity Job");

    /*
     * TODO implement
     */
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.setMapperClass(ItemSimilarityMapper.class);
    job.setReducerClass(ItemSimilarityReducer.class);

   // job.setNumReduceTasks(0);
    job.setMapOutputKeyClass(IntPairWritable.class);
    job.setMapOutputValueClass(IntPairWritable.class);
    job.setOutputKeyClass(IntPairWritable.class);
    //job.setOutputValueClass(IntPairWritable.class);
    job.setOutputValueClass(DoubleWritable.class);
    
    /*
     * 
     * 
     * 
     * Start the MapReduce job and wait for it to finish.
     * If it finishes successfully, return 0. If not, return 1.
     */
    boolean success = job.waitForCompletion(true);
    return (success ? 0 : 1);
  }
}


