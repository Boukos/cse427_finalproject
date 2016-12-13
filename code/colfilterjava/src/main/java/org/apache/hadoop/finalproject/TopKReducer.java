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
 
  

   @Override
   public void reduce(IntWritable key, Iterable<NumPairWritable> values, Context context) 
      throws IOException, InterruptedException {
	  int  values_size=0;
	  TreeMultimap< Double,Integer> topK =  TreeMultimap.create(Ordering.natural(),Ordering.natural().reverse());
	  
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
    	  int j=0;
    	 int  m=Math.min(values_size,N);
    	  while(j<keys.size())
    	  {
          //for(int i=keys.size()-1; i>=0; i--){
          	//look up from id to get movietitles
        //  	movie_titles.txt
    			Double sim=keys.get(j);
    			
          for(Integer id :topK.get(keys.get(j)))
          {
        	    
        	     context.write(key,new NumPairWritable(id,sim));
          i=i+1;
          }
          j=j+1;
    
        }
topK.clear();

   }
   
   @Override
   protected void setup(Context context) 
      throws IOException, InterruptedException {
      this.N = context.getConfiguration().getInt("N", 10); // default is top 10
   }
   
}
   




