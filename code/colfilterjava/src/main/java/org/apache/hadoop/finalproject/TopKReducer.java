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

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
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

   private int N = 10; // default
 
   private TreeMultimap< Double,Integer> topK =  TreeMultimap.create(Ordering.natural(),Ordering.natural().reverse());
  

   @Override
   public void reduce(IntWritable key, Iterable<NumPairWritable> values, Context context) 
      throws IOException, InterruptedException {
	  int  values_size=0;
      for (NumPairWritable val : values) {
  
    	 topK.put(val.getRight(),val.getLeft());
    	    if (topK.size() > N) {
    	    	
                topK.remove(topK.asMap().firstKey(), topK.get(topK.asMap().firstKey()).first());
             }
    	    values_size+=1;
      }
    	 
 
    	  List<Double> keys = new ArrayList<Double>(topK.keySet());
     	//  List<Integer> ids = new ArrayList<Integer>(topK.values());
    	  int i=0;
    	 int  m=Math.min(values_size,N);
    	  while(i<m)
    	  {
          //for(int i=keys.size()-1; i>=0; i--){
          	//look up from id to get movietitles
        //  	movie_titles.txt
    			Double sim=keys.get(i);
          for(Integer id :topK.get(keys.get(i)))
          {
        	    
        	     context.write(key,new NumPairWritable(id,sim));
          i=i+1;
          }
    
        }


   }
   
   @Override
   protected void setup(Context context) 
      throws IOException, InterruptedException {
      this.N = context.getConfiguration().getInt("N", 10); // default is top 10
   }
   
//   protected void cleanup(Context context) throws IOException,
//   InterruptedException {
//	   for (Double k : topK.keySet())
//	   {
// topK.removeAll(k);
//   }
//	   topK.clear();
//}
}

   




