
package stubs;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * This driver class is called using the ToolRunner.run method
 * call in the main method (below). Extending the Configured class 
 * enables the driver class to access Hadoop configuration options.
 */
public class TopKDriver extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {

  

    Job job = new Job(getConf());
    job.setJarByClass(AggregateByKeyDriver.class);
    job.setJobName("TopKDriver");
  

    /*
     * TODO implement
     */
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    /*
     * Specify the mapper and reducer classes.
     */
    job.setMapperClass(TopKMapper.class);
    job.setReducerClass(TopKReducer.class);

    job.setMapOutputKeyClass(IntWritable.class);   
    job.setMapOutputValueClass(NumPairWritable.class);   
    
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(NumPairWritable.class);
  

    boolean success = job.waitForCompletion(true);
    return success ? 0 : 1;
  }

  /*
   * The main method calls the ToolRunner.run method, which
   * calls an options parser that interprets Hadoop command-line
   * options and puts them into a Configuration object.
   */
  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new Configuration(), new TopKDriver(), args);
    System.exit(exitCode);
  }
}
