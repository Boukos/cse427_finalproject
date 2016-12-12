package stubs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/* 
 * To define a reduce function for your MapReduce job, subclass 
 * the Reducer class and override the reduce method.
 * The class definition requires four parameters: 
 *   The data type of the input key (which is the output key type 
 *   from the mapper)
 *   The data type of the input value (which is the output value 
 *   type from the mapper)
 *   The data type of the output key
 *   The data type of the output value
 */   
public class prep_job_reducer extends Reducer<Text, IntPairWritable, Text, IntPairWritable> {

  /*
   * The reduce method runs once for each key received from
   * the shuffle and sort phase of the MapReduce framework.
   * The method receives a key of type Text, a set of values of type
   * IntWritable, and a Context object.
   */
  @Override
	public void reduce(Text key, Iterable<IntPairWritable> values, Context context)
			throws IOException, InterruptedException {
		int numRates = 0;
		int sumRates = 0;
		
		/*
		 * For each value in the set of values passed to us by the mapper:
		 */
		for (IntPairWritable value : values) {
		  
		  /*
		   * Add the value to the word count counter for this key.
		   */
			numRates += value.getLeft();
			sumRates += value.getRight();
		}
		
		/*
		 * Call the write method on the Context object to emit a key
		 * and a value from the reduce method. 
		 */
		context.write(key, new IntPairWritable(numRates,sumRates));
	}
}