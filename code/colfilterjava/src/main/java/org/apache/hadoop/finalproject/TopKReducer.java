package stubs;


import java.io.IOException;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configurable;
;

/**
 *  Reducer's input are local top N from all mappers.
 *  We have a single reducer, which creates the final top N.
 * 
 * @author Mahmoud Parsian
 *
 */
public class TopKReducer  extends
   Reducer<IntWritable, NumPairWritable, IntWritable, NumPairWritable> {

   private int N = 3; // default
 
   private SortedMap< Double,Integer> topK = new TreeMap< Double,Integer>();
  

   @Override
   public void reduce(IntWritable key, Iterable<NumPairWritable> values, Context context) 
      throws IOException, InterruptedException {

      for (NumPairWritable val : values) {
  
    	 topK.put(val.getRight(),val.getLeft());
    	    if (topK.size() > N) {
                topK.remove(topK.firstKey());
             }
      }
    	 
 
    	  List<Double> keys = new ArrayList<Double>(topK.keySet());
     	//  List<Integer> ids = new ArrayList<Integer>(topK.values());
          for(int i=keys.size()-1; i>=0; i--){
          	//look up from id to get movietitles
        //  	movie_titles.txt
          	Integer id=topK.get(keys.get(i));
          	Double sim=keys.get(i);
        
     context.write(key,new NumPairWritable(id,sim));
        }


   }
   
   @Override
   protected void setup(Context context) 
      throws IOException, InterruptedException {
      this.N = context.getConfiguration().getInt("N", 2); // default is top 10
   }
   
   protected void cleanup(Context context) throws IOException,
   InterruptedException {
 topK.clear();
}
}

   




