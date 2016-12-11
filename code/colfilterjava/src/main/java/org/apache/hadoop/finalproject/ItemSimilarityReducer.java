package stubs;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapred.join.TupleWritable;

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
//public class ItemSimilarityReducer extends Reducer<IntPairWritable, IntPairWritable, IntPairWritable, DoubleWritable> {
public class ItemSimilarityReducer extends Reducer<IntPairWritable, IntPairWritable, IntPairWritable, DoubleWritable> {

  /*
   * The reduce method runs once for each key received from
   * the shuffle and sort phase of the MapReduce framework.
   * The method receives a key of type Text, a set of values of type
   * IntWritable, and a Context object.
   */
  @Override
	public void reduce(IntPairWritable key, Iterable<IntPairWritable> values, Context context)
			throws IOException, InterruptedException {
		double sim = 0;
		double norm_r1=0;
 double norm_r2=0;
	//	int sumRates = 0;
		
		/*
		 * For each value in the set of values passed to us by the mapper:
		 */
		ArrayList<Integer> r1=new ArrayList<Integer>();
		ArrayList<Integer> r2=new ArrayList<Integer>();
		for (IntPairWritable pair_rates : values) {
		  
		  /*
		   * Add the value to the word count counter for this key.
		   */
			
			int v1=pair_rates.getLeft();
			r1.add(v1);
			int v2=pair_rates.getRight();
			r2.add(v2);
			sim=sim+v1*v2;
			
			norm_r1=norm_r1+v1*v1;
			norm_r2=norm_r2+v2*v2;
		//	context.write(key, new DoubleWritable(v1));
			//context.write(key, new DoubleWritable(v2));
	//		context.write(key, new IntPairWritable((int)norm_r1,(int)norm_r1) );
	
		}
		
		/*
		 * Call the write method on the Context object to emit a key
		 * and a value from the reduce method. 
		 * 
		 */
		norm_r1=Math.sqrt(norm_r1);
		norm_r2=Math.sqrt(norm_r2);
		double sim2=sim/(norm_r1*norm_r2);
		//context.write(key, new IntPairWritable((int)sim,(int)sim));
	//	context.write(key,  new IntPairWritable((int)norm_r1,(int)norm_r1));
		//context.write(key,  new DoubleWritable(sim));
		//context.write(key,  new DoubleWritable(norm_r1));
	//	context.write(key,  new DoubleWritable(norm_r2));
		context.write(key,  new DoubleWritable(sim2));
	}
}